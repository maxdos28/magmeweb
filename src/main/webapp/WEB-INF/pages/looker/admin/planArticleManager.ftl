<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/planArticleManager.js"></script>
<div class="body" menu="item" submenu="article">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
               <div>
                   	<em class="g90 floatr"><a class="btnWS" href="${systemProp.appServerUrl}/look/look-article.action">返回</a></em>
                    <em class="g200"><input id="s-title" type="text" class="input g180" tips="名称" /></em>
                    <em class="g160"><select id="s-category" class="g140"><option value="-1">全部分类</option></select></em>
                    <em class="g160"><select id="s-item" class="g140"><option value="-1">全部栏目</option></select></em> 
                    <em class="g80"><input id="s-plan" type="checkbox" checked=checked class="input"/>定时上架</em> 
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                </div>
                <br>
              <div>
                    <em class="g200"><input id="planDate" type="text" class="input g180" tips="上架日期" /></em>
              		<em class="g140">
	                    <select id="planTime" class="g140">
	                    <option value="-1">上架时段</option>
	                    <option value="13">6:45</option>
	                    <option value="23">11:45</option>
	                    <option value="33">16:45</option>
	                    </select></em>
              		<em class="g80"><a class="btnBS" id="saveBtn" href="#">保存</a></em>
               </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g60"></td>
                    <td class="g50">缩略图</td>
                    <td class="g30">ID</td>
                    <td class="g70">分类</td>
                    <td class="g80">栏目 / 账号</td>
                    <td class="g90">发布</td>
                    <td class="g90">名称</td>
                    <td>描述</td>
                    <td class="g60">状态</td>
                    <td class="g90">时间</td>
                    <td>操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">          
                     
                </tbody>
            </table>
        </div>
    </div>
</div>

<@editContent />
<#macro editContent>
 <div class="popContent" id="pop100">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<em class="g80 tRight conAddBtn"><a id="additemBtn" class="btnGS btnCLS" href="javascript:void(0)">增加栏目</a></em>
            	<div id="itemdiv" class="con1">
	            	<em class="g100 tRight">&nbsp;</em>
	            	<p class="g200 floatl" id="aditemp">
	            	</p>
	            </div>
            	<input type="hidden" id="id" name="id">
	             <div>
	            	<em class="g100 tRight">标题</em>
	            	<em><input id="title" name="title" type="text" class="input g140"></em>
	            	<em class="g40 tRight">置顶</em>
	            	<em><input id="isTop" name="isTop" type="checkbox"></em>
	            </div>
	             <div>
	            	<em class="g100 tRight">描述</em>
	            	<em><textarea row=2 name="memo" id="memo" class="input g260"></textarea></em>
	            </div>
	             <div>
	            	<em class="g100 tRight">pad缩略图</em>
	            	<em><input id="smallPic1" name="smallPic1" type="file" class="g140">(472-472)</em>
	            </div>
	             <div>
	            	<em class="g100 tRight">phone缩略图</em>
	            	<em><input id="smallPic2" name="smallPic2" type="file" class="g140">(360-360)</em>
	            </div>
	            
	            <div id="aditemdiv" style="display:none">
	            <em class="clear auto tLeft"><select class="g140" name="item"></select><a name="deletePutBtn" href="javascript:void(0)" class="floatNone">X</a></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveArticleBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>