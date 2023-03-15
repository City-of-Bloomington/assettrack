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


public class LotOptAction extends TopAction{

    static final long serialVersionUID = 1027L;	
    static Logger logger = LogManager.getLogger(LotOptAction.class);
    //
    LotOptions lotOpt = null;
    String id = "",type="";
    String lotsTitle = "most recent options";
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
	    back = lotOpt.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = lotOpt.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(action.startsWith("Save")){
	    back = lotOpt.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = lotOpt.getId();
		addActionMessage("Saved Successfully");
	    }
	}
	else if(!id.equals("") || !type.equals("")){
	    getLotOpt();
	    back = lotOpt.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}				
	else{
	    getLotOpt();
	}
	return ret;
    }
    public LotOptions getLotOpt(){
	if(lotOpt == null){
	    lotOpt = new LotOptions();
	}
	if(!id.equals("")){
	    lotOpt.setId(id);
	}
	else if(!type.equals("")){
	    lotOpt.setType(type);
	}
	return lotOpt;

    }
    public void setLotOpt(LotOptions val){
	if(val != null){
	    lotOpt = val;
	}
    }
    public void setType(String val){
	if(val != null){
	    type = val;
	}
    }
    public String getLotsTitle(){
	return lotsTitle;
    }
    public String getType(){
	if(type.equals("") && lotOpt != null){
	    type = lotOpt.getType();
	}
	return type;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		


}





































