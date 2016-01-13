<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/workpublishAdmin.js"></script>
    <div class="conLeftMiddleRight"  menu="editor" label="workpublish">
    	<div class="con37 conTools">
        	<a href="/new-publisher/work-publish.action" ishome="2" class="btnSB">未审核</a>
        	<a href="/new-publisher/work-publish!auditedPage.action" ishome="5" class="btnSB">已审核</a>
        	<a href="/new-publisher/work-publish!publishedPage.action" ishome="1" class="btnGB">已发布</a>
        </div>

        <div class="conB con39">
        	<fieldset class="new">
            	<div>
                	<em><input type="text" id="queryContentText"  class="input g300"/></em>
                	<em><a class="btnWM" id="queryByTitleF" href="javascript:void(0);">搜索标题</a></em>
                	<em><a class="btnWM" id="queryByAuthorF" href="javascript:void(0);">搜索作者</a></em>
                </div>
            </fieldset>
        </div>
        
        
        <div class="conB con42">
        	<fieldset>
            	<hr />
                <div>
                	<em><a class="btnAS" id="workpublished0" href="javascript:void(0);">首页</a></em>
                	<em><a class="btnAS" id="workpublished1" href="javascript:void(0);">丽人</a></em>
                	<em><a class="btnAS" id="workpublished2" href="javascript:void(0);">绅士</a></em>
                	<em><a class="btnAS" id="workpublished3" href="javascript:void(0);">玩味</a></em>
                	<em><a class="btnAS" id="workpublished4" href="javascript:void(0);">座驾</a></em>
                	<em><a class="btnAS" id="workpublished5" href="javascript:void(0);">财界</a></em>
                </div>
            </fieldset>
        	
        </div>

        <div class="conB con43">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50">ID</td>
                    <td class="g80">作者</td>
                    <td class="g80">审核人</td>
                    <td class="tLeft">标题</td>
                    <td class="g150">分类</td>
                    <td class="g50">置顶</td>
                    <td class="g50">权重</td>
                    <td class="g120">频道置顶</td>
                    <td class="g120">发布时间</td>
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