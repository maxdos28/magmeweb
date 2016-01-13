<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/workpublish.js"></script>
    <div class="conLeftMiddleRight"  menu="editor" label="workpublish">
    	<div class="con37 conTools">
        	<a href="/new-publisher/work-publish.action" ishome="2" class="btnSB">未审核</a>
        	<a href="/new-publisher/work-publish!auditedPage.action" ishome="5" class="btnSB">已审核</a>
        	<a href="/new-publisher/work-publish!publishedPage.action" ishome="1" class="btnSB">已发布</a>
        	<a href="/new-publisher/work-publish!noEditorPage.action" ishome="7" class="btnGB">广场作品</a>
        </div>

        <div class="conB con39">
        	<fieldset class="new">
            	<div>
                	<em><input type="text" id="queryContentText"  class="input g300"/></em>
                	<em><a class="btnWM" id="queryByIdF" href="javascript:void(0);">搜索作品id</a></em>
                </div>
            </fieldset>
        </div>

        <div class="conB con43">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g80">作品id</td>
                    <td class="g80">作者</td>
                    <td class="tLeft">标题</td>
                    <td class="g150">删除</td>
                  </tr>
                </thead>
                 <tbody id="tbodyContext"></tbody>
            </table>

        </div>
        
        <div class="conFooter">
           <div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>