package com.arconorders.service

import com.arconorders.ArconOrder
import com.arconorders.exception.OrderProcessingException
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

    public List<ArconOrder> processOrders() throws OrderProcessingException {
        List<ArconOrder> orders = orderService.parseOrders()
        if (orders) {
            convertAndSendOrders(orders)
        }
        orders
    }

    public DubowSubmission convertAndSendOrders(List<ArconOrder> orders) throws OrderProcessingException {
        if (!orders.orderDetails.product.flatten().any {it != null}) throw new OrderProcessingException("Error: Nothing to send to Dubow.")
        def orderString = transformOrders(orders)
        DubowSubmission submission = new DubowSubmission(requestXmlString: orderString, arconOrders: orders)

        if (!orderString.contains('LineItems')) throw new OrderProcessingException("Error finding products. Order not sent.")
        println orderString

        if (sendOrders(submission)) {
            orders.each {
                it.success = true
                it.save()
            }
        }
        submission.save(failOnError: true)
    }

    public String transformOrders(List<ArconOrder> arconOrders) throws OrderProcessingException{
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
                                    def shippingMethod = getShippingMethod(fullAddress, order.state)
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

                                            if (!product) throw new OrderProcessingException(order?.orderID?.toString(), "Product Not Found! ${lineItem.productCode} - ${lineItem.getOption('Color')}")

                                            if (!productService.verifyProductExistsAtDubow(product)) throw new OrderProcessingException(order?.orderID.toString(), "Could not verify product ${lineItem.productCode} - ${lineItem.getOption('Color')} ID: ${lineItem.dubowProductId}")

                                            if (!lineItem.quantity)  throw new OrderProcessingException(order?.orderID.toString(), "Zero quantity. Must be an update")

                                            if (!product.validate()) {
                                                String errorMessage = product.errors.allErrors.collect { "[${it.field}: ${it.defaultMessage}] "}
                                                throw new OrderProcessingException(order?.orderID.toString(), "Product not valid: ${errorMessage}")
                                            }

                                            if (!lineItem.getOption('Color')) throw new OrderProcessingException(order?.orderID.toString(), "Color missing for product: ${product}")

                                            Notes(order.comments)
                                            IntegrationLineItem() {
                                                Name("LINEITEM_${lineItem.id}")
                                                IntegrationProduct() {
                                                    Color(lineItem.getOption('Color'))
                                                    Description(product.name)
//                                                      ProductTypeName('') ///???
                                                    ProductID(product.dubowProductId)
//                                                        if (product.mill) {
//                                                            VendorProductName(product.vendorCode)
//                                                            Mill(product.mill)
//                                                        } else {
//                                                            ProductName(product.vendorCode)
//                                                        }
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
                                                                //Name()
                                                                DesignTypeName('Embroidery')
                                                                DesignID(product.designNumber)
                                                                // Notes('These are some design Notes.')  /// ????
                                                                if (lineItem.getOption('Personalization')) {
                                                                    Customizations() {
                                                                        DesignCustomization() {
                                                                            //Font()
                                                                            Color(product.personalizationColor)
                                                                            Position(product.personalization)
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
                                                                DesignTypeName('Embroidery')
                                                                DesignID(product.appliqueDesignNumber)
                                                                IntegrationColorway() {
                                                                    GarmentLocationName(product.appliqueLocation)
                                                                    ColorwayID(product.appliqueColorWay)
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
                throw new OrderProcessingException(orderId: dubowSubmission.arconOrders.orderId.toString(), error: "Error sending to Dubow: [${response.data.ResponseSummary.Errors}]")
            }
            statusStr +=  serialize(response.data)
        } else {
            throw new OrderProcessingException(orderId: dubowSubmission.arconOrders.orderId.toString(), error: "Error sending to Dubow: [unknown]")
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

    private String getShippingMethod(fullAddress, state) {
        (fullAddress.contains('box')) ? 'USPS' : (['MN', 'ND', 'SD', 'NE', 'IA', 'IL', 'WI'].contains(state)) ? 'SPEDE' : 'UPSG'
    }
}
