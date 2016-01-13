//彩蛋
function Addeggs(index) {
	this.count = 3; //箱子个数
	this.pin = 1; //抽奖概率	-1为永远不中   1为100%中奖   100%/1   50%/2  以此类推
	this.btnWidth = 50; //按钮宽度
	this.btnHeight = 50; //按钮高度
	this.boxSpace = 70; //箱子间距	
	this.boxWidth = 160; //箱子宽度
	this.boxHeight = 160; //箱子高度
	this.eggNum = index; //蛋图片样子		this.returnRandom(50)+1	
	this.slogan = {
		start: '笨蛋还不快点蛋啊！',
		no: '可惜你没中！',
		yes: '恭喜猜中了！'
	}

	this.init(); //初始化
}

Addeggs.prototype = {

	init: function() {
		var This = this;
		var aOrig = ['MD_Slidedoor', 'Md_InnerscrollBar', 'MD_Textcolumn', 'textArea'];
		var $aItem = $('body div');
		var $audio = $("<audio></audio>"),
		audioSrc = ['../../../pubassets/imgs/egg/find.mp3', '../../../pubassets/imgs/egg/no.mp3', '../../../pubassets/imgs/egg/yes.mp3'];

		if ($('#easingScript').length == 0) {
			var script = document.createElement('script');
			script.src = "../../../pubassets/js/jquery.easing.1.3.min.js";
			script.setAttribute('id', 'easingScript');
			document.head.appendChild(script);
		}

		This.isPhone = $('body').hasClass('phone');

		if (This.isPhone) {
			this.btnWidth = 25;
			this.btnHeight = 25;
			this.boxSpace = 20;
			this.boxWidth = 80;
			this.boxHeight = 80;
		}

		var $htmlWidth = $('html').width(),
		$htmlHeight = $('html').height(),
		$bodyWidth = $('body').width();

		var $ScrollY = $('#bodyScrollY').length == 0;

		if ($ScrollY) {

			if ($('body').hasClass('phone')) {
				var $bodyHeight = $('body>div:last').offset().top + $('body>div:last').height();
			} else {
				var $bodyHeight = $(document).height();
			}
		} else {
			var $bodyHeight = $('#bodyScrollY').height();
		}

		This.overX = $htmlWidth < $bodyWidth;
		This.overY = $htmlHeight < $bodyHeight;

		This.btn = document.createElement('div');
		This.basketWidth = This.count * (This.boxWidth + This.boxSpace) + This.boxSpace;
		This.aControl = [];

		$aItem.each(function(i) {
			for (var value in aOrig) {
				if ($(this).hasClass(aOrig[value])) {
					This.aControl.push($(this).attr('id') + '|' + aOrig[value]);
				}
			}
		});

		for (var i = 0; i < audioSrc.length; i++) {
			var newAudio = $audio.clone();
			newAudio.attr('id', 'chatAudio' + i);
			newAudio.attr('src', audioSrc[i]);
			newAudio.appendTo('body');
		}

		if (This.aControl.length == 0) {

			if (This.overX) {
				var saleX = Math.floor(($bodyWidth - $htmlWidth) / $htmlWidth);
				var startX = $htmlWidth * saleX;
				var endX = startX + $htmlWidth;

				var XY = This.createArea(endX, $bodyHeight, startX, 0).split('|');
			} else if (This.overY) {
				var saleY = Math.ceil(($bodyHeight - $htmlHeight) / $htmlHeight);
				var startY = $htmlHeight * saleY;
				var endY = startY + $htmlHeight;

				var XY = This.createArea($bodyWidth, endY, 0, startY).split('|');

			} else {

				var XY = This.createArea($bodyWidth, $bodyHeight).split('|');
			}
			This.createBtn(XY[0], XY[1]);

			if ($ScrollY) {

				document.body.appendChild(This.btn);
			} else {

				$('#bodyScrollY')[0].appendChild(This.btn);
			}

		} else {
			var _lucky = This.aControl[This.returnRandom(This.aControl.length)].split('|');
			This.createBtn();
			if (_lucky[1] != 'MD_Slidedoor') {
				$('#' + _lucky[0]).find('>div')[0].appendChild(This.btn);

				This.btn.style.left = This.btn.parentNode.scrollWidth - This.btnWidth + 'px';
				This.btn.style.top = This.btn.parentNode.scrollHeight - This.btnHeight + 'px';
				This.btn.style.display = 'block';

			} else {
				var $pbody = $('#' + _lucky[0] + '>div.MD_body');
				var $cItem = $pbody.eq(This.returnRandom($pbody.length));
				$cItem[0].appendChild(This.btn);
				var XY = This.createArea($cItem.innerWidth(), $cItem.innerHeight()).split('|');
				This.btn.style.left = XY[0] + 'px';
				This.btn.style.top = XY[1] + 'px';
				This.btn.style.display = 'block';
			}
		}
	},
	createArea: function(maxW, maxH, minW, minH) {
		var maxWidth = maxW - this.btnWidth,
		maxHeight = maxH - this.btnHeight;
		if (arguments.length > 2) {
			var btnX = this.returnRandom(maxW, minW);
			var btnY = this.returnRandom(maxH, minH);
			btnX > minW ? btnX: btnX = minW;
			btnX < maxWidth ? btnX: btnX = maxWidth;
			btnY > minH ? btnY: btnY = minH;
			btnY < maxHeight ? btnY: btnY = maxHeight;
		} else {
			var btnX = this.returnRandom(maxW);
			var btnY = this.returnRandom(maxH);
			btnX > 0 ? btnX: btnX = 0;
			btnX < maxWidth ? btnX: btnX = maxWidth;
			btnY > 0 ? btnY: btnY = 0;
			btnY < maxHeight ? btnY: btnY = maxHeight;
		}
		return btnX + '|' + btnY;
	},
	createBtn: function(_x, _y) {
		var This = this;
		This.btn.style.cssText = 'position:absolute;cursor:pointer;background:url(../../../pubassets/imgs/egg/small_btn.png) no-repeat;z-index:199999;background-size:' + this.btnWidth + 'px ' + this.btnHeight + 'px';
		This.btn.style.width = This.btnWidth + 'px';
		This.btn.style.height = This.btnHeight + 'px';

		if (arguments.length == 2) {
			This.btn.style.left = _x + 'px';
			This.btn.style.top = _y + 'px';
		} else {
			This.btn.style.display = 'none';
		}
		$(This.btn).bind('tap',
		function(event) {
			var oEvent = event || window.event;
			oEvent.stopPropagation();

			oEvent.stopPropagation();
			oEvent.preventDefault();

			This.createBasket();
			This.createMask();
			$('#chatAudio0')[0].play();

			if (This.overX) {
				$('body').animate({
					scrollLeft: 0
				},
				400);
			} else if (This.overY) {
				$('body').animate({
					scrollTop: 0
				},
				400);
			}
			this.onclick = null;

		});
	},
	createBasket: function() {
		var This = this;
		var basket = document.createElement('div');
		var timer = null;
		basket.style.cssText = 'position:absolute;background:none;z-index:200000;display:none;cursor:pointer;}';
		basket.style.left = $(window).width() / 2 + 'px';
		basket.style.top = $(window).height() / 2 + 'px';

		basket.style.width = This.basketWidth + 'px';
		basket.style.height = This.boxHeight + 'px';
		basket.style.marginLeft = -This.basketWidth / 2 + 'px';
		basket.style.marginTop = -This.boxHeight / 2 + 'px';
		basket.setAttribute('id', 'basketBlock');
		document.body.appendChild(basket);
		$('#basketBlock').show();

		timer = setInterval(function() {
			if (document.getElementById('basketBlock')) {
				clearInterval(timer);
				timer = null;
			}
			This.createBox(document.getElementById('basketBlock'));
		},
		50);
	},
	createBox: function(d) {
		var dom = d;
		var This = this;
		if (dom) {
			var $sBox = $('<div class="boxes"></div>');
			var $sBox_behind = $('<div class="boxes_behind"></div>');
			var $sBox_front = $('<div class="boxes_front animaBox"><img/></div>');
			var eggPosition = This.returnRandom(This.pin);
			var $boxHtml = $('<div></div>');
			var clickFlag = true;

			$boxHtml.css({
				display: 'none',
				top: 0,
				position: 'absolute',
				width: $(dom).width(),
				height: $(dom).height()
			});

			$sBox_behind.css({
				width: This.boxWidth,
				height: This.boxHeight,
				position: 'absolute',
				left: 0,
				top: 0,
				zIndex: 1,
				backgroundImage: 'url(../../../pubassets/imgs/egg/ring.png)',
				backgroundPosition: '0 0',
				backgroundRepeat: 'no-repeat',
				backgroundSize: this.boxWidth + 'px ' + this.boxHeight + 'px'
			});
			$sBox_front.css({
				width: This.boxWidth,
				height: This.boxHeight,
				position: 'absolute',
				left: 0,
				top: 0,
				zIndex: 4
			});
			$sBox_front.find('img').attr({
				width: '100%',
				height: '100%',
				src: "../../../pubassets/imgs/egg/what.png"
			});
			$sBox.css({
				width: This.boxWidth,
				height: This.boxHeight,
				marginLeft: This.boxSpace,
				background: 'none',
				float: 'left',
				position: 'relative'
			});
			$sBox.data('good', 0);
			$sBox.bind('tap',
			function(event) {
				var oEvent = event || window.event;
				oEvent.stopPropagation();
				if (clickFlag) {

					var _index = $(this).index();
					var $box = $(dom).find('div.boxes');
					var $aniBox = $(dom).find('div.animaBox');

					$aniBox.animate({
						width: This.boxWidth * 3,
						height: This.boxHeight * 3,
						opacity: 0,
						left: '-100%',
						top: '-100%'
					},
					500,
					function() {
						$aniBox.remove();
					});

					if ($(this).data('good') == eggPosition) {
						$('#chatAudio2')[0].play();

						var X = This.isPhone ? $(window).width() - $(this).offset().left - 50 : $(window).width() - $(this).offset().left - 100; //- 80+80/3;
						var Y = This.isPhone ? $(this).offset().top + 53 / 3 : $(this).offset().top + 106 / 3;

						$box.eq(_index).append(This.createEgg().egg);
						$box.eq(_index).append(This.createEgg().dock);
						$('#eggSlogan').find('p').text(This.slogan.yes).append('<br/><a id="getEggEvent" href="javascript:void(0);">收集彩蛋</a>');

						$('#getEggEvent').one('tap',
						function(event) {
							var oEvent = event || window.event;
							oEvent.stopPropagation();
							This.fnReturnEgg(This.eggNum);
							$('#goodLucky,#eggDock').animate({
								left: X,
								top: -Y,
								width: 26,
								height: 36,
								opacity: 0
							},
							1200, 'easeInBack');
							setTimeout(function() {
								removeEggs(2000);
							},
							700);

						});

					} else {
						$('#chatAudio1')[0].play();
						var aO = [];
						for (var i = 0; i < This.count; i++) {
							aO.push(i);
						}
						aO.splice(_index, 1);
						$box.eq(aO[This.returnRandom(aO.length)]).append(This.createEgg().egg).append(This.createEgg().dock);
						$('#eggSlogan').find('p').text(This.slogan.no);

						setTimeout(function() {
							removeEggs(500);
						},
						2500);
					}
					$('#goodLucky,#eggDock').animate({
						opacity: 1
					},
					1000);

					clickFlag = false;
				}
			});

			$sBox.append($sBox_behind.clone(true));
			$sBox.append($sBox_front.clone(true));

			for (var i = 0; i < This.count; i++) {
				$boxHtml.append($sBox.clone(true));
			}

			$(dom).append($boxHtml);
			This.createSlogan(dom);
			$boxHtml.fadeIn(600);

			function removeEggs(timer) {
				$(dom).fadeOut(timer,
				function() {
					$(this).remove();
				});
				$(This.btn).fadeOut(timer,
				function() {
					$(this).remove();
				});
				$("audio[id^='chatAudio']").remove();
				$('#eggMask').fadeOut(timer,
				function() {
					$(this).remove();
				});
			}

		}
	},
	createEgg: function() {
		var This = this;
		var $egg = $('<div><img /></div>');
		var $dock = $('<div></div>');
		if (This.isPhone) {
			$egg.css({
				width: 42,
				height: 54,
				position: 'absolute',
				left: 20,
				top: 13,
				zIndex: 6,
				opacity: 0
			});
		} else {
			$egg.css({
				width: 82,
				height: 106,
				position: 'absolute',
				left: 41,
				top: 25,
				zIndex: 6,
				opacity: 0
			});
		}

		$egg.find('img').attr({
			width: '100%',
			height: '100%',
			src: "../../../pubassets/imgs/egg/egg" + This.eggNum + ".png"
		});
		$egg.attr('id', 'goodLucky');
		if (This.isPhone) {
			$dock.css({
				background: "url(../../../pubassets/imgs/egg/dock.png)",
				backgroundSize: '40px 15px',
				backgroundRepeat: 'no-repeat',
				backgroundPosition: "0 0",
				width: 40,
				height: 15,
				position: 'absolute',
				left: 23,
				top: 60,
				zIndex: 5,
				opacity: 1
			});
		} else {
			$dock.css({
				background: "url(../../../pubassets/imgs/egg/dock.png)",
				backgroundSize: '80px 30px',
				backgroundRepeat: 'no-repeat',
				backgroundPosition: "0 0",
				width: 80,
				height: 30,
				position: 'absolute',
				left: 43,
				top: 121,
				zIndex: 5,
				opacity: 1
			});
		}

		$dock.attr('id', 'eggDock');
		return {
			egg: $egg,
			dock: $dock
		};
	},
	createMask: function() {
		var $mask = $('<div></div>');
		var $body = $('body');
		$mask.attr('id', 'eggMask').css({
			width: $body.width(),
			height: $(document).height(),
			position: "absolute",
			zIndex: 199999,
			left: 0,
			top: 0,
			background: '#fff',
			opacity: 0.95
		});
		/*		$mask[0].addEventListener('touchmove',function(ev){
			var oEvent = ev || window.event;
			oEvent.preventDefault();
		},false);*/
		$body.append($mask);
	},
	createSlogan: function(dom) {
		var $slogan = $('<div><p></p></div>');
		$slogan.attr('id', 'eggSlogan').css({
			display: 'none',
			width: $(dom).width(),
			height: $(dom).height(),
			fontSize: '18px',
			textShadow: "0px 0px 1px rgba(0,0,0,0.5)",
			left: 0,
			top: $(dom).height(),
			position: 'absolute'
		});
		$slogan.find('p').css({
			textAlign: 'center',
			paddingTop: 50,
			lineHeight: 2
		});
		$slogan.find('p').text(this.slogan.start);
		$(dom).append($slogan);
		$slogan.fadeIn(600);
	},
	boxAnimate: function(fnParent, dom, iNow) {
		var This = fnParent;
		var $this = dom;
		iNow++;
		if ($this.hasClass('animaBox')) {
			$this.removeClass('animaBox')
		};
		setTimeout(function() {
			if (iNow > 16) {
				return;
			} //连续动画   len -1
			$this.css({
				backgroundPosition: ( - iNow * This.boxWidth) + "px 0"
			});
			This.boxAnimate(This, $this, iNow);
		},
		iNow * 2);

	},
	returnRandom: function(max, min) {
		if (arguments.length == 1) {
			return Math.floor(Math.random() * max);
		} else {
			return Math.floor(min + Math.random() * (max - min));
		}
	},
	fnReturnEgg: function(index) {
		if (typeof(androidObj) != "undefined") {
			window.androidObj.collectAnEgg(index);
		} else {
			window.open("iosegg:" + index);
			//alert(index);
		}
	}
}