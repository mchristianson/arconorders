package com.arconorders

import com.arconorders.service.OrderProcessingService
import org.springframework.dao.DataIntegrityViolationException

/**
 * SiteController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class SiteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    OrderProcessingService orderProcessingService

    def codes() {

    }

    def generateCodes() {
        Integer discountStartingId = params.discountStartingId as Integer

        Integer numberGiftCodes = params.numberGiftCodes as Integer
        Integer giftCodesAmount = params.giftCodesAmount as Integer

        Integer numberShippingCodes = params.numberShippingCodes as Integer
        Integer shippingCodesAmount = params.shippingCodesAmount as Integer

        String header = "discountautoid,name,minqty,maxqty,discounttype,discountvalue,lastmodified,span,begindate,enddate,minorderprice,maxorderprice,apply_to_all_orders,lastmodby,couponcode,onetimeuse,cannot_use_with_any_other,taxable_discountaftertax,couponusage\n"
        List rows = []
        numberGiftCodes.times {
            rows << "${discountStartingId++},\$${giftCodesAmount} Gift,,,Per Order,${giftCodesAmount},10/8/13 14:39,N,,,,,,1,${orderProcessingService.getUniqueID(5)},Y,Y,0,SingleUsePerCustomer"
        }
        numberShippingCodes.times {
            rows << "${discountStartingId++},Free Shipping,,,Free Shipping,${shippingCodesAmount},10/8/13 14:39,N,,,,,,1,${orderProcessingService.getUniqueID(5)},Y,N,0,SingleUsePerCustomer"
        }
        String csv = header + rows.join('\n')
        response.setContentType("application/octet-stream")

        response.setHeader("Content-disposition", "attachment;filename=discount_codes.csv")

        render(text:csv,contentType:"text/plain",encoding:"UTF-8")
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [siteInstanceList: Site.list(params), siteInstanceTotal: Site.count()]
    }

    def create() {
        [siteInstance: new Site(params)]
    }

    def save() {
        def siteInstance = new Site(params)
        if (!siteInstance.save(flush: true)) {
            render(view: "create", model: [siteInstance: siteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'site.label', default: 'Site'), siteInstance.id])
        redirect(action: "show", id: siteInstance.id)
    }

    def show() {
        def siteInstance = Site.get(params.id)
        if (!siteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])
            redirect(action: "list")
            return
        }

        [siteInstance: siteInstance]
    }

    def edit() {
        def siteInstance = Site.get(params.id)
        if (!siteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])
            redirect(action: "list")
            return
        }

        [siteInstance: siteInstance]
    }

    def update() {
        def siteInstance = Site.get(params.id)
        if (!siteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (siteInstance.version > version) {
                siteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'site.label', default: 'Site')] as Object[],
                        "Another user has updated this Site while you were editing")
                render(view: "edit", model: [siteInstance: siteInstance])
                return
            }
        }

        siteInstance.properties = params

        if (!siteInstance.save(flush: true)) {
            render(view: "edit", model: [siteInstance: siteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'site.label', default: 'Site'), siteInstance.id])
        redirect(action: "show", id: siteInstance.id)
    }

    def delete() {
        def siteInstance = Site.get(params.id)
        if (!siteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])
            redirect(action: "list")
            return
        }

        try {
            siteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'site.label', default: 'Site'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'site.label', default: 'Site'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
