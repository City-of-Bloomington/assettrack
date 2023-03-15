<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="dept" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Department</h1>

	</s:if>
	<s:else>
		<h1>Edit Department</h1>
		<s:hidden name="dept.id" value="%{dept.id}" />
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
		<s:if test="dept.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="dept.id" /> 
			</dl>
		</s:if>		
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="dept.name" value="%{dept.name}" size="30" maxlength="70" requiredLabel="true" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Inactive?</dt>
			<dd><s:checkbox name="dept.inactive" value="%{dept.inactive}" fieldValue="true" />Yes </dd>
		</dl>
		<s:if test="dept.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="id == ''">
	<s:set var="depts" value="depts" />
	<s:set var="deptsTitle" value="deptsTitle" />
	<%@  include file="depts.jsp" %>
</s:if>
<s:elseif test="dept.divisions != null">
	<s:set var="divisions" value="dept.divisions" />
	<s:set var="divisionsTitle" value="'Department Divisions '" />
	<%@  include file="divisions.jsp" %>	
</s:elseif>
<%@  include file="../gui/footer.jsp" %>


