/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package assets.action;
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;  
import org.apache.struts2.dispatcher.SessionMap;  
import org.apache.struts2.action.SessionAware;
// import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.action.ServletContextAware;  
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

public abstract class TopAction extends ActionSupport implements SessionAware, ServletContextAware{

    static final long serialVersionUID = 1710L;	
    boolean debug = false;
    String action="", url="", id="";
    String sqliteDbFile = "";
    static Logger logger = LogManager.getLogger(TopAction.class);
    User user = null;
    ServletContext ctx;
    Map<String, Object> sessionMap;

    public void setAction(String val){
	if(val != null)
	    action = val;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))
	    action = val;
    }		
    public String getAction(){
	return action;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public String getId(){
	return id;
    }
    public User getUser(){
	return user;
    }		
    String doPrepare(){
	String back = "";
	try{
	    user = (User)sessionMap.get("user");
	    if(user == null){
		back = LOGIN;
	    }
	    if(url.equals("")){
		String val = ctx.getInitParameter("url");
		if(val != null)
		    url = val;
		val = ctx.getInitParameter("sqliteDbFile");
		if(val != null)
		    sqliteDbFile = val;								
	    }
	}catch(Exception ex){
	    System.out.println(ex);
	}		
	return back;
    }		
    @Override  
    public void withSession(Map<String, Object> map) {  
	sessionMap=map;  
    }
    @Override  	
    public void withServletContext(ServletContext ctx) {  
        this.ctx = ctx;  
    }  	
}





































