package assets.web;

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

@WebServlet(urlPatterns = {"/TicketStatsService"})
public class TicketStatsService extends TopServlet{

    static Logger logger = LogManager.getLogger(TicketStatsService.class);
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
	String term ="", start_date="", end_date="";
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
	    else if (name.equals("start_date")){ 
		start_date = value;  
	    }
	    else if (name.equals("end_date")){ 
		end_date = value;  
	    }						
	    else if (name.equals("action")){ 
		action = value;  
	    }
	    else{
		// System.err.println(name+" "+value);
	    }
	}
	TicketStats stats = new TicketStats(debug, start_date, end_date);
	String back = stats.findStats();
	if(back.equals("")){
	    String json = prepJson(stats);
	    out.println(json);
	    // System.err.println(json);
	}
	out.flush();
	out.close();
    }
    //
    String prepJson(TicketStats stats){
	String json="";
	json = "{"+
	    // "\"start_date\":\""+stats.getStart_date_escaped()+"\","+
	    // "\"end_date\":\""+stats.getEnd_date_escaped()+"\","+
	    // "\"total_received\":\""+stats.getTotalReceived()+"\","+
	    "\"first_response_time\":\""+stats.getAvgResponse()+"\","+
	    "\"first_response_time_goal\":\"one hour or less\","+						
	    // "\"total_closed\":"+stats.getTotalClosed()+","+
	    "\"open_tickets\":"+stats.getTotalOpen()+","+
	    "\"open_tickets_goal\":\"less than 100\""+
	    "}";

	return json;

    }

}






















































