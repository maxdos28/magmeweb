<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/phoenix/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.backgroundPosition.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollphoto.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script>
$(function(){
	var $login = $("#conLogin");
	var $reg = $("#conReg");
	$login.find(".action .btnWB").click(function(){
		if(!isIE6){
			$login.css({boxShadow:"none"}).animate({marginLeft:-460,opacity:0.3},400).animate({marginLeft:-210,opacity:0},800,"easeOutQuart");
			$reg.show().css({opacity:0,boxShadow:"none"}).animate({marginLeft:40,opacity:0.5},400).animate({marginLeft:-210,opacity:1},800,"easeOutQuart",function(){$login.hide();$reg.css({boxShadow:"0 0 200px rgba(0,0,0,0.3)"})});
		}else{
			$login.hide();
			$reg.show();
		}
	});
	$reg.find(".action .btnWB").click(function(){
		if(!isIE6){
			$reg.css({boxShadow:"none"}).animate({marginLeft:-460,opacity:0.3},400).animate({marginLeft:-210,opacity:0},800,"easeOutQuart");
			$login.show().css({opacity:0,boxShadow:"none"}).animate({marginLeft:40,opacity:0.5},400).animate({marginLeft:-210,opacity:1},800,"easeOutQuart",function(){$reg.hide();$login.css({boxShadow:"0 0 150px rgba(0,0,0,0.3)"})});
		}else{
			$reg.hide();
			$login.show();
		}
	});
	
	
	$("#submit").unbind("click").live("click",function(){
		var password=$("#password").val();
		var userName=$("#userName").val();
		if(!password || !userName){
		   $('em.tipsError').html("用户名，密码不能为空");
		   $('em.tipsError').show();
		   return;
		}
		
		$.ajax({
		    url:SystemProp.appServerUrl+"/phoenix/phoenix-user!loginJson.action",
			type : "POST",
			async : true,
			data:{"userName":userName,"password":password,"appId":${appId?default(901)},"type":2},
			success: function(result){
				if(result.code==200){
					window.location.href=SystemProp.appServerUrl+"/phoenix/start-pic!index.action";
				}else{
				    $('em.tipsError').html(result.message);
		            $('em.tipsError').show();
				}
			}
		});
		
	});
	//回车事件
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$("#submit").click();
	     }
	}
});
</script>
<style>
html, body{ overflow:hidden;}
</style>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body class="pageAdminLogin">
<div class="conLogin fh_conLogin" id="conLogin">
    <h1 class="logo"><a class="png" href="#">(SEO)麦米 Magme</a></h1>
    <fieldset>
    	<h6>MAGIC EDITOR 后台管理系统</h6>
        <div>
            <em class="g70">&nbsp;</em>
            <em><input id="userName"  type="text" class="input g250" tips="User name/用户名" color="#999,#0F2030" /></em>
        </div>
         <div>
            <em class="g70">&nbsp;</em>
            <em>
            <input type="text" class="input g250" tips="Password/密码" color="#999,#0F2030" />
            <input id="password"  type="password" class="input g250 hide" tips="Password/密码" color="#999,#0F2030" /></em>
        </div>
        <div> 
            <em class="g70">&nbsp;</em>
            <em class="tipsError">密码错误</em>
        </div>
        <div class="action">
            <a id="submit" href="javascript:void(0)" class="btnBB">登录</a>
        </div>
    </fieldset>
</div>
<div class="copyright">Copyright © 2005-2012 <strong><span>麦米网</span> (www.magme.com)</strong> 沪ICP证：沪B2-20120009 </div>

</body>
</html>