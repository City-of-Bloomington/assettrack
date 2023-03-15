package assets.action;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
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

public class SoftwareSearchAction extends TopAction{

    static final long serialVersionUID = 1690L;	
    static Logger logger = LogManager.getLogger(SoftwareSearchAction.class);
    //
    List<Software> softwares = null;
    SoftwareList softwareList = null;
    List<Type> vendors = null;
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
	if(!action.equals("")){
	    getSoftwareList();
	    softwareList.setNoLimit();
	    back = softwareList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		softwares = softwareList.getSoftwares();
		if(softwares != null && softwares.size() > 0){
		    if(softwares.size() == 1){
			Software one = softwares.get(0);
			try{
			    HttpServletResponse res = ServletActionContext.getResponse();
			    String str = url+"software.action?id="+one.getId();
			    res.sendRedirect(str);
			    return super.execute();
			}catch(Exception ex){
			    System.err.println(ex);
			}						
		    }
		    softwaresTitle = "Found "+softwares.size()+" records";
		}
		else{
		    addActionMessage("No match found");
		    softwaresTitle = "No match found";
		}
	    }
	}				
	else{
	    getSoftwareList();
	    SoftwareList ml = new SoftwareList(debug);
	    back = ml.find();
	    softwares = ml.getSoftwares();
	}
	return ret;
    }
    public SoftwareList getSoftwareList(){
	if(softwareList == null){
	    softwareList = new SoftwareList(debug); 
	}
	return softwareList;
    }

    public String getSoftwaresTitle(){
	return softwaresTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    // most recent
    public List<Software> getSoftwares(){ 
	return softwares;
    }
    public List<Type> getVendors(){
	if(vendors == null){
	    getSoftwareList();
	    vendors = softwareList.getVendors();
	}
	return vendors;
    }		

}





































