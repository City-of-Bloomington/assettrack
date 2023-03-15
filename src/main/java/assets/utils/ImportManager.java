package assets.utils;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.HashSet;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;

public class ImportManager extends CommonInc{

    String date = "", id="";
    static String sqliteDbFile = "";
    static Logger logger = LogManager.getLogger(ImportManager.class);
    static final long serialVersionUID = 1420L;
    static CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder(); 

    Hashtable<String, String> domains = new Hashtable<>();
    Hashtable<String, String> locations = new Hashtable<>();
    Hashtable<String, String> employees = new Hashtable<>();
    Hashtable<String, String> types = new Hashtable<>();
    Hashtable<String, Integer> devHash = new Hashtable<>();
    Hashtable<Integer,Integer> devIdHash = new Hashtable<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    boolean employeeFlag = false, deviceFlag = false, softwareFlag=false,
	divisionFlag = false, locationFlag = false, monitorFlag = false,
	printerFlag = false, ticketsFlag = false;
    boolean autoImportFlag = false;
    List<String> sqlArr = new ArrayList<String>();
    List<ImportDetail> details = null;
    //
    public ImportManager(){
	//
    }		
    public ImportManager(boolean deb){
	//
	debug = deb;
    }
    public ImportManager(boolean deb, boolean all){
	//
	debug = deb;
	if(all){
	    setEmployeeFlag(true);
	    setDeviceFlag(true);
	    setSoftwareFlag(true);
	}
    }
    public ImportManager(boolean deb, String val){
	//
	debug = deb;
	setId(val);
    }				
    public ImportManager(boolean deb, String val, String val2){
	//
	debug = deb;
	setId(val);
	setDate(val2);
    }		
    //
    // setters
    //
    public void setSqliteDbFile(String val){
	if(val != null)
	    sqliteDbFile = val;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setDate(String val){
	if(val != null)
	    date = val;
    }
    public void setEmployeeFlag(boolean val){
	employeeFlag = val;
    }
    public void setDeviceFlag(boolean val){
	deviceFlag = val;
    }
    public void setSoftwareFlag(boolean val){
	softwareFlag = val;
    }
    public void setDivisionFlag(boolean val){
	divisionFlag = val;
    }
    public void setLocationFlag(boolean val){
	locationFlag = val;
    }
    public void setMonitorFlag(boolean val){
	monitorFlag = val;
    }
    public void setPrinterFlag(boolean val){
	printerFlag = val;
    }
    public void setTicketsFlag(boolean val){
	ticketsFlag = val;
    }
    //
    // To be used for scheduling imports with quarts
    //
    public void setAutoImportFlag(boolean val){
	autoImportFlag = val;
    }
    public void setAutoImportFlag(){
	autoImportFlag = true;
    }		
    public boolean getAutoImportFlag(){
	return autoImportFlag; // for testing purpose
    }		
    public boolean getEmployeeFlag(){
	return employeeFlag;
    }
    public boolean getDeviceFlag(){
	return deviceFlag;
    }
    public boolean getSoftwareFlag(){
	return softwareFlag;
    }
    public boolean getDivisionFlag(){
	return divisionFlag;
    }
    public boolean getLocationFlag(){
	return locationFlag;
    }
    public boolean getMonitorFlag(){
	return monitorFlag;
    }
    public boolean getPrinterFlag(){
	return printerFlag;
    }
    public boolean getTicketsFlag(){
	return ticketsFlag;
    }		
    public String doImport(){
	if(autoImportFlag){
	    employeeFlag = true;
	    deviceFlag = true;
	    softwareFlag = true;
	    printerFlag = true;
	    monitorFlag = true;
	    ticketsFlag = true;
	}				
	String back = "", qq = "";;
	if(employeeFlag){
	    back = importEmployees();
	    if(back.equals("")){
		message += "Employees imported successfully\n";
		qq = "insert into import_details values(0,?,'Employee','Success',null,now())";
	    }
	    else{
		addError("Employee import error "+back);
		qq = "insert into import_details values(0,?,'Employee','Failure','"+back+"',now())";								
	    }
	    sqlArr.add(qq);
	}
	if(divisionFlag){
	    back = importDivisions();
	    if(back.equals("")){
		message += "Divisions imported successfully\n";
		qq = "insert into import_details values(0,?,'Division','Success',null,now())";								
	    }
	    else{
		addError("Division import error "+back);
		qq = "insert into import_details values(0,?,'Division','Failure','"+back+"',now())";										
	    }
	    sqlArr.add(qq);						
	}
	if(locationFlag){
	    back = importLocations();
	    if(back.equals("")){
		message += "Locations imported successfully\n";
		qq = "insert into import_details values(0,?,'Location','Success',null,now())";												
	    }
	    else{
		addError("Location import error "+back);
		qq = "insert into import_details values(0,?,'Location','Failure','"+back+"',now())";		
	    }
	    sqlArr.add(qq);
	}
	if(deviceFlag){
	    back = importDevices();
	    if(back.equals("")){
		message += "Devices imported successfully\n";
		qq = "insert into import_details values(0,?,'Device','Success',null,now())";				
	    }
	    else{
		addError("Device import error "+back);
		qq = "insert into import_details values(0,?,'Device','Failure','"+back+"',now())";										
	    }
	    sqlArr.add(qq);
	}
	if(monitorFlag){
	    back = importMonitors();
	    if(back.equals("")){
		message += "Monitors imported successfully\n";
		qq = "insert into import_details values(0,?,'Monitor','Success',null,now())";												
	    }
	    else{
		addError("Monitor import error "+back);
		qq = "insert into import_details values(0,?,'Monitor','Failure','"+back+"',now())";		
	    }
	    sqlArr.add(qq);
	}
	if(printerFlag){
	    back = importPrinters();
	    if(back.equals("")){
		message += "Printers imported successfully\n";
		qq = "insert into import_details values(0,?,'Printer','Success',null,now())";				
	    }
	    else{
		addError("Printer import error "+back);
		qq = "insert into import_details values(0,?,'Printer','Failure','"+back+"',now())";										
	    }
	    sqlArr.add(qq);
	    try{
		Thread.sleep(5000); // 5s
	    }catch(Exception e){
		logger.error(e);
	    }
	}
	if(ticketsFlag){
	    back = importTickets();
	    if(back.equals("")){
		qq = "insert into import_details values(0,?,'Ticket','Success',null,now())";												
		message += "Tickets imported successfully\n";	
	    }
	    else{
		addError("Tickets import error "+back);
		qq = "insert into import_details values(0,?,'Ticket','Failure','"+back+"',now())";														
	    }
	    sqlArr.add(qq);
	    try{
		Thread.sleep(5000);
	    }catch(Exception e){
		logger.error(e);
	    }						
	}
	if(softwareFlag){
	    back = importSoftware();
	    if(back.equals("")){
		qq = "insert into import_details values(0,?,'Software','Success',null,now())";												
		message += "Software imported successfully\n";	
	    }
	    else{
		addError("Software import error "+back);
		qq = "insert into import_details values(0,?,'Software','Failure','"+back+"',now())";		
	    }
	    sqlArr.add(qq);
	    back = importSoftwareLicense();
	    if(back.equals("")){
		qq = "insert into import_details values(0,?,'Software','Success',null,now())";												
		message += "Software imported successfully\n";	
	    }
	    else{
		addError("Software license import error "+back);
		qq = "insert into import_details values(0,?,'Software','Failure','"+back+"',now())";		
	    }
	    sqlArr.add(qq);						
	    back = importSoftwareInstallations();
	    if(back.equals("")){
		qq = "insert into import_details values(0,?,'Software','Success',null,now())";												
		message += "Software installation imported successfully\n";	
	    }
	    else{
		addError("Software import error "+back);
		qq = "insert into import_details values(0,?,'Software','Failure','"+back+"',now())";		
	    }
	    sqlArr.add(qq);
						
	}
	back = doSave();
	return back;
    }
    public String getId(){
	return id;
    }
    public String getDate(){
	return date;
    }
    public List<ImportDetail> getDetails(){
	if(!id.equals("") && details == null){
	    ImportDetailList idl = new ImportDetailList(debug, id);
	    String back = idl.find();
	    if(back.equals("")){
		List<ImportDetail> ones = idl.getDetails();
		if(ones != null && ones.size() > 0){
		    details = ones;
		}
	    }
	}
	return details;
    }
    public String importTickets(){

	String back = "";
	Connection con = null, con2 = null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	con = Helper.getConnectionSqlite(sqliteDbFile);
	String qq = " select id,status,created_at,updated_at,first_response_secs from tickets ";
	String qq2 = " delete from tickets ";// delete all
	String qq3 = " insert into tickets values(?,?,?,?,?)";
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);
	    pstmt2.executeUpdate();
	    pstmt2 = con2.prepareStatement(qq3);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		pstmt2.setString(1, rs.getString(1));
		pstmt2.setString(2, rs.getString(2));
		pstmt2.setString(3, rs.getString(3));
		pstmt2.setString(4, rs.getString(4));
		pstmt2.setString(5, rs.getString(5));
		pstmt2.executeUpdate();
	    }
	}
        catch(SQLException ex){
	    // if the error message is "out of memory", 
	    // it probably means no database file is found
	    System.err.println(ex);
	    back += ex;
        }
        finally{						
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);
	}
	return back;
    }
    public String prepareHashes(){
	String qq   = " select name,id from domains ";
	String qq2  = " select name,id from locations ";				
	String qq3  = " select username,id from employees ";
	String qq4  = " select name,id from categories ";
	String back = "", msg="", str="", str2="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		str = rs.getString(1);
		str2 = rs.getString(2);
		if(str != null && str2 != null){
		    domains.put(str, str2);
		}
	    }
	    pstmt = con.prepareStatement(qq2);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		str = rs.getString(1);
		str2 = rs.getString(2);
		if(str != null && str2 != null){
		    locations.put(str, str2);
		}
	    }						
	    pstmt = con.prepareStatement(qq3);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		str = rs.getString(1);
		str2 = rs.getString(2);
		if(str != null && str2 != null){
		    employees.put(str, str2);
		}
	    }
	    pstmt = con.prepareStatement(qq4);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		str = rs.getString(1);
		str2 = rs.getString(2);
		if(str != null && str2 != null){
		    types.put(str, str2);
		}
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
	return back;				
    }
    //
    // we need the users to be
    //
    public Set<String> prepareForDeviceImport(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	// if employeeFlag was not checked we need to run this before
	// running device import to ensure that all employees are in the
	// system
	//
	if(!employeeFlag){
	    importEmployees();
	}
	// now we need the list of old devices external_id to figure out if we need to do update or insert
	//
	Set<String> set = new HashSet<>();
	String qq = " select name from devices where external_id is not null and name is not null";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String one = rs.getString(1);
		set.add(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return set;
    }
    //
    public Set<Integer> getOldMonitorSet(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	// if employeeFlag was not checked we need to run this before
	// running device import to ensure that all employees are in the
	// system
	//
	// now we need the list of old devices external_id to figure out if we need to do update or insert
	//
	Set<Integer> set = new HashSet<Integer>();
	String qq = " select external_id from monitors ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int one = rs.getInt(1);
		set.add(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return set;
    }
    public void getDeviceHash(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	// name is unique, external_id is not 
	// now we need the list of old devices name to figure out if we need to do update or insert
	//
	//Hashtable<String, Integer> table = new Hashtable<>();
	String qq = " select id,name,external_id from devices where external_id is not null  and name is not null";
				
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int id = rs.getInt(1);
		String name = rs.getString(2).toLowerCase();
		int ext_id = rs.getInt(3);
		devHash.put(name,id);
		devIdHash.put(ext_id, id);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	// return table;
    }
    private boolean isPureAscii(String str){
	return asciiEncoder.canEncode(str);
    }
    public String importDevices(){
				

	/**
	   SELECT name FROM sqlite_master WHERE type='table' and sql like '%service_tag%';
	   //static.spiceworks.com/images/products/0008/7044/optiplex-desktop-7010-overview3_profile_preview.jpg

	   */
	// Set<String> oldSet = prepareForDeviceImport();
	// Hashtable<String, Integer> devHash = getDeviceHash();
	prepareHashes();
	getDeviceHash();
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null,
	    pstmt3=null, pstmt30=null;
	ResultSet rs = null;
	String qq = " select d.id, d.name, null, d.serial_number, d.model,"+
	    "d.current_user, d.description, d.device_type,"+ // 8
	    "d.install_date,null,d.location,null,d.domain,"+ // 13 location 11
	    "null,d.processor_type,d.memory,null,null,"+ // 18
	    "d.mac_address,d.ip_address,null,null,d.c_purchase_date from devices d ";// 21
	//
	// we may use replace instead
	// replace into devices values(0,?,?,null,?,?, ...)
	//
	String qq2 = " insert into devices values(0,?,?,null,?,?,"+ // 1,2,3,4
	    "?,?,?,"+   // 9                     // 5,6,7
	    "?,null,?,null,?,"+               // 8,9,10
	    "'Active',?,?,null,null,"+        // 11,12
	    "?,?,null,null,null,null,null,null)";                      // 13,14
				
	String qq22 = " update devices set external_id=?,serial_num=?,model=?,employee_id=?,description=?,category_id=?,location_id=?,domain_id=?,processor=?,ram=?,mac_address=?,ip_address=? where id = ? ";
	// skip location
	// not sure about this one
	String qq23 = " update devices set external_id=?,serial_num=?,model=?,employee_id=?,description=?,category_id=?,domain_id=?,processor=?,ram=?,mac_address=?,ip_address=? where id=? ";				
	//
	// getting hard drive info
	//
	String qq3 = "select size,computer_id from disks where name like 'C:%'";
	String qq4 = "update devices set hd_size=? where id = ? ";
	//
	// after the import we set the asset_num field using
	// mysql regexp looking for names that start with 3 characters and then
	// 5 or more numbers (could end with letter or not)
	// see prepare.java for more info about mysql regexp
	//
	// for asset_num for new inserts only
	String qq5 = " update devices p set p.asset_num=substring(p.name,4) where  p.name regexp '^[a-z]{3}[0-9]{5,}' and p.asset_num is null" ;
	// for dept_id 
	String qq6 = " update devices p,divisions d set p.division_id=d.id where  p.name regexp '^[a-z]{3}[0-9]{5,}' and substring(p.name,1,3)=d.code " ;							
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    // System.err.println(qq);
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2); // device insert
	    pstmt3 = con2.prepareStatement(qq22); // update
	    pstmt30 = con2.prepareStatement(qq23); // update skip location
						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String emp_id=null, type_id=null,
		    domain_id=null,
		    location_id=null, date=null;
		String str="", str2="", str3="",str4="";
		long ram = 0;
		int ext_id = rs.getInt(1);
		String name = rs.getString(2);
		if(name != null){
		    if(!isPureAscii(name)){
			continue;
		    }
		    name = name.toLowerCase();
		}
								
		str = rs.getString(6); // current_user
		if(str != null && str.indexOf("\\") > 0){
		    str2 = str.substring(str.indexOf("\\")+1);
		    if(employees.containsKey(str2)){
			emp_id = employees.get(str2);
		    }
		}
		str = rs.getString(7); // description
		// we do not import data with error in the description
		// such as 'Window computer with error'
		if(str != null && str.indexOf("error") > -1) continue;
		str2 = rs.getString(8); // device_type
		if(str2 != null){
		    if(types.containsKey(str2)){
			type_id = types.get(str2);
		    }
		}
		str3 = rs.getString(11); // location
		boolean loc_flag = false;
		if(str3 != null && !str3.equals("")){
		    if(locations.containsKey(str3)){
			location_id = locations.get(str3);
			loc_flag = true;
		    }
		}								
		str4 = rs.getString(13); // domain
		if(str4 != null){
		    if(str4.startsWith("cob")){
			str4 = "COB";
		    }
		    if(domains.containsKey(str4)){
			domain_id = domains.get(str4);
		    }
		}
		String str5 = rs.getString(9);
		if(str5 != null){
		    date = str5.substring(0,10);
		}
		else{
		    str5 = rs.getString(23); // purchase_date										
		    if(str5 != null){
			date = str5.substring(0,10);
		    }
		}
		str5 = rs.getString(16);
		if(str5 != null){
		    ram = rs.getLong(16)/1000000000; // to gig
		}								
		// if no username available, we skip
		Device one = new Device(debug,
					null, // id
					rs.getString(1),
					rs.getString(2),
					null,
					rs.getString(4),
																				
					rs.getString(5),
					emp_id,
					rs.getString(7),
					type_id,
					date,
																				
					null,
					location_id,
					null,
					domain_id,
					null,
																				
					rs.getString(15),
					str5 == null?str5:""+ram,
					null,
					null,
					rs.getString(19),
																				
					rs.getString(20),
					null, // editable
					null, // related id
					// skip purchase date
					null,// purchase price
					null, // inventory_date
					null, // replace_asset_num
					false,  // voided
					null,
					null
					); 
		one.setLocationFlag(loc_flag);
		if(devHash != null && devHash.containsKey(name)){
		    int id = devHash.get(name);
		    devIdHash.put(ext_id, id);
		    // System.err.println(name+" found "+id);
		    one.setId(""+id);
		    if(loc_flag)										
			back = one.updateImport(pstmt3);
		    else
			back = one.updateImport(pstmt30);
		}
		else{
		    // System.err.println("not found "+name);
		    back = one.saveImport(pstmt2);
		}
		if(!back.equals("")){
		    msg += back+"\n";
		}
		// System.err.println(one.getId()+" "+emp_id+" "+location_id+" "+type_id+" "+domain_id+" "+one.getName());
	    }
	    //
	    pstmt = con.prepareStatement(qq3);
	    pstmt2 = con2.prepareStatement(qq4);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		String str2 = rs.getString(2);
		// System.err.println(str+ " "+str2);
		try{
		    if(str != null && str2 != null){
			long size = (rs.getLong(1))/1000000000; // gig
			// System.err.println(size+ " "+str2);
			pstmt2.setLong(1, size);
			pstmt2.setString(2, str2);
			pstmt2.executeUpdate();
		    }
		}catch(Exception ex){
		    System.err.println(ex + " "+str);
		}
	    }
	    //
	    // setting asset_num field
	    //
	    pstmt2 = con2.prepareStatement(qq5);
	    pstmt2.executeUpdate();
	    //
	    // set division_id
	    //
	    pstmt2 = con2.prepareStatement(qq6);
	    pstmt2.executeUpdate();
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);						
	    Helper.databaseDisconnect(con2, rs, pstmt2, pstmt3, pstmt30);
	}
	if(!msg.equals(""))
	    System.err.println(msg);
	return msg;

    }
    // ToDo need to find computer_id (new device id) 
    public String importMonitors(){

	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null, pstmt3=null;
	ResultSet rs = null;
	Set<Integer> oldSet = getOldMonitorSet();
	String qq = " select id,computer_id,name,null,serial_number,"+
	    " null,model_name,monitor_type,screen_width,screen_height,"+
	    " manufacturer,manufacturer_date,null,null,null,"+
	    " null "+
	    " from desktop_monitors ";
	//
	String qq2 = " insert into monitors values(0,?,?,?,null,?,"+ // 4
	    "null,?,?,?,?,"+                         // 4
	    "?,?,null,'Active',null,"+            // 2
	    "null,null)";                          
	String qq3 = " update monitors set device_id=?,name=?,serial_num=?,"+
	    "model=?,type=?,vertical_resolution=?,horizontal_resolution=?,"+
	    "manufacturer=? where external_id=? ";
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}

	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);
	    pstmt3 = con2.prepareStatement(qq3);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		boolean found = false;
		String date=null, device_id = null;
		int external_id = rs.getInt(1);
		int old_device_id = rs.getInt(2);
		String str = rs.getString(12);
		if(str != null){
		    date = str.substring(0,10);
		}
		if(oldSet.contains(external_id)){
		    found = true;
		}
		if(devIdHash.containsKey(old_device_id)){
		    device_id = ""+devIdHash.get(old_device_id);
		}
		Monitor one = new Monitor(debug,
					  null, // id
					  ""+external_id,
					  device_id, 
					  rs.getString(3),
					  null,
					  rs.getString(5),
																				
					  null,
					  rs.getString(7),
					  rs.getString(8),
					  rs.getString(9),
					  rs.getString(10),
																					
					  rs.getString(11),
					  date,
					  null,
					  "Active",
					  null,
																					
					  null,
					  null); //inventory_date
								
		if(found){
		    back = one.updateImport(pstmt3);										
		}
		else{
		    back = one.saveImport(pstmt2);
		}
		if(!back.equals("")){
		    msg += back+"\n";
		}
		// System.err.println(found+" "+external_id+" "+device_id+" "+one.getName());
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);
	    Helper.databaseDisconnect(con2, pstmt3, rs);						
	}
	if(!msg.equals(""))
	    System.err.println(msg);
	return msg;

    }
    // employees
    public String importEmployees(){
		
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	String qq = " select id,email,first_name,last_name,office_phone,role from users ";
	//
	// String qq2 = " insert into employees values(?,?,?, ?,?,?) on duplicate key update username=?,first_name=?,last_name=?,office_phone=?, role=?";
	String qq2 = " replace into employees values(?,?,?,?, ?,?,?)";				
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		String str2 = rs.getString(2);
		String str3 = rs.getString(3);
		String str4 = rs.getString(4);
		String str5 = rs.getString(5);
		String str6 = "end_user"; // ignore other roles
		String str22 = null;
		// if no username available, we skip
		if(str2.indexOf("@") > 0){ 
		    str22 = str2.substring(0, str2.indexOf("@"));
		    // id = external_id
		    Employee one = new Employee(debug, str, str, str22, str3, str4, str5, str6);
		    back = one.saveImport(pstmt2);
		    if(!back.equals("")){
			msg += back;
		    }
		    // System.err.println(str+" "+str2+" "+str3+" "+str4+" "+str5+" "+back);
		}
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	if(!msg.equals(""))
	    System.err.println(msg);
	return msg;

    }
    Set<Integer> getOldPrinterSet(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	Set<Integer> set = new HashSet<Integer>();
	String qq = " select external_id from printers ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int one = rs.getInt(1);
		set.add(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return set;
    }
    //
    public String importPrinters(){
		
	String back = "", msg="";
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null, pstmt3=null;
	ResultSet rs = null;
	Set<Integer> oldSet = getOldPrinterSet();
	String qq = " select id,name,computer_id,print_processor,printer_device,updated_on from printers where not (print_processor like 'win%' or print_processor like 'mod%')";
	//
	// asset_num = null
	String qq2 = " insert into printers values(0,?,null,?,?, ?,?,?,'Active', null, null,null,null)";
	String qq3 = " update printers set name=?, device_id=?,print_processor=?,description=? where external_id = ? ";
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);
	    pstmt3 = con2.prepareStatement(qq3);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		boolean found = false;
		int external_id = rs.getInt(1);
		String str2 = rs.getString(2);								
		int old_device_id = rs.getInt(3);
		String str4 = rs.getString(4);
		String str5 = rs.getString(5);
		String str6 = rs.getString(6);
		String date = null;
		String device_id = null;

		if(str6 != null && str6.length() > 10){
		    date = str6.substring(0,10);
		}
		if(oldSet.contains(external_id)){
		    found = true;
		}
		if(devIdHash.containsKey(old_device_id)){
		    device_id = ""+devIdHash.get(old_device_id);
		}								
		Printer one = new Printer(debug, null,""+external_id, null, str2, device_id, str4, str5, date,"Active", null, null, null, null);
		if(found){
		    back = one.updateImport(pstmt3);
		}
		else{
		    back = one.saveImport(pstmt2);
		}
		if(!back.equals("")){
		    msg += back;
		}
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	if(!msg.equals(""))
	    System.err.println(msg);				
	return msg;

    }
    Set<Integer> getOldSoftwareSet(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	Set<Integer> set = new HashSet<Integer>();
	String qq = " select external_id from softwares ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int one = rs.getInt(1);
		set.add(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return set;
    }
    Set<Integer> getOldLicenseSet(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	Set<Integer> set = new HashSet<Integer>();
	String qq = " select external_id from software_licenses ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int one = rs.getInt(1);
		set.add(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return set;
    }
    Set<Integer> getOldInstallationSet(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	Set<Integer> set = new HashSet<Integer>();
	String qq = " select external_id from software_installations ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int one = rs.getInt(1);
		set.add(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return set;
    }		
    //
		
    public String importSoftware(){
		
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null, pstmt3=null;
	ResultSet rs = null;
	String qq = " select s.id,s.display_name,s.vendor,s.install_date,s.software_installations_count from software s where s.id in(select i.software_id from software_installations i where i.software_license_id is not null); ";
	String qq2 = " insert into softwares values(0,?,?,?,?,?,null) ";
	String qq3 = " update softwares set display_name=?,vendor=?,install_count=? where external_id=? ";
	Set<Integer> oldSet = getOldSoftwareSet();
	//
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);
	    pstmt3 = con2.prepareStatement(qq3);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String date = null;
		boolean found = false;
		int old_id= rs.getInt(1);
		String str = rs.getString(4);
		if(str != null && str.length() > 0){
		    date = str.substring(0,10);
		}
		if(oldSet.contains(old_id)){
		    found = true;
		}
		Software one = new Software(debug,
					    null,
					    ""+old_id,
					    rs.getString(2),
					    rs.getString(3),
					    date,
					    rs.getString(5),
					    null);
		if(found){
		    back = one.updateImport(pstmt3);
		}
		else{
		    back = one.saveImport(pstmt2);
		}
		if(!back.equals("")){
		    msg += back;
		}
		// System.err.println(old_id+" "+one.getDisplay_name()+" "+date);
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	if(!back.equals(""))
	    System.err.println(back);
	return back;
    }
		
    public String importSoftwareLicense(){
		
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null, pstmt3=null;
	ResultSet rs = null;
				
	String qq = " select id,key,type,created_at,seat_limit from software_licenses";
	String qq2 = " insert into software_licenses values(0,?,?,?,?,?,null)";
	String qq3 = " update software_licenses set key_value=?,type=?,seat_limit=? where external_id=? ";
	Set<Integer> oldSet = getOldLicenseSet();
	//
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);
	    pstmt3 = con2.prepareStatement(qq3);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String date = null;
		boolean found = false;
		int old_id = rs.getInt(1);
		String str = rs.getString(4);
		if(str != null && str.length() > 0){
		    date = str.substring(0,10);
		}
		SoftwareLicense one = new SoftwareLicense(debug,
							  null,
							  ""+old_id,
							  rs.getString(2),
							  rs.getString(3),
							  date,
							  rs.getString(5),
							  null);
		if(oldSet.contains(old_id)){
		    back = one.updateImport(pstmt3);
		}
		else{
		    back = one.saveImport(pstmt2);
		}
		if(!back.equals("")){
		    msg += back;
		}
		// System.err.println(old_id+" "+one.getKey_value()+" "+date);
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	if(!back.equals(""))
	    System.err.println(back);				
	return back;
    }		
    Hashtable<Integer, Integer> getSoftwareHash(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();
	String qq = " select id,external_id from softwares where external_id is not null ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int id = rs.getInt(1);
		int exid = rs.getInt(2);
		table.put(exid,id);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return table;

    }
    Hashtable<Integer, Integer> getLicenseHash(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String back = "";
	//
	// now we need the list of old devices external_id to figure out if we need to do update or insert
	//
	Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();
	String qq = " select id,external_id from software_licenses where external_id is not null ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return null;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int id = rs.getInt(1);
		int exid = rs.getInt(2);
		table.put(exid,id);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    addError(""+ex);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}						
	return table;

    }		
    public String importSoftwareInstallations(){
		
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null, pstmt3=null;
	ResultSet rs = null;

	String qq = " select id,software_id,computer_id,software_license_id,install_date from software_installations where software_license_id is not null ";
	String qq2 = " insert into software_installations values(0,?,?,?,?,?,null)";
	String qq3 = " update software_installations set software_id=?,device_id=?,license_id=? where external_id=? ";
	Set<Integer> oldSet = getOldInstallationSet();
	// Hashtable<Integer, Integer> deviceTable = getDeviceHash();
	Hashtable<Integer, Integer> softwareTable = getSoftwareHash();
	Hashtable<Integer, Integer> licenseTable = getLicenseHash();
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);
	    pstmt3 = con2.prepareStatement(qq3);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		boolean found = false;
		String new_soft_id = null,
		    new_device_id=null,
		    new_lic_id=null;
		int old_id = rs.getInt(1);
		int old_soft_id = rs.getInt(2);
		int old_device_id = rs.getInt(3);
		int old_lic_id = rs.getInt(4);
		String date = null;
		String str = rs.getString(5);
		if(str != null && str.length() > 0){
		    date = str.substring(0,10);
		}
		if(oldSet.contains(old_id)){
		    found = true;
		}
		if(softwareTable.containsKey(old_soft_id)){
		    new_soft_id = ""+softwareTable.get(old_soft_id);
		}
		if(devIdHash.containsKey(old_device_id)){
		    new_device_id = ""+devIdHash.get(old_device_id);
		}
		if(licenseTable.containsKey(old_lic_id)){
		    new_lic_id = ""+licenseTable.get(old_lic_id);
		}								
		SoftwareInstallation one =
		    new SoftwareInstallation(debug,
					     null,
					     ""+old_id,
					     new_soft_id,
					     new_device_id,
					     new_lic_id,
																						 
					     date,
					     null);
		if(found){
		    back = one.updateImport(pstmt3);
		}
		else{
		    back = one.saveImport(pstmt2);
		}
		if(!back.equals("")){
		    msg += back;
		}
		// System.err.println(old_id+" "+new_soft_id+" "+new_device_id+" "+" "+new_lic_id+" "+date);
	    }
						
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	if(!back.equals(""))
	    System.err.println(back);				
	return back;
    }		
    /**
     * divisions 
     */
    public String importDivisions(){
		
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	String qq = " select distinct(department) from users ";
	//
	// we may use replace instead
	//
	String qq2 = " insert into divisions values(0,?,null,null,null)";
	String qq3 = " select distinct(name) from divisions ";
	Set<String> divSet = new HashSet<String>();
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    // first find the list of the divisions
	    // that we have so far, we will add the
	    // new ones only
	    pstmt2 = con2.prepareStatement(qq3);						
	    rs = pstmt2.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		if(str != null){
		    divSet.add(str);
		}
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		if(str != null && !divSet.contains(str)){ 
		    Division one = new Division(debug, null, str);
		    back = one.saveImport(pstmt2);
		    if(!back.equals("")){
			msg += back;
		    }
		    // System.err.println(str);
		}
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	return back;
    }
    public String importLocations(){
		
	String back = "", msg="";
		
	Connection con = null, con2=null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	Set<String> locSet = new HashSet<String>();
	String qq = " select distinct(location) from users union "+
	    " select distinct(location) from devices ";
	//
	// we may use replace instead
	//
	String qq2 = " insert into locations values(0,?,null)";
	String qq3 = " select name from locations ";
	con = Helper.getConnectionSqlite(sqliteDbFile);
	if(con == null){
	    back = "Could not connect to Sqlite DB";
	    addError(back);
	    return back;
	}
	con2 = Helper.getConnection();
	if(con2 == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}
	try{
	    pstmt2 = con2.prepareStatement(qq3);						
	    rs = pstmt2.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		if(str != null){
		    locSet.add(str);
		}
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con2.prepareStatement(qq2);						
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		if(str != null){
		    if(!locSet.contains(str)){
			Type one = new Type(debug, null, str, false, "locations");
			back = one.saveImport(pstmt2);
			if(!back.equals("")){
			    msg += back;
			}
			// System.err.println(str);
		    }
		}
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	    Helper.databaseDisconnect(con2, pstmt2, rs);						
	}
	return back;

    }				
    public String doSave(){
	String back = "", msg="";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " insert into data_imports values(0,now())";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.executeUpdate();
	    date = Helper.getToday();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);				
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }
	    for(String str:sqlArr){
		if(debug){
		    logger.debug(str);
		}				
		pstmt = con.prepareStatement(str);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
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
	return back;						
    }
    public String doSelect(){
	String back = "", msg="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select date_format(date,'%m/%d/%Y') from data_imports where id=? ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		date = rs.getString(1);
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
	return back;						
    }		

}
