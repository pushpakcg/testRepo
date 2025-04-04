<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="utf-8"  />
<html><head><meta charset="UTF-8"><meta http-equiv="X-UA-Compatible" content="IE=edge"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>Fixed Assets Reporting System - Custom Menu</title>
<jsp:include page="treeNodeReferences.jsp" />
 <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <!-- <link href="https://cdn.syncfusion.com/18.1.0.54/js/web/flat-azure/ej.web.all.min.css" rel="stylesheet" /> -->
    <link rel="stylesheet" href="css/styles_bs/widgets.css">
    <link rel="stylesheet" href="css/styles_bs/theme.css">
<link rel=StyleSheet href="css/styles_bs/main.css" type="text/css" media=screen><link href="http://www.jqueryscript.net/css/top.css" rel="stylesheet" type="text/css">
<script src="js/main.js"></script><script type="text/javascript" charset="utf8" src="/js/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
<script src="https://cdn.syncfusion.com/js/assets/external/jquery.easing.1.3.min.js" type="text/javascript"></script>
<script src="https://cdn.syncfusion.com/18.1.0.54/js/web/ej.web.all.min.js" type="text/javascript"></script>

   
   
<!-- <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"> -->
<link rel="stylesheet" href="css/styles_bs/jquery.dataTables.min.css">




