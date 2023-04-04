package assets.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.utils.*;
import assets.model.*;

public class EmpList extends CommonInc{

    static Logger logger = LogManager.getLogger(EmpList.class);
    static final long serialVersionUID = 1100L;
    static EnvBean bean = null;
    String name = "", dept="";
    List<Employee> emps = null;
    
    public EmpList(){
	super();
    }

    // we do not tables here
    public EmpList(EnvBean val, String val2){
	setEnvBean(val);
	setName(val2);
    }		
		
    public List<Employee> getEmps(){
	return emps;
    }
    public void setName(String val){
	if(val != null){
	    name = val;
	}
    }

    public void setEnvBean(EnvBean val){
	if(val != null)
	    bean = val;
    }		
    public String getName(){
	return name;
    }


    boolean connectToServer(Hashtable<String, String> env){

	if(env != null && bean != null){
	    env.put(Context.INITIAL_CONTEXT_FACTORY, 
		    "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, bean.getUrl());
	    env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
	    env.put(Context.SECURITY_PRINCIPAL, bean.getPrinciple());
	    env.put(Context.SECURITY_CREDENTIALS, bean.getPassword());
	    env.put("java.naming.ldap.attributes.binary","objectSID");
	}
	else{
	    return false;
	}
	try {
	    DirContext ctx = new InitialDirContext(env);
	} catch (NamingException ex) {
	    System.err.println(" ldap "+ex);
	    return false;
	}
	return true;
    }		
    public String find(){
	Hashtable<String, String> env = new Hashtable<String, String>(11);
	String back = "", fullName="", str="";
	Employee emp = null;
	if (!connectToServer(env)){
	    System.err.println("Unable to connect to ldap");
	    return null;
	}
	try{
	    //
	    DirContext ctx = new InitialDirContext(env);
	    SearchControls ctls = new SearchControls();
	    ctls.setCountLimit(2000);
	    String[] attrIDs = {"objectSID",
		"givenName",
		"department", // not accurate use dn instead
		"telephoneNumber",
		"mail",
		"cn",
		"sAMAccountName",
		"sn",
		"distinguishedName",
		"dn",
		"businessCategory",
		"employeeNumber",
		"employeeId", // id_code
		"title"};
	    //
	    ctls.setReturningAttributes(attrIDs);
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    String filter = "";
	    if(!name.isEmpty()){
		filter = "(sAMAccountName="+name+"*)";
	    }
	    else{ // all
		// we are excluding disabled users and any user that
		// givenName (first name) that starts with * code \2a 				
		filter ="(&(objectCategory=person)(objectCategory=user)(!(givenName=\2*)))";
	    }
	    System.err.println(" filter "+filter);
	    NamingEnumeration<SearchResult> answer = ctx.search("", filter, ctls);
	    int jj=1;
	    while(answer.hasMore()){
		//
		emp = new Employee();
		SearchResult sr = answer.next();
		Attributes atts = sr.getAttributes();
		Attribute dn = atts.get("distinguishedName");
		if (dn != null){
		    str = dn.get().toString();
		    // System.err.println("dn "+str);										
		    String strArr[] = setDn(str);
		}
		//
		Attribute cn = atts.get("sAMAccountName");
		if (cn != null){
		    str = cn.get().toString();
		    emp.setUsername(str);
		}
		Attribute givenname = atts.get("givenName");
		if (givenname != null){
		    str = givenname.get().toString();
		    if(str.indexOf("*") > -1) continue;
		    emp.setFirst_name(str);
		}
		Attribute sn = atts.get("sn");
		if (sn != null){
		    str = sn.get().toString();
		    emp.setLast_name(str);
		}
		Attribute en = atts.get("telephoneNumber");
		if (en != null){
		    str = en.get().toString();
		    emp.setOffice_phone(str);
		}		
		/**
		Attribute en = atts.get("employeeNumber");
		if (en != null){
		    str = en.get().toString();
		    emp.setEmployee_number(str);
		}
		Attribute ei = atts.get("employeeId");
		if (ei != null){
		    str = ei.get().toString();
		    emp.setId_code(str);
		}
		Attribute email = (Attribute)(atts.get("mail"));
		if (email != null){
		    str = email.get().toString();
		    emp.setEmail(str);
		}
		
		*/
		if(emps == null){
		    emps = new ArrayList<>();
		}

		if(!emp.getUsername().startsWith("*")){
		    emps.add(emp);
		}
		// System.err.println(jj+" emp "+emp.getInfo());
		// jj++;
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	}
	return back;
    }
    public static String getStringFromBytes(byte[] sid) {
	String str = "";
	System.err.println(" size "+sid.length);
	/*
	  for(byte b: sid){
	  str += byte2hex(b);
	  }
	*/
	for(byte b: sid){
	    str += String.format("%02x", b & 255);
	}
	return str;
    }
    public static String byte2hex(byte b) {
	String ret = Integer.toHexString((int)b&0xFF);
	if (ret.length()<2) ret = "0"+ret;
	return ret;
    }
    public String simpleFind(){
	Hashtable<String, String> env = new Hashtable<String, String>(11);
	String back = "", fullName="", str="";
	Employee emp = null;
	if (!connectToServer(env)){
	    System.err.println("Unable to connect to ldap");
	    return null;
	}
	try{
	    //
	    DirContext ctx = new InitialDirContext(env);
	    SearchControls ctls = new SearchControls();
	    String[] attrIDs = {"objectSid",
		"givenName",
		"department", // not accurate use dn instead
		"telephoneNumber",
		"mail",
		"cn", // mostly full name
		"sAMAccountName",
		"sn",
		"distinguishedName",
		"dn",
		"businessCategory",
		"employeeNumber",
		"employeeId", // id_code
		"title"};
	    //
	    ctls.setReturningAttributes(attrIDs);
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    String filter = "";
	    if(!name.isEmpty()){
		filter = "(sAMAccountName="+name+"*)";
	    }
	    else{ // all
		filter ="(&(objectCategory=person)(objectClass=user))";
	    }
	    System.err.println(" filter "+filter);
	    NamingEnumeration<SearchResult> answer = ctx.search("", filter, ctls);
	    while(answer.hasMore()){
		//
		emp = new Employee();
		SearchResult sr = answer.next();
		Attributes atts = sr.getAttributes();
		Attribute dn = atts.get("distinguishedName");
		/*
		  Attribute cn = atts.get("cn");
		  if (cn != null){
		  str = cn.get().toString();
		  emp.setUsername(str);
		  }
		*/
		Attribute cn = atts.get("sAMAccountName");
		if (cn != null){
		    str = cn.get().toString();
		    emp.setUsername(str);
		}								
		Attribute givenname = atts.get("givenName");
		if (givenname != null){
		    str = givenname.get().toString();
		    emp.setFirst_name(str);
		}
		Attribute sn = atts.get("sn");
		if (sn != null){
		    str = sn.get().toString();
		    emp.setLast_name(str);
		}
		Attribute en = atts.get("employeeNumber");
		if (en != null){
		    str = en.get().toString();
		    // emp.setEmployee_number(str);
		}
		Attribute ei = atts.get("employeeId");
		if (ei != null){
		    str = ei.get().toString();
		    // emp.setId_code(str);
		}
		Attribute email = (Attribute)(atts.get("mail"));
		if (email != null){
		    str = email.get().toString();
		    // emp.setEmail(str);
		}
		if(emps == null){
		    emps = new ArrayList<>();
		}
		// try to avoid made up names like "*parks-user";
		if(!emp.getUsername().startsWith("*")){
		    emps.add(emp);
		}
		System.err.println(" emp "+emp);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	}
	return back;
    }		
    String[] setDn(String val){
	String retArr[] = {"",""};
	if(val != null){
	    String dept="", grp = "", sub_grp="";
	    try{
		String val2 = val.substring(val.indexOf("OU"),val.indexOf("DC")-1);
		String strArr[] = val2.split(",");
		if(strArr != null){
		    if(strArr.length == 2){
			dept = strArr[0]; // transit
		    }
		    if(strArr.length == 3){
			if(val2.indexOf("City Hall") > 0){
			    dept = strArr[0];
			}
			else if(val2.indexOf("Utilities") > 0){
			    dept = strArr[0];
			    // strArr[1]; // utilities
			    // strArr[2] // Departments
			}
		    }
		    else if(strArr.length == 4){
			if(val2.indexOf("City Hall") > 0){
			    grp = strArr[0];
			    dept = strArr[1];
			}
			else{
			    // example maintenance,Dillman Plant,Utilities
			    if(strArr[3].indexOf("Utilities")>-1){
				sub_grp = strArr[0];
				grp = strArr[1];
				dept = strArr[2]; // ignore Uitilities
			    }
			    else if(strArr[2].indexOf("Utilities")>-1){
				grp = strArr[0];
				dept = strArr[1];
				// dept = strArr[2]; // ignore Uitilities
			    }
			    else if(strArr[1].indexOf("Utilities")> -1){
				dept = strArr[0];
			    }
			    else{
				sub_grp = strArr[0];
				grp = strArr[1];
				dept = strArr[2];
			    }
			}
		    }
		    if(!dept.isEmpty()){
			dept = dept.substring(dept.indexOf("=")+1);
			retArr[0] = dept;
		    }
		    if(!grp.isEmpty()){
			grp = grp.substring(grp.indexOf("=")+1);
			if(grp.equals("Showers")) grp="Staff";
		    }
		    else{
			grp = "Staff";
		    }
		    if(!sub_grp.isEmpty()){
			sub_grp = sub_grp.substring(sub_grp.indexOf("=")+1);
			grp = grp+" - "+sub_grp;
		    }
		    retArr[1] = grp;
		}
	    }catch(Exception ex){
		System.err.println(ex);
	    }
	    // System.err.println(" dept: "+dept+" group: "+grp);
	}
	return retArr;
    }
}






















































