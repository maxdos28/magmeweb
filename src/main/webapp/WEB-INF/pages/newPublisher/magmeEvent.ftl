
 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
	var curPageNum = ${pageNum!"1"};
	var pageSize = ${page.limit!"50"};
	var pageCount = ${page.totalPage!"1"};
</script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/magmeEvent.js"></script>

   <div class="conLeftMiddleRight" menu="editor" label="event" >
    	<div class="con19 conTools">
        	<fieldset>
            		<form id="searchForm" method="post">
            	<div>
                    <em class="floatr"><a id="btn_publish" class="btnBS" href="#">发布</a></em>
                    <em class="floatr"><a id="btn_preview" class="btnWS" href="#">预览</a></em>
                	<em class="tRight g60">创建日期</em>
                	<em class="g110"><input id="createdDate" name="createdDate" type="text" class="input g100" /></em>
                	<em class="tRight g60">事件ID</em>
                	<em><input name="eventId" type="text" class="input g40" /></em>
                	<em class="tRight g60">杂志ID</em>
                	<em><input name="publicationId" type="text" class="input g40" /></em>
                	<em class="tRight g40">标题</em>
                	<em><input name="title" type="text" class="input g70" /></em>
                	<em class="tRight g60">事件状态</em>
                	<em class="g80"><select name="status"><option value="">全部</option><option value="2">待发布</option><option value="1">生效</option><option value="0">无效</option></select></em>
                	<em class="tRight g60">适用手机</em>
                	<em class="g60"><select name="isSuitMobile"><option value="">全部</option><option value="0">否</option><option value="1">是</option></select></em>
                </div>
                <hr />
                <div>
                	<em class="tRight g60">出版商类型</em>
                	<em><select class="g60" name="reserved1"><option value="">全部</option><option value="0">非一线</option><option value="1">一线</option></select></em>
                	<em class="tRight g60">事件类型</em>
                	<em><select class="g60" name="adId"><option value="">全部</option><option value="0">普通</option><option value="1">广告</option></select></em>
                	<em class="tRight g50">制作人</em>
                	<em>
                	<select class="g100" id="userId" name="userId" >
                		<option value="">所有人</option>
            			<#if userList??>
						<#list userList as user>
							<option value="${(user.id)!''}">${(user.userName)!''}</option>
						</#list>
						</#if>
                   </select>
                	</em>
                	<em class="tRight g60">期刊ID</em>
                	<em><input name="issueId" type="text" class="input g60" /></em>
                	<em class="tRight g50">描述</em>
                	<em><input name="description" type="text" class="input g70" /></em>
                	<em class="tRight g60">是否推荐</em>
                	<em class="g80"><select name="isRecommend"><option value="">全部</option><option value="0">否</option><option value="1">是</option></select></em>
                </div>
                <hr />
                <div>
                	
                	
                	<em class="tRight g50">首页频道</em>
                	<em class="tRight g100">
	                	<select class="g60" id="userId" name="channel" >
	                		<option value="">全部</option>
	                		<#if sortList??>
								<#list sortList as t>
		                			<option value="${t.name!}">${t.name!}</option>
		                		</#list>
							</#if>
	                   	</select>
                	</em>
                	<em ><input type="checkbox" value='1'  name="sidebar" class="input" >频道侧栏</em>
                	<em class="tRight g50">&nbsp;</em>
                	<em class="tRight g50"><a id="btn_search" class="btnBS g70" href="#">查询</a></em>
                </div>
                <hr />
            	</form>
            </fieldset>
        </div>
        <div id="tablePageBarContainer" class="conB con22">
           <h2 style="text-align:center;">loading...</h2>
        </div>
        <div class="conFooter">
        	<div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>
       
