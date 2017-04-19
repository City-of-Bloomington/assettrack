/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */
package assets;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.mail.*;
import javax.mail.Address;
import javax.mail.internet.*;
import javax.activation.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;
import org.apache.log4j.Logger;


public class ImportJob implements Job{

    boolean debug = true;
		Logger logger = Logger.getLogger(ImportJob.class);
		static String sqliteDbFile = null;
		long serialVersionUID = 1417L;
		//
		// will send emails
		public ImportJob(){

		}
		public void execute(JobExecutionContext context)
        throws JobExecutionException {
				try{
						JobDataMap dataMap = context.getJobDetail().getJobDataMap();
						String str = (String)dataMap.get("sqliteDbFile");
						if(str != null){
								sqliteDbFile = str;
						}
						doInit();
						doWork();
						doDestroy();
				}
				catch(Exception ex){
						logger.error(ex);
						System.err.println(ex);
				}
		}
		public void doInit(){
				//
				// we can delete the old records from quartz
				//

		}
		public void doDestroy() {

		}	    
    /**
     * 
     */
    public void doWork(){
				ImportManager im = new ImportManager(debug);
				im.setAutoImportFlag();
				im.setSqliteDbFile(sqliteDbFile);
				String back = im.doImport();
				if(!back.equals("")){
						logger.error(back);
				}
		}

}






















































