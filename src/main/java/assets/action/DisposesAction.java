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

public class DisposesAction extends TopAction{

    static final long serialVersionUID = 1270L;	
    static Logger logger = LogManager.getLogger(DisposesAction.class);
    List<AuctionItem> items = null;
    List<DiscardItem> items2 = null;		
    List<Donation> items3 = null;
    List<RecycledItem> items4 = null;
    List<String[]> donationData = null;
    List<Lot> lots = null;
    String auctionItemsTitle = "Most recent auctioned items";
    String discardsTitle = "Most recent dicarded items";
    String donationsTitle = "Most recent donations";
    String recyclesTitle = "Most recent recycled items";
    String donationsReportTitle = "Donations classified by Organization, Type and Date ";
    boolean showDiscards = true, showAuctions = true,
	showRecycles = true, showDonations = true;
    String date_from="", date_to="", lot_id="";
    String date_range = "";
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
	if(!date_from.equals("") && !date_to.equals("")){
	    date_range = date_from+" - "+date_to;
	}
	return ret;
    }

    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public List<AuctionItem> getItems(){ 
	if(items == null){
	    AuctionItemList dl = new AuctionItemList();
	    dl.setLot_id(lot_id);
	    dl.setNoLimit();
	    String back = dl.find();
	    items = dl.getAuctionItems();
	}		
	return items;
    }
    public List<DiscardItem> getItems2(){ 
	if(items2 == null){
	    DiscardItemList dl = new DiscardItemList();
	    dl.setNoLimit();
	    String back = dl.find();
	    items2 = dl.getDiscards();
	}		
	return items2;
    }		
    public List<Donation> getItems3(){ 
	if(items3 == null){
	    DonationList dl = new DonationList();
	    dl.setDate_from(date_from);
	    dl.setDate_to(date_to);
	    dl.setLot_id(lot_id);
	    dl.setNoLimit();
	    String back = dl.find();
	    items3 = dl.getDonations();
	    dl.prepareReport();
	    donationData = dl.getDataList();
	}		
	return items3;
    }
    public List<String[]> getDonationData(){
	return donationData;
    }
    public List<RecycledItem> getItems4(){ 
	if(items4 == null){
	    RecycledItemList dl = new RecycledItemList();
	    dl.setDate_from(date_from);
	    dl.setDate_to(date_to);
	    dl.setLot_id(lot_id);
	    dl.setNoLimit();
	    String back = dl.find();
	    items4 = dl.getRecycledItems();
	}		
	return items4;
    }
    public String getAuctionItemsTitle(){
	return auctionItemsTitle+" "+date_range;
    }
    public String getDiscardsTitle(){
	return discardsTitle+" "+date_range;
    }
    public String getDonationsTitle(){
	return donationsTitle+" "+date_range;
    }
    public String getDonationsReportTitle(){
	return donationsReportTitle+" "+date_range;
    }		
    public String getRecyclesTitle(){
	return recyclesTitle+" "+date_range;
    }
		
    public boolean getShowRecycles(){
	return showRecycles;
    }
    public boolean getShowDonations(){
	return showDonations;
    }
    public boolean getShowDiscards(){
	return showDiscards;
    }
    public boolean getShowAuctions(){
	return showAuctions;
    }
    public void setShowRecycles(boolean val){
	showRecycles = val;
    }
    public void setShowDiscards(boolean val){
	showDiscards = val;
    }
    public void setShowDonations(boolean val){
	showDonations = val;
    }
    public void setShowAuctions(boolean val){
	showAuctions = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
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
    public String getLot_id(){
	return lot_id;
    }		
    public void setLot_id(String val){
	if(val != null && !val.equals(""))		
	    lot_id = val;
    }					
}





































