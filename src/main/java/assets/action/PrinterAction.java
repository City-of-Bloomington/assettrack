package assets.action;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
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

public class PrinterAction extends TopAction{

    static final long serialVersionUID = 1550L;	
    static Logger logger = LogManager.getLogger(PrinterAction.class);
    String device_id="";
    Printer printer = null;
    List<Printer> printers = null;
    String printersTitle = " Most recent Printers";		
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
	    back = printer.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = printer.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = printer.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Save Partial")){ 
	    back = printer.doPartialUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	    ret = "partial";
	}				
	else if(action.equals("Delete")){ 
	    back = printer.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
	    }
	}
	else if(action.equals("Edit")){
	    printer = new Printer(debug, id);
	    back = printer.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    if(printer.canBePartialUpdated()){
		ret = "partial";
	    }
	}				
	else if(!id.equals("")){ 
	    printer = new Printer(debug, id);
	    back = printer.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    ret = "view";
	}
	else{
	    getPrinter();
	}
	return ret;
    }
    public Printer getPrinter(){ 
	if(printer == null){
	    if(!id.equals("")){
		printer = new Printer(debug, id);
	    }
	    else{
		printer = new Printer();
		if(!device_id.equals("")){
		    printer.setDevice_id(device_id);
		}
	    }
	}		
	return printer;
    }

    public void setPrinter(Printer val){
	if(val != null)
	    printer = val;
    }

    public String getPrintersTitle(){
	return printersTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && printer != null){
	    id = printer.getId();
	}
	return id;
    }
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    // most recent
    public List<Printer> getPrinters(){ 
	if(printers == null){
	    PrinterList dl = new PrinterList();
	    String back = dl.find();
	    printers = dl.getPrinters();
	}		
	return printers;
    }

}





































