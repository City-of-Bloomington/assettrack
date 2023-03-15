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

public class ImportManagerList extends CommonInc{

    String date_from = "", date_to="";
    static Logger logger = LogManager.getLogger(ImportManagerList.class);
    static final long serialVersionUID = 1430L;
		
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    List<ImportManager> imports = null;
    public ImportManagerList(boolean deb){
	//
	debug = deb;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }		
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public List<ImportManager> getImports(){
	return imports;
    }
		
    public String find(){
	String back = "", msg="";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select id,date_format(date,'%m/%d/%Y') from data_imports order by id DESC limit 5 ";
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
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(imports == null){
		    imports = new ArrayList<ImportManager>();
		}
		ImportManager one = new ImportManager(debug, rs.getString(1), rs.getString(2));
		imports.add(one);
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
