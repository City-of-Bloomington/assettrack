<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<table class="fn1-table">	
	<caption><s:property value="#softwaresTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>			
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Vendor</b></th>			
			<th align="center"><b>Installed Date</b></th>
			<th align="center"><b>Installed Count</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#softwares">
			<tr>
				<td><a href="<s:property value='#application.url' />software.action?id=<s:property value='id' />"><s:property value="ids" /> </a></td>
				<td><s:property value="display_name" /></td>
				<td><s:property value="vendor" /></td>				
				<td><s:property value="installed" /></td>
				<td><s:property value="install_count" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
