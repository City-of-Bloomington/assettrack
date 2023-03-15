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

public class ReportAction extends TopAction{

    static final long serialVersionUID = 1595L;	
    static Logger logger = LogManager.getLogger(ReportAction.class);
    //
    DeviceList deviceList = null;
    List<Device> devices = null;
    List<Type> categories = null;
    List<Type> locations = null;
    List<Type> domains = null;
    List<Dept> depts = null;
    List<Division> divisions = null;
    String devicesTitle = " Expected Device for Cap R";		
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
	    getDeviceList();
	    deviceList.setNoLimit();
	    back = deviceList.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		devices = deviceList.getDevices();
		if(devices != null && devices.size() > 0){
		    if(action.equals("Submit")){
			devicesTitle = "Found "+devices.size()+" records";
		    }
		    else{
			ret="csv";
		    }
		}
		else{
		    addActionMessage("No match found");
		    devicesTitle = "No match found";
		}
	    }
	}				
	return ret;
    }
    public DeviceList getDeviceList(){ 
	if(deviceList == null){
	    deviceList = new DeviceList(debug);
	}		
	return deviceList;
    }
    public List<Type> getCategories(){
	if(categories == null){
	    TypeList tl = new TypeList(debug, null,"categories");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> types = tl.getTypes();
		if(types != null && types.size() > 0){
		    categories = types;
		}
	    }
	}
	return categories;
    }
    public List<Type> getLocations(){
	if(locations == null){
	    TypeList tl = new TypeList(debug, null,"locations");
	    tl.setJoin(" devices d");
	    tl.setCondition("d.location_id=t.id");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> types = tl.getTypes();
		if(types != null && types.size() > 0){
		    locations = types;
		}
	    }
	}
	return locations;
    }
    public List<Dept> getDepts(){
	if(depts == null){
	    DeptList tl = new DeptList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Dept> ones = tl.getDepts();
		if(ones != null && ones.size() > 0){
		    depts = ones;
		}
	    }
	}
	return depts;
    }		

    public void setDeviceList(DeviceList val){
	if(val != null)
	    deviceList = val;
    }

    public String getDevicesTitle(){
	return devicesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		

    // most recent
    public List<Device> getDevices(){
	return devices;
    }
    public List<Division> getDivisions(){
	if(divisions == null){
	    DivisionList tl = new DivisionList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Division> ones = tl.getDivisions();
		if(ones != null && ones.size() > 0){
		    divisions = ones;
		}
	    }
	}
	return divisions;
    }
    public List<Type> getYears(){
	int yy = Helper.getCurrentYear();
	List<Type> years = new ArrayList<Type>();
	years.add(new Type(debug, "-1","All"));
	for(int i=5;i > -1;i--){
	    String str = ""+(yy-i);
	    Type  one = new Type(debug, str, str);
	    years.add(one);
	}
	return years;
    }
    public List<Type> getDomains(){
	if(domains == null){
	    TypeList tl = new TypeList(debug, null,"domains");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> types = tl.getTypes();
		if(types != null && types.size() > 0){
		    domains = types;
		}
	    }
	}
	return domains;
    }		
}





































