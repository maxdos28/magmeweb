<#import "../components/newPublisher.ftl" as np>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${title!"Magme"}</title>
<@np.jsCss />

<script>

	

$(function(){
	$.jqueryScrollPhoto("#pubTopbar",5,192,5,0,600);
	$(".header .inner").hide().fadeIn(500);
	$(".body").hide().fadeIn(500);
	
	var menu=$("div[menu]").attr("menu")||"";
	$("#menu_"+menu).addClass("current");
	
	var menuSecond=$("#menu_"+menu).attr("id");
	if(menuSecond){
		var checkSecondName = $("div[menuSecond]").attr("menuSecond")||"";
		if(menuSecond=='menu_publisher')
		{
			var htmlHead = "";
			htmlHead+="<div class='subNav'>";
			htmlHead+="<ul class='inner'>";
		    <#if session_admin?exists>
		    htmlHead+="<li class='current'><a href='javascript:void(0)'>杂志信息设置</a></li>";
		    <#else>
		    if(checkSecondName){
		    	if(checkSecondName=='publisher'){
		    		htmlHead+="<li class='current'><a href='/new-publisher/publisher-config!to.action'>账户信息设置</a></li>";
		    		htmlHead+="<li><a href='/new-publisher/publication-config!to.action'>杂志信息设置</a></li>";
		    	}
		    	if(checkSecondName=='publication')
		    	{
		    		htmlHead+="<li><a href='/new-publisher/publisher-config!to.action'>账户信息设置</a></li>";
		    		htmlHead+="<li class='current'><a href='/new-publisher/publication-config!to.action'>杂志信息设置</a></li>";
		    	}
		    }
		    </#if>
		    htmlHead+="</ul></div>";
		    $("#newPubulisherHead").append(htmlHead);
		}
		
	}
});
</script>
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
<@np.header class />

<!--body-->
${body}

</body>
</html>