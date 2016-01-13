$(init);
var pageCount;
var currentPage;
function init(){
	var nowDate = new Date(),
	$sDate = $("#startDate"),
	$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	var data1 = {
			"currentPage" : 1,
			"startDate":$("#startDate").val(),
			"endDate":$("#endDate").val()
	};
	$.ajax({
		type : "POST",
		url : SystemProp.appServerUrl+ "/phoenix/feed-back!feedBackJson.action", 
		data : data1,
		dataType : "json",
		success : function(rs) {
			if(!rs || !(rs.code)|| rs.code!=200 || !(rs.data) || !(rs.data.pageCount)){
				alert("没有数据");
				return;
			}
			initPagination(rs);// data是返回来的json数据
		}
	});	
}

$("#searchBtn").unbind("click").live("click",function(){
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	if(!startDate || startDate=="" || !endDate || endDate==""){
		alert("开始时间和结束时间必填");
		return ;
	}
	init();
});
function initPagination(rs) {
	pageCount = rs.data.pageCount;
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
	//$("#eventListPageadd").append(
	//				"跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
	currentPage = page_id + 1;
	pageComm(page_id + 1);
	return false;
}
function pageComm(current) {
	$("#tbodyContent").html("");
	var startDate=$("#startDate").val();
	var endDate=$("#endDate").val();
	var data = {
		"currentPage" : current,
		"startDate":startDate,
		"endDate":endDate
	};
	$.ajax({type : "POST",
			url : SystemProp.appServerUrl+ "/phoenix/feed-back!feedBackJson.action", // 此处是调用后台的ACTION
			data : data,
			dataType : "json",
			success : function(rs) {
				if(rs.data.list){
					var str = "";
					$.each(rs.data.list,function(n, e) {
							str += "<tr>";
							str += "<td>"+e.updatedTime.replace("T",' ');+"</td>";											
							str += "<td>" + strEmpty(e.comment) + "</td>";									
							str += "<td>" + e.content + "</td>";
							str += "</tr>";
					});
					$("#tbodyContent").html(str);
					fnReadyTable();
				}else{
					var str = "";
					str += "<tr>";
					str += "<td></td>";										
					str += "<td> </td>";
					str += "</tr>";
					$("#tbodyContent").html(str);
					fnReadyTable();
				}
			}
	   });
}
