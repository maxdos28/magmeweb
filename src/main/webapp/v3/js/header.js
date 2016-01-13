$(function(){
	var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;


	//主分类超过1行显示更多
	fnHomeNav();
	function fnHomeNav(){
		var $nav = $(".header20150126 .headerNav");
		var $navInner = $nav.find("ul.classification");
		var navLock = 0;
		if($navInner.height() > $nav.height()){
			$navInner.append("<li class='more'><a>...</a><ul></ul></li>");
			var $navMore = $navInner.find("li.more");
			var $navMoreList = $navInner.find("li").filter(function() {
				return $(this).position().top>10;
			});
			$navMore.find("ul").append($navMoreList);
			$navMore.find(">a").click(function(){
				if(navLock==0){
					$(this).next("ul").fadeIn(100,function(){navLock=1});
					$(this).text("x");
				}else{
					navLock=0;
					$(this).next("ul").fadeOut(100);
					$(this).text("...");
				}
			});
		}
		$("body").click(function(){
			if(navLock==1){
				navLock=0;
				$navMore.find(">a").next("ul").fadeOut(100);
				$navMore.find(">a").text("...");
			}
		});
	}
});

