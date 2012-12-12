
<%@ page import="com.arconorders.DubowSubmission" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'dubowSubmission.label', default: 'DubowSubmission')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body onload="prettyPrint()">

<section id="show-dubowSubmission" class="first">

	<table class="table table-bordered table-striped">
		<tbody>
		
        <tr class="prop">
     				<td valign="top" class="name"><g:message code="dubowSubmission.dateCreated.label" default="Date Created" /></td>

     				<td valign="top" class="value"><g:formatDate date="${dubowSubmissionInstance?.dateCreated}" /></td>

     			</tr>
        <tr class="prop">
     				<td valign="top" class="name"><g:message code="dubowSubmission.arconOrders.label" default="Arcon Orders" /></td>

     				<td valign="top" style="text-align: left;" class="value">
     					<ul>
     					<g:each in="${dubowSubmissionInstance.arconOrders}" var="a">
     						<li><g:link controller="arconOrder" action="show" id="${a.id}">${a?.id}</g:link></li>
     					</g:each>
     					</ul>
     				</td>

     			</tr>

        <tr class="prop">
     				<td valign="top" class="name"><g:message code="dubowSubmission.successful.label" default="Successful" /></td>

     				<td valign="top" class="value"><g:formatBoolean boolean="${dubowSubmissionInstance?.successful}" /></td>

     			</tr>

        <tr class="prop">
     				<td valign="top" class="name"><g:message code="dubowSubmission.requestXmlString.label" default="Request" /></td>

     				<td valign="top" class="value"><pre class="prettyprint lang-xml">${fieldValue(bean: dubowSubmissionInstance, field: "requestXmlString")}</pre></td>

     			</tr>

			<tr class="prop">
				<td valign="top" class="name"><g:message code="dubowSubmission.responseXmlString.label" default="Response" /></td>
				
				<td valign="top" class="value"><pre class="prettyprint lang-xml">${fieldValue(bean: dubowSubmissionInstance, field: "responseXmlString")}</pre></td>
				
			</tr>
		


			<tr class="prop">
				<td valign="top" class="name"><g:message code="dubowSubmission.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${dubowSubmissionInstance?.lastUpdated}" /></td>
				
			</tr>
		

		</tbody>
	</table>
</section>

</body>
</html>
