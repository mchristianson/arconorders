package com.arconorders.service

import com.arconorders.OrderDetail
import com.arconorders.ArconOrder
import com.arconorders.Product
import com.arconorders.Site
import com.arconorders.OrderError
import com.arconorders.exception.OrderProcessingException
import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer

import static groovy.xml.XmlUtil.serialize


class OrderService {

    def emailService

    public List<ArconOrder> parseOrders() throws OrderProcessingException {
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

            Site site = Site.findByBaseUrl(orderXml.storename.text())
            String orderId = orderXml.orderid?.text()?.toString()
            if (!site) throw new OrderProcessingException(orderId, "Cannot find site [${orderXml.storename.text()}]. Arcon Order NOT created.")
            ArconOrder existingOrder = ArconOrder.findByOrderIDAndSite(orderXml.orderid.text(), site)

            if (!existingOrder) {
                println serialize(orderXml)
                if (!orderXml.details.table.tbody.tr) throw new OrderProcessingException(orderId, "Cannot find order details in ${orderXml}. Arcon Order NOT created.")

                List<OrderDetail> orderDetails = []
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

                        OrderDetail orderDetail = new OrderDetail(
                                productCode: productCode,
                                quantity: details.td[2].text() as Integer,
                                productName: productName,
                                productOptions: productOptions
                        )

                        Product product = Product.findByCodeAndColor(productCode, orderDetail.getOption('color'))
                        if (!product)  throw new OrderProcessingException(orderId, "Error: Cannot find product for [code:${productCode}, color:${orderDetail.getOption('color')}]. Arcon Order NOT created.")

                        orderDetail.product = product
                        orderDetails << orderDetail
                    }
                }
                ArconOrder order = new ArconOrder('orderID': orderXml.orderid.text(),
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
                order.save(failOnError: true)
                orderDetails.each { orderDetail ->
                    orderDetail.arconOrder = order
                    orderDetail.save(failOnError: true)
                    order.addToOrderDetails(orderDetail)
                }
                order.save(failOnError: true)
                orders << order
            } else {
                println "Order ${existingOrder} already processed."
            }
        }
        orders
    }
}
