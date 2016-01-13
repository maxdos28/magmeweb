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
<script src="${systemProp.staticServerUrl}/look/js/categoryAdManager.js"></script>
<div class="body" menu="ad" submenu="adcategory">
       <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <em class="g120">&nbsp;</em>
                    <em class="g160"><select id="s-type" class="g140">
                    <option value="-1">全部类型</option>
                    <option value="1">栏目</option>
                    <option value="0">外链</option>
                    </select></em>
                    <em class="g160"><select id="s-categoryId" class="g140"><option>全部分类</option></select></em>
                    <em class="g160"><select id="s-itemId" class="g140"><option>全部栏目</option></select></em>        
                    <em class="g200"><input id="s-title" type="text" class="input g180" tips="名称" /></em>
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                    <em><a class="btnSS" id="newBtn" href="#">添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                     <td class="g30">排序</td>
                    <td class="g50">缩略图</td>
                    <td class="g60">尺寸</td>
                    <td class="g120">名称</td>
                    <td class="g120">分类</td>
                    <td class="g40">类型</td>
                    <td>链接</td>
                    <td class="g120">栏目/文章</td>
                    <td class="g60">状态</td>
                    <td class="g130">操作</td>
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
 <div class="popContent" id="pop004">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="categoryRecommendId">
	            <div>
	            	<em class="g110 tRight">排序</em>
	            	<em><input id="sortOrder" name="sortOrder" type="text" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g110 tRight">名称</em>
	            	<em><input id="title" name="title" type="text" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g110 tRight">图片尺寸</em>
	            	<em><select id="size" name="size" class="g140">
	            	<option value="1">全尺寸</option>
	            	</select></em>
	            </div>
	             <div>
	            	<em class="g110 tRight">分类</em>
	            	<em><select id="categoryId" name="categoryId" class="g140"><option>全部分类</option></select></em>
	            </div>
	            <div>
	            	<em class="g110 tRight">图片(1256*380)</em>
	            	<em><em><input id="recommendPic" name="recommendPic" type="file" class="g140"></em></em>
	            </div>
	            <div>
	            	<em class="g110 tRight">类型</em>
	            	<em><label><input name="type" value="1" type="radio" checked>栏目</label></em>
	            	<em><label><input name="type" value="2" type="radio" checked>文章</label></em>
	            	<em class="g50"><label><input name="type" value="0" type="radio">外链</label></em>
	            </div>
	            <div id="itemdiv">
	            	<em class="g110 tRight">栏目</em>
	            	<em><select id="itemId" name="itemId" class="g140"><option>全部栏目</option></select></em>
	            </div>
	            <div id="articlediv" style="display:none">
	            	<em class="g110 tRight">文章</em>
	            	<em><input id="articleId" name="articleId" type="text" class="input g140" value=""></em>
	            </div>
	            <div id="linkdiv" style="display:none">
	            	<em class="g110 tRight">链接</em>
	            	<em><input id="link" name="link" type="text" class="input g140" value="http://"></em>
	            </div>
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>