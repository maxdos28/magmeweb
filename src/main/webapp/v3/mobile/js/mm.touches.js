/*!
 * author: xiwan (magme)
 * date: 2011/12/01
 */
(function(win, nav, doc){

var mm = mm||{};	// global naming space
var selfs = [];		// array to hold objects' pointers
var _idx_ = 0;		// object identifier
var _reader_ = null;// internal usage;
var _texter_ = null;// internal usage;
var _adser_ = null;	// internal usage;
var _pages_ = []; // internal usage;
var _sys_ = nav.userAgent.toLowerCase();
var _screen_ = win.screen;

Function.prototype.on = function(name, fn){
	if (name){
		this.prototype[name] = fn;
	}else{
		this.prototype = fn();
	}
	return this;
};

MMConfigs = mm.Configs = {
	version: _sys_.match(/OS ([\d_]+)/i)[1].replace(/_/g, "."),
	agent: _sys_.match(/safari/i), 
	statsT: +(new Date()),
	gestures:{tap:0,swipe:0,pinch:0},
	resolution: _screen_.width+"x"+_screen_.height,
	headerH: $(".header").height(),
	appServerUrl: null,
	statsUrl: "http://stat.magme.com/magmeStat/cvStat",
	dft: "/768_",
	lrg: "/q",
	sml: "/"	
};

MMCache = {};

MMInMem = mm.InMem = {
	/* get the length of object's attrs */
	len: function(o, flag){
		var n, len = 0;  
		for(n in o){  
			if(o.hasOwnProperty(n)){
				if (flag){
					if (o[n]!=null){
						len++;
					}
				}else{
					len++; 
				}
		    }  
		}  
		return len;
	},
	/* return property from Obj */
	getCash: function(o, key){
		for (var n in o){
			if (o.hasOwnProperty(n) && o[n]!=null && n==key){
				return o[n];
			}
		}
	},
	/* delete property from Obj */
	delCash: function(o, key){
		for (var n in o){
			if (o.hasOwnProperty(n) && o[n]!=null && n==key){
				o[n]=null;
				return o;
			}
		}
	},
	/* append property to Obj */
	istCash: function(o, newo){
		o[newo['key']] = newo['value'];
	}
}

MMTools = mm.Tools = {
	/* shortcut getElementById */
	$$: function(triggerElementId){
		return document.getElementById(triggerElementId)||null;
	},
	/* test whether the user disabled the cookie */
	testCookie: function () {  
	    var status = 0,
	    	cookieStr = "wb_check=kcehc_bw";  
	    document.cookie = cookieStr;  
	    if (document.cookie.indexOf(cookieStr) > -1) {  
	        status = 1;  
	        var date = new Date();  
	        date.setTime(date.getTime()-1000);  
	        document.cookie = cookieStr + "; expires=" + date.toGMTString();  
	    }  
	    return status;  
	},
	/* set cookie as pair of name and value */
	setCookie: function(name,value){
	  	var Days = 30; 
	  	var exp  = new Date();   
	  	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	  	document.cookie = name + "="+ escape(value) +";expires="+ exp.toGMTString();
	},
	/* get cookie through param name */
	getCookie: function(name){
	  var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
	  if(arr != null) return unescape(arr[2]); return null;
	},
	/* delete cookie through param name */
	delCookie: function(name){
	  var exp = new Date();
	  exp.setTime(exp.getTime() - 1);
	  var cval=this.getCookie(name);
	  if(cval!=null) document.cookie=name +"="+cval+";expires="+exp.toGMTString();
	},
	/* function bind */
	bind: function(eventTarget, eventType, eventHandler){
		if (eventTarget.addEventListener) {
			eventTarget.addEventListener(eventType, eventHandler, false);
		}else {
			eventTarget.attachEvent(eventType, eventHandler);
		}			
	},
	beacon: function(params){
		var paramsArray = [
		    "ipAddress="+(params.ipAddress||-1),
		    "city="+(params.city||""),
		    "province="+(params.province||""),
		    "userId="+(params.userId||-1),
		    "userType=-1",
		    "muid=-1",
		    "pubId="+(params.pubId||-1),
		    "issueId="+(params.issueId||-1),
		    "pageId="+(params.pageId||-1),
		    "interval="+(params.interval||0),
		    "sys="+(params.sys||""),
		    "agent="+(MMConfigs.agent||""),
		    "version="+(MMConfigs.version||""),
		    "resolution="+(MMConfigs.resolution||""),
		    "isPageAd=0",
		    "tap="+params.tap,
		    "swipe="+params.swipe,
		    "pinch="+params.pinch,
		];
		//alert(MMConfigs.statsUrl+"?"+paramsArray.join("&"))
		(new Image()).src=MMConfigs.statsUrl+"?"+paramsArray.join("&");
	},
	/* prevent default event */
	fixDefault: function( e ){
		( e && e.preventDefault )?e.preventDefault():e.returnValue=false;
		return e;
	},
	/* calculate the distance between two points*/
	distanceBetweenPoints: function(deltaX, deltaY){
		return Math.round(Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2)));
	},
	/* calculate the center point*/
	centerPoints: function(pt, pt2){
		return Math.round((pt+pt2)/2);
	},
	/* calculate swipe angle */
	calculateAngle: function(pos){
		var X = pos.startX-pos.curX;
		var Y = pos.curY-pos.startY;
		var r = Math.atan2(Y,X); //angle in radians (Cartesian system)
		var swipeAngle = Math.round(r*180/Math.PI); //angle in degrees
		if ( swipeAngle < 0 ) { 
			swipeAngle =  360 - Math.abs(swipeAngle); 
		}
		return swipeAngle;
	},
	//Generic Animation Step Value Generator By www.hesido.com
	easeInOut: function(minValue, maxValue, totalSteps, actualStep, powr){
		var delta = maxValue - minValue; 
		var stepp = minValue+(Math.pow(((1 / totalSteps) * actualStep), powr) * delta); 
		return Math.ceil(stepp);
	},
	/* fetch the Event content through ajax */
	fetchContent: function(appServerUrl, imgIndex){
		if (typeof _pages_[imgIndex-1] != 'number'){
			return;
		}
		_texter_.reset();
		var content = MMInMem.getCash(MMCache, imgIndex);
		if (typeof content!=="undefined"){
			$("#content").html(content);
			return;
		}
		$.ajax({
		    url: appServerUrl+'/mobile/mobile-read!getPageContentJson.action?eventId='+MMConfigs.eventId+'&issueId='+MMConfigs.issueId+'&pageNo='+_pages_[imgIndex-1],
		    type: 'GET',
		    dataType: 'json',
		    timeout: 1000,
		    error: function(){
		        console.log('Error loading json document');
		    },
		    success: function(data){
		    	if(data.code == 200 && data.data.textPage){
		    		var content = data.data.textPage.content;
		    		$("#content").html(content);
		    		MMInMem.istCash(MMCache, {key: imgIndex, value: content});
		    	}
		    }
		});				
	},
	fetchAdser: function(MMReader, adId){
		$.ajax({
		    url: MMConfigs.appServerUrl+'/ad/advertise!queryAdsById.action?issueId='+MMConfigs.issueId+'&status=5',
		    type: 'GET',
		    dataType: 'json',
		    timeout: 1000,
		    error: function(){
		    	MMReader.ready();
		        console.log('No ads found!');
		    },
		    success: function(data){
		    	if(data.code == 200){
					_adser_=data.data.insertAdList;
					var len = _adser_.length;
					var eventJump = -1;
					// update the page num attribute;
					_reader_.pages+=2*parseInt(len);
					for (var i=0; i<len; i++){
						_pages_.splice( _adser_[i].pageNo+i*2-1, 0, _adser_[i].userId+"/"+_adser_[i].id+"_768_1", _adser_[i].userId+"/"+_adser_[i].id+"_768_2" );
						if (adId>0 && _adser_[i].id == adId){
							eventJump = _adser_[i].pageNo+i*2-1;
						}
					}
					MMReader.ready(eventJump);
		    	}
		    }
		});	
	},
	/* change the image resource */
	changeImg: function($$, imgPrefix, imgType, imgIndex){
		var bigImg = bigImg || new Image();
		if(typeof imgIndex=='number'){
			bigImg.src = imgPrefix+imgType+imgIndex+".jpg";
		}	
		else{
			bigImg.src = MMConfigs.adsUrl+"/video/"+imgIndex+".jpg";
		}		
		if(bigImg.complete){
			$$.attr("src", bigImg.src);
		}else{
			bigImg.onload = function(){
				$$.attr("src", bigImg.src);
			};		
		}		
	},
	/* change the image resource */
	connectSrv: function(imgIndex){	
		MMConfigs.sys = "ipad.Protrait";
		if(MMConfigs.outW == 520 && typeof imgIndex=='number'){
			this.fetchContent(MMConfigs.appServerUrl, imgIndex);
			MMConfigs.sys = "ipad.Landscape";
		}
		this.beacon({
			pubId: MMConfigs.pubId,
			issueId: MMConfigs.issueId, 
			pageId: imgIndex,
			sys: MMConfigs.sys, 
			interval: -1*MMConfigs.statsT,
			tap: MMConfigs.gestures.tap,
			swipe: MMConfigs.gestures.swipe,
			pinch: MMConfigs.gestures.pinch
		});
		MMConfigs.statsT=+(new Date());
		MMConfigs.gestures={tap:0,swipe:0,pinch:0};
	},
	/* detect device orientation */
	detectOrientation: function(imgIndex){
		if(typeof window.onorientationchange != 'undefined'){
			$("#imgCnt").hide();
			$(window).scrollTop(0);
			MMConfigs.winH = $(window).height();
			if ( orientation == 0 || orientation == 180 ){
				$("#container").removeClass("readHorizontal");
				$("#imgCnt").removeClass("cntLandscape").addClass("cntProtrait");
				MMConfigs.outW = 1000;
				MMConfigs.imgW = 980;
				MMConfigs.imgH = 1306;
			}else{
				$("#container").addClass("readHorizontal");
				$("#imgCnt").removeClass("cntProtrait").addClass("cntLandscape");
				MMConfigs.outW = 520;
				MMConfigs.imgW = 500;
				MMConfigs.imgH = 670;
				MMTools.fetchContent(MMConfigs.appServerUrl, (typeof imgIndex=='number')?imgIndex:(_reader_)?_reader_.imgIndex:1);
			}
			$("#pageNum").css("left",Math.round((MMConfigs.imgW-100)/2)+"px");
			if(_reader_)
				_reader_.reset();
			$("#imgCnt").fadeIn(200);
		}
	},
	/* flip page animation */
	flipPage: function($$, horzDiff, flag){
		var t = (flag)?.15:0;
		var l = (flag)?horzDiff:0;
		$$.css({
			"-webkit-transform": "matrix(1, 0, 0, 1, "+l+", 0)",
			"-webkit-transition":"-webkit-transform "+t+"s ease-out"
		})[(horzDiff>0)?"prev":"next"]().css({
			"-webkit-transform": "matrix(1, 0, 0, 1, "+l+", 0)",
			"-webkit-transition":"-webkit-transform "+t+"s ease-out"
		});
	},
	/* move page animation */
	movePage: function($$, h, v, t){
		$$.css({
			"-webkit-transform": "matrix(1, 0, 0, 1, "+h+", "+v+")",
			"-webkit-transition":"-webkit-transform "+t+"s ease-out"
		});	
	},
	/* scale page animation */
	scalePage: function($$, l, t, w, h, s, i){
		$$.css({
			"left": l+"px", 
			"top": t+"px",
			"width": w+"px",
			"height": h+"px",
			"-webkit-transform": "scale("+s+")",
			"-webkit-transition":"-webkit-transform "+i+"s ease-out"
		});		
	},
	/* pinch page animation */
	pinchPage: function($$, x, y, s, i){
		$$.css({
			"-webkit-transform-origin": x+"px "+y+"px",
			"-webkit-transform": "scale("+s+")",
			"-webkit-transition":"-webkit-transform "+i+"s ease-out"
		});	
	},
	foo: function(){}
};
/*
 * Touches class
 * 
 * */
