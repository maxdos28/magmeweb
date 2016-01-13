<#setting locale="en_US"> 
<rss version="2.0">
	<channel>
		<title>麦米阅读</title>
		<link>http://www.magme.com/</link>
		<description>麦米阅读</description>
		<pubDate>${today?string("EEE, dd MMM yyyy HH:mm:ss")} +0800</pubDate>
		<lastBuildDate>${today?string("EEE, dd MMM yyyy HH:mm:ss")} +0800</lastBuildDate>
		<#if issueList?? && (issueList?size>0)>
			<#list issueList as issue>
			   <#if issue_index!=0>
			    <#assign prevIssue=issueList.get(issue_index-1)>
			   </#if>
			   <#--一个文章只能输出一次-->
			   <#if !(prevIssue??)>
				<item>
					<title><![CDATA[${issue.publicationName?default("")}]]></title>
					<link>${issue.fileName?default("")}/</link>
					<size>${issue.p1Size?default("")}/</size>
					<issueId>${issue.id?default("")}/</issueId>
					<publicationId>${issue.publicationId?default("")}/</publicationId>
					<totalPages>${issue.totalPages?default("")}/</totalPages>
					<description>
					<![CDATA[${issue.description?default("")}]]>
					</description>
					<updateTime><#if (issue.updatedTime)??>${issue.updatedTime?string("EEE, dd MMM yyyy HH:mm:ss")} +0800</#if></updateTime>
				</item>
			   </#if>
			</#list>
		</#if>
	</channel>
</rss>
