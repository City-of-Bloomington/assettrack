package assets.web;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.naming.*;
import javax.naming.directory.*;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.Phrase;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Font;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import assets.model.*;
import assets.list.*;
import assets.utils.*;

@WebServlet(urlPatterns = {"/LotPrintPdf"})
public class LotPrintPdf extends TopServlet {

    String url="",url2="";
    static Logger logger = LogManager.getLogger(LotPrintPdf.class);
    boolean debug = false;
    static final long serialVersionUID = 36L;


    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{
	res.setContentType("text/html");
	    
	String name, value, message="", action="";
	boolean success=true;
	BufferedWriter bw=null,bw2=null;
	Enumeration<String> values = req.getParameterNames();
	if(url.equals("")){
	    url    = getServletContext().getInitParameter("url");
	    url2   = getServletContext().getInitParameter("url2");
	    String str = getServletContext().getInitParameter("debug");
	    if(str != null && str.equals("true")) debug = true;
	}
	User user = null;
	HttpSession session = req.getSession(false);
	if(session != null){
	    user = (User)session.getAttribute("user");
	    if(user == null){
		String str = url+"Login";
		res.sendRedirect(str);
		return; 
	    }
	}
	else{
	    String str = url+"Login";
	    res.sendRedirect(str);
	    return; 
	}
	String [] vals;
	Lot lot = new Lot(debug);
	while (values.hasMoreElements()){
	    name = values.nextElement().trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();
			
	    if(name.equals("id")){
		lot.setId(value);
	    }			
	    else if (name.equals("action")) {
		action=value;
	    }
	}
	//
	// regenerate letters
	//
	message = lot.doSelect();
	if(!message.equals("")){
	    PrintWriter out = res.getWriter();			  
	    out.println("<html><head><title>Printable Waiver</title>");
	    out.println("</head><body>");
	    out.println("<center>");
	    out.println("<p>"+message+"</p>");
	    out.println("<br />");
	    out.print("</center></body></html>");
	    out.close();
	}
	else {
	    writeLotInfo(res,
			 lot);
	    return;
	}
    }
    /**
     * handles create pdf files that will be printed
     */
    String writeLotInfo(HttpServletResponse res, Lot lot){ 
	//
	// paper size legal (A4) 8.5 x 11
	// page 1-inch = 72 points
	//
	// Rectangle pageSize = new Rectangle(612, 792); // 8.5" X 11" : letter
	// this legal size 8.5 x 14 
	Rectangle pageSize = new Rectangle(612, 792); 
	// 2" top, bottom, 1/2" right left
        Document document = new Document(pageSize, 72, 72, 36, 36);// 18,18,54,35
	// Document document = new Document(PageSize.A3, 36, 36, 18, 18);		
	ServletOutputStream out = null;
	String back = "";
	// PdfPTable header = getHeader(attorneys);
	try{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = PdfWriter.getInstance(document, baos);
	    String str = "";
	    document.open();
	    writeInfo(lot, document);

	    document.close();
	    writer.close();
	    res.setHeader("Expires", "0");
	    res.setHeader("Cache-Control", 
			  "must-revalidate, post-check=0, pre-check=0");
	    res.setHeader("Pragma", "public");
	    //
	    // setting the content type
	    res.setContentType("application/pdf");
	    //
	    // the contentlength is needed for MSIE!!!
	    res.setContentLength(baos.size());
	    //
	    out = res.getOutputStream();
	    if(out != null){
		baos.writeTo(out);
	    }
	}catch(Exception ex){
	    logger.error(ex);
	    back += ex;
	}
	return back;
    }
    String writeInfo(Lot lot,
		     Document document){
	String back = "";
	try{
	    Font fnts = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL);
	    Font fntbs = new Font(Font.TIMES_ROMAN, 6, Font.BOLD);						
	    Font fnt = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
	    Font fntb = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
	    Font fntb2 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD|Font.UNDERLINE);
	    LotOptions lotOpt = lot.getLotOpt();
	    if(lotOpt == null){
		return " print options not set ";
	    }
	    Phrase phrase = new Phrase();
	    Chunk ch = new Chunk(" ", fnt);
	    phrase.add(ch);
	    phrase.add(Chunk.NEWLINE);
	    Paragraph pp = new Paragraph();
	    pp.setAlignment(Element.ALIGN_CENTER);
	    ch = new Chunk("IT Department", fntb2);
	    phrase.add(ch);
	    phrase.add(Chunk.NEWLINE);
	    ch = new Chunk(lot.getName(), fntb);
	    phrase.add(ch);
	    phrase.add(Chunk.NEWLINE);
	    ch = new Chunk("Lot ID: "+lot.getId()+" Date: "+lot.getDate()+" "+lot.getType(),fnt);
	    phrase.add(ch);						
	    phrase.add(Chunk.NEWLINE);
	    ch = new Chunk(lot.getType()+" Items List",fnt);
	    phrase.add(ch);						
	    phrase.add(Chunk.NEWLINE);
	    phrase.add(Chunk.NEWLINE);						
	    pp.add(phrase);
	    //
	    document.add(pp);
	    //
	    float width = 100f/14; // column count - 2 lot_options table
	    int count = 14;
	    //
	    count = lotOpt.getNotNullCount();
	    width = 100f/count;
						
