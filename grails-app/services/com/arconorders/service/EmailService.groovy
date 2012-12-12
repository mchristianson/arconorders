package com.arconorders.service

import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import javax.mail.*


class EmailService {
    def mailService

    def getEmail() {

        String gmailServer = "imap.gmail.com"
        int gmailPort = 993

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", gmailServer);
        props.setProperty("mail.imaps.port", gmailPort.toString());

//        Note that the "mail.store.protocol" property is "imaps" NOT plain "imap". JavaMail added SSL support to all protocols in version 1.3.2.

//        Once you have those properties set, you can create a Session and connect to the GMail IMAP server (the Store, in JavaMail terms) via IMAPS:
        def session = Session.getDefaultInstance(props, null)
        def store = session.getStore("imaps")
        store.connect(gmailServer, 'matt@arconinc.com', 'yahoo88')

//        With the connection to the Store, the next thing you probably want to do is see what's there. As I mentioned, GMail treats all your labels as IMAP folders. You can list the folders that are available, and then interact with the messages in whatever inboxStoreFolder you're interested in:

        store.defaultFolder.list().each { folder ->
            println folder.name
        }

        def inboxFolder = "INBOX"
        def inboxStoreFolder = store.getFolder(inboxFolder)
        inboxStoreFolder.open(Folder.READ_ONLY)

        def ordersFolder = "Volusion Orders"
        def ordersStoreFolder = store.getFolder(ordersFolder)
        ordersStoreFolder.open(Folder.READ_WRITE)

        def htmlMessageContent = []
        def messages = inboxStoreFolder.messages.findAll {it.subject.contains('Order number')}

        messages.each { msg ->
//            println "Subject: ${msg.subject}"
//            println "Sent on: ${msg.sentDate}"
//            println "From: ${msg.from}"
//            println "Content: ${msg.content}"
//            println "Headers: ${msg.allHeaders}"
            def multipart = msg.content
            def htmlPart = (multipart instanceof String) ? multipart : multipart.parts.find {it.type == 'TEXT/HTML; charset=ISO-8859-1'}?.content
//            println '++++++++++++++++++++++++++++++'
//            println htmlPart
//            println '++++++++++++++++++++++++++++++'
            htmlMessageContent << htmlPart
//            for (int x = 0; x < multipart.getCount(); x++) {
//
//                def bodyPart = multipart.getBodyPart(x);
//
//                String disposition = bodyPart.disposition
//
//                if (disposition != null && (disposition.equals(BodyPart.ATTACHMENT))) {
//                    println "Mail have some attachment : "
//
//                    def handler = bodyPart.dataHandler
//                    println "file name : " + handler.getName()
//                } else {
//                    println x
//                    println bodyPart.content
//                }
//            }
//            println "----------------------\n"
        }
//        ordersStoreFolder.appendMessages(messages)
        htmlMessageContent
    }

    public getEmailStub() {
        def text = new File("${SCH.servletContext.getRealPath ("/")}testOrder.txt").text
        [text.toString()]

    }

    public sendOrderEmail(List recipients, String orderStr, boolean isHtml = false) {
        mailService.sendMail {
           to recipients
           from "orders@arconinc.com"
           subject "Arcon Order Sent"
           if (isHtml) {
                html orderStr
            } else {
                body orderStr
            }
        }
    }

}
