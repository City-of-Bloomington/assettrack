<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="user" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="user.id == ''">
		<h1>New User</h1>
	</s:if>
	<s:else>
		<h1>User <s:property value="user.fullname" /></h1>
		<s:hidden name="user.id" value="%{user.id}" />
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
	<s:if test="user.id != ''">
		<dl class="fn1-output-field">
			<dt>ID</dt>
			<dd><s:property value="%{user.id}" /></dd>
		</dl>
	</s:if>
	<dl class="fn1-output-field">
		<dt>Username</dt>
		<dd><s:textfield name="user.username" size="10" value="%{user.username}" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>First Name </dt>
		<dd><s:textfield name="user.first_name" value="%{user.first_name}" size="30" maxlength="70" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Last Name </dt>
		<dd><s:textfield name="user.last_name" value="%{user.last_name}" size="30" maxlength="70" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Office Phone </dt>
		<dd><s:textfield name="user.office_phone" value="%{user.office_phone}" size="20" maxlength="20" /> </dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Roles</dt>
		<dd><s:select name="user.role" value="%{user.role}" list="#{'end_user':'End user','View':'View Only','Edit':'Edit','Edit:Admin':'All (Admin)'}" /></dd>
	</dl>
	<s:if test="user.id == ''">
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
<s:elseif test="users != null && users.size() > 0">
	<s:set var="users" value="%{users}" />
	<s:set var="usersTitle" value="usersTitle" />
	<%@  include file="users.jsp" %>
</s:elseif>

<%@  include file="../gui/footer.jsp" %>


