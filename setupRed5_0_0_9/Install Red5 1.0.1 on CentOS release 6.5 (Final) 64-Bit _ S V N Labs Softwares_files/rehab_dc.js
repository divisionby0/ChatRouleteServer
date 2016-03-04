var rehab = rehab || {};
rehab.dc = {

	init : function(callbacks) {
		
		if (Enabler.isInitialized()) enablerInitiated();
		else Enabler.addEventListener(studio.events.StudioEvent.INIT, enablerInitiated);
		
		function enablerInitiated() {
			if(callbacks.onInit) callbacks.onInit();

			if (Enabler.isPageLoaded()) pageLoaded();
			else Enabler.addEventListener(studio.events.StudioEvent.PAGE_LOADED, pageLoaded);
		}
		
		function pageLoaded() {
			if(callbacks.onPolite) callbacks.onPolite();

			if (Enabler.isVisible()) onVisible();
			else Enabler.addEventListener(studio.events.StudioEvent.VISIBLE, onVisible);
		}

		function onVisible() {
			if(callbacks.onVisible) callbacks.onVisible();
		}
	}
}

///////////////////////
// E X A M P L E  U S E
/*

rehab.dc.init({
	onInit: function() {
	
	},
	onPolite: function() {
	
	},
	onVisible: function() {
	
	}
});

*/
