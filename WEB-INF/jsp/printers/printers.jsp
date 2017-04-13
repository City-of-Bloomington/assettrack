<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#printersTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Asset Num</b></th>
			<th align="center"><b>Serial Num</b></th>			
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Related Device</b></th>
			<th align="center"><b>Print Processor</b></th>
			<th align="center"><b>Installed Date</b></th>
			<th align="center"><b>Inventory Date</b></th>			
			<th align="center"><b>Status</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#printers">
			<tr>
				<td><a href="<s:property value='#application.url' />printer.action?id=<s:property value='id' />"> <s:property value="ids" /> </a></td>
				<td><s:property value="asset_num" /></td>
				<td><s:property value="serial_num" /></td>				
				<td><s:property value="name" /></td>				
				<td><s:property value="device_id" /></td>
				<td><s:property value="print_processor" /></td>
				<td><s:property value="date" /></td>
				<td><s:property value="inventory_date" /></td>				
				<td><s:property value="status" /></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
