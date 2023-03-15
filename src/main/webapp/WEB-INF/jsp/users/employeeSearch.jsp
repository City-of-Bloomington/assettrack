<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="employeeSearch" id="form_id" method="post">
	<h1>Search Employees</h1>
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
	Note: to search for certain employee start typing her/his name in the 'Employee Name' field for auto complete.
	<div class="tt-row-container">
		<div class="tt-split-container">	
			<dl class="fn1-output-field">
					<dt>Employee Name </dt>
				<dd><s:textfield name="employeeList.name" value="%{employeeList.name}" size="25" maxlength="70" id="employee_name" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Employee ID</dt>
				<dd><s:textfield name="employeeList.id" size="10" value="%{employeeList.id}" id="employee_id" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>External ID</dt>
				<dd><s:textfield name="employeeList.external_id" size="10" value="%{employeeList.external_id}" /></dd>
			</dl>			
		</div>
		<div class="tt-split-container">			
			<dl class="fn1-output-field">
				<dt>Username</dt>
				<dd><s:textfield name="employeeList.username" size="10" value="%{employeeList.username}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Roles</dt>
				<dd><s:select name="employeeList.role" value="%{employeeList.role}" list="#{'-1':'All','end_user':'End user','admin':'Admin','helpdesk_admin':'Helpdesk Admin','helpdesk_teck':'Helpdesk Teck'}" /></dd>
			</dl>
		</div>
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
	<a href="<s:property value='#application.url' />employee.action" class="fn1-btn">New Employee </a>	
</s:form>
<s:if test="employees != null">
	<s:set var="employees" value="%{employees}" />
	<s:set var="employeesTitle" value="employeesTitle" />
	<%@  include file="employees.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>


