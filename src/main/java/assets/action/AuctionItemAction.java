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

public class AuctionItemAction extends TopAction{

    static final long serialVersionUID = 1050L;	
    static Logger logger = LogManager.getLogger(AuctionItemAction.class);
    //
    AuctionItem item = null;
    String auctionItemsTitle = " Most recent auction items";
    List<AuctionItem> items = null;
    List<Auction> auctions = null;		// donations
    String asset_id = "";
    String type = "";
    String asset_num = "";
    String lot_id = "";
    List<Lot> lots = null;
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
		addActionMessage("Updated Successfully");
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
	    getItem();
	}
	return ret;
    }

    public List<Auction> getAuctions(){
	if(auctions == null){
	    AuctionList tl = new AuctionList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Auction> ones = tl.getAuctions();
		if(ones != null && ones.size() > 0){
		    auctions = ones;
		}
	    }
	}
	return auctions;
    }
    public AuctionItem getItem(){
	if(item == null){
	    item = new AuctionItem();
	}
	if(!id.equals("")){
	    item.setId(id);
	}
	if(!type.equals("")){
	    item.setType(type);
	}
	if(!asset_id.equals("")){
	    item.setAsset_id(asset_id);
	}
	if(!asset_num.equals("")){
	    item.setAsset_num(asset_num);
	}
	if(!lot_id.equals("")){
	    item.setLot_id(lot_id);
	}				
	return item;
    }
    public void setItem(AuctionItem val){
	if(val != null){
	    item = val;
	}
    }

    public String getAuctionItemsTitle(){
	return auctionItemsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    // most recent

    public List<AuctionItem> getItems(){ 
	if(items == null){
	    AuctionItemList dl = new AuctionItemList();
	    String back = dl.find();
	    items = dl.getAuctionItems();
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
    public String getAsset_num(){
	return asset_num;
    }
				
    public void setAsset_id(String val){
	if(val != null && !val.equals(""))		
	    asset_id = val;
    }
    public void setAsset_num(String val){
	if(val != null && !val.equals(""))		
	    asset_num = val;
    }
    public String getLot_id(){
	return lot_id;
    }		
    public void setLot_id(String val){
	if(val != null && !val.equals(""))		
	    lot_id = val;
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





































