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

public class DeviceList extends CommonInc{

    static Logger logger = LogManager.getLogger(DeviceList.class);
    static final long serialVersionUID = 1200L;	
    String whichDate="d.installed", date_from="", date_to="";
    String id="", name="", external_id="";
    String serial_num="", model="", related_id="", 
	category_id="", installed="", 
	warranty_expire="",cap_replace_date="",location_id="",
	dept_id="", status="", asset_num="", domain_id="",
	division_id="", cost_from="", cost_to="",
	processor="", employee_id="", employee_name="", editable = "",
	exclude_id="", ip_address="", mac_address="", inventory_status="",
	year="", tag_code="";
    String limit ="30";
    boolean excludeVoided = false;
    //
    // we need this flag to help find the devices that can be auctioned
    // this could be disposed devices or warranty expired and not list in
    // any auction before
    //
    boolean forAuction = false;
    ArrayList<Device> devices = null;
    //
    public DeviceList(){
	super(Helper.debug);
    }
	
    public DeviceList(boolean val){
	super(val);
    }	

    public DeviceList(boolean val, String val2){
	super(val);
	setAsset_num(val2);
    }
    public String getId() {
	return id;
    }
    public String getExternal_id() {
	return external_id;
    }
    public String getRelated_id() {
	return related_id;
    }		
    public String getName() {
	return name;
    }
    public String getYear() {
	return year;
    }
    public String getTag_code() {
	return tag_code;
    }    
    public String getAsset_num() {
	return asset_num;
    }		
    public String getSerial_num() {
	return serial_num;
    }
    public String getDomain_id() {
	return domain_id;
    }		
    public String getModel() {
	return model;
    }
    public String getCategory_id() {
	return category_id;
    }
    public String getLocation_id() {
	return location_id;
    }
    public String getDept_id() {
	return dept_id;
    }
    public String getDivison_id() {
	return division_id;
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
    public String getCost_from() {
	return cost_from;
    }
    public String getCost_to() {
	return cost_to;
    }		
    public String getEmployee_id() {
	return employee_id;
    }
    public String getEmployee_name() {
	return employee_name;
    }		
    public String getWhichDate() {
	return whichDate;
    }
    public String getMac_address() {
	return mac_address;
    }
    public String getIp_address() {
	return ip_address;
    }		
    //
    public void setId(String id) {
	if(id != null)
	    this.id = id;
    }
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }		
    public void setExcludeDeviceId(String val) {
	if(val != null)
	    exclude_id = val;
    }	
    public void setForAuction() {
	forAuction = true;
    }	
    public void setEmployee_id(String val) {
	if(val != null)
	    employee_id = val;
    }
    public void setEmployee_name(String val) {
	if(val != null)
	    employee_name = val;
    }		
    public void setName(String val) {
	if(val != null)
	    name = val;
    }
    public void setTag_code(String val) {
	if(val != null)
	    tag_code = val;
    }    
    public void setYear(String val) {
	if(val != null && !val.equals("-1"))
	    year = val;
    }
    public void setInventory_status(String val) {
	if(val != null && !val.equals("-1"))
	    inventory_status = val;
    }		
    public void setAsset_num(String val) {
	if(val != null)
	    asset_num = val;
    }
    public void setSerial_num(String val) {
	if(val != null)
	    serial_num = val;
    }

    public void setModel(String val) {
	if(val != null)
	    model = val;
    }

