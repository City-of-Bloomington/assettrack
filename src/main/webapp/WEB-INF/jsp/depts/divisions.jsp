<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#divisionsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Division Name</b></th>			
			<th align="center"><b>Department</b></th>			
			<th align="center"><b>Code</b></th>
			<th align="center"><b>Inactive</b></th>						
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#divisions">
			<tr>
				<td><a href="<s:property value='#application.url' />division.action?id=<s:property value='id' />">Edit</a></td>
				<td><s:property value="name" /></td>				
				<td><s:property value="dept" /></td>
				<td><s:property value="code" /></td>
				<td><s:property value="inactive" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
