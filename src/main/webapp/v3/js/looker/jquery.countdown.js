;(function($){
$.fn.countdown = function(options) {
	var defaultOpt = {
		id: "#timer",
		height: 29.5,
		speed: 1000
	};
	var opt = $.extend({},defaultOpt,options);
	var id = opt.id;
	var height = opt.height;
    var speed = opt.speed;
    var Tday = opt.Tday;
    var step = 6;


	var DifferenceHour = -1;
	var DifferenceMinute = -1;
	var DifferenceSecond = -1;
	var daysms = 24 * 60 * 60 * 1000;
	var hoursms = 60 * 60 * 1000;
	var Secondms = 60 * 1000;
	var microsecond = 1000;
	var dSecs;
	var autoPlay = setInterval(clock,speed);

	setTimeout(function(){
		$(id).parent().css({visibility:'visible'});

		if(!DifferenceHour){
			$(id).find('div.day').hide();
		}
	},speed);




	function clock()
	{

		var time = new Date();
		var hour = time.getHours();
		var minute = time.getMinutes();
		var second = time.getSeconds();
		var convertHour = DifferenceHour;
		var convertMinute = DifferenceMinute;
		var convertSecond = DifferenceSecond;
		var Diffms = Tday.getTime() - time.getTime();
		DifferenceHour = Math.floor(Diffms / daysms);
		Diffms -= DifferenceHour * daysms;
		DifferenceMinute = Math.floor(Diffms / hoursms);
		Diffms -= DifferenceMinute * hoursms;
		DifferenceSecond = Math.floor(Diffms / Secondms);
		Diffms -= DifferenceSecond * Secondms;
		dSecs = Math.floor(Diffms / microsecond);
		var timemiao = Tday.getTime() - time.getTime();
		if(timemiao>=0&&timemiao<1000){
			location.reload();
		}
		if(DifferenceHour<0){
			clearInterval(autoPlay);
			$(id).find("span").css({backgroundPosition:"0 0"}).find("img").hide();
			DifferenceHour = DifferenceMinute = DifferenceSecond = dSecs = 0;
			return false;
		}
		fnShow();
	} 


	function fnShow(){

		fnPlay($(id).find("div.sec"), dSecs);
		fnPlay($(id).find("div.min"), DifferenceSecond);
		fnPlay($(id).find("div.hour"), DifferenceMinute);
		fnPlay($(id).find("div.day"), DifferenceHour);
	}
	
	
	function fnPlay(_this, num){
		var n0,n1,n2,_n0,_n1,_n2,i=0;
		if(num>99){
			n0 = String(num).slice(0,1);
			n1 = String(num).slice(1,2);
			n2 = String(num).slice(2,3);
		}else if(num>9){
			n0 = 0;
			n1 = String(num).slice(0,1);
			n2 = String(num).slice(1,2);
		}else{
			n0 = 0;
			n1 = 0;
			n2 = String(num).slice(0,1);
		}
		fnSlide();

		
		setTimeout(function(){
			_this.find("span").eq(0).text(n0);
			_this.find("span").eq(1).text(n1);
			_this.find("span").eq(2).text(n2);
		},600)
		
		function fnSlide(){
			i++;
			if( n0!=parseInt(_this.find("span").eq(0).text())){
				var _y = -((9-n0)*height*step + i*height) == -height*step*10 ? 0 : -((9-n0)*height*step + i*height);
				_this.find("span").eq(0).css({backgroundPosition:"0 "+ _y +"px"});
			}
			if( n1!=parseInt(_this.find("span").eq(1).text()) ){
				var _y = -((9-n1)*height*step + i*height) == -height*step*10 ? 0 : -((9-n1)*height*step + i*height);
				_this.find("span").eq(1).css({backgroundPosition:"0 "+ _y +"px"});
			}
			if( n2!=parseInt(_this.find("span").eq(2).text()) ){
				var _y = -((9-n2)*height*step + i*height) == -height*step*10 ? 0 : -((9-n2)*height*step + i*height);
				_this.find("span").eq(2).css({backgroundPosition:"0 "+ _y +"px"});
			}
			
			if(i<6){
				setTimeout(fnSlide,50)
			}
		}			
	}
	
}; 
})(jQuery); 