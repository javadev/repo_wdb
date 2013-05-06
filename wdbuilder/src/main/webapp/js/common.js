/**
 * 	common.js
 * 		set of function for support of uniform mouse event and XML requests processing
 * 	    (based on code, borrowed from site http://javascript.ru/ )
 * 
 *   	(tested on IE/Firefox/Chrome)
 */ 

// Trim the string
function trim(str){
	return str.replace(/^ss*/, '').replace(/ss*$/, '');
}
// Encode trimmed string
function totalEncode(str){
	return escape(trim(str));
}

// Generalized XMLHttpRequest creation (from Javascript.RU)
function getXmlHttp() {
	var xmlhttp;
	try {
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (E) {
			xmlhttp = false;
		}
	}
	if (!xmlhttp && 'undefined' != typeof XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	}

	return xmlhttp;
}

// Load HTML context using XMLHttpRequest (GET method) (from Javascript.RU) 
function loadByAJAX(url, acceptor) {
	document.body.style.cursor = "wait";
	var request = getXmlHttp();
	if (request) {
		request.onreadystatechange = function() {
			if (4 == request.readyState) {
				document.body.style.cursor = "default";
				if (200 == request.status) {
					clearError();
					acceptor( request.responseText);
					
				} else {
					// Some error:
					showError(request.responseText);
				}
			}
		};
		// Request without caching
		request.open("GET", url, true);
		request.setRequestHeader("connection", "close");
		request.send(null);
	}
}

// Request some HTML content by XMLHttpRequest and show it in some screen element 
function loadContent(url, containerId) {
	loadByAJAX(url, function(response) {
		var elem = document.getElementById(containerId);
		if (!elem) {
			return;
		}
		elem.innerHTML = response;
		var len = response.length;
		elem.style.display = ( 0==len ) ? "none" : "block";
		document.body.style.cursor = "default";
	});
}

// Simple form submission via XMLHttpRequest (POST method) (from Javascript.RU) 
function submitForm( url, params, acceptor) {
	document.body.style.cursor = "wait";
	var request = getXmlHttp();
	if (request) {
		request.onreadystatechange = function() {
			if (4 == request.readyState) {
				document.body.style.cursor = "default";
				if (200 == request.status) {
					clearError();
					acceptor( request.responseText);
				} else {
					// Some error:
					showError(request.responseText);
				}
			}
		};
		// Request without caching
		request.open("POST", url, true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		request.setRequestHeader("Content-length", params.length);
		// request.setRequestHeader("connection", "close");
		request.send(params);
	}
}

// Submit form and show HTML content from HTTP response from server in some screen element 
function submitFormWithContentReturn( url, params, containerId) {
	submitForm(url, params, function(response) {
		var elem = document.getElementById(containerId);
		if (!elem) {
			return;
		}
		elem.innerHTML = response;
		elem.style.display = "block";
		document.body.style.cursor = "default";
	});
}

// Error message header from HTTP response
function showError(message) {
	if( !message ) {
		return;
	}
	var obj = document.getElementById("errorArea");
	if (obj) {
		obj.style.display = "block";
		var str = "unknown error";
		if( 0<message.length ) {			
			// Select error message:
			str = message.match( "javax.servlet.ServletException.*");
			if( !str ) {
				str = "unknown error";
			} else {
				str = new String(str).substr( 32 );
			}
		}
		obj.innerHTML = str;
	}
}

// Hide error area
function clearError() {
	var obj = document.getElementById("errorArea");
	if (obj) {
		obj.style.display = "none";
		obj.innerHTML = "";
	}
}

