//分页功能
$(function() {
	// 初始页面
	initPage();
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td><input type='checkbox' name='plan' value='"+r.articleId+"'></td>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.smallPic+"'></td>";
		trContent += "<td>"+r.articleId+"</td>";
		trContent += "<td>"+strEmpty(r.categoryTitle)+"</td>";
		trContent += "<td>"+strEmpty(r.itemTitle)+"<br>----------<br>"+strEmpty(r.cuser)+"</td>";
		trContent += "<td>"+trimDate(r.createTime)+"<br>"+trimTime(r.createTime)+"</td>";
		if(r.isTop==1)
		{
			trContent += "<td class='tLeft'>"+r.title+"<br />[置顶]</td>";
		}
		else
		{
			trContent += "<td class='tLeft'>"+r.title+"</td>";
		}
		if(r.memo&&r.memo.length>50)
		{
			trContent += "<td class='tLeft'>"+r.memo.substring(0,25)+"......</td>";
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
		trContent += "<td></td>";
		trContent += "<td><a name='editBtn' class='btn' articleId="+r.articleId+"  href='#'>编辑</a>";
		if(r.articleStatus=="3")
		{
			//取消定时上架
			trContent += "<a name='cancelPlanBtn' articleId="+r.articleId+" class='btn' href='#'>取消定时</a>" ;
		}
		trContent += "</td>";
		trContent += "</tr>";
		return trContent;
	}
	function search()
	{
		$("#tbodyContext").html("");
		var data = {};
		if($("#s-title").val()!="名称")
			data.title = $("#s-title").val();
		if($("#s-category").val()!="-1")
			data.categoryId = $("#s-category").val();
		if($("#s-item").val()!="-1")
			data.itemId = $("#s-item").val();
		if($("#s-plan").attr("checked"))
				data.plan = 1;
		function callback1(rs) {
			if (rs.code != 200) {
				alert("操作失败");
			} else {
				var articleList = rs.data.articleList;
				if(articleList)
				{
					$.each(articleList,function(e,r){
						var content = writetablefn("",r);
						$("#tbodyContext").append(content);
					});
				}
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-plan-article!searchPlanArticleJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : data,
			success : callback1
		});
	}
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		search();
	});
	//保存
	$("#saveBtn").unbind("click").live("click",function(){
		var checkIds = $("input[type=checkbox][name=plan]:checked");
		if(!checkIds||checkIds.length==0)
		{
			alert("请选择文章");
			return;
		}
		var ids = "";
		for(i=0;i<checkIds.length;i++)
		{
			if(i>0)
				ids+=",";
			ids+=$(checkIds[i]).val();
		}
		var planDate = $("#planDate").val();
		var planTime = $("#planTime").val();
		if(!planDate||planDate=="上架日期")
		{
			alert("请选择上架日期");
			return;
		}
		if(!planTime||planTime=="-1")
		{
			alert("请选择上架时段");
			return;
		}
		if(!confirm("是否确认?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert(rs.message);
			} else {
				alert("操作成功");
				search();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-plan-article!savePlanArticleJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {articleIds:ids,planDate:planDate,planTime:planTime},
			success : callback1
		});
	});
	//编辑
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var articleId = $(this).attr("articleId");
		if(!articleId)
		{
			alert("无ID");
			return;
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
	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//增加栏目
	$("#additemBtn").unbind("click").live("click",function(){
		var sel = $("#aditemp").find("select[name=item]");
		//栏目限定最多2个
		if(sel&&sel.length>=2)
			return;
		$("#aditemp").append($("#aditemdiv").html());
	});
	//删除栏目
	$("a[name=deletePutBtn]").unbind("click").live("click",function(){
		$(this).parent().remove();
	});
	//取消定时上架
	$("a[name=cancelPlanBtn]").unbind("click").live("click",function(){
		var articleId = $(this).attr("articleId");
		if(!articleId)
		{
			alert("无ID");
			return;
		}
		if(!confirm("是否确认?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert(rs.message);
			} else {
				alert("操作成功");
				search();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-article!cancelPlanJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:articleId},
			success : callback1
		});
	});
	//保存
	$("#saveArticleBtn").bind("click", function() {
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
	function initPage() {
		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#planDate"),nowDate);
	      
	        //时间控件
	      //input_datepicker------------------------------------
	      function datePickerFun1($dateInput,date){
	        if(!date) date = nowDate;
	        $dateInput.DatePicker({
	          format:'Y-m-d',
	          date: date,
	          current: date,
	          starts: 0,
	          position: 'bottom',
	          onBeforeShow: function(){
	            $dateInput.DatePickerSetDate($dateInput.val()||date, true);
	          },
	          onChange: function(formated, dates){
	            $dateInput.val(formated);
	    //        $dateInput.DatePickerHide();
	          }
	        });
	      }
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
	      search();
	}
});
