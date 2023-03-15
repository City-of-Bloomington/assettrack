package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

/**
 * 
 * @version %I%, %G%
 */

public class MonitorList extends CommonInc{

    static Logger logger = LogManager.getLogger(MonitorList.class);
    static final long serialVersionUID = 1510L;	
    String whichDate="received", date_from="", date_to="";
    String id="", name="", external_id="";
    String serial_num="", model="", device_id="",
	type="", received="", manufacturer="",
	status="", asset_num="", 
	editable = "", inventory_status="",
	exclude_id="", limit = " limit 30 ";
    //
    // we need this flag to help find the devices that can be auctioned
    // this could be disposed devices or warranty expired and not list in
    // any auction before
    //
    boolean forAuction = false;
    ArrayList<Monitor> monitors = null;
    //
    public MonitorList(){
	super(Helper.debug);
    }
	
    public MonitorList(boolean val){
	super(val);
    }	
    public MonitorList(boolean val, String val2){
	super(val);
	setDevice_id(val2);
    }
    public MonitorList(boolean val, String val2, String val3){
	super(val);
	setDevice_id(val2);
	setAsset_num(val3);
    }
		
    public String getId() {
	return id;
    }

    public String getDevice_id() {
	return device_id;
    }
    public String getName() {
	return name;
    }
    public String getAsset_num() {
	return asset_num;
    }		
    public String getSerial_num() {
	return serial_num;
    }
    public String getModel() {
	return model;
    }
    public String getStatus() {
	if(status.equals("")){
	    return "-1";
	}
	return status;
    }
    public String getInventory_status() {
	if(status.equals("")){
	    return "-1";
	}
	return inventory_status;
    }		
    public String getDate_from() {
	return date_from;
    }
    public String getDate_to() {
	return date_to;
    }
    public String getWhichDate() {
	return whichDate;
    }
    public String getManufacturer(){
	return manufacturer;
    }
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }				
    //
    public void setId(String val) {
	if(val != null)
	    id = val;
    }
    public void setAsset_num(String val) {
	if(val != null)
	    asset_num = val;
    }		
    public void setDevice_id(String val) {
	if(val != null)
	    device_id = val;
    }
    public void setManufacturer(String val) {
	if(val != null)
	    manufacturer = val;
    }		
    public void setExcludeMonitorId(String val) {
	if(val != null)
	    exclude_id = val;
    }	
    public void setForAuction() {
	forAuction = true;
    }	

    public void setName(String val) {
	if(val != null)
	    name = val;
    }

    public void setSerial_num(String val) {
	if(val != null)
	    serial_num = val;
    }

    public void setModel(String val) {
	if(val != null)
	    model = val;
    }

    public void setWhichDate(String val){
	if(val != null)
	    whichDate = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }

    public void setType(String val) {
	if(val != null)
	    type = val;
    }	
    public void setStatus(String val) {
	if(val != null && !val.equals("-1"))
	    status = val;
    }
    public void setInventory_status(String val){
	if(val != null && !val.equals("-1"))
	    inventory_status = val;
    }
    public List<Monitor> getMonitors(){
	return monitors;
    }
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,external_id,device_id,name,asset_num,"+
	    " serial_num,screen_size,model,type,"+
	    "vertical_resolution,horizontal_resolution,"+
	    "manufacturer,"+
	    "date_format(received,'%m/%d/%Y'),"+
	    "expected_age,status,notes,editable, "+
	    "date_format(inventory_date,'%m/%d/%Y') "+		
	    " from monitors ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(!id.equals("")){
		qw += " id = ? ";
	    }
	    else if(!external_id.equals("")){
		qw += " external_id = ? ";
	    }
	    else if(!asset_num.equals("")){
		qw += " asset_num = ? ";
	    }								
	    else {
		if(!device_id.equals("")){
		    qw += " device_id = ? ";
		}
		if(!name.equals("")){
		    qw += " name like ? ";
		}	
		if(!serial_num.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " serial_num like ? ";
		}
		if(!model.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " model like ? ";
		}
		if(!type.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " type like ? ";
		}
		if(!status.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " status = ? ";
		}
		if(!manufacturer.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " manufacturer like ? ";
		}								
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += whichDate+" >= str_to_date('"+date_from+"','%m/%d/%Y')";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += whichDate+" <= str_to_date('"+date_to+"','%m/%d/%Y')";
		}
		if(!exclude_id.equals("")){
		    if(!qw.equals("")) qw += " and ";	
		    qw += " not id = ? ";
		}
		if(!inventory_status.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    if(inventory_status.equals("set"))
			qw += " inventory_date is not null ";
		    else
			qw += " inventory_date is null ";												
		}								
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by name ";
	    if(!limit.equals("")){
		qq += limit;
	    }
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!id.equals("")){
		pstmt.setString(jj++, id);
	    }
	    else if(!external_id.equals("")){
		pstmt.setString(jj++, external_id);
	    }
	    else if(!asset_num.equals("")){
		pstmt.setString(jj++, asset_num);
	    }								
	    else{
		if(!device_id.equals("")){
		    pstmt.setString(jj++, device_id);
		}
		if(!name.equals("")){
		    pstmt.setString(jj++, "%"+name+"%");
		}					
		if(!serial_num.equals("")){
		    pstmt.setString(jj++, "%"+serial_num+"%");
		}
		if(!model.equals("")){
		    pstmt.setString(jj++, "%"+model+"%");
		}
		if(!type.equals("")){
		    pstmt.setString(jj++, "%"+type+"%");
		}								
		if(!status.equals("")){
		    pstmt.setString(jj++, status);
		}
		if(!manufacturer.equals("")){
		    pstmt.setString(jj++, "%"+manufacturer+"%");
		}													
		if(!exclude_id.equals("")){
		    pstmt.setString(jj++, exclude_id);
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(monitors == null)
		    monitors = new ArrayList<Monitor>();
		Monitor one = new Monitor(debug,
					  rs.getString(1),
					  rs.getString(2),
					  rs.getString(3),
					  rs.getString(4),
					  rs.getString(5),
					  rs.getString(6),
					  rs.getString(7),
					  rs.getString(8),
					  rs.getString(9),
					  rs.getString(10),
					  rs.getString(11),
					  rs.getString(12),
					  rs.getString(13),
					  rs.getString(14),
					  rs.getString(15),
					  rs.getString(16),
					  rs.getString(17),
					  rs.getString(18)
					  );
		monitors.add(one);
	    }
	    if(monitors == null || monitors.size() == 0){
		message = " No match found ";
	    }
	}
	catch(Exception ex){
	    back += ex+" : "+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }

}






















































