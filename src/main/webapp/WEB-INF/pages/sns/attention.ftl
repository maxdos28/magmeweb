<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/register.css" rel="stylesheet" type="text/css" />


<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imgareaselect.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.floatDiv.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1_1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>


<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/sns/sns_registerAndLogin.js"></script>
<script>
$(function(){
	$(".regConStep2").show();
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
    
    <div class="regConStep2">
        <h1 class="logo"><a href="http://www.magme.com/" target="_blank" title="麦米网Magme">麦米网Magme</a></h1>
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
		            	<#if adminUserEx?? && adminUserEx.userId == userEx.userId>
		            	<#else>
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
		                </#if>
	                </#list>
                </#if>
            </div>
        </div>
        <fieldset class="regStep2">
            <div><a href="javascript:void(0)" class="btn" id="enterM1">进入M1</a></div>
        </fieldset>
    </div>
    
</body>
</html>