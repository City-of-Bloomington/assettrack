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

public class BarcodeSearchAction extends TopAction{

    static final long serialVersionUID = 1100L;	
    static Logger logger = LogManager.getLogger(BarcodeSearchAction.class);
    //
    Barcode barcode = null;
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
	    back = barcode.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		String str = "";
		if(barcode.foundDevice()){
		    Device one = barcode.getDevice();
		    str = url+"device.action?id="+one.getId();
		}
		else if(barcode.foundMonitor()){
		    Monitor one = barcode.getMonitor();
		    str = url+"monitor.action?id="+one.getId();
		}
		else if(barcode.foundPrinter()){
		    Printer one = barcode.getPrinter();
		    str = url+"printer.action?id="+one.getId();
		}
		else{
		    back = "No match found ";
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
	}
	else{		
	    getBarcode();
	}
	return ret;
    }
    public Barcode getBarcode(){ 
	if(barcode == null){
	    barcode = new Barcode(debug);
	}
	return barcode;
    }

    public void setBarcode(Barcode val){
	if(val != null){
	    barcode = val;
	}
    }

    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }

}





































