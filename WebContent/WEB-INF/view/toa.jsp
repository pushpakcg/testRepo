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
						<div class="col-lg-6  col-sm-6 " style="margin-left: 40%; height:30px; margin-top:8px; font-size:20px">
							<!-- <b style="background-color: white;">FA250 - Tax Register Report</b> -->
							<b >Tax Only Asset</b>
							
						</div>
						<!-- <b>FA250 - Tax Register Report</b> -->
					</div>

					</div>
				</div>
			</div>
		
		
<div class="container-fluid">

<div class="row">
<div class="col-sm-6" >	
<div>
<h4 style="background-color:#FED8B1;width:600px;font-weight: bold;padding: 5px;">Batch Update:<br></br><p>This feature is used to add Tax Only Assets and mark their status as Active</p></h4>
<form id="formId" class="form-horizontal" style="font-size:15px;">
    <div class="form-group" >
  
      <label class="control-label col-sm-2" for="fileUpload">File :</label>
      <div class="col-sm-3">
      <div>
        <input id="excelfile" type="file" >
        </div>
        <br>
        <button id="submit_btn" type="submit">Upload</button>
        </br>
        <br>
       <p>Status: <span><p id="updateStatus"></p></span></p>
       
        </br>
      </div>
    </div>

</form>
</div>	
</div>

 <div class="col-sm-6" >
<div>
<h4 style="background-color:#FED8B1;width:600px;font-weight: bold;padding: 5px;">Batch Delete: <br></br><p>This feature marks uploaded asset's status as inActive<br></br></p> </h4>
<form id="delFormId" class="form-horizontal" style="font-size:15px;">
    <div class="form-group" >
  
      <label class="control-label col-sm-2"  >File :</label>
      <div class="col-sm-3">
      <div>
        <input id="delexcelfile" type="file" >
        </div>
        <br>
        <button id="del_btn" type="submit" >Upload</button>
        </br>
        <br>
       <p>Status: <span><p id="deleteStatus"></p></span></p>
        </br>
      </div>
    </div>

</form>
</div>  
 
</div> 





</div>




<div>
</div>
</div>	
</body>
<script>
		   
window.onload=function(){
	

	document.getElementById('formId').addEventListener('submit', async function (e){
	e.preventDefault();
	const fileinput=document.getElementById('excelfile');
	if(!fileinput.files[0])
		{
		alert("select proper file");
		return;
		}
	
	const formData= new FormData();
	formData.append('file',fileinput.files[0])
	
	try{
		const response=await fetch('/FAWebApp/taxOnlyAsset/upload',{method:'POST', body:formData});
		const updateStatus=await response.text();
		console.log(updateStatus)
		document.getElementById('updateStatus').textContent=updateStatus
	}catch(error)
	{
		console.log(e);
	}
	
	
	
})

	document.getElementById('delFormId').addEventListener('submit', async function (e){
	e.preventDefault();
	const fileinput=document.getElementById('delexcelfile');
	if(!fileinput.files[0])
		{
		alert("select proper file");
		return;
		}
	
	const delFormData= new FormData();
	delFormData.append('file',fileinput.files[0])
	
	try{
		const response=await fetch('/FAWebApp/taxOnlyAsset/delete',{method:'POST', body:delFormData})
		const deleteStatus=await response.text();
		console.log(updateStatus)
		document.getElementById('deleteStatus').textContent=deleteStatus
	}catch(error)
	{
		console.log(e);
	} 
	})
}

</script>
</html>