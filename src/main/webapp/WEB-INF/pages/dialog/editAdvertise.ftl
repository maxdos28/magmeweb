<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/editAdvertise.js"></script>
<div id="viewAdDetail" class="popContent">
		<h6>广告详细信息</h6>
	    	<fieldset>
	        	<form method="post" id="editAdvertisForm">
	        	<#if advertise??>
	            <div>
                	<em class="title">广告编号</em>
                    <em>${(advertise.id)!''}<input type="hidden"  id="adName" value="${(advertise.id)!''}" class="input g280" /></em>
                </div>
	            <div>
                	<em class="title">广告标题</em>
                    <em><input type="text" id="adTitle" value="${(advertise.title)!''}" class="input g280" /></em>
                </div>
	            <div>
                	<em class="title">广告类型</em>
                    <em><#if advertise.adType??&&(advertise.adType==1|| advertise.adType==2 || advertise.adType==3)>
                    	互动广告<#elseif advertise.adType??&&(advertise.adType==5)>插页广告
                    	</#if>
                    	<input type="hidden" id="adType" value="${(advertise.adType)!''}" class="input g280" /></em>
                </div>
	            <div>
                	<em class="title">有效期</em>
                    <em><#if advertise.startTime??>${(advertise.startTime)?string("yyyy-MM-dd")}</#if></em>
                    <em>--</em>
                    <em><input type="text" id="adEndTime" name="adEndTime" value="<#if advertise.endTime??>${(advertise.endTime)?string("yyyy-MM-dd")}</#if>"  class="input g120" /></em>
                </div>
                <div>
                    <em class="title">描述</em>
                    <em><textarea class="input g280" id="adDescription" value="${(advertise.description)!''}" >${(advertise.description)!''}</textarea></em>
                </div>
                <div>
                    <em class="title">广告状态</em>
                    <em><#if advertise.status??&&(advertise.status==2 || advertise.status==5 || advertise.status==6 ||advertise.status==8)>
                    	上架
                    	<#elseif advertise.status??&&(advertise.status==1)>
     					待审核               	
                    	<#elseif advertise.status??&&(advertise.status==7)>
                    	下架
                    	</#if></em>
                </div>
                </#if>
	            </form>
	        </fieldset>
	         <div class="actionArea tRight">
                    <a href="javascript:void(0)" id="adSave" class="btnAM">保存</a>
                    <a href="javascript:void(0)" id="adClose" class="btnWM">关闭</a>
	            </div>
	    </div>