<script>
	$(document).ready(function() {
		//alert('1');
		updateUserData();
		
		$.urlParam = function(name){
		    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
		    if (results==null) {
		       return null;
		    }
		   
		    return decodeURI(results[1]) || 0;
		}
		
		var pageParam=$.urlParam('page');
		 //alert(pageParam);
		 $('iframe').attr('src', pageParam);
		
});
	
	$("#excelDownloadButton").click(function(){
	    excelDownload();
	  });
	
	function updateUserData() {
		console.log('updateUserData');
		var firstNameCookie = getCookie('firstName');
		var lastNameCookie = getCookie('lastName');
		var userIdCookie = getCookie('userId');
		console.log('firstName :'+firstNameCookie);
		console.log('lastName :'+lastNameCookie);
		console.log('userId :'+userIdCookie);
		/* if (firstNameCookie != "") {
			//$('#fawebusername').append(firstNameCookie + " " + lastNameCookie);
			$('#fawebusername').append(htmlEncode(firstNameCookie) + " " + htmlEncode(lastNameCookie));
		}
		if (userIdCookie != "") {
			$('#fawebuserId').append(htmlEncode(userIdCookie));
		} */
	}

	/* function htmlEncode(value) {
		return $('<textarea/>').text(value).html();
	} */

	function getCookie(cname) {
		  var name = cname + "=";
		  var ca = document.cookie.split(';');
		  for(var i = 0; i < ca.length; i++) {
		    var c = ca[i];
		    while (c.charAt(0) == ' ') {
		      c = c.substring(1);
		    }
		    if (c.indexOf(name) == 0) {
		      return c.substring(name.length, c.length);
		    }
		  }
		  return "";
		}  
	
	var localData = [
		{ id: 1, name: "FA Income Tax", hasChild: true },
        { id: 2, pid: 1, name: "Tax Asset Register",linkAttribute: "/FAWebApp/menu?page=taxAdditionReport" },
        { id: 3, pid: 1, name: "Tax Asset Additions Report",linkAttribute: "/FAWebApp/menutax?page=taxRegisterReport" },
        { id: 4, pid: 1, name: "Tax Asset Retirement Report",linkAttribute: "/FAWebApp/menutax?page=taxRetirement" },
        { id: 5, pid: 1, name: "ADS Tax Asset Register"},
        { id: 6, pid: 1, name: "Tax Roll Forward Report" },
        { id: 7, pid: 1, name: "Tax Re-class Report" },
        { id: 8, pid: 1, name: "Tax Form 4562" },
		   { id: 9, pid: 1, name: "Tax Form 4797" },
		   { id: 10, name: "FA Property Tax", hasChild: true},
	        { id: 11, pid: 10, name: "Property Tax Asset Register"},
	        { id: 12, pid: 10, name: "Property Tax Asset Addition Report" },
	        { id: 13, pid: 10, name: "Property Tax Asset Retirement Report" },
	        { id: 14, pid: 10, name: "Property Tax Physical Location Listing" },
	        { id: 15, name: "FA State Tax", hasChild: true},
	        { id: 16, pid: 15, name: "State Tax Asset Listing"},
	        { id: 17, name: "FA Master Data", hasChild: true},
	        { id: 18, pid: 17, name: "FA Company Listing"},
	        { id: 19, pid: 17, name: "FA Asset Class and Guidelines" },
	        { id: 20, name: "SAP FA Historical", hasChild: true},
	        { id: 21, pid: 20, name: "Prior Yearend Tax Recalculation Reporting"},
	        { id: 22, pid :20, name: "Year End Reports"},
	        { id: 23, name: "Legacy FA Historical", hasChild: true},
	        { id: 24, pid: 23, name: "2016 RECALCULATED USING 2018/Q3 DATA"},
	        { id: 25, pid :23, name: "2017 RECALCULATED USING 2018/Q3 DATA"},
	        { id: 26, pid :23, name: "2018 RECALCULATED USING 2018/Q3 DATA"},
	        { id: 27, pid: 23, name: "2018 YEAR END REPORTS"},
	        { id: 28, pid :23, name: "2017 YEAR END REPORTS"},
	        { id: 29, pid: 23, name: "2017 ADS DEPR REPORTS"},
	        { id: 30, pid :23, name: "2017 RECALCULATED USING Q4/2018"},
	        { id: 31, pid: 23, name: "2018 RECALCULATED USING Q3/2019"},
	        { id: 32, name: "UO FA Historical", hasChild: true},
	        { id: 33, name: "FA Dashboard", hasChild: true, expanded: true},
			   { id: 34,  pid: 33, name: "FA Asset Dashboard",linkAttribute: "/FAWebApp/menudash?page=dashboardReport"}
	        
	        
       /*  { id: 12, name: " Legacy FA Historical", hasChild: true},
		   { id: 13,  pid: 12, name: "2016 RECALCULATED USING 2018/Q3 DATA"},
		   { id: 14,  pid: 13, name: "FA120 - USRG"},
		   { id: 15,  pid: 13, name: "FA250 - Legacy NBC"},
		   { id: 16,  pid: 14, name: "Tax Addition Report"},
		   { id: 17,  pid: 14, name: "Tax Retirement Report"},
		   { id: 18,  pid: 14, name: "Tax Register Report"},
		   { id: 19,  pid: 15, name: "Tax Addition Report"},
		   { id: 20,  pid: 15, name: "Tax Retirement Report"},
		   { id: 21,  pid: 15, name: "Tax Register Report"},
		   { id: 22,  pid: 12, name: "2017 RECALCULATED USING 2018/Q3 DATA"},
		   { id: 23,  pid: 22, name: "FA120 - USRG"},
		   { id: 24,  pid: 22, name: "FA250 - Legacy NBC"},
		   { id: 25,  pid: 23, name: "Tax Addition Report"},
		   { id: 26,  pid: 23, name: "Tax Retirement Report"},
		   { id: 27,  pid: 23, name: "Tax Register Report"},
		   { id: 28,  pid: 24, name: "Tax Addition Report"},
		   { id: 29,  pid: 24, name: "Tax Retirement Report"},
		   { id: 30,  pid: 24, name: "Tax Register Report"},
		   { id: 31,  pid: 12, name: "2018 RECALCULATED USING 2018/Q3 DATA"},
		   { id: 32,  pid: 31, name: "FA120 - USRG"},
		   { id: 33,  pid: 31, name: "FA250 - Legacy NBC"},
		   { id: 34,  pid: 32, name: "Tax Addition Report"},
		   { id: 35,  pid: 32, name: "Tax Retirement Report"},
		   { id: 36,  pid: 32, name: "Tax Register Report"},
		   { id: 37,  pid: 33, name: "Tax Addition Report"},
		   { id: 38,  pid: 33, name: "Tax Retirement Report"},
		   { id: 39,  pid: 33, name: "Tax Register Report"},
		   { id: 40,  pid: 12, name: "2018 YEAR END REPORTS"},
		   { id: 41,  pid: 40, name: "FA120 - USRG"},
		   { id: 42,  pid: 40, name: "FA250 - Legacy NBC"},
		   { id: 43,  pid: 41, name: "Tax Addition Report"},
		   { id: 44,  pid: 41, name: "Tax Retirement Report"},
		   { id: 45,  pid: 41, name: "Tax Register Report"},
		   { id: 46,  pid: 42, name: "Tax Addition Report"},
		   { id: 47,  pid: 42, name: "Tax Retirement Report"},
		   { id: 48,  pid: 42, name: "Tax Register Report"},
		   { id: 49,  pid: 12, name: "2017 YEAR END REPORTS"},
		   { id: 50,  pid: 49, name: "FA120 - USRG"},
		   { id: 51,  pid: 49, name: "FA250 - Legacy NBC"},
		   { id: 52,  pid: 50, name: "Tax Addition Report"},
		   { id: 53,  pid: 50, name: "Tax Retirement Report"},
		   { id: 54,  pid: 50, name: "Tax Register Report"},
		   { id: 55,  pid: 51, name: "Tax Addition Report"},
		   { id: 56,  pid: 51, name: "Tax Retirement Report"},
		   { id: 57,  pid: 51, name: "Tax Register Report"},
		   { id: 58,  pid: 12, name: "2017 ADS DEPR REPORTS"},
		   { id: 59,  pid: 58, name: "FA120 - USRG"},
		   { id: 60,  pid: 58, name: "FA250 - Legacy NBC"},
		   { id: 61,  pid: 59, name: "Tax Addition Report"},
		   { id: 62,  pid: 59, name: "Tax Retirement Report"},
		   { id: 63,  pid: 59, name: "Tax Register Report"},
		   { id: 64,  pid: 60, name: "Tax Addition Report"},
		   { id: 65,  pid: 60, name: "Tax Retirement Report"},
		   { id: 66,  pid: 60, name: "Tax Register Report"},
		   { id: 67,  pid: 12, name: "2017 RECALCULATED USING Q4/2018"},
		   { id: 68,  pid: 67, name: "FA120 - USRG"},
		   { id: 69,  pid: 67, name: "FA250 - Legacy NBC"},
		   { id: 70,  pid: 68, name: "Tax Addition Report"},
		   { id: 71,  pid: 68, name: "Tax Retirement Report"},
		   { id: 72,  pid: 68, name: "Tax Register Report"},
		   { id: 73,  pid: 69, name: "Tax Addition Report"},
		   { id: 74,  pid: 69, name: "Tax Retirement Report"},
		   { id: 75,  pid: 69, name: "Tax Register Report"},
		   { id: 76,  pid: 12, name: "2018 RECALCULATED USING Q3/2019"},
		   { id: 77,  pid: 76, name: "FA120 - USRG"},
		   { id: 78,  pid: 76, name: "FA250 - Legacy NBC"},
		   { id: 79,  pid: 77, name: "Tax Addition Report"},
		   { id: 80,  pid: 77, name: "Tax Retirement Report"},
		   { id: 81,  pid: 77, name: "Tax Register Report"},
		   { id: 82,  pid: 78, name: "Tax Addition Report"},
		   { id: 83,  pid: 78, name: "Tax Retirement Report"},
		   { id: 84,  pid: 78, name: "Tax Register Report"},
		   { id: 85, name: "FA Financial"},
		   { id: 86,  pid: 85, name: "FA120-Asset Addition Report"},
		   { id: 87,  pid: 85, name: "FA250-Asset Addition Report"},
		   { id: 88,  pid: 85, name: "FA500-Asset Addition Report"},
		   { id: 89,  pid: 85, name: "FA120-Asset Register Report"},
		   { id: 90,  pid: 85, name: "FA250-Asset Register Report"},
		   { id: 91,  pid: 85, name: "FA500-Asset Register Report"},
		   { id: 92,  pid: 85, name: "FA120-Asset Retirement Report"},
		   { id: 93,  pid: 85, name: "FA250-Asset Retirement Report"},
		   { id: 94,  pid: 85, name: "FA500-Asset Retirement Report"},
		   { id: 95, name: "FA Dashboard"},
		   { id: 96,  pid: 95, name: "FA Asset Dashboard"}
 */		  
		   
 ];

