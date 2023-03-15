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

public class DeviceHistoryList extends CommonInc{

    static Logger logger = LogManager.getLogger(DeviceHistoryList.class);
    static final long serialVersionUID = 1190L;	
    String device_id="", updater_id="";
    String date_from="", date_to="";
    List<DeviceHistory> deviceHistory = null;
    //
    public DeviceHistoryList(){
	super();
    }
	
    public DeviceHistoryList(boolean val, String val2){
	super(val);
	setDevice_id(val2);
    }
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    public void setUpdater_id(String val){
	if(val != null)
	    updater_id = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }	
    public List<DeviceHistory> getDeviceHistory(){
	return deviceHistory;
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,device_id,status,date_format(date,'%m/%d/%Y'),updater_id "+
	    "from device_history ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    try{
		if(!device_id.equals("")){
		    qw += " device_id = ? ";
		}
		if(!updater_id.equals("")){
		    qw += " updater_id = ? ";
		}			
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
					
		    qw += " date >= str_to_date('"+date_from+";,'%m/%d/%Y')";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y')";
		}
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += " order by date DESC ";
		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		int jj = 1;
		if(!device_id.equals("")){
		    pstmt.setString(jj++, device_id);
		}
		if(!updater_id.equals("")){
		    pstmt.setString(jj++, updater_id);
		}	
		rs = pstmt.executeQuery();
		while(rs.next()){
		    if(deviceHistory == null)
			deviceHistory = new ArrayList<DeviceHistory>();
		    DeviceHistory one = new DeviceHistory(debug,
							  rs.getString(1),
							  rs.getString(2),
							  rs.getString(3),
							  rs.getString(4),
							  rs.getString(5));
		    deviceHistory.add(one);
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






















































