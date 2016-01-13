<#setting locale="en_US"> 
<rss version="2.0">
	<channel>
		<title>麦米阅读</title>
		<link>http://www.magme.com/</link>
		<description>麦米阅读</description>
		<pubDate>${today?string("EEE, dd MMM yyyy HH:mm:ss")} +0800</pubDate>
		<lastBuildDate>${today?string("EEE, dd MMM yyyy HH:mm:ss")} +0800</lastBuildDate>
		<#if articleList?? && (articleList?size>0)>
			<#list articleList as article>
			   <#if article_index!=0>
			    <#assign prevArticle=articleList.get(article_index-1)>
			   </#if>
			   <#--一个文章只能输出一次-->
			   <#if !(prevArticle??) || article.cid!=prevArticle.cid>
				<item>
					<title><![CDATA[${article.secondTitle?default("")}]]></title>
					<link>http://www.magme.com/sns/c${article.cid?default(0)}/</link>
					<description>
					<![CDATA[
					 <#if (article.magazineName)?? && (article.magazineUrl)?? && article.magazineName!="" && article.magazineUrl!="">
				         <p><a href="${article.magazineUrl!''}" target="_blank">摘自《${article.magazineName!''}》</a></p>
				       <#elseif (article.origin)?? && (article.originUrl)?? && article.origin!='' && article.originUrl!=''>
			       		 <p><a href="${article.originUrl!''}" target="_blank">来源：${article.origin!''}</a></p>
				       <#else>
				         <p><a href="javascript:void(0)"><#if (article.nickName)??>${(article.nickName)!''}<#else>${(article.userName)!''}</#if></a></p>
				     </#if>
			   </#if>
			        <p>
				    <#if article.articleExList?? && ((article.articleExList)?size>0)>
				      <#list article.articleExList as articleEx>
				         <img src="http://static.magme.com${articleEx.imgPath?default('')}" />${articleEx.content?default('')}
				      </#list>
				    </#if></p>
				    ${article.content?default("")} 
			   <#if article_index!=(articleList?size-1)>
			    <#assign nextArticle=articleList.get(article_index+1)>
			   </#if>
			   
			   <#--一个文章只能输出一次-->      
			   <#if (articleList?size==1) || (articleList?size==article_index+1) || (nextArticle?? && article.cid!=nextArticle.cid)>
			         ]]>
					</description>
					<pubDate><#if (article.startTime)??>${article.startTime?string("EEE, dd MMM yyyy HH:mm:ss")} +0800</#if></pubDate>
				</item>
			   </#if>
			</#list>
		</#if>
	</channel>
</rss>
