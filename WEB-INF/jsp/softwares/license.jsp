<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="license" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Software License</h1>
	</s:if>
	<s:else>
		<h1>Edit Software License<s:property value="license.id" /></h1>
		<s:hidden name="license.id" value="%{license.id}" />
		<s:hidden name="license.external_id" value="%{license.external_id}" />		
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
			<dt>License Key </dt>
			<dd><s:textfield name="license.key_value" value="%{license.key_value}" size="30" maxlength="40" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Type </dt>
			<dd><s:select name="license.type" value="%{license.type}" list="#{'IndividualLicense':'IndividualLicense','VolumeLicense':'VolumeLicense'}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Date </dt>
			<dd><s:textfield name="license.created" value="%{license.created}" size="10" maxlength="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Seat limit </dt>
			<dd><s:textfield name="license.seat_limit" value="%{license.seat_limit}" size="5" maxlength="5" /> </dd>
		</dl>		
	</div>
	<s:if test="license.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>
</s:form>
<s:if test="license.id == '' && licenses != null">
	<s:set var="licenses" value="licenses" />
	<s:set var="licensesTitle" value="licensesTitle" />
	<%@  include file="licenses.jsp" %>
</s:if>

<%@  include file="../gui/footer.jsp" %>

