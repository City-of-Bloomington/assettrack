<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="userSearch" id="form_id" method="post">
	<h1>Search Users</h1>
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
	<dl class="fn1-output-field">
		<dt>Employee ID</dt>
		<dd><s:textfield name="userList.id" size="10" value="%{userList.id}" id="user_id" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Username</dt>
		<dd><s:textfield name="userList.username" size="10" value="%{userList.username}" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Name </dt>
		<dd><s:textfield name="userList.name" value="%{userList.name}" size="30" maxlength="70" id="user_name" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Roles</dt>
		<dd><s:select name="userList.role" value="%{userList.role}" list="#{'-1':'All','end_user':'End user','View':'View Only','Edit':'Edit','Edit:Admin':'Admin'}" /></dd>
	</dl>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
	<a href="<s:property value='#application.url' />user.action" class="fn1-btn">New User </a>	
</s:form>
<s:if test="users != null">
	<s:set var="users" value="%{users}" />
	<s:set var="usersTitle" value="usersTitle" />
	<%@  include file="users.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>


