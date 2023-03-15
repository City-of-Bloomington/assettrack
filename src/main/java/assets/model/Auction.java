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

public class Auction extends Type{

    String date = "";
    static Logger logger = LogManager.getLogger(Auction.class);
    static final long serialVersionUID = 1010L;			
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    List<AuctionItem> auctionItems = null;
    String[] del_items = null;
    //
    public Auction(){
	super(Helper.debug);
    }
    public Auction(boolean deb, String val){
	//
	// initialize
	//
	super(deb, val);
    }		
    public Auction(boolean deb, String val, String val2){
	//
	// initialize
	//
	super(deb, val, val2);
    }	

    public Auction(boolean deb, String val, String val2, String val3){
	//
	// initialize
	//
	super(deb, val, val2);
	setDate(val3);
    }		
	
    //
    // getters
    //
    public String getDate(){
	return date;
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
    public String toString(){
	return name;
    }
    public List<AuctionItem> getAuctionItems(){

	if(auctionItems == null && !id.equals("")){
	    AuctionItemList oil = new AuctionItemList(debug, id);
	    String back = oil.find();
	    if(back.equals("")){
		auctionItems = oil.getAuctionItems();
	    }
	}
	return auctionItems;
    }
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into auctions values(0,?,?)";
	if(name.equals("")){
	    back = "auction name not set ";
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
	else{
	    try{
		pstmt = con.prepareStatement(qq);
		if(debug){
		    logger.debug(qq);
		}
		pstmt.setString(1,name);
		if(date.equals(""))
		    pstmt.setNull(2,Types.DATE);
		else
		    pstmt.setDate(2, new java.sql.Date(dateFormat.parse(date).getTime()));
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
	}
	return back;

    }
    public String doUpdate(){
		
	String back = "";
	if(name.equals("")){
	    back = "auction name not set ";
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
	else{
	    try{
		qq = "update auctions set name=?,date=? "+
		    "where id=?";
				
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		pstmt.setString(1,name);
		if(date.equals(""))
		    pstmt.setNull(2,Types.DATE);
		else
		    pstmt.setDate(2, new java.sql.Date(dateFormat.parse(date).getTime()));
		pstmt.setString(3,id);
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
		qq = "delete from auctions where id=?";
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
	}
	return back;

    }
	
    //
    public String doSelect(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select name,date_format(date,'%m/%d/%Y') "+
	    "from auctions where id=?";
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
		setDate(rs.getString(2));
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
