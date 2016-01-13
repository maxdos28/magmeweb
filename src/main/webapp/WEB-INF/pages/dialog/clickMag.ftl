        <div id="shareMgzDialog" class="popContent" >
             <h6><span>分享</span><div id='ckepop'><a class='jiathis_button_tsina' tagShare="tsina"></a><a class='jiathis_button_tqq' tagShare="tqq"></a><a class='jiathis_button_renren' tagShare="renren"></a><a class='jiathis_button_kaixin' tagShare="kaixin"></a></div></h6>
             <div class="left">
             	<a class="img" href="${systemProp.domain}/publish/mag-read.action?id=${issue.id}"><img src="${systemProp.magServerUrl}/${issue.publicationId}/172_${issue.id}.jpg" /></a>
                <h5><a href="${systemProp.domain}/publish/mag-read.action?id=${issue.id}">${issue.description!}</a></h5>
                <h6>
                <span title="喜欢" class="fav" favTypeId="mag_${issue.id}">${issue.enjoyNum}</span>
                <span title="粉丝数" class="fans">${publisher.followNum}</span>
                <span title="往期期刊" class="mgz" backPubId="${issue.publicationId}">往期期刊</span>
                </h6>
             </div>
             <div class="right">
             	<div class="head">
             	<a href="${systemProp.domain}/publish/publisher-home.action?publisherId=${publisher.id}">
             	<img src="<#if publisher.logo60?? && publisher.logo60!="">${systemProp.publishProfileServerUrl}${avatarResize(publisher.logo,"60")}
             	<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" />
             	</a>
             	</div>
                <h3><a href="${systemProp.domain}/publish/publisher-home.action?publisherId=${publisher.id}">${issue.publicationName}</a></h3>
                <a msg="2_${publisher.id}" class="btn" >意见建议</a>
                <a class="btn" followedUserId="${isFollow}_2_${publisher.id}"><#if isFollow=0>我要<#elseif isFollow=1>取消</#if>关注</a>
                <a class="btn" subscribePubId="${isSubscribe}_${issue.id}"><#if isSubscribe=0>我要<#elseif isSubscribe=1>取消</#if>订阅</a>
                <div class="sendMsg">
                    <input name="content" type="text" class="input" tips="请输入意见内容" /><a id="sendMsgSubmit" href="javascript:void(0)" class="btnBS" >发送</a>
                </div>
                <h5>热门自定义标签</h5>
                <ul id="clickTryTagList" class="clear clearFix listB listTag">
                    <#list tagList as tag>
                    <li><a tagTypeName="2_${issue.id}_${tag.name}" title="共有${tag.groupNum}人添加此标签" href="javascript:void(0)" <#if tag_index gte 0 && tag_index lt 6>class="tag${tag_index+1}"</#if></a>${tag.name}</a></li>
                    </#list>
                </ul>
                <div class="addTag">
                <input type="hidden" name="tagType" value="2" />
	            <input type="hidden" name="objectId" value="${issue.id}" />
                <input type="text" name="tagName" class="input" tips="请输入您的自定义标签" /><a id="addTagSubmit" href="javascript:void(0)" class="btnBS" >添加</a>
                </div>
             </div>
             <div class="clear"></div>
	    </div>