package assets.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.list.*;
import assets.utils.*;

public class Organization extends Type{

		String address = "", city="", state="", zip="", phone="",
				contact="";
		static Logger logger = LogManager.getLogger(Organization.class);
		static final long serialVersionUID = 1010L;			
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		//
		public Organization(){
				super(Helper.debug);
		}
		public Organization(boolean deb, String val){
				//
				// initialize
				//
				super(deb, val);
    }		
		public Organization(boolean deb, String val, String val2){
				//
				// initialize
				//
				super(deb, val, val2);
    }
		public Organization(boolean deb, String val, String val2, boolean val3){
				//
				// initialize
				//
				super(deb, val, val2, val3);
    }			

		public Organization(boolean deb,
												String val,
												String val2,
												boolean val3,
												String val4,
												String val5,
												String val6,
												String val7,
												String val8,
												String val9){
				//
				// initialize
				//
				super(deb, val, val2, val3);
				setAddress(val4);
				setCity(val5);
				setState(val6);
				setZip(val7);
				setContact(val8);
				setPhone(val9);
    }		
	
    //
    // getters
    //
		public String getAddress(){
				return address;
		}
		public String getCity(){
				return city;
		}
		public String getState(){
				return state;
		}
		public String getZip(){
				return zip;
		}		
		public String getPhone(){
				return phone;
		}
		public String getContact(){
				return contact;
		}		
    //
    // setters
    //
		public void setAddress(String val){
				if(val != null)
						address = val;
		}
		public void setCity(String val){
				if(val != null)
						city = val;
		}
		public void setState(String val){
				if(val != null)
						state = val;
		}
		public void setZip(String val){
				if(val != null)
						zip = val;
		}
		public void setContact(String val){
				if(val != null)
						contact = val;
		}
		public void setPhone(String val){
				if(val != null)
						phone = val;
		}		
		
    public String toString(){
				return name;
    }
		/**
		public List<AuctionItem> getAuctionItems(){

				if(auctionItems == null && !id.equals("")){
						AuctionItemList oil = new AuctionItemList(debug, id);
						String back = oil.find();
						if(back.equals("")){
								auctionItems = oil.getAuctionItems();
						}
				}
				return auctionItems;
		}
		*/
		public String doSave(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "insert into organizations values(0,?,null,?,?,?, ?,?,?)";
				if(name.equals("")){
						back = "organization name not set ";
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
				else{
						try{
								pstmt = con.prepareStatement(qq);
								if(debug){
										logger.debug(qq);
								}
								pstmt.setString(1,name);
								
								if(address.equals(""))
										pstmt.setNull(2,Types.VARCHAR);
								else
										pstmt.setString(2, address);
								if(city.equals(""))
										pstmt.setNull(3,Types.VARCHAR);
								else
										pstmt.setString(3, city);
								if(state.equals(""))
										pstmt.setNull(4,Types.VARCHAR);
								else
										pstmt.setString(4, state);
								if(zip.equals(""))
										pstmt.setNull(5,Types.VARCHAR);
								else
										pstmt.setString(5, zip);
								if(contact.equals(""))
										pstmt.setNull(6,Types.VARCHAR);
								else
										pstmt.setString(6, contact);
								if(phone.equals(""))
										pstmt.setNull(7,Types.VARCHAR);
								else
										pstmt.setString(7, phone);
								pstmt.executeUpdate();
								//
								// get the id of the new record
								//
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
								back += ex;
								logger.error(ex);
								addError(back);
						}
						finally{
								Helper.databaseDisconnect(con, pstmt, rs);
						}
				}
				return back;

		}
		public String doUpdate(){
		
				String back = "";
				if(name.equals("")){
						back = "organization name not set ";
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
				else{
						try{
								qq = "update organizations set name=?,inactive=?,address=?,city=?,state=?,zip=?,contact=?,phone=? where id=?";
				
								if(debug){
										logger.debug(qq);
								}
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,name);
								if(inactive.equals(""))
										pstmt.setNull(2,Types.CHAR);
								else
										pstmt.setString(2, "y");
								if(address.equals(""))
										pstmt.setNull(3,Types.VARCHAR);
								else
										pstmt.setString(3, address);
								if(city.equals(""))
										pstmt.setNull(4,Types.VARCHAR);
								else
										pstmt.setString(4, city);
								if(state.equals(""))
										pstmt.setNull(5,Types.VARCHAR);
								else
										pstmt.setString(5, state);
								if(zip.equals(""))
										pstmt.setNull(6,Types.VARCHAR);
								else
										pstmt.setString(6, zip);
								if(contact.equals(""))
										pstmt.setNull(7,Types.VARCHAR);
								else
										pstmt.setString(7, contact);
								if(phone.equals(""))
										pstmt.setNull(8,Types.VARCHAR);
								else
										pstmt.setString(8, phone);
								
								pstmt.setString(9,id);
								pstmt.executeUpdate();
						}
						catch(Exception ex){
								back += ex+":"+qq;
								logger.error(qq);
								addError(back);
						}
						finally{
								Helper.databaseDisconnect(con, pstmt, rs);
						}
				}
				return back;

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
								qq = "delete from organizations where id=?";
								if(debug){
										logger.debug(qq);
								}
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								pstmt.executeUpdate();
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
	
    //
		public String doSelect(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "select name,inactive,address,city,state,zip,contact,phone "+
						"from organizations where id=?";
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
								setName(rs.getString(1));
								setInactive(rs.getString(2) != null);
								setAddress(rs.getString(3));
								setCity(rs.getString(4));
								setState(rs.getString(5));
								setZip(rs.getString(6));
								setContact(rs.getString(7));
								setPhone(rs.getString(8));
						}
						else{
								back = "Record "+id+" not found";
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
