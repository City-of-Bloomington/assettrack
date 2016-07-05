<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="fn1-table">	
	<caption><s:property value="#installationsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>			
			<th align="center"><b>Software</b></th>
			<th align="center"><b>License Key</b></th>			
			<th align="center"><b>Device</b></th>
			<th align="center"><b>Date</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#installations">
			<tr>
				<td><a href="<s:property value='#application.url' />installation.action?id=<s:property value='id' />"><s:property value="ids" /> </a></td>
				<td><s:property value="software.display_name" /></td>
				<td><s:property value="license.key_value" /></td>
				<td><a href="<s:property value='#application.url' />device.action?id=<s:property value='device_id' />"><s:property value="device.name" /> </a></td>
				<td><s:property value="date" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
