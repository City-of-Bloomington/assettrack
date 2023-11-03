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

public class DisposeChangeAction extends TopAction{

    static final long serialVersionUID = 1320L;	
    static Logger logger = LogManager.getLogger(DisposeChangeAction.class);
    //
    DisposeChange dispose = null;
    String title = " Most recent changes";
    List<Type> organizations = null;	// for donations	
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
	    dispose.setUser_id(user.getId());
	    back = dispose.doChange();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = dispose.getId();
		addActionMessage("Changed Successfully");
	    }
	}
	else{
	    getDispose();
	    back = dispose.doSelect();
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
    public DisposeChange getDispose(){
	if(dispose == null){
	    dispose = new DisposeChange();
	}
	if(!id.equals(""))
	    dispose.setId(id);
	if(!method_from.equals(""))
	    dispose.setMethod_from(method_from);
	return dispose;
    }
    public void setDispose(DisposeChange val){
	if(val != null){
	    dispose = val;
	    if(!id.equals("")){
		dispose.setId(id);
	    }
	    if(!method_from.equals("")){
		dispose.setMethod_from(method_from);
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





































