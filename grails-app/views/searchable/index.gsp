<%@ page defaultCodec="html" %>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="grails.plugin.searchable.internal.lucene.LuceneUtils" %>
<%@ page import="grails.plugin.searchable.internal.util.StringQueryUtils" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<title>Arcon Orders Search</title>
</head>

<body>
<section id="search" class="first">
    <g:form url='[controller: "searchable", action: "index"]' id="searchableForm" name="searchableForm" method="get">
        <g:textField name="q" value="${params.q}" size="50"/> <input type="submit" value="Search" />
    </g:form>
    <g:if test="${haveQuery && haveResults}">
      Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
      results for <strong>${params.q}</strong>
    </g:if>
    <g:else>
    &nbsp;
    </g:else>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Results</th>
        </tr>
        </thead>
        <tbody>
        <g:each var="result" in="${searchResult.results}" status="index">
            <g:set var="className" value="${ClassUtils.getShortName(result.getClass())}" />
            <g:set var="link" value="${createLink(controller: className[0].toLowerCase() + className[1..-1], action: 'show', id: result.id)}" />
            <g:set var="desc" value="${result.toString()}" />
            <g:if test="${desc.size() > 120}"><g:set var="desc" value="${desc[0..120] + '...'}" /></g:if>

			<tr class="${(index % 2) == 0 ? 'odd' : 'even'}">

				<td><a href="${link}">${desc.encodeAsHTML()}</a><br/>
                    ${className} #${result.id}<br/>
                    ${link}
                </td>

			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
        <g:set var="totalPages" value="${Math.ceil(searchResult.total / searchResult.max)}" />
        <g:if test="${totalPages == 1}"><span class="currentStep">1</span></g:if>
        <g:else><bs:paginate controller="searchable" action="index" params="[q: params.q]" total="${searchResult.total}" prev="&lt; previous" next="next &gt;"/></g:else>
	</div>
</section>

</body>

</html>

