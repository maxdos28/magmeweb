<html>
<head>
</head>
<body>
	<style type="text/css">
		.linkage {display: inline;}
		.linkage li {float: left; margin-right:10px;word-wrap:break-word;}
	</style>
<script>
$(function(){
	$.jqueryScrollPhoto("#publisherMgzList",4,188,4,0,600);
	function thisMovie() {
         if (navigator.appName.indexOf("Microsoft") != -1) {
             return window["readerTool"];
         } else {
             return $("#readerTool2").get(0);
         }
     }
});
</script>
<div class="body">
	<!--bodyContent-->
    <div class="conLeft">
        <!--userInfo-->
        <div class="userInfo clearFix">
            <strong id="issueName" class="name">${((publication.name)!"")}</strong> <a href="javascript:void(0)" class="img"><img src="<#if (publisher.logo)?? && publisher.logo!="" >${systemProp.publishProfileServerUrl}${publisher.logo172}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" /></a>
            <ul class="atten">
                <li class="current"><a href="<#if issueCount==0>javascript:void(0);<#else>${systemProp.appServerUrl}/publish/publication-home!mag.action?publicationId=${publicationId!""}</#if>" alt="${((publication.name)!"")}的全部杂志"><strong>${issueCount!"0"}</strong><span>杂志</span></a></li>
                <li><a href="<#if issueImageCount==0>javascript:void(0);<#else>${systemProp.appServerUrl}/publish/publication-home!image.action?publicationId=${publicationId!""}&type=issueImage</#if>" alt="${((publication.name)!"")}的全部事件"><strong>${issueImageCount!"0"}</strong><span>事件</span></a></li>
                <li><a href="<#if userImageCount==0>javascript:void(0);<#else>${systemProp.appServerUrl}/publish/publication-home!image.action?publicationId=${publicationId!""}&type=userImage</#if>" alt="${((publication.name)!"")}的全部图片"><strong>${userImageCount!"0"}</strong><span>图片</span></a></li>
            </ul>
            <div class="tool clear">
            	<#if (publisher.weiboUid)??>
                <a class="btn btnSina" weibo_uid="${(publisher.weiboUid)!''}" href="javascript:void(0)" title="关注${((publication.name)!"")}的新浪微博">关注新浪微博</a>
                </#if>
            </div>
            <#if (publication.publisher)?? && (publication.publisher?trim) != ''>
            <div class="conB conNavList">
            	<h2>出版人</h2>
                <div class="conBody">
                    <a href="javascript:void(0)">${(publication.publisher)!""}</a>
                </div>
            </div>
            </#if>
             <#if (publication.issn)?? && (publication.issn?trim) != ''>
            <div class="conB conNavList">
            	<h2>刊号</h2>
                <div class="conBody">
                    <a href="javascript:void(0)">${(publication.name)!""}(${(publication.issn)!""})</a>
                </div>
            </div>
            </#if>
            <#if (publication.description)?? && (publication.description?trim) != ''>
            <div class="conB conNavList">
            	<h2>杂志描述</h2>
                <div class="conBody">${publication.description!""}</div>
            </div>
            </#if>
        </div>
    </div>
    
    <div class="conMiddleRight conPublisherHome">
        <div class="tools png">
        	<em id="weiboShare" class="iconShare png" title="分享"></em>
        	<em id="codeCopy" class="iconEmbed png" title="拷贝Embed代码"></em>
        </div>
    	<h1>${(publication.name)!""}&nbsp;${(issue.issueNumber)!""}</h1>
    	<div class="conReader" title="${(publication.name)!""}">
        	<p>${(publication.description)!""}</p>
        	<div style="font-size:60px; color:#ccc;text-align:center;">
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="736px" height="500px" id="readerTool">
                <param name="movie" value="${systemProp.staticServerUrl}/reader/EmbededTool.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="always" />
                <param name="allowFullScreen" value="true" />
                <param name="wmode" value="Opaque" />
                <param name="FlashVars" value="publicationId=${(publicationId)!'0'}&issueId=${(issueId)!'0'}" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/EmbededTool.swf" id="readerTool2" width="736px" height="500px">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="always" />
                    <param name="allowFullScreen" value="true" />
                    <param name="wmode" value="Opaque" />
                    <param name="FlashVars" value="publicationId=${(publicationId)!'0'}&issueId=${(issueId)!'0'}" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                	<p> 
                		Either scripts and active content are not permitted to run or Adobe Flash Player version
                		10.0.0 or greater is not installed.
                	</p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>        	
        	</div>
        </div>
        <div id="publisherMgzList" class="conB conPublisherMgzList">
        	<h2>杂志列表</h2>
            <a class="turnLeft"></a>
            <a class="turnRight"></a>
            <ul class="clearFix">
            	<#if issueList??>
            	<#list issueList as issue>
	                <li>
	                	<a href="http://${publication.domain}.magme.com/${publication.englishname}/${(issue.id)!"0"}.html">
	                		<img src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" />
	                		<span>${(issue.publicationName)!""}_${(issue.issueNumber)!""}</span>
	                	</a>
	                </li>
                </#list>
                </#if>
            </ul>
        </div>

    	<#if alikePublicationList?? && alikePublicationList?size gt 0>
	        <div class="conB conTagList">
	        	<h2>与<span>${publication.name!''} </span>&nbsp;相关的更多<span>${category.name}</span>&nbsp;杂志推荐</h2>
	            <ul class="clearFix">
            	<#list alikePublicationList as pub>
            		<#if pub.name??>
	                	<li><a href="http://${pub.domain}.magme.com/${pub.englishname}/"><span>${pub.name}</span></a></li>
            		</#if>
                </#list>
	            </ul>
	        </div>
        </#if>
    	<#if otherPublicationList?? && otherPublicationList?size gt 0>
        <div class="conB conTagList">
        	<h2>其它网友正在看</h2>
            <ul class="clearFix">
            	<#list otherPublicationList as pub>
            		<#if pub.name?? && pub_index lt 20>
	                	<li><a href="http://${pub.domain}.magme.com/${pub.englishname}/"><span>${pub.name}</span></a></li>
            		</#if>
                </#list>
            </ul>
        </div>
        </#if>
        
        <!-- Baidu Button BEGIN -->
        <br/><hr/>
		    <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
		        <span class="bds_more">分享到：</span>
		        <a class="bds_tsina"></a>
		        <a class="bds_tqq"></a>
		        <a class="bds_kaixin001"></a>
				<a class="shareCount"></a>
		    </div>
			<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=3619602" ></script>
			<script type="text/javascript" id="bdshell_js"></script>
			<script type="text/javascript">
				//在这里定义bds_config
				var bds_config = {'bdText':'三天不读书，智商不如猪！有品位的人都在这里看杂志！《麦米杂志阅读》，海量正版杂志满足您！'};
				document.getElementById("bdshell_js").src = "http://share.baidu.com/static/js/shell_v2.js?cdnversion=" + new Date().getHours();
			</script>
		<!-- Baidu Button END -->
    </div>

