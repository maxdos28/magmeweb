<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/messageManager.js"></script>
<div class="body" menu="message" submenu="msg">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <em class="g60">&nbsp;</em>
                    <em class="g90"><input class="input g80" type="text" id="s-from-date" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input class="input g80" type="text" id="s-end-date" tips="结束日期" /></em>
                    <em class="g160">
                    <select id="s-type" class="g140">
                    <option value="-1">消息分类</option>
                    <option value="0">公共消息</option>
                    <option value="1">客服消息</option>
                    <option value="2">系统消息</option>
                    <option value="3">用户消息</option>
                    </select></em>
                    <em class="g120"><input id="s-userId" type="text" class="input g100" tips="ID号" /></em>
                    <em class="g120"><input id="s-nickName" type="text" class="input g100" tips="昵称" /></em>
                    <em class="g120"><input id="s-content" type="text" class="input g100" tips="内容" /></em>
                    <em><a id="searchBtn" class="btnGS" href="#">查询</a></em>
                    <em><a id="releaseBtn" class="btnSS" href="#">发布</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g150">发布时间</td>
                    <td class="g140">发布对象</td>
                    <td class="g140">对象ID</td>
                    <td class="g140">对象昵称</td>
                    <td>消息内容</td>  
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
            <h6>发布</h6>
            <fieldset>
	            <div>
	            	<em class="g50 tRight">消息目标</em>
	            	<em class="g70"><label><input type="radio" name="messageType" value=0 checked>公共消息</label></em>
	            	<em><label><input type="radio" name="messageType" value=1>客服消息</label></em>
	            	<em><label><input type="radio" name="messageType" value=2>系统消息</label></em>
	            </div>
	            <div>
	            	<em class="g50 tRight">&nbsp;</em>
	            	<em><input id="userId" type="text" class="input g70" tips="用户ID"></em>
	            	<em>或</em>
	            	<em><input id="nickName" type="text" class="input g70" tips="用户昵称"></em>
	            </div>
				<div class="clear">
					<em class="g50 tRight">消息内容</em>
	            	<em><textarea id="message" class="input g300" rows="3"></textarea></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="sendMessageBtn" href="javascript:void(0)">发送</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
        </div>
</#macro>