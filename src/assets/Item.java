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
/**
 * place holder for a bunch of attributes that will be used
 * in multiple classes
 */

public class Item extends CommonInc{
	
		static Logger logger = Logger.getLogger(Item.class);
		static final long serialVersionUID = 1450L;	
    String asset_id="", date="", type="", id="", asset_num="";
		String user_id = "";
		User user = null;
		Object asset = null;
		Device device = null;
		Monitor monitor = null;
		Printer printer = null;
    public Item(){
				super();
    }
    public Item(boolean deb){
				super(deb);
    }		
		public Item(boolean deb, String val, String val2, String val3){

				super(deb);
				//
				// initialize
				//
				setId(val);
				setAsset_id(val2);
				setType(val3);
    }	
		public Item(boolean deb,
								String val,
								String val2,
								String val3,
								String val4,
								String val5){

				super(deb);
				//
				// initialize
				//
				setId(val);
				setAsset_id(val2);
				setAsset_num(val3);
				setType(val4);				
				setDate(val5);
    }
    //
    // setters
    //
		public void setId(String val){
				if(val != null)
						id = val;
    }		
		public void setAsset_id(String val){
				if(val != null)
						asset_id = val;
    }
		public void setAsset_num(String val){
				if(val != null)
						asset_num = val;
    }		
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
    }		
		
		public void setDate(String val){
				if(val != null)
						date = val.trim();
    }	
		public void setType(String val) {
				if(val != null && !val.equals("")){
						type = val;
				}
		}		
    //
    // getters
    //
		public String  getId(){
				return id;
    }				
		public String  getAsset_id(){
				return asset_id;
    }
		public String  getAsset_num(){
				return asset_num;
    }		
		public String  getDate(){
				return date;
    }	
		public String getType() {
				return type;
		}		
		public Object getAsset(){
				if(asset == null){
						prepareAsset();
				}
				return asset;
		}
		void prepareAsset(){
								
				if(asset == null && !asset_id.equals("")){
						String back = "";
						if(type.equals("") && !asset_num.equals("")){
								findType();
						}
						if(!type.equals("")){
								if(type.equals("device")){
										Device one = new Device(debug, asset_id);
										back = one.doSelect();
										if(back.equals("")){
												device = one;
												asset_num = device.getAsset_num();
												asset = (Object)one;
										}
								}
								else if(type.equals("monitor")){
										Monitor one = new Monitor(debug, asset_id);
										back = one.doSelect();
										if(back.equals("")){
												monitor = one;
												asset_num = monitor.getAsset_num();
												asset = (Object)one;												
										}
								}
								else if(type.equals("printer")){
										Printer one = new Printer(debug, asset_id);
										back = one.doSelect();
										if(back.equals("")){
												printer = one;
												asset_num = printer.getAsset_num();
												asset = (Object)one;
										}
								}
						}
				}
		}
		/**
		 * if type not set and we have asset_num
		 * we can use that to find asset_id and type
		 */
		void findType(){

				String back = "";
				String qq = " (select id, 'device' from devices where asset_num=?) "+
						"union (select id, 'monitor' from monitors where asset_num=?) "+
						"union (select id, 'printer' from printers where asset_num=?) ";		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return ;
				}
				try{
						if(debug){
								logger.debug(qq);
						}				
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,asset_num);
						pstmt.setString(2,asset_num);
						pstmt.setString(3,asset_num);
						rs = pstmt.executeQuery();
						if(rs.next()){
								asset_id = rs.getString(1);
								type = rs.getString(2);
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
		public User getUser(){
				if(user == null && !user_id.equals("")){
						User one = new User(debug, user_id);
						String back = one.doSelect();
						if(back.equals("")){
								user = one;
						}
				}
				return user;
		}		
}






















































