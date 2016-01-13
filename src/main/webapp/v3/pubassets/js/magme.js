//WEB阅读器在magme.com跨域问题
if(/static\.magme\.cn/.test(document.location.href))  document.domain = 'magme.com'

//判断是否iframe引入
var magmeiframeFlag = window.top != window.self;

//判断是否WEB阅读器
var isweb = magmeiframeFlag&&(/topicid=/.test(window.parent.document.location.search)||/tid=/.test(window.parent.document.location.search)||/\/c\d+/.test(window.parent.document.location.href));

//设备检测
var browser = {
	versions: function() {
		var u = navigator.userAgent,
		app = navigator.appVersion;
		return { //移动终端浏览器版本信息 
			trident: u.indexOf('Trident') > -1,
			//IE内核
			presto: u.indexOf('Presto') > -1,
			//opera内核
			webKit: u.indexOf('AppleWebKit') > -1,
			//苹果、谷歌内核
			gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
			//火狐内核
			mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/),
			//是否为移动终端
			ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
			//ios终端
			android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
			//android终端或者uc浏览器
			iPhone: u.indexOf('iPhone') > -1,
			//是否为iPhone或者QQHD浏览器
			iPad: u.indexOf('iPad') > -1,
			//是否iPad
			webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
		};
	} (),
	language: (navigator.browserLanguage || navigator.language).toLowerCase()
};
//("语言版本: "+browser.language);
//("是否为移动终端: "+browser.versions.mobile);
//("ios终端: "+browser.versions.ios);
//("android终端: "+browser.versions.android);
//("是否为iPhone: "+browser.versions.iPhone);
//("是否iPad: "+browser.versions.iPad);

//解决手机端顺序布局翻页闪烁
$("body.phone").css( {overflow: 'auto'});

//移动端不识别数字为电话号码格式
if (browser.versions.mobile && (browser.versions.android || browser.versions.ios)) {
	$('head').prepend('<meta content="telephone=no" name="format-detection" />');
}

//全部JS调用===============================================================================
var magmeScriptLoad = {
	parm: ['../../../pubassets/js/modernizr-2.5.3.min.js', //功能支持判断
	'../../../pubassets/js/jquery.mousewheel.min.js', //桌面端滚动条功能
	'../../../pubassets/js/jquery.mCustomScrollbar.min.js', //桌面端滚动条功能
	'../../../pubassets/js/jquery.mobile.custom.min.js', //Tap代替Click
	'../../../pubassets/js/magme.shareType.js', //分享页面跳转
	'../../../pubassets/js/magme.coverImg.js', //图片裁切处理
	'../../../pubassets/js/magme.customAnimate.js', //自定义动画
	'../../../pubassets/js/magme.sliderDoor.js', //显示切换
	'../../../pubassets/js/magme.sliderBanner.js', //图片切换
	'../../../pubassets/js/magme.innerScroolbar.js', //内部滚动
	'../../../pubassets/js/magme.images360.js', //360旋转控件
	'../../../pubassets/js/magme.textColumnIcon.js', //文字滚动添加图标
	'../../../pubassets/js/magme.replaceMediaSrc.js', //替换媒体路径
	'../../../pubassets/js/magme.androidVideo.js', //安卓视频处理
	'../../../pubassets/js/magme.touchScroll.js', //内部滚动翻页处理
	'../../../pubassets/js/magme.deviceGallery.js', //相册展示
	'../../../pubassets/js/magme.imagesLoad.js', //图片加载
	'../../../pubassets/js/magme.fontSize.js', //手机端字号调节
	'../../../pubassets/js/magme.gotoPage.js', //页面跳转
	'../../../pubassets/js/magme.ad.js', //广告控件
	'../../../pubassets/js/magme.egg.js', //彩蛋
	'http://s19.cnzz.com/stat.php?id=3197030&web_id=3197030'  //统计代码
	],
	_load: function(src) {
		document.write("<script src='" + src + "'><\/script>");
	}
}
for (var i in magmeScriptLoad.parm) {
	magmeScriptLoad._load(magmeScriptLoad.parm[i]);
}

