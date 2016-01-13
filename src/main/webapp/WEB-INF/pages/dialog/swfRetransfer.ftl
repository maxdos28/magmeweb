<#macro main>
	<div id="swfRetransfer" class="popContent" >
    	<fieldset>
        	<h6>新建广告</h6>
             <form id="swfRetransferForm" method="post">
	            <div>
	            	<em class="title">需要转换的页码</em>
	            	<em><input type="text" id="pageNos" name="pageNos" class="input g150" /></em>
	            </div>
	            <div>
                    <input type="hidden" id="mgzProcessId" name="mgzProcessId" value=""/>
                    <em class="title"></em>
	                <em ><a id="swfRetransferSubmit" href="javascript:void(0)" class="btnBS" >确定</a></em>
	                <em ><a id="swfRetransfercancel" href="javascript:void(0)" class="btnWS" >取消</a></em>
	                <em id ="tipError" class="tipsError">请填写相关信息</em>
	            </div>
	            
           </form>
        </fieldset>
    </div>
</#macro>