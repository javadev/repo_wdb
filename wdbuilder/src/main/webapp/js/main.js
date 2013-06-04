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
}

function hideProperties() {
	cleanElement("properties");
}

function initBootstrapControls() {
	
	// create the "styled" tooltips:
	$('.btn').tooltip({
		placement : 'bottom'
	});	
	$('.bs-dropdown').dropdown();		
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
	var formElem = document.getElementById("formId");
	if (!formElem) {
		return;
	}
	var params = "name=" + totalEncode(formElem.name.value) 
			+ "&background=" + formElem.background.value;
	submitForm("create-diagram-save", params, function(response) {
		var elem = document.getElementById("canvasFrame");
		if (!elem) {
			return;
		}
		elem.innerHTML = response;
		elem.style.display = "block";
		document.body.style.cursor = "default";
		loadDiagramList(true);
	});
}

// Submit form data for existing diagram updating
function submitEditCanvas() {
	var formElem = document.getElementById("formId");
	if (!formElem) {
		return;
	}
	var diagramKey = formElem.dkey.value;
	var params = "name=" + totalEncode(formElem.name.value) + "&dkey=" + diagramKey 
			+ "&background=" + formElem.background.value;
	submitForm("edit-diagram-save", params, function(response) {
		var elem = document.getElementById("properties");
		if (!elem) {
			return;
		}
		elem.innerHTML = response;
		elem.style.display = "block";
		document.body.style.cursor = "default";
		refreshDiagramList();
		loadCanvas(diagramKey);
		hideProperties();
	});
}

// Callback function for block creation
function callbackReloadBlock(response, diagramKey, blockKey) {
	var elem = document.getElementById("canvasFrame");
	if (!elem) {
		return;
	}
	// Response contains newly created block id:
	elem.innerHTML = response;
	elem.style.display = "block";
	document.body.style.cursor = "default";
	hideProperties();
}

//Submit data for new block creation
function submitCreateBlock(diagramKey, blockClass, fieldNames ) {
	
	var formElem = document.getElementById("formId");
	if (!formElem) {
		return;
	}
	var params = "dkey=" + diagramKey + "&blockClass=" + blockClass;
	for( var i=0; i<fieldNames.length; i++ ) {
		params += "&" + fieldNames[i] + "=" + formElem[ fieldNames[i] ].value;
	}
	
	var blockKey = null; // variable for callback function
	submitForm("create-block-save", params, callbackReloadBlock);
}

function submitEditBlock(diagramKey, blockKey, fieldNames ) {
	var formElem = document.getElementById("formId");
	if (!formElem) {
		return;
	}
	
	var params = "dkey=" + diagramKey + "&bkey=" + blockKey;
	for( var i=0; i<fieldNames.length; i++ ) {
		params += "&" + fieldNames[i] + "=" + formElem[ fieldNames[i] ].value;
	}
	
	submitForm("edit-block-save", params, callbackReloadBlock);
	
	// Hide the moving block:
	hideTemporaryObject( document.getElementById("car"));
}

function submitEditLink(diagramKey, linkKey, fieldNames ) {
	var formElem = document.getElementById("formId");
	if (!formElem) {
		return;
	}
	
	var params = "dkey=" + diagramKey + "&lkey=" + linkKey;
	for( var i=0; i<fieldNames.length; i++ ) {
		params += "&" + fieldNames[i] + "=" + formElem[ fieldNames[i] ].value;
	}
	
	submitForm("edit-link-save", params, callbackReloadBlock);
	
	// Hide the moving block:
	hideTemporaryObject( document.getElementById("car"));	
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
	loadContent("create-diagram", "canvasFrame");
}

// Open updating diagram form in additional screen section
function openEditDiagramDialog(diagramKey) {
	loadContent("edit-diagram?r=" + Math.random() + "&dkey=" + diagramKey,
			"properties");
}

function openCreateBlockDialog(diagramKey, blockClass ) {
	loadContent("create-block?r=" + Math.random() + "&dkey=" + diagramKey +
			"&blockClass=" + blockClass,
			"properties");
}


//Open form for existing block data update in additional section
function openEditBlockDialog(diagramKey, blockKey) {
	loadContent("edit-block?r=" + Math.random() + "&bkey=" + blockKey
			+ "&dkey=" + diagramKey, "properties");
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
			+ "&dkey=" + diagramKey, "properties");
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
