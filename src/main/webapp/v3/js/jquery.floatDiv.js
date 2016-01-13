// float div version 1.0 by lewis
;(function ($) {
jQuery.floatDiv = function(id,marginT) {
		var $id = $(id);
		var marginL = 0;
		var offsetT = $id.offset().top;
		var offsetL = $id.parent().offset().left;
		var thisH = $id.height() + marginT + 10;
		var scrollT = $(document).scrollTop();
		var bodyH = Math.min($(window).height(), document.body.clientHeight);
		init();
		
		//isIE6判断
		var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;

		//起始
		function init(){
			if(thisH < bodyH){
				$(window).bind("scroll",fnScroll);
			}
			$(window).bind("resize",fnResize);
			fnResize();
		}
		
		
		//舞台缩放事件
		function fnResize(){
			bodyH = Math.min($(window).height(), document.body.clientHeight);
			
			if(thisH > bodyH){
				$(window).unbind("scroll",fnScroll);
				$id.css({position:'static',marginTop:0,left:marginL});
			}else{
				$(window).bind("scroll",fnScroll);
				$id.css({marginTop:0});
				offsetL = $id.parent().offset().left;
				fnScroll();
			}
		}
		
		//舞台滚动事件
		function fnScroll(){
			scrollT = $(document).scrollTop();
			if(scrollT < offsetT - marginT){
				$id.css({position:'static',top:marginT,left:marginL});
			}else if(scrollT >= offsetT - marginT){
				offsetL = $id.parent().offset().left;
				if(isIE6){
					$id.css({position:'absolute',top:scrollT - offsetT + marginT,left:marginL});
					
				}else{
					$id.css({position:'fixed',top:marginT,left:offsetL});
				}
			}
		}
    }; 
})(jQuery); 