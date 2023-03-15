<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<table class="fn1-table">	
	<caption><s:property value="#lotsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>			
			<th align="center"><b>Type </b></th>
			<th align="center"><b>Date</b></th>
			<th align="center"><b>Status</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#lots">
			<tr>
				<td><a href="<s:property value='#application.url' />lot.action?id=<s:property value='id' />"><s:property value="id" /> </a></td>
				<td><s:property value="name" /></td>				
				<td><s:property value="type" /></td>
				<td><s:property value="date" /></td>
				<td><s:property value="status" /></td>
				<td><a href="<s:property value='#application.url' />lot.action?action=print&id=<s:property value='id' />">Printable </a></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
