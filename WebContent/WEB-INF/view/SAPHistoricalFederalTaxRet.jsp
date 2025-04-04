<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<style>
/* .smallInput{width:100%; border:0;} */
/* loading spinner*/

@keyframes ScaleInOut {
    0% {
        transform: scale(0.8);
    }

    100% {
        transform: scale(1.5);
    }
}

@keyframes Rotate {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
.spinner-svg {
    animation: Rotate 1.6s linear 0s infinite forwards;
}

 .stack-top{
 		position:absolute;
        margin-top:250px;
        margin-left:48%;
        display:none;
        z-index:10;    
    }


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
  min-width: 250px;
    margin-left: -125px;
    background-color: #333;
    color: #fff;
    text-align: center;
    border-radius: 2px;
    padding: 16px;
    display:none;
    position: fixed;
    z-index: 10;
    left: 50%;
    margin-top:330px;
    font-size: 17px;
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

.background
{
	
	filter:blur(0px);
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
		//search('yes');
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
			$(".stack-top").css("display","block");
			$("#snackbar").css("display","block");
			$(".background").css("filter","blur(4px)")
			localStorage.setItem("tick", 1);
		    excelDownload();
		    
		    
		});
		
		 $('#companyGroup').change(function(event) {
			 
			var groupName = $("select#companyGroup").val();
			$.getJSON('getSAPHistFederalTaxRetirementCompanyNames', {
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
		 $('#year').change(function(event) {
			 
				var reportyear = $("select#year").val();
				$.getJSON('getSAPHistoricalFederaltaxretReportNames', {
					reportyear : reportyear
				}, function(data) {
					var select = $('#reportName');
					select.find('option').remove();
					$('<option>').val('-1').text('Select Report').appendTo(select);
					$.each(data, function(key, value) {	
						//alert(key+"----"+value)
						$('<option>').val(key).text(value).appendTo(select);
						
		                
					});
					$('#reportName').addClass("form-control");
					$('#reportName').css("font-family: Calibri; font-size: 12px;");
				});
			}); 
		 
		 $('#reportName').change(function(event) {
				//console.log("clicked");
			  destroyTable();
			  var year=$("select#year").val();	
			  var reportId=$("select#reportName").val();
			  var urll="getSapHistIncTaxRetHeadings?reportId="+reportId+'&year='+year;
				jQuery.ajax({type: "post",url: urll,dataType: "json",cache: false,
					   success:function(headers) {
						// console.log(headers);
						 
						   var tableHeaders = "<thead><tr id='header' role='row'>";
			                headers.forEach(function(header) {
			                    tableHeaders += "<th>" + header + "</th>";
			                });
			                tableHeaders += "</tr></thead>";
			                $("#myDatatable").html(tableHeaders);
					   },error:function(){
							  //alert("Session Expired.Please login again.");
						}
				  
			  });
	}); 
	});
	let tvar;
	function destroyTable()
	{
		if($.fn.DataTable.isDataTable('#myDatatable'))
			{
			tvar.destroy();
			}
		$('#myDatatable').addClass('dataTable');
	}
	
	function getcookie(name = '') {
	    let cookies = document.cookie;
	    let cookiestore = {};
	    
	    cookies = cookies.split(";");
	    
	    if (cookies[0] == "" && cookies[0][0] == undefined) {
	        return undefined;
	    }
	    
	    cookies.forEach(function(cookie) {
	        cookie = cookie.split(/=(.+)/);
	        if (cookie[0].substr(0, 1) == ' ') {
	            cookie[0] = cookie[0].substr(1);
	        }
	        cookiestore[cookie[0]] = cookie[1];
	    });
	    
	    return (name !== '' ? cookiestore[name] : cookiestore);
	}
	
	function excelDownload(){
		
        
		var form = document.createElement("form");
		form.action = "downloadExcelSAPHistFedtaxRetData";
		form.method = "POST";
		var facompanygroup=$("select#companyGroup").val();		
		var facompany=$("select#companyName").val();
		var taxInstallDateFrom=$("#datepicker").val();
		var taxInstallDateTo=$("#datepicker1").val();
		var taxCreateDateFrom=$("#datepicker2").val();
		var taxCreateDateTo=$("#datepicker3").val();
		var year=$("select#year").val();		
		var reportId=$("select#reportName").val().trim();
		if (facompany === "-1") {
			facompany="";
		}
		
		
		var data={facompanygroup:escape(facompanygroup),facompany:escape(facompany),taxInstallDateFrom:escape(taxInstallDateFrom),taxInstallDateTo:escape(taxInstallDateTo)
				,year:escape(year),reportId:escape(reportId),excelDownloadFlow:'true'};
		
	
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
		
		document.cookie = "downloadStarted=0";
		console.log(getcookie('downloadStarted'));
		
		
		newcookie();
	}
	
	function newcookie(){
		var c2k=getcookie('downloadStarted');
		const myVar = setTimeout(newcookie, 2000);
		//console.log("checking")
		if(c2k==1)
			{
			$(".stack-top").css("display","none");
			$("#snackbar").css("display","none");
			$(".background").css("filter","blur(0px)")
			localStorage.setItem("tick",2);
			clearTimeout(myVar);
			console.log("cleared timeout")
			}
     
    }
	
	function search(initload){
		var url='loadServerSideSAPHistFedtaxRetData?';
		var facompanygroup=$("select#companyGroup").val();	
		var facompany=$("select#companyName").val();		
		var taxInstallDateFrom=$("#datepicker").val();
		var taxInstallDateTo=$("#datepicker1").val();
		var taxCreateDateFrom=$("#datepicker2").val();
		var taxCreateDateTo=$("#datepicker3").val();
		var year=$("select#year").val();		
		var reportId=$("select#reportName").val();
		
		
		if (facompany === "-1") {
			facompany="";
		}
		
		if (initload == "yes") {
			url=url+'&facompany=dummycompany123456';
		}else{
			url=url+'&facompanygroup='+facompanygroup+'&facompany='+facompany+'&taxInstallDateFrom='+taxInstallDateFrom+'&taxInstallDateTo='+taxInstallDateTo
			+'&year='+year+'&reportId='+reportId;
		}
		
		console.log(url);
		tvar= $('#myDatatable').DataTable({
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
<div class="h-100 d-flex align-items-center justify-content-center" >
		<div id="spin" class="stack-top">
            <!--svg image-->
            <svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                x="0px" y="0px" width="50px" height="50px" class="spinner-svg" viewBox="0 0 456.817 456.817"
                style="enable-background:new 0 0 456.817 456.817;" xml:space="preserve">
                <g>
                    <g>
                        <path
                            d="M109.641,324.332c-11.423,0-21.13,3.997-29.125,11.991c-7.992,8.001-11.991,17.706-11.991,29.129
    c0,11.424,3.996,21.129,11.991,29.13c7.998,7.994,17.705,11.991,29.125,11.991c11.231,0,20.889-3.997,28.98-11.991
    c8.088-7.991,12.132-17.706,12.132-29.13c0-11.423-4.043-21.121-12.132-29.129C130.529,328.336,120.872,324.332,109.641,324.332z" />
                        <path
                            d="M100.505,237.542c0-12.562-4.471-23.313-13.418-32.267c-8.946-8.946-19.702-13.418-32.264-13.418
    c-12.563,0-23.317,4.473-32.264,13.418c-8.945,8.947-13.417,19.701-13.417,32.267c0,12.56,4.471,23.309,13.417,32.258
    c8.947,8.949,19.701,13.422,32.264,13.422c12.562,0,23.318-4.473,32.264-13.422C96.034,260.857,100.505,250.102,100.505,237.542z" />
                        <path d="M365.454,132.48c6.276,0,11.662-2.24,16.129-6.711c4.473-4.475,6.714-9.854,6.714-16.134
    c0-6.283-2.241-11.658-6.714-16.13c-4.47-4.475-9.853-6.711-16.129-6.711c-6.283,0-11.663,2.24-16.136,6.711
    c-4.47,4.473-6.707,9.847-6.707,16.13s2.237,11.659,6.707,16.134C353.791,130.244,359.171,132.48,365.454,132.48z" />
                        <path
                            d="M109.644,59.388c-13.897,0-25.745,4.902-35.548,14.703c-9.804,9.801-14.703,21.65-14.703,35.544
    c0,13.899,4.899,25.743,14.703,35.548c9.806,9.804,21.654,14.705,35.548,14.705s25.743-4.904,35.544-14.705
    c9.801-9.805,14.703-21.652,14.703-35.548c0-13.894-4.902-25.743-14.703-35.544C135.387,64.29,123.538,59.388,109.644,59.388z" />
                        <path
                            d="M439.684,218.125c-5.328-5.33-11.799-7.992-19.41-7.992c-7.618,0-14.089,2.662-19.417,7.992
    c-5.325,5.33-7.987,11.803-7.987,19.421c0,7.61,2.662,14.092,7.987,19.41c5.331,5.332,11.799,7.994,19.417,7.994
    c7.611,0,14.086-2.662,19.41-7.994c5.332-5.324,7.991-11.8,7.991-19.41C447.675,229.932,445.02,223.458,439.684,218.125z" />
                        <path
                            d="M365.454,333.473c-8.761,0-16.279,3.138-22.562,9.421c-6.276,6.276-9.418,13.798-9.418,22.559
    c0,8.754,3.142,16.276,9.418,22.56c6.283,6.282,13.802,9.417,22.562,9.417c8.754,0,16.272-3.141,22.555-9.417
    c6.283-6.283,9.422-13.802,9.422-22.56c0-8.761-3.139-16.275-9.422-22.559C381.727,336.61,374.208,333.473,365.454,333.473z" />
                        <path d="M237.547,383.717c-10.088,0-18.702,3.576-25.844,10.715c-7.135,7.139-10.705,15.748-10.705,25.837
    s3.566,18.699,10.705,25.837c7.142,7.139,15.752,10.712,25.844,10.712c10.089,0,18.699-3.573,25.838-10.712
    c7.139-7.138,10.708-15.748,10.708-25.837s-3.569-18.698-10.708-25.837S247.636,383.717,237.547,383.717z" />
                        <path
                            d="M237.547,0c-15.225,0-28.174,5.327-38.834,15.986c-10.657,10.66-15.986,23.606-15.986,38.832
    c0,15.227,5.327,28.167,15.986,38.828c10.66,10.657,23.606,15.987,38.834,15.987c15.232,0,28.172-5.327,38.828-15.987
    c10.656-10.656,15.985-23.601,15.985-38.828c0-15.225-5.329-28.168-15.985-38.832C265.719,5.33,252.779,0,237.547,0z" />
                    </g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
                <g>
                </g>
            </svg>
    </div>
    </div>
 <div id="snackbar">Downloading Please Wait...</div>
	<div class="background">
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
							<b >SAP Historical Federal Tax Asset Retirement</b>
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
										<div class=" col-lg-2 col-sm-2 " style="margin-left: 5px;">Year</div>
										<div class=" col-lg-2 col-sm-2 " style="margin-left: -95px;">Report Name</div>
										<div class=" col-lg-2 col-sm-2 " style="margin-left: -75px;">Business Group</div>
										<div class=" col-lg-2 col-sm-2 " style="margin-left: -92px;">Company Code</div>
										<!-- 13sep -->
										<div class="col-lg-4 col-sm-4 " style="margin-top: 3px;margin-left: -70px;">Retirement Date From -  Retirement Date  To</div>
										<!-- 13sep -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Installation From Date</div> -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Installation To Date</div> -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Creation From Date</div>
										<div class="col-lg-2 col-sm-2 ">Tax Creation To Date</div> -->
										<!-- <div class="col-lg-4 col-sm-4 " style="margin-left: -198px; margin-top: 3px;">Tax Creation From - To Date</div>-->
										<div class=" col-lg-1 col-sm-1 "></div>
									</div>
           
                                      <div class="row" style="margin-left: 1px;">
										<div class="form-group col-lg-2 col-sm-2 ">
											<!-- <select class="form-control" id="compGrp" name="compGrp" path="compGrp" autocomplete="off" style="font-family: Calibri; font-size: 12px;"><option value="ALL">ALL</option><option value="ALL250">ALL250</option><option value="ALLTLMD">ALLTLMD</option><option value="CIM">CIM</option><option value="CORPHQ">CORPHQ</option><option value="CPFA">CPFA</option><option value="CPG">CPG</option><option value="DWA">DWA</option><option value="E!">E!</option><option value="FA200 + FA250">FA200 + FA250</option><option value="FA250 - ALL CO">FA250 - ALL CO</option><option value="FILM">FILM</option><option value="G4">G4</option><option value="GOLF">GOLF</option><option value="GROUP1">GROUP1</option><option value="GROUP2">GROUP2</option><option value="GROUP3">GROUP3</option><option value="GROUP4">GROUP4</option><option value="IMD">IMD</option><option value="IVILLAGE">IVILLAGE</option><option value="LEGACY NBC">LEGACY NBC</option><option value="LOCAL MEDIA">LOCAL MEDIA</option><option value="LVG&amp;0SD">LVG&amp;0SD</option><option value="MSNBC &amp; WEATHER PLUS">MSNBC &amp; WEATHER PLUS</option><option value="O&amp;T">O&amp;T</option><option value="RSN">RSN</option><option value="SPROUT">SPROUT</option><option value="STUDIOS">STUDIOS</option><option value="T:1_COMCAST CRAFTSY">T:1_COMCAST CRAFTSY</option><option value="T:1_DOMESTIC C-CORP">T:1_DOMESTIC C-CORP</option><option value="T:1_DOMESTIC JV">T:1_DOMESTIC JV</option><option value="T:3_UNI DOMESTIC">T:3_UNI DOMESTIC</option><option value="T:4_LEGACY DOMESTIC">T:4_LEGACY DOMESTIC</option><option value="T:5_TLMD DOMESTIC">T:5_TLMD DOMESTIC</option><option value="T:LA-BROADCAST TV">T:LA-BROADCAST TV</option><option value="T:LA-CABLE NET">T:LA-CABLE NET</option><option value="T:LA-CORP&amp;OTHER">T:LA-CORP&amp;OTHER</option><option value="T:LA-DREAMWORKS">T:LA-DREAMWORKS</option><option value="T:LA-FANDANGO">T:LA-FANDANGO</option><option value="T:LA-FILM">T:LA-FILM</option><option value="T:LA-VIDEO&amp;CONS PROD">T:LA-VIDEO&amp;CONS PROD</option><option value="T:NY-BROADCAST TV">T:NY-BROADCAST TV</option><option value="T:NY-CABLE NET">T:NY-CABLE NET</option><option value="T:NY-CORP&amp;OTHER">T:NY-CORP&amp;OTHER</option><option value="T:NY-LIQ/INACTIVE/NC">T:NY-LIQ/INACTIVE/NC</option><option value="T:NY-NEWS">T:NY-NEWS</option><option value="T:NY-OWNED STATIONS">T:NY-OWNED STATIONS</option><option value="T:NY-RSN">T:NY-RSN</option><option value="T:NY-SPORTS">T:NY-SPORTS</option><option value="T:NY-STATION VENTURE">T:NY-STATION VENTURE</option><option value="T:NY-STUDIO OPS">T:NY-STUDIO OPS</option><option value="TELEMUNDO">TELEMUNDO</option><option value="TELEMUNDO STATIONS">TELEMUNDO STATIONS</option><option value="TEMPGRP">TEMPGRP</option><option value="TEST_250">TEST_250</option><option value="TOPS">TOPS</option><option value="TOPS+MSNBC">TOPS+MSNBC</option><option value="TVSD">TVSD</option><option value="VERSUS">VERSUS</option></select> -->
											<select class="form-control" name="year" id="year" style="font-family: Calibri; font-size: 11px; width:130px">
												<option value="-1">Select Year</option> 
												<!-- <option value="-1"></option>-->
												<c:forEach var="item" items="${yearMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>

										</div>

										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -30px;">
										<select class="form-control" name="reportName" id="reportName" style="font-family: Calibri; font-size: 12px; width: 155px;paddding-left: 162px;margin-left: -31%;">
												 <option value="-1">Select ReportName</option> 
<!-- 												<option value="-1"></option> -->
												<c:forEach var="item" items="${reportNamesMap}">
<%-- 													<option >${item}</option> --%>
                                                 <option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>
	<!-- 										<select class="form-control" id="companyCode" name="companyCode" path="companyCode" autocomplete="off" style="font-family: Calibri; font-size: 12px;"><option value="" selected="selected">SELECT</option><option value="001">001 - TVSD 2002</option><option value="003">003 - ARTHOUSE</option><option value="004">004 - NBC STN MGMT,INC./WCAU L.P.</option><option value="005">005 - NBC STN MGMT,INC./WTVJ L.P.R</option><option value="007">007 - HOME VIDEO</option><option value="009">009 - NBC STUDIOS,INC.(U.M.S.)</option><option value="010">010 - FOCUS FEATURES</option><option value="012">012 - BROADCAST TV RETRANS</option><option value="015">015 - NBC NEWS BUREAUS, INC.</option><option value="028">028 - CPG MERCHANDISING</option><option value="030">030 - STUDIO PRODUCTION SERVICES</option><option value="031">031 - REAL ESTATE SERVICES</option><option value="032">032 - EARTH PROPERTIES</option><option value="036">036 - BRENNAN HOLDINGS, INC.</option><option value="037">037 - NBC NEWS CHANNEL INC.</option><option value="040">040 - NBC WEST</option><option value="041">041 - ALBUQUERQUE STUDIOS</option><option value="042">042 - FILMMAKER PRODUCTION SERVICES</option><option value="043">043 - FM PRODUCTION SERVICES (LIC)</option><option value="047">047 - CNBC,INC.</option><option value="053">053 - USA TV</option><option value="059">059 - UNIVERSAL TALK TV</option><option value="060">060 - 10UCP</option><option value="061">061 - NBCUNIVERSAL CAHUENGA LLC</option><option value="071">071 - NBC NEWS WORLDWIDE, INC.</option><option value="076">076 - NBC OLYMPICS,INC.</option><option value="077">077 - MSNBC</option><option value="082">082 - UNIVERSAL TV GROUP</option><option value="083">083 - NBC CORPORATION 83</option><option value="0A0">0A0 - BRAVO CORP</option><option value="0A1">0A1 - ENTERTAINMENT</option><option value="0A2">0A2 - NEWS</option><option value="0A3">0A3 - SPORTS</option><option value="0A4">0A4 - OLYMPICS</option><option value="0A5">0A5 - B&amp;NO</option><option value="0A6">0A6 - CORPORATE STAFF</option><option value="0A7">0A7 - TV NETWORK</option><option value="0EB">0EB - TELEMUNDO MEXICO</option><option value="0F1">0F1 - NBC FACILITIES,INC.-BN&amp;O</option><option value="0IC">0IC - IVPN (LAMAZE - IP)</option><option value="0JE">0JE - KIT CO</option><option value="0KA">0KA - USA NETWORK</option><option value="0KB">0KB - SCI FI CHANNEL, NY</option><option value="0KG">0KG - UTN CORP</option><option value="0KH">0KH - CABLE AFFILIATE</option><option value="0KQ">0KQ - CRAFTSY</option><option value="0KZ">0KZ - TVG WC OH</option><option value="0P5">0P5 - THE NBC EXPERIENCE</option><option value="0P8">0P8 - INTERACTIVE MEDIA</option><option value="0Q1">0Q1 - WZDC - DC</option><option value="0Q2">0Q2 - KTLM RIO GRANDE</option><option value="0Q3">0Q3 - WRDM  - HARTFORD</option><option value="0Q4">0Q4 - KHRR TUCSON, AZ</option><option value="0Q6">0Q6 - TELEMUNDO TELEVISION STUDIOS</option><option value="0Q8">0Q8 - SAN DIEGO SPANISH CHANNEL</option><option value="0Q9">0Q9 - KBLR-LAS VEGAS</option><option value="0QA">0QA - KVEA, LOS ANGELES, CA</option><option value="0QB">0QB - 0QB KWHY, LOS ANGELES, CA</option><option value="0QC">0QC - WSCV MIAMI, FLA</option><option value="0QD">0QD - WNJU, NEW YORK NY</option><option value="0QE">0QE - KXTX DALLAS, TX (REVISED 2004)</option><option value="0QF">0QF - WSNS CHICAGO, IL</option><option value="0QG">0QG - KSTS, SAN FRANCISCO</option><option value="0QH">0QH - KVDA SAN ANTONIO, TX</option><option value="0QI">0QI - KTMD HOUSTON, TX</option><option value="0QJ">0QJ - KMAS, DENVER</option><option value="0QK">0QK - TELEXITOS TV</option><option value="0QL">0QL - WWSI ATLANTIC CITY</option><option value="0QN">0QN - TLMD NATIONAL SALES</option><option value="0QO">0QO - TLMD DIVISION</option><option value="0QR">0QR - OQR TELEMUNDO NETWORK GROUP</option><option value="0QS">0QS - OQS GEMS INTERNATIONAL TV LLP</option><option value="0QU">0QU - 0QU TLMD NETWORK TEXAS SALES</option><option value="0QV">0QV - TELEMUNDO COMMUNICATIONS</option><option value="0QW">0QW - KDRX/KPHZ PHOENIX, AZ</option><option value="0QX">0QX - WNEU-BOSTON</option><option value="0QY">0QY - WKAQ, PR</option><option value="0QZ">0QZ - KNSO, FRESNO</option><option value="0R0">0R0 - SKYCASTLE T0R0</option><option value="0R5">0R5 - OUT OF HOME</option><option value="0R6">0R6 - LOCAL INTEGRATED MEDIA</option><option value="0R7">0R7 - COZI TV</option><option value="0R8">0R8 - OXYGEN</option><option value="0R9">0R9 - LX TV</option><option value="0RB">0RB - INTERNET &amp; BROADBAND</option><option value="0RF">0RF - 0RF, TELEMUNDO CONTENT CENTER</option><option value="0RH">0RH - IVILLAGE</option><option value="0RI">0RI - HEALTHCENTERSONLINE</option><option value="0RJ">0RJ - HEALTHOLOGY</option><option value="0RK">0RK - IVPN (LAMAZE - IM)</option><option value="0RL">0RL - ASTROLOGY</option><option value="0RM">0RM - PROMOTIONS.COM</option><option value="0RQ">0RQ - NBCSPORTS.COM</option><option value="0RV">0RV - ORV TLMD INTERNATIONAL DISTRI</option><option value="0RZ">0RZ - NBC DIGITAL MEDIA, INC</option><option value="0S1">0S1 - BRAVO B&amp;NO</option><option value="0S6">0S6 - BRAVO NETWORK</option><option value="0S9">0S9 - NBC UNIVERSAL WIRELESS INC.</option><option value="0SC">0SC - KCSO SACRAMENTO STATIONS</option><option value="0SD">0SD - TELEMUNDO SAN DIEGO</option><option value="0SL">0SL - KTMW SALT LAKE CITY</option><option value="0T1">0T1 - WNBC, NEW YORK</option><option value="0T3">0T3 - WMAQ CHICAGO</option><option value="0T4">0T4 - KNBC - LOS ANGELES, CA</option><option value="0T9">0T9 - WCAU, PHILADELPHIA</option><option value="0TB">0TB - WRC, WASHINGTON</option><option value="0TC">0TC - WTVJ, MIAMI, FLA</option><option value="0TF">0TF - NSO 2000 YEAREND</option><option value="0TJ">0TJ - WBTS - BOSTON</option><option value="0TM">0TM - MASTER CONTROL HUB</option><option value="0TS">0TS - KNSD, SAN DIEGO</option><option value="0TX">0TX - KXAS, FT WORTH / DALLAS</option><option value="0VG">0VG - KNTV, SAN JOSE</option><option value="0VH">0VH - WVIT-HARTFORD</option><option value="0W5">0W5 - B&amp;NO WEST LLC</option><option value="0W6">0W6 - CORP STAFF WEST LLC</option><option value="0W7">0W7 - WEATHER PLUS NETWORK LLC -0W7</option><option value="0W9">0W9 - WEATHER PLUS NETWORK LLC -0W9</option><option value="401">401 - UNIVERSAL FILM EXCHANGE</option><option value="404">404 - UNIVERSAL PICTURES</option><option value="407">407 - MP PRODUCTION</option><option value="475">475 - UNIVERSAL STUDIO LICENSING,INC</option><option value="621">621 - MCA TV LIMITED</option><option value="DC1">DC1 - DAILY CANDY</option><option value="DC2">DC2 - DAILY CANDY GROUP BUYING</option><option value="DF1">DF1 - FANDANGO</option><option value="DF2">DF2 - FANDANGO MARKETING</option><option value="DF3">DF3 - MOVIES.COM</option><option value="DF6">DF6 - MEDIANAVICO LLC</option><option value="DF7">DF7 - FLIXTER ROTTEN TOMATOES</option><option value="DF9">DF9 - FANDANGO MERCHANDISINGLLC</option><option value="DW1">DW1 - SWIRL</option><option value="DW2">DW2 - COMCAST DIGITAL</option><option value="DW3">DW3 - RHQ</option><option value="ES1">ES1 - WTMO - ORLANDO</option><option value="ES2">ES2 - WRMD - TAMPA</option><option value="ES3">ES3 - WWDT - FT MYERS</option><option value="ES4">ES4 - KTDO - EL PASO</option><option value="ES5">ES5 - WDMR - SPRINGFIELD</option><option value="ES6">ES6 - WRIW - PROVIDENCE</option><option value="ES7">ES7 - WZTD - RICHMOND</option><option value="ES8">ES8 - LIQ-TELEMUNDO NATI SALES,</option><option value="ES9">ES9 - WZGS - RALEIGH</option><option value="F02">F02 - UNIVERSAL TELEVISION GROUP</option><option value="F21">F21 - VUE</option><option value="F22">F22 - CRAFTSY(TAX)</option><option value="JA3">JA3 - NEWS ONLINE</option><option value="JA4">JA4 - MSNBC ONLINE</option><option value="KEA">KEA - E! ENTERTAINMENT TELEVISION</option><option value="KEF">KEF - E! NETWORKS PROD-E!</option><option value="KEG">KEG - COMCAST ENTERTAINMENT PRO</option><option value="KFA">KFA - STYLE</option><option value="KGA">KGA - G4 MEDIA INC</option><option value="KNA">KNA - IMD</option><option value="KPA">KPA - SPROUT</option><option value="KTA">KTA - COMCAST SHARED SERVICES CORP.</option><option value="KWA">KWA - COMCAST SPORTSGROUP MANAGEMENT</option><option value="KWB">KWB - CSN MIDATLANTIC</option><option value="KWC">KWC - CSN NORTHWEST C</option><option value="KWD">KWD - CSN WEST</option><option value="KWF">KWF - CSN PHILADELPHIA</option><option value="KWG">KWG - NBC SPORTS CHICAGO</option><option value="KWH">KWH - MOUNTAIN WEST SPORTS NETWORK</option><option value="KWJ">KWJ - CSN NEW ENGLAND</option><option value="KWL">KWL - CSN SAN FRANCISCO</option><option value="KWN">KWN - COMCAST CHARTER SPORTS SOUTHEA</option><option value="KWP">KWP - COMCAST SPORTS SOUTHWEST LLC</option><option value="KWQ">KWQ - THE COMCAST NETWORK PHILADELPH</option><option value="KWR">KWR - CN MIDATLANTIC</option><option value="KWS">KWS - NEW ENGLAND CABLE NEWS</option><option value="KWU">KWU - CSN CHICAGO</option><option value="LGA">LGA - GOLFNOW</option><option value="LGG">LGG - THE GOLF CHANNEL</option><option value="LGH">LGH - BRS GOLF LIMITED</option><option value="LND">LND - ALLIANCE OF ACTION SPORTS LLC.</option><option value="LVA">LVA - VERSUS</option><option value="LVB">LVB - SPORTS GROUP HQ</option><option value="LVD">LVD - FITNESS VIDEO VENTURES, LLC</option><option value="LVF">LVF - OLYMPICS CHANNEL</option><option value="LVG">LVG - SPORT NGIN</option><option value="S01">S01 - STUDIO OPERATION</option><option value="U05">U05 - PACIFIC DATA IMGS L.L.C.</option><option value="U07">U07 - DWA ONLINE, INC.</option><option value="U0E">U0E - DWA NOVA, LLC</option><option value="U10">U10 - DW ANIMATION L.L.C.</option><option value="U15">U15 - ASIA CHANNEL</option><option value="U20">U20 - CLASSIC MEDIA, LLC</option><option value="U25">U25 - BIG IDEA ENT., LLC</option><option value="U30">U30 - DWA TELEVISION, LLC</option><option value="U50">U50 - DW CONSUMER PRODUCTS</option><option value="U51">U51 - DWA LICENSING, LLC</option><option value="U60">U60 - DWA THEATRICAL MANAGEMENT</option><option value="U65">U65 - DRAGON ARENA SHOW</option></select> -->
										</div>


									<div class="row" style="margin-left: 1px;">
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -150px;">
											<!-- <select class="form-control" id="compGrp" name="compGrp" path="compGrp" autocomplete="off" style="font-family: Calibri; font-size: 12px;"><option value="ALL">ALL</option><option value="ALL250">ALL250</option><option value="ALLTLMD">ALLTLMD</option><option value="CIM">CIM</option><option value="CORPHQ">CORPHQ</option><option value="CPFA">CPFA</option><option value="CPG">CPG</option><option value="DWA">DWA</option><option value="E!">E!</option><option value="FA200 + FA250">FA200 + FA250</option><option value="FA250 - ALL CO">FA250 - ALL CO</option><option value="FILM">FILM</option><option value="G4">G4</option><option value="GOLF">GOLF</option><option value="GROUP1">GROUP1</option><option value="GROUP2">GROUP2</option><option value="GROUP3">GROUP3</option><option value="GROUP4">GROUP4</option><option value="IMD">IMD</option><option value="IVILLAGE">IVILLAGE</option><option value="LEGACY NBC">LEGACY NBC</option><option value="LOCAL MEDIA">LOCAL MEDIA</option><option value="LVG&amp;0SD">LVG&amp;0SD</option><option value="MSNBC &amp; WEATHER PLUS">MSNBC &amp; WEATHER PLUS</option><option value="O&amp;T">O&amp;T</option><option value="RSN">RSN</option><option value="SPROUT">SPROUT</option><option value="STUDIOS">STUDIOS</option><option value="T:1_COMCAST CRAFTSY">T:1_COMCAST CRAFTSY</option><option value="T:1_DOMESTIC C-CORP">T:1_DOMESTIC C-CORP</option><option value="T:1_DOMESTIC JV">T:1_DOMESTIC JV</option><option value="T:3_UNI DOMESTIC">T:3_UNI DOMESTIC</option><option value="T:4_LEGACY DOMESTIC">T:4_LEGACY DOMESTIC</option><option value="T:5_TLMD DOMESTIC">T:5_TLMD DOMESTIC</option><option value="T:LA-BROADCAST TV">T:LA-BROADCAST TV</option><option value="T:LA-CABLE NET">T:LA-CABLE NET</option><option value="T:LA-CORP&amp;OTHER">T:LA-CORP&amp;OTHER</option><option value="T:LA-DREAMWORKS">T:LA-DREAMWORKS</option><option value="T:LA-FANDANGO">T:LA-FANDANGO</option><option value="T:LA-FILM">T:LA-FILM</option><option value="T:LA-VIDEO&amp;CONS PROD">T:LA-VIDEO&amp;CONS PROD</option><option value="T:NY-BROADCAST TV">T:NY-BROADCAST TV</option><option value="T:NY-CABLE NET">T:NY-CABLE NET</option><option value="T:NY-CORP&amp;OTHER">T:NY-CORP&amp;OTHER</option><option value="T:NY-LIQ/INACTIVE/NC">T:NY-LIQ/INACTIVE/NC</option><option value="T:NY-NEWS">T:NY-NEWS</option><option value="T:NY-OWNED STATIONS">T:NY-OWNED STATIONS</option><option value="T:NY-RSN">T:NY-RSN</option><option value="T:NY-SPORTS">T:NY-SPORTS</option><option value="T:NY-STATION VENTURE">T:NY-STATION VENTURE</option><option value="T:NY-STUDIO OPS">T:NY-STUDIO OPS</option><option value="TELEMUNDO">TELEMUNDO</option><option value="TELEMUNDO STATIONS">TELEMUNDO STATIONS</option><option value="TEMPGRP">TEMPGRP</option><option value="TEST_250">TEST_250</option><option value="TOPS">TOPS</option><option value="TOPS+MSNBC">TOPS+MSNBC</option><option value="TVSD">TVSD</option><option value="VERSUS">VERSUS</option></select> -->
											<select class="form-control" name="companyGroup" id="companyGroup" style="font-family: Calibri; font-size: 11px; width:130px">
												<option value="-1">Select Group</option> 
												<!-- <option value="-1"></option>-->
												<c:forEach var="item" items="${companyGroupsMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>

										</div>

										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -30px;">
										<select class="form-control" name="companyName" id="companyName" style="font-family: Calibri; font-size: 12px; width: 155px;paddding-left: 162px;margin-left: -31%;">
												 <option value="-1">Select CompanyName</option> 
												<option value="-1"></option>
												<c:forEach var="item" items="${companyNamesMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>
	
										</div>

										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -142px;">

										<div class="form-control "style="width: 60%; padding: 4px 12px;">
												<!--   <i input type="text" class="fa fa-calendar"	class="form-control" id="installedFrom" name="installedFrom" path="installedFrom" autocomplete="off" style="font-size: 18px"></i>-->
												<input id="datepicker" name="installedFrom" type="text" class="smallInput calImg" value="" autocomplete="off">
												
											</div>
										</div>
										<!-- 13sep -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -7%;">

										<div class="form-control "style="width: 60%; padding: 4px 12px;">
										<input id="datepicker1" name="installedTo" type="text" class="smallInput calImg" value="" autocomplete="off">
										</div>
										</div>
										<!-- 13sep -->
										




<!-- 										<div class="form-group col-lg-2 col-sm-2 "style="margin-left: -7%;"> -->
<!-- 											<div class="form-control "style="width: 60%; padding: 4px 12px;"> -->
<!-- 												<i input type="text" class="fa fa-calendar"	class="form-control" id="creationFrom" name="creationFrom" path="creationFrom" autocomplete="off" style="font-size: 18px"></i> -->
<!-- 												<input id="datepicker2" name="creationFrom" type="text" class="smallInput calImg" value="" autocomplete="off"> -->
												
<!-- 											</div> -->
<!-- 										</div> -->
										
<!-- 										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -70px;"> -->
<!-- 										<div class="form-group col-lg-2 col-sm-2 "> -->
<!-- 											<div class="form-control "style="width: 60%; padding: 4px 12px;margin-left: -14%;"> -->
<!-- 										<input id="datepicker3" name="creationTo" type="text" class="smallInput calImg" value="" autocomplete="off"> -->
<!-- 										</div> -->
<!-- 										</div> -->
										
										<div class="form-group col-lg-1 col-sm-1 ">
										<!-- <button id="searchbutton">Searchbutton</button> -->
										<!-- <div class="form-group col-lg-1 col-sm-1 "> -->
										<a id="searchbutton" href="#" class="btn btn-info btn-md" data-toggle="tooltip" data-placement="right" title="Excute" style="margin-left: -30%;"> <span class="glyphicon glyphicon-play" id="executeBtn" style="font-size: 100%;margin-top: 3%;"></span>
											</a>
											</div>
											
											<div class="form-group col-lg-1 col-sm-1 ">
											<a id="excelDownloadButton" href="#" data-toggle="tooltip" data-placement="right" title="Download To Excel"> <i class="fa fa-file-excel-o" id="excelBtn" style="font-size: 200%;margin-left: -53%;"></i></a>
											</div>
											
											
											
											

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
	<div class="tableDiv" style="margin-top: 4px;margin-left:5px;margin-right:5px;overflow-x:scroll">
		<table id="myDatatable" class="table table-striped table-bordered dataTable no-footer" role="grid" aria-describedby="tickettable_info" style="width: 3971px;font-size: 12px;text-align: center">
	
	</table>
		</div>
		<!-- naidu -->
		</div>
 </body>
</html>