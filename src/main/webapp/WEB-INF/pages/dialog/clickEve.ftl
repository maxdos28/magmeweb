        <div id="shareEventDialog" class="popContent" >
             <h6><span>分享</span><div id='ckepop'><a class='jiathis_button_tsina' tagShare="tsina"></a><a class='jiathis_button_tqq' tagShare="tqq"></a><a class='jiathis_button_renren' tagShare="renren"></a><a class='jiathis_button_kaixin' tagShare="kaixin"></a></div></h6>
             <div class="left">
             	<a class="img" href="${systemProp.domain}/publish/mag-read.action?id=${event.issueId}&pageId=${event.pageNo}"><img src="${systemProp.fpageServerUrl}/event/${event.imgFile}" /></a>
                <h5><a href="${systemProp.domain}/publish/mag-read.action?id=${event.issueId}&pageId=${event.pageNo}">${event.description!}</a></h5>
                <h6><span class="create">创建于${event.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span><span title="喜欢" class="fav" favTypeId="eve_${event.id}">${event.enjoyNum}</span></h6>
             </div>
             <div class="right">
                <h5>热门自定义标签</h5>
                <ul id="clickTryTagList" class="clear clearFix listB listTag">
                    <#list tagList as tag>
                    <li><a tagTypeName="3_${event.id}_${tag.name}" title="共有${tag.groupNum}人添加此标签" href="javascript:void(0)" <#if tag_index gte 0 && tag_index lt 6>class="tag${tag_index+1}"</#if></a>${tag.name}</a></li>
                    </#list>
                </ul>
                <div class="addTag">
                	<input type="hidden" name="tagType" value="3" />
                	<input type="hidden" name="objectId" value="${event.id}" />
	                <input type="text" name="tagName" class="input" tips="请输入您的自定义标签" /><a id="addTagSubmit" href="javascript:void(0)" class="btnBS" >添加</a>
                </div>
             </div>
             <div class="clear"></div>
	    </div>