MMTouches = mm.Touches = function(){
	selfs.push(this);
	this.currDis = this.intDis = 0;
	this.swipeLength = 0;
	this.fingerCount = 0;
	this.finger1 = {startX:0, startY:0, curX:0, curY:0};
	this.finger2 = {startX:0, startY:0, curX:0, curY:0};
};

mm.Touches.on('init', function(touches, idx){
	var self=selfs[idx];
	this.$$.guid = idx;
	if (this.$$){
		self.touches = touches;	
		MMTools.bind(this.$$, "touchstart", this.touchStart);
		MMTools.bind(this.$$, "touchend", this.touchEnd);
		MMTools.bind(this.$$, "touchmove", this.touchMove);
		MMTools.bind(this.$$, "touchcancel", this.touchCancel);
	}else{
		console.log("touch object not exist!");
	}
})
.on('touchStart', function(event){
	$("#pageNum").fadeOut('fast');
	MMTools.fixDefault(event);
	var self=selfs[this.guid];
	self.fingerCount = event.touches.length;
	switch(self.fingerCount){
	case 1:
		self.finger1.startX = event.touches[0].pageX;
		self.finger1.startY = event.touches[0].pageY;
		break;
	case 2:
		self.finger1.startX = event.touches[0].pageX;
		self.finger1.startY = event.touches[0].pageY;
		
		self.finger2.startX = event.touches[1].pageX;
		self.finger2.startY = event.touches[1].pageY;
		
		self.initDis = MMTools.distanceBetweenPoints(
			(self.finger2.startX-self.finger1.startX),
			(self.finger2.startY-self.finger1.startY)
		);
		break;
	default:
		self.touchCancel(self, event);
		break;
	}
	
})//MMTools.debounce
.on('touchMove', function(event){
	MMTools.fixDefault(event);
	var self=selfs[this.guid];
	if (self.fingerCount != event.touches.length){
		return;
	}
	var tStamp = +(new Date());
	switch(self.fingerCount){
	case 1:
		if (tStamp&1){
			self.finger1.curX = event.touches[0].pageX;
			self.finger1.curY = event.touches[0].pageY;
			var deltaX = self.finger1.curX - self.finger1.startX;
			var deltaY = self.finger1.curY - self.finger1.startY;
			self.swipeLength = MMTools.distanceBetweenPoints(deltaX, deltaY);
			
			self.swipeAngle = MMTools.calculateAngle({
				startX: self.finger1.startX, startY: self.finger1.startY,
				curX: self.finger1.curX, curY: self.finger1.curY
			});
			if ( (self.swipeAngle <= 45) && (self.swipeAngle >= 0) ) {
				self.swipeDirection = 'a';
			} else if ( (self.swipeAngle <= 360) && (self.swipeAngle >= 315) ) {
				self.swipeDirection = 'a';
			} else if ( (self.swipeAngle >= 135) && (self.swipeAngle <= 225) ) {
				self.swipeDirection = 'd';
			} else if ( (self.swipeAngle > 45) && (self.swipeAngle < 135) ) {
				self.swipeDirection = 's';
			} else {
				self.swipeDirection = 'w';
			}
			if(self.touches.swipe)
				self.touches.swipe.apply(self.touches.target, [deltaX, deltaY, self.swipeDirection, 1]);	
		}
		break;
	case 2:
		if (tStamp&1){
			self.finger1.currX = event.touches[0].pageX;
			self.finger1.currY = event.touches[0].pageY;
			
			self.finger2.currX = event.touches[1].pageX;
			self.finger2.currY = event.touches[1].pageY;
			if (self.currDis)
				self.initDis = self.currDis;
			self.currDis = MMTools.distanceBetweenPoints(
				(self.finger2.currX-self.finger1.currX),
				(self.finger2.currY-self.finger1.currY)
			);
			var centX = MMTools.centerPoints(self.finger2.currX, self.finger1.currX);
			var centY = MMTools.centerPoints(self.finger2.currY, self.finger1.currY);
			
			if (self.initDis == 0) {
				self.initDis = self.currDis;
			}
			else if (self.currDis/self.initDis != 1){
				self.pinchDirection = (self.currDis/self.initDis>1)?1:0;
				if(self.touches.pinch)
					self.touches.pinch.apply(self.touches.target, [self.pinchDirection, centX, centY]);
			}
		}
		break;
	default:
		self.touchCancel(self, event);
		break;
	}
})
.on('touchEnd', function(event){
	MMTools.fixDefault(event);
	var self=selfs[this.guid];
	switch(self.fingerCount){
		case 1:
			if (!self.swipeLength){
				MMConfigs.gestures.tap++;
				if(self.touches.tapCallback)
					self.touches.tapCallback.apply(self.touches.target, [self.finger1.startX, self.finger1.startY]);
			}else{
				MMConfigs.gestures.swipe++;
				if(self.touches.swipeCallback)
					self.touches.swipeCallback.apply(self.touches.target, [self.swipeDirection]);
			}
			break;
		case 2:
			MMConfigs.gestures.pinch++;
			if(self.touches.pinchCallback)
				self.touches.pinchCallback.call(self.touches.target);
			break;
		default:
			//reader.reset();
			break;
	}
	self.touchCancel(self, event);
})
.on('touchCancel', function(that, event){
	that.fingerCount = 0;
	that.swipeLength = 0;
	that.currDis = that.intDis = 0;
	that.finger1 = {startX:0, startY:0, curX:0, curY:0};
	that.finger2 = {startX:0, startY:0, curX:0, curY:0};
})

