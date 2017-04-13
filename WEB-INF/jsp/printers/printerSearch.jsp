<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="printerSearch" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<h1>Search Printers</h1>
	<p>
		To add a new indepenent printer, click on 'New Printer'. <br />
		To add a printer attached to a computer, click on 'New Printer' on the Device page.
	</p>
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
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Printer ID</dt>
				<dd><s:textfield name="printerList.id" size="10" value="%{printerList.id}" /></dd>		
			</dl>
			<dl class="fn1-output-field">
				<dt>Asset Num</dt>
				<dd><s:textfield name="printerList.asset_num" size="15" value="%{printerList.asset_num}" id="bar_code_id" /></dd>		
			</dl>			
			<dl class="fn1-output-field">
				<dt>External ID</dt>
				<dd><s:textfield name="printerList.external_id" size="10" value="%{printerList.external_id}" /></dd>		
			</dl>
		</div>
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt>Printer Name</dt>
				<dd><s:textfield name="printerList.name" size="20" value="%{printerList.name}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Related Device </dt>
				<dd><s:textfield name="printerList.device_id" value="%{printerList.device_id}" size="10" maxlength="10" /> (ID) </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Print Processor </dt>
				<dd><s:textfield name="printerList.print_processor" value="%{printerList.print_processor}" size="30" maxlength="70" /> </dd>
			</dl>
		</div>
	</div>
	<dl class="fn1-output-field">
		<dt>Status </dt>
		<dd><s:radio name="printerList.status" value="%{printerList.status}" list="#{'-1':'All','Active':'Active','Donated':'Donated','Recycled':'Recycled','Auctioned':'Auctioned','Disposed':'Discarded'}" headerKey="-1" headerValue="All" /> </dd>				
	</dl>
	<dl class="fn1-output-field">
		<dt>Inventory Status</dt>
		<dd><s:radio name="printerList.inventory_status" value="%{printerList.inventory_status}" list="#{'-1':'All','set':'Date Set','unset':'Date Unset'}" /> </dd>
	</dl>		
	<dl class="fn1-output-field">
		<dt>Date Option </dt>
		<dd><s:radio name="printerList.whichDate" value="printerList.whichDate" list="#{'u.date':'Installed Date','u.inventory_date':'Inventory Date'}" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Date Range </dt>
		<dd>From:
			<s:textfield name="printerList.date_from" value="%{printerList.date_from}" size="10" maxlength="10" cssClass="date" /> To:
			<s:textfield name="printerList.date_to" value="%{printerList.date_to}" size="10" maxlength="10" cssClass="date" />
		</dd>
	</dl>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
	<a href="<s:property value='#application.url' />printer.action" class="fn1-btn">New Printer </a>	
</s:form>
<s:if test="printers != null && printers.size() > 0">
	<s:set var="printers" value="%{printers}" />
	<s:set var="printersTitle" value="printersTitle" />
	<%@  include file="printers.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>


