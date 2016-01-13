<#macro main>
        <div class="popContent" id="editTagDialog">
            <fieldset>
	        	<h6>广告位内容编辑</h6>
	        	<form method="post" id="editAdPositionForm">
	        	<input type="hidden" name="id">
	            <div>
	            	<em class="title">名称</em>
	            	<em><input type="text" class="input g350" name="title"></em>
	            </div>
	            <div>
	            	<em class="title">品牌</em>
	            	<em><input type="text" class="input g350" name="keywords"></em>
	            </div>
	            <div>
	            	<em class="title">描述</em>
	            	<em><textarea class="input g350" name="description"></textarea></em>
	            </div>
	            <div>
	            	<em class="title"></em>
	            	<a id="editAdPositionFormDeleteBtn" href="javascript:void(0)" class="btnOS">删除</a>
	                <em ><a id="editAdPositionFormUpdateBtn" href="javascript:void(0)" class="btnBS" >修改</a></em>
	                <em ><a id="editAdPositionFormCancelBtn" href="javascript:void(0)" class="btnWS" >取消</a></em>
	            </div>
	            </form>
	        </fieldset>
        </div>
</#macro>        