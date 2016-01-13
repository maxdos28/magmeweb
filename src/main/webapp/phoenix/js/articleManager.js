$(function() {
	init();
});
var pageCount;
var currentPage;
//保存所有栏目
var categoryArr = new Array();
function init() {

	var nowDate = new Date(),
		$sDate = $("#from-date"),
		$eDate = $("#end-date");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	//初始化下拉框
	$("#free-select").val("");
	$("#cate-select").val("");
	$.ajax({
		type : "POST",
		url : SystemProp.appServerUrl
				+ "/phoenix/phoenix-article!searchArticle.action", // 此处是调用后台的ACTION
		// data : "ws.gameid="+gameid,
		dataType : "json",
		success : function(msg) {
			initPagination(msg);// data是返回来的json数据
		}
	});
	
	//得到所有栏目
	var callback = function(rs){
		if(rs.code&&rs.code==200){
			
			if(rs.data.phoenixCategoryList){
				$.each(rs.data.phoenixCategoryList,function(n,e){
					categoryArr.push(e);
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
	//查询文章
	$("#search-article").bind("click",function(){
		var data = {
				"isFree":$("#free-select").val(),
				"categoryId":$("#cate-select").val(),
				"status":$("#status-select").val(),
				"fromDate":$("#from-date").val(),
				"endDate":$("#end-date").val(),
				"title":$("#title").val(),
				"currentPage" : 1
			};
		$.ajax({
			type : "POST",
			url : SystemProp.appServerUrl
					+ "/phoenix/phoenix-article!searchArticle.action", // 此处是调用后台的ACTION
			data : data,
			dataType : "json",
			success : function(msg) {
				initPagination(msg);// data是返回来的json数据
			}
		});
	});
	// 页面跳转
	$("#toPageOk").bind("click", function() {
		var currentPage = $("#toPageValue").val();
		if (currentPage > pageCount) {
			alert('超出最大页数');
			$("#toPageValue").val("");
			return false;
		}
		if (currentPage <= 0) {
			currentPage = 1
		}
		$("#eventListPage").html("");
		$("#eventListPage").pagination(pageCount, {
			num_edge_entries : 1, // 边缘页数
			num_display_entries : 20, // 主体页数
			callback : pageselectCallback,
			items_per_page : 1, // 每页显示1项
			current_page : currentPage - 1,
			prev_text : "前一页",
			next_text : "后一页"
		});
	});

	// 删除
	$("a[name='p_del']").live(
			"click",
			function() {
				if (!confirm('确认删除吗？'))
					return;
				var currObj = $(this);
				var articleId = currObj.attr("articleId");
				var data = {};
				data["id"] = articleId || "";
				var callback = function(rs) {
					if (rs.code && rs.code == 200) {
						pageComm(currentPage);
					} else {
						alert("错误:" + rs.message);
					}
				}
				$.ajax({
					url : SystemProp.appServerUrl
							+ "/phoenix/phoenix-article!delArticleJson.action",
					type : "POST",
					dataType : "json",
					async : false,
					data : data,
					success : callback
				});
			});
	// 推送
	$("a[name='p_push']").live(
			"click",
			function() {
				if (!confirm('确认推送吗？'))
					return;
				var currObj = $(this);
				var articleId = currObj.attr("articleId");
				var data = {};
				data["id"] = articleId || "";
				var callback = function(rs) {
					if (rs.code && rs.code == 200) {
						alert("推送成功");
					} else {
						alert("错误:" + rs.message);
					}
				}
				$.ajax({
					url : SystemProp.appServerUrl
							+ "/phoenix/phoenix-article!pushMessageJson.action",
					type : "POST",
					dataType : "json",
					async : false,
					data : data,
					success : callback
				});
			});
	// 添加
	$("#passBtn").bind("click", function() {
		$("#uploadArticleForm input").val("");
		$("#add-cate-select").val("0");
		$("#contentModDialog").fancybox();
		$("#save_content").text("新增");
	});
	// 编辑--查看
	$("a[name='p_edit']")
			.live(
					"click",
					function() {
						$("#uploadArticleForm input").val("");
						$("#add-cate-select").val("0");
						$("#uploadArticleForm input[name=id]").val($(this).attr("articleId"));
						$("#uploadArticleForm input[name=title]").val($(this).attr("title"));
						$("#uploadArticleForm input[name=description]").val($(this).attr("description"));
						$("#add-cate-select").val($(this).attr("categoryId"));
						// 初始化 end
						$("#contentModDialog").fancybox();
						$("#save_content").text("修改");
						
					});
	// 封面故事选择
	$("input[name='is-cover-story']")
			.live(
					"click",
					function() {
						var sort = $(this).parent().parent().next().find("input");
						if($(this).attr("checked")=="checked")
							sort.attr("disabled",false);
						else
						{
							sort.attr("disabled",true);
						}
					});
	// 保存
	$("a[name='p_update']")
			.live(
					"click",
					function() {
						var isCover = $(this).parent().parent().find("input[name=is-cover-story]");
						var sort = $(this).parent().parent().find("input[name=cover-story-sort]");
						var ic = "0";
						var s = "0";
						if(isCover.attr("checked")=="checked")
						{
							if(sort.val().length==0)
							{								
								alert("请输入排序");
								return;
							}
							ic = "1";
							s = sort.val();
						}
						
						$.ajax({
							type : "POST",
							url : SystemProp.appServerUrl
									+ "/phoenix/phoenix-article!updateArticleJson.action", // 此处是调用后台的ACTION
							data : {"id":$(this).attr("articleId"),"isCoverStory":ic,"coverStorySort":s},
							dataType : "json",
							success : function(msg) {
								if(msg.code&&msg.code==200)
								{
									alert("保存成功");
								}
								else
								{
									alert(msg.massage);
								}
							}
						});
					});
	// 审核
	$("a[name='p_audited']")
			.live(
					"click",
					function() {
						var status = $(this).attr("status");
						if(status&&status=='1')
							return;
						if(!confirm('确认要审核吗？'))
							return;
						$.ajax({
							type : "POST",
							url : SystemProp.appServerUrl
									+ "/phoenix/phoenix-article!auditedArticleJson.action", // 此处是调用后台的ACTION
							data : {"id":$(this).attr("articleId")},
							dataType : "json",
							success : function(msg) {
								if(msg.code&&msg.code==200)
								{
									alert("审核成功", function(){
										pageComm(currentPage);		
			                		});
								}
								else
								{
									alert(msg.massage);
								}
							}
						});
					});
	//增加内容
	$("#save_content").bind("click", function(e) {
		if($("#uploadArticleForm input[name=title]").val().length==0)
		{
			alert("请输入标题");
			return;
		}if($("#uploadArticleForm input[name=description]").val().length==0)
		{
			alert("请输入描述");
			return;
		}if($("#uploadArticleForm select[name=categoryId]").val().length==0)
		{
			alert("请选择栏目");
			return;
		}

		if($("#uploadArticleForm input[name=id]").val().length==0&&$("#uploadArticleForm input[name=articleImg]").val().length==0)
		{
			alert("请上传图片");
			return;
		}
		if($("#uploadArticleForm input[name=id]").val().length==0&&$("#uploadArticleForm input[name=articleZip]").val().length==0)
		{
			alert("请选择ZIP包");
			return;
		}
		e.preventDefault();
		$("#uploadArticleForm").submit();
	});
	//关闭编辑窗口
	$("#cancel_content").bind("click", function() {
		$.fancybox.close();
	});
	//收费免费选择
	$("#free-select").bind("change", function() {
		refreshCategory();
	});
	$("#uploadArticleForm").submit(function()
			{
		
		var postUrl = SystemProp.appServerUrl
		+ "/phoenix/phoenix-article!addArticleJson.action";
		if($("#uploadArticleForm input[name=id]").val().length>0)
			postUrl = SystemProp.appServerUrl
			+ "/phoenix/phoenix-article!updateArticleJson.action";
		
				$.ajaxFileUpload(
			            {
			                url : postUrl,
			                secureuri : false,
			                data : form2object('uploadArticleForm'),
			                fileElementId : ["articleZip","articleImg"],
			                content : $("#uploadArticleForm"),
			                dataType : "json",
			                async : true,
			                type : 'POST',
			                success : function(rs){
			                	if(!rs) return;
			                	if(rs.code != 200){
			                		alert(rs.message);
			                	}else{
			                		//modified by xw
			                		alert("保存成功！", function(){
			                			pageComm(currentPage);	
			                			$.fancybox.close();
			                		});           		
			                	}
			                },
			                //服务器响应失败处理函数
			                error : function (data, status, e) {			                	
			                	if(data.responseText.indexOf("\"code\":200"))
			                	{
			                		alert("保存成功！", function(){
			                			pageComm(currentPage);	
			                			$.fancybox.close();
			                		});
			                	}
			                	else
			                		alert("保存失败！");
			                }
			            }
			        );
			});
}
function refreshCategory()
{
	$("#cate-select").html("<option value=\"\">请选择</option>");
	var isFree = $("#free-select").val();
	$.each(categoryArr,function(n,e){
		if(isFree=='')
			$("#cate-select").append("<option value=\""+e.id+"\">"+e.name+"</option>");	
		else if(e.isFree==isFree)
		$("#cate-select").append("<option value=\""+e.id+"\">"+e.name+"</option>");	
	});
}
function initPagination(msg) {
	pageCount = msg.data.pageCount;
	$("#eventListPage").pagination(pageCount, {
		num_edge_entries : 1, // 边缘页数
		num_display_entries : 15, // 主体页数
		prev_text : "上页",
		next_text : "下页",
		items_per_page : 1, // 每页显示1项

		callback : function(page_index, jq) {
			pageselectCallback(page_index, jq);// 回调函数，把json数据传过去以便显示时取数
		}
	});
}

function pageselectCallback(page_id, jq) {
	$("#eventListPageadd").html("");
	$("#eventListPageadd")
			.append(
					"跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
	currentPage = page_id + 1;
	pageComm(page_id + 1);
	return false;
}
function pageComm(current) {
	$("#tbodyContent").html("");
	var data = {
		"isFree":$("#free-select").val(),
		"categoryId":$("#cate-select").val(),
		"status":$("#status-select").val(),
		"fromDate":$("#from-date").val(),
		"endDate":$("#end-date").val(),
		"title":$("#title").val(),
		"currentPage" : current
	};
	$.ajax({
				type : "POST",
				url : SystemProp.appServerUrl
						+ "/phoenix/phoenix-article!searchArticle.action", // 此处是调用后台的ACTION
				data : data,
				dataType : "json",
				success : function(msg) {

					var str = "";
					$
							.each(
									msg.data.list,
									function(n, e) {
										str += "<tr>";
										str += "<td><div><img src='"+SystemProp.staticServerUrl+e.imgUrl+"' /></div></td>";
										if(e.isCoverStory=="1")
										{
											str += "<td><label><input name='is-cover-story' type=\"checkbox\" checked=\"checked\"/></label></td>";
											str += "<td><label><input class=\"g50\" name='cover-story-sort' type=\"textfield\" value=\""+e.coverStorySort+"\"/></label></td>";
										}
										else
										{
											str += "<td><label><input name='is-cover-story' type=\"checkbox\" /></label></td>";
											str += "<td><label><input class=\"g50\" name='cover-story-sort' type=\"textfield\" value=\"\" disabled=1/></label></td>";
										}											
										str += "<td style='text-align:left'>" + e.title + "</td>";
										var desc = e.description;
										if(desc&&desc.length>22)
											desc = desc.substring(0,22)+"......";
										str += "<td style='text-align:left'>" + desc + "</td>";
										if(e.status=='1')
											str += "<td>已审核</td>";
										else
											str += "<td>未审核</td>";
										if(e.isPush=='1')
											str += "<td>已推送</td>";
										else
											str += "<td>未推送</td>";
										str += "<td>" + e.categoryName + "</td>";
										if(e.createTime&&e.createTime.length>10)
											str += "<td>" + e.createTime.substring(0,10) + "</td>";
										else
											str += "<td>" + e.createTime + "</td>";
											
										str += "<td><a class='btn' href='#' articleId='"
												+ e.id
												+ "' name=\"p_edit\" title=\"" + e.title + "\" description=\"" + e.description + "\" categoryId=\"" + e.categoryId + "\">编辑</a><a class='btn' name='p_update' articleId='"
												+ e.id
												+ "'  href='#'>保存</a><a class='btn' name='p_audited' status='"+e.status+"' articleId='"
												+ e.id
												+ "'  href='#'>审核</a><a class='btn' name='p_push' articleId='"
												+ e.id
												+ "'  href='#'>推送</a><a class='del' name='p_del' articleId='"
												+ e.id
												+ "'  href='#'>删除</a></td>";
										str += "</tr>";
									});
					$("#tbodyContent").html(str);
					fnReadyTable();
				}
			});
}

