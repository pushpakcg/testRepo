<%@page import="com.onelogin.saml2.Auth"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" charset="utf8" src="/js/jquery.min.js"></script> 
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
<script type="text/javascript">
//$(document).ready(function() {
	clearSessionCookies();
//});
</script>
</head>
<body>
	<%
	
		Auth auth = new Auth(request, response);

		String nameId = null;
		if (session.getAttribute("nameId") != null) {
			nameId = session.getAttribute("nameId").toString();
		}
		String nameIdFormat = null;
		if (session.getAttribute("nameIdFormat") != null) {
			nameIdFormat = session.getAttribute("nameIdFormat").toString();
		}
		String nameidNameQualifier = null;
		if (session.getAttribute("nameidNameQualifier") != null) {
			nameidNameQualifier = session.getAttribute("nameidNameQualifier").toString();
		}
		String nameidSPNameQualifier = null;
		if (session.getAttribute("nameidSPNameQualifier") != null) {
			nameidSPNameQualifier = session.getAttribute("nameidSPNameQualifier").toString();
		}
		String sessionIndex = null;
		if (session.getAttribute("sessionIndex") != null) {
			sessionIndex = session.getAttribute("sessionIndex").toString();
		}
		
		session.invalidate();
		
		auth.logout(null, nameId, sessionIndex, nameIdFormat, nameidNameQualifier, nameidSPNameQualifier);
	%>
</body>
</html>
