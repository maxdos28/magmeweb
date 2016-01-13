<#list tagList as tag>
    <div class="item">
        <a tag="${tag.id}" class="img" href="javascript:void(0)">
            <div style="height:${(tag.height)*210/(tag.width)}px;width:210px;"><img src="${systemProp.tagServerUrl+tag.path}" /></div>
        </a>
        <a tagTitle='title' class="name" href="javascript:void(0)">
			${tag.title}
        </a>
        <div class="tools">
        	<em class="icon1">点击(${tag.clickNum})</em>
            <em class="icon2">推荐(${tag.topNum})</em>
        </div>
        <div class="clearFix">
        	<#if ((tag.user.avatar)??)&&(tag.user.avatar!="")>
            <img src="${systemProp.profileServerUrl+tag.user.avatar30}" />
            <#else>
            <img src="${systemProp.staticServerUrl}/images/head30.gif" />
            </#if>
            <p><a href="javascript:void(0)" user="${tag.user.id}">${(tag.user.nickName)!"用户名"}</a>创建此标签，源于<a href="javascript:void(0)" issue="${tag.issue.id}">${(tag.issue.publicationName)!"杂志名"}</a></p>
        </div>
    </div>
</#list>