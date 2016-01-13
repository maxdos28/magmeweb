<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/userGold.js"></script>
<div class="body" menu="user" submenu="userGold">
     <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g30">&nbsp;</em>
                    <em class="g90"><input id="s-from-date" class="input g80" type="text" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input id="s-end-date" class="input g80" type="text" tips="结束日期" /></em>
                    <em class="g160"><input id="s-userId" type="text" class="input g140" tips="ID号" /></em>
                    <em class="g160"><input id="s-nickName" type="text" class="input g140" tips="昵称" /></em>
                    <em class="g140">
                    <select id="s-addType" class="g120">
                    <option value="-1">金币方向</option>
                    <option value="1">入金</option>
                    <option value="0">出金</option>
                    </select>
                    </em>
                    <em class="g160">
                    <select id="s-type" class="g140">
                    <option value="-1">所有操作方式</option>
                    <option value="1">分享</option>
                    <option value="2">签到</option>
                    <option value="3">阅读</option>
                    <option value="4">兑换</option>
                    <option value="5">初次登陆</option>
                    <option value="6">邀请好友</option>
                    </select>
                    </em>          
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g30">ID号</td>
                    <td class="g120">用户昵称</td>
                    <td class="g120">日期</td>
                    <td class="g120">金币方向</td>
                    <td>操作方式</td>
                    <td class="g120">数额</td>
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