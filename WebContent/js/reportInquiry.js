/*$(document).ready(function(){
	//Populate company dropdown
	//Add default value to company dropdown keep it selected.
	var selectedCompany = $("#companyNameCode").val();
	var iCode =$("#instanceCode").val();
	if($('#companyCode').val() == null){
		$('#companyCode').append('<option value="select" selected disabled hidden>SELECT COMPANY</option>');
	}
	var companiesJson = $('#companyJson').val();
	var companies = JSON.parse(companiesJson)
	var environment = $('#environment').val();
	document.getElementById("companyCode").style.backgroundColor = "#fff59d";
	for (val in companies){
		if(companies[val].companyCode == selectedCompany){
			document.getElementById("companyCode").style.backgroundColor = "#ffffff";
			$('#companyCode').append('<option style="background-color: #fff59d;"  value="' + companies[val].companyCode + '" selected="selected">' + companies[val].companyCode +' - '+companies[val].companyName + '</option>');
			$.ajax({
				type: "GET",
				dataType: "json",
				url: "getAssetMethod",
				data: {companyId:selectedCompany,
					env:environment,
					instanceCode:iCode}
			}).done(function(reply) {
				if($('#assetNum').val() == null){
					$('#assetNum').append('<option value="select" selected disabled hidden>SELECT ASSET NUMBER</option>');
				}
				fillAssetNumber(reply);
			});
		}else{
			$('#companyCode').append('<option style="background-color: #ffffff;" value="' + companies[val].companyCode + '">' + companies[val].companyCode +' - '+companies[val].companyName + '</option>');
		}
	}
	
	$('#executeBtn').click(function(){
		if(validateCompanyAsset()){
			//$('#loading').show();
			$('#searchForm').submit();
		}
	 
    });
	
	$('#acctingBtn').click(function(){
		$('#acctingform').submit();
	});

	$(function(){
		$('#companyCode').change(function(){
			var environment = $('#environment').val();
			var companyId=$(this).val();
			var companyName = this.options[this.selectedIndex].innerHTML;
			var iCode =$("#instanceCode").val();
			document.getElementById("companyCode").style.backgroundColor = "#ffffff";
			clearAssetList();
			$('#companyNameCode').value = companyName.substring(6, companyName.lenght);
			$.ajax({
				type: "GET",
				dataType: "json",
				url: "getAssetMethod",
				data: {companyId:companyId,
					env:environment,
					instanceCode:iCode}
			}).done(function(reply) {
				if($('#assetNum').val() == null){
					$('#assetNum').append('<option value="select" selected disabled hidden>SELECT ASSET NUMBER</option>');
				}
				fillAssetNumber(reply);
			});
		})
	});
	
	$(function(){
		$('#assetNum').change(function(){
			document.getElementById("assetNum").style.backgroundColor = "#ffffff";
		})
	});
		
		
		var acc = document.getElementsByClassName("accordion");
		var i;

		for (i = 0; i < acc.length; i++) {
			acc[i].addEventListener("click", function() {
				this.classList.toggle("active");
				var panel = this.nextElementSibling;
				if (panel.style.maxHeight) {
					panel.style.maxHeight = null;
				} else {
					panel.style.maxHeight = panel.scrollHeight + "px";
				}
			});
		}
		
		
});


function fillAssetNumber(reply){
	var selectedAsset = $("#assetNumber").val();
	document.getElementById("assetNum").style.backgroundColor = "#fff59d";
	for (val in reply){
		if(reply[val] == selectedAsset.trim()){
			$('#assetNum').append('<option style="background-color: #fff59d;" value="' + reply[val] + '" selected="selected">' + reply[val]+'</option>');
			document.getElementById("assetNum").style.backgroundColor = "#ffffff";
		}else{
			$('#assetNum').append('<option style="background-color: #ffffff;" value="' + reply[val] + '">' + reply[val]+'</option>');
		}
	}
}

function validateCompanyAsset(){
	var companyN = document.getElementById("companyCode");
	var selectedValueComp = companyN.options[companyN.selectedIndex].value;
	if (selectedValueComp == "select"){
		alert("Please Select a Company");
		return false;
	}else{
		var assetNum = document.getElementById("assetNum");
		var selectedValueAsset = assetNum.options[assetNum.selectedIndex].value;
		if (selectedValueAsset == "select"){
			alert("Please Choose an Asset Number");
			return false;
		}else {
			return true;
		}
	}
	
}

$(function() {
	$("li").click(function(e) {
	  e.preventDefault();
	  $("li").removeClass("selected");
	  $(this).addClass("selected");
	});
});

function openTab(tabName) {
    var i;
    var x = document.getElementsByClassName("data");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";  
    }
    document.getElementById(tabName).style.display = "block";  
}

function clearAssetList(){
	var select = $('#assetNum').empty();
}

*/