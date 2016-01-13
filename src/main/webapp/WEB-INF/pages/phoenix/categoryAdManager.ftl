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
<link href="${systemProp.staticServerUrl}/phoenix/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/categoryAd.js"></script>
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
<@header.main menuId="ad"/>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
		<div class="conTools clearFix tCenter">
		<a href="${systemProp.domain}/phoenix/phoenix-ad.action" class="btnSM">封面广告</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.domain}/phoenix/phoenix-category-ad.action" class="btnAM">栏目广告</a>
        </div>
    	<div class="conTools clearFix tCenter">
        	<fieldset>
            	<div>
            		<em>栏目</em>
                    <em class="mgr50" style="width:200px"><select id="cate-select" style="width:200px"><option value="">请选择</option></select></em> 
                	<em>名称</em>
                	<em><input type="text" class="input g140 " id="s-title" tips="名称"/></em>
                	<em><a href="#" id="searchBtn" class="btnBS">查询</a></em>
                	<em><a href="#" id="newBtn" class="btnBS">+添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB con04">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50">缩略图</td>
                    <td class="g40">排序</td>
                    <td class="g200">标题</td>
                    <td>链接</td>
                    <td class="g90">操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                    
                </tbody>
            </table>            
	        </div>
        
        <#import "../components/pagebar.ftl" as pageBar>
	   <@pageBar.main/>
    </div>




    
</div>
<@editContent />
<#macro editContent>

        <div class="popContent" id="adModDialog" style="width:400px;">
    	<fieldset>
            <h6>内容修改</h6>
            <form method="post" id="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <input type="hidden" name="id" id="adId" class="input g300" />
               <div>
                    <em class="g50">标题</em>
                    <em><input type="text" id="title" name="title" class="input g200" /></em>
                </div>
                <div>
                    <em class="g50">栏目</em>
                    <em><select id="add-cate-select" name="categoryId" style="width:200px"><option value="">请选择</option></select></em>
                </div>
                <div>
                    <em class="g50">顺序</em>
                    <em><input type="text" id="sortOrder" name="sortOrder" class="input g80" /></em>
                </div>
                <div>
                    <em class="g50">链接</em>
                    <em><input type="text" id="link" name="link" class="input g300" /></em>
                </div>
                <div>
                    <em class="g50">图片</em>
                    <em><input type="file" name="adPic" id="adPic" class="inputFile g200" />(768*384)</em>
                </div>
            <div class="actionArea tRight">
                 <em class="g50">&nbsp;</em>
                    <em><a href="#" id="saveBtn" class="btnAM">修改</a></em>
                    <em><a href="#" id="cancelBtn" class="btnWM">取消</a></em>
            </div>
            </form>
        </fieldset>
</div>
</#macro>
</body>
</html>