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
<script src="${systemProp.staticServerUrl}/look/js/userEgg.js"></script>
<div class="body" menu="egg" submenu="userEgg">
      <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g110">&nbsp;</em>
                    <em class="g190"><input id="s-userId" type="text" class="input g170" tips="用户ID" /></em>
                    <em class="g190"><input id="s-nickName" type="text" class="input g170" tips="用户昵称" /></em>
                    <em class="g190"><input id="s-eggCode" type="text" class="input g170" tips="彩蛋活动号" /></em>
                    <em class="g190"><input id="s-ticketNum" type="text" class="input g170" tips="彩卷号码" /></em>
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g120">用户ID</td>
                    <td class="g130">用户昵称</td>
                    <td class="g130">彩蛋活动号</td>
                    <td class="g120">已经获得彩蛋数量</td>
                    <td>彩卷号码</td>
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