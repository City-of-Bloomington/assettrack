<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="80%">
	-->
<table class="fn1-table">	
	<caption><s:property value="#importsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Date</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#imports">
			<tr>
				<td><a href="<s:property value='#application.url' />import.action?id=<s:property value='id' />"> <s:property value="id" /> More Details </a></td>
				<td><s:property value="date" /></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>




