<html>
<head>
<title>用户空间</title>
</head>
<body>
    <!--sideMiddle-->
    <div class="sideMiddle">
        <div class="con jqueryTagBox" id="snsMagezine">
            <div class="ctrl">
                <div pos='0'>我的收藏</div>
                <div pos='1'>我的订阅</div>
            </div>
            <div class="doorList clearFix">
                <div class="item itemSubscribe clearFix">
                    <div class="inner clearFix">
                    	<#if (favoriteList?? && (favoriteList?size > 0))>
	                    	<#list favoriteList as favorite>
		                        <div userFavoriteId="${favorite.id}" class="fItem clearFix">
		                            <a class="link" issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+favorite.issueId}" href="javascript:void(0)">
		                            	<img src="${systemProp.magServerUrl}/${favorite.issue.publicationId}/110_${favorite.issue.id}.jpg" />
		                            </a>
		                            <a class="name" href="javascript:void(0)">
		                            	<strong>${favorite.issue.publicationName}</strong>
		                            </a>
		                            <a name="deleteFavorite" href="javascript:void(0)" class="cancel">取消收藏</a>
		                            <!--    
		                                <p>${(favorite.issue.description)!""}</p>
		                            -->
		                            <div class="info">
		                                <p>共有<span>${favorite.issue.totalFavoriteNum}</span>人收藏</p>
		                            </div>
		                        </div>
	                        </#list>
	                    <#else>
	                    	<div class="nullInfo">您还没有任何收藏</div>
	                    </#if>
	                    	<!--conRecommend-->
	                        <div class="conB conRecommend">
	                            <h2>您可能会感兴趣的杂志</h2>
	                            <div class="conBody clearFix">
	                            	<#if issueList??>
	                            	<#list issueList as issue>
	                            		<#if issue_index%4==0>
		                                <div class="outer">
		                                    <div class="inner">	                            		
	                            		</#if>
												<a class="item showBar"  issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+issue.id}" href="javascript:void(0)">
													<img src="${systemProp.magServerUrl}/${issue.publicationId}/110_${issue.id}.jpg" />
													<span>${issue.issueNumber}</span>
													<div class="bookBar" issueId="${issue.id}">
														<p>
															<em class='save' name='subscribe' title='订阅' onclick='return false'>${issue.subscribeNum}</em>
															<em class='fav' name='collection' title='收藏' onclick='return false'>${issue.favoriteNum}</em>
														</p>
													</div>
												</a>	                            		
                            			<#if (issue_index%4==3)||((issue_index+1)==issueList?size)>	                            		
		                                    </div>
		                                </div>
		                                </#if>	                            	
	                            	</#list>
	                            	</#if>
	                            </div>
	                        </div>	                    
                    </div>
                </div>
                <div class="item itemFavorite clearFix">
                    <div class="inner clearFix">
                    	<#if (subscribeList?? && (subscribeList?size > 0))>
	                    	<#list subscribeList as subscribe>
	                    		<#if (subscribe.publication??)&&(subscribe.publication.lastIssue??)>
		                        <div userSubscribeId="${subscribe.id}" class="fItem clearFix">
		                            <a class="link" issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+subscribe.publication.lastIssue.id}" href="javascript:void(0)">
		                            	<#if (subscribe.publication.lastIssue)??>
		                                <img src="${systemProp.magServerUrl}/${subscribe.publication.id}/110_${subscribe.publication.lastIssue.id}.jpg" />
		                                <#else>
		                                <img src="images/tempMagezineBig2.jpg" />
		                                </#if>
		                            </a>
		                            <a class="name" href="javascript:void(0)">
		                                <strong>${subscribe.publication.name}</strong>
		                            </a>
		                            <a href="javascript:void(0)" class="cancel" name="deleteSubscribe">取消订阅</a>
		                            <!--
		                                <p>${subscribe.publication.description}</p>
		                            -->
		                            <div class="list">
		                            	<#list subscribe.publication.issueList as issue>
		                                <a issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+issue.id}" href="javascript:void(0)">${issue.issueNumber}</a>
		                                </#list>
		                            </div>
		                        </div>
		                        </#if>                    	
	                    	</#list>
                    	<#else>
	                    	<div class="nullInfo">您还没有任何订阅 </div>
	                    </#if>
	                    	<!--conRecommend-->
	                        <div id='conRecommend' class="conB conRecommend">
	                            <h2>您可能会感兴趣的杂志</h2>
	                            <div class="conBody clearFix">
	                            	<#if publicationList??>
	                            	<#list publicationList as publication>
	                            		<#if publication_index%4==0>
		                                <div class="outer">
		                                    <div class="inner">	                            		
	                            		</#if>
	                            		<#if publication.lastIssue??>
												<a class="item showBar"  issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+publication.lastIssue.id}" href="javascript:void(0)">
													<img src="${systemProp.magServerUrl}/${publication.lastIssue.publicationId}/110_${publication.lastIssue.id}.jpg" />
													<span>${publication.lastIssue.issueNumber}</span>
													<div class="bookBar" issueId="${publication.lastIssue.id}">
														<p>
															<em class='save' name='subscribe' title='订阅' onclick='return false'>${publication.lastIssue.subscribeNum}</em>
															<em class='fav' name='collection' title='收藏' onclick='return false'>${publication.lastIssue.favoriteNum}</em>
														</p>
													</div>
												</a>	                            				
	                            		</#if>
                            			<#if (publication_index%4==3)||((publication_index+1)==publicationList?size)>	                            		
		                                    </div>
		                                </div>
		                                </#if>	                            	
	                            	</#list>
	                            	</#if>	                            
	                            </div>
	                        </div>	                    
                    </div>
                </div>
            </div>
        </div>
    </div>
    	
    <div class="sideRight">
        <div class="ADBanner mgb20 mgt30">
        	<img src="${systemProp.staticServerUrl}/images/grey_300x250.jpg">
        </div>
    </div>
    
<script>
$(function(){
	$.jquerytagbox("#snsMagezine",${pos!"0"});
});
</script>    
</body>
</html>    