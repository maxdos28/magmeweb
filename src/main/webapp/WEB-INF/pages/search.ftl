<script src="${systemProp.staticServerUrl}/v3/js/sns/m1.js"></script>


<#if publicationNum=0 && userNum=0 && m1Num=0>
<div class="body nullInfo" style="display:block;">哎呀，什么都没有找到...</div>
</#if>

<#if searchType="Issue">
<div class="body bodyMagazine clearFix" id="magazineWallPublication" >
    <#if issueList?? && (issueList?size) gt 0 >
    <#list issueList as issue>
    <#if issue_index lt 20>
    <div class="item">
    	<a target="_blank" class="photo" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issue.id}">
            <img src="${systemProp.magServerUrl}/${issue.publicationId}/172_${issue.id}.jpg" />
            <h5>${issue.publicationName} ${issue.issueNumber} <span>&nbsp;</span></h5>
            <sup class="album"></sup>
        </a>
    </div>
	</#if>
    </#list>
    </#if>
	
</div>
<#if publicationNum gt 20 >
<a id="loadMorePublication" href="javascript:void(0);" class="clickLoadMore"><span>点击查看更多</span></a>
</#if>
<!--
<div id="loading" class="pageLoad"><span>正在加载内容...</span></div>
-->

<#elseif searchType="User">

<div class="body fullScreen bodyUser clearFix" id="userWallUser">
     
    <#if userList?? && (userList?size) gt 0 >
    <#list userList as user>
    <div class="item">
		<div class="userHead">
	        <a class="head" u='${user.id}' href="<#if user.type??&&user.type=0>${systemProp.appServerUrl}/publish/publisher-home.action?publisherId=${user.id}<#else><#if ((session_user)??) && (session_user.id==user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${user.id}/</#if></#if>">
	        	<img class="userHeadImage" src="<#if user.avatar?? && user.avatar!=""><#if user.type??&&user.type=0>${systemProp.publishProfileServerUrl}${avatarResize(user.avatar,"60")}<#else>${systemProp.profileServerUrl+user.avatar60}</#if><#else>images/head60.gif</#if>" />
	        </a>
        </div>
	        <strong>${user.nickName}</strong>
	        <p><span>${user.province!""}</span>${user.city!""}</p>
	    
    </div>
    </#list>
    </#if>
    
</div>
<#if userNum gt 20 >
<a id="loadMoreUser" href="javascript:void(0);" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></a>
</#if>
<#elseif searchType="Creative">
<script>
$(function(){
	//排列item
	$('#homeWallCreative').masonry({itemSelector: '.item'});
});
</script>
<div class="body bodyHome clearFix" id="homeWallCreative">
	 <#if searchCreativeList?? && (searchCreativeList?size) gt 0>
	    <#list searchCreativeList as c>
	    	<div class="item <#if c.imagePath?? && c.imagePath?length gt 0 ><#else>itemNopic</#if>">
				<a href="http://www.magme.com/sns/c${c.id}/" target="_blank">
			    	<div class="photo"><#if c.imagePath?? && c.imagePath?length gt 0 ><img height="${(((c.high!1) * 210) / c.width!1)}"  src="${systemProp.staticServerUrl}${avatarResize(c.imagePath!'','max_400')}" alt="${c.secondTitle!''}"/></#if></div>
			    	<div class="info png">
		                <h2><#if c.secondTitle??>${c.secondTitle!''}<#else>${c.title!''}</#if></h2>
			            <p><#if c.secondDesc??>${c.secondDesc!''}<#else>${c.described!''}</#if></p>
			        </div>
		        </a>
	        </div>
	   </#list>
	 </#if>
</div>
<#if m1Num gt 20 >
<a id="loadMoreCreative" href="javascript:void(0);" class="clickLoadMore"><span>点击查看更多</span></a>
</#if>    
</#if>

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

<script type="text/javascript">
var session_userId= "<#if session_user?exists>${(session_user.id)!''}</#if>";
var imgPath = "${systemProp.profileServerUrl}";
var appPath = "${systemProp.appServerUrl}";
var issuePath = "${systemProp.magServerUrl}";
var eventPath = "${systemProp.fpageServerUrl}";
var staticPath = "${systemProp.staticServerUrl}";
$(function(){
$("#headerSearch").show();
$(".userHead>.head>img").live("mouseenter",function(){
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
	$obj.find(".tool").stop(true,true).fadeIn(10);
	
});
$(".userHead").live("mouseleave",function(){
	var $obj = $(this);
	$obj.find(".tool").fadeOut(100);
});
currentPage=1;
$("#resetPublicationA").live("click",function(){
	currentPage=1;
});

$("#resetUserA").live("click",function(){
	currentPage=1;
	$("#loadMoreUser").show();
});
$("#resetFpageEventA").live("click",function(){
	currentPage=1;
});
$("#resetCreativeA").live("click",function(){
	currentPage=1;
});
//publication
$("#loadMorePublication").live("click",function(){
	currentPage++;
	var type = getUrlValue("searchType");
	var urlinfo = decodeURI(window.location.href);//获取url
	var queryStr = urlinfo.substring(urlinfo.lastIndexOf("=")+1);
	loadMoreAjax(queryStr,type,currentPage);
});
//user
$("#loadMoreUser").live("click",function(){
	currentPage++;
	var type = getUrlValue("searchType");
	var urlinfo = decodeURI(window.location.href);//获取url
	var queryStr = urlinfo.substring(urlinfo.lastIndexOf("=")+1);
	loadMoreAjax(queryStr,type,currentPage);
});
//event
$("#loadMoreFpageEvent").live("click",function(){
	currentPage++;
	var type = getUrlValue("searchType");
	var urlinfo = decodeURI(window.location.href);//获取url
	var queryStr = urlinfo.substring(urlinfo.lastIndexOf("=")+1);
	loadMoreAjax(queryStr,type,currentPage);
});
//creative
$("#loadMoreCreative").live("click",function(){
	currentPage++;
	var type = getUrlValue("searchType");
	var urlinfo = decodeURI(window.location.href);//获取url
	var queryStr = urlinfo.substring(urlinfo.lastIndexOf("=")+1);
	loadMoreAjax(queryStr,type,currentPage);
});
	
});
</script>