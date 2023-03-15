<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h1>Device <s:property value="device.id" /></h1>
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
	      <dt>Related Device </dt>
	      <dd>
		  <s:if test="device.hasRelated()">
		      <a href="<s:property value='#application.url'/>device.action?id=<s:property value='device.related_id' />" class="fn1-btn">Related Device <s:property value="device.related_id" /></a>		
		  </s:if>&nbsp;
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Name </dt>
	      <dd><s:property value="%{device.name}" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Asset Num </dt>
	      <dd><s:property value="%{device.asset_num}" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Serial Num </dt>
	      <dd><s:property value="%{device.serial_num}" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Tag Code </dt>
	      <dd><s:property value="%{device.tag_code}" /> </dd>
	  </dl>
	  <s:if test="device.replaced_id != ''">
	      <dl class="fn1-output-field">	      
		  <dt>Replaced Old </dt>
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
	  <s:if test="device.voided">
	      <dl class="fn1-output-field">	      
		  <dt>Voided </dt>
		  <dd>Yes</dd>
	      </dl>
	  </s:if>	  
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
	      <dt>Location </dt>
	      <dd><s:property value="%{device.location}" />
	      </dd>
	  </dl>
      </div>
      <div class="tt-split-container">
	  <dl class="fn1-output-field">
	      <dt>Division </dt>
	      <dd><s:property value="%{device.division}" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Installed Date </dt>
	      <dd><s:property value="%{device.installed}" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Mac Address </dt>
	      <dd><s:property value="%{device.mac_address}" /> 
	      </dd>
	  </dl>				
	  <dl class="fn1-output-field">
	      <dt>IP Address </dt>
	      <dd><s:property value="%{device.ip_address}" /> 
	      </dd>
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
	      <dt>Expected Age </dt>
	      <dd><s:property value="%{device.age_length}"/> (Years) 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Cost ($) </dt>
	      <dd><s:property value="%{device.cost}"/> 
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">				
	      <dt>Notes </dt>
	      <dd><s:property value="%{device.notes}" /></dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Inventory Date </dt>
	      <dd><s:property value="%{device.inventory_date}" /> 
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Status </dt>
	      <dd><s:property value="%{device.status}" /> 
	      </dd>
	  </dl>
      </div>
  </div>		
  <s:if test="device.anyUpdate()">				
      <a href="<s:property value='#application.url' />device.action?id=<s:property value='device.id' />&action=Edit" class="fn1-btn">Edit </a>
  </s:if>
  <s:if test="device.status !='Active' && device.canBeDisposed()">
      <a href="<s:property value='#application.url'/>dispose.action?asset_id=<s:property value='device.id' />&type=Device&asset_num=<s:property value='device.asset_num'/>" class="fn1-btn"> Dispose This Device</a>      
  </s:if>  
  <s:if test="device.status == 'Active'">	
      <a href="<s:property value='#application.url'/>installation.action?device_id=<s:property value='device.id' />" class="fn1-btn">New Software Installation</a>
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
  
<%@  include file="../gui/footer.jsp" %>

