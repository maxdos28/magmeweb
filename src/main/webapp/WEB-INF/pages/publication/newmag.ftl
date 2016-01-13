<html>
<head>

</head>
<body>
<script>
$(function(){
	//右侧热门文章执行
	$(".sideRight .conListBig a .pic img").coverImg();
});
</script>

<link href="${systemProp.staticServerUrl}/v3/style/channelMagazinev6.css" rel="stylesheet" type="text/css" />
<!--body-->
<div class="body pageMagazineHome clearFix">
	
    <div class="sideLeft">
    	<div class="conA conMgzInfo mgb0">
        	<a href="javascript:void(0);" class="cover">
        		<#if issueList?? &&  issueList.size()&gt;0 >
            		<img src="${systemProp.magServerUrl}/${publication.id}/200_${publication.issueId!}.jpg" />
	        	<#else>
	        		<img src="<#if (publication.logo)?? && publication.logo!="" >${systemProp.publishProfileServerUrl}${publication.logo!}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" />
	        	</#if>
	        	<span></span>
        	</a>
            <div class="info">
                <strong>${((publication.name)!"")}</strong>
                <#if publication.englishname??>
                	<p><span>英文名称：</span>${((publication.englishname)!"")}</p>
                </#if>
                <#if publisher.publishName??>
                	<p><span>出版社名称：</span>${publisher.publishName!}</p>
                </#if>
                <#if publication.createDate??>
                	<p><span>创刊时间：</span>${publication.createDate?string('yyyy')}</p>
                </#if>
                <#if category.name??>
                	<p><span>杂志类目：</span>${category.name!}</p>
                </#if>
                <#if publisher.webSite??>
                	<p><span>网站地址：</span>${publisher.webSite!}</p>
                </#if>
                <a href="<#if publication.signing==0>${systemProp.appServerUrl}/publish/mag-read.action?id=${publication.issueId!}<#else>javascript:void(0);</#if>"  class="btnAB"><span></span>在线阅读</a>
                <a href="javascript:void(0);" id='show_email_subscribe' class="btnWB"><span></span>杂志订阅</a>
            </div>
        </div>
    	<div class="conA conMgzDescription">
        	<h2>杂志简介</h2>
            <div class="conBody conMgzBody">
            	${publication.description!""}
            </div>
        	<#if publication.assessment?? && publication.assessment!="">
	        	<h2>编辑评价</h2>
	            <div class="conBody">
	            	${publication.assessment!""}
	            </div>
            </#if>
        </div>
        
        <#if issueList?? && issueList.size()&gt;0>
    	<div class="conA conMoreMgz">
			<#if issueList.size()&gt;4>
    			<h2><a target="_blank" href="${systemProp.appServerUrl}/publish/publication-home!magList.action?pubName=${((publication.englishname)!"")}">查看全部期刊</a>过往期刊</h2>
    		</#if>
            <div class="conBody">
            <#if issueList??>
            	<#list issueList as issue>
            		<#if issue_index&lt;4>
	                	<a target="_blank" title='${(issue.publicationName)!""}_${(issue.issueNumber)!""}' href="${systemProp.appServerUrl}/publish/mag-read.action?id=${(issue.id)}">
	                		<img src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" />
	                	</a>
	                </#if>
                </#list>
            </#if>
            </div>
        </div>
        </#if>
        <div id='PublicationComment' pid='${publication.id}' class="conA sendBox clearFix mgb0">
        	<img class="head" src="<#if session_user??><#if session_user.avatar??>${systemProp.profileServerUrl}${session_user.avatar!}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if><#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'"  />
            <div class="right">
            	<div class="input"><textarea  maxlength="196" tips="请输入文字" color="#9daf7b,#7c8865"></textarea><em></em></div>
                <div class="clearFix">
                    <a href="javascript:void(0);" class="btnGB">评论</a>
                    <strong class="txtNum">您还可以输入<span>196</span>字</strong>
                </div>
            </div>
        </div>
    	<div class="conA">
        	<h2>杂志评论</h2>
            <div id='p_comment' class="conBody conReply conReplyBig">
            	<#list comment as c>
            		<div class="bl"><a href="${systemProp.appServerUrl}/sns/u${c.uid}/" class="head">
            		<img src="<#if c.avatar??>${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" title="${c.nickname!}" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a><p><span>
            		<strong><a href="${systemProp.appServerUrl}/sns/u${c.uid}/">${stringSub(c.nickname!,20)}</a>${c.createTime?string("yyyy-MM-dd")}</strong>${c.content!}</span></p><em></em>
            		</div>	
            	</#list>
            </div>
            <a href="javascript:void(0);" class="conReplyMore"></a>
        </div>
    </div>
    
    
    
	<div class="sideRight sideRightLayout">
        
        <div class="con conSameMagazine">
            <a href="javascript:void(0)" class="title">同类杂志阅读</a>
            <#list alikePublicationList as pub>
            	<#if pub_index&lt;6>
	            	<#if pub_index==0 || pub_index==3>
	            		<div class="desk">
	            	</#if>
                	<a  href="http://${pub.domain}.magme.com/${pub.englishname}/">
                		<img src="<#if pub.lastIssue?? >${systemProp.magServerUrl}/${pub.lastIssue.publicationId}/200_${pub.lastIssue.id}.jpg<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" title="${pub.name!}" />
                	</a>
	            	<#if pub_index==2 || pub_index==5>
	            		 </div>
	            	</#if>
        		</#if>
            </#list>
        </div>
        
        <div class="con conTopMagazine">
            <a href="javascript:void(0)" class="title">杂志订阅排名</a>
            <#list publicationOrder as po >
            	<a href="http://${po.domain}.magme.com/${po.ename!}/"><h3>${po_index+1}.&nbsp;[&nbsp;${po.pname!}&nbsp;]</h3>
            		<#if po.leve==1>
            			<span class="up"></span>
            		<#elseif po.leve==0>
            			<span class="null"></span>
            		<#elseif po.leve==-1>
            			<span class="down"></span>
            		</#if>
            	 	<span class="up"></span>
        	 	</a>
            </#list>
        </div>
    
        <div class="con conMagazineList">
            <a href="javascript:void(0)" class="title">其他网友正在看</a>
            <div class="conBody tagList">
            	
            	<ul class="clearFix">
            		<#list otherPublicationList as pub>
	            		<#if pub.name?? && pub_index lt 20>
		                	<li><a href="http://${pub.domain}.magme.com/${pub.englishname}/"><span>${pub.name}</span></a></li>
	            		</#if>
	                </#list>
                </ul>
            </div>
        </div>
        
    </div>
</div>

<div class="popContent" id="emailSubscribe" >
	<fieldset class="new">
    	<h6>杂志订阅</h6>
    	<div>
    		<em><input type="text" tips="请输入您的电子邮件地址" class="input g310" style="color: rgb(204, 204, 204);"></em>
    	</div>
    	<em id='email_subscribe_error' style='display:none; color:red;'>电子邮箱地址错误</em>
    	<div><em class="tips">成功订阅后，我们会在每次新刊上架时给您发邮件，请务必填写正确的邮箱地址。</em></div>
        <div class="tRight" ><a pid='${publication.id}' tp='${publication.domain!}'  class="btnBS" href="javascript:void(0)">订阅</a></div>
    </fieldset>
</div>
<script>
$(function(){ 
	$("#headerSearch").show();
});
</script>
</body>
</html>
