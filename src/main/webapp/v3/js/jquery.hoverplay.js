;(function($){
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
jQuery.jqueryhoverplay = function(id,width,num) {
	var i = 0;
	var open = 0;
	var obj;
	$(id).hover(function(){
		obj=$(this).find(".album");
		delayPlay = setTimeout(function(){
			i=0;
			open = 1;
			if(!isIE6){
				play(obj);
			}else{
				obj.show().css({backgroundPosition:"right 0"});
			}
		},250);
	},function(){
		clearTimeout(delayPlay);
		open = 0;
		$(id).find(".album").css({backgroundPosition:"0 0"});
	});
	function play(obj){
		i++;
		setTimeout(function(){
			if(i>num-1){return}
			if(open==1){
				obj.css({backgroundPosition:(-i*width)+"px 0"});
				play(obj);
			}
		},i*3);	
	}
}; 
})(jQuery); 