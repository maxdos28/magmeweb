;$(function(){
	
	initPage();
	
	//list
	function pageComm(currentPage){
		var data = {};
		data.pageNo=pageCount;
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
				if(result.data.smsPhoneGroupList){
					$.each(result.data.smsPhoneGroupList,function(i,p){
						trContent += "<tr>";
						trContent += "<td><label><input type='checkbox' /></label></td>";
						trContent += "<td>"+p.name+"</td>";
						trContent += "<td>"+p.phoneCount+"</td>";
						trContent += "<td><a class='btn' href='javascript:void(0);'" +
						        " pname='"+p.name+"' addPhoneGroup='"+p.id +"'>追加</a>" +
						        "<a class='btn' href='"+SystemProp.kaiJieAppUrl+"/sms/sms-phone-group!exportData.action?id="+p.id+"' >导出</a>" +
								"<a class='del' delPhoneGroup='"+p.id+"' href='javascript:void(0);'>删除</a></td></td>";
						trContent += "</tr>";
					});
					$("#tbodyContext").html(trContent);
					fnReadyTable();
				}
				
			}
		};
		
		$.ajax({
			url: SystemProp.kaiJieAppUrl+"/sms/sms-phone-group!groupListJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//删除通讯录组
	$("a[delphonegroup]").unbind("click").live("click",function(e){
		e.stopPropagation();
		var $aa=$(this);
		var callback =function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				$aa.parent("tr").remove();
			}
			
		};
		var id=$(this).attr("delphonegroup");
		$.ajax({
			url: SystemProp.kaiJieAppUrl+"/sms/sms-phone-group!delJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: {"id":id},
			success: callback
		});
		
	});
	//添加通讯录
	$("#addSmsPhoneGroup").unbind("click").live("click",function(e){
		e.stopPropagation();
		if($("#name").attr("readonly")=="readonly"){
			$("#name").attr("readonly","");
		}
		$("#groupId").val("");
		$("#addSmsGroup").fancybox();
	});
	//追加通讯录
	$("a[addPhoneGroup]").unbind("click").live("click",function(e){
		e.stopPropagation();
		if($("#name").attr("readonly")!="readonly"){
			$("#name").attr("readonly","readonly");
		}
		$("#name").val($(this).attr("pname"));
		$("#groupId").val($(this).attr("addPhoneGroup"));
		$("#addSmsGroup").fancybox();
	});
	
	$("#cancelSmsGroup").unbind("click").live("click",function(e){
		e.stopPropagation();
		$.fancybox.close();
	});
	
	
	$("#enterSmsGroup").unbind("click").live("click",function(e){
		e.stopPropagation();
		$.ajaxFileUpload(
	            {
	                url : SystemProp.kaiJieAppUrl+"/sms/sms-phone-group!addGroupJson.action",
	                secureuri : false,
	                data : {"fileName":$("#uploadFile").val(),"name":$("#name").val(),"id":$("#groupId").val()},
	                fileElementId : "uploadFile",
	                content : $("#addSmsGroupForm"),
	                dataType : "json",
	                async : true,
	                type : 'POST',
	                success : function(rs){
	                	if(!rs) return;
	                	if(rs.code != 200){
	                		alert(rs.message);
	                	}else{
	                		$.fancybox.close();
	                		initPage();
	                		pageComm2();
	                	}
	                	
	                },
	                //服务器响应失败处理函数
	                error : function (data, status, e) {
	                	alert("亲，添加通讯录失败了失败啦！");
	                }
	            }
	        );
	});
	
		
	function pageComm2(){
		var data = {};
		data.pageNo=pageCount;
		var callback =function(result){
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
					pageCount = result.data.pageNo;
				}
			}
		};
		$.ajax({
			url: SystemProp.kaiJieAppUrl+"/sms/sms-phone-group!groupListJson.action",
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
		pageComm2();
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
	
});