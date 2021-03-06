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

public class DisposeChangeAction extends TopAction{

		static final long serialVersionUID = 1320L;	
		static Logger logger = Logger.getLogger(DisposeChangeAction.class);
		//
		DisposeChange item = null;
		String title = " Most recent changes";
		List<Type> organizations = null;		// donations
		List<Lot> lots = null;
		List<Auction> auctions = null;
		List<Type> locations = null;	// for recycle		
		String method_from = "", asset_num="", lot_id="";
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
				if(action.equals("Change")){
						item.setUser_id(user.getId());
						back = item.doChange();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = item.getId();
								addActionMessage("Changed Successfully");
						}
				}
				else{
						getItem();
						back = item.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}						
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
		public DisposeChange getItem(){
				if(item == null){
						item = new DisposeChange();
				}
				if(!id.equals(""))
						item.setId(id);
				if(!method_from.equals(""))
						item.setMethod_from(method_from);
				return item;
		}
		public void setItem(DisposeChange val){
				if(val != null){
						item = val;
						if(!id.equals("")){
								item.setId(id);
						}
						if(!method_from.equals("")){
								item.setMethod_from(method_from);
						}						
				}
		}

		public String getTitle(){
				return title;
		}		
		public void setMethod_from(String val){
				if(val != null && !val.equals(""))		
						method_from = val;
		}
		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
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
		public List<Type> getLocations(){
				if(locations == null){
						TypeList tl = new TypeList(debug, null, "recycle_locations");
						String back = tl.find();
						if(back.equals("")){
								List<Type> ones = tl.getTypes();
								if(ones != null && ones.size() > 0){
										locations = ones;
								}
						}
				}
				return locations;
		}
}





































