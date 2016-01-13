//
;(function($){
	jQuery.imageUploader = function() {
		var flashvars = {}; 
		var params = { allowfullscreen: "false", allowScriptAccess: "always", wmode: "transparent"}; 
		var attributes = { id : "flowdesigner"}; 
		swfobject.embedSWF("/reader/uploadMain.swf", "swfloader", "130", "48", "9.0.0", "swf/expressInstall.swf", flashvars, params, attributes);
		
		var share = {
			start : function(id,name) {
				$("#imagesList").show();
				$(".conShareImages>.imagesCon").show();
				var innerDiv="<div class='item' dataid='" + id + "'><div class='inner'><a href='javascript:void(0);' class='close'></a>";
				if($("#articleSortOrder").val()==0){
					innerDiv=innerDiv+"<a class='btnGS' href='javascript:void(0);'><label><input justCover='' type='checkbox' />只上首页</label></a>";
				}
				innerDiv=innerDiv+"<div class='loading'><strong>" + name + "</strong><span class='progress'><em>0%</em></span></div><div class='img'><div class='mask'></div><img src='#' /></div><textarea></textarea></div></div>";
				$("#imagesList>.inner").append(innerDiv).show();
			},
			progress : function(id,percent) {
				$("#imagesList>.inner>.item[dataid=" + id + "]").find(".progress>em").css({width : percent + "%"}).html(percent + "%");
			},
			error : function(id,type) {
				$("#imagesList>.inner>.item[dataid=" + id + "]").removeClass("itemComplete").find(".loading>span").addClass("error").html(type);
			},
			complete : function(id,url,w,h) {
				var $target = $("#imagesList>.inner>.item[dataid=" + id + "]");
				$target.addClass("itemComplete").find(".img>img").attr({"src" : url,"width" : w,"height" : h});
				$target.find(".img>img").coverImg();
				$target.removeAttr("dataid");
			}
		}
		return share;
	}

})(jQuery);

