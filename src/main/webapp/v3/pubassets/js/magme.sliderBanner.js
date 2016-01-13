//图片切换
$.fn.slideBanner = function(options) {
	var setting = {
		tp: 'flip',
		btn: false,
		auto: false,
		time: 3,
		showBtn: true
	};
	var opts = $.extend({},
	setting, options);
	var _webkitIframe = magmeiframeFlag && !browser.versions.webKit;

	//初始dom结构，调用相应css
	function fnCreateCSS(lnk, $hm, imgs, w, iframeData) {
		//桌面端默认左右滑动效果
		if (_webkitIframe) {
			$hm.parent().css({
				overflow: 'hidden'
			});
			$('div.MD_slideBanner .MD_sbCover').css({
				position: 'absolute',
				width: iframeData[0] + 'px',
				height: "100%",
				top: 0,
				left: 0
			}).find('img').css({
				display: 'block',
				float: 'left',
				width: iframeData[1] + 'px',
				height: iframeData[2] + 'px'
			});
		} else {
			var styleTag = document.createElement("link"),
			htmlTag = ['<div class="MD_sbCard"><div class="MD_sbFront"></div><div class="MD_sbBack"></div></div>', '<div class="MD_sbCard MD_sbTransflip1"><div class="MD_sbFront"></div> <div class="MD_sbBack"></div></div><div class="MD_sbCard MD_sbTransflip2"><div class="MD_sbFront"></div><div class="MD_sbBack"></div></div><div class="MD_sbCard MD_sbTransflip3"><div class="MD_sbFront"></div><div class="MD_sbBack"></div></div><div class="MD_sbCard MD_sbTransflip4"><div class="MD_sbFront"></div><div class="MD_sbBack"></div></div><div class="MD_sbCard MD_sbTransflip5"> <div class="MD_sbFront"></div><div class="MD_sbBack"></div></div>', '<div class="MD_cudefront MD_sbFront"></div><div class="MD_cudetop MD_sbBack"></div><div class="MD_cudebottom MD_sbBack"></div><div class="MD_cuderight MD_sbBack"></div><div class="MD_cudeleft MD_sbBack"></div>'];
			if ($('#slideBannerCSS').length == 0) {
				styleTag.setAttribute('type', 'text/css');
				styleTag.setAttribute('rel', 'stylesheet');
				styleTag.setAttribute('id', 'slideBannerCSS');
				styleTag.setAttribute('href', "../../../pubassets/css/slideDoor/" + lnk + ".css");
				$("head")[0].appendChild(styleTag);
			}
			if (lnk == 'flip' || lnk == 'rotation' || lnk == 'other') {
				$hm.append(htmlTag[0]);
			} else if (lnk == 'multiflip') {
				$hm.append(htmlTag[1]);
			} else if (lnk == 'cube') {
				var _width = w / 2;
				$hm.append(htmlTag[2]);
				$hm.find('div.MD_cudefront').css({
					"-webkit-transform": "scale3d(.858,.858,.858) translate3d(0,0," + _width + "px)"
				});
				$hm.find('div.MD_cuderight').css({
					"-webkit-transform": "scale3d(.858,.858,.858) rotate3d(0,1,0,90deg) translate3d(0,0," + _width + "px)"
				});
				$hm.find('div.MD_cudeleft').css({
					"-webkit-transform": "scale3d(.858,.858,.858) rotate3d(0,1,0,-90deg) translate3d(0,0," + _width + "px)"
				});
				$hm.find('div.MD_cudetop').css({
					"-webkit-transform": "scale3d(.858,.858,.858) rotate3d(1,0,0,90deg) translate3d(0,0," + _width + "px)"
				});
				$hm.find('div.MD_cudebottom').css({
					"-webkit-transform": "scale3d(.858,.858,.858) rotate3d(0,1,0,-90deg) translate3d(0,0," + _width + "px)"
				});
			}
			$hm.find('.MD_sbFront').append(imgs);
			$hm.find('.MD_sbBack').append(imgs);
		}
	}

	return $(this).each(function() {
		var $this = $(this),
		$teWrapper = $this.find('.MD_sbWrapper'),
		$teCover = $this.find('div.MD_sbCover'),
		$teImages = $this.find('div.MD_sbImages > img'),
		imagesCount = $teImages.length,
		currentImg = 0,
		$teControls = $this.find('.MD_sbControls'),
		$navNext = $teControls.find('.MD_sbNext'),
		$navPrev = $teControls.find('.MD_sbPrev'),
		type = opts.tp,
		$teTransition = $this.find('.MD_sbTransition'),
		// requires perspective
		wPerspective = ['flip1', 'flip2', 'rotation', 'multiflip1', 'multiflip2', 'cube', 'other1', 'other2', 'other3', 'other4'],
		animated = false,
		// check for support
		hasPerspective = Modernizr.csstransforms3d;

		var $tWidth = $this.width();

		if (!$('html').data('slideBanner')) {
			$('html').data('slideBanner', type);
		} else {
			type = $('html').data('slideBanner');
		}

		var tpLink = type.replace(/\d/g, '');
		if (tpLink != 'multiflip') {
			$this.find('img').attr({
				width: '100%',
				height: '100%'
			});
		} else {
			$this.find('img').attr({
				width: $tWidth,
				height: $this.height()
			});
		}

		//定义动画时间
		var typeTime = {
			flip: '1.3',
			rotation: '0.8',
			multiflip: '1.5',
			cube: '1.2',
			other: '0.6'
		};
		var time;
		for (var ty in typeTime) {
			if (ty == tpLink) {
				time = parseFloat(typeTime[ty]);
			}
		};

		time = (time + opts.time) * 1000;

		var _htmlImg = $this.find('div.MD_sbImages').html();

		var autoPlayflag = null;

		$teCover.append(_htmlImg);

		$teCover.find('img:first').show();

		if (opts.btn) {
			$teControls.show();
		} else {
			/*  $teWrapper.click(function(){
                        fn_NextGo($teWrapper);
                    });*/
		}

		var $thisData = [$tWidth * imagesCount, $tWidth, $this.height()];
		fnCreateCSS(tpLink, $teTransition, _htmlImg, $tWidth, $thisData);

		if (opts.showBtn) {
			var $btnStop = $("<div class='MD_autoButtn'><img src='../../../pubassets/imgs/icon/autobtn.png' /></div>");
			if (opts.auto) {
				$btnStop.addClass('stop').find('img').css({
					marginTop: -40
				});
			} else {
				$btnStop.addClass('play').find('img').css({
					marginTop: 0
				});
			}
			$this.append($btnStop);

			$btnStop.bind('tap',
			function(event) {
				event.stopPropagation();

				if (_webkitIframe) {
					if ($(this).hasClass('play')) {
						opts.auto = true;
						$(this).addClass('stop').removeClass('play').find('img').css({
							marginTop: -40
						});
						iframeNextGo();
						iframeAutoPlay();
					} else {
						opts.auto = false;
						$(this).addClass('play').removeClass('stop').find('img').css({
							marginTop: 0
						});
						iframeAutoPlay();
					}
				} else {
					if ($(this).hasClass('play')) {
						opts.auto = true;
						$(this).addClass('stop').removeClass('play').find('img').css({
							marginTop: -40
						});
						fn_NextGo($teWrapper);
						fn_autoPlay();
					} else {
						opts.auto = false;
						$(this).addClass('play').removeClass('stop').find('img').css({
							marginTop: 0
						});
						fn_autoPlay();
					}
				}

			});
		}

		if (browser.versions.mobile && (browser.versions.android || browser.versions.ios)) {

			$teWrapper.bind('swiperight',
			function(event) {
				var oEvent = event || window.event;
				oEvent.stopPropagation();
				oEvent.preventDefault();		
				fn_PrevGo($teWrapper);
			});
			$teWrapper.bind('swipeleft',
			function(event) {
				var oEvent = event || window.event;
				oEvent.stopPropagation();
				oEvent.preventDefault();				
				fn_NextGo($teWrapper);
			});

			$teWrapper.bind('touchstart tap',
			function(event) {
				var oEvent = event || window.event;
				oEvent.stopPropagation();
				oEvent.preventDefault();
			});
		}

		if (magmeiframeFlag && browser.versions.webKit) { //阅读器 webkit
			$teWrapper.click(function() {
				fn_NextGo($teWrapper);
			});
		}

		if (_webkitIframe) { //阅读器
			$teCover.click(function() {
				if (!$(this).is(':animated')) {
					iframeNextGo();
				}

			});
		}

		function iframeNextGo() { //阅读器 下一页
			currentImg++;
			if (currentImg == imagesCount - 1) {
				$teCover.find('img:first').css({
					position: 'relative',
					left: $tWidth * imagesCount
				});
			}

			$teCover.animate({
				marginLeft: '-' + currentImg * $tWidth
			},
			400,
			function() {
				if (currentImg == imagesCount) {
					currentImg = 0;
					$teCover.css({
						marginLeft: 0
					}).find('img:first').css({
						position: 'static',
						left: 0
					});
				}
			});

			if (autoPlayflag) {
				clearTimeout(autoPlayflag);
				autoPlayflag = null;
			}
			iframeAutoPlay();
		}

		function iframeAutoPlay() {
			if (opts.auto) {
				autoPlayflag = setTimeout(function() {
					if (opts.auto) {
						iframeNextGo();
					}
				},
				time);
			}
		}

		function fn_NextGo(dom) {

			if (hasPerspective && animated) {
				return false;
			}
			animated = true;

			switch (tpLink) {
			case 'flip':
				dom.find('.MD_sbCard').removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				break;
			case 'rotation':
				dom.find('.MD_sbFront').removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				dom.find('.MD_sbBack').removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				break;
			case 'other':
				dom.find('.MD_sbFront').removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				dom.find('.MD_sbBack').removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				break;
			case 'cube':
				dom.find('.cube').removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				break;
			case 'multiflip':
				dom.find(".MD_sbTransition>div").removeClass('MD_sbtnPrev').addClass('MD_sbtnNext');
				break;
			default:
				break;
			}

			showNext(1);

			if (autoPlayflag) {
				clearTimeout(autoPlayflag);
				autoPlayflag = null;
			}
			fn_autoPlay();

			return false;

		};

		function fn_PrevGo(dom) {

			if (hasPerspective && animated) {
				return false;
			}

			animated = true;

			switch (tpLink) {
			case 'flip':
				dom.find('.MD_sbCard').removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				break;
			case 'rotation':
				dom.find('.MD_sbFront').removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				dom.find('.MD_sbBack').removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				break;
			case 'other':
				dom.find('.MD_sbFront').removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				dom.find('.MD_sbBack').removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				break;
			case 'cube':
				dom.find('.cube').removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				break;
			case 'multiflip':
				dom.find(".MD_sbTransition>div").removeClass('MD_sbtnNext').addClass('MD_sbtnPrev');
				break;
			default:
				break;
			}

			showNext(0);
			if (autoPlayflag) {
				clearTimeout(autoPlayflag);
				autoPlayflag = null;
			}
			fn_autoPlay();

			return false;

		};

		function fn_autoPlay() {
			if (opts.auto) {
				autoPlayflag = setTimeout(function() {
					if (opts.auto) {
						fn_NextGo($teWrapper);
						if (animated) animated = false; //修复滑动门bug
					}
				},
				time);
			}
		}

		if (_webkitIframe) {
			iframeAutoPlay();
		} else {
			fn_autoPlay();
			init();
		}
		function init() {
			$teTransition.addClass(type);

			if (hasPerspective) {

				$teWrapper.on({
					'webkitAnimationStart': function(event) {},
					'webkitAnimationEnd': function(event) {

						if ((type === 'te-unfold1' && event.originalEvent.animationName !== 'unfold1_3Back') || (type === 'te-unfold2' && event.originalEvent.animationName !== 'unfold2_3Back')) return false;

						$teCover.removeClass('MD_sbHide');
						if ($.inArray(type, wPerspective) !== -1) $teWrapper.removeClass('MD_sbPerspective');
						$teTransition.removeClass('MD_sbShow');
						animated = false;
					}
				});

			}

		}

		function showNext(i) {

			if (hasPerspective) {
				$teTransition.addClass('MD_sbShow');
				$teCover.addClass('MD_sbHide');
				if ($.inArray(type, wPerspective) !== -1) {
					$teWrapper.addClass('MD_sbPerspective');
				}

			}

			updateImages(i);

		}
		function updateImages(i) {
			var $back = $teTransition.find('div.MD_sbBack'),
			$front = $teTransition.find('div.MD_sbFront');

			if (i == 1) { (currentImg === imagesCount - 1) ? (last_img = imagesCount - 1, currentImg = 0) : (last_img = currentImg, ++currentImg);
			} else if (i == 0) { (currentImg === 0) ? (last_img = 0, currentImg = imagesCount - 1) : (last_img = currentImg, --currentImg);
			}

			if ((type == 'other1' && i == 1) || (type == 'other2' && i == 0)) {
				$front.each(function() {
					$(this).find('img').eq(currentImg).show().siblings().hide();
				});
				$back.each(function() {
					$(this).find('img').eq(last_img).show().siblings().hide();
				});
			}

			else {
				$front.each(function() {
					$(this).find('img').eq(last_img).show().siblings().hide();
				});
				$back.each(function() {
					$(this).find('img').eq(currentImg).show().siblings().hide();
				});

			}

			$teCover.find('img').eq(currentImg).show().siblings().hide();

		}

	});
};;