package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class User extends CommonInc implements java.io.Serializable{

    String id="", username="", role="end_user";
    String first_name="", last_name="",office_phone="", fullName = "";
    static final long serialVersionUID = 1750L;
    static Hashtable<String, String> roles = null;
    static Logger logger = LogManager.getLogger(User.class);
    List<Device> devices = null;
    //
    public User(){
	super();
    }
    public User(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public User(String val){
	//
	setId(val);
    }
    public User(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public User(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setUsername(val2);
    }
    public User(boolean deb,
		String val,
		String val2,
		String val3,
		String val4,
		String val5,
		String val6
		){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setUsername(val2);
	setFirst_name(val3);
	setLast_name(val4);
	setOffice_phone(val5);
	setRole(val6);
    }
    private void setHashRoles(){
	if(roles == null){
	    roles = new Hashtable<String, String>(4);
	    roles.put("end_user","End User");
	    roles.put("View","View only");
	    roles.put("Edit","Edit");
	    roles.put("Edit:Admin","All (Admin)");
	}
				
    }
    public String getId(){
	return id;
    }
    public String getUsername(){
	return username;
    }		
    public String getRole(){
	return role;
    }
    public String getRoleInfo(){
	if(!role.equals("")){
	    if(roles == null){
		setHashRoles();
	    }
	    if(roles != null && roles.containsKey(role)){
		return roles.get(role);
	    }
	}
	return "";
    }
    public String getOffice_phone(){
	return office_phone;
    }		
    public String getFirst_name(){
	return first_name;
    }
    public String getLast_name(){
	return last_name;
    }
    public String getFullName(){
	if(fullName.equals("")){
	    fullName = last_name;
	    if(!first_name.equals("")){
		fullName += ", "+first_name;
	    }
	}
	return fullName;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setUsername(String val){
	if(val != null)
	    username = val;
    }		
    public void setRole(String val){
	if(val != null)
	    role = val;
    }
    public void setFirst_name(String val){
	if(val != null)
	    first_name = val;
    }
    public void setLast_name(String val){
	if(val != null)
	    last_name = val;
    }
    public void setOffice_phone(String val){
	if(val != null)
	    office_phone = val;
    }		
    //
    public boolean userExists(){
	return !last_name.equals("");
    }
    //
    public boolean hasRole(String val){
	return role != null && role.indexOf(val) > -1;
    }
    public boolean canEdit(){
	return hasRole("Edit");
    }
    public boolean canDelete(){
	return hasRole("Delete");
    }
    public boolean isAdmin(){
	return hasRole("Admin");
    }
    //
    public String toString(){
	return getFullName();
    }

    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,username,first_name,last_name,office_phone,role from users where  ";
	if(!id.equals("")){
	    qq += " id = ? ";
	}
	else if(!username.equals("")){
	    qq += " username = ? ";
	}
	else {
	    msg = " User id or username not set";
	    addError(msg);
	    return msg;
	}
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    if(!id.equals(""))
		stmt.setString(1, id);
	    else
		stmt.setString(1, username);								
	    rs = stmt.executeQuery();
	    if(rs.next()){
		setId(rs.getString(1));
		setUsername(rs.getString(2));
		setFirst_name(rs.getString(3));
		setLast_name(rs.getString(4));
		setOffice_phone(rs.getString(5));
		setRole(rs.getString(6));
	    }
	    else{
		msg = " No such user";
		message = msg;
	    }
	}catch(Exception ex){
	    logger.error(ex+" : "+qq);
	    msg += " "+ex;
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg;
    }
    //
    public String doSave(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	if(username.equals("") || last_name.equals("")){
	    msg = "username or last name not set";
	    return msg;
	}
	qq = "insert into users values(0,?,?,?,?,?)";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1, username);
	    if(first_name.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2,first_name);
	    stmt.setString(3,last_name);
	    if(office_phone.equals(""))
		stmt.setNull(4,Types.VARCHAR);
	    else
		stmt.setString(4, office_phone);
	    stmt.setString(5, role);
	    stmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    stmt = con.prepareStatement(qq);				
	    rs = stmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }						
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg; // success
    }
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update users set username=?,first_name=?,last_name=?,office_phone=?,role=? where id=?";
	//
	if(id.equals("") || username.equals("") || last_name.equals("")){
	    msg = "User id, username or last name not set";
	    return msg;
	}				
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1, username);
	    if(first_name.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2, first_name);
	    stmt.setString(3, last_name);
	    if(office_phone.equals(""))
		stmt.setNull(4,Types.VARCHAR);
	    else
		stmt.setString(4, office_phone);
	    if(role.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5, role);
	    stmt.setString(6, id);
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg; // success
    }		
    //
    public String doDelete(){

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  users where id=?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1,id);
	    stmt.executeUpdate();
	    message="Deleted Successfully";
	    //
	}
	catch(Exception ex){
	    msg = ex+" : "+qq;
	    logger.error(msg);
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}			
	return msg; 
    }
    public String saveImport(PreparedStatement stmt){

	String msg = "";
	if(id.equals("") || username.equals("") || last_name.equals("")){
	    msg = "User id, username or last name not set";
	    return msg;
	}
	if(stmt == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    // insert part
	    stmt.setString(1, id);						
	    stmt.setString(2, username);
	    if(first_name.equals(""))
		stmt.setNull(3,Types.VARCHAR);
	    else
		stmt.setString(3,first_name);
	    stmt.setString(4,last_name);
	    if(office_phone.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else						
		stmt.setString(5, office_phone);
	    if(role.equals(""))
		stmt.setNull(6,Types.INTEGER);
	    else
		stmt.setString(6, role);
	    // update part
	    /*
	      stmt.setString(7, username);
	      if(first_name.equals(""))
	      stmt.setNull(8,Types.VARCHAR);
	      else
	      stmt.setString(8,first_name);
	      stmt.setString(9,last_name);
	      if(office_phone.equals(""))
	      stmt.setNull(10,Types.VARCHAR);
	      else						
	      stmt.setString(10, office_phone);
	      stmt.setString(11, role);
	    */
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += ex;
	    logger.error(msg);
	    addError(msg);
	}
	return msg; // success
    }		

}
