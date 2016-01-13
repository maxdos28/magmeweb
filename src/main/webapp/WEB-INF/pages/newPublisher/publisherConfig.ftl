<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/v3/dv/js/publisherConfig.js"></script>
<#import "../dialog/helpComm.ftl" as pc>

<!--body-->
<div class="body" menu="publisher" menuSecond="publisher">
	<!--topBar-->
    <div class="conLeftMiddleRight">
        <div class="conB con10">
        	<div class="head">
		    	 <#if  (session_publisher.logo)?? && session_publisher.logo!="">
		    	   <img width="150" height="150" id="publisherLogo" src="${systemProp.publishProfileServerUrl}${session_publisher.logo172}" />
		    	  <#else>
		    	   <img width="150" height="150" id="publisherLogo" src="${systemProp.staticServerUrl}/v3/images/head150.gif" />
		    	 </#if>        	
            	<a class="btnWS" href="javascript:void(0)"><span>修改头像</span><input id="logoFile" name="logoFile" type="file" class="inputFile" /></a>
            </div>
            <div class="info">
            	<form id="editPublisherForm" method="post" action="" onsubmit="return false;">
	            	<fieldset>
	                	<div class="floatl g350">
	                    	<em class="title">用户名</em>
	                    	<em><input id="userName" name="userName" value="${(session_publisher.userName)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title"><span>*</span>杂志社名称</em>
	                    	<em><input id="publishName" name="publishName" value="${(session_publisher.publishName)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="clear floatl g350">
	                    	<em class="title"><span>*</span>邮箱</em>
	                    	<em><input id="email" name="email" value="${(session_publisher.email)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title">法人</em>
	                    	<em><input id="owner" name="owner" value="${(session_publisher.owner)!""}" type="text" class="input g200" /></em>
	                    </div>
	                    <hr class="clear" />
	                	<div class="floatl g350">
	                    	<em class="title"><span>*</span>联系人1</em>
	                    	<em><input id="contact1" name="contact1" value="${(session_publisher.contact1)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title"><span>*</span>联系人电话1</em>
	                    	<em><input id="contactPhone1" name="contactPhone1" value="${(session_publisher.contactPhone1)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="clear floatl g350">
	                    	<em class="title">联系人2</em>
	                    	<em><input id="contact2" name="contact2" value="${(session_publisher.contact2)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title">联系人电话2</em>
	                    	<em><input id="contactPhone2" name="contactPhone2" value="${(session_publisher.contactPhone2)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="clear floatl g350">
	                    	<em class="title">公司电话</em>
	                    	<em><input id="companyPhone" name="companyPhone" value="${(session_publisher.companyPhone)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title">传真号</em>
	                    	<em><input id="fax" name="fax" value="${(session_publisher.fax)!""}" type="text" class="input g200" /></em>
	                    </div>
	                     <div class="clear floatl g350">
	                    	<em class="title">省份</em>
	                    	<em><select province="${(session_publisher.provinceId)!"不限"}" id="provinceId" name="provinceId">
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
	                    </div>
	                	<div class="floatl g350">
	                    	<em class="title">城市</em>
	                    	<em><select city="${(session_publisher.cityId)!"不限"}" id="cityId" name="cityId" value="">
		                </select></em>
	                    </div>	                    
	                	<div class="clear floatl g350">
	                    	<em class="title">新浪微博账号</em>
	                    	<em><input id="weibouid" name="weiboUid" value="${(session_publisher.weiboUid)!""}" type="text" class="input g200" /></em><em class="icon16question" id="help_publisher" title="点击？查看详细"></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title">新浪微博效验码</em>
	                    	<em><input id="weiboverifier" name="weiboVerifier" value="${(session_publisher.weiboVerifier)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="clear floatl g350">
	                    	<em class="title">网站地址</em>
	                    	<em><input id="webSite" name="webSite" value="${(session_publisher.webSite)!""}" type="text" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title">其他联系方式</em>
	                    	<em>
			                   <select id="normalContactType" name="normalContactType">
			                      <option value="1" <#if session_publisher.normalContactType?? && session_publisher.normalContactType==1> selected="selected" </#if> >QQ</option>
			                      <option value="2"  <#if session_publisher.normalContactType?? && session_publisher.normalContactType==2> selected="selected" </#if> >MSN</option>
			                      <option value="3" <#if !(session_publisher.normalContactType??) || (session_publisher.normalContactType!=2 && session_publisher.normalContactType!=1)> selected="selected"</#if> >其它</option>
			                   </select>	                    	
	                    	</em>
	                    	<em><input id="normalContact" name="normalContact" class="input g130" type="text" value="${(session_publisher.normalContact)!""}"  /></em>
	                    </div>	                    
	                	<div class="clear floatl g750">
	                    	<em class="title">详细地址</em>
	                    	<em><input id="address" name="address" value="${(session_publisher.address)!""}" type="text" class="input g550" /></em>
	                    </div>
	                    <hr class="clear" />
	                	<div>
	                    	<em class="title">原密码</em>
	                    	<em><input id="oldPassword" name="oldPassword" type="password" class="input g200" /></em>
	                    	<em class="tips">如不需要修改密码，请留空</em>
	                    </div>
	                	<div class="floatl g350">
	                    	<em class="title">新密码</em>
	                    	<em><input id="password" name="password" type="password" class="input g200" /></em>
	                    </div>
	                	<div class="floatl">
	                    	<em class="title">新密码确认</em>
	                    	<em><input id="password2" name="password2" type="password" class="input g200" /></em>
	                    </div>
	                	<div class="clear floatl g250">
	                    	<em class="title"></em>
	                    	<em><a id="editPublisherFormSubmit" href="javascript:void(0)" class="btnBB">保存</a></em>
	                    	<em id="tipError" class="tipsError">请填写相关信息</em>
	                    </div>
	                </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<@pc.main />