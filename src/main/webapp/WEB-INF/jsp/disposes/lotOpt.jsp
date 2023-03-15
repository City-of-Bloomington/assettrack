<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="../gui/headerMin.jsp" %>
<s:form action="lotOpt" id="form_id" method="post">
	<s:hidden name="lotOpt.id" value="%{lotOpt.id}" />		
	<h4>Update Lot Print Options (<s:property value="lotOpt.type" />)</h4>
	Check or Uncheck the fields that will be used in the printout<br />
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
	<table width="100%" class="stats">
		<tr>
			<td>
				<s:checkbox name="lotOpt.name" value="%{lotOpt.name}" />Lot Name
			</td>
			<td>
				<s:checkbox name="lotOpt.asset_id" value="%{lotOpt.asset_id}" />Asset ID
			</td>
		</tr>
		<tr>						
			<td>
				<s:checkbox name="lotOpt.asset_num" value="%{lotOpt.asset_num}" />Asset Num
			</td>
			<td>
				<s:checkbox name="lotOpt.serial_num" value="%{lotOpt.serial_num}" />Serial Num
			</td>
		</tr>
		<tr>									
			<td>
				<s:checkbox name="lotOpt.category" value="%{lotOpt.category}" />Category
			</td>		
			<td>
				<s:checkbox name="lotOpt.division" value="%{lotOpt.division}" />Division
			</td>
		</tr>
		<tr>						
			<td>
				<s:checkbox name="lotOpt.installed" value="%{lotOpt.installed}" />Installed Date
			</td>
			<td>
				<s:checkbox name="lotOpt.date" value="%{lotOpt.date}" /><s:property value="%{lotOpt.type}" /> Date
			</td>
		</tr>
		<tr>
			<s:if test="lotOpt.type == 'Donation'">
				<td>
					<s:checkbox name="lotOpt.organization" value="%{lotOpt.organization}" />Organization
				</td>				
				<td>
					<s:checkbox name="lotOpt.value" value="%{lotOpt.value}" />Value
				</td>
			</s:if>
			<s:elseif test="lotOpt.type == 'Auction'">
				<td>
					<s:checkbox name="lotOpt.auction_name" value="%{lotOpt.auction_name}" />Auction Name
				</td>
				<td>
					<s:checkbox name="lotOpt.value" value="%{lotOpt.value}" />Value
				</td>
			</s:elseif>
			<s:else>
				<td>
					<s:checkbox name="lotOpt.weight" value="%{lotOpt.weight}" />Weight
				</td>
				<td>
					<s:checkbox name="lotOpt.recycle_location" value="%{lotOpt.recycle_location}" />Recycle location
				</td>				
			</s:else>
		</tr>
	</table>
	<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
</s:form>
<a href="javascript:window.close();">Close This Window</a>

<%@  include file="../gui/footer.jsp" %>

