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

public class SoftwareInstallationList extends CommonInc{

    static Logger logger = LogManager.getLogger(SoftwareInstallationList.class);
    static final long serialVersionUID = 1640L;
    String device_id = "", software_id="", license_id="", external_id=""; 
    String editable = "", limit = "30";
    boolean editable_only = false; // all
    List<SoftwareInstallation> installations = null;
	
    public SoftwareInstallationList(){
    }
    public SoftwareInstallationList(boolean deb){
	super(deb);
    }
    public SoftwareInstallationList(boolean deb,
				    String val,
				    String val2,
				    String val3){
	super(deb);
	setDevice_id(val);
	setSoftware_id(val2);
	setLicense_id(val3);
	setNoLimit();
    }
    public List<SoftwareInstallation> getInstallations(){
	return installations;
    }
		
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    public void setSoftware_id(String val){
	if(val != null)
	    software_id = val;
    }
    public void setLicense_id(String val){
	if(val != null)
	    license_id = val;
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
	String qq = "select i.id,i.external_id,i.software_id,i.device_id,i.license_id,"+
	    " date_format(i.date,'%m/%d/%Y'),i.editable "+ // 6
	    " from software_installations i ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(!external_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.external_id = ? ";
	    }						
	    if(!device_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.device_id = ? ";
	    }
	    if(!software_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.software_id = ? ";
	    }
	    if(!license_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.license_id = ? ";
	    }						
	    if(editable_only){
		if(!qw.equals("")) qw += " and ";
		qw += " i.editable is not null ";
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by i.date desc ";
	    if(!limit.equals("")){
		qq += " limit "+limit;
	    }
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!external_id.equals("")){
		pstmt.setString(jj++,external_id);
	    }						
	    if(!device_id.equals("")){
		pstmt.setString(jj++,device_id);
	    }
	    if(!software_id.equals("")){
		pstmt.setString(jj++,software_id);
	    }
	    if(!license_id.equals("")){
		pstmt.setString(jj++,license_id);
	    }						
	    rs = pstmt.executeQuery();
	    if(installations == null)
		installations = new ArrayList<SoftwareInstallation>();
	    while(rs.next()){
		SoftwareInstallation one =
		    new SoftwareInstallation(debug,
					     rs.getString(1),
					     rs.getString(2),
					     rs.getString(3),
					     rs.getString(4),
					     rs.getString(5),
					     rs.getString(6),
					     rs.getString(7));
		installations.add(one);
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






















































