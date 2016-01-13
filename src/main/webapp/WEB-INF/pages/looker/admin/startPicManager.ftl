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
<script src="${systemProp.staticServerUrl}/look/js/startPic.js"></script>
<div class="body" menu="startpic">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <em class="g480">&nbsp;</em>
                    <em class="g90"><input class="input g80" type="text" id="s-from-date" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input class="input g80" type="text" id="s-end-date" tips="结束日期" /></em>
                    <em class="g120"><input id="s-title" type="text" class="input g100" tips="名称" value=''/></em>
                    <em><a class="btnGS" id="searchStartPicBtn" href="#">查询</a></em>
                    <em><a class="btnSS" id="newStartPicBtn" href="#">添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g250">名称</td>
                    <td class="g120">图片缩略图</td>
                    <td>链接</td>
                    <td class="g80">状态</td>
                    <td class="g140">操作</td>
                    <td class="g120">时间</td>  
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
<div class="popContent" id="pop001" style="width:400px;">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
	            <div>
	            	<em class="g110 tRight">名称</em>
	            	<em><input name="picTitle" id="picTitle" type="text" class="input g200"></em>
	            	<input name="startPicId" id="startPicId" type="hidden"/>
	            </div>
	            <div>
	            	<em class="g110 tRight">PAD(1536*2048)</em>
	            	<em><input name="startLargePic" id="startLargePic" type="file" class="g200"></em>
	            </div>
	            <div>
	            	<em class="g110 tRight">PHONE(640*1136)</em>
	            	<em><input name="startSmallPic" id="startSmallPic" type="file" class="g200"></em>
	            </div>
	            <div>
	            	<em class="g110 tRight">链接</em>
	            	<em><input name="picLink" id="picLink" type="text" class="input g200" value="http://"></em>
	            </div>
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</div>
</#macro>