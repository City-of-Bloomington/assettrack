package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import javax.sql.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class DiscardItem extends Item{
    static Logger logger = LogManager.getLogger(DiscardItem.class);
    static final long serialVersionUID = 1230L;			
    final static String METHODS[] = {"Consumed","Discard","Lost"};
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String method = ""; // Consumed, Lost, Discard
    String description = "";
    //
    public DiscardItem() {
	super();
    }
		
    public DiscardItem(boolean deb) {
	super(deb);
    }
    public DiscardItem(boolean deb,
		       String val){
	super(deb, val);
    }			
    public DiscardItem(boolean deb,
		       String val,
		       String val2,
		       String val3,
		       String val4,
		       String val5,
		       String val6,
		       String val7) {
	super(deb, val, val2, val3, val4, val5);
	setMethod(val6);
	setDescription(val7);
    }	
    public String getMethod() {
	return method;
    }
		
    public String getDescription() {
	return description;
    }
    public void setMethod(String val) {
	if(val != null)
	    this.method = val;
    }	
    public void setDescription(String val) {
	if(val != null)
	    this.description = val;
    }
    public String toString(){
	String ret = "";
	ret += asset_id+" "+method+" "+date;
	return ret;
    }

    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into discarded_items values(0,?,?,?,?,?,?)";
	if(asset_id.equals("")){
	    back = "asset id not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
	prepareAsset();
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
	    pstmt.setString(1,asset_id);
	    if(asset_num.equals(""))
		pstmt.setNull(2,Types.VARCHAR);
	    else
		pstmt.setString(2,asset_num);						
	    pstmt.setString(3,type);
	    if(date.equals(""))
		date = Helper.getToday();
	    pstmt.setDate(4, new java.sql.Date(dateFormat.parse(date).getTime()));
	    if(method.equals(""))
		pstmt.setNull(5,Types.VARCHAR);
	    else
		pstmt.setString(5,method);
	    if(description.equals(""))
		pstmt.setNull(6,Types.VARCHAR);
	    else
		pstmt.setString(6,description);				
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);				
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }
	    if(device != null){
		back = device.updateStatus("Disposed");								
		DeviceHistory ih = new DeviceHistory(debug, null, asset_id, "Disposed",null,user_id);
		back = ih.doSave();
		if(!back.equals("")){
		    addError(back);
		}								
	    }
	    else if(monitor != null){
		back = monitor.updateStatus("Disposed");					
		if(!back.equals("")){
		    addError(back);
		}		
	    }
	    else if (printer != null){
		back = printer.updateStatus("Disposed");					
		if(!back.equals("")){
		    addError(back);
		}		
	    }								
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;

    }
    public	String doUpdate(){
		
	String back = "";
	if(id.equals("")){
	    back = "id not set ";
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
	    qq = "update discarded_items set item_id=?,asset_num=?,type=?,date=?,"+
		"method=?,description=? "+
		"where id=?";
			
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,asset_id);
	    if(asset_num.equals("")){
		pstmt.setNull(2,Types.VARCHAR);
	    }
	    else{
		pstmt.setString(2, asset_num);
	    }						
	    pstmt.setString(3,type);						
	    pstmt.setDate(4, new java.sql.Date(dateFormat.parse(date).getTime()));				
	    if(method.equals("")){
		pstmt.setNull(5,Types.INTEGER);
	    }
	    else{
		pstmt.setString(5,method);
	    }
	    if(description.equals("")){
		pstmt.setNull(6,Types.VARCHAR);
	    }
	    else{
		pstmt.setString(6,description);
	    }
	    pstmt.setString(7, id);
	    pstmt.executeUpdate();
	    message="Updated Successfully";
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }
	
    public	String doDelete(){
		
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
	    qq = "delete from discarded_items where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    pstmt.executeUpdate();
	    message="Deleted Successfully";
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
    public	String doSelect(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select item_id,asset_num,type,"+
	    "date_format(date,'%m/%d/%Y'),method,description "+
	    "from discarded_items where id=?";
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
		setAsset_id(rs.getString(1));
		setAsset_num(rs.getString(2));								
		setType(rs.getString(3));
		setDate(rs.getString(4));
		setMethod(rs.getString(5));
		setDescription(rs.getString(6));
	    }
	    else{
		back = "Record "+id+" Not found";
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
