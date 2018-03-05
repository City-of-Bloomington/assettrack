<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="discard" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />

	<s:hidden name="item.type" value="%{item.type}" />
	<s:hidden name="item.asset_id" value="%{item.asset_id}" />
	<s:if test="item.id == ''">
		<h1>Discard Asset </h1>
	</s:if>
	<s:else>
		<s:hidden name="item.id" value="%{item.id}" />		
		<h1>Edit Discarded Asset <s:property value="%{item.id}" /></h1>
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
		<dl class="fn1-output-field">
			<dt>Asset ID </dt>
			<dd>
				<s:if test="item.type == 'Device'">
					<a href="<s:property value='#application.url' />device.action?id=<s:property value='item.asset_id' />"><s:property value="item.asset_id" /> </a>
				</s:if>
				<s:elseif test="item.type == 'Monitor'">
					<a href="<s:property value='#application.url' />monitor.action?id=<s:property value='item.asset_id' />"><s:property value="item.asset_id" /> </a>
				</s:elseif>
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Asset Num </dt>
			<dd> <s:property value="%{item.asset_num}" />	</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Type </dt>
			<dd> <s:property value="%{item.type}" />	</dd>
		</dl>				
		<dl class="fn1-output-field">
			<dt>Discard Method</dt>
			<dd><s:select name="item.method" value="%{item.method}" list="#{'Lost':'Lost','Discard':'Discard','Consumed':'Consumed'}" headerKey="-1" headerValue="Pick a Method" required="true" /> 
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Date </dt>
			<dd><s:textfield name="item.date" value="%{item.date}" size="10" maxlength="10" cssClass="date" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Description </dt>
			<dd><s:textfield name="item.description" value="%{item.description}" size="50" maxlength="80" /> </dd>
		</dl>				
	</div>
	<s:if test="item.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/></dd>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url'/>changeDispose.action?id=<s:property value='item.id' />&method_from=Discard" class="fn1-btn"> Change Dispose Method</a>			
	</s:else>
</s:form>
<s:if test="item.id == '' && items != null">
	<s:set var="items" value="items" />
	<s:set var="itemsTitle" value="discardsTitle" />
	<%@  include file="discards.jsp" %>
</s:if>
<%@  include file="../gui/footer.jsp" %>

