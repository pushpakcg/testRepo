<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <title>code2java - Datatable Server Side Processing</title> -->

<!-- Below are the Style Sheets required for Data Tables. These can be customized as required -->
<link rel="stylesheet" type="text/css" href="css/db_site_ui.css">
<!-- <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.5.5/css/demo_table_jui.css"> -->
<link rel="stylesheet" href="css/styles_bs/demo_table_jui.css">
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
<link rel="stylesheet" href="/css/styles_bs/selectionCriteria.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<style>
/* .smallInput{width:100%; border:0;} */
.smallInput{
background-repeat: no-repeat;
    background-position: right center;
    background-size: 13px;
    background-image: url(../../images/calendar.png);
    width:100%;
    border:0;
}
.secondline_main {
	background-color: #334c67;
	overflow: hidden;
}

table#myDatatable tbody td {
    padding: 8px 0px;
}

.secondline_main strong {
	color: #ffffff;
	float: left;
	margin: 4px 0 0 20px;
}

#header {
    background-color: orange;
    font-size: 12px;
    text-color: black;
}
b {
  color: white;
}
.table-striped>tbody>tr:nth-of-type(odd) {
    background-color: bisque;
}
tr.odd td.sorting_1 {
    background-color: bisque;
}
tr.even td.sorting_1 {
    background-color: white;
}
.table > thead > tr > th,
.table > tbody > tr > th,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > tbody > tr > td,
.table > tfoot > tr > td {
  line-height: 1.42857143;
  vertical-align: top;
  border-top: 1px solid #ddd;
  padding: 1px;
}
.col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-xs-1, .col-xs-10, .col-xs-11, .col-xs-12, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9 {
    position: relative;
    min-height: 1px;
    padding-right: 2px;
    padding-left: 15px;
}
 #myDatatable_paginate {
	font-size: 12px;
    margin-top: 3px;
} 

/* naidu */
#snackbar {
    visibility: hidden;
    min-width: 250px;
    margin-left: -125px;
    background-color: #333;
    color: #fff;
    text-align: center;
    border-radius: 2px;
    padding: 16px;
    position: fixed;
    z-index: 1;
    left: 50%;
    bottom: 30px;
    font-size: 17px;
}

#snackbar.show {
    visibility: visible;
    -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
    animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

@-webkit-keyframes fadein {
    from {bottom: 0; opacity: 0;} 
    to {bottom: 30px; opacity: 1;}
}

@keyframes fadein {
    from {bottom: 0; opacity: 0;}
    to {bottom: 30px; opacity: 1;}
}
@-webkit-keyframes fadeout {
    from {bottom: 30px; opacity: 1;} 
    to {bottom: 0; opacity: 0;}
}

@keyframes fadeout {
    from {bottom: 30px; opacity: 1;}
    to {bottom: 0; opacity: 0;}
}



</style>




<!-- Below is the jQuery file required for using any Jquery component. -->
<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script> -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Below are the jQuery scripts required for Data Tables. -->
<script type="text/javascript" src="//cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <!-- latest one -->
  <!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"> -->
  <link rel="stylesheet" href="css/styles_bs/jquery.dataTables.min.css">
  

<!-- Initialization code of data table at the time of page load. -->
<script>

	$(document).ready(function() {
		search('yes');
		$( "#datepicker" ).datepicker();
		 $("#datepicker").on("change",function(){
		        var selected = $(this).val();
		       // alert(selected);
		    });
		$( "#datepicker1" ).datepicker();
		$("#datepicker1").on("change",function(){
	        var selected = $(this).val();
	       // alert(selected);
	    });
		$( "#datepicker2" ).datepicker();
		$("#datepicker2").on("change",function(){
	        var selected = $(this).val();
	       // alert(selected);
	    });
		$( "#datepicker3" ).datepicker();
		$("#datepicker3").on("change",function(){
	        var selected = $(this).val();
	        //alert(selected);
	    }); 
		$("#searchbutton").click(function(){
		    search('no');
		  });
	
		
		$("#excelDownloadButton").click(function(){
		    excelDownload();
		    
		    
		});
		
		 $('#companyGroup').change(function(event) {
			 
			var groupName = $("select#companyGroup").val();
			$.getJSON('getCompanyNames', {
				groupName : groupName
			}, function(data) {
				var select = $('#companyName');
				select.find('option').remove();
				$('<option>').val('-1').text('Select Company').appendTo(select);
				$.each(data, function(key, value) {
					$('<option>').val(key).text(value).appendTo(select);
				});
				$('#companyName').addClass("form-control");
				$('#companyName').css("font-family: Calibri; font-size: 12px;");
			});
		}); 
		 //datatable
		 
		
	});
	
	
	
	function excelDownload(){
		var x = document.getElementById("snackbar")
        x.className = "show";
        setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
        
		var form = document.createElement("form");
		form.action = "downloadExcelData";
		form.method = "POST";
		var facompany=$("select#companyName").val();
		var taxInstallDateFrom=$("#datepicker").val();
		var taxInstallDateTo=$("#datepicker1").val();
		var taxCreateDateFrom=$("#datepicker2").val();
		var taxCreateDateTo=$("#datepicker3").val();
		if (facompany === "-1") {
			facompany="";
		}
		var data={facompany: facompany,taxInstallDateFrom:taxInstallDateFrom,taxInstallDateTo:taxInstallDateTo
				,taxCreateDateFrom:taxCreateDateFrom,taxCreateDateTo:taxCreateDateTo,excelDownloadFlow:'true'};
		if (data) {
		for ( var key in data) {
			var input = document.createElement('input');
			input.type = 'hidden';
			input.name = key;
			input.value = data[key];
			form.appendChild(input);
		}
		}
		form.style.display = 'none';
		document.body.appendChild(form);
		form.submit();
		
	}
	
	
	function search(initload){
		var url='loadServerSideData?';
		var facompany=$("select#companyName").val();
		var taxInstallDateFrom=$("#datepicker").val();
		var taxInstallDateTo=$("#datepicker1").val();
		var taxCreateDateFrom=$("#datepicker2").val();
		var taxCreateDateTo=$("#datepicker3").val();
		
		if (facompany === "-1") {
			facompany="";
		}
		
		if (initload == "yes") {
			url=url+'&facompany=dummycompany123456';
		}else{
			url=url+'&facompany='+facompany+'&taxInstallDateFrom='+taxInstallDateFrom+'&taxInstallDateTo='+taxInstallDateTo
			+'&taxCreateDateFrom='+taxCreateDateFrom+'&taxCreateDateTo='+taxCreateDateTo;
		}
		
		console.log(url);
		$('#myDatatable').DataTable({
			//$('#myDatatable').removeAttr('width').DataTable({
			destroy: true,
			//"jQueryUI" : true,
			"pagingType" : "full_numbers",
			//"scrollX": "50px"
			//"pagingType" : "numbers",
			"paging": true,
			//"lengthMenu" : [ [ 5, 10, 50, -1 ], [ 5, 10, 50, "All" ] ],
			"processing": true,
			//"bServerSide": true,
	        "sAjaxSource" : url,
	       "scrollX": "true",
	       "scrollY": "true",
	        "sServerMethod": "POST"
	        	
		});
	}
