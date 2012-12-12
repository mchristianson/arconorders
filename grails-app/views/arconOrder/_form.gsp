<%@ page import="com.arconorders.ArconOrder" %>



			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'firstName', 'error')} ">
				<label for="firstName" class="control-label"><g:message code="arconOrder.firstName.label" default="First Name" /></label>
				<div class="controls">
					<g:textField name="firstName" value="${arconOrderInstance?.firstName}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'firstName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'lastName', 'error')} ">
				<label for="lastName" class="control-label"><g:message code="arconOrder.lastName.label" default="Last Name" /></label>
				<div class="controls">
					<g:textField name="lastName" value="${arconOrderInstance?.lastName}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'lastName', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'orderID', 'error')} ">
				<label for="orderID" class="control-label"><g:message code="arconOrder.orderID.label" default="Order ID" /></label>
				<div class="controls">
					<g:textField name="orderID" value="${arconOrderInstance?.orderID}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'orderID', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'orderDate', 'error')} ">
				<label for="orderDate" class="control-label"><g:message code="arconOrder.orderDate.label" default="Order Date" /></label>
				<div class="controls">
					<g:textField name="orderDate" value="${arconOrderInstance?.orderDate}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'orderDate', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'customerID', 'error')} ">
				<label for="customerID" class="control-label"><g:message code="arconOrder.customerID.label" default="Customer ID" /></label>
				<div class="controls">
					<g:textField name="customerID" value="${arconOrderInstance?.customerID}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'customerID', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'address1', 'error')} ">
				<label for="address1" class="control-label"><g:message code="arconOrder.address1.label" default="Address1" /></label>
				<div class="controls">
					<g:textField name="address1" value="${arconOrderInstance?.address1}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'address1', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'address2', 'error')} ">
				<label for="address2" class="control-label"><g:message code="arconOrder.address2.label" default="Address2" /></label>
				<div class="controls">
					<g:textField name="address2" value="${arconOrderInstance?.address2}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'address2', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'city', 'error')} ">
				<label for="city" class="control-label"><g:message code="arconOrder.city.label" default="City" /></label>
				<div class="controls">
					<g:textField name="city" value="${arconOrderInstance?.city}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'city', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'state', 'error')} ">
				<label for="state" class="control-label"><g:message code="arconOrder.state.label" default="State" /></label>
				<div class="controls">
					<g:textField name="state" value="${arconOrderInstance?.state}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'state', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'postalCode', 'error')} ">
				<label for="postalCode" class="control-label"><g:message code="arconOrder.postalCode.label" default="Postal Code" /></label>
				<div class="controls">
					<g:textField name="postalCode" value="${arconOrderInstance?.postalCode}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'postalCode', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'country', 'error')} ">
				<label for="country" class="control-label"><g:message code="arconOrder.country.label" default="Country" /></label>
				<div class="controls">
					<g:textField name="country" value="${arconOrderInstance?.country}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'country', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'comments', 'error')} ">
				<label for="comments" class="control-label"><g:message code="arconOrder.comments.label" default="Comments" /></label>
				<div class="controls">
					<g:textField name="comments" value="${arconOrderInstance?.comments}"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'comments', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'site', 'error')} required">
				<label for="site" class="control-label"><g:message code="arconOrder.site.label" default="Site" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="site" name="site.id" from="${com.arconorders.Site.list()}" optionKey="id" required="" value="${arconOrderInstance?.site?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'site', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'orderDetails', 'error')} ">
				<label for="orderDetails" class="control-label"><g:message code="arconOrder.orderDetails.label" default="Order Details" /></label>
				<div class="controls">
					
<ul class="one-to-many">
<g:each in="${arconOrderInstance?.orderDetails?}" var="o">
    <li><g:link controller="orderDetail" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="orderDetail" action="create" params="['arconOrder.id': arconOrderInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'orderDetail.label', default: 'OrderDetail')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'orderDetails', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: arconOrderInstance, field: 'success', 'error')} ">
				<label for="success" class="control-label"><g:message code="arconOrder.success.label" default="Success" /></label>
				<div class="controls">
					<bs:checkBox name="success" value="${arconOrderInstance?.success}" />
					<span class="help-inline">${hasErrors(bean: arconOrderInstance, field: 'success', 'error')}</span>
				</div>
			</div>

