
function DiagramResize() {

	var resizeObject = null;
	var blockKey = "";
	var diagramKey = "";
	var origin = null;
	var imageElem = null;
	var minSize = null;
	
	this.validate = function(e) {
		if (!imageElem) {
			return true;
		}
		if (minSize.width + origin.x > e.pageX) {
			return false;
		}
		if (minSize.height + origin.y > e.pageY) {
			return false;
		}
		return true;
	};	
	
	this.mouseMove = function(e) {
		if (!resizeObject) {
			return;
		}
		e = currentHandler.fixEvent(e);
		document.body.style.cursor = "move";
		var notifyData = {
			width : e.pageX - origin.x,
			height : e.pageY - origin.y
		};
		with (resizeObject.style) {
			display = "block";
			position = "absolute";
			left = origin.x + "px";
			top = origin.y + "px";
			width = notifyData.width + "px";
			height = notifyData.height + "px";
		}
		notifyData.valid = currentHandler.validate(e);
		resizeObject.className = notifyData.valid ? "" : "no-drag";
		currentHandler.notifyMove( notifyData );
		return false;
	};

	this.mouseUp = function(e) {
		if (!resizeObject || !origin) {
			return;
		}
		e = currentHandler.fixEvent(e);

		var notifyData = {
			width : e.pageX - origin.x,
			height : e.pageY - origin.y,
			diagram : diagramKey,
			block : blockKey
		};

		// Snap it to grid:
		notifyData.width = snapToGrid(notifyData.width);
		notifyData.height = snapToGrid(notifyData.height);

		// Update canvas size position
		notifyData.valid = currentHandler.validate(e);
				
		currentHandler.unbindFromMouse(notifyData, resizeObject);
		return false;
	};

	this.mouseDown = function(e, parentKey, key, w, h) {
		imageElem = document.getElementById("frameImage");
		resizeObject = document.getElementById("resizeFrame");
		if (!resizeObject) {
			return;
		}
		
		currentHandler = this;
		e = currentHandler.fixEvent(e);
		
		diagramKey = parentKey;
		blockKey = key;
		origin = currentHandler.getPosition(imageElem);
		minSize = { 
			width : w,
			height : h
		};
		
		document.body.style.cursor = "move";		
		
		currentHandler.notifyDown(e);
		currentHandler.setHandlers(currentHandler.mouseMove,
				currentHandler.mouseUp, currentHandler);
		return false;
	};	

	this.registerUpListener(function(args) {
		if (!args.valid) {
			return;
		}
		var url = "update-canvas-size?" + "r=" + Math.random() + "&dkey="
			+ args.diagram + "&width=" + args.width + "&height="
			+ args.height;
			loadContent(url, null);
	});
	this.registerUpListener(function(args) {
		loadCanvas(args.diagram, args.block);
	});
};

DiagramResize.prototype = new MouseHandler();

