$(function(){
	initPage();
	
	//全选
	$("#appAll").live("click",function(){
		//var checkFlag =publicationAll
		if($(this).attr("checked")){
			$("input[name='tApp']").attr("checked",true);
		}else{
			$("input[name='tApp']").attr("checked",false);
		}
	});
	
	//关闭查看对话框
	$("#viewAppChange").live("click",function(){
		$.fancybox.close();
	});
	
	$("input[name='sendType']").change(function(){
		var obj = $(this);
		if(obj.val()==2){
			$("#sendTime").show();
		}else{
			$("#sendTime").hide();
		}
	});
	
	//发送消息前准备数据
	$("#sendMessageA").click(function(){
		$("#iosMessage").val("");
		$("input[name='sendType'][value='1']").attr("checked",true);
		$("#sendTime").val("");
		$("#sendTime").hide();
		$("#conIosMessage").html(60);
		var appidStr ="";
		$.each($("input[name='tApp']:checked"),function(i,ios){
			var obj = $(this);
			appidStr += obj.attr("appId")+",";
		});
		if(!appidStr){
			alert("请选择对应的app");
			return;
		}
		$("#appIdHidden").val(appidStr);
		$("#adminIosMessage").fancybox();
	});
	
	//发送消息
	$("#iosMessageOk").live("click",function(){
		var data = {};
		var tmpStatus =$("input[name='sendType']:checked").val();
		data.status=tmpStatus;
		if(!$("#iosMessage").val()){
			alert("内容不能为空");
			return;
		}
		data.content=$("#iosMessage").val();
		if(tmpStatus){
			if(tmpStatus==2){
				if($("input[name='sendTime']").val()){
					data.sendTime=$("input[name='sendTime']").val();
				}else{
					alert("请选择日期");
					return;
				}
			}
		}
		data.appIdStr = $("#appIdHidden").val();
		alert("消息已发送");
		$.fancybox.close();
		function callback(result){
			if(result.code!=200){
				return;
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-message!addIosPush.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	//关闭对话框
	$("#iosMessageChanel").live("click",function(){
		$.fancybox.close();
	});
	
	//搜索
	$("#iosAppSearch").click(function(){
		pageComm2(1);
		initPage();
	});
	
	
	//查看
	$("a[name='viewBtn']").live("click",function(){
		var obj = $(this);
		var appId = obj.attr("appId");
		var data = {};
		data.iosId=appId;
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
			}else{
				var pojo = rs.data.iosAppPojo;
				$("#app_name").html(pojo.name);
				$("#app_kewWord").html(pojo.appKeyword);
				$("#app_description").html(pojo.description);
				$("#app_info").html(pojo.information);
				$("#app_secondType").html(pojo.secondType);
				if(pojo.userType==0){
					$("#app_user").html("magme");
				}else{
					$("#app_user").html("出版商");
				}
				var publicationStr = "";
				var objPubList = rs.data.iosAppPubList
				$.each(objPubList,function(k,pub){
					publicationStr += pub.publicationName+"、";
				});
				if(publicationStr.length>1){
					publicationStr =publicationStr.substring(0,publicationStr.length-1);
				}
				$("#app_publication").html(publicationStr);
				$("#adminAppConfirm").fancybox();
				//alert('成功');
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-manage!getPojoJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	
	
	//list
	// 公共
	function pageComm(currentPage){
		var data = {};
		data.userType=$("input:radio[name='userType']:checked").val();
		data.name=$("#iosName").val();
		data.publicationName=$("#publicationName").val();
		data.pageCount=pageCount;
		data.currentPage=currentPage;
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
				pageCount = result.data.pageNo;
				}
				var trContent = "";
				$.each(result.data.iosAppList,function(k,a){
					var uStr ="";
					var cStr = "";
					if(a.userType==0){
						uStr="magme";
						cStr="<label><input name='tApp' appId='"+a.id+"' type='checkbox' /></label>";
					}else if(a.userType==1){
						uStr="出版商";
					}
				
					trContent+="<tr>";
					trContent+="<td>"+a.id+"</td>";
					trContent+="<td>"+a.name+"</td>";
                    trContent+="<td>"+a.appKeyword+"</td>";
                    trContent+="<td>"+a.secondType+"</td>";
                    trContent+="<td>"+a.information+"</td>";
                    trContent+="<td>"+uStr+"</td>";
                    trContent+="<td>"+a.createTime.substring(0,10)+"</td>";
                    trContent+="<td><a class='btn' name='viewBtn' appId='"+a.id+"' href='javascript:void(0);'>查看</a></td>";
                    trContent+="<td>"+cStr+"</td>";
					trContent+="</tr>";
				});
				$("#tbodyContext").html(trContent);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-manage!listJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage){
		var data = {};
		data.userType=$("input:radio[name='userType']:checked").val();
		data.name=$("#iosName").val();
		data.publicationName=$("#publicationName").val();
		data.pageCount=pageCount;
		data.currentPage=currentPage;
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
				pageCount = result.data.pageNo;
				}
				//var trContent = "";
				//$("#tbodyContext").html(trContent);
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-manage!listJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	
	//公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	
	//初始化
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
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	datePickerFun($("#sendTime"),nowDate);
	
		//时间控件
	//input_datepicker------------------------------------
	function datePickerFun($dateInput,date){
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
//				$dateInput.DatePickerHide();
			}
		});
	}
	
})