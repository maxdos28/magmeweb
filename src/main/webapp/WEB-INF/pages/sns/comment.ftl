<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title> 我的评论(${session_user.nickName!}) - 麦米网</title>
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
<script src="${systemProp.staticServerUrl}/v3/js/jquery.autoTextarea.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/comment_scroll.js"></script>

<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1V4.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/m1.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/userConfig.js"></script>

<script>
$(function(){
	$.magmeShow(".sideLeft .theme");
	$("#bg img").coverImg();
	//加载评论tab
	if(isIE6){
		$.jquerytagbox("#myReplyTab",1);
		$("#myReplyTab .ctrl .a1").click();
	}else{
		$.jquerytagbox("#myReplyTab",0);
	}
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
    	<div class="userBar">
            <a class="png send" href="${systemProp.appServerUrl}/sns/article-pub.action" title='发布'>发布</a>
            <a class="png home" href="${systemProp.appServerUrl}/sns/square.action" title='m1广场'>广场</a>
            <a class="png comment" href="${systemProp.appServerUrl}/sns/comment.action" title='评论'>评论
			<#if commentFlag?? && commentFlag&gt;0>
			 <span>${commentFlag}</span>
			</#if>
            </a>
        </div>
        
		
		<div class="jqueryTagBox myReplyList" id="myReplyTab">
			<input id='sns_p_n_f' type="hidden" value='${page}' />
			<input id='sns_comment_op' type="hidden" value="${op!}" />
			<input id='page_time_lock'  type="hidden" value='${pageTimeLock!}' />
			
            <div class="ctrl">
                <div class="a1">收到的评论</div>
                <div class="a2">发出的评论</div>
                <p class="search">
                 <!--   <span class="searchInput">
                        <input type="text" tips="search" />
                        <a href="#" class="search" >搜索</a>
                    </span>-->
                </p>
            </div>
            <div class="doorList">
                <div class="item item1">
                	<#list commentList as c>
                		<div class="cList" com='${c.id}' ct='${c.ct}'>
	            			<a class="head" href="${systemProp.appServerUrl}/sns/u${c.uid}/">
	            				<img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
	                        <p><strong class='nickName' u='${c.uid}' title='${c.nickname!}'>${stringSub(c.nickname,24)}</strong><span class="time">${c.createTime?string("yyyy.MM.dd")}</span><span class="text"><#if c.ct==6>回复<#elseif c.ct=5>评论</#if>我的<#if c.ct==6>评论<#else>作品</#if></span>
	                        	<span class="works" c='${c.cid}' title='${c.title!}'>
	                        	<#if c.ct==5>
	                        		<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.cid}/">
	                        			“${stringSub(c.title!,18)}”
	                        		</a>
	                        	<#else>
	                        		“${stringSub(c.title!,18)}”
	                        	</#if>
	                        	</span>
	                        </p>
	                        <p>${c.content!}</p>
	                        <a class="ctrl" href="javascript:void(0);">回复</a>
	                    </div>
                	</#list>
                	<#if commentList.size()==0>
                		<div class="cList nullInfo">还没有任何评论哦~</div>
                	</#if>
                </div>
                <div class="item item2">
                	<#list commentListTo as c>
	                    <div class="cList" >
	            			<a class="head" href="<#if c.ct==9>javascript:void(0)<#else>${systemProp.appServerUrl}/sns/u${c.uid}/</#if>">
								<img src="<#if c.avatar?? && c.avatar!="" ><#if c.ct==9>${systemProp.publishProfileServerUrl}<#else>${systemProp.profileServerUrl}</#if>${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
					        <p>${c.content!}<span class="time">${c.createTime?string("yyyy.MM.dd")}</span></p>
					        <p><span class="text">我<#if c.ct==6>回复<#else>评论</#if>了</span><strong title='${c.nickname!}'>${stringSub(c.nickname,24)}</strong><span class="text">的<#if c.ct==9><#elseif c.ct==6>评论<#else>作品</#if></span>
					        	<span class="works" title='${c.title!}'>
					        		<#if c.ct==5>
		                        		<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.cid}/">
		                        			“${stringSub(c.title!,24)}”
		                        		</a>
		                        	<#elseif c.ct==9>
		                        		<a href=${systemProp.appServerUrl}/index-detail.action?itemId=${c.cid}&type=event"">
		                        			“${stringSub(c.title!,24)}”
		                        		</a>
		                        	<#else>
		                        		“${stringSub(c.title!,24)}”
		                        	</#if>
					        	</span>
					        </p>
					        <a comid='${c.id}' class="ctrl" ct='${c.ct}' href="javascript:void(0);">删除</a>
                    	</div>
                    </#list>
                    <#if commentListTo.size()==0>
                		<div class="cList nullInfo">还没有任何评论哦~</div>
                	</#if>
                </div>
            </div>
             <div class="changePage">
	            <#if page&gt;8 && (page/8)&gt;0>
					<#list (page-4)..pageCount as pn >
						<#if page&gt;1 && pn_index==0>
							<a href="javascript:void(0);" class='pre'>上一页</a>
						</#if>
						<#if pn_index&lt;9 >
							<a <#if pn==page>class="current"</#if> pn='${pn}' href="javascript:void(0);">${pn}</a>
						</#if>
						<#if pn_index&gt;1 && page&lt;pageCount && pn_index==(pageCount-page+4) >
							<a href="javascript:void(0);" class='next'>下一页</a>
						</#if>
					</#list>
				<#else>
					<#list 1..pageCount as pn >
						<#if page&gt;1 && pn_index==0>
							<a href="javascript:void(0);" class='pre'>上一页</a>
						</#if>
						<#if pn_index&lt;9 >
							<a <#if pn==page>class="current"</#if> pn='${pn}' href="javascript:void(0);">${pn}</a>
						</#if>
						
						<#if pn_index&gt;0 && page&lt;pageCount && pn_index==(pageCount-1) >
							<a href="javascript:void(0);" class='next'>下一页</a>
						</#if>
					</#list>
				</#if>
            </div>
        </div>
    </div>
    
	<div class="sideRight">
    	<div class="con conInfo">
        	<a class="a1" href="${systemProp.appServerUrl}/sns/user-index!attention.action"><span>${attention}</span>关注</a>
        	<a class="a2" href="${systemProp.appServerUrl}/sns/user-index!fans.action"><span>${fans}</span>粉丝</a>
        	<a class="a3" href="${systemProp.appServerUrl}/sns/${session_user.id}"><span>${creative}</span>内容</a>
        </div>
    	<div id="r_u_list" class="con conUser">
    		<#list publicAuthor as author>
            	<a class="author_list <#if author_index==4>last</#if>" href="javascript:void(0);" <#if author_index&gt;4>style='display:none;'</#if> >
	                <img url='<#if ((session_user)??) && (session_user.id==author.userId) >${systemProp.appServerUrl}/sns/u${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${author.userId}/</#if>' src="<#if author.avatar?? && author.avatar!="" >${systemProp.profileServerUrl}${avatarResize(author.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
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

    </div>
</div>

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
