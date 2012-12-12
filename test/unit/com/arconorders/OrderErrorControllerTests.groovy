package com.arconorders



import org.junit.*
import grails.test.mixin.*

/**
 * OrderErrorControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(OrderErrorController)
@Mock(OrderError)
class OrderErrorControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/orderError/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.orderErrorInstanceList.size() == 0
        assert model.orderErrorInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.orderErrorInstance != null
    }

    void testSave() {
        controller.save()

        assert model.orderErrorInstance != null
        assert view == '/orderError/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/orderError/show/1'
        assert controller.flash.message != null
        assert OrderError.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/orderError/list'


        populateValidParams(params)
        def orderError = new OrderError(params)

        assert orderError.save() != null

        params.id = orderError.id

        def model = controller.show()

        assert model.orderErrorInstance == orderError
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/orderError/list'


        populateValidParams(params)
        def orderError = new OrderError(params)

        assert orderError.save() != null

        params.id = orderError.id

        def model = controller.edit()

        assert model.orderErrorInstance == orderError
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/orderError/list'

        response.reset()


        populateValidParams(params)
        def orderError = new OrderError(params)

        assert orderError.save() != null

        // test invalid parameters in update
        params.id = orderError.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/orderError/edit"
        assert model.orderErrorInstance != null

        orderError.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/orderError/show/$orderError.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        orderError.clearErrors()

        populateValidParams(params)
        params.id = orderError.id
        params.version = -1
        controller.update()

        assert view == "/orderError/edit"
        assert model.orderErrorInstance != null
        assert model.orderErrorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/orderError/list'

        response.reset()

        populateValidParams(params)
        def orderError = new OrderError(params)

        assert orderError.save() != null
        assert OrderError.count() == 1

        params.id = orderError.id

        controller.delete()

        assert OrderError.count() == 0
        assert OrderError.get(orderError.id) == null
        assert response.redirectedUrl == '/orderError/list'
    }
}
