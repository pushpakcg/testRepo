//checkSsoCookie();
function checkSsoCookie() {
	//var ssoCookie = getCookie('R12SMSESSION');
	console.log('checkSsoCookie starts');
	//Stage and Dev
	//var ssoCookie = getCookie('SSMSESSION');
	//Prod
	//var ssoCookie = getCookie('SMSESSION');
	var firstNameCookie = getCookie('firstName');  
	//if (firstNameCookie != '' && ssoCookie != '' && ssoCookie != 'LOGGEDOFF') {
		if (firstNameCookie != '') {
		console.log('ssocookie present');
		var redirectpage = getCookie("page");
		
		if (redirectpage != undefined) {
			console.log('redirectpage present: ' + redirectpage);
			document.cookie = "page=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
			window.open(redirectpage, '_self');
		}
	} else {
		console.log('ssocookie not present');
		//alert('Cookie not present');
		document.cookie = "page=" + window.location.href;
		window.open('dologin', '_self');
	}
	}
//}

function clearSessionCookies(){
	console.log('clearSessionCookies called Starts');
	document.cookie = "firstName=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
	document.cookie = "lastName=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
	document.cookie = "userId=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
	console.log('clearSessionCookies called Ends');
}



function updateUserData() {
	console.log('updateUserData');
	var firstNameCookie = getCookie('firstName');
	var lastNameCookie = getCookie('lastName');
	var userIdCookie = getCookie('userId');
	
	
	
	//alert("userIdCookie"+userIdCookie);
	console.log('firstName :'+firstNameCookie);
	console.log('lastName :'+lastNameCookie);
	console.log('userId :'+userIdCookie);
	/*if (firstNameCookie != "") {
		//$('#fawebusername').append(htmlEncode(firstNameCookie) + " " + htmlEncode(lastNameCookie));
		$('#fawebusername').append(escapeHTML(firstNameCookie,true) + " " + escapeHTML(lastNameCookie,true));
		
	}
	if (userIdCookie != "") {
		$('#fawebuserId').escapeHTML(escape(userIdCookie,true));
		<p><c:out value="${sessionScope.uid}"/></p>
	}*/
	/*if (userIdCookie != "") {
		$("#fawebuserId").append(userIdCookie);
	}*/
}

/*function htmlEncode(value) {
	return $('<textarea/>').text(value).html();
}*/

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
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
