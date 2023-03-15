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

public class MonitorAction extends TopAction{

    static final long serialVersionUID = 1500L;	
    static Logger logger = LogManager.getLogger(MonitorAction.class);
    //
    String device_id="";
    Monitor monitor = null;
    List<Monitor> monitors = null;
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
	if(action.equals("Save")){ 
	    back = monitor.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = monitor.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = monitor.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Save Partial")){ 
	    back = monitor.doPartialUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	    ret = "partial";
	}				
	else if(action.equals("Delete")){ 
	    back = monitor.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
	    }
	}
	else if(action.equals("Edit")){
	    monitor = new Monitor(debug, id);
	    back = monitor.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    if(monitor.canBePartialUpdated()){
		ret = "partial";
	    }
	}				
	else if(!id.equals("")){ 
	    monitor = new Monitor(debug, id);
	    back = monitor.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    ret = "view";
	}
	else{
	    getMonitor();
	}
	return ret;
    }
    public Monitor getMonitor(){ 
	if(monitor == null){
	    if(!id.equals("")){
		monitor = new Monitor(debug, id);
	    }
	    else{
		monitor = new Monitor();
		if(!device_id.equals("")){
		    monitor.setDevice_id(device_id);
		}
	    }
	}		
	return monitor;
    }

    public void setMonitor(Monitor val){
	if(val != null)
	    monitor = val;
    }

    public String getMonitorsTitle(){
	return monitorsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && monitor != null){
	    id = monitor.getId();
	}
	return id;
    }
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    // most recent
    public List<Monitor> getMonitors(){ 
	if(monitors == null){
	    MonitorList dl = new MonitorList();
	    dl.setStatus("Active");
	    String back = dl.find();
	    monitors = dl.getMonitors();
	}		
	return monitors;
    }

}





































