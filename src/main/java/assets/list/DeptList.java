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

public class DeptList extends CommonInc{

    static Logger logger = LogManager.getLogger(DeptList.class);
    static final long serialVersionUID = 1150L;
    boolean active_only = false;
    String name = "";
    List<Dept> depts = null;
    public DeptList(){
	super();
    }	
    public DeptList(boolean val){
	super(val);
    }
    public List<Dept> getDepts(){
	return depts;
    }
    public void setActiveOnly(){
	active_only = true;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
		
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,name,inactive "+
	    " from departments ", qw="";
	if(active_only){
	    qw = " inactive is null ";
	}
	if(!name.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " name like ? ";
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
	    if(!name.equals("")){
		pstmt.setString(1, "%"+name+"%");
	    }
	    rs = pstmt.executeQuery();
	    if(depts == null)
		depts = new ArrayList<Dept>();
	    while(rs.next()){
		Dept one =
		    new Dept(debug,
			     rs.getString(1),
			     rs.getString(2),
			     rs.getString(3) != null && !rs.getString(4).equals(""));
		depts.add(one);
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






















































