<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<style>
table stats{
	border:1px solid;
	width:100%;
	}
table.stats th, table.stats td {
	border: 1px solid;
	font-size:0.75em;
	 }
</style>
<s:form action="lot" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="lot.id == ''">
		<h1>New Disposal Lot</h1>
	</s:if>
	<s:else>
		<s:hidden name="lot.id" value="%{lot.id}" />		
		<h1>Edit Lot <s:property value="%{lot.name}" /></h1>
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
	</p>
	<div class="tt-row-container">
		<s:if test="lot.id != ''">
			<dl class="fn1-output-field">				
				<dt>Lot ID </dt>
				<dd>
					<s:property value="%{lot.id}" /> (<s:property value="%{lot.date}" />)
				</dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Name *</dt>
			<dd><s:textfield name="lot.name" value="%{lot.name}" size="30" maxlength="70" required="true" /> 
			</dd>
		</dl>				
		<dl class="fn1-output-field">
			<dt>Type *</dt>
			<dd>
			<dd><s:radio name="lot.type" value="%{lot.type}" list="#{'Donation':'Donation','Recycle':'Recycle','Auction':'Auction'}" required="true" /> 
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Status *</dt>
			<dd>
			<dd><s:radio name="lot.status" value="%{lot.status}" list="#{'Active':'Active','Approval':'Approval','Complete':'Complete'}" required="true" /> 
			</dd>
		</dl>		
	</div>
	<s:if test="lot.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>
</s:form>
<s:if test="lot.id == '' && lots != null">
	<s:set var="lots" value="lots" />
	<s:set var="lotsTitle" value="lotsTitle" />
	<%@  include file="lots.jsp" %>
</s:if>
<s:elseif test="lot.type == 'Auction' && lot.items != null">
	<s:set var="items" value="lot.items" />
	<s:set var="itemsTitle" value="auctionItemsTitle" />
	<%@  include file="auctionItems.jsp" %>
</s:elseif>
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

