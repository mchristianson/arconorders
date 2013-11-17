package com.arconorders.exception

class OrderProcessingException extends RuntimeException {
    String orderId

    OrderProcessingException() {
        super()
    }
    OrderProcessingException(String orderId, String message) {
        super("${message} For Volusion OrderId: [${orderId}]")
        this.orderId = orderId
    }
}
