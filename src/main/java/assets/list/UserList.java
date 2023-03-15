package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class UserList extends CommonInc{

    static Logger logger = LogManager.getLogger(UserList.class);
    static final long serialVersionUID = 1780L;
    String name = "", id="", username="", role="", limit="limit 30";
    List<User> users = null;
    public UserList(){
	super();
    }
    public UserList(boolean deb){
	super(deb);
    }		
    public UserList(boolean deb, String val){
	super(deb);
	setName(val);
    }
    public List<User> getUsers(){
	return users;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setUsername(String val){
	if(val != null)
	    username = val;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }		
    public void setRole(String val){
	if(val != null && !val.equals("-1")){
	    role = val;
	}
    }
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public String getUsername(){
	return username;
    }		
    public String getRole(){
	if(role.equals("")){
	    return "-1";
	}
	return role;
    }
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select u.id,u.username,u.first_name,u.last_name,u.office_phone,u.role from users u ", qw ="";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " u.id = ? ";
	}
	else{
	    if(!name.equals("")){
		qw += " (u.first_name like ? or u.last_name like ?) ";
	    }
	    if(!username.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " u.username like ? ";
	    }						
	    if(!role.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " u.role=? ";
	    }
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by u.last_name ";
	if(!limit.equals("")){
	    qq += limit;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    else {
		int jj=1;
		if(!name.equals("")){
		    pstmt.setString(jj++,"%"+name+"%");
		    pstmt.setString(jj++,"%"+name+"%");								
		}
		if(!username.equals("")){
		    pstmt.setString(jj++,username+"%");		
		}
		if(!role.equals("")){
		    pstmt.setString(jj++,role);		
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(users == null)
		    users = new ArrayList<User>();
		User one =
		    new User(debug,
			     rs.getString(1),
			     rs.getString(2),
			     rs.getString(3),
			     rs.getString(4),
			     rs.getString(5),
			     rs.getString(6));
		users.add(one);
	    }
	}
	catch(Exception ex){
	    back += ex+" : "+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }
}






















































