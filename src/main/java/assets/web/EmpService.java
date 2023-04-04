package assets.web;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;
import assets.model.*;
import assets.list.*;
import assets.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/EmpService"})
public class EmpService extends TopServlet{

    static final long serialVersionUID = 1200L;
    static Logger logger = LogManager.getLogger(EmpService.class);
		
    public void doGet(HttpServletRequest req,
		      HttpServletResponse res)
	throws ServletException, IOException {
	doPost(req,res);
    }

    /**
     *
     * @param req The request input stream
     * @param res The response output stream
     */
    public void doPost(HttpServletRequest req,
		       HttpServletResponse res)
	throws ServletException, IOException {

	String id = "";

	//
	String message="", action="";
	res.setContentType("application/json");
	PrintWriter out = res.getWriter();
	String name, value;
	String term ="", type="";
	boolean success = true;
	HttpSession session = null;
	/**
	if(envBean == null){
	    envBean = new EnvBean();
	    String str = getServletContext().getInitParameter("ldap_url");
	    envBean.setUrl(str);
	    str = getServletContext().getInitParameter("ldap_principle");
	    envBean.setPrinciple(str);
	    str = getServletContext().getInitParameter("ldap_password");
	    envBean.setPassword(str);
	}
	*/
	Enumeration<String> values = req.getParameterNames();
	String [] vals = null;
	while (values.hasMoreElements()){
	    name = values.nextElement().trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();
	    if (name.equals("term")) { // this is what jquery sends
		term = value;
	    }
	    else if (name.equals("type")) {
		type = value;
	    }
	    else if (name.equals("action")){
		action = value;
	    }
	    else{
		// System.err.println(name+" "+value);
	    }
	}
	
	EmpList empList =  null;
	List<Employee> emps = null;
	if(envBean == null){
	    logger.error("EnvBean not set ");
	}
	if(envBean != null && term.length() > 1){
	    //
	    empList = new EmpList(envBean, term);
	    // empList.setName(term);
	    String back = empList.find();
	    if(back.isEmpty()){
		emps = empList.getEmps();
	    }
	}
	if(emps != null && emps.size() > 0){
	    String json = writeJson(emps);
	    out.println(json);
	}
	out.flush();
	out.close();
    }

    /**
     * Creates a JSON array string for a list of employees
     *
     * @param emps The employees list
     * @return The json string
     */
    String writeJson(List<Employee> emps){
	String json="";
	for(Employee one:emps){
	    // System.err.println(one.getFull_name());
	    if(!json.isEmpty()) json += ",";
	    json += "{\"id\":\""+one.getUsername()+"\",\"value\":\""+one.getFullName()+"\",\"first_name\":\""+one.getFirst_name()+"\",\"username\":\""+one.getUsername()+"\",\"last_name\":\""+one.getLast_name()+"\",\"office_phone\":\""+one.getOffice_phone()+"\"}";
	}
	json = "["+json+"]";
	return json;
    }


}
