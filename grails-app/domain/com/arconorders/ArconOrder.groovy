package com.arconorders

class ArconOrder {
    static searchable = true

    String firstName
    String lastName
    String orderID
    String orderDate
    String customerID
    // shipping address
    String address1
    String address2
    String city
    String state
    String postalCode
    String country
    String comments
    Site site
    Boolean success = false
    Date dateCreated
    Date lastUpdated

    static belongsTo = DubowSubmission
    static transients = ['emailOrderString']

    static mapping = {
        comments sqlType: "varchar(5000)"
    }

    static hasMany = [orderDetails: OrderDetail, dubowSubmissions: DubowSubmission]

    static constraints = {
        firstName(nullable: true)
        lastName(nullable: true)
        orderID(nullable: true)
        orderDate(nullable: true)
        customerID(nullable: true)
        address1(nullable: true)
        address2(nullable: true)
        city(nullable: true)
        state(nullable: true)
        postalCode(nullable: true)
        country(nullable: true)
        comments(nullable: true)
        site(nullable: false)
        orderDetails(nullable: false)
        dubowSubmissions(nullable: true)
    }

    String toString() {
       "${orderID}: ${orderDate}"
    }

    String toStringDetail() {
        """
        OrderID: [${orderID}]<br/>
        CustomerID: [${customerID}]<br/>
        OrderDate: [${orderDate}]<br/>
        address1: [${address1}]<br/>
        address2: [${address2}]<br/>
        city: [${city}]<br/>
        state: [${state}]<br/>
        postalcode: [${postalCode}]<br/>
        country: [${country}]<br/>
        comments: [${comments}]<br/>
        details: [${orderDetails}]<br/>
        success?: [${success}]<br/>
        """
    }

    String getEmailOrderString() {
        """
        <style>
        * {font: 100%/1.5 "myriad pro", helvetica, arial, sans-serif;}
        h1 {font-size:130%;font-weight:bold;}
        table {border-collapse: separate; border-spacing: 0;}
        table th {background-color:#ddd;}
        table td {border: 1px solid #ddd;padding: 5px;}
        .nowrap {white-space: nowrap;}
        </style>
        <style>

        </style>
        <h1>Order Processed for ${site}</h1>
        <table border="1"><thead>
            <tr><th>OrderID</th><th>Date</th><th>Customer</th><th>Details</th></tr>
            </thead>
            <tbody>
            <tr>
            <td>${orderID}</td><td>${orderDate}</td><td class="nowrap">
                ${customerID} - ${firstName} ${lastName}<br/>
                ${address1} ${address2}<br/>
                ${address2}
                ${city}, ${state} ${postalCode}</td>
            <td>${orderDetails.htmlFormattedString.join("<br/>")}</td>
            </tr>
            </tbody>
            </table>
        """
    }
    
}