/*
 * Texter class
 * */
MMTexter = mm.Texter = function(elemId){
	_texter_ = this;
	this.$$ = MMTools.$$(elemId); 	// dom object
	this.jQ = $(this.$$);			// jquery object
}

mm.Texter
/* inheritance func */
.on('', function(){
	return new MMTouches();
})
/* ready func: initialize the each touches' funcs */
.on('ready', function(){
	this.init({
		target: this,
		swipe: this.fingerScroll,
		swipeCallback: this.fingerScrollCallback
	}, _idx_++);
	return this;
})
/* reset func: reset text page position */
.on('reset', function(){
	this.width = this.jQ.width();
	this.height = this.jQ.height();
	MMTools.scalePage(this.jQ, 0, 0, this.width, this.height, 1, .15);
})
/* fingerScroll func: triggerred when the finger start to swipe vertically */
.on('fingerScroll', function(horzDiff, vertDiff, swipeDirection){
	if (swipeDirection=='w'||swipeDirection=='s'){
		MMTools.movePage(this.jQ, 0, vertDiff, .2);
	}
})
/* fingerScrollCallback func: call back after finger leaved */
.on('fingerScrollCallback', function(swipeDirection){
	var that = this;
	setTimeout(function(){
		var _top=that.jQ.position().top;
		if (_top>0){
			that.reset();
		}else{
			MMTools.scalePage(that.jQ, 0, _top, that.width, that.height, 1, 0);
		}
	}, 300);	
});

