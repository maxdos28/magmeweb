<#macro main class>


<#import "headerInFooter.ftl" as header>
<@header.main searchType="Issue"/>
<div class="footer20150126">
    <div class="inner clearFix">
    	<div class="clearFix">
            <div class="quickLink">
                <h6>快速导航</h6>
                <ul class="clearFix">
                	<li><a href="/static_join_us.html" rel="nofollow" >加入我们</a></li>
                	<li><a href="/static_friendlink.html">友情链接</a></li>
                	<li><a href="/static_policy.html" rel="nofollow" >法律声明</a></li>
                	<li><a id="userFeedBack" href="javascript:void(0);" rel="nofollow" >意见反馈</a></li>
                	<li><a href="http://interactive.magme.com">广告合作</a></li>
                </ul>
            </div>
            <div class="contact">
                <h6>联系我们</h6>
                <p>地址：上海市徐汇区虹桥路777号汇京国际广场807室</p>
                <p>电话：+86 021 62116886</p>
            </div>
            <div class="sns">
                <ul class="clearFix">
                	<li class="sina"><a href="http://e.weibo.com/magme" target="_blank"><span class="png"></span>新浪微博</a></li>
                	<li class="qq"><a href="http://e.t.qq.com/magme2011" target="_blank"><span class="png"></span>腾讯微博</a></li>
                	<li class="weixin"><a href="http://e.t.qq.com/magme2011" target="_blank"><span class="png"></span>官方微信</a></li>
                </ul>
            </div>
        </div>
        <#if class=="index">
				<#if footerLink?? && footerLink?size gt 0>
	            <div class="clear footerLink">
	            	<dl class="clearFix">
	                	<dt><a href="javascript:void(0);">友情链接</a></dt>
	                	<#if footerLink.content??>
	                		${footerLink.content}
	                	</#if>
	                </dl>
	            </div>
	            </#if>
	            <#if footerPubLink?? && footerPubLink?size gt 0 >
	                		<#if footerPubLink.content??&& footerPubLink.content?trim?length gt 0>
	            <div class="clear footerLink">
	            	<dl class="clearFix">
	                	<dt><a href="javascript:void(0);">友情链接</a></dt>
	                			${footerPubLink.content}
	                </dl>
	            </div>
	                		</#if>
	            </#if>
	            
            </#if>
        
        <p class="copyright">Copyright © 2005-2013 <strong><span>麦米购</span>&nbsp;www.magme.com&nbsp;</strong>沪ICP备15001756号-1</p>
        <div class="icon">
        	<a class="shgs" href="http://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=20121113150613536" target="_blank">上海工商</a>
        	<a class="wlshzx" href="http://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=20121113150613536" target="_blank">风格社会征信</a>
        	<a class="shwj110" href="http://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=20121113150613536" target="_blank">上海网警110</a>
        </div>
        
    </div>
</div>
<#import "./gap.ftl" as gap>
<@gap.main />
</#macro>

<#macro oldmain class>
<div class="footer clearFix" id="footer">
    <p>Copyright &copy; 2012 <span class="png">麦米</span>版权所有 | 沪ICP备 <a href="http://www.miibeian.gov.cn" target="_blank">10217320号-2</a></p>
    <#if class=="index">
    <div class="icon">
        	<a class="shgs" href="http://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=20121113150613536" target="_blank">上海工商</a>
        	<a class="wlshzx" href="http://www.sinsaa.org.cn/" target="_blank">风格社会征信</a>
        	<a class="shwj110" href="http://sh.cyberpolice.cn/infoCategoryListAction.do?act=initjpg" target="_blank">上海网警110</a>
    </div>
     </#if>
    <div class="link"><a href="${systemProp.appServerUrl}/static_about_us.html">公司信息</a><a href="${systemProp.appServerUrl}/static_friendlink.html">友情链接</a></div>
</div>
<#import "./gap.ftl" as gap>
<@gap.main />
</#macro>
