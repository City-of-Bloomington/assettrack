<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
<table class="stat" border="1" width="95%">
	-->

<table class="stats" width="80%">	
	<caption><s:property value="#donationsReportTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Organization</b></th>						
			<th align="center"><b>Type</b></th>
			<th align="center"><b>Date</b></th>
			<th align="center"><b>Count</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#donItems">
				<tr>
					<s:iterator var="two" value="one">					
						<td><s:property /></td>
					</s:iterator>
				</tr>
		</s:iterator>
	</tbody>
</table>