/*
 * Reader class
 * 
 * */
MMReader = mm.Reader = function(elemId, appServerUrl, magServerUrl, eventId, pubId, issueId, pages, imgIndex){
	MMTools.detectOrientation(imgIndex);
	
	MMConfigs.appServerUrl = appServerUrl;
	MMConfigs.pubId = pubId;
	MMConfigs.issueId = issueId;
	MMConfigs.eventId = eventId;
	
	_reader_ = this;
	this.$$ = MMTools.$$(elemId); 	// dom object
	this.jQ = $(this.$$);			// jquery object
	this.imgPrefix=magServerUrl+"/"+pubId+"/"+issueId;
	this.pages=Number(pages);
	this.imgIndex=Number(imgIndex); //become an index of array
	this.bound={left:0, top:0, width:MMConfigs.imgW, height:MMConfigs.imgH};
	this.tmpBound={left:0, top:0, width:MMConfigs.imgW, height:MMConfigs.imgH};
	this.scale={max: 2.5, min: .5, prv:1, cur:1, tlt:1, flag:0};
	this.drag={length: 20, left: 0, flag:0};
	
	var ps=0;
	while(ps<this.pages){
		_pages_.push(++ps);
	}

	if (imgIndex>2 || imgIndex<pages-2){
		(new Image()).src = this.imgPrefix+MMConfigs.dft+(imgIndex-2)+".jpg";
		(new Image()).src = this.imgPrefix+MMConfigs.dft+(imgIndex+2)+".jpg";	
	}else if (imgIndex<=2){
		(new Image()).src = this.imgPrefix+MMConfigs.dft+(imgIndex+2)+".jpg";
	}else if (imgIndex>=pages-2){
		(new Image()).src = this.imgPrefix+MMConfigs.dft+(imgIndex-2)+".jpg";
	}

	$("#turnLeft").click(function(){
		if(_reader_.imgIndex-->1){	
			MMTools.connectSrv(_reader_.imgIndex);
			_reader_.loadPages(MMConfigs.dft, "d");	
			_reader_.reset();
		}		
	});
	
	$("#turnRight").click(function(){
		if(_reader_.imgIndex++<pages){
			MMTools.connectSrv(_reader_.imgIndex);	
			_reader_.loadPages(MMConfigs.dft, "a");		
			_reader_.reset();
		}		
	});
};

