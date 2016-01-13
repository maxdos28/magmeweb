//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-egg-manager!searchEggManagerJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+strEmpty(r.eggCode)+"</td>";
		trContent += "<td>"+strEmpty(r.eggName)+"</td>";
		trContent += "<td>"+numEmpty(r.eggNums)+"</td>";
		trContent += "<td>"+trimDate(r.beginTime)+"</td>";
		trContent += "<td>"+trimDate(r.endTime)+"</td>";
		if(r.status==1)
		{
			trContent += "<td>上架</td>";
		}
		else
		{
			trContent += "<td>下架</td>";
		}
		trContent += "<td>";
		if(r.status==1)
			trContent += "<a name='changeStatusBtn' managerId="+r.id+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' managerId="+r.id+" class='btn' href='#'>上架</a>" ;
		trContent += "<a name='editBtn' class='btn' managerId="+r.id+" href='#'>编辑</a><a name='delBtn' class='del' managerId="+r.id+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-eggCode").val()!="彩蛋活动号")
		data.eggCode = $("#s-eggCode").val();
		if($("#s-eggName").val()!="活动名称")
		data.eggName = $("#s-eggName").val();
		
		pageComm(1, posturl, data, writetablefn);
	});
	//上架下架
	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var managerId = $(this).attr("managerId");
		if(!managerId)
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
			url : SystemProp.appServerUrl + "/look/look-egg-manager!changeStatusEggManagerJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {managerId:managerId},
			success : callback1
		});
	});
	//修改
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var managerId = $(this).attr("managerId");
		if(!managerId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$("#managerId").val(managerId);
		$("#itemp").html("");
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-egg-manager!eggManagerInfoJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {managerId:managerId},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
				}
				else
				{
					var egg = rs.data.eggManagerInfo;
					if(egg&&egg.eggInfo)
					{
						$("#eggCode").text(strEmpty(egg.eggInfo.eggCode));
						$("#eggName").val(strEmpty(egg.eggInfo.eggName));
						$("#eggNums").val(numEmpty(egg.eggInfo.eggNums));
						$("#eggNos").val(strEmpty(egg.eggInfo.eggNos));
						$("#beginTime").val(trimDate(egg.eggInfo.beginTime));
						$("#endTime").val(trimDate(egg.eggInfo.endTime));
					}
					if(egg&&egg.eggDetailList)
					{
						$.each(egg.eggDetailList,function(e,r){
							var em = $($("#itemdiv").html());
							var sele = em.find("select[name=item]");
							sele.val(r.itemId);
							var type = sele.find("option:checked").attr("type");
							if(type&&type==2)
							{
								sele.next().hide();
							}
							if(type&&type==1)
							{
								sele.next().show();
								em.find("input[name=articleBeginTime]").val(trimDate(r.articleBeginTime));
							}
							$("#itemp").append(em);
						});
					}
				}
			}
		});
		$("#pop003").fancybox();
		
	});
	//删除
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var managerId = $(this).attr("managerId");
		if(!managerId)
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
			url : SystemProp.appServerUrl + "/look/look-egg-manager!deleteEggManagerJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {managerId:managerId},
			success : callback1
		});
	});
	function initPage() {
		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#beginTime"),nowDate);
	      datePickerFun2($("#endTime"),nowDate);
	      datePickerFun2($("#itemdiv input[name=articleBeginTime]"),nowDate);

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
		 //栏目列表
	      var item = $("#itemdiv select[name=item]");
	      item.html("<option></option>");
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
							//有子栏目的栏目不添加
							if(r.isParent&&r.isParent==1)
								return true;
							item.append("<option value='"+r.id+"' type="+r.type+">"+r.title+"</option>");
						});
					}
				}
			});
	}
	//栏目选择事件
	$("select[name=item]").unbind("change").live("change", function() {
		var type = $(this).find("option:checked").attr("type");
		if(!type)
			return;
		if(type==2)
		{
			$(this).next().hide();
		}
		if(type==1)
		{
			$(this).next().show();
		}
	});

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#eggCode").text("");
		$("#managerId").val("0");
		$("#itemp").html("");
		$("#pop003").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		$("#submitForm").submit();
	});

	//删除关联ITEM
	$("a[name=deleteItemBtn]").unbind("click").live("click",function(){
		$(this).parent().remove();
	});
	//增加关联ITEM
	$("a[name=addItemBtn]").unbind("click").live("click",function(){
		$("#itemp").append($("#itemdiv").html());
	});
	$("#submitForm").validate({
		rules : {
			eggName : "required",
			eggNos : {
				eggNos : true,
				required : true
			},
			eggNums : {
				digits : true,
				required : true
			},
			beginTime : {
				date : true,
				required : true
			},
			endTime : {
				date : true,
				required : true
			}
		},
		submitHandler:save
	});
	function save()
	{
		if(checkEggNos())
			return;
		//检查是否选择了栏目
		var detailData=getDetailData();
		if(!detailData)
			return;
		var postUrl = SystemProp.appServerUrl+"/look/look-egg-manager!saveEggManagerJson.action";
		var data = {};
		data.managerId = $("#managerId").val();
		data.eggName = $("#eggName").val();
		data.eggNums = $("#eggNums").val();
		data.eggNos = $("#eggNos").val();
		data.beginTime = $("#beginTime").val();
		data.endTime = $("#endTime").val();
		data.eggDetail=obj2str(detailData);
		$.ajax({
			url: postUrl,
			type: "POST",
			dataType: "json",
			data: data,
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					alert("保存成功");
					refreshCurrentPage();
					$.fancybox.close();
				}
			}
		});
	}
	//检查彩蛋数量和彩蛋号码是否匹配
	function checkEggNos()
	{
		var num = parseInt($("#eggNums").val());
		var nos = $("#eggNos").val();
		var ns = nos.split(",");
		//检查是否按顺序输入彩蛋号码
		var checkNo = new Array();
		var k=0;
		for(var i=0;i<ns.length;i++)
		{
			if(ns[i].indexOf("-")>=0)
			{
				var ps = ns[i].split("-");
				if(ps.length!=2)
				{
					alert("格式错误");
					return true;
				}
				checkNo[k]=ps[0];
				k++;
				checkNo[k]=ps[1];
			}
			else
				checkNo[k]=ns[i];
			k++;
		}
		if(parseInt(checkNo[0])<=0)
		{
			alert("彩蛋号码需要从1开始");
			return true;
		}
		for(var i=0;i<checkNo.length-1;i++)
		{
			if(parseInt(checkNo[i])>parseInt(checkNo[i+1]))
			{
				alert("彩蛋号码要按顺序填写");
				return true;
			}
		}
		for(var i=0;i<ns.length;i++)
		var sum = 0;//计算总页数,逗号分隔计1页,横线分隔计差额
		for(var i=0;i<ns.length;i++)
		{
			if(ns[i].indexOf("-")>=0)
			{
				var ps = ns[i].split("-");
				if(ps.length!=2)
				{
					alert("格式错误");
					return;
				}
				var n = parseInt(ps[1])-parseInt(ps[0])+1;
				sum+=n;
			}
			else
			sum++;
		}
		if(num!=sum)
		{
			alert("彩蛋数量和彩蛋号码不匹配");
			return true;
		}			
		return false;
	}
	//得到添加的栏目
	function getDetailData()
	{
		var em = $("#itemp").find("em");
		if(!em)
		{
			alert("请添加栏目");
			return;
		}
		var dd = new Array();
		var i=0;
		$.each(em,function(e,r){
			var sel = $(r).find("select[name=item]");
			var itemId = $(r).find("select[name=item]").val();
			if(!sel||!sel.val())
				return true;
			var type = sel.find("option:checked").attr("type");
			if(!type)
				return true;
			var d = {};
			d.itemId = itemId;
			d.type = type;
			if(type==1)
			{
				var date = $(r).find("input[name=articleBeginTime]").val();
				if(!date)
				{
					alert("请输入文章日期");
					dd = 0;
					return false;
				}
				if(!isDate(date))
				{
					alert("日期格式错误");
					dd = 0;
					return false;
				}
				d.articleBeginTime = date;
			}
			dd[i]=d;
			i++;
		});
		if(!dd)
			return;
		var detailData = {};
		detailData.eggDetail = dd;
		return detailData;
	}
});
