<rss version="2.0">
	<channel>
		<title>麦米阅读</title>
		<link>http://www.magme.com/</link>
		<description>麦米阅读</description>
		<pubDate>${today?string("yyyy-MM-dd HH:mm:SS")}</pubDate>
		<lastBuildDate>${today?string("yyyy-MM-dd HH:mm:SS")}</lastBuildDate>
		<#if fpageEventList?? && (fpageEventList?size>0)>
			<#list fpageEventList as fpageevent>
				<item id="${fpageevent.id?default(0)}">
					<title><![CDATA[${fpageevent.title?default("")}]]></title>
					<link>http://www.magme.com/third/rss-event-detail.action?eventId=${fpageevent.id?default(0)}</link>
					<description>${fpageevent.description?html}</description>
					<pubDate><#if (fpageevent.updatedTime)??>${fpageevent.updatedTime?string("yyyy-MM-dd HH:mm:ss")}</#if></pubDate>
				</item>
			</#list>
		</#if>
	</channel>
</rss>