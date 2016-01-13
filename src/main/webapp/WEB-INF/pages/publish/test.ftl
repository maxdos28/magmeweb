<#import "../publish/pubCategory.ftl" as pubCategory>
<#import "../publish/issueItem.ftl" as issueItem>

<html>
<head>
<title>看米</title>
</head>
<body>
<#if issueList??>
<#list issueList as issue>
<div issueId="${issue.id}">
<div>
</div>
<#if issue.totalPages??>
<#list 0..(issue.totalPages) as pageNo> 
<div>
<img src="${systemProp.magServerUrl}/${(issue.publicationId)!"0"}/${(issue.id)!"0"}/768_${pageNo}.jpg">
<div>
</#list>
</#if>
</div>
</#list>
</#if>
</body>
</html>