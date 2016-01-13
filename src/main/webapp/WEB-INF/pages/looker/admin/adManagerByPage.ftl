<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/pageAdManager.js"></script>
<div class="body" menu="ad" submenu="adpage">
       <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <em class="g70">&nbsp;</em>
                    <em class="g90"><input id="s-from-date" class="input g80" type="text" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input id="s-end-date" class="input g80" type="text" tips="结束日期" /></em>
                    <em class="g160"><select id="s-status" class="g140"><option value="-1">全部状态</option></select></em>
                    <em class="g160"><select id="s-adTotalId" class="g140"><option value="-1">全部分类</option></select></em>     
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
                    <td class="g150">广告名称</td>
                    <td class="g120">广告分类</td>
                    <td class="g90">起始时间</td>
                    <td class="g90">到期时间</td>
                    <td class="g70">状态</td>
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
 <div class="popContent" id="pop004" style="height:500px;width:570px;">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="mobileAdDetailId" name="mobileAdDetailId">
	             <div>
	            	<em class="g80 tRight">名称</em>
	            	<em><input id="dname" name="dname" type="text" class="input g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">广告文件</em>
	            	<em><input id="adZip" name="adZip" type="file" class="g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">公司URL</em>
	            	<em><input id="adCompanyUrl" name="adCompanyUrl" type="text" class="input g140" value="http://"></em>
	            </div>
	           	 <div>
	            	<em class="g80 tRight">广告分类</em>
	            	<em><select id="totalAddId" name="totalAddId" class="g140"></select></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">开始时间</em>
	            	<em class="g90"><input id="onlineTime" name="onlineTime" class="input g80" type="text" /></em>
	            </div>
	           	<div>
	            	<em class="g80 tRight">结束时间</em>
	            	<em class="g100"><input id="offlineTime" name="offlineTime" class="input g80" type="text" /></em>
	            </div>
				<div>
	            	<em class="g80 tRight">内容类型</em>
	            	<em><label><input name="contentType" value="2" type="radio" checked>杂志</label></em>
	            	<em class="g80"><label><input name="contentType" value="1" type="radio">栏目(文章)</label></em>
	            	<em class="g80"><label><input name="contentType" value="3" type="radio">分类</label></em>
	            </div>
				<div id="putdiv">
	            	<em class="g80 tRight">关联杂志</em>
	            	<p class="g350 floatl" id="adputp" style="max-height:150px;overflow-y:auto;">
	            	</p>
	            	<em class="g80 tRight"><a id="addPutBtn" class="btnGS btnCLS" href="javascript:void(0)">增加</a></em>
	            </div>
	           	<div id="itemdiv" style="display:none;">
	            	<em class="g80 tRight">关联栏目</em>
	            	<p class="g350 floatl" id="aditemp" style="max-height:150px;overflow-y:auto;">
	            	</p>
	            	<em class="g80 tRight"><a id="additemBtn" class="btnGS btnCLS" href="javascript:void(0)">增加</a></em>
	            </div>
	           	<div id="catediv" style="display:none;">
	            	<em class="g80 tRight">关联分类(LOOK)</em>
	            	<em class="tRight"><select class="g120" id="category" name="category"></select><input class="g60" id="cate-page" validate=int name="position" tips="页码"></em>
	            </div>
	            <div id="adputdiv" style="display:none">
	            <em class="clear auto tLeft"><select class="g120" name="app"></select> <select class="g120" name="publication"></select><input class="g60" validate=int name="page" tips="页码"> <a name="deletePutBtn" href="javascript:void(0)" class="floatNone">X</a></em>
	            </div>
	            <div id="aditemdiv" style="display:none">
	            <em class="clear auto tLeft"><select class="g120" name="itemapp"></select>  <select class="g120" name="item"></select><input class="g60" validate=int name="page" tips="页码"> <a name="deletePutBtn" href="javascript:void(0)" class="floatNone">X</a></em>
	            </div>
	        </fieldset> 
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>