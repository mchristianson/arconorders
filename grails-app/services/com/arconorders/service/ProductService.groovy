package com.arconorders.service

import groovy.xml.XmlUtil
import groovyx.net.http.RESTClient
import com.arconorders.Product

class ProductService {

    def url = 'http://integration.dubowtextile.com/integration/orderintegrationservice.svc/xml/products'
    String PROD_ID_VERIFY_URL = 'http://integration.dubowtextile.com/integration/orderintegrationservice.svc/xml/products/*/verify'

    def errors = []
    def dubowProducts

    def initializeProductListFromDubow() {
        RESTClient allProducts = new RESTClient(url)
        def response = allProducts.get(contentType: groovyx.net.http.ContentType.XML, headers: [Accept: 'text/xml'])
        this.dubowProducts = response.data
    }

    def verifyAllProducts() {
        Product.findAllByDubowProductIdIsNotNull().each { product ->
            verifyProduct(product)
        }
    }

    def verifyProduct(vendorCode, mill) {
        String urlWithParams = "${url}?partname=${ URLEncoder.encode(vendorCode)}"
        if (mill) urlWithParams += "&mill=${ URLEncoder.encode(mill)}"
        RESTClient productVerify = new RESTClient(urlWithParams)
        print "[code:${vendorCode},mill:${mill}]"
        def response = productVerify.get(contentType: groovyx.net.http.ContentType.XML, headers: [Accept: 'text/xml'])
        println " FOUND? $response.data"
        response.data.text()
    }

    def verifyProduct(Product product) {
        RESTClient verification = new RESTClient(PROD_ID_VERIFY_URL.replace('*', product.dubowProductId))
        def dubowProd = verification.get(contentType: groovyx.net.http.ContentType.XML, headers: [Accept: 'text/xml']).data
        product.dubowProductName = getNodeValue(dubowProd?.Name)
        product.dubowProductDesc = getNodeValue(dubowProd?.Description)
        product.save()
        return product
    }

    public boolean verifyProductExistsAtDubow(Product product) {
        verifyProduct(product)
        return (product.dubowProductName)
    }

    private String getNodeValue(node) {
        if (!node) return null
        String str = node.toString().trim()
        return str != '' ? str : null
    }
}
