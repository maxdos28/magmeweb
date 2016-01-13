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
<script src="${systemProp.staticServerUrl}/look/js/categoryManager.js"></script>
<div class="body" menu="item" submenu="category">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <em class="g610">&nbsp;</em>               
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
                    <td class="g50">缩略图</td>
                    <td class="g40">权重</td>
                    <td class="g120">名称</td>
                    <td>描述</td>
                    <td class="g120">颜色</td>
                    <td class="g80">状态</td>
                    <td class="g140">操作</td>
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
 <div class="popContent" id="pop002" style="maxheight:550px;height:450px;width:400px;">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="categoryId">
	            <div>
	            	<em class="g80 tRight">颜色</em>
	            	<em><input type="text" name="color" id="color" class="g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">排序</em>
	            	<em><input type="text" name="sortOrder" id="sortOrder" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g80 tRight">名称</em>
	            	<em><input type="text" name="title" id="title" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g80 tRight">描述</em>
	            	<em><textarea row=2 name="memo" id="memo" class="input g140"></textarea></em>
	            </div>
	            <div>
		            	<em class="g80 tRight">图片(144*144)</em>
		            	<em><input name="categoryPic1" id="categoryPic1" type="file" class="g140"></em>
		        </div>
		        <div>
		            	<em class="g80 tRight">图片(96*96)</em>
		            	<em><input name="categoryPic2" id="categoryPic2" type="file" class="g140"></em>
		        </div>
		        <div>
		            	<em class="g80 tRight">图片(72*72)</em>
		            	<em><input name="categoryPic3" id="categoryPic3" type="file" class="g140"></em>
		        </div>
		        <div>
		            	<em class="g80 tRight">图片(48*48)</em>
		            	<em><input name="categoryPic4" id="categoryPic4" type="file" class="g140"></em>
		        </div>
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>