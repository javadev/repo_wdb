function BlockDrag() {
	
	var workspaceObject = null;
	var dragObject = null;
	var blockKey = "";
	var diagramKey = "";
	var origin = null;
	var delta = null;
	var imageElem = null;

	var startPos = null;

	this.getCenter = function(e) {
		var x = e.pageX - origin.x - delta.x;
		var y = e.pageY - origin.y - delta.y;

		// Calculate the center of block:
		var c = {
			x : x + Math.round(dragObject.clientWidth / 2),
			y : y + Math.round(dragObject.clientHeight / 2)
		};

		// Snap it to grid:
		c.x = snapToGrid(c.x);
		c.y = snapToGrid(c.y);

		return c;
	};

	this.isSmallMovement = function(e) {
		var offsetX = Math.abs(e.pageX - startPos.x);
		var offsetY = Math.abs(e.pageY - startPos.y);

		if (offsetX < 10 && offsetY < 10) {
			return true;
		}
		return false;
	};
	
	this.validate = function(e) {
		if (!imageElem) {
			return true;
		}
		var width = dragObject.clientWidth;
		var height = dragObject.clientHeight;

		var x = e.pageX - delta.x;
		var y = e.pageY - delta.y;

		if (origin.x + 2 * MARGIN > x) {
			return false;
		}
		if (origin.y + 2 * MARGIN > y) {
			return false;
		}
		if (origin.x + imageElem.width - 2 * MARGIN <= x + width) {
			return false;
		}
		if (origin.y + imageElem.height - 2 * MARGIN <= y + height) {
			return false;
		}

		return true;
	};	

	this.mouseMove = function(e) {
		if (!dragObject) {
			return false;
		}
		e = currentHandler.fixEvent(e);

		document.body.style.cursor = "move";
		
		var notifyObj = {
				top : e.pageY - delta.y,
				left : e.pageX - delta.x,
				diagram : diagramKey,
				block : blockKey
			};		

		with (dragObject.style) {
			position = "absolute";
			top = notifyObj.top + "px";
			left = notifyObj.left + "px";
		}

		notifyObj.valid = currentHandler.validate(e);
		if( notifyObj.valid ) {
			$(dragObject).addClass( "selected" ).removeClass( "no-drag" );
		} else {
			$(dragObject).removeClass( "selected" ).addClass( "no-drag" );
		}
		
		currentHandler.notifyMove(notifyObj);
		return false;
	};

	this.mouseUp = function(e) {
		if (!dragObject || !origin) {
			return false;
		}
		e = currentHandler.fixEvent(e);

		var notifyObj = {
			diagram : diagramKey,
			block : blockKey
		};

		notifyObj.valid = currentHandler.validate(e);
		notifyObj.center = currentHandler.getCenter(e);
		notifyObj.smallMovement = currentHandler.isSmallMovement(e);

		currentHandler.unbindFromMouse(notifyObj);
		hideCaret();
		/*
		if( workspaceObject && dragObject ) {
			workspaceObject.removeChild( dragObject );
		}
		*/
		return false;
	};
	
	this.mouseDown = function(e, parentKey, key, offsetX, offsetY) {
		// Create image moving object ("caret"):
		workspaceObject = document.getElementById("workspace");
		if( !workspaceObject ) {
			return false;
		}
		dragObject = document.getElementById("caret");
		/*
		dragObject = document.createElement("div");
		dragObject.setAttribute("id", "caret");
		workspaceObject.appendChild(dragObject);
		*/
		
		currentHandler = this;
		e = currentHandler.fixEvent(e);		

		blockKey = key;
		diagramKey = parentKey;
		imageElem = document.getElementById("frameImage");
		origin = currentHandler.getPosition(imageElem);

		delta = {
			x : e.pageX - offsetX - origin.x + SELECT_FRAME_WIDTH,
			y : e.pageY - offsetY - origin.y + SELECT_FRAME_WIDTH
		};

		// Reload the image with changed selection:
		// loadCanvas(diagramKey, blockKey);

		// Load the image content:
		loadContent("moving-block?r=" + Math.random() + "&bkey=" + blockKey
			+ "&dkey=" + diagramKey, "caret", function() {
			document.body.style.cursor = "move";

			currentHandler.notifyDown(e);
			currentHandler.setHandlers(currentHandler.mouseMove,
					currentHandler.mouseUp, currentHandler);
			
			with (dragObject.style) {
				display = "block";
				position = "absolute";
				top = (e.pageY - delta.y) + "px";
				left = (e.pageX - delta.x) + "px";
			}
			
			$(dragObject).addClass( "selected" );

			startPos = {
				x : e.pageX,
				y : e.pageY
			};			
		});
	};

	this.registerUpListener(function(args) {

		if (!args.valid || args.smallMovement) {
			return;
		}
		var url = "update-moving-block-position?" + "r=" + Math.random()
			+ "&bkey=" + args.block + "&dkey=" + args.diagram + "&x="
			+ args.center.x + "&y=" + args.center.y;
		loadContent(url, null);
	});

	this.registerUpListener(function(args) {
		loadCanvas(args.diagram, args.block);
	});
};

BlockDrag.prototype = new MouseHandler();
