<#import "../publish/publisherProfile.ftl" as publisherProfile>
<#import "../publish/publisherLeftMenu.ftl" as publisherLeftMenu>
<#import "../dialog/addAdvertise.ftl" as addAdvertise>

    <!--sideMiddleRight-->
    <div id="sideMiddleRight" class="sideMiddleRight">
       <div class="conB conAdvertisement">
		<h2>广告管理</h2>
		<div class="conBody">
	        <!--topTools-->
	        <div class="topBar clearFix">
	            <div class="floatr">
	                <a id="createAdvertise" href="javascript:void(0)" class="btnOS">新建</a>
	            </div>
	            <div class="floatl">
	                <strong><a publicationId="${publicationId}" href="javascript:void(0)">全部</a></strong>
	                <#if issueList??>
	                    <ul>
	                      <#list issueList as issue>
	                    	 <li><a <#if issueId?? && issueId==issue.id> class="current" </#if>pubId="${issue.publicationId}" issueId="${issue.id}" href="javascript:void(0)">${issue.issueNumber}</a></li>
	                      </#list>
	                    </ul>
	                </#if>
	             </div>
	        </div>
	
	        <div class="adList">
	         <#if adPosList??>
	              <#list adPosList as adPos>
	                <div class="item clearFix">
	                	<input type="checkbox" value="${adPos.id}" name="adposCheck" <#if  advertiseMap[""+adPos.id]??> disabled="disabled" </#if> />
	                    <div class="floatr">
	                      <#if advertiseMap[""+adPos.id]??><#--有广告才能编辑-->
	                        <a name="adposDel${adPos.id}"  editpos="${advertiseMap[""+adPos.id].id}" href="javascript:void(0)" class="btnBS" >编辑</a>
	                        <a href="javascript:void(0)" preissueId="${adPos.issueId}" pageId="${adPos.pageNo}" adposId="${adPos.id}" adId="${advertiseMap[""+adPos.id].id}" class="btnWS" >预览</a>
	                        <a name="adposDel${adPos.id}" delad="${advertiseMap[""+adPos.id].id}" deladposid="${adPos.id}" href="javascript:void(0)" class="btnOS" >删除</a>
	                        <#if adposmappingMap[""+adPos.id].status==2>
	                            <a mappingId="${adposmappingMap[""+adPos.id].id}" mappingadpos="adposDel${adPos.id}" href="javascript:void(0)" class="btnBS" >审核通过</a>
	                        </#if>
	                     </#if>
	                    </div>
	                    <a href="javascript:void(0)" class="link">
	                    	<div>
	                            <div class="pic"><img src="${systemProp.magServerUrl}/${publicationId}/${adPos.issueId}/${adPos.pageNo}.jpg" /></div>
	                            <strong><#if adPos.issueNumber??> ${adPos.issueNumber}</#if></strong>
	                            <span><#if adPos.keywords??> ${adPos.keywords} </#if></span>
	                        </div>
	                        <em name="adposDel${adPos.id}" <#if advertiseMap[""+adPos.id]?? && (advertiseMap[""+adPos.id].title)??> changeval="changeval${advertiseMap[""+adPos.id].id}" </#if> ><#if advertiseMap[""+adPos.id]?? && (advertiseMap[""+adPos.id].title)??>${advertiseMap[""+adPos.id].title}</#if> </em>
	                        <p name="adposDel${adPos.id}"  <#if advertiseMap[""+adPos.id]?? && (advertiseMap[""+adPos.id].title)??> changeval="changeval${advertiseMap[""+adPos.id].id}" </#if> ><#if advertiseMap[""+adPos.id]?? && (advertiseMap[""+adPos.id].description)??>${advertiseMap[""+adPos.id].description}</#if></p>
	                    </a>
	                </div>
	              </#list>
	          </#if>
	        </div>
	    </div>
	  </div>
	  <@addAdvertise.main/>
    </div>
    

