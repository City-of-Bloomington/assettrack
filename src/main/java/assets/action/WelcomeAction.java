package assets.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*; 
import javax.servlet.ServletContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.action.ServletContextAware;
import org.apache.struts2.action.SessionAware;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

public class WelcomeAction extends ActionSupport implements SessionAware, ServletContextAware{
    private static final long serialVersionUID = 1810L;
    static Logger logger = LogManager.getLogger(WelcomeAction.class);
    private ServletContext ctx;
    private Map<String, Object> sessionMap;
    private User user;
    String action = "", id="";
    //
    // if we have global list we can set them here and will
    // be available for all pages
    //
    @Override
    public String execute(){
	String ret = SUCCESS;
	doPrepare();
	if(user == null){
	    ret = LOGIN;
	}
	return ret;
    }
    void doPrepare(){
	String back = "";
	try{
	    user = (User)sessionMap.get("user");
	}catch(Exception ex){
	    System.out.println(ex);
	}		
    }	

    public void setAction(String val){
	action = val;
    }
    public String getAction(){
	return action;
    }
    public void setId(String val){
	id = val;
    }
    public String getId(){
	return id;
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


