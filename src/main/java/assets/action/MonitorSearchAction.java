package assets.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

public class MonitorSearchAction extends TopAction{

    static final long serialVersionUID = 1520L;	
    static Logger logger = LogManager.getLogger(MonitorSearchAction.class);
    //
    List<Monitor> monitors = null;
    MonitorList monitorList = null;
    String monitorsTitle = " Most recent Monitors";		
    public String execute(){
	String ret = SUCCESS;
	String back = doPrepare();
	if(!back.equals("")){
	    try{
		HttpServletResponse res = ServletActionContext.getResponse();
		String str = url+"Login";
		res.sendRedirect(str);
		return super.execute();
	    }catch(Exception ex){
		System.err.println(ex);
	    }	
	}
	if(!action.equals("")){
	    getMonitorList();
	    monitorList.setNoLimit();
	    back = monitorList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		monitors = monitorList.getMonitors();
		if(monitors != null && monitors.size() > 0){
		    if(monitors.size() == 1){
			Monitor one = monitors.get(0);
			try{
			    HttpServletResponse res = ServletActionContext.getResponse();
			    String str = url+"monitor.action?id="+one.getId();
			    res.sendRedirect(str);
			    return super.execute();
			}catch(Exception ex){
			    System.err.println(ex);
			}													
		    }										
		    monitorsTitle = "Found "+monitors.size()+" records";
		}
		else{
		    addActionMessage("No match found");
		    monitorsTitle = "No match found";
		}
	    }
	}				
	else{
	    getMonitorList();
	    MonitorList ml = new MonitorList(debug);
	    ml.setStatus("Active");
	    back = ml.find();
	    monitors = ml.getMonitors();
	}
	return ret;
    }
    public MonitorList getMonitorList(){
	if(monitorList == null){
	    monitorList = new MonitorList(debug); 
	}
	return monitorList;
    }

    public String getMonitorsTitle(){
	return monitorsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    // most recent
    public List<Monitor> getMonitors(){ 
	return monitors;
    }

}





































