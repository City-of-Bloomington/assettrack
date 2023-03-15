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

public class DeptAction extends TopAction{

    static final long serialVersionUID = 1130L;	
    static Logger logger = LogManager.getLogger(DeptAction.class);
    //
    Dept dept = null;
    List<Dept> depts = null;
    String deptsTitle = " All Departments";		
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
	    back = dept.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = dept.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = dept.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.equals("Delete")){ 
	    back = dept.doDelete();
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
	    dept = new Dept(id);
	    back = dept.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}		
	return ret;
    }
    public Dept getDept(){ 
	if(dept == null){
	    if(!id.equals("")){
		dept = new Dept(id);
	    }
	    else{
		dept = new Dept();
	    }
	}		
	return dept;
    }
		

    public void setDept(Dept val){
	if(val != null)
	    dept = val;
    }

    public String getDeptsTitle(){
	return deptsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && dept != null){
	    id = dept.getId();
	}
	return id;
    }
    // most recent
    public List<Dept> getDepts(){ 
	if(depts == null){
	    DeptList dl = new DeptList();
	    // dl.setStatus("Active");
	    String back = dl.find();
	    depts = dl.getDepts();
	}		
	return depts;
    }

}





































