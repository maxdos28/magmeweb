

/*
 *  colorNo: 全局的鼠标未移入颜色值
 *  colorYes: 全局的鼠标移入颜色值
 *  colorN,colorN: 如果input,textarea含有color属性时,会存入到这两个变量内
 */

;(function ($){
jQuery.jqueryInputTips = function() {
	
	
	// 设置颜色值
	var colorNo,colorYes,colorN,colorY;
	colorYes = '#333';
	colorNo = '#ccc';

	// 分别绑定方法
	$('input[tips],textarea[tips]').each(function(){
		checkColor($(this));
		$(this).css({color:colorN});
		$(this).val($(this).attr("tips"));
		$(this).focus(focusIn);
		$(this).blur(focusOut);
	});
	
	// 鼠标移入方法
	function focusIn(){
		
		checkColor($(this));
		$(this).css({color:colorY});
		var tips = $(this).attr('tips');
		var type = $(this).attr('type');

		if($(this).parent().find("input[type='password']").length !=0){
			if(type === 'text'){
				$(this).hide();
				var password = $(this).next().eq(0);
				password.show().val('').css({color:colorY}).focus();
			}
		}else{
			if( $(this).val() == tips ) {
				$(this).val('');
			}
		}
	}
	
	// 鼠标移出方法
	function focusOut(){
		
		checkColor($(this));
		
		$(this).css({color:colorN});
		var tips = $(this).attr('tips');
		var val = $(this).val();
		var type = $(this).attr('type');

		
		if ( $.trim(val) == '' || $(this).val() == null ) {
			if(type === 'password'){
				$(this).hide();
				$(this).prev().eq(0).show().css({color:colorN}).val(tips);
			}
			else{
				$(this).css({color:colorN}).val(tips);
			}
		}else{

			$(this).css({color:colorY});
		}
	}



	// 判断是否含有color属性
	function checkColor(e) {
		if (e.attr("color")) {
			colorY = e.attr("color").split(",")[1];
			colorN = e.attr("color").split(",")[0];
		} else {
			colorY = colorYes;
			colorN = colorNo;
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
