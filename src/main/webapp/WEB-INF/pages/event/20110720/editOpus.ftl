<html>
<head>
<title>麦米活动</title>
</head>
<body>
<style> 
.body .left .menu{ background-position:0 60px;}
fieldset div em.editer .ke-toolbar td *{ float:left; }
fieldset div em.editer * {float:none;}

</style>

<script>
$(document).ready(function(){	
	//初始化文本编辑器
	KE.show({
		id : 'eventOpusContent',
		cssPath : SystemProp.staticServerUrl+'/kindeditor/examples/index.css',
		width:"550px",
		height:"250px",
		filterMode:true,
		resizeMode:0,
		allowUpload : true, //允许上传图片  
		imageUploadJson : '${systemProp.appServerUrl}/kind-editor-upload!imageUploadJson.action', //服务端上传图片处理URI  
		afterCreate : function(id) {
			KE.event.ctrl(document, 13, function() {
				KE.sync(id);
			});
			KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
				KE.sync(id);
			});
		}
	});
});
</script>	
	<div class="right">
		<div class="con con10">
        	<h2>我要参与</h2>
	    	<fieldset>
	        	<form method="post" id="eventOpusForm">
	        		<input type="hidden" name="eventCode" value="20110720" />
					<input type="hidden" name="cover"/>	        		
		            <div>
		            	<em class="title">标题：</em>
		            	<em><input type="text" class="input g310" name="title"></em>
		            </div>	        	
		            <div>
		            	<em class="title">封面：</em>
		            	<em><input type="file" id="imgFile" name="imgFile"></em>
		            	<em><a id="coverUploadBtn" name="coverUploadBtn" href="javascript:void(0)" class="btnWS">上传</a></em>
		            </div>
		            <div id="coverPreviewDiv" style="display:none;">
		            	<em class="title">预览：</em>
		            	<em><img id="coverPreviewImg" src=""/></em>
		            </div>		            
		            <div>
		            	<em class="title">内容：</em>
		            	<em class="editer"><textarea id="eventOpusContent" name="content" rows="4" class="input g650" type="text"></textarea></em>
		            </div>
		            <div>
                    	<em class="title"></em>
		                <em ><a id ="eventOpusFormSubmitBtn" href="javascript:void(0)" class="btnBS">提交</a></em>
		                <!-- <em><a id="cancel" href="javascript:void(0)" class="btnWS" >取消</a></em> -->
		                <em id ="tipError" class="tipsError">请填写相关信息</em>
		            </div>
	            </form>
	        </fieldset>
	    </div>
	</div>
</body>
</html>