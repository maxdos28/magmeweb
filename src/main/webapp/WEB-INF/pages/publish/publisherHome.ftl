<!--body-->
<#import "publisherNav.ftl" as publisherNav> 
<#import "publisherComment.ftl" as publisherComment> 
<div class="body">
    <link href="${systemProp.staticServerUrl}/v3/style/channelPublisher.css" rel="stylesheet" type="text/css" />
	<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
	<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
	<script src="${systemProp.staticServerUrl}/v3/js/publisher.js"></script>
	<!--topBar-->
	<@publisherNav.main "publisherHome"/>
	<!--bodyContent-->
    <div class="conLeft">
    	<div class="userInfo clearFix">
    	    <strong class="name"><#if (publisher.publishName)??>${publisher.publishName}</#if></strong>
        	<a class="img">
        	   <img src="<#if (publisher.logo)?? && publisher.logo!="" >${systemProp.publishProfileServerUrl}${publisher.logo172}<#else>/images/head172.gif</#if>" />
        	</a>
        	
            <div class="tool clear">
                <a id="iconAdd" class="btn" <#if concern==1> style="display:none"</#if> href="javascript:void(0)" publisherId="${(publisher.id)?default('')}"> 
                                                         加为关注</a>
                <a id="iconSub" class="btn"  <#if concern==0> style="display:none" </#if> href="javascript:void(0)" publisherId="${(publisher.id)?default('')}">   
                                                        取消关注</a>
            </div>
            <div class="otherInfo">
            	<p class="iconClip"><strong>用户创建图片：</strong><span>${imageNum}</span></p>
            	<p class="iconTags"><strong>用户创建标签：</strong><span>${tagCount?default('0')}</span></p>
            	<p class="iconFriend"><strong>粉丝：</strong><span><#if publisher.followNum??>${publisher.followNum}</#if></span></p>
            	<p class="iconLike"><strong>喜欢：</strong><span>${enjoyNum}</p>
            	<p class="iconDoc"><strong>期刊：</strong><span><#if (issueList?size)??>${issueList?size}</#if></span></p>
            </div>
        </div>
      </div>
        <div class="conMiddle">
            <!--conPublisherHome-->
            <div class="conPublisherHome jqueryTagBox" id="conPublisherHome">
                <div class="ctrl">
                    <div>旗下期刊</div>
                    <div>给它留言</div>
                    <div>所有标签</div>
                </div>
                <div class="doorList clearFix">
                    <div class="item itemMgz clearFix">
                        <div id="slideBanner">
                            <div class="doorList">
                                <div class="inner clearFix">
                                  <#if issueList?? && (issueList?size>0)>
                                   <#list issueList as issue>
                                     <#if (issue_index+1)%3==1>
	                                    <div class="iitem">
	                                 </#if>
	                                         <div class="iiitem" publisherId="${publisher.id}" publicationId="${issue.publicationId}" issueId="${issue.id}">
		                                        <a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issue.id}">
							                    	<img src="${systemProp.magServerUrl}/${issue.publicationId}/172_${issue.id}.jpg" />
							                    </a>
							                    <h6>
							                        <em class="iconHeart" favTypeId="mag_${issue.id}"><#if (issue.enjoyNum)??>${issue.enjoyNum}</#if> </em>
							                    </h6>
							                    <h5>
							                        <span><img src="<#if (publisher.logo)?? >${systemProp.publishProfileServerUrl}${avatarResize(publisher.logo,'30')} <#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /></span>
							                        <a><#if issue.publicationName??>${issue.publicationName}</#if></a>
							                        <strong><#if issue.publishDate??>${issue.publishDate?string("yyyy-MM-dd")}</#if></strong>
							                    </h5>
						                    </div>
	                                   <#if (issue_index!=0 && (issue_index+1)%3==0) || (issue_index==(issueList?size-1))>
	                                    </div>
	                                   </#if>
                                    </#list>
                                   </#if>
                                </div>
                            </div>
                        </div>
                        <!--conWeibo-->
                        <#if (publisher.weiboUid)?? && (publisher.weiboVerifier)??>
	                        <div class="conB mgb0 conWeibo">
	                            <!-- <h2>米商微博</h2> -->
	                            <div class="conBody" style=" height:500px; background:#f9f9f9; text-align:center; line-height:500px; font-size:30px; color:#ccc;">
	                                <iframe width="100%" height="500" class="share_self"  frameborder="0" scrolling="no" src="http://widget.weibo.com/weiboshow/index.php?width=0&height=500&fansRow=2&ptype=1&speed=0&skin=1&isTitle=1&noborder=1&isWeibo=1&isFans=0&uid=${publisher.weiboUid}&verifier=${publisher.weiboVerifier}"></iframe>
	                            </div>
	                        </div>
                        </#if>
                    </div>
                    <div class="item itemMsg clearFix">
                        <div class="conComment">
                           <#if !(session_publisher??) || (session_publisher.id)!=publisher.id>
                            <div class="iitem iitemTitle" name="#comment">
                                <strong>留言<span>(${messageCount?default('0')})</span></strong>
                            </div>
                            <div class="iitem inputArea clearFix">
                                <textarea id="commentContent" class="input" tips="还没有评论，赶快抢沙发！"></textarea>
                                <div class="clearFix">
                                	<a id="commentBtn" publisherId="${(publisher.id)?default('')}" href="javascript:void(0)" class="btnBS floatr" >留言</a>
                                </div>
                            </div>
                           </#if>
                           <@publisherComment.main/>
                            <div id="changePage" class="changePage" publisherId="${(publisher.id)?default('')}">
                              <#if messageCount?? && (messageCount>0)>
                                <a href="javascript:void(0)" nav="1"><<</a>
                                <a href="javascript:void(0)" nav="prev"><</a>
                                <#if ((messageCount%size)>0)>
							    	  <#assign totalPages=(messageCount/size)+1/>
							       <#else>
							          <#assign totalPages=messageCount/size/>
							    </#if>
                                <#list 1..totalPages as page>
	                                <a href="javascript:void(0)" page="${page}" <#if (page>9)>class="hide"</#if> <#if pageNo==page>class="current"</#if> >${page}</a>
                                </#list>
                                <a href="javascript:void(0)" nav="next" totalPages="${totalPages}">></a>
                                <a href="javascript:void(0)" nav="${totalPages}">>></a>
                              </#if>
                            </div>
                        </div>                        
					</div>
					
                    <div class="item itemTag clearFix">
                        <div class="inner clearFix">
                            <ul class="clearFix listB">
                               <#if imageTagList?? && (imageTagList?size>0)>
                                  <li><a href="javascript:void(0)" class="title">米商图片的标签：</a></li>
                                  <#list imageTagList as imageTag>
                                     <li><a href="${systemProp.appServerUrl}/search!byTagName.action?tagName=${imageTag.name}&searchType=UserImage">${imageTag.name}</a></li>
                                  </#list>
                                </#if>
                            </ul>
                        </div>
            		</div>
                </div>
            
            </div>
        </div>
        <div class="conRight">
            <!--conFriend-->
            <div class="conB conFriend">
                <h2><a publisherId=${(publisher.id)?default('')} class="floatr important" href="${systemProp.appServerUrl}/search!byPublisherId.action?publisherId=${(publisher.id)?default('')}">更多</a>粉丝</h2>
                <div class="conBody">
                    <ul class="clearFix">
                      <#if userFollowList??>
	                       <#list userFollowList as userFollow>
		                        <li>
		                         <a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userFollow.id}" userId="${userFollow.id}">
		                          <img src="<#if (userFollow.avatar)??> ${systemProp.profileServerUrl}${avatarResize(userFollow.avatar,'46')} <#else> ${systemProp.staticServerUrl}/images/head46.gif </#if>" title="${userFollow.nickName}" />
		                          <span title="${userFollow.nickName}">${userFollow.nickName}</span>
		                         </a>
		                        </li>
	                       </#list>
                      </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
<!--footer-->
<div class="conAnswer" id="conAnswer" style="display:none;">
    <textarea id="conAnswerText" class="input" tips="请输入回复内容"></textarea>
    <a href="javascript:void(0)" publisherId="${(publisher.id)?default('')}" class="btnBS floatr" >回复</a>
</div>
</body>
