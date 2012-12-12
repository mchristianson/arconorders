
<%@ page import="com.arconorders.Product" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>
<p>
    <g:link action="verifyProduct" id="${productInstance.id}" class="btn btn-danger to_modal" >Verify Product</g:link>
</p>
<section id="show-product" class="first">

	<table class="table table-bordered table-striped">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.vendorCode.label" default="Vendor Code" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "vendorCode")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.dubowProductId.label" default="Dubow Product Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "dubowProductId")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.code.label" default="Code" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "code")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "name")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.mill.label" default="Mill" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "mill")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.color.label" default="Color" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "color")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.designNumber.label" default="Design Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "designNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.colorWay.label" default="Color Way" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "colorWay")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.location.label" default="Location" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "location")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.personalization.label" default="Personalization" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "personalization")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.personalizationColor.label" default="Personalization Color" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "personalizationColor")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.procurementType.label" default="Procurement Type" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "procurementType")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.appliqueDesignNumber.label" default="Applique Design Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "appliqueDesignNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.appliqueColorWay.label" default="Applique Color Way" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "appliqueColorWay")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.appliqueLocation.label" default="Applique Location" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: productInstance, field: "appliqueLocation")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="product.site.label" default="Site" /></td>
				
				<td valign="top" class="value"><g:link controller="site" action="show" id="${productInstance?.site?.id}">${productInstance?.site?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
		</tbody>
	</table>
</section>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Verify Product</h3>
  </div>
  <div class="modal-body">
      <p>${productInstance}</p>
    <p id="modal_body"></p>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
  </div>
</div>

<r:script>
    $('.to_modal').click(function(e) {
        e.preventDefault();
        var href = $(e.target).attr('href');
        if (href.indexOf('#') == 0) {
            $(href).modal('open');
        } else {
            $.get(href, function(data) {
                $('#modal_body').html(data);
                $('#myModal').modal();
            });
        }
    });
</r:script>
</body>

</html>
