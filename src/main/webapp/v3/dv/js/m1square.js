$(function() {
	// 首页左侧高度设置，每次加载更多时需要执行
	fnSetHomeHeight();
	
	// 排列item
	$('#homeWall').masonry({
		itemSelector : '.item'
	});
	
	var $item = $("#homeWall .item");
	var homeItemLock = 0;
	var homeDelayTime;
	
	window.itemsHov = function ($item){
		$item.mouseenter(function(){
			var obj = $(this);
			homeDelayTime = setTimeout(function(){
				obj.find(".info").addClass("infoCurrent");
			},500)
		}).mouseleave(function(){
			var obj = $(this);
			clearTimeout(homeDelayTime);
			obj.find(".infoCurrent").removeClass("infoCurrent");
		});
	}
	itemsHov($item);
	
	//item移入
	function itemHoverIE6(){
		if(isIE6){
			$(".body>.item").not(".itemFirst,.itemSpace").mouseenter(function(){
				$(this).find(".tools").addClass("toolsOn").find("em").css({display:"inline-block"});
			}).mouseleave(function(){
				$(this).find(".tools").removeClass("toolsOn").find("em").not(".favCurrent").css({display:"none"});
			});
		}
	}
	itemHoverIE6();
	
	var $homeWall = $("#homeWall");
	
	//登录
	$("#squareLoginA").click(function(){
		$("#userLogin").click();
	});
	//注册
	$("#squareLoginReg").click(function(){
		$("#userReg").click();
	});
	
	$("#changeUserList")
			.live(
					"click",
					function() {
						var begin = $(this).attr("begin");
						var size = 5;
						if (begin) {
							begin = Number(begin) + Number(size);
						} else {
							alert("参数错误");
							return;
						}
						var data = {};
						data.begin = begin;
						var ajaxUrl = "/sns/square!changeUserList.action"
						$
								.ajax({
									url : SystemProp.appServerUrl + ajaxUrl,
									type : "post",
									data : data,
									success : function(result) {
										if (result.code == 200) {
											if (result.data) {
												var itemList = result.data.userInfoList;
												var userHtml = "";
												$
														.each(
																itemList,
																function(k, u) {
																	var tempVal = "http://static.magme.com/v3/images/head60.gif";
																	userHtml += '<a href="http://www.magme.com/sns/u'
																			+ u.id
																			+ '/">';
																	if(u.avatar){
																	userHtml += '<img src="http://static.magme.com/profiles/'
																			+ avatarResize(u.avatar,"60")
																			+ '" />';
																	}else{
																		userHtml += '<img src="'
																			+ tempVal
																			+ '" />';
																	}
																	userHtml += '<strong>'
																			+ u.nickname
																			+ '</strong>';
																	userHtml += '<p><span>'
																			+ u.creativeCount
																			+ '</span>作品</p>';
																	/*
																	userHtml += '<em uId="'
																			+ u.id
																			+ '">加关注</em>';*/
																	userHtml += '</a>';
																});
												userHtml += '<a href="javascript:void(0)" id="changeUserList" begin="'
														+ result.data.begin
														+ '" size="5" class="more">换一批&nbsp;作者</a>';
												$("#changeUserListDiv").html(
														userHtml);
											}
										}
									}
								});
					});

	// 加为好友
	/*
	 * <em name="sns_addFriend" nick="${u.nickname!''}" u="${u.id}">加关注</em>
	$("em[name='sns_addFriend']").live("click", function() {
		if($("#userBar").attr("style")==undefined){
		gotoLogin("请登录后，才能添加关注哦！",1);
		return;
		}
		var _$this =this;
		var userId = $(this).attr("u");
		var nick=$(this).attr("nick");
		var tx=$(this).html();
		if(tx=='加关注'){
		ajaxAddFollow(userId,1,function(rs){
		if(!rs)return;
		var code = rs.code;
		if(code == 200){
		$(_$this).html('取消关注');
		if($(_$this).hasClass("iconAdd"))
		$(_$this).attr("class","iconCancel");
		else
		$(_$this).attr("class","cancel");
		$(_$this).parents(".userHead").find(".btnGS").html('取消关注');
		$(_$this).parents(".userHead").find(".btnGS").attr("class","btnWS sns_btnWS");
		}else if(code == 400){
		gotoLogin("用户没有登录，请登录！");
		}else{
		alert(rs.message);
		}
		});
		}else{
		if(confirm("确定不再关注 "+nick+" 了吗？"))
		{
		ajaxCancelFollow(userId,1,function(rs){
		if(!rs)return;
		var code = rs.code;
		if(code == 200){
		if($(_$this).hasClass("iconCancel"))
		$(_$this).attr("class","iconAdd");
		else
		$(_$this).attr("class","atten");
		$(_$this).html('加关注');
		$(_$this).parents(".userHead").find(".btnWS").html('添加关注');
		$(_$this).parents(".userHead").find(".btnWS").attr("class","btnGS sns_btnGS");
		}else if(code == 400){
		gotoLogin("用户没有登录，请登录！");
		}else{
		alert(rs.message);
		}
		});
		}
		}
		}); 
	*/

	var scrollFun = function(begin, size) {
		var loadNum = 15;
		// var $loading = $("#loading");
		var $loading = $("#loadMore");
		var $shortCut = $("#homeWall > div.item:visible:not([class~='patch'])");
		if (($shortCut.length % loadNum) != 0) {
			hasData = false;
			$loading.fadeOut(500);
			return;
		}

		begin = begin || 0;
		size = size || loadNum;

		begin = $shortCut.length;
		var data = {};
		data.begin = begin;
		data.size = size;
		var tagName = decodeURI(getUrlValue2("tagName"));
		if (tagName)
		{
			if(tagName != 'null'){
				data.tagName = tagName;
			}
		}

		var ajaxUrl = "/sns/square!squareList.action";
		$
				.ajax({
					url : SystemProp.appServerUrl + ajaxUrl,
					type : "GET",
					async : false,
					data : data,
					success : function(rs) {
						if (!rs) {
							hasData = false;
							$loading.fadeOut(500);
							return;
						}
						if (rs.code == 200 && !!rs.data
								&& !!rs.data.m1List
								&& rs.data.m1List.length > 0) {
							
							var itemList = rs.data.m1List;
							for ( var i = 0; i < itemList.length; i++) {
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
								if(item.imagePath){
									item.imagePath="http://static.magme.com"+avatarResize(item.imagePath,'max_400');
								}
								if(item.described){
									if(item.described.length > 150){
										item.described = item.described.substring(0,150) + "……";
									}
								}
							}
							itemStr = "<div class=\"item\">";
							itemStr += "<a href=\"http://www.magme.com/sns/c${id}/\" target=\"_blank\">";
							itemStr += "<div class=\"photo\"><img height=\"${high}\"  src=\"${imagePath}\" alt=\"${title}\"></div>";
							itemStr += "<div class=\"info png clearFix \">";
						    itemStr += "<div class=\"user\" class=\"png\" title=\"${nickName}\">";
						    itemStr += "<img src=\"http://static.magme.com/profiles/${avatar}\" onerror=\"this.src='/images/head60.gif'\"  />";
							itemStr += "<strong >${nickName}</strong>";
							itemStr += " </div>";
							itemStr += "<h2>${title}</h2>";
							itemStr += "<p>${described}</p>";
							itemStr += "</div>";
							itemStr += "</a>";
							itemStr += "<div class=\"tools png\"><em title=\"喜欢\" class=\"iconHeart png\" favTypeId=\"cre_${id}\"></em>";
							itemStr += "<em class=\"iconShare png\" shareTypeId=\"eve_${id}_creative\"></em></div>";
							itemStr += "</div>";
							
							var $event = $.tmpl(itemStr,itemList);
							$event.appendTo($homeWall);
							itemsHov($event);
							itemHoverIE6();
							
							$homeWall.masonry('appended', $event);
							// 首页左侧高度设置，每次加载更多时需要执行
							fnSetHomeHeight();
						} else {
							hasData = false;
							$loading.fadeOut(500);
						}

					}
				});
	}
	 //绑定页面的scroll事件
	scrollLoadData(scrollFun,600); 
	
	function avatarResize(imgPath, pre) {
		if (imgPath && imgPath.indexOf("/") > -1) {
			var index = imgPath.lastIndexOf("/") + 1;
			return imgPath.substring(0, index) + pre + "_"
					+ imgPath.substring(index);
		}
		return imgPath;
	}
	function fnSetHomeHeight() {
		var rightH = $(".sideRight").height();
		if ($.browser.safari) {
			rightH += 450;
		}
		if ($("#homeWall").height() < rightH) {
			$("#homeWall").height(rightH);
		}
	}
});