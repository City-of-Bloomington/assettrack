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


public class DisposeChange extends Item{
	
    static Logger logger = LogManager.getLogger(DisposeChange.class);
    static final long serialVersionUID = 1265L;			
    String auction_id="", description="",
	location_id="", organization_id="", lot_id="", method="";
    float value = 0f, weight=0f;
    String method_from="", method_to="";// Auction, Discard, Recycle, Donatation
    String discard_method=""; 
    public DisposeChange(){
	super();
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
    public void setMethod_from(String val){
	if(val != null)		
	    method_from = val;
    }
    public void setMethod_to(String val){
	if(val != null)		
	    method_to = val;
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
    public String getMethod_from() {
	return method_from;
    }
    public String getMethod_to() {
	if(method_to.equals("")){
	    if(!method_from.equals("")){
		if(method_from.equals("Donation")){
		    method_to ="Recycle";
		}
		else{
		    method_to = "Donation";
		}
	    }
	}
	return method_to;
    }
    public String getMethod() {
	if(method.equals(""))
	    return "-1";
	return method;
    }		

    public String doSelect(){
	String back = "";
	if(method_from.equals("")){
	    back = "No type specified ";
	    return back;
	}
	if(method_from.equals("Auction")){				
	    AuctionItem one = new AuctionItem(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		auction_id = one.getAuction_id();
		setValue(one.getValue());
		lot_id = one.getLot_id();
		getInfo(one);
	    }
	}
	else if(method_from.equals("Donation")){
	    Donation one = new Donation(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		organization_id = one.getOrganization_id();
		value = one.getValue();
		lot_id = one.getLot_id();
		getInfo(one);
	    }						
	}
	else if(method_from.equals("Recycle")){
	    RecycledItem one = new RecycledItem(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		location_id = one.getLocation_id();
		lot_id = one.getLot_id();
		setWeight(one.getWeight());
		description = one.getDescription();
		getInfo(one);
	    }
	}
	else if(method_from.equals("Discard")){
	    DiscardItem one = new DiscardItem(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		method = one.getMethod();
		description = one.getDescription();								
		getInfo(one);
	    }
	}
	else{
	    back = "Unknown dispose method "+method_from;
	}
	return back;
    }
    private void getInfo(Item item){
	asset_id=item.getAsset_id();
	asset_num=item.getAsset_num();
	type = item.getType();
    }
    //
    public String doChange(){
		
	String back = "";
	String new_status = "";
	if(id.equals("")){
	    back = " previous operation id not set ";
	    return back;
	}
	if(method_from.equals("")){
	    back = " previous dispose method not set ";
	    return back;
	}
	if(method_to.equals("")){
	    back = " next dipose method not set ";
	    return back;
	}
	if(method_from.equals("Auction")){
	    new_status="Auctioned";	    
	    AuctionItem one = new AuctionItem(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		back = changeDispose(one);
		if(back.equals("")){
		    back = one.doDelete();
		}
	    }
	}
	else if(method_from.equals("Donation")){
	    new_status = "Donated";
	    Donation one = new Donation(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		back = changeDispose(one);
		if(back.equals("")){
		    back = one.doDelete();
		}								
	    }						
	}
	else if(method_from.equals("Recycle")){
	    new_status= "Recycled";
	    RecycledItem one = new RecycledItem(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		back = changeDispose(one);
		if(back.equals("")){
		    back = one.doDelete();
		}										
	    }
	}
	else if(method_from.equals("Discard")){
	    new_status="Disposed";
	    DiscardItem one = new DiscardItem(debug, id);
	    back = one.doSelect();
	    if(back.equals("")){
		back = changeDispose(one);
		if(back.equals("")){
		    back = one.doDelete();
		}										
	    }
	}
	else{
	    back = "Unknown dispose method "+method_from;
	}
	if(back.isEmpty()){
	    // change the status in the original record
	    if(type.equals("device")){
		Device dd = new Device(debug, asset_id);
		back = dd.doSelect();
		back = dd.updateStatus(new_status);
	    }
	}
	return back;
    }
    private String changeDispose(Item item){
	String back="";
	getInfo(item);
	if(method_to.equals("Auction")){
	    AuctionItem one = new AuctionItem(debug, null, asset_id, asset_num, type, auction_id, ""+value, description, lot_id);
	    one.setUser_id(user_id);
	    back = one.doSave();
	    // id = one.getId();
	}
	else if(method_to.equals("Donation")){
	    Donation one = new Donation(debug, null, asset_id, asset_num, type, date, organization_id, ""+value,lot_id);
	    one.setUser_id(user_id);
	    back = one.doSave();
	    // id = one.getId();
	}
	else if(method_to.equals("Recycle")){
	    RecycledItem one = new RecycledItem(debug, null, asset_id, asset_num, type, date, location_id, ""+weight, description, lot_id);
	    one.setUser_id(user_id);
	    back = one.doSave();
	}
	else if(method_to.equals("Discard")){
	    DiscardItem one = new DiscardItem(debug, null, asset_id, asset_num, type, date,  method, description);
	    one.setUser_id(user_id);
	    back = one.doSave();
	    // id = one.getId();
	}
	else{
	    back = "Unknown dispose method "+method_to;
	}
	return back;
    }

}






















































