<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="recycle" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />

	<s:hidden name="item.type" value="%{item.type}" />
	<s:hidden name="item.asset_id" value="%{item.asset_id}" />
	<s:hidden name="item.asset_num" value="%{item.asset_num}" />	
	<s:if test="item.id == ''">
		<h1>Recycle Asset </h1>
	</s:if>
	<s:else>
		<s:hidden name="item.id" value="%{item.id}" />		
		<h1>Edit Recycle Item <s:property value="%{item.id}" /></h1>
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
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt>Asset ID </dt>
				<dd>
					<a href="<s:property value='#application.url' /><s:property value='item.typeLower' />.action?action=Edit&id=<s:property value='item.asset_id' />" class="fn1-btn">Edit <s:property value="%{item.asset_id}" /> </a>
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Asset Num </dt>
				<dd>
					<s:property value="%{item.asset_num}" /> 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Serial #</dt>
				<dd><s:property value="item.serial_num" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Name</dt>
				<dd><s:property value="item.name" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Category</dt>
				<dd><s:property value="item.categoryName" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Division</dt>
				<dd><s:property value="item.divisionName" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Installed Date</dt>
				<dd><s:property value="item.installed" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Type </dt>
				<dd><s:property value="%{item.type}" />	</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Location *</dt>
				<dd><s:select name="item.location_id" value="%{item.location_id}" list="%{locations}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Recycle Location" required="true" /> 
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Lot *</dt>
				<dd><s:select name="item.lot_id" value="%{item.lot_id}" list="%{lots}" listKey="id" listValue="name" headerKey="-1" headerValue="Pick a Lot" required="true" /> 
				</dd>
			</dl>				
			<dl class="fn1-output-field">
				<dt>Weight </dt>
				<dd><s:textfield name="item.weight" value="%{item.weight}" size="10" maxlength="10" /> (lb) </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Dispose Date </dt>
				<dd><s:textfield name="item.date" value="%{item.date}" size="10" maxlength="10" cssClass="date" /> </dd>
			</dl>		
			<dl class="fn1-output-field">
				<dt>Description </dt>
				<dd><s:textfield name="item.description" value="%{item.description}" size="25" maxlength="80" /> </dd>
			</dl>				
		</div>
	</div>
	<s:if test="item.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url'/>changeDispose.action?id=<s:property value='item.id' />&method_from=Recycle" class="fn1-btn"> Change Dispose Method</a>			
	</s:else>
</s:form>
<s:if test="item.id == '' && items != null">
	<s:set var="items" value="items" />
	<s:set var="itemsTitle" value="recyclesTitle" />
	<%@  include file="recycles.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

