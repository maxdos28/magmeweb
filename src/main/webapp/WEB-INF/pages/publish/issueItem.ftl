<#macro main>

<!--magezineBox-->
<#if familyIssuesMapList??>
   <#if familyNames??>
     <#list familyIssuesMapList as familyIssuesMap>
       <#if familyIssuesMap[familyId]??>
		    <#assign familyIssues=familyIssuesMap[familyId]>
		    <div class="kanmiCon kc${familyIssuesMap_index+1} <#if (familyIssuesMap_index+1==familyIssuesMapList?size)>kanmiConLast</#if>" id="${familyId}${familyIssuesMap_index}">
			    <div class="bigShow">
			       <div class="textList png">
				       <#list familyIssues as familyIssue>
				         <#if (familyIssue_index<6)>
			            	<a target="_magme" href="/publish/mag-read.action?id=${familyIssue.issueId}<#if familyIssue.pageNum??>&pageId=${familyIssue.pageNum}</#if>">
			                	<strong>${familyIssue.title}</strong>
			                    <p>${familyIssue.appendTitle}</p>
			                    <span>${familyIssue.content}</span>
			                </a>
					      </#if>
					    </#list>
				    </div>  <!--  End-textList  -->
						    
				    <div class="doorList">
				   		<div class="inner clearFix">
						    <#list familyIssues as familyIssue2>
						         <#if (familyIssue2_index<6)>
				                    <div issueId="${familyIssue2.issueId}" pageNum="<#if (familyIssue2.pageNum)??>${familyIssue2.pageNum}</#if>" class="item">
				                        <a class="showBar" href="javascript:void(0)">
				                        	<img name="issueRead" src="${systemProp.magServerUrl}/${familyIssue2.publicationId}/248_${familyIssue2.issueId}.jpg" />
				                        	<span name="issueRead">${familyIssue2.issueNum}</span>
				                        	<div class="bookBar">
				                        		<p>
				                        			<em class='save' title='订阅' name="subscribe" ></em>
				                        			<em class='fav' title='收藏' name="collection" ></em>
				                        		</p>
				                        	</div>
				                        </a>
				                    </div>
						         </#if>
					        </#list>
				        </div>
			        </div>   <!--  End-doorList  -->
				
				</div> <!--  End-bigShow  -->
					        
				<div class="smallShow">
					<!--前面6个-->
					<#if (familyIssues?size > 0)>
					<div class="item" style="display:block">
					    <div class="magezineBox">
						    <#list familyIssues as familyIssue3>
						         <#if (familyIssue3_index<3)>
							         <a issueId="${familyIssue3.issueId}" class="showBar m${familyIssue3_index+1}" href="javascript:void(0)">
							         	<img name="issueRead" src="${systemProp.magServerUrl}/${familyIssue3.publicationId}/110_${familyIssue3.issueId}.jpg" />
							         	<span name="issueRead" >${familyIssue3.issueNum}</span>
							         	<div class="bookBar">
							         		<p>
							         			<em class='save' title='订阅' name="subscribe" ></em>
			                        			<em class='fav' title='收藏' name="collection" ></em>
							         		</p>
							         	</div>
							         </a>
						         </#if>
						    </#list>
						</div>
						         
						<div class="magezineBox magezineBoxLast">
						    <#list familyIssues as familyIssue4>
						         <#if (familyIssue4_index>=3) && (familyIssue4_index<6)>
							         <a issueId="${familyIssue4.issueId}" class="showBar m${familyIssue4_index-2}" href="javascript:void(0)">
							         <img  name="issueRead" src="${systemProp.magServerUrl}/${familyIssue4.publicationId}/110_${familyIssue4.issueId}.jpg" />
							         <span name="issueRead" >${familyIssue4.issueNum}</span>
								         <div class="bookBar">
									         <p>
										         <em class='save' title='订阅' name="subscribe" ></em>
			                        			 <em class='fav' title='收藏' name="collection" ></em>
									         </p>
								         </div>
							         </a>
						         </#if>
						    </#list>
						</div>
					</div><!--  End-item  -->
					</#if>
						      
					<!--后面6个-->
					<#if (familyIssues?size > 6)>
					<div class="item">
					    <div class="magezineBox">
						    <#list familyIssues as familyIssue5>
						        <#if (familyIssue5_index>=6) && (familyIssue5_index<9)>
							        <a issueId="${familyIssue5.issueId}" class="showBar m${familyIssue5_index-5}" href="javascript:void(0)">
								        <img  name="issueRead" src="${systemProp.magServerUrl}/${familyIssue5.publicationId}/110_${familyIssue5.issueId}.jpg" />
								        <span  name="issueRead" >${familyIssue5.issueNum}</span>
								        <div class="bookBar">
									        <p>
									        	<em class='save' title='订阅' name="subscribe" ></em>
			                        			<em class='fav' title='收藏' name="collection" ></em>
									        </p>
								        </div>
							        </a>
						        </#if>
						    </#list>
						</div>
						         
						<div class="magezineBox magezineBoxLast">
						    <#list familyIssues as familyIssue6>
						        <#if (familyIssue6_index>=9) && (familyIssue6_index<12)>
							        <a issueId="${familyIssue6.issueId}" class="showBar m${familyIssue6_index-8}" href="javascript:void(0)">
								        <img name="issueRead"  src="${systemProp.magServerUrl}/${familyIssue6.publicationId}/110_${familyIssue6.issueId}.jpg" />
								        <span name="issueRead"  >${familyIssue6.issueNum}</span>
								        <div class="bookBar">
									        <p>
									        	<em class='save' title='订阅' name="subscribe" ></em>
			                        			<em class='fav' title='收藏' name="collection" ></em>
									        </p>
								        </div>
							        </a>
						        </#if>
						    </#list>
						</div>
					</div><!--  End-item  -->
					</#if>
					
					<#if (familyIssues?size>12)>
							<div class="item">
							    <div class="magezineBox">
								    <#list familyIssues as familyIssue7>
								        <#if (familyIssue7_index>=12) && (familyIssue7_index<15)>
									        <a issueId="${familyIssue7.issueId}" class="showBar m${familyIssue7_index-11}" href="javascript:void(0)">
										        <img  name="issueRead"   src="${systemProp.magServerUrl}/${familyIssue7.publicationId}/110_${familyIssue7.issueId}.jpg" />
										        <span  name="issueRead"  >${familyIssue7.issueNum}</span>
										        <div class="bookBar">
											        <p>
												        <em class='save' title='订阅' name="subscribe" ></em>
			                        			 		<em class='fav' title='收藏' name="collection" ></em>
										        	</p>
									        </div>
								        </a>
							        </#if>
							    </#list>
							</div>
							         
							<div class="magezineBox magezineBoxLast">
							    <#list familyIssues as familyIssue8>
							        <#if (familyIssue8_index>=15) && (familyIssue8_index<18)>
								        <a issueId="${familyIssue8.issueId}" class="showBar m${familyIssue8_index-14}" href="javascript:void(0)">
									        <img   name="issueRead"   src="${systemProp.magServerUrl}/${familyIssue8.publicationId}/110_${familyIssue8.issueId}.jpg" />
									        <span  name="issueRead"   >${familyIssue8.issueNum}</span>
									        <div class="bookBar">
										        <p>
											        <em class='save' title='订阅' name="subscribe" ></em>
			                        			 	<em class='fav' title='收藏' name="collection" ></em>
										        </p>
									        </div>
								        </a>
							        </#if>
							    </#list>
							</div>
						</div><!--  End-item  -->
					</#if>
					
					<a class="ctrl turnLeft png" href="javascript:void(0)">
						<span></span>
					</a>
		            <a class="ctrl turnRight png" href="javascript:void(0)">
		            	<span></span>
		            </a>	      
				</div><!--  End-smallShow  -->
						    
			</div>
		</#if>
	  </#list>
	 </#if>
 </#if>
</#macro>