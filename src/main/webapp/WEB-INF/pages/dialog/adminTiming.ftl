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
                        <em><select id="sendHour"><option value="">请选择</option><option value="6:45:00">6:45</option><option value="11:45:00">11:45</option><option value="16:30:00">16:30</option></select></em>
                    </div>
                </fieldset>
       		</div>
            <div class="actionArea tRight">
                <a class="btnSM" id="workAuditedClose" href="javascript:void(0);">取消</a>&nbsp;&nbsp;<a id="workAuditedOk" class="btnAM" href="javascript:void(0);">确定</a>
            </div>
        </div>
</#macro>