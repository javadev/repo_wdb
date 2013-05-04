function LineDraw() {
	var startSocketId = "";
	var diagramKey = "";

	var origin = null;
	var lineElem = null;
	var mapElem = null;
	var imageTopLeft = null;

	var targetObject = null;
	
	this.findBlock = function(c) {
		if (!mapElem) {
			return null;
		}
		var areaElem = mapElem.firstChild;
		while (areaElem) {
			var coordsStr = areaElem.coords;
			if (coordsStr) {
				var s = coordsStr.split(",");
				var fromX = s[0];
				var fromY = s[1];
				var toX = s[2];
				var toY = s[3];
				if (fromX <= c.x && toX >= c.x && fromY <= c.y && toY >= c.y) {
					// found!
					var id = areaElem.id;
					return id;
				}

			}
			areaElem = areaElem.nextSibling;
		}
		return null;
	};	

	this.mouseMove = function(e) {
		if (!lineElem) {
			return;
		}
		
		e = currentHandler.fixEvent(e);

		var newWidth = Math.abs(e.pageX - origin.x);
		var newHeight = Math.abs(e.pageY - origin.y);
		var newTop = Math.min(e.pageY, origin.y);
		var newLeft = Math.min(e.pageX, origin.x);

		var vClass = (e.pageX > origin.x) ? "line-left" : "line-right";
		var hClass = (e.pageY > origin.y) ? "line-bottom" : "line-top";

		with (lineElem.style) {
			position = "absolute";
			top = (newTop) + "px";
			left = (newLeft) + "px";
			width = (newWidth) + "px";
			height = (newHeight) + "px";
		}
		lineElem.className = hClass + " " + vClass;

		var notifyObj = {};
		notifyObj.coords = {
			x : e.pageX - imageTopLeft.x,
			y : e.pageY - imageTopLeft.y
		};
		notifyObj.socketId = currentHandler.findBlock(notifyObj.coords);
		// Show or hide drop target:
		if (notifyObj.socketId) {
			with (targetObject.style) {
				display = "block";
				position = "absolute";
				left = (e.pageX - 6) + "px";
				top = (e.pageY - 6) + "px";
			}
		} else {
			currentHandler.hideTemporaryObject(targetObject);
		}
		currentHandler.notifyMove(notifyObj);

		return false;
	};

	this.mouseUp = function(e) {
		if (!lineElem) {
			return;
		}
		e = currentHandler.fixEvent(e);

		var notifyObj = {
			event : e,
			diagram: diagramKey,
			beginSocketId : startSocketId
		};
		notifyObj.coords = {
			x : e.pageX - imageTopLeft.x,
			y : e.pageY - imageTopLeft.y
		};
		notifyObj.endSocketId = currentHandler.findBlock(notifyObj.coords);
		
		currentHandler.unbindFromMouse(notifyObj, lineElem, targetObject);

		return false;
	};

	this.mouseDown = function(e, parentId, id, offsetX, offsetY) {
		lineElem = document.getElementById("lineFrame");
		if (!lineElem) {
				return false; 
		}
		mapElem = document.getElementById("diagramImageMap");
		if (!mapElem) {
			return false;
		}
		var imageElem = document.getElementById("frameImage");
		if (!imageElem) {
			return false;
		}
		targetObject = document.getElementById("targetDiv");
		if (!targetDiv) {
			return false;
		}
		
		currentHandler = this;
		e = currentHandler.fixEvent(e);		

		diagramKey = parentId;
		// Line from - set:
		startSocketId = id;
		// Line to - skip:
		endSocketId = null;

		origin = {
			x : e.pageX,
			y : e.pageY
		};
		imageTopLeft = currentHandler.getPosition(imageElem);

		with (lineElem.style) {
			display = "block";
			position = "absolute";
			left = e.pageX + "px";
			top = e.pageY + "px";
			right = e.pageX + "px";
			bottom = e.pageY + "px";
			width = "0px";
			height = "0px";
		}
		
		currentHandler.notifyDown(e);
		currentHandler.setHandlers(currentHandler.mouseMove,
				currentHandler.mouseUp, currentHandler);

	};
	
	this.registerUpListener(function(args) {

		if (!args.endSocketId) {
			return;
		}
		var url = "create-link?" + "r=" + Math.random() + "&dkey="
				+ args.diagram + "&beginSocketId=" + args.beginSocketId
				+ "&endSocketId=" + args.endSocketId;

		// Connect blocks:
		loadContent(url, "properties");
	});

	this.registerUpListener(function(args) {
		loadCanvas(args.diagram, null);
	});
	
};

LineDraw.prototype = new MouseHandler();
