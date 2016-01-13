$(document).ready(function(){
	//tagbox
	$.jquerytagbox("#tagboxLogin",0);
	var publishLogin = $("#publishLogin");
	var formReRegister = $("#formReRegister");
	
	//getCode
	function getCode (img){
		var img = $(img);
		var src = SystemProp.appServerUrl + "/publish/authcode.action?random=" + Math.random();
		img.attr("src",src);
	}
	$("a[name='getPublisherAuthcode']").unbind('click').bind('click',function(e){
		e.preventDefault();
		getCode($(this).prev());
	});
	
	$("#reRegisterTab").unbind('click').bind('click',function(){
		$("#publishLogin").parent().removeClass("current").removeAttr("style");
		$("#formReRegister").parent().addClass("current").attr("style","display:block");
		$("#publisherLoginTab").removeClass("current");
		$(this).addClass("current");
		getCode( $("img[code]",formReRegister).eq(0) );
	});
	
	$("#publisherLoginTab").unbind('click').bind('click',function(){
		$("#formReRegister").parent().removeClass("current").removeAttr("style");
		$("#publishLogin").parent().addClass("current").attr("style","display:block");
		$("#reRegisterTab").removeClass("current");
		$("#publisherLoginTab").addClass("current");
		getCode( $("img[code]",publishLogin).eq(0) );
	});
	
	//init authcode
	getCode ( $("#publisherfirstcode").eq(0) );
	
	function publisherLoginSuccess(loginType){
		var url="";
		if(loginType==1){
			url="/publish/pcenter-publisher.action?random=";
		}else if(loginType==2){
			url="/ad/adcenter-home.action?random=";
		}else if(loginType==3){
			url="/publish/pcenter-publisher!index.action?random="
		}
		window.location.href = SystemProp.appServerUrl+url+Math.random();
		
	}
	//login
	$("#publisherLoginBtn").click(publisherLogin);
	//---------------------------------publisher_login-----------------------------------------
	function publisherLogin(){
		var userName = $("#userName",publishLogin).val();
		var password = $("#password",publishLogin).val();
		var loginType = $(":radio:checked").val();
		
		var authCode = $("#publisherauthcode",publishLogin).val();
		var callback = function(result){
			if(!result) return;
			var tipError = $(".tipsError",publishLogin);
			var data = result.data;
			var message = result.message;
			if(result.code != 200){
				if(data && data.authcode){
					message = data.authcode;
				}
				if(data && data.userName){
					message = data.userName;
				}
				tipError.html(message).show();
			}else{
				message = "登录成功!";
				tipError.text(message).show();
				setTimeout(function(){
					publisherLoginSuccess(loginType);
				},1000);
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/publish/front-publisher!loginJson.action",
			type: "POST",
			data: {"userName":userName,"password":password,"authcode":authCode,"loginType":loginType},
			success: callback
		});
	}
	//---------------------------enter_submmit---------------------------------------------
	$(document).keyup(function(event){
		if (event.keyCode == '13') {
			event.preventDefault();
			$(".btnOS:visible").click();
	    }
	});
	
	//register	
	$("#publishReg2222Submit").click(function(){
	    var tipError = $(".tipsError",formReRegister);
		var password = $("#passwordRe",formReRegister).val();
		var password2 = $("#password2",formReRegister).val();
		
		var contactPhone1 = $("#contactPhone1",formReRegister).val();
		var contact1 = $("#contact1",formReRegister).val();
		var email = $("#email",formReRegister).val();
		var publishName = $("#publishName",formReRegister).val();
		var userName = $("#userName",formReRegister).val();
		var authCode = $("#publisherauthcode",formReRegister).val();
		var normalContactType= $("#normalContactType",formReRegister).val();
		var normalContact= $("#normalContact",formReRegister).val();
		
				
		if(!$.trim(normalContact) || !$.trim(normalContact)){
			tipError.show().html("常用联系方式不能为空!");
			return;
		}else{
			tipError.hide();
		}
		
		if(!$.trim(password) || !$.trim(password2)){
			tipError.show().html("密码不能为空!");
			return;
		}else{
			tipError.hide();
		}
		if(password !== password2){
			tipError.show().html("确认密码错误!");
			return;
		}else{
			tipError.hide();
		}
		$(".loading",formReRegister).show();
		var callback2 = function(result){
			var code = result.code;
			var data = result.data;
			var message = result.message;
			if(code != 200){
				if(data && data.authcode){
					message = data.authcode;
					tipError.html(message).show();
					return;
				}
				if(data && data.userName){
					message = data.userName;
					tipError.html(message).show();
					return;
				}
				if(data && data.email){
					message = data.email;
					tipError.html(message).show();
					return;
				}
				if(data && data.password){
					message = data.password;
					tipError.html(message).show();
					return;
				}
			}else{
				tipError.show().html("注册成功,管理员审核后可以登录!");
				setTimeout(function(){
					publisherLoginSuccess(3);
				},1000);
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/publish/front-publisher!registerJson.action",
			type: "POST",
			data: {"password":password,"password2":password2,
			       "publishName":publishName,"userName":userName,
			       "email":email,"contact1":contact1,"contactPhone1":contactPhone1,"authcode":authCode,"normalContactType":normalContactType,"normalContact":normalContact},
			success: callback2,
			complete:function(){$(".loading",formReRegister).hide();}
		});
		
	});
	
	
	//check publish
	$("#userName ,#publishName,#email,#normalContact",formReRegister).unbind('blur').bind('blur',function(){
		var elementData = $(this).data("elementData");
		var value = $(this).val();
		if(!elementData || elementData != value){
			$(this).data("elementData",value);
			check($(this),formReRegister);
		}
	});
	function check(obj,register){
		if(!obj) return;
		var tipError = $(".tipsError",register);
		var id = obj.attr("id");
		var value = obj.val();
		if($.trim(value) == ''){
			tipError.html("用户名/杂志社名称/邮箱/常用联系方式 不能为空！").show();
			return;
		}
		
		var userData = {};
		userData[id] = value;
		var callback = function(result){
			if(result.code == 200){
				if(id == 'userName'){
					$("#checkName",register).show();
				}else if(id == 'publishName'){
					$("#checkPublishName",register).show();
				}else if(id=="email"){
					$("#checkEmail",register).show();
				}else{
					$("#checkNormalContact",register).show();
				}
				tipError.hide();
			}else{
				if(id == 'userName'){
					$("#checkName",register).hide();
				}else if(id == 'publishName'){
					$("#checkPublishName",register).hide();
				}else if(id=="email"){
					$("#checkEmail",register).hide();
				}else{
					$("#checkNormalContact",register).hide();
				}
				tipError.html(result.data[id]).show();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/front-publisher!validatePublisher.action",
			type : "POST",
			data : userData,
			success: callback
		});
	}
	
	var formAdRegister = $("#formAdRegister");

	$("#adUsername,#adCompanyname,#adEmail,#adContact,#adContactphone,#adContactNumber",formAdRegister).unbind('blur').bind('blur',function(){
		var elementData = $(this).data("elementData");
	
		var value = $(this).val();
		if(!elementData || elementData != value){
			$(this).data("elementData",value);
			checkAd($(this),formAdRegister);
		}
	});
	function checkAd(obj,register){
		if(!obj) return;
		var tipError = $(".tipsError",register);
		var id = obj.attr("id");
		var value = obj.val();
		if($.trim(value) == ''){
			tipError.html("用户名/杂志社或公司名称/电子邮件/联系人姓名/联系电话/常用联系方式 不能为空！").show();
			return;
		}
		
		var userData = {};
		if(id == 'adUsername'){
			userData["adAgency.userName"] = value;
		}
		if(id == 'adCompanyname'){
			userData["adAgency.companyName"] = value;
		}
		if(id == 'adEmail'){
			userData["adAgency.email"] = value;
		}
		if(id == 'adContact'){
			userData["adAgency.contact"] = value;
		}
		if(id == 'adContactphone'){
			userData["adAgency.contactPhone"] = value;
		}
		if(id == 'adContactNumber'){
			userData["adAgency.contactNumber"] = value;
		}
		var callback = function(result){
			if(result.code == 200){
				if(id == 'adUsername'){
					$("#checkUsername",register).show();
				}else if(id == 'adCompanyname'){
					$("#checkCompanyname",register).show();
				}else if(id=="adEmail"){
					$("#checkEmail",register).show();
				}else if(id=="adContact"){
					$("#checkContact",register).show();
				}else if(id=="adContactphone"){
					$("#checkContactphone",register).show();
				}else{
					$("#checkContactNumber",register).show();
				}
				tipError.hide();
			}else{
				if(id == 'adUsername'){
					$("#checkUsername",register).hide();
				}else if(id == 'adCompanyname'){
					$("#checkCompanyname",register).hide();
				}else if(id=="adEmail"){
					$("#checkEmail",register).hide();
				}else if(id=="adContact"){
					$("#checkContact",register).hide();
				}else if(id=="adContactphone"){
					$("#checkContactphone",register).hide();
				}else{
					$("#checkContactNumber",register).hide();
				}
				tipError.html(result.data[id]).show();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/ad/ad-agency!validateAdAgency.action",
			type : "POST",
			data : userData,
			success: callback
		});
	}

	
	//register	
	$("#adRegSubmit").click(function(){
	    var tipError = $(".tipsError",formAdRegister);

		var adUsername = $("#adUsername",formAdRegister).val();
		var adPassword = $("#adPassword",formAdRegister).val();
		var adPassword2 = $("#adPassword2",formAdRegister).val();
		var adCompanyname = $("#adCompanyname",formAdRegister).val();
		var adEmail = $("#adEmail",formAdRegister).val();

		var adContactphone = $("#adContactphone",formAdRegister).val();
		var adContactType = $("#adContactType",formAdRegister).val();
		var adContact = $("#adContact",formAdRegister).val();
		var adContactNumber = $("#adContactNumber",formAdRegister).val();
		var adAddress = $("#adAddress",formAdRegister).val();

		var adWebsite = $("#adWebsite",formAdRegister).val();
		var adFax1 = $("#adFax1",formAdRegister).val();
		var adFax2 = $("#adFax2",formAdRegister).val();
		if(!$("#adChk").is(':checked')){
			tipError.show().html("请接受声明");
			return;
		}else{
			tipError.hide();
		}

		$(".loading",formAdRegister).show();
		var callback = function(result){
			var code = result.code;
			var data = result.data;
			var message = result.message;
			if(code != 200){
				if(data && data.adUsername){
					message = data.adUsername;
					tipError.html(message).show();
					return;
				}
				if(data && data.adPassword){
					message = data.adPassword;
					tipError.html(message).show();
					return;
				}
				if(data && data.adCompanyname){
					message = data.adCompanyname;
					tipError.html(message).show();
					return;
				}
				if(data && data.adEmail){
					message = data.adEmail;
					tipError.html(message).show();
					return;
				}
				if(data && data.adContact){
					message = data.adContact;
					tipError.html(message).show();
					return;
				}
				if(data && data.adContactphone){
					message = data.adContactphone;
					tipError.html(message).show();
					return;
				}
				if(data && data.adContactNumber){
					message = data.adContactNumber;
					tipError.html(message).show();
					return;
				}
			}else{
				tipError.show().html("注册成功,管理员审核后可以登录!");
				setTimeout(function(){
					publisherLoginSuccess(3);
				},1000);
			}
		}

		$.ajax({
			url: SystemProp.appServerUrl+"/ad/ad-agency!registerJson.action",
			type: "POST",
			data: {"adAgency.userName":adUsername,
					"adAgency.password":adPassword,"adAgency.password2":adPassword2,
					"adAgency.companyName":adCompanyname,"adAgency.email":adEmail,

					"adAgency.contactPhone":adContactphone,
					"adAgency.contact":adContact,
					"adAgency.contactType":adContactType,"adAgency.contactNumber":adContactNumber,

					"adAgency.address":adAddress,"adAgency.webSite":adWebsite,
					"adAgency.fax":adFax1 + adFax2},
			success: callback,
			complete:function(){$(".loading",formAdRegister).hide();}
		});
	});
	
});