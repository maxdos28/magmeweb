//相册展示
function fnGallery() {
	//桌面版
	if (magmeiframeFlag && !/online=0/.test(document.location.search)) {
		var imgsAry = [];
		var current = arguments[0];
		for (var i = 1; i < arguments.length; i++) {
			imgsAry.push(arguments[i]);
		}
		var imgLen = imgsAry.length;
		var $topBody = $(window.top.document.body);

		var location = window.self.location.href.substring(0, window.self.location.href.lastIndexOf('/') + 1) + "photos/";
		var $iframe = $("<div id='blockGallery'><style>#blockGallery{z-index:1000;width:100%;height:100%;position:fixed;left:0;top:0;padding-bottom:55px;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;box-sizing:border-box;-webkit-user-select:none;-moz-user-select:none;-user-select:none;}#barGalleryBlock{position:absolute;height:55px;width:100%;position:absoutle;bottom:0;left:0;z-index:10;padding-top:25px;}#barGallery{overflow:hidden;height:125px;margin:0 auto;position:relative;}#barGallery div.img{display:inline-block;width:40px;height:40px;margin:0 2px;border:2px solid transparent;cursor:pointer;}#barList{left:0;position:absolute;height:104px;white-space:nowrap;top:6px;}#barGallery div.img.on{border:2px solid red;}#conGalleryBlock{width:100%;height:100%;position:relative;z-index:11;overflow:hidden;}#conGalleryBlock td{vertical-align:middle;}#conGalleryBlock table{width:100%;height:100%;}#conGallery{margin: 0 auto;}#maskGallery{width:100%;height:100%;background:#000;opacity:0.9;position:absolute;left:0;top:0;}#btnLeftGallery,#btnRightGallery{position:fixed;top:50%;margin-top:-64px;width:64px;height:128px;cursor:pointer;z-index:20;background:url(/appprofile/pubassets/imgs/icon/turnBtn.png) no-repeat;}#btnLeftGallery{left:20px;background-position:0 0;}#btnRightGallery{right:20px;background-position:-64px 0;}a.iswebcolse{position:absolute;right:10px;top:10px;display:block;width:25px;height:25px;background:url(http://static.magme.com/appprofile/read/img/iconClose.png) no-repeat;background-size:100% 100%;z-index:98}a.iswebcolsemask{position:absolute;right:10px;top:10px;display:block;width:25px;height:25px;z-index:99;}#conGalleryBlockbox{width:100000px;height:100%;}.conGalleryBlock{float:left;height:100%;}.conGallery{margin:0 auto;}</style></div>");
		var $bar = $("<div id='barGalleryBlock'><div id='barGallery'><div id='barList'></div></div>");
		var $cont = $("<div class='swiper-container' id='conGalleryBlock'><div class='swiper-wrapper' id='conGalleryBlockbox'></div></div>");
		var $load = $("<div class='loading-parent'><div class='loading'></div></div>");
		var $mask = $("<div id='maskGallery'></div>");
		var $btnL = $("<span id='btnLeftGallery'></span>");
		var $btnR = $("<span id='btnRightGallery'></span>");
		var $colse = $('<a href="javascript:;" class="iswebcolse"></a>');
		var $colsemask = $('<a href="javascript:;" class="iswebcolsemask"></a>');		
		var $imgs = $('<div class="img"></div>');
		var sImgW = 40 + 4 + 4; //缩略图宽 width + margin + border
		var $contlist = $("<div class='swiper-slide conGalleryBlock'><table><tr><td><img class='conGallery' style='display:none;' /></td></tr></table></div>");
		var area, half;
		var _move = 0;
		var loadimg = 0
		function imgsLoad(loadimg) {
			var src = location + imgsAry[loadimg]
			var mImg = new Image();
			mImg.onload = mImg.onerror = function() {
				mImg.onload = mImg.onerror = null;
				var _width = mImg.width;
				var _height = mImg.height;
				var $appclone = $contlist.clone();
				if (_width > $(parent).width() || _height > $(parent).height()) {
					$appclone.css({
						"background-image": "url(" + src + ")",
						"background-size": 'contain',
						"background-repeat": 'no-repeat',
						"background-position": 'center center'
					});
					$appclone.find('.conGallery').hide();
				} else {
					$appclone.css({
						'background': 'none'
					});
					$appclone.find('.conGallery').attr({
						'src': src,
						width: _width,
						height: _height
					}).css({
						'display': 'block'
					});
				};
				$appclone.attr({
					'w' : _width,
					'h' : _height,
					's' : src
				})				
				$cont.find('#conGalleryBlockbox').append($appclone);
				loadimg++;
				if(loadimg<imgLen){
					imgsLoad(loadimg);
				}else{
					window.parent._move = barMove;
					parent.me.readerGalleryon(current);
					$iframe.find('.loading-parent').remove();
				}
			}
			mImg.src = src;
		}
		imgsLoad(loadimg);
		for (var i = 0; i < imgLen; i++) {
			var _src = imgsAry[i].replace(/_A\.jpg/g, '_E.jpg');
			var _img = $imgs.clone();

			_img.css({
				"background-image": "url(" + location + _src + ")",
				"background-size": 'contain',
				"background-repeat": 'no-repeat',
				"background-position": 'center center'
			});
			$bar.find('#barList').append(_img);
		}

		function resetbg(){
			$topBody.find('.conGalleryBlock').each(function(){
				var $this = $(this);
				var $ww = Number($this.attr('w'));
				var $wh = Number($this.attr('h'));
				var $ws = $this.attr('s');
				if ($ww > $cont.width() || $wh > $cont.height()) {
					$this.css({
						"background-image": "url(" + $ws + ")",
						"background-size": 'contain',
						"background-repeat": 'no-repeat',
						"background-position": 'center center'
					});
					$this.find('.conGallery').hide();					
				}else{
					$this.css({
						'background': 'none'
					});
					$this.find('.conGallery').attr({
						'src': $ws,
						width: $ww,
						height: $wh
					}).css({
						'display': 'block'
					});
				}
			})
		}

		function getAreaLen() {
			var $topWidth = $topBody.width();
			var _area = Math.floor($topWidth / sImgW) % 2 != 0 ? Math.floor($topWidth / sImgW) : Math.floor($topWidth / sImgW) - 1;
			var _half = Math.floor(_area / 2);
			return {
				area: _area,
				half: _half
			}
		}
		function imgResize() {
			var $w = $topBody.width(); 
      	    if($topBody.find('#imgResizestylecss').length) $topBody.find('#imgResizestylecss').remove();
     	    var styletext = '<div id="imgResizestylecss"><style>';
                styletext += '.conGalleryBlock{width:'+$w+'px;}';
            	styletext += '</style></div>';
       		$topBody.append(styletext);			
			if (!$cont.is(":animated")) {
				area = getAreaLen().area;
				half = getAreaLen().half;
				if (imgLen < area) {
					$bar.find('#barGallery').hide().css({
						width: imgLen * sImgW
					}).fadeIn();
				} else {
					$bar.find('#barGallery').hide().css({
						width: area * sImgW
					}).fadeIn();
				}
				$bar.find('#barList').css({
					width: imgLen * sImgW
				});
				barMove(current);
			}
		}

		window.top.onresize = function() {
			imgResize();
			resetbg();
		}

	    function resizeleft(n,t){
	        var time = t?t:0;
	        var $z = 'translate(-'+parseInt(n*$topBody.width())+'px, 0px)';
	        $cont.find('#conGalleryBlockbox').css({'transition-duration': time+'ms','transform':$z});
	        $cont.find('#conGalleryBlockbox').css({'-webkit-transition-duration': time+'ms','-webkit-transform':$z});
	    };   
		function barMove(cur) {
			cur = parseInt(cur);
			if (cur - half > -1) {
				if (cur + half >= imgLen) {
					_move = imgLen - area;
				} else {
					_move = cur - half;

				}
			} else {
				_move = 0;
			}

			$bar.find('#barList').animate({
				marginLeft: '-' + sImgW * _move
			});
			currentIMGS(cur);
		}

		function currentIMGS(cur) {
			current = cur;
			$bar.find('div.img').eq(current).addClass('on').siblings().removeClass('on');
			if(parent.mySwiper){
				parent.mySwiper.swipeTo(current, 500)
			}
		}

		imgResize();
		$cont.append($colse).append($colsemask);
		$iframe.append($cont).append($bar).append($mask).append($btnL).append($btnR).append($load);
		$topBody.append($iframe);
		$bar.find('div.img').click(function(event) {
			event.stopPropagation();
			if (!$(this).hasClass('on')) {
				if (!$cont.is(":animated")) {
					barMove($(this).index());
				}
			}
		});
		$btnL.click(function(event) {
			event.stopPropagation();
			if (!$cont.is(":animated")) {
				current--;
				if (current < 0) {
					current = imgLen - 1;
				}
				barMove(current);
			}
		});
		$btnR.click(function(event) {
			event.stopPropagation();
			if (!$cont.is(":animated")) {
				current++;
				if (current > imgLen - 1) {
					current = 0;
				}
				barMove(current);
			}
		});

		$colse.click(function() {
			if ($(this).is(':visible')) {
				$iframe.remove();
			}
		});				
		if(isweb&&browser.versions.mobile && (browser.versions.android || browser.versions.ios)){		
			$btnL.hide();	
			$btnR.hide();	
		};	
		//解决tap点透与new Swiper第一次进来无法滑动
		setTimeout(function(){				
			$colsemask.hide();
		},400);
		//阅读器版
	} else {
		if (typeof(imageViewer) != "undefined") {
			imageViewer.show(arguments);
			return false;
		} else {
			var str = "";
			for (i = 0; i < arguments.length; i++) {
				if (i < arguments.length - 1) {
					str += arguments[i] + ","
				} else {
					str += arguments[i]
				}
			}
			if (!/online=0/.test(document.location.search)) {
				window.open("ios:" + str);
			}

		}
	}
}