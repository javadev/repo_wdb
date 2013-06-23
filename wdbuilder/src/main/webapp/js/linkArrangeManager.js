
function LinkArrange() {
	var resizeObject1 = null;
	var resizeObject2 = null;

	var base1 = null;
	var base2 = null;
	var canvasId = "";
	var linkId = "";
	var imageElem = null;
	var imagePos = null;

	var horizontal = false;
	
	this.invokeDiv = function(aId, aX, aY, aE, aFirst) {
		var obj = document.getElementById(aId);
		if (!obj) {
			return null;
		}
		var pos = {
			x : aX,
			y : aY
		};
		currentHandler.resizeDiv(obj, pos, aE, aFirst);
		return {
			elem : obj,
			pos : pos
		};
	};

	this.resizeDiv = function(aElem, aPos, aE, aFirst) {
		var x = aPos.x + imagePos.x;
		var y = aPos.y + imagePos.y;

		var leftDiv = Math.min(aE.pageX, x);
		var topDiv = Math.min(aE.pageY, y);

		with (aElem.style) {
			display = "block";
			position = "absolute";
			left = leftDiv + "px";
			top = topDiv + "px";
			width = Math.abs(aE.pageX - x) + "px";
			height = Math.abs(aE.pageY - y) + "px";
		}

		var noDrag = currentHandler.validate(aE) ? "" : "-no-drag";

		var hClass = aE.pageY <= y ? "line-bottom" : "line-top";
		var vClass = aE.pageX <= x ? "line-left" : "line-right";
		if (!aFirst) {
			vClass = "line-left" == vClass ? "line-right" : "line-left";
			hClass = "line-bottom" == hClass ? "line-top" : "line-bottom";
		}

		var className = vClass + noDrag + " " + hClass + noDrag;
		aElem.className = className;
	};

	this.validate = function(e) {
		if (!imageElem) {
			return true;
		}

		return e.pageX > imagePos.x + MARGIN
				&& e.pageX <= imageElem.width + imagePos.x - MARGIN
				&& e.pageY > imagePos.y + MARGIN
				&& e.pageY <= imageElem.height + imagePos.y - MARGIN;
	};

	this.mouseMove = function(e) {
		if (!resizeObject1 || !resizeObject2) {
			return false;
		}
		e = currentHandler.fixEvent(e);

		currentHandler.resizeDiv(resizeObject1, base1, e, !horizontal);
		currentHandler.resizeDiv(resizeObject2, base2, e, horizontal);
		
		var notifyObj = { event: e };
		currentHandler.notifyMove( notifyObj );

		return false;
	};

	this.mouseUp = function(e) {
		if (!resizeObject1 || !resizeObject2) {
			return false;
		}
		e = currentHandler.fixEvent(e);

		var notifyObj = {
			diagram : canvasId,
			link : linkId
		};

		notifyObj.valid = currentHandler.validate(e);

		notifyObj.x = e.pageX - imagePos.x;
		notifyObj.y = e.pageY - imagePos.y;

		// Snap it to the grid:
		notifyObj.x = snapToGrid(notifyObj.x);
		notifyObj.y = snapToGrid(notifyObj.y);

		currentHandler.unbindFromMouse(notifyObj, resizeObject1, resizeObject2);

		return false;
	};

	this.mouseDown = function(e, parentId, id, x1, y1, x2, y2, aHorizontal) {
		horizontal = aHorizontal;
		imageElem = document.getElementById("frameImage");
		
		currentHandler = this;

		canvasId = parentId;
		linkId = id;
		e = currentHandler.fixEvent(e);
		imagePos = currentHandler.getPosition(imageElem);

		var result = currentHandler.invokeDiv("arrangeLink1", x1, y1, e, true);
		if (null == result) {
			return false;
		}
		resizeObject1 = result.elem;
		base1 = result.pos;

		result = currentHandler.invokeDiv("arrangeLink2", x2, y2, e, false);
		if (null == result) {
			return false;
		}
		resizeObject2 = result.elem;
		base2 = result.pos;

		currentHandler.notifyDown(e);
		currentHandler.setHandlers(currentHandler.mouseMove,
				currentHandler.mouseUp, currentHandler);
	};

	this.registerUpListener(function(args) {
		if (!args.valid) {
			return;
		}
		var url = "move-link-pivot?r=" + Math.random() + "&dkey="
			+ args.diagram + "&lkey=" + args.link + "&x=" + args.x
			+ "&y=" + args.y;
		loadContent(url, null );
	});

	this.registerUpListener(function(args) {
		loadDiagram(args.diagram);
	});
};

LinkArrange.prototype = new MouseHandler();
