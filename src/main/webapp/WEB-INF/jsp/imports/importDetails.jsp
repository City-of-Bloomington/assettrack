<%@ include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h1>View Import <s:property value="impman.id" /></h1>
<s:if test="hasActionErrors()">
	<div class="errors">
    <s:actionerror/>
	</div>
</s:if>
<s:elseif test="hasActionMessages()">
	<div class="welcome">
    <s:actionmessage/>
	</div>
</s:elseif>
<table class="fn1-table">	
	<caption><s:property value="detailsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Data Type</b></th>
			<th align="center"><b>Status</b></th>
			<th align="center"><b>Date/Time</b></th>			
			<th align="center"><b>Error Message</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="details">
			<tr>
				<td><s:property value="id" /> </td>
				<td><s:property value="type" /></td>
				<td><s:property value="status" /></td>
				<td><s:property value="date_time" /></td>				
				<td><s:property value="errorMessage" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<a href="<s:property value='#application.url' />import.action?" class="fn1-btn"> Back to Import Page </a><br />
<%@  include file="../gui/footer.jsp" %>



