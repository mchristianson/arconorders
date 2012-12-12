package com.arconorders

import org.springframework.dao.DataIntegrityViolationException

/**
 * DubowSubmissionController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class DubowSubmissionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.sort = params.sort ?: 'lastUpdated'
        params.order = params.order ?: 'desc'
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [dubowSubmissionInstanceList: DubowSubmission.list(params), dubowSubmissionInstanceTotal: DubowSubmission.count()]
    }

    def create() {
        [dubowSubmissionInstance: new DubowSubmission(params)]
    }

    def save() {
        def dubowSubmissionInstance = new DubowSubmission(params)
        if (!dubowSubmissionInstance.save(flush: true)) {
            render(view: "create", model: [dubowSubmissionInstance: dubowSubmissionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), dubowSubmissionInstance.id])
        redirect(action: "show", id: dubowSubmissionInstance.id)
    }

    def show() {
        def dubowSubmissionInstance = DubowSubmission.get(params.id)
        if (!dubowSubmissionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), params.id])
            redirect(action: "list")
            return
        }

        [dubowSubmissionInstance: dubowSubmissionInstance]
    }

    def edit() {
        def dubowSubmissionInstance = DubowSubmission.get(params.id)
        if (!dubowSubmissionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), params.id])
            redirect(action: "list")
            return
        }

        [dubowSubmissionInstance: dubowSubmissionInstance]
    }

    def update() {
        def dubowSubmissionInstance = DubowSubmission.get(params.id)
        if (!dubowSubmissionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dubowSubmissionInstance.version > version) {
                dubowSubmissionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'dubowSubmission.label', default: 'DubowSubmission')] as Object[],
                        "Another user has updated this DubowSubmission while you were editing")
                render(view: "edit", model: [dubowSubmissionInstance: dubowSubmissionInstance])
                return
            }
        }

        dubowSubmissionInstance.properties = params

        if (!dubowSubmissionInstance.save(flush: true)) {
            render(view: "edit", model: [dubowSubmissionInstance: dubowSubmissionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), dubowSubmissionInstance.id])
        redirect(action: "show", id: dubowSubmissionInstance.id)
    }

    def delete() {
        def dubowSubmissionInstance = DubowSubmission.get(params.id)
        if (!dubowSubmissionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), params.id])
            redirect(action: "list")
            return
        }

        try {
            dubowSubmissionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dubowSubmission.label', default: 'DubowSubmission'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
