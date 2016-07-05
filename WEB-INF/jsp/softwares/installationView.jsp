<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h1>Software Installation <s:property value="installation.id" /></h1>
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
		<dt>External ID </dt>
		<dd><s:property value="%{installation.external_id}" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Software </dt>
		<dd>
			<a href="<s:property value='#application.url' />software.action?id=<s:property value='installation.software_id' />" class="fn1-btn"><s:property value="installation.software.display_name" /> </a>
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>License Key </dt>
		<dd>
			<a href="<s:property value='#application.url' />license.action?id=<s:property value='%{installation.license_id}' />" class="fn1-btn"><s:property value="%{installation.license.key_value}" /> </a>
		</dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Device </dt>
		<dd>
			<a href="<s:property value='#application.url' />device.action?id=<s:property value='%{installation.device_id}' />" class="fn1-btn"><s:property value="%{installation.device.name}" /> </a>			
		</dd>
	</dl>
	
	<dl class="fn1-output-field">				
		<dt>Installed Date </dt>
		<dd><s:property value="%{installation.date}" /></dd>
	</dl>

	<s:if test="installation.canEdit()">
		<dl class="fn1-output-field">
			<dt></dt>
			<dd>
				<a href="<s:property value='#application.url' />installation.action?id=<s:property value='installation.id' />&action=Edit" class="fn1-btn">Edit </a>
			</dd>
		</dl>
	</s:if>	
</div>

<s:if test="installations != null">
	<s:set var="installations" value="installations" />
	<s:set var="installationsTitle" value="installationsTitle" />
	<%@  include file="installations.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

