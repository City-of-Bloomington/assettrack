<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#employeesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Username</b></th>
			<th align="center"><b>Full Name</b></th>
			<th align="center"><b>Office Phone</b></th>
			<th align="center"><b>Role</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#employees">
			<tr>
				<td><a href="<s:property value='#application.url' />employee.action?id=<s:property value='id' />"> <s:property value="id" /></a></td>
				<td><s:property value="username" /></td>				
				<td><s:property value="fullName" /></td>
				<td><s:property value="office_phone" /></td>
				<td><s:property value="role" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
