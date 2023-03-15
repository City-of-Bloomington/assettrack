package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.text.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Software extends CommonInc implements java.io.Serializable{

    String id="", external_id="", display_name="", vendor="", installed="", install_count="1",editable="";
    static final long serialVersionUID = 1600L;	
    static Logger logger = LogManager.getLogger(Software.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    List<SoftwareInstallation> installations = null;
    //
    public Software(){
	super();
    }
    //
    public Software(boolean deb, String val){
	//
	// initialize
	//
	debug = deb;
	setId(val);
    }
    //
    public Software(boolean deb, String val, String val2){
	super(deb);
	setId(val);
	setDisplay_name(val2);
    }
    //
    public Software(boolean deb, String val, String val2, String val3, String val4, String val5,String val6, String val7){
	super(deb);
	setId(val);
	setExternal_id(val2);
	setDisplay_name(val3);
	setVendor(val4);
	setInstalled(val5);
	setInstall_count(val6);
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
    public String getDisplay_name(){
	return display_name;
    }
    public String getVendor(){
	return vendor;
    }
    public String getInstalled(){
	return installed;
    }
    public String getEditable(){
	return editable;
    }
    public String getInstall_count(){
	return install_count;
    }
    public boolean canEdit(){
	return !editable.equals("");
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
    public void setDisplay_name(String val){
	if(val != null)
	    display_name = val.trim();
    }
    public void setVendor(String val){
	if(val != null)
	    vendor = val.trim();
    }
    public void setInstalled(String val){
	if(val != null)
	    installed = val;
    }		
    public void setInstall_count(String val){
	if(val != null)
	    install_count = val;
    }
    public void setEditable(String val){
	if(val != null)
	    editable = val;				
    }
    public String toString(){
	return display_name;
    }

    public boolean hasInstallations(){
	getInstallations();
	return installations != null && installations.size() > 0;

    }
    public List<SoftwareInstallation> getInstallations(){
	SoftwareInstallationList sil = new SoftwareInstallationList(debug, null, id, null);
	String back = sil.find();
	if(back.equals("")){
	    List<SoftwareInstallation> ones = sil.getInstallations();
	    if(ones != null && ones.size() > 0){
		installations = ones;
	    }
	}
	return installations;
    }
    //
    public String doSelect(){
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select external_id,display_name,vendor,date_format(installed,'%m/%d/%Y'),install_count,editable from softwares where id=?";
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
		setDisplay_name(rs.getString(2));
		setVendor(rs.getString(3));
		setInstalled(rs.getString(4));
		setInstall_count(rs.getString(5));
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
    public String saveImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
	    pstmt.setString(jj++, external_id);
	    if(display_name.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, display_name);						
	    if(vendor.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, vendor);
	    if(installed.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setString(jj++, installed);	// as text
	    if(install_count.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, install_count);								
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
	    if(display_name.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, display_name);						
	    if(vendor.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, vendor);
	    if(install_count.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, install_count);
	    pstmt.setString(jj++, external_id);
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += ex;
	    System.err.println(id+" "+ex);
	}
	return msg;
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
	if(display_name.equals("") || vendor.equals("")){
	    msg = "software name or vendor not set";
	    return msg;
	}
	String qq = "";
	qq = "insert into softwares values(0,?,?,?,?,?,'y')";
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
	    if(external_id.equals("")){
		pstmt.setNull(1, Types.INTEGER);
	    }
	    else {
		pstmt.setString(1,external_id);
	    }
	    pstmt.setString(2,display_name);
	    pstmt.setString(3,vendor);
	    if(installed.equals("")){
		installed = Helper.getToday();
	    }
	    pstmt.setDate(4, new java.sql.Date(dateFormat.parse(installed).getTime()));										
	    pstmt.setString(5,install_count);
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
	qq = "update softwares set display_name=?,vendor=?,installed=?,install_count=? where id=?";
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
	    int jj=1;
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(jj++,display_name);
	    pstmt.setString(jj++,vendor);
	    if(installed.equals(""))
		installed = Helper.getToday();
	    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(installed).getTime()));
	    pstmt.setString(jj++, install_count);
	    pstmt.setString(jj++, id);
	    pstmt.executeUpdate();
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
	if(msg.equals("")){
	    msg = doSelect();
	}
	return msg; // success
    }		

}
