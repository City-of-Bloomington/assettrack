<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="../gui/headerMin.jsp" %>
	<h3>IT Department</h3>	
	<p><b> <s:property value="%{lot.name}" /> Info</b></p>
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
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt></dt>
			<dd>
			Lot ID: <s:property value='id' />,	 Date: <s:property value="%{lot.date}" />, Type:<s:property value="%{lot.type}" />
			</dd>
	</dl>
</div>
<s:elseif test="lot.type == 'Auction' && lot.items != null">
	<s:set var="items" value="lot.items" />
	<s:set var="itemsTitle" value="auctionItemsTitle" />
	<%@  include file="auctionItems.jsp" %>
</s:elseif>
<s:elseif test="lot.type == 'Donation' && lot.items3 != null">
	<s:set var="items" value="lot.items3" />
	<s:set var="itemsTitle" value="donationsTitle" />
	<%@  include file="donations.jsp" %>
</s:elseif>
<s:elseif test="lot.type == 'Recycle' && lot.items4 != null">
	<s:set var="items" value="lot.items4" />
	<s:set var="itemsTitle" value="recyclesTitle" />
	<%@  include file="recycles.jsp" %>
</s:elseif>

<%@  include file="../gui/footer.jsp" %>

