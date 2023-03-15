<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="report" id="form_id" method="post">
	<s:hidden name="deviceList.status" value="Active" />
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
  <h3>Cap R Reports </h3>
	Note: use year or date range but not both. <br />
	If you use year and date range at the same time, date range will be ignored.
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Installed Year </dt>
			<dd><s:select name="deviceList.year" value="%{deviceList.year}" list="years" listKey="id" listValue="name" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Installed Date </dt>
			<dd> from <s:textfield name="deviceList.date_from" value="%{deviceList.date_from}" size="10" cssClass="date" /> to
				<s:textfield name="deviceList.date_to" value="%{deviceList.date_to}" size="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Category </dt>
			<dd><s:select name="deviceList.category_id" value="%{deviceList.category_id}" list="%{categories}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Domain </dt>
			<dd><s:select name="deviceList.domain_id" value="%{deviceList.domain_id}" list="%{domains}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Department </dt>
			<dd><s:select name="deviceList.dept_id" value="%{deviceList.dept_id}" list="%{depts}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> 
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Division </dt>
			<dd><s:select name="deviceList.division_id" value="%{deviceList.division_id}" list="%{divisions}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> 
			</dd>
		</dl>
		
		<dl class="fn1-output-field">
			<dt>Location </dt>
			<dd><s:select name="deviceList.location_id" value="%{deviceList.location_id}" list="%{locations}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
		</dl>
	</div>	
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
	<s:submit name="action" type="button" value="Save to CSV" class="fn1-btn"/>	
</s:form>
<s:if test="devices != null">
	<s:set var="devices" value="devices" />	
	<s:set var="devicesTitle" value="devicesTitle" />
	<%@  include file="devices.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

