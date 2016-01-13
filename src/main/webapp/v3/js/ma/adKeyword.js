$(function() {
	
	var posturl = SystemProp.appServerUrl
	+ "/ma/keyword!searchKeyword.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+r.id+"</td>";
		trContent += "<td>"+r.keyword+"</td>";
		
		trContent += "<td>";
		trContent += "<a name='delBtn' class='del' keywordId="+r.id+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	$("#searchKeywordBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-keyword").val())
		data.keyword = $("#s-keyword").val();
		pageComm(1, posturl, data, writetablefn);
	});
	$("#newKeywordBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#pop002").fancybox();
	});
	$("a[name=delBtn]").unbind("click").live("click", function() {
		if(!confirm("是否确认?"))
			return;
		var id = $(this).attr("keywordId");
		if(!id)
		{
			alert("无ID");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/keyword!deleteKeyword.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:id},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("保存成功");
					$.fancybox.close();
					refreshCurrentPage();
				}
			}
		});
	});
	$("#saveKeywordBtn").unbind("click").live("click", function() {
		if(!$("#keyword").val())
		{
			alert("请输入标签名");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/keyword!addKeyword.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {keyword:$("#keyword").val()},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("保存成功");
					$.fancybox.close();
					refreshCurrentPage();
				}
			}
		});
	});
	$("#cancelBtn").unbind("click").live("click", function() {
		$.fancybox.close();
	});
});