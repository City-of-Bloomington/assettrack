package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class SoftwareList extends CommonInc{

    static Logger logger = LogManager.getLogger(SoftwareList.class);
    static final long serialVersionUID = 1680L;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		
    String vendor = "", name="", id="", date_from="", date_to=""; 
    String editable = "", external_id="", limit="30";
    boolean editable_only = false; // all
    List<Type> vendors = null;
    List<Software> softwares = null;
		
    public SoftwareList(){
    }
    public SoftwareList(boolean deb){
	super(deb);
    }
    public SoftwareList(boolean deb, String val){
	super(deb);
	setVendor(val);
    }
    public SoftwareList(boolean deb, String val, String val2){
	super(deb);
	setVendor(val);
	setEditable(val2);
    }		
    public List<Software> getSoftwares(){
	return softwares;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }				
    public void setVendor(String val){
	if(val != null && !val.equals("-1"))
	    vendor = val;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }		
    public void setEditable(String val){
	if(val != null){
	    editable = val;
	    if(!editable.equals("")){
		editable_only = true;
	    }
	}
    }
    public String getVendor(){
	if(vendor.equals("")){
	    return "-1";
	}
	return vendor;
    }
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }		
    public List<Type> getVendors(){
	if(vendors == null){
	    findVendors();
	}
	return vendors;
    }
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }			
    public void setEditableOnly(){
	editable_only = true;
    }
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,external_id,display_name,vendor,date_format(installed,'%m/%d/%Y'),install_count,editable from softwares ";
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
	    else {
		if(!vendor.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " vendor = ? ";
		}
		if(!name.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " display_name like ? ";
		}
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " installed >= ? ";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " installed <= ? ";
		}						
		if(editable_only){
		    if(!qw.equals("")) qw += " and ";
		    qw += " editable is null ";
		}
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by installed desc ";
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
	    else{
		if(!vendor.equals("")){
		    pstmt.setString(jj++, vendor);
		}
		if(!name.equals("")){
		    pstmt.setString(jj++, "%"+name+"%");
		}
		if(!date_from.equals("")){
		    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
		}
		if(!date_to.equals("")){
		    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
		}
	    }
	    rs = pstmt.executeQuery();
	    if(softwares == null)
		softwares = new ArrayList<Software>();
	    while(rs.next()){
		Software one =
		    new Software(debug,
				 rs.getString(1),
				 rs.getString(2),
				 rs.getString(3),
				 rs.getString(4),
				 rs.getString(5),
				 rs.getString(6),
				 rs.getString(7));
		softwares.add(one);
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
    public String findVendors(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select distinct(vendor) from softwares order by vendor ";
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
	    rs = pstmt.executeQuery();
	    if(vendors == null){
		vendors = new ArrayList<Type>();
		Type one = new Type(debug, "-1","All");
		vendors.add(one);
	    }
	    while(rs.next()){
		String str = rs.getString(1);
		if(str != null){
		    Type one = new Type(debug, str, str);
		    vendors.add(one);
		}
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






















































