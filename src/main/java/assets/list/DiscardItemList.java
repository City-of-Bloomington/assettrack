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

public class DiscardItemList extends CommonInc{

    static Logger logger = LogManager.getLogger(DiscardItemList.class);
    static final long serialVersionUID = 1240L;			
    String method="", type="", date_from="", date_to="", item_id="";
    String limit = " limit 50 ";
		
    List<DiscardItem> discards = null;
    public DiscardItemList(){
	super();
    }
    public DiscardItemList(boolean val){
	super(val);
    }
	
    public DiscardItemList(boolean val, String val2){
	super(val);
	setMethod(val2);
    }
    public DiscardItemList(boolean val, String val2, String val3){
	super(val);
	setMethod(val2);
	setItem_id(val3);
    }		
    public void setItem_id(String val){
	if(val != null)
	    item_id = val;
    }	
    public void setMethod(String val){
	if(val != null)
	    method = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }	
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public List<DiscardItem> getDiscards(){
	return discards;
    }
    public String getItem_id(){
	return item_id;
    }
    public String getMethod(){
	return method;
    }
    public String getType(){
	return type;
    }		
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public void setNoLimit(){
	limit = "";
    }
				
		
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,item_id,asset_num,type,date_format(date,'%m/%d/%Y'),method, "+
	    "description from discarded_items ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	else{
	    try{
		if(!item_id.equals("")){
		    qw = " item_id = ? ";
		}
		if(!method.equals("")){
		    if(!qw.equals("")) qw += " and ";					
		    qw += " method = ? ";
		}
		if(!type.equals("")){
		    if(!qw.equals("")) qw += " and ";					
		    qw += " type = ? ";
		}								
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y') ";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y') ";
		}				
		if(!qw.equals("")) qw = " where "+qw;
		qq += qw;

		if(debug){
		    logger.debug(qq);
		}
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!item_id.equals("")){
		    pstmt.setString(jj++, item_id);
		}								
		if(!method.equals("")){
		    pstmt.setString(jj++, method);
		}
		if(!type.equals("")){
		    pstmt.setString(jj++, type);
		}								
		rs = pstmt.executeQuery();
		while(rs.next()){
		    DiscardItem pp = new DiscardItem(debug,
						     rs.getString(1),
						     rs.getString(2),
						     rs.getString(3),
						     rs.getString(4),
						     rs.getString(5),
						     rs.getString(6),
						     rs.getString(7));
		    if(discards == null)
			discards = new ArrayList<DiscardItem>();
		    discards.add(pp);
		}
		if(discards == null)
		    message="No match found";
	    }
	    catch(Exception ex){
		back += ex+" : "+qq;
		logger.error(back);
		addError(back);
	    }
	    finally{
		Helper.databaseDisconnect(con, pstmt, rs);
	    }
	}
	return back;
    }
}






















































