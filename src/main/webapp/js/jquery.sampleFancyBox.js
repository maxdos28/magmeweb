/*
 * SimpleFancyBox - jQuery Plugin
 * author:edward
 * 
 */
;(function($){
	var wrap,outer,overlay,content,opt,timer,close;
	var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
	$.fancybox = {};
	$.fancybox.init = function() {
		if ($("#fancybox-wrap").length) {
			return;
		}

		$('body').append(
			overlay	= $('<div id="fancybox-overlay"></div>'),
			wrap = $('<div id="fancybox-wrap" class="pop"><a class="del" id="fancybox-close" title="关闭"></a></div>')
		);

//		outer = $('<div id="fancybox-outer"></div>')
//			.appendTo( wrap );
		
		content = $('<div id="fancybox-content"></div>').appendTo(wrap);
		close = $('<a id="fancybox-close"></a>').css('display','block').appendTo(outer);

		close.click($.fancybox.close);

		if (!$.support.opacity) {
			//wrap.addClass('fancybox-ie');
		}

		if (isIE6) {
			//wrap.addClass('fancybox-ie6');
		}
		//小窗口时移动滚动条bug
		$(window).scroll(function(){
			$('#fancybox-overlay').css({width: $(document).width()});
		});
	};
	
	$.fn.fancybox = function(options){
		if (!$(this).length) {
			return this;
		}
		// default configuration properties
		var defaults = {
			padding : 0,
			margin: 0,
			width : 560,
			height : 340,
			overlayShow : true,
			overlayOpacity : 0.7,
			overlayColor : '#000',
			
			hideOnOverlayClick:false,
			centerOnScroll:false,
			
			onStart : function(){
				if (isIE6) {
					$('select').css({ visibility: "hidden" });
				}
			},
			onCancel : function(){},
			onComplete : function(){},
			onCleanup : function(){},
			onClosed : function(){
				$('select').css({ visibility: "visible" });
			},
			onError : function(){}
		}; 
		
		opt = $.extend({},defaults,options);
		
		_get_viewport = function() {
			return [
				$(window).width() - (opt.margin * 2),
				$(window).height() - (opt.margin * 2),
				$(document).scrollLeft() + opt.margin,
				$(document).scrollTop() + opt.margin
			];
		};
		_get_obj_pos = function(obj) {
			var pos = obj.offset();

			pos.top += parseInt( obj.css('paddingTop'), 10 ) || 0;
			pos.left += parseInt( obj.css('paddingLeft'), 10 ) || 0;

			pos.top += parseInt( obj.css('border-top-width'), 10 ) || 0;
			pos.left += parseInt( obj.css('border-left-width'), 10 ) || 0;

			pos.width = obj.outerWidth(true);
			pos.height = obj.outerHeight(true);
			return pos;
		},
		
		
		_finish = function(){
			//close's button
			$("#fancybox-close").unbind('click').bind('click',$.fancybox.close);
			
			if(opt.hideOnOverlayClick){
				overlay.bind('click', $.fancybox.close);	
			}
			
			$(window).bind("resize.fb", $.fancybox.resize);
			if (opt.centerOnScroll) {
				$(window).bind("scroll.fb", $.fancybox.center);
			}
			
			//wrap.css('height', 'auto');
			wrap.show();
			$('#fancybox-content').find("select").css({ visibility: "visible" });
			$.fancybox.center(true);
		};
		
		_show = function() {
			var pos, borderWidth;
			if (wrap.is(":visible")) {
				return;
			}
			//onStart
			opt.onStart();
			
			$(content.add( overlay )).unbind();
			$(window).unbind("resize.fb scroll.fb");
			$(document).unbind('keydown.fb');
			
			//overlay's style
			if(opt.overlayShow){
				overlay.css({
					'background-color' : opt.overlayColor,
					'opacity' : opt.overlayOpacity,
					'width' : '100%',
					'height' : '100%'
				});
				if (!overlay.is(':visible')) {
					overlay.show();
				}
			}else{
				overlay.hide();
			}
			
			wrap.removeAttr("style");
			content.css('border-width', opt.padding);
			pos = _get_obj_pos($(this));
			content
				.css({
					'width' : pos.width,
					'height' : pos.height
				})
				.html( $(this).show() );
			//$(this).hide();
			var wrapWidth = pos.width + opt.padding*2,
				wrapHeight = pos.height + opt.padding*2;
			wrap.css({'width':wrapWidth,'height':wrapHeight});
			
			_finish();
		};
		
		_show.call($(this));
		
		//onComplete fuction
		opt.onComplete();
		
		return $(this);
	};
	
	$.fancybox.center = function(isFirst,windowCenter) {
		var view;
		var timer = 100;
		
		view = _get_viewport();
		
		//control the overlay's height
		if (overlay.is(':visible')) {
			overlay.css({'height': $(document).height(),'width':$(window).width()});
		}
		
		if ((wrap.width() > view[0] && wrap.height() > view[1])) {
			return;	
		}
		var left = parseInt(Math.max(view[2] , view[2] + ((view[0] - content.width() ) * 0.5) - opt.padding));
		var top = parseInt(Math.max(view[3] , view[3] + ((view[1] - content.height() ) * 0.5) - opt.padding));
		if(isFirst){
			wrap.css({'top':0,'left':left});
			timer = 300;
		}
		var warpStyle = {'top' : top,'left' : left};
//		if(windowCenter){
//			warpStyle = {'top' : top,'left' : left};
//		}
		wrap
			.stop()
			.animate(warpStyle,timer);
		
	};
	
	//close fancybox
	$.fancybox.close = function() {
		content.children().hide().appendTo($('body'));
		if (wrap.is(':hidden')) {
			return;
		}
		overlay.fadeOut(500);
		wrap.fadeOut(500,function(){
			opt.onClosed();
		});
	};
	$.fancybox.resize = function() {
		if(!timer){
			timer = setTimeout(function(){
				$.fancybox.center();
				timer = null;
			},300);
		}
		if($(".datepicker").length>0){
			$(".datepicker").hide();
		}
	};
	
	
	$(document).ready(function() {
		$.fancybox.init();
	});
})(jQuery);