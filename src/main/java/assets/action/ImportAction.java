package assets.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

public class ImportAction extends TopAction{

    static final long serialVersionUID = 1410L;	
    static Logger logger = LogManager.getLogger(DeptAction.class);
    //
    ImportManager impman = null;
    List<ImportManager> imports = null;
    String importsTitle = " Most recent imports";
    String detailsTitle = " This import details";
    public List<ImportDetail> details = null;
    public String execute(){
	String ret = SUCCESS;
	String back = doPrepare();
	if(!back.equals("")){
	    try{
		HttpServletResponse res = ServletActionContext.getResponse();
		String str = url+"Login";
		res.sendRedirect(str);
		return super.execute();
	    }catch(Exception ex){
		System.err.println(ex);
	    }	
	}
	if(action.equals("Run Imports")){
	    getImpman();						
	    impman.setSqliteDbFile(sqliteDbFile);
	    back = impman.doImport();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = impman.getId();
		addActionMessage("Data imported Successfully");
	    }
	}				
	else if(action.equals("Refresh")){
	    // nothing
	}				
	else if(!id.equals("")){
	    getImpman();
	    impman.setSqliteDbFile(sqliteDbFile);
	    back = impman.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		details = impman.getDetails();
		ret = "details";
	    }
	}
	else{
	    getImpman();
	    impman.setSqliteDbFile(sqliteDbFile);
	}
	return ret;
    }
    public ImportManager getImpman(){ 
	if(impman == null){
	    if(!id.equals("")){
		impman = new ImportManager(debug, id);
								
	    }
	    else{
		impman = new ImportManager(debug);
	    }
	}		
	return impman;
    }

    public void setImpman(ImportManager val){
	if(val != null){
	    impman = val;
	}
    }

    public String getImportsTitle(){
	return importsTitle;
    }
    public String getDetailsTitle(){
	return detailsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }		
    public String getId(){
	if(id.equals("") && impman != null){
	    id = impman.getId();
	}
	return id;
    }
    // most recent
    public List<ImportManager> getImports(){ 
	if(imports == null){
	    ImportManagerList dl = new ImportManagerList(debug);
	    // dl.setStatus("Active");
	    String back = dl.find();
	    imports = dl.getImports();
	}		
	return imports;
    }
    public List<ImportDetail> getDetails(){
	return details;
    }

}





































