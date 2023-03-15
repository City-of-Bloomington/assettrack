<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="organization" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Organization</h1>
	</s:if>
	<s:else>
		<h1>Edit Organization <s:property value="%{organization.id}" /></h1>
		<s:hidden name="organization.id" value="%{organization.id}" />
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
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="organization.name" value="%{organization.name}" size="30" maxlength="70" requiredLabel="true" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Address </dt>
			<dd><s:textfield name="organization.address" value="%{organization.address}" size="30" maxlength="70" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>City </dt>
			<dd><s:textfield name="organization.city" value="%{organization.city}" size="30" maxlength="70" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>State </dt>
			<dd><s:textfield name="organization.state" value="%{organization.state}" size="2" maxlength="2" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Zip </dt>
			<dd><s:textfield name="organization.zip" value="%{organization.zip}" size="15" maxlength="15" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Contact Name </dt>
			<dd><s:textfield name="organization.contact" value="%{organization.contact}" size="30" maxlength="70" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Phone </dt>
			<dd><s:textfield name="organization.phone" value="%{organization.phone}" size="30" maxlength="30" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Is Inactive? </dt>
			<dd><s:checkbox name="organization.inactive" value="%{organization.inactive}" />  </dd>
		</dl>		
		<s:if test="organization.id == ''">
			<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
			<a href="<s:property value='#application.url' />doUpload.action?obj_type=Organization&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>		
		</s:else>
	</div>
</s:form>
<s:if test="organizations != null">
	<s:set var="organizations" value="organizations" />
	<s:set var="organizationsTitle" value="organizationsTitle" />
	<%@  include file="organizations.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>


