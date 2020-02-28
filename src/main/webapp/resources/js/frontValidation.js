$(function() {
	$.datepicker.setDefaults({numberOfMonths: 3, dateFormat: 'dd/mm/yy'});
	$('#introduced').datepicker({onSelect: function(dateStr) {
		$('#discontinued').datepicker('option', 'minDate', $(this).datepicker('getDate'));
	}});
	$('#discontinued').datepicker({onSelect: function(dateStr) {

		$('#introduced').datepicker('option', 'maxDate', $(this).datepicker('getDate'));

	}});
});
