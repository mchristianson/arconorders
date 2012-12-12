package com.arconorders

import com.arconorders.util.ArconUtil
import org.springframework.dao.DataIntegrityViolationException

/**
 * ArconOrderController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class ArconOrderController {

    def orderService
    def orderProcessingService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def resendOrder() {
        ArconOrder arconOrderInstance = ArconOrder.get(params.id)
        arconOrderInstance.orderDetails.each {it.attachProduct()}
        orderProcessingService.convertAndSendOrders([arconOrderInstance])
        if (orderProcessingService.errors) {
            flash.error = ArconUtil.convertMessageToString(orderProcessingService.errors, true)
        }
        if (orderProcessingService.messages) {
            flash.message += ArconUtil.convertMessageToString(orderProcessingService.messages, false)
        }
        orderProcessingService.errors = []
        orderProcessingService.messages = []
        redirect(action: 'show', id: params.id)
    }

    def parseOrders() {
        def orders = orderService.parseOrders()
        if (orderService.errors) {
            flash.error = ArconUtil.convertMessageToString(orderProcessingService.errors, true)
        } else {
            flash.message = ArconUtil.convertMessageToString(["Orders ${orders.orderID} processed."], false)
        }
        if (orderService.messages) {
            flash.message = ArconUtil.convertMessageToString(orderProcessingService.messages, false)
        }
        orderProcessingService.errors = []
        orderProcessingService.messages = []
        redirect(action: 'index')
    }
    def processOrders() {
        def orders = orderProcessingService.processOrders()
        if (orderProcessingService.errors) {
            flash.error = ArconUtil.convertMessageToString(orderProcessingService.errors, true)
        } else {
            flash.message = ArconUtil.convertMessageToString(["Orders ${orders.orderID} processed."], false)
        }
        if (orderProcessingService.messages) {
            println orderProcessingService.messages.flatten()
            flash.message = ArconUtil.convertMessageToString(orderProcessingService.messages, false)
        }
        orderProcessingService.errors = []
        orderProcessingService.messages = []
        redirect(action: 'list')
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.sort = params.sort ?: 'orderDate'
        params.order = params.order ?: 'desc'
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [arconOrderInstanceList: ArconOrder.list(params), arconOrderInstanceTotal: ArconOrder.count()]
    }

    def create() {
        [arconOrderInstance: new ArconOrder(params)]
    }

    def save() {
        def arconOrderInstance = new ArconOrder(params)
        if (!arconOrderInstance.save(flush: true)) {
            render(view: "create", model: [arconOrderInstance: arconOrderInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), arconOrderInstance.id])
        redirect(action: "show", id: arconOrderInstance.id)
    }

    def show() {
        def arconOrderInstance = ArconOrder.get(params.id)
        if (!arconOrderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "list")
            return
        }

        [arconOrderInstance: arconOrderInstance]
    }

    def edit() {
        def arconOrderInstance = ArconOrder.get(params.id)
        if (!arconOrderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "list")
            return
        }

        [arconOrderInstance: arconOrderInstance]
    }

    def update() {
        def arconOrderInstance = ArconOrder.get(params.id)
        if (!arconOrderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (arconOrderInstance.version > version) {
                arconOrderInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'arconOrder.label', default: 'ArconOrder')] as Object[],
                        "Another user has updated this ArconOrder while you were editing")
                render(view: "edit", model: [arconOrderInstance: arconOrderInstance])
                return
            }
        }

        arconOrderInstance.properties = params

        if (!arconOrderInstance.save(flush: true)) {
            render(view: "edit", model: [arconOrderInstance: arconOrderInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), arconOrderInstance.id])
        redirect(action: "show", id: arconOrderInstance.id)
    }

    def delete() {
        def arconOrderInstance = ArconOrder.get(params.id)
        if (!arconOrderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "list")
            return
        }

        try {
            arconOrderInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
