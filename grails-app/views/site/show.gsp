
<%@ page import="com.arconorders.Site" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'site.label', default: 'Site')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-site" class="first">

	<table class="table table-bordered table-striped">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="site.name.label" default="Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: siteInstance, field: "name")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="site.baseUrl.label" default="Base Url" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: siteInstance, field: "baseUrl")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="site.products.label" default="Products" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${siteInstance.products}" var="p">
						<li><g:link controller="product" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="site.users.label" default="Users" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${siteInstance.users}" var="u">
						<li><g:link controller="user" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
