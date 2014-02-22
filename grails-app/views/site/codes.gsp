<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<title>Generate Codes</title>
</head>

<body>

<section id="create-site" class="first">

	<g:form action="generateCodes" class="form-horizontal" >
		<fieldset class="form">
            <legend>Gift Codes</legend>
            <div class="control-group fieldcontain required">
                <label for="discountStartingId" class="control-label">Starting ID<span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="discountStartingId" required="" value="0"/>
                </div>
            </div>
            <div class="control-group fieldcontain required">
                <label for="numberGiftCodes" class="control-label">Quantity<span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="numberGiftCodes" required="" value="0"/>
                </div>
            </div>
            <div class="control-group fieldcontain required">
                <label for="giftCodesAmount" class="control-label">Amount<span class="required-indicator">*</span></label>
                <div class="controls">
                    <div class="input-prepend">
                      <span class="add-on">$</span>
                        <g:textField name="giftCodesAmount" required="" value="0"/>
                    </div>
                </div>
            </div>
		</fieldset>
		<fieldset class="form">
            <legend>Free Shipping</legend>
            <div class="control-group fieldcontain required">
                <label for="numberShippingCodes" class="control-label">Quantity<span class="required-indicator">*</span></label>
                <div class="controls">
                    <g:textField name="numberShippingCodes" required="" value="0"/>
                </div>
            </div>
            <div class="control-group fieldcontain required">
                <label for="shippingCodesAmount" class="control-label">Amount<span class="required-indicator">*</span></label>
                <div class="controls">
                    <div class="input-prepend">
                      <span class="add-on">$</span>
                        <g:textField name="shippingCodesAmount" required="" value="0"/>
                    </div>
                </div>
            </div>
		</fieldset>
		<div class="form-actions">
			<g:submitButton name="generateCodes" class="btn btn-primary" value="Generate" />
		</div>
	</g:form>
	
</section>

</body>

</html>
