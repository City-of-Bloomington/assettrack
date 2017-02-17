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

public class LotList extends CommonInc{

		static Logger logger = Logger.getLogger(LotList.class);
		static final long serialVersionUID = 1075L;			
		String date_from="", date_to="", id="", name="", status="", type="";
		String limit = " limit 10 ";
		List<Lot> lots = null;
		
    public String getId(){
				return id;
    }
    public String getName(){
				return name;
    }
    public String getType(){
				if(type.equals("")) return "-1";
				return type;
    }
    public String getStatus(){
				if(status.equals("")) return "-1";
				return status;
    }		
    public String getDate_from(){
				return date_from;
    }
    public String getDate_to(){
				return date_to;
    }	
    //
    // setters
    //
    public void setId (String val){
				if(val != null)
						id = val.trim();
    }
    public void setName (String val){
				if(val != null)
						name = val.trim();
    }
    public void setType (String val){
				if(val != null && !val.equals("-1"))
						type = val;
    }
		public void setStatus (String val){
				if(val != null && !val.equals("-1"))
						status = val.trim();
    }
    public void setDate_from(String val){
				if(val != null)
						date_from = val.trim();
    }
    public void setDate_to(String val){
				if(val != null)
						date_to = val.trim();
    }	
		public void setNoLimit(){
				limit = "";
		}
	
		public LotList(){

		}
		public LotList(boolean val){
				super(val);
		}
		public LotList(boolean val, String val2){
				super(val);
				setName(val2);
		}	
		public List<Lot> getLots(){
				return lots;
		}
	
		public String find(){
		
				String back = "", qw = "";
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				Connection con = Helper.getConnection();
				String qq = "select id,name,date_format(date,'%m/%d/%Y'),type,status "+
						" from lots ";
				if(con == null){
						back = "Could not connect to DB";
						addError(back);
						return back;
				}
				if(!id.equals("")){
						qw += " id = ? ";
				}
				else {
						if(!name.equals("")){
								qw += " name like ? ";
						}
						if(!type.equals("")){
								qw += " type = ? ";
						}
						if(!status.equals("")){
								qw += " status = ? ";
						}								
						if(!date_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y') ";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y') ";
						}					
				}
				try{
						if(!qw.equals("")){
								qq += " where "+qw;
						}
						qq += " order by date DESC "+limit;
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);
						if(!id.equals("")){
								pstmt.setString(1, id);
						}
						else {
								int jj=1;
								if(!name.equals("")){
										pstmt.setString(jj++, "%"+name+"%");
								}
								if(!type.equals("")){
										pstmt.setString(jj++, type);
								}
								if(!status.equals("")){
										pstmt.setString(jj++, status);
								}
						}
						rs = pstmt.executeQuery();
						while(rs.next()){
								Lot one =
										new Lot(debug,
														rs.getString(1),
														rs.getString(2),
														rs.getString(3),
														rs.getString(4),
														rs.getString(5));
								if(lots == null)
										lots = new ArrayList<Lot>();
								lots.add(one);
						}
						if(lots == null || lots.size() == 0){
								message= "No match found";
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






















































