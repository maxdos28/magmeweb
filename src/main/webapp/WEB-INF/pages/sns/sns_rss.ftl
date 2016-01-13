<rss version="2.0">
	<channel>
		<title>麦米网-M1</title>
		<link>http://www.magme.com/</link>
		<description>麦米网-M1</description>
		<pubDate>${pageTimeLock!}</pubDate>
		<lastBuildDate>${pageTimeLock!}</lastBuildDate>
		<#if creativeList?? && (creativeList?size>0)>
			<#list creativeList as c>
				<item id="${c.id?default(0)}">
					<title><![CDATA[${c.title?default("")}]]></title>
					<link><![CDATA[http://www.magme.com/sns/c${c.id?default(0)}/]]></link>
					<description>${c.content?html}</description>
					<pubDate><#if (c.updateTime)??>${c.updateTime?string("yyyy-MM-dd HH:mm:ss")}</#if></pubDate>
				</item>
			</#list>
		</#if>
	</channel>
</rss>