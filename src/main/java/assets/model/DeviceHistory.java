package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;


public class DeviceHistory extends History{
	
    static Logger logger = LogManager.getLogger(DeviceHistory.class);
    static final long serialVersionUID = 1180L;	
    String device_id="";
    Device device = null;
    //
    public DeviceHistory(){

	super();
	//
	// initialize
	//
    }
    public DeviceHistory(Device val, User val2){

	super();
	setDevice(val);
	setChanger(val2);
	//
	// initialize
	//
    }	
    public DeviceHistory(boolean deb,
			 String val,
			 String val2,
			 String val3,
			 String val4,
			 String val5){

	super(deb);
	//
	// initialize
	//
	setId(val);
	setDevice_id(val2);
	setStatus(val3);
	setDate(val4);
	setUpdater_id(val5);
    }
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }		
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    //
    // getters
    //
    public String  getId(){
	return id;
    }		
    public String  getDevice_id(){
	return device_id;
    }
    public void setDevice(Device val){
	if(val != null){
	    device = val;
	    device_id = device.getId();
	    setStatus(device.getStatus());
	}
    }	
    public Device getDevice(){
	if(device == null && !device_id.equals("")){
	    Device one = new Device(debug, device_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		device = one;
	    }
	}
	return device;
    }	
    //
    public String doSave(){
		
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(device_id.equals("")) return "device id not set ";
	String qq = "insert into device_history values(0,?,?,?,?)";
	//
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
	    pstmt.setString(1,device_id);
	    if(status.equals(""))
		pstmt.setNull(2, Types.INTEGER);
	    else
		pstmt.setString(2, status);
	    if(date.equals(""))
		date = Helper.getToday();
	    pstmt.setDate(3, new java.sql.Date(dateFormat.parse(date).getTime()));
	    pstmt.setString(4,updater_id);
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

}






















