</script>
</head>
<body>

 
	<!-- <h1>FAW</h1>
	
	<form method="post" action="search">
	
	  <select class="form-control" name="companyName"><option value="" selected="selected">SELECT</option><option value="100">100 - UNIVERSAL STUDIOS HOLLYWOOD</option><option value="101">101 - UNIVERSAL CITYWALK</option><option value="102">102 - TERRA PROPERTIES UCC</option><option value="109">109 - HILLTOP PARKING</option><option value="121">121 - LUDO BIRD</option><option value="122">122 - VOODOO</option><option value="123">123 - MARGARITAVILLE</option><option value="124">124 - MENCHIES</option><option value="127">127 - VIVO ITALIAN KITCHEN</option><option value="129">129 - USS CITYWALK</option><option value="177">177 - DODGER DOGS</option></select>
	  
	  <select class="form-control" id="sSearch_4" name="sSearch_4"><option value="" selected="selected">City</option><option value="Seattle">Seattle</option><option value="Walla">Walla</option></select>
	   <select class="form-control" id="state" name="state"><option value="" selected="selected">State</option><option value="AP">AP</option><option value="TS">TS</option></select>
	  
	  
	  <button >Search</button> -->
	  <div class="fluid-container secondline_main">
			<div class="container-fluid tab-pane" id="2a">
				<div class="col-lg-12  col-sm-12 ">
					<div class="accordion" id="Selection">
						<div class="col-lg-6  col-sm-6 " style="margin-left: 40%;">
							<!-- <b style="background-color: white;">FA250 - Tax Register Report</b> -->
							<b >Tax Asset Register</b>
						</div>
						<!-- <b>FA250 - Tax Register Report</b> -->
					</div>
					<div class="panel col-lg-12  col-sm-12 " style="max-height: 34%;">
						<div class="row">
							<div id="Selectiontab">
							
								<!-- <div id="taxRegReport col-lg-12  col-sm-12 " style="max-height: 34%;"> -->
								<div id="taxRegReport col-lg-12  col-sm-12 " style="width: 1340px;">
									<div class="row" style="margin-left: 1px;">
										<!-- <div class=" col-lg-2 col-sm-2 ">Company Group</div>
										<div class="col-lg-2 col-sm-2 ">Company Name</div> -->
										<div class=" col-lg-4 col-sm-4 " style="margin-top: 3px;margin-left: 54px;">Group &amp;Company Name</div>
										<!-- 13sep -->
										<div class="col-lg-4 col-sm-4 " style="margin-top: 3px;margin-left: -9%;">Tax Installation From - To Date</div>
										<!-- 13sep -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Installation From Date</div> -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Installation To Date</div> -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Creation From Date</div>
										<div class="col-lg-2 col-sm-2 ">Tax Creation To Date</div> -->
										<div class="col-lg-4 col-sm-4 " style="margin-left: -60px; margin-top: 3px;">Tax Creation From - To Date</div>
										<div class=" col-lg-1 col-sm-1 "></div>
									</div>



									<div class="row" style="margin-left: 1px;">
										<div class="form-group col-lg-2 col-sm-2 ">
											<!-- <select class="form-control" id="compGrp" name="compGrp" path="compGrp" autocomplete="off" style="font-family: Calibri; font-size: 12px;"><option value="ALL">ALL</option><option value="ALL250">ALL250</option><option value="ALLTLMD">ALLTLMD</option><option value="CIM">CIM</option><option value="CORPHQ">CORPHQ</option><option value="CPFA">CPFA</option><option value="CPG">CPG</option><option value="DWA">DWA</option><option value="E!">E!</option><option value="FA200 + FA250">FA200 + FA250</option><option value="FA250 - ALL CO">FA250 - ALL CO</option><option value="FILM">FILM</option><option value="G4">G4</option><option value="GOLF">GOLF</option><option value="GROUP1">GROUP1</option><option value="GROUP2">GROUP2</option><option value="GROUP3">GROUP3</option><option value="GROUP4">GROUP4</option><option value="IMD">IMD</option><option value="IVILLAGE">IVILLAGE</option><option value="LEGACY NBC">LEGACY NBC</option><option value="LOCAL MEDIA">LOCAL MEDIA</option><option value="LVG&amp;0SD">LVG&amp;0SD</option><option value="MSNBC &amp; WEATHER PLUS">MSNBC &amp; WEATHER PLUS</option><option value="O&amp;T">O&amp;T</option><option value="RSN">RSN</option><option value="SPROUT">SPROUT</option><option value="STUDIOS">STUDIOS</option><option value="T:1_COMCAST CRAFTSY">T:1_COMCAST CRAFTSY</option><option value="T:1_DOMESTIC C-CORP">T:1_DOMESTIC C-CORP</option><option value="T:1_DOMESTIC JV">T:1_DOMESTIC JV</option><option value="T:3_UNI DOMESTIC">T:3_UNI DOMESTIC</option><option value="T:4_LEGACY DOMESTIC">T:4_LEGACY DOMESTIC</option><option value="T:5_TLMD DOMESTIC">T:5_TLMD DOMESTIC</option><option value="T:LA-BROADCAST TV">T:LA-BROADCAST TV</option><option value="T:LA-CABLE NET">T:LA-CABLE NET</option><option value="T:LA-CORP&amp;OTHER">T:LA-CORP&amp;OTHER</option><option value="T:LA-DREAMWORKS">T:LA-DREAMWORKS</option><option value="T:LA-FANDANGO">T:LA-FANDANGO</option><option value="T:LA-FILM">T:LA-FILM</option><option value="T:LA-VIDEO&amp;CONS PROD">T:LA-VIDEO&amp;CONS PROD</option><option value="T:NY-BROADCAST TV">T:NY-BROADCAST TV</option><option value="T:NY-CABLE NET">T:NY-CABLE NET</option><option value="T:NY-CORP&amp;OTHER">T:NY-CORP&amp;OTHER</option><option value="T:NY-LIQ/INACTIVE/NC">T:NY-LIQ/INACTIVE/NC</option><option value="T:NY-NEWS">T:NY-NEWS</option><option value="T:NY-OWNED STATIONS">T:NY-OWNED STATIONS</option><option value="T:NY-RSN">T:NY-RSN</option><option value="T:NY-SPORTS">T:NY-SPORTS</option><option value="T:NY-STATION VENTURE">T:NY-STATION VENTURE</option><option value="T:NY-STUDIO OPS">T:NY-STUDIO OPS</option><option value="TELEMUNDO">TELEMUNDO</option><option value="TELEMUNDO STATIONS">TELEMUNDO STATIONS</option><option value="TEMPGRP">TEMPGRP</option><option value="TEST_250">TEST_250</option><option value="TOPS">TOPS</option><option value="TOPS+MSNBC">TOPS+MSNBC</option><option value="TVSD">TVSD</option><option value="VERSUS">VERSUS</option></select> -->
											<select class="form-control" name="companyGroup" id="companyGroup" style="font-family: Calibri; font-size: 11px; width:130px">
												<option value="-1">Select Group</option> 
												<option value="-1"></option>
												<c:forEach var="item" items="${companyGroupsMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>

										</div>

										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -23px;">
										<select class="form-control" name="companyName" id="companyName" style="font-family: Calibri; font-size: 12px; width: 155px;paddding-left: 162px;margin-left: -31%;">
												 <option value="-1">Select CompanyName</option> 
												<option value="-1"></option>
												<c:forEach var="item" items="${companyNamesMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>
	<!-- 										<select class="form-control" id="companyCode" name="companyCode" path="companyCode" autocomplete="off" style="font-family: Calibri; font-size: 12px;"><option value="" selected="selected">SELECT</option><option value="001">001 - TVSD 2002</option><option value="003">003 - ARTHOUSE</option><option value="004">004 - NBC STN MGMT,INC./WCAU L.P.</option><option value="005">005 - NBC STN MGMT,INC./WTVJ L.P.R</option><option value="007">007 - HOME VIDEO</option><option value="009">009 - NBC STUDIOS,INC.(U.M.S.)</option><option value="010">010 - FOCUS FEATURES</option><option value="012">012 - BROADCAST TV RETRANS</option><option value="015">015 - NBC NEWS BUREAUS, INC.</option><option value="028">028 - CPG MERCHANDISING</option><option value="030">030 - STUDIO PRODUCTION SERVICES</option><option value="031">031 - REAL ESTATE SERVICES</option><option value="032">032 - EARTH PROPERTIES</option><option value="036">036 - BRENNAN HOLDINGS, INC.</option><option value="037">037 - NBC NEWS CHANNEL INC.</option><option value="040">040 - NBC WEST</option><option value="041">041 - ALBUQUERQUE STUDIOS</option><option value="042">042 - FILMMAKER PRODUCTION SERVICES</option><option value="043">043 - FM PRODUCTION SERVICES (LIC)</option><option value="047">047 - CNBC,INC.</option><option value="053">053 - USA TV</option><option value="059">059 - UNIVERSAL TALK TV</option><option value="060">060 - 10UCP</option><option value="061">061 - NBCUNIVERSAL CAHUENGA LLC</option><option value="071">071 - NBC NEWS WORLDWIDE, INC.</option><option value="076">076 - NBC OLYMPICS,INC.</option><option value="077">077 - MSNBC</option><option value="082">082 - UNIVERSAL TV GROUP</option><option value="083">083 - NBC CORPORATION 83</option><option value="0A0">0A0 - BRAVO CORP</option><option value="0A1">0A1 - ENTERTAINMENT</option><option value="0A2">0A2 - NEWS</option><option value="0A3">0A3 - SPORTS</option><option value="0A4">0A4 - OLYMPICS</option><option value="0A5">0A5 - B&amp;NO</option><option value="0A6">0A6 - CORPORATE STAFF</option><option value="0A7">0A7 - TV NETWORK</option><option value="0EB">0EB - TELEMUNDO MEXICO</option><option value="0F1">0F1 - NBC FACILITIES,INC.-BN&amp;O</option><option value="0IC">0IC - IVPN (LAMAZE - IP)</option><option value="0JE">0JE - KIT CO</option><option value="0KA">0KA - USA NETWORK</option><option value="0KB">0KB - SCI FI CHANNEL, NY</option><option value="0KG">0KG - UTN CORP</option><option value="0KH">0KH - CABLE AFFILIATE</option><option value="0KQ">0KQ - CRAFTSY</option><option value="0KZ">0KZ - TVG WC OH</option><option value="0P5">0P5 - THE NBC EXPERIENCE</option><option value="0P8">0P8 - INTERACTIVE MEDIA</option><option value="0Q1">0Q1 - WZDC - DC</option><option value="0Q2">0Q2 - KTLM RIO GRANDE</option><option value="0Q3">0Q3 - WRDM  - HARTFORD</option><option value="0Q4">0Q4 - KHRR TUCSON, AZ</option><option value="0Q6">0Q6 - TELEMUNDO TELEVISION STUDIOS</option><option value="0Q8">0Q8 - SAN DIEGO SPANISH CHANNEL</option><option value="0Q9">0Q9 - KBLR-LAS VEGAS</option><option value="0QA">0QA - KVEA, LOS ANGELES, CA</option><option value="0QB">0QB - 0QB KWHY, LOS ANGELES, CA</option><option value="0QC">0QC - WSCV MIAMI, FLA</option><option value="0QD">0QD - WNJU, NEW YORK NY</option><option value="0QE">0QE - KXTX DALLAS, TX (REVISED 2004)</option><option value="0QF">0QF - WSNS CHICAGO, IL</option><option value="0QG">0QG - KSTS, SAN FRANCISCO</option><option value="0QH">0QH - KVDA SAN ANTONIO, TX</option><option value="0QI">0QI - KTMD HOUSTON, TX</option><option value="0QJ">0QJ - KMAS, DENVER</option><option value="0QK">0QK - TELEXITOS TV</option><option value="0QL">0QL - WWSI ATLANTIC CITY</option><option value="0QN">0QN - TLMD NATIONAL SALES</option><option value="0QO">0QO - TLMD DIVISION</option><option value="0QR">0QR - OQR TELEMUNDO NETWORK GROUP</option><option value="0QS">0QS - OQS GEMS INTERNATIONAL TV LLP</option><option value="0QU">0QU - 0QU TLMD NETWORK TEXAS SALES</option><option value="0QV">0QV - TELEMUNDO COMMUNICATIONS</option><option value="0QW">0QW - KDRX/KPHZ PHOENIX, AZ</option><option value="0QX">0QX - WNEU-BOSTON</option><option value="0QY">0QY - WKAQ, PR</option><option value="0QZ">0QZ - KNSO, FRESNO</option><option value="0R0">0R0 - SKYCASTLE T0R0</option><option value="0R5">0R5 - OUT OF HOME</option><option value="0R6">0R6 - LOCAL INTEGRATED MEDIA</option><option value="0R7">0R7 - COZI TV</option><option value="0R8">0R8 - OXYGEN</option><option value="0R9">0R9 - LX TV</option><option value="0RB">0RB - INTERNET &amp; BROADBAND</option><option value="0RF">0RF - 0RF, TELEMUNDO CONTENT CENTER</option><option value="0RH">0RH - IVILLAGE</option><option value="0RI">0RI - HEALTHCENTERSONLINE</option><option value="0RJ">0RJ - HEALTHOLOGY</option><option value="0RK">0RK - IVPN (LAMAZE - IM)</option><option value="0RL">0RL - ASTROLOGY</option><option value="0RM">0RM - PROMOTIONS.COM</option><option value="0RQ">0RQ - NBCSPORTS.COM</option><option value="0RV">0RV - ORV TLMD INTERNATIONAL DISTRI</option><option value="0RZ">0RZ - NBC DIGITAL MEDIA, INC</option><option value="0S1">0S1 - BRAVO B&amp;NO</option><option value="0S6">0S6 - BRAVO NETWORK</option><option value="0S9">0S9 - NBC UNIVERSAL WIRELESS INC.</option><option value="0SC">0SC - KCSO SACRAMENTO STATIONS</option><option value="0SD">0SD - TELEMUNDO SAN DIEGO</option><option value="0SL">0SL - KTMW SALT LAKE CITY</option><option value="0T1">0T1 - WNBC, NEW YORK</option><option value="0T3">0T3 - WMAQ CHICAGO</option><option value="0T4">0T4 - KNBC - LOS ANGELES, CA</option><option value="0T9">0T9 - WCAU, PHILADELPHIA</option><option value="0TB">0TB - WRC, WASHINGTON</option><option value="0TC">0TC - WTVJ, MIAMI, FLA</option><option value="0TF">0TF - NSO 2000 YEAREND</option><option value="0TJ">0TJ - WBTS - BOSTON</option><option value="0TM">0TM - MASTER CONTROL HUB</option><option value="0TS">0TS - KNSD, SAN DIEGO</option><option value="0TX">0TX - KXAS, FT WORTH / DALLAS</option><option value="0VG">0VG - KNTV, SAN JOSE</option><option value="0VH">0VH - WVIT-HARTFORD</option><option value="0W5">0W5 - B&amp;NO WEST LLC</option><option value="0W6">0W6 - CORP STAFF WEST LLC</option><option value="0W7">0W7 - WEATHER PLUS NETWORK LLC -0W7</option><option value="0W9">0W9 - WEATHER PLUS NETWORK LLC -0W9</option><option value="401">401 - UNIVERSAL FILM EXCHANGE</option><option value="404">404 - UNIVERSAL PICTURES</option><option value="407">407 - MP PRODUCTION</option><option value="475">475 - UNIVERSAL STUDIO LICENSING,INC</option><option value="621">621 - MCA TV LIMITED</option><option value="DC1">DC1 - DAILY CANDY</option><option value="DC2">DC2 - DAILY CANDY GROUP BUYING</option><option value="DF1">DF1 - FANDANGO</option><option value="DF2">DF2 - FANDANGO MARKETING</option><option value="DF3">DF3 - MOVIES.COM</option><option value="DF6">DF6 - MEDIANAVICO LLC</option><option value="DF7">DF7 - FLIXTER ROTTEN TOMATOES</option><option value="DF9">DF9 - FANDANGO MERCHANDISINGLLC</option><option value="DW1">DW1 - SWIRL</option><option value="DW2">DW2 - COMCAST DIGITAL</option><option value="DW3">DW3 - RHQ</option><option value="ES1">ES1 - WTMO - ORLANDO</option><option value="ES2">ES2 - WRMD - TAMPA</option><option value="ES3">ES3 - WWDT - FT MYERS</option><option value="ES4">ES4 - KTDO - EL PASO</option><option value="ES5">ES5 - WDMR - SPRINGFIELD</option><option value="ES6">ES6 - WRIW - PROVIDENCE</option><option value="ES7">ES7 - WZTD - RICHMOND</option><option value="ES8">ES8 - LIQ-TELEMUNDO NATI SALES,</option><option value="ES9">ES9 - WZGS - RALEIGH</option><option value="F02">F02 - UNIVERSAL TELEVISION GROUP</option><option value="F21">F21 - VUE</option><option value="F22">F22 - CRAFTSY(TAX)</option><option value="JA3">JA3 - NEWS ONLINE</option><option value="JA4">JA4 - MSNBC ONLINE</option><option value="KEA">KEA - E! ENTERTAINMENT TELEVISION</option><option value="KEF">KEF - E! NETWORKS PROD-E!</option><option value="KEG">KEG - COMCAST ENTERTAINMENT PRO</option><option value="KFA">KFA - STYLE</option><option value="KGA">KGA - G4 MEDIA INC</option><option value="KNA">KNA - IMD</option><option value="KPA">KPA - SPROUT</option><option value="KTA">KTA - COMCAST SHARED SERVICES CORP.</option><option value="KWA">KWA - COMCAST SPORTSGROUP MANAGEMENT</option><option value="KWB">KWB - CSN MIDATLANTIC</option><option value="KWC">KWC - CSN NORTHWEST C</option><option value="KWD">KWD - CSN WEST</option><option value="KWF">KWF - CSN PHILADELPHIA</option><option value="KWG">KWG - NBC SPORTS CHICAGO</option><option value="KWH">KWH - MOUNTAIN WEST SPORTS NETWORK</option><option value="KWJ">KWJ - CSN NEW ENGLAND</option><option value="KWL">KWL - CSN SAN FRANCISCO</option><option value="KWN">KWN - COMCAST CHARTER SPORTS SOUTHEA</option><option value="KWP">KWP - COMCAST SPORTS SOUTHWEST LLC</option><option value="KWQ">KWQ - THE COMCAST NETWORK PHILADELPH</option><option value="KWR">KWR - CN MIDATLANTIC</option><option value="KWS">KWS - NEW ENGLAND CABLE NEWS</option><option value="KWU">KWU - CSN CHICAGO</option><option value="LGA">LGA - GOLFNOW</option><option value="LGG">LGG - THE GOLF CHANNEL</option><option value="LGH">LGH - BRS GOLF LIMITED</option><option value="LND">LND - ALLIANCE OF ACTION SPORTS LLC.</option><option value="LVA">LVA - VERSUS</option><option value="LVB">LVB - SPORTS GROUP HQ</option><option value="LVD">LVD - FITNESS VIDEO VENTURES, LLC</option><option value="LVF">LVF - OLYMPICS CHANNEL</option><option value="LVG">LVG - SPORT NGIN</option><option value="S01">S01 - STUDIO OPERATION</option><option value="U05">U05 - PACIFIC DATA IMGS L.L.C.</option><option value="U07">U07 - DWA ONLINE, INC.</option><option value="U0E">U0E - DWA NOVA, LLC</option><option value="U10">U10 - DW ANIMATION L.L.C.</option><option value="U15">U15 - ASIA CHANNEL</option><option value="U20">U20 - CLASSIC MEDIA, LLC</option><option value="U25">U25 - BIG IDEA ENT., LLC</option><option value="U30">U30 - DWA TELEVISION, LLC</option><option value="U50">U50 - DW CONSUMER PRODUCTS</option><option value="U51">U51 - DWA LICENSING, LLC</option><option value="U60">U60 - DWA THEATRICAL MANAGEMENT</option><option value="U65">U65 - DRAGON ARENA SHOW</option></select> -->
										</div>

										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -113px;">

										<div class="form-control "style="width: 80%; padding: 4px 12px;">
												<!--   <i input type="text" class="fa fa-calendar"	class="form-control" id="installedFrom" name="installedFrom" path="installedFrom" autocomplete="off" style="font-size: 18px"></i>-->
												<input id="datepicker" name="installedFrom" type="text" class="smallInput calImg" value="" autocomplete="off">
												
											</div>
										</div>
										<!-- 13sep -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -4%;">

										<div class="form-control "style="width: 84%; padding: 4px 12px;">
										<input id="datepicker1" name="installedTo" type="text" class="smallInput calImg" value="" autocomplete="off">
										</div>
										</div>
										<!-- 13sep -->
										




										<div class="form-group col-lg-2 col-sm-2 "style="margin-left: -2%;">
											<div class="form-control "style="width: 81%; padding: 4px 12px;">
												<!-- <i input type="text" class="fa fa-calendar"	class="form-control" id="creationFrom" name="creationFrom" path="creationFrom" autocomplete="off" style="font-size: 18px"></i> -->
												<input id="datepicker2" name="creationFrom" type="text" class="smallInput calImg" value="" autocomplete="off">
												
											</div>
										</div>
										
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -21px;">
										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
											<div class="form-control "style="width: 84%; padding: 4px 12px;margin-left: -14%;">
										<input id="datepicker3" name="creationTo" type="text" class="smallInput calImg" value="" autocomplete="off">
										</div>
										</div>
										
										<div class="form-group col-lg-1 col-sm-1 ">
										<!-- <button id="searchbutton">Searchbutton</button> -->
										<!-- <div class="form-group col-lg-1 col-sm-1 "> -->
										<a id="searchbutton" href="#" class="btn btn-info btn-md" data-toggle="tooltip" data-placement="right" title="Excute" style="margin-left: -25%;"> <span class="glyphicon glyphicon-play" id="executeBtn" style="font-size: 100%;margin-top: 3%;"></span>
											</a>
											</div>
											
											<div class="form-group col-lg-1 col-sm-1 ">
											<a id="excelDownloadButton" href="#" data-toggle="tooltip" data-placement="right" title="Download To Excel"> <i class="fa fa-file-excel-o" id="excelBtn" style="font-size: 200%;margin-left: -53%;"></i></a>
											</div>
											
											<div id="snackbar">Downloading Excel Sheet...</div>
											
											

											<!-- <a href="#" class="btn btn-info btn-md" data-toggle="tooltip" onclick="search()"
												data-placement="right" title="Excute"> <span
												class="glyphicon glyphicon-play" id="Search"
												style="font-size: 100%;"></span>
											</a>
											 <button >Search</button> --> 
										</div>

										<div class="form-group col-lg-1 col-sm-1 ">

											<!-- <a href="#" class="btn btn-info btn-md" data-toggle="tooltip" data-placement="right" title="Excute"> <span class="glyphicon glyphicon-play" id="executeBtn" style="font-size: 100%;"></span>
											</a> -->
										</div>
										<!-- <div class="form-group col-lg-1 col-sm-1 ">
											<a href="#" data-toggle="tooltip" data-placement="right" title="Download To Excel"> <i class="fa fa-file-excel-o" id="excelBtn" style="font-size: 200%;"></i></a>
											
										</div> -->
										<!-- <button id="excelDownloadButton" style="margin-left: -14%;">Excel</button> -->
										

										<!-- <div>
											<strong style="color: black;">Company Group</strong>
										</div>
										<div class="form-group col-lg-2 col-sm-2 col-md-2 col-xs-2">
											<select required class="form-control" class="chosen"
												id="compGrp" name="compGrp" path="compGrp"
												autocomplete="off"
												style="font-family: Calibri; font-size: 12px;"></select>
										</div>
										<div>
											<strong style="color: black;">Company Name</strong>
										</div>
										<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2">

											<select class="form-control" class="chosen" id="companyCode"
												name="companyCode" path="companyCode" autocomplete="off"
												style="font-family: Calibri; font-size: 12px;"></select>
										</div>
										<div>
											<strong style="color: black;">Tax Installation Start
												Date</strong>
										</div>
										<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2">

											<input type="text" class="form-control"
												class="smallInput calImg" id="installedFrom"
												name="installedFrom" path="installedFrom" autocomplete="off"
												style="height: 25px; width: 100px" />
										</div>
									</div>
									<div class="row">
										<div>
											<strong style="color: black;">&nbsp;End Date</strong>
										</div>
										<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2 ">

											<input type="text" class="form-control"
												class="smallInput calImg" id="installedTo"
												name="installedTo" path="installedTo" autocomplete="off"
												style="height: 25px; width: 100px" />
										</div>
										<div>
											<strong style="color: black;">&nbsp;Tax Creation
												Start Date</strong>

										</div>
										<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2">

											<input type="text" class="form-control"
												class="smallInput calImg" id="creationFrom"
												name="creationFrom" path="creationFrom" autocomplete="off"
												style="height: 25px; width: 100px" />
										</div>
										<div>
											<strong style="color: black;">&nbsp;End Date</strong>
										</div>
										<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2">

											<input type="text" class="form-control"
												class="smallInput calImg" id="creationTo" name="creationTo"
												path="creationTo" autocomplete="off"
												style="height: 25px; width: 100px" />
										</div>
									</div>
								</div>
								<div id="taskBar">
									<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2">
										<a href='#' class='btn addbtn' id="executeBtn"
											style="height: 25px;">Execute</a>
									</div>
									<div class="form-group col-lg-2 col-sm-2  col-md-2 col-xs-2">

										<a href='#' class='btn addbtn' id="excelBtn"
											style="height: 25px;">Download to Excel</a>
									</div>
								</div> -->
									</div>
								</div>
													</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	  
	
	<!-- </form> -->
	
	<!-- <h3>Datatable Server Side Processing</h3> -->
	<div class="tableDiv" style="margin-top: 4px;margin-left:5px;margin-right:5px">
	<!-- <div style="width: 80%"> -->
		<!-- Below table will be displayed as Data table -->
		<!-- <table id="myDatatable" class="display datatable">
			<thead>
				<tr>
					<th>UNIQUEASSET</th>
					<th>FACOMPANY</th>
					<th>SAPBU</th>
					<th>ASSETNUMBERPRIMARY</th>
					<th>ASSETCOMPONENT</th>
					<th>INTERNALASSET</th>
					<th>ASSETDESCRIPTION</th>
					<th>ENTEREDDATE</th>
					<th>TAXINSTALLDATE</th>
					<th>TAXASSETLIFE</th>
					<th>TAXREMAININGLIFE</th>
					<th>TAXCOSTBASIS</th>
					<th>TAXDEPRECIABLEBASIS</th>
					<th>TAXACCUMULATEDRESERVE</th>
					<th>TAXYTDDEPRECIATION</th>
					<th>TAXNETBOOKVALUE</th>
					<th>BONUSDEPRECIATION</th>
					<th>YTDBONUSDEPRECIATION</th>
					<th>ELECTOUTBONUS</th>
					<th>BONUSFLAG</th>
					<th>BONUS</th>
					<th>YTDAMTDEPR</th>
					<th>ADRADDITIONALDEPR</th>
					<th>ACRSCLASS</th>
					<th>TAXREGULATIONCODE</th>
					<th>TAXDEPRTABLE</th>
					<th>SECTION12451250PROPERTY</th>
					<th>LISTEDPROPERTY</th>
					<th>DEPRINTANGIBLE</th>
					<th>FIRSTDEPRYEAR</th>
					<th>CORPINTERNALASSET</th>
					<th>TAXAMTTABLE</th>
					<th>TAXNEWORUSED</th>
					<th>TAXAUTO</th>
					<th>TAXLEASEHOLDIMPR</th>
					<th>TAXDEPRMETHOD</th>
					<th>TAXDEPRCONVENTION</th>
					<th>CORPINSTALLDATE</th>
					<th>CORPASSETLIFE</th>
					<th>CORPBOOKCOSTBASIS</th>
					<th>CORPBOOKACCUMRESERVE</th>
					<th>CORPBOOKCURRENTDEPR</th>
					<th>CORPLASTYEARDEPRECIATED</th>
					<th>CORPLASTMONTHDEPRECIATED</th>
					<th>CORPNBV</th>
					<th>CORPFULLYRETIRED</th>
					<th>PROJECT</th>
					<th>CATEGORY</th>
					<th>CATEGORYDESC</th>
					<th>TAXINSTALLATIONYR</th>
					<th>TAXCURRACCYEAR</th>
					
					</tr>
			</thead>
			No need to add body tag here :)
		</table> -->
		<!-- <div class="dataTables_length" id="tickettable_length"><label>Show <select name="tickettable_length" aria-controls="tickettable" class=""><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div>
		<div id="tickettable_filter" class="dataTables_filter"><label>Search:<input type="search" class="" placeholder="" aria-controls="tickettable"></label></div> -->
		<table id="myDatatable" class="table table-striped table-bordered dataTable no-footer" role="grid" aria-describedby="tickettable_info" style="width: 3971px;font-size: 12px;text-align: center">
		<thead>
			<tr id="header" role="row"><td class="resultsHeader sorting_asc" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Unique Asset#: activate to sort column descending" style="width: 100px;">Unique Asset#<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="FA Company: activate to sort column ascending" style="width: 47px;">FA Company<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="SAP BU: activate to sort column ascending" style="width: 22px;">SAP BU<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Asset Number - Primary: activate to sort column ascending" style="width: 39px;">Asset Number - Primary<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Asset Component: activate to sort column ascending" style="width: 56px;">Asset Component<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Internal Asset#: activate to sort column ascending" style="width: 37px;">Internal Asset#<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Asset Description: activate to sort column ascending" style="width: 220px;">Asset Description<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Entered Date: activate to sort column ascending" style="width: 38px;">Entered Date<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Install Date: activate to sort column ascending" style="width: 29px;">Tax Install Date<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Asset Life: activate to sort column ascending" style="width: 28px;">Tax Asset Life<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Remaining Life: activate to sort column ascending" style="width: 52px;">Tax Remaining Life<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Cost Basis: activate to sort column ascending" style="width: 27px;">Tax Cost Basis<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Depreciable Basis: activate to sort column ascending" style="width: 58px;">Tax Depreciable Basis<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Accumulated Reserve: activate to sort column ascending" style="width: 63px;">Tax Accumulated Reserve<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax YTD Depreciation: activate to sort column ascending" style="width: 61px;">Tax YTD Depreciation<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Net Book Value: activate to sort column ascending" style="width: 28px;">Tax Net Book Value<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Bonus Depreciation: activate to sort column ascending" style="width: 61px;">Bonus Depreciation<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="YTD Bonus Depreciation: activate to sort column ascending" style="width: 61px;">YTD Bonus Depreciation<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Elect Out Bonus: activate to sort column ascending" style="width: 31px;">Elect Out Bonus<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Bonus % Flag: activate to sort column ascending" style="width: 31px;">Bonus % Flag<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Bonus %: activate to sort column ascending" style="width: 31px;">Bonus %<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="YTD Amt Depr: activate to sort column ascending" style="width: 24px;">YTD Amt Depr<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="ADR Additional Depr: activate to sort column ascending" style="width: 48px;">ADR Additional Depr<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="ACRS Class: activate to sort column ascending" style="width: 31px;">ACRS Class<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Regulation Code: activate to sort column ascending" style="width: 52px;">Tax Regulation Code<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Depr Table: activate to sort column ascending" style="width: 26px;">Tax Depr Table<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Section 1245/1250 Property: activate to sort column ascending" style="width: 52px;">Section 1245/1250 Property<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Listed Property: activate to sort column ascending" style="width: 42px;">Listed Property<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Depr Intangible: activate to sort column ascending" style="width: 48px;">Depr Intangible<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="First Depr Year: activate to sort column ascending" style="width: 24px;">First Depr Year<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Internal Asset#: activate to sort column ascending" style="width: 37px;">Corp Internal Asset#<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Amt Table: activate to sort column ascending" style="width: 26px;">Tax Amt Table<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax New Or Used: activate to sort column ascending" style="width: 26px;">Tax New Or Used<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Auto: activate to sort column ascending" style="width: 23px;">Tax Auto<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Leasehold Impr: activate to sort column ascending" style="width: 50px;">Tax Leasehold Impr<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Depr Method: activate to sort column ascending" style="width: 70px;">Tax Depr Method<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Depr Convention: activate to sort column ascending" style="width: 55px;">Tax Depr Convention<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Install Date: activate to sort column ascending" style="width: 29px;">Corp Install Date<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Asset Life: activate to sort column ascending" style="width: 28px;">Corp Asset Life<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Book Cost Basis: activate to sort column ascending" style="width: 27px;">Corp Book Cost Basis<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Book Accum Reserve: activate to sort column ascending" style="width: 41px;">Corp Book Accum Reserve<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Book Current Depr: activate to sort column ascending" style="width: 37px;">Corp Book Current Depr<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Last Year Depreciated: activate to sort column ascending" style="width: 59px;">Corp Last Year Depreciated<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Last Month Depreciated: activate to sort column ascending" style="width: 59px;">Corp Last Month Depreciated<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp NBV: activate to sort column ascending" style="width: 24px;">Corp NBV<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Corp Fully Retired: activate to sort column ascending" style="width: 36px;">Corp Fully Retired<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Project#: activate to sort column ascending" style="width: 40px;">Project#<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Category: activate to sort column ascending" style="width: 44px;">Category<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Category Desc: activate to sort column ascending" style="width: 250px;">Category Desc<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Installation Yr: activate to sort column ascending" style="width: 52px;">Tax Installation Yr<br></td><td class="resultsHeader sorting" tabindex="0" aria-controls="tickettable" rowspan="1" colspan="1" aria-label="Tax Curr Acc Year: activate to sort column ascending" style="width: 22px;">Tax Curr Acc Year<br></td></tr>
		</thead>
		<tbody>
			
		 <tr class="odd"><td valign="top" colspan="51" class="dataTables_empty"></td></tr></tbody> 
		
		
	</table>
		</div>
		<!-- naidu -->
		
 </body>
</html>