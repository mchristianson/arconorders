package com.arconorders.service

import groovy.xml.XmlUtil
import groovyx.net.http.RESTClient

import static groovy.xml.XmlUtil.serialize
import static groovyx.net.http.ContentType.*

import com.arconorders.OrderError
import com.arconorders.DubowSubmission

class OrderProcessingService {
    def productService
    def grailsApplication
    def orderService
    def emailService

    def errors = []
    def messages = []

    def processOrders() {
        orderService.errors = []
        orderService.messages = []
        def orders = orderService.parseOrders()
        if (orderService.errors) errors << orderService.errors
        if (orderService.messages) messages << orderService.messages
        if (orders) {
            convertAndSendOrders(orders)
        }
        orders
    }

    def convertAndSendOrders(orders) {
        if (orders.orderDetails.product.flatten().any {it != null}) {
            def orderString = transformOrders(orders)
            DubowSubmission submission = new DubowSubmission(requestXmlString: orderString, arconOrders: orders)

            if (orderString.contains('LineItems')) {
                messages << "sending it!!!!"
                println orderString

                if (sendOrders(submission)) {
                    orders.each {
                        it.success = true
                        it.save()
                    }
                }
            } else {
                errors << "Error finding products. Order not sent."
            }
            submission.save(failOnError: true)
        } else {
            errors << "Nothing to send to Dubow."
        }
    }

