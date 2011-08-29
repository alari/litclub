package litclub

import org.springframework.dao.DataIntegrityViolationException

class PersonalMessageHeadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personalMessageHeadInstanceList: PersonalMessageHead.list(params), personalMessageHeadInstanceTotal: PersonalMessageHead.count()]
    }

    def create() {
        [personalMessageHeadInstance: new PersonalMessageHead(params)]
    }

    def save() {
        def personalMessageHeadInstance = new PersonalMessageHead(params)
        if (!personalMessageHeadInstance.save(flush: true)) {
            render(view: "create", model: [personalMessageHeadInstance: personalMessageHeadInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), personalMessageHeadInstance.id])
        redirect(action: "show", id: personalMessageHeadInstance.id)
    }

    def show() {
        def personalMessageHeadInstance = PersonalMessageHead.get(params.id)
        if (!personalMessageHeadInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), params.id])
            redirect(action: "list")
            return
        }

        [personalMessageHeadInstance: personalMessageHeadInstance]
    }

    def edit() {
        def personalMessageHeadInstance = PersonalMessageHead.get(params.id)
        if (!personalMessageHeadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), params.id])
            redirect(action: "list")
            return
        }

        [personalMessageHeadInstance: personalMessageHeadInstance]
    }

    def update() {
        def personalMessageHeadInstance = PersonalMessageHead.get(params.id)
        if (!personalMessageHeadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (personalMessageHeadInstance.version > version) {
                personalMessageHeadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead')] as Object[],
                          "Another user has updated this PersonalMessageHead while you were editing")
                render(view: "edit", model: [personalMessageHeadInstance: personalMessageHeadInstance])
                return
            }
        }

        personalMessageHeadInstance.properties = params

        if (!personalMessageHeadInstance.save(flush: true)) {
            render(view: "edit", model: [personalMessageHeadInstance: personalMessageHeadInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), personalMessageHeadInstance.id])
        redirect(action: "show", id: personalMessageHeadInstance.id)
    }

    def delete() {
        def personalMessageHeadInstance = PersonalMessageHead.get(params.id)
        if (!personalMessageHeadInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), params.id])
            redirect(action: "list")
            return
        }

        try {
            personalMessageHeadInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'personalMessageHead.label', default: 'PersonalMessageHead'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
