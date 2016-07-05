<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="installation" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Software Installation</h1>
	</s:if>
	<s:else>
		<h1>Edit Software Installation <s:property value="installation.id" /></h1>
		<s:hidden name="installation.id" value="%{installation.id}" />
		<s:hidden name="installation.external_id" value="%{installation.external_id}" />		
	</s:else>
	<s:hidden name="installation.device_id" value="%{installation.device_id}" />	
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
			<s:if test="id == ''">			
				<dt>Software </dt>
				<dd>
					<s:textfield name="installation.software_name" value="%{installation.software_name}" id="software_name" />
					Software ID
					<s:textfield name="installation.software_id" value="%{installation.software_id}" size="6" maxlength="10" id="software_id" />*
				</dd>
			</s:if>
			<s:else>
				<dt>Software ID </dt>
				<dd>
					<s:textfield name="installation.software_id" value="%{installation.software_id}" size="6" maxlength="10" id="software_id" />*					
					<s:property value="installation.software.display_name" />
				</dd>
			</s:else>
		</dl>
		<dl class="fn1-output-field">
			<dt>License Key </dt>
			<dd>
				<s:if test="id == ''">
					<s:textfield name="installation.key_value" value="%{installation.key_value}" size="30" maxlength="30" />* Key Type <s:select name="installation.type" value="%{installation.type}" list="#{'IndividualLicense':'IndividualLicense','VolumeLicense':'VolumeLicense'}" /> 					
				</s:if>
				<s:else>
					<s:textfield name="installation.license_id" value="%{installation.license_id}" size="10" maxlength="10" required="true" />* Key <s:property value="installation.license.key_value" />
				</s:else>
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Device </dt>
			<dd>
				<a href="<s:property value='#application.url' />device.action?id=<s:property value='installation.device_id' />" class="fn1-btn"><s:property value="installation.device" /></a>
			</dd>
		</dl>
		<dl class="fn1-output-field">				
			<dt>Date </dt>
			<dd><s:textfield name="installation.date" value="%{installation.date}" rows="10" cols="10" cssClass="date" /></dd>
		</dl>
	</div>
	<s:if test="id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>
</s:form>
<s:if test="installation.id == '' && installations != null">
	<s:set var="installations" value="installations" />
	<s:set var="installationsTitle" value="installationsTitle" />
	<%@  include file="installations.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>