//document ready==========================================================================
$(function() {

	//阅读器内a链接用阅读器内浏览器打开  
	if (browser.versions.mobile && (browser.versions.android || browser.versions.ios)) {
		if (! (/online=0/.test(document.location.search))) {
			$('a').each(function() {
				var $this = $(this);
				var _href = $this.attr('href');
				var flag = /^javascript/.test(_href); 
				var _click = $this.attr('href').replace(/javascript\:/g, '');
				$this.removeAttr('href');
				$this.bind('tap',
				function(event) {
					var oEvent = event || window.event;
					oEvent.stopPropagation();
					if(flag){
						eval(_click);
					}else{
						if(isweb){
							window.open(_href);	
						}else{	
							browser.versions.android?window.androidObj.linkJump(_href):window.open("iosopenurl:" + _href);
						}	
					}				
				});					


			});
		}
	} else {
		$('a').bind('click',
		function(event) {
			var oEvent = event || window.event;
			oEvent.stopPropagation();
		});
	}

	//安卓和IOS下所有带滚动控件的翻页处理
	if (browser.versions.android || browser.versions.ios) {
		$('.MD_Textcolumn,.Md_InnerscrollBar,.MD_slideBanner,.textArea').each(function(i) {
			var _this = $(this)[0];
			if (typeof(_this) != 'undefined') { //deviceTtouch方法见touchScroll.js
				_this.addEventListener('touchstart', deviceTtouch(this.event, browser.versions.ios, browser.versions.android), false);
				//IOS5翻页bug
				if (navigator.userAgent.indexOf('OS 5_') != -1 && browser.versions.ios) {
					document.addEventListener('touchstart',
					function() {},
					false);
				}
			}
		});
	}

	//桌面端文字滚动js处理
	if (!browser.versions.mobile || (!browser.versions.android && !browser.versions.ios)) {
		if ($('.MD_Textcolumn').length > 0) {
			$(".MD_Textcolumn").css({
				"overflow": "hidden"
			});
			$(".MD_Textcolumn .MD_TextcolumnInner").css({
				"-webkit-column-count": "1",
				"-moz-column-count": "1",
				"column-count": "1",
				height: 'auto'
			});
			$(".MD_Textcolumn").mCustomScrollbar({
				mouseWheelPixels: 300
			});
			$('.MD_Textcolumn').MD_TextcolumnIcon(1);
		}
		if ($(".textArea").length > 0) {
			$('.textArea').css({
				"overflow": "hidden"
			});
			$(".textArea").mCustomScrollbar({
				mouseWheelPixels: 300
			});
			$('.textArea').MD_TextcolumnIcon(1);
		}
	} else {
		$('.MD_Textcolumn').MD_TextcolumnIcon(0);
		$('.textArea').MD_TextcolumnIcon(1);
	}

	//WEB阅读器  滑动事件
	if ((browser.versions.mobile && (browser.versions.android || browser.versions.ios))&&isweb) {
	     $('body').swipeleft(function(){
      		parent.me.readerNext();
      	})
        $('body').swiperight(function(){
        	parent.me.readerPrev();
      	}) 
    }

});

