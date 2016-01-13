<#import "../components/statheader.ftl" as statheader>
<#import "../components/statleft.ftl" as statleft>

<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelData.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>


<script>
function fnReadyTable(){
	var tableID=1;
	var tdID =1;
	var tableNum = $('table.table').size();
	for(tableID;tableID<=tableNum;tableID++) {
		$('table.table').get(tableID-1).lang='table'+tableID;
		var tdNum = $('table.table[lang*=table'+tableID+'] tr:first td').size();
		for(tdID; tdID <= tdNum; tdID++) {
			$('table.table tr td:nth-child('+tdID+')').addClass('t'+tdID);
		};
	};
}
$(function(){
	fnReadyTable();
	var $JQtableBg = $("table.JQtableBg");
	var $JQtableHover = $("table.JQtableBg tbody tr");
	$JQtableBg.each(function(){
		$(this).find("tbody tr:odd").addClass('bgColorTable');
	});
	$JQtableHover.live("mouseover",function(){
	$(this).addClass("bgTrHover");
	});
	$JQtableHover.live("mouseout",function(){
		$(this).removeClass("bgTrHover");
	});
})

//highlight
$(function(){
   //var menuid=$("div[menuid]").attr("menuid");
   //if(menuid!=''){
   //   $("a[naviMenuId="+menuid+"]").addClass("current");
  // }else{
  //    $("a[naviMenuId=1]").addClass("current");
   //}
   
   var location=window.location.href.substring(19);
   $("a[href='"+location+"']").addClass("current");
   
})
</script>

<!--[if lt IE 7]>
<script src="/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<@statheader.main/>
<div class="body">
<@statleft.main/>
<div class="conMiddleRight">
${body}
</div>
</div>

</body>
</html>