<#macro main>
	<div id="editAdAgencyDialog" class="popContent popRegister">
    	<fieldset>
        	<h6>编辑账户信息</h6>
        	<form id="editAdAgencyForm" method="post" action="" onsubmit="return false;">
            <div>
            	<em class="title">用户名</em>
            	<em class="g200"><input id="userName" name="userName" value="${(session_adagency.userName)!""}" class="input g150" type="text" disabled/></em>
            	<em class="title">公司名</em>
            	<em><input id="companyName" name="companyName" value="${(session_adagency.companyName)!""}" class="input g150" type="text" disabled/></em>
            </div>
            <div>
            	<em class="title">邮箱*</em>
            	<em class="g200"><input id="email" name="email" value="${(session_adagency.email)!""}" class="input g150" type="text" /></em>
            </div>
            <hr />
            <div>
            	<em class="title">联系人</em>
            	<em class="g200"><input id="contact" name="contact" value="${(session_adagency.contact)!""}" class="input g150" type="text"/></em>
            	<em class="title">联系人电话</em>
            	<em><input id="contactPhone" name="contactPhone" value="${(session_adagency.contactPhone)!""}" class="input g150" type="text"/></em>
            </div>
            <div>
            	<em class="title">公司电话</em>
            	<em class="g200"><input id="companyPhone" name="companyPhone" value="${(session_adagency.companyPhone)!""}" class="input g150" type="text"/></em>
            	<em class="title">传真号</em>
            	<em><input id="fax" name="fax" value="${(session_adagency.fax)!""}" class="input g150" type="text"/></em>
            </div>
          
            <div>
            	<em class="title">网站地址</em>
            	<em class="g200"><input id="webSite" name="webSite" value="${(session_adagency.webSite)!""}" class="input g150" type="text" /></em>
            	<em class="title">常用联系方式*</em>
                <em>
                   <select class="g70" id="contactType" name="contactType">
                      <option value="1" <#if session_adagency.contactType?? && session_adagency.contactType=="1"> selected="selected" </#if> >QQ</option>
                      <option value="2"  <#if session_adagency.contactType?? && session_adagency.contactType=="2"> selected="selected" </#if> >MSN</option>
                      <option value="3" <#if !(session_adagency.contactType??) || (session_adagency.contactType!="2" && session_adagency.contactType!="1")> selected="selected"</#if> >其它</option>
                   </select>
                </em>
            	<em><input id="contactNumber" name="contactNumber" class="input g80" type="text" value="${(session_adagency.contactNumber)!""}"  /></em>
            </div>
            
            <div>
            <em class="title">街道地址</em>
            	<em><input id="address" name="address" value="${(session_adagency.address)!""}" class="input g430" type="text" /></em>
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