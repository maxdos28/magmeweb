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
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/eggManager.js"></script>
<div class="body" menu="egg" submenu="egg">
      <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g410">&nbsp;</em>
                    <em class="g200"><input id="s-eggCode" type="text" class="input g180" tips="彩蛋活动号" /></em>
                    <em class="g200"><input id="s-eggName" type="text" class="input g180" tips="活动名称" /></em>
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                    <em><a class="btnSS" id="newBtn" href="#">添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g120">彩蛋活动号</td>
                    <td>活动名称</td>
                    <td class="g120">数量</td>
                    <td class="g90">开始时间</td>
                    <td class="g90">结束时间</td>
                    <td class="g90">状态</td>
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
 <div class="popContent" id="pop003" style="height:500px;width:480px;">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="managerId">
	            <div>
	            	<em class="g80 tRight">彩蛋活动号</em>
	            	<em id="eggCode">自动生成</em>
	            </div>
	             <div>
	            	<em class="g80 tRight">彩蛋名称</em>
	            	<em><input id="eggName" name="eggName" type="text" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g80 tRight">彩蛋数量</em>
	            	<em><input id="eggNums" name="eggNums" type="text" class="input g140"></em>
	            </div>
	             <div>
	            	<em class="g80 tRight">彩蛋号码</em>
	            	<em><input id="eggNos" name="eggNos" type="text" class="input g140"></em>
	            	<em>格式:1,2,3-8,9</em>
	            </div>
	            <div>
	            	<em class="g80 tRight">开始时间</em>
	            	<em class="g90"><input id="beginTime" name="beginTime" class="input g80" type="text" /></em>
	            </div>
	           	<div>
	            	<em class="g80 tRight">结束时间</em>
	            	<em class="g100"><input id="endTime" name="endTime" class="input g80" type="text" /></em>
	            </div>
	           	<div>
	            	<em class="g80 tRight">关联栏目</em>
	            	<p class="g270 floatl" id="itemp" style="max-height:100px;overflow-y:auto;">
	            	</p>
	            	<em class="g80 tRight"><a class="btnGS btnCLS" name="addItemBtn" href="javascript:void(0)">增加</a></em>
	            </div>
	            <div id="itemdiv" style="display:none;">
	            <em id="itemem" class="clear auto tLeft"><select class="g150" name="item"></select> <input name="articleBeginTime" class="input g80" type="text" /> <a name="deleteItemBtn" href="javascript:void(0)" class="floatNone">X</a></em>
	            </div>
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>