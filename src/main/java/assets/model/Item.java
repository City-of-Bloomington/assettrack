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
import assets.utils.*;


public class Item extends CommonInc{
	
    static Logger logger = LogManager.getLogger(Item.class);
    static final long serialVersionUID = 1450L;	
    String asset_id="", date="", type="", id="", asset_num="";
    String user_id = "";
    User user = null;
    Object asset = null;
    Device device = null;
    Monitor monitor = null;
    Printer printer = null;
    // we need these two devices when a printer or monitor is linked to
    // a device to extract other info
    Device monitorDevice = null;
    Device printerDevice = null;		
    boolean prepared = false;

    public Item(){
	super();
    }
    public Item(boolean deb){
	super(deb);
    }
    public Item(boolean deb, String val){

	super(deb);
	//
	// initialize
	//
	setId(val);
    }			
    public Item(boolean deb, String val, String val2, String val3){

	super(deb);
	//
	// initialize
	//
	setId(val);
	setAsset_id(val2);
	setType(val3);
    }	
    public Item(boolean deb,
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
	setAsset_id(val2);
	setAsset_num(val3);
	setType(val4);				
	setDate(val5);
    }
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }		
    public void setAsset_id(String val){
	if(val != null)
	    asset_id = val;
    }
    public void setAsset_num(String val){
	if(val != null)
	    asset_num = val;
    }		
    public void setUser_id(String val){
	if(val != null)
	    user_id = val;
    }		
		
    public void setDate(String val){
	if(val != null)
	    date = val.trim();
    }	
    public void setType(String val) {
	if(val != null && !val.equals("")){
	    type = val;
	}
    }		
    //
    // getters
    //
    public String getId(){
	return id;
    }				
    public String getAsset_id(){
	return asset_id;
    }
    public String getAsset_num(){
	return asset_num;
    }		
    public String getDate(){
	return date;
    }	
    public String getType() {
	return type;
    }
    public String getTypeLower() {
	return type.toLowerCase();
    }		
    public Object getAsset(){
	if(asset == null && !prepared){
	    prepareAsset();
	    prepared = true;
	}
	return asset;
    }
    //
    // the following gets are derived from the asset type
    //
    public String getName(){
	getAsset();
	if(device != null){
	    return device.getName();
	}
	if(printer != null){
	    return printer.getName();
	}
	if(monitor != null){
	    return monitor.getName();
	}				
	return "";
    }
    public String getNotes(){
	getAsset();
	if(device != null){
	    return device.getDescription();
	}
	if(printer != null){
	    return printer.getDescription();
	}
	if(monitor != null){
	    return monitor.getNotes();
	}				
	return "";

    }
    public String getCategoryName(){
	getAsset();
	if(device != null){
	    Type cat = device.getCategory();
	    if(cat != null)
		return cat.getName();
	    return "";
	}
	if(printer != null){
	    return "Printer";
	}
	if(monitor != null){
	    return "Monitor";
	}
	return "";
    }
    public String getDivisionName(){
	getAsset();
	if(device != null){
	    Type div =  device.getDivision();
	    if(div != null)
		return div.getName();
	    return "";
	}
	if(printer != null){
	    if(printerDevice != null){
		Type div =  printerDevice.getDivision();
		if(div != null)
		    return div.getName();
		return "";
	    }
	}
	if(monitor != null){
	    if(monitorDevice != null){
		Type div =  monitorDevice.getDivision();
		if(div != null)
		    return div.getName();
		return "";
	    }
	}
	return "";
    }
    public String getSerial_num(){
	getAsset();
	if(device != null){
	    return device.getSerial_num();
	}
	if(printer != null){
	    return printer.getSerial_num();
	}
	if(monitor != null){
	    return monitor.getSerial_num();
	}				
	return "";
    }
    public String getInstalled(){
	getAsset();
	if(device != null){
	    return device.getInstalled();
	}
	if(printer != null){
	    return printer.getDate();
	}
	if(monitor != null){
	    return monitor.getReceived();
	}
	return "";
    }
    //
    // not provided an part of invoices
    //
    public String getCost(){
	getAsset();
	if(device != null){
	    return device.getCost();
	}
	if(printer != null){
						
	}
	return "";
    }		
    void prepareAsset(){

	String back = "";				
	if(asset == null && !asset_id.equals("")){
	    if(type.equals("") && !asset_num.equals("")){
		findType();
	    }
	}
	if(!type.equals("")){
	    if(type.equals("Device")){
		Device one = new Device(debug, asset_id);
		back = one.doSelect();
		if(back.equals("")){
		    device = one;
		    asset_num = device.getAsset_num();
		    asset = (Object)one;
		}
	    }
	    else if(type.equals("Monitor")){
		Monitor one = new Monitor(debug, asset_id);
		back = one.doSelect();
		if(back.equals("")){
		    monitor = one;
		    asset_num = monitor.getAsset_num();
		    asset = (Object)one;
		    if(!monitor.getExternal_id().equals("")){
			Device one2 = new Device(debug, monitor.getExternal_id());
			back = one2.doSelect();
			if(back.equals("")){
			    monitorDevice = one2;
			}
		    }												
		}
	    }
	    else if(type.equals("Printer")){
		Printer one = new Printer(debug, asset_id);
		back = one.doSelect();
		if(back.equals("")){
		    printer = one;
		    asset_num = printer.getAsset_num();
		    asset = (Object)one;
		    if(!printer.getExternal_id().equals("")){
			Device one2 = new Device(debug, printer.getExternal_id());
			back = one2.doSelect();
			if(back.equals("")){
			    printerDevice = one2;
			}
		    }
		}
	    }
	}
    }
		
    /**
     * if type not set and we have asset_num
     * we can use that to find asset_id and type
     */
    void findType(){

	String back = "";
	String qq = " (select id, 'Device' from devices where asset_num=?) "+
	    "union (select id, 'Monitor' from monitors where asset_num=?) "+
	    "union (select id, 'Printer' from printers where asset_num=?) ";		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return ;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }				
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,asset_num);
	    pstmt.setString(2,asset_num);
	    pstmt.setString(3,asset_num);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		asset_id = rs.getString(1);
		type = rs.getString(2);
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

    }
    public User getUser(){
	if(user == null && !user_id.equals("")){
	    User one = new User(debug, user_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		user = one;
	    }
	}
	return user;
    }		
}






















































