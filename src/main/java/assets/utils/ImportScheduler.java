/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */
package assets.utils;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;

public class ImportScheduler implements ServletContextListener{

    static boolean debug = false;
    static Logger logger = LogManager.getLogger(ImportScheduler.class);
    static final long serialVersionUID = 1435L;
    static String sqliteDbFile = null;
    int year = 2016, month = 7, day = 6;
    Date startDate = null;
    //		
    public void contextDestroyed(ServletContextEvent arg0) {
	//
    };
    public void contextInitialized(ServletContextEvent sce){ 

	try{
	    Calendar cal = new GregorianCalendar();
	    cal.set(year, (month-1), day);
	    cal.set(Calendar.HOUR_OF_DAY, 7);//to run at 7:45 am of the specified day
	    cal.set(Calendar.MINUTE, 45);
	    startDate = cal.getTime();
	    if(sqliteDbFile == null){
		ServletContext ctx = sce.getServletContext();
		String str = ctx.getInitParameter("sqliteDbFile");
		if(str != null)
		    sqliteDbFile = str;
	    }
	    run();
	}
	catch(Exception ex){
	    logger.error(ex);
	}
    }
	
    public void run() throws Exception {
	//
	try{
	    String msg = "";
	    logger.debug("------- Initializing ----------------------");
			
	    // First we must get a reference to a scheduler
	    // SchedulerFactory sf = new StdSchedulerFactory();
	    // Scheduler sched = sf.getScheduler();
			
	    logger.debug("------- Initialization Complete -----------");
			
	    // computer a time that is on the next round minute
	    //  Date runTime = evenMinuteDate(new Date());
			
	    logger.debug("------- Scheduling Job  -------------------");
			
	    // define the job and tie it to our Job class
			
	    String jobName = "job_import_16_"+month+"_"+day;
	    //System.err.println(" before job");
	    JobDetail job = JobBuilder.newJob(ImportJob.class)
		.withIdentity(jobName, "import_group")
		.build();
	    job.getJobDataMap().put("sqliteDbFile", sqliteDbFile);
	    // JobDetail job = new JobDetail();
	    //job.setName(jobName);
	    // job.setJobClass(InventoryJob.class);
	    //
	    // pass initialization parameters into the job
	    // job.getJobDataMap().put("to","sibow"+emailStr);
	    // job.getJobDataMap().put("from","sibow"+emailStr);
	    /*
	      if(!act.getAnotherUserid().equals("")){
	      job.getJobDataMap().put("cc", act.getAnotherUserid()+emailStr);
	      }
	    */
	    // job.getJobDataMap().put("subject","Marketbucks Iventory Notification");	   

	    //second minute hours day month (day of week) year
	    // 
	    // Trigger will run at 7am on the speciified date
	    // cron date and time entries (year can be ignored)
	    // second minute hour day-of-month month week-day year
	    // you can use ? no specific value, 0/5 for incrment (every 5 seconds)
	    // * for any value (in minutes mean every minute
	    /*
	      Trigger trigger = newTrigger()
	      .withIdentity("trigger_"+month+"_"+day+"_"+year, "accrualGroup")
	      .startAt(startDate)
	      .withSchedule(cronSchedule("* * 8 0 0/2 * ,FRI")
	      // .withMisfireHandlingInstructionFireNow())
	      .withMisfireHandlingInstructionFireAndProceed())
	      .endAt(endDate)						  
	      // .withMisfireHandlingInstructionIgnoreMisfires())
	      .build();
	    */
	    Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity(jobName, "import_group")
		.startAt(startDate)
		.withSchedule(simpleSchedule()
			      // .withIntervalInMinutes(5)
			      .withIntervalInHours(24) //  every day
			      .repeatForever()
			      // .withRepeatCount(2) 
			      // .withMisfireHandlingInstructionFireNow())
			      .withMisfireHandlingInstructionIgnoreMisfires())
		// .endAt(endDate)						  
		.build();
			
	    // Tell quartz to schedule the job using our trigger
	    Scheduler sched = new StdSchedulerFactory().getScheduler();
	    sched.start();
	    sched.scheduleJob(job, trigger);
	    // System.err.println(" after schedule ");
	    //  logger.info(job.getKey() + " will run at: " + runTime);  
			
	    // Start up the scheduler (nothing can actually run until the 
	    // scheduler has been started)
	    //	sched.start();

	    /*
	      logger.info("------- Started Scheduler -----------------");
			  
	      // wait long enough so that the scheduler as an opportunity to 
	      // run the job!
	      logger.info("------- Waiting 65 seconds... -------------");
	      try {
	      // wait 65 seconds to show job
	      Thread.sleep(65L * 1000L); 
	      // executing...
	      } catch (Exception e) {
	      }
			  
	      // shut down the scheduler
	      logger.info("------- Shutting Down ---------------------");
	      sched.shutdown(true);
	      logger.info("------- Shutdown Complete -----------------");
	    */
	}catch(Exception ex){
	    logger.error(ex);
	}
    }
	
	
}
