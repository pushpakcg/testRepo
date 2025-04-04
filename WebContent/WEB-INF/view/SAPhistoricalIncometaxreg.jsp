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
/* spinner css  */

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


/*spinner code start */

 .stack-top{
 		position:absolute;
        margin-top:250px;
        margin-left:48%;
        display:none;
        z-index:10;     
    }

/*spinner code start */
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
/* spinner */
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
			$.getJSON('getSAPHistoricalIncometaxCompanyNames', {
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
			// console.log("yesr")
				var reportyear = $("select#year").val();
				$.getJSON('getSAPHistoricalIncometaxReportNames', {
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
			  var urll="getHistIncomeTaxRegHeadings?reportId="+reportId+'&year='+year;
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
		form.action = "SAPHistIncomeTaxdownloadExcelData";
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
		
		
		var data={facompanygroup:escape(facompanygroup),facompany: escape(facompany),taxInstallDateFrom:escape(taxInstallDateFrom),taxInstallDateTo:escape(taxInstallDateTo)
				,taxCreateDateFrom:escape(taxCreateDateFrom),taxCreateDateTo:escape(taxCreateDateTo),year:escape(year),reportId:escape(reportId),excelDownloadFlow:'true'};
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
		// $('#myDatatable thead').remove();
		 
		
		var url='loadSAPHistIncomeTaxserverSideData?';
		var facompanygroup=$("select#companyGroup").val();	
		var facompany=$("select#companyName").val();		
		var taxInstallDateFrom=$("#datepicker").val();
		var taxInstallDateTo=$("#datepicker1").val();
		var taxCreateDateFrom=$("#datepicker2").val();
		var taxCreateDateTo=$("#datepicker3").val();
		var year=$("select#year").val();	
		//var  reportVal=document.getElementById("reportName").options[document.getElementById("reportName").selectedIndex].text;
		//var str = reportVal;
        //var reportId=str.substr(0, 10);
		var reportId=$("select#reportName").val();
		
		  

		
		if (facompany === "-1") {
			facompany="";
		}
		
		if (initload == "yes") {
			url=url+'&facompany=dummycompany123456';
		}else{
			url=url+'&facompanygroup='+facompanygroup+'&facompany='+facompany+'&taxInstallDateFrom='+taxInstallDateFrom+'&taxInstallDateTo='+taxInstallDateTo
			+'&taxCreateDateFrom='+taxCreateDateFrom+'&taxCreateDateTo='+taxCreateDateTo+'&year='+year+'&reportId='+reportId;
		}
		//alert(url)
		console.log(url);
		
		 
				 	/* setTimeout(()=>{datafetch(url);},3000) */
		tvar= $('#myDatatable').DataTable({
   			//$('#myDatatable').removeAttr('width').DataTable({
   			destroy: true,
   			
   		    
   			//"autoWidth": true,
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
			
				   //alert(data);
			 /*  function datafetch(url)
			  {
				 tvar= $('#myDatatable').DataTable({
		       			//$('#myDatatable').removeAttr('width').DataTable({
		       			destroy: true,
		       			
		       		    
		       			//"autoWidth": true,
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
			  } */
			       
			   
	 
	                        
	                        /*  $('#myDatatable').DataTable(); */  
			   
		
	
	
		
			
		
		
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
    <div id="snackbar">Downloading Please wait...</div>
    </div>
 
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
							<b >SAP Historical Income Tax Asset Register</b>
						</div>
						<!-- <b>FA250 - Tax Register Report</b> -->
					</div>
					<div class="panel col-lg-12  col-sm-12 " style="max-height: 34%;" >
						<div class="row">
							<div id="Selectiontab">
							
								<!-- <div id="taxRegReport col-lg-12  col-sm-12 " style="max-height: 34%;"> -->
								<div id="taxRegReport col-lg-12  col-sm-12 " style="width: 108%;">
									<div class="row" style="margin-left: 1px;">
										<!-- <div class=" col-lg-2 col-sm-2 ">Company Group</div>
										<div class="col-lg-2 col-sm-2 ">Company Name</div> -->
										<!-- <div class=" col-lg-4 col-sm-4 " style="margin-top: 3px;margin-left: 54px;">Group &amp;Company Name</div> -->
										<div class=" col-lg-2 col-sm-2 " style="margin-left: 5px;">Year</div>
										<div class=" col-lg-2 col-sm-2 " style="margin-left: -130px;">Report Name</div>
										<div class=" col-lg-2 col-sm-2 " style="margin-left: -82px;">Business Group</div>
										<div class=" col-lg-2 col-sm-2 " style="margin-left: -90px;">Company Code</div>
										<!-- 13sep -->
										<div class="col-lg-4 col-sm-4 " style="margin-top: 3px;margin-left: -55px;">Install Date From - To Date</div>
										<!-- 13sep -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Installation From Date</div> -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Installation To Date</div> -->
										<!-- <div class="col-lg-2 col-sm-2 ">Tax Creation From Date</div>
										<div class="col-lg-2 col-sm-2 ">Tax Creation To Date</div> -->
										<div class="col-lg-3 col-sm-3 " style="margin-left: -190px; margin-top: 3px;">Creation From -  To Date</div>
										<div class=" col-lg-1 col-sm-1 "></div>
									</div>



									<div class="row" style="margin-left: 1px;">
										<div class="form-group col-lg-1 col-sm-1 ">
											
											<select class="form-control" name="year" id="year" style="font-family: Calibri; font-size: 11px; width:100px">
												<option value="-1">Select Year</option> 
												<!-- <option value="-1"></option>-->
												<c:forEach var="item" items="${yearMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>

										</div>

										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: 60px;">
										<select class="form-control" name="reportName" id="reportName" style="font-family: Calibri; font-size: 12px; width: 155px;paddding-left: 162px;margin-left: -31%;">
												 <option value="-1">Select ReportName</option> 
<!-- 												<option value="-1"></option> -->
												<c:forEach var="item" items="${reportNamesMap}">
<!-- 													<option >${item}</option> -->
                                                 <option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>
	
										</div>


									<!--<div class="row" style="margin-left: 1px;">-->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -150px;">
											
											<select class="form-control" name="companyGroup" id="companyGroup" style="font-family: Calibri; font-size: 11px; width:130px">
												<option value="-1">Select Group</option> 
												<!-- <option value="-1"></option>-->
												<c:forEach var="item" items="${companyGroupsMap}">
													<option value="${item.key}">${item.value}</option>
												</c:forEach>
											</select>

										</div>

										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -21px;">
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
										




										<div class="form-group col-lg-2 col-sm-2 "style="margin-left: -7%;">
											<div class="form-control "style="width: 60%; padding: 4px 12px;">
												<!-- <i input type="text" class="fa fa-calendar"	class="form-control" id="creationFrom" name="creationFrom" path="creationFrom" autocomplete="off" style="font-size: 18px"></i> -->
												<input id="datepicker2" name="creationFrom" type="text" class="smallInput calImg" value="" autocomplete="off">
												
											</div>
										</div>
										
										<div class="form-group col-lg-2 col-sm-2 " style="margin-left: -70px;">
										<!-- <div class="form-group col-lg-2 col-sm-2 "> -->
											<div class="form-control "style="width: 60%; padding: 4px 12px;margin-left: -14%;">
										<input id="datepicker3" name="creationTo" type="text" class="smallInput calImg" value="" autocomplete="off">
										</div>
										</div>
										
										<div class="form-group col-lg-1 col-sm-1 ">
										<!-- <button id="searchbutton">Searchbutton</button> -->
										<!-- <div class="form-group col-lg-1 col-sm-1 "> -->
										<a id="searchbutton" href="#" class="btn btn-info btn-md" data-toggle="tooltip" data-placement="right" title="Excute" style="margin-left: -100%;"> <span class="glyphicon glyphicon-play" id="executeBtn" style="font-size: 100%;margin-top: 3%;"></span>
											</a>
											</div>
											
											<div class="form-group col-lg-1 col-sm-1 ">
											<a id="excelDownloadButton" href="#" data-toggle="tooltip" data-placement="right" title="Download To Excel"> <i class="fa fa-file-excel-o" id="excelBtn" style="font-size: 200%;margin-left: -35%;margin-top: -40%;"></i></a>
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
		
	  
	
	<!-- </form> -->
	
	<!-- <h3>Datatable Server Side Processing</h3> -->
	<div class="tableDiv" style="margin-top: 4px;margin-left:5px;margin-right:5px;overflow-x:scroll" >
			
	
		<table id="myDatatable" class="table table-striped table-bordered dataTable no-footer" role="grid" aria-describedby="tickettable_info" style="width: 3971px;font-size: 12px;text-align: center">
		
		
		
		
	</table> 
		</div>
		</div>
		
 </body>
</html>