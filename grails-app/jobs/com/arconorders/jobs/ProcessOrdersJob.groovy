package com.arconorders.jobs

class ProcessOrdersJob {
    def orderProcessingService
    static triggers = {
        cron name: 'processOrdersTrigger', cronExpression: "0 10,16 * * * ?"
    }
    def group = "Orders"

    def execute() {
        orderProcessingService.processOrders()
    }
}
