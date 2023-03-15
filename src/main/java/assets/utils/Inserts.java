package assets.utils;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import assets.model.*;

public class Inserts{

    boolean debug = false;
    static final long serialVersionUID = 1440L;	
    //
    //
    // xhtmlHeaderInc
    final static String xhtmlHeaderInc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
	"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"+
	"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\">";
    //
    // basic constructor
    public Inserts(boolean deb){
	//
	// initialize
	//
	debug = deb;
    }
    public final static String jqDateStr(String url){
		
	return "{ nextText: \"Next\",prevText:\"Prev\", buttonText: \"Pick Date\", showOn: \"both\", navigationAsDateFormat: true, buttonImage: \""+url+"js/calendar.gif\"}";		

    }
    //
    // main page banner
    //
    public final static String banner(String url){

	String banner = "<head>\n"+
	    "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />\n"+
	    "<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n"+
	    "<link rel=\"SHORTCUT ICON\" href=\"https://apps.bloomington.in.gov/favicon.ico\" />\n"+
	    "<style type=\"text/css\" media=\"screen\">\n"+
	    "/*<![CDATA[*/"+			
	    "           @import url(\"https://apps.bloomington.in.gov/skins/isotope/skin.css\");\n"+
	    "/*]]>*/\n"+		
	    "</style>\n"+
	    "<style type=\"text/css\" media=\"print\">\n"+
	    "/*<![CDATA[*/"+			
	    "      @import url(\"https://apps.bloomington.in.gov/skins/isotope/print.css\");\n"+
	    "body { color:black; background-color:white; margin:0px; font-family:sans-serif; font-size:11pt; }\n"+
	    "/*]]>*/\n"+			
	    "</style>\n"+
	    "<link rel=\"stylesheet\" href=\""+url+"js/jquery-ui.css\" type=\"text/css\" media=\"all\" />"+
	    "<link rel=\"stylesheet\" href=\""+url+"js/jquery.ui.theme.css\" type=\"text/css\" media=\"all\" />\n"+	
	    "<style type=\"text/css\">"+  // user defined
	    "/*<![CDATA[*/"+
	    "table.wide {width:100%;border:none}"+
	    "table.widesolid {width:100%;border-style:solid}"+
	    "table.box {width:100%;border:2px solid}"+
	    "table.box th {border:2px solid; text-align:center; vertical-align:bottom; } "+
	    "table.box td {border:2px solid} "+
	    "table.control {width:100%} "+
	    "table.control td {text-align:center; padding:.5em} "+
	    "tr.solid {border-style:solid}\n"+
	    "td.right {text-align:right}\n"+
	    "td.left {text-align:left}\n"+
	    "td.center {text-align:center}\n"+
	    "th.small{width:15%}\n"+
	    "th.right {text-align:right}\n"+
	    "td.title{background-color:navy;color:white;font-weight:bold;}\n"+
	    "p.warning { color:red}\n"+
	    "p.center {text-align:center}\n"+
	    "div.center {text-align:center}\n"+
	    "div.left {text-align:left}\n"+	
	    "div.green { font-size:80%; color:green}\n"+
	    "span.center {text-align:center}\n"+
	    "span.green { font-size:80%; color:green}\n"+
	    "/*]]>*/\n"+
	    "</style>\n"+
	    //
	    // Java Script common for all pages
	    //
	    " <script type=\"text/javascript\">                 \n"+
	    "//<![CDATA[                                        \n"+
	    "  function checkDate(dt){                          \n"+     
	    "    var dd = dt.value;                             \n"+
	    "   if(!dd || dd.length == 0) return true;          \n"+
	    "   var dar = dd.split(\"/\");                      \n"+
	    " if(dar.length < 3){                               \n"+
	    " alert(\"Not a valid date: \"+dd);                 \n"+
	    "      dt.select();                                 \n"+
	    "      dt.focus();                                  \n"+
	    "      return false;}                               \n"+
	    "   var m = dar[0];                                 \n"+
	    "   var d = dar[1];                                 \n"+
	    "   var y = dar[2];                                 \n"+
	    "   if(isNaN(m) || isNaN(d) || isNaN(y)){           \n"+
	    "      alert(\"Not a valid date: \"+dd);                 \n"+
	    "      dt.select();                                 \n"+
	    "      dt.focus();                                  \n"+
	    "      return false; }                              \n"+
	    "  if(!validateDate(m,d,y)) {                        \n"+
	    "      alert(\"Not a valid date: \"+dd);                 \n"+
	    "      dt.select();                                 \n"+
	    "      dt.focus();                                  \n"+
	    "      return false;                                \n"+
	    "      }                                            \n"+
	    "    return true;                                   \n"+
	    " }                                              \n"+
	    " function isFutureDate(dt){     \n"+
	    "   var dd = dt.value;                             \n"+
	    "   if(!dd || dd.length == 0) return false;         \n"+
	    "   var dar = dd.split(\"/\");                      \n"+
	    "   if(dar.length < 3){                               \n"+
	    "      alert(\"Not a valid date: \"+dd);                 \n"+
	    "      return false;                               \n"+
	    "   }                                  \n"+			
	    "   var m = dar[0];                                 \n"+
	    "   var d = dar[1];                                 \n"+
	    "   var y = dar[2];                                 \n"+
	    "   var date = new Date(y,(m-1),d);    \n"+ // month start with 0
	    "   var today = new Date();           \n"+
	    "   var diff = (date.getTime()) - (today.getTime());  \n"+// trans to digits
	    "   if(diff < 0){                 \n"+
	    "      return false;                               \n"+
	    "   }                                  \n"+			
	    "   return true;                      \n"+
	    " }                                  \n"+
	    " function validateDate(month,day,year){     \n"+
	    "   if(!isNaN(month)){                         \n"+
	    "      month = month - 1;                         \n"+
	    "   }                                          \n"+
	    //
	    "   var date = new Date(year,month,day);       \n"+
	    "   if(year != date.getFullYear()){             \n"+
	    "                                           \n"+
	    "    return false;                              \n"+
	    "  }                                            \n"+
	    "  if(month != date.getMonth()){                 \n"+
	    "    return false;                              \n"+
	    "  }                                            \n"+
	    "  if(day != date.getDate()){                     \n"+
	    "    return false;                               \n"+
	    "  }                                            \n"+
	    "  return true;                                 \n"+
	    " }                                             \n"+
	    "   function indexOf(xx,s){               \n"+
	    "       var  l = xx.length;               \n"+
	    "       var o = -1;                       \n"+
	    "     for(var i=0; i<l; i++){             \n"+
	    "        var c = xx.charAt(i);            \n"+
	    "       if(c == s) o = i;                 \n"+
            "      }                                  \n"+
	    "    return o;                            \n"+
	    "   }                                     \n"+
	    "  function checkNumber(dt){                        \n"+     
	    "    var dd = dt.value;                             \n"+
	    "   if(dd.length == 0) return true;                \n"+
	    "     if(isNaN(dd)){                                \n"+
	    "      alert(\"Not a valid Number: \"+dd);          \n"+
	    "      dt.select();                                 \n"+
	    "      dt.focus();                                  \n"+
	    "      return false;                                \n"+
	    "        }                                          \n"+
	    "    return true;                                   \n"+
	    "   }                                               \n"+
	    "  function validateDelete(){	                    \n"+
	    "   var x = false;                                  \n"+
	    " x = confirm(\"Are you sure you want to delete this record\");\n"+
	    "   return x;                                       \n"+
	    "	}	             		              \n"+
	    "  function refreshPage(){	              \n"+
	    "    document.forms[0].submit();            \n"+
	    "  }                                      \n"+
	    "  function addColon(time,e){	          \n"+
	    " keyCode = (window.Event) ? e.which: e.keyCode;  \n"+
	    "  if(keyCode > 47 && keyCode < 58){              \n"+
	    "  if(time.value.length == 2) {                   \n"+
	    "  if(time.value.indexOf(\":\") == -1){           \n"+
	    "   time.value = time.value+\":\";        \n"+
	    "  }}}}                                   \n"+
	    "  function checkTime(time,e){	          \n"+
	    "   keyCode = (window.Event) ? e.which: e.keyCode;     \n"+
	    "   if(keyCode > 47 && keyCode < 58){                 \n"+
	    "     var len = time.value.length;                      \n"+
	    "     if(len < 5) {                                     \n"+
	    "       if(time.value.indexOf(\":\") == 1){              \n"+
	    "         time.value = \"0\"+time.value;                   \n"+
	    "      }                                                 \n"+
	    "      if(time.value.indexOf(\":\") == 2){              \n"+
	    "        if(len == 3) {                                    \n"+
	    "         time.value = time.value+\"00\";                  \n"+
	    "        }else {                                          \n"+
	    "         time.value = time.value+\"0\";                   \n"+
	    "  }}}}}                                             \n"+
	    " //]]>                                    \n"+			
	    " </script>				                  \n"+
	    "<title>Sponsors - City of Bloomington, Indiana</title>\n"+
	    "</head>\n"+
	    "<body>\n"+
	    "<div id=\"banner\">\n"+
	    "  <div id=\"application_name\">Sponsors</div>\n"+
	    "  <div id=\"location_name\">City Of Bloomington, IN</div>\n"+
	    "  <div id=\"navigation\"></div>\n"+
	    "</div>";
	return banner;
    }

