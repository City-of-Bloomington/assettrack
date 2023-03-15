package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.util.List;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class DivisionList extends CommonInc{

    static Logger logger = LogManager.getLogger(DivisionList.class);
    static final long serialVersionUID = 1300L;
    boolean active_only = false;
    String name = "", dept_id="";
    List<Division> divisions = null;
    public DivisionList(){
	super();
    }	
    public DivisionList(boolean val){
	super(val);
    }
    public DivisionList(boolean val, String val2){
	super(val);
	setDept_id(val2);
    }		
    public List<Division> getDivisions(){
	return divisions;
    }
    public void setActiveOnly(){
	active_only = true;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setDept_id(String val){
	if(val != null)
	    dept_id = val;
    }		
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,dept_id,name,code,inactive "+
	    " from divisions ", qw="";
	if(active_only){
	    qw = " inactive is null ";
	}
	if(!name.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " name like ? ";
	}
	if(!dept_id.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " dept_id = ? ";
	}				
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq += " order by name ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!name.equals("")){
		pstmt.setString(jj++, "%"+name+"%");
	    }
	    if(!dept_id.equals("")){
		pstmt.setString(jj++, dept_id);
	    }						
	    rs = pstmt.executeQuery();
	    if(divisions == null)
		divisions = new ArrayList<Division>();
	    while(rs.next()){
		Division one =
		    new Division(debug,
				 rs.getString(1),
				 rs.getString(2),
				 rs.getString(3),
				 rs.getString(4),
				 rs.getString(5) != null && !rs.getString(5).equals(""));
		divisions.add(one);
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






















































