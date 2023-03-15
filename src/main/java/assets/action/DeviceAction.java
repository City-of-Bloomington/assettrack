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

public class DeviceAction extends TopAction{

    static final long serialVersionUID = 1170L;	
    static Logger logger = LogManager.getLogger(DeviceAction.class);
    //
    Device device = null;
    List<Device> devices = null;
    List<Type> categories = null;
    List<Type> locations = null;
    List<Type> domains = null;
    List<Dept> depts = null;
    List<Division> divisions = null;
    String devicesTitle = " Most recent Devices";
    String related_id = "";
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
	    device.setUser_id(user.getId());
	    back = device.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = device.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){
	    device.setUser_id(user.getId());						
	    back = device.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Save Partial")){
	    device.setUser_id(user.getId());						
	    back = device.doPartialUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	    ret = "partial";
	}
	else if(action.endsWith("Duplicate")){
	    device.setUser_id(user.getId());						
	    back = device.changeStatusToDuplicate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}	
	else if(action.equals("Delete")){ 
	    back = device.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		addActionMessage("Deleted Successfully");								
		ret = "search";
	    }
	}
	else if(action.equals("Edit")){ 
	    device = new Device(debug, id);
	    back = device.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    if(device.canBePartialUpdated()){
		ret = "partial";
	    }
	}				
	else if(action.equals("Refresh")){
	    // nothing
	}				
	else if(!id.equals("")){
	    ret = "view";
	    device = new Device(debug, id);
	    back = device.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else{
	    getDevice();
	}
	return ret;
    }
    public Device getDevice(){ 
	if(device == null){
	    if(!id.equals("")){
		device = new Device(debug, id);
	    }
	    else{
		device = new Device();
		if(!related_id.equals("")){
		    device.setRelated_id(related_id);
		}
	    }
	}		
	return device;
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

    public void setDevice(Device val){
	if(val != null)
	    device = val;
    }

    public String getDevicesTitle(){
	return devicesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && device != null){
	    id = device.getId();
	}
	return id;
    }
    public String getRelated_id(){
	return related_id;
    }
    public void setRelated_id(String val){
	if(val != null && !val.equals(""))		
	    related_id = val;
    }				
    // most recent
    public List<Device> getDevices(){ 
	if(devices == null){
	    DeviceList dl = new DeviceList();
	    dl.setStatus("Active");
	    String back = dl.find();
	    devices = dl.getDevices();
	}		
	return devices;
    }

}





































