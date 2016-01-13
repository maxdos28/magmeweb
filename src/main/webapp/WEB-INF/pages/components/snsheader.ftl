<#macro main searchType>
<!--header-->
<div class="header clearFix">
	<div class="outer png">
        <div class="inner png">
            <h1 class="logo" id="logo"><a class="png" href="${systemProp.domain}/" title="无处不悦读">麦米 Magme</a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
                <li class="home"><a href="${systemProp.appServerUrl}/">首页</a></li>
                <li class="magazine"><a href="${systemProp.appServerUrl}/publish/magazine.html">杂志</a></li>
                <li class="kanmi"><a href="${systemProp.appServerUrl}/sns/article.action">M1</a></li>
                <#if dynamicMenu??>
                <li class="publisher"><a href="javascript:void(0)">${dynamicMenu!""}</a></li>
                </#if>
            </ul>
            <!--loginBar-->
            <ul class="conUser" id="loginBar">
                <li class="reg" id="userReg"><a href="javascript:void(0)">注册</a></li>
                <li class="login" id="userLogin"><a href="javascript:void(0)">登录</a></li>
            </ul>
            <!--userBar-->
            <ul class="conUser hide" id="userBar">
                <li class="head"><a href="javascript:void(0)"><img id="avatar30" src="${systemProp.staticServerUrl}/images/head30.gif" /></a></li>
                <li class="name"><a href="${systemProp.appServerUrl}/sns/article.action"><strong id="nickName">Edward</strong></a></li>
                <li class="message"><a href="/sns/message-user.action" id="newMessageNum" title="">消息</a></li>
                <li class="setup"><a href="/sns/info-user.action" title="设置">设置</a></li>
                <li class="logout" id="logout"><a href="javascript:void(0)" title="退出">退出</a></li>
            </ul>
            
          	<div class="search" id="headerSearch">
				<input type="hidden" name="searchType" value="${searchType?default('User')}" />
				<a href="javascript:void(0)">搜索</a>
				<div class="box png"><input class="in" type="text" name="queryStr" <#if queryStr?? && queryStr!="">value="${queryStr}"</#if> /><input class="btn" type="button" value="搜索" /></div>
				<!--  <input type="submit" class="btn" value="" />-->
			</div>
            
            <#if sortList?? && (sortList?size) gt 0>
            <a class="navBtn png" id="navBtn">全部分类</a>
            </#if>
            
        </div>
    </div>
    
    <#if sortList?? && (sortList?size) gt 0>
    <div class="nav png" id="nav">
        <div class="navInner png clearFix">
            <div class="navLeft">
                <ul>
                    <#list sortList as sort>
                    <#if (sort_index%3)=0 >
                    <li>
                    </#if>
                    
                    <a href="javascript:void(0)">${sort.name}</a>
                    
                    <#if ((sort_index+1)%3)=0 || (sort_index+1)=sortList?size>
                    </li>
                    </#if>
                    </#list>
                </ul>
                <div class="arrow"></div>
            </div>
            <div class="navRight">
                <#list sortList as sort>
                <#if (sort_index%3)=0 >
            	<div class="item clearFix">
            	 </#if>  
                    <dl <#if ((sort.publicationList)?size>20)> class="col2" </#if> >
                    	<dt><a sortId="${sort.id}" href="javascript:void(0)">${sort.name}</a></dt>
                        <#list sort.publicationList as pub>
                        <dd><a publicationId="${pub.id}" href="javascript:void(0)">${pub.name}</a></dd>
                        </#list>
                    </dl>
                   
                <#if ((sort_index+1)%3)=0 || (sort_index+1)=sortList?size>
                </div>
                </#if>
                </#list>
                
                <#--
                <dl class="important png">
                    <dt>其它推荐</dt>
                    <dd><a href="javascript:void(0)">Magazine Name</a></dd>
                    <dd><a href="javascript:void(0)">Magazine Name</a></dd>
                    <dd><a href="javascript:void(0)">Magazine Name</a></dd>
                    <dd><a href="javascript:void(0)">Magazine Name</a></dd>
                    <dd><a href="javascript:void(0)">Magazine Name</a></dd>
                    <dd><div class="search"><input type="text" class="in" /><input type="button" class="btn" /></div></dd>
                </dl>-->
            </div>
        </div>
        <a class="close" href="javascript:void(0)">关闭</a>
    </div>
    </#if>
    
    <#if homeSortList?? && (homeSortList?size) gt 0>
    <div class="nav png">
    	<div class="navInner">
            <div class="navRight">
                <ul class="classification item clearFix">
                	<#list homeSortList as sort>
                    <li <#if sortId??&&(sortId==sort.id)>class="current"</#if>><a sortId="${sort.id}" href="javascript:void(0)">${sort.name}</a></li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>  
    </#if>  
</div>

