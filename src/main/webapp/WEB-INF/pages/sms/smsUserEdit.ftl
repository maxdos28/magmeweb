<!--body-->
<div class="body">
<script src="${systemProp.staticServerUrl}/v3/sms/devjs/smsUserEdit.js"></script>
    <div class="conLeftMiddleRight">
    	<div class="conTools">
        	<fieldset>
            	<h2>账户信息管理</h2>
            </fieldset>
        </div>
    	<div class="conB con1">
        	<fieldset class="new">
            	<div>
                	<em class="g80">登录帐户</em>
                	<em><input type="text" class="input g300 disabled" value="${smsUser.userName}" disabled /></em>
                </div>
            	<div>
                	<em class="g80">用户全称</em>
                	<em><input type="text" class="input g300" id="nickName" value="${(smsUser.nickName)!''}" /></em>
                </div>
            	<div>
                	<em class="g80">登录密码</em>
                	<em><input type="password" id="password"  class="input g300" /></em>
                </div>
            	<div>
                	<em class="g80">确认密码</em>
                	<em><input type="password" id="password2" class="input g300" /></em>
                </div>
            	<div>
                	<em class="g80">&nbsp;</em>
                	<em>不需修改密码，请留空</em>
                </div>
                <hr />
            	<div>
                	<em class="g80">用户类别</em>
                	<em><select class="g310 disabled" disabled><option><#if smsUser.type?? && smsUser.type==2>代理<#elseif smsUser.type?? && smsUser.type==3>高级<#else>普通</#if></option></select></em>
                </div>
            	<div>
                	<em class="g80">帐户余额</em>
                	<em class="g240">￥${smsUser.balanceFloat} RMB</em>
                    <em><a class="btnRS" href="#">充值</a></em>
                </div>
                <hr />
            	<div>
                	<em class="g80">&nbsp;</em>
                    <em><a class="btnBB" id="userEditEnter" href="javascript:void(0);">确定</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conFooter">
		</div>
    </div>
</div>