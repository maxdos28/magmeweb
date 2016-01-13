;$(function(){
	
	var topSlide = {
		current : 1,
		timerid : 1,
		t : null,
		init : function(){
			$("#topSlide>.dot>a").bind("click", topSlide.onclick);
			topSlide.current = 1;
			topSlide.timer();
		},
		onclick : function (e){
			if( !$(this).hasClass("current") ) {
				topSlide.move($(this).index() + 1);
				topSlide.timerid = $(this).index() + 2;
				$("#topSlide>.dot>a").removeClass("current");
				$(this).addClass("current");
				clearTimeout(topSlide.t);
				topSlide.t = setTimeout(function(){
					topSlide.timer();
				},10000);
			}
		},
		timer : function(){
			clearTimeout(topSlide.t);
			topSlide.t = setTimeout(function(){
				topSlide.timer();
			},10000);
			if (topSlide.current == 3){
				topSlide.timerid = 1;
			}
			topSlide.move(topSlide.timerid);
			$("#topSlide>.dot>a").removeClass("current");
			$($("#topSlide>.dot>a")[topSlide.timerid - 1]).addClass("current");
			topSlide.timerid += 1;
		},
		move : function(target){
			if (topSlide.current != target) {
				switch (topSlide.current) {
					case 1 :
						$("#topSlide>.inner>.c1").stop(true,true).animate({left:"-960px"}, 600);
						$("#topSlide>.inner>.c2").stop(true,true).animate({left:"-960px"}, 600);
						break;
					case 2 :
						if (topSlide.current < target) {
							$("#topSlide>.inner>.c3").stop(true,true).animate({left:"-960px"}, 600);
							$("#topSlide>.inner>.c4").stop(true,true).animate({left:"-960px"}, 600);
							$("#topSlide>.inner>.c5").stop(true,true).animate({left:"-960px"}, 600);
						}else{
							$("#topSlide>.inner>.c3").stop(true,true).animate({left:"1200px"}, 600);
							$("#topSlide>.inner>.c4").stop(true,true).animate({left:"1200px"}, 600);
							$("#topSlide>.inner>.c5").stop(true,true).animate({left:"1200px"}, 600);
						}
						break;
					case 3 :
						$("#topSlide>.inner>.c6").stop(true,true).animate({left:"1200px"}, 600);
						$("#topSlide>.inner>.c7").stop(true,true).animate({left:"1200px"}, 600);
						$("#topSlide>.inner>.c8").stop(true,true).animate({left:"1200px"}, 600);
						$("#topSlide>.inner>.c9").stop(true,true).animate({left:"1200px"}, 600);
						$("#topSlide>.inner>.c10").stop(true,true).animate({left:"1200px"}, 600);
						break;
				}
			}
			switch (target) {
				case 1 :
					if (topSlide.current == 1) {
						$("#topSlide>.inner>.c1").css({"left":"960px"});
						$("#topSlide>.inner>.c2").css({"left":"960px"});
						$("#topSlide>.inner>.c1").stop(true,true).animate({left:"-30px"}, 600);
						$("#topSlide>.inner>.c2").stop(true,true).delay(160).animate({left:"510px"}, 800);
					}else{
						$("#topSlide>.inner>.c1").css({"left":"-960px"});
						$("#topSlide>.inner>.c2").css({"left":"-960px"});
						$("#topSlide>.inner>.c1").stop(true,true).animate({left:"-30px"}, 600);
						$("#topSlide>.inner>.c2").stop(true,true).delay(120).animate({left:"510px"}, 800);
					}
					break;
				case 2 :
					if (topSlide.current < target) {
						$("#topSlide>.inner>.c3").css({"left":"1200px"});
						$("#topSlide>.inner>.c4").css({"left":"1200px"});
						$("#topSlide>.inner>.c5").css({"left":"1200px"});
						$("#topSlide>.inner>.c3").stop(true,true).animate({left:"90px"}, 600);
						$("#topSlide>.inner>.c4").stop(true,true).delay(120).animate({left:"580px"}, 700);
						$("#topSlide>.inner>.c5").stop(true,true).delay(160).animate({left:"500px"}, 800);
					}else{
						$("#topSlide>.inner>.c3").css({"left":"-960px"});
						$("#topSlide>.inner>.c4").css({"left":"-960px"});
						$("#topSlide>.inner>.c5").css({"left":"-960px"});
						$("#topSlide>.inner>.c3").stop(true,true).animate({left:"90px"}, 600);
						$("#topSlide>.inner>.c4").stop(true,true).delay(120).animate({left:"580px"}, 700);
						$("#topSlide>.inner>.c5").stop(true,true).delay(160).animate({left:"500px"}, 800);
					}
					break;
				case 3 :
					$("#topSlide>.inner>.c6").css({"left":"1200px"});
					$("#topSlide>.inner>.c7").css({"left":"1200px"});
					$("#topSlide>.inner>.c8").css({"left":"1200px"});
					$("#topSlide>.inner>.c9").css({"left":"1200px"});
					$("#topSlide>.inner>.c10").css({"left":"1200px"});
					$("#topSlide>.inner>.c6").stop(true,true).animate({left:"0"}, 600);
					$("#topSlide>.inner>.c7").stop(true,true).delay(300).animate({left:"90px"}, 800);
					$("#topSlide>.inner>.c8").stop(true,true).delay(260).animate({left:"260px"}, 800);
					$("#topSlide>.inner>.c9").stop(true,true).delay(220).animate({left:"430px"}, 800);
					$("#topSlide>.inner>.c10").stop(true,true).delay(180).animate({left:"600px"}, 800);
					break;
			}
			topSlide.current = target;
		}
	};
	
	topSlide.init();
	
	
});