<a id="userFeedBack" href="javascript:void(0)" class="feedbackBtn">用户反馈</a>
<a id="magmeWeibo" href="javascript:void(0)" class="addWeibo">用户反馈</a>

<div id="popLogin" class="popContent">
	<div class="popLeft">
    <form id="popLoginForm" action="" onsubmit="return false;" method="post">
            <fieldset class="new">
                <h6>登&nbsp;&nbsp;录</h6>
                <div class="username">
                    <em><span class="icon"></span><input class="input" id="userName" name="userName" type="text" tipsSpan="用户名/邮箱" /></em>
                </div>
                <div class="password">
                    <em><span class="icon"></span><input class="input" id="password" name="password" type="password" tipsSpan="密码" /></em>
                    <em class="tipsError">密码输错啦！</em>
                    <em class="tipsWrong"></em>
                </div>
                <div>
                	<em class="floatr"><a id="popForgetPassword" href="javascript:void(0)">忘记密码？</a></em>
                    <em class="remanber"><label><input id="remember_login" type="checkbox" checked/>下次自动登录</label></em>
                </div>
                <div>
                    <em class="m0"><a class="btnBB" id="submit" name="signIn" href="#" >登录</a></em>
                </div>
            </fieldset>
    </form>
    </div>
    <div class="popRight">
    	<p>用其他帐号登录</p>
    	<ul class="select clearFix">
    		<li><a href="javascript:void(0)" name="login_qq"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-qq.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_renren"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-renren.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_weibo"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-weibo.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_baidu"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-baidu.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_kaixin"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-kaixin001.gif" /></a></li>
        </ul>
    	<p>没有麦米账户？</p>
        <a id="goRegPop" class="btnWB" href="#" >注册</a>
    </div>
</div>
<div id="popRegister" class="popContent">
	<div class="popLeft">
    <form id="popRegisterForm" action="" onsubmit="return false;" method="post">
            <fieldset class="new">
                <h6>新用户注册</h6>
                <div id="checkUserName">
                    <em><input class="input" id="userName" name="userName" type="text" tipsSpan="用户名" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">用户名已经存在</em>
                </div>
                <div id="checkEmail">
                    <em><input class="input" id="email" name="email" type="text" tipsSpan="邮箱" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">邮箱错误或已注册</em>
                </div>
                <div id="checkPassword">
                    <em><input class="input" id="password" name="password" type="password" tipsSpan="密码" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">密码长度不能小于6位</em>
                </div>
                <div id="checkPassword2">
                    <em><input class="input" id="password2" name="password2" type="password" tipsSpan="确认密码" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">确认密码输错了</em>
                </div>
                <div>
                    <em class="m0"><a class="btnBB" id="submit" name="submit" href="#" >注册</a></em>
                </div>
            </fieldset>
    </form>
    </div>
    <div class="popRight">
    	<p>用其他帐号登录</p>
    	<ul class="select clearFix">
    		<li><a href="javascript:void(0)" name="login_qq"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-qq.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_renren"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-renren.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_weibo"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-weibo.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_baidu"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-baidu.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_kaixin"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-kaixin001.gif" /></a></li>
        </ul>
    	<p>已经有麦米账户？</p>
        <a id="goLoginPop" class="btnWB" href="#" >登录</a>
    </div>
</div>

<div id="sinaWeibo" class="popContent">
	<fieldset class="new">
    <h6>关注新浪微博</h6>
    <div class="link clearFix">
    	<a href="http://www.weibo.com/u/2173428060" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_magme.jpg" /><strong>官方微博</strong><span name="addWeibo_2173428060">+加关注</span></a>
    	<a href="http://www.weibo.com/u/2236683793" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_fashion.jpg" /><strong>时尚频道</strong><span name="addWeibo_2236683793">+加关注</span></a>
    	<a href="http://www.weibo.com/u/2237267865" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_finance.jpg" /><strong>财经频道</strong><span name="addWeibo_2237267865">+加关注</span></a>
    	<a href="http://www.weibo.com/u/2236334264" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_automobile.jpg" /><strong>汽车频道</strong><span name="addWeibo_2236334264">+加关注</span></a>
    	<a href="http://www.weibo.com/u/2236447763" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_entertainment.jpg" /><strong>娱乐频道</strong><span name="addWeibo_2236447763">+加关注</span></a>
    	<a href="http://www.weibo.com/u/2237226891" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_technology.jpg" /><strong>科技频道</strong><span name="addWeibo_2237226891">+加关注</span></a>
    	<a href="http://www.weibo.com/u/2236538607" target="_blank"><img src="${systemProp.staticServerUrl}/v3/images/icon/weibo/logo_travel.jpg" /><strong>旅游频道</strong><span name="addWeibo_2236538607">+加关注</span></a>
    </div>
    </fieldset>
</div>
</#macro>