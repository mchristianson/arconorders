
<%@ page import="com.arconorders.Site" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'site.label', default: 'Site')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-site" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="name" title="${message(code: 'site.name.label', default: 'Name')}" />
			
				<g:sortableColumn property="baseUrl" title="${message(code: 'site.baseUrl.label', default: 'Base Url')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${siteInstanceList}" status="i" var="siteInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${siteInstance.id}">${fieldValue(bean: siteInstance, field: "name")}</g:link></td>
			
				<td>${fieldValue(bean: siteInstance, field: "baseUrl")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${siteInstanceTotal}" />
	</div>
</section>

</body>

</html>
