
package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class LotOptions extends CommonInc{

    String id="", date = "", asset_id="", asset_num="", serial_num="",
	name="", // lot name
	category="", division="",installed="", value="", weight="",
	recycle_location="", auction_name="", description="",
	organization="", // for donation 
	type=""; // Donation, Recycle, Auction
    int notNullCount = 0;
    static Logger logger = LogManager.getLogger(LotOptions.class);
    static final long serialVersionUID = 1025L;			

    // List<AuctionItem> auctionItems = null;

    //
    public LotOptions(){
    }
    public LotOptions(boolean deb, String val){
	//
	// initialize
	//
	debug = deb;
	setId(val);
    }		
    public LotOptions(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setType(val2);
    }	
	
    //
    // getters
    //
    public String getId(){
	return id;
    }
    public String getType(){
	if(id.equals("")) return "Donation";
	return type;
    }
    public boolean getDate(){
	return !date.equals("");
    }
    public boolean getAsset_id(){
	return !asset_id.equals("");
    }
    public boolean getAsset_num(){
	return !asset_num.equals("");
    }
    public boolean getSerial_num(){
	return !serial_num.equals("");
    }
    public boolean getName(){
	return !name.equals("");
    }
    public boolean getCategory(){
	return !category.equals("");
    }
    public boolean getDivision(){
	return !division.equals("");
    }
    public boolean getInstalled(){
	return !installed.equals("");
    }
    public boolean getValue(){
	return !value.equals("");
    }
    public boolean getWeight(){
	return !weight.equals("");
    }
    public boolean getOrganization(){
	return !organization.equals("");
    }
    public boolean getRecycle_location(){
	return !recycle_location.equals("");
    }
    public boolean getAuction_name(){
	return !auction_name.equals("");
    }
    public boolean getDescription(){
	return !description.equals("");
    }		
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setType(String val){
	if(val != null && !val.equals("-1"))
	    type = val;
    }
    public void setCategory(boolean val){
	if(val)
	    category = "y";
    }
    public void setDivision(boolean val){
	if(val)
	    division = "y";
    }		
    public void setAsset_id(boolean val){
	if(val)
	    asset_id = "y";
    }
    public void setAsset_num(boolean val){
	if(val)
	    asset_num = "y";
    }
    public void setSerial_num(boolean val){
	if(val)
	    serial_num = "y";
    }
    public void setName(boolean val){
	if(val)
	    name = "y"; 
    }
    public void setDescription(boolean val){
	if(val)
	    description = "y"; 
    }		
    public void setInstalled(boolean val){
	if(val)
	    installed = "y"; 
    }		
    public void setValue(boolean val){
	if(val)
	    value = "y";
    }		
    public void setDate(boolean val){
	if(val)
	    date = "y";
    }
    public void setWeight(boolean val){
	if(val)
	    weight = "y";
    }
    public void setOrganization(boolean val){
	if(val)
	    organization = "y";
    }
    public void setRecycle_location(boolean val){
	if(val)
	    recycle_location = "y";
    }
    public void setAuction_name(boolean val){
	if(val)
	    auction_name = "y";
    }		
    public int getNotNullCount(){
	return notNullCount;
    }
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if( type.equals("")){
	    back=" type not set ";
	    return back;
	}
	String qq = "insert into lot_options "+
	    "values(0,?,?,?,?,"+
	    "?,?,?,?,?,"+
	    "?,?,?,?,?, ?)";
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
	    int jj=1;
	    if(asset_id.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(asset_num.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(serial_num.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(name.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(category.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");

	    if(division.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(installed.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(date.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(description.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");						
	    if(value.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(weight.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");

	    if(organization.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(recycle_location.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(auction_name.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");						
	    pstmt.setString(jj++,type);
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
	if(id.equals("")){
	    back = "id set ";
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
	// we do not update type
	try{
	    qq = "update lot_options set asset_id=?,asset_num=?,serial_num=?,"+
		"name=?,category=?,division=?,installed=?, date=?,"+
		"description=?,value=?,weight=?,organization=?,recycle_location=?,"+
		"auction_name=? "+
		"where id=?";
						
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(asset_id.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(asset_num.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(serial_num.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(name.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(category.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");

	    if(division.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(installed.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(date.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(description.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");							
	    if(value.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(weight.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");

	    if(organization.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(recycle_location.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");
	    if(auction_name.equals(""))
		pstmt.setNull(jj++, Types.CHAR);
	    else
		pstmt.setString(jj++,"y");	
	    pstmt.setString(jj++,id);
	    pstmt.executeUpdate();
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
	    qq = "delete from lot_options where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
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
	
    //
    public String doSelect(){
		
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select id,asset_id,asset_num,serial_num,name,category,"+
	    "division,installed,date,description,value,weight,organization,"+
	    "recycle_location,auction_name,type,"+
	    "((asset_id is not null)+"+
	    "(asset_num is not null)+"+
	    "(serial_num is not null)+"+
	    "(name is not null)+"+
	    "(category is not null)+"+
	    "(division is not null)+"+
	    "(installed is not null)+"+
	    "(date is not null)+"+
	    "(description is not null)+"+
	    "(value is not null)+"+
	    "(weight is not null)+"+
	    "(organization is not null)+"+
	    "(recycle_location is not null)+"+
	    "(auction_name is not null))"+ // we ingore id,type 
	    "from lot_options where ";
	if(!id.equals("")){
	    qq += " id=? ";
	}
	else if(!type.equals("")){
	    qq += " type=? ";
	}
	else {
	    back = " Neither id nor type are set ";
	    return back;
	}
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
	    if(!id.equals(""))
		pstmt.setString(1,id);
	    else
		pstmt.setString(1, type);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setId(rs.getString(1));
		setAsset_id(rs.getString(2) != null);
		setAsset_num(rs.getString(3) != null);
		setSerial_num(rs.getString(4) != null);
		setName(rs.getString(5) != null);
		setCategory(rs.getString(6) != null);
		setDivision(rs.getString(7) != null);
		setInstalled(rs.getString(8) != null);
		setDate(rs.getString(9) != null);
		setDescription(rs.getString(10) != null);								
		setValue(rs.getString(11) != null);
		setWeight(rs.getString(12) != null);
		setOrganization(rs.getString(13) != null);
		setRecycle_location(rs.getString(14) != null);
		setAuction_name(rs.getString(15) != null);
		setType(rs.getString(16));
		notNullCount = rs.getInt(17);
	    }
	    else{
		back = "Record not found";
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
