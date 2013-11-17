package com.arconorders

class OrderError {

    String error
    String orderId
    Boolean processed = false
    Date dateCreated
    Date lastUpdated


    static constraints = {
        orderId(nullable: true, blank: true)
        error(nullable: false, blank: false)
    }
}
