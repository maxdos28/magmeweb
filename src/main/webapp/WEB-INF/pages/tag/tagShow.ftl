<html>
<head>
<title>标签墙</title>
</head>
<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/tagShow.js"></script>
<div class="body clearFix">
	<div>
		<input id="tagId" type="hidden" value="${tag.id}"/>
		<input id="tagWidth" type="hidden" value="${tag.width}"/>
		<input id="tagHeight" type="hidden" value="${tag.height}"/>
		<input id="tagLeft" type="hidden" value="${tag.x}"/>
		<input id="tagTop" type="hidden" value="${tag.y}"/>
		<input id="imgUrl" type="hidden" value="${systemProp.magServerUrl+"/"+tag.publicationId+"/"+tag.issueId+"/b"+tag.pageNo+".jpg"}"/>
		<input id="tagUrl" type="hidden" value="${systemProp.tagServerUrl+tag.path}"/>
	</div>
    <!--topBar-->
    <div class="kanmiTopBar">
       
        <a id="goBackPage" href="javascript:void(0)" class="btnBack">返回上一页</a>
    </div>
    <!--sideLeft-->
	<div class="side236">
    	<div class="conC conUserList">
        	<h2 class="clearFix">
        		<a href="${systemProp.appServerUrl}/user!visit.action?id=${tag.user.id}">
        		<#if (tag.user.avatar)??>
        			<img src="${systemProp.profileServerUrl+tag.user.avatar30}" />
        		<#else>
        			<img src="${systemProp.staticServerUrl}/images/head30.gif" />
        		</#if>
        		<strong>${(tag.user.nickName)!"用户名"}</strong>
        		</a>
        		<span>共有${(tag.user.statsMap.totalTagNum)!"0"}个标签</span>
        	</h2>
        	<div class="conBody">
            	<div id="tagList" begin="0" size="9" userId="${tag.user.id}" class="inner clearFix">
            		<#if tagList??>
            		<#list tagList as tag>
            			<a href="${systemProp.appServerUrl+"/user-tag!show.action?id="+tag.id}"><img src="${systemProp.tagServerUrl+tag.path68}" title="${tag.title}"/></a>
            		</#list>
            		</#if>
                </div>
                <div class="control">
                	<a id="turnR" class="turn right <#if (tagList?size<9)>stop</#if>" href="javascript:void(0)"></a>
                    <a id="turnL" class="turn left stop" href="javascript:void(0)"></a>
                	<a href="${systemProp.appServerUrl+"/user-tag!tagWall.action?begin=0&size=20&userId="+tag.user.id}">显示全部</a>
                </div>
            </div>
        </div>
    </div>
    <!--sideMiddle-->
	<div class="side484">
    	<div class="conC conBigMgzShow bigMgzShow1" id="conBigMgzShow">
    		<a class="btnToggle" href="javascript:void(0)"></a>
    		<img bigimg="bigImg" url="${systemProp.appServerUrl+"/publish/mag-read.action?id="+tag.issueId+"&pageId="+tag.pageNo}" src="${systemProp.magServerUrl+"/"+tag.publicationId+"/"+tag.issueId+"/b"+tag.pageNo+".jpg"}"/>
        	<div class="mask" title="点击阅读杂志" ></div>
            <div class="showArea" title="点击阅读杂志">
            	<img src="${systemProp.tagServerUrl+tag.path}" /><a class="del" title="关闭"></a>
            </div>
            <h6 id="tagTitle">${tag.title}</h6>
			<p>${tag.description}</p>
			<div class="info clearFix">
                <span>创建于${tag.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                <a class="iconDig" id="supportBtn" tagId="${(tag.id)!"0"}" href="javascript:void(0)">推荐<strong id="supportNum">${(tag.topNum)!"0"}</strong></a>
                <a class="iconShare">分享</a>
                <div id="ckepop">
	                <a class="jiathis_button_tsina" tagShare='tsina'></a>
	                <a class="jiathis_button_tqq" tagShare='tqq'></a>
	                <a class="jiathis_button_renren" tagShare='renren'></a>
                </div>
            </div>
        </div>
        <div begin="10" size="10" id="tagCommentList" class="conC conTagComment">
        	<div class="item itemTitle" name="#comment">
                <strong>评论<span>(${tagCommentCount})</span></strong>
            </div>
            <div id="tagComment" class="item inputArea clearFix">
            	<form id="tagCommentForm" method="post" action="">
	            	<input type="hidden" name="tagId" value="${tag.id}" />
	            	<textarea name="content" tips="还没有评论，赶快抢沙发！"></textarea>
	                <div code="getcode" class="clearFix hide">
	                	<#if !(session_user??)>
	                	<input type="text" class="in" name="userName" tips="用户名" />
	                    <input type="text" class="in" tips="密码" />
	                    <input type="password" name="password" class="in g100 hide" tips="密码" />
	                    </#if>
	                    <input code="getcode" name="authcode" type="text" class="in code" tips="验证码" />
	                    <a name="getAuthcode" href="javascript:void(0)" class="floatl"><img src="${systemProp.staticServerUrl}/images/code.gif" class="floatl" />&nbsp;&nbsp;看不清，换一个</a>
	              	</div>
	                <a href="javascript:void(0)" id="tagCommentSubmit" class="btnBS floatr" >评论</a>
	                <#if !(session_user??)>
	                <a href="#" name="toUserRegisterBtn" class="important floatl hide" >新用户注册</a>
	                </#if>
                </form>
            </div>        
        	<#if tagCommentList??>
			<#list tagCommentList as tagComment>        
        	<div class="item clearFix">
            	<a href="${systemProp.appServerUrl}/user!visit.action?id=${tagComment.user.id}">
                <span>${tagComment.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                <#if (tagComment.user.avatar)??&&(tagComment.user.avatar!="")>
                <div class="userHead"><img src="${systemProp.profileServerUrl+tagComment.user.avatar46}" /><sub></sub></div>
                <#else>
                <div class="userHead"><img src="${systemProp.staticServerUrl}/images/head46.gif" /><sub></sub></div>
                </#if>
                <strong>${tagComment.user.nickName}</strong>
                <p>${tagComment.contentInfo.content}</p>
                </a>
            </div>
            </#list>
            </#if>
        </div>
    </div>
    <!--sideRight-->
	<div class="side236">
    	<div class="conC conTagTime">
        	<h2>源于${tag.issue.publicationName}</h2>
            <div class="conBody clearFix">
            	<a target="_magme" href="${systemProp.appServerUrl+"/publish/mag-read.action?id="+tag.issueId}"><img src="${systemProp.magServerUrl+"/"+tag.publicationId+"/100_"+tag.issueId+".jpg"}"/></a>
                <p>${(tag.issue.description)!""}<a target="_magme" class="important" href="${systemProp.appServerUrl+"/publish/mag-read.action?id="+tag.issueId}">[更多内容]</a></p>
            </div>
        </div>
        <div class="ADBanner mgb20">
        	<img src="${systemProp.staticServerUrl}/images/grey_234x60.jpg">
        </div>
    </div>
</div>
</body>
</html>
