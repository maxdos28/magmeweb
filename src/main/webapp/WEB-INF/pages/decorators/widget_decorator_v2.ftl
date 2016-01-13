<#import "../components/mobilefooter.ftl" as mobilefooter>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if lt IE 7 ]> <html class="ie6" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html xmlns="http://www.w3.org/1999/xhtml"> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>麦米网|Magme</title>
<link href="${systemProp.staticServerUrl}/v3/widget/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/base.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/pop.css" rel="stylesheet" type="text/css" />

<!--[if lt IE 7]>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/widget/js/DD_belatedPNG_0.0.8a-min.js" type="text/javascript"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<#if widget_auth??>
<script>
	var xx='${widget_auth.type!}';
	function getX(){
		return xx;
	}
</script>
</#if>
<#if widget_auth??&&widget_auth.type=='renren'>
<script src="${systemProp.staticServerUrl}/widget/js/renren.js"></script>
<#elseif widget_auth??&&widget_auth.type=='kaixin'>
<script src="${systemProp.staticServerUrl}/widget/js/kaixin.js"></script>
<#elseif widget_auth??&&widget_auth.type=='sina'>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js" language="JavaScript"></script>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=${widget_auth.apiKey}" type="text/javascript" charset="utf-8"></script>
<#elseif widget_auth??&&widget_auth.type=='taobao'>
<script src="http://a.tbcdn.cn/apps/stargate/ac/js/proxy.js"></script>
<#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??&&(widget_auth.subType=='qzone'||widget_auth.subType=='pengyou')>
<script src="http://fusion.qq.com/fusion_loader?appid=${widget_auth.apiID}&platform=${widget_auth.subType}" type="text/javascript" charset="utf-8" ></script>
<#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??&&widget_auth.subType=='weibo'>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="http://mat1.gtimg.com/app/vt/public/openjs/latest.js"></script>
</#if>


</head>
<body>

<div class="main">

<!--header-->
<div class="header clearFix">
	<div class="outer">
        <div class="inner">
            <h1 class="logo" id="logo"><a class="png" href="javascript:void(0)" title="无处不悦读">麦米网|Magme</a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
                <li <#if pageTitle?? && pageTitle=="magzine" > class="current" </#if>><a href="/widget/widget!magzineShow.action<#if widget_auth??>?xx=${widget_auth.type}<#if widget_auth.subType??>&yy=${widget_auth.subType}</#if></#if>">杂志阅读</a></li>
                <li <#if pageTitle?? && pageTitle=="newEvent" > class="current" </#if>><a href="/widget/widget!newEvent.action<#if widget_auth??>?xx=${widget_auth.type}</#if>">内容精选</a></li>
            </ul>
            
                <#if widget_auth??&&(widget_auth.type=='renren'||(widget_auth.type=='kaixin'&&widget_auth.sessionKey??))>
			    <a id="invite" class="btnOS floatr" href="javascript:void(0)">邀请</a>
			    <a id="like" class="btnOS floatr btnlike" href="javascript:void(0)">喜欢</a>
			    
			    <#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??&&widget_auth.subType!='qplus' >
			    <a id="invite" class="btnOS floatr" href="javascript:void(0)">邀请</a>
			    <a id="share" class="btnOS floatr btnlike" href="javascript:void(0)">分享</a>
			
			    <#elseif widget_auth??&&widget_auth.type=='sina'>
	            <a id="magmeWeibo" href="http://weibo.com/magme" target="_blank" class="floatr"><img src="${systemProp.staticServerUrl}/v3/widget/images/magme_weibo.gif" /></a>
	            <a id="shareToWeibo" href="javascript:void(0)" class="floatr"><img src="${systemProp.staticServerUrl}/v3/widget/images/weibo_publish_button_24.gif" /></a>
                </#if>
            
            <!--search-->
            <div class="search floatr clearFix"><input type="text" name="queryStr" <#if queryStr?? && queryStr!=''> value="${queryStr}"</#if> class="in" /><input id="searchBtn" type="button" class="btn" /></div>
        </div>
    </div>
</div>

<!--body-->
<div class="body pageWidget clearFix">
    <!--conTagWall-->
	<div class="outer">
	<!--topBar-->
	<div class="topBar" id="topBar">
	    <div class="categoryList">
	        <a href="/widget/widget!<#if pageTitle?? && pageTitle=='newEvent' >newEvent<#else>magzineShow</#if>.action<#if widget_auth??>?xx=${widget_auth.type}<#if widget_auth.subType??>&yy=${widget_auth.subType}</#if></#if>" <#if !sortId??>class="current"</#if>>全部</a>
	        <#if sortList??>
	        <#list sortList as sort>
	            <#if sort_index lt 18>
	            <a href="/widget/widget!<#if pageTitle?? && pageTitle=='newEvent' >newEvent<#else>magzineShow</#if>.action?sortId=${sort.id}<#if widget_auth??>&xx=${widget_auth.type}<#if widget_auth.subType??>&yy=${widget_auth.subType}</#if></#if>" <#if sortId??&&sortId=sort.id>class="current"</#if>>${sort.name}</a>
	            </#if>
	        </#list>
	        </#if>
	    </div>
	</div>
		${body}
    </div>
