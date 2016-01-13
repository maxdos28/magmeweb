<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>事件精选&nbsp;[分类名]&nbsp;麦米网Magme&nbsp;-&nbsp;世界新杂志&nbsp;发现新内容</title>
<meta name="Keywords" content="麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="麦米网提供数百种最新杂志在线免费阅读，财经，旅游，娱乐，时尚，汽车，体育，数码生活等。在麦米看书，交流，分享，交友，新的阅读生活由此开启">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/register.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script>
$(function(){
	$("fieldset input[type = text],fieldset input[type = password]").bind("focus",onFocus).bind("blur",onFocus);
	function onFocus(e) {
		if (e.type == "focus") {
			$(this).css({filter: 'alpha(opacity=100)','-moz-opacity': 1,opacity: 1});
		} else {
			if ($(this).attr("type") == "password") {
				$(this).prev().css({filter: 'alpha(opacity=80)','-moz-opacity': 0.8,opacity: 0.8});
			} else {
				$(this).css({filter: 'alpha(opacity=80)','-moz-opacity': 0.8,opacity: 0.8});
			}
		}
	}
	$(".hotUser .item").slice(1,$(".hotUser .item").length).bind("click", onUserClick);
	function onUserClick() {
		if ( $(this).hasClass("itemCurrent") ) {
			$(this).removeClass("itemCurrent");
		} else {
			$(this).addClass("itemCurrent");
		}
	}
	
	var isCk=false;
	$("#invite_code").live("blur", function(){
		var invite= $(this).val();
		if(invite=='邀请码')
			invite='';
			
		if(invite==''){
			$("#invite_code").attr("style","border: 1px solid red;");
			$("#invite_html").html('请输入M1频道邀请码');
		}else{
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/sns-index!ckInvite.action",
				type : "post",
				data : {"inviteCode":invite},
				dataType : 'json',
				success : function (rs){
					var code = rs.code;
					if(code == 200){
						var num =rs.data.num;
						if(num>0){
							$("#invite_code").attr("style","border: 1px solid none;");
							$("#invite_html").html('M1频道邀请码（可以使用）');
							isCk=true;
						}else{
							$("#invite_code").attr("style","border: 1px solid red;");
							$("#invite_html").html('M1频道邀请码（不可用或不存在）');
							isCk=false;
						}
					}
				}
			});
		}
	});
	
	
	var clickFlag=true;
	$(".btn").live("click", function(){
		if(clickFlag && isCk){
			clickFlag=false;
			var invite= $("#invite_code").val();
			if(invite=='邀请码')
				invite='';
				
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/sns-index!confirm.action",
				type : "post",
				data : {"inviteCode":invite},
				dataType : 'json',
				success : function (rs){
					var code = rs.code;
					if(code == 200){
						//toRegStep2();
						window.location.href= SystemProp.appServerUrl+"/sns/article.action";
					}else{
						$("#invite_code").attr("style","border: 1px solid red;");
						$("#invite_html").html('M1频道邀请码（不可用或不存在）');
						isCk=false;
					}
					clickFlag=true;
				}
			});
		}
	});
	
	function toRegStep2() {
		$(".loginCon").fadeOut();
		$(".regConStep2").delay(200).fadeIn(200, function(){$(".scroll-pane").jScrollPane();});
		$(".quickLinks").fadeOut();
		$(".footer>.inner").fadeOut();
	}
	
	$("#enterM1",$(".regConStep2")).click(function(){
		var ids = "";
		$("a.itemCurrent",$(".regConStep2")).each(function(){
			if(ids.length > 0) ids +="_";
			ids += $(this).attr("userid");
		});
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/user-follow!addFollowJsons.action",
			type : "post",
			data : {"ids" : ids},
			dataType : 'json',
			success : function (rs){
				var url = SystemProp.appServerUrl;
				if(!url) url = "http://www.magme.com";
				document.location.href = url + "/sns/sns-index.action";
			}
		});
	});
});
</script>
</head>
<body class="m1LoginRegPage">
    
    <div class="quickLinks">
    	<a href="http://www.magme.com" target="_self">首页</a>
    	<a href="${systemProp.appServerUrl}/sns/article.action" class="a2">返回M1</a>
    </div>
  
    <div class="loginCon" style="display: block;">
        <h1 class="logo"><a href="http://www.magme.com/" target="_blank" title="麦米网Magme">麦米网Magme</a></h1>
        <fieldset class="login">
            <legend>邀请码验证</legend>
            <div><p >需输入邀请码才能发布M1频道作品</p><input id='invite_code' type="text" tips="邀请码" /></div>
             <div><p class="error" id='invite_html'>需输入邀请码才能发布M1频道作品</p></div>
            <div><input type="button" value="确认" class="btn" /></div>
        </fieldset>
    </div>
  
   <div class="regConStep2">
        <h1 class="logo"><a href="http://www.magme.com" target="_blank" title="麦米网Magme">麦米网Magme</a></h1>
        <p>我们为您推荐了一些用户，请点击关注</p>
        <div class="hotUser">
        	<div class="scroll-pane clearFix">
        		<#if adminUserEx??>
	                <a href="javascript:void(0);" class="item itemCurrent" userid="${adminUserEx.userId}">
	                    <div class="img">
	                    	<#if adminUserEx.imagePath?? && adminUserEx.imagePath!='' >
	                    		<img src="${systemProp.profileServerUrl}${adminUserEx.imagePath}" />
	                    	<#else>
	                    		<img src="${systemProp.staticServerUrl}/images/head60.gif"/>
	                    	</#if>
	                    </div>
	                    <strong>${adminUserEx.nameZh}</strong>
	                </a>
                </#if>
                <#if userExList??>
		            <#list userExList as userEx>
		                <a href="javascript:void(0);" class="item" userid="${userEx.userId}">
		                    <div class="img">
		                    	<#if userEx.imagePath?? && userEx.imagePath!='' >
		                    		<img src="${systemProp.profileServerUrl}${userEx.imagePath}" />
		                    	<#else>
		                    		<img src="${systemProp.staticServerUrl}/images/head60.gif"/>
		                    	</#if>
		                    </div>
		                    <strong>${userEx.nameZh}</strong>
		                    <span>${userEx.office}</span>
		                </a>
	                </#list>
                </#if>
            </div>
        </div>
        <fieldset class="regStep2">
            <div><a href="javascript:void(0)" class="btn" id="enterM1">进入M1</a></div>
        </fieldset>
    </div>
    <div class="footer">
    	<div class="inner">
          
        </div>
    </div>

</body>
</html>