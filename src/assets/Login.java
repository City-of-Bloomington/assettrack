package assets;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.sql.*;
import java.net.URL;
import org.apache.log4j.Logger;


public class Login extends HttpServlet{

		static final long serialVersionUID = 1470L;	
		String cookieName = ""; // "cas_session";
		String cookieValue = ""; // ".bloomington.in.gov";
    String dbStr="";
    String dbUser="",dbPass="",url="";
    boolean debug = false;
		static Logger logger = Logger.getLogger(Login.class);
	
    /**
     * Generates the login form for all users.
     *
     * @param req the request 
     * @param res the response
     */
    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException {
				String username = "", ipAddress = "", message="", id="";
				boolean found = false;
	
				res.setContentType("text/html");
				PrintWriter out = res.getWriter();
				if(url.equals("")){
						url  = getServletContext().getInitParameter("url");
						String str = getServletContext().getInitParameter("cookieName");
						if(str != null)
								cookieName = str;
						str = getServletContext().getInitParameter("cookieValue");
						if(str != null)
								cookieValue = str;
						str = getServletContext().getInitParameter("debug");
						if(str != null && str.equals("true")) debug = true;
				}
				HttpSession session = null;
				String userid = req.getRemoteUser(); 
				if(userid != null){
						session = req.getSession(true);			
						// setCookie(req, res);
						User user = getUser(userid);
						if(user != null && user.userExists() && session != null){
								session.setAttribute("user",user);
								out.println("<head><title></title><META HTTP-EQUIV=\""+
														"refresh\" CONTENT=\"0; URL=" + url +
														"welcome.action" + 
														"\"></head>");
								out.println("<body>");
								out.println("</body>");
								out.println("</html>");
								out.flush();
								return;
						}
						else{
								message = " Unauthorized access";
						}
				}
				else{
						message += " You can not access this system, check with IT or try again later";
				}
				out.println("<head><title></title><body>");
				out.println("<p><font color=red>");
				out.println(message);
				out.println("</p>");
				out.println("</body>");
				out.println("</html>");
				out.flush();	
    }
	
		void setCookie(HttpServletRequest req, 
									 HttpServletResponse res){ 
				Cookie cookie = null;
				boolean found = false;
				Cookie[] cookies = req.getCookies();
				if(cookies != null){
						for(int i=0;i<cookies.length;i++){
								String name = cookies[i].getName();
								if(name.equals(cookieName)){
										found = true;
								}
						}
				}
				//
				// if not found create one with 0 time to live;
				//
				// System.err.println(" cookie found ? "+found);
				if(!found){
						cookie = new Cookie(cookieName,cookieValue);
						res.addCookie(cookie);
				}
		}
    /**
     * Procesesses the login and check for authontication.
     * @param req
     * @param res
     */		
    User getUser(String username){

				boolean success = true;
				User user = null;
				String fullName="",role="",dept="", message="";
				user = new User(debug, null, username);
				String back = user.doSelect();
				if(!back.equals("")){
						message += back;
						logger.error(back);
						return null;
				}
				return user;
    }

}

