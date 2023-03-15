<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="stats" with="100%">	
	<caption><s:property value="#itemsTitle" /></caption>
			<tr>
				<s:if test="lot.lotOpt.asset_id">
					<td><b>Asset ID</b></td>
				</s:if>
				<s:if test="lot.lotOpt.asset_num">				
					<td><b>Asset Num</b></td>
				</s:if>				
				<td><b>Serial #</b></td>
				<td><b>Name</b></td>
				<td><b>Category</b></td>
				<td><b>Division</b></td>			
				<td><b>Installed Date</b></td>				
				<td><b>Auction</b></td>			
				<td><b>Date</b></td>
				<td><b>Value</b></td>
				<td><b>Description</b></td>
			</tr>	
		<s:iterator var="one" value="#items">

			<tr>
				<td><a href="<s:property value='#application.url' />auctionItem.action?id=<s:property value='id' />"><s:property value="asset_id" /> </a></td>
				<td><s:property value="asset_num" /></td>
				<td><s:property value="serial_num" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="categoryName" /></td>
				<td><s:property value="divisionName" /></td>			
				<td><s:property value="installed" /></td>								
				<td><s:property value="auction" /></td>				
				<td><s:property value="date" /></td>
				<td><s:property value="value" /></td>
				<td><s:property value="description" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
