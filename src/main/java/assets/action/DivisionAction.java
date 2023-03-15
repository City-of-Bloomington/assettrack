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

public class DivisionAction extends TopAction{

    static final long serialVersionUID = 1290L;	
    static Logger logger = LogManager.getLogger(DivisionAction.class);
    //
    Division division = null;
    List<Division> divisions = null;
    List<Dept> depts = null;
    String divisionsTitle = " All Divisions";		
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
	    back = division.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = division.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = division.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){ 
	    back = division.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
		ret = "search";
	    }
	}
	else if(action.equals("Refresh")){
	    // nothing
	}				
	else if(!id.equals("")){ 
	    division = new Division(id);
	    back = division.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public Division getDivision(){ 
	if(division == null){
	    if(!id.equals("")){
		division = new Division(id);
	    }
	    else{
		division = new Division();
	    }
	}		
	return division;
    }
		

    public void setDivision(Division val){
	if(val != null)
	    division = val;
    }

    public String getDivisionsTitle(){
	return divisionsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && division != null){
	    id = division.getId();
	}
	return id;
    }
    // most recent
    public List<Division> getDivisions(){ 
	if(divisions == null){
	    DivisionList dl = new DivisionList();
	    // dl.setStatus("Active");
	    String back = dl.find();
	    divisions = dl.getDivisions();
	}		
	return divisions;
    }
    // 
    public List<Dept> getDepts(){ 
	if(depts == null){
	    DeptList dl = new DeptList(debug);
	    String back = dl.find();
	    depts = dl.getDepts();
	}		
	return depts;
    }
}





































