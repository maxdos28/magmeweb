<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>图片精选&nbsp;麦米网Magme&nbsp;-&nbsp;世界新杂志&nbsp;发现新内容</title>
<meta name="Keywords" content="麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="麦米网提供数百种最新杂志在线免费阅读，财经，旅游，娱乐，时尚，汽车，体育，数码生活等。在麦米看书，交流，分享，交友，新的阅读生活由此开启">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />
<style type="text/css" rel="stylesheet">
	.reg {display:none;}
	.login {display:none;}
</style>
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
<input id="pageTimeLock" name="pageTimeLock" type="hidden" value="${pageTimeLock!}" />
	<div class="sideLeft" id="Dashboard">
		<!--theme start-->
        <#list creativeList as c>
        	<div class="theme">
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
					                    	<img src="<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
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
		            	<a target="_blank"  href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a><span class="date">${c.updateTime?string("yyyy-MM-dd")}</span></h2>
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
	        		
    		</div>
        </#list>
       <!--theme end-->         
    </div>
    
	<div class="sideRight">
		<div id="rightInner" style="width:238px;">
            <div class="con conRegister">
                <a id='snsbtnWB' href="${systemProp.appServerUrl}/sns/m1-register-and-login.action" class="btnWB">登录</a>
                <a id='snsbtnGB' href="${systemProp.appServerUrl}/sns/m1-register-and-login.action#register" class="btnGB">注册</a>
            </div>
    
            <div id="r_u_list" class="con conUser">
                <#list publicAuthor as author>
            	<a class="author_list <#if author_index==4>last</#if>" href="javascript:void(0);" <#if author_index&gt;4>style='display:none;'</#if> >
	                <img url='${systemProp.appServerUrl}/sns/u${author.userId}/' src="<#if author.avatar?? && author.avatar!="" >${systemProp.profileServerUrl}${avatarResize(author.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	                <strong>${author.nickname!}</strong>
	                <p><span>${author.num!}</span>作品</p>
	                <em nick='${author.nickname!}' u='${author.userId}' class='atten'>加关注</em>
	            </a>
            </#list>
                <a href="javascript:void(0)" class="more">换一批&nbsp;作者</a>
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
		 <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ss">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ss_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=qc">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_qc_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=jj">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_jj_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ye">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ye_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ly">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ly_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ms">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ms_m.jpg" width="238" />
            </a>
        </div>
    </div>
</div>
<span style="cursor:pointer;" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></span>
<a  id="loading"  style="display:none" href="javascript:void(0);" class="loading32">loading...</a>

<div id="userNewMsg" class="popContent hide">
	<fieldset>
    	<h6>发送私信给<span id='sns_mes_userName' name="userName"></span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
