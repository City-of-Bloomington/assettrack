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

public class EmployeeAction extends TopAction{

    static final long serialVersionUID = 1350L;	
    static Logger logger = LogManager.getLogger(EmployeeAction.class);
    //
    Employee employee = null;
    List<Employee> employees = null;
    String employeesTitle = "Current Employees";
    List<Device> devices = null;
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
	    back = employee.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = employee.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = employee.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
		id = employee.getId();
	    }
	}
	else if(action.equals("Delete")){ 
	    back = employee.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		employee = new Employee();
		addActionMessage("Deleted Successfully");
		id="";
	    }
	}
	else if(action.equals("Edit")){ 
	    employee = new Employee(id);
	    back = employee.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else if(action.startsWith("New")){ 
	    employee = new Employee();
	}				
	else if(!id.equals("")){ 
	    getEmployee();
	    back = employee.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else{		
	    getEmployee();
	}
	return ret;
    }
    public Employee getEmployee(){ 
	if(employee == null){
	    if(!id.equals("")){
		employee = new Employee(id);
	    }
	    else{
		employee = new Employee();
	    }
	}		
	return employee;
    }

    public void setEmployee(Employee val){
	if(val != null){
	    employee = val;
	    if(!id.equals("")){
		employee.setId(id);
	    }
	}
    }

    public String getEmployeesTitle(){
	return employeesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setYear(String val){
	if(val != null && !val.equals(""))		
	    id = val; // id also
    }		
    public String getId(){
	if(id.equals("") && employee != null){
	    id = employee.getId();
	}
	return id;
    }
    public String getYear(){
	if(id.equals("") && employee != null){
	    id = employee.getId();
	}
	return id;
    }		
    // most recent
    public List<Employee> getEmployees(){ 
	if(employees == null){
	    EmployeeList dl = new EmployeeList();
	    String back = dl.find();
	    employees = dl.getEmployees();
	}		
	return employees;
    }
    public boolean hasDevices(){
	getDevices();
	return devices != null && devices.size() > 0;
    }
    public List<Device> getDevices(){
	if(devices == null && !id.equals("")){
	    DeviceList dl = new DeviceList(debug);
	    dl.setEmployee_id(id);
	    String back = dl.find();
	    if(back.equals("")){
		List<Device> ones = dl.getDevices();
		if(ones != null && ones.size() > 0){
		    devices = ones;
		}
	    }
	    else{
		addActionError(back);
	    }
	}
	return devices;
    }		

}





































