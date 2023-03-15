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

public class LotList extends CommonInc{

    static Logger logger = LogManager.getLogger(LotList.class);
    static final long serialVersionUID = 1075L;			
    String date_from="", date_to="", id="", name="", status="", type="";
    String active_id="";
    String limit = " limit 10 ";
    List<Lot> lots = null;
		
    public String getId(){
	return id;
    }
    public String getActive_id(){
	if(active_id.isEmpty())
	    return "-1";
	return active_id;
    }		
    public String getName(){
	return name;
    }
    public String getType(){
	if(type.equals("")) return "-1";
	return type;
    }
    public String getStatus(){
	if(status.equals("")) return "-1";
	return status;
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
	if(val != null && !val.isEmpty())
	    id = val;
    }
    public void setActive_id (String[] vals){
	if(vals != null && !vals[0].equals("-1"))
	    active_id = vals[0].trim();
    }		
    public void setName (String val){
	if(val != null)
	    name = val.trim();
    }
    public void setType (String val){
	if(val != null && !val.equals("-1"))
	    type = val;
    }
    public void setStatus (String val){
	if(val != null && !val.equals("-1"))
	    status = val.trim();
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val.trim();
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val.trim();
    }	
    public void setNoLimit(){
	limit = "";
    }
	
    public LotList(){

    }
    public LotList(boolean val){
	super(val);
    }
    public LotList(boolean val, String val2){
	super(val);
	setName(val2);
    }	
    public List<Lot> getLots(){
	return lots;
    }
	
    public String find(){
		
	String back = "", qw = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,name,date_format(date,'%m/%d/%Y'),type,status "+
	    " from lots ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	String id2 = "";
	if(!active_id.isEmpty()){
	    id2 = active_id;
	}
	else if(!id.isEmpty()){
	    id2 = id;
	}
	if(!id2.isEmpty()){
	    qw += " id = ? ";
	}
	else {
	    if(!name.isEmpty()){
		qw += " name like ? ";
	    }
	    if(!type.isEmpty()){
		qw += " type = ? ";
	    }
	    if(!status.isEmpty()){
		qw += " status = ? ";
	    }								
	    if(!date_from.isEmpty()){
		if(!qw.equals("")) qw += " and ";
		qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y') ";
	    }
	    if(!date_to.isEmpty()){
		if(!qw.equals("")) qw += " and ";
		qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y') ";
	    }					
	}
	try{
	    if(!qw.isEmpty()){
		qq += " where "+qw;
	    }
	    qq += " order by date DESC "+limit;
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    if(!id2.isEmpty()){
		pstmt.setString(1, id2);
	    }
	    else {
		int jj=1;
		if(!name.isEmpty()){
		    pstmt.setString(jj++, "%"+name+"%");
		}
		if(!type.isEmpty()){
		    pstmt.setString(jj++, type);
		}
		if(!status.isEmpty()){
		    pstmt.setString(jj++, status);
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		Lot one =
		    new Lot(debug,
			    rs.getString(1),
			    rs.getString(2),
			    rs.getString(3),
			    rs.getString(4),
			    rs.getString(5));
		if(lots == null)
		    lots = new ArrayList<Lot>();
		if(!lots.contains(one))
		    lots.add(one);
	    }
	    if(lots == null || lots.size() == 0){
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
	return back;
    }
}






















































