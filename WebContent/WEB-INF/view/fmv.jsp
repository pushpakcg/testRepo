<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- <title>code2java - Datatable Server Side Processing</title> -->

<!-- Below are the Style Sheets required for Data Tables. These can be customized as required -->
<script src="js/sso.js"></script>
<link rel="stylesheet" type="text/css" href="css/db_site_ui.css">
<!-- <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.5.5/css/demo_table_jui.css"> -->
<link rel="stylesheet" href="css/styles_bs/demo_table_jui.css">
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
<link rel="stylesheet" href="/css/styles_bs/selectionCriteria.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> 
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


/* toggle slider */
.switch {
  position: relative;
  display: inline-block;
  width: 75px;
  height: 34px;
}

.switch input {
  display: none;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #e3e8e4;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2ab934;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(40px);
  -ms-transform: translateX(40px);
  transform: translateX(40px);
}


.on {
  display: none;
}

.on, .off {
  color: white;
  position: absolute;
  transform: translate(-50%, -50%);
  top: 50%;
  left: 50%;
  font-size: 10px;
  font-family: Verdana, sans-serif;
  user-select:none;
}

input:checked + .slider .on {
  display: block;
}

input:checked + .slider .off {
  display: none;
}


.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
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
   <script src="js/sso.js"></script> 
  <!-- latest one -->
  <!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"> -->
  <link rel="stylesheet" href="css/styles_bs/jquery.dataTables.min.css">
  

<!-- Initialization code of data table at the time of page load. -->
<script>
var emp=${emptyObj}
console.log(emp)


	$(document).ready(function() {
		updateUserData();
       loadSSO();
        function loadSSO(){	
	 	      //var loginSSOid=document.getElementById('fawebuserId');
	 	     //var loginSSO = loginSSOid.textContent;
				//var url="loadSAPSSOData?loginSSO="+loginSSO;
				
				var url="loadSAPSSOData";
				jQuery.ajax({type: "get",url: url,dataType: "json",cache: false,
				   success:function(data) {
				    	   if(data.flag=="Y")
				    		   {
				    		   $(".secondline_main").show();
				    		   $(".container").show();
				    		   }
				    	   else{
				    		   $(".secondline_main").hide();
				    		   $(".container").hide();
				    		   alert("You Don't have a Admin Rights");
				    	   }
				   },
				error:function(){
				  //alert("Session Expired.Please login again.");
				}
				});
				
				
		}
	
	})
	
		
		
		
	
</script>
</head>
<body>

 
	
	  <div class="container-fluid secondline_main">
			<div class="container-fluid tab-pane" id="2a">
				<div class="col-lg-12  col-sm-12 ">
					<div class="accordion" id="Selection">
						<div class="col-lg-6  col-sm-6 " style="margin-left: 40%;">
							<!-- <b style="background-color: white;">FA250 - Tax Register Report</b> -->
							<b >FMV Dashboard</b>
							<p id="fawebuserId" hidden></p>
						</div>
						<!-- <b>FA250 - Tax Register Report</b> -->
					</div>
					<div class="panel col-lg-12  col-sm-12 " style="max-height: 34%;">
						<div class="row">
							<div id="Selectiontab">
							
								<!-- <div id="taxRegReport col-lg-12  col-sm-12 " style="max-height: 34%;"> -->
								<div id="taxRegReport col-lg-12  col-sm-12 " style="width: 1450px;">
									<div class="row" style="margin-left: 1px;">
										<!-- <div class=" col-lg-2 col-sm-2 ">Company Group</div>
										<div class="col-lg-2 col-sm-2 ">Company Name</div> -->
										<!-- <div class=" col-lg-4 col-sm-4 " style="margin-top: 3px;margin-left: 54px;">Group &amp;Company Name</div> -->
										<div class=" col-lg-2 col-sm-2 " style="margin-left: 65px;font-weight: bold;">Year</div>
										
									</div>
           
										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -30px;">
										<select class="form-control" name="year" id="year" style="font-family: Calibri; font-size: 14px; width: 150px;paddding-left: 142px;margin-left: 43%;">
												 <option value="-1">Select Year</option> 
<!-- 												<option value="-1"></option> -->
												<c:forEach var="item" items="${yearList}">
