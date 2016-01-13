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
<script src="${systemProp.staticServerUrl}/phoenix/js/categoryManager.js"></script>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<@header.main menuId="category"/>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix tCenter">
<a href="${systemProp.domain}/phoenix/phoenix-category.action" class="btnAM">栏目管理</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.domain}/phoenix/phoenix-article!content.action" class="btnSM">内容管理</a>
        </div>

    	<div class="conB">
        	<fieldset>
                <div class="clearFix">
                    <div class="floatl g480">
                        <em style="width:80px">添加免费分类</em>
                        <em><input id="first-str" class="input g200" name="search_title" type="text"></em>
                    </div>
                    <div>
                        <em style="width:80px">添加收费分类</em>
                        <em><input id="second-str" class="input g200" name="search_title" type="text"></em>
                    </div>
                    <hr />
                </div>
                <div class="clearFix">
                    <div class="floatl g480">
                        <em style="width:80px">分类描述</em>
                        <em><input id="first-desc" class="input g200" name="search_title" type="text"></em>
                        <em><a id="passBtn1" class="btnBS" href="javascript:void(0);">添加</a></em>
                    </div>
                    <div>
                        <em style="width:80px">分类描述</em>
                        <em><input id="second-desc" class="input g200" name="search_title" type="text"></em>
                        <em><a id="passBtn2" class="btnBS" href="javascript:void(0);">添加</a></em>
                    </div>
                    <hr />
                </div>
            </fieldset>
        </div>
        <div class="conB con02">
           	<div class="clearFix">
                    <dl class="clear floatl" id="free-dl">
                    	<dt><label>免费</label></dt>
                    </dl>
                    <dl class="floatl" id="charge-dl">
                    	<dt><label>收费</label></dt>
                    </dl>
               	</div>
        </div>
        
        <div class="conFooter">
		</div>
    </div>
</div>
</html>