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

public class LotAction extends TopAction{

    static final long serialVersionUID = 1025L;	
    static Logger logger = LogManager.getLogger(LotAction.class);
    //
    Lot lot = null;
    String lotsTitle = " Most recent active lots";
    String auctionItemsTitle = "Auctioned items List";
    String donationsTitle = "Donation Items List";
    String recyclesTitle = "Recycled Items List";
    List<Lot> lots = null;
    LotOptions lotOpt = null;
    String lotOpt_id = "";
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
	    back = lot.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = lot.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(action.startsWith("Save")){
	    back = lot.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = lot.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(action.startsWith("Edit")){
	    getLot();
	    back = lot.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else if(action.startsWith("print")){
	    getLot();
	    back = lot.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    getLotOpt();
	    if(lotOpt != null){
		back = lotOpt.doUpdate();
		if(!back.equals("")){
		    addActionError(back);
		}
		else{
		    ret = "print";
		}
	    }
	    else{
		ret = "print";
	    }
	}				
	else if(!id.equals("")){
	    getLot();
	    back = lot.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    ret = "view";
	}				
	else{
	    getLot();
	}
	return ret;
    }
    public List<Lot> getLots(){
	if(lots == null){
	    LotList tl = new LotList(debug);
	    tl.setStatus("Active");
	    String back = tl.find();
	    if(back.equals("")){
		List<Lot> ones = tl.getLots();
		if(ones != null && ones.size() > 0){
		    lots = ones;
		}
	    }
	}
	return lots;
    }
    public LotOptions getLotOpt(){
	if(lotOpt == null && lot != null){
	    String type = lot.getType();
	    if(!type.equals("")){
		LotOptions lo = new LotOptions(debug, null, type);
		String back = lo.doSelect();
		if(back.equals("")){
		    lotOpt = lo;
		}
	    }
	}
	return lotOpt;

    }
    public Lot getLot(){
	if(lot == null){
	    lot = new Lot();
	}
	if(!id.equals("")){
	    lot.setId(id);
	}
	return lot;
    }
    public void setLot(Lot val){
	if(val != null){
	    lot = val;
	}
    }
    public void setLotOpt(LotOptions val){
	if(val != null){
	    lotOpt = val;
	}
    }
    public String getLotsTitle(){
	return lotsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getRecyclesTitle(){
	return recyclesTitle;
    }
    public String getDonationsTitle(){
	return donationsTitle;
    }		
    public String getAuctionItemsTitle(){
	return auctionItemsTitle;
    }

}





