<%-- 													<option >${item}</option> --%>
                                                 <option value="${item}">${item}</option>
												</c:forEach>
											</select>
	<!-- 										<select class="form-control" id="companyCode" name="companyCode" path="companyCode" autocomplete="off" style="font-family: Calibri; font-size: 12px;"><option value="" selected="selected">SELECT</option><option value="001">001 - TVSD 2002</option><option value="003">003 - ARTHOUSE</option><option value="004">004 - NBC STN MGMT,INC./WCAU L.P.</option><option value="005">005 - NBC STN MGMT,INC./WTVJ L.P.R</option><option value="007">007 - HOME VIDEO</option><option value="009">009 - NBC STUDIOS,INC.(U.M.S.)</option><option value="010">010 - FOCUS FEATURES</option><option value="012">012 - BROADCAST TV RETRANS</option><option value="015">015 - NBC NEWS BUREAUS, INC.</option><option value="028">028 - CPG MERCHANDISING</option><option value="030">030 - STUDIO PRODUCTION SERVICES</option><option value="031">031 - REAL ESTATE SERVICES</option><option value="032">032 - EARTH PROPERTIES</option><option value="036">036 - BRENNAN HOLDINGS, INC.</option><option value="037">037 - NBC NEWS CHANNEL INC.</option><option value="040">040 - NBC WEST</option><option value="041">041 - ALBUQUERQUE STUDIOS</option><option value="042">042 - FILMMAKER PRODUCTION SERVICES</option><option value="043">043 - FM PRODUCTION SERVICES (LIC)</option><option value="047">047 - CNBC,INC.</option><option value="053">053 - USA TV</option><option value="059">059 - UNIVERSAL TALK TV</option><option value="060">060 - 10UCP</option><option value="061">061 - NBCUNIVERSAL CAHUENGA LLC</option><option value="071">071 - NBC NEWS WORLDWIDE, INC.</option><option value="076">076 - NBC OLYMPICS,INC.</option><option value="077">077 - MSNBC</option><option value="082">082 - UNIVERSAL TV GROUP</option><option value="083">083 - NBC CORPORATION 83</option><option value="0A0">0A0 - BRAVO CORP</option><option value="0A1">0A1 - ENTERTAINMENT</option><option value="0A2">0A2 - NEWS</option><option value="0A3">0A3 - SPORTS</option><option value="0A4">0A4 - OLYMPICS</option><option value="0A5">0A5 - B&amp;NO</option><option value="0A6">0A6 - CORPORATE STAFF</option><option value="0A7">0A7 - TV NETWORK</option><option value="0EB">0EB - TELEMUNDO MEXICO</option><option value="0F1">0F1 - NBC FACILITIES,INC.-BN&amp;O</option><option value="0IC">0IC - IVPN (LAMAZE - IP)</option><option value="0JE">0JE - KIT CO</option><option value="0KA">0KA - USA NETWORK</option><option value="0KB">0KB - SCI FI CHANNEL, NY</option><option value="0KG">0KG - UTN CORP</option><option value="0KH">0KH - CABLE AFFILIATE</option><option value="0KQ">0KQ - CRAFTSY</option><option value="0KZ">0KZ - TVG WC OH</option><option value="0P5">0P5 - THE NBC EXPERIENCE</option><option value="0P8">0P8 - INTERACTIVE MEDIA</option><option value="0Q1">0Q1 - WZDC - DC</option><option value="0Q2">0Q2 - KTLM RIO GRANDE</option><option value="0Q3">0Q3 - WRDM  - HARTFORD</option><option value="0Q4">0Q4 - KHRR TUCSON, AZ</option><option value="0Q6">0Q6 - TELEMUNDO TELEVISION STUDIOS</option><option value="0Q8">0Q8 - SAN DIEGO SPANISH CHANNEL</option><option value="0Q9">0Q9 - KBLR-LAS VEGAS</option><option value="0QA">0QA - KVEA, LOS ANGELES, CA</option><option value="0QB">0QB - 0QB KWHY, LOS ANGELES, CA</option><option value="0QC">0QC - WSCV MIAMI, FLA</option><option value="0QD">0QD - WNJU, NEW YORK NY</option><option value="0QE">0QE - KXTX DALLAS, TX (REVISED 2004)</option><option value="0QF">0QF - WSNS CHICAGO, IL</option><option value="0QG">0QG - KSTS, SAN FRANCISCO</option><option value="0QH">0QH - KVDA SAN ANTONIO, TX</option><option value="0QI">0QI - KTMD HOUSTON, TX</option><option value="0QJ">0QJ - KMAS, DENVER</option><option value="0QK">0QK - TELEXITOS TV</option><option value="0QL">0QL - WWSI ATLANTIC CITY</option><option value="0QN">0QN - TLMD NATIONAL SALES</option><option value="0QO">0QO - TLMD DIVISION</option><option value="0QR">0QR - OQR TELEMUNDO NETWORK GROUP</option><option value="0QS">0QS - OQS GEMS INTERNATIONAL TV LLP</option><option value="0QU">0QU - 0QU TLMD NETWORK TEXAS SALES</option><option value="0QV">0QV - TELEMUNDO COMMUNICATIONS</option><option value="0QW">0QW - KDRX/KPHZ PHOENIX, AZ</option><option value="0QX">0QX - WNEU-BOSTON</option><option value="0QY">0QY - WKAQ, PR</option><option value="0QZ">0QZ - KNSO, FRESNO</option><option value="0R0">0R0 - SKYCASTLE T0R0</option><option value="0R5">0R5 - OUT OF HOME</option><option value="0R6">0R6 - LOCAL INTEGRATED MEDIA</option><option value="0R7">0R7 - COZI TV</option><option value="0R8">0R8 - OXYGEN</option><option value="0R9">0R9 - LX TV</option><option value="0RB">0RB - INTERNET &amp; BROADBAND</option><option value="0RF">0RF - 0RF, TELEMUNDO CONTENT CENTER</option><option value="0RH">0RH - IVILLAGE</option><option value="0RI">0RI - HEALTHCENTERSONLINE</option><option value="0RJ">0RJ - HEALTHOLOGY</option><option value="0RK">0RK - IVPN (LAMAZE - IM)</option><option value="0RL">0RL - ASTROLOGY</option><option value="0RM">0RM - PROMOTIONS.COM</option><option value="0RQ">0RQ - NBCSPORTS.COM</option><option value="0RV">0RV - ORV TLMD INTERNATIONAL DISTRI</option><option value="0RZ">0RZ - NBC DIGITAL MEDIA, INC</option><option value="0S1">0S1 - BRAVO B&amp;NO</option><option value="0S6">0S6 - BRAVO NETWORK</option><option value="0S9">0S9 - NBC UNIVERSAL WIRELESS INC.</option><option value="0SC">0SC - KCSO SACRAMENTO STATIONS</option><option value="0SD">0SD - TELEMUNDO SAN DIEGO</option><option value="0SL">0SL - KTMW SALT LAKE CITY</option><option value="0T1">0T1 - WNBC, NEW YORK</option><option value="0T3">0T3 - WMAQ CHICAGO</option><option value="0T4">0T4 - KNBC - LOS ANGELES, CA</option><option value="0T9">0T9 - WCAU, PHILADELPHIA</option><option value="0TB">0TB - WRC, WASHINGTON</option><option value="0TC">0TC - WTVJ, MIAMI, FLA</option><option value="0TF">0TF - NSO 2000 YEAREND</option><option value="0TJ">0TJ - WBTS - BOSTON</option><option value="0TM">0TM - MASTER CONTROL HUB</option><option value="0TS">0TS - KNSD, SAN DIEGO</option><option value="0TX">0TX - KXAS, FT WORTH / DALLAS</option><option value="0VG">0VG - KNTV, SAN JOSE</option><option value="0VH">0VH - WVIT-HARTFORD</option><option value="0W5">0W5 - B&amp;NO WEST LLC</option><option value="0W6">0W6 - CORP STAFF WEST LLC</option><option value="0W7">0W7 - WEATHER PLUS NETWORK LLC -0W7</option><option value="0W9">0W9 - WEATHER PLUS NETWORK LLC -0W9</option><option value="401">401 - UNIVERSAL FILM EXCHANGE</option><option value="404">404 - UNIVERSAL PICTURES</option><option value="407">407 - MP PRODUCTION</option><option value="475">475 - UNIVERSAL STUDIO LICENSING,INC</option><option value="621">621 - MCA TV LIMITED</option><option value="DC1">DC1 - DAILY CANDY</option><option value="DC2">DC2 - DAILY CANDY GROUP BUYING</option><option value="DF1">DF1 - FANDANGO</option><option value="DF2">DF2 - FANDANGO MARKETING</option><option value="DF3">DF3 - MOVIES.COM</option><option value="DF6">DF6 - MEDIANAVICO LLC</option><option value="DF7">DF7 - FLIXTER ROTTEN TOMATOES</option><option value="DF9">DF9 - FANDANGO MERCHANDISINGLLC</option><option value="DW1">DW1 - SWIRL</option><option value="DW2">DW2 - COMCAST DIGITAL</option><option value="DW3">DW3 - RHQ</option><option value="ES1">ES1 - WTMO - ORLANDO</option><option value="ES2">ES2 - WRMD - TAMPA</option><option value="ES3">ES3 - WWDT - FT MYERS</option><option value="ES4">ES4 - KTDO - EL PASO</option><option value="ES5">ES5 - WDMR - SPRINGFIELD</option><option value="ES6">ES6 - WRIW - PROVIDENCE</option><option value="ES7">ES7 - WZTD - RICHMOND</option><option value="ES8">ES8 - LIQ-TELEMUNDO NATI SALES,</option><option value="ES9">ES9 - WZGS - RALEIGH</option><option value="F02">F02 - UNIVERSAL TELEVISION GROUP</option><option value="F21">F21 - VUE</option><option value="F22">F22 - CRAFTSY(TAX)</option><option value="JA3">JA3 - NEWS ONLINE</option><option value="JA4">JA4 - MSNBC ONLINE</option><option value="KEA">KEA - E! ENTERTAINMENT TELEVISION</option><option value="KEF">KEF - E! NETWORKS PROD-E!</option><option value="KEG">KEG - COMCAST ENTERTAINMENT PRO</option><option value="KFA">KFA - STYLE</option><option value="KGA">KGA - G4 MEDIA INC</option><option value="KNA">KNA - IMD</option><option value="KPA">KPA - SPROUT</option><option value="KTA">KTA - COMCAST SHARED SERVICES CORP.</option><option value="KWA">KWA - COMCAST SPORTSGROUP MANAGEMENT</option><option value="KWB">KWB - CSN MIDATLANTIC</option><option value="KWC">KWC - CSN NORTHWEST C</option><option value="KWD">KWD - CSN WEST</option><option value="KWF">KWF - CSN PHILADELPHIA</option><option value="KWG">KWG - NBC SPORTS CHICAGO</option><option value="KWH">KWH - MOUNTAIN WEST SPORTS NETWORK</option><option value="KWJ">KWJ - CSN NEW ENGLAND</option><option value="KWL">KWL - CSN SAN FRANCISCO</option><option value="KWN">KWN - COMCAST CHARTER SPORTS SOUTHEA</option><option value="KWP">KWP - COMCAST SPORTS SOUTHWEST LLC</option><option value="KWQ">KWQ - THE COMCAST NETWORK PHILADELPH</option><option value="KWR">KWR - CN MIDATLANTIC</option><option value="KWS">KWS - NEW ENGLAND CABLE NEWS</option><option value="KWU">KWU - CSN CHICAGO</option><option value="LGA">LGA - GOLFNOW</option><option value="LGG">LGG - THE GOLF CHANNEL</option><option value="LGH">LGH - BRS GOLF LIMITED</option><option value="LND">LND - ALLIANCE OF ACTION SPORTS LLC.</option><option value="LVA">LVA - VERSUS</option><option value="LVB">LVB - SPORTS GROUP HQ</option><option value="LVD">LVD - FITNESS VIDEO VENTURES, LLC</option><option value="LVF">LVF - OLYMPICS CHANNEL</option><option value="LVG">LVG - SPORT NGIN</option><option value="S01">S01 - STUDIO OPERATION</option><option value="U05">U05 - PACIFIC DATA IMGS L.L.C.</option><option value="U07">U07 - DWA ONLINE, INC.</option><option value="U0E">U0E - DWA NOVA, LLC</option><option value="U10">U10 - DW ANIMATION L.L.C.</option><option value="U15">U15 - ASIA CHANNEL</option><option value="U20">U20 - CLASSIC MEDIA, LLC</option><option value="U25">U25 - BIG IDEA ENT., LLC</option><option value="U30">U30 - DWA TELEVISION, LLC</option><option value="U50">U50 - DW CONSUMER PRODUCTS</option><option value="U51">U51 - DWA LICENSING, LLC</option><option value="U60">U60 - DWA THEATRICAL MANAGEMENT</option><option value="U65">U65 - DRAGON ARENA SHOW</option></select> -->
										</div>


										
										</div>
										
										<div class="form-group col-lg-1 col-sm-1" >
										<!-- <button id="searchbutton">Searchbutton</button> -->
										<!-- <div class="form-group col-lg-1 col-sm-1 "> -->
										<button id="searchbutton"  class="btn btn-primary btn-md" title="Excute" style="margin-left:30px"> 
											View</button>
											</div>
											
											<div class="form-group col-lg-1 col-sm-1 ">
										<!-- <button id="searchbutton">Searchbutton</button> -->
										<!-- <div class="form-group col-lg-1 col-sm-1 "> -->
										<button id="newbutton" href="#" class="btn btn-primary" data-toggle="tooltip" data-placement="right" title="Insert" style="margin-left: 250%;">
											Add Data</button>
											</div>
											
											<div class="form-group col-lg-1 col-sm-1 ">
										<!-- <button id="searchbutton">Searchbutton</button> -->
										<!-- <div class="form-group col-lg-1 col-sm-1 "> -->
										<button id="excelDown" href="#" class="btn btn-primary" data-toggle="tooltip" data-placement="right" title="Insert" style="margin-left: 660px;">
											Export Rates</button>
											</div>
																					
									</div>
								</div>
							</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	  
	
		</div>
		
