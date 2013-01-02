var ABSOLUTE_PATH = (location.href.indexOf("bhbo") > -1) ? "/bhbo/" : "/"
$(function() {
    $(".jquery_datepicker").datepicker({
        showOn: 'button',
        buttonImage: ABSOLUTE_PATH+'images/calendar-icon.gif',
        buttonImageOnly: true
    });
    $(".jquery_datepicker_year_list").datepicker({
        changeMonth: true,
        changeYear: true,
        showOn: 'button',
        buttonImage: ABSOLUTE_PATH+'images/calendar-icon.gif',
        buttonImageOnly: true
    });
});
