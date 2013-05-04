// WDB.js
var WDB = {};

// Mouse Handler - base of all managers:
function MouseHandler() {

	currentHandler = null;

	this.downListeners = new Array();
	this.upListeners = new Array();
	this.moveListeners = new Array();

	this.notify = function(e, a) {
		for ( var i = 0; i < a.length; i++) {
			a[i](e);
		}
	};

	this.notifyUp = function(e) {
		this.notify(e, this.upListeners);
	};

	this.notifyDown = function(e) {
		this.notify(e, this.downListeners);
	};

	this.notifyMove = function(e) {
		this.notify(e, this.moveListeners);
	};
	
	this.registerMoveListener = function(listener) {
		this.moveListeners.push(listener);
	};

	this.registerUpListener = function(listener) {
		this.upListeners.push(listener);
	};

	this.registerDownListener = function(listener) {
		this.downListeners.push(listener);
	};	

	// Add fields to IE event in order to process it the same way as
	// Firefox/Chrome event (from Javascript.RU)
	this.fixEvent = function(e) {
		e = e || window.event;
		if (null == e.pageX && null != e.clientX) {
			var html = document.documentElement;
			var body = document.body;
			e.pageX = e.clientX - (html.clientLeft || 0)
					+ (html && html.scrollLeft || body && body.scrollLeft || 0);
			e.pageY = e.clientY - (html.clientTop || 0)
					+ (html && html.scrollTop || body && body.scrollTop || 0);
		}
		if (!e.which && e.button) {
			e.which = e.button & 1 ? 1 : (e.button & 2 ? 3 : (e.button & 4 ? 2
					: 0));
		}
		return e;
	};

	// Recursive calculate absolute screen element position from
	// it's
	// parents' positions (from Javascript.RU)
	this.getPosition = function(e) {
		var left = 0;
		var top = 0;
		while (e.offsetParent) {
			left += e.offsetLeft;
			top += e.offsetTop;
			e = e.offsetParent;
		}
		left += e.offsetLeft;
		top += e.offsetTop;

		return {
			y : top,
			x : left
		};
	};

	this.setHandlers = function(mouseMove, mouseUp, handler) {
		currentHandler = handler;

		document.onmousemove = mouseMove;
		document.onmouseup = mouseUp;
		document.ondragstart = function() {
			return false;
		};
		document.onselectstart = function() {
			return false;
		};
	};

	this.skipHandlers = function() {
		currentHandler = null;
		document.onselectstart = document.ondragstart = document.onmouseup = document.onmousemove = null;
	};

	this.hideTemporaryObject = function(obj) {
		if (!obj) {
			return;
		}
		obj.style.display = "none";
		obj.className = "";
	};
	
	this.unbindFromMouse = function(notifyObj, tempObjs ) {
		currentHandler.notifyUp( notifyObj );
		for( var i=1; i<arguments.length; i++ ) {
			currentHandler.hideTemporaryObject(arguments[i]);
		}
		currentHandler.skipHandlers();
	};
};

