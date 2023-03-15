<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="deviceSearch" id="form_id" method="post">
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
  <h3> Search Devices </h3>
  <p>To add a new deive, click on 'New Device' button below<br />
		Note: to search for certain employee start typing her/his name in the 'Employee Name' field for auto complete.</p>
  <div class="tt-row-container">
      <div class="tt-split-container">				
	  <dl class="fn1-output-field">
	      <dt>ID </dt>
	      <dd><s:textfield name="deviceList.id" value="%{deviceList.id}" size="10" maxlength="10" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>External ID </dt>
	      <dd><s:textfield name="deviceList.external_id" value="%{deviceList.external_id}" size="10" maxlength="10" />
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Employee Name</dt>
	      <dd><s:textfield name="deviceList.employee_name" value="%{deviceList.employee_name}" size="20" maxlength="20" id="employee_name" />
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Employee ID</dt>		
	      <dd><s:textfield name="deviceList.employee_id" value="%{deviceList.employee_id}" size="20" maxlength="20" id="employee_id" />
	      </dd>
	  </dl>			
	  <dl class="fn1-output-field">
	      <dt>Device Name </dt>
	      <dd><s:textfield name="deviceList.name" value="%{deviceList.name}" size="20" maxlength="70" id="name" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Tag Code </dt>
	      <dd><s:textfield name="deviceList.tag_code" value="%{deviceList.tag_code}" size="15" maxlength="30" id="name" /> </dd>
	  </dl>	  
	  <dl class="fn1-output-field">
	      <dt>Asset Num </dt>
	      <dd><s:textfield name="deviceList.asset_num" value="%{deviceList.asset_num}" size="15" maxlength="15" id="bar_code_id" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Serial Num </dt>
	      <dd><s:textfield name="deviceList.serial_num" value="%{deviceList.serial_num}" size="20" maxlength="30"  /> </dd>
	  </dl>
      </div>
      <div class="tt-split-container">
	  <dl class="fn1-output-field">
	      <dt>Model </dt>
	      <dd><s:textfield name="deviceList.model" value="%{deviceList.model}" size="20" maxlength="30" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Category </dt>
	      <dd><s:select name="deviceList.category_id" value="%{deviceList.category_id}" list="%{categories}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Domain </dt>
	      <dd><s:select name="deviceList.domain_id" value="%{deviceList.domain_id}" list="%{domains}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> 
	      </dd>
	  </dl>
	  <dl class="fn1-output-field">
	      <dt>Division </dt>
	      <dd><s:select name="deviceList.division_id" value="%{deviceList.division_id}" list="%{divisions}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /> 
	      </dd>
	  </dl>		
	  <dl class="fn1-output-field">
	      <dt>Mac Address </dt>
	      <dd><s:textfield name="deviceList.mac_address" value="%{deviceList.mac_address}" size="20" maxlength="20" /> </dd>
	  </dl>				
	  <dl class="fn1-output-field">
	      <dt>IP Address </dt>
	      <dd><s:textfield name="deviceList.ip_address" value="%{deviceList.ip_address}" size="20" maxlength="20" /> </dd>
	  </dl>
      </div>
  </div>
  <dl class="fn1-output-field">
      <dt>Location </dt>
      <dd><s:select name="deviceList.location_id" value="%{deviceList.location_id}" list="%{locations}" listKey="id" listValue="name" headerKey="-1" headerValue="All" /></dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>Status </dt>
      <dd><s:radio name="deviceList.status" value="%{deviceList.status}" list="#{'-1':'All','Active':'Active','Donated':'Donated','Recycled':'Recycled','Auctioned':'Auctioned','Disposed':'Discarded','Replaced':'Replaced','Duplicate':'Duplicate'}" /></dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>Exclude </dt>	
      <dd><s:checkbox name="deviceList.excludeVoided" value="%{deviceList.excludeVoided}" /> Voided devices </dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>Inventory Status</dt>
      <dd><s:radio name="deviceList.inventory_status" value="%{deviceList.inventory_status}" list="#{'-1':'All','set':'Date Set','unset':'Date Unset'}" /> </dd>
  </dl>	
  <dl class="fn1-output-field">
      <dt>Date Option </dt>
      <dd><s:radio name="deviceList.whichDate" value="deviceList.whichDate" list="#{'d.installed':'Installed Date','d.inventory_date':'Inventory Date'}" /></dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>Date Range</dt>
      <dd>from: <s:textfield name="deviceList.date_from" value="%{deviceList.date_from}" size="10" maxlength="10" cssClass="date" /> to:
	  <s:textfield name="deviceList.date_to" value="%{deviceList.date_to}" size="10" maxlength="10" cssClass="date" />					
      </dd>
  </dl>
  <dl class="fn1-output-field">
      <dt>Cost ($) </dt>
      <dd>from: <s:textfield name="deviceList.cost_from" value="%{deviceList.cost_from}" size="10" maxlength="10" /> to:
	  <s:textfield name="deviceList.cost_to" value="%{deviceList.cost_to}" size="10" maxlength="10" />					
      </dd>
  </dl>	
  <s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
  <s:submit name="action" type="button" value="Save to CSV" class="fn1-btn"/>	
  <a href="<s:property value='#application.url' />device.action" class="fn1-btn">New Device </a>
</s:form>
<s:if test="devices != null">
    <s:set var="devices" value="devices" />	
    <s:set var="devicesTitle" value="devicesTitle" />
    <%@  include file="devices.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

