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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

@WebServlet(urlPatterns = {"/TypeService"})
public class TypeService extends TopServlet{

    static final long serialVersionUID = 1740L;	
    static Logger logger = LogManager.getLogger(TypeService.class);
    /**
     * Generates the Group form and processes view, add, update and delete
     * operations.
     * @param req
     * @param res
     */
    
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	doPost(req,res);
    }
    /**
     * @link #doGetost
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
	String term ="", type="organizations";
	boolean success = true;
	HttpSession session = null;
	Enumeration values = req.getParameterNames();
	String [] vals = null;
	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("term")) { // this is what jquery sends
		term = value;
	    }
	    else if (name.equals("action")){ 
		action = value;  
	    }
	    else if (name.equals("type")){ 
		type = value;  
	    }						
	    else{
		System.err.println(name+" "+value);
	    }
	}
	List<Type> types = null;
	TypeList ol = null;
	if(term.length() > 1){
	    //
	    ol = new TypeList(debug, term, type);// type = table name
	    String back = ol.find();
	    if(back.equals("")){
		types = ol.getTypes();
	    }
	}
	if(types != null && types.size() > 0){
	    String json = writeJson(types);
	    out.println(json);
	}
	out.flush();
	out.close();
    }

    String writeJson(List<Type> ones){
	String json="";
	for(Type one:ones){
	    if(!json.equals("")) json += ",";
	    json += "{\"id\":\""+one.getId()+"\",\"value\":\""+one.getName()+"\"}";
	}
	json = "["+json+"]";
	// System.err.println("json "+json);
	return json;
    }

}






















































