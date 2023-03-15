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

public class SoftwareAction extends TopAction{

    static final long serialVersionUID = 1610L;	
    static Logger logger = LogManager.getLogger(SoftwareAction.class);
    //
    Software software = null;
    List<Software> softwares = null;
    String softwaresTitle = " Most recent Softwares";		
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
	    back = software.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = software.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = software.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){
	    /*
	      back = software.doDelete();
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
	    software = new Software(debug, id);
	    back = software.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }						
	}				
	else if(!id.equals("")){
	    ret = "view";
	    software = new Software(debug, id);
	    back = software.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public Software getSoftware(){ 
	if(software == null){
	    if(!id.equals("")){
		software = new Software(debug, id);
	    }
	    else{
		software = new Software();
	    }
	}		
	return software;
    }
		

    public void setSoftware(Software val){
	if(val != null)
	    software = val;
    }

    public String getSoftwaresTitle(){
	return softwaresTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && software != null){
	    id = software.getId();
	}
	return id;
    }
    // most recent
    public List<Software> getSoftwares(){ 
	if(softwares == null){
	    SoftwareList dl = new SoftwareList();
	    String back = dl.find();
	    softwares = dl.getSoftwares();
	}		
	return softwares;
    }

}





































