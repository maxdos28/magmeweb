;$(function(){
	
	function strDate(date){
		return (date instanceof Date) ?
				(date.getFullYear()+"-"+ 
						((date.getMonth()+1 < 10) ? ("0"+(date.getMonth()+1)) : (date.getMonth()+1))+"-"+
				 (date.getDate()<10?("0"+date.getDate()) : date.getDate())) : "";
	}
	$("a[name='deleteadvideo']").unbind("click").live("click",function(){
		if(!ConfirmDel('确认删除吗？')) return;
		var adId=$(this).attr("adId");
		var deleteAd=$(this);
		var parentAd=$(this).parent("td").prev("td");
		var callback=function(rs){
			if(rs.code == 200){
				parentAd.html("无效");
				$(deleteAd).remove();
			}else{
				if(rs.message){
					alert(rs.message);
				}else{
					alert("error!");
				}
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertise-video!delJson.action",
			type: "POST",
			dataType: "json",
			data: {"id":adId},
			success: callback
		});
	});
	
	$("#advertiseVideoSearchBtn").unbind("click").bind("click",function(){
		//$("#viewAdDetail").facybox();
	});
	
	$("a[name='editAdVideo'][adId]").unbind("click").live("click",function(e){
		e.preventDefault();
		$("#editAdvertisForm")[0].reset();
		$("#isadd").val(0);//edit
		var adId=$(this).attr("adId");
		var callback=function(rs){
			var ad=rs.data.ad;
			if(rs.code == 200){
				$("#title").val(ad.title);
				$("#description").val(ad.description);
				$("#id").val(ad.id);
				//$("#status").val(ad.status);
				if(ad.startTime){
					$("#startTime").val(ad.startTime.split('T')[0]);
				}
				if(ad.endTime){
					$("#endTime").val(ad.endTime.split('T')[0]);
				}
				if(rs.data.tags){
					$("#tags").val(rs.data.tags);
				}
				$("#startTime").attr("disabled","disabled");
				$("#viewAdDetail").fancybox();
				$("#viewAdDetail").parents(".pop").attr("class","popAdmin");
				var ttWidth=$("#viewAdDetail").parents("#fancybox-wrap").width();
				var ttHeight=$("#viewAdDetail").parents("#fancybox-wrap").height();
				ttWidth =ttWidth-30;
				ttHeight =ttHeight+28;
				$("#viewAdDetail").parents("#fancybox-wrap").width(ttWidth).height(ttHeight);
				$("#viewAdDetail").parent().height(ttHeight);
			}else{
				if(rs.message){
					alert(rs.message);
				}else{
					alert("error!");
				}
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertise-video!getJson.action",
			type: "POST",
			dataType: "json",
			data: {"id":adId},
			success: callback
		});
		
	});
	
	$("#advertiseVideoAddBtn").unbind("click").bind("click",function(){
		$("#editAdvertisForm")[0].reset();
		$("#isadd").val(1);
		var d=new Date();
		$("#startTime").val(strDate(d));
		$("#startTime").attr("disabled","disabled");
		$("#viewAdDetail").fancybox();
	});
	
	$("#adSave").unbind("click").bind("click",function(e){
		e.preventDefault();
		$("#editAdvertisForm").submit();
	});
	$("#adClose").unbind("click").bind("click",function(e){
		e.preventDefault();
		$("#viewAdDetail").parents(".popAdmin").attr("class","pop");
		$.fancybox.close();
	});
	
	$("#editAdvertisForm").submit(function(){
		var adFile=$("#adFile").val();
		var isadd=$("#isadd").val();//edit
		if(isadd==1 && !adFile){
			$("#tipError").html("上传文件为空").show();
			return false;
		}
		if(!$("#title").val()){
			$("#tipError").html("标题为空").show();
			return false;
		}
		if(!$("#title").val()){
			$("#tipError").html("标题为空").show();
			return false;
		}
		if(!$("#startTime").val()){
			$("#tipError").html("开始时间为空").show();
			return false;
		}
		if(!$("#endTime").val()){
			$("#tipError").html("结束时间为空").show();
			return false;
		}
		$("#tipError").html("文件上传中").show();
		var names = $("#adFile").val().split(".");
		var filename=$.trim(names[names.length-1]).toLowerCase();
		
		if(isadd==1 && filename){
			
		}
		if(isadd==1 || (isadd==0 && filename)){
			if( filename != 'avi' && filename != 'mpg' && filename != 'mp4' && filename != 'mov' ){
				$("#tipError").html("请检查上传文件格式,支持avi,mpg,mp4,mov").show();
				return false;
			}
		}
		if(isadd==1){
			var requrl=SystemProp.appServerUrl+"/new-publisher/advertise-video!addJson.action";
		}else{
			var requrl=SystemProp.appServerUrl+"/new-publisher/advertise-video!editJson.action";
		}
		$.ajaxFileUpload(
	            {
	                url : requrl,
	                secureuri : false,
	                data : form2object('editAdvertisForm'),
	                fileElementId : "adFile",
	                content : $("#editAdvertisForm"),
	                dataType : "json",
	                async : true,
	                type : 'POST',
	                success : function(rs){
	                	if(!rs) return;
	                	if(rs.code != 200){
	                		$("#tipError").html(rs.message).show();
	                	}else{
	                		if(isadd==1){
	                			$("#tipError").html("上传首页广告成功").show();
	                		}else{
	                			$("#tipError").html("修改首页广告成功").show();
	                		}
	                		
	                	}
	                },
	                //服务器响应失败处理函数
	                error : function () {
	                	if(isadd==1){
	                		alert("亲,添加首页广告失败啦2");
	                	}else{
	                		alert("亲,修改首页广告失败啦2");
	                	}
	                }
	            }
        );
	});
	
	//预览
	$("a[name='preview']").live("click",function(){
		var obj = $(this);
		openDiv(obj.attr("url"));
	});
	
	function openDiv(address){
		//var urlStr = address;
		//$("#videoPlayIframe").attr("src","${systemProp.appServerUrl}/index-detail!toVideoPlay.action?url="+urlStr);
		var urlStr = SystemProp.appServerUrl + "/index-detail!toVideoPlay.action?url="+address;
		$("#videoAdvertiseDialog").html("<iframe id='videoPlayIframe' src='"+urlStr+"' width='600' height='450' frameborder='0' scrolling='no'></iframe>");
		$("#videoAdvertiseDialog").fancybox({'autoDimensions':false,'width':800,'height':450});
		//$("#videoAdvertiseDialog").parents(".popAdmin").attr("class","pop")
		
		//$("#videoAdvertiseDialog").parents("#fancybox-wrap").attr("class","pop").width(600).height(450);
		//$("#videoAdvertiseDialog").parent().width(600).height(450);
	}
		
	//搜索
	$("#advertiseVideoSearchBtn").live("click",function(){
		pageComm2(1);
		initPage();
	});
	
	//页面跳转
	$("#toPageOk").live("click",function(){
			var currentPage = $("#toPageValue").val();
			if(currentPage>adPageCount) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
			if(currentPage<=0){currentPage=1} 
			$("#eventListPage").html("");
        	$("#eventListPage").pagination(adPageCount, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 20, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				current_page:currentPage-1, 
				prev_text: "前一页",
				next_text: "后一页"
			});
		});
	
	
	//广告公共
	function pageComm(currentPage){
		var data = {};
		data.currentPage=currentPage;
		data.status =$("#categoryId").val();
		data.startTime=$("#startTimeAd").val();
		data.endTime=$("#endTimeAd").val();
		var callback =function(result){
			//alert(result.message);
			if(result.code!=200){
					return;
				}
			$("#tbodyContext").html("");
			if(result.data.adList){
				var trStr = "";
				$.each(result.data.adList,function(k,p){
					trStr +="<tr adVideoId ="+p.id+">";
					trStr +="<td name='title' class='tLeft'>"+p.title+"</td>";
					if(p.startTime){
						trStr +="<td name='startTime'>"+p.startTime.substring(0,10)+"</td>";
					}else{
						trStr +="<td name='startTime'>&nbsp;</td>";
					}
					if(p.endTime){
					trStr +="<td name='endTime'>"+p.endTime.substring(0,10)+"</td>";
					}else{
					trStr +="<td name='endTime'>&nbsp;</td>";
					}
					if(p.description){
					trStr +="<td name='description' >"+p.description+"</td>";
					}else{
					trStr +="<td name='description' >&nbsp;</td>";
					}
					trStr +="<td name='edit'><a name='editAdVideo' class='btn'  adId='"+p.id+"' href='javascript:void(0)'>编辑</a></td>";
					trStr +="<td name='preview'><a name='preview' class='btn' adId='"+p.id+"' url='"+p.mediaUrl+"' href='javascript:void(0)'>预览</a></td>";
					if(p.status==0){
						trStr +="<td name='status'>无效</td>";
					}else{
						trStr +="<td name='status'>有效</td>";	
					}
					if(p.status==1){
						trStr +="<td><a name='deleteadvideo' class='del' adId='"+p.id+"' href='javascript:void(0)'>删除</a></td>";
					}else{
						trStr +="<td>&nbsp;</td>";
					}
					trStr +="</tr>";
				});
				$("#tbodyContext").html(trStr);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertise-video!queryPageJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//广告 公共
	function pageComm2(currentPage){
		var data = {};
		data.currentPage=currentPage;
		data.status =$("#categoryId").val();
		data.startTime=$("#startTimeAd").val();
		data.endTime=$("#endTimeAd").val();
		var callback =function(result){
			//alert(result.message);
			if(result.code!=200){
			adPageCount=1;
					return;
				}
			if(result.data.adList){
				adPageCount = result.data.pageNo;
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertise-video!queryPageJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	
	initPage();
		
	//广告 公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	
	//广告公共
	function initPage(){
		$("#eventListPage").pagination(adPageCount, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 20, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "前一页",
			next_text: "后一页"
		});
	}

	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	datePickerFun($("#startTime"),nowDate);
	datePickerFun($("#endTime"),nowDate);
	datePickerFun($("#startTimeAd"),nowDate);
	datePickerFun($("#endTimeAd"),nowDate);
	
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
	
});