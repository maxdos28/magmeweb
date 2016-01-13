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

	var ajaxUrl = SystemProp.appServerUrl+"/phoenix/article-read!readJson.action";
	
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
			mydataArray.push([adData.name,adData.pv,adData.uv]);
		}
		
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'bar','86321583T0067135F');
		myChart.setDataArray(myData);
		myChart.setTitle('阅读数量');
		myChart.setTitleColor('#8E8E8E');
		myChart.setAxisNameX('栏目名称');
		myChart.setAxisNameY('数目');
		myChart.setAxisValuesAngle(60);
		myChart.setAxisNameFontSize(11);
		myChart.setAxisNameColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisWidth(1);
		myChart.setBarValuesColor('#2F6D99');
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisPaddingLeft(80);
		myChart.setTitleFontSize(11);
		myChart.setBarColor('#2D6B96', 1);
		myChart.setBarColor('#0000FF', 2);
		myChart.setBarBorderWidth(0);
		myChart.setBarSpacingRatio(50);
		myChart.setBarOpacity(0.9);
		myChart.setFlagRadius(3);
		myChart.setTooltipPosition('nw');
		myChart.setTooltipOffset(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForBar(1, 'pv');
		myChart.setLegendForBar(2, 'uv');
		myChart.setSize(780, 380);
		myChart.setGridColor('#c2c2c2');
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
		condition.isFree = $("#isFree").val();
		condition.phoenixCategoryId = $("#phoenixCategoryId").val();
		
		showChart(condition,ajaxUrl,callBack);
	});
	
	
	//级联免费和栏目
	$("#isFree").unbind("change").live("change",function(){
	     var callback2=function(rs){
	         $("#phoenixCategoryId").html("");
	         if(rs && rs.code==200){
	             var phoenixCategoryList=rs.data.phoenixCategoryList;
	             var length=rs.data.phoenixCategoryList.length;
	             var html='<option value="" >全部</option>';
	             for(var i=0;i<length;i++){
	                 html+="<option value="+phoenixCategoryList[i].id+" >"+phoenixCategoryList[i].name+"</option>";
	             }
	         	$("#phoenixCategoryId").html(html);    
	         }
	     };
	     $.ajax({
	        url: SystemProp.appServerUrl+"/phoenix/phoenix-category!queryChannelJson.action",
			type: "GET",
			data: {"isFree":$("#isFree").val()},
			success: callback2
	     });
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
        <a href="${systemProp.appServerUrl}/phoenix/app-start!index.action" class="btnSM">用户安装量</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/app-start.action" class="btnSM">设备分布</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.appServerUrl}/phoenix/article-read.action" class="btnAM">阅读数量</a>
        </div>
        <div class="conB clearFix">
        	<fieldset>
            	<div>
                	<em>开始日期</em>
                	<em><input id="startDate" type="text" class="input g140" /></em>
                	<em>结束日期</em>
                	<em><input id="endDate" type="text" class="input g140" /></em>
                	<em>收费方式</em>
                	<em>
                	  <select id="isFree">
                	      <option value="" >全部</option>
		            	  <option value="1" <#if isFree?? && isFree=1> selected </#if>  >免费</option>
		            	  <option value="0" <#if isFree?? && isFree=0> selected </#if>  >收费</option>
	                  </select>
	                </em>
                	<em>栏目名称</em>
                	<em>
                	  <select id="phoenixCategoryId">
                	      <option value="" >全部</option>
                	      <#if clist?? && (clist?size>0)>
	                	      <#list clist as c>
			            	    <option value="${c.id}" <#if phoenixCategoryId?? && phoenixCategoryId=c.id> selected </#if>  >${c.name}</option>
			            	  </#list>
		            	  </#if>
	                  </select>
	                </em>
                	<em><a id="changeChart" href="javascript:void(0)" class="btnBS">确定</a></em>
                </div>
            </fieldset>
        </div>

        <div class="conB con04">
            <div style="height:500px; background:#eee; line-height:500px; text-align:center; color:#000; font-size:40px;">
             <div id="mychart" class="conBody" style="width:1000px;height:500px;padding:20px;">
	         </div>
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