$(function(){

	

	//排列item
	$('#homeWall').masonry({itemSelector: '.item'});
	//图片lazyload
	var $homeWall = $("#homeWall");
	$homeWall.find('.photo').lazyload({
		//placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
		effect: "fadeIn"
	});
	
	
	
	//loading- scrolling
	if($("body").height() < $(window).height()){
		hasData = false;
		$("#loadMore").fadeOut(500);
	}
	var scrollFun = function (begin,size,sortId,publicationId){
		var loadNum = 30;
		var $loading = $("#loadMore");
		var $shortCut = $("#homeWall > div.item:visible:not([class~='patch'])");
		if(($shortCut.length%loadNum) != 0){
			hasData = false;
			$loading.fadeOut(500);
			return;
		}

		begin = begin || 0;
		size = size || loadNum;
		
		publicationId = (!!publicationId) ? publicationId : getUrlValue("publicationId");
		begin = $shortCut.length ;
		var data = {};
		data.begin = begin;
		data.size = size;
		if(curSortId)
			data.sortId = curSortId;
		if(curTagId)
			data.secondSortId = curTagId;
			
		if(!!publicationId){
			data.publicationId = publicationId;
		}		
		
		var ajaxUrl = (window.location.href.indexOf("preview")>-1)?"/index!eventAjaxPreview.action":"/index!eventAjax.action";
		$.ajax({
			url : SystemProp.appServerUrl + ajaxUrl,
			type : "GET",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					hasData = false;
					$loading.fadeOut(500);
					return;
				}
				if(rs.code == 200 && !!rs.data && !!rs.data.looArticleList && rs.data.looArticleList.length>0){
					var itemList = rs.data.looArticleList;
					var itemHtml = "";
					for(var i = 0; i < itemList.length; i++){
						var item = itemList[i];
						var it_high =1;
						var it_width =1;
						if(item.high){
							it_high = item.high;
						}
						if(item.width){
							it_width =item.width;
						}
						item.high = it_high * 210 / it_width;//确定图片的显示高度
						if(item.smallPic){
							item.smallPic="http://static.magme.com"+item.smallPic;
						}
						if(item.described&&item.described.length > 150){
							item.described = item.described.substring(0,150) + "……";
						}
						
						if(item.parentTitle){
							var itemList_b = item.itemTitle.split(",");
							var itemList_id = item.itemId.split(",");
							var itemList_parentid = item.parentId.split(",");
							var tStr = "";
							for(k=0;k<itemList_b.length;k++){
								if(k<3){
									var tempName =  itemList_b[k];
									var id = itemList_id[k];
									if(itemList_parentid[k]!="0")
										id = itemList_parentid[k];
									tStr += "<strong class=\"navC"+id+"\">"+tempName+"</strong>";	
								}
							}
							item.itemTitle = tStr;
						}
						itemHtml +="<div class=\"item\">";
						itemHtml += "<a href=\"http://www.magme.com/sns/c"+item.id+"/\" target=\"_blank\">";
						itemHtml += "<div class=\"photo\"><img height=\""+item.high+"\"  src=\""+item.smallPic+"\" alt=\""+item.title+"\"></div>";
						itemHtml += "<div class=\"info png\">";
						itemHtml += "<div class=\"class\">"+item.itemTitle+"</div>";
						itemHtml += "<h2>"+item.title+"</h2>";
						itemHtml += "<p>"+item.memo+"</p>";
						itemHtml += "</div>";
						itemHtml += "</a>";
						itemHtml += "<div class=\"tools png\">";
						itemHtml += "<em class=\"iconShare png\" shareTypeId=\"eve_"+item.id+"_creative\"></em></div>";
						itemHtml += "</div>";
						
					}
					
					var $event = $.tmpl(itemHtml);
					$event.appendTo($homeWall);
					//itemsHov($event);
					//itemHoverIE6();
					
					$homeWall.masonry('appended', $event);


				}else{
					hasData = false;
					$loading.fadeOut(500);
				}
				
			}
		});
	}
	//绑定页面的scroll事件
	scrollLoadData(scrollFun,600);
});


$(window).load(function() {
(function(){
    //右侧
    var $sRobj = $('.sideRight'),
        $sRTop = $sRobj.offset().top,
        $sRLeft = $sRobj.offset().left,
        $sRHeight = $sRobj.outerHeight(),
        $sRParent  = $sRobj.parent(),
        $sRParenLeft  = $sRParent.offset().left,
        $sRline  =  $sRLeft-$sRParenLeft,
        $footertop = $('.footer20150126').offset().top,
        ltflag = $(window).width()>1180; 
    //   
    function resizesideright(){
        $sRParenLeft = $sRParent.offset().left,
        $sRLeft = $sRline + $sRParenLeft;
        ltflag = $(window).width()>1180;
        var $wh = $(window).height();
        var $wscrollTop = $(window).scrollTop();  
        if($wh-$sRTop-$sRHeight>=0&&ltflag){
            //内容小于屏幕
           if($footertop-$wscrollTop-$sRHeight-$sRTop>0&&ltflag){
                $sRobj.css({'position' : 'fixed','left' : $sRLeft,'top' : $sRTop,'bottom': 'auto'});     
           }else{
               var $relute = $footertop-$sRHeight-$sRTop+16;
               $sRobj.css({'position' : 'absolute','left' : '','top' : $relute,'bottom': 0});
           } 
        }else{
            //内容大于屏幕
           if($sRHeight-($wh-$sRTop)<$wscrollTop&&ltflag&&$footertop-$wscrollTop>$wh){
               $sRobj.css({'position' : 'fixed','left' : $sRLeft,'top' : 'auto','bottom': 0});  
           }else if($footertop-$wscrollTop<$wh&&ltflag){
                var $relute = $footertop-$sRHeight-$sRTop+16;
               $sRobj.css({'position' : 'absolute','left' : '','top' : $relute,'bottom': 0});
           }else{
              $sRobj.css({'position' : '','left' : '','top' : '','bottom': ''});          
           }        
        };
    }
    resizesideright();
    $(window).on("resize",resizesideright);
    $(window).on("scroll",resizesideright);
    $('.clickLoadMore').click(function(){
        $footertop = $('.footer20150126').offset().top;
        resizesideright();
    });    
})();
});
