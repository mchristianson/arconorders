dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    logSql = false
}
hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
//    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"

            dbCreate = "update" // one of 'create', 'create-drop','update'
            def jdbcUrl = "jdbc:mysql://localhost/arconorder?autoReconnect=true"
            url = jdbcUrl

            username = "arconorder"
            password = "!arconorder!"


            if( !jdbcUrl.contains('localhost') ) {
                println "******************************************************************"
                println "******************************************************************"
                println "******************************************************************"
                println "******************************************************************"
                println "********* WARNING NON LOCALHOST DEV DATASOURCE WARNING!   ********"
                println "******************************************************************"
                println "******************************************************************"
                println "******************************************************************"
                println "******************************************************************"
            }
        }
    }

    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            dialect = org.hibernate.dialect.MySQLInnoDBDialect
            driverClassName = 'com.mysql.jdbc.Driver'
            username = 'arconstoreuser'
            password = "!arconorder!"
            url = 'jdbc:mysql://localhost/arconorder?useUnicode=true&characterEncoding=utf8&autoReconnect=true'
            dbCreate = "update" // one of 'create', 'create-drop','update'
            //Reconnection Properties
            properties {
                maxActive = 30
                minIdle = 1
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true
                validationQuery = "SELECT 1"
                minEvictableIdleTimeMillis = (1000 * 60 * 5)
                timeBetweenEvictionRunsMillis = (1000 * 60 * 5)
            }
        }
    }
}