<div class="container-fluid">
<form:form class="form-horizontal" style="font-size:15px;" method="post" modelAttribute="fmvModel">
<div class="row">
<div class="col-sm-6" >		
<div class="container-fluid" >
 
  <h4 class="col-sm-12" style="background-color:#FED8B1;font-weight: bold;padding: 5px;" id="formInputData">Modify Rates</h4>
  <div id="cont" style="margin-top:60px">
  <div class="form-group" id="vyear" style="display:none">
      <label  class="control-label col-sm-2" for="modelyear" style="text-align: left;"> Years :</label>
      <div class="col-sm-3">
        <input  type="number" class="form-control inputDisabler" id="modelyear"  name="modelyear" oninput="validateYear(this)" required disabled/>
      </div>
    </div>
  
     <div class="form-group">
      <label  class="control-label col-sm-2" for="year5" style="text-align: left;">5 Years :</label>
      <div class="col-sm-3">
        <input  type="number" class="form-control inputDisabler" id="year5"  name="year5" oninput="validateInput(this)" required disabled/>
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="year6" style="text-align: left;">6 Years :</label>
      <div class="col-sm-3">          
        <input type="number" class="form-control inputDisabler" id="year6" name="year6" oninput="validateInput(this)" required disabled>
      </div>
    </div>
    <div class="form-group" >
      <label class="control-label col-sm-2" for="year7" style="text-align: left;">7 Years :</label>
      <div class="col-sm-3">
        <input type="number" class="form-control inputDisabler" id="year7" name="year7" oninput="validateInput(this)" required disabled>
      </div>
    </div>
     <div class="form-group" >
      <label class="control-label col-sm-2" for="year8" style="text-align: left;">8 Years :</label>
      <div class="col-sm-3">
        <input type="number" class="form-control inputDisabler" id="year8" name="year8" oninput="validateInput(this)" required disabled>
      </div>
    </div>
    <div class="form-group" >
      <label class="control-label col-sm-2" for="year9" style="text-align: left;">9 Years :</label>
      <div class="col-sm-3">          
        <input type="number" class="form-control inputDisabler" id="year9" name="year9" oninput="validateInput(this)" required disabled>
      </div>
    </div>
    <div class="form-group" >
      <label class="control-label col-sm-2" for="year10" style="text-align: left;">10 Years :</label>
      <div class="col-sm-3">
        <input type="number" class="form-control inputDisabler" id="year10" name="year10"  oninput="validateInput(this)" required disabled>
      </div>
    </div>
    
    
    
    
 
