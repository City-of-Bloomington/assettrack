<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h1> Software <s:property value="software.id" /></h1>
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
			<dt>Name </dt>
			<dd><s:property value="%{software.display_name}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>External ID </dt>
			<dd><s:property value="%{software.external_id}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Vendor </dt>
			<dd><s:property value="%{software.vendor}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Install Date </dt>
			<dd><s:property value="%{software.installed}" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">				
			<dt>Installed Count </dt>
			<dd><s:property value="%{software.install_count}" /></dd>
		</dl>
		<s:if test="software.canEdit()">		
			<dl class="fn1-output-field">
				<dt> </dt>
				<dd>
					<a href="<s:property value='#application.url' />software.action?id=<s:property value='software.id' />&action=Edit" class="fn1-btn">Edit </a>		
				</dd>
			</dl>
		</s:if>
	</div>
<s:if test="software.hasInstallations()">
	<s:set var="installations" value="software.installations" />
	<s:set var="installationsTitle" value="'Software Installed Devices'" />
	<%@  include file="installations.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

