package assets.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

public class DonationAction extends TopAction{

    static final long serialVersionUID = 1320L;	
    static Logger logger = LogManager.getLogger(DisposeAction.class);
    //
    Donation item = null;
    String donationsTitle = " Most recent Donations";
    List<Donation> items = null;
    List<Type> organizations = null;		// donations
    List<Lot> lots = null;
    String asset_id = "", asset_num="", lot_id="";
    String type = "";		
    public String execute(){
	String ret = SUCCESS;
	String back = doPrepare();
	if(!back.equals("")){
	    try{
		HttpServletResponse res = ServletActionContext.getResponse();
		String str = url+"Login";
		res.sendRedirect(str);
		return super.execute();
	    }catch(Exception ex){
		System.err.println(ex);
	    }	
	}
	if(action.equals("Save")){
	    item.setUser_id(user.getId());
	    back = item.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = item.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(action.startsWith("Save")){
	    item.setUser_id(user.getId());
	    back = item.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = item.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(!id.equals("")){
	    getItem();
	    back = item.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }						
	}				
	else{

	}
	return ret;
    }
    public List<Type> getOrganizations(){
	if(organizations == null){
	    TypeList tl = new TypeList(debug, null,"organizations");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> types = tl.getTypes();
		if(types != null && types.size() > 0){
		    organizations = types;
		}
	    }
	}
	return organizations;
    }
    public Donation getItem(){
	if(item == null){
	    item = new Donation();
	}
	if(!id.equals("")){
	    item.setId(id);
	}
	if(!type.equals("")){
	    item.setType(type);
	}
	if(!lot_id.equals("")){
	    item.setLot_id(lot_id);
	}				
	if(!asset_id.equals("")){
	    item.setAsset_id(asset_id);
	}
	if(!asset_num.equals("")){
	    item.setAsset_num(asset_num);
	}				
	return item;
    }
    public void setItem(Donation val){
	if(val != null){
	    item = val;
	}
    }

    public String getDonationsTitle(){
	return donationsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    // most recent

    public List<Donation> getItems(){ 
	if(items == null){
	    DonationList dl = new DonationList();
	    String back = dl.find();
	    items = dl.getDonations();
	}		
	return items;
    }
    public String getType(){
	return type;
    }
    public void setType(String val){
	if(val != null && !val.equals(""))		
	    type = val;
    }
    public String getAsset_id(){
	return asset_id;
    }
    public void setAsset_id(String val){
	if(val != null && !val.equals(""))		
	    asset_id = val;
    }
    public String getLot_id(){
	return lot_id;
    }		
    public void setLot_id(String val){
	if(val != null && !val.equals(""))		
	    lot_id = val;
    }		
    public String getAsset_num(){
	return asset_num;
    }
    public void setAsset_num(String val){
	if(val != null && !val.equals(""))		
	    asset_num = val;
    }
    public List<Lot> getLots(){
	if(lots == null){
	    LotList ll = new LotList(debug);
	    ll.setStatus("Active");
	    String back = ll.find();
	    if(back.equals("")){
		lots = ll.getLots();
	    }
	}
	return lots;
    }
		
}





































