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
function loadContent(url, containerId, callback ) {
	loadByAJAX(url, function(response) {
		var elem = document.getElementById(containerId);
		if (!elem) {
			return;
		}
		elem.innerHTML = response;
		var len = response.length;
		elem.style.display = ( 0==len ) ? "none" : "block";
		document.body.style.cursor = "default";
		if( callback ) {
			callback();
		}
	});
}

function submitForm( aUrl, anAcceptor) {
	document.body.style.cursor = "wait";
	var paramsStr = $('#formId').serialize();
	$.ajax({
		type: "POST",
		dataType: "html",
		url: aUrl,
		data: paramsStr,
		success : function( data ) {
			document.body.style.cursor = "default";
			anAcceptor( data );
		},
		error : function ( data ) {
			document.body.style.cursor = "default";
			showError(request.responseText);
		}		
	});
}

function submitFormWithContentReturn( url, containerId) {
	document.body.style.cursor = "wait";
	var paramsStr = $('#formId').serialize();
	$.ajax({
		type: "POST",
		dataType: "html",
		url: aUrl,
		data: paramsStr,
		success : function( data ) {
			document.body.style.cursor = "default";
			var elem = $('#' + containerId );
			elem.show();
			elem.html( data );
		},
		error : function ( data ) {
			document.body.style.cursor = "default";
			showError(request.responseText);
		}		
	});	
}

