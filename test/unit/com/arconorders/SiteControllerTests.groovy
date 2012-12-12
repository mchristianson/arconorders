package com.arconorders



import org.junit.*
import grails.test.mixin.*

/**
 * SiteControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(SiteController)
@Mock(Site)
class SiteControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/site/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.siteInstanceList.size() == 0
        assert model.siteInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.siteInstance != null
    }

    void testSave() {
        controller.save()

        assert model.siteInstance != null
        assert view == '/site/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/site/show/1'
        assert controller.flash.message != null
        assert Site.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/site/list'


        populateValidParams(params)
        def site = new Site(params)

        assert site.save() != null

        params.id = site.id

        def model = controller.show()

        assert model.siteInstance == site
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/site/list'


        populateValidParams(params)
        def site = new Site(params)

        assert site.save() != null

        params.id = site.id

        def model = controller.edit()

        assert model.siteInstance == site
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/site/list'

        response.reset()


        populateValidParams(params)
        def site = new Site(params)

        assert site.save() != null

        // test invalid parameters in update
        params.id = site.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/site/edit"
        assert model.siteInstance != null

        site.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/site/show/$site.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        site.clearErrors()

        populateValidParams(params)
        params.id = site.id
        params.version = -1
        controller.update()

        assert view == "/site/edit"
        assert model.siteInstance != null
        assert model.siteInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/site/list'

        response.reset()

        populateValidParams(params)
        def site = new Site(params)

        assert site.save() != null
        assert Site.count() == 1

        params.id = site.id

        controller.delete()

        assert Site.count() == 0
        assert Site.get(site.id) == null
        assert response.redirectedUrl == '/site/list'
    }
}
