<#macro main>
 <div id="adminContentEdit" class="popContent" style="width:500px;">
            <h6>作品编辑</h6>
        	<div class="conBody">
                <fieldset>
                <div class="clearFix select">
                   <#if creativeCategoryList?? && (creativeCategoryList?size>0)>
                       <#list creativeCategoryList as creativeCategory>
		                    <dl>
		                        <#-- 一级分类 ,一级分类的颜色表示-->
		                    	<dt><label class="navC1" parentid="${creativeCategory.id}">${creativeCategory.name}</label></dt>
		                    	<#-- 二级级分类 -->
		                    	<#if (creativeCategory.childCreativeList)?? && ((creativeCategory.childCreativeList)?size>0)>
			                    	<#list creativeCategory.childCreativeList as cc>
				                    	<dd><label><input type="checkbox" name="ccname" picCollection="0" value="${cc.id}" parentId="${cc.parentId}" childCategoryId="${cc.id}"/>${cc.name}</label></dd>
			                    	</#list>
			                    	
			                    	<#--前四个有图库标示-->
			                    	<#if (creativeCategory.id<5)>
			                    		<dd class="album"><label><input type="checkbox" name="picCollection"  parentId="${creativeCategory.id}"/>[图库]</label></dd>
			                    	</#if>
		                    	</#if>
		                    </dl>
	                    </#list>
                    </#if>
               	</div>
                <hr />
                <div class="floatl g320">
                    <div>
                        <em>标题</em>
                        <em><input type="hidden" id="popCreativeIdHidden" value="" /><input type="text" id="popContentTitleDig" maxlength="20" class="input g300" /></em>
                    </div>
                    <div>
                        <em>内容</em>
                        <em><textarea id="popContentDescribedDig"  class="input g300"></textarea></em>
                    </div>
                </div>
                </fieldset>
       		</div>
            <div class="actionArea tRight">
                <a class="btnSM" id="popPublishClose" href="javascript:void(0);">取消</a>&nbsp;&nbsp;<a id="popPublishOk" class="btnAM" href="javascript:void(0);">确定</a>
            </div>
        </div>
</#macro>