<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<#import "../components/gap.ftl" as gap>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelSns.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>

<script>
$(function(){
	$('#kanmiWall').masonry({itemSelector: '.item'});
	//用户图片杂志页鼠标移入头像时显示功能菜单------------
	$(".body>.itemUser>.userInfo").hover(function(){
		$(this).find(".tool>.inner").css({opacity:0}).stop(true,true).animate({marginTop:0,opacity:1},250);
	},function(){
		$(this).find(".tool>.inner").stop(true,true).animate({marginTop:-100,opacity:0},250);
	});
	//加为好友
	$("#addFriend").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能添加好友哦！");
			return;
		}
		var userId = $(this).attr("userId");
		ajaxAddFollow(userId,1,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				$("#addFri").hide();
				$("#removeFri").show();
				alert("操作成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	//sendMsg-------------------------------------
	$("#sendMsgBtn").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能发送消息！");
			return;
		}
		$("#userNewMsg").fancybox();
	});
	$("#closePop").click(function(e){e.preventDefault();$.fancybox.close();});
	$("#send").click(function(e){
		e.preventDefault();
		var userId = $(this).attr("userId");
		var content = $("#msgContent").val();
		if(!content || content === '请输入消息内容'){
			alert('请输入消息内容');
			return;
		}
		//1-->send to user
		ajaxSendMsg(userId,1,content,function(rs){
			if(!rs) return;
			var code = rs.code;
			if(code == 200){
				$.fancybox.close();
				alert("发送成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
});
</script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>

<!--header-->
<@header.main searchType="User"/>

<!--body-->
<div <#if tab??&&tab=="friend">class="body noBg bodyUser fullScreen clearFix"<#elseif tab??&&tab=="enjoyIssue">class="body noBg bodyMagazine fullScreen clearFix" id="userCenterWall"<#else>class="body noBg bodyKanmi fullScreen clearFix" id="kanmiWall"</#if>>
  <div class="titleBar clearFix"><h2><span>${(visitUser.nickName)!""}</span>的全部<#if tab??&&tab=="friend">好友<#elseif tab??&&tab=="enjoyIssue">杂志<#else>图片</#if></h2></div>
  <div class="item itemUser clearFix">
    <div class="userInfo"> <strong class="name">${(visitUser.nickName)!""}</strong> <a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${(visitUser.id)!"0"}" class="img"><img src="<#if ((visitUser.avatar)??)&&(visitUser.avatar!="")>${systemProp.profileServerUrl+visitUser.avatar60}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" /></a>
      <ul class="atten png clearFix">
        <li <#if tab??&&(tab=="enjoyImage" || tab=="userImage")>class="current"</#if> ><a href="${systemProp.appServerUrl}/user-visit!enjoyImage.action?userId=${userId}"><strong>${(visitUser.statsMap.enjoyImageNum)!"0"}</strong><span>图片</span></a></li>
        <li <#if tab??&&tab=="enjoyIssue">class="current"</#if>><a href="${systemProp.appServerUrl}/user-visit!enjoyIssue.action?userId=${userId}"><strong>${(visitUser.statsMap.enjoyIssueNum)!"0"}</strong><span>杂志</span></a></li>
        <li <#if tab??&&tab=="friend">class="current"</#if>><a href="${systemProp.appServerUrl}/user-visit!friend.action?userId=${userId}"><strong>${(visitUser.statsMap.friendNum)!"0"}</strong><span>好友</span></a></li>
      </ul>
      <#if (!session_user??)||(session_user??&&userId??&&userId!=session_user.id)>
      <div class="tool clear">
        <div class="inner">
        	<a id="sendMsgBtn" userId="${(visitUser.id)!""}" class="message" href="javascript:void(0)">发消息</a>
        	<a id="addFriend" userId="${(visitUser.id)!""}" class="atten" href="javascript:void(0)">加好友</a>
        </div>
      </div>
      </#if>
      <input type="hidden" id="isVisitor" value="1" />
    </div>
    
    <#if tagList??>
	<div class="tagList">
    	<ul class="clearFix">
        	<li class="title"><a href="javascript:void(0)">标签分类</a></li>
	    	<#list tagList as tag>
	    		<li class="<#if tagName??&&(tagName==tag.name)>current</#if>"><a href="${systemProp.appServerUrl}/user-visit!${tab}.action?userId=${userId}&tagName=${encode(tag.name)}">${tag.name}</a></li>
	    	</#list>        	
        </ul>
    </div>
    </#if>     
  </div>
    
	${body} 
</div>

<!--footer-->
<@footer.main class="footerMini footerStatic"/>
<div id="loading" class="pageLoad"><span>正在加载内容...</span></div>
<div id="userNewMsg" class="popContent hide">
	<fieldset>
    	<h6>发送私信给<span name="userName">${(visitUser.nickName)!""}</span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="${(visitUser.id)!""}" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>
</body>
</html>