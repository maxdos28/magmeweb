<#macro main>
	<div id="editPublisherDialog" class="popContent popRegister">
    	<fieldset>
        	<h6>编辑账户信息</h6>
        	<form id="editPublisherForm" method="post" action="" onsubmit="return false;">
            <div>
            	<em class="title">用户名</em>
            	<em class="g200"><input id="userName" name="userName" value="${(session_publisher.userName)!""}" class="input g150" type="text" disabled/></em>
            	<em class="title">杂志社名称*</em>
            	<em><input id="publishName" name="publishName" value="${(session_publisher.publishName)!""}" class="input g150" type="text" disabled/></em>
            </div>
            <div>
            	<em class="title">邮箱*</em>
            	<em class="g200"><input id="email" name="email" value="${(session_publisher.email)!""}" class="input g150" type="text" /></em>
            	<em class="title">法人</em>
            	<em><input id="owner" name="owner" value="${(session_publisher.owner)!""}" class="input g150" type="text" /></em>
            </div>
            <hr />
            <div>
            	<em class="title">联系人1*</em>
            	<em class="g200"><input id="contact1" name="contact1" value="${(session_publisher.contact1)!""}" class="input g150" type="text"/></em>
            	<em class="title">联系人电话1*</em>
            	<em><input id="contactPhone1" name="contactPhone1" value="${(session_publisher.contactPhone1)!""}" class="input g150" type="text"/></em>
            </div>
            <div>
            	<em class="title">联系人2</em>
            	<em class="g200"><input id="contact2" name="contact2" value="${(session_publisher.contact2)!""}" class="input g150" type="text"/></em>
            	<em class="title">联系人电话2</em>
            	<em><input id="contactPhone2" name="contactPhone2" value="${(session_publisher.contactPhone2)!""}" class="input g150" type="text"/></em>
            </div>
            <div>
            	<em class="title">公司电话</em>
            	<em class="g200"><input id="companyPhone" name="companyPhone" value="${(session_publisher.companyPhone)!""}" class="input g150" type="text"/></em>
            	<em class="title">传真号</em>
            	<em><input id="fax" name="fax" value="${(session_publisher.fax)!""}" class="input g150" type="text"/></em>
            </div>
            <div>
            	<em class="title">省份</em>
            	<em class="g200"><input id="provinceId" name="provinceId" value="${(session_publisher.provinceId)!""}" class="input g150" type="text" /></em>
            	<em class="title">城市</em>
            	<em><input id="cityId" name="cityId" value="${(session_publisher.cityId)!""}" class="input g150" type="text"/></em>
            </div>
            
            <div>
            	<em class="title">weibouid</em>
            	<em class="g200"><input id="weibouid" name="weiboUid" value="${(session_publisher.weiboUid)!""}" class="input g150" type="text" /></em>
            	<em class="title">weiboverifier</em>
            	<em><input id="weiboverifier" name="weiboVerifier" value="${(session_publisher.weiboVerifier)!""}" class="input g150" type="text"/></em>
            </div>
            
            <div>
            	<em class="title">网站地址</em>
            	<em class="g200"><input id="webSite" name="webSite" value="${(session_publisher.webSite)!""}" class="input g150" type="text" /></em>
            	<em class="title">常用联系方式*</em>
                <em>
                   <select class="g70" id="normalContactType" name="normalContactType">
                      <option value="1" <#if session_publisher.normalContactType?? && session_publisher.normalContactType==1> selected="selected" </#if> >QQ</option>
                      <option value="2"  <#if session_publisher.normalContactType?? && session_publisher.normalContactType==2> selected="selected" </#if> >MSN</option>
                      <option value="3" <#if !(session_publisher.normalContactType??) || (session_publisher.normalContactType!=2 && session_publisher.normalContactType!=1)> selected="selected"</#if> >其它</option>
                   </select>
                </em>
            	<em><input id="normalContact" name="normalContact" class="input g80" type="text" value="${(session_publisher.normalContact)!""}"  /></em>
            </div>
            <div>
            <em class="title">街道地址</em>
            	<em><input id="address" name="address" value="${(session_publisher.address)!""}" class="input g430" type="text" /></em>
            </div>
            <hr />
            <div class="important">
            	<em class="title">原密码</em>
            	<em><input id="oldPassword" name="oldPassword" class="input g150" type="password" /></em>
            	<em class="tips">不需要修改密码，请留空</em>
            </div>
            <div class="important">
            	<em class="title">新密码</em>
            	<em class="g200"><input id="password" name="password" class="input g150" type="password"/></em>
            	<em class="title">确认密码</em>
            	<em><input id="password2" name="password2" class="input g150" type="password"/></em>
            </div>
            <div>
            	<em class="title"></em>
                <em ><a id="editPublisherFormSubmit" href="#" class="btnBS" >确定</a></em>
                <em ><a id="cancel" href="#" class="btnWS" >取消</a></em>
                <em id ="tipError" class="tipsError">请填写相关信息</em>
            </div>
            </form>
        </fieldset>
    </div>
</#macro>