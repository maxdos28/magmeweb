$(function(){
	initPage();
	
	$("input[name='sendTime']").hide();
	$("input[name='sendType']").change(function(){
		var obj = $(this);
		if(obj.val()==2){
			$("input[name='sendTime']").show();
		}else{
			$("input[name='sendTime']").hide();
		}
	});
	
	//搜索
	$("input[name='status']").change(function(){
		pageComm2(1);
		initPage();
	});
	$("input[name='userType']").change(function(){
		pageComm2(1);
		initPage();
	});
	
	//del
	$("a[name='delBtn']").live("click",function(){
		var obj = $(this);
		if(!ConfirmDel('确认删除?')) {return;}
		var data = {};
		data.iosId=obj.attr('iosId');
		data.status=0;
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
			}else{
				pageComm2(1);
				initPage();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-message!updateStatusIosPush.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//编辑前的准备
	$("a[name='editBtn']").live("click",function(){
		var obj = $(this);
		$("#iosMessage").val("");
		$("input[name='sendType'][value='1']").attr("checked",true);
		$("#sendTime").val("");
		$("#sendTime").hide();
		$("#conIosMessage").html(60);
		var data = {};
		data.iosId=obj.attr('iosId');
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
			}else{
				var po = rs.data.iosPushPojo;
				
				$("#iosMessage").val(po.content);
				$("#appIdHidden").val(po.id);
				if(po.status==2){
					$("#sendTime").val(po.sendTime.substring(0,10));
					$("input:radio[name='sendType'][value='2']").attr("checked","checked");
					$("input[name='sendTime']").show();
				}
				$("#conIosMessage").text(60-$("#iosMessage").val().length);
				$("#adminIosMessage").fancybox();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-message!getPojoJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//发送消息
	$("#iosMessageOk").live("click",function(){
		var data = {};
		var tmpStatus =$("input[name='sendType']:checked").val();
		data.status=tmpStatus;
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
		data.iosId = $("#appIdHidden").val();
		function callback(result){
			if(result.code!=200){
				alert(result.message);
				return;
			}
			alert("保存成功");
			$.fancybox.close();
			pageComm2(1);
			initPage();
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
	
	//重置
		$("#iosBtnChanle").live("click",function(){
			$("#iosMessage").val("");
			$("#iosId").val("");
			$("input:radio[name='sendType'][value='1']").attr("checked","checked");
			$("#sendTime").val("");
			$("input[name='sendTime']").hide();
			$("#conIosMessage").html(60);
		});
	
	
	//list
	// 公共
	function pageComm(currentPage){
		var data = {};
		data.status=$("input:radio[name='status']:checked").val();
		data.userType=$("input:radio[name='userType']:checked").val();
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
				$.each(result.data.iosPushList,function(k,a){
					trContent+="<tr>";
					trContent+="<td>"+a.id+"</td>";
					trContent+="<td>"+a.appName+"</td>";
					var tempContent= "";
					if(a.content){
						if(a.content.length>30){
							tempContent = a.content.substring(0,30)+"……";
						}else{
							tempContent = a.content;
						}
					}
					trContent+="<td>"+tempContent+"</td>";
					if(a.sendTime){
						trContent+="<td>"+a.sendTime.substring(0,10)+"</td>";
					}else{
						trContent+="<td>&nbsp;</td>";
					}
					
					if(a.status){
						if(a.status==2){
							trContent+="<td>待发送</td>";
							if(a.userType==0){
								trContent+="<td><a class='btn' href='javascript:void(0);' name='editBtn' iosId='"+a.id+"' >编辑</a><a class='del' name='delBtn' iosId='"+a.id+"' href='javascript:void(0);'>删除</a></td>";
							}else{
								trContent+="<td>&nbsp;</td>";
							}
						}
						else if(a.status==1){
							trContent+="<td>已发送</td>";
							if(a.userType==0){
								trContent+="<td><a class='del' name='delBtn' iosId='"+a.id+"' href='javascript:void(0);'>删除</a></td>";
							}else{
								trContent+="<td>&nbsp;</td>";
							}
						}
					}else{
						trContent+="<td>&nbsp;</td><td>&nbsp;</td>";
					}
					trContent+="</tr>";
				});
				$("#tbodyContext").html(trContent);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-message!listJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage){
		var data = {};
		data.status=$("input:radio[name='status']:checked").val();
		data.userType=$("input:radio[name='userType']:checked").val();
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
			url: SystemProp.appServerUrl+"/new-publisher/app-message!listJson.action",
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