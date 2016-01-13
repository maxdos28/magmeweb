<#import "../components/gap.ftl" as gap>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>麦米网，杂志免费在线极速阅读平台</title>
<meta name="description" content="麦米网——电子杂志免费在线极速阅读平台。在这里看杂志，聊杂志，分享交流。阅读麦米，开启新生活！" />
<meta name="keywords" content="麦米，电子杂志，在线杂志，免费电子杂志，电子杂志下载，数码杂志，时尚杂志，汽车杂志，财经杂志" />

<link href="${systemProp.staticServerUrl}/widget/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/widget/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/widget/style/kanmi.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/widget/style/base.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>

<#if widget_auth??&&widget_auth.type=='renren'>
<link href="${systemProp.staticServerUrl}/widget/style/renren.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/widget/js/renren.js"></script>
<#elseif widget_auth??&&widget_auth.type=='kaixin'>
<link href="${systemProp.staticServerUrl}/widget/style/kaixin.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/widget/js/kaixin.js"></script>
<#elseif widget_auth??&&widget_auth.type=='sina'>
<link href="${systemProp.staticServerUrl}/widget/style/weibo.css" rel="stylesheet" type="text/css" />
<script src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js" language="JavaScript"></script>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=${widget_auth.apiKey}" type="text/javascript" charset="utf-8"></script>
<#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??>
<script src="http://fusion.qq.com/fusion_loader?appid=${widget_auth.apiID}&platform=${widget_auth.subType}" type="text/javascript" charset="utf-8" ></script>
</#if>


<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js" type="text/javascript"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->


</head>
<body>

<div class="main">
<!--header-->
<div class="header">
    <h1 class="logo">麦米magme</h1>
    <!--nav-->
    <ul id="menu" class="nav">
        <li <#if pageTitle?? && pageTitle=="magzine" > class="current" </#if>><a href="/widget/widget!magzine.action">杂志阅读</a></li>
        <li <#if pageTitle?? && pageTitle=="newTag" > class="current" </#if>><a href="/widget/widget!newTag.action">最新标签</a></li>
        <!--<li <#if pageTitle?? && pageTitle=="hotTag" > class="current" </#if>><a href="/widget/hotTag.html">最热标签</a></li>-->
    </ul>
    
    
    
    <#if widget_auth??&&(widget_auth.type=='renren'||(widget_auth.type=='kaixin'&&widget_auth.sessionKey??))>
    <a id="invite" class="btnOS floatr" href="javascript:void(0)">邀请</a>
    <a id="like" class="btnOS floatr btnlike" href="javascript:void(0)">喜欢</a>
    
    <#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType?? >
    <a id="invite" class="btnOS floatr" href="javascript:void(0)">邀请</a>
    <a id="share" class="btnOS floatr btnlike" href="javascript:void(0)">分享</a>

    <#elseif widget_auth??&&widget_auth.type=='sina'>
    <a id="shareToWeibo" style="margin:7px 12px 0 0;float:right; text-decoration:none;" href="javascript:void(0)"><img src="${systemProp.staticServerUrl}/widget/images/icon/sns32/weibo_publish_button_24.gif"/></a>
    <a id="magmeWeibo" style="margin:7px 12px 0 0; float:right; text-decoration:none;"><img src="${systemProp.staticServerUrl}/widget/images/magme_weibo.jpg"/></a>
    <!-- 
    <a id="follow" style="margin:7px 12px 0 0; float:right; text-decoration:none; " href="javascript:void(0);" ><img title="加关注" src="http://img.t.sinajs.cn/t35/style/images/common/transparent.gif" /></a>
     -->
    </#if>
    
    <div class="search">
    	<input type="text" name="queryStr" <#if queryStr?? && queryStr!=''> value="${queryStr}"<#else> tips="请输入杂志名称"</#if> class="floatl input g100" /><a id="searchBtn" href="javascript:void(0)" class="btn floatl">搜索</a>
    </div>
</div>

<!--body-->
${body}

<#if widget_auth?? && widget_auth.type=='tencent' >
<p style="text-align:right; margin-top:10px; padding-top:8px; border-top:1px solid #eee;">
本应用由上海居冠软件有限公司开发，您在使用过程中遇到问题请联系QQ<br />
<a href="http://wpa.qq.com/msgrd?v=3&uin=49495958&site=qq&menu=yes" target="_blank">
<img align="absmiddle" border="0" title="点击这里给我发消息" alt="点击这里给我发消息" src="http://wpa.qq.com/pa?p=2:49495958:50">
</a>
</p>
</#if>

