
<%@ page import="com.arconorders.OrderError" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'orderError.label', default: 'OrderError')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-orderError" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="orderError.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${orderErrorInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="orderError.error.label" default="Error" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: orderErrorInstance, field: "error")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="orderError.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${orderErrorInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="orderError.orderId.label" default="Order Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: orderErrorInstance, field: "orderId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
