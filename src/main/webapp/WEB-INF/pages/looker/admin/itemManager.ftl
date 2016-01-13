<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/colorpicker.js"></script>
<link rel="stylesheet" href="${systemProp.staticServerUrl}/look/style/colorpicker.css" type="text/css" />
<script src="${systemProp.staticServerUrl}/look/js/itemManager.js"></script>
<div class="body" menu="item" menu="item" submenu="item">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <em class="g120">&nbsp;</em>
                    <em class="g160">
                    <select id="s-isRecommend" class="g140">
                    <option value="-1">全部推荐</option>
                    <option value="1">推荐</option>
                    <option value="0">不推荐</option>
                    </select>
                    </em>
                    <em class="g160">
                    <select id="s-itemType" class="g140">
                    <option value="-1">全部类型</option>
                    <option value="1">文章</option>
                    <option value="2">杂志</option>
                    </select>
                    </em>
                    <em class="g160"><select id="s-categoryId" class="g140"><option value="-1">全部分类</option></select></em>               
                    <em class="g200"><input id="s-title" type="text" class="input g180" tips="名称" /></em>
                    <em><a id="searchBtn" class="btnGS" href="#">查询</a></em>
                    <em><a id="newBtn" class="btnSS" href="#">添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g40">顺序</td>
                    <td>名称</td>
                    <td class="g120">分类</td>
                    <td class="g90">类型</td>
                    <td class="g90">状态</td>
                    <td class="g170">操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">          
                     
                </tbody>
            </table>
        </div>
        <#import "../../components/pagebar.ftl" as pageBar>
	   <@pageBar.main/>
    </div>
</div>

<@editContent />
<#macro editContent>
 <div class="popContent" id="pop003" style="maxheight:550px;height:450px;">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="itemId">
	            <div>
	            	<em class="g80 tRight">排序</em>
	            	<em><input id="sortOrder" name="sortOrder" type="text" class="input g70"></em>
	            	<em class="g50 tRight">不可删除</em>
	            	<em><input id="isDelete" class="input g20" name="isDelete" type="checkbox"></em>
	            	<em class="g20 tRight">新</em>
	            	<em><input id="isNew" class="input g20" name="isNew" type="checkbox"></em>
	            	<em class="g20 tRight">热</em>
	            	<em><input id="isHot" class="input g20" name="isHot" type="checkbox"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">父栏目</em>
	            	<em><select id="parentId" name="parentId" class="g140"><option value="0"></option></select></em>
	            </div>
	             <div>
	            	<em class="g80 tRight">名称</em>
	            	<em><input id="title" name="title" type="text" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g80 tRight">描述</em>
	            	<em><textarea row=2 name="memo" id="memo" class="input g140"></textarea></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">分类</em>
	            	<em><select id="categoryId" name="categoryId" class="g140"></select></em>
	            </div>	            
	            <div>
	            	<em class="g80 tRight">颜色</em>
	            	<em><input type="text" name="color" id="color" class="g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">类别</em>
	            	<em><label><input name="itemType" value="2" type="radio" checked>杂志</label></em>
	            	<em class="g50"><label><input name="itemType" value="1" type="radio">文章</label></em>
	            </div>
	            <div id="publicationDiv">
	            	<em class="g80 tRight">杂志关联</em>
	            	<em><select id="publicationId" name="publicationId" class="g140"><option value=1>杂志1</option></select></em>
	            </div>
	            <div id="picDiv" style="display:none;">
	            	<div>
		            	<em class="g80 tRight">图片(472*472)</em>
		            	<em><input name="itemPic" id="itemPic" type="file" class="g140"></em>
		            </div>
	            </div>
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>