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
<script src="${systemProp.staticServerUrl}/look/js/userFeedback.js"></script>
<div class="body" menu="user" submenu="userFeedback">
      <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g90"><input class="input g80" type="text" id="s-from-date" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input class="input g80" type="text" id="s-end-date" tips="结束日期" /></em>
                    <em class="g160"><input id="s-userId" type="text" class="input g140" tips="ID号" /></em>
                    <em class="g160"><input id="s-nickName" type="text" class="input g140" tips="昵称" /></em>
                    <em class="g160">
                    <select id="s-replyStatus" class="g140">
                    <option value="-1">全部状态</option>
                    <option value="1">已回复</option>
                    <option value="0">未回复</option>
                    </select></em>
                    <em class="g160">
                    <select id="s-os" class="g140">
                    <option value="-1">全部设备</option>
                    <option value="ANDROID">android</option>
                    <option value="iOS">ios</option>
                    </select></em>           
                    <em><a id="searchBtn" class="btnGS" href="#">查询</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g30">ID号</td>
                    <td class="g90">用户昵称</td>
                    <td class="g80">日期</td>
                    <td>内容</td>
                    <td>设备信息</td>
                    <td class="g50">状态</td>
                    <td>回复</td>                   
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
            <h6>回复</h6>
            	<input type="hidden" id="userFeedbackId">
            <fieldset>
	            <div>
	            	<em class="g120 tLeft">请输入回复内容</em>
	            	
	            </div>
				<div class="clear">
	            	<em><textarea id="message" class="input g350" rows="3"></textarea></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="replayMessageBtn" href="javascript:void(0)">发送</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
        </div>
</#macro>