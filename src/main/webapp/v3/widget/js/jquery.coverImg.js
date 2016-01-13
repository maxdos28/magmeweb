(function($){
    $.fn.coverImg = function(options) {
        var defaults={
            "width":null,
            "height":null
        };
        var opts = $.extend({},defaults,options);
        return $(this).each(function() {

			var imgW = $(this).attr("width") || $(this).width();
			var imgH = $(this).attr("height") || $(this).height();
			
			
			var showW = $(this).parent().width();
			var showH = $(this).parent().height();
			var imgNoun = imgW/imgH;
			var showNoun = showW/showH;
			
		
		
			if(imgNoun < showNoun){
				$(this).width(showW).height(showW/imgNoun).css({marginLeft:0,marginTop:0});
			}else{
				$(this).width(showH*imgNoun).height(showH).css({marginLeft:-(showH*imgNoun-showW)/2,marginTop:0});
			}


        });
    };
})(jQuery);
