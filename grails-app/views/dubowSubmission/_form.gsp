<%@ page import="com.arconorders.DubowSubmission" %>



			<div class="control-group fieldcontain ${hasErrors(bean: dubowSubmissionInstance, field: 'responseXmlString', 'error')} ">
				<label for="responseXmlString" class="control-label"><g:message code="dubowSubmission.responseXmlString.label" default="Response Xml String" /></label>
				<div class="controls">
					<g:textField name="responseXmlString" value="${dubowSubmissionInstance?.responseXmlString}"/>
					<span class="help-inline">${hasErrors(bean: dubowSubmissionInstance, field: 'responseXmlString', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: dubowSubmissionInstance, field: 'successful', 'error')} ">
				<label for="successful" class="control-label"><g:message code="dubowSubmission.successful.label" default="Successful" /></label>
				<div class="controls">
					<bs:checkBox name="successful" value="${dubowSubmissionInstance?.successful}" />
					<span class="help-inline">${hasErrors(bean: dubowSubmissionInstance, field: 'successful', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: dubowSubmissionInstance, field: 'arconOrders', 'error')} ">
				<label for="arconOrders" class="control-label"><g:message code="dubowSubmission.arconOrders.label" default="Arcon Orders" /></label>
				<div class="controls">
					<g:select name="arconOrders" from="${com.arconorders.ArconOrder.list()}" multiple="multiple" optionKey="id" size="5" value="${dubowSubmissionInstance?.arconOrders*.id}" class="many-to-many"/>
					<span class="help-inline">${hasErrors(bean: dubowSubmissionInstance, field: 'arconOrders', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: dubowSubmissionInstance, field: 'requestXmlString', 'error')} ">
				<label for="requestXmlString" class="control-label"><g:message code="dubowSubmission.requestXmlString.label" default="Request Xml String" /></label>
				<div class="controls">
					<g:textField name="requestXmlString" value="${dubowSubmissionInstance?.requestXmlString}"/>
					<span class="help-inline">${hasErrors(bean: dubowSubmissionInstance, field: 'requestXmlString', 'error')}</span>
				</div>
			</div>

