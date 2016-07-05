<%@  include file="../gui/header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="barcodeSearch" id="form_id" method="post">
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
  <h3> Barcode search </h3>
	<p>Scan the barcode on the device</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Asset # </dt>
			<dd><s:textfield name="barcode.asset_num" value="%{barcode.asset_num}" size="15" maxlength="15" id="bar_code_id" /> (barcode scan)
			</dd>
		</dl>
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
</s:form>

<%@  include file="../gui/footer.jsp" %>

