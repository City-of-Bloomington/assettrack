<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="auction" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Auction</h1>
	</s:if>
	<s:else>
		<h1>Edit Auction <s:property value="%{auction.id}" /></h1>
		<s:hidden name="auction.id" value="%{auction.id}" />
	</s:else>
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
  <p>* Required field <br />
		<s:if test="id != ''">
			If you make any change, please hit the 'Save Changes' button
		</s:if>
		<s:else>
			You must hit 'Save' button to save data.
		</s:else>
	</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="auction.name" value="%{auction.name}" size="30" maxlength="70" requiredLabel="true" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Auction Date </dt>
			<dd><s:textfield name="auction.date" value="%{auction.date}" size="10" maxlength="10" requiredLabel="true" required="true" cssClass="date" />* </dd>
		</dl>
		<s:if test="type.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="auctions != null">
	<s:set var="auctions" value="auctions" />
	<s:set var="auctionsTitle" value="auctionsTitle" />
	<%@  include file="auctions.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>


