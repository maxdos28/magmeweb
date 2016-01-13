;(function ($){
jQuery.jqueryInputTips = function() {

	// 设置颜色值
	var colorNo 	= '#ccc';
	var colorYes 	= '#333';

	// 分别绑定方法
	$('input[tips],textarea[tips]').each(function(){
		$(this).css({color:colorNo});
		$(this).val($(this).attr("tips"));
		$(this).focus(focusIn);
		$(this).blur(focusOut);
	});
	
	// 鼠标移入方法
	function focusIn(){
		$(this).css({color:colorYes});
		var tips = $(this).attr('tips');
		var type = $(this).attr('type');
		if(tips === '确认密码' || tips === '密码'){
			if(type === 'text'){
				$(this).hide();
				var password = $(this).next().eq(0);
				password.show().val('').css({color:colorYes}).focus();
			}
		}else{
			if( $(this).val() == tips ) {
				$(this).val('');
			}
		}
	}
	
	// 鼠标移出方法
	function focusOut(){
		$(this).css({color:colorNo});
		var tips = $(this).attr('tips');
		var val = $(this).val();
		var type = $(this).attr('type');
		
		if ( $.trim(val) == '' || $(this).val() == null ) {
			if(tips === '确认密码' || tips === '密码'){
				if(type === 'password'){
					$(this).hide();
					$(this).prev().eq(0).show().css({color:colorNo}).val(tips);
				}
			}else{
				$(this).css({color:colorNo}).val(tips);
			}
		}else{
			$(this).css({color:colorYes});
		}
	}




	//头部登录用-----------------------------------------------------------------------------------------------------------
	$('input[tipsSpan]').parent().parent().css({position:"relative"});
	$('input[tipsSpan],textarea[tipsSpan]').each(function(){
		$(this).css({color:colorYes});
		$(this).focus(focusInTips);
		$(this).blur(focusOutTips);
		$(this).keydown(focusChange);
		if($(this).val()==""){
			$(this).parent().parent().append("<span class='tips'>"+$(this).attr('tipsSpan')+"</span>").css({color:colorNo});
		}
	});
	//当点击在span上的时候 focus 于input/textarea
	$("span.tips").live("click",function(){
		$(this).parent().find("input").eq(0).focus();
		$(this).parent().find("textarea").eq(0).focus();
		$(this).animate({opacity:0.3},300);
	})
	// 鼠标移入方法(新)
	function focusInTips(){
		$(this).parent().parent().find("span.tips").animate({opacity:0.3},300);
	}
	// 鼠标移出方法(新)
	function focusOutTips(){
		$(this).parent().parent().find("span.tips").animate({opacity:1},300);
		if($(this).val()=="" && $(this).parent().parent().find("span.tips").length==0){
			$(this).parent().parent().append("<span class='tips'>"+$(this).attr('tipsSpan')+"</span>").find(".tips").css({opacity:0,color:colorNo}).animate({opacity:1},300);
		}
	}
	// 文本框改变
	function focusChange(){
		$(this).parent().parent().find("span.tips").remove();
	}
	
	
}; 
})(jQuery); 



$(document).ready(function(){
	$.jqueryInputTips();
});
