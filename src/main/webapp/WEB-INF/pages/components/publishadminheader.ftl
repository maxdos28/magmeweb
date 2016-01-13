<!--header-->
<#macro main menuId="publish">
<div class="header">
	<div class="inner">
        <!--logo-->
        <h1 class="logo"><a href="javascript:void(0)">麦米-Magme</a></h1>
        <!--nav-->
        <ul class="nav">
            <li <#if menuId=="index"> class="current"</#if> ><a href="http://www.magme.com/publish/pcenter-publisher!index.action">首页</a></li>
            <li <#if menuId=="product"> class="current"</#if> ><a href="http://www.magme.com/publish/pcenter-publisher!product.action">产品与服务</a></li>
            <#if session_publisher??>
            	<li <#if menuId=="publish">class="current"</#if> ><a id="publisherCenter" href="http://www.magme.com/publish/pcenter-publisher.action">杂志商中心</a></li>
            </#if>
            <!--${systemProp.appServerUrl}/publish/pcenter-publisher.action-->
            <#if session_aduser??>
            	<li <#if menuId="advertise">class="current"</#if> ><a id="advertiserCenter" href="http://www.magme.com/ad/adcenter-home.action">广告商中心</a></li>
            </#if>
            <!--${systemProp.appServerUrl}/ad/adcenter-home.action-->
        </ul>
        <!--loginBar-->
        <ul id="publisherBar" class="userBar" <#if session_aduser??>style="display:none;"</#if> >
            <li class="reg"><a class="sub noSub" href="http://www.magme.com/publish/front-publisher.action">注册</a>
            </li>
            <li class="login"><a class="sub" href="javascript:void(0)"  id="adloginBtn">登录</a>
            	<div class="box" id="publisherLogin">
                	<sub></sub>
                	<div class="con">
                        <fieldset class="png">
                            <div class="input">
                                <em><input class="in" id="userName" name="userName" type="text" tipsSpan="用户名/邮箱" /></em>
                            </div>
                            <div class="input">
                                <em><input class="in" id="password" name="password" type="password" tipsSpan="密码" /></em>
                            </div>
                            <div>
                                <em>登录身份：</em>
                                <em><label><input type="radio" name="loginType" checked="checked" value="1"/>出版商</label></em>
                                <em><label><input type="radio" name="loginType"  value="2"/>广告商</label></em>
                                <em><label><input type="radio" name="loginType"  value="3"/>麦米</label></em>
                            </div>
                            <div code="getcode">
                                <em class="m0"><input id="authcode" code="getcode" class="input g60" name="authcode" type="text" tipsSpan="验证码" /></em>
                                <em class="code"><a name="getPublisherAuthcode" href="javascript:void(0)" code="loginCode"><img code="getcode" id="reqisterCode" src="${systemProp.staticServerUrl}/images/code.gif" class="code" style="background:#ccc;"/>看不清？换一个</a></em>
                            </div>
                            <div>
                                <em class="m0"><a class="btnOS" id="publisherSignIn" name="signIn" href="javascript:void(0)" >登录</a></em>
                                <em class="loading"></em>
                                <em class="tipsError">请填写相关信息</em>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </li>
        </ul>
        <!--loginBar-->
        <ul class="loginBar" <#if !(session_aduser??)>style="display:none;"</#if> >
        	<li>
        	   <a href="javascript:void(0)">
        	     <#if session_aduser?? && session_aduser.logo?? && session_aduser.logo!="" && session_aduser.level==1>
		    	   <img id="publisherMiniLogo" src="${systemProp.publishProfileServerUrl}${avatarResize(session_aduser.logo,'30')}" />
		    	  <#elseif session_aduser?? && session_aduser.logo?? && session_aduser.logo!="" && session_aduser.level==2 >
		    	   <img id="publisherMiniLogo" src="${systemProp.adProfileServerUrl}${avatarResize(session_aduser.logo,'30')}" />
		    	  <#else>
		    	   <img id="publisherMiniLogo" src="${systemProp.staticServerUrl}/images/head30.gif" />
		    	 </#if>
        	   </a>
        	</li>
        	<li class="user">
        	     <strong><#if session_aduser??>${session_aduser.name}</#if></strong>
        	</li>
        	<li class="logout"><a href="javascript:void(0)" id="adlogout">退出</a></li>
        </ul>

    </div>
</div>
</#macro>