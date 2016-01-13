<#import "../components/mobilefooter.ftl" as mobilefooter>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--[if lt IE 7 ]> <html class="ie6" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html xmlns="http://www.w3.org/1999/xhtml"> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget20120529/style/style.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget20120529/style/jquery.jscrollpane.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/js/readerTool.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/widget20120529/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<#if xx?? && xx=="qq">
<script src="http://cdn.qplus.com/js/qplus.api.js"></script> 
</#if>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
	DD_belatedPNG.fix('.png');
	document.execCommand("BackgroundImageCache",false,true);
	function getUrlValue(name){
		var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
		if (r!=null) return unescape(r[2]); return null;
	}
</script>
<![endif]-->
</head>
<body>
    <div class="main">
        <!--header-->
        <div class="header clearFix">
            <div class="outer png">
                <div class="inner png">
                    <h1 class="logo" id="logo">
                        <a class="png" href="javascript:void(0)" title="(SEO)无处不悦读">(SEO)麦米网Magme</a>
                    </h1>
                    <!--nav-->
	                <#if xx?? && xx=="qq">
	                <ul class="menu" id="menu">
                        <li <#if pageTitle?? && pageTitle=="pub"> class="current"</#if>>
				<a href="/widget/qplus!pub.action?xx=${xx!}">杂志阅读</a>
	                </li>
                        <li <#if pageTitle?? && pageTitle=="event"> class="current"</#if>>
				<a href="/widget/qplus!event.action?xx=${xx!}">内容精选</a>
                        </li>
			<li <#if pageTitle?? && (pageTitle=="enjoy"||pageTitle=="read")> class="current"</#if>>
				<a href="/widget/qplus!enjoy.action?xx=${xx!}">我的麦米</a>
			</li><#else>
			<ul class="menu" id="menu">
                        <li <#if pageTitle?? && pageTitle=="pub"> class="current"</#if>>
				<a href="/widget/gleasy!pub.action?xx=${xx!}">杂志阅读</a>
	                </li>
			</#if>
                    </ul>
                    <div class="search" id="headerSearch">
                        <input class="in" type="text" name="queryStr" <#if queryStr?? && queryStr!=''> value="${queryStr}" tips="${queryStr}" <#else> tips="请输入搜索内容"</#if> />
                        <a class="btn" id="searchBtn" href="javascript:void(0)">搜索</a>
                    </div>
                    
                </div>
            </div>
            <div class="nav">
                <div class="inner">
                    <ul class="classification item clearFix">
			<#if pageTitle?? && pageTitle !='enjoy' && pageTitle != 'read' >
				<li <#if !sortId??>class="current"</#if>>
					 <#if xx?? && xx=="qq">
				   <a href="/widget/qplus!<#if pageTitle?? && pageTitle=='pub' >pub<#else>event</#if>.action?xx=${xx!}">全部</a>
				   <#else>
				   	<a href="/widget/gleasy!<#if pageTitle?? && pageTitle=='pub' >pub<#else>event</#if>.action?xx=${xx!}">全部</a>
				   </#if>
				</li>
				<#if sortList??>
				<#list sortList as sort>
				    <#if sort_index lt 18>
				    <li <#if sortId??&&sortId=sort.id>class="current"</#if>>
				     <#if xx?? && xx=="qq">
				   <a href="/widget/qplus!<#if pageTitle?? && pageTitle=='pub' >pub<#else>event</#if>.action?sortId=${sort.id}&xx=${xx!}">
				   <#else>
				   	<a href="/widget/gleasy!<#if pageTitle?? && pageTitle=='pub' >pub<#else>event</#if>.action?sortId=${sort.id}&xx=${xx!}">
				   </#if>
					${sort.name}
				    </a>
				    </li>
				    </#if>
				</#list>
				</#if>
			<#elseif pageTitle?? && (pageTitle ='enjoy' || pageTitle == 'read')>	
				<li <#if pageTitle?? && pageTitle=="enjoy"> class="current"</#if>>
					<a href="/widget/qplus!enjoy.action?xx=${xx!}">收藏杂志</a>
				</li>
				<li <#if pageTitle?? && pageTitle=="read"> class="current"</#if>>
					<a href="/widget/qplus!read.action?changeFlg=0&xx=${xx!}" id="lastestRead">最近阅读</a>
				</li>
			</#if>
                    </ul>
                </div>
            </div>
        </div>
        <!--body-->
	<div class="body clearFix">
        ${body}
	<a class="foot feedBack">意见反馈</a>
	<!--<a class="foot footShare">分享应用</a>-->
	<a class="foot footMagme">麦米官方微博</a>
	</div>
    </div>
</body>

<script>
	var qqid = "${app_openid!}";
	function getUrlValue(name){
		var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
		if (r!=null) return unescape(r[2]); return null;
	}
$(function(){
	$("#searchBtn").live('click',function(e){
		e.preventDefault();
		var queryStr = $("input[name=queryStr]").val();
		if(!!queryStr && $.trim(queryStr)!="" ){
			<#if xx?? && xx=="qq">
			window.location.href=SystemProp.appServerUrl+"/widget/qplus!search.action?xx=${xx!}&pageTitle=${pageTitle!}&queryStr="+encodeURIComponent(queryStr);
			<#else>
			window.location.href=SystemProp.appServerUrl+"/widget/gleasy!search.action?xx=${xx!}&pageTitle=${pageTitle!}&queryStr="+encodeURIComponent(queryStr);
			</#if>
			
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

	$(".footMagme").live('click',function(e){
		window.open ("http://e.weibo.com/magme", "newwindow");

	});
	$(".feedBack").live('click',function(e){
		e.preventDefault();
			if($("#feedBackSmall").length>0) {
				$("#feedBackSmall").fancybox();
			}
		var callback = function(result){
			$("body").append(result);
			$feedBackSmall = $("#feedBackSmall");
			$feedBackSmall.fancybox();
			$("#feedbackSubmitBtn").click(function(e){
				e.preventDefault();
				var form=$("#feedBackSmallForm");
				var categoryId=form.find("select[name='education']").val();
				var content=form.find("textarea[name='content']").val();
				
				if(!categoryId||!(/^\d+$/.test(categoryId))){
					alert("请选择意见类别",function(){
						form.find("select[name='education']").focus();
					});
					return;
				}
				
				if(!$.trim(content)){
					alert("意见内容不允许为空",function(){
						form.find("textarea[name='content']").focus();
					});
					return;
				}
				
				var callback = function(result){
					var code = result.code;
					var message = result["message"];
					if(code == 400){
						gotoLogin(message);
					}else if(code == 300||code==500){
						alert(message);	
					}else if(code == 200){
						alert("感谢您提交意见反馈");
						$.fancybox.close();
					}
					return;
				};
				
				$.ajax({
					url: SystemProp.appServerUrl+"/widget/qplus!saveCategory.action?xx=${xx!}",
					type: "POST",
					data: {"categoryId":categoryId,"content":content},
					success: callback
				});
			});
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/widget/qplus!categoryAjax.action",
			type : "POST",
			success: callback
		});
	});
})
</script>
<@mobilefooter.main />
</html>