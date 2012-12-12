
<%@ page import="com.arconorders.Product" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<p>
    <g:link action="verifyProducts" class="btn btn-danger">Verify Products</g:link>
</p>
<section id="list-product" class="first">

    <table class="table table-bordered">
        <thead>
        <tr>

            <g:sortableColumn property="vendorCode" title="${message(code: 'product.vendorCode.label', default: 'Vendor Code')}" />

            <g:sortableColumn property="dubowProductId" title="${message(code: 'product.dubowProductId.label', default: 'Dubow Product Id')}" />

            <g:sortableColumn property="code" title="${message(code: 'product.code.label', default: 'Code')}" />

            <g:sortableColumn property="name" title="${message(code: 'product.name.label', default: 'Name')}" />

            <g:sortableColumn property="mill" title="${message(code: 'product.mill.label', default: 'Mill')}" />

            <g:sortableColumn property="color" title="${message(code: 'product.color.label', default: 'Color')}" />

        </tr>
        </thead>
        <tbody>
            <g:each in="${productInstanceList}" status="i" var="productInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${productInstance.id}">${fieldValue(bean: productInstance, field: "vendorCode")}</g:link></td>
			
				<td>${fieldValue(bean: productInstance, field: "dubowProductId")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "code")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "name")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "mill")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "color")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${productInstanceTotal}" />
	</div>
</section>

</body>

</html>
