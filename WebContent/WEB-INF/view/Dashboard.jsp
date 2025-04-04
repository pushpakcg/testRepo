<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="js/sso.js"></script>
<link rel="stylesheet" type="text/css" href="css/db_site_ui.css">
<link rel="stylesheet" href="css/styles_bs/demo_table_jui.css">
<link rel="stylesheet" href="/css/styles_bs/selectionCriteria.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
<link rel="stylesheet" href="css/styles_bs/jquery.dataTables.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
</head>
<body>
<div class="container-fluid">
<div class="row" style="margin-top:0px;">
<!-- <div>Property Tax Asset Register Report</div> -->
 <div class="col-md-6">
 <div id="myPlot" style="width:100%;height: 460px;"></div>
 </div>
<div class="col-md-6">
<div id="myPlotPie" style="width:100%;height: 460px;"></div>
</div>
</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/chartjs-plugin-datalabels/2.0.0-rc.1/chartjs-plugin-datalabels.min.js" integrity="sha512-+UYTD5L/bU1sgAfWA0ELK5RlQ811q8wZIocqI7+K0Lhh8yVdIoAMEs96wJAIbgFvzynPm36ZCXtkydxu1cs27w==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
$(document).ready(function() {
	getCharts();
	function getCharts(){	
				var url="getChartData";
				jQuery.ajax({type: "get",url: url,dataType: "json",cache: false,
				   success:function(data) {  
					   myChart(data);  				   
				   },
				error:function(){
				  //alert("Session Expired.Please login again.");
				}
				});
		}

	    
function myChart(data){
	//alert("data.p=="+data.c.length);
	//alert("data.B=="+data.B.length+"---"+data.B);	
var xArray = data.c;
var yArray = data.p;
var zArray = data.B; 
var bgColor=poolColors(data.c.length);
var data = [{
  x:xArray,
  y:yArray,
  type:"bar",
  text:zArray,
  hovertemplate: '%{y:.2f}<br><extra></extra>',
  textposition: 'outside',
 // hoverinfo: 'x+y',
  //opacity: 0.5,
  //hovertemplate: ' x: %{x}<br> y: %{y}<br> z: %{z}<br> Placement:%{text}<br> myText: ' + yArray,
  marker: {
	  color: bgColor
  }
}];
var data2 = [{
	labels:xArray, 
	values:yArray, 
	hole:.4, 
	type:"pie",
	text:yArray,
	hoverinfo: 'label+text+percent',
	textinfo: 'percent',
	marker: {
		  colors: bgColor
	  }
	}];

var layout = {
		//title:"Property Tax Asset Register Report",
		xaxis: {
		    tickangle: 30
		  }
		};
var layout2 = {
		title:"",
 		height: 460
// 		  width: 1000
		};
Plotly.newPlot("myPlot", data, layout);
Plotly.newPlot("myPlotPie", data2, layout2);
	}
	
function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function poolColors(a) {
    var pool = [];
    for(i = 0; i < a; i++) {
        pool.push(getRandomColor());
    }
    return pool;
}
// function dynamicColors() {
//     var r = Math.floor(Math.random() * 255);
//     var g = Math.floor(Math.random() * 255);
//     var b = Math.floor(Math.random() * 255);
//     return "rgba(" + r + "," + g + "," + b + ", 0.5)";
// }

});	
</script>
</body>
</html>