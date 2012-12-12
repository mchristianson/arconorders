package com.arconorders



import org.junit.*
import grails.test.mixin.*

/**
 * DubowSubmissionControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(DubowSubmissionController)
@Mock(DubowSubmission)
class DubowSubmissionControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dubowSubmission/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dubowSubmissionInstanceList.size() == 0
        assert model.dubowSubmissionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dubowSubmissionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dubowSubmissionInstance != null
        assert view == '/dubowSubmission/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dubowSubmission/show/1'
        assert controller.flash.message != null
        assert DubowSubmission.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dubowSubmission/list'


        populateValidParams(params)
        def dubowSubmission = new DubowSubmission(params)

        assert dubowSubmission.save() != null

        params.id = dubowSubmission.id

        def model = controller.show()

        assert model.dubowSubmissionInstance == dubowSubmission
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dubowSubmission/list'


        populateValidParams(params)
        def dubowSubmission = new DubowSubmission(params)

        assert dubowSubmission.save() != null

        params.id = dubowSubmission.id

        def model = controller.edit()

        assert model.dubowSubmissionInstance == dubowSubmission
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dubowSubmission/list'

        response.reset()


        populateValidParams(params)
        def dubowSubmission = new DubowSubmission(params)

        assert dubowSubmission.save() != null

        // test invalid parameters in update
        params.id = dubowSubmission.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dubowSubmission/edit"
        assert model.dubowSubmissionInstance != null

        dubowSubmission.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dubowSubmission/show/$dubowSubmission.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dubowSubmission.clearErrors()

        populateValidParams(params)
        params.id = dubowSubmission.id
        params.version = -1
        controller.update()

        assert view == "/dubowSubmission/edit"
        assert model.dubowSubmissionInstance != null
        assert model.dubowSubmissionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dubowSubmission/list'

        response.reset()

        populateValidParams(params)
        def dubowSubmission = new DubowSubmission(params)

        assert dubowSubmission.save() != null
        assert DubowSubmission.count() == 1

        params.id = dubowSubmission.id

        controller.delete()

        assert DubowSubmission.count() == 0
        assert DubowSubmission.get(dubowSubmission.id) == null
        assert response.redirectedUrl == '/dubowSubmission/list'
    }
}
