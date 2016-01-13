$(function(){
	//项目修改
	$("a[name='projectView']").live("click",function(){
		var pid = $(this).attr("pid");
		var step = $(this).attr("step");
		var url =SystemProp.kaiJieAppUrl;
		url=url+"/sms/project-manage!stepFirst.action";
		if(step){
			if(step==1){
				url=SystemProp.kaiJieAppUrl+"/sms/project-manage!stepFirst.action";
			}else if(step==2){
				url=SystemProp.kaiJieAppUrl+"/sms/project-manage!stepSecond.action";
			}else if(step==3){
				url=SystemProp.kaiJieAppUrl+"/sms/project-manage!stepThird.action";
			}else if(step==4){
				url=SystemProp.kaiJieAppUrl+"/sms/project-manage!stepFourth.action";
			}else if(step==5){
				url=SystemProp.kaiJieAppUrl+"/sms/project-manage!stepFifth.action";
			}else{//跳转到统计页面的初始页面
				
			}
			
		}
		url= url+"?id="+pid;
		window.location.href=url;
	});
	
	
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
				if(result.data.projectList){
					$.each(result.data.projectList,function(i,project){
						trContent += "<tr>";
						trContent += "<td>"+project.name+"</td>";
						var statusStr ="";
						if(project.status){
							if(project.status==0){
								statusStr="无效";
							}else if(project.status==2){
								statusStr="未完成";
							}else if(project.status==3){
								statusStr="发送中";
							}else if(project.status==4){
								statusStr="发送结束";
							}else if(project.status==5){
								statusStr="报表查看";
							}
						}
						trContent += "<td>"+statusStr+"</td>";
						trContent += "<td>"+project.completePercentage+"</td>";
						trContent += "<td>"+project.successPercentage+"</td>";
						if(project.status==5){
							trContent += "<td><a class=\"btn\" name='projectReportView' step='"+project.step+"' pid='"+project.id+"' href='"+SystemProp.kaiJieAppUrl+"/sms/sms-dm-user-area.action?smsProjectId="+project.id+"'>查看</a></td>";
						}else if(project.status==4 || project.status==3 ){
							trContent += "<td>&nbsp;</td>";
						}else{
							trContent += "<td><a class=\"btn\" name='projectView' step='"+project.step+"' pid='"+project.id+"' href='javascript:void(0);'>查看</a></td>";
						}
						
						trContent += "</tr>";
					});
					$("#tbodyContext").html(trContent);
					fnReadyTable();
				}
				
			}
		};
		
		$.ajax({
			url: SystemProp.kaiJieAppUrl+"/sms/project-manage!projectList.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
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
			url: SystemProp.kaiJieAppUrl+"/sms/project-manage!projectList.action",
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
	
})