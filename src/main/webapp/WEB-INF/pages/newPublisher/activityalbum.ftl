<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script>
	var pageCount = ${pageCount!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/activityalbum.js"></script>

<div class="conLeftMiddleRight" menu="editor" label="<#if type??&&type==9999>albumApp<#else>album</#if>">  
	<div class="con29 conTools">
        	<fieldset>
        	<form id="activityAlbumForm">
                    	<input type="hidden" name="aaid" id="aaid"/>
                <div>
                	<#if type??&&type==9999>
                	<em class="g50">标题</em><em class="g250"><input name="title"  type="text" class="input g200" /></em>
                	<#else>
                	<em class="g50">活动标题</em><em class="g250"><input name="title"  type="text" class="input g200" /></em>
                	<em class="g50">链接地址</em><em class="g250"><input type="text" name="url" class="input g200" /></em></#if>
                	<em class="g50">权重</em><em><input name="weights" type="text" class="input g80" /></em>
                </div>
                <hr />
            	<div>
            		<#if type??&&type==9999>
            			<em class="g50">栏目</em><em class="g250">
                			<select name="type" class="g200">
                                	<option value="9999">开机图片</option>
                                </select>
                	</em>
            		<#else>
            		<em class="g50">栏目</em><em class="g250">
                			<select name="type" class="g200">
                                	<option value="0">首页</option>
                                	<#if sortList?? && (sortList?size) gt 0>
						              <#list sortList as slist>
						              		<option value="${slist.id}">${slist.name}</option>
						              </#list>
					              </#if>
                                </select>
                	</em>
                	</#if>
                	<em class="g50">上传图片</em><em class="g250"><input id="pic" name="pic" type="file" class="g200" /></em>
                	<#if type??&&type==9999>
            		<#else>
                	<em>图片尺寸为：238px-140px</em>
                	</#if>
                </div>
                <hr />
            	<div>
                	<em class="g50">图片说明</em><em><textarea name="alt" class="input g140"></textarea></em>
                </div>
            	<div class="btn">
                    <em><a class="btnWB" id="resetActivityAlbumBtn" href="javascript:void(0)">清空</a></em>
                    <em><a class="btnBB" id="saveActivityAlbumBtn" href="javascript:void(0)">保存</a></em>
                </div>
                </form>
            </fieldset>
        </div>
    	<div class="con30 conB">
        	<fieldset>
            	<div>
                	<em class="g100"><strong>查询条件</strong></em>
                	<#if type??&&type==9999>
                		<em>栏目</em><em class="g250"><select class="g200" name="filter_type"><option value="9999">开机图片</option>
                    </select></em>
                	<#else>
                	<em>栏目</em><em class="g250"><select class="g200" name="filter_type"><option value="0">首页</option>
                      <#if sortList?? && (sortList?size) gt 0>
			              <#list sortList as slist>
			              		<option value="${slist.id}">${slist.name}</option>
			              </#list>
		              </#if>
                    </select></em>
                	</#if>
                	
                	<em>状态</em><em class="g250">
                	<select name="filter_status" class="g200"><option value="12">待发布+已发布</option><option value="1">已发布</option><option value="2">待发布</option><option value="0">下架</option></select>
                	</em>
                </div>
                <hr />
            </fieldset>
        </div>
	<div class="conB con31">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td>标题</td>
                    <td class="g100">图片</td>
                    <#if type??&&type==9999><#else><td class="g150">链接</td></#if>
                    <td class="g60">栏目</td>
                    <td class="g60">权重</td>
                    <td class="g100">创建时间</td>
                    <td class="g60">状态</td>
                    <td class="g60">编辑</td>
                    <td class="g60">修改</td>
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