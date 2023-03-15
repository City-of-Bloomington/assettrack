package assets.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

public class TicketStatsAction extends TopAction{

    static final long serialVersionUID = 1027L;	
    static Logger logger = LogManager.getLogger(TicketStatsAction.class);
    //
    Lot lot = null;
    String title = " Most recent active lots";
    TicketStats stats = null;
    String start_date = "", end_date = "";
    public String execute(){
	String ret = SUCCESS;
	String back = doPrepare();
	// if(!action.equals("")){
	getStats();
	stats.setStart_date(start_date);
	stats.setEnd_date(end_date);
	back = stats.findStats();
	if(!back.equals("")){
	    addActionError(back);
	}
	else{
	    addActionMessage("Saved Successfully");
	}
	return ret;
    }
    public void setTicketStats(TicketStats val){
	if(val != null)
	    stats = val;
    }
    public TicketStats getStats(){
	if(stats == null){
	    stats = new TicketStats(debug, start_date, end_date);
	}
	return stats;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getTitle(){
	return title;
    }
    public void setStart_date(String val){
	if(val != null && !val.equals(""))		
	    start_date = val;
    }
    public void setEnd_date(String val){
	if(val != null && !val.equals(""))		
	    end_date = val;
    }		
    public String getStart_date(){
	return start_date;
    }
    public String getEnd_date(){
	return end_date;
    }		

}





































