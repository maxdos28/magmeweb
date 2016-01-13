       <div id="feedBackDialog" class="popContent" >
			 <form id="feedbackForm" method="post" onsubmit="return false;">       			
             <fieldset class="new formFeedback">
             	<h6>意见反馈</h6>
                <p>欢迎提出宝贵建议和意见，您留下的每个字都将被用来改善我们的服务。</p>
                <div>
                    <em class="title">意见类别</em>
                    <em><select name="categoryId" class="g200" >
                        <option selected="selected" value="">请选择</option>
                        <#if feedBackCategoryList??>
                        <#list feedBackCategoryList as category>
                        	<option value="${(category.id)!"0"}">${(category.name)!""}</option>
                        </#list>
                        </#if>
                    </select></em>                    
                </div>
                <div>
                    <em class="title">意见内容</em>
                    <em><textarea name="content" class="input g570" /></textarea></em>
                </div>
                <div>
                    <em class="title">&nbsp;</em>
                    <em><a href="javascript:void(0)" id="feedbackFormSubmitBtn" class="btnBB" >提交</a></em>
                    <em><a href="javascript:void(0)" id="feedbackFormCancelBtn" class="btnWB" >取消</a></em>
                </div>                
            </fieldset>
            </form>
	    </div>
	    
	    