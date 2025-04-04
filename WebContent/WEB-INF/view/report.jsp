

<!-- %@taglib uri="https://urldefense.proofpoint.com/v2/url?u=http-3A__www.springframework.org_tags_form&d=DwIGAg&c=LO_8KzsOlAGvgA6hdGI4v02U_XLiES0sR51Zca0yIy4&r=nn102LZRJldP9uADhBJLW-Xy2XeA1ike13GfCiXy4Wk&m=Ni6OQvW-7BlTR7RS2gBXP5ujA9PhK1MDmzpo76xykT0&s=MGuG9cyKcWVwp3K14ZuLU5I6_Dd6EHnCuDVXa72H0js&e= " prefix="form"%-->
<form:form id="searchForm" action="fetchReport"
	modelAttribute="infiniumRequestForm" method="post">
	<form:hidden id="exportExcel" path="exportExcel" value="false" />
	<form:hidden id="companyJson" path="companyJson"
		value="${companiesMap}" />
	<form:hidden id="companyGrpKey" path="companyGrpKey"
		value="${companiesGrp}" />
	<form:hidden id="companyGroupCode" path="companyGroupCode" value="" />
	<form:hidden id="companyNameCode" path="companyNameCode" value="" />
	<form:hidden id="reportName" path="reportName" value="${reportName}" />
	<form:hidden id="instanceName" path="instanceName"
		value="${instanceName}" />
	<form:hidden id="environment" path="environment" value="${environment}" />
	<form:hidden id="instanceCode" path="instanceCode"
		value="${instanceCode}" />
	<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/css/styles_bs/selectionCriteria.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Date Picker -->
<meta charset = "utf-8">
      <title>jQuery UI Datepicker functionality</title>
      <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
         <link href="CSS/jquery.dataTables.min.css" rel="stylesheet" />
         <link href="CSS/select.dataTables.min.css" rel="stylesheet" />
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <!-- datatable -->
 <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<!-- <script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/fSelect.js"></script> -->
<script type="text/javascript" src="js/dataTables.select.min.js"></script>
<script type="text/javascript" src="js/dataTables.buttons.min.js"></script>
	</head>
	<body>
	<include page="FetchData" />
		<div class="fluid-container secondline_main">
			<div class="container-fluid tab-pane" id="2a">
				<div class="col-lg-12  col-sm-12 ">
					<div class="accordion" id="Selection">
						<div class="col-lg-6  col-sm-6 ">
							<b>${pageTitle}</b>
						</div>
						<b>Filter</b>
					</div>
					<div class="panel col-lg-12  col-sm-12 " style="max-height: 10%;">
						<div class="row">
							<div id="Selectiontab">
								<div id="taxAddReport col-lg-12  col-sm-12 ">
									<div class="row" style="margin-left: 1px;">
										<div class=" col-lg-2 col-sm-2">Company Group</div>
										<div class="col-lg-2 col-sm-2 ">Company Name</div>
										<div class="col-lg-2 col-sm-2 ">From Date</div>
										<div class=" col-lg-2 col-sm-2 ">To Date</div>
										<div class=" col-lg-1 col-sm-1 "></div>
										<div class="form-group col-lg-1 col-sm-1 "></div>

									</div>
									<div class="row" style="margin-left: 1px;">
										<div class="form-group col-lg-2 col-sm-2 ">
											<select class="form-control" style="width:170px;">

<option value="ALL">ALL</option><option value="ALL120">ALL120</option><option value="EAST">EAST</option><option value="REDO1">REDO1</option><option value="REDO10">REDO10</option><option value="REDO2">REDO2</option><option value="REDO3">REDO3</option><option value="REDO4">REDO4</option><option value="REDO5">REDO5</option><option value="REDO6">REDO6</option><option value="REDO7">REDO7</option><option value="REDO8">REDO8</option><option value="REDO9">REDO9</option><option value="T:1_DOMESTIC JV">T:1_DOMESTIC JV</option><option value="T:3_UNI DOMESTIC">T:3_UNI DOMESTIC</option><option value="T:LA-PARKS &amp; REC USH">T:LA-PARKS & REC USH</option></select>


										</div>

										<div class="form-group col-lg-2 col-sm-2 ">
											<%-- <select class="form-control" class="chosen" id="companyCode"
												name="companyCode" path="companyCode" autocomplete="off"
												style="font-family: Calibri; font-size: 12px;"></select>--%>
											<select class="form-control">	<option value="" selected="selected">SELECT</option><option value="100">100 - UNIVERSAL STUDIOS HOLLYWOOD</option><option value="101">101 - UNIVERSAL CITYWALK</option><option value="102">102 - TERRA PROPERTIES UCC</option><option value="109">109 - HILLTOP PARKING</option><option value="121">121 - LUDO BIRD</option><option value="122">122 - VOODOO</option><option value="123">123 - MARGARITAVILLE</option><option value="124">124 - MENCHIES</option><option value="127">127 - VIVO ITALIAN KITCHEN</option><option value="129">129 - USS CITYWALK</option><option value="177">177 - DODGER DOGS</option></select>
										</div>

										<div class="form-group col-lg-2 col-sm-2 ">

											<p><input type = "text" id = "creationFrom"></p>
											
										</div>
										<div class="form-group col-lg-2 col-sm-2 ">

											
											
											<p><input type = "text" id = "creationTo"></p>
											
											
										</div>
										<div class="form-group col-lg-1 col-sm-1 ">

											<%-- <a href="#" class="btn btn-info btn-md" data-toggle="tooltip"
												data-placement="right" title="Excute"> <span
												class="glyphicon glyphicon-play" id="executeBtn" 
												style="font-size: 100%;"></span>
											</a>--%>
											<form action="FetchData" method="post" >
<input type="submit" value="Get All Record">
</form>
										</div>
										
										<div class="form-group col-lg-1 col-sm-1 ">
											<a href="#" data-toggle="tooltip" data-placement="right"
												title="Download To Excel"> <i class="fa fa-file-excel-o"
												id="excelBtn" style="font-size: 200%;"></i></a>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
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
			/* Date Pikcer */
			 
         $(function() {
            $( "#creationFrom" ).datepicker();
            $( "#creationTo" ).datepicker();
         });
      
		</script>
		<table id="table_id" style="width: 120%; border: 1px solid black;">
		<thead>
			<tr>
				<th>CUSTOMERID</th>
				<th>CUSTOMERNAME </th>
				<th>CONTACTNAME </th>
				<th>ADDRESS</th>
				<th>CITY</th>
				<th>POSTALCODE</th>
				<th>COUNTRY  </th>
				</tr>
		</thead>
		<tr>
			<td colspan="7"></td>
		</tr>
	</table>
	</body>
</form:form>