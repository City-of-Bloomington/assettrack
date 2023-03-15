package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.ArrayList;
import java.util.*;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.utils.*;

public class PageList extends ArrayList<String>{

    String id="", name="", empid="";
    boolean debug = false, production = false;
    String errors = "";
    static final long serialVersionUID = 1530L;	
    String url = "";
    String urlStr = "";
    int count = 0, pageNumber = 1, pageSize = 0;
    String prevPage = "", nextPage = "";
    HashMap<String, String> map = new HashMap<String, String>();
    static Logger logger = LogManager.getLogger(PageList.class);
    public PageList(boolean deb){
	debug = deb;
    }
	
    public PageList(boolean deb, String url, int count, int pageSize, int pageNumber){
	debug = deb;
	this.url = url;
	this.count = count;
	this.pageSize = pageSize;
	this.pageNumber = pageNumber;
    }
    public void addPair(String name, String value){
	if(name != null && value != null && !name.equals("") && !value.equals("")){
	    map.put(name, value);
	}
    }
    public void setUrl(String val){
	if(val != null)
	    url = val;
    }
    public void setCount(int val){
	count = val;
    }
    public void setPageSize(String val){
	if(val != null){
	    try{
		pageSize = Integer.parseInt(val);
	    }catch(Exception ex){
		logger.error(ex);
	    }
	}
    }
    public void setPageSize(int val){
	pageSize = val;
    }	
    public void setPageNumber(String val){
	if(val != null){
	    try{
		pageNumber = Integer.parseInt(val);
	    }catch(Exception ex){
		logger.error(ex);
	    }	
	}
    }	
    public String getPrevPage(){
	return prevPage;
    }
    public String getNextPage(){
	return nextPage;
    }
    public int getCurrentPage(){
	return pageNumber;
    }
    public boolean hasPages(){
	return size() > 1;
    }
    public boolean hasNextPage(){
	return !nextPage.equals("");
    }
    public boolean hasPrevPage(){
	return !prevPage.equals("");
    }	
    void buildUrlStr(){
	urlStr = url;
	for( String key : map.keySet() ) {
	    String value = map.get( key );
	    urlStr += "&amp;"+key+"="+value;			
	}
	urlStr += "&amp;pageSize="+pageSize;
    }
    //
    public void build(){
	buildUrlStr();
	if(count > 1 && pageSize > 1 && count > pageSize){
	    int pageCount = count / pageSize;
	    if(pageCount * pageSize < count){
		pageCount++;
	    }
	    for(int i=1;i <= pageCount;i++){
		String str = urlStr+"&amp;pageNumber="+i;
		if((i+1) == pageNumber){ // one before
		    prevPage = str;
		}
		else if(i > 1 && i <= pageCount && (i-1) == pageNumber){
		    nextPage = str;
		}
		this.add(str);
	    }
	}
    }
    public String getPagesStr(){
	if(hasPages()){
	    String all = "Pages ";
	    int jj = 1;
	    if(hasPrevPage()){
		all += "<a href=\""+getPrevPage()+"\">Prev < </a> ";
	    }
	    for(String page:this){
		if(jj != getCurrentPage()){
		    all += "<a href=\""+page+"\">"+jj+"</a> ";
		}
		else{
		    all += jj+" ";
		}
		jj++;
	    }
	    if(hasNextPage()){	
		all += "<a href=\""+getNextPage()+"\">Next > </a> ";
	    }
	    return all;
	}
	return "";
    }
}
