<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>

<script src="${systemProp.staticServerUrl}/v3/dv/js/register.js"></script>
<div class="conReg" id="conReg">
    <h1 class="logo"><a class="png" href="/new-publisher/login!to.action">(SEO)麦米 Magme</a></h1>
<form id="publisherRegisterForm" methdo="post" action="javascript:void(0)">
    <fieldset class="new">
    	<h6>出版商后台管理</h6>
        <div>
            <em class="title">用户名：</em>
            <em><input name="userName" type="text" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
        <div>
            <em class="title">杂志社名称：</em>
            <em><input name="publishName" type="text" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
        <div>
            <em class="title">邮箱：</em>
            <em><input name="email" type="text" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
         <div>
            <em class="title">联系人：</em>
            <em><input name="contact1" type="contact1" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
        <div>
            <em class="title">联系电话：</em>
            <em><input name="contactPhone1" type="contactPhone1" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
        <div>
            <em class="title">密码：</em>
            <em><input name="password" type="password" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
        <div>
            <em class="title">确认密码：</em>
            <em><input name="password2" type="password" class="input g200" /></em>
            <em class="tipsError"></em>
        </div>
         <div>
            <em class="title">验证码：</em>
            <em><input name="authcode" type="text" class="input g60" /></em>
            <em id="authcode" class="code"><img class="code" style="background:#ccc;" src="${systemProp.staticServerUrl}/images/code.gif" /><a name="getPublisherAuthcode" href="javascript:void(0)">看不清？换一个</a></em>
            <em class="tipsError"></em>
        </div>
        <div>
            <em class="title"></em>
            <em><label><input type="checkbox" id="publisherAgreement" /><span>我已阅读并接受</span><a class="important" onclick="window.open('/publisherAgreement.html','','width=800,height=500')">《居冠软件用户协议》</a></label></em>
            <em class="tipsError"></em>
        </div>
        <div class="action">
            <em class="title">&nbsp;</em>
            <em><a href="javascript:void(0)" id="publisherRegisterBtn" class="btnBB" >注册</a></em>
            <em><a href="/new-publisher/login!to.action" class="btnWB" >登录</a></em>
            <em id="tipsError" class="tipsError"></em>
        </div>
    </fieldset>
</form>   
</div>
<div class="copyright">Copyright © 2005-2012 <strong><span>麦米网</span> (www.magme.com)</strong> 沪ICP证：沪B2-20120009 </div>