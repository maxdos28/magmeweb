//安卓端在video上添加播放图标，使用安卓弹出层播放视频
$(function() {
	if (browser.versions.android&&!isweb) {
		androidVideo();
	}
	function androidVideo() {
		var dom = document,
		video = dom.getElementsByTagName('video'),
		_span = dom.createElement("span");

		_span.style.cssText = 'width:84px;height:84px;background:url(../../../pubassets/imgs/icon/video_android.png) no-repeat;position:absolute;left:50%;top:50%;margin-left:-42px;margin-top:-42px;background-size:84px 84px;}';

		while (video.length != 0) {
			var _this = video[0],
			_source = _this.getElementsByTagName('source')[0].getAttribute('src'),
			_parent = _this.parentNode,
			_src = _this.getAttribute('poster'),
			_width = _this.getAttribute('width'),
			_height = _this.getAttribute('height'),
			_img = dom.createElement("img"),
			_div = dom.createElement("div");

			_span.setAttribute('poster', _source);
			_div.appendChild(_span.cloneNode());
			_parent.insertBefore(_div, _this);
			_parent.removeChild(_this);

			var flag = $(_parent).height() == 0;

			if (_src == null || _src == '') {
				//video替换为image，无poster图时使用黑底色
				_div.style.cssText = "cursor:pointer;position:relative;height:100%;width:100%;background:#000;";
				if (flag) {
					_parent.style.width = '100%';
					_parent.style.height = $(window).width() * 0.6 + 'px';
					_parent.style.marginLeft = 0;
					_parent.style.marginRight = 0;
				}
			} else {
				if (!flag) {
					//video替换为image，使用poster图
					_div.style.cssText = "cursor:pointer;position:relative;height:100%;width:100%;background:url(" + _src + ") no-repeat center center #000;background-size:contain;";
				} else {
					_div.style.cssText = "cursor:pointer;position:relative;height:100%;width:100%;";
					_img.src = _src;
					_img.style.width = '100%';
					_div.appendChild(_img);
				}
			}

			//图标点击时，执行安卓
			_div.onclick = function(event) {
				event.stopPropagation();
				popVideo(this.getElementsByTagName('span')[0].getAttribute('poster'));
			}

		}
	}

	function popVideo(src) {
		//判断是否是安卓端，imageViewer为安卓内控件
		if (typeof(imageViewer) != "undefined") {
			imageViewer.showVideo(src);
			return false;
		}

	}

});