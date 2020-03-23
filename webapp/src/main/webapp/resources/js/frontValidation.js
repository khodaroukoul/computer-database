$(function () {
    $.datepicker.setDefaults({numberOfMonths: 3, dateFormat: 'dd/mm/yy'});
    $('#introduced').datepicker({
        onSelect: function (dateStr) {
            var minDate = $(this).datepicker('getDate');
            minDate.setDate(minDate.getDate() + 1);
            $('#discontinued').datepicker('option', 'minDate', minDate);
        }
    });
    $('#discontinued').datepicker({
        onSelect: function (dateStr) {
            var maxDate = $(this).datepicker('getDate');
            maxDate.setDate(maxDate.getDate() - 1);
            $('#introduced').datepicker('option', 'maxDate', maxDate);

        }
    });
});
