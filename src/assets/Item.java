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
    String asset_id="", date="", type="", id="";
		String user_id = "";
		User user = null;
		Object asset = null;
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
								String val4){

				super(deb);
				//
				// initialize
				//
				setId(val);
				setAsset_id(val2);
				setType(val3);				
				setDate(val4);
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
		public String  getDate(){
				return date;
    }	
		public String getType() {
				return type;
		}		
		public Object getAsset(){
				if(asset == null && !asset_id.equals("")){
						String back = "";
						if(type != null){
								if(type.equals("device")){
										Device one = new Device(debug, asset_id);
										back = one.doSelect();
										if(back.equals("")){
												asset = (Object)one;
										}
								}
								else if(type.equals("monitor")){
										Monitor one = new Monitor(debug, asset_id);
										back = one.doSelect();
										if(back.equals("")){
												asset = (Object)one;												
										}
								}
						}
				}
				return asset;
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






















































