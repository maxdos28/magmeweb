//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-lucky-card-manager!searchLuckyCardJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+strEmpty(r.id)+"</td>";
		trContent += "<td>"+strEmpty(r.name)+"</td>";
		if(r.type==1)
			trContent += "<td>无奖</td>";
		else if(r.type==2)
			trContent += "<td>金币</td>";
		else
			trContent += "<td>礼品</td>";
		trContent += "<td>"+numEmpty(r.luckNum)+"</td>";
		trContent += "<td>"+numEmpty(r.sortOrder)+"</td>";
		if(r.status==1)
		{
			trContent += "<td>未刮奖</td>";
		}
		else if(r.status==2)
		{
			trContent += "<td>锁定</td>";
		}
		else 
		{
			trContent += "<td>已刮奖</td>";
		}
		trContent += "<td>";
		trContent += "<a name='sortBtn' class='btn' sort="+r.sortOrder+" cardId="+r.id+" href='#'>改变顺序</a>";
		if(r.status==1)
			trContent += "<a name='delBtn' class='del' cardId="+r.id+" href='#'>删除</a>" ;
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
		if($("#s-type").val()!=-1)
		data.type = $("#s-type").val();
		
		pageComm(1, posturl, data, writetablefn);
	});
	
	//删除
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var cardId = $(this).attr("cardId");
		if(!cardId)
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
			url : SystemProp.appServerUrl + "/look/look-lucky-card-manager!deleteLuckyJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:cardId},
			success : callback1
		});
	});
	function initPage() {
		
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#adcardp").html("");
		$("#pop006").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		$("#submitForm").submit();
	});
	$("#submitForm").validate({
		rules : {
			total : {
				required : true,
				number : true				
			}
		},
		submitHandler:save
	});
	function save()
	{
		//检查总数和明细数量是否相等
		var total = $("#total").val();
		var qtyinput = $("#adcardp").find("input[name=qty]");
		var checkqty = 0;
		if(!qtyinput||qtyinput.length==0)
		{
			alert("请增加刮刮卡类型");
			return;
		}
		for(i=0;i<qtyinput.length;i++)
		{
			if($(qtyinput[i]).val().length>0)
			{
				checkqty += parseInt($(qtyinput[i]).val());
			}
		}
		if(checkqty!=total)
		{
			alert("总数量和明细数量不一致");
			return;
		}
		var postUrl = SystemProp.appServerUrl+"/look/look-lucky-card-manager!saveLuckyCardJson.action";
		var cc = $("#adcardp").children();
		var cardstr = "";//以逗号分隔,同值以冒号分隔,顺序为(类型+礼品ID/奖励金币+数量)
		for(i=0;i<cc.length;i++)
		{
			var type = $(cc[i]).find("select[name=cardType]").val();
			var gift = $(cc[i]).find("select[name=giftId]").val();
			var luckNum = $(cc[i]).find("input[name=luckNum]").val();
			var qty = $(cc[i]).find("input[name=qty]").val();
			if(!qty||qty.length==0)
				continue;
			if(type==2&&!luckNum)
			{
				alert("请填写金币奖励");
				return;
			}
			//礼品
			if(type==3&&!gift)
			{
				alert("请选择礼品奖励");
				return;
			}
			var str = type+":";
			if(type==3)
				str+=gift;
			else
				str+=luckNum;
			str+=":"+qty;
			if(cardstr.length>0)
				cardstr+=",";
			cardstr+=str;
		}
		if(cardstr.length==0)
		{
			alert("请增加刮刮卡类型");
			return;
		}
		$.ajax({
			url : postUrl,
			type : "POST",
			async : false,
			dataType : "json",
			data : {cardstr:cardstr,total:total},
			success : function(rs) {
				if (rs.code != 200) {
					alert("操作失败");
				} else {
					alert("操作成功");
					$.fancybox.close();
					location.reload();
				}
			}
		});
	}

	//增加类型
	$("#addcardBtn").bind("click", function() {
		$("#adcardp").append($("#adcarddiv").html());
	});
	//删除类型
	$("a[name=deleteCardBtn]").unbind("click").live("click",function(){
		$(this).parent().remove();
	});
	//类型选择
	$("select[name=cardType]").unbind("change").live("change",function(){
		var type = $(this).val();
		var gift = $(this).parent().parent().find("select[name=giftId]");
		var luckNum = $(this).parent().parent().find("input[name=luckNum]");
		var qty = $(this).parent().parent().find("input[name=qty]");
		//无奖
		if(type==1)
		{
			gift.hide();
			luckNum.show();
			luckNum.val("0");
			luckNum.attr("readonly",true);
		}
		//金币
		else if(type==2)
		{
			gift.hide();
			luckNum.show();
			luckNum.attr("readonly",false);
		}
		//礼品
		else
		{
			gift.show();
			luckNum.val(gift.find("option:checked").attr("qty"));
			luckNum.attr("readonly",true);
		}
	});
	//礼品选择
	$("select[name=giftId]").unbind("change").live("change",function(){
		var luckNum = $(this).parent().parent().find("input[name=luckNum]");
		luckNum.val($(this).find("option:checked").attr("qty"));
	});
	//改变顺序
	$("a[name=sortBtn]").unbind("click").live("click",function(){
		$("#submitForm2")[0].reset();
		var sort = $(this).attr("sort");
		var cardId = $(this).attr("cardId");
		$("#currentSort").val(sort);
		$("#cardId").val(cardId);
		$("#pop003").fancybox();
	});
	//关闭顺序窗口
	$("#cancelSortBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存顺序
	$("#saveSortBtn").bind("click", function() {
		$("#submitForm2").submit();
	});
	$("#submitForm2").validate({
		rules : {
			offset : {
				required : true,
				number : true
			}
		},
		submitHandler:saveSort
	});
	function saveSort()
	{
		var postUrl = SystemProp.appServerUrl+"/look/look-lucky-card-manager!changeSortJson.action";
		var cardId = $("#cardId").val();
		var currentSort = parseInt($("#currentSort").val());
		var direction = parseInt($("[type=radio][name=direction]:checked").val());
		var offset = parseInt($("#offset").val());
		var sort;
		if(direction==1)
		{
			sort = currentSort+offset;
		}
		else
		{
			sort = currentSort-offset;
		}
		if(sort<=0)
			sort = 1;
		if(currentSort==sort)
		{
			alert("顺序未改变");
			return;
		}
		$.ajax({
			url : postUrl,
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:cardId,sortOrder:sort},
			success : function(rs) {
				if (rs.code != 200) {
					alert("操作失败");
				} else {
					alert("操作成功");
					$.fancybox.close();
					refreshCurrentPage();
				}
			}
		});
	}
});
