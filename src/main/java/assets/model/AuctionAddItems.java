package assets.model;
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
import assets.list.*;
import assets.utils.*;

public class AuctionAddItems extends CommonInc{
		
    boolean debug = false;
    static Logger logger = LogManager.getLogger(AuctionAddItems.class);
    static final long serialVersionUID = 1030L;			
    String auction_id="", lot_id="", user_id="", whichDate="";
    Auction auction = null;
    String [] item_ids = null;
    String [] item_vals = null; // value
    String [] item_types = null;
    //
    public AuctionAddItems(){
	super();
	whichDate="warranty_expire"; // set the defualt		
    }
    public AuctionAddItems(boolean deb, String val){
	super(deb);
	setAuction_id(val);
	whichDate="warranty_expire"; // set the defualt
    }	

    public String getAuction_id() {
	return auction_id;
    }
    public String getLot_id() {
	return lot_id;
    }		
    public Auction getAuction() {
	if(auction == null && !auction_id.equals("")){
	    auction = new Auction(debug, auction_id);
	    auction.doSelect();
	}
	return auction;
    }	
    //
    public void setAuction_id(String val) {
	if(val != null)
	    auction_id = val;
    }
    public void setUser_id(String val) {
	if(val != null)
	    user_id = val;
    }
    public void setLot_id(String val) {
	if(val != null)
	    lot_id = val;
    }		
    public void setItem_ids(String[] vals) {
	if(vals != null)
	    item_ids = vals;
    }
    public void setItem_vals(String[] vals) {
	if(vals != null)
	    item_vals = vals;
    }
    public void setItem_types(String[] vals) {
	if(vals != null)
	    item_types = vals;
    }		
    public void setItem_ids(String val) {// needed for jsp
    }
    public void setItem_vals(String val) {// needed for jsp
    }
    public void setItem_types(String val) {// needed for jsp
    }		
    /**
     * because of the way checkboxes and input fields in a form when
     * submitted are treated differently, where the only checkboxes that are
     * checked sent while are the input text field are sent
     * Therefore we have to loop of the values to skip the ones that are
     * unset
     */
    public void add(){
	int jj=0;
	if(item_ids != null && item_vals != null){
	    for(int i=0;i<item_ids.length;i++){
		String val = "0", type="";
		for(int j=jj;j<item_vals.length;j++){
		    if(item_vals[j] != null && !item_vals[j].equals("")){
			val = item_vals[j];
			if(item_types[j] != null && !item_types[j].equals("")){
			    type = item_types[j];
			}
			jj=j+1;
			break;
		    }
		}
		AuctionItem one = new AuctionItem(debug, null, auction_id, item_ids[i], null, type, val, null, lot_id);
		String back = one.doSave();
		if(!back.equals("")){
		    addError(back);
		}
	    }
	}
    }

}






















































