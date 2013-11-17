
<%@ page import="com.arconorders.Product" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require modules="datatables"/>
    <g:javascript>
            $(function() {
                $('.datatablea').dataTable({
                    'sPaginationType': 'bootstrap',
                    aoColumns: [
                        {sName: "vendorCode"},
                        {sName: "dubowProductId"},
                        {sName: "code"},
                        {sName: "name"},
                        {sName: "mill"},
                        {sName: "color"},
                        {sName: "colorWay"},
                        {sName: "designNumber"}
                      ]
                }).makeEditable({
                    sUpdateURL: '<g:createLink action="updateRow"/>',
                    sAddURL:  '<g:createLink action="save"/>',
                    oAddNewRowFormOptions: {
                        title: 'Add a new product',
                        show: "blind",
                        hide: "explode",
                        width: "600px"
                    }
               });

            });
    </g:javascript>
</head>

<body>
	
<p>
    <g:link action="verifyProducts" class="btn btn-danger">Verify Products</g:link>
</p>
<section id="list-product" class="first">
    <form class="plain" action="" method="post" enctype="multipart/form-data">

    <table class="table table-bordered datatablea">
        <thead>
        <tr>

            <th>Vendor Code</th>

            <th>Dubow Product Id</th>
            <th>Code</th>
            <th>Name</th>
            <th>Mill</th>
            <th>Color</th>
            <th>Colorway</th>
            <th>Design Number</th>
        </tr>
        </thead>
        <tbody>
            <g:each in="${productInstanceList}" status="i" var="productInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'} ${productInstance.dubowProductName ? '' : 'error'}" id="${productInstance.id}">
			
				<td><g:link action="show" id="${productInstance.id}">${fieldValue(bean: productInstance, field: "vendorCode")}</g:link></td>
			
				<td>${fieldValue(bean: productInstance, field: "dubowProductId")}</td>

				<td>${fieldValue(bean: productInstance, field: "code")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "name")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "mill")}</td>
			
				<td>${fieldValue(bean: productInstance, field: "color")}</td>

				<td>${fieldValue(bean: productInstance, field: "colorWay")}</td>

				<td>${fieldValue(bean: productInstance, field: "designNumber")}</td>

			</tr>
		</g:each>
		</tbody>
	</table>
        </form>
</section>

</body>

</html>
