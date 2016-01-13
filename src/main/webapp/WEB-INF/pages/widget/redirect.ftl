<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>

<script type="text/javascript">

/* 
*get url value by given name 
*/
function getUrlValue(name){ 
	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
	if (r!=null) return unescape(r[2]); return null;
}

var xx = getUrlValue("xx");
if(xx){
if(xx=='renren'){
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

else{
	window.location.href=SystemProp.domain+"/widget/magzine.html?xx="+xx;
}
}

</script>


