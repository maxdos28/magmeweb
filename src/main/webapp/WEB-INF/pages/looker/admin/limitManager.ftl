<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/ui-darkness/jquery-ui-1.10.4.custom.min.css"  rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery-ui-timepicker-addon.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/limitManager.js"></script>
<div class="body" menu="egg" submenu="limit">
      <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g10">&nbsp;</em>
                    <em class="g160"><input id="s-giftCode" type="text" class="input g140" tips="礼品号" /></em>
                    <em class="g200"><input id="s-giftName" type="text" class="input g180" tips="礼品名称" /></em>
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                    <em><a class="btnSS" id="newBtn" href="#">添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50">缩略图</td>
                    <td class="g100">礼品号</td>
                    <td>礼品名称</td>
                    <td class="g80">所需金币</td>
                    <td class="g80">数量</td>
                    <td class="g80">显示数量</td>
                    <td class="g80">上架时间</td>
                    <td class="g80">抢购时间</td>
                    <td class="g60">状态</td>
                    <td class="g150">操作</td>
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
 <div class="popContent" id="pop003">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="giftId" name="giftId">
	             <div>
	            	<em class="g80 tRight">礼品号</em>
	            	<em id="giftCode">自动生成</em>
	            </div>
	             <div>
	            	<em class="g80 tRight">礼品名称</em>
	            	<em><input id="giftName" name="giftName" type="text" class="input g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">图片</em>
	            	<em><input name="giftPic" id="giftPic" type="file" class="g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">所需金币</em>
	            	<em><input id="goldNum" name="goldNum" type="text" class="input g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">数量</em>
	            	<em><input id="qty" name="qty" type="text" class="input g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">显示数量</em>
	            	<em><input id="showQty" name="showQty" type="text" class="input g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">上架时间</em>
	            	<em><input id="startDate" name="startDate" type="text" class="input g140"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">抢购时间</em>
	            	<em><input id="endDate" name="endDate" type="text" class="input g140"></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>