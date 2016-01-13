<#macro main moreNewsFeed>
    	<div id="followInfosArea" class="conB conInteractionArea">
        	<h2>麦动广场</h2>
            <div id="newsFeed" class="conBody clearFix">
            	<#if (feedCount>0)>
	            	<#list feedList as feed>
		            	<#if (feed.object)??>
						<div class="listItem clearFix">
		                	<div class="left">
		                    	<div class="userHead">
		                    		<a href="${systemProp.appServerUrl+"/user!visit.action?id="+feed.user.id}">
		                    			<#if (feed.user.avatar)??&&(feed.user.avatar!="")>
		                    			<img src="${systemProp.profileServerUrl+feed.user.avatar60}" title="${feed.user.nickName}" />
		                    			<#else>
		                    			<img src="${systemProp.staticServerUrl}/images/head60.gif" title="${feed.user.nickName}" />
		                    			</#if>
		                    			<sup></sup>
		                    		</a>
		                    	</div>
		                    </div>
		                	<div class="right">
		                		<h6>
		                			<a href="${systemProp.appServerUrl+"/user!visit.action?id="+feed.user.id}">${feed.user.nickName}</a>
		                			<#if feed.actionTypeId==7&&(feed.object)??>
		                				<span>收藏了杂志</span>
		                			<#elseif feed.actionTypeId==8&&(feed.object)??>
		                				<span>订阅了杂志</span>
		                			<#elseif feed.actionTypeId==10&&(feed.object)??>
		                				<span>制作了标签</span>
		                			</#if>
		                		</h6>
		                		<#if feed.actionTypeId==7&&(feed.object)??>
		                        <div class="content clearFix">
		                        	<a issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+feed.object.issue.id}" class="floatlImg" href="javascript:void(0)">
			                        	<img class="floatlImg" src="${systemProp.magServerUrl+"/"+feed.object.issue.publicationId+"/100_"+feed.object.issue.id+".jpg"}" />
		                        	</a>
		                        	<strong>
			                        	<a issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+feed.object.issue.id}" href="javascript:void(0)">
			                           		${feed.object.issue.publicationName}
			                           	</a>
		                           	</strong>
		                            <span>${feed.object.issue.issueNumber}</span>
		                            <p>${(feed.object.issue.description)!""}</p>
		                            </a>
		                        </div>
		                        <div class="navBtn" issueId="${feed.object.issue.id}">
		                        	<span>${feed.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
		                        	<a class="iconFavorites" name="collection" href="javascript:void(0)">收藏</a>
		                        	<a class="iconSubscribe" name="subscribe" href="javascript:void(0)">订阅</a>
		                        </div>
								<#elseif feed.actionTypeId==8&&(feed.object)??>
		                        <div class="content clearFix">
		                        	<a issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+feed.object.publication.lastIssue.id}" class="floatlImg" href="javascript:void(0)">
		                        		<img class="floatlImg" src="${systemProp.magServerUrl+"/"+feed.object.publication.id+"/100_"+feed.object.publication.lastIssue.id+".jpg"}" />
		                            </a>
		                            <strong>
			                            <a issueReadUrl="${systemProp.appServerUrl+"/publish/mag-read.action?id="+feed.object.publication.lastIssue.id}" href="javascript:void(0)">
			                            	${feed.object.publication.description}
			                            </a>
		                            </strong>
		                            <p>${(feed.object.publication.description)!""}</p>
		                            </a>
		                        </div>
		                        <div class="navBtn" issueId="${feed.object.publication.lastIssue.id}">
		                        	<span>${feed.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
		                        	<a class="iconFavorites" name="collection" href="javascript:void(0)">收藏</a>
		                        	<a class="iconSubscribe" name="subscribe" href="javascript:void(0)">订阅</a>
		                        </div>
				                <#elseif feed.actionTypeId==10&&(feed.object)??>
		                        <div class="content clearFix">
		                        	<a class="floatlImg" href="${systemProp.appServerUrl+"/user-tag!show.action?id="+feed.object.id}">
			                        	<img class="floatlImg" src="${systemProp.tagServerUrl+feed.object.path}" />
		                            </a>
		                            <strong>
		                            	<a name='tagTitle' href="${systemProp.appServerUrl+"/user-tag!show.action?id="+feed.object.id}">
		                            		${feed.object.title}
		                            	</a>
		                            </strong>
		                            <p>${(feed.object.description)!""}</p>
		                        </div>
		                        <div class="navBtn" tagId="${feed.object.id}">
		                        	<span>${feed.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
		                        	<a class="iconRecommended" name="tagTop" tagId="${(feed.object.id)!"0"}" href="javascript:void(0)">推荐(${feed.object.topNum})</a>
		                        	<a class="iconComments" name="tagContent" href="javascript:void(0)">评论</a>
		                        	<!--
		                        	<a href="javascript:void(0)" name="tagShare" class="iconShare">分享</a>
		                        	-->
		                        </div>
								<div class="comments">
		                           	<h5 class="otherPeople"><strong>最新评论</strong>(总共${feed.object.totalCommentNum}条)</h5>
		                           	<#if (feed.object.tagCommentList)??>
			                           	<#list feed.object.tagCommentList as tagComment>
				                            <div class="item">
				                            	<a href="${systemProp.appServerUrl}/user!visit.action?id=${tagComment.user.id}">
								                <#if (tagComment.user.avatar)??>
								                <div class="smallHead"><img src="${systemProp.profileServerUrl+tagComment.user.avatar46}"/></div>
								                <#else>
								                <div class="smallHead"><img src="${systemProp.staticServerUrl}/images/head46.gif" /></div>
								                </#if>
								                </a>                            
				                                <p><strong><a href="${systemProp.appServerUrl}/user!visit.action?id=${tagComment.user.id}">${tagComment.user.nickName}</a></strong>：${tagComment.contentInfo.content}</p>
				                                <div class="navBtn">
				                                    <span>${tagComment.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
				                                </div>
				                            </div>
			                            </#list>  
		                            </#if>                         	
		                           	<div class="item inputArea clearFix">
		                           		<textarea name="content" class="auto"></textarea>
		                           		<a tagId="${feed.object.id}" class="btnBS" name="tagCommentSubmit" href="javascript:void(0)">提交</a>
		                           	</div>
								</div>                        
				                </#if>                         
		                    </div>
		                </div>
		                </#if>
	            	</#list>
	            <#else>
	            	<div class="nullInfo">
	                	暂时没有动态更新
	                </div>	
            	</#if>
            </div>
            <#if (feedList?? && (feedList?size > 0))>
            	<div id="loadMore" class="loadMore hide"><a name="moreNewsFeed" userId="${(visitUser.id)!"0"}" listbegin="5" listsize="5" listcount="${feedCount!"0"}" href="javascript:void(0)">以往更多动态</a></div>
        	</#if>
        </div>
</#macro>