<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="fn1-table">	
	<caption><s:property value="#licensesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>			
			<th align="center"><b>License Key</b></th>
			<th align="center"><b>Type</b></th>			
			<th align="center"><b>Date</b></th>
			<th align="center"><b>Seat limit</b></th>			
			<th align="center"><b>Editable</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#licenses">
			<tr>
				<td><a href="<s:property value='#application.url' />license.action?id=<s:property value='id' />"><s:property value="id" /> </a></td>
				<td><s:property value="key_value" /></td>
				<td><s:property value="type" /></td>				
				<td><s:property value="created" /></td>
				<td><s:property value="seat_limit" /></td>				
				<td><s:property value="editable" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
