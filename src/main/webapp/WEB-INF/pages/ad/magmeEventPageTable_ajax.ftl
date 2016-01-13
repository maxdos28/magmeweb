<script type="text/javascript">
	curPageNum = ${pageNum!"1"};
	pageSize = ${page.limit!"50"};
	pageCount = ${page.totalPage!"1"};
</script>
	
		<table class="table JQtableBg" width="100%">
			<thead>
				<tr>
					<td class="g50">事件ID</td>
					<td class="g50">期刊ID</td>
					<td class="g80">杂志名称</td>
					<td class="g50">缩略图</td>
					<td class="g40">权重</td>
					<td class="g100">标题</td>
					<td>描述</td>
					<td class="g40">状态</td>
					<td class="g40">推荐</td>
					<td class="g40">手机</td>
					<td class="g80">创建时间</td>
					<td class="g50">详细</td>
				</tr>
			</thead>
			<tbody>
			
				<#if fpageEventlist??>
					<#list fpageEventlist as pevent>
						<tr>
							<td>${(pevent.id)}</td>
							<td>${(pevent.issueId)}</td>
							<td>${(pevent.publicationName)!''}</td>
							<td>
		                    	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read!reader.action?pageId=${pevent.pageNo}&desType=2&id=${pevent.issueId}">
									<img src="${systemProp.fpageServerUrl}/event${(pevent.imgFile)}" width="50" />
								</a>
								<p><span><#if (pevent.width>200)>2<#else>1</#if></span>
								x
								<span><#if (pevent.height>200)>2<#else>1</#if></span></p></td>
							<td>${(pevent.weight)}</td>
							<td>${(pevent.title)}</td>
							<td>${(pevent.description)}</td>
							<td><#if (pevent.status==2)>待发布<#elseif (pevent.status==1)>生效<#else>无效</#if></td>
							<td><#if (pevent.isRecommend==1)>是<#else>否</#if></td>
							<td><#if (pevent.isSuitMobile==1)>是<#else>否</#if></td>
							<td>${(pevent.createdTime?string('yyyy-MM-dd'))}</td>
							<td><a name="editEvent" eventId="${(pevent.id)!""}" class="btn" href="javascript:void(0)">编辑</a></td>
						</tr>
					</#list>
				</#if>
			</tbody>
		</table>