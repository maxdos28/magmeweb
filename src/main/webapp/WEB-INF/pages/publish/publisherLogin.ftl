<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/channelPublisher.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.tagbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/publisherLogin.js"></script>
<script>
$(function(){
	$.jquerytagbox("#publisherLogin",0);
});
</script>

<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<style>
body{ margin:100px auto 0 auto; background:#FFF;}
</style>
<body>

<div class="jqueryTagBox" id="publisherLogin">
    <div class="ctrl">
        <div id="publisherLoginTab">登录</div>
        <div id="reRegisterTab">出版商注册</div>
	<div id="adRegisterTab">广告商注册</div>
    </div>
    <div class="doorList">
                <div class="item">
			<fieldset id="publishLogin"  class="formPublisherLogin">
				<div>
					<em class="title">用户名/邮箱：</em>
					<em><input id="userName" type="text" class="input g200" /></em>
				</div>
				<div>
					<em class="title">密码：</em>
					<em><input id="password" type="password" class="input g200" /></em>
				</div>
				<div>
			    <em class="title">身份：</em>
			    <em><label><input type="radio" name="loginType" value="1" checked="checked"/>出版商</label></em>
			    <em><label><input type="radio" name="loginType" value="2"/>广告商</label></em>
			    <em><label><input type="radio" name="loginType" value="3"/>麦米</label></em>
			</div>
				<div>
					<em class="title">验证码：</em>
					<em><input id="publisherauthcode" type="text" class="input g60" /></em>
				    <em class="code"><img code="getCode" id="publisherfirstcode" class="code" style="background:#ccc;" src="${systemProp.appServerUrl}/images/code.gif" /><a name="getPublisherAuthcode" href="javascript:void(0)">看不清？换一个</a></em>
				</div>
				
				<div>
					<em class="title">&nbsp;</em>
					<em><a id="publisherLoginBtn" href="#" class="btnOS" >登录</a></em>
					<em class="tipsError">请填写相关信息</em>
				</div>
			</fieldset>
		</div><!--item -->
		<div class="item">
		<fieldset id="formReRegister" class="formPublisherRegister">
					<div>
					<em class="title">用户名：</em>
					<em><input id="userName" type="text" class="input g200" /></em>
					<em id="checkName" class="tipsRight"></em>
				    </div>
				    <div>
					<em class="title">杂志社名称：</em>
					<em><input id="publishName" type="text" class="input g200" /></em>
					<em id="checkPublishName" class="tipsRight"></em>
				    </div>
					<div>
					<em class="title">邮箱：</em>
					<em><input id="email" type="text" class="input g200" /></em>
					<em id="checkEmail" class="tipsRight"></em>
				    </div>
				    <div>
					<em class="title">联系人：</em>
					<em><input id="contact1" type="contact1" class="input g200" /></em>
				    </div>
				    <div>
					<em class="title">联系电话：</em>
					<em><input id="contactPhone1" type="contactPhone1" class="input g200" /></em>
				    </div>
				    
				    <div>
					<em class="title">常用联系方式: </em>
					<em>
					   <select class="g70" id="normalContactType" name="normalContactType">
					      <option value="1" >QQ</option>
					      <option value="2" >MSN</option>
					      <option value="3" >其它</option>
					   </select>
					</em>
					<em><input id="normalContact" name="normalContact" class="input g80" type="text" value=""  /></em>
					<em id="checkNormalContact" class="tipsRight"></em>
				    </div>
				    
					<div>
					<em class="title">密码：</em>
					<em><input id="passwordRe" type="password" class="input g200" /></em>
				    </div>
					<div>
					<em class="title">确认密码：</em>
					<em><input id="password2" type="password" class="input g200" /></em>
				    </div>
				     <div>
					<em class="title">验证码：</em>
					<em><input id="publisherauthcode" type="text" class="input g60" /></em>
					<em class="code"><img code="getCode" class="code" style="background:#ccc;" src="${systemProp.appServerUrl}/images/code.gif" /><a name="getPublisherAuthcode" href="javascript:void(0)">看不清？换一个</a></em>
				    </div>
					<div>
					<em class="title">&nbsp;</em>
					<em><a id="publishReg2222Submit" href="#" class="btnOS" >注册</a></em>
					<em class="loading"></em>
					<em class="tipsError">请填写相关信息</em>
				    </div>
				    <div class="tips">
						</div>	
			   </fieldset>
		</div><!--item -->
	<div class="item">
		    <fieldset id="formAdRegister" class="formAdRegister">
			<div>
			    <em class="title">用户名：</em>
			    <em><input id="adUsername" type="text" class="input g220" /></em>
			    <em id="checkUsername" class="tipsRight"></em>
			</div>
			<div>
			    <em class="title">设置密码：</em>
			    <em><input id="adPassword" type="password" class="input g220" /></em>
			</div>
			<div>
			    <em class="title">重复密码：</em>
			    <em><input id="adPassword2" type="password" class="input g220" /></em>
			</div>
			<div>
			    <em class="title">杂志社或公司名称：</em>
			    <em><input id="adCompanyname" type="text" class="input g220" /></em>
			     <em id="checkCompanyname" class="tipsRight"></em>
			</div>
			<hr /><br />
			<div>
			    <em class="title">电子邮件：</em>
			    <em><input id="adEmail" type="text" class="input g220" /></em>
			     <em id="checkEmail" class="tipsRight"></em>
			</div>
			<div>
			    <em class="title">联系人姓名：</em>
			    <em><input id="adContact" type="text" class="input g220" /></em>
			     <em id="checkContact" class="tipsRight"></em>
			</div>
			<div>
			    <em class="title">联系电话：</em>
			    <em><input id="adContactphone" type="text" class="input g220" /></em>
			     <em id="checkContactphone" class="tipsRight"></em>
			</div>
			<div>
			    <em class="title">常用联系方式：</em>
			    <em>
				   <select class="g80" id="adContactType" name="adContactType">
				      <option value="1" >QQ</option>
				      <option value="2" >MSN</option>
				      <option value="3" >其它</option>
				   </select>
				</em>
			    <em><input id="adContactNumber" type="text" class="input g140" /></em>
			    <em id="checkContactNumber" class="tipsRight"></em>
			</div>
			<div>
			    <em class="title">通信地址：</em>
			    <em><input id="adAddress" type="text" class="input g220" /></em>
			</div>
			<hr /><br />
			<div>
			    <em class="title">网站地址：</em>
			    <em><input id="adWebsite" type="text" class="input g220" value="http://" /></em>
			</div>
			<div>
			    <em class="title">传真号码：</em>
			    <em><input id="adFax1" type="text" class="input g80" /></em>
			    <em><input id="adFax2" type="text" class="input g130" /></em>
			</div>
			<div>
			    <em class="title"></em>
			    <em><label><input id="adChk" type="checkbox" />我已阅读声明并接受 麦米网推广服务合同</label></em>
			    <em></em>
			</div>
			<div>
			    <em class="title">&nbsp;</em>
			    <em><a id="adRegSubmit" href="#" class="btnOS" >注册</a></em>
			    <em class="loading"></em>
			    <em class="tipsError">请填写相关信息</em>
			</div>
		    </fieldset>
		</div><!--item -->
        </div>
</div>

</body>
</html>