package assets;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.io.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;


public class RecycledItem extends Item{
	
		static Logger logger = Logger.getLogger(RecycledItem.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		static final long serialVersionUID = 1580L;	
    String location_id="";
		String weight="", description="";

		Type location = null;
    public RecycledItem(){

				//
				// initialize
				//
    }
		public RecycledItem(boolean deb,
												String val,
												String val2,
												String val3,
												String val4,
												String val5,
												String val6,
												String val7,
												String val8
												){

				super(deb, val, val2, val3, val4, val5);
				//
				// initialize
				//
				setLocation_id(val6);				
				setWeight(val7);
				setDescription(val8);
    }	
    //
    // setters
    //
    public void setLocation_id(String val){
				if(val != null)		
						location_id = val;
    }
    public void setWeight(String val){
				if(val != null)
						weight = val;
    }
    public void setDescription(String val){
				if(val != null)
						description = val;
    }		
		//
    // getters
    //
    public String  getWeight(){
				return weight;
    }
    public String  getDescription(){
				return description;
    }		
    public String  getLocation_id(){
				return location_id;
    }

		public Type getLocation(){
				if(location == null && !location_id.equals("")){
						Type one = new Type(debug, location_id, null, false,"recycle_locations");
						String back = one.doSelect();
						if(back.equals("")){
								location = one;
						}
				}
				return location;
		}	
		//
		public String doSave(){
		
				String back = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(asset_id.equals("") || location_id.equals("")){
						back = "recycle location or item not set ";
						addError(back);
						return back;
				}
				if(user == null && user_id.equals("")){
						back = "changer not set ";
						addError(back);
						return back;
				}
				String qq = "insert into recycled_items values(0,?,?,?,?,?,?,?)";
				//
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
						pstmt.setString(1, location_id);				
						pstmt.setString(2, asset_id);
						if(asset_num.equals(""))
								pstmt.setNull(3, Types.VARCHAR);
						else
								pstmt.setString(3, asset_num);						
						pstmt.setString(4, type);
						if(date.equals(""))
								date = Helper.getToday();
						pstmt.setDate(5, new java.sql.Date(dateFormat.parse(date).getTime()));						
						if(weight.equals(""))
								pstmt.setNull(6, Types.DOUBLE);
						else
								pstmt.setString(6, weight);
						if(description.equals(""))
								pstmt.setNull(7, Types.VARCHAR);
						else
								pstmt.setString(7, description);
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
								back = one.updateStatus("Recycled");								
								DeviceHistory ih = new DeviceHistory(debug, null, asset_id, "Recycled",null,user_id);
								back = ih.doSave();
								if(!back.equals("")){
										addError(back);
								}								
						}
						else if(type.equals("monitor")){
								Monitor one = new Monitor(debug, asset_id);
								back = one.updateStatus("Recycled");					
								if(!back.equals("")){
										addError(back);
								}		
						}
						else if (type.equals("printer")){
								Printer one = new Printer(debug, asset_id);
								back = one.updateStatus("Recycled");					
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
		// nothing to update
		public String doUpdate(){
		
				return "";
		}
	
		public String doDelete(){
		
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
				else{
						try{
								qq = "delete from recycled_items where id=?";
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
				}
				return back;
		}
		public String doSelect(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "select asset_id,asset_num,type,date_format(date,'%m/%d/%Y'),location_id,weight,description from recycled_items where id=?";		
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				else{
						try{
								if(debug){
										logger.debug(qq);
								}				
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										setAsset_id(rs.getString(1));
										setAsset_num(rs.getString(2));
										setType(rs.getString(3));
										setDate(rs.getString(4));
										setLocation_id(rs.getString(5));
										setWeight(rs.getString(6));
										setDescription(rs.getString(7));
								}
								else{
										back= "Record "+id+" Not found";
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
				}
				return back;
		}

}






















































