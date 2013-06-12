/**
 * main.js Application-specific methods
 */

// Constant: margin
var MARGIN = 10;
var SELECT_FRAME_WIDTH = 2;

// body.oninit handler
function init() {
	WDB.DiagramResize = new DiagramResize();
	WDB.LinkArrange = new LinkArrange();
	WDB.BlockDrag = new BlockDrag();
	WDB.LineDraw = new LineDraw();
		
	refreshDiagramList();
	
	$('.errorArea').hide();
}

function hideProperties() {
	cleanElement("properties");
}

function initBootstrapControls() {
	
	// create the "styled" tooltips:
	$('.btn').tooltip({
		placement : 'bottom'
	});	
	$('.dropdown-toggle').dropdown();	
	
	$('.alert').alert();
}

// Reload diagram list and close active diagram
function refreshDiagramList() {
	loadDiagramList(true);
	cleanElement('canvasFrame');
	hideProperties();
	hideCaret();	
	
}

// Request diagram list from server
function loadDiagramList(full) {	
	var url = "diagram-list?r=" + Math.random() + "&full=" + full;
	loadContent( url, "canvasList", function() { initBootstrapControls(); });
}

// Hide screen section
function cleanElement(id) {
	var elem = document.getElementById(id);
	if (elem) {
		elem.style.display = "none";
		elem.innerHTML = "";
	}
	;
}

// Set the caret:
function setCaret( diagramKey, blockKey, left, top, width, height ) {	
	var c = $('#caret');
	c.show();
	var o = $('#frameImage').offset();
	o.left += left - SELECT_FRAME_WIDTH;
	o.top += top - SELECT_FRAME_WIDTH;
	c.width(width);
	c.height(height);
	c.offset(o);
	
	var deleteCall = "deleteBlock('" + diagramKey + "','" + blockKey + "');" +
		"event.stopPropagation();return false;";
	
	var editCall = "openEditBlockDialog('" + diagramKey + "','" + blockKey + "');" +
		"event.stopPropagation();return false;";	
	
	// Assign some buttons for blocks:
	var str = '<div class="btn-group btn-mini cursor-icons">';
	str += '<a href="#" class="inline-btn" title="Edit" onmousedown="' + editCall + '"><i class="icon-white icon-edit"></i></a>';	
	str += '<a href="#" class="inline-btn" title="Delete" onmousedown="' + deleteCall + '"><i class="icon-white icon-remove"></i></a>';
	str += '&nbsp;&nbsp;</div>';
	
	c.html( str );
	
	c.bind( "mousedown", function(event) {
		WDB.BlockDrag.mouseDown( event, diagramKey, blockKey, left, top );
		return false;
	});
	c.addClass( "selected" );
	
	initBootstrapControls();
}

// Hide caret:
function hideCaret() {
	$('#caret').hide();
	$('#caret').html('');
}

//Set the caret:
function setCaretLink( e, diagramKey, linkKey) {	
	var c = $('#caret');
	c.show();
	var o = $('#frameImage').offset();
	
	e = new MouseHandler().fixEvent( e );
	
	o.left = e.pageX - 16;
	o.top = e.pageY - 8;
	
	c.width( 32 );
	c.height( 16 );
	c.offset(o);
	
	var deleteCall = "deleteLink('" + diagramKey + "','" + linkKey + "');" +
		"event.stopPropagation();return false;";
	
	var editCall = "openEditLinkDialog('" + diagramKey + "','" + linkKey + "');" +
		"event.stopPropagation();return false;";	
	
	// Assign some buttons for blocks:
	var str = '<div class="btn-group btn-mini cursor-icons">';
	str += '<a href="#" class="inline-btn" title="Edit Link" onmousedown="' + editCall + '"><i class="icon-white icon-edit"></i></a>';	
	str += '<a href="#" class="inline-btn" title="Delete Link" onmousedown="' + deleteCall + '"><i class="icon-white icon-remove"></i></a>';
	str += '&nbsp;&nbsp;</div>';
	
	c.html( str );
	
	c.addClass( "selected" );
	
	initBootstrapControls();
}

// Reset form to initial values (handler of "reset" link)
function resetForm() {
	var formElem = document.getElementById("formId");
	if (!formElem) {
		return;
	}
	formElem.reset();
}

// Submit form data for diagram creation
function submitCreateCanvas() {
	submitForm( "create-diagram-save", function( response ) {
		$('#canvasFrame').empty().append( response ).show();
		loadDiagramList(true);	
	});
}

// Submit form data for existing diagram updating
function submitEditCanvas() {
	submitForm( "edit-diagram-save", function( response ) {
		$('#canvasFrame').empty().append( response ).show();
		loadDiagramList(true);
		hideProperties();	
	});
}

