$(init);
var pageCount;
var currentPage;
function init(){
	$.ajax({
		type : "POST",
		url : SystemProp.appServerUrl
				+ "/phoenix/phoenix-android-revert!searchRevert.action", // 此处是调用后台的ACTION
		// data : "ws.gameid="+gameid,
		dataType : "json",
		success : function(msg) {
			initPagination(msg);// data是返回来的json数据
		}
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
		"currentPage" : current
	};
	$.ajax({
				type : "POST",
				url : SystemProp.appServerUrl
						+ "/phoenix/phoenix-android-revert!searchRevert.action", // 此处是调用后台的ACTION
				data : data,
				dataType : "json",
				success : function(msg) {
					if(msg.data.list)
					{
						var str = "";
						$
								.each(
										msg.data.list,
										function(n, e) {
											str += "<tr>";
											str += "<td>"+e.deviceNum+"</td>";										
											str += "<td>" + e.orderNum + "</td>";
											str += "<td>" + e.createTime + "</td>";
											str += "<td><a class='btn' href='#' name='p_audited' revertId='"+e.id+"'>审核通过</a></td>";
											str += "</tr>";
										});
						$("#tbodyContent").html(str);
						fnReadyTable();
					}
					else
					{
						var str = "";
						str += "<tr>";
						str += "<td></td>";										
						str += "<td> </td>";
						str += "<td> </td>";
						str += "<td> <a class='btn' href='#' name='p_audited'></a></td>";
						str += "</tr>";
						$("#tbodyContent").html(str);
						fnReadyTable();
					}
				}
			});
}
//审核
$("a[name='p_audited']")
.live(
		"click",
		function() {
			var id = $(this).attr("revertId");
			
			$.ajax({
				type : "POST",
				url : SystemProp.appServerUrl
						+ "/phoenix/phoenix-android-revert!auditedAndroidRevert.action", // 此处是调用后台的ACTION
				data : {"id":id},
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