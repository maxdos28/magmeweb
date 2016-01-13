<link href="${systemProp.staticServerUrl}/v3/style/channelDetail.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/eveShow.js"></script>

<!--body-->
<div class="body clearFix">
	<div class="sideLeft">
        <!--其它事件图片-->
    	<div class="otherPic JQfadeIn">
        	<div class="inner clearFix">
        		<#if preIssueImageList??>
        		<#list preIssueImageList as preIssueImage>
        		<#if sufIssueImageList?size<3 >
        			<#if preIssueImageList?size< (6-sufIssueImageList?size+preIssueImage_index+1) >
        			<div class="item"><a href="${systemProp.appServerUrl}/issue-image!show.action?id=${preIssueImage.id}"><img src="${systemProp.fpageServerUrl+"/event"+preIssueImage.imgFile68}" /></a></div>
        			</#if>
        		<#else>
        			<#if preIssueImageList?size< (6-3+preIssueImage_index+1) >
        			<div class="item"><a href="${systemProp.appServerUrl}/issue-image!show.action?id=${preIssueImage.id}"><img src="${systemProp.fpageServerUrl+"/event"+preIssueImage.imgFile68}" /></a></div>
        			</#if>        		
        		</#if>
        		</#list> 
        		</#if>       	
                <div class="item current"><a href="${systemProp.appServerUrl}/issue-image!show.action?id=${issueImage.id}"><img src="${systemProp.fpageServerUrl+"/event"+issueImage.imgFile68}" /></a></div>
                <#if sufIssueImageList??>
        		<#list sufIssueImageList as sufIssueImage>
        		<#if preIssueImageList?size<3 >
        			<#if sufIssueImage_index<(6-preIssueImageList?size) >
        			<div class="item"><a href="${systemProp.appServerUrl}/issue-image!show.action?id=${sufIssueImage.id}"><img src="${systemProp.fpageServerUrl+"/event"+sufIssueImage.imgFile68}" /></a></div>
        			</#if>
        		<#else>
        			<#if sufIssueImage_index<3 >
        			<div class="item"><a href="${systemProp.appServerUrl}/issue-image!show.action?id=${sufIssueImage.id}"><img src="${systemProp.fpageServerUrl+"/event"+sufIssueImage.imgFile68}" /></a></div>
        			</#if>        		
        		</#if>        		
        		</#list> 
        		</#if>                
            </div>
        </div>
        <!--看米大图-->
    	<div class="photoArea" eventId = "${(issueImage.id)!"0"}">
        	<a class="photo" target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${issueImage.id}"><img src="${systemProp.fpageServerUrl+"/event"+issueImage.imgFile}" /></a>
			<a class="tagName shopping png" href="javascript:void(0)">${(issueImage.title)!""}</a>        	
        	<#if preIssueImageList??&&(preIssueImageList?size!=0) >
        	<#list preIssueImageList as preIssueImage>
        		<#if (preIssueImage_index+1)==(preIssueImageList?size)>
        			<a href="${systemProp.appServerUrl}/issue-image!show.action?id=${preIssueImage.id}" class="turnL">上一张</a>
        		</#if>
        	</#list>
        	<#else>
        	<a class="turnL" href="javascript:void(0)">上图</a>
        	</#if>
        	
        	<#if sufIssueImageList??&&(sufIssueImageList?size!=0) >
        	<#list sufIssueImageList as sufIssueImage>
        		<#if sufIssueImage_index==0>
        			<a href="${systemProp.appServerUrl}/issue-image!show.action?id=${sufIssueImage.id}" class="turnR">下一张</a>
        		</#if>
        	</#list>
        	<#else>
        	<a class="turnR" href="javascript:void(0)">下图</a>
        	</#if>        	        	
            <div class="photoBottom"></div>
        </div>
        <!--看米描述-->
        <div class="description bgLine">
        	<p>${(issueImage.description)!""}</p>
        </div>
        <!--点击分享工具-->
        <div class="tools line">
        	<div class="info">
            	<strong class="click">点击次数：<span>${(issueImage.clickNum)!"0"}</span></strong>
                <strong class="from">出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issueImage.issueId}">[${(issueImage.publicationName)!""}]</a>创建于<span>${issueImage.createdTime?string("yyyy/MM/dd")}</span></strong>
            </div>
        	<a class="iconHeart <#if isEnjoy?? && isEnjoy==1>favCurrent</#if>" favTypeId="eve_${issueImage.id}" href="javascript:void(0)">${(issueImage.enjoyNum)!"0"}</a>
        	<a class="iconShare" href="javascript:void(0)">分享到</a>
        	<a class="icon" tagShare="tsina" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-weibo.gif" /></a>
        	<a class="icon" tagShare="kaixin" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-kaixin001.gif" /></a>
        	<a class="icon" tagShare="renren" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-renren.gif" /></a>
        	<a class="icon" tagShare="tqq" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-qq.gif" /></a>
        </div>
        <!--热门标签-->
        <div id="tagList" class="tagList line">
            <ul class="clearFix">
                <li class="title"><a href="javascript:void(0)">标签分类</a></li>
		    	<#list tagList as tag>
		    		<li class="<#if tagName??&&(tagName==tag.name)>current</#if>"><a name="tagName" href="javascript:void(0)">${tag.name}</a></li>
		    	</#list>
		    	<li class="add"><input id="tagInfo" type="text" tips="添加新标签" /><a id="addTag" eveId="${issueImage.id}" href="javascript:void(0)"></a></li>                  
            </ul>
        </div>
    </div>
	<div class="sideRight">
    	<div class="otherLike line JQfadeIn">
        	<h6>喜欢此图片的人也喜欢</h6>
            <div class="conBody clearFix">
            	<#list enjoyIssueImageList as ui>
            		<div class="item"><a href="${systemProp.appServerUrl}/issue-image!show.action?id=${ui.id}"><img src="${systemProp.fpageServerUrl+"/event"+ui.imgFile68}" /></a></div>
            	</#list>            
            </div>
        </div>
    
    </div>
</div>