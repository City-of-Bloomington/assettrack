<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="monitor" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Monitor</h1>
	</s:if>
	<s:else>
		<h1>Edit Monitor <s:property value="monitor.id" /></h1>
		<s:hidden name="monitor.id" value="%{monitor.id}" />
		<s:hidden name="monitor.external_id" value="%{monitor.external_id}" />		
	</s:else>
	<s:hidden name="monitor.device_id" value="%{monitor.device_id}" />
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
				** Mark checkbox to delete <br />
				If you make any change, please hit the 'Save Changes' button
			</s:if>
			<s:else>
				You must hit 'Save' button to save data
			</s:else>
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt>Name </dt>
				<dd><s:textfield name="monitor.name" value="%{monitor.name}" size="20" maxlength="70" id="name" required="true" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Related Device </dt>
				<dd><a href="<s:property value='#application.url' />device.action?id=<s:property value='%{monitor.device_id}' />"><s:property value="%{monitor.device_id}" /> </a></dd>			
			</dl>		
			<dl class="fn1-output-field">
				<dt>Asset Num </dt>
				<dd><s:textfield name="monitor.asset_num" value="%{monitor.asset_num}" size="10" maxlength="10" id="asset_num" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Serial Num </dt>
				<dd><s:textfield name="monitor.serial_num" value="%{monitor.serial_num}" size="20" maxlength="30"  /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Model </dt>
				<dd><s:textfield name="monitor.model" value="%{monitor.model}" size="20" maxlength="30" /> </dd>
			</dl>		
			<dl class="fn1-output-field">
				<dt>Screen Size </dt>
				<dd><s:textfield name="monitor.screen_size" value="%{monitor.screen_size}" size="4" maxlength="4" /> </dd>
			</dl>			
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Type </dt>
				<dd><s:textfield name="monitor.type" value="%{monitor.type}" size="20" maxlength="30" /> 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Rsolution </dt>
				<dd>Vert <s:textfield name="monitor.vertical_resolution" value="%{monitor.vertical_resolution}" size="4" maxlength="4" />
					Horiz <s:textfield name="monitor.horizontal_resolution" value="%{monitor.horizontal_resolution}" size="4" maxlength="4" />				
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Manufacturer </dt>
				<dd><s:textfield name="monitor.manufacturer" value="%{monitor.manufacturer}" size="20" maxlength="50" /> 				
			</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Received </dt>
				<dd><s:textfield name="monitor.received" value="%{monitor.received}" size="10" maxlength="10" cssClass="date" /> 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Expected Age </dt>
				<dd><s:textfield name="monitor.expected_age" value="%{monitor.expected_age}" size="3" maxlength="3" />(Years) 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Inventory Date </dt>
				<dd><s:textfield name="monitor.inventory_date" value="%{monitor.inventory_date}" size="10" maxlength="10" cssClass="date" /> 
				</dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Status </dt>
				<dd><s:property value="%{monitor.status}" /></dd>			
			</dl>
		</div>
		<dl class="fn1-output-field">				
			<dt>Notes </dt>
			<dd><s:textarea name="monitor.notes" value="%{monitor.notes}" rows="5" cols="50" /></dd>
		</dl>
	</div>
	<s:if test="monitor.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url'/>dispose.action?type=Monitor&asset_id=<s:property value='monitor.id' />&asset_num=<s:property value='monitor.asset_num' />" class="fn1-btn"> Dispose This Monitor</a>
		<a href="<s:property value='#application.url' />doUpload.action?obj_type=Monitor&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>				
	</s:else>
</s:form>
<%@  include file="../gui/footer.jsp" %>