</div>
</div>
</div>

 <div class="col-sm-6" >
<div class="container-fluid" >
 
<div class="row" style="margin-left:0%; margin-top:10px">
<div class="col-sm-4">
<label class="switch">
  <input type="checkbox" id="togBtn" disabled>
  <div class="slider round">
    <span class="on"></span>
    <span class="off"></span>
  </div>
</label>
</div>
<div class="form-group col-sm-4" id="updateForm" ;>
    <label class="control-label col-sm-3" >
    <button type="submit" class="btn btn-primary enableItem" style="margin-left:110px" disabled>Save</button>
    </label>
    </div>
    
    <div class="form-group col-sm-4" id="cancel" >
    <label class="control-label col-sm-3" for="cancel" ">
    <button type="button" class="btn btn-danger" >Cancel</button>
    </label>
    </div>
</div>
  <div class="form-group" >
      <label class="control-label col-sm-2" for="year11" style="text-align: left;">11 Years :</label>
      <div class="col-sm-3">          
        <input type="number" class="form-control inputDisabler" id="year11" name="year11" oninput="validateInput(this)" required disabled>
      </div>
    </div>
     <div class="form-group">
      <label class="control-label col-sm-2" for="year12" style="text-align: left;">12 Years :</label>
      <div class="col-sm-3">          
        <input type="number" class="form-control inputDisabler" id="year12" name="year12" oninput="validateInput(this)" required disabled>
      </div>
    </div>
  
    <div class="form-group" >
      <label class="control-label col-sm-2" for="year15" style="text-align: left;">15 Years :</label>
      <div class="col-sm-3">
        <input type="number" class="form-control inputDisabler" id="year15"  name="year15" oninput="validateInput(this)" required disabled>
      </div>
    </div>
    <div class="form-group" >
      <label class="control-label col-sm-2" for="year20" style="text-align: left;">20 Years :</label>
      <div class="col-sm-3">          
        <input type="number" class="form-control inputDisabler" id="year20" name="year20" oninput="validateInput(this)"  required disabled>
      </div>
    </div>
    <div class="form-group" >
      <label class="control-label col-sm-2" for="computers" style="text-align: left;">Computers :</label>
      <div class="col-sm-3">          
        <input type="number" class="form-control inputDisabler" id="computers" name="computers" oninput="validateInput(this)" required disabled>
      </div>
    </div>
    <div class="form-group" >
      <label class="control-label col-sm-2" for="untrended6year" style="text-align: left;">Untrended 6 Years :</label>
      <div class="col-sm-3">
        <input type="number" class="form-control inputDisabler" id="untrended6year" name="untrended6year" oninput="validateInput(this)" required disabled>
      </div>
    </div>
   
    
   
   
    </div>
    
 
