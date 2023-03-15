<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="softwareSearch" id="form_id" method="post">

	<h1>Search Softwares</h1>
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
	<p>To add a new software, click on 'New Software' button below.</p>
	<dl class="fn1-output-field">
		<dt>ID</dt>
		<dd><s:textfield name="softwareList.id" size="10" value="%{softwareList.id}" /></dd>		
	</dl>
<dl class="fn1-output-field">
		<dt>External ID</dt>
		<dd><s:textfield name="softwareList.external_id" size="10" value="%{softwareList.external_id}" /></dd>		
	</dl>	
	<dl class="fn1-output-field">
		<dt>Name</dt>
		<dd><s:textfield name="softwareList.name" size="30" value="%{softwareList.name}" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Vendor </dt>
		<dd><s:select name="softwareList.vendor" value="%{softwareList.vendor}" list="vendors" listKey="id" listValue="name" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Install Date, From: </dt>
		<dd>
			<s:textfield name="softwareList.date_from" value="%{softwareList.date_from}" size="10" maxlength="10" cssClass="date" /> To:
			<s:textfield name="softwareList.date_to" value="%{softwareList.date_to}" size="10" maxlength="10" cssClass="date" />
		</dd>
	</dl>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
	<a href="<s:property value='#application.url' />software.action" class="fn1-btn">New Software </a>	
</s:form>
<s:if test="softwares != null && softwares.size() > 0">
	<s:set var="softwares" value="%{softwares}" />
	<s:set var="softwaresTitle" value="softwaresTitle" />
	<%@  include file="softwares.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>