    //
    // main page banner
    //
    public final static String banner2(String url, String bodyStr, String js){

	String banner = "<head>\n"+
	    "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />\n"+
	    "<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n"+
	    "<link rel=\"SHORTCUT ICON\" href=\"https://apps.bloomington.in.gov/favicon.ico\" />\n"+
	    "<style type=\"text/css\" media=\"screen\">\n"+
	    "           @import url(\"https://apps.bloomington.in.gov/skins/isotope/skin.css\");\n"+
	    "</style>\n"+
	    "<style type=\"text/css\" media=\"print\">\n"+
	    "      @import url(\"https://apps.bloomington.in.gov/skins/isotope/print.css\");\n"+
	    "body { color:black; background-color:white; margin:0px; font-family:sans-serif; font-size:11pt; }\n"+
	    "</style>\n"+

	    "<style type=\"text/css\">"+  // user defined
	    "table.wide {width:100%;border:none}"+
	    "table.widesolid {width:100%;border-style:solid}"+
	    "table.box {width:100%;border:2px solid}"+
	    "table.box th {border:2px solid; text-align:center; vertical-align:bottom; } "+
	    "table.box td {border:2px solid} "+
	    "table.control {width:100%} "+
	    "table.control td {text-align:center; padding:.5em} "+
	    "tr.solid {border-style:solid}\n"+
	    "td.right {text-align:right}\n"+
	    "</style>\n";
	if(!js.equals("")){
	    banner += " <script type=\"text/javascript\">\n"+
		js+
		" </script> \n";
	}
	//
	banner += "<title>Sponsors - City of Bloomington, Indiana</title>\n"+
	    "</head>\n";		
	banner += "<body "+bodyStr+">\n";

	banner += "<div id=\"banner\">\n"+
	    "  <div id=\"application_name\">Sponsors</div>\n"+
	    "  <div id=\"location_name\">City Of Bloomington, IN</div>\n"+
	    "  <div id=\"navigation\"></div>\n"+
	    "</div>";
	return banner;
    }
	
