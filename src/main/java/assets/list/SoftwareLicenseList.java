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

public class SoftwareLicenseList extends CommonInc{

    static Logger logger = LogManager.getLogger(SoftwareLicenseList.class);
    static final long serialVersionUID = 1670L;
    String editable = "", key_value="", software_id="", device_id="",
	external_id="",limit="30";
    boolean editable_only = false; // all
    List<SoftwareLicense> licenses = null;
	
    public SoftwareLicenseList(){
    }
    public SoftwareLicenseList(boolean deb){
	super(deb);
    }
    public SoftwareLicenseList(boolean deb, String val){
	super(deb);
	setKey_value(val);
    }
    public List<SoftwareLicense> getLicenses(){
	return licenses;
    }


    public void setKey_value(String val){
	if(val != null)
	    key_value = val;
    }
    public void setSoftware_id(String val){
	if(val != null && !val.equals("-1"))
	    software_id = val;
    }
    public void setDevice_id(String val){
	if(val != null && !val.equals("-1"))
	    device_id = val;
    }		
    public void setEditable(String val){
	if(val != null){
	    editable = val;
	    if(!editable.equals("")){
		editable_only = true;
	    }
	}
    }
    public void setEditableOnly(){
	editable_only = true;
    }
    public void setNoLimit(){
	limit = "";
    }
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }				
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select l.id,l.external_id,l.key_value,l.type,date_format(l.created,'%m/%d/%Y'),l.seat_limit,l.editable from software_licenses l left join software_installations i on l.id=i.license_id ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(!external_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " l.external_id = ? ";
	    }						
	    if(editable_only){
		if(!qw.equals("")) qw += " and ";
		qw += " l.editable is not null ";
	    }
	    if(!key_value.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " l.key_value like ? ";
	    }
	    if(!software_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.software_id = ? ";
	    }
	    if(!device_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.device_id = ? ";
	    }						
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by l.created desc ";
	    if(!limit.equals("")){
		qq += " limit "+limit;
	    }
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!external_id.equals("")){
		pstmt.setString(jj++, external_id);
	    }						
	    if(!key_value.equals("")){
		pstmt.setString(jj++, key_value+"%");
	    }
	    if(!software_id.equals("")){
		pstmt.setString(jj++, software_id);
	    }
	    if(!device_id.equals("")){
		pstmt.setString(jj++, device_id);
	    }						
	    rs = pstmt.executeQuery();
	    if(licenses == null)
		licenses = new ArrayList<SoftwareLicense>();
	    while(rs.next()){
		SoftwareLicense one =
		    new SoftwareLicense(debug,
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7));
		licenses.add(one);
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






















































