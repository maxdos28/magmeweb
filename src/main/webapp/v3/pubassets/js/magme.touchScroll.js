//内部滚动翻页处理，安卓和IOS都用这份
function deviceTtouch(ev, ios, andr) {
	var addBorder = $('html').width() < $('body').width();//横向跨页
	var addScrollTop = $('html').height() < $('body').height();//纵向跨页

	return function() {
		var oEvent = ev || event;
		var This = this;

		var Child = This.children[0];
		var _startx = oEvent.touches[0].pageX;
		var _starty = oEvent.touches[0].pageY;
		var flag = true;
		var maxX, maxY;

		var textX = $(This).hasClass('MD_Textcolumn_icon');
		var innerX = $(This).hasClass('MD_overflowX');
		var textY = $(This).hasClass('textArea');
		var innerY = $(This).hasClass('MD_overflowY');
		var slideBanner = $(This).hasClass('MD_slideBanner');

		if (textX) { //文字横向滚动
			maxX = Child.scrollWidth - This.offsetWidth;
		} else if (innerX) { //内部横向滚动
			maxX = Child.offsetWidth - This.offsetWidth
		} else if (textY) { //文字纵向滚动
			maxY = Child.scrollHeight - This.offsetHeight;
		} else if (innerY) { //内部纵向滚动
			maxY = Child.offsetHeight - This.offsetHeight;
		}

		if (andr) {
			var type = (textX || innerX) ? 'overflowX': (textY || innerY) ? 'overflowY': slideBanner ? 'slideBanner': '';
			var val = '';
			switch (type) {
			case 'overflowX':
				var _left = This.scrollLeft;
				val = _left == 0 ? -1 : _left < maxX ? 0 : 1;
				break;
			case 'overflowY':
				var _top = This.scrollTop;
				val = _top == 0 ? -1 : _top < maxY ? 0 : 1;
				break;
			case 'slideBanner':
				val = 0;
				break;
			default:
				return false;
			}

			var isHorizontal = type == "overflowY" ? 0 : 1;

			if (typeof(androidObj) != "undefined") {
				window.androidObj.touchScroll(isHorizontal, val);
			}
		}

		else if (ios) {

			if (!addBorder && (innerX || textX)) { //ios处理 横向滚动
				$('body').css({
					borderRight: '1px solid #000'
				});
			}
			if (!addScrollTop && (innerY || textY)) { //ios处理 纵向滚动
				$('body').css({
					borderBottom: '1px solid #000'
				});
			}

			if (maxX > 0) { //横向滚动
				This.ontouchmove = function(ev) {
					var oEvent = ev || event;

					var _movex = oEvent.touches[0].pageX;
					var _movey = oEvent.touches[0].pageY;

					var _fly = Math.abs(_startx - _movex) > Math.abs(_starty - _movey);

					if (maxX == This.scrollLeft && _fly) {
						if (_startx - _movex > 0) {
							flag = false;
							if (navigator.userAgent.indexOf('OS 5_') != -1) {
								window.open("iosflip:" + 1);
							}
						}

					} else if (This.scrollLeft == 0 && _fly) {
						if (_startx - _movex < 0) {
							flag = false;
							if (navigator.userAgent.indexOf('OS 5_') != -1) {
								window.open("iosflip:" + 0);
							}
						}
					}

					if (!addBorder && (innerX || textX)) { //ios处理 横向滚动
						if (_startx - _movex > 0) {
							if (!addScrollTop && !flag) {
								$('body').scrollLeft(1);
							}else{
								oEvent.stopPropagation();
							};
						} else {
							if (!addScrollTop && !flag) {
								$('body').scrollLeft(0);
							}else{
								oEvent.stopPropagation();
							}
						}
					}else{
						oEvent.stopPropagation();
					}

				}
			} else if (maxY > 0) {
				This.ontouchmove = function(ev) {
					var oEvent = ev || event;

					var _movex = oEvent.touches[0].pageX;
					var _movey = oEvent.touches[0].pageY;

					var _fly = Math.abs(_starty - _movey) > Math.abs(_startx - _movex);

					if (This.scrollTop == 0 && _fly) {
						if (_starty - _movey < 0) {
							flag = false;
							//window.open("iosclose:");
						}
					} else if (maxY == This.scrollTop && _fly) {
						if (_startx - _movex > 0) {
							flag = false;
						}
					}

					if (!addScrollTop && (innerY || textY)) { //ios处理 纵向滚动
						if (_starty - _movey > 0) {
							//从下到上
							if (!flag) {
								$('body').scrollTop(1);

							}
						} else { //从上到下
							if (!flag) {
								$('body').scrollTop(0);
							}
						}
					}

				}
			}
		}
	}
}