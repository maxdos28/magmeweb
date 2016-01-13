$(function(){

	$('#btn_publish').click(function(){window.open(SystemProp.appServerUrl + '/index!publish.action?timestamp='+((new Date).getTime()));});
	$('#btn_preview').click(function(){window.open(SystemProp.appServerUrl + '/index!preview.action?timestamp='+((new Date).getTime()));});

	$("#btn_search").click(function(e){
		initPage();
	});
	$("a[name='editEvent']").unbind().live("click",function(e){
		e.preventDefault();
		var obj=$(this);
		showAddOrUpdate(obj);
	});	

	datePickerFun($("#createdDate"));
	//添加标签
	$("#newTag").unbind("keyup").live("keyup", function(e){
		if(e.keyCode == 13){
			onAddTag();
		}
	});
	$("#addTagButton").unbind("click").live("click", onAddTag);
	
	$("#tagListLi li:not(.current):not(.add)").unbind("click").live("click", function(){
		$(this).toggleClass("change");
	});
	
	initPage();
});
function onAddTag(){
	var textPageForm =$("#adminEventEdit");
	var name= textPageForm.find("#newTag").val();
	if(name){
		if(name=='添加新标签'){
			alert("标签名称不能为空");
			return;
		}
	}else{
		alert("标签名称不能为空");
		return;
	}
	var repeat = '';
	var names = name.split(",");
	for(var i = 0; i < names.length; i++){
		$("#tagListLi li a").each(function(){ 
			if($(this).html() === names[i]){
				repeat = names[i];
				return false;
			}
		});
	}
	if(repeat){
		alert("不能重复添加标签:" + repeat);
		return;
	}
	for(var i = 0; i < names.length; i++){
		$("#tagListLi li").first().after('<li><a class="change" href="javascript:void(0);">' + names[i] + '</a></li>');
	}
	$("#newTag").val("");
}

function showAddOrUpdate(obj){
	var eventId;
	if(obj){
		eventId = obj.attr("eventId");
	}
	var content = $("#fancybox-content");
	var dialogClose = $.fancybox.close;
	$.fancybox.close = function(){
		dialogClose();
		if($("#adminEventEdit")) $("#adminEventEdit").remove();			
	};
	var callback = function(result){
		$("body").append(result);
		$addEvent = $("#adminEventEdit");
		$addEvent.fancybox();
		
		$("#editSubmitBtn",content).click(function(){
			
			var data=form2object('editEventForm');
			data.imgFileName = data.imgFile;
			for(var p in data){
				if(data[p]){
					var left = true;
					while(data[p].indexOf("'") != -1 || data[p].indexOf("\"") != -1){
						data[p] = data[p].replace("\"", left ? "“" : "”")
									.replace("'", left ? "‘" : "’");
						left = !left;
					}
				}
			}
			var tags = [];
			$("#tagListLi li[class='change']").each(function(){
				tags[tags.length] = $("a",$(this)).html();
			});
			var tagNames = tags.join(",");
			if(tagNames){
				data["tag.name"] = tagNames;
				data["tag.objectId"] = eventId;
				data["tag.type"] = "3";
			}
			var callback = function(result){
				if (result.code == 200) {
					alert("保存成功");
					if(eventId){
						turnPage(curPageNum);
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
					url : SystemProp.appServerUrl+"/new-publisher/edit-event!saveJson.action",
	                secureuri : false,
	                data : data,
	                fileElementId : "imgFile",
	                content : $("#editEventForm"),
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
		url: SystemProp.appServerUrl+"/new-publisher/edit-event!editAjax.action",
		type : "POST",
		dataType : "html",
		data: {"eventId":eventId},
		success: callback
	});
}


function initPage(){
	$("#eventListPage").pagination(pageCount, {
		num_edge_entries: 1, //边缘页数
		num_display_entries: 15, //主体页数
		callback:pageselectCallback,
		items_per_page: 1, //每页显示1项
		prev_text: "上页",
		next_text: "下页"
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
	data.limit = pageSize;
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
		url:SystemProp.appServerUrl+"/new-publisher/edit-event!getListAjax.action",
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
		num_display_entries: 15, //主体页数
		callback:pageselectCallback,
		items_per_page: 1, //每页显示1项
		current_page:currentPage-1, 
		prev_text: "上页",
		next_text: "下页"
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