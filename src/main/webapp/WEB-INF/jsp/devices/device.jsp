<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="device" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Device</h1>
	</s:if>
	<s:else>
	    <h1>Edit Device <s:property value="device.id" /></h1>
	    <s:hidden name="device.id" value="%{device.id}" />
	    <s:hidden name="device.replaced_id" value="%{device.replaced_id}" />    
	    <s:hidden name="device.external_id" value="%{device.external_id}" />		
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
      To add/change an employee to this device start typing the name in the employee field below to pick from the list<br />
      <s:if test="id != ''">
	  If you make any change, please hit the 'Save Changes' button
      </s:if>
      <s:else>
	  Use location if no employee was assigned to this device.<br /> 				
	  You must hit 'Save' button to save data.
      </s:else>
  </p>
  <div class="tt-row-container">
      <dl class="fn1-output-field">
	  <dt>Employee </dt>
	  <dd><s:textfield name="device.employee_name" value="%{device.employee_name}" size="20" maxlength="20" id="employee_name" />Employee ID
	      <s:textfield name="device.employee_id" value="%{device.employee_id}" size="20" maxlength="20" id="employee_id" />
	  </dd>
      </dl>			
      <dl class="fn1-output-field">
	  <dt>Location </dt>
	  <dd><s:select name="device.location_id" value="%{device.location_id}" list="%{locations}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Location" />
	  </dd>
      </dl>
      <div class="tt-split-container">
	  <dl class="fn1-output-field">
	      <dt>Name </dt>
	      <dd><s:textfield name="device.name" value="%{device.name}" size="20" maxlength="70" id="name" required="true" />* </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Asset Num </dt>
	      <dd><s:textfield name="device.asset_num" value="%{device.asset_num}" size="10" maxlength="10" id="asset_num" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Tag Code </dt>
	      <dd><s:textfield name="device.tag_code" value="%{device.tag_code}" size="15" maxlength="20" id="tag_code" /> </dd>
	  </dl>	  
	  <s:if test="id != ''">			
	      <dl class="fn1-output-field">
		  <dt>Mark as </dt>
		  <dd><s:checkbox name="device.voided" value="%{device.voided}" /> Void (for duplicates)</dd>
	      </dl>
	  </s:if>
	  <s:if test="device.replaced_id != ''">		
	      <dl class="fn1-output-field">
		  <dt>Replace Old Device</dt>
		  <dd>
		      <a href="<s:property value='#application.url'/>device.action?id=<s:property value='device.replaced_id' />" class="fn1-btn"> Device
			  <s:property value="%{device.replaced_id}" />
		      </a>
		  </dd>
	      </dl>
	  </s:if>	      
	  <s:if test="device.hasNewReplacement()">
	      <dl class="fn1-output-field">	      
		  <dt>Replaced by New </dt>
		  <dd>
		      <a href="<s:property value='#application.url'/>device.action?id=<s:property value='device.new_replace_id' />" class="fn1-btn"> Device
			  <s:property value="%{device.new_replace_id}" />
		      </a>
		  </dd>
	      </dl>
	  </s:if>	  
	  <dl class="fn1-output-field">
	      <dt>Replaces Asset Num</dt>
	      <dd><s:textfield name="device.replace_asset_num" value="%{device.replace_asset_num}" size="10" maxlength="10" /> </dd>
	  </dl>
	  
	  <dl class="fn1-output-field">
	      <dt>Related Device </dt>
	      <dd><s:textfield name="device.related_id" value="%{device.related_id}" size="10" maxlength="10" /> (ID) </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Serial Num </dt>
	      <dd><s:textfield name="device.serial_num" value="%{device.serial_num}" size="20" maxlength="30"  /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Model </dt>
	      <dd><s:textfield name="device.model" value="%{device.model}" size="20" maxlength="30" /> </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Description </dt>
	      <dd><s:textfield name="device.description" value="%{device.description}" size="25" maxlength="70" /> </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Category </dt>
	      <dd><s:select name="device.category_id" value="%{device.category_id}" list="%{categories}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Category" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Domain </dt>
	      <dd><s:select name="device.domain_id" value="%{device.domain_id}" list="%{domains}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Domain" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Division </dt>
	      <dd><s:select name="device.division_id" value="%{device.division_id}" list="%{divisions}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Division" /> 
	      </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Installed Date </dt>
	      <dd><s:textfield name="device.installed" value="%{device.installed}" size="10" maxlength="10" cssClass="date" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Status </dt>
	      <dd><s:property value="%{device.status}" /> 
	      </dd>
	  </dl>
	  
      </div>
      <div class="tt-split-container">		
	  <dl class="fn1-output-field">
	      <dt>Mac Address </dt>
	      <dd><s:textfield name="device.mac_address" value="%{device.mac_address}" size="20" maxlength="20" /> 
	      </dd>
	  </dl>				
	  <dl class="fn1-output-field">
	      <dt>IP Address </dt>
	      <dd><s:textfield name="device.ip_address" value="%{device.ip_address}" size="20" maxlength="20" /> 
	      </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Processor </dt>
	      <dd><s:textfield name="device.processor" value="%{device.processor}" size="30" maxlength="50" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Ram </dt>
	      <dd><s:textfield name="device.ram" value="%{device.ram}" size="20" maxlength="20" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Hard Drive Size </dt>
	      <dd><s:textfield name="device.hd_size" value="%{device.hd_size}" size="20" maxlength="20" />(GB) 
	      </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Expected Age </dt>
	      <dd><s:textfield name="device.age_length" value="%{device.age_length}" size="3" maxlength="3" />(Years) 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Cost ($) </dt>
	      <dd><s:textfield name="device.cost" value="%{device.cost}" size="10" maxlength="10" />
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">				
	      <dt>Notes </dt>
	      <dd><s:textarea name="device.notes" value="%{device.notes}" rows="5" cols="50" /></dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Inventory Date </dt>
	      <dd><s:textfield name="device.inventory_date" value="%{device.inventory_date}" size="10" maxlength="10" cssClass="date" /> </dd>
	  </dl>			
      </div>
  </div>
  <s:if test="device.id == ''">
      <s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
  </s:if>
  <s:else>
      <s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
      <s:if test="device.checkForDups()">
	  <s:submit name="action" type="button" value="Mark As Duplicate" class="fn1-btn"/>
      </s:if>
      <s:if test="device.status == 'Active'">		
	  <a href="<s:property value='#application.url'/>installation.action?device_id=<s:property value='device.id' />" class="fn1-btn"> Software Installation</a>
	  <a href="<s:property value='#application.url'/>monitor.action?device_id=<s:property value='device.id' />" class="fn1-btn">New Monitor</a>
	  <a href="<s:property value='#application.url'/>printer.action?device_id=<s:property value='device.id' />" class="fn1-btn">New Printer</a>
	  <a href="<s:property value='#application.url'/>device.action?related_id=<s:property value='device.id' />" class="fn1-btn">New Related Device</a>	
	  <a href="<s:property value='#application.url'/>dispose.action?asset_id=<s:property value='device.id' />&type=Device&asset_num=<s:property value='device.asset_num'/>" class="fn1-btn"> Dispose This Device</a>				
	  <a href="<s:property value='#application.url' />doUpload.action?obj_type=Device&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>				
      </s:if>
      <s:if test="device.hasMonitors()" >
	  <s:set var="monitorsTitle" value="'Related Monitors'" />
	  <s:set var="monitors" value="%{device.monitors}" />
	  <%@  include file="../monitors/monitors.jsp" %>			
      </s:if>
      <s:if test="device.hasInstallations()" >
	  <s:set var="installationsTitle" value="'Installed Software'" />
	  <s:set var="installations" value="%{device.installations}" />
	  <%@  include file="../softwares/installations.jsp" %>			
      </s:if>
      <s:if test="device.hasPrinters()" >
	  <s:set var="printersTitle" value="'Attached Printers'" />
	  <s:set var="printers" value="%{device.printers}" />
	  <%@  include file="../printers/printers.jsp" %>			
      </s:if>
      <s:if test="device.hasRelatedDevices()" >
	  <s:set var="devicesTitle" value="'Attached Devices'" />
	  <s:set var="devices" value="%{device.relatedDevices}" />
	  <%@  include file="devices.jsp" %>			
      </s:if>			
  </s:else>
</s:form>

<%@  include file="../gui/footer.jsp" %>

