
<%@ page import="com.arconorders.ArconOrder" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'arconOrder.label', default: 'Order')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
<p>
    <g:link action="processOrders" class="btn btn-danger">Process Orders</g:link>
</p>

<section id="list-arconOrder" class="first">
	<table class="table table-bordered">
		<thead>
			<tr>
			
                <g:sortableColumn property="orderID" title="${message(code: 'arconOrder.orderID.label', default: 'Order ID')}" />

                <g:sortableColumn property="orderDate" title="${message(code: 'arconOrder.orderDate.label', default: 'Order Date')}" />

				<th>Customer</th>

                <g:sortableColumn property="site" title="${message(code: 'arconOrder.site.label', default: 'Site')}" />
                <th>Submissions</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${arconOrderInstanceList}" status="i" var="arconOrderInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
                <td><g:link action="show" id="${arconOrderInstance.id}">${fieldValue(bean: arconOrderInstance, field: "orderID")}</g:link></td>
                <td>${fieldValue(bean: arconOrderInstance, field: "orderDate")}</td>

				<td>
                    ${fieldValue(bean: arconOrderInstance, field: "firstName")} ${fieldValue(bean: arconOrderInstance, field: "lastName")}<br/>
                    ${fieldValue(bean: arconOrderInstance, field: "customerID")}
                    ${fieldValue(bean: arconOrderInstance, field: "address1")}<br/>
                    ${fieldValue(bean: arconOrderInstance, field: "city")} ${fieldValue(bean: arconOrderInstance, field: "state")}, ${fieldValue(bean: arconOrderInstance, field: "postalCode")}
                </td>
                <td>${fieldValue(bean: arconOrderInstance, field: "site")}</td>
                <td><g:each in="${arconOrderInstance.dubowSubmissions}" var="sub">
                    [<g:link controller="dubowSubmission" action="show" id="${sub.id}">${sub.id}</g:link>]
                </g:each></td>

			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${arconOrderInstanceTotal}" />
	</div>
</section>

</body>

</html>
