<#macro main>
	<div id="editIssueDialog" class="popContent popRegister">
        <h6>编辑期刊信息</h6>
        	<fieldset>
        	<form method="post" id="editIssueForm" enctype="multipart/form-data" onsubmit="return false;">
            <div>
            	<em class="title">关键词<input type="hidden" name="id" value="" /></em>
            	<em><input type="text" name="keyword" class="input g220" <#if session_admin?exists>disabled="disabled"</#if> /></em>
            </div>
            <div>
            	<em class="title">发布日期</em>
            	<em><input type="text" id="publishDate" name="publishDate" class="input g220" <#if session_admin?exists>disabled="disabled"</#if> /></em>
            </div>
            <div>
            	<em class="title">期刊号</em>
            	<em><input type="text" name="issueNumber" class="input g220" <#if session_admin?exists>disabled="disabled"</#if> /></em>
            </div>
            
            <div>
            	<em class="title">描述</em>
            	<em><textarea rows="4" class="input g280" name="description" value="" <#if session_admin?exists>disabled="disabled"</#if>></textarea></em>
            </div>
            <#if session_admin?exists>
            <#else>
            <div>
            	<em class="title">文件</em>
            	<em><input type="file" id="issueFile" name="issueFile" /></em>
            </div>
            <div>
            	<em class="title">缩略图</em>
            	<em><input type="file" id="smallPic1" name="smallPic1" />(472*472)</em>
            </div>
            <div>
            	<em class="title">缩略图</em>
            	<em><input type="file" id="smallPic2" name="smallPic2" />(360*360)</em>
            </div>
           	<div>
            	<em class="title">是否加密</em>
            	<em><input type="radio" name="isPassWord" value="0" checked />否</em><em><input type="radio" name="isPassWord" value="1" />是</em><em><input class="input g40" maxLength="4" type="password" name="password" value="" ></input></em>
            </div>
            </#if>
            
            </form>
        </fieldset>
        <div class="actionArea tRight">
               <#if session_admin?exists>
			                    	<#else>
                <em><a href="javascript:void(0)" class="btnSM" id ="editIssueFormSubmit">确定</a></em>
                <em><a href="javascript:void(0)" class="btnAM" id="cancel" >取消</a></em>
                </#if>
            </div>
    </div>
</#macro>
<#macro netease>
<style> #updateNeteaseDialog .changePage a{margin:2px;}</style>
	<div id="updateNeteaseDialog" class="popContent popRegister" style="width:500px;">
        <h6>选择要更新的页码</h6>
        <input type="hidden" id="neteaseIssueId">
        <div id="pageDiv"  class="changePage" style="text-align:left;">
        </div>
        <div class="actionArea tRight">
                <em><a href="javascript:void(0)" class="btnSM" id ="updateNeteaseBtn">确定</a></em>
                <em><a href="javascript:void(0)" class="btnAM" id="cancel" >取消</a></em>
        </div>
    </div>
</#macro>