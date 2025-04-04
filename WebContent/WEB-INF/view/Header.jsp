<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<script type="text/javascript" charset="utf8" src="js/jquery.min.js"></script>

<link rel="stylesheet" href="css/styles_bs/headerLogo.css">
</head>
<script>
	$(document).ready(
			function() {
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
					<%-- <p>${userName}</p> --%>
					<ul class="dropdown-menu">
						<li><a href="./resetPassword"><i
								class="fa fa-undo fa-stack-2x reset_psw"></i> Reset Password</a></li>
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