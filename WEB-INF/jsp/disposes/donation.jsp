<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="donation" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />

	<s:hidden name="item.type" value="%{item.type}" />
	<s:hidden name="item.asset_id" value="%{item.asset_id}" />
	<s:hidden name="item.asset_num" value="%{item.asset_num}" />	
	<s:if test="item.id == ''">
		<h1>Asset Donation</h1>
	</s:if>
	<s:else>
		<s:hidden name="item.id" value="%{item.id}" />		
		<h1>Edit Asset Donation <s:property value="%{item.id}" /></h1>
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
		<dl class="fn1-output-field">
			<dt>Asset ID </dt>
			<dd>
				<s:property value="%{item.asset_id}" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Asset Num </dt>
			<dd>
				<s:property value="%{item.asset_num}" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Type </dt>
			<dd>
				<s:property value="%{item.type}" />				
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Organization *</dt>
			<dd><s:select name="item.organization_id" value="%{item.organization_id}" list="%{organizations}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Organization" required="true" /> 
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Value </dt>
			<dd>$<s:textfield name="item.value" value="%{item.value}" size="10" maxlength="10" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Date </dt>
			<dd><s:textfield name="item.date" value="%{item.date}" size="10" maxlength="10" cssClass="date" /> 
			</dd>
		</dl>
	</div>
	<s:if test="item.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>
</s:form>
<s:if test="item.id == '' && donations != null">
	<s:set var="items" value="donations" />
	<s:set var="itemsTitle" value="donationsTitle" />
	<%@  include file="donations.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

