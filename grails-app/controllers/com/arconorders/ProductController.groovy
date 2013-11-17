package com.arconorders

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

/**
 * ProductController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class ProductController {

    def productService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def verifyProducts() {
        productService.verifyAllProducts()
        flash.message = 'Successfully verified all products.'
        redirect(action: "list", params: params)
    }

    def verifyProduct() {
        def product = Product.read(params.id)
        product = productService.verifyProduct(product)
        String message = product.dubowProductName ?
            "<div class=\"alert alert-success\">${product.dubowProductName} - ${product.dubowProductDesc}</div>" :
            "<div class=\"alert alert-error\">Product ID: ${product.dubowProductId} was not found at dubow!</div>"
        render message
    }

    def updateRow() {
        def productInstance = Product.get(params.id)
        productInstance."${params.columnName}" = params.value
        productInstance = productService.verifyProduct(productInstance)
        productInstance.save()
        render params.value
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        [productInstanceList: Product.list(), productInstanceTotal: Product.count()]
    }

    def create() {
        [productInstance: new Product(params)]
    }

    def save() {
        def productInstance = new Product(params)
        if (!productInstance.save(flush: true)) {
            render(view: "create", model: [productInstance: productInstance])
            return
        }
        productInstance = productService.verifyProduct(productInstance)

        flash.message = message(code: 'default.created.message', args: [message(code: 'product.label', default: 'Product'), productInstance.id])
        redirect(action: "show", id: productInstance.id)
    }

    def show() {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
            redirect(action: "list")
            return
        }

        [productInstance: productInstance]
    }

    def edit() {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
            redirect(action: "list")
            return
        }

        [productInstance: productInstance]
    }

    def update() {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (productInstance.version > version) {
                productInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'product.label', default: 'Product')] as Object[],
                        "Another user has updated this Product while you were editing")
                render(view: "edit", model: [productInstance: productInstance])
                return
            }
        }

        productInstance.properties = params

        if (!productInstance.save(flush: true)) {
            render(view: "edit", model: [productInstance: productInstance])
            return
        }
        productInstance = productService.verifyProduct(productInstance)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'Product'), productInstance.id])
        redirect(action: "show", id: productInstance.id)
    }

    def delete() {
        def productInstance = Product.get(params.id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
            redirect(action: "list")
            return
        }

        try {
            productInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'product.label', default: 'Product'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'product.label', default: 'Product'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
