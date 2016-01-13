<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>

<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/ma/statMaAdHeadReport.js"></script>
<div class="body" menu="adTradeList">
      <div class="conLeftMiddleRight" style="min-height:600px;">
    	<div class="conB">
		<h2>广告信息</h2>
		<fieldset>
        	<div>
        		<input type="hidden" id="adHeadId" value="${adHeadId?if_exists}">
            	<em class="g80">广告名称</em><em class="g100"><#if adHeadInfo?exists>${adHeadInfo.adName}</#if></em>
            	<em class="g60">上线日期</em><em class="g200"><#if adHeadInfo?exists>${adHeadInfo.startDate?string("yyyy-MM-dd")}~${adHeadInfo.endDate?string("yyyy-MM-dd")}</#if></em>
            	<em class="g60">内容日期</em><em class="g200"><#if adHeadInfo?exists>${adHeadInfo.startDate?string("yyyy-MM-dd")}~${adHeadInfo.endDate?string("yyyy-MM-dd")}</#if></em>
            </div>
        	<hr />
        	<div>
            	<em class="g80">广告标签</em>
            	<em class="g600">
            	<#if adHeadKeyword?exists>
            		<#list adHeadKeyword as k>
            			${k.keyword},
            		</#list>
            	</#if>
            	</em>
            </div>
        	<hr />
        	<div>
            	<em class="g80">设备及尺寸</em>
            	<em class="g600">
            	<#if adHeadSize?exists>
            		PAD:
            		<#list adHeadSize as s>
            			<#if s.device==1>
            			${s.sizeValue},
            			</#if>
            		</#list>
            		&nbsp;&nbsp;&nbsp;&nbsp;
            		PHONE:
            		<#list adHeadSize as s>
            			<#if s.device==2>
            			${s.sizeValue},
            			</#if>
            		</#list>
            	</#if>
            	</em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    </div>
</div>
