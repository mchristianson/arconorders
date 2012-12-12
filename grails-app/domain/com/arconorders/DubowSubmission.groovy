package com.arconorders

class DubowSubmission {
    static searchable = true

    String requestXmlString
    String responseXmlString
    Boolean successful = false
    Date lastUpdated
    Date dateCreated
    List arconOrders

    static hasMany = [arconOrders: ArconOrder]

    static mapping = {
        requestXmlString sqlType: "text"
        responseXmlString sqlType: "text"
    }

    static constraints = {
        responseXmlString(nullable: true)
        successful(nullable: true)
    }

    String toString() {
        def orders = arconOrders.id.join(',')
        "Submission: ${lastUpdated}: ${successful?'success':'failed'} [${orders}]"
    }
}
