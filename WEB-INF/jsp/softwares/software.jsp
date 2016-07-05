<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="software" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Software</h1>
	</s:if>
	<s:else>
		<h1>Edit Software <s:property value="software.id" /></h1>
		<s:hidden name="software.id" value="%{software.id}" />
		<s:hidden name="software.external_id" value="%{software.external_id}" />		
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
				You must hit 'Save' button to save data
			</s:else>
	</p>
	<div class="tt-row-container">	
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="software.display_name" value="%{software.display_name}" size="30" maxlength="70" id="name" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Vendor </dt>
			<dd><s:textfield name="software.vendor" value="%{software.vendor}" size="30" maxlength="70" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Install Date </dt>
			<dd><s:textfield name="software.installed" value="%{software.installed}" size="10" maxlength="10" cssClass="date" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">				
			<dt>Installed Count </dt>
			<dd><s:textfield name="software.install_count" value="%{software.install_count}" size="4" maxlength="4" /></dd>
		</dl>
	</div>
	<s:if test="software.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url' />license.action" class="fn1-btn">Add License Key </a>				
	</s:else>
</s:form>
<s:if test="software.id == '' && softwares != null">
	<s:set var="softwares" value="softwares" />
	<s:set var="softwaresTitle" value="softwaresTitle" />
	<%@  include file="softwares.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

