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
<script src="${systemProp.staticServerUrl}/look/js/userGift.js"></script>
<div class="body" menu="user" submenu="userGift">
      <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g10">&nbsp;</em>
                    <em class="g90"><input class="input g80" type="text" id="s-from-date" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input class="input g80" type="text" id="s-end-date" tips="结束日期" /></em>
                    <em class="g160"><input id="s-userId" type="text" class="input g140" tips="ID号" /></em>
                    <em class="g160"><input id="s-nickName" type="text" class="input g140" tips="昵称" /></em>
                    <em class="g160">
                    <select id="s-giftId" class="g140">
                    <option value="-1">礼品名称</option>
                    </select></em>
                    <em class="g160">
                    <select id="s-giftStatus" class="g140">
                    <option value="-1">兑换状态</option>
                    <option value="0">未发</option>
                    <option value="1">拒绝</option>
                    <option value="2">发放</option>
                    </select></em>          
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g30">ID号</td>
                    <td class="g90">用户昵称</td>
                    <td class="g80">用户手机号</td>
                    <td class="g60">用户姓名</td>
                    <td>用户邮箱</td>
                    <td class="g90">用户职业</td>
                    <td class="g70">申请日期</td>
                    <td class="g90">礼品名称</td>
                    <td class="g50">状态</td>
                    <td class="g90">操作</td>                   
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
            <h6>拒绝</h6>
            <fieldset>
            	<input type="hidden" id="userGiftId">
	            <div>
	            	<em class="g120 tLeft">请输入拒绝原因</em>
	            	
	            </div>
				<div class="clear">
	            	<em><textarea id="message" class="input g350" rows="3"></textarea></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="rejectMessageBtn" href="javascript:void(0)">发送</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
        </div>
</#macro>