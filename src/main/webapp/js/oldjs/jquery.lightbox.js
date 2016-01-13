//Light-box version 1.02  by lewis
;(function($){
	jQuery.lightBox = function(msg,onClose) {
		
		_init = function(){
			if($("#lightbox").length == 0){
				var lightBoxHtml = '<div id="lightbox" class="lightbox">'
					+'<div class="popBody"><p name="message"></p><a id="sure" href="javascript:void(0)" class="btnBS">确定</a></div>'
					+'<a id="lightbox_close" class="popClose"></a></div>';
				$('body').append(lightBoxHtml);
			}
		};
		_init();
		var forId = "#lightbox";
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
				if (broVer != 6.0 || $.browser.mozilla) {
					$(forId).css({ top: browserHeight / 2 - offsetY + addTop });
				}
				else {
					top = jQuery(window).scrollTop() + browserHeight / 2 - offsetY;
					$(forId).css({ top: top });
				}
			}
		}
	

		if ($(forId).data("isOpen") || $(forId).data("isOpen") == null) {
			open();
		}
	
	
		function open(){
			addTop = $(document).scrollTop();
			$(forId).data("isOpen", false);
			$('body').css({ position: 'relative', 'z-index': '888' });
			$(forId).find("p[name='message']").html(msg);
			$('body').append("<div class='eaMask'></div>");
			$('.eaMask').unbind('click').click(jQuery.lightBox.close);
			bodyHeight = $(document).height();
			browserHeight = $(window).height();
			if (browserHeight > bodyHeight) {
				maskHeight = browserHeight;
			} else {
				maskHeight = bodyHeight;
			}
	
			if (broVer == 6.0 && !$.browser.mozilla) {
				$(forId).css({ position: 'absolute' });
			} else {
				addTop = 0;
				$(forId).css({ position: 'fixed' });
			}
			$(forId).css({
				top: browserHeight / 2 - offsetY + addTop,
				left: '50%',
				zIndex: 10000,
				marginLeft: -$(forId).width() / 2,
				marginTop: -$(forId).height() / 2
			});
			$('.eaMask').css({
				background: '#202020',
				position: 'absolute',
				cursor: 'wait',
				left: '0',
				top: '0',
				filter: 'alpha(opacity=75)',
				'-moz-opacity': 0.75,
				opacity: 0.75,
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
			$(forId).fadeIn(200);
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
			$(forId).css({ top: top });
		}
		function closes() {
			$('.eaMask').fadeOut(200);
			$(forId).fadeOut(200);
			setTimeout("$('.eaMask').remove();", 200);
			setTimeout("$('select').css({visibility:'visible'})", 200);
			$(forId).data("isOpen", true);
			$(".status").hide();
			if(onClose && typeof onClose == "function"){
				onClose();
			}
		}
		function resizeHandle() {
			bodyHeight = $(document).height();
			browserHeight = $(window).height();
			if (browserHeight > bodyHeight) {
				maskHeight = browserHeight;
			} else {
				maskHeight = bodyHeight;
			}
			$('.eaMask').css({ height: maskHeight});
		}
	};
	jQuery.lightBox.close = function(){};
	
	
	$(document).unbind('keyup').keyup(function(event){
		if (event.keyCode == '13' || event.keyCode == '27') {
			event.preventDefault();
			jQuery.lightBox.close();
	    }
	});


})(jQuery);

function alert(message,onClose){
	$.lightBox(message,onClose);
}


