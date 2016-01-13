$(function(){
	//选择对应的分类
	$("input[name='golfType']").live("click",function(){
		var obj = $(this);
		var nt = obj.attr("nt");
		var fCheckedValue = obj.attr("fvalue");
		if(nt==1){//一级分类
			$("input[name='firstTypeName']").val(obj.attr("valText"));
			$("input[name='secondTypeName']").val("");
		}
		if(nt==2){//二级分类
			$("select[name='newsTypeSelect']").val(fCheckedValue);
			$("input[name='secondTypeName']").val(obj.attr("valText"));
			$("input[name='firstTypeName']").val("");
		}
	});
	//删除对应的分类
	$("#delNewsTypeBtn").click(function(){
		var obj = $("input[name='golfType']:checked");
		var nt = obj.attr("nt");
		if(nt){
			var checkedValue = obj.attr("value");
			var data = {};
			data.id=checkedValue;
			if(nt){
				var msg="一级分类删除后,对应的二级分类也会删除。确认吗？";
				if(nt==2){
					msg="确认删除吗？";
				}
				if(ConfirmDel(msg)){
					function callback(result){
						if(result.code!=200){
							return;
						}else{
							location.replace(location.href);//刷新页面
						}
					}
					$.ajax({
						url: SystemProp.appServerUrl+"/golf/news-type!del.action",
						type: "POST",
						dataType: "json",
						data: data,
						success: callback
					});
				}
			}
		}else{
			alert("请选择要删除的分类")
		}
	});	
	//清除对应的数值
	$("#clearNewsTypeRadioBtn").click(function(){
		clearAllInputValue();
	});	
	//一级分类的提交
	$("#firstSubmitBtn").click(function(){
		var data = {};
		var cid = $("input[name='golfType']:checked").val();
		var cname = $("input[name='firstTypeName']").val(); 
		if(cid){
			data.id=cid;
		}
		if(cname){
			data.name=cname;
		}else{
			alert("一级分类的名称不能为空");
			return;
		}
		function callback(result){
			if(result.code!=200){
				return;
			}else{
				location.replace(location.href);//刷新页面
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/golf/news-type!op.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//二级分类的提交
	$("#secondtSubmitBtn").click(function(){
		var data = {};
		var cid = $("input[name='golfType']:checked").val();
		var cname = $("input[name='secondTypeName']").val(); 
		var pid = $("select[name='newsTypeSelect']").val();
		if(cid){
			data.id=cid;
		}
		if(cname){
			data.name=cname;
		}else{
			alert("二级分类的名称不能为空");
			return;
		}
			data.pid = pid;
		function callback(result){
			if(result.code!=200){
				return;
			}else{
				location.replace(location.href);//刷新页面
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/golf/news-type!op.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	function clearAllInputValue(){
		$("select[name='newsTypeSelect']").val("");
		$("input[name='firstTypeName']").val("");
		$("input[name='secondTypeName']").val("");
		$("input[name='golfType']").attr("checked",false);
	}
});