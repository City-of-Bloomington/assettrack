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
import java.text.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class PrinterList extends CommonInc{

    static Logger logger = LogManager.getLogger(PrinterList.class);
    static final long serialVersionUID = 1560L;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		
    String name = "", id="", external_id="", print_processor="", device_id="",
	editable="", asset_num="", whichDate="u.date",
	inventory_status="";
    String date_from="", date_to="", status = "", limit =" limit 30 ";
    List<Printer> printers = null;
    public PrinterList(){
	super();
    }
    public PrinterList(boolean deb){
	super(deb);
    }		
    public PrinterList(boolean deb, String val){
	super(deb);
	setDevice_id(val);
    }
    public PrinterList(boolean deb, String val, String val2){
	super(deb);
	setDevice_id(val);
	setAsset_num(val2);
    }		
    public List<Printer> getPrinters(){
	return printers;
    }
    public void setDevice_id(String val){
	if(val != null)
	    device_id = val;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setAsset_num(String val){
	if(val != null)
	    asset_num = val;
    }		
    public void setPrint_processor(String val){
	if(val != null)
	    print_processor = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setEditable(String val){
	if(val != null)
	    editable = val;
    }
    public void setStatus(String val){
	if(val != null && !val.equals("-1"))
	    status = val;
    }
    public String getExternal_id() {
	return external_id;
    }
		
    public void setExternal_id(String val) {
	if(val != null)
	    external_id = val;
    }		
		
    public String getId(){
	return id;
    }
    public String getAsset_num(){
	return asset_num;
    }		
    public String getName(){
	return name;
    }
    public String getStatus(){
	if(status.equals("")){
	    return "-1";
	}
	return status;
    }
    public String getInventory_status() {
	if(status.equals("")){
	    return "-1";
	}
	return inventory_status;
    }				
    public String getDevice_id(){
	return device_id;
    }
    public String getPrint_processor(){
	return print_processor;
    }
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public String getEditable(){
	return editable;
    }
    public String getWhichDate(){
	return whichDate;
    }
    public void setWhichDate(String val){
	if(val != null)
	    whichDate = val;
    }
    public void setInventory_status(String val){
	if(val != null && !val.equals("-1"))
	    inventory_status = val;
    }		
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select u.id,u.external_id,u.asset_num,u.name,u.device_id,u.print_processor,u.description,date_format(u.date,'%m/%d/%Y'),u.status,u.notes,u.editable,u.serial_num,u.inventory_date from printers u ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	String qw="";
	if(!id.equals("")){
	    qw += " u.id = ? ";
	}
	else if(!external_id.equals("")){
	    qw += " u.external_id = ? ";
	}
	else if(!asset_num.equals("")){
	    qw += " u.asset_num = ? ";
	}				
	else {
	    if(!editable.equals("")){
		qw += " u.editable is not null ";
	    }				
	    if(!device_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " u.device_id = ? ";
	    }				
	    if(!name.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " u.name like ? ";
	    }
	    if(!print_processor.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " u.print_processor like ? ";
	    }
	    if(!date_from.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += whichDate+" >= ? ";
	    }
	    if(!date_to.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw +=  whichDate+" <= ? ";
	    }
	    if(!status.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " u.stats = ? ";
	    }
	    if(!inventory_status.equals("")){
		if(!qw.equals("")) qw += " and ";
		if(inventory_status.equals("set"))
		    qw += " u.inventory_date is not null ";
		else
		    qw += " u.inventory_date is null ";												
	    }									
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by id desc ";
	if(!limit.equals("")){
	    qq += limit;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!id.equals("")){
		pstmt.setString(jj++,id);
	    }
	    else if(!external_id.equals("")){
		pstmt.setString(jj++,external_id);
	    }
	    else if(!asset_num.equals("")){
		pstmt.setString(jj++,asset_num);
	    }						
	    else {
		if(!device_id.equals("")){
		    pstmt.setString(jj++,device_id);
		}
		if(!name.equals("")){
		    pstmt.setString(jj++, name+"%");
		}
		if(!print_processor.equals("")){
		    pstmt.setString(jj++,print_processor+"%");
		}
		if(!date_from.equals("")){
		    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));
		}
		if(!date_to.equals("")){
		    pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));
		}
		if(!status.equals("")){
		    pstmt.setString(jj++,status);
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(printers == null)
		    printers = new ArrayList<Printer>();
		Printer one =
		    new Printer(debug,
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(5),
				rs.getString(6),
				rs.getString(7),
				rs.getString(8),
				rs.getString(9),
				rs.getString(10),
				rs.getString(11),
				rs.getString(12),
				rs.getString(13));
		printers.add(one);
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






















































