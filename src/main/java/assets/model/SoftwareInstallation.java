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

public class SoftwareInstallation extends CommonInc implements java.io.Serializable{

    String id="", external_id="", software_id="", device_id="", date="",
	license_id="",editable="";
    //
    // for software_license
    //
    static final long serialVersionUID = 1620L;	
    static Logger logger = LogManager.getLogger(SoftwareInstallation.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Device device = null;
    Software software = null;
    SoftwareLicense license = null;
    //
    public SoftwareInstallation(){
	super();
    }
    //
    public SoftwareInstallation(boolean deb, String val){
	//
	// initialize
	//
	debug = deb;
	setId(val);
    }
    //
    //
    public SoftwareInstallation(boolean deb, String val, String val2, String val3, String val4, String val5, String val6, String val7){
	super(deb);
	setId(val);
	setExternal_id(val2);
	setSoftware_id(val3);
	setDevice_id(val4);
	setLicense_id(val5);
	setDate(val6);
	setEditable(val7);
    }		
    //
    // getters
    //
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
    public String getSoftware_id(){
	return software_id;
    }
    public String getDevice_id(){
	return device_id;
    }
    public String getLicense_id(){
	return license_id;
    }		
    public String getDate(){
	return date;
    }
    public String getEditable(){
	return editable;
    }
    public String getKey_value(){
	getLicense();
	if(license != null){
	    return license.getKey_value();
	}
	return "";
    }		
    public boolean canEdit(){
	return !editable.equals("");
    }
    public Software getSoftware(){
	if(!software_id.equals("")){
	    Software one = new Software(debug, software_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		software = one;
	    }
	}
	return software;
    }
    public Device getDevice(){
	if(device == null && !device_id.equals("")){
	    Device one = new Device(debug, device_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		device = one;
	    }
	}
	return device;
    }
    public SoftwareLicense getLicense(){
	if(license == null){
	    if(!license_id.equals("")){
		SoftwareLicense one = new SoftwareLicense(debug, license_id);
		String back = one.doSelect();
		if(back.equals("")){
		    license = one;
		}
	    }
	    else{
		license = new SoftwareLicense(debug);
	    }
	}
	return license;
    }
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }				
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setSoftware_id(String val){
	if(val != null)
	    software_id = val;
    }
    public void setSoftware_name(String val){
	//ignore for auto_complete
    }		
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    public void setLicense_id(String val){
	if(val != null)
	    license_id = val;
    }
    public void setKey_value(String val){
	if(val != null){
	    getLicense();
	    if(license != null){
		license.setKey_value(val);
	    }
	}
    }		
    public void setDate(String val){
	if(val != null){
	    date = val;
	    getLicense();
	    if(license != null){
		license.setCreated(val);
	    }
	}
    }
    public void setType(String val){
	if(val != null){
	    getLicense();
	    if(license != null){
		license.setType(val);
	    }
	}
    }		
    public void setEditable(String val){
	if(val != null)
	    editable = val;				
    }
    public void setDevice(Device val){
	if(val != null)
	    device = val;
    }
    public void setSoftware(Software val){
	if(val != null)
	    software = val;
    }
    public void setLicense(SoftwareLicense val){
	if(val != null)
	    license = val;
    }		
    public String toString(){
	return id;
    }
    public String saveImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
	    if(external_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else						
		pstmt.setString(jj++, external_id);
	    if(software_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, software_id);						
	    if(device_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, device_id);
	    if(license_id.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++, license_id);	
	    if(date.equals(""))
		pstmt.setNull(jj++, Types.DATE);
	    else
		pstmt.setString(jj++, date);								
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += ex;
	    System.err.println(id+" "+ex);
	}
	return msg;
		
    }
    public String updateImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
	    if(software_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, software_id);						
	    if(device_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, device_id);
	    if(license_id.equals(""))
		pstmt.setNull(jj++,Types.INTEGER);
	    else
		pstmt.setString(jj++, license_id);	
	    pstmt.setString(jj++, external_id);
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += ex;
	    System.err.println(id+" "+ex);
	}
	return msg;
		
    }				
		
    //
    public String doSelect(){
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select external_id,software_id,device_id,license_id,date_format(date,'%m/%d/%Y'),editable "+
	    "from software_installations where id=?";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }				
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setExternal_id(rs.getString(1));
		setSoftware_id(rs.getString(2));
		setDevice_id(rs.getString(3));
		setLicense_id(rs.getString(4));
		setDate(rs.getString(5));
		setEditable(rs.getString(6));
	    }
	    else{
		back ="Record "+id+" Not found";
		message = back;
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

	return back;
    }
    /**
     * this is used when new software is entered manually
     * that is why editable flag is on
     */
    public String doSave(){
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	if(license != null && license.hasData()){
	    msg = license.doSave();
	    if(msg.equals("")){
		license_id = license.getId();
	    }
	    else{
		System.err.println(msg);
	    }
	}
	if(license_id.equals("") || software_id.equals("") || device_id.equals("")){
	    msg = "software id, license id or deive id not set";
	    return msg;
	}
	String qq = "";
	qq = "insert into software_installations values(0,?,?,?,?,?,'y')";
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
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(external_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else						
		pstmt.setString(jj++, external_id);						
	    pstmt.setString(jj++, software_id);
	    pstmt.setString(jj++, device_id);
	    pstmt.setString(jj++, license_id);						
	    if(date.equals("")){
		date = Helper.getToday();
	    }
	    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));
	    editable = "y";
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);				
	    rs = pstmt.executeQuery();
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
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg; // success
    }
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	if(license_id.equals("") || software_id.equals("") || device_id.equals("")){
	    msg = "software id, license id or deive id not set";
	    return msg;
	}				
	qq = "update software_installations set external_id=?,software_id=?,device_id=?,license_id=?,date=? where id=?";
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
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(external_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else						
		pstmt.setString(jj++, external_id);								
	    pstmt.setString(jj++,software_id);
	    pstmt.setString(jj++,device_id);
	    pstmt.setString(jj++,license_id);						
	    if(date.equals(""))
		date = Helper.getToday();
	    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));
	    pstmt.setString(jj++, id);
	    pstmt.executeUpdate();
	    editable = "y";
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg; // success
    }		

}
