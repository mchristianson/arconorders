package com.arconorders.jobs

import com.arconorders.OrderError
import com.arconorders.exception.OrderProcessingException

class ProcessOrdersJob {
    def orderProcessingService
    static triggers = {
        cron name: 'processOrdersTrigger', cronExpression: "0 10,16 * * * ?"
    }
    def group = "Orders"

    def execute() {
        try {
            orderProcessingService.processOrders()
        } catch (OrderProcessingException e) {
            new OrderError(orderId: e.orderId, error: e.message).save()
        }
    }
}