</div>

<#if widget_auth?? && widget_auth.type=='tencent' >
<p style="text-align:right; margin-top:10px; padding-top:8px; border-top:1px solid #eee;">
本应用由上海居冠软件(麦米网)开发，您在使用过程中遇到问题请联系我们&nbsp;&nbsp;技术支持：麦米网（www.magme.com）
</p>
</#if>

</div>

<#if widget_auth??&&widget_auth.type=='baidu'>
    <script src="http://app.baidu.com/static/appstore/monitor.st"></script>
</#if>
</body>

<script type="text/javascript">

function bindHover(){
	
	$(".item").hover(
	function(){
		$(this).find("img").fadeTo(300,0.7);
	},function(){
		$(this).find("img").stop(true,true).fadeTo(300,1);
	})	
}

//function getUrlValue(name)
function getUrlValue(name){
	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
	if (r!=null) return unescape(r[2]); return null;
}

$(function(){
    bindHover();
	
	$("#searchBtn").live('click',function(e){
		e.preventDefault();
		var queryStr = $("input[name=queryStr]").val();
		if(!!queryStr && $.trim(queryStr)!="" ){
			window.location.href="/widget/widget-search.action?queryStr="+encodeURIComponent(queryStr) + "&xx=" + xx;
		}
		else{
		    alert("输入不能为空");
		}
	});

	$("input[name=queryStr]").unbind('keyup').live('keyup',function(e){
		e.preventDefault();
		if(e.keyCode == 13){
			$("#searchBtn").click();
		}
	});
	
	<#if widget_auth??&&widget_auth.type=='renren'>
	$("#invite").live('click',function(){
		showDialog('http://widget.renren.com/dialog/request?display=iframe&selector_mode=naf&actiontext=+&app_msg=%e6%8d%ae%e8%af%b4%ef%bc%8c%e9%9f%a9%e5%af%92%e5%8f%aa%e9%98%85%e8%af%bb%e6%9d%82%e5%bf%97%e5%92%8c%e6%8a%a5%e7%ba%b8%e3%80%82%e8%bf%99%e4%b9%88%e5%a5%bd%e7%9a%84%e5%ba%94%e7%94%a8%ef%bc%8c%e6%95%b4%e4%b8%aa%e5%ba%94%e7%94%a8%e4%b8%ad%e5%bf%83%e7%8b%ac%e6%ad%a4%e4%b8%80%e5%ae%b6%ef%bc%9b%e6%8e%a8%e8%8d%90%e7%bb%99%e4%bd%a0%ef%bc%8c%e5%bf%ab%e6%9d%a5%e8%af%95%e8%af%95%ef%bc%81&app_id=${widget_auth.apiID}&accept_url=http%3a%2f%2fapps.renren.com%2fmagzine&accept_label=%e8%bf%9b%e5%85%a5%e5%ba%94%e7%94%a8','邀请');
		if($ma){
			var url = "/third/invite?type=${widget_auth.type}";
			$ma(url,"0");
		}
	    return false;
	});
	
	$("#like").live('click',function(){
		showDialog('http://widget.renren.com/dialog/like?redirect_url=http://apps.renren.com/magzine&like_url=http%3a%2f%2fapps.renren.com%2fmagzine','信息');
		if($ma){
			var url = "/third/share?type=${widget_auth.type}";
			$ma(url,"0");
		}
	    return false;
	});
	<#elseif widget_auth??&&widget_auth.type=='kaixin'&&widget_auth.sessionKey??>
	$("#invite").live('click',function(){
		var date=new Date();
		var call_id=Date.UTC(date.getYear(),date.getMonth()+1,date.getDay(),date.getHours(),date.getMinutes(),date.getSeconds(),date.getMilliseconds());
		
		var para_api_key="api_key=${widget_auth.apiKey}";
		var para_call_id="call_id="+call_id;
		var para_method="method=actions.sendInvitation";
		var para_session_key="session_key=${widget_auth.sessionKey}";
		var para_text="text=";
		var para_v="v=1.2";
		
		var sig = hex_md5(para_api_key+para_call_id+para_method+para_session_key+para_text+para_v+"${widget_auth.secretKey}");
		var para_sig="sig="+sig;
		//para_text="text=%C4%E3%BA%C3";
		
		var url=para_api_key+"&"+para_call_id+"&"+para_method+"&"+para_session_key+"&"+para_text+"&"+para_v+"&"+para_sig;
		showKxDialog(base64AndRep(url));
		if($ma){
			var url = "/third/invite?type=${widget_auth.type}";
			$ma(url,"0");
		}
	    return false;
	});
	
	$("#like").live('click',function(){
		var date=new Date(); 
		var call_id=Date.UTC(date.getYear(),date.getMonth()+1,date.getDay(),date.getHours(),date.getMinutes(),date.getSeconds(),date.getMilliseconds()); 
		
		var para_api_key="api_key=${widget_auth.apiKey}";
		var para_call_id="call_id="+call_id;
		var para_link="link=http://www.kaixin001.com/!app_magzine/";
		var para_linktext="linktext=";
		var para_method="method=actions.sendNewsFeed";
		var para_pic="pic=${systemProp.staticServerUrl+"/v3/widget/images/80_80_round.gif"}";
		var para_session_key="session_key=${widget_auth.sessionKey}";
		var para_text="text=";
		var para_v="v=1.2";
		var sig = hex_md5(para_api_key+para_call_id+para_link+para_linktext+para_method+para_pic+para_session_key+para_text+para_v+"${widget_auth.secretKey}");
		var para_sig="sig="+sig;
		
		var url=para_api_key+"&"+para_call_id+"&"+para_link+"&"+para_linktext+"&"+para_method+"&"+para_pic+"&"+para_session_key+"&"+para_text+"&"+para_v+"&"+para_sig;
		
		showKxDialog(base64AndRep(url));
		if($ma){
			var url = "/third/share?type=${widget_auth.type}";
			$ma(url,"0");
		}
	    return false;
	});	
	
	setHeight();
	
	<#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??&&(widget_auth.subType=='qzone'||widget_auth.subType=='pengyou')>

	$("#invite").live('click',function(){
		fusion2.dialog.invite
		({

		  // 可选，微博平台不可使用该参数。邀请理由，最长不超过35个字符。若不传则默认在弹框中显示"这个应用不错哦，跟我一起玩吧！"
		  msg  :"据说，韩寒只阅读杂志和报纸。应用中心独此一家！推荐给你，快来试试！"

		  // 可选，微博平台不可使用该参数。邀请配图的URL，图片尺寸最大不超过120*120 px。若不传则默认在弹框中显示应用的icon
		  //img :"http://qzonestyle.gtimg.cn/qzonestyle/act/qzone_app_img/app353_353_75.png",

		  // 可选。透传参数，用于onSuccess回调时传入的参数，用于识别请求
		  //context :"invite",

		  // 可选。用户操作后的回调方法。
		  //onSuccess : function (opt) {  alert("邀请成功" + opt.context); },

		  // 可选。用户取消操作后的回调方法。
		  //onCancel : function () { alert("邀请取消"); },

		  // 可选。对话框关闭时的回调方法。
		  //onClose : function () {  alert("邀请关闭"); }

		});
		if($ma){
			var url = "/third/invite?type=${widget_auth.type}";
			$ma(url,"0");
		}
		
	});		
	
	$("#share").live('click',function(){
		fusion2.dialog.share
		({
		  // 可选。分享应用的URL，点击该URL可以进入应用，必须是应用在平台内的地址。
		  url:  "<#if widget_auth.subType=='pengyou'>http://apps.pengyou.com/${widget_auth.apiID}<#elseif widget_auth.subType=='qzone'>http://rc.qzone.qq.com/myhome/${widget_auth.apiID}</#if>",

		  // 可选。默认展示在输入框里的分享理由。
		  desc:"据说，韩寒只阅读杂志和报纸。这么好的应用，整个应用中心独此一家！推荐给你，快来试试！",

		  // 必须。应用简要描述。
		  summary :"三天不读书，智商不如猪！有品位的人都在这里看杂志！《麦米杂志阅读》，海量正版杂志满足您！",

		  // 必须。分享的标题。
		  title :"麦米杂志阅读"

		  // 可选。透传参数，用于onSuccess回调时传入的参数，用于识别请求。
		  //context:"share",

		  // 可选。用户操作后的回调方法。
		  //onSuccess : function (opt) {  alert("分享成功！");  },

		  // 可选。用户取消操作后的回调方法。
		  //onCancel : function () { alert("Cancelled: " + opt.context);  },

		  // 可选。对话框关闭时的回调方法。
		  //onClose : function () { alert("Closed") }

		});
		if($ma){
			var url = "/third/share?type=${widget_auth.type}";
			$ma(url,"0");
		}
	});	
	
	<#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??&&widget_auth.subType=='weibo'>
	T.init({ 
	    appkey: "${widget_auth.apiKey}"
	});
	
	$("#invite").live('click',function(){
//		var status = T.loginStatus();
//		if (status) {
//		    //用户已登录
			frameInfo = '<iframe id="qqInviteFrm" width="680" height="450" scrolling="no" src="http://app.t.qq.com/invite/index?appid=${widget_auth.apiID}&msg=据说，韩寒只阅读杂志和报纸。这么好的应用，整个应用中心独此一家；推荐给你，快来试试！~"></iframe>';
			$("#qqInviteFrm").remove();
			$(frameInfo).appendTo($("body"));
			var qqInviteDialog = $("#qqInviteFrm");
			qqInviteDialog.fancybox();
//		} else {
//			//用户未登录
//		    alert("请先登录腾讯微博！");
//		}
			if($ma){
				var url = "/third/invite?type=${widget_auth.type}";
				$ma(url,"0");
			}
	})
	
	$("#share").live('click',function(){
//		var status = T.loginStatus();
//		if (status) {
//		    //用户已登录
			qqShare = '<div id="sinaWeiboFarward" class="popContent"><fieldset class="new"><div><textarea name="content" class="input">据说，韩寒只阅读杂志和报纸。《麦米杂志阅读》，推荐给你，快来试试！http://www.magme.com/widget/widget!magzine.action?xx=tencent&yy=weibo</textarea></div><div class="tRight"><a id="publishToQQ" href="javascript:void(0)" class="btnBS" >分享到微博</a></div></fieldset></div>';
			$("#sinaWeiboFarward").remove();
			$(qqShare).appendTo($("body"));
			var qqWeiboFarward = $("#sinaWeiboFarward");
			qqWeiboFarward.fancybox();
			$("#publishToQQ",qqWeiboFarward).click(function(e){e.preventDefault();publishToQQ($("#sinaWeiboFarward textarea[name=content]").val());});
//		} else {
//			//用户未登录
//		    alert("请先登录腾讯微博！");
//		}
			if($ma){
				var url = "/third/share?type=${widget_auth.type}";
				$ma(url,"0");
			}
	})

	function publishToQQ(content){
		T.api("/t/add",
		{
			content : content
		},"json","POST")
		 .success(function (response) {
		    //调用成功
		    alert("分享成功");
		    $("#fancybox-close").click();
		 })
		 .error(function (code, message){
		    //调用失败
		    alert("分享失败");
		 });
		if($ma){
			var url = "/third/share?type=${widget_auth.type}";
			$ma(url,"0");
		}
	}
	
	<#elseif widget_auth??&&widget_auth.type=='sina'&&widget_auth.sinaUid??>

	//$("#magmeWeibo").live('click',function(){
	//	window.open("http://weibo.com/magme");
	//});

	$("#shareToWeibo").live('click',function(){
		sinaShare = '<div id="sinaWeiboFarward" class="popContent"><fieldset class="new"><div><textarea name="content" class="input">三天不读书，智商不如猪。有品位的人都在这里看杂志！《麦米杂志阅读》，海量正版杂志满足你！http://t.cn/SZEinI</textarea></div><div class="tRight"><a id="publishToSina" href="javascript:void(0)" class="btnBS" >分享到微博</a></div></fieldset></div>';
		$("#sinaWeiboFarward").remove();
		$(sinaShare).appendTo($("body"));
		var sinaWeiboFarward = $("#sinaWeiboFarward");
		sinaWeiboFarward.fancybox();
		$("#publishToSina",sinaWeiboFarward).click(function(e){e.preventDefault();publishToSina($("#sinaWeiboFarward textarea[name=content]").val());});
		if($ma){
			var url = "/third/share?type=${widget_auth.type}";
			$ma(url,"0");
		}
	});	
		
	function publishToSina(content){
		WB2.anyWhere(function(W){
		    // 获取评论列表
		W.parseCMD("/statuses/upload_url_text.json", function(sResult, bStatus){
		    if(bStatus == true) {
				alert("分享成功！");
				$("#fancybox-close").click();
		    }
		    else{
		    	alert("分享失败！可能是您已经分享过了~");
		    }
		},{
			status : encodeURI(content),
			url : '${systemProp.staticServerUrl+"/v3/widget/images/80_80_round.gif"}'
		},{
		    method: 'post'
		});
		});
	}

	<#elseif widget_auth??&&widget_auth.type=='taobao'>
	crossFrame.init();
	
	</#if>
})
</script>
<@mobilefooter.main />
</html>
