<%@ taglib uri="/struts-tags" prefix="s" %>
<% 
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
response.setHeader("Pragma", "public");
response.setHeader("Content-Disposition","inline; filename=assets_data.csv");
response.setContentType("application/csv");
%>
"ID","External ID","Name","Description","Asset #","Serial #","Tag Code","Model #","Employee","Category","Domain","Installed","Processor","Mac Address","Division","Department","Location","Cost","Inventory Date","Status"
<s:iterator var="one" value="devices">
"<s:property value="id" />","<s:property value="external_id" />","<s:property value="name" />","<s:property value="description" />","<s:property value="asset_num" />","<s:property value="serial_num" />","<s:property value="tag_code" />","<s:property value="model" />","<s:property value="employee" />","<s:property value="category" />","<s:property value="domain" />","<s:property value="installed" />","<s:property value="processor" />","<s:property value="mac_address" />","<s:property value="division" />","<s:property value="dept" />","<s:property value="location" />","<s:property value="cost" />","<s:property value="inventory_date" />"<s:property value="status" />"
</s:iterator>

