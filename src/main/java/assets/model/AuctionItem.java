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

public class AuctionItem extends Item{
	
    static Logger logger = LogManager.getLogger(AuctionItem.class);
    static final long serialVersionUID = 1040L;			
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String auction_id="", description="", lot_id="";
    float value = 0.0f;
    Auction auction = null;
    Lot lot = null;
    public AuctionItem(){

	//
	// initialize
	//
    }
    public AuctionItem(boolean deb, String val){

	super(deb, val);
    }		
    public AuctionItem(boolean deb, String val, String val2){

	super(deb);
	//
	// initialize
	//
	setAuction_id(val);
	setAsset_id(val2);
    }
    public AuctionItem(boolean deb,
		       String val,  // id
		       String val2, // asset_id
		       String val3, // asset_num
		       String val4, // type
		       String val5, // auction_id
		       String val6, // value
		       String val7, // description
		       String val8){  // lot_id

	super(deb, val, val2, val3, val4, null); // no date for auction
	//
	setAuction_id(val5);
	setValue(val6);
	setDescription(val7);
	setLot_id(val8);
    }
    //
    // setters
    //
    public void setAuction_id(String val){
	if(val != null && !val.equals("-1"))		
	    auction_id = val;
    }
    public void setLot_id(String val){
	if(val != null && !val.equals("-1"))		
	    lot_id = val;
    }		
    public void setValue(String val) {
	if(val != null && !val.equals("")){
	    try{
		value = Float.parseFloat(val);
	    }catch(Exception ex){}
	}
    }
    public void setDescription(String val){
	if(val != null)		
	    description = val.trim();
    }		
    //
    // getters
    //
    public String  getAuction_id(){
	return auction_id;
    }
    public String  getLot_id(){
	return lot_id;
    }		
    public String getValue() {
	return ""+value;
    }
    public String getDescription() {
	return description;
    }		
    public Auction getAuction(){
	if(auction == null && !auction_id.equals("")){
	    Auction one = new Auction(debug, auction_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		auction = one;
	    }
	}
	return auction;
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
    //
    public String doSave(){
		
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(asset_id.equals("") || auction_id.equals("") || lot_id.equals("")){
	    back = "auction id, asset id or lot id not set ";
	    addError(back);
	    return back;
	}
	prepareAsset();
	String qq = "insert into auction_items values(0,?,?,?,?, ?,?,?)";
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
	    pstmt.setString(1,auction_id);				
	    pstmt.setString(2,asset_id);
	    if(!asset_num.equals(""))
		pstmt.setString(3,asset_num);
	    else
		pstmt.setNull(3,Types.VARCHAR);								
	    if(!type.equals(""))
		pstmt.setString(4,type);
	    else
		pstmt.setNull(4,Types.INTEGER);
	    pstmt.setFloat(5,value);
	    if(!description.equals(""))
		pstmt.setString(6,description);
	    else
		pstmt.setNull(6,Types.VARCHAR);
	    pstmt.setString(7,lot_id);
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
		back = device.updateStatus("Auctioned");
		if(back.equals("")){
		    DeviceHistory ih = new DeviceHistory(debug, null, asset_id, "Auctioned",null,user_id);
		    back = ih.doSave();
		}
		if(!back.equals("")){
		    addError(back);
		}
	    }
	    else if(monitor != null){
		back = monitor.updateStatus("Auctioned");					
		if(!back.equals("")){
		    addError(back);
		}		
	    }
	    else if (printer != null){
		back = printer.updateStatus("Auctioned");					
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
    public String doUpdate(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String str="";
	String qq = "";
	prepareAsset();
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "update auction_items set ";
	    qq += "auction_id=?,asset_id=?, asset_num=?,type=?,";
	    qq += "value=?,description=?,lot_id=? ";
	    qq += "where id=? ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,auction_id);
	    pstmt.setString(2,asset_id);
	    if(!asset_num.equals(""))
		pstmt.setString(3,asset_num);
	    else
		pstmt.setNull(3,Types.VARCHAR);											
	    if(!type.equals(""))
		pstmt.setString(4,type);
	    else
		pstmt.setNull(4,Types.INTEGER);
	    pstmt.setFloat(5,value);
	    if(!description.equals(""))
		pstmt.setString(6,description);
	    else
		pstmt.setNull(6,Types.VARCHAR);	
	    pstmt.setString(7,lot_id);
	    pstmt.setString(8,id);								
	    pstmt.executeUpdate();
	    message="Updated Successfully";
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(qq);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	doSelect();
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
	    qq = "delete from auction_items where id=?";
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
    public String doSelect(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select asset_id,asset_num,type,auction_id,value,description,lot_id "+
	    " from auction_items where id=?";		
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
		setAuction_id(rs.getString(4));
		setValue(rs.getString(5));
		setDescription(rs.getString(6));
		setLot_id(rs.getString(7));
	    }
	    else{
		back= "Record "+id+" Not found";
		message=back;
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






















































