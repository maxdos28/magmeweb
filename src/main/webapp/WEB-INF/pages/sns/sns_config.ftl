<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />


<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.smartSearch.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imgareaselect.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.floatDiv.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1_1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>

<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/sns/sns_config.js"></script>
<script>
$(function(){
});
</script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<!--header-->

<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>


        	

<!--body-->
<div class="body pageM1 clearFix">
	<div class="sideLeftMiddle">
        <!--conMyTag-->
        <div class="conM1Config">
        	<h2 class="bigTitle">账户设置</h2>
        	
        	
            <fieldset class="new">
                <form id="editUserInfoForm" method="post" action="" onsubmit="return false;">
                <div class="important">
                    <em class="title">原密码</em>
                    <em><input id="oldPassword" name="oldPassword" class="input g150" type="password"></em>
                    <em class="tips">不需要修改密码，请留空</em>
                </div>
                <div class="important">
                    <em class="title">新密码</em>
                    <em class="g220"><input id="password" name="password" class="input g150" type="password"></em>
                    <em class="title">确认密码</em>
                    <em><input id="password2" name="password2" class="input g150" type="password"></em>
                </div>
                <hr />
                <div>
                    <em class="title">用户名</em>
                    <em class="g220"><input id="userName" name="userName" value="${session_user.userName}" class="input g150" type="text" disabled=""></em>
                    <em class="title">昵称</em>
                    <em><input id="nickName" name="nickName" value="${session_user.nickName}" class="input g150" type="text"></em>
                </div>
                <div>
                	<em class="title">真实姓名</em>
                    <em class="g220"><input id="reserve3" name="reserve3" value="${session_user.reserve3!}" class="input g150" type="text" ></em>
                    <em class="title">联系电话</em>
                    <em><input id="phone" name="phone" value="${(session_user.phone)!""}" class="input g150" type="text"></em>
                </div>
                <div>
                    <em class="title">邮箱</em>
                    <em class="g220"><input id="email" name="email" value="${session_user.email}" class="input g150" type="text"></em>
                    <em class="title">生日</em>
                    <em><input id="birthdate" name="birthdate" value="${(session_user.birthdate?string("yyyy-MM-dd"))!""}" class="input g150" type="text"></em>
                </div>
                <div>
                    <em class="title">性别</em>
                    <em class="g40"><label><input type="radio" name="gender" value="1" checked="true">男</label></em>
                    <em class="g40"><label><input type="radio" name="gender" value="2">女</label></em>
                    <em class="g130"><label><input type="radio" name="gender" value="0">保密</label></em>
                </div>
                <hr />
                <div>
                    <em class="title">职业</em>
                    <em class="g220"><input id="occupation" name="occupation" value="${(session_user.occupation)!""}" class="input g150" type="text"></em>
                </div>
                <div>
                    <em class="title">教育水平</em>
                    <em><select id="education" name="education" value="${(session_user.education)!""}" class="g70 inputFile" >
						<option value="" <#if !((session_user.education)??)||(session_user.education=="")>selected</#if>>请选择</option>
	                	<option value="博士" <#if ((session_user.education)??)&&(session_user.education=="博士")>selected</#if>>博士</option>
	                	<option value="研究生" <#if ((session_user.education)??)&&(session_user.education=="研究生")>selected</#if>>研究生</option>
	                	<option value="本科" <#if ((session_user.education)??)&&(session_user.education=="本科")>selected</#if>>本科</option>
	                	<option value="专科/技校" <#if ((session_user.education)??)&&(session_user.education=="专科/技校")>selected</#if>>专科/技校</option>
	                	<option value="高中" <#if ((session_user.education)??)&&(session_user.education=="高中")>selected</#if>>高中</option>
	                	<option value="other" <#if ((session_user.education)??)&&(session_user.education=="other")>selected</#if>>其他</option>
                    </select></em>
                </div>
                <div>
                    <em class="title">星座</em>
                    <em class="g220"><select id="astro" name="astro" class="g90" >
	                	<option value="0" <#if !((session_user.astro)??)||(session_user.astro==0)>selected</#if>>请选择</option>
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
                </div>
                <div>
                    <em class="title">血型</em>
                    <em class="g220"><select id="bloodType" name="bloodType" class="g90" >
            			<option value="0" <#if !((session_user.bloodType)??)||(session_user.bloodType==0)>selected</#if>>请选择</option>
            			<option value="1" <#if ((session_user.bloodType)??)&&(session_user.bloodType==1)>selected</#if>>A型</option>
	                	<option value="2" <#if ((session_user.bloodType)??)&&(session_user.bloodType==2)>selected</#if>>B型</option>
	                	<option value="3" <#if ((session_user.bloodType)??)&&(session_user.bloodType==3)>selected</#if>>O型</option>
	                	<option value="4" <#if ((session_user.bloodType)??)&&(session_user.bloodType==4)>selected</#if>>AB型</option>
	                	<option value="5" <#if ((session_user.bloodType)??)&&(session_user.bloodType==5)>selected</#if>>其他</option>                    </select>
                    </em>                    
                </div>
                <div>
                    <em class="title">爱好</em>
                    <em><input id="hobbies" name="hobbies" class="input g520" value="${(session_user.hobbies)!""}" type="text"></em>
                </div>
                <div>
                    <em class="title">简介</em>
                    <em><textarea id="reserve2" name="reserve2" class="input g510">${(session_user.reserve2)!""}</textarea></em>
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
                    <em class="title auto">城市</em>
                    <em><select city="${(session_user.city)!"不限"}" id="city" name="city" value="">
		                </select>
		            </em>
                </div>
                <div>
                    <em class="title">地址</em>
                    <em><input id="address" name="address" value="${(session_user.address)!""}" class="input g520" type="text"></em>
                </div>
                <div>
                    <em class="title"></em>
                    <em><a id="submitEditUserInfoForm" href="javascript:void(0)" class="btnBB">保存</a></em>
                    <em>
	                    <#if (userEx??)>
                    		<#if (userEx.audit == 0)>已申请，待审核
	                    		<#elseif (userEx.audit == 1)>已认证通过
	                    		<#elseif (userEx.audit == 2)>审核未通过
                    		</#if>
	                    <#else>
			                <a id="applyMCertification" href="javascript:void(0)" class="btnWB">麦米认证申请</a>
	                    </#if>
                    </em>
                </div>
                <div>
                	<em class="title"></em>
                    <em id="tipError" class="tipsError">请填写相关信息</em>
                </div>
                </form>
            </fieldset>
        </div>
        
        
    </div>

	<div class="sideRight">
    	<div class="con conHead">
    	<#--${systemProp.appServerUrl}/sns/user-index.action-->
        	<a class="head" href="javascript:void(0)" title='${session_user.nickName!}'>
            	<img  id="userAvatar" src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl+session_user.avatar}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" />
            	<strong>${stringSub(session_user.nickName,24)}</strong>
            	<#if userEx?? && userEx.audit==1><em class="png m"></em></#if>
            </a>
        </div>
    	<div class="con conInfo">
        	<a class="a1" href="${systemProp.appServerUrl}/sns/user-index!attention.action"><span>${attention}</span>关注</a>
        	<a class="a2" href="${systemProp.appServerUrl}/sns/user-index!fans.action"><span>${fans}</span>粉丝</a>
        	<a class="a3" href="${systemProp.appServerUrl}/sns/u${(session_user.id)!'0'}/"><span>${creative}</span>内容</a>
        </div>
	</div>        
