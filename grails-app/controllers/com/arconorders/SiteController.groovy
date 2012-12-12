package com.arconorders

import org.springframework.dao.DataIntegrityViolationException

/**
 * SiteController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class SiteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
