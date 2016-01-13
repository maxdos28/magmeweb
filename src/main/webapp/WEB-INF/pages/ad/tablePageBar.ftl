<#macro manageAdMyAd>            
            	<h2>广告列表</h2>
                <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <thead>
                  <tr>
                    <td width="50">广告编号</td>
                    <td>名称</td>
                    <td width="60">类型</td>
                    <td width="60">状态</td>
                    <td width="100">创建时间</td>
                    <td width="60">预览</td>
                    <td width="80">详细信息</td>
                    <td width="80">广告数据</td>
                  </tr>
                  </thead>
                  <tbody>
                  <#list advertiseList as advertise>
                  <tr advertiseId="${(advertise.id)!""}">
                    <td>${(advertise.id)!""}</td>
                    <td name="title">${(advertise.title)!""}</td>
                    <td>${(advertise.adTypeMsg)!""}</td>
                    <td>${(advertise.statusMsg)!""}</td>
                    <td>${(advertise.createdTime?string("yyyy-MM-dd"))!""}</td>
                    <td><a href="${systemProp.appServerUrl}/publish/mag-read!adPreview.action?advertiseId=${(advertise.id)!""}" target="_blank">预览</a></td>
                    <td><a name="viewAdvertise" type="1" advertiseId="${(advertise.id)!""}" href="javascript:void(0)">查看</a></td>
                    <td><a href="${systemProp.appServerUrl}/ad/manage-data!search.action?advertiseId=${advertise.id!""}">数据</a></td>
                  </tr>
                  </#list>
                  </tbody>
                </table>
	            <!--分页代码-->
				${pageBar!""}               
</#macro>

<#macro manageAdMyMgzAd>            
            	<h2>广告列表</h2>
                <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <thead>
                  <tr>
                    <td width="50">广告编号</td>
                    <td>名称</td>
                    <td width="60">类型</td>
                    <td width="60">状态</td>
                    <td width="100">创建时间</td>
                    <td width="60">预览</td>
                    <td width="80">详细信息</td>
                    <td width="80">审核</td>
                  </tr>
                  </thead>
                  <tbody>
                  <#list advertiseList as advertise>
                  <tr advertiseId="${(advertise.id)!""}">
                    <td>${(advertise.id)!""}</td>
                    <td name="title">${(advertise.title)!""}</td>
                    <td>${(advertise.adTypeMsg)!""}</td>
                    <td name="status">${(advertise.statusMsg)!""}</td>
                    <td>${(advertise.createdTime?string("yyyy-MM-dd"))!""}</td>
                    <td><a href="${systemProp.appServerUrl}/publish/mag-read!adPreview.action?advertiseId=${(advertise.id)!""}" target="_blank">预览</a></td>
                    <td><a name="viewAdvertise" type="2" advertiseId="${(advertise.id)!""}" href="javascript:void(0)">查看</a></td>
                    <td name="updateStatus">
                    	<#if (advertise.status)==1>
                    	<a name="updateStatusAdvertise" advertiseId="${(advertise.id)!""}" status="3" href="javascript:void(0)">通过</a> | <a name="updateStatusAdvertise" advertiseId="${(advertise.id)!""}" status="4" href="javascript:void(0)">不通过</a>
                    	</#if>
                    </td>
                  </tr>
                  </#list>
                  </tbody>
                </table>
	            <!--分页代码-->
				${pageBar!""}                
</#macro>

<#macro manageAdPosition>  
			<table class="clear table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <td>期刊名称</td>
                <td width="90">广告位数量</td>
                <td width="90">广告位信息</td>
                <td width="90">创建广告位</td>
                <td width="90">广告数量</td>
                <td width="90">广告信息</td>
              </tr>
              </thead>
              <tbody>
              <#list issueList as issue>
              <tr>
                <td>${(issue.description)!""}</td>
                <td>${(issue.positionNum)!"0"}</td>
                <td><a name="viewAdPosition" issueId="${issue.id}" href="javascript:void(0)">点击查看</a></td>
                <td><a href="${systemProp.appServerUrl}/publish/mag-read!pubReader.action?id=${issue.id}">添加</a></td>
                <td>${(issue.advertiseNum)!"0"}</td>
                <td>
                	<#if session_admagme??>
                	<a href="${systemProp.appServerUrl}/ad/manage-magme-ad.action?issueId=${issue.id}">
                		点击查看
                	</a>
                	<#elseif session_publisher??>
                	<a href="${systemProp.appServerUrl}/ad/manage-my-mgz-ad.action?issueId=${issue.id}">
                		点击查看
                	</a>
                	</#if>                	
                </td>
              </tr>
              </#list>
              </tbody>
            </table>
            <!--分页代码-->
			${pageBar!""}  
</#macro>

