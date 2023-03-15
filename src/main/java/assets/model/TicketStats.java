package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;
/**
 * class to gather stats from the ticket table to provide performance 
 * service for dashboard app
 */

public class TicketStats extends CommonInc{
	
    static Logger logger = LogManager.getLogger(TicketStats.class);
    static final long serialVersionUID = 1455L;
    DecimalFormat dfl = new DecimalFormat("####0.00");
    // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());		
    SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		
    String start_date="", end_date="";
    int totalReceived = 0, totalOpen = 0, days=0, totalClosed=0;
    float avgResponse = 0f, avgOpen = 0f, avgClosed = 0f, avgReceived=0f;
		
    public TicketStats(){
	super();
    }
    public TicketStats(boolean deb,
		       String val,
		       String val2){
	super(deb);
	//
	// initialize
	//
	setStart_date(val);
	setEnd_date(val2);
    }
    //
    // setters
    //
    public void setStart_date(String val){
	if(val != null){
	    if(val.indexOf("/") > -1){
		start_date = val;
	    }
	    else if(val.indexOf("-") > -1){
		try{
		    java.util.Date date = df.parse(val);
		    start_date = df2.format(date);
		}catch(Exception ex){}
	    }
	}
    }
    public void setEnd_date(String val){
	if(val != null){
	    if(val.indexOf("/") > -1){
		end_date = val;
	    }
	    else if(val.indexOf("-") > -1){
		try{
		    java.util.Date date = df.parse(val);
		    end_date = df2.format(date);
		}catch(Exception ex){}
	    }
	}
    }
    public String getTotalOpen(){

	return ""+totalOpen;
    }
    public String getTotalClosed(){
	return ""+totalClosed;
    }
    public String getTotalReceived(){
	return ""+totalReceived;
    }		
    public String getAvgOpen(){
	return ""+dfl.format(avgOpen);
    }		
    public String getAvgClosed(){
	return ""+dfl.format(avgClosed);
    }
    public String getAvgResponse2(){
	String ret = "";
	int resp = (int)avgResponse/60; // minutes
	if(resp > 60){
	    int hrs = resp/60;
	    if(hrs == 1){
		ret = ""+hrs+" hour";
	    }
	    else{
		ret = ""+hrs+" hours";
	    }
	}
	int mnt = resp%60;
	if(mnt > 0){
	    if(!ret.equals("")) ret += " and ";
	    ret += mnt+" minutes";
	}
	return ret;
    }
    public String getAvgResponse(){ // seconds
	String ret = "";
	return ""+(int)avgResponse;

    }		
    public String getAvgReceived(){
	return ""+dfl.format(avgReceived);
    }		
    //
    // getters
    //
    public String getStart_date(){
	return start_date;
    }
    public String getEnd_date(){
	return end_date;
    }
    public String getStart_date_escaped(){
	if(!start_date.equals("")){
	    return Helper.escapeIt(start_date);
	}
	return start_date;
    }
    public String getEnd_date_escaped(){
	if(!end_date.equals("")){
	    return Helper.escapeIt(end_date);
	}
	return end_date;
    }		
    // this is needed if no date range was submitted
    private void setDates(){
	if(start_date.equals("") && end_date.equals("")){
	    end_date = Helper.getToday();
	    start_date = Helper.getDateFrom(end_date, -1)+" 00:00:00";
	    end_date = end_date+" 00:00:00";
	}
    }
    public String findStats(){
	setDates();
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select count(*) cnt, status status from tickets where created_at >= str_to_date(?,'%m/%d/%Y %H:%i:%s') and created_at <= str_to_date(?,'%m/%d/%Y %H:%i:%s') group by status ";
	String qq2 = " select count(*), sum(first_response_sec), datediff(max(created_at),min(created_at)) from tickets where created_at >= str_to_date(?,'%m/%d/%Y %H:%i:%s') and created_at <= str_to_date(?,'%m/%d/%Y %H:%i:%s') ";
	String qq3 = " select count(*) from tickets where closed_at >= str_to_date(?,'%m/%d/%Y %H:%i:%s') and closed_at <= str_to_date(?,'%m/%d/%Y %H:%i:%s') ";
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
	    pstmt.setString(1, start_date);
	    pstmt.setString(2, end_date);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		int cnt = rs.getInt(1);
		String str = rs.getString(2);
		totalReceived += cnt;
		if(str.equals("Open")){
		    totalOpen = cnt;
		}
	    }
	    if(debug){
		logger.debug(qq2);
	    }				
	    pstmt = con.prepareStatement(qq2);
	    pstmt.setString(1, start_date);
	    pstmt.setString(2, end_date);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		int totalResponse = rs.getInt(2); 
		days = rs.getInt(3);
		if(totalReceived > 0){
		    avgResponse = totalResponse/(totalReceived+0f);// minutes
		}
	    }
	    if(debug){
		logger.debug(qq3);
	    }				
	    pstmt = con.prepareStatement(qq3);
	    pstmt.setString(1, start_date);
	    pstmt.setString(2, end_date);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		totalClosed = rs.getInt(1);
	    }
	    if(days > 0){
		avgOpen = totalOpen/(days+0f);
		avgClosed = totalClosed/(days+0f);
		avgReceived = totalReceived/(days+0f);
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

}






















