    def transformOrders(arconOrders) {
        def sw = new StringWriter()
        def now = new Date()

        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.OrderIntegrationRequest(xmlns: 'http://schemas.datacontract.org/2004/07/Dms.Domain.Dtos') {
            Authorization {
                TransactionID(uniqueID)
                UserID(grailsApplication.config.userId)
                Password(grailsApplication.config.password)
            }
            Orders() {
                IntegrationOrder() {
                    TimeStamp(now.format('MM/dd/yyyy HH:mm:ss'))
                    CustomerID(grailsApplication.config.customerId)
                    ContactID(grailsApplication.config.contactId)
                    PoNumber("PO#BB-${getUniqueID(5)}")
                    Manifests {
                        arconOrders.each { order ->
                            if (order.orderDetails.product.any{it} ) {
                                IntegrationManifest {
                                    DateToShip((now + 7).format('MM/dd/yyyy'))
                                    def fullAddress = (order.address1 + order.address2).toLowerCase()
                                    def shippingMethod = (fullAddress.contains('box')) ? 'USPS' : (order.state == 'MN') ? 'SPEDE' : 'UPSG'
                                    ShipMethodAbbreviation(shippingMethod)
                                    ShipToAddress() {
                                        Address1(order.firstName + ' ' + order.lastName)
                                        Address2(order.address2)
                                        Address3(order.address1)
                                        City(order.city)
                                        State(order.state)
                                        Zip(order.postalCode)
                                    }
                                    Notes("Volusion Order #: ${order.orderID}")
                                     LineItems() {
                                        order.orderDetails.each { lineItem ->
                                            def product = lineItem.product
                                            if (!product) {
                                                new OrderError(orderId: order?.orderID, error: "SPREADSHEET PRODUCT NOT FOUND! ${lineItem.productCode} - ${lineItem.getOption('Color')}").save()
                                                errors << "SPREADSHEET PRODUCT NOT FOUND! ${lineItem.productCode} - ${lineItem.getOption('Color')}"
                                            } else if (!productService.verifyProduct(product.vendorCode, product.mill)) {
                                                new OrderError(orderId: order?.orderID, error: "DUBOW PRODUCT NOT FOUND! code:${product.vendorCode}, mil:${product.mill}").save()
                                                errors << "DUBOW PRODUCT NOT FOUND! code:${product.vendorCode}, mil:${product.mill}"
                                            } else if (!lineItem.quantity) {
                                                errors << "Zero quantity. Must be an update."
                                            } else {
                                                Notes(order.comments)
                                                IntegrationLineItem() {
                                                    Name("LINEITEM_${lineItem.id}")
                                                    IntegrationProduct() {
                                                        Color(lineItem.getOption('Color'))
                                                        Description(product.name)
                                                        //                                                ProductTypeName('') ///???
    //                                                    ProductID(product.dubowProductId)
                                                        if (product.mill) {
                                                            VendorProductName(product.vendorCode)
                                                            Mill(product.mill)
                                                        } else {
                                                            ProductName(product.vendorCode)
                                                        }
                                                        ProcurementType(product.procurementType)
                                                    }
                                                    LineItemSizes() {
                                                        IntegrationLineItemSize() {
                                                            Quantity(lineItem.quantity)
                                                            SizeIndex(lineItem.sizeIndex)
                                                            SizeAsString(lineItem.getOption('Size') ?: 'NoSize')
                                                        }
                                                    }
                                                    if (product.designNumber || product.appliqueDesignNumber) {
                                                        Designs() {
                                                            if (product.designNumber) {
                                                                IntegrationDesign() {
                                                                    DesignID(product.designNumber)
                                                                    DesignTypeName('Embroidery')
                                                                    // Notes('These are some design Notes.')  /// ????
                                                                    if (lineItem.getOption('Personalization')) {
                                                                        Customizations() {
                                                                            DesignCustomization() {
                                                                                Position(product.personalization)
                                                                                Color(product.personalizationColor)
                                                                                TextLines() {
                                                                                    TextLine() {
                                                                                        LineNumber(1)
                                                                                        Text(lineItem.getOption('Personalization'))
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    IntegrationColorway() {
                                                                        GarmentLocationName(product.location)
                                                                        ColorwayID(product.colorWay)
                                                                    }
                                                                }
                                                            }
                                                            if (product.appliqueDesignNumber && lineItem.getOption('Add Full Back Logo?')) {
                                                                IntegrationDesign() {
                                                                    DesignID(product.appliqueDesignNumber)
                                                                    DesignTypeName('Embroidery')
                                                                    IntegrationColorway() {
                                                                        GarmentLocationName(product.appliqueLocation)
                                                                        ColorwayID(product.appliqueColorWay)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } // for each line item
                                    }
                                } // for each order.
                            } // if has an order
                        }
                    }
                }
            }
        } // Integration request.

        return sw.toString()
    }

    DubowSubmission sendOrders(DubowSubmission dubowSubmission) {
        String statusStr = ""
//        dubowSubmission.responseXmlString = 'It worked!'
//        return dubowSubmission
        statusStr += "Sending orders..\n"
        statusStr += dubowSubmission.arconOrders
        statusStr += XmlUtil.serialize(dubowSubmission.requestXmlString)
        statusStr += "\n"
        RESTClient orders = new RESTClient(grailsApplication.config.dubowurl)
        statusStr += grailsApplication.config.dubowurl
        statusStr += "\n"
        def response = orders.post(body: '<?xml version="1.0" encoding="utf-8"?>\r\n' + dubowSubmission.requestXmlString, contentType: groovyx.net.http.ContentType.XML, headers: [Accept: 'text/xml'])

        statusStr += "RESPONSE: $response"
        statusStr += "\n"
        dubowSubmission.responseXmlString = serialize(response.data)
        statusStr += "\n"
        if (response.status == 200) {
            statusStr += "Received response from Dubow."
            if (response.data.ResponseSummary.IsSuccess.text() == "true") {
                dubowSubmission.successful = true
                statusStr +=  "Succesfully sent order to Dubow"
            } else {
                new OrderError(error:"Error sending orders to Dubow")
            }
            statusStr +=  serialize(response.data)
        } else {
            statusStr +=  "Error sending orders to Dubow"
        }
        statusStr += "\n"
        statusStr +=  "Done.."
        println statusStr
        def siteList = dubowSubmission.arconOrders.each { order ->
            emailService.sendOrderEmail(order.site.users.email.flatten().unique(), order.emailOrderString.toString(), true)
        }
        emailService.sendOrderEmail(['christianson.matt@gmail.com'], statusStr)
        dubowSubmission
    }

    def getUniqueID(int length = 10) {
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        int maxIndex = validChars.length()
        def rnd = new Random()
        (1..length).sum { validChars[rnd.nextInt(maxIndex)]}
    }

    def getDubowProducts(config) {
        RESTClient orders = new RESTClient("http://integration.dubowtextile.com/integration/orderintegrationservice.svc/xml/products/get")
        println grailsApplication.config.dubowurl
        def response = orders.post(body: '<?xml version="1.0" encoding="utf-8"?>', contentType: groovyx.net.http.ContentType.XML, headers: [Accept: 'text/xml'])
        println response

    }
}
