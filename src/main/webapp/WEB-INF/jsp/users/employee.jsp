<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="employee" id="form_id" method="post">
    <s:hidden name="action2" id="action2" value="" />
    <s:if test="employee.id == ''">
	<h1>New Employee</h1>
    </s:if>
    <s:else>
	<h1>Employee: <s:property value="employee.fullName" /></h1>
	<s:hidden name="employee.id" value="%{employee.id}" />
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
    <s:if test="employee.id != ''">
	<dl class="fn1-output-field">
	    <dt>ID</dt>
	    <dd><s:property value="%{employee.id}" /></dd>
	</dl>
    </s:if>
    <s:if test="employee.id == ''">
	<p>To add a new Employee, start typing the employee username then pick from the list</p>
	<dl class="fn1-output-field">
	    <dt>Search Employee Using username </dt>
	    <dd><input type="text" id="emp_name" name="employee.fullName" size="30" value="" /></dd>
	</dl>
    </s:if>    
    <dl class="fn1-output-field">
	<dt>Username</dt>
	<dd><s:textfield name="employee.username" id="username_id" size="10" value="%{employee.username}" /></dd>
    </dl>	
    <dl class="fn1-output-field">
	<dt>First Name </dt>
	<dd><s:textfield name="employee.first_name" id="first_name_id" value="%{employee.first_name}" size="30" maxlength="70" /> </dd>
    </dl>
    <dl class="fn1-output-field">
	<dt>Last Name </dt>
	<dd><s:textfield name="employee.last_name" id="last_name_id" value="%{employee.last_name}" size="30" maxlength="70" /> </dd>
    </dl>
    <dl class="fn1-output-field">
	<dt>Office Phone </dt>
	<dd><s:textfield name="employee.office_phone" id="office_phone_id" value="%{employee.office_phone}" size="20" maxlength="20" /> </dd>
    </dl>	
    <dl class="fn1-output-field">
	<dt>Roles</dt>
	<dd><s:select name="employee.role" value="%{employee.role}" list="#{'end_user':'End user','admin':'Admin','helpdesk_admin':'Helpdesk Admin','helpdesk_teck':'Helpdesk Teck'}" /></dd>
    </dl>
    <s:if test="employee.id == ''">
	<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
    </s:if>
    <s:else>
	<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
    </s:else>
</s:form>
<s:if test="id != ''">
    <s:if test="hasDevices()">
	<s:set var="devicesTitle" value="'Devices Associated with Employee'" />
	<s:set var="devices" value="%{devices}" />
	<%@  include file="../devices/devices.jsp" %>		
    </s:if>
</s:if>
<s:elseif test="employees != null && employees.size() > 0">
    <s:set var="employees" value="%{employees}" />
    <s:set var="employeesTitle" value="employeesTitle" />
    <%@  include file="employees.jsp" %>
</s:elseif>

<%@  include file="../gui/footer.jsp" %>


