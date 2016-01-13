;(function ($){
jQuery.jqueryScrollPhoto = function(id,step,width,showNum,now,speed) {
	var leng=$(id+' ul li').length;
	var $outer = $(id+' ul');
	var $item = $(id+' ul li');
	var $ctrl = $(id+'>a');
	var $ctrlLeft = $(id+'>a.turnLeft');
	var $ctrlRight = $(id+'>a.turnRight');
	var $pic = $(id).prev(".conStyle").find(".item");
	var page = Math.ceil(leng/showNum);
	var margin = 0;
	var lock = 0;
	var i = 0;
	//ready
	if(now>page-1){now=page-1}
	i = now;
	$outer.css({width:leng*width});
	if(now==0){$ctrlLeft.addClass("disable")}
	if(leng <= showNum || now == page-1){$ctrlRight.addClass("disable")}
	$outer.css( { marginLeft: -(now*step*width) });
	$ctrl.click(function(){
		if(lock==0){
			lock=1;
			if($(this).hasClass("turnLeft")){
				if (i>0) {
					i--;
					margin=-i*step*width;
					$outer.animate( { marginLeft: margin } , speed ,"easeOutQuad" );
					$ctrl.removeClass("disable");
					if(i==0){
						$(this).addClass("disable");
					}
				}
			}else{
				if (i<page-1) {
					i++;
					margin=-i*step*width;
					$outer.animate( { marginLeft: margin } , speed ,"easeOutQuad" );
					$ctrl.removeClass("disable");
					if(i==page-1){
						$(this).addClass("disable");
					}
				}
			}
			setTimeout(function(){lock=0},speed);
		}
	});
	$item.click(function(){
		$(this).addClass("current").siblings().removeClass("current");
	});
}; 
})(jQuery);