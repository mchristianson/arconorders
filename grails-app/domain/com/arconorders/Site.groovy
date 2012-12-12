package com.arconorders

class Site {

    String name
    String baseUrl

    static hasMany = [
        products: Product,
        users: User
    ]

    static constraints = {
        name(nullable: false, blank: false)
        baseUrl(nullable: false, blank: false)
    }

    String toString() {
        name
    }
}
