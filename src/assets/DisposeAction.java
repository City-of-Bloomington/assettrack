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

public class DisposeAction extends TopAction{

		static final long serialVersionUID = 1250L;	
		static Logger logger = Logger.getLogger(DisposeAction.class);
		//
		DisposeItem item = null;
		String method = "Donation";
		String asset_id = "";
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
				if(action.equals("Next")){
						String str = "";
						if(method.equals("Donation")){
								str = url+"donation.action?asset_id="+asset_id+"&type="+type;
						}
						else if(method.equals("Recycle")){
								str = url+"recycle.action?asset_id="+asset_id+"&type="+type;
								
						}
						else if(method.equals("Discard")){
								str = url+"discard.action?asset_id="+asset_id+"&type="+type;
						}
						else if(method.equals("Auction")){
								str = url+"auctionItem.action?asset_id="+asset_id+"&type="+type;
						}						
						else {
								back += " Unknown dispose method "+method;
								addActionError(back);
						}
						if(!str.equals("")){
								try{
										HttpServletResponse res = ServletActionContext.getResponse();
										res.sendRedirect(str);
										return super.execute();
								}catch(Exception ex){
										System.err.println(ex);
								}	
						}
				}				
				else{

				}
				return ret;
		}

		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
		}
		public String getMethod(){
				return method;
		}
		public void setMethod(String val){
				if(val != null && !val.equals("-1"))		
						method = val;
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
}





































