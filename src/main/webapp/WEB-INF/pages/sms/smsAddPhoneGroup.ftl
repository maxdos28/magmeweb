<#macro main>
	        <div id="addSmsGroup" class="popContent" id="addList">
	            <h6>添加通讯列表</h6>
	            <input id="groupId" type="hidden" class="input g240">
	            <form method="post" id="addSmsGroupForm" enctype="multipart/form-data" onsubmit="return false;">
		            <fieldset>
			            <div>
			            	<em class="g80 tRight">通讯列表名称</em>
			            	<em><input id="name" type="text" class="input g240"></em>
			            </div>
			            <div>
			            	<em class="g80 tRight">上传文件</em>
			            	<em><input id="uploadFile" type="file" name="uploadFile" class="inputFile g240"></em>
			            </div>
			        </fieldset>
		            <div class="actionArea tCenter">
		                <a id="enterSmsGroup"  class="btnBM" href="javascript:void(0)">确定</a>
		                <a id="cancelSmsGroup" class="btnWM" href="javascript:void(0)">取消</a>
		            </div>
	            </form>
	        </div>
</#macro>