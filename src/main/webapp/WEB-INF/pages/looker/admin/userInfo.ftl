<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/userInfo.js"></script>
<div class="body" menu="user" submenu="userInfo">
     <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g110">&nbsp;</em>
                    <em class="g200"><input id="s-uid" type="text" class="input g180" tips="用户关联ID" /></em>
                    <em class="g200"><input id="s-userId" type="text" class="input g180" tips="ID号" /></em>
                    <em class="g200"><input id="s-nickName" type="text" class="input g180" tips="昵称" /></em>
                    <em class="g160">
                    <select id="s-type" class="g140">
                    <option value="-1">关联类型</option>
                    <option value="0">新浪微博</option>
                    <option value="1">QQ</option>
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
                    <td class="g100">用户昵称</td>
                    <td class="g30">性别</td>
                    <td class="g30">年龄</td>
                    <td class="g80">手机</td>
                    <td class="g90">关联类型</td>
                    <td>用户关联ID</td>
                    <td class="g90">金币余额</td>
                    <td class="g90">邀请号</td>
                    <td class="g200">操作</td>
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
            <h6>详细</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <input type="hidden" id="userId">
            <fieldset>
            	<div class="detailPop">
            		<div class="detailImg">
		            	<img id="gravatar" width="100" height="100" style="background:#ccc;" src=""></em>
		            </div>
		            <div>
		            	<em class="g80 tRight">昵称</em>
		            	<em id="i-nickName"></em>
		            </div>
		            <div>
		            	<em class="g80 tRight">性别</em>
		            	<em id="sex"></em>
		            </div>
		            <div>
		            	<em class="g80 tRight">年龄</em>
		            	<em id="age"></em>
		            </div>
		            <div>
		            	<em class="g80 tRight">金币</em>
		            	<em id="gold"></em>
		            </div>
		            <div>
		            	<em class="g80 tRight">关联ID</em>
		            	<em id="uid"></em>
		            </div>
					<div>
		            	<em class="tRight g80">邀请号</em>
		            	<em id="invitationCode"></em>
		            </div>
					<div>
		            	<em class="tRight g80">注册时间</em>
		            	<em id="createTime"></em>
		            </div>
		            <div>
		            	<em class="g80 tRight">电话</em>
		            	<em id="mobile"></em>
		            </div>
		        </div>    
	        </fieldset>
            <!--div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">确定</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div-->
            </form>
        </div>
</#macro>

<@messageContent />
<#macro messageContent>
 <div class="popContent" id="pop004">
            <h6>发消息</h6>
            <fieldset>
            <input type="hidden" id="messageUserId">
	            <div>
	            	<em class="g80 tRight">消息目标</em>
	            	<em id="message-nickname"></em>
	            </div>
				<div class="clear">
	            	<em class="tRight g80">消息内容</em>
	            	<em><textarea id="message" class="input g270" rows="3"></textarea></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="sendMessageBtn" href="javascript:void(0)">发送</a>
                <a class="btnWS" id="cancelMessageBtn" href="javascript:void(0)">取消</a>
            </div>
        </div>
</#macro>