	    float[] widths = new float[count];
	    for(int i=0; i<count; i++) {
		widths[i]=width;
	    }
	    PdfPTable table = new PdfPTable(widths);
	    table.setWidthPercentage(100.0f);
	
	    // table header
						
	    PdfPCell cell = new PdfPCell(new Phrase(" ", fntb));// to start no use
	    if(lotOpt.getAsset_id()){
		cell = new PdfPCell(new Phrase("Asset ID", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		// cell.setBorder(Rectangle.NO_BORDER);  
		table.addCell(cell);
	    }
	    if(lotOpt.getAsset_num()){
		cell = new PdfPCell(new Phrase("Asset No.", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getSerial_num()){
		cell = new PdfPCell(new Phrase("Serial No.", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getName()){
		cell = new PdfPCell(new Phrase("Name", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getCategory()){
		cell = new PdfPCell(new Phrase("Category", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getDivision()){
		cell = new PdfPCell(new Phrase("Division", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }						
	    if(lotOpt.getInstalled()){
		cell = new PdfPCell(new Phrase("Installed Date", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getDate()){
		cell = new PdfPCell(new Phrase("Date", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getDescription()){
		cell = new PdfPCell(new Phrase("Description", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getValue()){
		cell = new PdfPCell(new Phrase("Value", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getWeight()){
		cell = new PdfPCell(new Phrase("Weight", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }												
	    if(lotOpt.getOrganization()){
		cell = new PdfPCell(new Phrase("Organization", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    if(lotOpt.getRecycle_location()){
		cell = new PdfPCell(new Phrase("Recycle Location", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }						
	    if(lotOpt.getAuction_name()){
		cell = new PdfPCell(new Phrase("Auction", fntbs));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	    }
	    List<AuctionItem> auctionItems = lot.getItems();
	    if(lot.getType().equals("Donation")){
		back +=writeDonations(document, lotOpt, lot, table);
	    }
	    else if(lot.getType().equals("Recycle")){
		System.err.println(" writing recyles ");
		back +=writeRecycles(document, lotOpt, lot, table);
	    }
	    else if(lot.getType().equals("Auction")){
		System.err.println(" writing auctions ");
		back +=writeAuctions(document, lotOpt, lot, table);
	    }
	    else{
		back = "no items found ";

	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    back += ex;
	}
	return back;
    }
    String writeDonations(Document document,
			  LotOptions lotOpt,
			  Lot lot,
			  PdfPTable table){
	String back = "";
	List<Donation> items = lot.getItems3();
	if(items == null || items.size() < 1)
	    return back;
	Font fnts = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL);
	try{
	    PdfPCell cell = new PdfPCell(new Phrase(" ", fnts));
	    for(Donation item:items){
		if(lotOpt.getAsset_id()){
		    cell = new PdfPCell(new Phrase(item.getAsset_id(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // cell.setBorder(Rectangle.NO_BORDER);  
		    table.addCell(cell);
		}
		if(lotOpt.getAsset_num()){
		    cell = new PdfPCell(new Phrase(item.getAsset_num(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getSerial_num()){
		    cell = new PdfPCell(new Phrase(item.getSerial_num(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getName()){
		    cell = new PdfPCell(new Phrase(item.getName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getCategory()){
		    cell = new PdfPCell(new Phrase(item.getCategoryName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDivision()){
		    cell = new PdfPCell(new Phrase(item.getDivisionName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}						
		if(lotOpt.getInstalled()){
		    cell = new PdfPCell(new Phrase(item.getInstalled(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDate()){
		    cell = new PdfPCell(new Phrase(item.getDate(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDescription()){
		    cell = new PdfPCell(new Phrase(item.getNotes(),fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getValue()){
		    cell = new PdfPCell(new Phrase(""+item.getValue(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getWeight()){
		    // cell = new PdfPCell(new Phrase(item.getWeight(), fntb));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}												
		if(lotOpt.getOrganization()){
		    Type organ = item.getOrgan();
		    if(organ != null){
			cell = new PdfPCell(new Phrase(organ.getName(), fnts));
		    }
		    else{
			cell = new PdfPCell(new Phrase(" ", fnts));
		    }
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getRecycle_location()){
		    // cell = new PdfPCell(new Phrase(item.getRecycle_location(), fntb));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}						
		if(lotOpt.getAuction_name()){
		    // cell = new PdfPCell(new Phrase(item.getAuction().getName(), fntb));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    //cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}						
	    }
	    document.add(table);

	}
	catch(Exception ex){
	    logger.error(ex);
	    back += ex;
	}				
	return back;
    }
    String writeRecycles(Document document,
			 LotOptions lotOpt,
			 Lot lot,
			 PdfPTable table){
	String back = "";
	List<RecycledItem> items = lot.getItems4();
	if(items == null || items.size() < 1)
	    return back;				
	Font fnts = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL);
	try{
	    PdfPCell cell = new PdfPCell(new Phrase(" ", fnts));
	    for(RecycledItem item:items){

		if(lotOpt.getAsset_id()){
		    cell = new PdfPCell(new Phrase(item.getAsset_id(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // cell.setBorder(Rectangle.NO_BORDER);  
		    table.addCell(cell);
		}
		if(lotOpt.getAsset_num()){
		    cell = new PdfPCell(new Phrase(item.getAsset_num(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getSerial_num()){
		    cell = new PdfPCell(new Phrase(item.getSerial_num(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getName()){
		    cell = new PdfPCell(new Phrase(item.getName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getCategory()){
		    cell = new PdfPCell(new Phrase(item.getCategoryName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDivision()){
		    cell = new PdfPCell(new Phrase(item.getDivisionName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}						
		if(lotOpt.getInstalled()){
		    cell = new PdfPCell(new Phrase(item.getInstalled(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDate()){
		    cell = new PdfPCell(new Phrase(item.getDate(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDescription()){
		    cell = new PdfPCell(new Phrase(item.getNotes(),fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getValue()){
		    //cell = new PdfPCell(new Phrase(""+item.getValue(), fnts));
		    //cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    //cell.setVerticalAlignment(Element.ALIGN_TOP);
		    //table.addCell(cell);
		}
		if(lotOpt.getWeight()){
		    cell = new PdfPCell(new Phrase(item.getWeight(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}												
		if(lotOpt.getOrganization()){
		    // cell = new PdfPCell(new Phrase(item.getOrgan().getName(), fnts));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}
		if(lotOpt.getRecycle_location()){
		    Type loc = item.getLocation();
		    if(loc != null){
			cell = new PdfPCell(new Phrase(lot.getName(), fnts));
		    }
		    else{
			cell = new PdfPCell(new Phrase(" ", fnts));
		    }
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}						
		if(lotOpt.getAuction_name()){
		    // cell = new PdfPCell(new Phrase(item.getAuction().getName(), fntb));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    //cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}						
	    }
	    document.add(table);
	}
	catch(Exception ex){
	    logger.error(ex);
	    back += ex;
	}				
	return back;

    }
    String writeAuctions(Document document,
			 LotOptions lotOpt,
			 Lot lot,
			 PdfPTable table){
	String back = "";
	List<AuctionItem> items = lot.getItems();
	if(items == null || items.size() < 1)
	    return back;
	Font fnts = new Font(Font.TIMES_ROMAN, 6, Font.NORMAL);
				
	try{
	    PdfPCell cell = new PdfPCell(new Phrase(" ", fnts));
	    for(AuctionItem item:items){
		if(lotOpt.getAsset_id()){
		    cell = new PdfPCell(new Phrase(item.getAsset_id(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // cell.setBorder(Rectangle.NO_BORDER);  
		    table.addCell(cell);
		}
		if(lotOpt.getAsset_num()){
		    cell = new PdfPCell(new Phrase(item.getAsset_num(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getSerial_num()){
		    cell = new PdfPCell(new Phrase(item.getSerial_num(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getName()){
		    cell = new PdfPCell(new Phrase(item.getName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getCategory()){
		    cell = new PdfPCell(new Phrase(item.getCategoryName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDivision()){
		    cell = new PdfPCell(new Phrase(item.getDivisionName(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}						
		if(lotOpt.getInstalled()){
		    cell = new PdfPCell(new Phrase(item.getInstalled(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDate()){
		    cell = new PdfPCell(new Phrase(item.getDate(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getDescription()){
		    cell = new PdfPCell(new Phrase(item.getNotes(),fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getValue()){
		    cell = new PdfPCell(new Phrase(""+item.getValue(), fnts));
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}
		if(lotOpt.getWeight()){
		    // cell = new PdfPCell(new Phrase(item.getWeight(), fnts));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}												
		if(lotOpt.getOrganization()){
		    // cell = new PdfPCell(new Phrase(item.getOrgan().getName(), fnts));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}
		if(lotOpt.getRecycle_location()){
		    // cell = new PdfPCell(new Phrase(item.getLocation().getName(), fnts));
		    // cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    // cell.setVerticalAlignment(Element.ALIGN_TOP);
		    // table.addCell(cell);
		}						
		if(lotOpt.getAuction_name()){
		    Type auction = item.getAuction();
		    if(auction != null){
			cell = new PdfPCell(new Phrase(auction.getName(), fnts));
		    }
		    else{
			cell = new PdfPCell(new Phrase(" ", fnts));
		    }
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setVerticalAlignment(Element.ALIGN_TOP);
		    table.addCell(cell);
		}						
	    }
	    document.add(table);
	}
	catch(Exception ex){
	    logger.error(ex);
	    back += ex;
	}				
	return back;

    }		
}






















































