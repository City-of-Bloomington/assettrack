package assets;
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
import org.apache.log4j.Logger;

public class DisposesAction extends TopAction{

		static final long serialVersionUID = 1270L;	
		static Logger logger = Logger.getLogger(DisposesAction.class);
		List<AuctionItem> items = null;
		List<DiscardItem> items2 = null;		
		List<Donation> items3 = null;
		List<RecycledItem> items4 = null;
		String auctionItemsTitle = "Most recent auctioned items";
		String discardsTitle = "Most recent dicarded items";
		String donationsTitle = "Most recent donations";
		String recyclesTitle = "Most recent recycled items";
		boolean showDiscards = true, showAuctions = true,
				showRecycles = true, showDonations = true;
				
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
				return ret;
		}

		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public List<AuctionItem> getItems(){ 
				if(items == null){
						AuctionItemList dl = new AuctionItemList();
						String back = dl.find();
						items = dl.getAuctionItems();
				}		
				return items;
		}
		public List<DiscardItem> getItems2(){ 
				if(items2 == null){
						DiscardItemList dl = new DiscardItemList();
						String back = dl.find();
						items2 = dl.getDiscards();
				}		
				return items2;
		}		
		public List<Donation> getItems3(){ 
				if(items3 == null){
						DonationList dl = new DonationList();
						String back = dl.find();
						items3 = dl.getDonations();
				}		
				return items3;
		}
		public List<RecycledItem> getItems4(){ 
				if(items4 == null){
						RecycledItemList dl = new RecycledItemList();
						String back = dl.find();
						items4 = dl.getRecycledItems();
				}		
				return items4;
		}
		public String getAuctionItemsTitle(){
				return auctionItemsTitle;
		}
		public String getDiscardsTitle(){
				return discardsTitle;
		}
		public String getDonationsTitle(){
				return donationsTitle;
		}
		public String getRecyclesTitle(){
				return recyclesTitle;
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
		
}





































