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

// TODO this for adding auctions
public class AuctionAction extends TopAction{

		static final long serialVersionUID = 1020L;	
		static Logger logger = Logger.getLogger(AuctionAction.class);
		//
		Auction auction = null;
		String auctionsTitle = " Most recent auctions";
		List<Auction> auctions = null;
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
						back = auction.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = auction.getId();
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.startsWith("Save")){
						back = auction.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = auction.getId();
								addActionMessage("Saved Successfully");
						}
				}				
				else if(!id.equals("")){
						// nothing
						back = auction.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}						
				}				
				else{

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
		public Auction getAuction(){
				if(auction == null){
						auction = new Auction();
				}
				if(!id.equals("")){
						auction.setId(id);
				}
				return auction;
		}
		public void setAuction(Auction val){
				if(val != null){
						auction = val;
				}
		}

		public String getAuctionsTitle(){
				return auctionsTitle;
		}		
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}		

}





































