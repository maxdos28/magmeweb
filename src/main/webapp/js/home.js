//切米达人排行榜
$(function(){
	var $id = $("#homeVip");
	var $vipHead = $("#homeVip .headList li");
	var $vipOuter = $("#homeVip .userTag tr");
	var $vipInner = $("#homeVip .userTag .inner");
	var $userArrow = $("#homeVip .headList .arrow");
	var $load = $("#homeVip .loading");
	var innerWidth = 0;
	var innerLeft = 0;
	var index = 0;
	var speed = 15;
	
	//ready
	$vipHead.eq(0).addClass("current");
	fnLoadEnd(0);
	
	//switch user
	$vipHead.click(function(){
		index = $(this).index();
		if(!$(this).hasClass("current")){
			$(this).addClass("current").siblings().removeClass("current");
			$userArrow.animate({left:$(this).index()*72+38},300);
			$vipOuter.hide();
			$load.show();
			//在此插入图片路径
			fnLoadEnd(index);
		}
	});
	//mouseover
	$vipInner.mousemove(function(e){
		$(this).stop();
		innerLeft = parseInt($vipInner.eq(index).css("marginLeft"));
	}).mouseleave(function(){
		$vipInner.eq(index).animate({marginLeft:372-innerWidth},(innerWidth+innerLeft)*speed);
	});
	//set inner width
	function fnSetWidth(id){
		var i = 0;
		var iWidth = 0;
		$(id).find("a").each(function(){
			iWidth += $(id).find("a").eq(i).outerWidth()+4;
			i++;
		});
		innerWidth = iWidth;
	}
	//set inner animate
	function fnVipMove(index){
		if(innerWidth>372){
			$vipInner.eq(index).stop().css({marginLeft:0}).delay(550).animate({marginLeft:372-innerWidth},innerWidth*speed);
		}
	}
	//image load
	function fnLoadEnd(index){
		$vipInner.eq(index).onImagesLoad({
			selectorCallback:function(){
				$load.fadeOut(300);	
				$vipOuter.eq(index).fadeIn(500);
				//show
				fnSetWidth($vipInner.eq(index));
				$vipInner.eq(index).width(innerWidth);
				fnVipMove(index);
			}
		});
	}
});
