
<%@ page import="com.arconorders.ArconOrder" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'arconOrder.label', default: 'Order')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require modules="datatables"/>
    <g:javascript>
            $(function() {
                $('.datatablea').dataTable({
                    aaSorting: [[ 1, "desc" ]],
                    sPaginationType: 'bootstrap',
                    aoColumns: [
                     { sType: "numeric" },
                     { sType: "date" },
                     null,
                     null,
                     null,
                     null
                   ]
                });
           });
    </g:javascript>
</head>

<body>
<p>
    <g:link action="processOrders" class="btn btn-danger">Process Orders</g:link>
</p>

<section id="list-arconOrder" class="first">
	<table class="table table-bordered datatablea">
		<thead>
			<tr>
                <th>Order ID</th>
                <th>Order Date</th>
                <th>Customer</th>
                <th>Site</th>
                <th>Submissions</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${arconOrderInstanceList}" status="i" var="arconOrderInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
                <td>${fieldValue(bean: arconOrderInstance, field: "orderID")}</td>
                <td>${arconOrderInstance.orderDate.replace('AM','').replace('PM','')}</td>

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
                <td><g:link action="show" id="${arconOrderInstance.id}" class="btn">view</g:link></td>

			</tr>
		</g:each>
		</tbody>
	</table>
</section>

</body>

</html>
