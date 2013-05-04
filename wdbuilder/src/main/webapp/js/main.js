/**
 * main.js Application-specific methods
 */

// Constant: margin
var MARGIN = 10;

// body.oninit handler
function init() {
	WDB.DiagramResize = new DiagramResize();
	WDB.LinkArrange = new LinkArrange();
	WDB.BlockDrag = new BlockDrag();
	WDB.LineDraw = new LineDraw();
		
	refreshDiagramList();
}

// Reload diagram list and close active diagram
function refreshDiagramList() {
	loadDiagramList(true);
	cleanElement("canvasFrame");
	cleanElement("properties");
}

// Request diagram list from server
function loadDiagramList(full) {
	loadContent("diagram-list?r=" + Math.random() + "&full=" + full,
			"canvasList");
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
	var params = "name=" + totalEncode(formElem.name.value) + "&width="
			+ formElem.width.value + "&height=" + formElem.height.value
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
		loadCanvas(diagramKey, null);
		cleanElement("properties");
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
	if (blockKey) {
		loadCanvas(diagramKey, blockKey);
	}
	cleanElement("properties");
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

//Submit data for new block creation
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
}

// Drop diagram
function deleteCanvas( diagramKey) {
	if (!confirm("Are you sure?")) {
		return;
	}
	loadContent("delete-diagram?r=" + Math.random() + "&dkey=" + diagramKey,
			"canvasList");
	loadDiagramList(true);
	cleanElement("properties");
	cleanElement("canvasFrame");
}

// Load current diagram content to main screen section
function loadCanvas(diagramKey, blockKey) {
	loadContent("diagram?r=" + Math.random() + "&dkey=" + diagramKey + "&bkey="
			+ blockKey, "canvasFrame");
	cleanElement("properties");
	if (blockKey && 0 != blockKey.length) {
		loadContent("selected-block-info?r=" + Math.random() + "&bkey="
				+ blockKey + "&dkey=" + diagramKey, "properties");
	}
	cleanElement("resizeFrame");
}

// Open diagram creation form in main screen section
function openCreateCanvasDialog() {
	cleanElement("properties");
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
	cleanElement("properties");
	loadCanvas(diagramKey, null);
}

// Switching between block/line mode of diagram editor
function switchMode(diagramKey) {
	loadContent("switch-mode?r=" + Math.random(), "canvasFrame");
	cleanElement("properties");
	loadCanvas(diagramKey, null);
}

// Remove link handler
function deleteLink(diagramKey, linkKey) {
	if (!confirm("Are you sure?")) {
		return;
	}
	;
	loadContent("delete-link?r=" + Math.random() + "&lkey=" + linkKey
			+ "&dkey=" + diagramKey, "canvasFrame");
	cleanElement("properties");
	loadCanvas(diagramKey, null);
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
