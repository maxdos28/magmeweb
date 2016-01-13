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

	var ajaxUrl = SystemProp.appServerUrl+"/phoenix/app-start!appInstallJson.action";
	
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.resList || rs.data.resList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.resList);
	};
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		var mydataArray = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			var ttdate='';
			if(adData.dateTime){
				ttdate = adData.dateTime.substring(0,10);
			}
			mydataArray.push([ttdate,adData.count]);
		}
		
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData,"blue");
		myChart.setTitle('用户安装量');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('安装量');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(80);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisValuesNumberX(data.length);
		myChart.setGraphExtend(true);
		myChart.setLineColor('#9F0505',"blue");
		myChart.setGridColor('#c2c2c2');
		myChart.setLineWidth(3);
		myChart.setAxisValuesAngle(60);
		myChart.setSize(780, 380);
		for(var i=0;i<data.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForLine('blue', 'uv');
		myChart.draw();

	}
	//设置请求的时间
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
		firstCondition = {"startDate":strsd,"endDate":stred};
	$eDate.val(stred);
	$sDate.val(strsd);
	
	showChart(firstCondition,ajaxUrl,callBack);
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.os = $("#os").val();
		
		showChart(condition,ajaxUrl,callBack);
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
        <a href="${systemProp.appServerUrl}/phoenix/app-start!index.action" class="btnAM">用户安装量</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/app-start.action" class="btnSM">设备分布</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/article-read.action" class="btnSM">阅读数量</a>
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

        <div class="conB">
             <div id="mychart" class="conBody" style="width:850px;height:500px;padding:20px;">
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