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

public class Donation extends Item{
	
    static Logger logger = LogManager.getLogger(Donation.class);
    static final long serialVersionUID = 1310L;	
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String organization_id ="", lot_id="";
    float value = 0.0f;
    Type organ = null;
    Lot lot = null;
    List<Donation> donations = null;
    public Donation(){
	super();
    }
    public Donation(boolean deb, String val){
	super(deb, val);
    }		
    public Donation(boolean deb, String val, String val2, String val3){

	super(deb, val, val2, val3);
    }
		
    public Donation(boolean deb,
		    String val,
		    String val2,
		    String val3,
		    String val4,
		    String val5,
		    String val6,
		    String val7,
		    String val8){

	super(deb, val, val2, val3, val4, val5);
	//
	// initialize
	//
	setOrganization_id(val6);				
	setValue(val7);
	setLot_id(val8);
    }
    //
    // setters
    //
		
    public void setOrganization_id(String val){
	if(val != null && !val.equals("-1"))		
	    organization_id = val;
    }
    public void setValue(String val) {
	if(val != null && !val.equals("")){
	    try{
		value = Float.parseFloat(val);
	    }catch(Exception ex){}
	}
    }
    public void setLot_id(String val) {
	if(val != null && !val.equals("-1")){
	    lot_id = val;
	}
    }
    public void setValue(float val) {
	value = val;
    }
    //
    // getters
    //
    public String  getOrganization_id(){
	return organization_id;
    }

    public float getValue() {
	return value;
    }
    public String getLot_id() {
	if(lot_id.equals("")) return "-1";
	return lot_id;
    }
    public Type getOrgan(){
	if(organ == null && !organization_id.equals("")){
	    Type one = new Type(debug, organization_id, null, false,"organizations");
	    String back = one.doSelect();
	    if(back.equals("")){
		organ = one;
	    }
	}
	return organ;
    }
    public Lot getLot(){
	if(lot == null && !lot_id.equals("")){
	    Lot one = new Lot(debug, lot_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		lot = one;
	    }
	}
	return lot;
    }		
    public List<Donation> getDonations(){
	if(donations == null && !organization_id.equals("")){
	    DonationList dl = new DonationList(debug, organization_id);
	    String back = dl.find();
	    if(back.equals("")){
		List<Donation> ones = dl.getDonations();
		if(ones != null)
		    donations = ones;
	    }
	}
	return donations;
    }
    //
    public String doSave(){
		
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(asset_id.equals("") || lot_id.equals("") )
	    return " asset id or lot id not set ";
	prepareAsset();
	String qq = "insert into donations values(0,?,?,?,?,?,?,?)";
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
	    if(organization_id.equals(""))
		pstmt.setNull(1, Types.INTEGER);
	    else
		pstmt.setString(1,organization_id);								
	    pstmt.setString(2,asset_id);
	    if(asset_num.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, asset_num);								
	    if(type.equals(""))
		pstmt.setNull(4, Types.INTEGER);
	    else
		pstmt.setString(4, type);
	    if(date.equals(""))
		date = Helper.getToday();
	    pstmt.setDate(5, new java.sql.Date(dateFormat.parse(date).getTime()));
	    pstmt.setFloat(6, value);
	    pstmt.setString(7, lot_id);								
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
		back = device.updateStatus("Donated");
		if(back.equals("")){
		    DeviceHistory ih = new DeviceHistory(debug, null, asset_id, "Donated",null,user_id);
		    back = ih.doSave();
		}
		if(!back.equals("")){
		    addError(back);
		}
	    }
	    else if(monitor != null){
		back = monitor.updateStatus("Donated");					
		if(!back.equals("")){
		    addError(back);
		}		
	    }
	    else if (printer != null){
		back = printer.updateStatus("Donated");					
		if(!back.equals("")){
		    addError(back);
		}		
	    }								
	}
	catch(Exception ex){
	    back += ex;
	    System.err.println(" except "+back);											
	    logger.error(ex);
	    addError(back);	
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;

    }
    public String doUpdate(){
		
	String back = "";
		
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
	else{
	    try{
		qq = "update donations set ";
		qq += " value=?,organization_id=?, lot_id=?,date=? ";
		qq += "where id = ? ";
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		pstmt.setFloat(1,value);
		if(organization_id.equals(""))
		    pstmt.setNull(2,Types.INTEGER);
		else
		    pstmt.setString(2,organization_id);
		if(lot_id.equals(""))
		    pstmt.setNull(3,Types.INTEGER);
		else
		    pstmt.setString(3, lot_id);
		if(date.equals(""))
		    date = Helper.getToday();
		pstmt.setDate(4, new java.sql.Date(dateFormat.parse(date).getTime()));
		pstmt.setString(5,id);				
		pstmt.executeUpdate();
		message = "Updated Successfully";
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
	else{
	    try{
		qq = "delete from donations where id=? ";
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		message = "Deleted Successfully";
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
	return back;
    }
	
    //
    public String doSelect(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select asset_id,asset_num,type,date_format(date,'%m/%d/%Y'),organization_id,value,lot_id "+
	    " from donations where id=?";		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
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
		    setOrganization_id(rs.getString(5));
		    setValue(rs.getFloat(6));
		    setLot_id(rs.getString(7));
		}
		else{
		    back= "Record "+id+" Not found";
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
	}
	return back;
    }

}






















































