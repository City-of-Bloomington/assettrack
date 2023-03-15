<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->
<s:form action="lots" id="form_id" method="post">
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
	<p>Search Disposal Lots</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>ID </dt>
			<dd><s:textfield name="lotList.id" value="%{lotList.id}" size="8" maxlength="8"/> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Lot Status </dt>
			<dd><s:radio name="lotList.status" value="%{lotList.status}" list="#{'-1':'All','Active':'Active','Approval':'Approval','Complete':'Complete'}"/> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Lot Type </dt>
			<dd><s:radio name="lotList.type" value="%{lotList.type}" list="#{'-1':'All','Donation':'Donation','Recycle':'Recycle','Auction':'Auction'}"/> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Active Lot </dt>
			<dd><s:select name="lotList.active_id" value="%{lotList.active_id}" list="%{activeLots}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick a Lot" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Date, from </dt>
			<dd><s:textfield name="lotList.date_from" value="%{lotList.date_from}" size="10" cssClass="date" /> to <s:textfield name="lotList.date_to" value="%{lotList.date_to}" size="10" cssClass="date" />
			</dd>
		</dl>
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
	<a href="<s:property value='#application.url' />lot.action" class="fn1-btn">New Lot </a>
			
</s:form>			

<s:if test="lots != null && lots.size() > 0">
	<s:set var="lots" value="lots" />
	<s:set var="lotsTitle" value="lotsTitle" />
	<%@  include file="lots.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>
