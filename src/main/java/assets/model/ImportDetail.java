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
/**
 * place holder for a bunch of attributes that will be used
 * in multiple classes
 */

public class ImportDetail extends CommonInc{
	
    static Logger logger = LogManager.getLogger(ImportDetail.class);
    static final long serialVersionUID = 1413L;	
    String import_id="", status="", type="", id="", error_message="", date_time="";
    public ImportDetail(){
	super();
    }
    public ImportDetail(boolean deb){
	super(deb);
    }		
    public ImportDetail(boolean deb,
			String val,
			String val2,
			String val3,
			String val4,
			String val5,
			String val6){

	super(deb);
	//
	// initialize
	//
	setId(val);
	setImport_id(val2);
	setType(val3);
	setStatus(val4);
	setErrorMessage(val5);
	setDate_time(val6);
    }	
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }		
    public void setImport_id(String val){
	if(val != null)
	    import_id = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    public void setDate_time(String val){
	if(val != null)
	    date_time = val;
    }		
    public void setStatus(String val){
	if(val != null)
	    status = val;
    }
    public void setErrorMessage(String val){
	if(val != null)
	    error_message = val;
    }		
    //
    // getters
    //
    public String  getId(){
	return id;
    }				
    public String  getImport_id(){
	return import_id;
    }		
    public String  getStatus(){
	return status;
    }	
    public String getType() {
	return type;
    }
    public String getErrorMessage() {
	return error_message;
    }		
    public String getDate_time() {
	return date_time;
    }
}






















































