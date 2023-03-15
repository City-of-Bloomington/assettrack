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

public class DonationList extends CommonInc{

    static Logger logger = LogManager.getLogger(DonationList.class);
    static final long serialVersionUID = 1330L;	
    String organization_id="", organ_name="", device_id="";
    String date_from="", date_to="", lot_id="";
    String limit = " limit 50 ";
    List<Donation> donations = null;
    //
    public DonationList(){
	super();
    }
	
    public DonationList(boolean val, String val2){
	super(val);
	setOrganization_id(val2);
    }
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    public void setLot_id(String val){
	if(val != null && !val.equals("-1"))
	    lot_id = val;
    }		
    public void setOrganization_id(String val){
	if(val != null)
	    organization_id = val.trim();
    }
    public void setOrgan_name(String val){ // not used in search we use vendor_id
	if(val != null)
	    organ_name = val.trim();
    }	
    public void setDate_from(String val){
	if(val != null)
	    date_from = val.trim();
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val.trim();
    }
    public String  getDevice_id(){
	return device_id;
    }
    public String  getLot_id(){
	return lot_id;
    }		
    public String  getOrganization_id(){
	return organization_id;
    }
    public String  getOrgan_name(){
	return organ_name;
    }	
    public String  getDate_from(){
	return date_from;
    }
    public String  getDate_to(){
	return date_to;
    }
    public void setNoLimit(){
	limit = "";
    }	
    public List<Donation> getDonations(){
	return donations;
    }
	
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,asset_id,asset_num,type,date_format(date,'%m/%d/%Y'),organization_id,value,lot_id from donations ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    try{
		if(!organization_id.equals("")){
		    qw += " organization_id = ? ";
		}
		if(!device_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " device_id = ? ";
		}
		if(!lot_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " lot_id = ? ";
		}								
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
					
		    qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y')";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y')";
		}
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += " order by date DESC "+limit;
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		int jj = 1;
		if(!organization_id.equals("")){
		    pstmt.setString(jj++, organization_id);
		}
		if(!device_id.equals("")){
		    pstmt.setString(jj++, device_id);
		}
		if(!lot_id.equals("")){
		    pstmt.setString(jj++, lot_id);
		}								
		rs = pstmt.executeQuery();
		while(rs.next()){
		    Donation one = new Donation(debug,
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8)
						);
		    if(donations == null)
			donations = new ArrayList<Donation>();
		    donations.add(one);
		}
		if(donations == null){
		    message = "No match found";
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

    List<String[]> dataList = new ArrayList<String[]>();
    public List<String[]> getDataList(){
	return dataList;
    }
    // String[] titleArray = {"Count","Type","Date","Organization","Total Value"};

    public String prepareReport(){

	// dataList.add(titleArray); // first row
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select "+
	    " o.name name, "+
	    " d.type type, "+
	    " date_format(d.date,'%m/%d/%Y') date,"+						
	    " count(*) "+
	    " from donations d,organizations o ";
	String qw = " d.organization_id=o.id ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    try{
		if(!organization_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.organization_id = ? ";
		}
		if(!lot_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.lot_id = ? ";
		}								
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
					
		    qw += " d.date >= str_to_date('"+date_from+"','%m/%d/%Y')";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " d.date <= str_to_date('"+date_to+"','%m/%d/%Y')";
		}
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += " group by type,date,name order by name,date ";
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		int jj = 1;
		if(!organization_id.equals("")){
		    pstmt.setString(jj++, organization_id);
		}
		if(!lot_id.equals("")){
		    pstmt.setString(jj++, lot_id);
		}								
		rs = pstmt.executeQuery();
		while(rs.next()){
		    String[] arr = new String[4];
		    for(int i=0;i<4;i++){
			String str = rs.getString(i+1);
			if(str != null){
			    arr[i] = str;
			}
			else
			    arr[i] = "";
		    }
		    dataList.add(arr);
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






















































