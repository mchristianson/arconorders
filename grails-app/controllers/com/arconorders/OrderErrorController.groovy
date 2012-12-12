package com.arconorders

import org.springframework.dao.DataIntegrityViolationException

/**
 * OrderErrorController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class OrderErrorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.sort = params.sort ?: 'lastUpdated'
        params.order = params.order ?: 'desc'
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [orderErrorInstanceList: OrderError.list(params), orderErrorInstanceTotal: OrderError.count()]
    }

    def create() {
        [orderErrorInstance: new OrderError(params)]
    }

    def save() {
        def orderErrorInstance = new OrderError(params)
        if (!orderErrorInstance.save(flush: true)) {
            render(view: "create", model: [orderErrorInstance: orderErrorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'orderError.label', default: 'OrderError'), orderErrorInstance.id])
        redirect(action: "show", id: orderErrorInstance.id)
    }

    def show() {
        def orderErrorInstance = OrderError.get(params.id)
        if (!orderErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orderError.label', default: 'OrderError'), params.id])
            redirect(action: "list")
            return
        }

        [orderErrorInstance: orderErrorInstance]
    }

    def edit() {
        def orderErrorInstance = OrderError.get(params.id)
        if (!orderErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orderError.label', default: 'OrderError'), params.id])
            redirect(action: "list")
            return
        }

        [orderErrorInstance: orderErrorInstance]
    }

    def update() {
        def orderErrorInstance = OrderError.get(params.id)
        if (!orderErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orderError.label', default: 'OrderError'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (orderErrorInstance.version > version) {
                orderErrorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'orderError.label', default: 'OrderError')] as Object[],
                        "Another user has updated this OrderError while you were editing")
                render(view: "edit", model: [orderErrorInstance: orderErrorInstance])
                return
            }
        }

        orderErrorInstance.properties = params

        if (!orderErrorInstance.save(flush: true)) {
            render(view: "edit", model: [orderErrorInstance: orderErrorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'orderError.label', default: 'OrderError'), orderErrorInstance.id])
        redirect(action: "show", id: orderErrorInstance.id)
    }

    def delete() {
        def orderErrorInstance = OrderError.get(params.id)
        if (!orderErrorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'orderError.label', default: 'OrderError'), params.id])
            redirect(action: "list")
            return
        }

        try {
            orderErrorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'orderError.label', default: 'OrderError'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'orderError.label', default: 'OrderError'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
