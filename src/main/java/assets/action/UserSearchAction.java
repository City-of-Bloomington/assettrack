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

public class UserSearchAction extends TopAction{

    static final long serialVersionUID = 1790L;	
    static Logger logger = LogManager.getLogger(UserSearchAction.class);
    //
    User user = null;
    List<User> users = null;
    String usersTitle = "Current Users";
    UserList userList = null;
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
	    back = userList.find();
	    userList.setNoLimit();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		users = userList.getUsers();
		if(users == null || users.size() == 0){
		    addActionMessage("No match found");
		    usersTitle = "No match found";										
		}
		else{
		    usersTitle = "Matched "+users.size()+" records ";				
		}
	    }
	}				
	else{		
	    getUserList();
	}
	return ret;
    }
    public UserList getUserList(){ 
	if(userList == null){
	    userList = new UserList(debug);
	}
	return userList;
    }

    public void setUserList(UserList val){
	if(val != null){
	    userList = val;
	}
    }

    public String getUsersTitle(){
	return usersTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    // most recent
    public List<User> getUsers(){ 
	if(action.equals("") && users == null){
	    UserList dl = new UserList();
	    String back = dl.find();
	    users = dl.getUsers();
	}		
	return users;
    }

}





































