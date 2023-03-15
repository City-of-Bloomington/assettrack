package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.io.*;
import java.sql.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Employee extends CommonInc implements java.io.Serializable{

    //
    // external_id most likely not needed
    // since no employee will be added through this interface
    //
    String id="", username="", role="end_user", external_id="";
    String first_name="", last_name="",office_phone="", fullName = "";
    static final long serialVersionUID = 1340L;
		
    static Logger logger = LogManager.getLogger(Employee.class);
    List<Device> devices = null;
    //
    public Employee(){
	super();
    }
    public Employee(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Employee(String val){
	//
	setId(val);
    }
    public Employee(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Employee(boolean deb,
		    String val,
		    String val2,
		    String val3,
		    String val4,
		    String val5,
		    String val6,
		    String val7
		    ){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setExternal_id(val2);
	setUsername(val3);
	setFirst_name(val4);
	setLast_name(val5);
	setOffice_phone(val6);
	setRole(val7);
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
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
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
    public boolean employeeExists(){
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
    public int hashCode(){
	int ret = 37;
	if(!id.equals("")){
	    try{
		ret += Integer.parseInt(id);
	    }catch(Exception ex){}
	}
	return ret;		
    }
    public boolean equals(Object ob){
	if(ob instanceof Employee){ 
	    Employee one = (Employee)ob;
	    return one.getId().equals(this.id);
	}
	return false;
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select external_id,username,first_name,last_name,office_phone,role from employees where  id=? ";
	if(id.equals("")){
	    msg = " Employee id not set";
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
	    stmt.setString(1, id);
	    rs = stmt.executeQuery();
	    if(rs.next()){
		setExternal_id(rs.getString(1));
		setUsername(rs.getString(2));
		setFirst_name(rs.getString(3));
		setLast_name(rs.getString(4));
		setOffice_phone(rs.getString(5));
		setRole(rs.getString(6));
	    }
	    else{
		msg = " No such employee";
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
	if(id.equals("") || username.equals("") || last_name.equals("")){
	    msg = "Employee id, username or last name not set";
	    return msg;
	}
	qq = "insert into employees values(0,?,?,?,?,?,?)";
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
	    if(external_id.equals(""))
		stmt.setNull(1,Types.INTEGER);
	    else
		stmt.setString(1, external_id);						
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
	    stmt.setString(6, role);
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
	qq = "update employees set external_id=?,username=?,first_name=?,last_name=?,office_phone=?,role=? where id=?";
	//
	if(id.equals("") || username.equals("") || last_name.equals("")){
	    msg = "Employee id, username or last name not set";
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
	    if(external_id.equals(""))
		stmt.setNull(1,Types.INTEGER);
	    else
		stmt.setString(1, external_id);						
	    stmt.setString(2, username);
	    if(first_name.equals(""))
		stmt.setNull(3,Types.VARCHAR);
	    else
		stmt.setString(3, first_name);
	    stmt.setString(4, last_name);
	    if(office_phone.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5, office_phone);
	    if(role.equals(""))
		stmt.setNull(6,Types.VARCHAR);
	    else
		stmt.setString(6, role);
	    stmt.setString(7, id);
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
	qq = "delete from  employees where id=?";
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
	if(!username.equals("") && last_name.equals("")){
	    last_name = username;
	}
	if(id.equals("") || last_name.equals("")){
	    msg = id;
	    msg += " Employee id or last name not set";
						
	    return msg;
	}
	if(stmt == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    // insert part
	    int jj=1;
	    stmt.setString(jj++, id);												
	    stmt.setString(jj++, external_id);
	    if(username.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, username);
	    if(first_name.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++,first_name);
	    stmt.setString(jj++,last_name);
	    if(office_phone.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else						
		stmt.setString(jj++, office_phone);
	    if(role.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, role);
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
