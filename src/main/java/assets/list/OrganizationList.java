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

public class OrganizationList extends CommonInc{

    static Logger logger = LogManager.getLogger(OrganizationList.class);
    static final long serialVersionUID = 1075L;			
    String date_from="", date_to="", id="", name="";
    boolean active_only = false;
    List<Organization> organizations = null;

		
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
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val.trim();
    }
    public void setName (String val){
	if(val != null)
	    name = val.trim();
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val.trim();
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val.trim();
    }
    public void setActiveOnly(){
	active_only = true;
    }

	
    public OrganizationList(){

    }
    public OrganizationList(boolean val){
	super(val);
    }
    public OrganizationList(boolean val, String val2){
	super(val);
	setName(val2);
    }	
    public List<Organization> getOrganizations(){
	return organizations;
    }
	
    public String findShort(){
		
	String back = "", qw = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,name,inactive  "+
	    " from organizations ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    if(!id.equals("")){
		qw += " id = ? ";
	    }
	    else {
		if(!name.equals("")){
		    qw += " name like ? ";
		}
		if(active_only){
		    if(!qw.equals("")) qw += " and ";
		    qw += " inactive is null ";
		}
		/*
		  if(!date_from.equals("")){
		  if(!qw.equals("")) qw += " and ";
		  qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y') ";
		  }
		  if(!date_to.equals("")){
		  if(!qw.equals("")) qw += " and ";
		  qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y') ";
		  }
		*/
	    }
	    try{
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += " order by name ";
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		if(!id.equals("")){
		    pstmt.setString(1, id);
		}
		else {
		    if(!name.equals("")){
			pstmt.setString(1, "%"+name+"%");
		    }
		}
		rs = pstmt.executeQuery();
		while(rs.next()){
		    Organization one =
			new Organization(debug,
					 rs.getString(1),
					 rs.getString(2),
					 rs.getString(3) != null);
		    if(organizations == null)
			organizations = new ArrayList<Organization>();
		    organizations.add(one);
		}
		if(organizations == null || organizations.size() == 0){
		    message= "No match found";
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
	}
	return back;
    }
    public String find(){
		
	String back = "", qw = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,name,inactive,address,city,state,zip,contact,phone  "+
	    " from organizations ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    if(!id.equals("")){
		qw += " id = ? ";
	    }
	    else {
		if(!name.equals("")){
		    qw += " name like ? ";
		}
		if(active_only){
		    if(!qw.equals("")) qw += " and ";
		    qw += " inactive is null ";
		}
		/*
		  if(!date_from.equals("")){
		  if(!qw.equals("")) qw += " and ";
		  qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y') ";
		  }
		  if(!date_to.equals("")){
		  if(!qw.equals("")) qw += " and ";
		  qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y') ";
		  }
		*/
	    }
	    try{
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += " order by name ";
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		if(!id.equals("")){
		    pstmt.setString(1, id);
		}
		else {
		    if(!name.equals("")){
			pstmt.setString(1, "%"+name+"%");
		    }
		}
		rs = pstmt.executeQuery();
		while(rs.next()){
		    Organization one =
			new Organization(debug,
					 rs.getString(1),
					 rs.getString(2),
					 rs.getString(3) != null,
					 rs.getString(4),
					 rs.getString(5),
					 rs.getString(6),
					 rs.getString(7),
					 rs.getString(8),
					 rs.getString(9));
		    if(organizations == null)
			organizations = new ArrayList<Organization>();
		    organizations.add(one);
		}
		if(organizations == null || organizations.size() == 0){
		    message= "No match found";
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
	}
	return back;
    }		

		
}






















































