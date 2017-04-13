<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="fn1-table">	
	<caption><s:property value="#monitorsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Related Device ID</b></th>			
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Asset #</b></th>
			<th align="center"><b>Serial #</b></th>
			<th align="center"><b>Model #</b></th>
			<th align="center"><b>Type</b></th>
			<th align="center"><b>Screen Size</b></th>			
			<th align="center"><b>Received</b></th>
			<th align="center"><b>Inventory Date</b></th>			
			<th align="center"><b>Status</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#monitors">
			<tr>
				<td><a href="<s:property value='#application.url' />monitor.action?id=<s:property value='id' />"><s:property value="ids" /> </a></td>
				<td><s:property value="device_id" /></td>				
				<td><s:property value="name" /></td>
				<td><s:property value="asset_num" /></td>
				<td><s:property value="serial_num" /></td>
				<td><s:property value="model" /></td>
				<td><s:property value="type" /></td>
				<td><s:property value="screen_size" /></td>
				<td><s:property value="received" /></td>
				<td><s:property value="inventory_date" /></td>				
				<td><s:property value="status" /></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
