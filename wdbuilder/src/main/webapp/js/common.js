// Request some HTML content by XMLHttpRequest and show it in some screen element 
function loadContent(aUrl, aContainerId, callback ) {
	$.ajax({
		type: "GET",
		dataType: "html",
		url: aUrl,
		success : function( data ) {
			document.body.style.cursor = "default";
			var len = data.length;
			if( 0!=len ) {
				$( '#' + aContainerId ).show().html( data );
				if( callback ) {
					callback();
				}
				clearError();
			} else {
				$( '#' + aContainerId ).hide();
			}
		},
		error : function ( data ) {
			document.body.style.cursor = "default";
			showError(data);
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
			showError(data);
		}		
	});
}

function submitFileForm( aUrl, anAcceptor) {
	document.body.style.cursor = "wait";
	var paramsStr = $('#formId').serialize();		
	$.ajax({
		type: 'POST',
		contentType: 'multipart/form-data',
		dataType: "html",
		url: aUrl,
		data: paramsStr,
		success : function( data ) {
			document.body.style.cursor = "default";
			anAcceptor( data );
		},
		error : function ( data ) {
			document.body.style.cursor = "default";
			showError(data);
		}		
	});
}

