<%@  include file="../gui/header.jsp" %>
<!--
     * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
     * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
     * @author W. Sibo <sibow@bloomington.in.gov>
     *
-->
<s:form action="device" id="form_id" method="post">
    <s:hidden name="action2" id="action2" value="" />
    <s:hidden name="device.id" value="%{device.id}" />
    <s:hidden name="device.replaced_id" value="%{device.replaced_id}" />    
    <h1>Edit Device <s:property value="device.id" /></h1>
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
	      <dt>External ID </dt>
	      <dd><s:property value="%{device.external_id}" /> </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Name </dt>
	      <dd><s:property value="%{device.name}" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Asset Num </dt>
	      <dd><s:textfield name="device.asset_num" value="%{device.asset_num}" size="10" maxlength="10" id="asset_num" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Tag Code </dt>
	      <dd><s:textfield name="device.tag_code" value="%{device.tag_code}" size="15" maxlength="20" id="tag_code" /> </dd>
	  </dl>
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
	      <dt>Mark as </dt>
	      <dd><s:checkbox name="device.voided" value="%{device.voided}" /> Void (for duplicates)</dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Replaces Asset Num </dt>
	      <dd><s:textfield name="device.replace_asset_num" value="%{device.replace_asset_num}" size="10" maxlength="10" /> 					
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Related Device </dt>
	      <dd><s:textfield name="device.related_id" value="%{device.related_id}" size="10" maxlength="10" /> (ID) 					
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Serial Num </dt>
	      <dd><s:property value="%{device.serial_num}" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Model </dt>
	      <dd><s:property value="%{device.model}" /> </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Employee </dt>
	      <dd><s:property value="%{device.employee}" /> </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Description </dt>
	      <dd><s:property value="%{device.description}" /> </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Category </dt>
	      <dd><s:property value="%{device.category}" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Domain </dt>
	      <dd><s:property value="%{device.domain}" /> 
	      </dd>
	  </dl>				
	  <dl class="fn1-output-field">
	      <dt>Division </dt>
	      <dd><s:select name="device.division_id" value="%{device.division_id}" list="%{divisions}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Division" /> 					
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Status </dt>
	      <dd><s:property value="%{device.status}" /> 
	      </dd>
	  </dl>
      </div>
      <div class="tt-split-container">
	  <dl class="fn1-output-field">
	      <dt>Installed Date </dt>
	      <dd><s:property value="%{device.installed}" /> 					
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Mac Address </dt>
	      <dd><s:property value="%{device.mac_address}" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>IP Address </dt>
	      <dd><s:property value="%{device.ip_address}" /> </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Processor </dt>
	      <dd><s:property value="%{device.processor}" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Ram </dt>
	      <dd><s:property value="%{device.ram}" /> (GB)
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Hard Drive Size </dt>
	      <dd><s:property value="%{device.hd_size}" /> (GB) 
	      </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Cost ($) </dt>
	      <dd><s:textfield name="device.cost" value="%{device.cost}" size="10" maxlength="10" />
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Expected Age </dt>
	      <dd><s:textfield name="device.age_length" value="%{device.age_length}" size="3" maxlength="3" />(Years) 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">				
	      <dt>Notes </dt>
	      <dd><s:textarea name="device.notes" value="%{device.notes}" rows="5" cols="50" /></dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Inventory Date </dt>
	      <dd><s:textfield name="device.inventory_date" value="%{device.inventory_date}" size="10" maxlength="10" cssClass="date" />
	      </dd>
	  </dl>			
      </div>
  </div>
  <dl class="fn1-output-field">
      <dt>Location </dt>
      <dd><s:select name="device.location_id" value="%{device.location_id}" list="%{locations}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Location" /> 					
      </dd>
  </dl>
  <s:submit name="action" type="button" value="Save Partial Changes" class="fn1-btn"/>
  <a href="<s:property value='#application.url'/>installation.action?device_id=<s:property value='device.id' />" class="fn1-btn">New Software Installation</a>
  <a href="<s:property value='#application.url'/>monitor.action?device_id=<s:property value='device.id' />" class="fn1-btn">New Monitor</a>
  <a href="<s:property value='#application.url'/>printer.action?device_id=<s:property value='device.id' />" class="fn1-btn">New Printer</a>
<a href="<s:property value='#application.url'/>device.action?related_id=<s:property value='device.id' />" class="fn1-btn">New Related Device</a>	
<a href="<s:property value='#application.url'/>dispose.action?asset_id=<s:property value='device.id' />&type=Device&asset_num=<s:property value='device.asset_num'/>" class="fn1-btn"> Dispose This Device</a>	

</s:form>
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
<%@  include file="../gui/footer.jsp" %>

