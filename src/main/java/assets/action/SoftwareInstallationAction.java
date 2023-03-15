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

public class SoftwareInstallationAction extends TopAction{

    static final long serialVersionUID = 1630L;	
    static Logger logger = LogManager.getLogger(SoftwareInstallationAction.class);
    //
    String device_id="";
    SoftwareInstallation installation = null;
    List<SoftwareInstallation> installations = null;
    String installationsTitle = " Most recent Softwares Installations";		
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
	    back = installation.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = installation.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = installation.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){
	    /*
	      back = installation.doDelete();
	      if(!back.equals("")){
	      // back to the same page 
	      addActionError(back);
	      }
	      else{
	      addActionMessage("Deleted Successfully");								
	      ret = "search";
	      }
	    */
	}
	else if(action.equals("Refresh")){
	    // nothing
	}
	else if(action.equals("Edit")){
	    installation = new SoftwareInstallation(debug, id);
	    back = installation.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else if(!id.equals("")){
	    ret = "view";
	    installation = new SoftwareInstallation(debug, id);
	    back = installation.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else { // for new stuff
	    getInstallation();
	}
	return ret;
    }
    public SoftwareInstallation getInstallation(){ 
	if(installation == null){
	    if(!id.equals("")){
		installation = new SoftwareInstallation(debug, id);
	    }
	    else{
		installation = new SoftwareInstallation();
		if(!device_id.equals("")){
		    installation.setDevice_id(device_id);
		}
	    }
	}		
	return installation;
    }

    public void setInstallation(SoftwareInstallation val){
	if(val != null)
	    installation = val;
    }

    public String getInstallationsTitle(){
	return installationsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setDevice_id(String val){
	if(val != null && !val.equals(""))		
	    device_id = val;
    }		
    public String getId(){
	if(id.equals("") && installation != null){
	    id = installation.getId();
	}
	return id;
    }
    public String getDevice_id(){
	if(id.equals("") && installation != null){
	    device_id = installation.getDevice_id();
	}
	return device_id;
    }		
    // most recent
    public List<SoftwareInstallation> getInstallations(){
	/*
	  if(installations == null){
	  SoftwareInstallationList dl = new SoftwareInstallationList();
	  String back = dl.find();
	  installations = dl.getInstallations();
	  }
	*/
	return installations;
    }

}





































