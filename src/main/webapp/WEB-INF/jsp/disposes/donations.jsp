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
		<td><b>Asset ID</b></td>
		<td><b>Asset Num</b></td>
		<td><b>Serial #</b></td>
		<td><b>Name</b></td>
		<td><b>Category</b></td>
		<td><b>Division</b></td>
		<td><b>Installed Date</b></td>
		<td><b>Organization</b></td>
		<td><b>Date</b></td>
	</tr>
	<s:iterator var="one" value="#items">
		<tr>
			<td><a href="<s:property value='#application.url' />donation.action?id=<s:property value='id' />"><s:property value="asset_id" /> </a></td>
			<td><s:property value="asset_num" />&nbsp;</td>
			<td><s:property value="serial_num" />&nbsp;</td>
			<td><s:property value="name" />&nbsp;</td>
			<td><s:property value="categoryName" />&nbsp;</td>
			<td><s:property value="divisionName" />&nbsp;</td>
			<td><s:property value="installed" />&nbsp;</td>
			<td><s:property value="organ" />&nbsp;</td>
			<td><s:property value="date" />&nbsp;</td>
		</tr>
	</s:iterator>
</table>
