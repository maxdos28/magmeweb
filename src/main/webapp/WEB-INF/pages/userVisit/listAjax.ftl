<#import "list.ftl" as list>

<#if param=="enjoyImage">
<@list.enjoyImage/>
<#elseif param="enjoyIssue">
<@list.enjoyIssue/>
<#elseif param="enjoyEvent">
<@list.enjoyEvent/>
<#elseif param="userImage">
<@list.userImage/>
</#if>

