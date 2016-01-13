
$(function(){ 

	datePickerFun($("#sideAdBeginTime"));
	datePickerFun($("#sideAdEndTime"));

	$("#submitSearchFormBtn").click(function(){
		initPage();
	});
	$("#manageAdminEmail").click(function(){
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-admin-email!to.action",
			type : "POST",
			dataType : "html",
			data: {},
			success: function(result){
				$("body").append(result);
			}
		});
	});
	

	$("a[name='updateStatusAdSide']").unbind().live("click",function(e){
		var obj=$(this);
		var data={"id":obj.attr("adSideId"),"status":obj.attr("status")};
		
		var callback=function(rs){
			if(rs.code==200){
				alert("操作成功");
				var ad=rs.data.advertise;
				var html="";
				if(data.status==2){
					html='<a name="updateStatusAdSide" adSideId="'+data.id+'" status="3" href="javascript:void(0)">下架</a>';
				} else {
					html='<a name="updateStatusAdSide" adSideId="'+data.id+'" status="2" href="javascript:void(0)">发布</a>';
				}
				obj.parents("tr").find("td[name='updateStatus']").html(html);
			}else{
				alert(rs.message);
			}		 
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-sidebar!updateStatusJson.action",
			type : "POST",
			dataType : "json",
			data:data,
			success: callback
		});		
	});

	$("#addSidebar").click(function(){
		showAddOrUpdate();
	});
	
	$("a[name='editAdSide']").unbind().live("click",function(e){
		e.preventDefault();
		var obj=$(this);
		showAddOrUpdate(obj);
	});	
	initPage();
});

function showAddOrUpdate(obj){
	var adSideId;
	if(obj){
		adSideId = obj.attr("adSideId");
	}
	var content = $("#fancybox-content");
	var dialogClose = $.fancybox.close;
	$.fancybox.close = function(){
		dialogClose();
		if($("#addAd")) $("#addAd").remove();			
	};
	var callback = function(result){
		$("body").append(result);
		datePickerFun($("#sideAdValidBeginTime"));
		datePickerFun($("#sideAdValidEndTime"));
		$addAd = $("#addAd");
		$addAd.fancybox();
		
		$("#editAdSideFormCancelBtn",content).click(function(){$.fancybox.close();});
		$("#editAdSideFormSubmitBtn",content).click(function(){
			var data=form2object('editAdSideForm');
			var callback = function(result){
				if (result.code == 200) {
					alert("保存成功");
					if(adSideId){
						var pa=obj.parents("tr").eq(0);
						if(data.id){
							pa.find("td[name='description']").text(data.description);
							//var categoryName=$("#editAdSideForm").find("select[name='categoryId'] option:selected").text();
							//pa.find("td[name='categoryName']").text(categoryName);
							pa.find("td[name='validBeginTime']").text(data.validBeginTime);
							pa.find("td[name='validEndTime']").text(data.validEndTime);
							//pa.find("td[name='pos']").text(data.pos);
						}else{
							turnPage(curPageNum);
						}
					} else {
						initPage();
					}
					$.fancybox.close();
				}else{
					alert(result.message);
				}
			}
			
			$.ajaxFileUpload(
				{
					url : SystemProp.appServerUrl+"/new-publisher/edit-sidebar!saveJson.action",
	                secureuri : false,
	                data : data,
	                fileElementId : "imageFile",
	                content : $("#editAdSideForm"),
	                dataType : "json",
	                success : callback,
	                //服务器响应失败处理函数
	                error : function (data, status, e) {
	                	alert("error");
	                }
	            }
	        );
		});
	};
	
	$.ajax({
		url: SystemProp.appServerUrl+"/new-publisher/edit-sidebar!editAjax.action",
		type : "POST",
		dataType : "html",
		data: {"id":adSideId},
		success: callback
	});
}

function initPage(){
	$("#eventListPage").pagination(pageCount, {
		num_edge_entries: 1, //边缘页数
		num_display_entries: 20, //主体页数
		callback:pageselectCallback,
		items_per_page: 1, //每页显示1项
		prev_text: "前一页",
		next_text: "后一页"
	});
}

function pageselectCallback(page_id, jq){
	$("#eventListPageadd").html("");
	$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
	turnPage(page_id+1);
	return false;
}

function turnPage(pageNum){
	curPageNum = pageNum;
	var data = form2object('searchForm');
	data.pageNum = pageNum;
	data.pageSize = pageSize;
	var oldPageCount = pageCount;
	var callback = function(rs){
		$("#tablePageBarContainer").html(rs);
		if(oldPageCount != pageCount){
			initPage();
		} else {
			fnReadyTable();
		}
	}
	
	$.ajax({
		url:SystemProp.appServerUrl+"/new-publisher/edit-sidebar!getListAjax.action",
		type : "POST",
		data : data,
		success: callback
	});
}

//页面跳转
$("#toPageOk").live("click",function(){
	var currentPage = $("#toPageValue").val();
	if(currentPage>pageCount) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
	if(currentPage<=0){currentPage=1} 
	$("#eventListPage").html("");
	$("#eventListPage").pagination(pageCount, {
		num_edge_entries: 1, //边缘页数
		num_display_entries: 20, //主体页数
		callback:pageselectCallback,
		items_per_page: 1, //每页显示1项
		current_page:currentPage-1, 
		prev_text: "前一页",
		next_text: "后一页"
	});
});


function datePickerFun($dateInput){
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var date= myDate.getFullYear()+"-"+month+"-"+datetime; 

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
			//$dateInput.DatePickerHide();
		}
	});
}	
