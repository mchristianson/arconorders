<%@ page import="com.arconorders.OrderError" %>



			<div class="control-group fieldcontain ${hasErrors(bean: orderErrorInstance, field: 'error', 'error')} ">
				<label for="error" class="control-label"><g:message code="orderError.error.label" default="Error" /></label>
				<div class="controls">
					<g:textField name="error" value="${orderErrorInstance?.error}"/>
					<span class="help-inline">${hasErrors(bean: orderErrorInstance, field: 'error', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: orderErrorInstance, field: 'orderId', 'error')} ">
				<label for="orderId" class="control-label"><g:message code="orderError.orderId.label" default="Order Id" /></label>
				<div class="controls">
					<g:textField name="orderId" value="${orderErrorInstance?.orderId}"/>
					<span class="help-inline">${hasErrors(bean: orderErrorInstance, field: 'orderId', 'error')}</span>
				</div>
			</div>

