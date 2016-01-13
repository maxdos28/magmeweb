<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/adSize.js"></script>
<div class="body" menu="size">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g480"><h2>Pad</h2></em>                    
                    <em><h2>Phone</h2></em>                    
              </div>
            </fieldset>
        </div>
        <div class="conB clearFix">
        	<div class="g480 floatl">
            <a id="addPadSizeBtn" class="btnAS mgb12" href="javascript:void(0);">新建</a>
            <table width="460" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>	
                    <td class="g120">名称</td>
                    <td>尺寸(px)</td>
                    <td class="g70">操作</td>
                  </tr>
                </thead>
                <tbody>  
                	<#if sizeList?exists>
                	<#list sizeList as sl>
                	<#if sl.device == 1>                         
                    <tr>
                        <td>${sl.sizeName?if_exists}</td>
                        <td>${sl.sizeValue?if_exists}</td>
                        <td><a name="delBtn" sizeId="${sl.id?if_exists}" class="del" href="#">删除</a></td>
                    </tr>
                    </#if>
                    </#list>
                    </#if>
                </tbody>
            </table>
            </div>
        	<div class="g460 floatl">
            <a id="addPhoneSizeBtn" class="btnAS mgb12" href="javascript:void(0);">新建</a>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>	
                    <td class="g120">名称</td>
                    <td>尺寸(px)</td>
                    <td class="g70">操作</td>
                  </tr>
                </thead>
                <tbody>                           
                    <#if sizeList?exists>
                	<#list sizeList as sl>
                	<#if sl.device == 2>                         
                    <tr>
                        <td>${sl.sizeName?if_exists}</td>
                        <td>${sl.sizeValue?if_exists}</td>
                        <td><a name="delBtn" sizeId="${sl.id?if_exists}" class="del" href="#">删除</a></td>
                    </tr>
                    </#if>
                    </#list>
                    </#if>
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>

<@editContent />
<#macro editContent>
<div class="popContent" id="pop003">
            <h6>新建广告尺寸</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input id="device" type="hidden">
	            <div>
	            	<em class="g60 tRight">名称</em>
	            	<em><input id="sizeName" type="text" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g60 tRight">宽</em>
	            	<em><input id="sizeValue_w" type="text" class="input g50"></em>
	            	<em class="g30 tRight">高</em>
	            	<em><input id="sizeValue_h" type="text" class="input g50"></em>
	            </div>
	        </fieldset>
	        </form>
            <div class="actionArea tCenter">
                <a id="saveSizeBtn" class="btnGS" href="javascript:void(0)">新建</a>
                <a id="cancelBtn" class="btnWS" href="javascript:void(0)">取消</a>
            </div>
</div>
</#macro>