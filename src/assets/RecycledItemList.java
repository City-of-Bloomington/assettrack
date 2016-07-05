package assets;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.log4j.Logger;


public class RecycledItemList extends CommonInc{

		static Logger logger = Logger.getLogger(RecycledItemList.class);
		static final long serialVersionUID = 1590L;	
		String location_id="";
		String asset_id = "", type="";
		String date_from = "", date_to="";
	
		RecycledItem recycledItem = null;
		ArrayList<RecycledItem> recycledItems = null;

		public RecycledItemList(){
				super();
		}
		public RecycledItemList(boolean val, String val2){
				super(val);
				setLocation_id(val2);
		}
		public void setLocation_id(String val){
				if(val != null)
						location_id = val;
		}
		public void setAsset_id(String val){
				if(val != null)
						asset_id = val;
		}
		public void setType(String val){
				if(val != null)
						type = val;
		}		
		public void setDate_from(String val){
				if(val != null)
						date_from = val.trim();
		}
		public void setDate_to(String val){
				if(val != null)
						date_to = val.trim();
		}
		public String  getAset_id(){
				return asset_id;
    }
		public String  getType(){
				return type;
    }		
		public String  getLocation_id(){
				return location_id;
    }
		public String  getDate_from(){
				return date_from;
    }
		public String  getDate_to(){
				return date_to;
    }	

		public List<RecycledItem> getRecycledItems(){
				return recycledItems;
		}

		public String find(){
		
				String back = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select r.id,r.asset_id,r.type,  "+
						" date_format(date,'%m/%d/%Y'),r.location_id,r.weight,r.description "+
						" from recycled_items r ";
				String qw = "";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				try{
						if(!location_id.equals("")){
								qw += " r.location_id = ? ";
						}
						if(!asset_id.equals("")){
								qw += " r.asset_id = ? ";
						}
						if(!type.equals("")){
								qw += " r.type = ? ";
						}						
						if(!date_from.equals("")){
								qw += " and r.date >= str_to_date('"+date_from+"','%m/%d/%Y')";
						}
						if(!date_to.equals("")){
								qw += " and r.date <= str_to_date('"+date_to+"','%m/%d/%Y')";
						}
						qq = qq + qw +" order by r.date DESC ";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						int jj = 1;
						if(!location_id.equals("")){
								pstmt.setString(jj++, location_id);
						}
						if(!asset_id.equals("")){
								pstmt.setString(jj++, asset_id);
						}
						if(!type.equals("")){
								pstmt.setString(jj++, type);
						}						
						rs = pstmt.executeQuery();
						while(rs.next()){
								if(recycledItems == null)
										recycledItems = new ArrayList<RecycledItem>();
								RecycledItem one =
										new RecycledItem(debug,
																		 rs.getString(1),
																		 rs.getString(2),
																		 rs.getString(3),
																		 rs.getString(4),
																		 rs.getString(5),
																		 rs.getString(6),
																		 rs.getString(7)
																		 );
								recycledItems.add(one);
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






















































