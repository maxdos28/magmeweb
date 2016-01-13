 <script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/golf/workpublish.js"></script>
<#import "../dialog/adminContentEditGolf.ftl" as pc>
<div class="body" menu="news">
<div class="conLeftMiddleRight">
    	<div class="con37 conTools">
        	<a href="/golf/work-publish.action" ishome="2" class="btnGB">未审核</a>
        	<a href="/golf/work-publish!auditedPage.action" ishome="5" class="btnSB">已审核</a>
        </div>
        <div class="conB con38 clearFix">
        	<h2>分类筛选</h2>
        	 <#if creativeCategoryList?? && (creativeCategoryList?size>0)>
                       <#list creativeCategoryList as creativeCategory>
		                    <dl>
		                        <#-- 一级分类 ,一级分类的颜色表示-->
		                    	<dt><a  href="javascript:void(0);" class="navC1" parentid="${creativeCategory.id}">${creativeCategory.name}(${creativeCategory.creativeCount!''})</a></dt>
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
                    <td class="g100">作者</td>
                    <td class="g260 tLeft">标题</td>
                    <td class="g150">分类</td>
                    <td class="g60">操作</td>
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
</div>
<@pc.main/>