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

public class LotsAction extends TopAction{

    static final long serialVersionUID = 1275L;	
    static Logger logger = LogManager.getLogger(LotsAction.class);
    List<Lot> lots = null;
    List<Lot> activeLots = null;		
    LotList lotList = null;
    String lotsTitle = "Most recent active lots";
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
	if(!action.equals("")){
	    getLotList();
	    lotList.setNoLimit();
	    back = lotList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		lots = lotList.getLots();
		if(lots != null && lots.size() > 0){
		    if(lots.size() == 1){
			Lot one = lots.get(0);
			try{
			    HttpServletResponse res = ServletActionContext.getResponse();
			    String str = url+"lot.action?id="+one.getId();
			    res.sendRedirect(str);
			    return super.execute();
			}catch(Exception ex){
			    System.err.println(ex);
			}													
		    }										
		    lotsTitle = "Found "+lots.size()+" records";
		}
		else{
		    addActionMessage("No match found");
		    lotsTitle = "No match found";
		}
	    }
	}				
	else{
	    getLotList();
	}
	return ret;
    }
    public LotList getLotList(){
	if(lotList == null){
	    lotList = new LotList(debug); 
	}
	return lotList;
    }
    @Override
    public void setAction(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public List<Lot> getActiveLots(){
	if(activeLots == null){
	    LotList ll = new LotList(debug);
	    ll.setStatus("Active");
	    String back = ll.find();
	    if(back.equals("")){
		activeLots = ll.getLots();
	    }
	}
	return activeLots;
    }
    public List<Lot> getLots(){
	if(lots == null){
	    LotList ll = new LotList(debug);
	    String back = ll.find();
	    if(back.equals("")){
		lots = ll.getLots();
	    }
	}
	return lots;
    }
    public String getLotsTitle(){
	return lotsTitle;
    }

}





































