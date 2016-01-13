        <!--conMiddleRight-->
        	<#if issue??>
        	<div class="left">
            	<img src="${systemProp.magServerUrl+'/'+issue.publicationId+'/'+issue.id+'/768_'+pageNo+'.jpg'}" />
            </div>
            <div class="right">
            	<form id="textPageForm">
            	<input type="hidden" name="issueId" value="${issueId}" />
            	<input type="hidden" name="pageNo" value="${pageNo}" />
            	<textarea name="content" class="input">${(textPage.content)!""}</textarea>
                <a name="textPageFormSaveBtn" class="btnBS" href="javascript:void(0)">保存</a>
                <a name="textPageFormSaveBtn" totalPages="${issue.totalPages}" class="btnOS" href="javascript:void(0)">保存并跳转至下一页</a>
                </form>
                <!--分页代码-->
                <div class="changePage">
                	<#list 0..issue.totalPages as i>
                		<a href="javascript:void(0);" textPageIssueId="${issue.id}" textPagePageNo="${i}" <#if i==pageNo >class="current"</#if>>${i}</a>
                	</#list>
                </div>
            </div>
            </#if>