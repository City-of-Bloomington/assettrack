<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="stats" width="100%">	
	<caption><s:property value="#itemsTitle" /></caption>
	<tr>
		<s:if test="lot.lotOpt.asset_id">
			<td><b>Asset ID</b></td>
		</s:if>
		<s:if test="lot.lotOpt.asset_num">		
			<td><b>Asset Num</b></td>
		</s:if>
		<s:if test="lot.lotOpt.serial_num">				
			<td><b>Serial #</b></td>
		</s:if>
		<s:if test="lot.lotOpt.name">						
			<td><b>Name</b></td>
		</s:if>
		<s:if test="lot.lotOpt.category">						
			<td><b>Category</b></td>
		</s:if>
		<s:if test="lot.lotOpt.division">			
			<td><b>Division</b></td>
		</s:if>
		<s:if test="lot.lotOpt.installed">						
			<td><b>Installed Date</b></td>
		</s:if>
		<s:if test="lot.lotOpt.organization">						
			<td><b>Organization</b></td>
		</s:if>
		<s:if test="lot.lotOpt.date">						
			<td><b>Date</b></td>
		</s:if>
		<s:if test="lot.lotOpt.value">						
			<td><b>Value</b></td>
		</s:if>			
	</tr>
	<s:iterator var="one" value="#items">
		<tr>
			<s:if test="lot.lotOpt.asset_id">			
				<td><a href="<s:property value='#application.url' />donation.action?id=<s:property value='id' />"><s:property value="asset_id" /> </a></td>
			</s:if>
			<s:if test="lot.lotOpt.asset_num">						
				<td><s:property value="asset_num" /></td>
			</s:if>
			<s:if test="lot.lotOpt.serial_num">						
				<td><s:property value="serial_num" /></td>
			</s:if>
			<s:if test="lot.lotOpt.name">						
				<td><s:property value="name" /></td>
			</s:if>
			<s:if test="lot.lotOpt.category">						
				<td><s:property value="categoryName" /></td>
			</s:if>
			<s:if test="lot.lotOpt.division">						
				<td><s:property value="divisionName" /></td>
			</s:if>
			<s:if test="lot.lotOpt.installed">						
				<td><s:property value="installed" /></td>
			</s:if>
			<s:if test="lot.lotOpt.organization">						
				<td><s:property value="organ" /></td>
			</s:if>
			<s:if test="lot.lotOpt.date">		
				<td><s:property value="date" /></td>
			</s:if>
			<s:if test="lot.lotOpt.value">						
				<td><s:property value="value" /></td>
			</s:if>
		</tr>
	</s:iterator>
</table>
