<%@ page import="com.arconorders.Site" %>



			<div class="control-group fieldcontain ${hasErrors(bean: siteInstance, field: 'name', 'error')} required">
				<label for="name" class="control-label"><g:message code="site.name.label" default="Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="name" required="" value="${siteInstance?.name}"/>
					<span class="help-inline">${hasErrors(bean: siteInstance, field: 'name', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: siteInstance, field: 'baseUrl', 'error')} required">
				<label for="baseUrl" class="control-label"><g:message code="site.baseUrl.label" default="Base Url" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="baseUrl" required="" value="${siteInstance?.baseUrl}"/>
					<span class="help-inline">${hasErrors(bean: siteInstance, field: 'baseUrl', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: siteInstance, field: 'products', 'error')} ">
				<label for="products" class="control-label"><g:message code="site.products.label" default="Products" /></label>
				<div class="controls">
					
<ul class="one-to-many">
<g:each in="${siteInstance?.products?}" var="p">
    <li><g:link controller="product" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="product" action="create" params="['site.id': siteInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'product.label', default: 'Product')])}</g:link>
</li>
</ul>

					<span class="help-inline">${hasErrors(bean: siteInstance, field: 'products', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: siteInstance, field: 'users', 'error')} ">
				<label for="users" class="control-label"><g:message code="site.users.label" default="Users" /></label>
				<div class="controls">
					<g:select name="users" from="${com.arconorders.User.list()}" multiple="multiple" optionKey="id" size="5" value="${siteInstance?.users*.id}" class="many-to-many"/>
					<span class="help-inline">${hasErrors(bean: siteInstance, field: 'users', 'error')}</span>
				</div>
			</div>

