<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${user.nickName!}的作品 - 麦米网Magme </title>
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
<script src="${systemProp.staticServerUrl}/v3/js/sns/m1_index.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/m1.js"></script>

<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1V4.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/userindex.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/snsuser.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/userConfig.js"></script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/m1square.js"></script>

<script>
$(function(){
	$(".sideLeft .theme .conPhoto .conPlay a img").coverImg();
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
<!--body-->
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>
<div class="body pageM1 noBg clearFix">
    <input type="hidden" id="userIdForIndex" value="${userId!''}"/>
	<div class="sideLeft" id="Dashboard">
	     <!--topBar-->
	    <#if (session_user.id)?? && (user.id)?? && session_user.id==user.id>
	    	<div class="userBar">
	            <a class="png send" href="${systemProp.appServerUrl}/sns/article-pub.action" title='发布'>发布</a>
	            <a class="png home" href="${systemProp.appServerUrl}/sns/square.action" title='m1广场'>广场</a>
	            <a class="png comment" href="${systemProp.appServerUrl}/sns/comment.action" title='评论'>评论
				<#if commentFlag?? && commentFlag&gt;0>
				 <span>${commentFlag}</span>
				</#if>
	            </a>
	        </div>
        </#if>
        
        <!-- theme start -->
        <#if creativeList?? && (creativeList.size()>0)>
        <#list creativeList as c>
         <div class="theme">
			<div class="content <#if c.imagePath??>conPhoto<#else>conNull</#if> clearFix">
				<#if !(session_user??)>
					<div class="calendar">
	            		<span>${c.updateTime?string('yyyy')} &nbsp;/&nbsp;${c.updateTime?string('MM')}</span>
	            		<strong>${c.updateTime?string('dd')}</strong>
	            	</div>
            	</#if>
            	<#if c.imagePath??>
                <div class="conPlay">
                    <div class="inner">
        				<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!'0'}/" w='${c.width!}' h='${c.high!}' pic="" class="a0 pic cover">
						<img width='${c.width!}' height='${c.high!}'  src="<#if (c.imagePath)??>${systemProp.staticServerUrl}${avatarResize(c.imagePath,'max_800')}</#if>"  />
						</a>
                    </div>
                </div>
                </#if>
                <div class="uInfo">
                   <#--
                	<#if (c.magazineName)?? && c.magazineName!="">
                    	<h3 class="png">
                    	    <a target="_blank" href="${c.magazineUrl!''}" title="出自&nbsp;[&nbsp;${c.magazineName!''}&nbsp;]">mask</a>
                    		<img src="${systemProp.magServerUrl}/${c.publicationid!''}/${c.issueid!''}/1.jpg" />
                    	</h3>
                    </#if>-->
                    <div class="userHead png">
                        <a class="head" u='${c.userId}' href="${systemProp.appServerUrl}/sns/u${c.userId}/">
	                        <img src="<#if (c.avatar)?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	                        <strong class="infoName">${stringSub(user.nickName,24)}</strong>
	                        <#if (c.reserver1)?? && c.reserver1=='M'>
			            		<em class="png m" title="麦米认证编辑"></em>
			            	</#if>
		            	</a>
                    </div>
                    <div class="tagList">
                      <#if (c.tagStrList)?? && (c.tagStrList.size()>0)>
	                	<#list c.tagStrList as tag>
	                    	<a  target="_blank"  href="${systemProp.appServerUrl}/sns/square.action?tagName=${encode(tag)}">${tag}</a>
	                    </#list>
	                  </#if>
	                </div>
                </div>
            	<h2 class='til' til="${c.firstTitle!''}" url="${systemProp.appServerUrl}/sns/c${c.id!''}/">
            	<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!''}/">${c.firstTitle!''}</a><span class="date"><#if c.updateTime??>${c.updateTime?string("yyyy-MM-dd")}</#if></span></h2>
                <div class="text">
                    <p>${c.content!''}</p>
                    <a class="readMore" href="${systemProp.appServerUrl}/sns/c${c.id!''}/" target="_blank">继续阅读</a>
                </div>
             	<div cre="${c.id!''}" class="tools png">
             	<#if (session_user.id)?? && (user.id)?? && session_user.id==user.id>
             		<em class="edit"><a href="${systemProp.appServerUrl}/sns/article-pub!edit.action?creativeId=${c.id!''}">编辑</a></em>
					<em class="delete"><a href="javascript:void(0);">删除</a></em>
				</#if>
                <em title="评论" commentCreativeId="${c.id!'0'}" class="iconMoreInfo png"></em>
                <em title="喜欢" class="iconHearted png"></em>
                </div>
            </div>
           </div>
          </#list>
         </#if>	
        <#if !(creativeList??) || creativeList.size()==0>
        	<div class="theme">
        		
        	</div>
        </#if>
         <!-- theme end -->
    </div>
    
    
    
	<div class="sideRight">
    	<div class="con conHead">
        	<a class="head" href="javascript:void(0);" title='${user.nickname!}'>
        	 	<img id="userAvatarForM1"  src="<#if (user.avatar)?? && user.avatar!="" >${systemProp.profileServerUrl}${user.avatar!}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head172.gif'" />
            	<strong>${stringSub(user.nickName,24)}</strong>
                <!--<em class="png p"></em>-->
                <#if (user.reserve1)?? && (user.reserve1=="M") >
                	<em class="png m"></em>
                </#if>
                <#if (session_user.id)?? && (user.id)?? && session_user.id==user.id>
	                <div class="more">
	                    <span id="changeAvatar" class="a1">更换头像</span>
	                </div>
                </#if>
            </a>
        	<div class="btn">
                <#if !session_user?? || user.id!=session_user.id>
                	<#if !user.isF??>
                		<a u='${user.id}' nick='${user.nickName!}' class="iconAdd" href="javascript:void(0)">加关注</a>
	                <#else>
	                	<#if user.id!=9999>
	                	   <a u='${user.id}' nick='${user.nickName!}' class="iconCancel" href="javascript:void(0)">取消关注</a>
	                	</#if>
	                </#if>
	                <a class="iconMessage" href="javascript:void(0)">发消息</a>
                </#if>
            </div>
        </div>
    	<div class="con conInfo">
        	<a class="a1" href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!attention.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!attention.action</#if>"><span>${userInfo.attention}</span>关注</a>
        	<a class="a2" href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!fans.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!fans.action</#if>"><span>${userInfo.fans}</span>粉丝</a>
        	<a class="a3" href="${systemProp.appServerUrl}/sns/u${userInfo.id}/"><span>${userInfo.creativeCount}</span>内容</a>
        </div>
        <#-- 
    	<div class="con conFans">
        	<div class="conBody clearFix">
        		<#if creativeNumUser?? && creativeNumUser.size()&gt;0>
	        		<#list creativeNumUser as cn>
	        			<a href="${systemProp.appServerUrl}/sns/u${cn.id}/" title="${(cn.nickName)!''}">
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
            	<a href="<#if !session_user?? || user.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!fans.action?u=${user.id}<#else>${systemProp.appServerUrl}/sns/user-index!fans.action</#if>" class="more"><#if session_user?? && session_user.id=user.id>wo<#else>Ta</#if>的粉丝</a>
            <#else>
            	<a href="javascript:void(0)" class="more"><#if session_user?? && session_user.id=user.id>wo<#else>Ta</#if>的粉丝</a>
            </#if>
        </div>-->
        
         <#if similarAttention?? && similarAttention.size()&gt;0>
        <div class="con conFans">
	        <div class="conBody clearFix">
	        	<#list similarAttention as cn>
	    			<a href="${systemProp.appServerUrl}/sns/u${cn.id}/" title="${cn.nickname}">
	    				<img src="<#if cn.avatar?? && cn.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cn.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	    			</a>
	    		</#list>
	        </div>
	        <a href="javascript:void(0)" class="more">我和他都关注了</a>
        </div>
        </#if>
        <#if similarFans?? && similarFans.size()&gt;0>
        <div class="con conFans">
	        <div class="conBody clearFix">
	        	<#list similarFans as cn>
	    			<a href="${systemProp.appServerUrl}/sns/u${cn.id}/" title="${(cn.nickname)!''}">
	    				<img src="<#if cn.avatar?? && cn.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cn.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
	    			</a>
	    		</#list>
	        </div>
	        <a href="javascript:void(0)" class="more">这些人也关注了他</a>
        </div>
        </#if>
        
        
        <div class="con conUser" id="changeUserListDiv">
    		<#if userInfoList?? && userInfoList?size gt 0 >
    			<#list userInfoList as u>
    				<a href="http://www.magme.com/sns/u${u.id}/">
		                <img src="<#if u.avatar?? && u.avatar?length gt 0 >http://static.magme.com/profiles/${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
		                <strong>${u.nickname!''}</strong>
		                <p><span>${u.creativeCount!'0'}</span>作品</p>
		               
		            </a>
    			</#list>
    		</#if>
            <a href="javascript:void(0)" id="changeUserList" begin="0" size="5" class="more">换一批&nbsp;作者</a>
        </div>
        
        <#--
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
        </div>-->
    </div>
</div>
<span style="cursor:pointer;" class="clickLoadMore" id="clickLoadMoreForIndex"><span>狂点这里&nbsp;&nbsp;查看更多</span></span>
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
    	<h6>发送私信给<span name="userName">${user.nickName!}</span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="${user.id!'0'}" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>

<div class="popContent hide" id="uploadUserAvatarDialog">
    <fieldset>
        <h6>更换头像</h6>
        <form id="editAvatarForm" method="post" action="/user-update!saveAvatarJson.action" onsubmit="return false;">
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

<#if (session_user)?? && session_user.id&gt;0>
	<input id="isLogin"  type="hidden" value="1" />
<#else>
	<input id="isLogin"  type="hidden" value="0" />
</#if>
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
