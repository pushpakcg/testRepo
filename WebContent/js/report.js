$(document).ready(function(){
	$('#resultTable').DataTable({
		dom: 'Bfrtip',
		"bPaginate": true,
		"pageLength": 15,
    	"searching": true,
    	buttons: [
          'excel'
        ]
	});
	var dt = new Date();
    $("#timeSpan").html(dt.toLocaleTimeString());
    $("#dateSpan").html(dt.toLocaleDateString());
	$('.dt-buttons').css({paddingBottom: '2px'});
	
	$('#executeBtn').click(function(){
		if(validateDates()){
			$('#loading').show();
			$('#searchForm').submit();
		}
	 
    });
	
	$('#excelBtn').click(function(){
		if(validateDates()){
			$('#exportExcel').val(true);
			$('#searchForm').submit();
			$('#fader').css('display', 'block');
			deleteCookie('downloadStarted');
			setCookie('downloadStarted', 0, 100); //Expiration could be anything... As long as we reset the value
			setTimeout(checkDownloadCookie, 1000); //Initiate the loop to check the cookie.
			$('#exportExcel').val(false);
		}
	});

	$('.buttons-html5').hide();
	
	var companiesJsonString = $('#companyJson').val();
	var json = JSON.parse(companiesJsonString);
	
	var companiesGroup = $('#companyGrpKey').val();
	var companiesGroupJson = JSON.parse(companiesGroup);
	
	//Load dropdowns on page load
	
	var selectedCompanyGroup = $("#companyGroupCode").val().trim();
	var selectedCompany = $("#companyNameCode").val().trim();
	
	//Populate Company Group dropdown
	$.each(companiesGroupJson , function(i, val) {
		if(val == selectedCompanyGroup){
			//Retain the value of already selected company group by making it selected 
			$('#compGrp').append('<option value="' + val + '" selected="selected">' + val + '</option>');
		}else{
			$('#compGrp').append('<option value="' + val + '">' + val + '</option>');
		}
	});
	
	//Populate company dropdown
	//Add default value to company dropdown keep it selected.
	if($('#companyCode').val() == null){
		$('#companyCode').append('<option value="" selected="selected">SELECT</option>');
	}
	
	if(selectedCompanyGroup == ""){
		//No company group is selected. Populate company drop down with values of ALL company group.  
		$.each(json, function (i, v) {
			$.each(v, function (idx, val) {
				if(val.companyGroupCode == "ALL"){
					if(val.companyCode == selectedCompany){
						$('#companyCode').append('<option value="' + val.companyCode + '" selected="selected">' + val.companyCode +' - '+val.companyName + '</option>');
					}else{
						$('#companyCode').append('<option value="' + val.companyCode + '">' + val.companyCode +' - '+val.companyName + '</option>');
					}
				}
			});
		});
	}
	else{
		//Populate company dropdown with values of selected company group
		$.each(json, function (i, v) {
			$.each(v, function (idx, val) {
				if(val.companyGroupCode == selectedCompanyGroup){
					if(val.companyCode == selectedCompany){
						//Retain value of already selected company by making it selected
						$('#companyCode').append('<option value="' + val.companyCode + '" selected="selected">' + val.companyCode +' - '+val.companyName + '</option>');
					}else{
						$('#companyCode').append('<option value="' + val.companyCode + '">' + val.companyCode +' - '+val.companyName + '</option>');
					}
				}
			});
		});
	}
	
	//Trigger for the plugin to update/render the changes on screen.
	$('#companyCode').trigger('chosen:updated');
	
	//End of Load dropdowns on page load
		
	//Load Company names based on company Group
	$('#compGrp').on('change', function(){
		$('#companyCode').empty();
		$('#companyCode').append('<option value="" selected="selected">SELECT</option>');
		$('#companyNameCode').val('');
		$('#companyCode').val('');
		var cGroup = $('#compGrp').val();	
		$('#companyGroupCode').val(cGroup);	
		if(cGroup.trim() != ""){
			$.each(json, function (i, v) {
  				$.each(v, function (idx, val) {
					if(val.companyGroupCode == cGroup){
						$('#companyCode').append('<option value="' + val.companyCode + '">' + val.companyCode +' - '+val.companyName + '</option>');
					}
  				});
			});
		}else{
			$.each(json, function (i, v) {
				$.each(v, function (idx, val) {
					if(val.companyGroupCode == "ALL"){
						$('#companyCode').append('<option value="' + val.companyCode + '">' + val.companyCode +' - '+val.companyName + '</option>');
					}
				});
			});
		}
		$('#companyCode').trigger('chosen:updated');
	});
	
	//Save company code to hidden variable
	$('#companyCode').on('change', function(){
		$('#companyNameCode').val('');
		var company = $('#companyCode').val();
		$('#companyNameCode').val(company);
	});
	
	//Date pickers
	$("#installedFrom").datepicker({autoClose: true});
    $("#installedTo").datepicker({autoClose: true});
    $("#creationFrom").datepicker({autoClose: true});
    $("#creationTo").datepicker({autoClose: true});
     
    $("#submitBtn").button();
});

