
<%@ page import="com.arconorders.OrderError" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'orderError.label', default: 'OrderError')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-orderError" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'orderError.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="error" title="${message(code: 'orderError.error.label', default: 'Error')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'orderError.lastUpdated.label', default: 'Last Updated')}" />
			
				<g:sortableColumn property="orderId" title="${message(code: 'orderError.orderId.label', default: 'Order Id')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${orderErrorInstanceList}" status="i" var="orderErrorInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${orderErrorInstance.id}">${fieldValue(bean: orderErrorInstance, field: "dateCreated")}</g:link></td>
			
				<td>${fieldValue(bean: orderErrorInstance, field: "error")}</td>
			
				<td><g:formatDate date="${orderErrorInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: orderErrorInstance, field: "orderId")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${orderErrorInstanceTotal}" />
	</div>
</section>

</body>

</html>
