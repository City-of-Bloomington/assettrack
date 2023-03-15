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

public class SoftwareLicenseAction extends TopAction{

    static final long serialVersionUID = 1660L;	
    static Logger logger = LogManager.getLogger(SoftwareLicenseAction.class);
    //
    String software_id = "";
    SoftwareLicense license = null;
    List<SoftwareLicense> licenses = null;
    String licensesTitle = " Most recent Software Licenses";		
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
	    back = license.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = license.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = license.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){
	    /*
	      back = license.doDelete();
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
	    license = new SoftwareLicense(debug, id);
	    back = license.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}						
	else if(!id.equals("")){ 
	    license = new SoftwareLicense(debug, id);
	    back = license.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    ret = "view";
	}
	else{
	    getLicense();
	}
	return ret;
    }
    public SoftwareLicense getLicense(){ 
	if(license == null){
	    if(!id.equals("")){
		license = new SoftwareLicense(debug, id);
	    }
	    else{
		license = new SoftwareLicense();
	    }
	}		
	return license;
    }

    public void setLicense(SoftwareLicense val){
	if(val != null)
	    license = val;
    }

    public String getLicensesTitle(){
	return licensesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && license != null){
	    id = license.getId();
	}
	return id;
    }
    public String getSoftware_id(){
	return software_id;
    }
    public void setSoftware_id(String val){
	if(val != null && !val.equals(""))		
	    software_id = val;
    }				
    // most recent
    public List<SoftwareLicense> getLicenses(){ 
	if(licenses == null && id.equals("")){
	    SoftwareLicenseList dl = new SoftwareLicenseList();
	    String back = dl.find();
	    licenses = dl.getLicenses();
	}		
	return licenses;
    }

}





































