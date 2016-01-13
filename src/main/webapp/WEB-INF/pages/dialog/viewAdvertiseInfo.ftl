        <div id="viewAdDetail" class="popContent">
	    	<h6>广告详细信息</h6>
	    	<fieldset>
	        	<form id="updateAdvertiseForm" method="post">
	        	<input type="hidden" name="id" value="${(advertise.id)!""}" />
	            <div>
                	<em class="title">广告位编号</em>
                    <em>${(advertise.adPosition.id)!""}</em>
                </div>
	            <div>
                	<em class="title">广告位添加人</em>
                    <em>${(advertise.adPosition.userName)!""}</em>
                </div>
	            <div>
                	<em class="title">广告所属期刊</em>
                    <em>${(advertise.issueName)!""}</em>
                </div>
                <hr />
	            <div>
                	<em class="title">广告编号</em>
                    <em>${(advertise.id)!""}</em>
                </div>
	            <div>
                	<em class="title">广告内容添加人</em>
                    <em>${(advertise.userName)!""}</em>
                </div>
	            <div>
                	<em class="title">广告标题</em>
                    <em>
                    	<#if isModify??&&isModify==1>
                    	<input name="title" value="${(advertise.title)!""}" type="text" class="input g280" />
                    	<#else>
                    	${(advertise.title)!""}
                    	</#if>
                    </em>
                </div>
	            <div>
                	<em class="title">广告类型</em>
                    <em>${(advertise.adTypeMsg)!""}</em>
                </div>
	            <div>
	            	<#if (advertise.adType)??&&advertise.adType==1> 
                	<em class="title">广告链接</em>
                	<#elseif (advertise.adType)??&&advertise.adType==2>
                	<em class="title">图片链接</em>
                	<#elseif (advertise.adType)??&&advertise.adType==3>
                	<em class="title">视频链接</em>
                	</#if>
                    <em>
                    	<#if isModify??&&isModify==1>
                    	<input name="linkurl" value="${(advertise.linkurl)!""}" type="text" class="input g280" />
                    	<#else>
                    	${(advertise.linkurl)!""}
                    	</#if>
                    </em>
                </div>
                <div>
                    <em class="title">描述</em>
                    <em><textarea <#if isModify??&&isModify==1><#else>readonly="readonly"</#if>name="description" class="input g280">${(advertise.description)!""}</textarea></em>
                </div>
                <div>
                    <em class="title">广告状态</em>
                    <em>${(advertise.statusMsg)!""}</em>
                </div>
                <#if isMagme??&&isMagme==1>
                <div>
                    <em class="title">备注</em>
                    <em><textarea name="remark" class="input g280">${(advertise.remark)!""}</textarea></em>
                </div>
                </#if>                
	            </form>
	        </fieldset>
	        <div class="actionArea tRight">
                    <#if isModify??&&isModify==1>
					<a id="editAdvertiseFormSubmitBtn" href="javascript:void(0)" class="btnAM" >确定</a>
					</#if>                    
                    <a id="editAdvertiseFormCancelBtn" href="javascript:void(0)" class="btnWM">关闭</a>
	            </div>
	    </div>