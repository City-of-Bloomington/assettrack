<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h1>Printer <s:property value="printer.id" /></h1>
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
				<dt>ID</dt>
				<dd><s:property value="%{printer.id}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>External ID</dt>
				<dd><s:property value="%{printer.external_id}" /></dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt>Related Device </dt>
				<dd><a href="<s:property value='#application.url' />device.action?id=<s:property value='printer.device_id' />"> <s:property value="printer.device_id" /></a></dd>			
			</dl>
			<dl class="fn1-output-field">
				<dt>Asset Num</dt>
				<dd><s:property value="%{printer.asset_num}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Serial Num</dt>
				<dd><s:property value="%{printer.serial_num}" /></dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Name</dt>
				<dd><s:property value="%{printer.name}" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">					
			<dl class="fn1-output-field">
				<dt>Print Processor </dt>
				<dd><s:property value="%{printer.print_processor}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Description </dt>
				<dd><s:property value="%{printer.description}" /> </dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt>Date</dt>
			<dd><s:property value="%{printer.date}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Inventory Date</dt>
			<dd><s:property value="%{printer.inventory_date}" /> </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Status</dt>
				<dd><s:property value="%{printer.status}" /> </dd>
			</dl>		
		</div>
	</div>
	<s:if test="printer.anyUpdate()">
		<a href="<s:property value='#application.url'/>printer.action?id=<s:property value='printer.id' />&action=Edit" class="fn1-btn"> Edit </a>
	</s:if>
	<a href="<s:property value='#application.url'/>dispose.action?asset_id=<s:property value='printer.id' />&type=Printer&asset_num=<s:property value='printer.asset_num' />" class="fn1-btn"> Dispose This Printer</a>	
	<a href="<s:property value='#application.url' />doUpload.action?obj_type=Printer&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>		
	<a href="<s:property value='#application.url' />printer.action" class="fn1-btn">New Printer </a>		

<%@  include file="../gui/footer.jsp" %>


