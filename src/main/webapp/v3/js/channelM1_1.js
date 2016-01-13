//拖动插件
;(function($){
$.dragSort = function(id,num) {
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
var isIE7 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
var $id = $(id),
	 $item,
	 $null,
	 $hand,
	 length = $id.find(">.inner>.item").length,
	 moveDown = false,
	 x,_x,__x,
	 y,_y,__y,
	 W = 310,
	 H = 140,
 	 lock = 0,
	 lockSTO,
	 current,
	 target,
	 targetA,
	 targetZ,
	 T = num,
	 L;
//鼠标按下事件
$id.find(".item .mask").live('mousedown',function(e){
	if(lock==0){
		lock=1;
		moveDown = true;
		var $obj = $(this).parents(".item");
		current = $obj.index()+1;
		target = current;
		//定义偏移
		_x = $obj.position().left;
		_y = $obj.position().top;
		__x= e.pageX - $obj.offset().left + $id.offset().left;
		__y= e.pageY - $obj.offset().top + $id.offset().top;
		//定义拖动item
		$obj.clone().addClass("hand").appendTo($id);
		$hand = $id.find(".hand");
		$hand.css({opacity:0.75,zIndex:9,position:"absolute",left:_x,top:_y});
		//放置占位Item
		$obj.replaceWith("<div class='item itemNull'><a></a></div>");
		$null = $id.find(".itemNull");
		//定义item
		$item = $id.find(".inner .item");
		length = $item.length;
		L = Math.ceil(length/num);
		$id.find(">.inner").height(L*H);
		//设置绝对定位
		$item.each(function(){
			//当前行
			var thisL = Math.ceil(($(this).index()+1)/T-1);
			//当前列
			var thisT = $(this).index()-thisL*T;
			$(this).css({position:"absolute",left:thisT*W,top:thisL*H});
		});
	}
});
//拖动事件
$(document).mousemove(function(e){
	if(moveDown){
		moveStart = true;
		//清除鼠标拖动时浏览器默认拖选操作
		document.selection&&document.selection.empty&&(document.selection.empty(),1)||window.getSelection&&window.getSelection().removeAllRanges();
		x = e.pageX - __x;
		y = e.pageY - __y;
		$hand.css({left:x,top:y});
		var currentL = Math.ceil(current/T);
		var currentT = current-(currentL-1)*T;
		var offsetX = x-(currentT-1)*W;
		var offsetY = y-(currentL-1)*H;
		if(offsetX > W/2){
			if(offsetX> (T-currentT)*W){offsetX = (T-currentT)*W}//判断超出边界
			currentT = currentT + Math.floor((offsetX+W/2)/W);//向右移动
		}
		if(offsetX < -W/2){
			if(offsetX < -(currentT-1)*W){offsetX = -(currentT-1)*W}//判断超出边界
			currentT = currentT - Math.floor((Math.abs(offsetX)+W/2)/W);//向左移动
		}
		if( offsetY > H/2){
			if(offsetY > (L-currentL)*H){offsetY = (L-currentL)*H}//判断超出边界
			currentL = currentL + Math.floor((offsetY+H/2)/H);//向下移动
		}
		if(offsetY < -H/2){
			if(offsetY < -(currentL-1)*W){offsetY = -(currentL-1)*W}//判断超出边界
			currentL = currentL - Math.floor((Math.abs(offsetY)+H/2)/H);//向上移动
		}
		targetZ = currentT + (currentL-1)*T;//定义目标位置
		if(targetZ > length){targetZ=length;}//判断超出总范围
		//判断有移位后再处理
		if(target!=targetZ){
			//定义每次开始位置
			targetA = target;
			target = targetZ;
			//向后移动
			if(targetA < targetZ){
				//占位项Html位置调整
				if(target!=length){
					//放置在结束项前一位
					$item.eq(targetZ).before($null);
				}else{
					//放置在结束项前一个的后一位
					$item.eq(targetZ-1).after($null);
				}
				//移动每个位置改变的Item
				$item.slice(targetA-1,targetZ).each(function(){
					//定义当前左偏移
					var offsetL = Math.ceil(($(this).index()+1)/T-1);
					//定义当前上偏移
					var offsetT = $(this).index()-offsetL*T;
					//移动到目标位置动作
					if(!isIE6){
						$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutCirc");
					}else{
						$(this).css({left:offsetT*W,top:offsetL*H});
					}
				});
			}
			//向前移动
			else if(targetA > targetZ){
				$item.eq(targetZ-1).before($null);
				$item.slice(targetZ-1,targetA).each(function(){
					var offsetL = Math.ceil(($(this).index()+1)/T-1);
					var offsetT = $(this).index()-offsetL*T;
					if(!isIE6){
						$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutCirc");
					}else{
						$(this).css({left:offsetT*W,top:offsetL*H});
					}
				});
			}
			//定义item
			$item = $id.find(".inner .item");
			length = $item.length;
			L = Math.ceil(length/num);
		}
		$("#con1").html("x:"+x+" / y:"+y);////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		$("#con2").html("currentL:"+currentL+" / currentT:"+currentT);////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		$("#con3").html("offsetX:"+offsetX+" / offsetY:"+offsetY);////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		$("#con4").html("target:"+target);////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		$("#con5").html("current:"+current);//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		$("#con6").html(targetA+" to "+targetZ);//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
}).mouseup(function(){
	if(moveDown){
		moveDown = false;
		$item.css({position:"static"}).removeAttr("style");
		$hand.animate({left:$null.position().left,top:$null.position().top},100,"easeOutCirc");
		setTimeout(function(){
			$hand.css({position:"static",opacity:1}).removeAttr("style").removeClass("hand");
			$null.replaceWith($hand);
			lock=0;
		},100);
	}
});	

//删除方法，可外部调用
$.dragSort.delItem = function(index){
	$item = $id.find(">.inner>.item");
	length = $item.length;
	L = Math.ceil(length/num);
	$id.find(">.inner").height(L*H);
	$item.each(function(){
		//当前行
		var thisL = Math.ceil(($(this).index()+1)/T-1);
		//当前列
		var thisT = $(this).index()-thisL*T;
		$(this).css({position:"absolute",left:thisT*W,top:thisL*H});
	});
	$item.slice(index+1,length).each(function(){
		var offsetL = Math.ceil(($(this).index()+1)/T-1);
		var offsetT = $(this).index()-offsetL*T;
		if(offsetT>0){
			offsetT-=1;
		}else{
			offsetT+=2;
			offsetL-=1;
		}
		//移动到目标位置动作
		if(!isIE6){
			$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutCirc");
		}else{
			$(this).css({left:offsetT*W,top:offsetL*H});
		}
	});
	//定义item
	setTimeout(function(){
		$item = $id.find(">.inner>.item");
		length = $item.length;
		L = Math.ceil(length/num);
		$id.find(">.inner").height(L*H);
		$item.css({position:"static"}).removeAttr("style");
		//重设右栏高度
		$(".body .conRight").css({height : $(".body .conShareOuter").height() - 8 + "px" });
	},300);
}

}})(jQuery);




$(function(){
	//调用拖动功能
	$.dragSort("#imagesList",2);
	
	var sharePage = {
		init : function() {
			$("#imgLayout>.inner>.album>.item").live("click", sharePage.onChooseLayout);
			$("#imagesList>.inner>.item>.inner>.close").live("click", sharePage.delImg);
			$(".conRight>.conClass>.inner>ul>li").live("click", sharePage.toggleChecked);
			$(".conRight>.conPublic>.ploy").live("click", sharePage.checkboxChecked);
			$(".conRight>.conPublic>.ploy>input").live("click", sharePage.checkboxChecked);
			$(".conRight>.conPublic>.ishome").live("click", sharePage.checkboxIshomeChecked);
			$(".conRight>.conPublic>.ishome>input").live("click", sharePage.checkboxIshomeChecked);
			
			$(".conWorkTabs>.imgBtn").live("click", sharePage.workShowImages);
			$(".conWorkTabs>.musicBtn").live("click", sharePage.workShowMusic);
			$(".conWorkTabs>.videoBtn").live("click", sharePage.workShowVideo);
			
			//判断发布模式
			var indexis = location.href.lastIndexOf("#")+1;
//			var shareType = location.href.substr(indexis,5);
			var showType;
//			var showTitle;
//			switch(shareType){
//				case "image" :	showType = "shareImages"; 	showTitle = "发布图片"; break;
//				case "music" :	showType = "shareMusic"; 	showTitle = "发布音乐"; break;
//				case "video" :	showType = "shareVideo";  	showTitle = "发布视频"; break;
//				case "works" :	showType = "shareWorks";  	showTitle = "发布作品"; sharePage.workType(); break;
//				default		 :	showType = "shareText"; 	showTitle = "发布文字"; break;
//			}
//			//定义页面Title
//			$(".conLeftMiddle").addClass(showType).find(".pageTitle>h1").text(showTitle);
			//定义侧栏高度
			sharePage.resize();
		},
		delImg : function() {
			$(this).parents(".item").remove();
			if($("#imagesList>.inner>.item").length == 0) {
				$("#imagesList").hide();
			}
			$.imageUploader.selectLayout();
		},
		onChooseLayout : function(){
			$(this).addClass("current").parent(".album").find(".item").not($(this)).removeClass("current");
		},
		resize : function() {
			$(".body .conRight").css({height : $(".body .conShareOuter").height() - 8 + "px" });
		},
//		workType : function() {
//			$(".conWorkTabs>.imgBtn").live("click", sharePage.workShowImages);
//			$(".conWorkTabs>.musicBtn").live("click", sharePage.workShowMusic);
//			$(".conWorkTabs>.videoBtn").live("click", sharePage.workShowVideo);
//		},
		workShowImages : function() {
			$(".shareWorks>.pageInner>.conAddImages").show();
			$(".shareWorks>.pageInner>.conShareMusic").hide();
			$(".shareWorks>.pageInner>.conShareVideo").hide();
			$(".conWorkTabs .imgBtn").addClass("current").siblings().removeClass("current");
		},
		workShowMusic : function() {
			$(".shareWorks>.pageInner>.conAddImages").hide();
			$(".shareWorks>.pageInner>.conShareMusic").show();
			$(".shareWorks>.pageInner>.conShareVideo").hide();
			$(".conWorkTabs .musicBtn").addClass("current").siblings().removeClass("current");
		},
		workShowVideo : function() {
			$(".shareWorks>.pageInner>.conAddImages").hide();
			$(".shareWorks>.pageInner>.conShareMusic").hide();
			$(".shareWorks>.pageInner>.conShareVideo").show();
			$(".conWorkTabs .videoBtn").addClass("current").siblings().removeClass("current");
		},
		workAppenditem : function(type) {
			var css;
			if (type == "music") {
				css = "itemMusic";
			} else {
				css = "itemVideo";
			}
			$("#imagesList>.inner").append("<div class='item itemComplete " + css + " uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='img'><div class='mask'><div class='ico png'></div></div><img src='http://u1.tdimg.com/6/20/178/110431532873914467690241287072355457216.jpg' /></div><textarea></textarea></div></div>").show();
			$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
			sharePage.resize();
		},
		toggleChecked : function() {
			if(!$(this).hasClass("onRead")){
				if ($(this).hasClass("current")) {
					$(this).removeClass("current");
					var text = $(this).children("a").html();
				} else {
					$(this).addClass("current");
				}
			}
		},
		checkboxChecked:function(){
			if($("#cr-cb-ploy").attr("checked"))
				$("#cr-cb-ploy").attr("checked",false);
			else
				$("#cr-cb-ploy").attr("checked",true);
		},
		checkboxIshomeChecked:function(){
			if($("#cr-cb-ishome").attr("checked"))
				$("#cr-cb-ishome").attr("checked",false);
			else
				$("#cr-cb-ishome").attr("checked",true);
		}
	}
	sharePage.init();
	
	
	var userList = {
		init : function(){
			$("#userList>.input>.open").live("click", userList.toggleOpen);
			$("#userList>.input>.inner>span>a").live("click", userList.eachDelete);
			$("#userList>.lists>.inner>a").live("click", userList.userClick);
			$("#userList").live("mouseleave", userList.rollOut);
			$("#userList").live("mouseenter", userList.rollOver);
			if (isIE6) {
				$("#userList").css({position : "static"});
			}
		},
		toggleOpen : function() {
			if (!userList.isOpen) {
				userList.isOpen = true;
				if (isIE6) {
					$("#userList").css({position : "relative"});
				}
				
				var u_condition = {};
				var u_url=SystemProp.appServerUrl+"/sns/public-sns!userList.action";
				
				$.ajax({
					url : u_url,
					type : "POST",
					dataType:'json',
					data : u_condition,
					success: function(rs){
						if(rs.code==200){
							$("#userlistshow").html('');
							for(var i=0;i<rs.data.userlist.length;i++ ){
								var u=rs.data.userlist[i];
								var imgsrc;
								if(u.avatar!=null && u.avatar!='')
								 imgsrc = u.avatar.replace(u.userId+"_","60_"+u.userId+"_");
								else
									imgsrc=SystemProp.staticServerUrl+"/v3/images/head60.gif";
								var html="";
								var isc=0;
								$("#creativeuser").find("span").each(function(){
									if($(this).attr("user")==u.userId)
										isc=1;
								});
								if(u.reserve==null || u.reserve==''){
									html="<a user='"+u.userId+"'  href=\"javascript:void(0);\" class=\"clearFix";
									if(isc==1)
										html+=" checked";
									html+="\"><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"'  /><strong>"+u.nickname+"</strong><p></p><sub></sub></a>";
								}else{
									html="<a user='"+u.userId+"'  href=\"javascript:void(0);\" class=\"clearFix";
									if(isc==1)
										html+=" checked";
									html+="\"><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"'  /><strong>"+u.nickname+"</strong><p>杂志编辑</p><sub></sub></a>";
								}
								$("#userlistshow").append(html);
							}
							
							$("#userList>.lists").stop(true,true).fadeIn(200);
							userList.resize();
						}else{
						}
					}
				});
				
			} else {
				userList.isOpen = false;
				$("#userList>.lists").stop(true,true).fadeOut(200);
				if (isIE6) {
					setTimeout(function(){
						$("#userList").css({position : "static"});
					},200);
				}
			}
		},
		rollOut : function() {
			userList.t = setTimeout(function(){
				if (userList.isOpen) { userList.toggleOpen()};
			},1000);
		},
		rollOver : function() {
			clearTimeout(userList.t);
		},
		userClick : function() {
			if (!$(this).hasClass("checked")) {
				$(this).addClass("checked");
				$("#userList>.input>.inner").append("<span user='" + $(this).attr("user") + "' un='"+$(this).attr("un") +"' nick='"+$(this).attr("nick")+"' >" + $(this).find("strong").html() + "<a href='javascript:void(0);'></a></span>");
				if (!$("#userList>.input>.open").hasClass("open2")) {
					$("#userList>.input>.open").addClass("open2");
				}
				userList.userid ++;
			} else {
				$("#userList>.input>.inner>span[user=" + $(this).attr("user") + "]").remove();
				$(this).removeClass("checked").removeAttr("user");
				if ($("#userList>.input>.inner>span").length == 0) {
					$("#userList>.input>.open").removeClass("open2");
				}
			}
			userList.resize();
		},
		eachDelete : function() {
			$("#userList>.lists>.inner>a[user=" + $(this).parent("span").attr("user") + "]").removeClass("checked").removeAttr("user");
			$(this).parent("span").remove();
			if ($("#userList>.input>.inner>span").length == 0) {
				$("#userList>.input>.open").removeClass("open2");
			}
			userList.resize();
		},
		resize : function() {
			$("#userList>.lists").css({top : $("#userList>.input").height() + 13});
			sharePage.resize();
		},
		t : null,
		isOpen : false,
		userid : 0
	}
	userList.init();
	
	$(".addtags").unbind('click').click(function(e){
		e.preventDefault();
		
		var tag=$("#addtags").val();
		if(tag!="添加新标签"){
			var tvl=0;
			$('#tags>li').each(function() {if ($(this).find("a").html()==tag)tvl=1;});
			if(tvl==0) $("#addcurrent").prev().after("<li class='current'><a href='javascript:void(0);'>"+tag+"</a></li>");
			else alert("已经存在标签："+tag);
			$("#addtags").val('');
		}
	});
	$("#addtags").live("keyup",function(event){
		if (event.keyCode == '13') {
		    event.preventDefault();
		    var tag=$("#addtags").val();
			if(tag!="添加新标签"){
				var tvl=0;
				$('#tags>li').each(function() {if ($(this).find("a").html()==tag)tvl=1;});
				if(tvl==0) $("#addcurrent").prev().after("<li class='current'><a href='javascript:void(0);'>"+tag+"</a></li>");
				else alert("已经存在标签："+tag);
				$("#addtags").val('');
			}
	   }
	});
	
	
	var ajaxUrl = SystemProp.appServerUrl+"/sns/creative!magazine.action";
	
	$("#cr-text-magazineUrl").live("blur", function(){
		var rex=/^.*www.magme.com\/publish\/mag-read.action\?.*=\d*$/;
		var uv=$.trim($(this).val());
		if(rex.test(uv)){
			var condition = {"magazineUrl":uv};
			$.ajax({
				url : ajaxUrl,
				type : "POST",
				dataType:'json',
				data : condition,
				success: function(rs){
					if(rs.code==200){
						$("#magazineName").val(rs.data.magazineName);
						$("#issueid").val(rs.data.issueid);
						$("#publicationid").val(rs.data.publicationid);
						
						$("#magazineUrlError").hide();
						$(".friends").show();
					}else{
						$("#magazineName").val('');
						$("#issueid").val('');
						$("#publicationid").val('');
						$("#creativeuser").html('');
						$(".friends").hide();
						alert("杂志链接错误");
						
					}
				}
			});
		}
		else if(uv!=''){
			$("#magazineName").val('');
			$("#issueid").val('');
			$("#publicationid").val('');
			$("#creativeuser").html('');
			$(".friends").hide();
			alert("抱歉，杂志链接错误并且只支持本站中杂志链接");
		}
			
	});
	
	// ascii 处理
	function ascii2native(strAscii) { 
	     var output = ""; 
	     var posFrom = 0; 
	     var posTo = strAscii.indexOf("\\u", posFrom); 
	     while (posTo >= 0) { 
	         output += strAscii.substring(posFrom, posTo); 
	         output += toChar(strAscii.substr(posTo, 6)); 
	         posFrom = posTo + 6; 
	         posTo = strAscii.indexOf("\\u", posFrom); 
	     } 
	     output += strAscii.substr(posFrom); 
	     return output; 
	} 
	function toChar(str) { 
	     if (str.substr(0, 2) != "\\u") return str; 

	     var code = 0; 
	     for (var i=2; i<str.length; i++) { 
	         var cc = str.charCodeAt(i); 
	         if (cc >= 0x30 && cc <= 0x39) 
	             cc = cc - 0x30; 
	         else if (cc >= 0x41 && cc <= 0x5A) 
	             cc = cc - 0x41 + 10; 
	         else if (cc >= 0x61 && cc <= 0x7A) 
	             cc = cc - 0x61 + 10; 
	         
	         code <<= 4; 
	         code += cc; 
	     } 
	    
	     return String.fromCharCode(code); 
	}
	//更改视频图片
	$("input[changePic='']").unbind("change").live("change",function(){
		var filename=$(this).val();
		if(!filename){
			return;
		}
		var type=filename.substring(filename.indexOf(".")).toLowerCase();
		if(!type || (type!='.jpg' && type!='.jpeg' && type!='.pjpeg' && type!=".gif" && type!=".png") ){
			alert("不支持的文件类型，支持jpg,jpeg,pjpeg,gif,png!");
			return;
		}
		$("input[changePic='']").each(function(){
			$(this).removeAttr("id");
		});
		$(this).attr("id","changePicFile");
		var randomKey=Math.random();
		$(this).attr("changePicFileRandom","changePicFile"+randomKey);
		$.ajaxFileUpload(
	            {
	                url : SystemProp.appServerUrl+"/sns/creative-img!changePic.action?type="+type,
	                secureuri : false,
	                fileElementId : "changePicFile",
	                dataType : "json",
	                async : true,
	                type : 'POST',
	                success : function(rs){
	                	if(!rs) return;
	                	if(rs.code != 200){
	                		alert("更换图片失败");
	                	}else{
	                		$("input[changePicFileRandom='changePicFile"+randomKey+"']").parent().parent().parent().attr("picurl",rs.data.path);
		                	$("input[changePicFileRandom='changePicFile"+randomKey+"']").parent().next().find("img").attr("src",rs.data.path);
	                	}
	                },
	                //服务器响应失败处理函数
	                error : function (data, status, e) {
	                	alert("更换图片失败");
	                }
	            }
	        );
	});
	
	
	// ascii 处理 结束
	
	var cr_video_url;
	$("#cr_video_url").live("blur", function(){
		if($(this).val()!=''){
			var _url = SystemProp.appServerUrl+"/sns/video.action";
			var opval=$("#cr_operate").val();
			var uv=$.trim($(this).val());
			var condition = {"video":uv};
			if(cr_video_url!=uv){
				cr_video_url=uv;
				$.ajax({
					url : _url,
					type : "POST",
					dataType:'json',
					data : condition,
					success: function(rs){
						if(rs.code==200){
							$("#cr_video_error").hide();
							var title=ascii2native(rs.data.title);
							if(rs.data==null){
								alert('不支持的视频地址');
							}else{
								if(opval=='works'){
									$("#cr_video_error_sp").hide();
									$("#imagesList").show();
									$(".conShareImages>.imagesCon").show();
									$("#imagesList>.inner").append("<div picUrl='"+rs.data.picUrl+"' play='"+rs.data.playerUrl+"' class='item itemComplete itemVideo uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><a class='btnGS' href='javascript:void(0);'><span>更换图片</span><input changePic='' type='file' class='inputFile' name='changePicFile'/></a><div class='img'><div class='mask'><div class='ico png'></div></div><img style='padding: 30px 0 0 20px;' src='"+rs.data.picUrl+"' /></div><textarea>"+title+"</textarea></div></div>").show();
									$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
									sharePage.resize();
								}else{
									$("#cr_video_error").hide();
									$(".myVideo").attr("picUrl",rs.data.picUrl);
									$(".myVideo").attr("title",title);
									$(".myVideo").attr("play",rs.data.playerUrl);
									$(".myVideo").show();
									$(".myVideo").find("img").attr("src",rs.data.picUrl);
									$(".myVideo").find("p").html(title);
								}
							}
							
						}else{
							if(opval=='works'){
								alert('不支持的视频地址');
							}else{
								alert('不支持的视频地址');
							}
							
						}
					}
				});
			}
		}else{
			if(cr_video_url!=''){
				$(".myVideo").removeAttr("picurl");
				$(".myVideo").removeAttr("title");
				$(".myVideo").removeAttr("play");
				$(".myVideo").hide();
				$(".myVideo").find("img").removeAttr("src");
				$(".myVideo").find("p").html('');
				cr_video_url=$(this).val();
			}
			$("#cr_video_error").hide();
		}
	});
	$(".myVideo>.close").live("click", function(){
		$("#cr_video_url").val('');
		$(".myVideo").removeAttr("picurl");
		$(".myVideo").removeAttr("title");
		$(".myVideo").removeAttr("play");
		$(".myVideo").hide();
		$(".myVideo").find("img").removeAttr("src");
		$(".myVideo").find("p").html('');
		cr_video_url='';
		$("#cr_video_error").hide();
	});
	
});

