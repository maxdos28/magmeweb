//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-item!searchItemJson.action";
	
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		if(!r.parentId||r.parentId==0)
			trContent += "<td>"+numEmpty(r.sortOrder)+"</td>";
		else
			trContent += "<td>"+numEmpty(r.itemSortOrder)+"</td>";
		trContent += "<td>"+strEmpty(r.itemTitle)+"</td>";
		if(!r.parentId||r.parentId==0)
			trContent += "<td>"+strEmpty(r.categoryTitle)+"</td>";
		else
			trContent += "<td>"+strEmpty(r.parentCategoryTitle)+"</td>";
		if(r.itemType==1)
		{
			trContent += "<td>文章</td>";
		}
		else
		{
			trContent += "<td>杂志</td>";
		}
		if(r.status==1)
		{
			trContent += "<td>上架</td>";
		}
		else
		{
			trContent += "<td>下架</td>";
		}
		trContent += "<td class='tRight'>";
		if(r.parentId==0)
		{
			if(r.isRe==0)
				trContent += "<a name='recommendBtn' itemId="+r.itemId+" class='btn' href='#'>推荐</a>" ;
			else
				trContent += "<a name='recommendBtn' itemId="+r.itemId+" class='btn' href='#'>取消</a>" ;
		}
		if(r.status==1)
			trContent += "<a name='changeStatusBtn' itemId="+r.itemId+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' itemId="+r.itemId+" class='btn' href='#'>上架</a>" ;
				
		trContent += "<a name='editBtn' class='btn' itemId="+r.itemId+" parentCategoryId="+r.parentCategoryId+"  href='#'>编辑</a><a name='delBtn' class='del' itemId="+r.itemId+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var itemId = $(this).attr("itemId");
		if(!itemId)
		{
			alert("无ID");
			return;
		}
		if(!confirm("是否确认?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert("操作失败");
			} else {
				alert("操作成功");
				refreshCurrentPage();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-item!changeStatusItemJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:itemId},
			success : callback1
		});
	});

	$("a[name=recommendBtn]").unbind("click").live("click",function(){
		var itemId = $(this).attr("itemId");
		if(!itemId)
		{
			alert("无ID");
			return;
		}
		if(!confirm("是否确认?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert("操作失败");
			} else {
				alert("操作成功");
				refreshCurrentPage();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-item!recommendItemJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:itemId},
			success : callback1
		});
	});
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var itemId = $(this).attr("itemId");
		var parentCategoryId = $(this).attr("parentCategoryId");
		if(!itemId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		loadItemParent();
		$.ajax({
			url: SystemProp.appServerUrl + "/look/look-item!itemInfoJson.action",
			type: "POST",
			dataType: "json",
			data: {id:itemId},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var item = rs.data.itemInfo;
					if(item)
					{
						var info = item.itemInfo;
						if(info)
						{
							$("#itemId").val(info.id);
							$("#title").val(info.title);
							$("#sortOrder").val(info.sortOrder);
							$("#parentId").val(info.parentId);
							if(item.categoryId)
								$("#categoryId").val(item.categoryId);
							
							//有父栏目时显示父栏目的分类
							if(info.parentId&&info.parentId>0)
							{
								$("#picDiv").show();
								$("#categoryId").attr("disabled",true);
								$("#categoryId").val(parentCategoryId);;
							}
							else
							{
								$("#picDiv").hide();
								$("#categoryId").attr("disabled",false);
							}
							if(info.type&&info.type==1)
							{
								$("#publicationDiv").hide();
							}
							else
							{
								$("#publicationDiv").show();
								$("#picDiv").hide();
							}
							if(info.type==1)
							{
								$("input[type=radio][name=itemType][value=1]").attr("checked",true);
								$("select[name=parentId]").attr("disabled",false);								
							}
							else
							{
								$("input[type=radio][name=itemType][value=2]").attr("checked",true);
								$("select[name=parentId]").val("0");
								$("select[name=parentId]").attr("disabled",true);
							}
							if(info.color)
							{
								$("#color").val(info.color);
								$("#color").attr("style","BACKGROUND-COLOR:#"+info.color);
							}
							else
							{
								$("#color").attr("style","");
							}

							if(info.isDelete==1)
							{
								$("#isDelete").attr("checked",true);
							}
							else
							{
								$("#isDelete").attr("checked",false);
							}
							if(info.isNew==1)
							{
								$("#isNew").attr("checked",true);
							}
							else
							{
								$("#isNew").attr("checked",false);
							}
							if(info.isHot==1)
							{
								$("#isHot").attr("checked",true);
							}
							else
							{
								$("#isHot").attr("checked",false);
							}
							$("#memo").val(info.memo);
							if(item.itemRelList&&item.itemRelList.length>0)
							{
								$("#publicationId").val(item.itemRelList[0].relId);
							}							
						}						
					}
				}
			}
		});
		$("#pop003").fancybox();
		
	});
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var itemId = $(this).attr("itemId");
		if(!itemId)
		{
			alert("无ID");
			return;
		}
		if(!confirm("是否删除?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert("操作失败");
			} else {
				alert("操作成功");
				refreshCurrentPage();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-item!deleteItemJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:itemId},
			success : callback1
		});
	});
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-isRecommend").val()!=-1)
			data.isRecommend = $("#s-isRecommend").val();
		if($("#s-itemType").val()!=-1)
			data.itemType = $("#s-itemType").val();
		if($("#s-categoryId").val()!=-1)
			data.categoryId = $("#s-categoryId").val();
		if($("#s-title").val()!="名称")
			data.title = $("#s-title").val();
		pageComm(1, posturl, data, writetablefn);
	});
	function initPage() {
		//颜色
		$('#color').ColorPicker({
			onSubmit: function(hsb, hex, rgb, el) {
				$(el).val(hex);
				$(el).ColorPickerHide();
				$("#color").attr("style","BACKGROUND-COLOR:#"+hex);
			},
			onBeforeShow: function () {
				$(this).ColorPickerSetColor(this.value);
				$("#color").attr("style","BACKGROUND-COLOR:#"+this.value);
			}
		})
		.bind('keyup', function(){
			$(this).ColorPickerSetColor(this.value);
			$("#color").attr("style","BACKGROUND-COLOR:#"+this.value);
		});
		//分类列表
		$("#s-categoryId").html("<option value='-1'>全部分类</option>");
		$("#categoryId").html("<option></option>");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-category!allCategoryJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			success : function(rs){
				if (rs.code != 200) {
					alert("分类获取失败");
				} else {
					$.each(rs.data.categoryList,function(i,r){
						$("#s-categoryId").append("<option value='"+r.id+"'>"+r.title+"</option>");
						$("#categoryId").append("<option value='"+r.id+"'>"+r.title+"</option>");
					});
				}
			}
		});
		//杂志列表
		$("#publicationId").html("<option></option>");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-item!publicationList.action",
			type : "POST",
			async : false,
			dataType : "json",
			success : function(rs){
				if (rs.code != 200) {
					alert("分类获取失败");
				} else {
					$.each(rs.data.publicationList,function(i,r){
						$("#publicationId").append("<option value='"+r.id+"'>"+r.name+"</option>");
					});
				}
			}
		});
		
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#itemId").val(0);
		$("input[name=itemType][value=1]").attr("checked",true);
		$("#publicationDiv").hide();
		$("#picDiv").hide();
		$("select[name=parentId]").attr("disabled",false);
		loadItemParent();
		$("#pop003").fancybox();
	});
	//类型选择
	$("input[type=radio][name=itemType]").unbind("change").live("change",function(){
		var v = $(this).val();
		if(v==1)
		{
			$("#publicationDiv").hide();
			$("select[name=parentId]").attr("disabled",false);
		}
		else
		{
			$("#publicationDiv").show();
			$("#picDiv").hide();
			$("select[name=parentId]").val("0");
			$("select[name=parentId]").attr("disabled",true);
		}
	});
	//父类选择
	$("select[name=parentId]").unbind("change").live("change",function(){
		var v = $(this).val();
		if(v=="0")
		{
			$("#picDiv").hide();
			$("#categoryId").attr("disabled",false);
		}
		else
		{
			$("#picDiv").show();
			$("#categoryId").attr("disabled",true);
		}
	});
	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		$("#submitForm").submit();
	});
	$("#submitForm").validate({
		rules : {
			title : "required",
			categoryId : {
				required : function(){if($("#parentId").val()=="0")return true;else return false; }
			},
			itemType : "required",
			memo : "required",
			sortOrder : {
				number : true,
				required : true
			},
			publicationId:{
				required : function(){if($("input[type=radio][name=itemType]:checked").val()==2)return true;else return false; }
			},
			itemPic:{
				required : function(){if($("input[type=radio][name=itemType]:checked").val()==1&&(!$("#itemId").val()||$("#itemId").val()=="0")&&($("#parentId").val()&&$("#parentId").val()>0))return true;else return false; }
			}
		},
		submitHandler:save
	});
	function save()
	{
		var postUrl = SystemProp.appServerUrl+"/look/look-item!saveItemJson.action";
		var data = form2object('submitForm');
		data.id = $("#itemId").val();
		data.type = $("input[type=radio][name=itemType]:checked").val();
		if($("#isDelete").attr("checked"))
			data.isDelete = 1;
		else
			data.isDelete = 0;
		if($("#isNew").attr("checked"))
			data.isNew = 1;
		else
			data.isNew = 0;
		if($("#isHot").attr("checked"))
			data.isHot = 1;
		else
			data.isHot = 0;
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "itemPic"],
			content : $("#submitForm"),
			dataType : "json",
			async : true,
			type : 'POST',
			success : function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					alert("保存成功");
					refreshCurrentPage();
					$.fancybox.close();
				}
			},
			// 服务器响应失败处理函数
			error : function(rs, status, e) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					
				}
			}
		});
	}
	//加载父栏目
	function loadItemParent()
	{
		$("#parentId").html("<option value='0'></option>");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-item!allItemJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			success : function(rs){
				if (rs.code != 200) {
					alert("栏目获取失败");
				} else {
					$.each(rs.data.itemList,function(i,r){
						if(r.parentId&&r.parentId!=0)
							return true;
						if(r.type==2)
							return true;
						$("#parentId").append("<option value='"+r.id+"'>"+r.title+"</option>");
					});
				}
			}
		});
	}
});