</div>


<div id="embedCode" class="popContent">
    <fieldset>
    	<h6>分享阅读器代码</h6>
        <div class="floatl g320">
        	<em class="title">模式</em>
            <em><label><input type="radio" name="viewMode" value="1" checked/>单本封面</label></em>
            <em><label><input type="radio" name="viewMode" value="2"/>单本阅读器</label></em>
            <em><label><input type="radio" name="viewMode" value="3"/>多本阅读器</label></em>
        </div>	
    	<div class="floatl g180">
        	<em class="title">宽-高</em>
            <em><input type="text" id="changeWidth" class="input g50" value="600"/></em>
            <em><input type="text" id="changeHeight" class="input g50" value="800" disabled/></em>
        </div>
    	<div class="floatl g120 color">
        	<em class="title">背景色</em>
            <em id="colorSelector"><div></div><span class="png"></span></em>
        </div>	
        <hr class="clear" />
    	<div class="clear floatl g240">
        	<em class="title">刊号</em>
            <em><label><input type="radio" id="curIssueRadio" name="issueId" value="${(issue.id)!"0"}" checked/>当前期刊</label></em>
            <em><label><input name="issueId" type="radio" value="0" />最新一期</label></em>
        </div>	
        <hr class="clear" />
        <div>
            <em><textarea id="codeInfo" class="input g420"><embed FlashVars="publicationId=${(publicationId)!'0'}&issueId=${(issueId)!'0'}&viewMode=1&backColor=0x303136" src="${systemProp.staticServerUrl+"/reader/OffsiteTool"}.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" wmode="opaque" width="600" height="400"></embed></textarea></em>
        </div>
        <div>
            <em class="floatr">
            	<a id="clip_button" href="javascript:void(0)" class="btnBB" >复制代码</a>
	            <object id="flash1" data="${systemProp.appServerUrl}/ClipboardBtn.swf" style="position:absolute; bottom:20px; left:487px; width:148px; height:38px;" type="application/x-shockwave-flash">
					<param name="movie" value="${systemProp.appServerUrl}/ClipboardBtn.swf" />
					<param name="wmode" value="Transparent" />
				</object>
			</em>
        	<em id="tipInfo" class="floatl hide">该内容已经复制，你可以使用Ctrl+V 粘贴。</em>
        </div>
    </fieldset>
</div>



</div>
</body>
</html>
