<#import "../components/phoenixAdminHeader.ftl" as header>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/phoenix/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/startPic.js"></script>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<!--header-->
<@header.main menuId="startPic"/>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix">
            <fieldset>
            	<div>
                    <form id="showTypeForm" action="${systemProp.appServerUrl}/phoenix/start-pic!saveShowType.action" method="post" enctype="multipart/form-data">
                    	<em>显示方式:</em>
	            		<em><input type="radio" name="showType" value="1" <#if showType?exists&&showType==1>checked</#if>>轮播</em>
	            		<em><input type="radio" name="showType" value="2" <#if showType?exists&&showType==2>checked</#if>>滑动</em>
	            		
	                	<em><a id="saveShowTypeBtn" class="btnBS" href="javascript:void(0)">保存</a></em>
                	</form>
                </div>
                <br>
                <div>
                    <form id="form" action="${systemProp.appServerUrl}/phoenix/start-pic!add.action" method="post" enctype="multipart/form-data">
                    	<em>类型</em>
	            		<em><select id="showType" name="showType" ><option value="1" checked>轮播</option><option value="2">滑动</option></select></em>
	            		<em>启动页图片</em>
	            		<em id="picSize">(768*1024)</em>
	            		<em><input id="pic" name="pic" type="file" class="inputFile" /></em>
	            		
	            		<em class="g80 tRight">URL</em>
	            		<em><input id="picLink" name="picLink" type="text" class="input g300" /></em>
	                	<em><a id="addPic" class="btnBS" href="javascript:void(0)">添加</a></em>
                	</form>
                	<form id="moveForm" action="${systemProp.appServerUrl}/phoenix/start-pic!move.action">
                	   <input type="hidden" id="moveId" name="id"/>
                	   <input type="hidden" id="moveLeft" name="moveLeft"/>
                	</from>
                </div>
            </fieldset>
        </div>
        <div class="conB con01">
            <h2>封面列表(轮播)</h2>
            <ul class="clearFix">
              <#if phoenixStartPicList?? && (phoenixStartPicList?size>0)>
               <#list phoenixStartPicList as startPic>
               <#if startPic.type!=3>
                <li class="item showBar" startPicId="${startPic.id}">
                    <img src="${systemProp.staticServerUrl}${startPic.picPath}" />
                    <div class="border"></div>
                    <a class="del" href="javascript:void(0);">下架</a>
                    <#if startPic_index!=0>
                    <a class= "moveL" href= "javascript:void(0);"> 前移 </a>
                    </#if>
                    <#if startPic_index!=(phoenixStartPicList?size-1)>
                    <a class= "moveR" href= "javascript:void(0);"> 后移 </a>
                    </#if>
                </li>
                </#if>
               </#list>
              </#if>
            </ul>
        </div>
        <div class="conB con01">
            <h2>封面列表(滑动)</h2>
            <ul class="clearFix">
              <#if phoenixStartPicList?? && (phoenixStartPicList?size>0)>
               <#list phoenixStartPicList as startPic>
               <#if startPic.type==3>
                <li class="item showBar" startPicId="${startPic.id}">
                    <img src="${systemProp.staticServerUrl}${startPic.picPath}" />
                    <div class="border"></div>
                    <a class="del" href="javascript:void(0);">下架</a>
                </li>
              </#if>
               </#list>
              </#if>
            </ul>
        </div>
        
        <div class="conFooter">
		</div>
    </div>
</div>

</body>
</html>