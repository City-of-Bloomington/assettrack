<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="printer" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden name="printer.id" value="%{printer.id}" />
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
				<dt>External ID</dt>
				<dd><s:property value="%{printer.external_id}" /></dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt>Related Device </dt>
				<dd><a href="<s:property value='#application.url' />device.action?id=<s:property value='printer.device_id' />"> <s:property value="printer.device_id" /></a></dd>			
			</dl>
			<dl class="fn1-output-field">
				<dt>Asset Num</dt>
				<dd><s:textfield name="printer.asset_num" value="%{printer.asset_num}" size="15" maxlength="15" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Serial Num</dt>
				<dd><s:textfield name="printer.serial_num" value="%{printer.serial_num}" size="20" maxlength="30" /></dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Name</dt>
				<dd><s:property value="%{printer.name}" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Print Processor </dt>
				<dd><s:property value="%{printer.print_processor}" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Status</dt>
				<dd><s:property value="%{printer.status}" /> </dd>
			</dl>		
		</div>
		<div class="tt-split-container">					

			<dl class="fn1-output-field">
				<dt>Description </dt>
				<dd><s:property value="%{printer.description}" /> </dd>
			</dl>	
			<dl class="fn1-output-field">
				<dt>Date</dt>
			<dd><s:textfield name="printer.date" value="%{printer.date}" size="10" maxlength="10" cssClass="date" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Inventory Date</dt>
			<dd><s:textfield name="printer.inventory_date" value="%{printer.inventory_date}" size="10" maxlength="10" cssClass="date" /> </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Notes</dt>
				<dd><s:textarea name="printer.notes" value="%{printer.notes}" rows="10" cols="30" /> </dd>
			</dl>						
		</div>
	</div>
	<s:submit name="action" type="button" value="Save Partial Changes" class="fn1-btn" />
<a href="<s:property value='#application.url'/>dispose.action?asset_id=<s:property value='printer.id' />&type=Printer&asset_num=<s:property value='printer.asset_num' />" class="fn1-btn"> Dispose This Printer</a>

	<a href="<s:property value='#application.url' />doUpload.action?obj_type=Printer&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>			
</s:form>
<%@  include file="../gui/footer.jsp" %>