    //
    public final static String menuBar(String url, boolean logged){
	String menu = "<div class='menuBar'>\n<ul>";
	if(logged){
	    menu += "<li><a href=\""+url+"/Logout\">Logout</a></li>\n";
	}
	menu += "</ul></div>\n";
	return menu;
    }
    //
    // sidebar
    //
    public final static String sideBar(String url, User user){

	String ret = "<div id=\"leftSidebar\" class=\"left sidebar\">"+
	    "<h3 class=\"titleBar\">New Records</h3>\n"+
	    "<ul>\n"+
	    "<li><a href=\""+url+"SponsorServ\">New Sponsor</a></li>\n"+
	    "<li><a href=\""+url+"OpportServ\">New Opportunity</a></li>\n"+
	    "<li><a href=\""+url+"SponsorshipServ?\">New Sponsorship</a></li>\n"+
	    "<li><a href=\""+url+"ActionServ?\">New Note</a></li>\n"+			
	    "</ul>"+
	    "<h3 class=\"titleBar\">Search</h3>"+
	    "<ul>"+
	    "<li><a href=\""+url+"SponSearchServ?\">Sponsors</a></li>\n"+
	    "<li><a href=\""+url+"OpportSearchServ\">Opportunities</a></li>\n"+
	    "<li><a href=\""+url+"SponshipSearchServ?\">Sponsorships</a></li>\n"+
	    "<li><a href=\""+url+"InvoiceSearchServ?\">Invoices</a></li>\n"+
	    "<li><a href=\""+url+"PaymentSearchServ?action=Submit&amp;date_from=today&amp;hasBalance=y\">Upcoming Payments</a></li>\n"+
	    "<li><a href=\""+url+"ExpenseSearchServ?\">Expenses</a></li>\n"+
	    "<li><a href=\""+url+"TaskSearchServ?\">Notes</a></li>\n"+
	    "</ul>"+
	    "<h3 class=\"titlBar\">Misc</h3>"+
	    "<ul>"+
	    "<li><a href=\""+url+"AttentionServ?\">Need Attention</a></li>\n"+	
	    "<li><a href=\""+url+"CategoryServ?\">Category Editor</a></li>\n"+
	    "<li><a href=\""+url+"SendOut?\">Send Out</a></li>\n"+
	    "</ul>"+
	    "<h3 class=\"titlBar\">Reports</h3>"+
	    "<ul>"+
	    "<li><a href=\""+url+"ReportServ?\">Sponsors</a></li>\n"+
	    "<li><a href=\""+url+"EventSponshipReport?\">Event Sponsorships</a></li>\n"+	
	    "<li><a href=\""+url+"IncomeReport?\">Periodic Income</a></li>\n";
		
	ret += "</ul></div>";
	return ret;
    }
	
}






















































