package com.arconorders.service
import groovyx.net.http.RESTClient
import com.arconorders.Product

class ProductService {

    def url = 'http://integration.dubowtextile.com/integration/orderintegrationservice.svc/xml/products/verify'

    def errors = []

    def verifyProduct(vendorCode, mill) {
        String urlWithParams = "${url}?partname=${ URLEncoder.encode(vendorCode)}"
        if (mill) urlWithParams += "&mill=${ URLEncoder.encode(mill)}"
        RESTClient productVerify = new RESTClient(urlWithParams)
        print "[code:${vendorCode},mill:${mill}]"
        def response = productVerify.get(contentType: groovyx.net.http.ContentType.XML, headers: [Accept: 'text/xml'])
        println " FOUND? $response.data"
        response.data.text()
    }
    
    def verifyAllProducts() {
        def results = []
        def products = Product.executeQuery( "select distinct p.vendorCode, p.mill from Product p")
        println products
        products.each { 
            results << [product: "${it[0]}:${it[1]}", found: verifyProduct(it[0], it[1])]
        }
        results
    }
}
