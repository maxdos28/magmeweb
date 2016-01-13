<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>杂志阅读授权</title>
  <script src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js" language="JavaScript"></script>  
  
</head>
<body style="background:url(${systemProp.staticServerUrl}/v3/widget/images/sns/bg_${pubId}.jpg) 0 0 no-repeat;">
<script type="text/javascript">
function getUrlValue(name){ 
	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
	if (r!=null) return unescape(r[2]); return null;
}

var xx = getUrlValue("xx");
var pubId = getUrlValue("pubId");
var redirect_uri = 'http://apps.weibo.com/${pubName}';

if(xx){
   if(xx=='sina'){
		//var accessToken = getUrlValue("id");
		//alert(accessToken);
		App.AuthDialog.show({
			client_id : '${appKey}',    //必选，appkey
			redirect_uri : redirect_uri,     //必选，授权后的回调地址
			height: 120    //可选，默认距顶端120px
			});
	}

}

</script>
</body>
</html>