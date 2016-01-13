<script src="${systemProp.staticServerUrl}/v3/dv/golf/newsType.js"></script>
<style>
.conSelectClass dl{ width:140px; margin-right:10px;}
.conSelectClass dl dt,
.conSelectClass dl dd{ height:30px; line-height:30px; margin:2px 0;}
.conSelectClass dl dd label{ display:block; width:100%;}

.conSelectClass dl dt{ background:#D7DCDF; color:#292F3C; text-indent:10px;}
.conSelectClass dl dd{ background:#f5f5f5;}
</style>
<div class="body" menu="type" >
    <div class="conLeftMiddleRight">
    	<div class="conTools">
        	<fieldset>
                <div class="clearFix">
                    <div>
                        <em>一级分类</em>
                        <em><input class="input g200" maxlength="20" name="firstTypeName" type="text"></em>
                        <em><a id="firstSubmitBtn" class="btnWS" href="javascript:void(0);">提交</a></em>
                    </div>
                    <hr />
                    <div>
                        <em>二级分类</em>
                        <em><select name="newsTypeSelect">
                        		<#if creativeCategoryList?? && (creativeCategoryList?size>0)>
                       				<#list creativeCategoryList as creativeCategory>
                       					<option value="${creativeCategory.id}">${creativeCategory.name}</option>
                       				</#list>
                       			</#if>
                        	</select>
                        </em>
                        <em><input class="input g130"  name="secondTypeName" maxlength="20" type="text"></em>
                        <em><a id="secondtSubmitBtn" class="btnWS" href="javascript:void(0);">提交</a></em>
                    </div>
                    <hr />
                    <div>
                    	<em><a id="delNewsTypeBtn" class="btnWS " href="javascript:void(0);">删除</a></em>
                    	<em><a id="clearNewsTypeRadioBtn" class="btnWS " href="javascript:void(0);">清空</a></em>
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="conB conSelectClass">
           	<div class="clearFix">
                    <em class="title floatl">选择分类</em>
                    <#if creativeCategoryList?? && (creativeCategoryList?size>0)>
                       <#list creativeCategoryList as creativeCategory>
			                <dl class="<#if creativeCategory_index==0>clear</#if> floatl">
		                    	<dt><label><input type="radio" nt="1" name="golfType" value="${creativeCategory.id}" valText="${creativeCategory.name}" />${creativeCategory.name}</label></dt>
		                    	<#if creativeCategory.childCreativeList?? && (creativeCategory.childCreativeList?size>0) >
		                    		<#list creativeCategory.childCreativeList as childList>
		                    			<dd><label><input type="radio" nt="2" name="golfType" fvalue="${creativeCategory.id}" value="${childList.id}" valText="${childList.name}" />${childList.name}</label></dd>
		                    		</#list>
		                    	</#if>
	                   		</dl>
	                    </#list>
                    </#if>
            </div>
        </div>
        <div class="conFooter">
		</div>
    </div>
</div>
