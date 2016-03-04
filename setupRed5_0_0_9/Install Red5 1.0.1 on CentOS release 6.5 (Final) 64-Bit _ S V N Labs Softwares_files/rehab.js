var rehab = {
	image_cache : {},

	createElement : function(_settings) {

		// default settings
		var settings = _settings || {};
		settings.type = settings.type || "div";
		settings.position = settings.position || "absolute";

		var element = document.createElement(settings.type);
		
		switch(settings.type) {
			case "canvas" :
				element.width = settings.width;
				element.height = settings.height;
			break;
			case "video" :
				if (settings.autoplay) element.autoplay = settings.autoplay;
				if (settings.loop) element.loop = settings.loop;
				if (settings.controls) element.controls = settings.controls;
				if (settings.muted) element.muted = settings.muted;
				if (settings.poster) element.poster = settings.poster;
				if (settings.preload) element.preload = settings.preload;
			break;
		}

		// set video sources
		if (settings.sources) {
			// loop through sources array
			var sources = settings.sources;
			for (var i = 0; i < sources.length; i++) {
				// create source tag
				var sourceTag = document.createElement("source");
				sourceTag.src = sources[i].url;
				sourceTag.type = sources[i].type;
				element.appendChild(sourceTag);
			}
		}

		// handle image
		if(settings.backgroundImage) {

			element.style.backgroundSize = settings.backgroundSize || "contain";
			element.style.backgroundRepeat = settings.backgroundRepeat || "no-repeat";

			if(Object.prototype.toString.call(settings.backgroundImage) === "[object Array]") {			
				element.images = settings.backgroundImage;
				element.sequences = {};
				element.currentFrame = 1;
				element.addSequence = function(label, frames) { this.sequences[label] = frames; };
				element.playSequence = function(label) {
					var _this = this;
					var sequence = this.sequences[label];
					for(var i = 0; i < sequence.length; ++i) {
						TweenLite.delayedCall((1 / 24) * i, function(frame) {
							_this.gotoAndStop(frame);
						}, [sequence[i]]);
					}
				};
				element.gotoAndStop = function(frame) { this.currentFrame = frame; this.style.backgroundImage = "url(" + this.images[frame - 1] + ")"; };
				
				for(var i = 0; i < element.images.length; ++i) {
					loadImg(element.images[i], i === 0);
				}
			} else {
				loadImg(settings.backgroundImage, true);	
			}
			
		} else {
			applySettings();
		}
		
		function applySettings() {
			if(settings.id) element.id = settings.id;
			
			if(settings.parent) settings.parent.appendChild(element);
			if(settings.innerHTML) element.innerHTML = settings.innerHTML;
			delete settings.innerHTML;
			delete settings.retina;
			delete settings.parent;
			delete settings.id;
			delete settings.type;
			delete settings.autoplay;
			delete settings.loop;
			delete settings.controls;
			delete settings.muted;
			delete settings.poster;
			delete settings.preload;
			delete settings.sources;
			
			TweenLite.set(element, settings);
		}

		function loadImg(src, doSetImage) {
			var img = rehab.image_cache[src];
			if (img) { // if preloaded
				if(doSetImage) setImage.apply(img);
			} else {
				img = document.createElement("img");
				img.src = src;
				if(doSetImage) img.onload = setImage;
				rehab.image_cache[src] = img;
			}
		}

		function setImage() {			
			var isSVG = this.src.slice(-4) == ".svg";
			if(isSVG) document.body.appendChild(this); // IE fix
			settings.width =  Math.round(settings.width || (settings.retina ? this.width / 2 : this.width));
			settings.height = Math.round(settings.height || (settings.retina ? this.height / 2 : this.height));
			settings.backgroundImage = "url(" + this.src + ")";
			applySettings();
			if(isSVG) document.body.removeChild(this); // IE fix
		}

		// functions	
		element.appendChildren = function(children) { for(var i = 0; i < children.length; ++i) this.appendChild(children[i]); };
		element.set = function(settings) { TweenLite.set(this, settings); };
		element.to = function(time, settings) { TweenLite.to(this, time, settings); };
		element.from = function(time, settings) { TweenLite.from(this, time, settings); };
		element.fromTo = function(time, from, to) { TweenLite.fromTo(this, time, from, to); };
		element.get = function(property) { return ((this._gsTransform && this._gsTransform[property]) || (this._gsTransform && this._gsTransform[property] === 0)) ? this._gsTransform[property] : (this.style[property].slice(-2) == "px") ? parseFloat(this.style[property]) : this.style[property]; };
		element.centerHorizontal = function() { TweenLite.set(this, {left: 0, right: 0, marginLeft: "auto", marginRight: "auto"}); };
		element.centerVertical = function() { TweenLite.set(this, {top: 0, bottom: 0, marginTop: "auto", marginBottom: "auto"}); };
		element.center = function() { TweenLite.set(this, {top: 0, left: 0, right: 0, bottom: 0, margin: "auto"}); };

		return element; 
	},

	preloadImages : function(images, callback, scope) {
		var numImages = images.length;
		var loadedImages = 0;
		var img = null;
		for(var i = 0; i < numImages; ++i) {
 			img = document.createElement("img");
			img.src = img.shortSrc = images[i];
			img.onload = function() {
				loadedImages++;
				rehab.image_cache[this.shortSrc] = this;
				if(loadedImages == numImages) {
					if (scope) {
						callback.call(scope);
					} else {
						callback();
					}
				}
			};
		}
	},
	include: function (script, callback) {
		console.log("#include '" + script + "'");
		var include_script = document.createElement('script');
		include_script.type = "text/javascript";
		include_script.src = script;
		document.head.appendChild(include_script);
		include_script.onload = callback ? callback : function() { console.log("Resource loaded: " + include_script.src); };
		include_script.onerror = function () { console.log("Oops! Could not load resource '"+ script +"'"); };
	}
};