//windows ready==========================================================================
$(window).load(function() {

	//通知安卓阅读器页面加载完成
	if (typeof(androidObj) != "undefined") {
		window.androidObj.pageLoadFinished();
	}

	//移动端相册onclick事件替换为tap
	if (browser.versions.mobile && (browser.versions.android || browser.versions.ios)) {
		$("div[onclick]").each(function(i) {
			var $this = $(this);
			var _click = $this.attr('onclick').replace(/javascript\:/g, '');
			$this.removeAttr('onclick');
			$this.bind('tap',
			function(event) {
				var oEvent = event || window.event;
				oEvent.stopPropagation();
				eval(_click);
			});

		});
	}

	//单指切换显示阅读器header   14-7-16  iframe除外
	if ((browser.versions.mobile && (browser.versions.android || browser.versions.ios))&&!magmeiframeFlag) {
		if (! (/online=0/.test(document.location.search))) {
			$('body').bind('tap',
			function() {
				if (browser.versions.ios) {
					window.open("iossingletap:");
				} else if (browser.versions.android) {
					window.androidObj.touchTitle();
				}
			});
		}
	}

	//ios5整页翻页问题magme.touchScroll.js 修改	
	if (browser.versions.ios && navigator.userAgent.indexOf('OS 5_') != -1) {
		var ios5ScrollW = {
			init: function() {
				var hw = $('html').width();
				var bw = $('body').width();
				var flag = false;
				if (hw < bw) {
					document.addEventListener('touchstart', moveStart(this.event), false);
				}

				function moveStart(ev) {
					return function(ev) {
						var oEvent = ev || event;
						var _startx = oEvent.touches[0].pageX;
						var _starty = oEvent.touches[0].pageY;
						flag = true;
						document.addEventListener('touchmove', moveGo(this.event, _startx, _starty), false);
					}
				}

				function moveGo(ev, _startx, _starty) {
					return function() {
						var oEvent = ev || event;
						var _movex = oEvent.touches[0].pageX;
						var _movey = oEvent.touches[0].pageY;
						document.addEventListener('touchend', moveEnd(this.event, _startx, _starty, _movex, _movey), false);
					}
				}
				function moveEnd(ev, _startx, _starty, _movex, _movey) {
					return function() {
						if (document.body.scrollLeft == 0) {
							if (_startx - _movex < 0 && flag) {
								window.open("iosflip:" + 0);
								flag = false;
							}
						} else if (bw == document.body.scrollLeft + hw) {

							if (_startx - _movex > 0 && flag) {
								window.open("iosflip:" + 1);
								flag = false;
							}
						}
					}
				}

			}
		}.init();
	}

	//除了文本所有控件，旋转后有锯齿bug，修复(不完善缺少阴影等) 
	$('body div:not([class*=textArea])').each(function(i) {
		var $this = $(this);
		var rotateCss = String($this.css('-webkit-transform') || $this.css('transform'));
		var rotateFlag = rotateCss.replace(/[matrix\(\)]/g, '').split(',');

		if ((rotateFlag[0] != 1 || rotateFlag[1] != 0) && rotateFlag[0] != 'none') {
			var _boxShadow = String($this.css('boxShadow')) == 'none' ? '0 0 1px rgba(255,255,255,0)': String($this.css('boxShadow'));
			if (_boxShadow == 'none') {
				_boxShadow = '0 0 1px rgba(255,255,255,0)';
			} else {
				var _ary = _boxShadow.substring('4').replace(/(px)/g, ',').replace(/\)/g, ',').split(',');
				var _flag = false;
				for (var i = 0,
				len = _ary.length; i < len; i++) {
					if (parseInt(_ary[i]) != 0 && _ary[i] != '') {
						_flag = true;
						break;
					}
				}
				if (!_flag) {
					_boxShadow = '0 0 1px rgba(255,255,255,0)';
				}
			}
			rotateCss = 'matrix(' + rotateFlag[0] + ',' + rotateFlag[1] + ',' + rotateFlag[2] + ',' + rotateFlag[3] + ',0,0)';
			rotateCss += ' translateZ(0)';
			if ($this.attr('class').indexOf('CM_rotate') != -1) {
				rotateCss = '';
			}
			$this.css({
				'-webkit-transform': rotateCss,
				'transform': rotateCss,
				boxShadow: _boxShadow,
				'-webkit-box-sizing': 'border-box',
				'box-sizing': 'border-box'
			});
		}
	});

	//桌面端网页预览时，处理跨页内容的ifream滚动条
	if (magmeiframeFlag) {
		var iframeHtmlScroll = {
			init: function() {
				var h = $('html'),
				b = $('body'),
				hw = h.width(),
				hh = h.height(),
				bw = parseInt(b.css('width')),
				bh = parseInt(b.css('height'));

				var scaleX = Math.floor(bw / hw);
				var scaleY = Math.floor(bh / hh);

				if (scaleX > 1) {
					$('html').css({
						'overflow-x': 'scroll'
					});
				}
				if (scaleY > 1) {
					$('html').css({
						'overflow-y': 'scroll'
					});
				}
			}

		}.init();
	}

	//桌面端自定义动画调用     web阅读器除外 
	if((magmeiframeFlag&&!isweb) || /online=0/.test(document.location.search)){
		if(window.fnCustomAnimate){
			fnCustomAnimate();
		}	
	}


		
});