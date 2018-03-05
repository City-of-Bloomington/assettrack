<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<table class="stats" width="100%" border="1">	
	<caption><s:property value="#itemsTitle" /></caption>
			<tr>
				<td><b>Asset ID</b></td>
				
				<td><b>Asset Num</b></td>
				<td><b>Serial #</b></td>
				<td><b>Name</b></td>
				<td><b>Category</b></td>
				<td><b>Installed</b></td>							
				<!--  <td><b>Recycle Location</b></td> -->
				<td><b>Description</b></td>
				<td><b>Location</b></td>
				<td><b>Date</b></td>
			</tr>			
		<s:iterator var="one" value="#items">
			<tr>
				<td><a href="<s:property value='#application.url' />recycle.action?id=<s:property value='id' />"><s:property value="asset_id" /> </a></td>
				<td><s:property value="asset_num" /></td>
				<td><s:property value="serial_num" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="categoryName" /></td>
				<!-- <td><font size="-2"><s:property value="divisionName" /></font></td>	-->		
				<td><s:property value="installed" /></td>
				
				<!--  <td><s:property value="location" /></td>	-->			
				<td><s:property value="description" /></td>
				<td><s:property value="location" /></td>				
				<td><s:property value="date" /></td>
			</tr>
		</s:iterator>
</table>