$(function () {
 $("#treeView").ejTreeView({
    fields: { id: "id", parentId: "pid", text: "name", hasChild: "hasChild", dataSource: localData, expanded: "expanded", linkAttribute: "linkAttribute" },
    cssClass: 'custom'
 });
});
	
</script>

</head>
<style>.topnav-right { float: left;}

</style> 
 <style type="text/css">
        .custom.e-treeview .e-item {
            background-image: url(images/Horizontal.png);
            background-repeat: repeat-y;
        }

        .custom.e-treeview .e-text-wrap {
            padding-left: 4px;
        }

        .custom .e-text-wrap:before {
              display: block;
              content: " ";
              background: url(images/Vertical.png);
              height: 10px;
              position: absolute;
              width: 20px;
              margin-left: -17px;
              margin-top: 8px;
              background-repeat: repeat-x;
        }

        .custom.e-treeview .e-icon.e-minus:before {
             content: "-"; 
            font-size: 12px;
            margin-left: 2px;
            background: #fff url(icons.png) no-repeat scroll;
            padding-inline-start: 2px;
            padding-inline-end: 2px;
             
            }

       .custom.e-treeview .e-icon.e-plus:before {
             content: "+"; 
            font-size: 12px;
            margin-left: 2px;
            background: #fff url(icons.png) no-repeat scroll;
            padding-inline-start: 2px;
            padding-inline-end: 2px;
             
        }
        .e-treeview-wrap .e-icon.e-minus:before {
   
}

        .custom.e-treeview .e-icon.e-minus, .custom.e-treeview .e-icon.e-plus{
            /* border: 1px solid gray; */
            padding: 0px;
            display: flex;
            margin-left: -28px;
            align-items: center;
           /*  background-color: darkgrey; */
        }

        .e-item.last.e-collapse {
            background-repeat: no-repeat !important;
        }
		
        .custom.e-treeview .e-item.last {
            background-image: url(images/3.png);
            background-repeat: no-repeat;
        }
        .e-treeview .e-text {
    color: #333;
    color: white;
    font-size: 12px;
    }
    .e-icon {
    width: 16px;
    height: 12px;
}
   
    </style>