</div> 
</div>
<div class="row" >
<div class="col-sm-6">		
<div class="container-fluid">
<label for="statusMsg">Status:</label><span id="statusMsg">${stat}</span>

</div>
</div>
</div>
<div class="row">
<div class="col-sm-6">		
<div class="container-fluid">
<!-- <div class="form-group" > 
    <label class="control-label col-sm-3"  style="margin-left:40px;">
    <button type="button" class="btn btn-primary enableUpdateBtn" disabled>Update</button>
    </label>
    </div> -->
    
</div>
</div>
<div class="col-sm-6">		
<div class="container-fluid">

    
   <!--  <div class="form-group" id="insertForm" style="display:none">
    <label class="control-label col-sm-3" for="saveform" style="margin-left:40px;">
    <button type="button" class="btn btn-primary enableItem" id="saveform" disabled>Save</button>
    </label>
    </div> -->
</div>
</div>
</div>

</form:form>
</div>




<div>
</div>
</div>	
</body>
<script>
$("#searchbutton").click(function(){
	var year=$("select#year").val();
	 if(year=='-1')
		{
		alert("Please Select Year");
		return false;
		} 
	  $("#statusMsg").css({"display":"none"}) 
	$("#togBtn").prop('disabled',false);
	var formYear={year:year}
	$.ajax({
		url: 'modelData',
		type: 'POST',
		data:formYear,
		dataType: "json",
	   success:function(data) {
	      console.log(data);
	      setModelValue(data);
	      $("#vyear").css({"display":"none"})
	      $(".inputDisabler").prop('disabled',true)
	       $(".enableItem").prop('disabled',true)
	       $("#insertForm").css({"display":"none"})
	   },
	error:function(){
	  
	}
	});
	
  });
  
	
	function createDinamicInp(key)
	{   
				var cont=$('<div>',{id:'cont'})
			    var odiv=$('<div>',{class:'form-group'})
			    var olabel=$('<label>',{
			    	class:"control-label col-sm-2",
			    	for:key,
			    	style:"text-align: left;"
			    })
			    
			    var idiv=$('<div>',{class:'col-sm-4'})
			    var oinp=$('<input>',{
			    	type:"number",
			    	class:"form-control",
			    	id:key,
			    	name:key,
			 		required:true,
			 		
			    })
			    olabel.text(key);
			    idiv.append(oinp)
			    odiv.append(olabel)
			    odiv.append(idiv)
			    $('#cont').append(odiv);
			    
			    
	}
  
  function setModelValue(data)
  {
	  $("#modelyear").val(data.modelyear);
	  $("#year5").val(data.year5);
	  $("#year6").val(data.year6);
	  $("#year7").val(data.year7);
	  $("#year8").val(data.year8);
	  $("#year9").val(data.year9);
	  $("#year10").val(data.year10);
	  $("#year11").val(data.year11);
	  $("#year12").val(data.year12);
	  $("#year15").val(data.year15);
	  $("#year20").val(data.year20);
	  $("#computers").val(data.computers);
	  $("#untrended6year").val(data.untrended6year);
	  
  }
  
  $("#newbutton").click(function(){ 
	  
	  $("#searchbutton").prop('disabled',true)
	  $("#cancel").css({"display":"block"})
	  $(".enableItem").prop('disabled',false)
	  $("#statusMsg").css({"display":"none"}) 
	  $('#year').val('-1')
	  $("#year").prop('disabled',true)
	  $(".inputDisabler").prop('disabled',true)
	  $('#modelyear').css({"display":"block"})
	  $('#vyear').css({"display":"block"})
	  $("input[type='number']").val("")
	 $("#togBtn").prop({"checked":true,"disabled":false})
	 $(".inputDisabler").prop("disabled",false);
	   
	    
	   
  })
  

    function validateInput(input)
  {
	var inputValue=parseInt(input.value);
	
	/* if(isNaN(inputValue))
		{
		alert("please enter a valid number");
		input.value='';
		} */
	 if(inputValue<0 || inputValue>=99)
		{
		alert("please enter a valid number");
		input.value='';
		}
  } 
  function validateYear(input)
  {
	var inputValue=parseInt(input.value);
	
	/* if(isNaN(inputValue))
		{
		alert("please enter a valid number");
		input.value='';
		} */
	 if(inputValue<1 || inputValue>=2100)
		{
		alert("please enter a valid number");
		input.value='';
		}
  } 
  
 /*   function validateAllInput()
  {
	  var inputs=document.querySelectorAll('input[type="number"]');
	  for(var i=0;i<inputs.length;i++)
		  {
		  validateInput(inputs[i]);
		  }
  }  */ 
   
   $("#togBtn").change(function()
	{
	   if($("#togBtn").prop("checked"))
		   {
		   $(".inputDisabler").prop("disabled",false);
		   $(".enableItem").prop('disabled',false);
		   $("#cancel").css({"display":"block"})
		   }
	   else 
		   {
		   $(".inputDisabler").prop("disabled",true);
		   $(".enableItem").prop('disabled',true);
		   }
	  
	   
	   
	}	   
   )
   $("#cancel").click(function()
	{
	   $('#year').val('-1')
	   $("input[type='number']").val("");
	   $(".inputDisabler").prop("disabled",true);
	   $(".enableUpdateBtn").prop('disabled',false)
	    $(".enableItem").prop('disabled',true)
	   $("#togBtn").prop({"checked":false,"disabled":true})
	   
	   
	  
	    
	   
	   
	}	   
   )
   
   $("#excelDown").click(function()
		   {
	   $.ajax({
		   url:'excelExport',
		   type:'GET',
		   success:function(data){console.log("hit");
		   var link=document.createElement('a');
		   link.href="excelExport";
		   document.body.appendChild(link);
		   link.click();
		   document.body.removeChild(link);
		   },
		   error:function(){}
	   })
		   })
</script>
</html>