        <div id="addAd" class="popContent">
	    	<h6><#if adSide??>修改<#else>添加</#if>广告</h6>
	    	<fieldset>
	        	<form id="editAdSideForm" method="post">
	        	<input type="hidden" name="id" value="${(adSide.id)!""}" />
	            <div>
                	<em class="title">所属类别</em>
                    <em>
                    	<#if adSide??>
                    	<input class="g250" disabled="disabled" value="${adSide.categoryName}"/>
                    	<input name="categoryId" disabled="disabled" value="${adSide.categoryId}" type="hidden"/>
                        <#else><select class="g250" name="categoryId">
							<option value="">请选择类别</option>
                           <#if categoryList??>
                                <#list categoryList as category>
                                    <option value="${category.id}" <#if adSide??&&adSide.categoryId==category.id >selected="true"</#if>>${category.name}</option>
                                </#list>
                            </#if>
                        </select>   
                        </#if>                 
                    </em>
                </div>
	            <div>
                	<em class="title">位置</em>
                    <em>
                    	<#if adSide??><input class="g250" name="pos" disabled="disabled" value="${adSide.pos}"/>
                    	<#else><select class="g250" name="pos">
                    		<option value="">请选择位置</option>
                    		<#list 1..10 as pos>
                    		<option value="${pos}" <#if adSide??&&adSide.pos==pos >selected="true"</#if>>${pos}</option>
                    		</#list>
                    	</select>
                    	</#if>
                    </em>
                </div>
	            <div>
                	<em class="title">上传图片</em>
                    <em><input type="file" id="imageFile" name="imageFile" class="g250" /></em>
                </div>
	            <div>
                	<em class="title">发布时间</em>
                    <em><input type="text" id="sideAdValidBeginTime" readOnly="true" name="validBeginTime" value="${(adSide.validBeginTime?string("yyyy-MM-dd"))!""}" class="input g250" /></em>
                </div>
	            <div>
                	<em class="title">到期时间</em>
                    <em><input type="text" id="sideAdValidEndTime" readOnly="true" name="validEndTime" value="${(adSide.validEndTime?string("yyyy-MM-dd"))!""}" class="input g250" /></em>
                </div>
	            <div>
                	<em class="title">链接</em>
                    <em><input type="text" name="link" value="${(adSide.link)!""}" class="input g250" /></em>
                </div>
	            <div>
                	<em class="title">描述</em>
                    <em><textarea name="description" class="input g250">${(adSide.description)!""}</textarea></em>
                </div>
	            </form>
	        </fieldset>
	         <div class="actionArea tRight">
                    <a id="editAdSideFormSubmitBtn" href="javascript:void(0)" class="btnSM" >保存</a>
                    <a id="editAdSideFormCancelBtn" href="javascript:void(0)" class="btnAM">关闭</a>
	            </div>
	    </div>