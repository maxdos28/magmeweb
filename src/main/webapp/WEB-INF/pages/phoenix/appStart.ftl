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
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script type="text/javascript">
$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	var ajaxUrl = SystemProp.appServerUrl+"/phoenix/app-start!appUseJson.action";
	
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
	$eDate.val(stred);
	$sDate.val(strsd);
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		var ajaxUrl2=ajaxUrl+"?startDate="+$sDate.val()+"&endDate="+$eDate.val();
		myMap.refreshData(ajaxUrl2,"设备分布");
	};
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var ajaxUrl2 = ajaxUrl + "?startDate="+$sDate.val()+"&endDate="+$eDate.val()+"&os="+$("#os").val();;
		myMap.refreshData(ajaxUrl2,"设备分布");
	});
});
</script>
</head>
<body>
<!--header-->
<@header.main menuId="appdata"/>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix tCenter">
        <a href="${systemProp.appServerUrl}/phoenix/app-start!index.action" class="btnSM">用户安装量</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/app-start.action" class="btnAM">设备分布</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/article-read.action" class="btnSM">阅读数量</a>
        </div>
        <div class="conB clearFix">
        	<fieldset>
            	<div>
                	<em>开始日期</em>
                	<em><input id="startDate" type="text" class="input g140" /></em>
                	<em>结束日期</em>
                	<em><input id="endDate" type="text" class="input g140" /></em>
                	<em>操作系统</em>
                	<em>
                	  <select id="os">
                	      <option value="" >全部</option>
		            	  <option value="android" <#if os?? && os='android'> selected </#if>  >androiod</option>
		            	  <option value="ios" <#if os?? && os='ios'> selected </#if>  >ios</option>
	                  </select>
	                </em>
                	<em><a id="changeChart" href="javascript:void(0)" class="btnBS">确定</a></em>
                </div>
            </fieldset>
        </div>

        <div class="conB con04">
            <div style="height:500px; background:#eee; line-height:500px; text-align:center; color:#000; font-size:40px;">
             <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	          id="myMap1" width="450px" height="500px"
	          codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab" style="margin:0 auto;">
			     <param name="movie" value="${systemProp.staticServerUrl}/reader/MagmePieChart.swf" />
			     <param name="quality" value="high" />
			     <param name="wmode" value="opaque" />
			     <param name="bgcolor" value="#869ca7" />
			     <param name="allowScriptAccess" value="always" />
			     <embed id="myMap2" src="${systemProp.staticServerUrl}/reader/MagmePieChart.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
			         width="450px" height="500px" name="ExternalInterfaceExample" align="middle"
			         play="true" loop="false" quality="high" allowScriptAccess="always"
			         type="application/x-shockwave-flash"
			         pluginspage="http://www.macromedia.com/go/getflashplayer" style="margin:0 auto;">
			     </embed>
			 </object>
	       </div>
        </div>
        <#--
        <div class="conFooter tRight">
                <a href="javascript:void(0)">导出Excel</a>
		</div>-->
    </div>
</div>

</body>
</html>