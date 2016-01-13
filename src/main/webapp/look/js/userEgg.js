//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-user-egg!searchUserEggJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.userId)+"</td>";
		trContent += "<td>"+strEmpty(r.nickName)+"</td>";
		trContent += "<td>"+strEmpty(r.eggCode)+"</td>";
		trContent += "<td>"+strEmpty(r.eggNum)+"</td>";
		trContent += "<td>"+strEmpty(r.ticketNum)+"</td>";
		if(r.userEggManagerId)
		{
			if(!r.winning||r.winning==0)
			{
				trContent += "<td>";
				trContent += "<a name='sendBtn' class='btn' userEggManagerId="+r.userEggManagerId+" href='#'>中奖</a>";
				trContent += "</td>";
			}
			else if(r.winning==1)
			{
				trContent += "<td>已中奖</td>";
			}
			
		}
		else
		{
			trContent += "<td></td>";
		}		
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-userId").val()!="用户ID")
			data.userId = $("#s-userId").val();
		if($("#s-nickName").val()!="用户昵称")
			data.nickName = $("#s-nickName").val();
		if($("#s-eggCode").val()!="彩蛋活动号")
			data.eggCode = $("#s-eggCode").val();
		if($("#s-ticketNum").val()!="彩卷号码")
			data.ticketNum = $("#s-ticketNum").val();
		pageComm(1, posturl, data, writetablefn);
	});
	//发放
	$("a[name=sendBtn]").unbind("click").live("click", function() {
		var userEggManagerId = $(this).attr("userEggManagerId");
		if(!userEggManagerId)
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
				$.fancybox.close();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-user-egg!winningUserEggJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userEggManagerId:userEggManagerId},
			success : callback1
		});
	});
	
	function initPage() {
	}

});
