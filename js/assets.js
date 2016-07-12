clicked_button_id="";
var icons = {
    header:"ui-icon-circle-plus",
    activeHeader:"ui-icon-circle-minus"
};
$(function() {
    $("#bar_code_id").focus();
});
$(".date").datepicker({
    nextText: "Next",
    prevText:"Prev",
    buttonText: "Pick Date",
    showOn: "both",
    navigationAsDateFormat: true,
    buttonImage: "/assettrack/js/calendar.gif"
});
$("#software_name").autocomplete({
    source: APPLICATION_URL + "SoftwareService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#software_id").val(ui.item.id);
        }
    }
});
$("#employee_name").autocomplete({
    source: APPLICATION_URL + "EmployeeService?withDevice=y",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#employee_id").val(ui.item.id);
        }
    }
});
$("#user_name").autocomplete({
    source: APPLICATION_URL + "UserService?format=json",
    minLength: 2,
    delay: 100,
    select: function( event, ui ) {
        if(ui.item){
            $("#user_id").val(ui.item.id);
        }
    }
});
$("#selection_id").change(function() {
		$("#type_id").val("");
		$("#type_name_id").val("");
    $("#form_id").submit();
});
jQuery(function ($) {
    var launcherClick = function(e)  {
            var openMenus   = $('.menuLinks.open'),
                menu        = $(e.target).siblings('.menuLinks');
            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);

            menu.removeClass('closed');
            menu.   addClass('open');
            e.stopPropagation();
        },
        documentClick = function(e) {
            var openMenus   = $('.menuLinks.open');

            openMenus.removeClass('open');
            setTimeout(function() { openMenus.addClass('closed'); }, 300);
        };
    $('.menuLauncher').click(launcherClick);
    $(document       ).click(documentClick);
});
$(document).on("click","button", function (event) {
	clicked_button_id = event.target.id;
});

function doRefresh(){
		document.getElementById("action2").value="Refresh";		
		document.getElementById("form_id").submit();				
}


