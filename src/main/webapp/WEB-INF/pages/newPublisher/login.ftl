<script src="${systemProp.staticServerUrl}/v3/dv/js/login.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/register.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<#if message??>
<script>
	alert('${message}');
</script>
</#if>

<div class="conLogin" id="conLogin">
    <h1 class="logo"><a class="png" href="/new-publisher/login!to.action">麦米 Magme</a></h1>
    <fieldset>
    <div id="publisherLoginForm">
    	<h6>出版商管理登录</h6>
        <div>
            <em class="g70">&nbsp;</em>
            <em><input type="text" name="userName" tips="用户名 / Email" class="input g250"  color="#999,#fff" /></em>
        </div>
        <div>
            <em class="g70">&nbsp;</em>
            <em><input type="text"  tips="密码" class="input g250" color="#999,#fff" /><input name="password" type="password" class="input g250 hide" color="#999,#fff" /></em>
        </div>
        <div class="action">
            <em class="g50">&nbsp;</em>
            <em><a href="javascript:void(0)" id="publisherLoginBtn" class="btnGB">登录</a></em>
            <em><a href="javascript:void(0)" class="btnWB">注册</a></em>
        </div>
     </div>
    </fieldset>
</div>





<div class="conReg" id="conReg">
    <h1 class="logo"><a class="png" href="/new-publisher/login!to.action">麦米 Magme</a></h1>
<form id="publisherRegisterForm" methdo="post" action="javascript:void(0)">
    <fieldset class="new">
    	<h6>出版商注册</h6>
        <div>
            <em class="g120 tRight">用户名：</em>
            <em><input name="userName" type="text" class="input g200" /></em>
        </div>
        <div>
            <em class="g120 tRight">杂志社名称：</em>
            <em><input name="publishName" type="text" class="input g200" /></em>
        </div>
        <div>
            <em class="g120 tRight">邮箱：</em>
            <em><input name="email" type="text" class="input g200" /></em>
        </div>
         <div>
            <em class="g120 tRight">联系人：</em>
            <em><input name="contact1" type="contact1" class="input g200" /></em>
        </div>
        <div>
            <em class="g120 tRight">联系电话：</em>
            <em><input name="contactPhone1" type="contactPhone1" class="input g200" /></em>
        </div>
        <div>
            <em class="g120 tRight">密码：</em>
            <em><input name="password" type="password" class="input g200" /></em>
        </div>
        <div>
            <em class="g120 tRight">确认密码：</em>
            <em><input name="password2" type="password" class="input g200" /></em>
        </div>
         <div>
            <em class="g120 tRight">验证码：</em>
            <em><input name="authcode" type="text" class="input g60" /></em>
            <em id="authcode" class="code"><img class="code" style="background:#ccc;" src="${systemProp.staticServerUrl}/images/code.gif" /><a name="getPublisherAuthcode" href="javascript:void(0)">看不清？换一个</a></em>
        </div>
         <div>
            <em class="g120">&nbsp;</em>
            <em><label><input type="checkbox" id="publishAgreement" /><span>我已阅读并接受</span><a class="important" onclick="window.open('/publisherAgreement.html','','width=800,height=500')">《居冠软件用户协议》</a></label></em>
            <em></em>
        </div>
         <div class="action">
            <em class="g60">&nbsp;</em>
            <em><a href="javascript:void(0);" id="publisherRegisterBtn" class="btnGB" >注册</a></em>
            <em><a href="javascript:void(0);" class="btnWB" >登录</a></em>
        </div>
    </fieldset>
</form>   
</div>
<div class="copyright">Copyright © 2005-2012 <strong><span>麦米网</span> (www.magme.com)</strong> 沪ICP证：沪B2-20120009 </div>
