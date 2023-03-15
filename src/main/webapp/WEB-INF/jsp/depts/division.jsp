<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="division" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Division</h1>
	</s:if>
	<s:else>
		<h1>Edit Division</h1>
		<s:hidden name="division.id" value="%{division.id}" />
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
  <p>* Required field <br />
		<s:if test="id != ''">
			If you make any change, please hit the 'Save Changes' button
		</s:if>
		<s:else>
			You must hit 'Save' button to save data.
		</s:else>
	</p>
	<div class="tt-row-container">
		<s:if test="division.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="division.id" /> 
			</dl>
		</s:if>		
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="division.name" value="%{division.name}" size="30" maxlength="70" requiredLabel="true" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Department </dt>
			<dd><s:select name="division.dept_id" value="%{division.dept_id}" list="depts" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Department" required="true" />* </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Code </dt>
			<dd><s:textfield name="division.code" value="%{division.code}" size="3" maxlength="3"  /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Inactive?</dt>
			<dd><s:checkbox name="division.inactive" value="%{division.inactive}" /> Yes </dd>
		</dl>
		<s:if test="division.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="id == ''">
	<s:set var="divisions" value="divisions" />
	<s:set var="divisionsTitle" value="divisionsTitle" />
	<%@  include file="divisions.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>


