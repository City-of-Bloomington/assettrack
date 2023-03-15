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

public class PrinterSearchAction extends TopAction{

    static final long serialVersionUID = 1570L;	
    static Logger logger = LogManager.getLogger(PrinterSearchAction.class);
    //
    List<Printer> printers = null;
    PrinterList printerList = null;
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
	if(!action.equals("")){
	    getPrinterList();
	    printerList.setNoLimit();
	    back = printerList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		printers = printerList.getPrinters();
		if(printers != null && printers.size() > 0){
		    if(printers.size() == 1){
			Printer one = printers.get(0);
			try{
			    HttpServletResponse res = ServletActionContext.getResponse();
			    String str = url+"printer.action?id="+one.getId();
			    res.sendRedirect(str);
			    return super.execute();
			}catch(Exception ex){
			    System.err.println(ex);
			}													
		    }										
		    printersTitle = "Found "+printers.size()+" records";
		}
		else{
		    addActionMessage("No match found");
		    printersTitle = "No match found";
		}
	    }
	}				
	else{
	    getPrinterList();
	    PrinterList ml = new PrinterList(debug);
	    back = ml.find();
	    printers = ml.getPrinters();
	}
	return ret;
    }
    public PrinterList getPrinterList(){
	if(printerList == null){
	    printerList = new PrinterList(debug); 
	}
	return printerList;
    }

    public String getPrintersTitle(){
	return printersTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    // most recent
    public List<Printer> getPrinters(){ 
	return printers;
    }

}





































