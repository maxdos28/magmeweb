//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/phoenix/phoenix-category-ad!searchPhoenixCategoryAdJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.picPath+"'></td>";
		trContent += "<td>"+r.sortOrder+"</td>";
		trContent += "<td>"+r.title+"</td>";
		if(r.link&&r.link.length>60)
		{
			trContent += "<td class='tLeft'>"+r.link.substring(0,60)+"......</td>";
		}
		else
		{
			trContent += "<td class='tLeft'>"+strEmpty(r.link)+"</td>";
		}
		trContent += "<td><a name='editBtn' class='btn' adId="+r.id+"  href='#'>编辑</a><a name='delBtn' class='del' adId="+r.id+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("a[name=editBtn]").unbind("click").live("click",function(){
		var adId = $(this).attr("adId");
		if(!adId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$.ajax({
			url: SystemProp.appServerUrl + "/phoenix/phoenix-category-ad!phoenixCategoryAdInfoJson.action",
			type: "POST",
			dataType: "json",
			data: {categoryAdId:adId},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var c = rs.data.phoenixCategoryAdInfo;
					if(c)
					{
						$("#adId").val(c.id);
						$("#title").val(c.title);
						$("#add-cate-select").val(c.categoryId);
						$("#sortOrder").val(c.sortOrder);
						$("#link").val(c.link);
					}
				}
			}
		});
		$("#adModDialog").fancybox();
		
	});
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var adId = $(this).attr("adId");
		if(!adId)
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
			url : SystemProp.appServerUrl + "/phoenix/phoenix-category-ad!deletePhoenixCategoryAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {categoryAdId:adId},
			success : callback1
		});
	});
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-title").val()!="名称")
		data.title = $("#s-title").val();
		data.categoryId = $("#cate-select").val();
		pageComm(1, posturl, data, writetablefn);
	});
	function initPage() {

		//得到所有栏目
		var callback = function(rs){
			if(rs.code&&rs.code==200){
				
				if(rs.data.phoenixCategoryList){
					$.each(rs.data.phoenixCategoryList,function(n,e){
						//全部栏目
						$("#cate-select").append("<option value=\""+e.id+"\">"+e.name+"</option>");	
						//编辑文章页面的栏目列表
						$("#add-cate-select").append("<option value=\""+e.id+"\">"+e.name+"</option>");
					});
				}
			}else{
				alert("error:"+rs.message);
			}
		};
		var data={};
		$.ajax({
			url: SystemProp.appServerUrl+"/phoenix/phoenix-category!manageChannelJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#adId").val(0);
		$("#adModDialog").fancybox();
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
			categoryId : "required",
			link : {
				url : true,
				required : true
			},
			sortOrder : {
				number : true,
				required : true
			}
		},
		submitHandler:checkPic
	});
	function checkPic()
	{
		var postUrl = SystemProp.appServerUrl+"/phoenix/phoenix-category-ad!checkPic.action";
		var data = form2object('submitForm');
		if($("#adId").val())
			data.categoryAdId = $("#adId").val();
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "adPic"  ],
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
					var temp = rs.data.tempFile;
					if(rs.data.checkPic=="0")
					{
						if(rs.message&&!confirm(rs.message))
							return;
						else
						{
							save(temp);
						}
					}
					else
					{
						save(temp);
					}
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
	function save(temp)
	{
		var postUrl = SystemProp.appServerUrl+"/phoenix/phoenix-category-ad!savePhoenixCategoryAdJson.action";
		var data = form2object('submitForm');
		if($("#adId").val())
			data.categoryAdId = $("#adId").val();
		data.tempFile = temp;
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "adPic"  ],
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
