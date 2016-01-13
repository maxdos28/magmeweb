<#macro main>
	<div id="userInfoDialog" class="popContent popRegister">
    	<fieldset>
        	<h6>编辑账号</h6>
        	<form id="editForm" method="post" action="" onsubmit="return false;">
            <div class="important">
            	<em class="title">原密码</em>
            	<em><input id="oldPassword" name="oldPassword" class="input g130" type="password"></em>
            	<em class="tips">不需要修改密码，请留空</em>
            </div>
            <div class="important">
            	<em class="title">新密码</em>
            	<em class="g180"><input id="password" name="password" class="input g130" type="password"></em>
            	<em class="title">确认密码</em>
            	<em><input id="password2" name="password2" class="input g130" type="password"></em>
            </div>
            <hr />
            <div>
            	<em class="title">用户名</em>
            	<em class="g180"><input id="userName" name="userName" value="${session_user.userName}" class="input g130" type="text" disabled=""></em>
            	<em class="title">昵称</em>
            	<em><input id="nickName" name="nickName" value="${session_user.nickName}" class="input g130" type="text"></em>
            </div>
            <div>
            	<em class="title">邮箱</em>
            	<em class="g180"><input id="email" name="email" value="${session_user.email}" class="input g130" type="text"></em>
            	<em class="title">联系电话</em>
            	<em><input id="phone" name="phone" value="${(session_user.phone)!""}" class="input g130" type="text"></em>
            </div>
            <div>
            	<em class="title">性别</em>
            	<em class="g40"><label><input type="radio" name="gender" value="1" <#if session_user.gender==1>checked="true"</#if>>男</label></em>
            	<em class="g40"><label><input type="radio" name="gender" value="2" <#if session_user.gender==2>checked="true"</#if>>女</label></em>
            	<em class="g90"><label><input type="radio" name="gender" value="0" <#if !(session_user.gender??)||(session_user.gender==0)>checked="true"</#if>>保密</label></em>
            	<em class="title">生日</em>
            	<em><input id="birthdate" name="birthdate" value="${(session_user.birthdate?string("yyyy-MM-dd"))!""}" class="input g130" type="text"></em>
            	<em>(1985-01-11)</em>
            </div>
            <hr />
            <div>
            	<em class="title">职业</em>
            	<em class="g180"><input id="occupation" name="occupation" value="${(session_user.occupation)!""}" class="input g130" type="text"></em>
            	<em class="title">教育水平</em>
            	<em>
	            	<select id="education" name="education" value="${(session_user.education)!""}" class="g90" >
	                	<option value="" <#if !((session_user.education)??)||(session_user.education=="")>selected</#if>></option>
	                	<option value="博士" <#if ((session_user.education)??)&&(session_user.education=="博士")>selected</#if>>博士</option>
	                	<option value="研究生" <#if ((session_user.education)??)&&(session_user.education=="研究生")>selected</#if>>研究生</option>
	                	<option value="本科" <#if ((session_user.education)??)&&(session_user.education=="本科")>selected</#if>>本科</option>
	                	<option value="专科/技校" <#if ((session_user.education)??)&&(session_user.education=="专科/技校")>selected</#if>>专科/技校</option>
	                	<option value="高中" <#if ((session_user.education)??)&&(session_user.education=="高中")>selected</#if>>高中</option>
	                	<option value="other" <#if ((session_user.education)??)&&(session_user.education=="other")>selected</#if>>其他</option>
	                </select>
                </em>
            </div>
            <div>
            	<em class="title">星座</em>
            	<em class="g180">
            		<select id="astro" name="astro" class="g90">
	                	<option value="0" <#if !((session_user.astro)??)||(session_user.astro==0)>selected</#if>></option>
	                	<option value="1" <#if ((session_user.astro)??)&&(session_user.astro==1)>selected</#if>>水瓶座</option>
	                	<option value="2" <#if ((session_user.astro)??)&&(session_user.astro==2)>selected</#if>>双鱼座</option>
	                	<option value="3" <#if ((session_user.astro)??)&&(session_user.astro==3)>selected</#if>>白羊座</option>
	                	<option value="4" <#if ((session_user.astro)??)&&(session_user.astro==4)>selected</#if>>金牛座</option>
	                	<option value="5" <#if ((session_user.astro)??)&&(session_user.astro==5)>selected</#if>>双子座</option>
	                	<option value="6" <#if ((session_user.astro)??)&&(session_user.astro==6)>selected</#if>>巨蟹座</option>
	                	<option value="7" <#if ((session_user.astro)??)&&(session_user.astro==7)>selected</#if>>狮子座</option>
	                	<option value="8" <#if ((session_user.astro)??)&&(session_user.astro==8)>selected</#if>>处女座</option>
	                	<option value="9" <#if ((session_user.astro)??)&&(session_user.astro==9)>selected</#if>>天枰座</option>
	                	<option value="10" <#if ((session_user.astro)??)&&(session_user.astro==10)>selected</#if>>天蝎座</option>
	                	<option value="11" <#if ((session_user.astro)??)&&(session_user.astro==11)>selected</#if>>射手座</option>
	                	<option value="12" <#if ((session_user.astro)??)&&(session_user.astro==12)>selected</#if>>摩羯座</option>
	                </select>
                </em>
            	<em class="title">血型</em>
            	<em>
            		<select id="bloodType" name="bloodType" class="g90" >
            			<option value="0" <#if !((session_user.bloodType)??)||(session_user.bloodType==0)>selected</#if>></option>
            			<option value="1" <#if ((session_user.bloodType)??)&&(session_user.bloodType==1)>selected</#if>>A型</option>
	                	<option value="2" <#if ((session_user.bloodType)??)&&(session_user.bloodType==2)>selected</#if>>B型</option>
	                	<option value="3" <#if ((session_user.bloodType)??)&&(session_user.bloodType==3)>selected</#if>>O型</option>
	                	<option value="4" <#if ((session_user.bloodType)??)&&(session_user.bloodType==4)>selected</#if>>AB型</option>
	                	<option value="5" <#if ((session_user.bloodType)??)&&(session_user.bloodType==5)>selected</#if>>其他</option>
            		</select>
            	</em>
            </div>
            <div>
            	<em class="title">爱好</em>
            	<em><input id="hobbies" name="hobbies" rows="4" class="input g380" value="${(session_user.hobbies)!""}" type="text"></em>
            </div>
            <hr />
            <div>
            	<em class="title">省份</em>
            	<em><select province="${(session_user.province)!"不限"}" id="province" name="province">
            		<option value="北京">北京</option>
                	<option value="广东">广东</option>
                	<option value="上海">上海</option>
                	<option value="天津">天津</option>
                	<option value="重庆">重庆</option>
                	<option value="江苏">江苏</option>
                	<option value="浙江">浙江</option>
                	<option value="辽宁">辽宁</option>
                	<option value="湖北">湖北</option>
                	<option value="四川">四川</option>
                	<option value="陕西">陕西</option>
                	<option value="河北">河北</option>
                	<option value="山西">山西</option>
                	<option value="河南">河南</option>
                	<option value="吉林">吉林</option>
                	<option value="黑龙江">黑龙江</option>
                	<option value="内蒙古">内蒙古</option>
                	<option value="山东">山东</option>
                	<option value="安徽">安徽</option>
                	<option value="福建">福建</option>
                	<option value="湖南">湖南</option>
                	<option value="广西">广西</option>
                	<option value="江西">江西</option>
                	<option value="贵州">贵州</option>
                	<option value="云南">云南</option>
                	<option value="西藏">西藏</option>
                	<option value="海南">海南</option>
                	<option value="甘肃">甘肃</option>
                	<option value="宁夏">宁夏</option>
                	<option value="青海">青海</option>
                	<option value="新疆">新疆</option>
                	<option value="香港">香港</option>
                	<option value="澳门">澳门</option>
                	<option value="台湾">台湾</option>
                	<option value="海外">海外</option>
                	<option value="其他">其他</option>
                       
                	
                </select>
                </em>
            	<em>城市</em>
            	<em><select city="${(session_user.city)!"不限"}" id="city" name="city" value="">
                	<option value="不限">请选择城市</option>
                </select></em>
            </div>
            <div>
            	<em class="title">地址</em>
            	<em><input id="address" name="address" value="${(session_user.address)!""}" class="input g380" type="text"></em>
            </div>
            <div>
            	<em class="title"></em>
                <em><a id="submit" href="#" class="btnBS">确定</a></em>
                <em><a id="cancel" href="#" class="btnWS">取消</a></em>
                <em id="tipError" class="tipsError">请填写相关信息</em>
            </div>
            </form>
        </fieldset>
	</div>
	
</#macro>