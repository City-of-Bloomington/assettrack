<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h1> Software License <s:property value="license.id" /></h1>
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
			<dt>License Key </dt>
			<dd><s:property value="%{license.key_value}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Type </dt>
			<dd><s:property value="%{license.type}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Date </dt>
			<dd><s:property value="%{license.created}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Seat limit </dt>
			<dd><s:property value="%{license.seat_limit}" /> </dd>
		</dl>
		<s:if test="license.canEdit()">
			<dl class="fn1-output-field">
				<dt> </dt>
				<dd>
					<a href="<s:property value='#application.url' />license.action?id=<s:property value='license.id' />&action=Edit" class="fn1-btn">Edit </a>					
				</dd>
			</dl>
		</s:if>
	</div>
<s:if test="licenses != null">
	<s:set var="licenses" value="licenses" />
	<s:set var="licensesTitle" value="licensesTitle" />
	<%@  include file="licenses.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

