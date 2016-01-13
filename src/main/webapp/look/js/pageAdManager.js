//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-ad-manager-by-page!searchPageAdJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.adName)+"</td>";
		trContent += "<td>"+strEmpty(r.totalName)+"</td>";
		trContent += "<td>"+trimDate(r.startDate)+"</td>";
		trContent += "<td>"+trimDate(r.endDate)+"</td>";
		if(r.adStatus==1)
		{
			trContent += "<td>上架</td>";
		}
		else
		{
			trContent += "<td>下架</td>";
		}
		trContent += "<td>";
		if(r.adStatus==1)
			trContent += "<a name='changeStatusBtn' mobileAdDetailId="+r.mobileAdDetailId+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' mobileAdDetailId="+r.mobileAdDetailId+" class='btn' href='#'>上架</a>" ;
		trContent += "<a name='editBtn' class='btn' mobileAdDetailId="+r.mobileAdDetailId+" href='#'>编辑</a><a name='delBtn' class='del' mobileAdDetailId="+r.mobileAdDetailId+" href='#'>删除</a>";
		if(r.appId==904)
			trContent += "<a name='toNeteaseBtn' mobileAdDetailId="+r.mobileAdDetailId+" adName='"+r.adName+"' class='btn' href='#'>上传到网易</a>" ;
		trContent += "</td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-from-date").val()!="开始日期")
			data.startDate = $("#s-from-date").val();
		if($("#s-end-date").val()!="结束日期")
			data.endDate = $("#s-end-date").val();
		if($("#s-status").val()!="-1")
			data.status = $("#s-status").val();
		if($("#s-adTotalId").val()!="-1")
			data.adTotalId = $("#s-adTotalId").val();
		if($("#s-title").val()!="名称")
			data.title = $("#s-title").val();	
		
		pageComm(1, posturl, data, writetablefn);
	});
	//上传到网易
	$("a[name=toNeteaseBtn]").unbind("click").live("click",function(){
		var mobileAdDetailId = $(this).attr("mobileAdDetailId");
		if(!mobileAdDetailId)
		{
			alert("无ID");
			return;
		}
		var adName = $(this).attr("adName");
		if(!confirm("是否要上传到网易?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert(rs.message);
			} else {
				alert("已加入到上传队列中");
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!toNeteaseAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {mobileAdDetailId:mobileAdDetailId,dname:adName},
			success : callback1
		});
	});
	//上架下架
	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var mobileAdDetailId = $(this).attr("mobileAdDetailId");
		if(!mobileAdDetailId)
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
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!changeStatusPageAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {mobileAdDetailId:mobileAdDetailId},
			success : callback1
		});
	});
	//修改
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var mobileAdDetailId = $(this).attr("mobileAdDetailId");
		if(!mobileAdDetailId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$("#mobileAdDetailId").val(mobileAdDetailId);
		$("#adputp").html("");
		$("#aditemp").html("");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!pageAdInfoJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {mobileAdDetailId:mobileAdDetailId},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
				}
				else
				{
					var info = rs.data.pageAdInfo;
					if(info&&info.mobileAdDetailInfo)
					{
						$("#dname").val(numEmpty(info.mobileAdDetailInfo.dname));
						$("#url").val(strEmpty(info.mobileAdDetailInfo.url));
						$("#adCompanyUrl").val(strEmpty(info.mobileAdDetailInfo.adCompanyUrl));
						$("#totalAddId").val(info.mobileAdDetailInfo.totalAddId);
						//$("#position").val(numEmpty(info.mobileAdDetailInfo.position));
						$("#onlineTime").val(trimDate(info.mobileAdDetailInfo.onlineTime));
						$("#offlineTime").val(trimDate(info.mobileAdDetailInfo.offlineTime));
						var contentType=info.mobileAdDetailInfo.contentType;
						//文章
						if(contentType==1)
						{
							$("input[type=radio][name=contentType][value=1]").attr("checked",true);
							$("#putdiv").hide();
							$("#itemdiv").show();
							$("#catediv").hide();
							if(info.mobileAdPutList)
							{
								$.each(info.mobileAdPutList,function(e,r){
									var em = $($("#aditemdiv").html());
									var sele = em.find("select[name=itemapp]");
									sele.val(r.appid);
									getitem(sele);
									sele.next().val(r.publicationId);
									sele.next().next().val(r.position);
									$("#aditemp").append(em);
								});
							}
						}
						//杂志
						else if(contentType==2)
						{
							$("input[type=radio][name=contentType][value=2]").attr("checked",true);
							$("#putdiv").show();
							$("#itemdiv").hide();
							$("#catediv").hide();
							if(info.mobileAdPutList)
							{
								$.each(info.mobileAdPutList,function(e,r){
									var em = $($("#adputdiv").html());
									var sele = em.find("select[name=app]");
									sele.val(r.appid);
									getpub(sele);
									sele.next().val(r.publicationId);
									sele.next().next().val(r.position);
									$("#adputp").append(em);
								});
							}
						}
						//分类
						else
						{
							$("input[type=radio][name=contentType][value=3]").attr("checked",true);
							$("#putdiv").hide();
							$("#itemdiv").hide();
							$("#catediv").show();
							$("#category").val(numEmpty(info.mobileAdDetailInfo.categoryId));
							$("#cate-page").val(numEmpty(info.mobileAdDetailInfo.position));
						}
						
					}
					
				}
			}
		});
		$("#pop004").fancybox();
		
	});
	//删除
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var mobileAdDetailId = $(this).attr("mobileAdDetailId");
		if(!mobileAdDetailId)
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
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!deletePageAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {mobileAdDetailId:mobileAdDetailId},
			success : callback1
		});
	});
	function initPage() {

		//广告分类列表
		$("#s-adTotalId").html("<option value='-1'>全部分类</option>");
		$("#totalAddId").html("<option></option>");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!getAdTotalListJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			success : function(rs){
				if (rs.code != 200) {
					alert("广告分类获取失败");
				} else {
					if(rs.data.adTotalList)
					$.each(rs.data.adTotalList,function(i,r){
						$("#s-adTotalId").append("<option value='"+r.id+"'>"+r.dname+"</option>");
						$("#totalAddId").append("<option value='"+r.id+"'>"+r.dname+"</option>");
					});
				}
			}
		});

		//APP列表
		$("#adputdiv select[name=app]").html("<option></option>");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!getAppListJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			success : function(rs){
				if (rs.code != 200) {
					alert("APP获取失败");
				} else {
					if(rs.data.appList)
					$.each(rs.data.appList,function(i,r){
						$("#adputdiv select[name=app]").append("<option value='"+r.id+"'>"+r.name+"</option>");
					});
				}
			}
		});
		//栏目的app列表
		$("#aditemdiv select[name=itemapp]").html("<option></option>");
		$("#aditemdiv select[name=itemapp]").append("<option value='901'>凤凰周刊</option>");
		$("#aditemdiv select[name=itemapp]").append("<option value='903'>LOO客</option>");
		//LOOK分类加载
		$("#category").html("<option></option>");
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
						$("#category").append("<option value='"+r.id+"'>"+r.title+"</option>");
					});
				}
			}
		});

		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#s-from-date"),nowDate);
	      datePickerFun2($("#s-end-date"),nowDate);
	      datePickerFun1($("#onlineTime"),nowDate);
	      datePickerFun2($("#offlineTime"),nowDate);

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
	      function datePickerFun2($dateInput,date){
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
	}

	$("select[name=app]").unbind("change").live("change", function() {
		getpub($(this));
	});
	$("select[name=itemapp]").unbind("change").live("change", function() {
		getitem($(this));
	});
	//得到杂志列表
	function getpub(btn)
	{
		var appId = btn.val();
		var ps = btn.next();
		ps.html("<option></option>");
		if(appId)
		{
			$.ajax({
				url : SystemProp.appServerUrl + "/look/look-ad-manager-by-page!getPublicationListJson.action",
				type : "POST",
				async : false,
				data : {appId:appId},
				dataType : "json",
				success : function(rs){
					if (rs.code != 200) {
						alert("杂志获取失败");
					} else {
						if(rs.data.publicationList)
						$.each(rs.data.publicationList,function(i,r){
							ps.append("<option value='"+r.publicationId+"'>"+r.pname+"</option>");
						});
					}
				}
			});
		}
	}
	//得到栏目列表
	function getitem(btn)
	{
		var appId = btn.val();
		var ps = btn.next();
		ps.html("<option></option>");
		if(appId&&appId=='901')
		{
			phoenixItem(ps,appId);
		}
		if(appId&&appId=='903')
		{
			lookItem(ps);
		}
	}
	//LOOK栏目列表
	function lookItem(is)
	{
		//栏目列表
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
							is.append("<optgroup label='"+r.title+"'>");
							$.each(rs.data.itemList,function(i1,r1){
								if(r1.type==2)
									return true;
								if(r1.parentId&&r1.parentId==parentId)
								{
									is.append("<option value='"+r1.id+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+r1.title+"</option>");
								}
							});
						    is.append("</optgroup>");
						}
						else
						{
							is.append("<option value='"+r.id+"'>"+r.title+"</option>");
						}
					});
				}
			}
		});
	}
	//凤凰栏目列表
	function phoenixItem(is,appId)
	{
		//栏目列表
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-item!getPhoenixItemByAppIdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {"appId":appId},
			success : function(rs){
				if (rs.code != 200) {
					alert("栏目获取失败");
				} else {
					$.each(rs.data.phoenixCategoryList,function(i,r){
						//杂志类栏目不添加
						if(r.type==2)
							return true;
						//有子栏目的栏目不添加
						if(r.isParent&&r.isParent==1)
							return true;
						is.append("<option value='"+r.id+"'>"+r.name+"</option>");
					});
				}
			}
		});
	}
	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#mobileAdDetailId").val("0");
		$("#adputp").html("");
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
	//内容类型选择
	$("input[type=radio][name=contentType]").bind("click", function() {
		//文章
		if($(this).val()==1)
		{
			$("#putdiv").hide();
			$("#itemdiv").show();
			$("#catediv").hide();
		}
		//杂志
		else if($(this).val()==2)
		{
			$("#putdiv").show();
			$("#itemdiv").hide();
			$("#catediv").hide();
		}
		//分类
		else
		{

			$("#putdiv").hide();
			$("#itemdiv").hide();
			$("#catediv").show();
		}
	});

	//删除关联
	$("a[name=deletePutBtn]").unbind("click").live("click",function(){
		$(this).parent().remove();
	});
	//增加关联
	$("#addPutBtn").unbind("click").live("click",function(){
		$("#adputp").append($("#adputdiv").html());
	});
	$("#additemBtn").unbind("click").live("click",function(){
		$("#aditemp").append($("#aditemdiv").html());
	});
	$("#submitForm").validate({
		rules : {
			dname : "required",
			totalAddId : "required",
			contentType : "required",
			adZip :{
				required : function (){if(!$("#mobileAdDetailId").val()||$("#mobileAdDetailId").val()=="0")return true;else return false;}
			},
			
			onlineTime :{
				custDate:true,
				required : true
			},
			offlineTime :{
				custDate:true,
				required : true
			},
			position : {
				digits : true,
				required : function(){if($("input[type=radio][name=contentType]:checked").val()==3)return true;return false;}
			},
			category : {
				required : function(){if($("input[type=radio][name=contentType]:checked").val()==3)return true;return false;}
			}
		},
		submitHandler:save
	});
	function save()
	{
		var contentType = $("input[type=radio][name=contentType]:checked").val();
		var detailData;
		if(contentType==1)
		{
			//检查是否选择了栏目
			detailData = getItemStr();
		}
		else if(contentType==2)
		{

			//检查是否选择了杂志
			detailData = getPutStr();
		}
		else
		{
			detailData="category";
		}
		if(!detailData)
			return;
		var postUrl = SystemProp.appServerUrl+"/look/look-ad-manager-by-page!savePageAdJson.action";
		var data = form2object('submitForm');
		data.mobileAdDetailId = $("#mobileAdDetailId").val();
		data.adPutStr = detailData;
		if(contentType==3)
		{
			data.categoryId = $("#category").val();
			data.position = $("#cate-page").val();
		}
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "adZip" ],
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
	
	function getPutStr()
	{
		var em = $("#adputp").find("em");
		if(!em)
		{
			alert("请添加关联");
			return;
		}
		var str = "";
		$.each(em,function(e,r){
			var sel = $(r).find("select[name=app]");
			if(!sel.val())
				return true;
			if(!sel.next().val())
				return true;
			if(sel.next().next().val().length==0||sel.next().next().val()=="页码")
			{
				return true;
			}				
			if(str.length>0)
				str+=";";
			str+=sel.val();
			str+=",";
			str+=sel.next().val();
			str+=",";
			str+=sel.next().next().val();
		});
		if(str.length==0)
		{
			alert("请添加关联");
			return;
		}
		return str;
	}
	function getItemStr()
	{
		var em = $("#aditemp").find("em");
		if(!em)
		{
			alert("请添加关联");
			return;
		}
		var str = "";
		$.each(em,function(e,r){
			var sel = $(r).find("select[name=itemapp]");
			if(!sel.val())
				return true;
			if(!sel.next().val())
				return true;
			if(sel.next().next().val().length==0||sel.next().next().val()=="页码")
			{
				return true;
			}				
			if(str.length>0)
				str+=";";
			str+=sel.val();
			str+=",";
			str+=sel.next().val();
			str+=",";
			str+=sel.next().next().val();
		});
		if(str.length==0)
		{
			alert("请添加关联");
			return;
		}
		return str;
	}
});
