;(function($){
//imgWidth:源图宽
//imgHeight:源图高
//tagWidth:tag图宽
//tagHeight:tag图高
//tagLeft:tag左边距
//tagTop:tag上边距
//imgUrl:源图url
//tagUrl:tag图url
	jQuery.showTagArea = function(setting) {
		var defaultOpt = {
			onClose:function(){},
			imgWidth: 472,
			imgHeight: 584,
			tagWidth: 150,
			tagHeight: 80,
			tagLeft: 240,
			tagTop: 170,
			//imgUrl: SystemProp.staticServerUrl+"/images/tempMagezineBig4.jpg",
			//tagUrl: SystemProp.staticServerUrl+"/images/tempMagezineBig4_r2_c2.jpg"
			imgUrl: "images/tempMagezineBig4.jpg",
			tagUrl: "images/tempMagezineBig4_r2_c2.jpg"
		};
		var opt = $.extend({},defaultOpt,setting);
		var imgWidth = opt.imgWidth,
			imgHeight = opt.imgHeight,
			tagWidth = opt.tagWidth,
			tagHeight = opt.tagHeight,
			tagLeft = opt.tagLeft,
			tagTop = opt.tagTop,
			imgUrl = opt.imgUrl,
			tagUrl = opt.tagUrl;
		var id = ".conBigMgzShow";
		var $btn = $(id).find(".btnToggle");
		var maxWidth = 472;
		var maxHeight = Math.round(imgHeight*maxWidth/imgWidth);
		var $bigImg = $(id).find("img[bigimg]");
		var $del = $(id+ " .showArea>a.del");
		var $mask = $(id+" .mask");
		var $showArea = $(id+" .showArea");
		var $tag = $(id+" .showArea>img");
		var showAreaWidth = Math.round(tagWidth*maxWidth/imgWidth);
		var showAreaHeight = Math.round(tagHeight*maxHeight/imgHeight);
		var showAreaTop = Math.round(tagTop*maxHeight/imgHeight)+5;
		var showAreaLeft = Math.round(tagLeft*maxWidth/imgWidth)+5;
		$bigImg.attr("src",imgUrl);
		$tag.attr("src",tagUrl);
		//$(id).height(maxHeight);
		$mask.css({width:maxWidth,height:maxHeight,opacity:0.5});
		
		if(Math.random()>0.5){
			$btn.parent().removeClass("bigMgzShow1").addClass("bigMgzShow2");
			var autoWidth;
			if(tagWidth<473){
				autoWidth=tagWidth;
			}else{
				autoWidth=472;
			}
			$showArea.css({width:autoWidth,height:"auto",top:0,left:0});
			$showArea.find("img").css({width:autoWidth,height:"auto"});
		}else{
			$showArea.css({width:showAreaWidth,height:showAreaHeight,top:showAreaTop,left:showAreaLeft});
			$tag.css({width:showAreaWidth,height:showAreaHeight});
		}
		
		$del.click(function(){
			$mask.fadeOut(300);
			$showArea.fadeOut(300);
			setTimeout(function(){$(id).css({position:"inherit"})},300);
			
			opt.onClose();
		});
		
		$btn.click(function(){
			if($(this).parent().hasClass("bigMgzShow1")){
				$(this).parent().removeClass("bigMgzShow1").addClass("bigMgzShow2");
				var autoWidth;
				if(tagWidth<473){
					autoWidth=tagWidth;
				}else{
					autoWidth=472;
				}
				$showArea.css({width:autoWidth,height:"auto",top:0,left:0});
				$showArea.find("img").css({width:autoWidth,height:"auto"});
				viewClick("tagShow_view_tag");
			}else{
				$(this).parent().removeClass("bigMgzShow2").addClass("bigMgzShow1");
				$showArea.css({width:showAreaWidth,height:showAreaHeight,top:showAreaTop,left:showAreaLeft});
				$tag.css({width:showAreaWidth,height:showAreaHeight});
				viewClick("tagShow_view_whole");
			}
		});
		
		function viewClick(code){
	    	$.ajax({
					url:SystemProp.appServerUrl+"/user-tag!clickJson.action",
					type : "POST",
					data : {"code":code},
					dataType : "json"
			});
		}
	}; 
})(jQuery); 