</div>


	<div id="applyCertification" class="popContent">
		<fieldset class="new">
	        	<form id="applyForm" method="post">
	    	<h6>麦米认证申请</h6>
	        <div>
	        	<em class="g90 tRight">真实姓名</em>
	            <em><input type="text" class="input g300" name="nameZh"/></em>
	        </div>
	        <div>
	            <em class="g90 tRight">联系电话</em>
	            <em><input type="text" class="input g300" name="phone" /></em>
	        </div>
	        <div>
	        	<em class="g90 tRight">所属出版商</em>
	            <em id="selectPublisher" class="smartSearch">
                   	<input type="text" class="input g300" name="publisher" />
                    <div class="list"><div class="inner jspContainer"></div></div>
                </em>
	        </div>
	        <div>
	        	<em class="g90 tRight">目前职位</em>
	            <em><input type="text" class="input g300"  name="office" /></em>
	        </div>
	        <div>
	        	<em class="g90 tRight">工作相关证明</em>
	            <em><input type="file" id="imgFile"   name="imgFile" class="g320"  /></em>
	        </div>
	        <div><em class="g90">&nbsp;</em><a href="javascript:void(0)" class="btnGB" id="doApply" >申请</a></div>
	        <div><em class="tips">填写说明 ：<br>
			1.每项均为必填，且必须填写真实信息，否则可能会影响您的审核通过几率。<br>
			2.工作相关证明可以上传工作牌、名片，等可以证明工作状态的图片,支持jpg、jpeg、gif、png</em></div>
			</form>
	    </fieldset>
	</div>
<img id="ID_IMG_CK" src="" style="display:none" />
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>