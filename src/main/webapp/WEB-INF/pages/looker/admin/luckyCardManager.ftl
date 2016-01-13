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
<script src="${systemProp.staticServerUrl}/look/js/luckyCardManager.js"></script>
<div class="body" menu="egg" submenu="luckyCard">
      <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g10">&nbsp;</em>
                    <em class="g100">
                    <select id="s-status" class="g100">
                    	<option value=-1>全部状态</option>
                    	<option value=1>未刮奖</option>
                    	<option value=2>锁定</option>
                    	<option value=3>已刮奖</option>
                    </select>
                    </em>
                    <em class="g100">
                    <select id="s-type" class="g100">
                    	<option value=-1>全部类型</option>
                    	<option value=2>金币</option>
                    	<option value=3>礼品</option>
                    	<option value=1>无奖</option>
                    </select>
                    </em>
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                    <em><a class="btnSS" id="newBtn" href="#">添加</a></em>
                    <em>
                    <#if luckCardUseList?exists>
                    	当前刮刮卡:
                    	<#assign total=0>
                    	<#list luckCardUseList as lc>
                    		<#if lc.type == 1>
                    		无奖
                    		<#elseif lc.type==2>
                    		金币(${lc.luckNum})
                    		<#else>
                    		礼品
                    		</#if>
                    		${lc.qty}个&nbsp;&nbsp;
                    		<#assign total=total+lc.qty>
                    	</#list>
                    	总刮刮卡数:${total}
                    </#if>
                    </em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g40">ID</td>
                    <td>名称</td>
                    <td class="g100">类型</td>
                    <td class="g60">奖励</td>
                    <td class="g60">顺序</td>
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
 <div class="popContent" id="pop006">
            <h6>生成刮刮卡</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
	             <div>
	            	<em class="g80 tRight">刮刮卡总数量</em>
	            	<em><input id="total" name="total" type="text" class="input g140"></em>
	            	<!-- em class="g140 tRight">删除当前未刮奖的刮刮卡</em>
	            	<em><input id="isDel" name="isDel" type="checkbox" class="input g20"></em -->
	            </div>
	            <div>
	            <em class="g80 tRight conAddBtn"><a id="addcardBtn" class="btnGS btnCLS" href="javascript:void(0)">增加</a></em>
            	</div>
            	<div id="carddiv" class="con1" style="height:400px;">
            	<em class="clear auto tLeft"><em class="g150">类型</em><em class="g140">奖励</em><em class="g140">数量</em></em>
	            	<p class="g600 floatl" id="adcardp">
	            	
	            	</p>
	            </div>
	           <div id="adcarddiv" style="display:none">
	            <em class="clear auto tLeft"><em class="g150"><select class="g140" name="cardType"><option value=2>金币</option><option value=3>礼品</option><option value=1>无奖</option></select></em>
	            <em class="g150"><select style="display:none;" class="g140" name="giftId">
	            <#if giftList?exists>
	            	<#list giftList as gl>
	            		<option value=${gl.id} qty=${gl.qty}>${gl.giftName}</option>
	            	</#list>
	            </#if>
	            </select></em>
	            <em class="g150"><input name="luckNum" validate=int type="text" class="input g140" tips="中奖金币" /></em>
	            <em class="g150"><input name="qty" type="text" validate=int class="input g140" tips="刮刮卡数量" /></em><a name="deleteCardBtn" href="javascript:void(0)" class="floatNone">X</a></em>
	            </div>
	            <div class="actionArea tCenter">
	                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
	                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
	            </div>
	        </fieldset>
            </form>
        </div>
</#macro>

<@sortContent />
<#macro sortContent>
 <div class="popContent" id="pop003">
            <h6>生成刮刮卡</h6>
            <form method="post" id="submitForm2" name="submitForm2" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<input type="hidden" id="cardId">
	            <div>
	            	<em class="g80 tRight">当前位置</em>
	            	<em><input id="currentSort" name="currentSort" type="text" readonly=readonly class="input g80"></em>
	            	<em class="g80 tRight">最大位置</em>
	            	<em>${sortOrder!}</em>
            	</div>
	             <div>
	            	<em class="g80 tRight">移动方向</em>
	            	<em><em>向前</em><input name="direction" type="radio" value=-1 checked class="input g20"><em>向后</em><input name="direction" value=1 type="radio" class="input g20"></em>
	            </div>
	            <div>
	            	<em class="g80 tRight">移动距离</em>
	            	<em><input id="offset" name="offset" type="text" class="input g140"></em>
            	</div>
	            <div class="actionArea tCenter">
	                <a class="btnGS" id="saveSortBtn" href="javascript:void(0)">保存</a>
	                <a class="btnWS" id="cancelSortBtn" href="javascript:void(0)">取消</a>
	            </div>
	        </fieldset>
            </form>
        </div>
</#macro>