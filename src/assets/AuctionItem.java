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


public class AuctionItem extends Item{
	
		static Logger logger = Logger.getLogger(AuctionItem.class);
		static final long serialVersionUID = 1040L;			
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    String auction_id="", description="";
		float value = 0.0f;
		Auction auction = null;

    public AuctionItem(){

				//
				// initialize
				//
    }
		public AuctionItem(boolean deb, String val, String val2){

				super(deb);
				//
				// initialize
				//
				setAuction_id(val);
				setAsset_id(val2);
    }
		/*
		public AuctionItem(boolean deb,
											 String val,
											 String val2,
											 String val3,
											 String val4,
											 String val5,
											 String val6){
				super(deb, val, val2, val3, val4, null); // no date for auction

				//
				// initialize
				//
				setAuction_id(val);
				setDescription(val5);
    }
		*/
		public AuctionItem(boolean deb,
											 String val,  // id
											 String val2, // asset_id
											 String val3, // asset_num
											 String val4, // type
											 String val5, // auction_id
											 String val6, // value
											 String val7){ // description

				super(deb, val, val2, val3, val4, null); // no date for auction
				//
				setAuction_id(val5);
				setValue(val6);
				setDescription(val7);
    }
    //
    // setters
    //
    public void setAuction_id(String val){
				if(val != null)		
						auction_id = val.trim();
    }
		public void setValue(String val) {
				if(val != null && !val.equals("")){
						try{
								value = Float.parseFloat(val);
						}catch(Exception ex){}
				}
		}
    public void setDescription(String val){
				if(val != null)		
					 description = val.trim();
    }		
    //
    // getters
    //
    public String  getAuction_id(){
				return auction_id;
    }
		public String getValue() {
				return ""+value;
		}
		public String getDescription() {
				return description;
		}		
		public Auction getAuction(){
				if(auction == null && !auction_id.equals("")){
						Auction one = new Auction(debug, auction_id);
						String back = one.doSelect();
						if(back.equals("")){
								auction = one;
						}
				}
				return auction;
		}	
		//
		public String doSave(){
		
				String back = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(asset_id.equals("") || auction_id.equals("")){
						back = "auction id or asset id not set ";
						addError(back);
				}
				prepareAsset();
				String qq = "insert into auction_items values(0,?,?,?,?,?,?)";
				//
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
								pstmt.setString(1,auction_id);				
								pstmt.setString(2,asset_id);
								if(!asset_num.equals(""))
										pstmt.setString(3,asset_num);
								else
										pstmt.setNull(3,Types.VARCHAR);								
								if(!type.equals(""))
										pstmt.setString(4,type);
								else
										pstmt.setNull(4,Types.INTEGER);
								pstmt.setFloat(5,value);
								if(!description.equals(""))
										pstmt.setString(6,description);
								else
										pstmt.setNull(6,Types.VARCHAR);								
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
								if(device != null){
										back = device.updateStatus("Auctioned");
										if(back.equals("")){
												DeviceHistory ih = new DeviceHistory(debug, null, asset_id, "Auctioned",null,user_id);
												back = ih.doSave();
										}
										if(!back.equals("")){
												addError(back);
										}
								}
								else if(monitor != null){
										back = monitor.updateStatus("Auctioned");					
										if(!back.equals("")){
												addError(back);
										}		
								}
								else if (printer != null){
										back = printer.updateStatus("Auctioned");					
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
				}
				return back;

		}
		public String doUpdate(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String str="";
				String qq = "";
				prepareAsset();
				con = Helper.getConnection();
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				else{
						try{
								qq = "update auction_items set ";
								qq += "auction_id=?,asset_id=?, asset_num=?,type=?,";
								qq += "value=?,description=? ";
								qq += "where id=? ";
								if(debug){
										logger.debug(qq);
								}
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,auction_id);
								pstmt.setString(2,asset_id);
								if(!asset_num.equals(""))
										pstmt.setString(3,asset_num);
								else
										pstmt.setNull(3,Types.VARCHAR);											
								if(!type.equals(""))
										pstmt.setString(4,type);
								else
										pstmt.setNull(4,Types.INTEGER);
								pstmt.setFloat(5,value);
								if(!description.equals(""))
										pstmt.setString(6,description);
								else
										pstmt.setNull(6,Types.VARCHAR);	
								pstmt.setString(7,id);		
								pstmt.executeUpdate();
								message="Updated Successfully";
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
								qq = "delete from auction_items where id=?";
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
	
    //
		public String doSelect(){
		
				String back = "";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "select asset_id,asset_num,type,auction_id,value,description "+
						" from auction_items where id=?";		
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
										setAuction_id(rs.getString(4));
										setValue(rs.getString(5));
										setDescription(rs.getString(6));										
								}
								else{
										back= "Record "+id+" Not found";
										message=back;
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






















































