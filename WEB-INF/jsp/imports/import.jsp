<%@ include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="import" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Data Import</h1>
	</s:if>
	<s:else>
		<h1>View Import <s:property value="impman.id" /></h1>
		<s:hidden name="impman.id" value="%{impman.id}" />
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
	<s:if test="impman.hasErrors()">
		<ul>
		<s:iterator var="one" value="%{impman.errors}">
			<li><s:property /> </li>
		</s:iterator>
		</ul>
	</s:if>
	<p>check the fields that you want to be imported from spiceworks</p>
	<div class="tt-row-container">
		<s:if test="impman.id != ''">
			<dl class="fn1-output-field">
				<dt>Date </dt>
				<dd><s:property value="%{impman.date}" /> 
				</dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>1 - </dt>
			<dd><s:checkbox name="impman.employeeFlag" value="%{impman.employeeFlag}" /> Employees </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>2 - </dt>
			<dd><s:checkbox name="impman.divisionFlag" value="%{impman.divisionFlag}" /> Divisions </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>3 - </dt>
			<dd><s:checkbox name="impman.locationFlag" value="%{impman.locationFlag}" /> Locations </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>4 - </dt>
			<dd><s:checkbox name="impman.deviceFlag" value="%{impman.deviceFlag}" /> Devices </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>5 - </dt>
			<dd><s:checkbox name="impman.monitorFlag" value="%{impman.monitorFlag}" /> Monitors </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>6 - </dt>
			<dd><s:checkbox name="impman.softwareFlag" value="%{impman.softwareFlag}" /> Software Licenses </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>7 - </dt>
			<dd><s:checkbox name="impman.printerFlag" value="%{impman.printerFlag}" /> Printers </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>8 - </dt>
			<dd><s:checkbox name="impman.ticketsFlag" value="%{impman.ticketsFlag}" /> Tickets </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>9 - </dt>
			<dd><s:checkbox name="impman.autoImportFlag" value="%{impman.autoImportFlag}" /> Simulate Schedule Import (Test purpose only)</dd>
		</dl>		
		
		<s:if test="id == ''">
			<s:submit name="action" type="button" value="Run Imports" class="fn1-btn"/></dd>
		</s:if>
	</div>
</s:form>
<s:if test="imports != null && imports.size() > 0">
	<s:set var="imports" value="imports" />
	<s:set var="importsTitle" value="importsTitle" />
	<%@  include file="imports.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>




