        <div id="shareTagDialog" class="popContent" >
             <h6><span>分享</span><div id='ckepop'><a class='jiathis_button_tsina' tagShare="tsina"></a><a class='jiathis_button_tqq' tagShare="tqq"></a><a class='jiathis_button_renren' tagShare="renren"></a><a class='jiathis_button_kaixin' tagShare="kaixin"></a></div></h6>
             <div class="left">
             	<a class="img" href="${systemProp.domain}/user-image!show.action?imageId=${userImage.id}"><img src="${systemProp.tagServerUrl+userImage.path172}" /></a>
                <h5><a href="${systemProp.domain}/user-image!show.action?imageId=${userImage.id}">${userImage.description!}</a></h5>
                <h6><span title="喜欢" class="fav" favTypeId="pic_${userImage.id}">${userImage.enjoyNum}</span><span title="点击数" class="top">${userImage.clickNum}</span><span title="评论数" class="chat">${commentNum}</span></h6>
             </div>
             <div class="right">
             	<div class="head">
             	<a href="${systemProp.domain}/user-visit!index.action?userId=${user.id}">
             	<img src="<#if user.avatar60?? && user.avatar60!="">${systemProp.profileServerUrl+user.avatar60}
             	<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" />
             	</a>
             	</div>
                <h3><a href="${systemProp.domain}/user-visit!index.action?userId=${user.id}">${user.nickName}</a></h3>
                <a followedUserId="${isFollow}_1_${user.id}" class="btn addFriend"><#if isFollow=0>加为<#elseif isFollow=1>取消</#if>好友</a>
                <a msg="1_${user.id}" class="btn message">发送消息</a>
                <div class="sendMsg" id="sendMsgTry">
                    <input name="content" type="text" class="input" tips="请输入消息内容" /><a id="sendMsgSubmit" href="javascript:void(0)" class="btnBS" >发送</a>
                </div>
                <h5>热门自定义标签</h5>
                <ul id="clickTryTagList" class="clear clearFix listB listTag">
                    <#list tagList as tag>
                    <li><a tagTypeName="1_${userImage.id}_${tag.name}" title="共有${tag.groupNum}人添加此标签" href="javascript:void(0)" <#if tag_index gte 0 && tag_index lt 6>class="tag${tag_index+1}"</#if></a>${tag.name}</a></li>
                    </#list>
                </ul>
                <div class="addTag">
                <input type="hidden" name="tagType" value="1" />
	            <input type="hidden" name="objectId" value="${userImage.id}" />
                <input type="text" name="tagName" class="input" tips="请输入您的自定义标签" /><a id="addTagSubmit" href="javascript:void(0)" class="btnBS" >添加</a>
                </div>
             </div>
             <div class="clear"></div>
       </div>