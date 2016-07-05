<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="dispose" id="form_id" method="post">
	<h1>Dispose Asset</h1>	
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden name="type" value="%{type}" />
	<s:hidden name="asset_id" value="%{asset_id}" />	
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
				<s:property value="%{asset_id}" /> Type:
				<s:property value="%{type}" />				
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Dispose Method </dt>
			<dd><s:radio name="method" value="%{method}" list="#{'Recycle':'Recycle','Donation':'Donation','Auction':'Auction','Discard':'Discard'}" /> 
			</dd>
		</dl>
	</div>
	<s:submit name="action" type="button" value="Next" class="fn1-btn"/></dd>
</s:form>
<%@  include file="../gui/footer.jsp" %>

