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
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/feedback.js"></script>


<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<@header.main menuId="feedback"/>
<body>

<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix tCenter">
        	<fieldset>
            	<div>
                	<em>开始日期</em>
                	<em><input type="text" class="input g140 datepicker" id="startDate" /></em>
                	<em>结束日期</em>
                	<em><input type="text" class="input g140 datepicker" id="endDate"/></em>
                	<em><a href="javascript:void(0);" id="searchBtn" class="btnBS">确定</a></em>
                </div>
            </fieldset>
        </div>

        <div class="conB con03">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td>时间</td>
                    <td class="g250">联系方式</td>
                    <td class="g500">内容</td>
                  </tr>
                </thead>
                <tbody id="tbodyContent">
                </tbody>
            </table>            
	        </div>
        
        <div class="conFooter">
            <div id="eventListPageadd" class="gotoPage"></div>
			<div id="eventListPage" class="changePage" ></div>
		</div>
    </div>




    
</div>

</body>
</html>