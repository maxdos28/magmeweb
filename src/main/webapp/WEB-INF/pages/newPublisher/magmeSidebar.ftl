 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	var curPageNum = ${pageNum!"1"};
	var pageSize = ${pageSize!"10"};
	var pageCount = ${pageBar.totalPageCount!"0"};
</script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/magmeSidebar.js"></script>
 
<#import "../ad/magmeSidebarPageTable.ftl" as pt>

 <div class="conLeftMiddleRight" menu="editor" label="adSide">
    	<div class="con21 conTools">
        	<fieldset>
            		<form id="searchForm" method="post">
            	<div>
                    <em class="floatr"><a id="manageAdminEmail" class="btnBS" href="#">邮件设置</a></em>
                	<em>结束时间范围</em>
                	<em><input id="sideAdBeginTime" name="beginTime" type="text" class="input g100" /></em>
                	<em>至</em>
                	<em class="g150"><input id="sideAdEndTime" name="endTime" type="text" class="input g100" /></em>
                	<em>原始分类</em>
                	<em>
                		<select name="categoryId">
							<option value="">全部类别</option>
                           <#if categoryList??>
                                <#list categoryList as category>
                                    <option value="${category.id}" <#if categoryId??&&categoryId==category.id >selected="true"</#if>>${category.name}</option>
                                </#list>
                            </#if>
                        </select>
                	</em>
                    <em><a id="submitSearchFormBtn" class="btnBS" href="#">搜索</a></em>
                    <em><a id="addSidebar" class="btnWS" href="#">添加侧栏广告</a></em>
                </div>
                </form>
            </fieldset>
        </div>
        <div id="tablePageBarContainer" class="conB con22">
            <@pt.main />
        </div>

       <div class="conFooter">
        	<div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>
    
