import static grails.util.Environment.*

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

//grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

def configLocation = System.getenv('ARCON_CONFIG_LOCATION') ?: "${userHome}/.grails"
//def configFilePath = "file:$configLocation/$appName-config.groovy"
def configFilePath = "file:$configLocation/$appName-config.groovy"
println "**** Externalized config file directory is $configFilePath"
grails.config.locations = [configFilePath]

grails.plugin.databasemigration.changelogFileName = "changelog.groovy"

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.app.context = "/"
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
og4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}
grails.config.defaults.locations = [KickstartResources]
// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.arconorders.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.arconorders.UserRole'
grails.plugins.springsecurity.authority.className = 'com.arconorders.Role'

bandbusername='rrvsairam@gmail.com'
bandbpassword='3261416B715FDEAF8059A6B65A64ED84C8847227C2C3882FF92AB040E3633285'
bandbcolumns='o.OrderID,o.OrderDate,o.OrderNotes,o.OrderStatus,o.PONum,o.ShipAddress1,o.ShipAddress2,o.ShipCity,o.ShipCompanyName,o.ShipCountry,o.ShipDate,o.ShipFaxNumber,o.ShipFirstName,o.ShipLastName,o.ShipPhoneNumber,o.ShippingMethodID,o.ShipPostalCode,o.ShipState,od.OrderDetailID,od.OptionID,od.Options,od.OptionIDs,od.ProductCode,od.ProductID,od.ProductName,od.ProductNote,od.ProductPrice,od.TotalPrice,od.Quantity,odo.OptionID,odo.OrderDetailID'
dubowurl='http://integration.dubowtextile.com/integration/orderintegrationservice.svc/test/xml/orders/new'
password='arconsolutions'
//dubowurl='http://integration.dubowtextile.com/integration/orderintegrationservice.svc/xml/orders/new'
//password='Bu113rFl1y'

userId='10064'
customerId='10064'
contactId='4554'
//userId='10033'
//password='0f9sdj3'
//customerId='10033'
//contactId='3336'

grails {
   mail {
     host = "smtp.gmail.com"
     port = 465
     username = "matt@arconinc.com"
     password = "yahoo88"
     props = ["mail.smtp.auth":"true",
              "mail.smtp.socketFactory.port":"465",
              "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
              "mail.smtp.socketFactory.fallback":"false"]
   }
}
grails.plugins.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugins.springsecurity.interceptUrlMap = [
   '/js/**':        ['IS_AUTHENTICATED_ANONYMOUSLY'],
   '/css/**':       ['IS_AUTHENTICATED_ANONYMOUSLY'],
   '/images/**':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
   '/*':            ['IS_AUTHENTICATED_ANONYMOUSLY'],
   '/login/**':     ['IS_AUTHENTICATED_ANONYMOUSLY'],
   '/logout/**':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
   '/arconOrder/**':        ['ROLE_ADMIN'],
   '/dubowSubmission/**':   ['ROLE_ADMIN'],
   '/orderError/**':        ['ROLE_ADMIN'],
   '/product/**':           ['ROLE_ADMIN'],
   '/site/**':              ['ROLE_ADMIN'],
   '/user/**':              ['ROLE_ADMIN'],
]
grails.plugin.springsecurity.dao.reflectionSaltSourceProperty = 'username'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.arconorders.User'
grails.plugin.cloudfoundry.showStackTrace = true
