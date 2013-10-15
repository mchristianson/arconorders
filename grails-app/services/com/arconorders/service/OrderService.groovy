package com.arconorders.service

import com.arconorders.OrderDetail
import com.arconorders.ArconOrder
import com.arconorders.Product
import com.arconorders.Site
import com.arconorders.OrderError
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer

import static groovy.xml.XmlUtil.serialize


class OrderService {

    def emailService
    def messages = []
    def errors = []

    public parseOrders() {
//        def emailMessages = emailService.getEmailStub()
        def emailMessages = emailService.getEmail()

        def orders = []
        emailMessages.findAll {it.contains('#xmlstart#')} .each { message ->

            def xmlstart = message.indexOf('#xmlstart#')+10
            def xmlend = message.indexOf('#xmlend#')-1
            def orderString = message[xmlstart..xmlend]
            // Clean any messy HTML
            def cleaner = new HtmlCleaner()
            def node = cleaner.clean(orderString)

            // Convert from HTML to XML
            def props = cleaner.getProperties()
            def serializer = new SimpleXmlSerializer(props)
            def xml = serializer.getXmlAsString(node)

            // Parse the XML into a document we can work with
            def orderXml = new XmlSlurper(false,false).parseText(xml).body.xml
            println serialize(orderXml)
            Site site = Site.findByBaseUrl(orderXml.storename.text())
            ArconOrder order = ArconOrder.findByOrderIDAndSite(orderXml.orderid.text(), site)
            if (!order) {

                order = new ArconOrder('orderID': orderXml.orderid.text(),
                        site: site,
                        customerID: orderXml.customerid.text(),
                        orderDate: orderXml.orderdate.text(),
                        firstName: orderXml.firstname.text(),
                        lastName: orderXml.lastname.text(),
                        address1: orderXml.address1.text(),
                        address2: orderXml.address2.text() ?: "",
                        city: orderXml.city.text(),
                        state: orderXml.state.text(),
                        postalCode: orderXml.postalcode.text(),
                        country: orderXml.country.text(),
                        comments: orderXml.comments.text())

                orderXml.details.table.tbody.tr.each { details ->
                    def productCode = details.td[0].text()
                    println "productCode: [$productCode]"
                    if (productCode.trim() != "\u00A0" && productCode != 'Code' && !productCode.contains('DSC') && !productCode.contains('FLB-8000-DT')) {

                        def detailsText = details.td[1].text().replace("\u00A0", '')
                        def addition = detailsText[0] == '[' ? 1 : 0
                        def productName = detailsText[addition..detailsText.indexOf('[',addition)-1]
                        def productOptions = detailsText[detailsText.indexOf('[',addition)..-1-addition]
                        println "productName: $productName"
                        println "productOptions: $productOptions"

                        println productOptions
                        OrderDetail orderDetail = new OrderDetail(
                                productCode: productCode,
                                quantity: details.td[2].text() as Integer,
                                productName: productName,
                                productOptions: productOptions,
                                arconOrder: order
                        )
                        Product product = Product.findByCodeAndColor(productCode, orderDetail.getOption('color'))
                        if (!product) {
                            def orderError = new OrderError(orderId: orderXml.orderid.text(), error: "Cannot find product for ${productCode} / ${productOptions}").save()
                            errors << orderError
                        } else {
                            orderDetail.product = product
                        }
                        order.addToOrderDetails(orderDetail)
                        order.save()
                        orderDetail.arconOrder = order
                        println orderDetail.arconOrder
                        orderDetail.save()
                    }
                }
                order.save(failOnError: true)
                orders << order
            } else {
                messages << "order ${orderXml.orderid.text()} already processed."
            }
        }
        orders
    }
}
