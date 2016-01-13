<#macro main>

<!--magezineBox-->
<#if familyIssueMap??>
	    <#list familyIssueMap?keys as familyIssueMapKey>
		    <#assign familyIssueList=familyIssueMap[familyIssueMapKey]>
		    <#if (lastestIssueMap[familyIssueMapKey])??>
		       <#assign lastestIssueList=lastestIssueMap[familyIssueMapKey]>
		    </#if>
		    <div class="kanmiCon kc${familyIssueMapKey_index+1} <#if ((familyIssueMapKey_index+1)==familyIssueMap?size)>kanmiConLast</#if>" id="${familyIssueMapKey_index}">
			    <div class="bigShow">
			       <div class="textList png">
			         <#if familyIssueList??>
			           <#list familyIssueList as familyIssue>
				         <#if (familyIssue_index<6)>
			            	<a target="_magme" href="/publish/mag-read.action?id=${familyIssue.issueId}<#if familyIssue.pageNum??>&pageId=${familyIssue.pageNum}</#if>">
			                	<strong>${familyIssue.title}</strong>
			                    <p>${familyIssue.appendTitle}</p>
			                    <span>${familyIssue.content}</span>
			                </a>
					      </#if>
					    </#list>
					  </#if>
				    </div>  <!--  End-textList  -->
						    
				    <div class="doorList">
				   		<div class="inner clearFix">
				   		  <#if familyIssueList??>
					   		<#list familyIssueList as familyIssue2>
					           <#if (familyIssue2_index<6)>
					                    <div issueId="${familyIssue2.issueId}" pageNum="<#if (familyIssue2.pageNum)??>${familyIssue2.pageNum}</#if>" class="item">
					                        <a class="showBar" href="javascript:void(0)">
					                        	<img name="issueRead" src="${systemProp.magServerUrl}/${familyIssue2.publicationId}/248_${familyIssue2.issueId}.jpg" />
					                        	<span name="issueRead"><#if (familyIssue2.publicationName)??><#if (familyIssue2.publicationName)??>${familyIssue2.publicationName}</#if></#if>${familyIssue2.issueNum}</span>
					                        	<div class="bookBar">
					                        		<p>
					                        			<em class='save' title='订阅' name="subscribe" ><#if (familyIssue2.subscribeNum)?? && (familyIssue2.subscribeNum)!=0 >${familyIssue2.subscribeNum}</#if></em>
			                        			 		<em class='fav' title='收藏' name="collection" ><#if (familyIssue2.favoriteNum)?? && (familyIssue2.favoriteNum)!=0 >${familyIssue2.favoriteNum}</#if></em>
					                        		</p>
					                        	</div>
					                        </a>
					                    </div>
							       </#if>
						        </#list>
					        </#if>
				        </div>
			        </div>   <!--  End-doorList  -->
				</div> <!--  End-bigShow  -->
				   
				<div class="smallShow">
					<#if lastestIssueList?? && (lastestIssueList?size > 0)>
						<#assign lastestIssueCount=1>
						<#list lastestIssueList as lastestIssue>
							<#if lastestIssueCount==7>
							   <#assign lastestIssueCount=1>
							</#if>
							
							<#if lastestIssueCount==1>
							<div class="item" ><!--  start-item  -->
							</#if>
							<#if lastestIssueCount==1 || lastestIssueCount==4>
							   <div class="magezineBox  <#if (lastestIssueCount>3)>magezineBoxLast</#if>">
							</#if>
							         <a issueId="${lastestIssue.id}" class="showBar m<#if (lastestIssueCount>3)>${lastestIssueCount-3}<#else>${lastestIssueCount}</#if>" href="javascript:void(0)">
							         	<img name="issueRead" src="${systemProp.magServerUrl}/${lastestIssue.publicationId}/110_${lastestIssue.id}.jpg" />
							         	<span name="issueRead" ><#if (lastestIssue.publicationName)??>${lastestIssue.publicationName}</#if>${lastestIssue.issueNumber}</span>
							         	<div class="bookBar">
							         		<p>
							         			<em class='save' title='订阅' name="subscribe" ><#if (lastestIssue.subscribeNum)?? && (lastestIssue.subscribeNum)!=0>${lastestIssue.subscribeNum}</#if></em>
			                        			<em class='fav' title='收藏' name="collection" ><#if (lastestIssue.favoriteNum)?? && (lastestIssue.favoriteNum)!=0>${lastestIssue.favoriteNum}</#if></em>
							         		</p>
							         	</div>
							         </a>
							 <#if ((lastestIssue_index+1)%3==0 && lastestIssue_index!=0) || (lastestIssue_index+1)==(lastestIssueList?size)>
							    </div>
							 </#if>
							<#if ((lastestIssue_index+1)%6==0 && lastestIssue_index!=0) || (lastestIssue_index+1)==(lastestIssueList?size)>
							</div><!--  End-item  -->
							</#if>
							<#--做完后自增1-->
							<#assign lastestIssueCount=lastestIssueCount+1>
						 </#list>
					 </#if>
					<a class="ctrl turnLeft png" href="javascript:void(0)">
						<span></span>
					</a>
		            <a class="ctrl turnRight png" href="javascript:void(0)">
		            	<span></span>
		            </a>	      
				</div><!--  End-smallShow  -->
			</div>
		</#list>
</#if>
</#macro>