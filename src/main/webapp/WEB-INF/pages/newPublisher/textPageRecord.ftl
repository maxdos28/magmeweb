<script>
	var issueid = ${issueId!''};
</script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.domain}/v3/dv/js/textPageRecord.js"></script>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conB con13">
        	<h2>
        	<input id="issueType" type="hidden" value="${issue.issueType}"/>
        	<#if issue.issueType??&&issue.issueType==2>
        	<label class="floatr g150"><input id="boutique" name="boutique" <#if issueContents??&&issueContents.isBoutique?? && issueContents.isBoutique==1>checked="checked"</#if> type="checkbox">设为精品页<em class="icon16question"></em></label>
        	<label class="floatr"><input id="catalog" name="catalog" <#if issueContents??&&issueContents.isCatalog??&&issueContents.isCatalog==1>checked="checked"</#if> type="checkbox">设为目录<em class="icon16question"></em></label>
        	<#else>
        	<label class="floatr"><input id="ad" name="ad" <#if textPage?? && textPage.ad?? && textPage.ad==1>checked="checked"</#if> type="checkbox">此页为广告页<em class="icon16question" title="为了统计内容的完成率"></em></label>
        	<label class="floatr g150"><input id="cflag" name="cflag" <#if issueContents??>checked="checked"</#if> type="checkbox">添加此页为索引<em class="icon16question" title="用户在阅读时可以点击索引目录跳转到相关文章处进行阅读"></em></label>
        	</#if>
        	<a href="/new-publisher/magazine-list!to.action" class="btnWS floatl mgr20">返回</a><a class="btnBS floatl" href="/new-publisher/issue-category-manage.action?issueId=${issue.id}">栏目管理</a>
        	</h2>
        	
			<div class="pic">
			<#if issue.issueType??&&issue.issueType==2>
				<img id="imgUrl" width="460" src="${systemProp.staticServerUrl+'/appprofile/'+issue.jpgPath+'/'+pageNo+'/pad_q'+pageNo+'.jpg'}" />
			<#else>
				<img id="imgUrl" width="460" src="${systemProp.magServerUrl+'/'+issue.publicationId+'/'+issue.id+'/768_'+pageNo+'.jpg'}" />
			</#if>
            </div>
            <form id="textPageForm">
			<div class="text">
            	<input type="hidden" name="issueId" value="${issueId}" />
            	<input type="hidden" name="pageNo" value="${pageNo}" />
            	<input type="text" class="input" tips="标题" value="<#if issue.issueType??&&issue.issueType==2><#if issueContents??>${(issueContents.title)!''}</#if><#else><#if textPage??>${(textPage.title)!''}</#if></#if>" name="title"  />
            	<textarea name="content" class="input"><#if issue.issueType??&&issue.issueType==2><#if issueContents??>${(issueContents.description)!''}</#if><#else><#if textPage??>${(textPage.content)!""}</#if></#if></textarea>
                <div class="tagList" style="display:none;" >
                	<em class='icon16question'  style="position:absolute; left:-25px; top:5px" title='添加与文章内容相关的标签，当搜索标签时，可以提高文章的阅读量'></em>
                    <ul class="clearFix" id="tagListLi">
                        <li class="title"><a>标签分类</a></li>
                        <li class="current"><a href="javascript:void(0)">早上</a></li>
                       
                        <li><a href="javascript:void(0)">标签最多放30个</a></li>
                        <li class="add"><input type="text" id="newTag" tips="添加新标签" /><a id="addTagButton" href="javascript:void(0)"></a></li>
                    </ul>
                </div>
                <div class="ctrl">
                	<a name="textPageFormSaveBtn" moveDirection="down" totalPages="${issue.totalPages}" class="btnBB floatr" href="javascript:void(0)">保存/下一页</a>
                    <a name="textPageFormSaveBtn" moveDirection="up" totalPages="${issue.totalPages}" href="javascript:void(0)" class="btnWB">保存/上一页</a>
                </div>
                 <div id="pageDiv"  class="changePage">
                	<#list 1..issue.totalPages as i>
                		<a href="javascript:void(0);" textPageIssueId="${issue.id}" textPagePageNo="${i}" <#if i==pageNo >class="current"</#if>>${i}</a>
                	</#list>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>