// Callback function for block creation
function callbackReloadBlock(response) {
	$('#canvasFrame').empty().html( response ).show();
	document.body.style.cursor = "default";
	hideProperties();
}

//Submit data for new block creation
function submitCreateBlock() {
	submitForm("create-block-save", callbackReloadBlock);
}

function submitEditBlock() {
	submitForm("edit-block-save", callbackReloadBlock);
	$('#caret').hide();
}

function submitEditLink(diagramKey) {
	submitForm("edit-link-save", function() {
		loadCanvas(diagramKey);
	});
	$('#caret').hide();
}

// Drop diagram
function deleteCanvas( diagramKey) {
	if (!confirm("Are you sure?")) {
		return;
	}
	loadContent("delete-diagram?r=" + Math.random() + "&dkey=" + diagramKey,
			"canvasList");
	loadDiagramList(true, '');
	hideProperties();
	cleanElement("canvasFrame");
}

// Load current diagram content to main screen section
function loadCanvas(diagramKey) {
	// Set the active item:
	$('#canvasList .active').removeClass('active');
	$('#d' + diagramKey).addClass( 'active' );
	
	cleanElement("resizeFrame");	
	hideProperties();
	
	loadContent("diagram?r=" + Math.random() + "&dkey=" + diagramKey, "canvasFrame",
			function() { initBootstrapControls(); }
	);
}

// Open diagram creation form in main screen section
function openCreateCanvasDialog() {
	hideProperties();
	loadContent("create-diagram", "canvasFrame", function() {
		initBootstrapControls();
	});
}

//Open diagram creation form in main screen section
function openImportDiagramDialog() {
	hideProperties();
	loadContent("import-diagram", "canvasFrame", function() {
		initBootstrapControls();
	});
}

// Open updating diagram form in additional screen section
function openEditDiagramDialog(diagramKey) {
	loadContent("edit-diagram?r=" + Math.random() + "&dkey=" + diagramKey,
			"properties",  function() {
				initBootstrapControls();
			});
}

function openCreateBlockDialog(diagramKey, blockClass ) {
	loadContent("create-block?r=" + Math.random() + "&dkey=" + diagramKey +
			"&blockClass=" + blockClass,
			"properties", function() {
				initBootstrapControls();
			});
}


//Open form for existing block data update in additional section
function openEditBlockDialog(diagramKey, blockKey) {
	loadContent("edit-block?r=" + Math.random() + "&bkey=" + blockKey
			+ "&dkey=" + diagramKey, "properties" , function() {
				initBootstrapControls();
			});
}

// Hide screen section (handler to "close" link )
function closeDialog(elemId) {
	var elem = document.getElementById(elemId);
	elem.innerHTML = "";
}

// Remove block handler
function deleteBlock(diagramKey, blockKey) {
	if (!confirm("Are you sure?")) {
		return;
	}
	loadContent("delete-block?r=" + Math.random() + "&bkey=" + blockKey
			+ "&dkey=" + diagramKey, "canvasFrame");
	hideProperties();
	loadCanvas(diagramKey);
}

// Switching between block/line mode of diagram editor
function switchMode(diagramKey) {
	loadContent("switch-mode?r=" + Math.random(), "canvasFrame");
	hideProperties();
	loadCanvas(diagramKey);
}

//Open form for existing block data update in additional section
function openEditLinkDialog(diagramKey, linkKey) {
	loadContent("edit-link?r=" + Math.random() + "&lkey=" + linkKey
			+ "&dkey=" + diagramKey, "properties", function() {
		initBootstrapControls();
	});
}

// Remove link handler
function deleteLink(diagramKey, linkKey) {
	if (!confirm("Are you sure?")) {
		return;
	}
	;
	loadContent("delete-link?r=" + Math.random() + "&lkey=" + linkKey
			+ "&dkey=" + diagramKey, "canvasFrame");
	hideProperties();
	loadCanvas(diagramKey);
};

// recalculate coordinate in order to snap to grid
function snapToGrid( n ) {
	var p = n % MARGIN;
	n -= p;
	if( p>=(MARGIN/2) ) {
		n += MARGIN;
	}
	return n;
}

//Error message header from HTTP response
function showError(message) {
	if( !message ) {
		return;
	}
	var obj = document.getElementById("errorArea");
	if (obj) {
		obj.style.display = "block";
		var str = "unknown error";
		message = message.responseText;
		if( 0<message.length ) {			
			// Select error message:
			str = message.match( "javax.servlet.ServletException.*");
			if( !str ) {
				str = "unknown error";
			} else {
				str = new String(str).substr( 32 );
			}
		}
		str += '<a class="close" data-dismiss="alert" href="#">&times;</a>';
		obj.innerHTML = str;
		
		$('.alert').alert();
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
