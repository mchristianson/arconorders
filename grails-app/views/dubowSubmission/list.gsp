
<%@ page import="com.arconorders.DubowSubmission" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'dubowSubmission.label', default: 'DubowSubmission')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-dubowSubmission" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
                <g:sortableColumn property="lastUpdated" title="${message(code: 'dubowSubmission.lastUpdated.label', default: 'Date')}" />

				<g:sortableColumn property="successful" title="${message(code: 'dubowSubmission.successful.label', default: 'Successful')}" />

                <th>Orders</th>

			</tr>
		</thead>
		<tbody>
		<g:each in="${dubowSubmissionInstanceList}" status="i" var="dubowSubmissionInstance">
			<tr class="${dubowSubmissionInstance.successful ? (i % 2) == 0 ? 'odd' : 'even' : 'error'}">
			
                <td><g:link action="show" id="${dubowSubmissionInstance.id}"><g:formatDate date="${dubowSubmissionInstance.lastUpdated}" /></g:link></td>

				<td><g:formatBoolean boolean="${dubowSubmissionInstance.successful}" /></td>

                <td>
                    <g:each in="${dubowSubmissionInstance.arconOrders}" var="order">
                        [<g:link controller="arconOrder" action="show" id="${order.id}">${order.orderID}</g:link>]
                    </g:each>
                </td>
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${dubowSubmissionInstanceTotal}" />
	</div>
</section>

</body>

</html>