<#macro manageAdMagmeAd>
            	<h2>广告列表</h2>
                <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <thead>
                  <tr>
                    <td width="50">广告编号</td>
                    <td width="100">广告名称</td>
                    <td width="60">类型</td>
                    <td width="80">状态</td>
                    <td width="80">发布时间</td>
                    <td width="40">预览</td>
                    <td width="55">详细信息</td>
                    <td width="30">数据</td>
                    <td width="80">操作</td>
                    <td>备注</td>
                  </tr>
                  </thead>
                  <tbody>
                  <#if advertiseList??>
                  <#list advertiseList as advertise>
                  <tr advertiseId="${(advertise.id)!""}">
                    <td>${(advertise.id)!""}</td>
                    <td name="title">${(advertise.title)!""}</td>
                    <td>${(advertise.adTypeMsg)!""}</td>
                    <td name="status">${(advertise.statusMsg)!""}</td>
                    <td>${(advertise.createdTime?string("yyyy-MM-dd"))!""}</td>
                    <td><a href="${systemProp.appServerUrl}/publish/mag-read!adPreview.action?advertiseId=${(advertise.id)!""}" target="_blank">预览</a></td>
                    <td><a name="viewAdvertise" type="3" advertiseId="${(advertise.id)!""}" href="javascript:void(0)">查看</a></td>
                    <td><a href="${systemProp.appServerUrl}/ad/manage-data!search.action?advertiseId=${advertise.id!""}">数据</a></td>
                    <td name="updateStatus">
                    	<#if advertise.status==2||advertise.status==3>
                    	<a name="updateStatusAdvertise" advertiseId="${(advertise.id)!""}" status="5" href="javascript:void(0)">发布</a> | <a name="updateStatusAdvertise" advertiseId="${(advertise.id)!""}" status="6" href="javascript:void(0)">不发布</a>
                    	<#elseif advertise.status==7>
                    	<a name="updateStatusAdvertise" advertiseId="${(advertise.id)!""}" status="5" href="javascript:void(0)">发布</a>
                    	<#elseif advertise.status==5>
                    	<a name="updateStatusAdvertise" advertiseId="${(advertise.id)!""}" status="7" href="javascript:void(0)">下架</a>
                    	</#if>
                    </td>
                    <td name="remark">${(advertise.remark)!""}</td>
                  </tr>
                  </#list>
                  </#if>
                  </tbody>
                </table>
                
                ${pageBar!""}
</#macro> 

<#macro manageAdPositionDetail>
<table class="clear table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
  <tr>
    <td width="70">广告位编号</td>
    <td width="90">创建时间</td>
    <td width="90">广告位内容</td>
    <td width="90">广告位品牌</td>
    <td>广告位描述</td>
    <td width="120">创建者</td>
    <td width="30">预览</td>
    <td width="50">编辑</td>
  </tr>
  </thead>
  <tbody>
  <#list adPositionList as adPosition>
  <tr>
    <td>${(adPosition.id)!""}</td>
    <td>${(adPosition.createdTime?string("yyyy-MM-dd"))!""}</td>
    <td>${(adPosition.title)!""}</td>
    <td>${(adPosition.keywords)!""}</td>
    <td>${(adPosition.description)!""}</td>
    <td width="120">${(adPosition.userName)!""}</td>
    <td width="50"><a id="detailpreview" detailpreview="${(adPosition.pageNo)!""}" issue="${(adPosition.issueId)!""}" adid="${(adPosition.id)!""}"  href="javascript:void(0)">预览</a></td>
    <td width="50"><#if (adPosition.isModify)??&&(adPosition.isModify==1)><a name="editAdPosition" href="javascript:void(0)">编辑</a></#if></td>
  </tr>
  </#list>
  </tbody>
</table>
</#macro>

<#macro manageAdSide>
                <h2><a name="editAdSide" href="javascript:void(0)" class="floatr btnBS">添加广告</a>广告列表</h2>
                <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <thead>
                  <tr>
                    <td width="50">广告编号</td>
                    <td>广告描述</td>
                    <td width="60">所属类别</td>
                    <td width="80">发布时间</td>
                    <td width="80">到期时间</td>
                    <td width="60">所在位置</td>
                    <td width="50">详细</td>
                    <td width="50">数据</td>
                    <td width="50">操作</td>
                  </tr>
                  </thead>
                  <tbody>
                  <#if adSideList??>
                  <#list adSideList as adSide>
                  <tr adSideId="${(adSide.id)!""}">
                    <td>${(adSide.id)!""}</td>
                    <td name="description">${(adSide.description)!""}</td>
                    <td name="categoryName">${(adSide.categoryName)!""}</td>
                    <td name="validBeginTime">${(adSide.validBeginTime?string("yyyy-MM-dd"))!""}</td>
                    <td name="validEndTime" >${(adSide.validEndTime?string("yyyy-MM-dd"))!""}</td>
                    <td name="pos">${(adSide.pos)!""}</td>
                    <td><a name="editAdSide" adSideId="${(adSide.id)!""}" href="javascript:void(0)">查看</a></td>
                    <td><a href="${systemProp.appServerUrl}/ad/manage-data!search.action?advertiseId=${adSide.id}&type=4">查看</a></td>
                    <td name="updateStatus">
                    	<#if adSide.status==1||adSide.status==3>
                    	<a name="updateStatusAdSide" adSideId="${(adSide.id)!""}" status="2" href="javascript:void(0)">发布</a>
                    	<#elseif adSide.status==2>
                    	<a name="updateStatusAdSide" adSideId="${(adSide.id)!""}" status="3" href="javascript:void(0)">下架</a>
                    	</#if>                    	
                    </td>
                  </tr>
                  </#list>
                  </#if>
                  </tbody>
                </table>
                ${pageBar!""}
</#macro>                                       