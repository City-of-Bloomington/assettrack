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

public class DisposeItem extends Item{
	
    static Logger logger = LogManager.getLogger(DisposeItem.class);
    static final long serialVersionUID = 1260L;			
    String auction_id="", description="",
	location_id="", organization_id="", lot_id="";
    float value = 0.0f, weight=0.0f;
    String method="";// Auction, Discard, Recycle, Donatation
    String discard_method=""; 
    public DisposeItem(){

	super();
	//
    }
    public DisposeItem(boolean deb,
		       String val){

	super(deb, val);
    }
    public DisposeItem(boolean deb,
		       String val,
		       String val2,
		       String val3,
		       String val4,
		       String val5,
		       String val6){
	super(deb, val, val2, val3, val4, val5);
	setLot_id(val6);

    }		
    //
    // setters
    //
    public void setAuction_id(String val){
	if(val != null)		
	    auction_id = val;
    }
    public void setLot_id(String val){
	if(val != null && !val.equals("-1"))		
	    lot_id = val;
    }		
    public void setLocation_id(String val){
	if(val != null && !val.equals("-1"))		
	    location_id = val;
    }
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
    public void setWeight(String val) {
	if(val != null && !val.equals("")){
	    try{
		weight = Float.parseFloat(val);
	    }catch(Exception ex){}
	}
    }		
    public void setDescription(String val){
	if(val != null)		
	    description = val.trim();
    }
    public void setMethod(String val){
	if(val != null && !val.equals("-1"))		
	    method = val;
    }
    public void setDiscard_Method(String val){
	if(val != null && !val.equals("-1"))		
	    discard_method = val;
    }		
    //
    // getters
    //
    public String  getAuction_id(){
	return auction_id;
    }
    public String  getLocation_id(){
	return location_id;
    }
    public String  getLot_id(){
	return lot_id;
    }		
    public String getValue() {
	return ""+value;
    }
    public String getWeight() {
	return ""+weight;
    }		
    public String getDescription() {
	return description;
    }
    public String getMethod() {
	return method;
    }
    public String getDiscard_method() {
	return discard_method;
    }		
    //
    public String doDispose(){
		
	String back = "";
	if(method.equals("Auction")){
	    AuctionItem one = new AuctionItem(debug, null, asset_id, asset_num, type, auction_id, ""+value, description, lot_id);
	    back = one.doSave();
	    id = one.getId();
	}
	else if(method.equals("Donation")){
	    Donation one = new Donation(debug, null, asset_id, asset_num, type, date, organization_id, ""+value,lot_id);
	    back = one.doSave();
	    id = one.getId();
	}
	else if(method.equals("Recycle")){
	    RecycledItem one = new RecycledItem(debug, null, asset_id, asset_num, type, date, location_id, ""+weight, description, lot_id);
	    back = one.doSave();
	}
	else if(method.equals("Discard")){
	    DiscardItem one = new DiscardItem(debug, null, asset_id, asset_num, type, date,  method, description);
	    back = one.doSave();
	    id = one.getId();
	}
	else{
	    back = "Unknown dispose method "+method;
	}
	return back;
    }

}






















































