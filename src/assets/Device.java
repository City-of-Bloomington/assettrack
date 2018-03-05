package assets;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class Device extends CommonInc{

		static Logger logger = Logger.getLogger(Device.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		/*
		// in database
		static final String[] STATUSES = {"Active","Auctioned","Donated","Recycled","Disposed"};
		static final int[] STATUS_ORDER = { 1,         2,        2,           2,          2};
		*/
		static final long serialVersionUID = 1160L;
		//
		// spiceworks scanned devices are not editable only manully entered ones
		// such as monitors, scanners, etc
		//
		String id="", external_id="", editable="", name="", asset_num="";
		String serial_num="", model="", description="",
				service_tag="", express_service_tag="", category_id="", installed="",
				warranty_expire="",location_id="", processor="", ram="", hd_size="",
				division_id="",domain_id="",status="Active", notes="", age_length="3",
				mac_address="",ip_address="", cost="", inventory_date="", replace_asset_num="", voided="";
		String old_replace_asset_num=""; 
		String user_id="", employee_id="", related_id="";
		//
		Type dept = null;
		Division division = null;
		Type domain = null;
		Type location = null;
		Type category = null;
		List<DeviceHistory> history = null;
		User user = null;
		Employee employee = null;

		List<SoftwareInstallation> installations = null;
		List<Monitor> monitors = null;
		List<Printer> printers = null;
		List<Device> relatedDevices = null;
		boolean locationFlag = false; // needed for updated from external apps
		public Device() {
		}
		public Device(boolean deb, String val) {
				debug = deb;
				setId(val);
		}
	
		public Device(boolean deb,
									String _id,
									String _external_id,
									String _name,
									String _asset_num,
									String _serial_num,
									
									String _model,
									String _employee_id,
									String _description,
									String _category_id,
									String _installed,
									
									String _age_length,
									String _location_id,
									String _division_id,
									String _domain_id,
									String _status,
									
									String _processor,
									String _ram,
									String _hd_size,
									String _notes,
									String _mac_address,
									
									String _ip_address,
									String _editable,
									String _related_id,
									String _cost,
									String _inventor_date,
									
									String _replace_asset_num,
									boolean _voided
									) {
				debug = deb;
				setId(_id);
				setExternal_id(_external_id);
				setName(_name);
				setAsset_num(_asset_num);
				setSerial_num(_serial_num);
				
				setModel(_model);
				setEmployee_id(_employee_id);
				setDescription(_description);
				setCategory_id(_category_id);
				setInstalled(_installed);
				
				setAge_length(_age_length);
				setLocation_id(_location_id);
				setDivision_id(_division_id);
				setDomain_id(_domain_id);
				setStatus(_status);
				
				setProcessor(_processor);
				setRam(_ram);
				setHd_size(_hd_size);
				setNotes(_notes);
				setMac_address(_mac_address);
				
				setIp_address(_ip_address);
				setEditable(_editable);
				setRelated_id(_related_id);
				setCost(_cost);
				setInventory_date(_inventor_date);
				
				setReplace_asset_num(_replace_asset_num);
				// we need this to compare if new one is set
				setOld_replace_asset_num(_replace_asset_num);
				setVoided(_voided);
		}	
	
		public String getId() {
				return id;
		}
		public String getIds(){
				String ret = id;
				if(!external_id.equals("")){
						ret += " ("+external_id+")";
				}
				return ret;
		}
		public String getRelated_id() {
				return related_id;
		}		
		public String getEditable() {
				return editable;
		}
		public String getName() {
				return name;
		}
		public boolean getVoided() {
				return !voided.equals("");
		}		
		public String getSerial_num() {
				return serial_num;
		}
		public String getModel() {
				return model;
		}
		public String getAsset_num() {
				return asset_num;
		}		
		public String getEmployee_id() {
				return employee_id;
		}
		public String getCategory_id() {
				return category_id;
		}
		public String getInstalled() {
				return installed;
		}
		public String getdescription() {
				return description;
		}
		public String getAge_length() {
				return age_length;
		}
		public String getLocation_id() {
				return location_id;
		}
		public String getDivision_id() {
				return division_id;
		}
		public String getStatus() {
				return status;
		}
		public String getProcessor() {
				return processor;
		}
		public String getRam() {
				return ram;
		}
		public String getHd_size() {
				return hd_size;
		}		
		public String getNotes() {
				return notes;
		}
		public boolean isEditable(){
				return !editable.equals("");
		}
		public String getMac_address() {
				return mac_address;
		}
		public String getIp_address() {
				return ip_address;
		}
		public String getDomain_id() {
				return domain_id;
		}
		public String getDescription() {
				return description;
		}
		
		public String getExternal_id() {
				return external_id;
		}		
		public void setExternal_id(String val) {
				if(val != null)
						external_id = val;
		}
		public String getCost() {
				return cost;
		}
		public String getInventory_date() {
				return inventory_date;
		}
		public String getReplace_asset_num() {
				return replace_asset_num;
		}
		
		//
		// setters
		//
		public void setId(String val) {
				if(val != null)
						id = val;
		}
		public void setRelated_id(String val) {
				if(val != null)
						related_id = val;
		}		
		public void setEditable(String val) {
				if(val != null)
						editable = val;
		}
		
		public void setName(String val) {
				if(val != null)
						name = val;
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

		public void setDescription(String val) {
				if(val != null)
						description = val;
		}

		public void setEmployee_id(String val) {
				if(val != null)
						employee_id = val;
		}
		public void setEmployee_name(String val) {
				// ignore, needed for auto_complete
		}		

		public void setCategory_id(String val) {
				if(val != null && !val.equals("-1"))						
						category_id = val;
		}

		public void setAge_length(String val) {
				if(val != null)
						age_length = val;
		}

		public void setInstalled(String val) {
				if(val != null)
						installed = val;
		}

		public void setLocation_id(String val) {
				if(val != null && !val.equals("-1"))
						location_id = val;
		}

		public void setDivision_id(String val) {
				if(val != null && !val.equals("-1"))						
						division_id = val;
		}
		public void setDomain_id(String val) {
				if(val != null && !val.equals("-1"))
						domain_id = val;
		}
		public void setUser_id(String val) {
				if(val != null){
						user_id = val;
				}
		}
		
		public void setUser(User val) {
				if(val != null){
						user = val;
						user_id = user.getId();
				}
		}		
		public void setStatus(String val) {
				if(val != null)
						status = val;
		}
		public void setProcessor(String val) {
				if(val != null){
						processor = val;
				}
		}
		public void setRam(String val) {
				if(val != null){
						ram = val;
				}
		}
		public void setHd_size(String val) {
				if(val != null){
						hd_size = val;
				}
		}
		public void setNotes(String val) {
				if(val != null){
						notes = val;
				}
		}
		public void setMac_address(String val) {
				if(val != null){
						mac_address = val;
				}
		}
		public void setIp_address(String val) {
				if(val != null){
						ip_address = val;
				}
		}
		public void setCost(String val) {
				if(val != null){
						cost = val;
				}
		}
		public void setVoided(boolean val) {
				if(val){
						voided = "y";
				}
		}		
		public void setInventory_date(String val) {
				if(val != null){
						inventory_date = val;
				}
		}
		public void setReplace_asset_num(String val) {
				if(val != null){
						replace_asset_num = val;
				}
		}
		public void setOld_replace_asset_num(String val) {
				if(val != null){
						old_replace_asset_num = val;
				}
		}		
		public void setLocationFlag(boolean val){
				locationFlag = val;
		}
		public boolean anyUpdate(){
				return status.equals("Active");
		}
		// user entered 
		public boolean canBeUpdated(){
				return !editable.equals("") && status.equals("Active");
		}
		// 
		public boolean canBePartialUpdated(){
				return editable.equals("") && status.equals("Active");
		}
		public boolean hasRelated(){
				return !related_id.equals("");
		}
		//
		public String toString(){
				String ret = asset_num;
				if(ret.equals("") && !name.equals("")){
						ret = name;
				}
				return ret;
		}
		public Type getDomain(){
				if(domain == null && !domain_id.equals("")){
						Type sp = new Type(debug, domain_id, null, false,"domains");
						String back = sp.doSelect();
						if(back.equals("")){
								domain = sp;
						}
				}
				return domain;
		}
		public Division getDivision(){
				if(division == null && !division_id.equals("")){
						Division sp = new Division(debug, division_id);
						String back = sp.doSelect();
						if(back.equals("")){
								division = sp;
						}
				}
				return division;
		}
		public Type getDept(){
				if(dept == null){
						getDivision();
						if(division != null){
								dept = division.getDept();
						}
				}
				return dept;
		}
		public User getUser(){
				if(user == null && !user_id.equals("")){
						User sp = new User(debug, user_id);
						String back = sp.doSelect();
						if(back.equals("")){
								user = sp;
						}
				}
				return user;
		}
		public Employee getEmployee(){
				if(employee == null && !employee_id.equals("")){
						Employee sp = new Employee(debug, employee_id);
						String back = sp.doSelect();
						if(back.equals("")){
								employee = sp;
						}
				}
				return employee;
		}				
		public Type getCategory(){
				if(category == null && !category_id.equals("")){
						Type sp = new Type(debug, category_id);
						String back = sp.doSelect();
						if(back.equals("")){
								category = sp;
						}
				}
				return category;
		}
		public Type getLocation(){
				if(location == null && !location_id.equals("")){
						Type sp = new Type(debug, location_id, null, false, "locations");
						String back = sp.doSelect();
						if(back.equals("")){
								location = sp;
						}
				}
				return location;
		}
		public List<Monitor> getMonitors(){
				if(monitors == null && !id.equals("")){
						MonitorList sp = new MonitorList(debug, id);
						String back = sp.find();
						if(back.equals("")){
								monitors = sp.getMonitors();
						}
				}
				return monitors;
		}
		public List<Printer> getPrinters(){
				if(printers == null && !id.equals("")){
						PrinterList sp = new PrinterList(debug, id);
						String back = sp.find();
						if(back.equals("")){
								printers = sp.getPrinters();
						}
				}
				return printers;
		}
		public List<Device> getRelatedDevices(){
				if(relatedDevices == null && !id.equals("")){
						DeviceList sp = new DeviceList(debug);
						sp.setRelated_id(id);
						String back = sp.find();
						if(back.equals("")){
								List<Device> ones = sp.getDevices();
								if(ones != null && ones.size() > 0){
										relatedDevices = ones;
								}
						}
				}
				return relatedDevices;
		}
		public boolean hasRelatedDevices(){
				getRelatedDevices();
				return relatedDevices != null;
		}
		public boolean hasMonitors(){
				getMonitors();
				return monitors != null && monitors.size() > 0;
		}
		public boolean hasPrinters(){
				getPrinters();
				return printers != null && printers.size() > 0;
		}				
		public List<DeviceHistory> getHistory(){
				if(history == null && !id.equals("")){
						DeviceHistoryList otl = new DeviceHistoryList(debug, id);// device id
						String back = otl.find();
						if(back.equals("")){
								history = otl.getDeviceHistory();
						}
						else{
								addError(back);
						}
				}
				return history;
		}
		//
		public boolean hasInstallations(){
				getInstallations();
				return installations != null && installations.size() > 0;
		}
		//
		public List<SoftwareInstallation> getInstallations(){
				if(installations == null && !id.equals("")){
						SoftwareInstallationList sil = new SoftwareInstallationList(debug, id,null,null);
						String back = sil.find();
						if(back.equals("")){
								installations = sil.getInstallations();
						}
				}
				return installations;
		}
		public String saveImport(PreparedStatement pstmt){
				String msg = "";
				try{
						int jj=1;
						pstmt.setString(jj++, external_id);  // 1
						if(name.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, name);  // 2
						if(serial_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,serial_num); // 3
						if(model.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,model); // 4
						//
						if(employee_id.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,employee_id); // 5
						if(description.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,description); // 6
						if(category_id.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,category_id); //7 
						// 
						if(installed.equals(""))
								pstmt.setNull(jj++,Types.DATE);
						else
								pstmt.setString(jj++, installed);	// // 8
						if(location_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,location_id); // 9
						if(domain_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,domain_id); // 10
						
						if(processor.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,processor);
						if(ram.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,ram);
						if(mac_address.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,mac_address);
						if(ip_address.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++, ip_address);						
						pstmt.executeUpdate();
				}catch(Exception ex){
						msg += "save "+external_id+" "+ex;
						logger.error(msg);
				}
				return msg;
		}
		public String updateImport(PreparedStatement pstmt){
				String msg = "";
				try{
						int jj=1;
						if(external_id.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else
								pstmt.setString(jj++, external_id);
						if(serial_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,serial_num);
						if(model.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,model);
						//
						if(employee_id.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,employee_id);
						if(description.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,description);
						if(category_id.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,category_id);
						if(locationFlag){
								pstmt.setString(jj++, location_id);
						}
						if(domain_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,domain_id);
						if(processor.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,processor);
						if(ram.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,ram);
						if(mac_address.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else{
								pstmt.setString(jj++,mac_address);
						}
						if(ip_address.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,ip_address);
						pstmt.setString(jj++, id); 
						pstmt.executeUpdate();
				}catch(Exception ex){
						msg += " update "+id+" "+ex;
						logger.error(msg);
				}
				return msg;
		}		
		
		public String doSave(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "insert into devices values(0,?,"+
						"?,?,?,?,?,"+
						"?,?,?,?,?,"+
						"?,?,?,?,?,"+			
						"?,?,?,?,'y',"+
						"?,?,?,?,null)"; // inventory_date, replace #
				editable = "y";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				else{
						try{
								pstmt = con.prepareStatement(qq);
								if(debug){
										logger.debug(qq);
								}
								back = fillPStatement(pstmt);
								if(back.equals("")){
										pstmt.executeUpdate();
										//
										// get the id of the new record
										//
										qq = "select LAST_INSERT_ID() ";
										if(debug){
												logger.debug(qq);
										}
										pstmt = con.prepareStatement(qq);				
										rs = pstmt.executeQuery();
										if(rs.next()){
												id = rs.getString(1);
										}
										DeviceHistory ih = new DeviceHistory(debug, null, id, status,null, user_id);
										back += ih.doSave();
								}
						}
						catch(Exception ex){
								back += ex;
								logger.error(ex);
								addError(back);
						}
						finally{
								Helper.databaseDisconnect(con, pstmt, rs);
						}
						if(back.equals("") && !replace_asset_num.equals("")){
								back = updateRelatedDevices();
						}
				}
				return back;

		}
		String fillPStatement(PreparedStatement pstmt){
				String back = "";
				int jj=1;
				try{
						if(external_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++, external_id);						
						if(name.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,name);
						if(asset_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,asset_num);						
						if(serial_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,serial_num);
						if(model.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,model);
						if(employee_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,employee_id);
						if(description.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,description);
						if(category_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,category_id);
						if(installed.equals(""))
								pstmt.setNull(jj++,Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(installed).getTime()));						
						pstmt.setString(jj++,""+age_length);
						if(location_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,location_id);
						if(division_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,division_id);	
			
						if(domain_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,domain_id);
						if(id.equals("")){
								status = "Active";
						}
						if(status.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,status);
						if(processor.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,processor);
						if(ram.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,ram);
						if(hd_size.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,hd_size);
						if(notes.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,notes);
						if(mac_address.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,mac_address);
						if(ip_address.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,ip_address);						
						editable = "y";
						if(related_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,related_id);
						if(cost.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,cost);
						if(inventory_date.equals(""))
								pstmt.setNull(jj++,Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(inventory_date).getTime()));
						if(replace_asset_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,replace_asset_num);
						if(voided.equals(""))
								pstmt.setNull(jj++,Types.CHAR);
						else
								pstmt.setString(jj++,"y");						
				}
				catch(Exception ex){
						logger.error(ex);
						back += ex;
				}
				return back;
		}
		private String updateRelatedDevices(){
				String back = "";
				//
				if(id.equals("") && replace_asset_num.equals("")) return back;
				// if this is done before, no need to do it again
				if(replace_asset_num.equals(old_replace_asset_num)) return back;
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String old_id="";
				//
				// first find the device id
				//
				String qs = " select id from devices where asset_num=? ";
				String qq = " update devices set related_id=? where related_id=? ";
				//
				// these will be updated through the import update, but we are
				// doing this anyway here as well
				String qq2 = " update monitors set device_id=? where device_id=? ";
				String qq3 = " update printers set device_id=? where device_id=? ";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				
				try{
						if(debug){
								logger.debug(qs);
						}						
						pstmt = con.prepareStatement(qs);
						pstmt.setString(1, replace_asset_num);
						rs = pstmt.executeQuery();
						if(rs.next()){
								old_id = rs.getString(1);
						}
						if(old_id.equals("")){
								back=" device id not found for asset num: "+replace_asset_num;
								return back;
						}
						if(debug){
								logger.debug(qq);
						}						
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						pstmt.setString(2, old_id);
						pstmt.executeUpdate();
						if(debug){
								logger.debug(qq2);
						}
						pstmt = con.prepareStatement(qq2);
						pstmt.setString(1, id);
						pstmt.setString(2, old_id);
						pstmt.executeUpdate();
						if(debug){
								logger.debug(qq3);
						}
						pstmt = con.prepareStatement(qq3);
						pstmt.setString(1, id);
						pstmt.setString(2, old_id);
						pstmt.executeUpdate();						
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;						

		}
		//
		public String doUpdate(){
		
				String back = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String str="";
				String qq = "";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "update devices set external_id=?,"+
								"name=?,asset_num=?,serial_num=?,model=?,employee_id=?,"+
								"description=?,"+
								"category_id=?,installed=?,age_length=?, "+
								"location_id=?,division_id=?,domain_id=?,status=?,processor=?, "+
								"ram=?,hd_size=?,notes=?,mac_address=?,ip_address=?,"+
								"related_id=?,cost=?,inventory_date=?,replace_asset_num=?, "+
								"voided=? "+
								"where id=?";
						
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						back = fillPStatement(pstmt);
						if(back.equals("")){
								pstmt.setString(26,id); // 24 - 1 (editable)
								pstmt.executeUpdate();
						}
						DeviceHistory ih = new DeviceHistory(debug, null, id, status, null,user_id);
						back = ih.doSave();												
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				if(back.equals("") && !replace_asset_num.equals("")){
						back = updateRelatedDevices();
				}				
				return back;

		}
		public String doPartialUpdate(){
		
				String back = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String str="";
				String qq = "";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "update devices set "+
								"asset_num=?, age_length=?, "+
								"division_id=?, notes=?, location_id=?, related_id=?, "+
								"inventory_date=?, replace_asset_num=?,voided=? "+
								"where id=?";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(asset_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,asset_num);						
						pstmt.setString(jj++,""+age_length);
						if(division_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,division_id);	
						if(notes.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,notes);
						if(location_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++, location_id);								
						if(related_id.equals(""))
								pstmt.setNull(jj++,Types.INTEGER);
						else
								pstmt.setString(jj++,related_id);
						if(inventory_date.equals(""))
								pstmt.setNull(jj++,Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(inventory_date).getTime()));						
						if(replace_asset_num.equals(""))
								pstmt.setNull(jj++,Types.VARCHAR);
						else
								pstmt.setString(jj++,replace_asset_num);
						if(voided.equals(""))
								pstmt.setNull(jj++,Types.CHAR);
						else
								pstmt.setString(jj++,"y");						
						pstmt.setString(10, id); 
						pstmt.executeUpdate();
						status="Active";
						DeviceHistory ih = new DeviceHistory(debug, null, id, status, null,user_id);
						back = ih.doSave();												
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				if(back.equals("") && !replace_asset_num.equals("")){
						back = updateRelatedDevices();
				}				
				if(back.equals("")){
						back = doSelect();
				}
				return back;

		}		
		public String updateStatus(String new_status){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String str="";
				String qq = "";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "update devices set status=? where id= ? ";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, new_status);
						pstmt.setString(2, id); 
						pstmt.executeUpdate();
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(back);
						addError(back);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;

		}
		public String doDelete(){
		
				String back = "", qq = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
		
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				else{
						try{
								qq = "delete from devices where id=?";
								if(debug){
										logger.debug(qq);
								}
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								pstmt.executeUpdate();
								message="Deleted Successfully";
						}
						catch(Exception ex){
								back += ex+":"+qq;
								logger.error(back);
								addError(back);
						}
						finally{
								Helper.databaseDisconnect(con, pstmt, rs);
						}
				}
				return back;
		}
    //
		public String doSelect(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "select external_id,name,asset_num,"+
						" serial_num,model,employee_id,description,"+
						"category_id,"+
						"date_format(installed,'%m/%d/%Y'),age_length,"+
						"location_id,division_id,domain_id,status, "+
						"processor,ram,hd_size,notes,mac_address,ip_address,editable, "+
						"related_id,cost, "+
						"date_format(inventory_date,'%m/%d/%Y'),replace_asset_num,voided "+
						" from devices where id=?";
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				else{
						try{
								if(debug){
										logger.debug(qq);
								}				
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										setExternal_id(rs.getString(1));
										setName(rs.getString(2));
										setAsset_num(rs.getString(3));
										setSerial_num(rs.getString(4));
										setModel(rs.getString(5));  
										setEmployee_id(rs.getString(6));
										setDescription(rs.getString(7));
										setCategory_id(rs.getString(8));
										setInstalled(rs.getString(9));
										setAge_length(rs.getString(10));
										setLocation_id(rs.getString(11));
										setDivision_id(rs.getString(12));
										setDomain_id(rs.getString(13));
										setStatus(rs.getString(14));
										setProcessor(rs.getString(15));
										setRam(rs.getString(16));
										setHd_size(rs.getString(17));
										setNotes(rs.getString(18));
										setMac_address(rs.getString(19));
										setIp_address(rs.getString(20));
										setEditable(rs.getString(21));
										setRelated_id(rs.getString(22));
										setCost(rs.getString(23));
										setInventory_date(rs.getString(24));
										setReplace_asset_num(rs.getString(25));
										// replica
										setOld_replace_asset_num(rs.getString(25));
										setVoided(rs.getString(26) != null);
								}
								else{
										return "Record "+id+" Not found";
								}
						}
						catch(Exception ex){
								back += ex+":"+qq;
								logger.error(back);
								addError(back);
						}
						finally{
								Helper.databaseDisconnect(con, pstmt, rs);			
						}
				}
				return back;
		}

}
