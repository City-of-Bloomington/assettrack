<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table border="1" width="80%">
  <caption><s:property value="attachmentsTitle" /></caption>
  <tr>
	<td align="center"><b>ID</b></td>		
	<td align="center"><b>File</b></td>
	<td align="center"><b>Date</b></td>
	<td align="center"><b>User</b></td>
	<td align="center"><b>Notes</b></td>	
  </tr>
  <s:iterator value="#uploads">
	<tr>
	  <td><s:property value="id" /></td>		
		<td><a href="<s:property value='#application.url' />doDownload?id=<s:property value='id' />"><s:property value="old_file_name" /></a></td>		
	  <td><s:property value="date" /></td>
	  <td><s:property value="user" /></td>
	  <td><s:property value="notes" /></td>		
	</tr>
  </s:iterator>
</table>






































