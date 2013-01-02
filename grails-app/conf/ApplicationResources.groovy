modules = {
    application {
        resource url:'js/application.js'
    }
    datatables {
        dependsOn "bootstrap"
        resource url: '/js/jquery.dataTables.js'
        resource url: '/js/jquery.jeditable.js'
        resource url: '/js/jquery.dataTables.editable.js'
        resource url: '/js/jquery.validate.js'
        resource url: '/js/twitter.datatables.js'
        resource url: '/css/demo_table_jui.css'
    }
}