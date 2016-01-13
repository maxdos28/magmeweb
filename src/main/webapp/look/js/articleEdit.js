//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-article!searchArticleJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		var disabled = "disabled";
		if(r.articleStatus=="5"||r.articleStatus=="3"||r.articleStatus=="2"||r.articleStatus=="0")
		{
			disabled = "";
		}
		trContent += "<td><input name='checkArticle' type='checkbox' "+disabled+" status='"+r.articleStatus+"' value='"+r.articleId+"'></td>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.smallPic+"'></td>";
		trContent += "<td>"+r.articleId+"</td>";
		trContent += "<td>"+r.categoryTitle+"</td>";
		trContent += "<td>"+strEmpty(r.itemTitle)+"<br>----------<br>"+strEmpty(r.cuser)+"</td>";
		trContent += "<td>"+trimDate(r.createTime)+"<br>"+trimTime(r.createTime)+"</td>";
		if(r.isTop==1)
		{
			trContent += "<td class='tLeft'><a target='_blank' href='"+SystemProp.staticServerUrl+"/appprofile/read/html5.html?aid=903&tid="+r.articleId+"&d=0#1'>"+r.title+"</a><br />[置顶]</td>";
		}
		else
		{
			trContent += "<td class='tLeft'><a target='_blank' href='"+SystemProp.staticServerUrl+"/appprofile/read/html5.html?aid=903&tid="+r.articleId+"&d=0#1'>"+r.title+"</a></td>";
		}
		if(r.memo&&r.memo.length>20)
		{
			trContent += "<td class='tLeft'>"+r.memo.substring(0,15)+"......</td>";
		}
		else
		{
			trContent += "<td class='tLeft'>"+strEmpty(r.memo)+"</td>";
		}
		if(r.articleStatus==0)
		{
			trContent += "<td>下架</td>";
		}
		else if(r.articleStatus==1)
		{
			trContent += "<td>已上架</td>";
		}
		else if(r.articleStatus==2)
		{
			trContent += "<td>待审核</td>";
		}
		else if(r.articleStatus==3)
		{
			trContent += "<td>定时</td>";
		}
		else if(r.articleStatus==4)
		{
			trContent += "<td>未通过</td>";
		}
		else if(r.articleStatus==5)
		{
			trContent += "<td>待一审</td>";
		}
		else if(r.articleStatus==6)
		{
			trContent += "<td>待二审</td>";
		}
		else if(r.articleStatus==7)
		{
			trContent += "<td>待终审</td>";
		}
		else if(r.articleStatus==8)
		{
			trContent += "<td>待上架</td>";
		}
		if(r.articleStatus==3)
		{
			if(r.planTime)
			{
				if(r.planTime==13)
				{
					trContent += "<td>"+trimDate(r.planDate)+"<br>6:45 [定时]</td>";
				}else if(r.planTime==23)
				{
					trContent += "<td>"+trimDate(r.planDate)+"<br>11:45 [定时]</td>";
				}else if(r.planTime==33)
				{
					trContent += "<td>"+trimDate(r.planDate)+"<br>16:45 [定时]</td>";
				}else
				{
					trContent += "<td></td>";
				}
			}
			else
			{
				trContent += "<td></td>";
			}
			
		}
		else
		trContent += "<td>"+trimDate(r.publishDate)+"<br>"+trimTime(r.publishDate)+"</td>";
		trContent += "<td>"+numEmpty(r.moreCount)+"</td>";
		trContent += "<td class='tRight'>";
		//待审核和未通过可以修改
		trContent += "<a name='relLookBtn' status="+r.articleStatus+" articleId="+r.articleId+" class='btn' href='#'>关联</a>" ;
		trContent += "<a name='editBtn' status="+r.articleStatus+" class='btn' articleId="+r.articleId+"  href='#'>编辑</a>" ;
		if(r.articleStatus==2||r.articleStatus==4)
		{
			trContent += "<a name='delBtn' class='del' articleId="+r.articleId+" href='#'>删除</a>";
		}	
				
		trContent += "</td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-status").val()!=-1)
			data.status = $("#s-status").val();
		if($("#s-category").val()!=-1)
			data.categoryId = $("#s-category").val();
		if($("#s-item").val()!=-1)
			data.itemId = $("#s-item").val();
		if($("#s-title").val()!="名称")
			data.title = $("#s-title").val();
		if($("#s-cuser").val()!="上传用户")
			data.cuser = $("#s-cuser").val();
		pageComm(1, posturl, data, writetablefn);
	});
	//编辑
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var articleId = $(this).attr("articleId");
		if(!articleId)
		{
			alert("无ID");
			return;
		}
		var status = $(this).attr("status");
		if(status==2||status==4||status==0)
		{
			$("#saveBtn").show();
		}
		else
		{
			$("#saveBtn").hide();
		}
		$("#submitForm")[0].reset();
		$("#aditemp").html("");
		$.ajax({
			url: SystemProp.appServerUrl + "/look/look-article!articleInfoJson.action",
			type: "POST",
			dataType: "json",
			data: {id:articleId},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var c = rs.data.articleInfo;
					if(c.articleInfo)
					{
						$("#id").val(c.articleInfo.id);
						$("#title").val(c.articleInfo.title);
						$("#memo").val(c.articleInfo.memo);
						$("#title2").val(c.articleInfo.title2);
						if(c.articleInfo.isTop&&c.articleInfo.isTop=="1")
							$("#isTop").attr("checked",true);
						else
							$("#isTop").attr("checked",false);
						if(c.itemIds)
						{
							$.each(c.itemIds,function(e,r){
								var em = $($("#aditemdiv").html());
								var sele = em.find("select[name=item]");
								sele.val(r.itemId);
								$("#aditemp").append(em);
							});
						}
					}
				}
			}
		});
		$("#pop100").fancybox();
		
	});
	//删除
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var articleId = $(this).attr("articleId");
		if(!articleId)
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
			url : SystemProp.appServerUrl + "/look/look-article!deleteArticleJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:articleId},
			success : callback1
		});
	});
	//多看看
	$("a[name=relLookBtn]").unbind("click").live("click",function(){
		var articleId = $(this).attr("articleId");
		if(!articleId)
		{
			alert("无ID");
			return;
		}
		var status = $(this).attr("status");
		if(status==2||status==4)
		{
			$("#addLookBtn").show();
		}
		else
		{
			$("#addLookBtn").hide();
		}
		$("#look-articleId").val(articleId);
		$("#pop011").fancybox();
		$("#looktbody").html("");
		function callback1(rs) {
			if (rs.code != 200) {
				alert("得到多看看失败");
			} else {
				if(!rs.data.manyLookList)
					return;
				$.each(rs.data.manyLookList,function(e,r){
					var trContent = "<tr>";
					trContent += "<td><input name='id' type='hidden' value='"+r.id+"'><input name='sortOrder' class='g30 tRight' type='text' value='"+r.sortOrder+"'></td>";
					trContent += "<td><input name='title' class='g140 tCenter' type='text' value='"+r.title+"'></td>";
					trContent += "<td><select name='type' class='g80'><option value='1'>文章</option><option value='2'>图片</option><option value='3'>视频</option><option value='4'>购买</option></select></td>";
					trContent += "<td><input name='url' style='width:100%;' type='text' value='"+r.url+"'></td>";
					trContent += "<td>";
					if(status==2||status==4)
					{
						trContent += "<a name='saveLookBtn' class='remove' href='#'>保存</a>" ;
						//if(r.articleStatus==1)
						//	trContent += "<a name='changeLookStatusBtn' class='btn' href='#'>下架</a>" ;
						//else
						//	trContent += "<a name='changeLookStatusBtn' class='btn' href='#'>上架</a>" ;
						trContent += "<a name='delLookBtn' class='btn' href='#'>删除</a>";
					}	
					trContent += "</td>";
					trContent += "</tr>";
					var tr = $(trContent)
					var st = tr.find("select[name=type]");
					st.val(r.type);
					$("#looktbody").append(tr);
				});
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-article!getManyLook.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:articleId},
			success : callback1
		});
	});
	//多看看上架下架
	$("a[name=changeLookStatusBtn]").unbind("click").live("click",function(){
		var manyLookId = $(this).parent().parent().find("input[name=id]").val();
		if(!manyLookId)
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
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-article!changeStatusManyLook.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {manyLookId:manyLookId},
			success : callback1
		});
	});
	//多看看保存
	$("a[name=saveLookBtn]").unbind("click").live("click",function(){
		var saveBtn = $(this);
		var idinput = $(this).parent().parent().find("input[name=id]");
		var articleId = $("#look-articleId").val();
		if(!articleId)
		{
			alert("无文章ID");
			return;
		}
		var sortOrder = $(this).parent().parent().find("input[name=sortOrder]").val();
		var manyType = $(this).parent().parent().find("select[name=type]").val();
		var manyTitle = $(this).parent().parent().find("input[name=title]").val();
		var manyUrl = $(this).parent().parent().find("input[name=url]").val();
		if(!sortOrder||!manyType||!manyTitle||!manyUrl)
		{
			alert("排序,类型,名称,URL必须填写");
			return;
		}
		var data = {};
		data.id = articleId;
		data.sortOrder = sortOrder;
		data.manyType = manyType;
		data.manyTitle = manyTitle;
		data.manyUrl = manyUrl;
		data.manyLookId = idinput.val();
		$.ajax({
			url: SystemProp.appServerUrl + "/look/look-article!saveManyLook.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					alert("保存成功");
					saveBtn.attr("class","remove");
					if(!idinput.val())
					{
						idinput.val(rs.data.manyLookId);
					}
				}
			}
		});
		$("#pop100").fancybox();
		
	});
	//多看看删除
	$("a[name=delLookBtn]").unbind("click").live("click",function(){
		var tr = $(this).parent().parent();
		var manyLookId = tr.find("input[name=id]").val();
		if(!manyLookId)
		{
			tr.remove();
			return;
		}
		if(!confirm("是否删除?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert("操作失败");
			} else {
				alert("操作成功");
				tr.remove();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-article!deleteManyLook.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {manyLookId:manyLookId},
			success : callback1
		});
	});
	//多看看添加
	$("#addLookBtn").unbind("click").live("click",addLook);
	function addLook(){
			var trContent = "<tr>";
			trContent += "<td><input name='id' type='hidden' value=''><input name='sortOrder' class='g30 tRight' type='text' value=''></td>";
			trContent += "<td><input name='title' class='g140 tCenter' type='text' value=''></td>";
			trContent += "<td><select name='type' class='g80'><option value='1'>文章</option><option value='2'>图片</option><option value='3'>视频</option><option value='4'>购买</option></select></td>";
			trContent += "<td><input name='url' style='width:100%;' type='text' value=''></td>";
			trContent += "<td>";
			trContent += "<a name='saveLookBtn' class='btn' href='#'>保存</a>" ;
			//trContent += "<a name='changeLookStatusBtn' class='btn' href='#'>上架</a>" ;
			trContent += "<a name='delLookBtn' class='btn' href='#'>删除</a>";
			trContent += "</td>";
			trContent += "</tr>";
			$("#looktbody").append(trContent);
	}
	//多看看关闭
	$("#closeLookBtn").unbind("click").live("click",function(){
		$.fancybox.close();
	});
	function initPage() {

		//分类列表
		$("#s-category").html("<option value='-1'>全部分类</option>");
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
						$("#s-category").append("<option value='"+r.id+"'>"+r.title+"</option>");
					});
				}
			}
		});

		//栏目列表
		var sitem = $("#s-item");
		sitem.html("<option value='-1'>全部栏目</option>");
		var is = $("#aditemdiv select[name=item]");
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
		$("#id").val(0);
		$("#pop100").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//增加栏目
	$("#additemBtn").unbind("click").live("click",function(){
		$("#aditemp").append($("#aditemdiv").html());
	});
	//删除栏目
	$("a[name=deletePutBtn]").unbind("click").live("click",function(){
		$(this).parent().remove();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		$("#submitForm").submit();
	});
	$("#submitForm").validate({
		rules : {
			title : "required",
			memo : "required",
			sortOrder : {
				number : true,
				required : true
			},
			color : "required"
		},
		submitHandler:save
	});
	function save()
	{
		var itemData = getItemStr();
		if(!itemData)
			return;
		var postUrl = SystemProp.appServerUrl+"/look/look-article!saveArticleJson.action";
		var data = form2object('submitForm');
		data.itemIds = itemData;
		if($("#isTop").attr("checked"))
			data.isTop = "1";
		else
			data.isTop = "0";
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "smallPic1","smallPic2","articleZip" ],
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
	function getItemStr()
	{
		var em = $("#aditemp").find("em");
		if(!em)
		{
			alert("请添加栏目");
			return;
		}
		var str = "";
		var check = {};
		var c = false;
		$.each(em,function(e,r){
			var sel = $(r).find("select[name=item]");
			var v = sel.val();
			if(!v)
				return true;		
			if(check[v])
			{
				alert("栏目不能重复");
				c=true;
				return false;
			}
			else
			{
				check[v] = v;
			}
			if(str.length>0)
				str+=",";
			str+=v;
		});
		if(c)
			return;
		if(str.length==0)
		{
			alert("请添加栏目");
			return;
		}
		return str;
	}
});
