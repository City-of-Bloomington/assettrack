package assets;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import javax.sql.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class DiscardItem extends Item{
		static Logger logger = Logger.getLogger(DiscardItem.class);
		static final long serialVersionUID = 1230L;			
		final static String METHODS[] = {"Consumed","Discard","Lost"};
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String method = ""; // Consumed, Lost, Discard
		String description = "";
		//
		public DiscardItem() {
				super();
		}
		
		public DiscardItem(boolean deb) {
				super(deb);
		}
		
		public DiscardItem(boolean deb,
											 String val,
											 String val2,
											 String val3,
											 String val4,
											 String val5,
											 String val6) {
				super(deb, val, val2, val3, val4);
				setMethod(val5);
				setDescription(val6);
		}	
		public String getMethod() {
				return method;
		}
		
		public String getDescription() {
				return description;
		}
		public void setMethod(String val) {
				if(val != null)
						this.method = val;
		}	
		public void setDescription(String val) {
				if(val != null)
						this.description = val;
		}
		public String toString(){
				String ret = "";
				ret += asset_id+" "+method+" "+date;
				return ret;
		}

		public String doSave(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "insert into discarded_items values(0,?,?,?,?,?)";
				if(asset_id.equals("")){
						back = "asset id not set ";
						logger.error(back);
						addError(back);
						return back;
				}
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						pstmt = con.prepareStatement(qq);
						if(debug){
								logger.debug(qq);
						}
						pstmt.setString(1,asset_id);
						pstmt.setString(2,type);
						if(date.equals(""))
								date = Helper.getToday();
						pstmt.setDate(3, new java.sql.Date(dateFormat.parse(date).getTime()));
						if(method.equals(""))
								pstmt.setNull(4,Types.VARCHAR);
						else
								pstmt.setString(4,method);
						if(description.equals(""))
								pstmt.setNull(5,Types.VARCHAR);
						else
								pstmt.setString(5,description);				
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
						if(type.equals("device")){
								Device one = new Device(debug, asset_id);
								back = one.updateStatus("Disposed");								
								DeviceHistory ih = new DeviceHistory(debug, null, asset_id, "Disposed",null,user_id);
								back = ih.doSave();
								if(!back.equals("")){
										addError(back);
								}								
						}
						else if(type.equals("monitor")){
								Monitor one = new Monitor(debug, asset_id);
								back = one.updateStatus("Disposed");					
								if(!back.equals("")){
										addError(back);
								}		
						}
						else if (type.equals("printer")){
								Printer one = new Printer(debug, asset_id);
								back = one.updateStatus("Disposed");					
								if(!back.equals("")){
										addError(back);
								}		
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
		public	String doUpdate(){
		
				String back = "";
				if(id.equals("")){
						back = "id not set ";
						logger.error(back);
						addError(back);
						return back;
				}
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String str="";
				String qq = "";
		
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "update discarded_items set item_id=?,type=?,date=?,"+
								"method=?,description=? "+
								"where id=?";
			
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,asset_id);												
						pstmt.setString(2,type);						
						pstmt.setDate(3, new java.sql.Date(dateFormat.parse(date).getTime()));				
						if(method.equals("")){
								pstmt.setNull(4,Types.INTEGER);
						}
						else{
								pstmt.setString(4,method);
						}
						if(description.equals("")){
								pstmt.setNull(5,Types.VARCHAR);
						}
						else{
								pstmt.setString(5,description);
						}
						pstmt.setString(6,id);
						pstmt.executeUpdate();
						message="Updated Successfully";
				}
				catch(Exception ex){
						back += ex+":"+qq;
						logger.error(qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return back;
		}
	
		public	String doDelete(){
		
				String back = "", qq = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
		
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						qq = "delete from discarded_items where id=?";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,id);
						pstmt.executeUpdate();
						message="Deleted Successfully";
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
	
    //
		public	String doSelect(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "select item_id,type,"+
						"date_format(date,'%m/%d/%Y'),method,description "+
						"from discarded_items where id=?";
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
								setAsset_id(rs.getString(1));
								setType(rs.getString(2));
								setDate(rs.getString(3));
								setMethod(rs.getString(4));
								setDescription(rs.getString(5));
						}
						else{
								back = "Record "+id+" Not found";
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

}
