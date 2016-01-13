<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>我的喜欢(${session_user.nickName!}) - 麦米网Magme</title>
<meta name="Keywords" content="(SEO)麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="(SEO)麦米网提供数百种最新杂志在线免费阅读，财经，旅游，娱乐，时尚，汽车，体育，数码生活等。在麦米看书，交流，分享，交友，新的阅读生活由此开启">
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
var xx= "${xx!}"; 
	function getX(){
		return xx;
	}
$(function(){
	$.magmeShow(".sideLeft .theme");
	$("#bg img").coverImg();
});
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
<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>

<!--body-->
<div class="body pageM1 noBg clearFix">
	<div class="sideLeft" id="Dashboard">
        <!--topBar-->
    	<div class="topBar">
            <a class="head" href="javascript:void(0)">
                <img src="<#if session_user.avatar?? && session_user.avatar!="" >${systemProp.profileServerUrl}${session_user.avatar!}<#else>${systemProp.staticServerUrl}/v3/images/head100.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/v3/images/head100.gif'" />
                <#if (session_user.reserve1??) && (session_user.reserve1=="M") ><em class="png m" title="麦米认证编辑">M</em></#if>
                <div class="more">
                    <span id="changeAvatar" class="a1">更换头像</span>
                     <!--<span class="a2">更换背景</span> -->
                </div>
            </a>
            <a class="png send" href="${systemProp.appServerUrl}/sns/creative.action?operate=works" title='发布'>发布</a>
            <a class="png home" href="${systemProp.appServerUrl}/sns/user-index!home.action" title='首页'>首页</a>
            <a class="png favorite" href="${systemProp.appServerUrl}/sns/user-index!enjoy.action" title='收藏'>收藏</a>
            <a class="png comment" href="${systemProp.appServerUrl}/sns/comment.action" title='评论'>评论
            <#if commentFlag?? && commentFlag&gt;0>
				<span>${commentFlag}</span>
			</#if>
            </a>
            <a class="png mycut" href="javascript:void(0)" title='切米'>切米</a>
        </div>
		<!--theme start-->
        <#list creativeList as c>
        	<div class="theme">
	        	<#if c.cType==9>
		        	<div class="content conForward clearFix">
		                <div class="conPlay">
		                    <a href="${systemProp.appServerUrl}/index-detail.action?itemId=${c.id}&type=event">
		                        <div class="photo"><img src="${systemProp.fpageServerUrl}/event${c.magazineUrl!}" alt="${c.title!}"></div>
		                        <div class="uInfo png">
		                            <div class="userHead png">
		                                <div class="head" href="javascript:void(0)"><img src="<#if c.avatar?? && c.avatar!="">${systemProp.publishProfileServerUrl}${c.avatar!}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
		                                <strong class="infoName">${stringSub(c.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong><em class="png p" title="麦米认证杂志"></em></div>
		                            </div>
		                        </div>
		                        <h2 class='til' til='${c.title!}' url='${systemProp.appServerUrl}/sns/c${c.id!}/' >${c.title!}<span class="date">${c.updateTime?string("yyyy-MM-dd")}</span></h2>
		                        <div class="text">
		                            <p>${c.content!}</p>
		                        </div>
		                    </a>
		                    <div class="userList">
		                    </div>
		                    <div cre="${c.id}" class="tools png">
		                        <em title="喜欢" ct='9' class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em>
		                    </div>
		                </div>
		                <div class="forwardRight">
		                     <#if c.fpageEventComment.size()==0>
		                        		<div class="nullInfo">
									             还没有任何评论哦~
					                    </div>
		                     <#else>
		                	<div class="conReplyOuter">
		                        <div class="conReply conReplyBig">
		                        	<#list c.fpageEventComment?reverse as cfs >
		                        	    <#assign commentcontent=cfs.content>
		                        	    <#if cfs.status==0>
		                        	   	   <#assign commentcontent="该评论已删除！">    
		                        	    </#if>
		                        		<#if cfs_index%2==0>
		                        			 <div class="bl">
		                        		<#else>
		                        			<div class="br">
		                        		</#if>
	                        				<a title="${cfs.user.nickName!}" href="<#if ((session_user)??) && (session_user.id==cfs.user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${cfs.user.id}/</#if>" class="head">
		                        				<img src="<#if cfs.user.avatar?? && cfs.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cfs.user.avatar,'46')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
		                        				<p><span>
		                        				<#if (session_user.id)?? && session_user.id==cfs.user.id>
												       <#if !cfs.status?? || (cfs.id?? && cfs.status?? && cfs.status==1)>
												    	<del commentId="${cfs.id}" type="event"></del>
												       </#if>
												 </#if>
		                        				<strong><a href="<#if ((session_user)??) && (session_user.id==cfs.user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${cfs.user.id}/</#if>">${stringSub(cfs.user.nickName!,20)}</a>
		                        			 	${cfs.createdTime?string('yyyy-MM-dd')}</strong>${commentcontent}</span></p><em></em>
		                        		</div>
		                        	</#list>
		                        </div>
		                    </div>
		                    </#if>
		                    <div cid='${c.id}' class="conReplySend">
		                        <input type="text" class="input" tips="请输入您的评论" maxlength='196' /><a class="btnGB">评论</a>
		                    </div>
		                </div>
		            </div>
	        	<#else>
	        		<#if c.creativeEx.size()==1>
	        			<#list c.creativeEx as ex>
	        				<#if ex.conType==2>
			        			<div class="content conPhoto clearFix">
					                <div class="conPlay">
					                    <div class="inner">
					        				<a text='${ex.content!}' w='${ex.w!}' h='${ex.h!}' pic='${systemProp.staticServerUrl}${ex.imgPath}' class="a0 pic cover">
		        							<img width='${creativeResize("w",ex.w!,ex.h!,500)}' height='${creativeResize("h",ex.w!,ex.h!,500)}' src="${systemProp.staticServerUrl}${avatarResize(ex.imgPath,'500')}"  />
		        							</a>
					                    </div>
					                    <#if c.creativeEx.size()&gt;7>
					                    	<a class="more "></a>
					                    </#if>
					                </div>
					                <div class="uInfo">
					                	<#if c.magazineName?? && c.magazineName!="">
					                    	<h3 class="png"><a href="${c.magazineUrl!}" title="出自&nbsp;[&nbsp;${c.magazineName!}&nbsp;]">mask</a>
					                    		<img src="${systemProp.magServerUrl}/${c.publicationid!}/${c.issueid!}/1.jpg" />
					                    	</h3>
					                    </#if>
					                    <div class="userHead png">
					                        <a class="head" u='${c.userId}' href="${systemProp.appServerUrl}/sns/u${c.userId}/">
						                        <img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                        <strong class="infoName">${stringSub(c.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong>
						                        <#if c.ism?? && c.ism=='M'>
								            		<em class="png m" title="麦米认证编辑"></em>
								            	</#if>
							            	</a>
					                    </div>
					                    <div class="tagList">
						                	<#list c.tags as tag>
						                    	<a href="javascript:void(0)">${tag}</a>
						                    </#list>
						                </div>
					                </div>
					            	<h2 class='til' til='${c.title!}' url='${systemProp.appServerUrl}/sns/c${c.id!}/'>
					            	<#if c.activity==true><a href="${systemProp.appServerUrl}/v3/event/20120824/event.html" target="_blank" class="eventName">[十万年薪]</a></#if>
					            	<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a><span class="date">${c.updateTime?string("yyyy-MM-dd")}</span></h2>
					                <div class="text">
					                    <p>${c.content!}</p>
					                    <#if c.cut!>
					                    	<a class="readMore" href="${systemProp.appServerUrl}/sns/c${c.id!}/" target="_blank">继续阅读</a>
					                    </#if>
					                </div>
					             	<div cre="${c.id}" class="tools png">
					                    <em title="转发" class="iconShare png"></em>
					                    <em title="评论" class="iconMoreInfo png"></em>
					                	<em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em>
					                </div>
					            	<div class="userList">
					            		<#list c.creativeUser as u>
						                    <a href="${systemProp.appServerUrl}/sns/u${u.userId}/" u='' title="${u.nickname!}">
						                    	<img src="${systemProp.profileServerUrl}<#if u.avatar?? && u.avatar!="" >${avatarResize(u.avatar,'60')}</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                    </a>
						                    <#if !u_has_next >
						                    <span>${u.num}个参与人</span>
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
					                            <a class="head" u='${c.userId}' href="${systemProp.appServerUrl}/sns/u${c.userId}/">
							                        <img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
							                        <#if c.ism?? && c.ism=='M'>
									            		<em class="png m" title="麦米认证编辑"></em>
									            	</#if>
							            		</a>
					                        </div>
					                        <h2 class='til' til='${c.title!}' url='${systemProp.appServerUrl}/sns/c${c.id!}/'>
					                        <#if c.activity==true><a href="${systemProp.appServerUrl}/v3/event/20120824/event.html" target="_blank" class="eventName">[十万年薪]</a></#if>
					                        <a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a></h2>
					                        <strong class="infoName">${stringSub(c.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong><span class="date">${c.updateTime?string("yyyy-MM-dd")}</span>
					                    </div>
					                    ${ex.path}
					                    <div class="text">
					                        <p>${c.content!}</p>
					                        <#if c.cut!>
						                    	<a class="readMore" href="${systemProp.appServerUrl}/sns/c${c.id!}/" target="_blank">继续阅读</a>
						                    </#if>
					                    </div>
					                    <div class="userList">
					                        <#list c.creativeUser as u>
							                    <a href="${systemProp.appServerUrl}/sns/u${u.userId}/" u='' title="${u.nickname!}">
							                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
							                    </a>
							                    <#if !u_has_next >
							                    <span>${u.num}个参与人</span>
							                    </#if>
						                    </#list>
					                    </div>
					                <div cre="${c.id}" class="tools png">
					                    <em title="转发" class="iconShare png"></em>
					                    <em title="评论" class="iconMoreInfo png"></em>
					                	<em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em>
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
					                        <a class="head" u='${c.userId}' href="${systemProp.appServerUrl}/sns/u${c.userId}/">
						                        <img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                        <strong class="infoName">${stringSub(c.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong>
						                        <#if c.ism?? && c.ism=='M'>
								            		<em class="png m" title="麦米认证编辑"></em>
								            	</#if>
							            	</a>
					                    </div>
					                    <div class="tagList">
						                	<#list c.tags as tag>
						                    	<a href="javascript:void(0)">${tag}</a>
						                    </#list>
						                </div>
					                </div>
					            	<h2 class='til' til='${c.title!}' url='${systemProp.appServerUrl}/sns/c${c.id!}/'>
					            	<#if c.activity==true><a href="${systemProp.appServerUrl}/v3/event/20120824/event.html" target="_blank" class="eventName">[十万年薪]</a></#if>
					            	<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a><span class="date">${c.updateTime?string("yyyy-MM-dd")}</span></h2>
					                <div class="text">
					                    <p>${c.content!}</p>
					                    <#if c.cut!>
					                    	<a class="readMore" href="${systemProp.appServerUrl}/sns/c${c.id!}/" target="_blank">继续阅读</a>
					                    </#if>
					                </div>
					               <div cre="${c.id}" class="tools png">
					                    <em title="转发" class="iconShare png"></em>
					                    <em title="评论" class="iconMoreInfo png"></em>
					                	<em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em>
					                </div>
					            	<div class="userList">
					            		<#list c.creativeUser as u>
						                    <a href="${systemProp.appServerUrl}/sns/u${u.userId}/" u='' title="${u.nickname!}">
						                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                    </a>
						                    <#if !u_has_next >
						                    <span>${u.num}个参与人</span>
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
			                    	<#list c.creativeEx as ex>
				                    	<#if ex.conType==3>
					        				<a text='${ex.content!}' pic='${ex.imgPath}' path='${ex.path}' class="<#if ex_index &lt; 7>a${ex_index}</#if> audio <#if ex_index==0>cover</#if>"><img src="${ex.imgPath!}" /></a>
					        			<#elseif ex.conType==4>
					        				<a text='${ex.content!}' pic='${ex.imgPath}' path='${ex.path}'  class="<#if ex_index &lt; 7>a${ex_index}</#if> video <#if ex_index==0>cover</#if>"><img src="${ex.imgPath!}" /></a>
					        			<#else>
					        				<a text='${ex.content!}' w='${ex.w!}' h='${ex.h!}' pic='${systemProp.staticServerUrl}${ex.imgPath}' class="<#if ex_index &lt; 7>a${ex_index}</#if> pic <#if ex_index==0>cover</#if>">
		        								<#if ex_index == 0>
		        									<img width='${creativeResize("w",ex.w!,ex.h!,500)}' height='${creativeResize("h",ex.w!,ex.h!,500)}' src="${systemProp.staticServerUrl}${avatarResize(ex.imgPath,'500')}"  />
			        							<#else>
			        								<img width='${creativeResize("w",ex.w!,ex.h!,100)}' height='${creativeResize("h",ex.w!,ex.h!,100)}' src="${systemProp.staticServerUrl}${avatarResize(ex.imgPath,'100')}"  />
			        							</#if>
		        							</a>
					        			</#if>
		        					</#list>
			                    </div>
			                    <#if c.creativeEx.size()&gt;7>
			                    	<a class="more "></a>
			                    </#if>
			                </div>
			                <div class="uInfo">
			                	<#if c.magazineName?? && c.magazineName!="">
			                    	<h3 class="png"><a href="${c.magazineUrl!}" title="出自&nbsp;[&nbsp;${c.magazineName!}&nbsp;]">mask</a>
			                    		<img src="${systemProp.magServerUrl}/${c.publicationid!}/${c.issueid!}/1.jpg" />
			                    	</h3>
			                    </#if>
			                    <div class="userHead png">
			                        <a class="head" u='${c.userId}' href="${systemProp.appServerUrl}/sns/u${c.userId}/">
				                        <img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
				                        <strong class="infoName">${stringSub(c.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong>
				                        <#if c.ism?? && c.ism=='M'>
						            		<em class="png m" title="麦米认证编辑"></em>
						            	</#if>
					            	</a>
			                    </div>
			                    <div class="tagList">
				                	<#list c.tags as tag>
				                    	<a href="javascript:void(0)">${tag}</a>
				                    </#list>
				                </div>
			                </div>
			            	<h2 class='til' til='${c.title!}' url='${systemProp.appServerUrl}/sns/c${c.id!}/'>
			            	<#if c.activity==true><a href="${systemProp.appServerUrl}/v3/event/20120824/event.html" target="_blank" class="eventName">[十万年薪]</a></#if>
			            	<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a><span class="date">${c.updateTime?string("yyyy-MM-dd")}</span></h2>
			                <div class="text">
			                    <p>${c.content!}</p>
			                    <#if c.cut!>
			                    	<a class="readMore" href="${systemProp.appServerUrl}/sns/c${c.id!}/" target="_blank">继续阅读</a>
			                    </#if>
			                </div>
			             	<div cre="${c.id}" class="tools png">
			                    <em title="转发" class="iconShare png"></em>
			                    <em title="评论" class="iconMoreInfo png"></em>
			                	<em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em>
			                </div>
			            	<div class="userList">
			            		<#list c.creativeUser as u>
				                    <a href="${systemProp.appServerUrl}/sns/u${u.userId}/" u='' title="${u.nickname!}">
				                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
				                    </a>
				                    <#if !u_has_next >
				                    <span>${u.num}个参与人</span>
				                    </#if>
			                    </#list>
			                </div>
			            </div>
	        		</#if>
	        		
	        	</#if>
    		</div>
        </#list>
         <#if creativeList.size()==0>
        	<div class="theme">
        		
        	</div>
        </#if>
    </div>
    
	<div class="sideRight">
    	<div class="con conInfo">
        	<a class="a1" href="${systemProp.appServerUrl}/sns/user-index!attention.action"><span>${attention}</span>关注</a>
        	<a class="a2" href="${systemProp.appServerUrl}/sns/user-index!fans.action"><span>${fans}</span>粉丝</a>
        	<a class="a3" href="${systemProp.appServerUrl}/sns/user-index!home.action"><span>${creative}</span>内容</a>
        </div>
    	<#--<div class="con conFavorite">
            <a class="a1" href="javascript:void(0)">
                <strong>收藏的内容</strong>
                <span>(888)</span>
            </a>
            <a class="a2" href="javascript:void(0)">
                <strong>收藏的事件</strong>
                <span>(666)</span>
            </a>
            <a class="a3" href="javascript:void(0)">
                <strong>收藏的杂志</strong>
                <span>(369)</span>
            </a>
        </div>-->
    	<#--<div class="con conMorePic">
    		<#list newCreative as ncv>
            	<div class="item">
            		<a <#if ((session_user)??) && (session_user.id==ncv.userId) >url="${systemProp.appServerUrl}/sns/user-index.action"<#else>url="${systemProp.appServerUrl}/sns/u${ncv.userId}/" </#if> href='javascript:void()' />
            			<span>
            				<#list ncv.ex as ex >
            					<#if ex_index==0>
            						<#if ex.conType==2>
            							<img src="${avatarResize(ex.imgPath!,'500')}"/>
            						<#else>
            							<img src="${ex.imgPath!}" />
            						</#if>
            					</#if>
            				</#list>
            			</span>
            		</a>
            	</div>
            </#list>
        </div>-->
    	<div id="r_u_list" class="con conUser">
    		<#list publicAuthor as author>
            	<a class="author_list <#if author_index==4>last</#if>" href="javascript:void(0);" <#if author_index&gt;4>style='display:none;'</#if> >
	                <img url='<#if ((session_user)??) && (session_user.id==author.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${author.userId}/</#if>' src="<#if author.avatar?? && author.avatar!="" >${systemProp.profileServerUrl}${avatarResize(author.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	                <strong>${author.nickname!}</strong>
	                <p><span>${author.num!}</span>作品</p>
	                <#if !session_user?? || session_user.id!=author.userId>
		                <#if author.isF?? && author.isF&gt;0>
		                	<#if author.userId!=9999>
		                		<em class='cancel' nick='${author.nickname!}' u='${author.userId}'>取消关注</em>
		                	</#if>
		               	<#else>
		               		<em class='atten' nick='${author.nickname!}' u='${author.userId}'>加关注</em>
		               	</#if>
	               	</#if>
	            </a>
            </#list>
            <a href="javascript:void(0)" class="more">换一批&nbsp;用户</a>
        </div>
    	<div id="cre_list" class="con conList">
    		<#list publicCreative as pcrv>
	            <a class="pcrv_list <#if pcrv_index==4>last</#if>"  href="${systemProp.appServerUrl}/sns/c${pcrv.cid}/" <#if pcrv_index&gt;4>style='display:none;'</#if>>
	                <strong>${pcrv.title!}</strong>
	                <span>(${pcrv.num!})</span>
	            </a>
            </#list>
            <a href="javascript:void(0)" class="more">换一批&nbsp;话题</a>
        </div>

    </div>
</div>
<span style="cursor:pointer;" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></span>
<a id="loading"  style="display:none" href="javascript:void(0);" class="loading32">loading...</a>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />

<#--<div id="bg" style="background-image:url(${systemProp.staticServerUrl}/v3/images/temp/bg.jpg);"><img src="${systemProp.staticServerUrl}/v3/images/temp/bg.jpg" width="1044" height="608" /></div>-->

<div class="popContent hide" id="uploadUserAvatarDialog">
    <fieldset>
        <h6>更换头像</h6>
        <form id="editAvatarForm" method="post" action="" onsubmit="return false;">
        	<input type="hidden" name="x" value="0" />
			<input type="hidden" name="y" value="0" />
			<input type="hidden" name="width" value="0" />
			<input type="hidden" name="height" value="0" />
			<input type="hidden" id="avatarFileName" name="avatarFileName"/>
        </form>
        <div class="imgArea">
        	<#if ((session_user.avatar)??)&&(session_user.avatar!="")>
				<a id="avatarBox" href="javascript:void(0)">
					<span><img id="avatar" name="avatar" src="${systemProp.profileServerUrl+session_user.avatar}" /></span>
				</a>
			<#else>
				<a id="avatarBox" href="javascript:void(0)" class="hide">
					<span><img id="avatar" name="avatar" src="${systemProp.staticServerUrl}/images/head150.gif" /></span>
				</a>
			</#if>
        </div>
        <div class="tCenter">
            <a id="submit" href="javascript:void(0)" class="btnBS" >确定</a>
            <a id="cancel" href="javascript:void(0)" class="btnWS" >取消</a>
            <#if ((session_user.avatar)??)&&(session_user.avatar!="")>
        		<a id="uploadBtn" class="btnAS" href="javascript:void(0)">
            		<span>重新上传头像</span>
            		<input id="avatarFile" name="avatarFile" type="file" class="inputFile" />
            	</a>
        	<#else>
        		<a id="uploadBtn" class="btnAB" href="javascript:void(0)">
            		<span>上传头像</span>
            		<input id="avatarFile" name="avatarFile" type="file" class="inputFile" />
            	</a>
        	</#if>
            <span class="tipsError" id="tipsError">请选择头像</span>
        </div>
    </fieldset>
</div>
<div id="forwardM1" class="popContent">
	<fieldset class="new">
    	<h6>转发到M1</h6>
        <div>
            <em><textarea maxlength="196" class="input g380" type="text"></textarea></em>
        </div>
        <div>
            <em class="floatr"><a class="btnGB">转发</a></em>
            <em><label><input checked=true class='ck_async'  type="checkbox" />同时评论</label></em>
        </div>
        <div class="count">您还可以输入<span>196</span>字</div>
    </fieldset>
</div>
<div id="userNewMsg" class="popContent hide">
	<fieldset>
    	<h6>发送私信给<span id="sns_mes_userName" name="userName"></span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>
</body>
</html>
