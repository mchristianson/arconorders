<%@ page import="com.arconorders.Product" %>



			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'vendorCode', 'error')} required">
				<label for="vendorCode" class="control-label"><g:message code="product.vendorCode.label" default="Vendor Code" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="vendorCode" required="" value="${productInstance?.vendorCode}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'vendorCode', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'dubowProductId', 'error')} ">
				<label for="dubowProductId" class="control-label"><g:message code="product.dubowProductId.label" default="Dubow Product Id" /></label>
				<div class="controls">
					<g:textField name="dubowProductId" value="${productInstance?.dubowProductId}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'dubowProductId', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'code', 'error')} required">
				<label for="code" class="control-label"><g:message code="product.code.label" default="Code" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="code" required="" value="${productInstance?.code}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'code', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'name', 'error')} required">
				<label for="name" class="control-label"><g:message code="product.name.label" default="Name" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="name" required="" value="${productInstance?.name}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'name', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'mill', 'error')} ">
				<label for="mill" class="control-label"><g:message code="product.mill.label" default="Mill" /></label>
				<div class="controls">
					<g:textField name="mill" value="${productInstance?.mill}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'mill', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'color', 'error')} ">
				<label for="color" class="control-label"><g:message code="product.color.label" default="Color" /></label>
				<div class="controls">
					<g:textField name="color" value="${productInstance?.color}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'color', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'designNumber', 'error')} ">
				<label for="designNumber" class="control-label"><g:message code="product.designNumber.label" default="Design Number" /></label>
				<div class="controls">
					<g:textField name="designNumber" value="${productInstance?.designNumber}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'designNumber', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'colorWay', 'error')} ">
				<label for="colorWay" class="control-label"><g:message code="product.colorWay.label" default="Color Way" /></label>
				<div class="controls">
					<g:textField name="colorWay" value="${productInstance?.colorWay}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'colorWay', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'location', 'error')} ">
				<label for="location" class="control-label"><g:message code="product.location.label" default="Location" /></label>
				<div class="controls">
					<g:textField name="location" value="${productInstance?.location}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'location', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'personalization', 'error')} ">
				<label for="personalization" class="control-label"><g:message code="product.personalization.label" default="Personalization" /></label>
				<div class="controls">
					<g:textField name="personalization" value="${productInstance?.personalization}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'personalization', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'personalizationColor', 'error')} ">
				<label for="personalizationColor" class="control-label"><g:message code="product.personalizationColor.label" default="Personalization Color" /></label>
				<div class="controls">
					<g:textField name="personalizationColor" value="${productInstance?.personalizationColor}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'personalizationColor', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'procurementType', 'error')} required">
				<label for="procurementType" class="control-label"><g:message code="product.procurementType.label" default="Procurement Type" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:textField name="procurementType" required="" value="${productInstance?.procurementType}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'procurementType', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'appliqueDesignNumber', 'error')} ">
				<label for="appliqueDesignNumber" class="control-label"><g:message code="product.appliqueDesignNumber.label" default="Applique Design Number" /></label>
				<div class="controls">
					<g:textField name="appliqueDesignNumber" value="${productInstance?.appliqueDesignNumber}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'appliqueDesignNumber', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'appliqueColorWay', 'error')} ">
				<label for="appliqueColorWay" class="control-label"><g:message code="product.appliqueColorWay.label" default="Applique Color Way" /></label>
				<div class="controls">
					<g:textField name="appliqueColorWay" value="${productInstance?.appliqueColorWay}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'appliqueColorWay', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'appliqueLocation', 'error')} ">
				<label for="appliqueLocation" class="control-label"><g:message code="product.appliqueLocation.label" default="Applique Location" /></label>
				<div class="controls">
					<g:textField name="appliqueLocation" value="${productInstance?.appliqueLocation}"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'appliqueLocation', 'error')}</span>
				</div>
			</div>

			<div class="control-group fieldcontain ${hasErrors(bean: productInstance, field: 'site', 'error')} required">
				<label for="site" class="control-label"><g:message code="product.site.label" default="Site" /><span class="required-indicator">*</span></label>
				<div class="controls">
					<g:select id="site" name="site.id" from="${com.arconorders.Site.list()}" optionKey="id" required="" value="${productInstance?.site?.id}" class="many-to-one"/>
					<span class="help-inline">${hasErrors(bean: productInstance, field: 'site', 'error')}</span>
				</div>
			</div>

