<script src="${systemProp.staticServerUrl}/v3/dv/golf/login.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<#if message??>
<script>
	alert('${message}');
</script>
</#if>

<div class="conLogin" id="conLogin">
    <h1 class="logo"><a class="png" href="/golf/login!to.action">新闻后台</a></h1>
    <fieldset>
    <div id="golfLoginForm">
    	<h6>高尔夫新闻后台管理登录</h6>
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
            <em><a href="javascript:void(0)" id="golfLoginBtn" class="btnGB">登录</a></em>
        </div>
     </div>
    </fieldset>
</div>

<div class="copyright">Copyright © 2005-2013 <strong><span>麦米网</span> (www.magme.com)</strong> 沪ICP证：沪B2-20120009 </div>
