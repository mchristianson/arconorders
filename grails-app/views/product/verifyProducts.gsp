
<!doctype html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'arconOrder.label', default: 'ArconOrder')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

	<body>
    <section id="show-arconOrder" class="first">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>

                    <th>Product</th>
                    <th>Found</th>

                </tr>
            </thead>
            <tbody>
            <g:each in="${products}" status="i" var="r">
                <tr class="${r.found.toBoolean() ? (i % 2) == 0 ? 'odd' : 'even' : 'error'}">
                    <td><g:link controller="searchable" action="index" params="[q:r.product.split(':')[0]]">${r.product}</g:link></td>
                    <td>${r.found}</td>
                </tr>
            </g:each>
            </tbody>
			</table>
		</section>
	</body>
</html>
