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

//
public class OrganizationAction extends TopAction{

    static final long serialVersionUID = 1025L;	
    static Logger logger = LogManager.getLogger(OrganizationAction.class);
    //
    Organization organization = null;
    String organizationsTitle = " Organizations";
    List<Organization> organizations = null;
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
	    back = organization.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = organization.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(action.startsWith("Save")){
	    back = organization.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = organization.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(!id.equals("")){
	    getOrganization();
	    back = organization.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }						
	}				
	else{
	    getOrganization();
	}
	return ret;
    }

    public List<Organization> getOrganizations(){
	if(organizations == null){
	    OrganizationList tl = new OrganizationList();
	    String back = tl.find();
	    if(back.equals("")){
		List<Organization> ones = tl.getOrganizations();
		if(ones != null && ones.size() > 0){
		    organizations = ones;
		}
	    }
	}
	return organizations;
    }
    public Organization getOrganization(){
	if(organization == null){
	    organization = new Organization();
	}
	if(!id.equals("")){
	    organization.setId(id);
	}
	return organization;
    }
    public void setOrganization(Organization val){
	if(val != null){
	    organization = val;
	}
    }

    public String getOrganizationsTitle(){
	return organizationsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		

}





