mm.Reader
.on('', function(){
	return new MMTouches();
})
.on('ready', function(eventJump){
	if (eventJump && eventJump>0) {
		this.imgIndex = eventJump+1;
	}
	MMTools.changeImg( this.jQ.prev(), this.imgPrefix, MMConfigs.dft, _pages_[this.imgIndex-2] );
	MMTools.changeImg( this.jQ.next(), this.imgPrefix, MMConfigs.dft, _pages_[this.imgIndex] );				
	this.reset();
	this.init({
		target: this,
		tap: this.doubleTapFunc,
		tapCallback: this.doubleTapCallback,
		swipe: this.dragFunc,
		swipeCallback: this.dragCallback,
		pinch: this.scaleFunc,
		pinchCallback: this.scaleCallback
	}, _idx_++);
	return this;
})
.on('reset', function(){
	if (this._Timer){
		clearInterval(this._Timer);
	}

	MMTools.changeImg(this.jQ, this.imgPrefix, MMConfigs.dft, _pages_[this.imgIndex-1]);
	MMTools.scalePage(this.jQ, 0, 0, MMConfigs.imgW, MMConfigs.imgH, 1, .15);
	MMTools.scalePage(this.jQ.next(), MMConfigs.outW, 0, MMConfigs.imgW, MMConfigs.imgH, 1, .15);
	MMTools.scalePage(this.jQ.prev(), -1*MMConfigs.outW, 0, MMConfigs.imgW, MMConfigs.imgH, 1, .15);

	this.bound={left:0, top:0, width:MMConfigs.imgW, height:MMConfigs.imgH};
	this.tmpBound={left:0, top:0, width:MMConfigs.imgW, height:MMConfigs.imgH};
	this.scale={max: 2.5, min: .5, prv:1, cur:1, tlt:1, flag:0};
	this.drag={length: 20, left: 0, flag:0};	
})
.on('loadPages', function(type, direction){
	var preLoadIdx = 1;
	MMTools.changeImg( this.jQ, this.imgPrefix, type, _pages_[this.imgIndex-1] );
	this.imgIndex=(this.imgIndex<1)?1:(this.imgIndex>this.pages)?this.pages:this.imgIndex;
	var preIdx = (this.imgIndex>1)?this.imgIndex-1:1;
	var nxtIdx = (this.imgIndex<this.pages)?this.imgIndex+1:this.pages;
	//alert(_pages_[preIdx-1]+" "+_pages_[this.imgIndex-1]+" "+_pages_[nxtIdx-1])
	if (direction=="d"){
		MMTools.changeImg( this.jQ.prev(), this.imgPrefix, type, _pages_[preIdx-1] );
		MMTools.changeImg( this.jQ.next(), this.imgPrefix, type, _pages_[nxtIdx-1] );
		preLoadIdx = (this.imgIndex>=2)?(this.imgIndex-2):1;
	}else{
		MMTools.changeImg( this.jQ.next(), this.imgPrefix, type, _pages_[nxtIdx-1] );
		MMTools.changeImg( this.jQ.prev(), this.imgPrefix, type, _pages_[preIdx-1] );
		preLoadIdx = (this.imgIndex<this.pages-1)?(this.imgIndex+2):this.pages;
	}
	$("#pageNum").text(this.imgIndex).fadeIn('fast');
	// preload function
	(new Image()).src = this.imgPrefix+type+preLoadIdx+".jpg";
})
.on('dragFunc', function(horzDiff, vertDiff, swipeDirection, autoFlip){
	var imgW = MMConfigs.imgW;
	var imgH = MMConfigs.imgH;
	var outW = MMConfigs.outW;
	if( this.scale.flag==0){
		if (swipeDirection=='a' || swipeDirection=='d'){
			this.drag.flag=1;
			if (Math.abs(this.jQ.position().left)>.5*imgW){
				if(autoFlip){
					if (this._Timer){
						clearInterval(this._Timer);
					}
					var that = this;
					this._Timer = setInterval(function(){
						(horzDiff>0)?(--that.imgIndex):(++that.imgIndex);
						that.loadPages(MMConfigs.sml, swipeDirection);				
					}, 350);					
				}else{
					MMTools.flipPage(this.jQ, horzDiff, 1);
				}			
			}else{
				MMTools.flipPage(this.jQ, horzDiff, 1);
			}
		}else{
			
		}
	}else{
		this.tmpBound.left=horzDiff;
		this.tmpBound.top=vertDiff;
		MMTools.movePage(this.jQ, horzDiff, vertDiff, .1);		
	}
})
.on('dragCallback', function(swipeDirection){
	var imgW = MMConfigs.imgW;
	var imgH = MMConfigs.imgH;
	var that=this;
	if( that.scale.flag==0){
		setTimeout(function(){
			var _L = that.jQ.position().left;
			var flag = (_L>0)?1:0;
			switch(_L){
				case 0:
					if (swipeDirection=='w'){
						if ($("#imgCnt").css("-webkit-transform").indexOf(MMConfigs.headerH)!=-1){
							MMTools.movePage($("#imgCnt"), 0, (MMConfigs.winH-MMConfigs.headerH-MMConfigs.imgH), .25);
						}else{
							MMTools.movePage($(".header, #imgCnt, #txtCnt"), 0, -1*MMConfigs.headerH, .25);
						}
					}else{
						MMTools.movePage($(".header, #imgCnt, #txtCnt"), 0, 0, .25);
					}
					break;
				default:
					if (that._Timer){
						clearInterval(that._Timer);
					}
					var factor = (Math.abs(_L)>that.drag.length)?(flag)?1:-1:0;
					that.dragFunc(factor*MMConfigs.outW, 0, swipeDirection, 0);
					if (factor){
						(flag)?(--that.imgIndex):(++that.imgIndex);
						setTimeout(function(){
							MMTools.flipPage(that.jQ, factor*MMConfigs.outW, 0);
							MMTools.connectSrv(that.imgIndex);
							that.loadPages(MMConfigs.dft, swipeDirection);
							that.drag.flag=0;
						}, 300);
					}
					var statsT=+(new Date());
					MMConfigs.statsT -= statsT;
					break;
			}
		}, 200);
	}else{
		function setUpOrigin(){
			that.bound.left = Math.round(that.jQ.position().left);
			that.bound.top = Math.round(that.jQ.position().top);
			MMTools.scalePage(that.jQ, that.bound.left, that.bound.top, that.bound.width, that.bound.height, 1, 0);
		}
		setTimeout(function(){
			var transformMatrix = {X:-1, Y:-1};
			
			var L1 = -1*that.bound.left;
			var T1 = -1*that.bound.top;
			var L2 = L1+imgW-that.bound.width;
			var T2 = T1+imgH-that.bound.height;
			
			var curL = that.jQ.position().left;
			var curT = that.jQ.position().top;
			var boundR = imgW-that.bound.width;
			var boundB = imgH-that.bound.height;
				
			var cond1=(curL>0);
			var cond2=(curT>0);
			var cond3=(curL<boundR);
			var cond4=(curT<boundB);
			
			var cases =[
				(cond1 && cond2), // left top
				(cond1 && !cond2 && !cond4), // left
				(cond1 && cond4), // left bottom
				(cond4 && !cond1 && !cond3), // bottom
				(cond3 && cond4), // rightn bottom
				(cond3 && !cond2 && !cond4), // right 
				(cond2 && cond3), // right top
				(cond2 && !cond1 && !cond3)// top
			];
			var len = cases.length;
			while(len--){
				if(cases[len]){
					if(cond1 && (len==0||len==1||len==2)){transformMatrix.X=L1;}else if(!cond1 && (len==3||len==7)){transformMatrix.X=that.tmpBound.left;}
					if(cond2 && (len==0||len==6||len==7)){transformMatrix.Y=T1;}else if(!cond2 && (len==1||len==5)){transformMatrix.Y=that.tmpBound.top;}
					if(cond3 && (len==4||len==5||len==6)){transformMatrix.X=L2;}else if(!cond3 && (len==3||len==7)){transformMatrix.X=that.tmpBound.left;}
					if(cond4 && (len==2||len==3||len==4)){transformMatrix.Y=T2;}else if(!cond4 && (len==1||len==5)){transformMatrix.Y=that.tmpBound.top;}
					break;
				}
			}
			transformMatrix.X=transformMatrix.X.toFixed(2);
			transformMatrix.Y=transformMatrix.Y.toFixed(2);
			if (transformMatrix.X!=-1 || transformMatrix.Y!=-1){	
				MMTools.movePage(that.jQ, transformMatrix.X, transformMatrix.Y, 0);
				setTimeout(function(){
					setUpOrigin();
				}, 200);		
			}else{
				setUpOrigin();
			}
		}, 300);	
	}

})
.on('doubleTapFunc', function(){
	
})
.on('doubleTapCallback', function(centX, centY){
	if (!this.clock){
		this.clock = +(new Date());
		var that=this;
		setTimeout(function(){
			that.clock =  null;
		}, 330);
	}else{
		var clock2 = +(new Date());
		if( (this.scale.flag==0 || this.scale.flag==-1)&& clock2-this.clock<300 ){
			var scaleV = 1.75;
			if ( this.bound.width >= scaleV*MMConfigs.imgW){
				scaleV = 1;
				this.scale.flag=0;
			}else{
				this.scale.flag=-1;
			}
			MMTools.pinchPage(this.jQ, centX, centY, scaleV, .2);
			var that = this;
			setTimeout(function(){
				that.bound.width=scaleV*MMConfigs.imgW;
				that.bound.height=scaleV*MMConfigs.imgH;
				that.bound.left=Math.round(centX*(1-scaleV));
				that.bound.top=Math.round(centY*(1-scaleV));
				MMTools.scalePage(that.jQ, that.bound.left, that.bound.top, that.bound.width, that.bound.height, 1, 0);
			}, 300);
		}
		this.clock = null;
	}
})
.on('scaleFunc', function(pinchDirection, centX, centY){
	if(!this.drag.flag){
		var imgWidth = this.tmpBound.width;
		var imgHeight = this.tmpBound.height;
		var factor = 1;
		if ( pinchDirection==1 ){
			if (imgWidth < this.scale.max*MMConfigs.imgW){
				this.scale.flag = 1; 
			}else{
				this.scale.flag=-1;
			}
		}else{
			if (imgWidth > this.scale.min*MMConfigs.imgW){
				this.scale.flag = 2;
				factor = -1;	
			}else{
				this.scale.flag=-2;
			}
		}
		if (this.scale.flag>0){
			this.scale.cur += .05*factor;
			this.tmpBound.width=this.scale.cur*this.bound.width;
			this.tmpBound.height=this.scale.cur*this.bound.height;
			//this.tmpBound.left=Math.round(centX*(1-this.scale.cur));
			//this.tmpBound.top=Math.round(centY*(1-this.scale.cur));	
			var _centX = Math.abs(this.bound.left)+centX;
			var _centY = Math.abs(this.bound.top)+centY;
			MMTools.pinchPage(this.jQ, _centX, _centY, this.scale.cur, .3);
		}
	}else{
		this.scale.flag=0;
	}
})
.on('scaleCallback', function(){
	if( (this.jQ.prev().position().left+MMConfigs.outW) || 
		(this.jQ.next().position().left-MMConfigs.outW) ){
		this.reset();
		return;
	}
	var that=this;
	setTimeout(function(){
		//that.scale.tlt = that.scale.cur*that.scale.prv;	
		//that.scale.prv = that.scale.cur;				
		that.bound.width = that.tmpBound.width;
		that.bound.height = that.tmpBound.height;
		that.bound.left = Math.max(MMConfigs.outW-that.bound.width, Math.min(0, that.jQ.position().left));
		that.bound.top = Math.max(MMConfigs.imgH-that.bound.height, Math.min(0, that.jQ.position().top));

		that.scale.cur = 1;
		if (that.bound.width<=MMConfigs.imgW){
			that.reset();
		}else{
			MMTools.scalePage(that.jQ, that.bound.left, that.bound.top, that.bound.width, that.bound.height, 1, 0);
			if ((that.jQ.width()>2*MMConfigs.imgW))
				MMTools.changeImg(that.jQ, that.imgPrefix, MMConfigs.lrg, _pages_[that.imgIndex-1]);
			else
				MMTools.changeImg(that.jQ, that.imgPrefix, MMConfigs.dft, _pages_[that.imgIndex-1]);	
		}	
	}, 250);
});

if (MMTools.testCookie() && !MMTools.getCookie("probe")){
	MMTools.setCookie("probe", 1);
	if(MMConfigs.version[0]<5){
		alert("For better experience, please update your iso to 5.0!!!");
	}
}

MMSetup = function(MMTexter, MMReader, MMAds, adId){
	MMTexter.ready();
	if (MMAds){	
		MMConfigs.adsUrl=MMAds;
		MMTools.fetchAdser(MMReader, adId);	
	}else{
		MMReader.ready();
		console.log("Ads is closed!!!");
	}
};

win.onorientationchange = MMTools.detectOrientation;

})(window, navigator, document);