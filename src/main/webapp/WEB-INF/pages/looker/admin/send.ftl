<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/sendManager.js"></script>
<div class="body" menu="message" submenu="send">
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
                    <option value="-1">类型</option>
                    <option value="1">文章</option>
                    <option value="2">内容</option>
                    </select></em>
                    <em class="g120"><input id="s-articleId" type="text" validate=int class="input g100" tips="文章ID号" /></em>
                    <em class="g120"><input id="s-message" type="text" class="input g100" tips="内容" /></em>
                    <em><a id="searchBtn" class="btnGS" href="#">查询</a></em>
                    <em><a id="releaseBtn" class="btnSS" href="#">推送</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g150">类型</td>
                    <td class="g140">文章ID</td>
                    <td class="g140">推送时间</td>
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
            <h6>推送</h6>
            <fieldset>
	            <div>
	            	<em class="g50 tRight">类型</em>
	            	<em class="g70"><label><input type="radio" name="sendType" value=1>文章</label></em>
	            	<em><label><input type="radio" name="sendType" value=2 checked>内容</label></em>
	            </div>
				<div class="clear" id="articleDiv">
					<em class="g50 tRight">文章ID</em>
	            	<em><input type="text" validate=int name="articleId" id="articleId"></em>
	            </div>
				<div class="clear" id="msgDiv">
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