<script type="text/javascript">
	$(document).ready(function(){ 
/* <script type="text/javascript"> */ 
var _gaq = _gaq || []; _gaq.push(['_setAccount', 'UA-36251023-1']); _gaq.push(['_trackPageview']);
(function() { var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);})(); });
</script>
<body><div class="fluid-container header_main"><div class="container-fluid"><div class="row"><div class="col-lg-1 col-xs-1" style="margin-top:5px;"><button type="button" id="sidebarCollapse" class="navbar-btn"><a id="nav-toggle" href="#"><span></span></a></button></div><div class="col-lg-4 col-xs-4 logo_main" style="margin-top:10px;"><img src="images/Home/logo.png" class="img-responsive"></div>
<div class="col-lg-3 col-xs-4 faat_logo"><img src="images/Home/type 2.png" class="img-responsive" style="margin-top: 4px;"></div>


<!-- <div class="col-lg-2 col-xs-3 user_main" style="margin-left: 113px;">
				 
<i class="fa fa-user-circle-o user" data-toggle="dropdown"></i>
					 <p id="fawebusername" style="padding-left: 40px;margin-top: 12px;font-size: 18px"align="right"></p><ul class="dropdown-menu"><li><a href="../resetPassword"><iclass="fa fa-undo="" fa-stack-2x="" reset_psw"="">Reset Password</iclass="fa></a></li>
<li><a href="https://login.stg.inbcu.com/login/logoff.jsp"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
					</ul>
				</div>
				<div class="col-lg-1 col-xs-3 user_main">
				<a href="/FAWebApp" class="topnav-right" style="padding-left: 40px;margin-top: 5px;"><img src="images/Home/home.png" class="img-responsive" width="30px" height="15px"></a>
				</div> -->
