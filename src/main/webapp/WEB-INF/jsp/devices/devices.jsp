<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="fn1-table">	
	<caption><s:property value="#devicesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>			
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Description</b></th>			
			<th align="center"><b>Asset #</b></th>
			<th align="center"><b>Serial #</b></th>
			<th align="center"><b>Tag Code</b></th>			
			<th align="center"><b>Model #</b></th>
			<th align="center"><b>Employee</b></th>			
			<th align="center"><b>Category</b></th>
			<th align="center"><b>Domain</b></th>			
			<th align="center"><b>Installed</b></th>
			<th align="center"><b>Division</b></th>						
			<th align="center"><b>Location</b></th>
			<th align="center"><b>Cost</b></th>			
			<th align="center"><b>Status</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#devices">
			<tr>
				<td><a href="<s:property value='#application.url' />device.action?id=<s:property value='id' />"><s:property value="ids" /></a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="description" /></td>				
				<td><s:property value="asset_num" /></td>
				<td><s:property value="serial_num" /></td>
				<td><s:property value="tag_code" /></td>
				
				<td><s:property value="model" /></td>
				<td><s:property value="employee" /></td>
				<td><s:property value="category" /></td>
				<td><s:property value="domain" /></td>
				<td><s:property value="installed" /></td>
				<td><s:property value="division" /></td>
				<td><s:property value="location" /></td>
				<td><s:property value="cost" /></td>						
				<td><s:property value="status" /></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
