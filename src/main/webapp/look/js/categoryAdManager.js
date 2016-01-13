//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-ad-manager-by-category!searchCategoryAdJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.sortOrder)+"</td>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.picPath+"'></td>";
		if(r.size==1)
		{
			trContent += "<td>全尺寸</td>";
		}
		else
		{
			trContent += "<td>1/2尺寸</td>";
		}
		trContent += "<td>"+strEmpty(r.title)+"</td>";
		trContent += "<td>"+numEmpty(r.categoryTitle)+"</td>";
		if(r.adType==1)
		{
			trContent += "<td>栏目</td>";
		}
		else if(r.adType==0)
		{
			trContent += "<td>外链</td>";
		}
		else
		{
			trContent += "<td>文章</td>";
		}
		trContent += "<td>"+strEmpty(r.link)+"</td>";
		if(r.adType==1)
		{
			trContent += "<td>"+numEmpty(r.itemTitle)+"</td>";
		}
		else if(r.adType==2)
		{
			trContent += "<td>"+numEmpty(r.articleId)+"</td>";
		}
		else
		{
			trContent += "<td></td>";
		}
		
		if(r.status==1)
		{
			trContent += "<td>上架</td>";
		}
		else
		{
			trContent += "<td>下架</td>";
		}
		trContent += "<td>";
		if(r.status==1)
			trContent += "<a name='changeStatusBtn' categoryRecommendId="+r.categoryRecommendId+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' categoryRecommendId="+r.categoryRecommendId+" class='btn' href='#'>上架</a>" ;
		trContent += "<a name='editBtn' class='btn' categoryRecommendId="+r.categoryRecommendId+" href='#'>编辑</a><a name='delBtn' class='del' categoryRecommendId="+r.categoryRecommendId+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-type").val()!="-1")
		data.type = $("#s-type").val();
		if($("#s-categoryId").val()!="-1")
		data.categoryId = $("#s-categoryId").val();
		if($("#s-itemId").val()!="-1")
			data.itemId = $("#s-itemId").val();
		if($("#s-title").val()!="名称")
			data.title = $("#s-title").val();	
		
		pageComm(1, posturl, data, writetablefn);
	});
	//上架下架
	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var categoryRecommendId = $(this).attr("categoryRecommendId");
		if(!categoryRecommendId)
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
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-category!changeStatusCategoryAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {categoryRecommendId:categoryRecommendId},
			success : callback1
		});
	});
	//修改
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var categoryRecommendId = $(this).attr("categoryRecommendId");
		if(!categoryRecommendId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$("#categoryRecommendId").val(categoryRecommendId);
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-category!categoryAdInfoJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {categoryRecommendId:categoryRecommendId},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
				}
				else
				{
					var cr = rs.data.categoryRecommendInfo;
					if(cr)
					{
						$("#sortOrder").val(numEmpty(cr.sortOrder));
						$("#title").val(strEmpty(cr.title));
						$("#size").val(cr.size);
						$("#categoryId").val(cr.categoryId);
						if(cr.type&&cr.type==1)
						{
							$("#itemdiv").show();
							$("#linkdiv").hide();
							$("#articlediv").hide();
							$("#itemId").val(cr.itemId);
							$("input[type=radio][name=type][value=1]").attr("checked",true);
						}
						else if(cr.type&&cr.type==0)
						{
							$("#itemdiv").hide();
							$("#linkdiv").show();
							$("#articlediv").hide();
							$("#link").val(strEmpty(cr.link));
							$("input[type=radio][name=type][value=0]").attr("checked",true);
						}
						else
						{
							$("#itemdiv").hide();
							$("#linkdiv").hide();
							$("#articlediv").show();
							$("#articleId").val(strEmpty(cr.articleId));
							$("input[type=radio][name=type][value=2]").attr("checked",true);
						}
					}
				}
			}
		});
		$("#pop004").fancybox();
		
	});
	//删除
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var categoryRecommendId = $(this).attr("categoryRecommendId");
		if(!categoryRecommendId)
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
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-category!deleteCategoryAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {categoryRecommendId:categoryRecommendId},
			success : callback1
		});
	});
	function initPage() {

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

		//栏目列表
		var sitem = $("#s-itemId");
		sitem.html("<option value='-1'>全部栏目</option>");
		var is = $("#itemId");
		is.html("<option></option>");
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
						//杂志类栏目不添加
						if(r.type==2)
							return true;
						if(r.parentId!=0)
							return true;
						//有子栏目的栏目
						if(r.isParent&&r.isParent==1)
						{
							var parentId = r.id;
							sitem.append("<optgroup label='"+r.title+"'>");
							is.append("<optgroup label='"+r.title+"'>");
							$.each(rs.data.itemList,function(i1,r1){
								if(r1.type==2)
									return true;
								if(r1.parentId&&r1.parentId==parentId)
								{
									sitem.append("<option value='"+r1.id+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+r1.title+"</option>");
									is.append("<option value='"+r1.id+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+r1.title+"</option>");
								}
							});
							sitem.append("</optgroup>");
						    is.append("</optgroup>");
						}
						else
						{
							sitem.append("<option value='"+r.id+"'>"+r.title+"</option>");
							is.append("<option value='"+r.id+"'>"+r.title+"</option>");
						}						
					});
				}
			}
		});
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#categoryRecommendId").val("0");
		$("#pop004").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		$("#submitForm").submit();
	});
	//关闭编辑窗口
	$("input[type=radio][name=type]").bind("click", function() {
		if($(this).val()==0)
		{
			$("#itemdiv").hide();
			$("#linkdiv").show();
			$("#articlediv").hide();
		}
		else if($(this).val()==1)
		{
			$("#itemdiv").show();
			$("#linkdiv").hide();
			$("#articlediv").hide();
		}
		else
		{
			$("#itemdiv").hide();
			$("#linkdiv").hide();
			$("#articlediv").show();
		}
	});
	$("#submitForm").validate({
		rules : {
			title : "required",
			categoryId : "required",
			itemId : "required",
			type : {
				required : function(){if($("input[type=radio][name=type]:checked").val()=="1")return true;else return false;}
			},
			link :{
				url:true,
				required : function(){if($("input[type=radio][name=type]:checked").val()=="0")return true;else return false;}
			},
			articleId :{
				digits:true,
				required : function(){if($("input[type=radio][name=type]:checked").val()=="2")return true;else return false;}
			},
			sortOrder : {
				digits : true,
				required : true
			},
			recommendPic : {
				required : function(){if(!$("#categoryRecommendId").val()||$("#categoryRecommendId").val()=="0")return true;else return false;}
			}
		},
		submitHandler:save
	});
	function save()
	{
		var postUrl = SystemProp.appServerUrl+"/look/look-ad-manager-by-category!saveCategoryAdJson.action";
		var data = form2object('submitForm');
		data.categoryRecommendId = $("#categoryRecommendId").val();
		data.title = $("#title").val();
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "recommendPic"],
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
});
