<#import "../publish/pubCategory.ftl" as pubCategory>
<html> 
<head>
<title>看米</title>
</head>
<body>
	  <script type="text/javascript" src="${systemProp.staticServerUrl}/js/kanmi2.js"></script>
      <!--topBar-->
      <@pubCategory.main/>
      <!--issueList-->
      <!--magezineBox--> 
	  <div id="deskShow" class="deskShow">
	  <input type="hidden" id="begin" value="<#if begin??>${begin}<#else>0</#if>"/>
	  <input type="hidden" id="size" value="<#if (issueList?size)??>${(issueList?size)}</#if>"/>
		        <#if issueList??>
			          <#list  issueList as issue>
				           <#if (issue_index+1)%7==1 && (((issueList?size)/7)?int>=3) && (((issue_index+1)/7)?int==((issueList?size)/7)?int)>
				             <div class="item itemLast">
				           <#elseif (issue_index+1)%7==1 >
					         <div class="item">
					       </#if>
					            <a issueId="${issue.id}" class="showBar"  href="javascript:void(0)" style="display: inline;">
					              <img name="issueRead"  src="${systemProp.magServerUrl}/${issue.publicationId}/110_${issue.id}.jpg" />
					              <span name="issueRead" ><#if (issue.publicationName)??>${issue.publicationName}</#if>${issue.issueNumber}</span>
					              <div class="bookBar">
					              	<p>
						              <em class='save' title='订阅' name="subscribe" ><#if (issue.subscribeNum)?? && (issue.subscribeNum)!=0>${issue.subscribeNum}</#if></em>
			                          <em class='fav' title='收藏' name="collection" ><#if (issue.favoriteNum)?? && (issue.favoriteNum)!=0>${issue.favoriteNum}</#if></em>
						            </p>
					              </div>
					            </a>
					        <#if (issue_index+1)%7==0>
						         </div>
						       <#elseif (issue_index+1)==(issueList?size)>
						         </div>
					        </#if>
				      </#list>
		        </#if>
	 </div>
	 
	 <#if recommendIssues?? && (recommendIssues?size>0)>
		 <div class="deskShow deskOther">
		  <#list recommendIssues as issue>
			    <#if issue_index==0 || issue_index==6>
		          <div class="item <#if issue_index==6 || ((recommendIssues?size)<=6)> itemLast</#if>">
		        </#if>
		          <#if issue_index==0>
		             <a class="title" href="javascript:void(0)" style="display: inline;"><img src="${systemProp.staticServerUrl}/images/book145.gif" /><span></span></a>
		          </#if>
		          <a issueId="${issue.id}" class="showBar"  href="javascript:void(0)" style="display: inline;">
		              <img name="issueRead"  src="${systemProp.magServerUrl}/${issue.publicationId}/110_${issue.id}.jpg" />
		              <span name="issueRead" ><#if (issue.publicationName)??>${issue.publicationName}</#if>${issue.issueNumber}</span>
		              <div class="bookBar">
		              	<p>
			              <em class='save' title='订阅' name="subscribe" ><#if (issue.subscribeNum)?? && (issue.subscribeNum)!=0>${issue.subscribeNum}</#if></em>
                          <em class='fav' title='收藏' name="collection" ><#if (issue.favoriteNum)?? && (issue.favoriteNum)!=0>${issue.favoriteNum}</#if></em>
			            </p>
		              </div>
			        </a>
		        <#if issue_index==5 || issue_index==(recommendIssues?size)-1>
		          </div>
		        </#if>
	      </#list>
	    </div>
	  </#if>
</body>
</html>
 
 

