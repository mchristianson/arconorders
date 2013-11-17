
<%@ page import="com.arconorders.ArconOrder" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'arconOrder.label', default: 'Order')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>
<p>
    <g:link action="resendOrder" id="${arconOrderInstance.id}" class="btn btn-danger">Resend Order</g:link>
</p>
<g:each in="${unprocessedErrors}" var="error">
    <div class="alert alert-error"><p>${error.error} ${error.dateCreated} [<g:link action="clearError" id="${error.id}">clear</g:link>]</p></div>
</g:each>

<section id="show-arconOrder" class="first">

	<table class="table table-bordered table-striped">
		<tbody>
		
        <tr class="prop">
            <td valign="top" class="name"><g:message code="arconOrder.success.label" default="Success" /></td>
            
            <td valign="top" class="value"><g:formatBoolean boolean="${arconOrderInstance?.success}" /></td>

        </tr>
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.firstName.label" default="First Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "firstName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.lastName.label" default="Last Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "lastName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.orderID.label" default="Order ID" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "orderID")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.orderDate.label" default="Order Date" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "orderDate")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.customerID.label" default="Customer ID" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "customerID")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.address1.label" default="Address1" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "address1")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.address2.label" default="Address2" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "address2")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.city.label" default="City" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "city")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.state.label" default="State" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "state")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.postalCode.label" default="Postal Code" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "postalCode")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.country.label" default="Country" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "country")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.comments.label" default="Comments" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: arconOrderInstance, field: "comments")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.site.label" default="Site" /></td>
				
				<td valign="top" class="value"><g:link controller="site" action="show" id="${arconOrderInstance?.site?.id}">${arconOrderInstance?.site?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${arconOrderInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="arconOrder.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${arconOrderInstance?.lastUpdated}" /></td>
				
			</tr>


		</tbody>
	</table>
    <h2>Order Details</h2>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Product Code</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Options</th>
        </tr>
        </thead>
        <tbody>
            <g:each in="${arconOrderInstance.orderDetails}" var="o">
                <tr>
                    <td><g:link controller="product" action="show" id="${o.product?.id}">${o.productCode}</g:link> </td>
                    <td>${o.productName}</td>
                    <td>${o.quantity}</td>
                    <td>${o.productOptions}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
</section>

</body>

</html>
