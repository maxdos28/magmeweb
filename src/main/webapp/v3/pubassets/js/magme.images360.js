//360旋转控件
$.fn.images360 = function(options) {
	var setting = {
		page: 49,
		url: null,
		tp: 'dire'
	};
	var opts = $.extend({},
	setting, options);

	return $(this).each(function() {
		if (opts.url == null) {
			return false;
		}
		var $this = $(this),
		_flag = false,
		_save,
		_i = 1,
		_page = opts.page,
		_current = 1,
		_target,
		_url = opts.url,
		_type = opts.tp,
		_width = $this.find('img').width(),
		_height = $this.find('img').height(),
		_frag = document.createDocumentFragment();

		var $imgs = $this.find('img');

		//桌面端鼠标操作
		$this.bind('mousedown', MD_down(this.event, 0));
		$(document).bind('mousemove', MD_move(this.event, 0));
		$(document).bind('mouseup', MD_up(this.event, 0));

		//移动端拖动操作
		$this[0].addEventListener("touchstart", MD_down(this.event, 1), false);

		function MD_down(event, tp) {
			return function(event) {
				event.stopPropagation();
				event.preventDefault();
				_flag = true;
				if (tp == 0) {
					_save = event.pageX;
				} else {
					_save = event.touches[0].pageX;
				}
				document.addEventListener("touchmove", MD_move(this.event, 1), false);
				document.addEventListener("touchend", MD_up(this.event, 1), false);
			}
		}
		function MD_move(event, tp) {
			return function(event) {
				event.stopPropagation();	
				if (_flag) {
					if (tp == 0) {
						event.preventDefault();
						var _moveX = event.pageX;
					} else {
						var _moveX = event.touches[0].pageX;
					}
					//左右拖动判断，图片递增或递减
					if (_type == 'dire') {
						_target = -parseInt((_moveX - _save) / 25) + _current;
					} else {
						_target = parseInt((_moveX - _save) / 25) + _current;
					}
					//循环拖动
					if (_target > _page) {
						_target = _target - _page;
					} else if (_target < 1) {
						_target = _page + _target;
					}
					fnImagesShow(_target);
				}
			}
		}

		function MD_up(event, tp) {
			return function(event) {

				if (_flag) {
					_flag = false;
					_current = _target;
					if (tp == 0) {
						event.preventDefault();
						_save = event.pageX;
					} else {
						_save = event.touches[0].pageX;
					}
				}

			}
		}

		function fnImagesShow(n) {
			var _imgs = $imgs.eq(n - 1);
			if (_imgs.is(":hidden")) {
				$imgs.hide();
				$imgs.eq(n - 1).show();
			}
		}

		function fnImagesLoad(src) {
			var _img = new Image();
			_img.src = src;
			_img.style.display = 'none';
			_img.width = _width;
			_img.height = _height;
			return _img;
		}

	});
};