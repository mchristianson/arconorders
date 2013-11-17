package com.arconorders.service

import grails.test.mixin.TestFor

@TestFor(ProductService)
class ProductServiceTest {
    void testParseProducts() {
        service.parseProducts()
    }
}
