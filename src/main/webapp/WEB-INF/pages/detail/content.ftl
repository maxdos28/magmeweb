<#macro main>
<#import "./comment.ftl" as comment>
	<div class="conOtherRead" id="conOtherRead">
		<h2>推荐内容</h2>
		<a class="turnLeft png"></a>
		<a class="turnRight png"></a>
		<div class="inner">
		<ul class="clearFix">
			<#if itemList?? && (itemList?size) gt 0>
			<#list itemList as item>
				 <li class="item">
				<a href="javascript:void(0);" name="tjitem" itemId="${item.itemId}" type="${item.type}">
				    <div class="photo"><img src="<#if item.itemImagepath??><#if item.type=="event">${systemProp.fpageServerUrl}/event<#else>${systemProp.staticServerUrl}</#if>${avatarResize(item.itemImagepath,'210')}</#if>" width="<#if item.itemWidth??>210</#if>" height="<#if item.itemWidth?? && item.itemHeight??>${item.itemHeight/item.itemWidth*210}</#if>" alt="<#if item.itemTitle??>${item.itemTitle}</#if>"></div>
				    <div class="info png">
					<strong><#if item.ownerName??>${item.ownerName}</#if></strong>
					<h6 class="png" title="<#if item.itemTitle??>${item.itemTitle}</#if>">
						<#if item.ownerAvatar??>
							<img src="<#if item.type=='event'>${systemProp.publishProfileServerUrl}<#else>${systemProp.profileServerUrl}</#if>${avatarResize(item.ownerAvatar,'46')}" />
		    	    	<#else>
			    	    	<img src="${systemProp.staticServerUrl}/v3/images/head46.gif" />
		    	    	</#if>
		    	    </h6>
					<h4><#if item.itemTitle??>${item.itemTitle}</#if></h4>
					<p><#if item.itemContent??>${item.itemContent}</#if></p>
					<div class="tools png"><em class="iconDetail png"></em><em title="喜欢" class="iconHeart png"></em><em class="iconShare"></em></div>
				    </div>
				</a>
			    </li>
			</#list>
			</#if>
		</ul>
		</div>
	</div>

	<div class="sideLeft con">
		<div class="sendBox clearFix">
			<img src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl}${avatarResize(session_user.avatar,'60')}<#else>images/head60.gif</#if>" class="head" id="userhead"/>
		    <div class="right">
			<div class="input"><textarea maxlength="196" tips="请输入文字" id="content" color="#9daf7b,#7c8865"></textarea><em></em></div>
			<div class="clearFix">
				<!--<a href="javascript:void(0);" class="shareWeiBo shareWeiBoChecked clearFix"><sup></sup>同时转发到新浪微博</a>-->
			    <a href="javascript:void(0);" class="btnGB" id="addComment">评论</a>
			    <strong class="txtNum">您还可以输入<span>196</span>字</strong>
			</div>
		    </div>
		</div>
		<div class="conReply conReplyBig" id="commentContainer"><@comment.main /></div>
		<a href="javascript:void(0);" class="conReplyMore" id="conReplyMore"></a>
	</div>

	<div class="sideRight">
		<#if advertiseList?? && (advertiseList?size) gt 0>
	        <div class="con conBuyAd " id="conBuyAd">
	            <div class="doorList">
	                <div class="inner">
	                		<#list advertiseList as adlist>
	                		<div class="item"><img src="<#if adlist.imgurl??>${adlist.imgurl}</#if>" title="<#if adlist.title??>${adlist.title}</#if>" /><div class="info"><strong><#if adlist.title??>${adlist.title}</#if></strong><p><a href="<#if adlist.linkurl??>${adlist.linkurl}</#if>" target="_blank">查看详细</a></p></div></div>
	                		</#list>
	                </div>
	            </div>
	        </div>
        </#if>
        <div class="con conAd">
			<iframe src="/baidu_clb.html#0_6"  scrolling="no" height="140" frameborder="0" width="238" style="display:block;margin:0"></iframe>
        </div>
        <#if adVideo??>
        <div class="con conVideoAd" id="videoAd_div">
        	<#list adVideo as alist>
	        	<a href="javascript:void(0);">
	                <img id="ADvideo" src="<#if alist.imgurl??>${alist.imgurl}</#if>" width="456" height="320" />
	                <span id="ADvideoPlay" url="<#if alist.mediaurl??>${alist.mediaurl}</#if>" class="png"></span>
	            </a>
            </#list>
        </div>
        </#if>
    </div>
</#macro> 