
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

public class Lot extends Type{

    String date = "",
	type="", // Donation, Recycle, Auction
	status=""; // Active, Approval, Complete
    static Logger logger = LogManager.getLogger(Lot.class);
    static final long serialVersionUID = 1015L;			
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    // List<AuctionItem> auctionItems = null;

    List<AuctionItem> items = null;
    List<Donation> items3 = null;
    List<RecycledItem> items4 = null;
    LotOptions lotOpt = null;
    String[] del_items = null;
    //
    public Lot(){
    }
    public Lot(boolean deb){
	//
	// initialize
	//
	super(deb);
    }				
    public Lot(boolean deb, String val){
	//
	// initialize
	//
	super(deb, val);
    }		
    public Lot(boolean deb, String val, String val2){
	//
	// initialize
	//
	super(deb, val, val2);
    }	

    public Lot(boolean deb, String val, String val2,
	       String val3,
	       String val4,
	       String val5
	       ){
	//
	// initialize
	//
	super(deb, val, val2);
	setDate(val3);
	setType(val4);
	setStatus(val5);
    }		
	
    //
    // getters
    //
    public String getDate(){
	return date;
    }
    public String getStatus(){
	if(id.equals("")) return "Active";
	return status;
    }
    public String getType(){
	if(id.equals("")) return "Donation";
	return type;
    }		
    public void setDel_item(String[] vals){
	del_items = vals;
    }
    //
    // setters
    //
    public void setDate(String val){
	if(val != null)
	    date = val;
    }
    public void setType(String val){
	if(val != null && !val.equals("-1"))
	    type = val;
    }
    public void setStatus(String val){
	if(val != null && !val.equals("-1"))
	    status = val;
    }
    public boolean canEdit(){
	return id.equals("") || !status.equals("Complete");
    }
    public List<AuctionItem> getItems(){ 
	if(items == null){
	    AuctionItemList dl = new AuctionItemList();
	    dl.setLot_id(id);
	    dl.setNoLimit();
	    String back = dl.find();
	    items = dl.getAuctionItems();
	}		
	return items;
    }
    public List<Donation> getItems3(){ 
	if(items3 == null){
	    DonationList dl = new DonationList();
	    dl.setLot_id(id);
	    dl.setNoLimit();
	    String back = dl.find();
	    items3 = dl.getDonations();
	}		
	return items3;
    }
    public List<RecycledItem> getItems4(){ 
	if(items4 == null){
	    RecycledItemList dl = new RecycledItemList();
	    dl.setLot_id(id);
	    dl.setNoLimit();
	    String back = dl.find();
	    items4 = dl.getRecycledItems();
	}		
	return items4;
    }
    // we find the lotOpt print options based on lot type
    public LotOptions getLotOpt(){
	if(lotOpt == null && !id.equals("")){
	    LotOptions lo = new LotOptions(debug, null, type);
	    String back = lo.doSelect();
	    if(back.equals("")){
		lotOpt = lo;
	    }
	}
	return lotOpt;
    }
    @Override
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(name.equals("") || type.equals("")){
	    back=" lot name or type not set ";
	    return back;
	}
	status="Active";
	date = Helper.getToday();
	String qq = "insert into lots values(0,?,now(),?,'Active')";
	if(name.equals("")){
	    back = "lot name not set ";
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
	    pstmt.setString(2,type);
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
    @Override
    public String doUpdate(){
		
	String back = "";
	if(name.equals("") || type.equals("") || status.equals("")){
	    back = "lot name, type or status not set ";
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
	    qq = "update lots set name=?,type=?,status=? "+
		"where id=?";
						
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,name);
	    pstmt.setString(2,type);
	    pstmt.setString(3,status);
	    pstmt.setString(4,id);
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
	return back;

    }
    @Override
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
	    qq = "delete from lots where id=?";
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
	String qq = "select id,name,date_format(date,'%m/%d/%Y'),type,status "+
	    "from lots where id=?";
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
		setName(rs.getString(2));
		setDate(rs.getString(3));
		setType(rs.getString(4));
		setStatus(rs.getString(5));
	    }
	    else{
		back = "Record "+id+" not found";
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
