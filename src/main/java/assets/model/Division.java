package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Division extends Type{

    static Logger logger = LogManager.getLogger(Division.class);
    static final long serialVersionUID = 1280L;
    String code="", dept_id="";
    Type dept = null;
    //
    public Division(){
	super();
    }
    public Division(String val){
	//
	// initialize
	//
	super(val);
    }
    //
    public Division(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Division(boolean deb, String val){
	//
	// initialize
	//
	super(deb, val);
    }		
    public Division(boolean deb, String val, String val2){
	//
	// initialize
	//
	super(deb, val, val2);
    }
    public Division(boolean deb, String val, String val2, String val3, String val4, boolean val5){
	//
	// initialize
	//
	super(deb, val, val3, val5);
	setDept_id(val2);
	setCode(val4);
    }
		
    public void setDept_id(String val){
	if(val != null)
	    dept_id = val;
    }
    public String getDept_id(){
	return dept_id;
    }
    public void setCode(String val){
	if(val != null){
	    code = val;
	}
    }
    public String getCode(){
	return code;
    }		
    public Type getDept(){
	if(dept == null && !dept_id.equals("")){
	    Type sp = new Type(debug, dept_id, null, false, "departments");
	    String back = sp.doSelect();
	    if(back.equals("")){
		dept = sp;
	    }
	}
	return dept;
    }
    // ToDo
    public String saveImport(PreparedStatement stmt){

	String msg = "";
	if(name.equals("")){
	    msg = " name not set";
	    return msg;
	}
	if(stmt == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    //
	    // we only know the name
	    //
	    stmt.setString(1, name);
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += ex;
	    logger.error(msg);
	    addError(msg);
	}
	return msg; // success
    }				
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into divisions values(0,?,?,?,null)";
	if(name.equals("")){
	    back = "department name not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    pstmt = con.prepareStatement(qq);
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt.setString(1, dept_id);
	    pstmt.setString(2, name);
	    if(code.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, code);
	    pstmt.executeUpdate();
	    //
	    // get the id of the new record
	    //
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);				
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;

    }
		
    public String doUpdate(){
		
	String back = "";
	if(name.equals("")){
	    back = "department name not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String str="";
	String qq = "";
		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "update divisions set dept_id=?,name=?,code=?,inactive=? "+
		"where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, dept_id);
	    pstmt.setString(2,name);
	    if(code.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, code);						
	    if(inactive.equals(""))
		pstmt.setNull(4, Types.VARCHAR);
	    else
		pstmt.setString(4, "y");
	    pstmt.setString(5,id);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;

    }
	
    public String doDelete(){
		
	String back = "", qq = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "delete from divisions where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    pstmt.executeUpdate();
	    message="Deleted Successfully";
	    id="";
	    name="";
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }
	
    //
    public String doSelect(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select dept_id,name,code,inactive "+
	    "from divisions where id=?";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }				
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setDept_id(rs.getString(1));
		setName(rs.getString(2));
		setCode(rs.getString(3));								
		setInactive(rs.getString(4) != null && !rs.getString(4).equals(""));								
	    }
	    else{
		back ="Record "+id+" Not found";
		message = back;
	    }
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);			
	}
	return back;
    }		
	

}
