<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${sns_creative.title!} 预览  - 麦米网Magme</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imgareaselect.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.floatDiv.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/sns_scroll.js"></script>

<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1V4.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/m1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/userindex.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/userConfig.js"></script>

<script>
$(function(){
	$.magmeShow(".sideLeft .theme");
	$("#bg img").coverImg();
});


</script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>

<!--body-->
<div class="body pageM1 noBg clearFix">
	<div class="sideLeft" id="Dashboard">
        <!-- theme start -->
	        <div class="theme">
            	<div class="calendar">
            		<span>${sns_creative.updateTime?string('yyyy')} &nbsp;/&nbsp;${sns_creative.updateTime?string('MM')}</span>
            		<strong>${sns_creative.updateTime?string('dd')}</strong>
            	</div>
        		<#if sns_creative_ex.size()==1>
        			<#list sns_creative_ex as ex>
        				<#if ex.conType==2>
		        			<div class="content conPhoto clearFix">
				                <div class="conPlay">
				                    <div class="inner">
				        				<a text='${ex.content!}' w='${ex.w!}' h='${ex.h!}' pic='${ex.imgPath}' class="a0 pic cover">
	        							<img width='${creativeResize("w",ex.w!,ex.h!,500)}' height='${creativeResize("h",ex.w!,ex.h!,500)}' src="${avatarResize(ex.imgPath,'500')}"  />
	        							</a>
				                    </div>
				                    <#if sns_creative_ex.size()&gt;7>
				                    	<a class="more "></a>
				                    </#if>
				                </div>
				                <div class="uInfo">
				                	<#if sns_creative.magazineName?? && sns_creative.magazineName!="">
				                    	<h3 class="png"><a href="${sns_creative.magazineUrl!}" title="出自&nbsp;[&nbsp;${sns_creative.magazineName!}&nbsp;]">mask</a>
				                    		<img src="${systemProp.magServerUrl}/${sns_creative.publicationid!}/${sns_creative.issueid!}/1.jpg" />
				                    	</h3>
				                    </#if>
				                    <div class="userHead png">
				                        <a class="head" u='${session_user.userId!}' href="javascript:void(0);">
					                        <img src="<#if session_user.avatar?? && session_user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(session_user.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
					                        <strong class="infoName">${session_user.nickName!}</strong>
					                        <#if session_user.ism?? && session_user.ism=='M'>
							            		<em class="png m" title="麦米认证编辑"></em>
							            	</#if>
						            	</a>
				                    </div>
				                    <div class="tagList">
					                	<#list sns_creative_tags as tag>
					                    	<a href="javascript:void(0)">${tag!}</a>
					                    </#list>
					                </div>
				                </div>
				            	<h2 class='til' til='${sns_creative.title!}' >${sns_creative.title!}<span class="date">${sns_creative.updateTime?string("yyyy-MM-dd")}</span></h2>
				                <div class="text">
				                    <p>${sns_creative.content!}</p>
				                </div>
				            	<div class="userList">
				            		<#list sns_creative_user as u>
					                    <#if u_index&lt;5>
						                    <a href="javascript:void(0)" u='' title="${u.nickName}">
						                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar!,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                    </a>
						                    </#if>
						                    <#if !u_has_next >
						                    	<span>${sns_creative_user.size()}个参与人</span>
						                    </#if>
				                    </#list>
				                </div>
				            </div>
        				<#elseif ex.conType==3>
        					<div class="content conAudio clearFix">
				                <div class="conPlay">
				                    <a href="javascript:void(0)"><img src='${ex.imgPath!}' width="400" height="402" /></a>
				                </div>
				                <div class="audioRight">
				                	<div class="uInfo">
				                        <div class="userHead png">
				                            <a class="head"  href="javascript:void(0)">
						                        <img src="<#if session_user.avatar?? && session_user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(session_user.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                        <#if session_user.ism?? && session_user.ism=='M'>
								            		<em class="png m" title="麦米认证编辑"></em>
								            	</#if>
						            		</a>
				                        </div>
				                        <h2 class='til' til='${sns_creative.title!}' url=''>${sns_creative.title!}</h2>
				                        <strong class="infoName">${session_user.nickName}</strong><span class="date">${sns_creative.updateTime?string("yyyy-MM-dd")}</span>
				                    </div>
				                    ${ex.path!}
				                    <div class="text">
				                        <p>${sns_creative.content!}</p>
				                    </div>
				                    <div class="userList">
				                        <#list sns_creative_user as u>
						                   <#if u_index&lt;5>
						                    <a href="javascript:void(0)" u='' title="${u.nickName!}">
						                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar!,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                    </a>
						                    </#if>
						                    <#if !u_has_next >
						                    	<span>${sns_creative_user.size()}个参与人</span>
						                    </#if>
					                    </#list>
				                    </div>
				                </div>
				            </div>
	        			<#elseif ex.conType==4>
	        				<div class="content conVideo clearFix">
				                <div class="conPlay">
				               	 	<a href="javascript:void(0)"  path='${ex.path}'  class="mask png"><span class="png">play</span></a>
				                    <img src="${ex.imgPath!}" />
				                </div>
				                <div class="uInfo">
				                    <div class="userHead png">
				                        <a class="head"  href="javascript:void(0)">
					                        <img src="<#if session_user.avatar?? && session_user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(session_user.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
					                        <strong class="infoName">${session_user.nickName}</strong>
					                        <#if session_user.ism?? && session_user.ism=='M'>
							            		<em class="png m" title="麦米认证编辑"></em>
							            	</#if>
						            	</a>
				                    </div>
				                    <div class="tagList">
					                	<#list sns_creative_tags as tag>
					                    	<a href="javascript:void(0)">${tag}</a>
					                    </#list>
					                </div>
				                </div>
				            	<h2 class='til' til='${sns_creative.title!}' >${sns_creative.title!}<span class="date">${sns_creative.updateTime?string("yyyy-MM-dd")}</span></h2>
				                <div class="text">
				                    <p>${sns_creative.content}</p>
				                </div>
				            	<div class="userList">
				            		<#list sns_creative_user as u>
					                    <#if u_index&lt;5>
						                    <a href="javascript:void(0)" u='' title="${u.nickName}">
						                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar!,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                    </a>
						                    </#if>
						                    <#if !u_has_next >
						                    	<span>${sns_creative_user.size()}个参与人</span>
						                    </#if>
				                    </#list>
				                </div>
				            </div>
	        			</#if>
        			</#list>
        		<#else>
        			<div class="content conPhoto clearFix">
		                <div class="conPlay">
		                    <div class="inner">
		                    	<#list sns_creative_ex as ex>
			                    	<#if ex.conType==3>
				        				<a text='${ex.content!}' pic='${ex.imgPath}' path='${ex.path}' class="<#if ex_index &lt; 7>a${ex_index}</#if> audio <#if ex_index==0>cover</#if>"><img src="${ex.imgPath!}" /></a>
				        			<#elseif ex.conType==4>
				        				<a text='${ex.content!}' pic='${ex.imgPath}' path='${ex.path}'  class="<#if ex_index &lt; 7>a${ex_index}</#if> video <#if ex_index==0>cover</#if>"><img src="${ex.imgPath!}" /></a>
				        			<#else>
				        				<a text='${ex.content!}' w='${ex.w!}' h='${ex.h!}' pic='${ex.imgPath}' class="<#if ex_index &lt; 7>a${ex_index}</#if> pic <#if ex_index==0>cover</#if>">
	        							<img width='${creativeResize("w",ex.w!,ex.h!,500)}' height='${creativeResize("h",ex.w!,ex.h!,500)}' src="${avatarResize(ex.imgPath,'500')}"  />
	        							</a>
				        			</#if>
	        					</#list>
		                    </div>
		                    <#if sns_creative_ex.size()&gt;7>
		                    	<a class="more "></a>
		                    </#if>
		                </div>
		                <div class="uInfo">
		                	<#if sns_creative.magazineName?? && sns_creative.magazineName!="">
		                    	<h3 class="png"><a href="${sns_creative.magazineUrl!}" title="出自&nbsp;[&nbsp;${sns_creative.magazineName!}&nbsp;]">mask</a>
		                    		<img src="${systemProp.magServerUrl}/${sns_creative.publicationid!}/${sns_creative.issueid!}/1.jpg" />
		                    	</h3>
		                    </#if>
		                    <div class="userHead png">
		                        <a class="head"  href="javascript:void(0)">
			                        <img src="<#if session_user.avatar?? && session_user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(session_user.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
			                        <strong class="infoName">${session_user.nickName}</strong>
			                        <#if session_user.ism?? && session_user.ism=='M'>
					            		<em class="png m" title="麦米认证编辑"></em>
					            	</#if>
				            	</a>
		                    </div>
		                    <div class="tagList">
			                	<#list sns_creative_tags as tag>
			                    	<a href="javascript:void(0)">${tag}</a>
			                    </#list>
			                </div>
		                </div>
		            	<h2 class='til' til='${sns_creative.title!}' >${sns_creative.title!}<span class="date">${sns_creative.updateTime?string("yyyy-MM-dd")}</span></h2>
		                <div class="text">
		                    <p>${sns_creative.content!}</p>
		                </div>
		            	<div class="userList">
		            		<#list sns_creative_user as u>
		            			<#if u_index&lt;5>
			                    <a href="javascript:void(0)" u='' title="${u.nickName}">
			                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar!,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
			                    </a>
			                    </#if>
			                    <#if !u_has_next >
			                    	<span>${sns_creative_user.size()}个参与人</span>
			                    </#if>
		                    </#list>
		                </div>
		            </div>
        		</#if>
	        		
    		</div>
         <!-- theme end -->
    </div>
    
    
    
	<div class="sideRight">
    	<div class="con conHead">
        	<a class="head" href="javascript:void(0)" title='${userInfo.nickname!}'>
        	 	<img src="<#if userInfo.avatar?? && userInfo.avatar!="">${systemProp.profileServerUrl}${userInfo.avatar!}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head172.gif'" />
            	<strong>${stringSub(userInfo.nickname,24)}</strong>
                <em class="png">M</em>
            </a>
        	<div class="btn">
            </div>
        </div>
    	<div class="con conInfo">
        	<a class="a1" href="javascript:void(0)"><span>${userInfo.attention}</span>关注</a>
        	<a class="a2" href="javascript:void(0)"><span>${userInfo.fans}</span>粉丝</a>
        	<a class="a3" href="javascript:void(0)"><span>${userInfo.creativeCount}</span>内容</a>
        </div>
    </div>
</div>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
