//Light-box version 1.02  by lewis
;(function($){
	var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
	jQuery.lightBox = function(msg,onClose,width,align,isTips) {
		width = width || 240;
		align = align || 'center';
		_init = function(){
			var lightBoxHtml = '<div id="lightbox" class="lightbox">'
				+'<div class="popBody"><p name="message"></p><a id="sure" href="javascript:void(0)" class="btnBS">确定</a></div>'
				+'<a id="lightbox_close" class="popClose png"></a></div>';
			$('body').append(lightBoxHtml);
		};
		_init();
		var forId = "#lightbox";
		var $lightbox = $(forId);
	    var bodyHeight;
	    var browserHeight;
	    var maskHeight;
	    var addTop;
	    var offsetY = 16;
	    var top;
	    var broVer = $.browser.version;
	    jQuery.lightBox.close = closes;
		function resize() {
			if ($('.eaMask').is(":visible")) {
				bodyHeight = $(document).height();
				browserHeight = $(window).height();
				if (browserHeight > bodyHeight) {
					maskHeight = browserHeight;
				} else {
					maskHeight = bodyHeight;
				};
				$('.eaMask').css({ height: maskHeight ,width:$(window).width()});
				if (!isIE6) {
					if($lightbox.hasClass("lightboxTips")){
						$lightbox.css({ top: 0 });
					}else{
						$lightbox.css({ top: browserHeight / 2 - offsetY + addTop });
					}
				}
				else {
					top = $(window).scrollTop() + browserHeight / 2 - offsetY;
					$lightbox.css({ top: top });
				}
			}
		}
	

		if ($lightbox.data("isOpen") || $lightbox.data("isOpen") == null) {
			open();
		}
	
	
		function open(){
			addTop = $(document).scrollTop();
			$lightbox.data("isOpen", false);
			
			
			
			$('body').css({ position: 'relative', 'z-index': '888'});
			$lightbox.find("p[name='message']").html(msg+"");
			if(!!align){
				$lightbox.find("p[name='message']").css("text-align",align);
			}
			$('body').append("<div class='eaMask'></div>");
			$('.eaMask').unbind('click').click(jQuery.lightBox.close);
			bodyHeight = $(document).height();
			browserHeight = $(window).height();
			if (browserHeight > bodyHeight) {
				maskHeight = browserHeight;
			} else {
				maskHeight = bodyHeight;
			}
			//isTips------
			if (!!isTips) {
				$lightbox.addClass("lightboxTips");
				width = null;
			}
			if (isIE6 || !!isTips) {
				$lightbox.css({ position: 'absolute' });
			} else {
				addTop = 0;
				$lightbox.css({ position: 'fixed' });
			}
			if(!!width){
				$lightbox.width(width);
			}
			$lightbox.css({
				top: browserHeight / 2 - offsetY + addTop,
				left: '50%',
				zIndex: 10000,
				marginLeft: -$lightbox.width() / 2,
				marginTop: -$lightbox.height() / 2
			});
			//isTips------
			if(!!isTips){
				$lightbox.css({top:0,marginTop:0});
			}
			$('.eaMask').css({
				background: '#000000',
				position: 'absolute',
				cursor: 'wait',
				left: '0',
				top: '0',
				filter: 'alpha(opacity=40)',
				'-moz-opacity': 0.4,
				opacity: 0.4,
				zIndex: 9999,
				height: maskHeight,
				display: 'none',
				width: $(window).width()
			});
			$(window).bind("resize", resize);
			//小窗口时移动滚动条bug
			$(window).scroll(function(){
				if($(window).width() > 980){
					$('.eaMask').css({width: $(window).width()});
				}else{
					$('.eaMask').css({width: $(document).width()});
				}
			});
			$('.eaMask').fadeIn(200);
			$lightbox.fadeIn(200);
			if (broVer == 6.0 && !$.browser.mozilla) {
				$('select').css({ visibility: "hidden" });
				$('.lightbox select').css({ visibility: "visible" });
				$(window).bind("scroll", scrollHandle);
			};
		}
	
		$(".popClose").unbind('click').click(closes);
		$("#lightbox #sure").unbind('click').click(closes);

		function scrollHandle() {
			top = jQuery(window).scrollTop() + browserHeight / 2 - offsetY;
			$lightbox.css({ top: top });
		}
		function closes() {
			$('.eaMask').fadeOut(200);
//			$lightbox.fadeOut(200);
			$('#lightbox').remove();
			setTimeout(function(){
				$('.eaMask').remove();
				$('select').css({visibility:'visible'});
			}, 200);
			$lightbox.data("isOpen", true);
			if(onClose && typeof onClose === "function"){
				setTimeout(function (){onClose();},250);
			}
			//$('html').css({height: "auto", overflow:"auto"});//tips时去除滚动条
		}
//		function resizeHandle() {
//			bodyHeight = $(document).height();
//			browserHeight = $(window).height();
//			if (browserHeight > bodyHeight) {
//				maskHeight = browserHeight;
//			} else {
//				maskHeight = bodyHeight;
//			}
//			$('.eaMask').css({ height: maskHeight});
//		}
	};
	jQuery.lightBox.close = function(){};
	
	
	$(document).unbind('keyup').keyup(function(event){
		if (event.keyCode == '13' || event.keyCode == '27') {
			event.preventDefault();
			jQuery.lightBox.close();
	    }
	});


})(jQuery);

function alert(message,onClose,width,align){
	$.lightBox(message,onClose,width,align);
}


