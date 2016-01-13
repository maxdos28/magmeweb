<html>
<head>
<title>用户空间</title>
</head>
<body>
    <!--sideMiddleRight-->
    <div id="" class="sideMiddleRight">
        <div id="snsTagManage" class="conB snsTagManage">
			<h2>标签管理</h2>
            <div class="conBody">
            	<div class="inner">
            		<#list tagList as tag>
            			<div tagId="${tag.id}" class="item">
            				<input type="hidden" name="title" value="${(tag.title)!""}" />
            				<input type="hidden" name="keyword" value="${(tag.keyword)!""}" />
            				<input type="hidden" name="description" value="${(tag.description)!""}" />
            				<a href="${systemProp.appServerUrl+"/user-tag!show.action?id="+tag.id}">
            					<img src="${systemProp.tagServerUrl}${tag.path}" />
            					<span><span>${tag.title}</span><sub name="editTag" onclick="return false" title="编辑"></sub></span>
							</a>            					
            			</div>
            		</#list>
                </div>
            </div>
        </div>
    </div>
</body>
</html>    