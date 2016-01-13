<#macro main>
   <div id="adminCon1" class="popContent">
        	<h6>编辑内容</h6>
        	<fieldset>
        	<form method="post" onsubmit="return false;" id="editReleaseAuditForm">
        		<input type="hidden" name="cid" value="">
	            <div class="floatl g220">
                	<em><img name="imagePath" width="200" height="200" src="" /></em>
                </div>
	            <div>
                	<em>专题</em>
                	 <em id="edit_pagedid" class="smartSearch">
		                   	<input type="text" class="input g150" name="pagedid" />
		                    <div class="list" style="width:150px"><div class="inner g150 jspContainer"></div></div>
		             </em>
		             <!--
		             <em>权重</em><em><input type="text" class="input g150" name="weight" /></em>-->
                </div>
                <!--
	            <div class="clear" id="tagContent" >
                	
                </div>
	            <div>
                    <em><textarea name="content" class="input g590"></textarea></em>
                </div>-->
               </form>
            </fieldset>
            <div class="actionArea tRight">
                    <a class="btnSM" id="eseBtn" href="javascript:void(0);">取消</a>
                    <a class="btnAM" id="okBtn" href="javascript:void(0);">确定</a>
                </div>
        </div>
</#macro>