<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#organizationsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Organization</b></th>
			<th align="center"><b>Address</b></th>
			<th align="center"><b>Contact Name</b></th>
			<th align="center"><b>Phone</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#organizations">
			<tr>
				<td><a href="<s:property value='#application.url' />organization.action?id=<s:property value='id' />">Edit</a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="address" /></td>
				<td><s:property value="contact" /></td>
				<td><s:property value="phone" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
