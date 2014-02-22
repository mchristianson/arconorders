package com.arconorders

class Product {
    static searchable = true

    String vendorCode
    String dubowProductId
    String dubowProductName
    String dubowProductDesc
    String code
    String name
    String mill
    String color
    String designNumber
    String colorWay
    String location
    String personalization
    String personalizationColor
    String procurementType
    String appliqueDesignNumber
    String appliqueColorWay
    String appliqueLocation

    static transients = ['selectValue']

    static belongsTo = [site: Site]

    static constraints = {
        vendorCode(nullable:false, blank:false)
        dubowProductId(nullable:false, blank:false, matches: "^[0-9]*\$")
        dubowProductName(nullable:true, blank:true)
        dubowProductDesc(nullable:true, blank:true)
        code(nullable:false, blank:false)
        name(nullable:false, blank:false)
        mill(nullable:true, blank:true)
        color(nullable:true, blank:true)
        designNumber(nullable:true, blank:true, matches: "^[0-9]*\$")
        colorWay(nullable:true, blank:true, matches: "^[0-9]*\$")
        location(nullable:true, blank:true)
        personalization(nullable:true, blank:true)
        personalizationColor(nullable:true, blank:true)
        procurementType(nullable:false, blank:false)
        appliqueDesignNumber(nullable:true, blank:true)
        appliqueColorWay(nullable:true, blank:true)
        appliqueLocation(nullable:true, blank:true)
    }

    private static integerValidator = {val, obj ->

    }
    String toString() {
        "$code: $name [$color]"
    }

    String toStringDetail() {
        """
        [vendorCode: $vendorCode],
        [dubowProductId: $dubowProductId]
        [code: $code]
        [mill: $mill],
        [color: $color],
        [designNumber: $designNumber],
        [colorWay: $colorWay]
        """
    }

    String getSelectValue() {
        "${code} - ${name}"
    }

}
