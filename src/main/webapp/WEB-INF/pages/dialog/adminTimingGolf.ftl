<#macro main>
 <div id="adminTiming" class="popContent" style="width:400px;">
            <h6>定时发布</h6>
        	<div class="conBody">
                <fieldset>
                    <div>
                    	<em>选择日期</em>
                        <em><input id="adminTimingIdStr" type="hidden" value="" ><input type="text" id="sendTime" class="input g200" value="" /></em>
                    </div>
                    <div>
                    	<em>选择时段</em>
                        <em><select id="sendHour">
                        		<option value="">请选择</option>
                        		<#list 0..23 as i>
                        			<#if (i<10) >
                        				<option value="0${i}">0${i}</option>
                        			<#else>
                        				<option value="${i}">${i}</option>
                        			</#if>
                        		</#list>
                        	 </select>时</em>
                        	 <em><select id="sendMin">
                        		<#list 0..60 as i>
                        			<#if (i<10) >
                        				<option value="0${i}">0${i}</option>
                        			<#else>
                        				<option value="${i}">${i}</option>
                        			</#if>
                        		</#list>
                        	 </select>分</em>
                        	 <em> <select id="sendSec">
                        		<#list 0..60 as i>
                        			<#if (i<10) >
                        				<option value="0${i}">0${i}</option>
                        			<#else>
                        				<option value="${i}">${i}</option>
                        			</#if>
                        		</#list>
                        	 </select>秒</em>
                    </div>
                </fieldset>
       		</div>
            <div class="actionArea tRight">
                <a class="btnSM" id="workAuditedClose" href="javascript:void(0);">取消</a>&nbsp;&nbsp;<a id="workAuditedOk" class="btnAM" href="javascript:void(0);">确定</a>
            </div>
        </div>
</#macro>