</div>



<#if widget_auth??&&widget_auth.type=='baidu'>
    <script src="http://app.baidu.com/static/appstore/monitor.st"></script>
</#if>
</body>

<script type="text/javascript">

$(function(){

	$("#searchBtn").live('click',function(e){
		e.preventDefault();
		var queryStr = $("input[name=queryStr]").val();
		if(!!queryStr && $.trim(queryStr)!="" && queryStr!="请输入杂志名称"){
			window.location.href="/widget/widget-search.action?queryStr="+queryStr;
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
		showDialog('http://widget.renren.com/dialog/request?display=iframe&selector_mode=naf&actiontext=+&app_msg=%e8%bf%99%e9%87%8c%e5%a5%bd%e5%a4%9a%e6%88%91%e5%96%9c%e6%ac%a2%e7%9a%84%e6%9d%82%e5%bf%97%ef%bc%8c%e4%bd%a0%e4%b9%9f%e6%9d%a5%e7%9c%8b%e7%9c%8b%e5%90%a7%ef%bc%81&app_id=${widget_auth.apiID}&accept_url=http%3a%2f%2fapps.renren.com%2fmagzine&accept_label=%e8%bf%9b%e5%85%a5%e5%ba%94%e7%94%a8','邀请');
	    return false;
	});
	
	$("#like").live('click',function(){
		showDialog('http://widget.renren.com/dialog/like?redirect_url=http://apps.renren.com/magzine&like_url=http%3a%2f%2fapps.renren.com%2fmagzine','信息');
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
		var para_pic="pic=${systemProp.domain+"/widget/images/80_80.gif"}";
		var para_session_key="session_key=${widget_auth.sessionKey}";
		var para_text="text=";
		var para_v="v=1.2";
		var sig = hex_md5(para_api_key+para_call_id+para_link+para_linktext+para_method+para_pic+para_session_key+para_text+para_v+"${widget_auth.secretKey}");
		var para_sig="sig="+sig;
		
		var url=para_api_key+"&"+para_call_id+"&"+para_link+"&"+para_linktext+"&"+para_method+"&"+para_pic+"&"+para_session_key+"&"+para_text+"&"+para_v+"&"+para_sig;
		
		showKxDialog(base64AndRep(url));
	    return false;
	});	
	
	setHeight();
	
	<#elseif widget_auth??&&widget_auth.type=='tencent'&&widget_auth.apiID??&&widget_auth.subType??>

	$("#invite").live('click',function(){
		fusion2.dialog.invite
		({

		  // 可选，微博平台不可使用该参数。邀请理由，最长不超过35个字符。若不传则默认在弹框中显示"这个应用不错哦，跟我一起玩吧！"
		  msg  :"这里好多我喜欢的杂志，你也来看看吧！~"

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
	
	});		
	
	$("#share").live('click',function(){
		fusion2.dialog.share
		({
		  // 可选。分享应用的URL，点击该URL可以进入应用，必须是应用在平台内的地址。
		  url:  "<#if widget_auth.subType=='pengyou'>http://apps.pengyou.com/${widget_auth.apiID}<#elseif widget_auth.subType=='qzone'>http://rc.qzone.qq.com/myhome/${widget_auth.apiID}</#if>",

		  // 可选。默认展示在输入框里的分享理由。
		  desc:"这里好多我喜欢的杂志，你也来看看吧！~",

		  // 必须。应用简要描述。
		  summary :"电子杂志免费在线极速阅读平台。这里有精美的电子杂志，包含数码杂志，时尚杂志，汽车杂志，财经杂志可以免费在线看！",

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
	});	
	
	<#elseif widget_auth??&&widget_auth.type=='sina'&&widget_auth.sinaUid??>

	$("#magmeWeibo").live('click',function(){
		window.open("http://weibo.com/magme");
	});

	$("#shareToWeibo").live('click',function(){
		WB2.anyWhere(function(W){
		    // 获取评论列表
		W.parseCMD("/statuses/update.json", function(sResult, bStatus){
		    if(bStatus == true) {
				alert("分享成功");
		    }
		    else{
		    	alert("您已经分享过！");
		    }
		},{
			status : encodeURI('我正在玩这个应用《麦米杂志阅读》，觉得不错，推荐你也来看看，这里有精美的电子杂志，包含数码杂志，时尚杂志，汽车杂志，财经杂志可以免费在线看。http://apps.weibo.com/magzine')
		    //status : 'abc'
		},{
		    method: 'post'
		});
		});
	
	});	
	
	</#if>
})
</script>
<@gap.main />
</html>
