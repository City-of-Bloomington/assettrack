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

public class DiscardAction extends TopAction{

    static final long serialVersionUID = 1220L;	
    static Logger logger = LogManager.getLogger(DiscardAction.class);
    //
    DiscardItem item = null;
    String discardsTitle = " Most recent Discarded Items";
    List<DiscardItem> items = null;
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

    public DiscardItem getItem(){
	if(item == null){
	    item = new DiscardItem();
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
	return item;
    }
    public void setItem(DiscardItem val){
	if(val != null){
	    item = val;
	}
    }

    public String getDiscardsTitle(){
	return discardsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    // most recent

    public List<DiscardItem> getItems(){ 
	if(items == null){
	    DiscardItemList dl = new DiscardItemList();
	    String back = dl.find();
	    items = dl.getDiscards();
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
}





































