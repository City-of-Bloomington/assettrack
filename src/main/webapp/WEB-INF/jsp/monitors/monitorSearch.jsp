<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="monitorSearch" id="form_id" method="post">
	<h1>Search Monitors</h1>
	<p>Note: to add a new monitor, go to a related device and click on 'New Monitor"</p>
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
				<dt>Monitor ID </dt>
				<dd><s:textfield name="monitorList.id" value="%{monitorList.id}" size="10" maxlength="10" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>External ID </dt>
				<dd><s:textfield name="monitorList.external_id" value="%{monitorList.external_id}" size="10" maxlength="10" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Asset Num </dt>
				<dd><s:textfield name="monitorList.asset_num" value="%{monitorList.asset_num}" size="10" maxlength="10" id="bar_code_id" /> </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Monitor Name </dt>
				<dd><s:textfield name="monitorList.name" value="%{monitorList.name}" size="20" maxlength="70" id="name" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Related Device </dt>
				<dd><s:textfield name="monitorList.device_id" value="%{monitorList.device_id}" size="10" maxlength="20" />(ID) </dd>				
			</dl>		
		</div>
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt>Serial Num </dt>
				<dd><s:textfield name="monitorList.serial_num" value="%{monitorList.serial_num}" size="20" maxlength="30"  /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Model </dt>
				<dd><s:textfield name="monitorList.model" value="%{monitorList.model}" size="20" maxlength="30" /> </dd>
			</dl>		
			<dl class="fn1-output-field">
				<dt>Type </dt>
				<dd><s:textfield name="monitorList.type" value="%{monitorList.type}" size="20" maxlength="30" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Manufacturer </dt>
				<dd><s:textfield name="monitorList.manufacturer" value="%{monitorList.manufacturer}" size="20" maxlength="50" /></dd>
			</dl>
		</div>
	</div>
	<dl class="fn1-output-field">
		<dt>Status </dt>
		<dd><s:radio name="monitorList.status" value="%{monitorList.status}" list="#{'-1':'All','Active':'Active','Donated':'Donated','Recycled':'Recycled','Auctioned':'Auctioned','Disposed':'Discarded'}" headerKey="-1" headerValue="All" /> </dd>				
	</dl>
	<dl class="fn1-output-field">
		<dt>Inventory Status</dt>
		<dd><s:radio name="monitorList.inventory_status" value="%{monitorList.inventory_status}" list="#{'-1':'All','set':'Date Set','unset':'Date Unset'}" /> </dd>
	</dl>			
	<dl class="fn1-output-field">
		<dt>Date Option </dt>
		<dd><s:radio name="monitorList.whichDate" value="monitorList.whichDate" list="#{'received':'Installed Date','inventory_date':'Inventory Date'}" /></dd>
	</dl>		
	<dl class="fn1-output-field">
		<dt>Date Range </dt>
		<dd>From: <s:textfield name="monitorList.date_from" value="%{monitorList.date_from}" size="10" maxlength="10" cssClass="date" /> To:
				<s:textfield name="monitorList.date_to" value="%{monitorList.date_to}" size="10" maxlength="10" cssClass="date" /> </dd>
	</dl>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>

</s:form>
<s:if test="monitors != null">
	<s:set var="monitors" value="monitors" />
	<s:set var="monitorsTitle" value="monitorsTitle" />
	<%@  include file="monitors.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

