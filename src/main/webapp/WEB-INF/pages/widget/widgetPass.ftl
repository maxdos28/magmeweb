<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>杂志阅读授权</title>
  <script src="${systemProp.domain}/widget/js/renren.js"></script>
  <script src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js" language="JavaScript"></script>  
  
</head>
<body style="background:url(${systemProp.domain}/widget/images/background.jpg) 0 0 no-repeat;">
<script type="text/javascript">
function getUrlValue(name){ 
	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
	if (r!=null) return unescape(r[2]); return null;
}

var xx = getUrlValue("xx");
if(xx){
	if(xx=='renren'){
		//var xn_sig_session_key = getUrlValue("xn_sig_session_key");
		var redirect_uri = 'http://apps.renren.com/magzine/magzine.html';
			  var uiOpts = {
				  url : "http://graph.renren.com/oauth/authorize",
				  display : "iframe",
				  params : {"response_type":"token","client_id":"3a2740e731624f53ae054d914b870fbe"},
				  onSuccess: function(r){
				    top.location = redirect_uri;
				  },
				  onFailure: function(r){} 
			  };
			  Renren.ui(uiOpts);
	}

	else if(xx=='kaixin'){
		var session_key = getUrlValue("session_key");
		var id = getUrlValue("id");
	
		if(id&&id!=''){
			window.location.href="${systemProp.domain}/widget/magzine.html?xx="+xx+"&id="+id+"&session_key="+session_key;
		}
		else{
			window.location.href="${systemProp.domain}/widget/magzine.html?xx="+xx+"&session_key="+session_key;
		}
	}
	else if(xx=='sina'){
	
		//var accessToken = getUrlValue("id");
		//alert(accessToken);
		
		App.AuthDialog.show({
			client_id : '366059465',    //必选，appkey
			redirect_uri : 'http://apps.weibo.com/magzine',     //必选，授权后的回调地址
			height: 120    //可选，默认距顶端120px
			});
	}

}

</script>
</body>
</html>