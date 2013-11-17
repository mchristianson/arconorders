package com.arconorders

import com.arconorders.exception.OrderProcessingException
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
        try {
            orderProcessingService.convertAndSendOrders([arconOrderInstance])
            flash.message = "${arconOrderInstance} order sent successfully."
        } catch (OrderProcessingException e) {
            new OrderError(orderId: arconOrderInstance.id?.toString(), error: e.message)
            flash.error = ArconUtil.convertMessageToString([e.message], true)
        }
        redirect(action: 'show', id: params.id)
    }

    def processOrders() {
        try {
            def orders = orderProcessingService.processOrders()
            flash.message = (orders) ? "Orders ${orders.orderID} processed." : 'No orders to process'
        } catch (OrderProcessingException e) {
            new OrderError(orderId: e.orderId, error: e.message).save()
            flash.error = ArconUtil.convertMessageToString([e.message], true)
        }
        redirect(action: 'list')
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        def list = ArconOrder.list()
        def unprocessedErrors = OrderError.findAllByProcessed(false)
        [arconOrderInstanceList: list, arconOrderInstanceTotal: ArconOrder.count(), unprocessedErrors: unprocessedErrors]
    }

    def clearError() {
        OrderError orderError = OrderError.get(params.id)
        orderError.processed = true
        orderError.save()
        redirect(action: "list", params: params)
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
        def arconOrderInstance = ArconOrder.get(params.id) ?: ArconOrder.findByOrderID(params.orderId)
        if (!arconOrderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'arconOrder.label', default: 'ArconOrder'), params.id])
            redirect(action: "list")
            return
        }
        def unprocessedErrors = OrderError.findAllByOrderId(arconOrderInstance.orderID).findAll { !it.processed }
        println unprocessedErrors
        [arconOrderInstance: arconOrderInstance, unprocessedErrors: unprocessedErrors]
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
