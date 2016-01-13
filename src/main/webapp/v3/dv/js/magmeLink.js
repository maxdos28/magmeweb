
	$(document).ready(function(){
		$('#saveBtn').click(getContent);
		
		$("#saveFooterBtn").click(getFooterContent);
		$("#saveFooterPubBtn").click(getFooterPubContent);
		$("#pubTypeSelect").change(function(){
			footerPubContent();
		});
		
	});
	function getContent(){
		//var content = $('#container').val();
		var content = KE.html('container');
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/edit-link!doUpdate.action',
			type : 'post',
			dataType: "json",
			data : {content: content},
			success : callback
		});
	}
	
	function callback(result){
		if(result.code == 200){
			alert("保存成功");
		} else {
			alert("保存失败");
		}
	}
	
	function getFooterContent(){
		//var content = $('#container').val();
		var indexContent = $("#containerFooter").val();
		if(!indexContent) {alert('内容不能为空.');return ;}
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/edit-link!doFLUpdate.action',
			type : 'post',
			dataType: "json",
			data : {indexContent: indexContent},
			success : callbackfooter
		});
	}
	
	function callbackfooter(result){
		if(result.code == 200){
			alert("保存成功");
		} else {
			alert("保存失败");
		}
	}
	
	function getFooterPubContent(){
		//var content = $('#container').val();
		var indexContent = $("#containerPub").val();
		var type = $("#pubTypeSelect").val();
		if(!indexContent) {alert('内容不能为空.');return ;}
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/edit-link!doPubUpdate.action',
			type : 'post',
			dataType: "json",
			data : {pubContent: indexContent,linkType:type},
			success : callbackfooter
		});
	}
	
	function footerPubContent(){
		var type = $("#pubTypeSelect").val();
		function footerPub(result){
			$("#containerPub").val(result.data.indexContent);
		}
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/edit-link!doPubLink.action',
			type : 'post',
			dataType: "json",
			data : {linkType:type},
			success : footerPub
		});
	}
	