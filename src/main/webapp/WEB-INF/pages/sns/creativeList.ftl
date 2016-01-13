 <#list creativeList as c>
	<#if c?? > 
	<div class="theme">
    	<#if c.cType==9>
        	<div class="content conForward clearFix">
                <div class="conPlay">
                    <a href="${systemProp.appServerUrl}/index-detail.action?itemId=${c.id}&type=event">
                        <div class="photo"><img src="${systemProp.fpageServerUrl}/event${c.magazineUrl!}" alt="${c.title!}"></div>
                        <div class="uInfo png">
                            <div class="userHead png">
                                <div class="head" href="javascript:void(0)"><img src="<#if c.avatar?? && c.avatar!="">${systemProp.publishProfileServerUrl}${c.avatar!}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
                                <strong class="infoName">${c.nickname}</strong><em class="png p" title="麦米认证杂志"></em></div>
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
                    					<img src="<#if cfs.user.avatar?? && cfs.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cfs.user.avatar,'46')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'"  /></a>
                    			 		<p><span>
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
			        				<a text='${ex.content!}' pic='${ex.imgPath}' path='${ex.path}' class="<#if ex_index &lt; 7>a${ex_index}</#if> audio <#if ex_index==0>cover</#if>"><img width="70" src="${ex.imgPath!}" /></a>
			        			<#elseif ex.conType==4>
			        				<a text='${ex.content!}' pic='${ex.imgPath}' path='${ex.path}'  class="<#if ex_index &lt; 7>a${ex_index}</#if> video <#if ex_index==0>cover</#if>"><img  width="70" src="${ex.imgPath!}" /></a>
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
		                    	<img src="${systemProp.profileServerUrl}<#if u.avatar?? && u.avatar!="" >${avatarResize(u.avatar,'60')}</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
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
	</#if>
</#list>
 <#if creativeList.size()==0>
	<div class="theme">
	</div>
</#if>
<script>
$(function(){
	$(".sideLeft .theme .conPhoto .conPlay a img").coverImg();
	$(".sideLeft .theme .conForward .conReplyOuter").each(function(){
		$(this).jScrollPane().data('jsp').scrollToY(999999);
	})
	//转发按钮移入
	var forwardTools = "<div id='shareDiv' style='display:none'><p><a title='新浪微博' tp='tsina' href='javascript:void(0)' class='weibo png'></a><a href='javascript:void(0)' tp='tqq' title='腾讯微博' class='tencent png'></a><a title='开心网' tp='kaixin'  href='javascript:void(0)' class='kaixin png'></a><a title='人人网' tp='renren' href='javascript:void(0)' class='renren png'></a><a title='M1社区' href='javascript:void(0)' tp='m1' class='m1 png'></a></p></div>";
	$("em.iconShare").mouseenter(function(){
		$(this).append(forwardTools).find("div").fadeIn(200);
	}).mouseleave(function(){
		$(this).find("div").remove();
	});
	
	$(".sideLeft .theme").find(".userHead>.head>img").mouseenter(function(){
		var $obj = $(this).parents(".userHead");
		if($obj.find(".tool").length==0){
			fnLoadUserInfo($obj);
		}else{
			var tou = $obj.find("a").attr("u");;
			var tcu = $obj.find(".tool").find(".sns_mes").attr("u");
			if(tou!=tcu || $obj.find(".inner").length>1){
				 $obj.find(".tool").remove();
				 fnLoadUserInfo($obj);
			}
		}
		$obj.find(".tool").stop(true,true).fadeIn(200);;
	});
	$(".sideLeft .theme").find(".userHead").mouseleave(function(){
		var $obj = $(this);
		$obj.find(".tool").fadeOut(100);
	});
});

</script>
