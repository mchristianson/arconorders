package com.arconorders

class OrderDetail {

    String productName
    String productCode
    Integer quantity
    String productOptions
    Product product
    ArconOrder arconOrder

//    static belongsTo = ArconOrder

    static transients = ['dubowProductId', 'sizeIndex', 'dubowSizeIndexArr', 'attachProduct','htmlFormattedString']

    static constraints = {
        productName(nullable: true)
        productCode(nullable: true)
        quantity(nullable: true)
        productOptions(nullable: true)
        product(nullable: true)
        arconOrder(nullable: false)
    }

    String toString() {
        """
            Product Name: [$productName]<br/>
            Product Code: [$productCode]<br/>
            Quantity: [$quantity]<br/>
            Options: [$productOptions]<br/>
            ProductObj: [$product]<br/>
          """
          }

    String getOption(optionName) {
        def optionsMap = [:]
        this.productOptions.tokenize('[').each {
            def arr = it.split(':')
            optionsMap[arr[0].toLowerCase()] = arr[1].minus(']')
        }

        optionsMap[optionName.toLowerCase()]
    }

    String getDubowProductId() {
        productCode.split('-')[0]
    }

    def dubowSizeIndexArr = ['S': 1,
            'M': 2,
            'L': 3,
            'XL': 4,
            '2XL': 5,
            '3XL': 6,
            '4XL': 7]

    Integer getSizeIndex() {
        dubowSizeIndexArr[getOption('Size')] ?: 0
    }

    def attachProduct() {
        this.product = Product.findByCodeAndColor(productCode, getOption('color'))

    }
    
    def getHtmlFormattedString() {
        "[Code:${productCode}] [Quantity: ${quantity}] [Options: ${productOptions}]"
    }
}