    public void setCategory_id(String val) {
	if(val != null && !val.equals("-1"))
	    category_id = val;
    }
    public void setDomain_id(String val) {
	if(val != null && !val.equals("-1"))
	    domain_id = val;
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
    public void setCost_from(String val){
	if(val != null)
	    cost_from = val;
    }
    public void setCost_to(String val){
	if(val != null)
	    cost_to = val;
    }		
    public void setLocation_id(String val) {
	if(val != null && !val.equals("-1"))
	    location_id = val;
    }

    public void setDivision_id(String val) {
	if(val != null && !val.equals("-1"))
	    division_id = val;
    }
    public void setDept_id(String val) {
	if(val != null && !val.equals("-1"))
	    dept_id = val;
    }			
    public void setStatus(String val) {
	if(val != null && !val.equals("-1"))
	    status = val;
    }
    public void setMac_address(String val) {
	if(val != null)
	    mac_address = val;
    }
    public void setIp_address(String val) {
	if(val != null)
	    ip_address = val;
    }
    public void setRelated_id(String val) {
	if(val != null)
	    related_id = val;
    }		
    public void setLimit(String val){
	if(val != null)
	    limit = val;
    }
    public void setExcludeVoided(boolean val){
	if(val)
	    excludeVoided = true;
				
    }
    public boolean getExcludeVoided(){
	return excludeVoided;
    }
    public void setNoLimit(){
	limit = "";
    }
		
    public List<Device> getDevices(){
	return devices;
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select d.id,d.external_id,d.name,d.asset_num, d.serial_num, d.model,"+
	    "d.employee_id,d.description,d.category_id,"+
	    "date_format(d.installed,'%m/%d/%Y'),d.age_length,"+
	    " d.location_id,d.division_id,d.domain_id,"+
	    " d.status,d.processor,d.ram,d.hd_size,d.notes,"+
	    " d.mac_address,d.ip_address,d.editable,d.related_id,d.cost, "+
	    " date_format(d.inventory_date, '%m/%d/%Y'),d.replace_asset_num, "+
	    " d.voided,d.replaced_id,d.tag_code "+
	    " from devices d left join divisions v on d.division_id=v.id  ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(excludeVoided){
		qw += " d.voided is not null ";
	    }
	    if(!id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " d.id = ? ";
	    }
	    else if(!external_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " d.external_id = ? ";
	    }
	    else if(!asset_num.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " d.asset_num = ? ";
	    }														
	    else {
		if(!dept_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " v.dept_id = ? ";
		}
		if(!related_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.related_id = ? ";
		}								
		if(!name.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.name like ? ";
		}
		if(!serial_num.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.serial_num like ? ";
		}
		if(!model.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.model like ? ";
		}
		if(!employee_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.employee_id = ? ";
		}
		if(!category_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.category_id = ? ";
		}
		if(!location_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.location_id = ? ";
		}
		if(!division_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.division_id = ? ";
		}
		if(!status.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.status = ? ";
		}
		if(!tag_code.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.tag_code like ? ";
		}		
		if(!inventory_status.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    if(inventory_status.equals("set"))
			qw += " d.inventory_date is not null ";
		    else
			qw += " d.inventory_date is null ";												
		}
		if(!year.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " year("+whichDate+") = ? ";
		}
		else {
		    if(!date_from.equals("")){
			if(!qw.equals("")) qw += " and ";
			qw += whichDate+" >= str_to_date('"+date_from+"','%m/%d/%Y')";
		    }
		    if(!date_to.equals("")){
			if(!qw.equals("")) qw += " and ";
			qw += whichDate+" <= str_to_date('"+date_to+"','%m/%d/%Y')";
		    }
		}
		if(!exclude_id.equals("")){
		    if(!qw.equals("")) qw += " and ";	
		    qw += " not d.id = ? ";
		}
		if(!mac_address.equals("")){
		    if(!qw.equals("")) qw += " and ";	
		    qw += " d.mac_address like ? ";
		}
		if(!ip_address.equals("")){
		    if(!qw.equals("")) qw += " and ";	
		    qw += " d.ip_address like ? ";
		}
		if(!cost_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.cost >= ? ";	
		}
		if(!cost_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.cost <= ? ";		
		}								
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by d.id DESC ";
	    if(!limit.equals("")){
		qq += " limit "+limit;
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
		if(!dept_id.equals("")){
		    pstmt.setString(jj++, dept_id);
		}										
		if(!related_id.equals("")){
		    pstmt.setString(jj++, related_id);
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
		if(!employee_id.equals("")){
		    pstmt.setString(jj++, employee_id);
		}								
		if(!category_id.equals("")){
		    pstmt.setString(jj++, category_id);
		}
		if(!location_id.equals("")){
		    pstmt.setString(jj++, location_id);
		}
		if(!division_id.equals("")){
		    pstmt.setString(jj++, division_id);
		}
		if(!status.equals("")){
		    pstmt.setString(jj++, status);
		}
		if(!tag_code.equals("")){
		    pstmt.setString(jj++, tag_code+"%");
		}		
		if(!year.equals("")){
		    pstmt.setString(jj++, year);
		}								
		if(!exclude_id.equals("")){
		    pstmt.setString(jj++, exclude_id);
		}
		if(!mac_address.equals("")){
		    pstmt.setString(jj++, "%"+mac_address+"%");
		}
		if(!ip_address.equals("")){
		    pstmt.setString(jj++, "%"+ip_address+"%");
		}
		if(!cost_from.equals("")){
		    pstmt.setString(jj++, cost_from);
		}
		if(!cost_to.equals("")){
		    pstmt.setString(jj++, cost_to);
		}								
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(devices == null)
		    devices = new ArrayList<Device>();
		Device one = new Device(debug,
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
					rs.getString(18),
					rs.getString(19),
					rs.getString(20),
																				
					rs.getString(21),
					rs.getString(22),
					rs.getString(23),
					rs.getString(24),
					rs.getString(25),
																				
					rs.getString(26),
					rs.getString(27) != null,
					rs.getString(28),
					rs.getString(29)
					);
		devices.add(one);
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






















































