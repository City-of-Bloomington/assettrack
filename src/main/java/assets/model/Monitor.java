package assets.model;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Monitor extends CommonInc{

    static Logger logger = LogManager.getLogger(Monitor.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    /*
    // in database
    static final String[] STATUSES = {"Active","Auctioned","Donated","Recycled","Disposed"};
    static final int[] STATUS_ORDER = { 1,         2,        2,           2,          2};
    */
    static final long serialVersionUID = 1490L;
    //
    // spiceworks scanned devices are not editable only manully entered ones
    // such as monitors, scanners, etc
    //
    String id="", external_id="", editable="", name="", asset_num="";
    String serial_num="", model="", device_id="",screen_size="",
	received="", type="",vertical_resolution="", horizontal_resolution="",
	manufacturer="", inventory_date="",
	status="Active", notes="", expected_age="3";

    String user_id="", employee_id="";
    //
    Device device = null;
    User user = null;
		
    public Monitor() {
    }
    public Monitor(boolean deb, String val) {
	debug = deb;
	setId(val);
    }
	
    public Monitor(boolean deb,
									 
		   String _id,
		   String _external_id,
		   String _device_id,									 
		   String _name,
		   String _asset_num,
		   String _serial_num,
									 
		   String _screen_size,
		   String _model,
		   String _type,
		   String _vertical_resolution,
		   String _horizontal_resolution,
									 
		   String _manufacturer,
		   String _received,
		   String _expected_age,
		   String _status,
		   String _notes,
									 
		   String _editable,
		   String _inventory_date
		   ) {
	debug = deb;
				
	setId(_id);
	setExternal_id(_external_id);
	setDevice_id(_device_id);
	setName(_name);
	setAsset_num(_asset_num);
	setSerial_num(_serial_num);

	setScreen_size(_screen_size);
	setModel(_model);
	setType(_type);
	setVertical_resolution(_vertical_resolution);
	setHorizontal_resolution(_horizontal_resolution);
				
	setManufacturer(_manufacturer);
	setReceived(_received);
	setExpected_age(_expected_age);
	setStatus(_status);
	setNotes(_notes);
				
	setEditable(_editable);
	setInventory_date(_inventory_date);
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
    public String getDevice_id() {
	return device_id;
    }		
    public String getEditable() {
	return editable;
    }
    public String getName() {
	return name;
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
    public String getScreen_size() {
	return screen_size;
    }		
    public String getType() {
	return type;
    }
    public String getManufacturer() {
	return manufacturer;
    }		
    public String getVertical_resolution() {
	return vertical_resolution;
    }
    public String getHorizontal_resolution() {
	return horizontal_resolution;
    }
    public String getReceived() {
	return received;
    }
    // probably will not be used
    public String getExpected_age() {
	return expected_age;
    }
    public String getStatus() {
	return status;
    }

    public String getNotes() {
	return notes;
    }
    public boolean isEditable(){
	return !editable.equals("");
    }
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }
    public String getInventory_date(){
	return inventory_date;
    }				
    //
    // setters
    //
    public void setId(String val) {
	if(val != null)
	    id = val;
    }
    public void setDevice_id(String val) {
	if(val != null)
	    device_id = val;
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

    public void setType(String val) {
	if(val != null)
	    type = val;
    }
    public void setManufacturer(String val) {
	if(val != null)
	    manufacturer = val;
    }		

    public void setScreen_size(String val) {
	if(val != null)
	    screen_size = val;
    }

    public void setExpected_age(String val) {
	if(val != null)
	    expected_age = val;
    }

    public void setReceived(String val) {
	if(val != null)
	    received = val;
    }

    public void setVertical_resolution(String val) {
	if(val != null)
	    vertical_resolution = val;
    }
    public void setHorizontal_resolution(String val) {
	if(val != null)
	    horizontal_resolution = val;
    }
    public void setUser_id(String val) {
	if(val != null){
	    user_id = val;
	}
    }
    public void setInventory_date(String val) {
	if(val != null)
	    inventory_date = val;
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
    public void setNotes(String val) {
	if(val != null){
	    notes = val;
	}
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
    //
    public String toString(){
	String ret = asset_num;
	if(ret.equals("") && !name.equals("")){
	    ret = name;
	}
	if(!status.equals("")){
	    if(!status.equals("")) ret += ", ";
	    ret += status;
	}
	return ret;
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
    public Device getDevice(){
	if(device == null && !device_id.equals("")){
	    Device sp = new Device(debug, device_id);
	    String back = sp.doSelect();
	    if(back.equals("")){
		device = sp;
	    }
	}
	return device;
    }				
    //
    public String saveImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
						
	    pstmt.setString(jj++, external_id);
	    if(device_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, device_id);						
	    if(name.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, name);
	    // asset_num = null
	    if(serial_num.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,serial_num);
	    // screen_size = null
	    if(model.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,model);
	    //
	    if(type.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,type);
	    if(vertical_resolution.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++,vertical_resolution);
	    if(horizontal_resolution.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,horizontal_resolution);
	    if(manufacturer.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,manufacturer);
	    // 
	    if(received.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setString(jj++, received);	// as text
	    // expected_age = null
	    // status = 'Active'
	    // notes = null
	    // editable = null
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += " Save "+ex;
	    // System.err.println(id+" "+ex);
	    logger.error(external_id+" "+ex);
	}
	return msg;
    }
    public String updateImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
	    if(device_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, device_id);						
	    if(name.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, name);
	    if(serial_num.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,serial_num);
	    if(model.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,model);
	    if(type.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,type);
	    if(vertical_resolution.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++,vertical_resolution);
	    if(horizontal_resolution.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,horizontal_resolution);
	    if(manufacturer.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,manufacturer);
	    pstmt.setString(jj++, external_id);
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += "update "+ex;
	    logger.error(external_id+" "+ex);
	    // System.err.println(id+" "+ex);
	}
	return msg;
    }
		
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into monitors values(0,?,"+
	    "?,?,?,?,?,"+
	    "?,?,?,?,?,"+
	    "?,?,'Active',?,'y',?)";			
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
		    message ="Saved Successfully";
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
	    qq = "update monitors set status=? where id = ? ";
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
    String fillPStatement(PreparedStatement pstmt){
	String back = "";
	int jj=1;
	try{
	    if(external_id.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++,external_id);								
	    if(device_id.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++,device_id);						
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
	    if(screen_size.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,screen_size);						
	    if(model.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,model);
						
	    if(type.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,type);
	    if(vertical_resolution.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++,vertical_resolution);
	    if(horizontal_resolution.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++,horizontal_resolution);
	    if(manufacturer.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,manufacturer);						
	    if(received.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(received).getTime()));
						
	    pstmt.setString(jj++,""+expected_age);
	    if(id.equals("")){
		status="Active";
	    }
	    // otherwise we skip status
	    if(notes.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,notes);
	    if(inventory_date.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(inventory_date).getTime()));
	    editable = "y";
	}
	catch(Exception ex){
	    logger.error(ex);
	    back += ex;
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
	    qq = "update monitors set "+
		"asset_num=?, screen_size=?,received=?, expected_age=?, "+
		"notes=?,inventory_date=? "+
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
	    if(screen_size.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,screen_size);							
	    if(received.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(received).getTime()));						
	    pstmt.setString(jj++,""+expected_age);
	    if(notes.equals(""))
		pstmt.setNull(jj++,Types.VARCHAR);
	    else
		pstmt.setString(jj++,notes);
	    if(inventory_date.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(inventory_date).getTime()));												
	    pstmt.setString(7,id); 
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
	if(back.equals("")){
	    back = doSelect();
	}
	return back;

    }				
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
	    qq = "update monitors set external_id=?,device_id=?,"+
		"name=?,asset_num=?,serial_num=?,screen_size=?,model=?,type=?,"+
		"vertical_resolution=?,horizontal_resolution=?,manufacturer=?,"+
		"received=?,expected_age=?, "+
		"notes=?,inventory_date=? "+ // we do not change status here
		"where id=?";
						
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    back = fillPStatement(pstmt);
	    if(back.equals("")){
		pstmt.setString(16,id); // 18 - 2 (editable)
		pstmt.executeUpdate();
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
	if(back.equals("")){
	    back = doSelect(); // to get status 
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
		qq = "delete from monitors where id=?";
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
	String qq = "select external_id,device_id,name,asset_num,"+
	    " serial_num,screen_size,model,type,"+
	    "vertical_resolution,horizontal_resolution,"+
	    "manufacturer,"+
	    "date_format(received,'%m/%d/%Y'),"+
	    "expected_age,status,notes,editable, "+
	    "date_format(inventory_date,'%m/%d/%Y') "+						
	    " from monitors where id=?";
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
		    setDevice_id(rs.getString(2));
		    setName(rs.getString(3));
		    setAsset_num(rs.getString(4));
		    setSerial_num(rs.getString(5));
		    setScreen_size(rs.getString(6));
		    setModel(rs.getString(7));  
		    setType(rs.getString(8));
		    setVertical_resolution(rs.getString(9));
		    setHorizontal_resolution(rs.getString(10));  
		    setManufacturer(rs.getString(11));
		    setReceived(rs.getString(12));
		    setExpected_age(rs.getString(13));
		    setStatus(rs.getString(14));
		    setNotes(rs.getString(15));
		    setEditable(rs.getString(16));
		    setInventory_date(rs.getString(17));
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
