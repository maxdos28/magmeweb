
        <div id="adminEventEdit" class="popContent">
        <h6>事件编辑</h6>
		        	<fieldset>
	        	<form id="editEventForm" method="post">
		            	
		                <div class="g340 floatl">
		                    <div><em class="g60 tRight">事件ID</em><em><input id="eventId" name="eventId" type="text" class="input g180 disabled" disabled="disabled" value="${fpageEvent.id}" /></em></div>
		                    <div><em class="g60 tRight">权重</em><em><input name="weight" type="text" class="input g30" value="${fpageEvent.weight}"/></em></div>
		                    <div>
		                    	<em class="g60 tRight">缩略图</em>
		                    	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${fpageEvent.id}">
		                    	<em class="pic"><img class="size11 clearFix" src="${systemProp.fpageServerUrl}/event${fpageEvent.imgFile}"/></em>
		                    	</a>
		                    </div>
		                    <div>
		                    	<em class="g60 tRight">尺寸</em>
		                    	<em><input type="text" class="input g30 disabled" disabled  value="${fpageEvent.width}" /></em>
		                    	<em>&nbsp;x&nbsp;</em>
		                    	<em><input type="text" class="input g30 disabled" disabled value="${fpageEvent.height}" /></em>
                				<em><input id="imgFile" name="imgFile" type="file" class="g200" /></em>
		                    </div>
						</div>
		                <div class="g360 floatl">
		                    <div><em class="g60 tRight">标题</em><em><input name="title" type="text" class="input g270" value="${fpageEvent.title}" /></em></div>
		                    <div><em class="g60 tRight">描述</em><em><textarea name="description" class="input g270"> ${fpageEvent.description}</textarea></em></div>
		                    <div>
		                    	<em class="g60 tRight">状态</em>
		                    	<em class="g80">
		                    		<select name="status">
		                    			<option value="2" <#if fpageEvent.status==2>selected="selected"</#if>>待发布</option>
		                    			<option value="1" <#if fpageEvent.status==1>selected="selected"</#if>>生效</option>
		                    			<option value="0" <#if fpageEvent.status==0>selected="selected"</#if>>无效</option>
		                    		</select>
		                    	</em>
		                    	<em>推荐</em>
			                    <em class="g80">
			                    	<select name="isRecommend">
			                    		<option value="0" <#if fpageEvent.isRecommend==0>selected="selected"</#if>>否</option>
			                    		<option value="1" <#if fpageEvent.isRecommend==1>selected="selected"</#if>>是</option>
			                    	</select>
			                    </em>
		                    	<em>手机</em>
		                    	<em>
		                    		<select name="isSuitMobile">
		                    			<option value="0" <#if fpageEvent.isSuitMobile==0>selected="selected"</#if>>否</option>
		                    			<option value="1" <#if fpageEvent.isSuitMobile==1>selected="selected"</#if>>是</option>
	                    			</select>
	                    		</em>
		                    </div>
		                    <div>
		                    	<em class="g60 tRight">创建时间</em>
		                    	<em><input type="text" class="input g100 disabled" disabled="disabled" value="${(fpageEvent.createdTime?string('yyyy-MM-dd'))}" /></em>
		                    	<em class="g60 tRight">出版商级别</em>
		                    	<em><input type="text" class="input g100 disabled" disabled="disabled" value="${fpageEvent.reserved1}" /></em>
		                    </div>
		                    <div>
		                    	<em class="g60 tRight">杂志ID</em>
		                    	<em><input type="text" class="input g100 disabled" disabled="disabled" value="${fpageEvent.publicationId}" /></em>
		                    	<em class="g60 tRight">期刊ID</em>
		                    	<em><input type="text" class="input g100 disabled" disabled="disabled" value="${fpageEvent.issueId}" /></em>
		                    </div>
		                    <div>
		                    	<em class="g60 tRight">开始页码</em>
		                    	<em><input name="pageNo" type="text" class="input g100" value="${fpageEvent.pageNo}" /></em>
		                    	<em class="g60 tRight">结束页码</em>
		                    	<em><input name="endPageNo" type="text" class="input g100" value="${fpageEvent.endPageNo}" /></em>
		                    </div>
		                    <div>
			                    <em class="g60 tRight">事件类型</em>
			                    <em><input type="text" class="input g100 disabled" disabled="disabled" value="<#if fpageEvent?? && fpageEvent.adId??>广告<#else>普通</#if>"/></em>
		                    </div>
		                    <div>
			                    <em class="g60 tRight">制作人</em>
			                    <em><input type="text" class="input g100 disabled" disabled="disabled" value="<#if adminPo?? && adminPo.userName??>${adminPo.userName}</#if>"/></em>
			                    <em class="g60 tRight">&nbsp;</em>
			                    <em><input type="checkbox" value='1'  name="sidebar" <#if fpageEvent.sidebar==1>checked="checked"</#if> class="input" ><b style="color:red;">频道侧栏</b></em>
		                    </div>
		                    <#if fpageEvent?? && fpageEvent.advertise?? >
			                    <div>
			                    	<em class="g60 tRight">广告状态</em>
			                    	<em><input type="text" class="input g100 disabled"  disabled="disabled" 
			                    		value="<#if fpageEvent.advertise.status==1>待审核
		                    				<#elseif fpageEvent.advertise.status==0>无效
		                    				<#elseif fpageEvent.advertise.status==7>下架
		                    				<#elseif fpageEvent.advertise.status==3 || fpageEvent.advertise.status==4>
		                    				<#elseif fpageEvent.advertise.status==2 || fpageEvent.advertise.status==5
		                    					||fpageEvent.advertise.status==6 || fpageEvent.advertise.status==8>上架
		                    				</#if>" /></em>
			                    </div>
		                    </#if>
		                </div>
		                <hr class="clear" />
		                <div class="tagList" style="width:700px;">
		                    <ul id="tagListLi" class="clearFix">
		                        <li class="add"><input type="text" tips="添加新标签" id="newTag" /><a href="javascript:void(0)" id="addTagButton"></a></li>
                				<#if sortList?? && sortList?size gt 0>
                					<#list sortList as sort>
										<li class="<#if sort.isDefault==1>current</#if>"><a href="javascript:void(0);">${sort.name}</a></li>
                					</#list>
                				</#if>
                				<#if fpageEvent.tagList?? >
                					<#list fpageEvent.tagList as tag>
										<li class="current"><a href="javascript:void(0);">${tag.name}</a></li>
                					</#list>
                				</#if>
		                    </ul>
		                    
		                    <!--
		                    <div>
		                    	<em class="g60 tRight">标签</em>
		                    	<em class="g270 tRight">
		                    		<div class="tagList">
										<ul id="tagListLi" class="clearFix">
		                    				<#if sortList?? && sortList?size gt 0>
		                    					<#list sortList as sort>
													<li><a class="<#if sort.isDefault==0>un</#if>selected" href="javascript:void(0);">${sort.name}</a></li>
		                    					</#list>
		                    				</#if>
		                    				<#if fpageEvent.tagList?? >
		                    					<#list fpageEvent.tagList as tag>
													<li><a class="selected" href="javascript:void(0);">${tag.name}</a></li>
		                    					</#list>
		                    				</#if>
											<li class="add"><input type="text" tips="添加新标签" id="newTag"><a href="javascript:void(0)" id="addTagButton"></a></li>
										</ul>
									</div>
		                    	</em>
		                    </div>
		                    -->
		                    
		                </div>
		                <hr class="clear" />
		                
            </form>
		            </fieldset>
		          <div class="actionArea tRight"><a id="editSubmitBtn" href="javascript:void(0)" class="btnAB" >保存</a></div>
	    </div>