<div class="col-lg-1 col-xs-3 user_main" style="float: right;margin-top: 8px;">
				<a href="/FAWebApp" class="topnav-right"><img src="images/Home/home.png" class="img-responsive" width="30px" height="15px"></a>
				</div>
				<div class="col-lg-2 col-xs-3 user_main" style="float: right;">
				 <!-- <a href="/FAAAT/home" class="topnav-right"><img src="/images/Home/home.png" class="img-responsive" width="30px" height="15px"></a> -->
					<i class="fa fa-user-circle-o user" data-toggle="dropdown" aria-expanded="false" style="margin-top:7px;"></i>	
					<!-- <p id="fawebusername" style="margin-top: 13px;font-size: 18px;float:right;"></p> -->
					<p style="margin-top: 13px;font-size: 18px;float:right;"><c:out value="${sessionScope.FirstName}"/> <c:out value="${sessionScope.LastName}"/></p>
					<ul class="dropdown-menu">
					<!-- DEV -->
						<!-- <li><a href="https://login.stg.inbcu.com/login/logoff.jsp"><span class="glyphicon glyphicon-log-out" style="margin-right:8px;"></span>Logout</a></li> -->
						<!-- PROD -->
						<!-- <li><a href="https://login.inbcu.com/login/logoff.jsp"><span class="glyphicon glyphicon-log-out" style="margin-right:8px;"></span>Logout</a></li> -->
						<!-- STAGE -->
						<li><a href="dologout"><span class="glyphicon glyphicon-log-out" style="margin-right:8px;"></span>Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
 	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-4 col-sm-4" id="wrapper">
				<nav id="sidebar" class="col-lg-12 col-sm-12 ">
					<div class="sidebar-header">
						<div id="menuleftcontent"></div>
					</div>
					<div id="mainContent">
						<input id="treeNodes" type="hidden" value='${treeNodes}' />
						<input id="menu" type="hidden" value='${fa_menu}' />
						
 <!-- <div id="leftContainer"><p><ul class=tree><li id="faIncomeTaxDiv">FA Income Tax<div class="expander expanded"></div><ul class="expanded"><li>FA120 - Tax Addition Report</li><li>FA120 - Tax Retirement Report</li><li>FA120 - Tax Register Report</li><li>FA250 - Tax Addition Report</li><li>FA250 - Tax Retirement Report</li><li><a href="/FAWebApp/menu?page=taxAdditionReport" target="_self">FA250 - Tax Register Report</a></li><li>FA500 - Tax Addition Report</li><li>FA500 - Tax Retirement Report</li><li>FA500 - Tax Register Report</li></ul></li><li>FA Historical<div class=expander></div><ul><li>2016 RECALCULATED USING 2018/Q3 DATA<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li><li>2017 RECALCULATED USING 2018/Q3 DATA<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li><li>2018 FORECAST USING 2018/Q3 DATA<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li><li>2018 YEAR END REPORTS<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li><li>2017 YEAR END REPORTS<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li><li>2017 ADS DEPR REPORTS<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li><li>2017 RECALCULATED USING Q4/2018<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li> <li>2018 RECALCULATED USING Q4/2018<div class=expander></div><ul><li>FA120 - USRG<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li><li>FA250 - Legacy NBC<div class=expander></div><ul><li>Tax Addition Report</li><li>Tax Retirement Report</li><li>Tax Register Report</li></ul></li></ul></li></ul></li><li>FA Financial<div class=expander></div><ul><li>FA120-Asset Addition Report</li><li>FA250-Asset Addition Report</li><li>FA500-Asset Addition Report</li><li>FA120-Asset Register Report</li><li>FA250-Asset Register Report</li><li>FA500-Asset Register Report</li><li>FA120-Asset Retirement Report</li><li>FA250-Asset Retirement Report</li><li>FA500-Asset Retirement Report</li></ul></li><li>FA Dashboard<div class=expander></div><ul><li>FA Asset Dashboard</li></ul></li></ul></div> -->
 
 <div class="content" style="margin: 25px;">
        <div class="row">
            <div class="cols-sample-area">
                <div style="width: 350px; max-width:100%">
                    <div id="treeView"></div>
                </div>
            </div>
        </div>
    </div>

						<form:form>
							<%-- <jsp:include page="report.jsp" /> --%>
						</form:form>
					</div>
				</nav>
				 <%-- <div class="col-lg-12  col-sm-12  body_main">
				
					<!-- <iframe src="" name="rightCont"> -->
					<div src="" name="rightCont">
					
					 <jsp:include page="myDatatable.jsp" /> 
					 <jsp:include page="${param.page}" /> 
					</div>
						
				</div>  --%>
				<div class="col-lg-12  col-sm-12  body_main">
					 <iframe src="" name="rightCont"
					style="overflow:hidden; overflow-y:scroll;width: 100%; height: 100%; margin-top: 5px;"></iframe>
					
				</div>
				
			</div>
		</div>
	</div>

</body>
</html>
