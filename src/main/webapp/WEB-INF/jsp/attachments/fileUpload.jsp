<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="doUpload" method="post" enctype="multipart/form-data">
	<s:hidden name="obj_id" value="%{obj_id}" />
	<s:hidden name="obj_type" value="%{obj_type}" />	
  <h3>Attach New File</h3>
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
  <p>* indicate a required field</p>

	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt><s:property value="obj_type" /> ID:</dt>
			<dd>
					<a href="<s:property value='#application.url' /><s:property value='objectLink' />"><s:property value="obj_id" /></a>
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>File:</dt>
			<dd><input type="file" name="upload" value="Pick File" />*</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Notes:</dt>
			<dd><s:textarea name="notes" value="%{notes}" row="5" cols="50" /></dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt></dt>
			<dd>
				<s:submit name="action" type="button" value="Submit" /></dd>
		</dl>			
	</div>
</s:form>

<s:if test="uploads != null">
  <s:set var="uploads" value="uploads" />
	<s:set var="attachmentsTitle" value="'Most Recent Attachments'" />
  <%@  include file="fileUploads.jsp" %>	
</s:if>
<%@  include file="../gui/footer.jsp" %>	






































