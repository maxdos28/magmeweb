<document>
	<webName>magme</webName>
	<webSite>http://www.magme.com</webSite>
    <#if issueMapList?? && (issueMapList?size>0)>
       <#list issueMapList?values as issueList>
		    <item>
			    <#list issueList as issue>
			        <#if issue_index==0>
					    <op>add</op>
						<title>${(issue.publicationName)?html}</title>
					    <class>${(issue.categoryName)?html}</class>
					    <Description><![CDATA[ ${(issue.pubDesc)!''} ]]></Description> 
				    </#if>
				    <series>
				       <phases>第${issue.publishDate?string("yyyyMMdd")}期</phases>
				       <date>${issue.publishDate?string("yyyy-MM-dd")}</date>
				       <uptime>${issue.updatedTime?string("yyyy-MM-dd")}</uptime>
				       <imageLink>http://static.magme.com/pdfprofile/${issue.publicationId?default("")}/200_${issue.id?default("")}.jpg</imageLink>
				       <playLink>http://www.magme.com/publish/mag-read.action?id=${issue.id?default("")}</playLink>
				    </series>
			    </#list>
			</item>	
		</#list>
	</#if>
</document>