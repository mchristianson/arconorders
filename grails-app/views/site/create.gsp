<%@ page import="com.arconorders.Site" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'site.label', default: 'Site')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>

<section id="create-site" class="first">

	<g:hasErrors bean="${siteInstance}">
	<div class="alert alert-error">
		<g:renderErrors bean="${siteInstance}" as="list" />
	</div>
	</g:hasErrors>

	<g:form action="save" class="form-horizontal" >
		<fieldset class="form">
			<g:render template="form"/>
		</fieldset>
		<div class="form-actions">
			<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            <button class="btn" type="reset">Cancel</button>
		</div>
	</g:form>

</section>

</body>

</html>
