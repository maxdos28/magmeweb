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
<link href="${systemProp.staticServerUrl}/v3/style/datepicker.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/phoenix/js/statCategoryPvUv.js"></script>
<!--header-->
<@header.main menuId="appdata"/>
<div class="body">
      <div class="conLeftMiddleRight" style="min-height:600px;">
      	<div class="conTools clearFix tCenter">
        <a href="${systemProp.appServerUrl}/phoenix/phoenix-stat-category-pv-uv.action" class="btnAM">阅读数量</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/phoenix-stat-category-trend.action" class="btnSM">阅读趋势</a>
        </div>
    	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>分类</em><em>
            		<select id="categoryId">
            			<option value="0">全部</option>
            			<#if categoryList??>
            				<#list categoryList as cc>
            					<option value="${cc.id!0}">${cc.name!''}</option>	
            				</#list>
            			</#if>
            		</select>
            	</em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">栏目浏览量报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    </div>
</div>
