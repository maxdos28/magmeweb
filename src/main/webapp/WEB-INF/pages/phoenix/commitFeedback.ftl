<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>用户反馈</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/phoenix/style/reset2.css" rel="stylesheet">
<link href="${systemProp.staticServerUrl}/phoenix/style/base.css" rel="stylesheet">
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/app.js"></script>
<style type="text/css">
html{background: #fff;height:100%;}
</style>
</head>
<input id="os" type="hidden" value='${os!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="version" type="hidden" value='${v!}' >
<body class="appSubmit">
<div class="appBody" style="padding-top: 0px;">
	<div class="appItemBox_phone">
		<div class="appItem_input">
	    	<label>手机：<input type="number" class="input" id="contact"  placeholder="输入手机" /></label>
	    </div>
   </div>
   <div class="appItemBox_phone">
		<div class="appItem_input">
	    	<label>邮箱：<input type="email" class="input" id="email"  placeholder="输入邮箱" /></label>
	    </div>
   </div>
	<div class="appItemBox_last">
		<div class="appItem_textarea">
	    	<textarea placeholder="请留下您的宝贵意见" id="content"></textarea>
	    </div>
	</div>
</div>
<script>
	function commitfb()
	{
	
		var contact = $("#contact").val();
		var email = $("#email").val();
		var content = $("#content").val();
		if(!contact && !email)
		{
			alertMes("请留下您的联系方式");
			return;
		}
		if(!content)
		{
			alertMes("请留下您的宝贵意见");
			return;
		}
		if(!contact)
		{
			contact = email;
		}
		else if(email)
		{
			contact = contact+";"+email;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/app/usr!feedback.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {appId:$("#appId").val(),categoryId:4,contact:contact,content:content},
			success : function (rs) {
				if (rs.code != 200) {
					alertMes("提交失败!");
				} else {
					alertMes("提交成功!");
					sendSuccess();
				}
			}
		});
	}

	var os_andriod = "ANDROID";
	var os_ios = "IOS";
	
	var os = $("#os").val();
	var appId = $("#appId").val();
	
	function sendSuccess(){
		if(os == os_andriod){
			window.magme.sendSuccess();
		}else if(os == os_ios){
			window.open("iosmethod:sendSuccess");
		}
	};
	
	function alertMes(msg){
		if(os == os_andriod){
			window.magme.alert(msg);
		}else if(os == os_ios){
			window.open("iosmessage:"+msg);
		}
	};
</script>
</body>
</html>
