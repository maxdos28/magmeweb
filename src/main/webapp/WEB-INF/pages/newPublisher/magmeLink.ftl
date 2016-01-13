<script src="${systemProp.staticServerUrl}/v3/dv/js/magmeLink.js"></script>
<script src="${systemProp.appServerUrl}/kindeditor/kindeditor.js"></script>
<script>
	KE.show({
		id : 'container',
		allowPreviewEmoticons : false,
		allowUpload : true, //允许上传图片  
		imageUploadJson : '${systemProp.appServerUrl}/new-publisher/edit-link!uploadImg.action', //服务端上传图片处理URI 
		afterCreate : function(id) {
			KE.event.ctrl(document, 13, function() {
				KE.sync(id);
			});
			KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
				KE.sync(id);
			});
		}
	});
</script>
<div class="conLeftMiddleRight" menu="editor" label="page"> 
友情链接页面管理
<textarea id="container" name="content" style="width:928px;height:300px;visibility:hidden;">
		<#if content??>
			${(content)!''}
		</#if>
</textarea>

<div style="text-align:center;" >
	<a class="btnBB" id="saveBtn" href="javascript:void(0)" style="margin:10px 0;">保存/修改</a>
</div>
<br />
首页页脚友情链接管理
<textarea id="containerFooter" name="indexContent" style="width:928px;height:300px;">
		<#if indexContent??>
			${(indexContent)!''}
		</#if>
</textarea>

<div style="text-align:center;" >
	<a class="btnBB" id="saveFooterBtn" href="javascript:void(0)" style="margin:10px 0;">保存/修改</a>
</div>
<br />
杂志页页脚友情链接管理
<select id="pubTypeSelect" >
	<option value="3">杂志综合</option>
	<option value="4">财经</option>
	<option value="5">居家</option>
	<option value="6">旅游</option>
	<option value="7">男性</option>
	<option value="8">女性</option>
	<option value="9">汽车</option>
	<option value="10">情感</option>
	<option value="11">时尚</option>
	<option value="12">IT</option>
	<option value="13">文化</option>
	<option value="14">艺术</option>
	<option value="15">外文</option>
	<option value="16">生活</option>
	<option value="17">学术</option>
	<option value="18">娱乐</option>
</select>
<textarea id="containerPub" name="containerPub" style="width:928px;height:300px;">
	<#if pubContent??>
			${(pubContent)!''}
		</#if>
</textarea>

<div style="text-align:center;" >
	<a class="btnBB" id="saveFooterPubBtn" href="javascript:void(0)" style="margin:10px 0;">保存/修改</a>
</div>
</div>
