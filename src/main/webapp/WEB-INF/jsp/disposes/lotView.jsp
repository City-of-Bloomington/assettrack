<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h2> <s:property value="%{lot.name}" /></h2>
<s:if test="hasActionErrors()">
	<div class="errors">
    <s:actionerror/>
	</div>
</s:if>
<s:elseif test="hasActionMessages()">
	<div class="welcome">
    <s:actionmessage/>
	</div>
</s:elseif>
<div class="tt-row-container">
	<dl class="fn1-output-field">				
		<dt>Lot ID </dt>
		<dd>
			<s:property value='id' /> (<s:property value="%{lot.date}" />)
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Type </dt>
		<dd>
			<dd><s:property value="%{lot.type}" /> 
			</dd>
	</dl>		
	<dl class="fn1-output-field">
		<dt>Status </dt>
		<dd><s:property value="%{lot.status}" /> </dd>
	</dl>
	<s:if test="lot.canEdit()">
		<a href="<s:property value='#application.url' />lot.action?action=Edit&id=<s:property value='id' />" class="fn1-btn">Edit</a>
	</s:if>
	<input type="button" onclick="window.open('<s:property value='#application.url' />lotOpt.action?type=<s:property value='lot.type' />','Notes','toolbar=0,location=0,directories=0,status=0,menubar=1,scrollbars=1,top=100,left=100,resizable=1,width=400,height=400');"  value="Print Options" class="fn1-btn" />		
	<a href="<s:property value='#application.url' />lot.action?action=print&id=<s:property value='id' />" class="fn1-btn">Printable</a>
	<a href="<s:property value='#application.url' />LotPrintPdf?id=<s:property value='id' />" class="fn1-btn">Pdf Print</a>	
</div>

<s:if test="lot.type == 'Auction' && lot.items != null">
	<s:set var="items" value="lot.items" />
	<s:set var="itemsTitle" value="auctionItemsTitle" />
	<%@  include file="auctionItems.jsp" %>
</s:if>
<s:elseif test="lot.type == 'Donation' && lot.items3 != null">
	<s:set var="items" value="lot.items3" />
	<s:set var="itemsTitle" value="donationsTitle" />
	<%@  include file="donations.jsp" %>
</s:elseif>
<s:elseif test="lot.type == 'Recycle' && lot.items4 != null">
	<s:set var="items" value="lot.items4" />
	<s:set var="itemsTitle" value="recyclesTitle" />
	<%@  include file="recycles.jsp" %>
</s:elseif>

<%@  include file="../gui/footer.jsp" %>

