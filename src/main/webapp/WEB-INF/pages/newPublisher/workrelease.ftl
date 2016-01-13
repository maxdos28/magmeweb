 <script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/workpublish.js"></script>
<#import "../dialog/adminContentEdit.ftl" as pc>
    <div class="conLeftMiddleRight"  menu="editor" label="workpublish">
    	<div class="con37 conTools">
        	<a href="/new-publisher/work-publish.action" ishome="2" class="btnGB">未审核</a>
        	<a href="/new-publisher/work-publish!auditedPage.action" ishome="5" class="btnSB">已审核</a>
        	<a href="/new-publisher/work-publish!publishedPage.action" ishome="1" class="btnSB">已发布</a>
        	<a href="/new-publisher/work-publish!noEditorPage.action" ishome="7" class="btnSB">广场作品</a>
        </div>

        <div class="conB con39">
        	<fieldset class="new">
            	<div>
                	<em><input type="text" id="queryContentText" class="input g300"/></em>
                	<em><a class="btnWM" id="queryByTitleF" href="javascript:void(0);">搜索标题</a></em>
                	<em><a class="btnWM" id="queryByAuthorF" href="javascript:void(0);">搜索作者</a></em>
                </div>
            </fieldset>
        </div>
        
        <div class="conB con38 clearFix">
        	<h2>分类筛选</h2>
        	 <#if creativeCategoryList?? && (creativeCategoryList?size>0)>
                       <#list creativeCategoryList as creativeCategory>
		                    <dl>
		                        <#-- 一级分类 ,一级分类的颜色表示-->
		                    	<dt><a  href="javascript:void(0);" class="navC${creativeCategory.id}" parentid="${creativeCategory.id}">${creativeCategory.name}(${creativeCategory.creativeCount!''})</a></dt>
		                    	<#-- 二级级分类 -->
		                    	<#if (creativeCategory.childCreativeList)?? && ((creativeCategory.childCreativeList)?size>0)>
			                    	<#list creativeCategory.childCreativeList as cc>
				                    	<dd><a href="javascript:void(0);" parentId="${cc.parentId}" childCategoryId="${cc.id}" >${cc.name}(${cc.creativeCount!''})</a></dd>
			                    	</#list>
		                    	</#if>
		                    </dl>
	                    </#list>
                    </#if>
           
        </div>
        
        
        
        <div class="conB con40">
        	<fieldset>
            	<hr />
                <div>
                	<em><a id="workReleaseCheckId" class="btnAS" href="#">审核通过</a></em>
                </div>
            </fieldset>
        	
        </div>

        <div class="conB con41">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50"><label><input id="theadAllCheckBox" type="checkbox" />全选</label></td>
                    <td class="g80">作者</td>
                    <td class="g180 tLeft">标题</td>
                    <td class="g50">编辑</td>
                    <td class="g90">时间</td>
                    <td class="g120">分类</td>
                    <td class="g50">删除</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                </tbody>
            </table>
        </div>
        <div class="conFooter">
           <div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>
<@pc.main/>