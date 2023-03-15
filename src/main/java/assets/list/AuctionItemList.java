package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class AuctionItemList extends CommonInc{

    static Logger logger = LogManager.getLogger(AuctionItemList.class);
    static final long serialVersionUID = 1060L;			
    String auction_id="", lot_id="", limit=" limit 50 ";
    Auction auction = null;
    ArrayList<AuctionItem> auctionItems = null;

    public AuctionItemList(){
	super();
    }
    public AuctionItemList(boolean val, String val2){
	super(val);
	setAuction_id(val2);
    }	
    public void setAuction_id(String val){
	if(val != null)
	    auction_id = val.trim();
    }
    public void setLot_id(String val){
	if(val != null)
	    lot_id = val.trim();
    }		
    public void setAuction(Auction val){
	if(val != null){
	    auction = val;
	    auction_id = auction.getId();
	}
    }
    public List<AuctionItem> getAuctionItems(){
	return auctionItems;
    }
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,asset_id,asset_num,type,auction_id,value,description,lot_id "+
	    " from auction_items ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    try{
								
		if(!auction_id.equals("")){
		    qw += " auction_id = ? ";
		}
		if(!lot_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " lot_id = ? ";
		}								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		int jj = 1;
		if(!auction_id.equals("")){
		    pstmt.setString(jj++, auction_id);
		}
		if(!lot_id.equals("")){
		    pstmt.setString(jj++, lot_id);
		}								
		rs = pstmt.executeQuery();
		while(rs.next()){
		    if(auctionItems == null)
			auctionItems = new ArrayList<AuctionItem>();
		    AuctionItem one =
			new AuctionItem(debug,
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8)
					);
		    auctionItems.add(one);
		}
	    }
	    catch(Exception ex){
		back += ex+" : "+qq;
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






















































