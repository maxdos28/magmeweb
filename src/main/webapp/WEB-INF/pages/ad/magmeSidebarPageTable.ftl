<#macro main>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
		<thead>
			<tr>
				<td class="tLeft">广告描述</td>
				<td class="g80">类别</td>
				<td class="g100">发布时间</td>
				<td class="g100">结束时间</td>
				<td class="g50">位置</td>
				<td class="g60">详细</td>
				<td class="g60">操作</td>
			</tr>
		</thead>
		<tbody>
			<#if adSideList??>
				<#list adSideList as adSide>
					<tr adSideId="${(adSide.id)!""}">
						<td name="description" class="tLeft">${(adSide.description)!""}</td>
						<td name="categoryName">${(adSide.categoryName)!""}</td>
						<td name="validBeginTime">${(adSide.validBeginTime?string("yyyy-MM-dd"))!""}</td>
						<td name="validEndTime" >${(adSide.validEndTime?string("yyyy-MM-dd"))!""}</td>
						<td name="pos">${(adSide.pos)!""}</td>
						<td><a name="editAdSide" adSideId="${(adSide.id)!""}" class="btn" href="javascript:void(0)">查看</a></td>
						<td name="updateStatus">
						<#if adSide.status==2>
							<a name="updateStatusAdSide" adSideId="${(adSide.id)!""}" class="btn" status="3" href="javascript:void(0)">下架</a>
						<#else>
							<a name="updateStatusAdSide" adSideId="${(adSide.id)!""}" class="btn" status="2" href="javascript:void(0)">发布</a>
						</#if>                    	
						</td>
					</tr>
				</#list>
			</#if>
		</tbody>
	</table>
</#macro> 