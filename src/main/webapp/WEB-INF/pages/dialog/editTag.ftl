<#macro main>
	<div class="popContent" id="editTagDialog">
        <fieldset>
        	<h6>编辑标签</h6>
        	<form method="post" id="editTagForm">
        	<input type="hidden" name="id">
            <div>
            	<em class="title">标题</em>
            	<em><input type="text" class="input g350" name="title"></em>
            </div>
            <div>
            	<em class="title">关键字</em>
            	<em><input type="text" class="input g350" name="keyword"></em>
            </div>
            <div>
            	<em class="title">描述</em>
            	<em><textarea rows="4" class="input g350" name="description"></textarea></em>
            </div>
            <div>
            	<em class="title"></em>
            	<a id="editTagFormDeleteBtn" href="javascript:void(0)" class="btnOS">删除</a>
                <em ><a id="editTagFormSubmitBtn" href="javascript:void(0)" class="btnBS" >修改</a></em>
                <em ><a id="editTagFormCancelBtn" href="javascript:void(0)" class="btnWS" >取消</a></em>
                <em id ="tipError" class="tipsError">请填写相关信息</em>
            </div>
            </form>
        </fieldset>
    </div>
	
</#macro>