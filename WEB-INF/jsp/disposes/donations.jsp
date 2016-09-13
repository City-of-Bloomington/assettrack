<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="fn1-table">	
	<caption><s:property value="#itemsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Asset ID</b></th>
			<th align="center"><b>Asset Num</b></th>			
			<th align="center"><b>Type </b></th>
			<th align="center"><b>Organization</b></th>			
			<th align="center"><b>Date</b></th>
			<th align="center"><b>Value</b></th>			
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#items">
			<tr>
				<td><a href="<s:property value='#application.url' />donation.action?id=<s:property value='id' />"><s:property value="id" /> </a></td>
				<td><s:property value="asset_id" /></td>
				<td><s:property value="asset_num" /></td>				
				<td><s:property value="type" /></td>
				<td><s:property value="organ" /></td>				
				<td><s:property value="date" /></td>
				<td><s:property value="value" /></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
