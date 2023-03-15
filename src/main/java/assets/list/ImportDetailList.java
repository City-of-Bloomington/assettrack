package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class ImportDetailList extends CommonInc{

    static Logger logger = LogManager.getLogger(ImportDetailList.class);
    static final long serialVersionUID = 1415L;
    String import_id="";
    List<ImportDetail> details = null;
    public ImportDetailList(boolean deb){
	//
	debug = deb;
    }
    public ImportDetailList(boolean deb, String val){
	//
	debug = deb;
	setImport_id(val);
    }		
    public void setImport_id(String val){
	if(val != null)
	    import_id = val;
    }
    public String getImport_id(){
	return import_id;
    }
    public List<ImportDetail> getDetails(){
	return details;
    }
		
    public String find(){
	String back = "", msg="";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select id,import_id,type,status,message,date_format(date_time,'%m/%d/%Y %H:%i') from import_details where import_id=? order by id ";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}				
	if(debug){
	    logger.debug(qq);
	}				
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, import_id);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(details == null){
		    details = new ArrayList<ImportDetail>();
		}
		ImportDetail one = new ImportDetail(debug, rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
		details.add(one);
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(ex);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;						
    }		

}
