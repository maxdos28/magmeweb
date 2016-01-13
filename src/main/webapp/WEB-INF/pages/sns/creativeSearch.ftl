<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/m1.js"></script>
<div class="body fullScreen searchTitle">
	<div class="bodyInner line clearFix">
        <h2>搜索结果</h2>
        <a id="resetPublicationA" <#if searchType="Issue">class="current"</#if> href="<#if publicationNum gt 0 && searchType?? && searchType!="Issue">search.action?searchType=Issue&queryStr=${queryStr!}<#else>javascript:void(0)</#if>">杂志<span>(${publicationNum})</span></a>
        <a id="resetUserA" <#if searchType="User">class="current"</#if> href="<#if userNum gt 0 && searchType?? && searchType!="User">search.action?searchType=User&queryStr=${queryStr!}<#else>javascript:void(0)</#if>">用户<span>(${userNum})</span></a>
        <a id="resetCreativeA" <#if searchType="Creative">class="current"</#if> href="<#if m1Num gt 0 && searchType?? && searchType!="Creative">search.action?searchType=sns&queryStr=${queryStr!}<#else>javascript:void(0)</#if>">M1作品<span>(${m1Num})</span></a>
    </div>
</div>

<#if m1Num==0>
<div class="body nullInfo" style="display:block;">哎呀，什么都没有找到...</div>
</#if>

<div class="body bodyHome clearFix" id="homeWall">
	 <#if searchCreativeList?? && (searchCreativeList?size) gt 0>
	    <#list searchCreativeList as c>
	    	<div class="item <#if c.imagePath?? && c.imagePath?length gt 0 ><#else>itemNopic</#if>">
			<a href="http://www.magme.com/sns/c${c.id}/" target="_blank">
		    	<div class="photo"><#if c.imagePath?? && c.imagePath?length gt 0 ><img height="${(((c.high!1) * 210) / c.width!1)}"  src="${systemProp.staticServerUrl}${avatarResize(c.imagePath!'','max_400')}" alt="${c.secondTitle!''}"></#if></div>
		    	<div class="info png">
	                <h2>${c.secondTitle!''}</h2>
		            <p>${c.secondDesc!''}</p>
		        </div>
	        </a>
	    </div>
	   </#list>
	 </#if>
</div>
<#if m1Num gt 20 >
	<a id="loadMoreUser" href="javascript:void(0);" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></a>
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