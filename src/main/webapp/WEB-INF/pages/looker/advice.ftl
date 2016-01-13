<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>用户反馈</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>

</head>
<body class="appSubmit">
<style type="text/css">
html{background: #1e1e1e;height:100%;}
</style>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<div class="appBody">
	<div class="appItem_Opinion">
		<div class="appItem_Opinion_score">	
			<#assign starCount=[1,2,3,4,5]>
			<div class="score-list"><span>操作易用：</span>
				<ul class="clearFix" id="star1">			
					<#list starCount as sc>
			        <li <#if looUserStar?? && looUserStar.star1?? && looUserStar.star1 gte sc>class="on"</#if>></li>
			        </#list>
				</ul>
			</div>
			<div class="score-list"><span>内容质量：</span>
				<ul class="clearFix" id="star2">					
			        <#list starCount as sc>
			        <li <#if looUserStar?? && looUserStar.star2?? && looUserStar.star2 gte sc>class="on"</#if>></li>
			        </#list>
				</ul>
			</div>
			<div class="score-list"><span>更新速度：</span>
				<ul class="clearFix" id="star3">					
			        <#list starCount as sc>
			        <li <#if looUserStar?? && looUserStar.star3?? && looUserStar.star3 gte sc>class="on"</#if>></li>
			        </#list>
				</ul>
			</div>
		</div>
		<textarea id="txAdvice" placeholder="大侠给点意见吧"></textarea>
	</div>
</div>
<#if !looUserStar?? || (looUserStar.star1 == 0 && looUserStar.star2 == 0 && looUserStar.star3 == 0)>
<input type="hidden" id="user-star" value="n">
<script type="text/javascript">

$(function(){
	$('.score-list li').bind('click', function(event) {
		var i = $(this).index()+1,$par = $(this).parents('.score-list'),$li = $par.find('li');
		$li.removeClass();
		$par.find('li:lt('+i+')').addClass('on');	
	 });
})
</script>
<#else>
<input type="hidden" id="user-star" value="y">
</#if>
</body>
</html>
