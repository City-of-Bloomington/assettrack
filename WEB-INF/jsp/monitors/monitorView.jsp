<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h1>Monitor <s:property value="monitor.id" /></h1>
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
<div class="tt-row-container">
	<div class="tt-split-container">			
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:property value="%{monitor.name}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>External ID </dt>
			<dd><s:property value="%{monitor.external_id}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Related Device </dt>
			<dd><a href="<s:property value='#application.url' />device.action?id=<s:property value='%{monitor.device_id}' />"><s:property value="%{monitor.device_id}" /> </a></dd>			
		</dl>		
		<dl class="fn1-output-field">
			<dt>Asset Num </dt>
			<dd><s:property value="%{monitor.asset_num}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Serial Num </dt>
			<dd><s:property value="%{monitor.serial_num}" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Model </dt>
			<dd><s:property value="%{monitor.model}" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Screen Size </dt>
			<dd><s:property value="%{monitor.screen_size}" /> </dd>
		</dl>			
	</div>
	<div class="tt-split-container">
		<dl class="fn1-output-field">
			<dt>Type </dt>
			<dd><s:property value="%{monitor.type}" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Rsolution </dt>
			<dd>Vert <s:property value="%{monitor.vertical_resolution}" />
				Horiz <s:property value="%{monitor.horizontal_resolution}" />
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Manufacturer </dt>
			<dd><s:property value="%{monitor.manufacturer}" /> 				
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Received </dt>
			<dd><s:property value="%{monitor.received}" /> 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Expected Age </dt>
			<dd><s:property value="%{monitor.expected_age}" />(Years) 
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Inventory Date </dt>
			<dd><s:property value="%{monitor.inventory_date}" /> 
			</dd>
		</dl>		
		<dl class="fn1-output-field">				
			<dt>Notes </dt>
			<dd><s:property value="%{monitor.notes}" /></dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Status </dt>
			<dd><s:property value="%{monitor.status}" /> 
			</dd>
		</dl>
	</div>
	
</div>
	<s:if test="monitor.anyUpdate()">
		<a href="<s:property value='#application.url'/>monitor.action?id=<s:property value='monitor.id' />&action=Edit" class="fn1-btn"> Edit </a>			
	</s:if>
<a href="<s:property value='#application.url'/>dispose.action?type=Monitor&asset_id=<s:property value='monitor.id' />&asset_num=<s:property value='monitor.asset_num' />" class="fn1-btn"> Dispose This Monitor</a>	
	<a href="<s:property value='#application.url' />doUpload.action?obj_type=Monitor&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>		

<%@  include file="../gui/footer.jsp" %>

