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

public class EmployeeSearchAction extends TopAction{

    static final long serialVersionUID = 1370L;	
    static Logger logger = LogManager.getLogger(EmployeeSearchAction.class);
    //
    Employee employee = null;
    List<Employee> employees = null;
    String employeesTitle = "Current Employees";
    EmployeeList employeeList = null;
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
	    back = employeeList.find();
	    employeeList.setNoLimit();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		employees = employeeList.getEmployees();
		if(employees == null || employees.size() == 0){
		    addActionMessage("No match found");
		    employeesTitle = "No match found";										
		}
		else{
		    if(employees.size() == 1){
			Employee one = employees.get(0);
			try{
			    HttpServletResponse res = ServletActionContext.getResponse();
			    String str = url+"employee.action?id="+one.getId();
			    res.sendRedirect(str);
			    return super.execute();
			}catch(Exception ex){
			    System.err.println(ex);
			}
		    }
		    employeesTitle = "Matched "+employees.size()+" records ";				
		}
	    }
	}				
	else{		
	    getEmployeeList();
	}
	return ret;
    }
    public EmployeeList getEmployeeList(){ 
	if(employeeList == null){
	    employeeList = new EmployeeList(debug);
	}
	return employeeList;
    }

    public void setEmployeeList(EmployeeList val){
	if(val != null){
	    employeeList = val;
	}
    }

    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    // most recent
    public List<Employee> getEmployees(){ 
	if(action.equals("") && employees == null){
	    EmployeeList dl = new EmployeeList();
	    String back = dl.find();
	    employees = dl.getEmployees();
	}		
	return employees;
    }

}





































