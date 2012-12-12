package com.arconorders



import org.junit.*
import grails.test.mixin.*

/**
 * ArconOrderControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(ArconOrderController)
@Mock(ArconOrder)
class ArconOrderControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/arconOrder/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.arconOrderInstanceList.size() == 0
        assert model.arconOrderInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.arconOrderInstance != null
    }

    void testSave() {
        controller.save()

        assert model.arconOrderInstance != null
        assert view == '/arconOrder/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/arconOrder/show/1'
        assert controller.flash.message != null
        assert ArconOrder.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/arconOrder/list'


        populateValidParams(params)
        def arconOrder = new ArconOrder(params)

        assert arconOrder.save() != null

        params.id = arconOrder.id

        def model = controller.show()

        assert model.arconOrderInstance == arconOrder
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/arconOrder/list'


        populateValidParams(params)
        def arconOrder = new ArconOrder(params)

        assert arconOrder.save() != null

        params.id = arconOrder.id

        def model = controller.edit()

        assert model.arconOrderInstance == arconOrder
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/arconOrder/list'

        response.reset()


        populateValidParams(params)
        def arconOrder = new ArconOrder(params)

        assert arconOrder.save() != null

        // test invalid parameters in update
        params.id = arconOrder.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/arconOrder/edit"
        assert model.arconOrderInstance != null

        arconOrder.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/arconOrder/show/$arconOrder.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        arconOrder.clearErrors()

        populateValidParams(params)
        params.id = arconOrder.id
        params.version = -1
        controller.update()

        assert view == "/arconOrder/edit"
        assert model.arconOrderInstance != null
        assert model.arconOrderInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/arconOrder/list'

        response.reset()

        populateValidParams(params)
        def arconOrder = new ArconOrder(params)

        assert arconOrder.save() != null
        assert ArconOrder.count() == 1

        params.id = arconOrder.id

        controller.delete()

        assert ArconOrder.count() == 0
        assert ArconOrder.get(arconOrder.id) == null
        assert response.redirectedUrl == '/arconOrder/list'
    }
}
