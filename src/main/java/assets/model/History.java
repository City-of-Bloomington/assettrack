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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class History extends CommonInc{
	
    static Logger logger = LogManager.getLogger(History.class);
    static final long serialVersionUID = 1400L;	
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String status="", date="", updater_id="", id=""; // userid
    User changer = null;
    // basic constructor	
    public History(){

	super();
    }
    public History(boolean deb){

	super(deb);
    }	
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setUpdater_id(String val){
	if(val != null)
	    updater_id = val;
    }
    public void setDate(String val){
	if(val != null)
	    date = val;
    }	
    public void setStatus(String val){
	if(val != null){
	    status = val;
	}
    }
    public void setChanger(User val){
	if(val != null){
	    changer = val;
	    updater_id = changer.getId();
	}
    }
    //
    // getters
    //
    public String getId(){
	return id;
    }		
    public String getUpdater_id(){
	return updater_id;
    }
    public String getDate(){
	return date;
    }	
    public String getStatus(){
	return status;
    }
    public User getChanger(){
	if(changer == null && !updater_id.equals("")){
	    User one = new User(debug, updater_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		changer = one;
	    }
	}
	return changer;
    }

}






















































