package assets;
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
import java.util.ArrayList;
import java.util.List;
 import org.apache.log4j.Logger;

public class SoftwareService extends HttpServlet{

    String url="";
		static final long serialVersionUID = 1700L;			
    boolean debug = false;
		static Logger logger = Logger.getLogger(SoftwareService.class);
     
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
				String term ="", type="";
				boolean success = true;
				HttpSession session = null;
				if(url.equals("")){
						url    = getServletContext().getInitParameter("url");
						//
						String str = getServletContext().getInitParameter("debug");
						if(str.equals("true")) debug = true;
				}
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
						else{
								// System.err.println(name+" "+value);
						}
				}
				List<Software> softwares = null;
				SoftwareList ol = null;
				if(term.length() > 1){
						//
						ol = new SoftwareList(debug);
						ol.setName(term);
						String back = ol.find();
						if(back.equals("")){
								softwares = ol.getSoftwares();
						}
				}
				if(softwares != null && softwares.size() > 0){
						String json = writeJson(softwares);
						out.println(json);
				}
				out.flush();
				out.close();
    }
		/**
		 * *************************
		 *
		 * json format as an array
		 [
		 {"value":"Walid Sibo",
		 "id":"sibow",
		 "dept":"ITS"
		 },
		 {"value":"schertza",
		 "id":"Alan Schertz",
		 "dept":"ITS"
		 }
	   ]
	   ***************************
		 */
		String writeJson(List<Software> ones){
				String json="";
				for(Software one:ones){
						if(!json.equals("")) json += ",";
						json += "{\"id\":\""+one.getId()+"\",\"value\":\""+one.getDisplay_name()+"\"}";
				}
				json = "["+json+"]";
				// System.err.println("json "+json);
				return json;
		}

}






















































