package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.text.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Printer extends CommonInc implements java.io.Serializable{

    String id="", asset_num="", name="", description = "", device_id="",
	date="", print_processor="", editable="", status="",
	notes="", external_id="", inventory_date="";
    String serial_num = ""; // added by IT people
    static final long serialVersionUID = 1540L;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		
    static Logger logger = LogManager.getLogger(Printer.class);
    //
    public Printer(){
	super();
    }
    public Printer(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Printer(String val){
	//
	setId(val);
    }
    public Printer(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Printer(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setName(val2);
    }
    public Printer(boolean deb,
		   String val,
		   String val2,
		   String val3,
		   String val4,
		   String val5,
		   String val6,
		   String val7,
		   String val8,
		   String val9,
		   String val10,
		   String val11,
		   String val12,
		   String val13
		   ){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setExternal_id(val2);
	setAsset_num(val3);
	setName(val4);
	setDevice_id(val5);
	setPrint_processor(val6);
	setDescription(val7);
	setDate(val8);
	setStatus(val9);
	setNotes(val10);
	setEditable(val11);
	setSerial_num(val12);
	setInventory_date(val13);
    }		
    public String getId(){
	return id;
    }
    public String getIds(){
	String ret = id;
	if(!external_id.equals("")){
	    ret += " ("+external_id+")";
	}
	return ret;
    }
    public String getName(){
	return name;
    }
    public String getPrint_processor(){
	return print_processor;
    }		
    public String getDescription(){
	return description;
    }
    public String getDevice_id(){
	return device_id;
    }
    public String getDate(){
	return date;
    }
    public String getInventory_date(){
	return inventory_date;
    }		
    public String getStatus(){
	return status;
    }		
    public String getEditable(){
	return editable;
    }
    public String getExternal_id() {
	return external_id;
    }
    public String getAsset_num() {
	return asset_num;
    }
    public String getNotes() {
	return notes;
    }
    public String getSerial_num() {
	return serial_num;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }		
    public void setAsset_num(String val) {
	if(val != null)
	    asset_num = val;
    }
    public void setSerial_num(String val) {
	if(val != null)
	    serial_num = val;
    }		
		
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setInventory_date(String val){
	if(val != null)
	    inventory_date = val;
    }		
    public void setStatus(String val){
	if(val != null)
	    status = val;
    }		
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    public void setDescription(String val){
	if(val != null)
	    description = val;
    }
    public void setPrint_processor(String val){
	if(val != null)
	    print_processor = val;
    }
    public void setDate(String val){
	if(val != null)
	    date = val;
    }
    public void setEditable(String val){
	if(val != null)
	    editable = val;
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
	return name;
    }

    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select external_id,asset_num,name,device_id,print_processor,description,date_format(date,'%m/%d/%Y'),status,notes,editable,serial_num,"+
	    "date_format(inventory_date,'%m/%d/%Y') from printers where id=? ";
	if(id.equals("")){
	    msg = " Printer id not set";
	    addError(msg);
	    return msg;
	}
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1, id);
	    rs = stmt.executeQuery();
	    if(rs.next()){
		setExternal_id(rs.getString(1));
		setAsset_num(rs.getString(2));
		setName(rs.getString(3));
		setDevice_id(rs.getString(4));
		setPrint_processor(rs.getString(5));
		setDescription(rs.getString(6));
		setDate(rs.getString(7));
		setStatus(rs.getString(8));
		setNotes(rs.getString(9));
		setEditable(rs.getString(10));
		setSerial_num(rs.getString(11));
		setInventory_date(rs.getString(12));
	    }
	    else{
		msg = " No found";
	    }
	}catch(Exception ex){
	    logger.error(ex+" : "+qq);
	    msg += " "+ex;
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg;
    }
    //
    public String doSave(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";

	qq = "insert into printers values(0,?,?,?,?,?,?,?,'Active',?,'y',null,null)";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    if(external_id.equals(""))
		stmt.setNull(1,Types.INTEGER);
	    else
		stmt.setString(1,external_id);
	    if(asset_num.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2,asset_num);						
	    stmt.setString(3, name);
	    if(device_id.equals(""))
		stmt.setNull(4,Types.INTEGER);
	    else
		stmt.setString(4,device_id);
	    if(print_processor.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5,print_processor);
	    if(description.equals(""))
		stmt.setNull(6,Types.VARCHAR);
	    else
		stmt.setString(6, description);
	    if(date.equals(""))
		date = Helper.getToday();
	    stmt.setDate(7, new java.sql.Date(dateFormat.parse(date).getTime()));						if(notes.equals(""))
																    stmt.setNull(8,Types.VARCHAR);
	    else
		stmt.setString(8, notes);
	    stmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    stmt = con.prepareStatement(qq);				
	    rs = stmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }									
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg; // success
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
	    qq = "update printers set status=? where id= ? ";
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
		
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update printers set external_id=?,asset_num=?,name=?,device_id=?,print_processor=?,description=?,date=?,notes=?,serial_num=?,inventory_date=? where id=?";
	//
	if(id.equals("")){
	    msg = " id not set ";
	    return msg;
	}				
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    if(external_id.equals(""))
		stmt.setNull(1,Types.INTEGER);
	    else
		stmt.setString(1,external_id);
	    if(asset_num.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2, asset_num);
	    stmt.setString(3, name);
	    if(device_id.equals(""))
		stmt.setNull(4,Types.INTEGER);
	    else
		stmt.setString(4,device_id);
	    if(print_processor.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5,print_processor);
	    if(description.equals(""))
		stmt.setNull(6,Types.VARCHAR);
	    else
		stmt.setString(6, description);
	    if(date.equals(""))
		date = Helper.getToday();
	    stmt.setDate(7, new java.sql.Date(dateFormat.parse(date).getTime()));
	    if(notes.equals(""))
		stmt.setNull(8,Types.VARCHAR);
	    else
		stmt.setString(8, notes);
	    if(serial_num.equals(""))
		stmt.setNull(9,Types.VARCHAR);
	    else
		stmt.setString(9, serial_num);
	    if(inventory_date.equals(""))
		stmt.setNull(10,Types.DATE);
	    else
		stmt.setDate(10, new java.sql.Date(dateFormat.parse(inventory_date).getTime()));						
	    stmt.setString(11, id);
	    editable="y";
	    status="Active";// we do not change this through printer form
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg; // success
    }
    public String doPartialUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update printers set asset_num=?,date=?,notes=?,serial_num=?,inventory_date=? where id=?";
	//
	if(id.equals("")){
	    msg = " id not set ";
	    return msg;
	}				
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    int jj=1;
	    if(asset_num.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, asset_num);
	    if(date.equals("")){
		stmt.setNull(jj++,Types.DATE);
	    }
	    else 
		stmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));
	    if(notes.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, notes);
	    if(serial_num.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, serial_num);
	    if(inventory_date.equals("")){
		stmt.setNull(jj++,Types.DATE);
	    }
	    else 
		stmt.setDate(jj++, new java.sql.Date(dateFormat.parse(inventory_date).getTime()));						
	    stmt.setString(jj++, id);
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	if(msg.equals("")){
	    msg = doSelect();
	}
	return msg; 
    }				
    //
    public String doDelete(){

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  printers where id=?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1,id);
	    stmt.executeUpdate();
	    message="Deleted Successfully";
	    //
	}
	catch(Exception ex){
	    msg = ex+" : "+qq;
	    logger.error(msg);
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}			
	return msg; 
    }
    public String saveImport(PreparedStatement stmt){

	String msg = "";
	if(external_id.equals("") || name.equals("")){
	    msg = "id, name not set";
	    return msg;
	}
	if(stmt == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    // insert part
	    if(external_id.equals(""))
		stmt.setNull(1,Types.INTEGER);
	    else
		stmt.setString(1,external_id);						
	    stmt.setString(2, name);
	    if(device_id.equals(""))
		stmt.setNull(3,Types.INTEGER);
	    else
		stmt.setString(3,device_id);
	    stmt.setString(4,print_processor);
	    if(description.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else						
		stmt.setString(5, description);
	    if(date.equals(""))
		stmt.setNull(6,Types.DATE);
	    else
		stmt.setString(6, date);
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += ex;
	    logger.error(msg);
	    addError(msg);
	}
	return msg; // success
    }
    public String updateImport(PreparedStatement stmt){

	String msg = "";
	try{
	    int jj=1;
	    stmt.setString(jj++, name); // 1
	    if(device_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER); // 2
	    else
		stmt.setString(jj++,device_id);
	    stmt.setString(jj++, print_processor); // 3
	    if(description.equals(""))
		stmt.setNull(jj++,Types.VARCHAR); // 4
	    else						
		stmt.setString(jj++, description);
	    stmt.setString(jj++, external_id);		// 5
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += ex;
	    logger.error(msg);
	    addError(msg);
	}
	return msg; // success
    }				

}