function validateDates(){
	var valid = true;
	$('.calImg').each(function(index, item) {
		 
	    if( $(item).val() != '' && !isValidDate($(item).val()) ){
	    	//Not valid date value.
	    	//$(item).css("background-color", "red");
	    	valid = false;
	    }
	});
	if(!valid){
		alert('Invalid dates entered');
	}
	return valid;
}

//--------------------------------------------------------------------------
//This function validates the date for MM/DD/YYYY format. 
//--------------------------------------------------------------------------
function isValidDate(dateStr) {

	// Checks for the following valid date formats:
	// MM/DD/YYYY
	// Also separates date into month, day, and year variables
	var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	
	var matchArray = dateStr.match(datePat); // is the format ok?
	if (matchArray == null) {
	//alert("Date must be in MM/DD/YYYY format")
	return false;
	}
	
	month = matchArray[1]; // parse date into variables
	day = matchArray[3];
	year = matchArray[4];
	if (month < 1 || month > 12) { // check month range
	//alert("Month must be between 1 and 12");
	return false;
	}
	if (day < 1 || day > 31) {
	//alert("Day must be between 1 and 31");
	return false;
	}
	if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	//alert("Month "+month+" doesn't have 31 days!")
	return false;
	}
	if (month == 2) { // check for february 29th
	var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	if (day>29 || (day==29 && !isleap)) {
	 //alert("February " + year + " doesn't have " + day + " days!");
	 return false;
	  }
	}
	return true;  // date is valid
}

var setCookie = function(name, value, expiracy) {
	  var exdate = new Date();
	  exdate.setTime(exdate.getTime() + expiracy * 1000);
	  var c_value = escape(value) + ((expiracy == null) ? "" : "; expires=" + exdate.toUTCString());
	  document.cookie = name + "=" + c_value + '; path=/';
};


var getCookie = function(name) {
  var i, x, y, ARRcookies = document.cookie.split(";");
  for (i = 0; i < ARRcookies.length; i++) {
    x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
    y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
    x = x.replace(/^\s+|\s+$/g, "");
    if (x == name) {
      return y ? decodeURI(unescape(y.replace(/\+/g, ' '))) : y; //;//unescape(decodeURI(y));
    }
  }
};

var deleteCookie = function(name) {
    document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
};

/*var getCookie = function(name) {
  var i, x, y, ARRcookies = document.cookie.split(";");
  var done = 1;
  for (i = 0; i < ARRcookies.length; i++) {
    x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
    y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
    x = x.replace(/^\s+|\s+$/g, "");
    if (x == name) {
      done = 0;
    }
  }
  return done;
};*/

var downloadTimeout;
var checkDownloadCookie = function() {
  if (getCookie("downloadStarted") == 1) {
    setCookie("downloadStarted", "false", 100); //Expiration could be anything... As long as we reset the value
    $('#fader').css('display', 'none');
  } else {
    downloadTimeout = setTimeout(checkDownloadCookie, 1000); //Re-run this function in 1 second.
  }
};


$(window).load(function() {
	$(".chosen").chosen();
 	$('#loading').hide();
});
