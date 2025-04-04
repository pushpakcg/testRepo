<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!doctype html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>FARS HOME</title>
     
     <link rel="stylesheet" href="css/styles_bs/home.css">
   </head>
  
   <body>
   


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Header</title>
<link href='https://fonts.googleapis.com/css?family=Roboto'
	rel='stylesheet'>
 <link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet"> 
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="css/bs/bootstrap-toggle.min.css"
	type="text/css">

 <script type="text/javascript" charset="utf8" src="/js/jquery.min.js"></script> 
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
 <script src="js/sso.js"></script>  

<link rel="stylesheet" href="css/styles_bs/headerLogo.css">
</head>
<script>
	$(document).ready(function() {
				
				updateUserData()
			

				$(window).scroll(
						function() {
							var header_main = $('.header_main'), scroll = $(
									window).scrollTop();

							if (scroll >= 100)
								header_main.addClass('fixed_top');
							else
								header_main.removeClass('fixed_top');
						});

			});
</script>
<body>
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<div class="fluid-container header_main">
		<div class="container-fluid">
			<!-- Header starts here -->
			<div class="row">
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-5 logo_main">
					<img src="images/Home/logo.png" class="img-responsive">
				</div>
				<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 faat_logo">
					<!-- <img src="images/Home/faat_logo.png" class="img-responsive"> -->
					<img src="images/Home/type 2.png" class="img-responsive">
				</div>
				<div class="col-lg-4 col-xs-4 user_main">
					<i class="fa fa-user-circle-o user" data-toggle="dropdown"></i>
					<!-- <p id="fawebusername"></p> -->
					<p><c:out value="${sessionScope.FirstName}"/> <c:out value="${sessionScope.LastName}"/></p>
                   <ul class="dropdown-menu">
						<!-- <li><a href="./resetPassword"><i
								class="fa fa-undo fa-stack-2x reset_psw"></i> Reset Password</a></li> -->
								<!-- DEV -->
						<!-- <li><a href="https://login.stg.inbcu.com/login/logoff.jsp"> <span
								class="glyphicon glyphicon-log-out"></span> Logout
						</a></li> -->
						<!-- PROD -->
						<!-- <li><a href="https://login.inbcu.com/login/logoff.jsp"> <span
								class="glyphicon glyphicon-log-out"></span> Logout
						</a></li> -->
						<!-- STAGE -->
						<li><a href="dologout"> <span
								class="glyphicon glyphicon-log-out"></span> Logout
						</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
     <div class="container-fluid">
         <div class="row">
            <div class="col-lg-12 col-xs-12 body_main">
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main"  id="dashboard">
                     <a class="disaply"><img src="images/Home/dashboard.png" class="img-responsive"></a> 
                      <p>FA Dashboard</p>
                      </div>
               </div>
              
                <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main" id="incometax">
                 <a class="disaply"> <img src="images/Home/incometax.png" class="img-responsive"></a>
                  <p>FA Income Tax</p>
                  </div>
               </div>
               
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main" id="propertytax">
                    <a class="disaply"> <img src="images/Home/propertytax.png" class="img-responsive"></a>
                     <p>FA Property Tax</p>
                  </div>
               </div>
               
               
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main" id="statetax">
                     <img src="images/Home/statetax.png" class="img-responsive">
                     <p>FA StateTax</p>
                  </div>
               </div>
              <!--  <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main01" id="incometax">
                 <a class="disaply"> <img src="images/Home/incometax.png" class="img-responsive"></a>
                  <p>FA Income Tax</p>
                  </div>
               </div> -->
                <div class="col-lg-3 col-xs-3">
                  <div class="cardle_main01" id="f_reporting">
                     <a class="disaply"><img src="images/Home/f_reporting.png" class="img-responsive"></a>
                      <p>FA Master Data</p>
                  </div>
               </div>
               <!-- <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main01" id="propertytax">
                    <a class="disaply"> <img src="images/Home/propertytax.png" class="img-responsive"></a>
                     <p>FA Property Tax</p>
                  </div>
               </div> -->
               
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main01" id="historical">
                     <img src="images/Home/historical.png" class="img-responsive"> 
                    
                     <p>SAP FA Historical</p>
                  </div>
               </div>
               
                              <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main01" id="inquires">
                    <a class="disaply"><img src="images/Home/inquires.png" class="img-responsive"></a>
                     <p>Legacy FA Historical</p>
                  </div>
               </div>
               
               <!-- <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main01" id="statetax">
                     <img src="images/Home/statetax.png" class="img-responsive">
                     <p>FA StateTax</p>
                  </div>
               </div> -->
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main01" id="maintainance">
                      <img src="images/Home/admin.png" class="img-responsive">
                     <p>UO FA Historical</p>
                  </div>
               </div>
               
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main02" id="infinium">
                     <img src="images/Home/infinium.png" class="img-responsive">
                     <p>Infinium FA Portal</p> 
                   
                  </div>
               </div>
               <div class="col-lg-3  col-xs-3">
                  <div class="cardle_main02" id="queue">
                     <img src="images/Home/queue.png" class="img-responsive">
                     <p>SAP FA</p>
                  </div>
               </div>
               <div class="col-lg-3 col-xs-3">
                  <div class="cardle_main02" id="admin">
                     <a class="disaply"><img src="images/Home/maintainance.png" class="img-responsive"></a>
                     <p>Admin</p>
                  </div>
               </div>
               <div class="col-lg-3 col-xs-3">
                  <div class="cardle_main02" id="usermanual">
                     <img src="images/Home/usermanual.png" id="usermanual" class="img-responsive">
                     <p>User Manual</p>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!-- body content starts here -->
      <!-- footer starts here -->
      <div class="fluid-container footer_main">
         <div class="container-fluid">
            <!-- Header starts here -->
            <div class="row">
               <div class="col-lg-4 col-xs-4 footer_first">
                  <ul>
                     <p>Links</p>
                     <li><a class="disaply" href="https://kpmgleasingtool.kpmg.com"		target="_blank">KLT</a></li>
						
                     <li><a class="disaply" href="http://portal.inbcu.com/irj/portal"	target="_blank">SAP Portal</a></li>
						
                     <li><a class="disaply" href="http://snap.inbcu.com/" target="_blank">SNAP</a></li>
                     
                     <li><a class="disaply" href="https://nbcu.service-now.com/nbcusp"	target="_blank">NBCU Service Portal</a></li>
                     <li><a class="disaply" href="https://nbcuni.us2.blackline.com/" target="_blank">Blackline </a> </li>
                  </ul>
               </div>
               <div class="col-lg-4  col-xs-4 footer_second">
                  <ul>
                     <p> Other Documents</p>
                     <li id="KLTManual">  KLT Manual
                    </li>
                  </ul>
               </div>
               <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 footer_second">
                  <ul>
                     <p>Support</p>
                     <li>Email : <a>FAIT@nbcuni.com</a></li>
                  </ul>
               </div>
            </div>
         </div>
      </div>
      
      <!-- footer ends here -->
      <!--       <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> -->
      <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <script type="text/javascript">
		
		 $('#maintainance,#f_reporting,#inquires,#queue,#admin,#usermanual').on('click', function() {
			    alert("This module is Under Construction!");
			  });
		 $('#KLTManual').on('click', function() {
			    alert("Under Construction!");
			  });
		 
		 $('#incometax').on('click', function() {
			     location.replace("/FAWebApp/menu");
			  });
		 
		 $('#historical').on('click', function() {
		     location.replace("/FAWebApp/sapfahistorical");
		  });
		 
		 $('#propertytax').on('click', function() {
		     location.replace("/FAWebApp/menu");
		  });
		 
		 $('#statetax').on('click', function() {
		     location.replace("/FAWebApp/menu");
		  });
		 
		 
		 $('#dashboard').on('click', function() {
		     location.replace("/FAWebApp/menudash");
		  });
		 $('#infinium').on('click', function() {
		     location.replace("http://usof91:8012/FAAAT/login");
			 
		 });
		 
		 /*
		 $('#Blackline,#KLTManual,#SNAP').on('click', function() {
			    alert("Under Construction!");
			  });
		 */
	</script>
   </body>
</html>