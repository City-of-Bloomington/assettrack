package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.io.*;
import java.sql.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Barcode extends CommonInc implements java.io.Serializable{

    String asset_num="";
    static final long serialVersionUID = 1090L;
		
    static Logger logger = LogManager.getLogger(Barcode.class);
    Device device = null;
    Monitor monitor = null;
    Printer printer = null;
    //
    public Barcode(){
	super();
    }
    public Barcode(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public String getAsset_num(){
	return asset_num;
    }
    public void setAsset_num(String val){
	if(val != null)
	    asset_num = val;
    }
    //
    public boolean foundDevice(){
	return device != null;
    }
    public boolean foundMonitor(){
	return monitor != null;
    }
    public boolean foundPrinter(){
	return printer != null;
    }
    public Device getDevice(){
	return device;
    }
    public Monitor getMonitor(){
	return monitor;
    }
    public Printer getPrinter(){
	return printer;
    }
    //
    public String find(){
	String back = "";
	if(asset_num.equals("")){
	    back = "Barcode number not set ";
	    return back;
	}
	DeviceList dl = new DeviceList(debug, asset_num);
	back = dl.find();
	if(back.equals("")){
	    List<Device> ones = dl.getDevices();
	    if(ones != null && ones.size() > 0){
		device = ones.get(0);
		return back;
	    }
	}
	MonitorList ml = new MonitorList(debug, null, asset_num);
	back = ml.find();
	if(back.equals("")){
	    List<Monitor> ones = ml.getMonitors();
	    if(ones != null && ones.size() > 0){
		monitor = ones.get(0);
		return back;
	    }
	}
	PrinterList pl = new PrinterList(debug, null, asset_num);
	back = pl.find();
	if(back.equals("")){
	    List<Printer> ones = pl.getPrinters();
	    if(ones != null && ones.size() > 0){
		printer = ones.get(0);
		return back;
	    }
	}				
	back = "No match found ";
	return back;
    }

}
