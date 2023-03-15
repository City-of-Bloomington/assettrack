package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.text.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class SoftwareLicense extends CommonInc implements java.io.Serializable{

    String id="", key_value="", created="", editable="";
    String seat_limit="",type="", external_id="";
    static final long serialVersionUID = 1650L;	
    static Logger logger = LogManager.getLogger(SoftwareLicense.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    //
    public SoftwareLicense(){
	super();
    }
    //
    public SoftwareLicense(boolean deb){
	//
	debug = deb;
    }		
    public SoftwareLicense(boolean deb, String val){
	//
	debug = deb;
	setId(val);
    }
    //
    public SoftwareLicense(boolean deb, String val, String val2){
	super(deb);
	setId(val);
	setKey_value(val2);
    }
    //
    public SoftwareLicense(boolean deb, String val, String val2, String val3, String val4, String val5, String val6, String val7){
	super(deb);
	setId(val);
	setExternal_id(val2);
	setKey_value(val3);
	setType(val4);				
	setCreated(val5);
	setSeat_limit(val6);
	setEditable(val7);
    }		
    //
    // getters
    //
    public String getId(){
	return id;
    }
    public String getType(){
	return type;
    }		
    public String getKey_value(){
	return key_value;
    }
    public String getSeat_limit(){
	return seat_limit;
    }		
    public String getCreated(){
	return created;
    }
    public String getEditable(){
	return editable;
    }
    public boolean canEdit(){
	return !editable.equals("");
    }
    public boolean hasData(){
	return !key_value.equals("");
    }
    public String getExternal_id() {
	return external_id;
    }		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }				
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    public void setSeat_limit(String val){
	if(val != null)
	    seat_limit = val;
    }		
    public void setKey_value(String val){
	if(val != null)
	    key_value = val.trim();
    }
    public void setCreated(String val){
	if(val != null)
	    created = val;
    }		
    public void setEditable(String val){
	if(val != null)
	    editable = val;				
    }
    public String toString(){
	return key_value;
    }
    public String saveImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
	    if(external_id.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else								
		pstmt.setString(jj++, external_id);
	    if(key_value.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, key_value);						
	    if(type.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, type);
	    if(created.equals(""))
		pstmt.setNull(jj++,Types.DATE);
	    else
		pstmt.setString(jj++, created);	// as text
	    if(seat_limit.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, seat_limit);								
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += ex;
	    System.err.println(id+" "+ex);
	}
	return msg;
		
    }
    public String updateImport(PreparedStatement pstmt){
	String msg = "";
	try{
	    int jj=1;
	    if(key_value.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, key_value);						
	    if(type.equals(""))
		pstmt.setNull(jj++, Types.VARCHAR);
	    else
		pstmt.setString(jj++, type);
	    if(seat_limit.equals(""))
		pstmt.setNull(jj++, Types.INTEGER);
	    else
		pstmt.setString(jj++, seat_limit);
	    pstmt.setString(jj++, external_id);
						
	    pstmt.executeUpdate();
	}catch(Exception ex){
	    msg += ex;
	    System.err.println(id+" "+ex);
	}
	return msg;
		
    }				
    //
    public String doSelect(){
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select external_id,key_value,type,date_format(created,'%m/%d/%Y'),seat_limit,editable "+
	    "from software_licenses where id=?";
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
	    pstmt.setString(1,id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setExternal_id(rs.getString(1));
		setKey_value(rs.getString(2));
		setType(rs.getString(3));
		setCreated(rs.getString(4));
		setSeat_limit(rs.getString(5));
		setEditable(rs.getString(6));								
	    }
	    else{
		back ="Record "+id+" Not found";
		message = back;
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
    /**
     * this is used when new software is entered manually
     * that is why editable flag is on
     */
    public String doSave(){
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;		
	editable = "y";
	String str="", msg="";
	if(key_value.equals("")){
	    msg = "license key not set";
	    return msg;
	}
	String qq = "";
	qq = "insert into software_licenses values(0,?,?,?,?,?,'y')";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    pstmt = con.prepareStatement(qq);
	    if(external_id.equals(""))
		pstmt.setNull(1, Types.INTEGER);
	    else
		pstmt.setString(1, external_id);						
	    pstmt.setString(2,key_value);
	    if(type.equals(""))
		pstmt.setNull(3, Types.INTEGER);
	    else
		pstmt.setString(3, type);
	    if(created.equals("")){
		created = Helper.getToday();
	    }
	    pstmt.setDate(4, new java.sql.Date(dateFormat.parse(created).getTime()));
	    if(seat_limit.equals(""))
		pstmt.setNull(5, Types.INTEGER);
	    else
		pstmt.setString(5, seat_limit);						
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);				
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }						
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg; // success
    }
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	if(key_value.equals("")){
	    msg = "license key not set";
	    return msg;
	}				
	qq = "update software_licenses set external_id=?, key_value=?,type=?,created=?,seat_limit=? where id=?";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    pstmt = con.prepareStatement(qq);
	    if(external_id.equals(""))
		pstmt.setNull(1, Types.INTEGER);
	    else
		pstmt.setString(1, external_id);									
	    pstmt.setString(2,key_value);
	    if(type.equals(""))
		pstmt.setNull(3, Types.INTEGER);
	    else
		pstmt.setString(3, type);						
	    if(created.equals(""))
		created = Helper.getToday();
	    pstmt.setDate(4, new java.sql.Date(dateFormat.parse(created).getTime()));
	    if(seat_limit.equals(""))
		pstmt.setNull(5, Types.INTEGER);
	    else
		pstmt.setString(5, seat_limit);									
	    pstmt.setString(6, id);
	    pstmt.executeUpdate();
	    editable = "y";
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg; // success
    }		

}
