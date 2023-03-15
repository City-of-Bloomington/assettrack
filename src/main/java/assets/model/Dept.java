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

public class Dept extends Type{

    static Logger logger = LogManager.getLogger(Dept.class);
    static final long serialVersionUID = 1120L;
    List<Division> divisions = null;
    //
    public Dept(){
	super();
    }
    public Dept(String val){
	//
	// initialize
	//
	super(val);
    }
    //
    public Dept(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Dept(boolean deb, String val){
	//
	// initialize
	//
	super(deb, val);
    }		
    public Dept(boolean deb, String val, String val2){
	//
	// initialize
	//
	super(deb, val, val2);
    }
    public Dept(boolean deb, String val, String val2, boolean val3){
	//
	// initialize
	//
	super(deb, val, val2, val3);
    }
    public List<Division> getDivisions(){
	if(divisions == null && !id.equals("")){
	    DivisionList dl = new DivisionList(debug, id);
	    String back = dl.find();
	    if(back.equals("")){
		List<Division> ones = dl.getDivisions();
		if(ones != null && ones.size() > 0){
		    divisions = ones;
		}
	    }
	}
	return divisions;
    }
		
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
	String qq = "insert into departments values(0,?,null)";
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
	    pstmt.setString(1,name);
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
	    qq = "update departments set name=?,inactive=? "+
		"where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,name);
	    if(inactive.equals(""))
		pstmt.setNull(2, Types.VARCHAR);
	    else
		pstmt.setString(2, "y");
	    pstmt.setString(3,id);
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
	    qq = "delete from departments where id=?";
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
	String qq = "select name,inactive "+
	    "from departments where id=?";
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
		setName(rs.getString(1));
		setInactive(rs.getString(2) != null && !rs.getString(2).equals(""));								
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
