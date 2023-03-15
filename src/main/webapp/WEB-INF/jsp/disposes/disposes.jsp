<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->
<s:form action="disposes" id="form_id" method="post">
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
  <p>Most recent disposes classified by type <br />
		Select the disposal types, lot below.
	</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Show </dt>
			<dd><s:checkbox name="showRecycles" value="%{showRecycles}" /> Recycles</dd>
			<dd><s:checkbox name="showDonations" value="%{showDonations}" /> Donations </dd>
			<dd><s:checkbox name="showAuctions" value="%{showAuctions}" /> Auctioned Items</dd>
			<dd><s:checkbox name="showDiscards" value="%{showDiscards}" /> Discarded Items</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Lot </dt>
			<dd><s:select name="lot_id" value="%{lot_id}" list="%{lots}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick a Lot" required="true" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Date, from </dt>
			<dd><s:textfield name="date_from" value="%{date_from}" size="10" cssClass="date" /> to <s:textfield name="date_to" value="%{date_to}" size="10" cssClass="date" />
			</dd>
		</dl>
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/></dd>
</s:form>			
<s:if test="action != ''">
	<s:if test="showAuctions && items != null">
		<s:set var="items" value="items" />
		<s:set var="itemsTitle" value="auctionItemsTitle" />
		<%@  include file="auctionItems.jsp" %>
	</s:if>
	<s:if test="showDiscards && items2 != null">
		<s:set var="items" value="items2" />
		<s:set var="itemsTitle" value="discardsTitle" />
		<%@  include file="discards.jsp" %>
	</s:if>
	<s:if test="showDonations && items3 != null">
		<s:set var="items" value="items3" />
		<s:set var="itemsTitle" value="donationsTitle" />
		<%@  include file="donations.jsp" %>
		<s:set var="donItems" value="donationData" />
		<s:set var="donationsReportTitle" value="donationsReportTitle" />
		<%@  include file="donationReport.jsp" %>	
	</s:if>
	<s:if test="showRecycles && items4 != null">
		<s:set var="items" value="items4" />
		<s:set var="itemsTitle" value="recyclesTitle" />
		<%@  include file="recycles.jsp" %>
	</s:if>
</s:if>
<%@  include file="../gui/footer.jsp" %>
