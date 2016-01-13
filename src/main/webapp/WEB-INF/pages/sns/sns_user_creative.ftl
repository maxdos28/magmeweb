<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title><#if creativeList?? && (creativeList?size)&gt;0  >${creativeList.get(0).title!}</#if> - 麦米网</title>
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
<script src="${systemProp.staticServerUrl}/v3/js/sns/snsuser.js"></script>
<script>
$(function(){
	$.magmeShow(".sideLeft .theme");
	$("#bg img").coverImg();
});
</script>

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
<input id="pageTimeLock" type="hidden" value="${pageTimeLock!}" />
<input id="_c_u_flag"  type="hidden" value="${userInfo.id}" />

	<div class="sideLeft" id="Dashboard">
		<style>
        	.eventArea20120812{ background:#ffffE9; width:540px; padding:10px 15px; margin-left:100px; margin-bottom:20px; box-shadow:0 1px 3px rgba(0,0,0,0.25);}
			.eventArea20120812 strong{ color:#B42547; font-size:16px;}
			.eventArea20120812 p{ color:#7D8B96; font-size:14px; line-height:1.6em; margin-top:5px;}
			.eventCorner20120812{}
        </style>
        <div id='10w_event_html' style="display:none" class="eventArea20120812">
        	<strong>吐槽几句吧，有机会获奖哦</strong>
            <p>通过自己微博转发，加上自己的评语，提交截图到猪八戒网站，有机会获得10元奖励哦。提交格式为：会员ID+点评截图 <a class="aBlue" target="_blank" href="${systemProp.appServerUrl}/v3/event/20120824/event.html">详情链接</a></p>
       	</div>
        
		<script>
	        $(function(){
	            $("#Dashboard .content .tools .iconMoreInfo").click();
	            
	            if(new Date()<Date.parse("2012/11/08 23:59")){
		            var tpv = getCookie("10w_event");
		            if(tpv==null){
		            	$("#10w_event_html").show();
				 		setCookie("10w_event","1");
					}
				}
	        });
        </script>
        <!-- theme start -->
         <#list creativeList as c>
        	<div class="theme">
	        	<#if c.cType==9>
	        	<#else>
	        		<#if c.creativeEx.size()==1>
	        			<#list c.creativeEx as ex>
	        				<#if ex.conType==2>
			        			<div class="content conPhoto clearFix">
			        				<div class="calendar">
					            		<span>${c.updateTime?string('yyyy')} &nbsp;/&nbsp;${c.updateTime?string('MM')}</span>
					            		<strong>${c.updateTime?string('dd')}</strong>
					            	</div>
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
					                        <a class="head" u='${c.userId}' href="<#if ((session_user)??) && (session_user.id==c.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${c.userId}/</#if>">
						                        <img src="<#if userInfo.avatar?? && userInfo.avatar!="" >${systemProp.profileServerUrl}${avatarResize(userInfo.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                        <strong class="infoName">${stringSub(userInfo.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong>
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
					            	<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a>
					            	<span class="date">${c.updateTime?string("yyyy-MM-dd")}</span></h2>
					                <div class="text">
					                    <p>${c.content!}</p>
					                </div>
					             	<div cre="${c.id}" class="tools png">
						             	<#if session_user?? && userInfo.id==session_user.id>
						             		<#if c.comefrom==0><em class='edit'><a  href="${systemProp.appServerUrl}/sns/creative!edit.action?cid=${c.id}">编辑</a></em></#if>
		                	 	 			<em class='delete'><a href="javascript:void(0);">删除</a></em>
		                	 	 		</#if>
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
	        						<div class="calendar">
					            		<span>${c.updateTime?string('yyyy')} &nbsp;/&nbsp;${c.updateTime?string('MM')}</span>
					            		<strong>${c.updateTime?string('dd')}</strong>
					            	</div>
					                <div class="conPlay">
					                    <a href="javascript:void(0)"><img src='${ex.imgPath!}' width="400" height="402" /></a>
					                </div>
					                <div class="audioRight">
					                	<div class="uInfo">
					                        <div class="userHead png">
					                            <a class="head" u='${c.userId}' href="<#if ((session_user)??) && (session_user.id==c.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${c.userId}/</#if>">
							                        <img src="<#if userInfo.avatar?? && userInfo.avatar!="" >${systemProp.profileServerUrl}${avatarResize(userInfo.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
							                        <#if c.ism?? && c.ism=='M'>
									            		<em class="png m" title="麦米认证编辑"></em>
									            	</#if>
							            		</a>
					                        </div>
					                        <h2 class='til' til='${c.title!}' url='${systemProp.appServerUrl}/sns/c${c.id!}/'>
					                        <#if c.activity==true><a href="${systemProp.appServerUrl}/v3/event/20120824/event.html" target="_blank" class="eventName">[十万年薪]</a></#if>
					                        <a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!}/">${c.title!}</a></h2>
					                        <strong class="infoName">${stringSub(userInfo.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong><span class="date">${c.updateTime?string("yyyy-MM-dd")}</span>
					                    </div>
					                    ${ex.path}
					                    <div class="text">
					                        <p>${c.content!}</p>
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
					                	<#if session_user?? && userInfo.id==session_user.id>
						             		<#if c.comefrom==0><em class='edit'><a  href="${systemProp.appServerUrl}/sns/creative!edit.action?cid=${c.id}">编辑</a></em></#if>
		                	 	 			<em class='delete'><a href="javascript:void(0);">删除</a></em>
	                	 	 			</#if>
					                    <em title="转发" class="iconShare png"></em>
					                    <em title="评论" class="iconMoreInfo png"></em>
					                	<em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em>
					                </div>
					                </div>
					            </div>
		        			<#elseif ex.conType==4>
		        				<div class="content conVideo clearFix">
		        					<div class="calendar">
					            		<span>${c.updateTime?string('yyyy')} &nbsp;/&nbsp;${c.updateTime?string('MM')}</span>
					            		<strong>${c.updateTime?string('dd')}</strong>
					            	</div>
					                <div class="conPlay">
					               	 	<a href="javascript:void(0)"  path='${ex.path}'  class="mask png"><span class="png">play</span></a>
					                    <img src="${ex.imgPath!}" />
					                </div>
					                <div class="uInfo">
					                    <div class="userHead png">
					                        <a class="head" u='${c.userId}' href="<#if ((session_user)??) && (session_user.id==c.userId) >${systemProp.appServerUrl}//sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${c.userId}/</#if>">
						                        <img src="<#if userInfo.avatar?? && userInfo.avatar!="" >${systemProp.profileServerUrl}${avatarResize(userInfo.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
						                        <strong class="infoName">${stringSub(userInfo.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong>
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
					                </div>
					               <div cre="${c.id}" class="tools png">
						               	<#if session_user?? && userInfo.id==session_user.id>
						             		<#if c.comefrom==0><em class='edit'><a  href="${systemProp.appServerUrl}/sns/creative!edit.action?cid=${c.id}">编辑</a></em></#if>
		                	 	 			<em class='delete'><a href="javascript:void(0);">删除</a></em>
		                	 	 		</#if>
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
	        				<div class="calendar">
			            		<span>${c.updateTime?string('yyyy')} &nbsp;/&nbsp;${c.updateTime?string('MM')}</span>
			            		<strong>${c.updateTime?string('dd')}</strong>
			            	</div>
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
			                        <a class="head" u='${c.userId}' href="<#if ((session_user)??) && (session_user.id==c.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${c.userId}/</#if>">
				                        <img src="<#if userInfo.avatar?? && userInfo.avatar!="" >${systemProp.profileServerUrl}${avatarResize(userInfo.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
				                        <strong class="infoName">${stringSub(userInfo.nickname,24)}<#if c.comefrom!=0><span title='转载自&nbsp;${c.comefromNick!}'>[转载]</span></#if></strong>
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
			                </div>
			             	<div cre="${c.id}" class="tools png">
			             		<#if session_user?? && userInfo.id==session_user.id>
				             		<#if c.comefrom==0><em class='edit'><a  href="${systemProp.appServerUrl}/sns/creative!edit.action?cid=${c.id}">编辑</a></em></#if>
                	 	 			<em class='delete'><a href="javascript:void(0);">删除</a></em>
                	 	 		</#if>
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
       <#-- 推荐阅读 -->
       <#if creativeList.size()&gt;0>
       	  <div class="theme">
			<div class="content conMore clearFix">
				<strong>推荐阅读</strong>
	        	<#list recommend as r>
	        		<div class="item">
	                	<a href="${systemProp.appServerUrl}/sns/c${r.creativeId!}/" target="_blank"><div>
	                	<#if r.conType??&&r.conType==4>
	                		<img src="${r.imgPath!''}" />
	                	<#else>
	                		<img src="${systemProp.staticServerUrl}${avatarResize(r.imgPath!,'100')}" />
	                	</#if>
	                	</div>
	                	<p>${r.content!}</p>
                    </a>
                </div>
	        	</#list>
        	</div>
          </div>
        </#if>
         <!-- theme end -->
    </div>
    
    
    
	<div class="sideRight">
    	<div class="con conHead">
        	<a class="head" href="javascript:void(0);" title='${userInfo.nickname!}'>
        	 	<img url="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/u${userInfo.id}/<#else>${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/</#if>" src="${systemProp.profileServerUrl}${userInfo.avatar!}" onerror="this.src='${systemProp.staticServerUrl}/images/head172.gif'" />
            	<strong>${stringSub(userInfo.nickname,24)}</strong>
                <!--<em class="png p"></em>-->
                <#if (userInfo.ism??) && (userInfo.ism=="M") >
                	<em class="png m"></em>
                </#if>
            </a>
        	<div class="btn">
                <#if !session_user?? || userInfo.id!=session_user.id>
                	<#if !userInfo.isF??>
                		<a u='${userInfo.id}' nick='${userInfo.nickname!}' class="iconAdd" href="javascript:void(0)">加关注</a>
	                <#else>
	                	<#if userInfo.id!=9999>
	                	<a u='${userInfo.id}' nick='${userInfo.nickname!}' class="iconCancel" href="javascript:void(0)">取消关注</a>
	                	</#if>
	                </#if>
	                <a class="iconMessage" href="javascript:void(0)">发消息</a>
	                <a class="iconRss" href="${systemProp.appServerUrl}/sns/rss.action?u=${userInfo.id}" target="_blank">RSS</a>
                </#if>
            </div>
        </div>
    	<div class="con conInfo">
        	<a class="a1" href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!attention.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!attention.action</#if>"><span>${userInfo.attention}</span>关注</a>
        	<a class="a2" href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!fans.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!fans.action</#if>"><span>${userInfo.fans}</span>粉丝</a>
        	<a class="a3" href="<#if session_user?? && userInfo.id==session_user.id>${systemProp.appServerUrl}/sns/user-index!home.action<#else>${systemProp.appServerUrl}/sns/u${userInfo.id}/</#if>"><span>${userInfo.creativeCount}</span>内容</a>
        </div>
    	<#--<div class="con conMorePic">
            <#list newCreative as ncv>
            	<div class="item">
            		<a <#if ((session_user)??) && (session_user.id==ncv.userId) >url="${systemProp.appServerUrl}/sns/user-index.action"<#else>url="${systemProp.appServerUrl}/sns/u${ncv.userId}/" </#if> href='javascript:void()'>
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
    	<div class="con conFans">
        	<div class="conBody clearFix">
        		<#if creativeNumUser?? && creativeNumUser.size()&gt;0>
	        		<#list creativeNumUser as cn>
	        			<a href="${systemProp.appServerUrl}/sns/u${cn.id}/" title="${cn.nickname}">
	        				<img src="<#if cn.avatar?? && cn.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cn.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	        			</a>
	        		</#list>
	        	<#else>
	        		<a href="javascript:void(0)" style='width:80%;text-align: center;'>
        				<br/>
        				还没有粉丝哦！
        			</a>
        		</#if>
            </div>
            <#if creativeNumUser?? && creativeNumUser.size()&gt;0>
            	<a href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!fans.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!fans.action</#if>" class="more"><#if session_user?? && session_user.id=userInfo.id>wo<#else>Ta</#if>的粉丝</a>
            <#else>
            	<a href="javascript:void(0)" class="more"><#if session_user?? && session_user.id=userInfo.id>wo<#else>Ta</#if>的粉丝</a>
            </#if>
        </div>
         <div class="con conBanner">
        	<a href="${systemProp.appServerUrl}/v3/event/20120824/event.html"><img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_activity.jpg" /></a>
        </div>
    	<div id="r_u_list" class="con conUser">
           <#list publicAuthor as author>
            	<a class="author_list <#if author_index==4>last</#if>" href="javascript:void(0)" <#if author_index&gt;4>style='display:none;'</#if> >
	                <img url='<#if ((session_user)??) && (session_user.id==author.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${author.userId}/</#if>' src="<#if author.avatar?? && author.avatar!="" >${systemProp.profileServerUrl}${avatarResize(author.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	                <strong>${author.nickname!}</strong>
	                <p><span>${author.num!}</span>作品</p>
	                <#if !session_user?? || session_user.id!=author.userId>
		                <#if author.isF?? && author.isF&gt;0>
		                	<#if author.userId!=9999>
		                	<em class='cancel' nick='${author.nickname}' u='${author.userId}'>取消关注</em>
		                	</#if>
		               	<#else>
		               		<em class='atten' nick='${author.nickname}' u='${author.userId}'>加关注</em>
		               	</#if>
	               	</#if>
	            </a>
            </#list>
            <a href="javascript:void(0)" class="more">换一批&nbsp;作者</a>
        </div>
    	<div  id="cre_list" class="con conList">
           <#list publicCreative as pcrv>
	            <a class="pcrv_list" <#if pcrv_index==4>style='box-shadow:0 1px 3px rgba(0, 0, 0, 0.25)'</#if>  href="${systemProp.appServerUrl}/sns/c${pcrv.cid}/" <#if pcrv_index&gt;4>style='display:none;'</#if>>
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
    	<h6>发送私信给<span name="userName">${userInfo.nickname!}</span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="${userInfo.id}" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>
<#if (session_user)?? && session_user.id&gt;0>
	<input id="isLogin"  type="hidden" value="1" />
<#else>
	<input id="isLogin"  type="hidden" value="0" />
</#if>
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
