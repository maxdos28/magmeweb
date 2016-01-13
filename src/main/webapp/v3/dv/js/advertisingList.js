$(function(){ 	
	//杂志分页 左翻页
	$("#mgleft").live("click",function(){
		currentPage = currentPage-1;
		if(currentPage<=1){//已经是第一页
			currentPage = 1;
			magComm(currentPage);
			$("#mgleft").addClass("disable");
		}else{
			$("#mgleft").removeClass("disable");
			magComm(currentPage);
		}
	});
	//杂志分页 右翻页
	$("#mgright").live("click",function(){
		currentPage = currentPage+1;
		if(currentPage>=pageCount){//已经是最后一页
			currentPage=pageCount
			magComm(currentPage);
			$("#mgright").addClass("disable");
		}else{
			$("#mgright").removeClass("disable");
			magComm(currentPage);
		}
	});
	
	//广告类型
	$("a[name='hdad']").live("click",function(){
		var obj = $(this);
		$("a[name='hdad']").removeClass("current");//清除对应的样式
		obj.addClass("current");
		pageComm2(1,piid);
		initPage();
	});
	
	//杂志对应的广告
	$("li[name='publicationById']").live("click",function(){
		$("li[name='publicationById']").removeClass("current");
		$(this).addClass("current");
		$("#tbodyContext").html("");
		var obj = $(this);
		pageComm2(1,obj.attr("pid"));
		initPage();
	});
	
	//下架
	$("#adDown").unbind("click").live("click",function(){
		var obj = $(this);
		var typeTemp = $("a[name='hdad'][Class='current']").attr("adtype");
		if(typeTemp==2){
			adComm(obj.attr("adid"),0);
		}else{
			adComm(obj.attr("adid"),7);
		}
	});
	
		//上架
	$("#adUp").unbind("click").live("click",function(){
		var obj = $(this);
		var typeTemp = $("a[name='hdad'][Class='current']").attr("adtype");
		if(typeTemp==2){
			adComm(obj.attr("adid"),1);
		}else{
			adComm(obj.attr("adid"),5);
		}
	});
		
	//审核
	$("#adCheck").unbind("click").live("click",function(){
		var obj = $(this);
		adComm(obj.attr("adid"),6);
	});
	
	//杂志搜索
	$("a[name='magSearchButton']").live("click",function(){
		magComm(1);
	});
	
	//过滤
	$("input[name='typeradio'][@radio]").live("click",function(){
		var obj = $(this);
		pageComm2(1,piid);
		initPage();
		//alert(obj.val());
		//alert("xx:"+$("input[name='typeradio']:radio").attr("checked")); 
	});
	
	//页面跳转
	$("#toPageOk").live("click",function(){
			var currentPage = $("#toPageValue").val();
			if(currentPage>adPageCount) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
			if(currentPage<=0){currentPage=1} 
			$("#eventListPage").html("");
        	$("#eventListPage").pagination(adPageCount, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 15, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				current_page:currentPage-1, 
				prev_text: "上页",
				next_text: "下页"
			});
		});
	//互动广告的编辑
	$("#adEdit").live("click",function(){
		var obj = $(this);
		var data = {};
		data["advertise.id"]=obj.attr("adid");
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-advertising!doEditTo.action",
			type: "POST",
			dataType: "html",
			data: data,
			success: function(result){
				$("body").append(result);
			}
		});
	});
	//广告操作
	function adComm(adid,statusValue){
		var data ={};
		var typeTemp = $("a[name='hdad'][Class='current']").attr("adtype");
		data.type= typeTemp;
		data["advertise.id"]=adid;
		data["advertise.status"]=statusValue;
		var callback = function(){
			if(typeTemp==2){
				if(statusValue==0){//下架操作
					$("#statusTD[adid='"+adid+"']").html("下架");
					$("#adDown[adid='"+adid+"']").html("上架");
					$("#adDown[adid='"+adid+"']").attr("id","adUp");
				}else if(statusValue==1){//上架操作
					$("#statusTD[adid='"+adid+"']").html("通过审核");
					$("#adUp[adid='"+adid+"']").html("下架");
					$("#adUp[adid='"+adid+"']").attr("id","adDown");
				}
			}else{
				if(statusValue==7){//下架操作
					$("#statusTD[adid='"+adid+"']").html("下架");
					$("#adDown[adid='"+adid+"']").html("上架");
					$("#adDown[adid='"+adid+"']").attr("id","adUp");
				}else if(statusValue==6){//审核通过操作
					$("#statusTD[adid='"+adid+"']").html("通过审核");
					$("#checkTD[adid='"+adid+"']").html("已审核");
				}else if(statusValue==5){//上架操作
					$("#statusTD[adid='"+adid+"']").html("通过审核");
					$("#adUp[adid='"+adid+"']").html("下架");
					$("#adUp[adid='"+adid+"']").attr("id","adDown");
				}
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertising-list!checkAdJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
		
		//杂志公共
	function magComm(currentPage){
		var data={};
		data.currentPage=currentPage;
		var magName = $("#magName").val();
		var magBeginTime = $("#magTimeBegin").val();
		var magEndTime = $("#magTimeEnd").val();
		if(magName=="杂志名称")magName="";
		if(magBeginTime=="创建时间")magBeginTime="";
		if(magEndTime=="创建时间")magEndTime="";
		data["publication.name"]=magName;
		data["publication.createdTime"]=magBeginTime;
		data["publication.createdTimeEnd"]=magEndTime;
		var callback=function(result){
			firstPid = "";
			if(result.data.publicationList){
				//重置总页数
				pageCount = result.data.pageNo;
				currentPage =1;
				$("#mgMenu").html("");//清空杂志
					var liStr = "";
					$.each(result.data.publicationList,function(k,p){
					if(k==0)firstPid=p.id;
					 liStr +="<li class=\"item showSlide\" name=\"publicationById\"  pid=\""+p.id+"\">";
					 if(p.imgPath){
					 	liStr +="<img src=\""+SystemProp.magServerUrl+p.imgPath+"\" />";
					 }else{
					 	liStr +="<img src=\"/v3/images/cover182-243.gif\" />";
					 }
			         liStr +="           <div class=\"border\"></div>";
			         liStr +="           <strong>"+p.name+"</strong>";
			        liStr +="        </li>";
					});
					if(liStr==""){
						$("#tbodyContext").html("");//清空广告
						piid="empty";
					}else{
						pageComm2(1,firstPid);
						initPage();
					}
					$("#mgMenu").html(liStr);
					
			}
		}
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertising-list!doJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	
	//广告 公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	//广告公共
	function pageComm(currentPage){
		var data = {};
		data.adCurrentPage=currentPage;
		data.status =$("input[name='typeradio']:radio:checked").val();
		data.type=$("a[name='hdad'][Class='current']").attr("adtype");
		data["publication.id"]=piid||"";
		var typeTemp = $("a[name='hdad'][Class='current']").attr("adtype");
		var callback =function(result){
			//alert(result.message);
			if(result.code!=200){
					return;
				}
			$("#tbodyContext").html("");
			if(result.data.adList){
				var trStr = "";
				$.each(result.data.adList,function(k,p){
					trStr +="<tr>";
	                       trStr +=" <td class=\"tLeft\">"+p.publicationName+p.issueNumber+"</td>";
	                       trStr +=" <td>"+p.title+"</td>";
	                       tempDes = "";
	                       if(p.description){
	                       	tempDes=p.description
	                       }
	                       trStr +=" <td>"+tempDes+"</td>";
	                       
	                       var pstat=p.status;
	                       tempStr = ""
	                       if(typeTemp==2){//互动内容
	                        trStr +=" <td>&nbsp;</td>";
	                       		if(pstat==0){
	                       			tempStr = "下架";
	                       		}else if(pstat==1){
	                       			tempStr = "通过审核";
	                       		}
	                       }else{//互动广告和插页广告
	                       hrefUrl = SystemProp.appServerUrl+"/publish/mag-read!adPreview.action?advertiseId="+p.id;
	                       trStr +=" <td><a target='_blank' href=\""+hrefUrl+"\">预览</a></td>";
		                       if(pstat==2||pstat==5||pstat==6||pstat==8){
		                       		tempStr="通过审核";
		                       }else if(pstat==0){
		                       		tempStr="无效";
		                       }
		                       else if(pstat==1){
		                       		tempStr="待审核";
		                       }
		                       else if(pstat==7){
		                       		tempStr="下架";
		                       }
		                   }
		                   trStr +=" <td id='statusTD' adid='"+p.id+"'>"+tempStr+"</td>";
	                       if(p.userTypeId==2){//出版商
	                       		if(typeTemp==2){//互动内容
	                       			trStr +=" <td></td>";
	                       			tempStr3 ="";
		                       		if(pstat==0){
		                       			trStr +=" <td><a class=\"btn\" href=\"javascript:void(0);\" id=\"adUp\" adid=\""+p.id+"\" >上架</a></td>";
		                       		}else{
		                       			trStr +=" <td><a class=\"btn\" href=\"javascript:void(0);\" id=\"adDown\" adid=\""+p.id+"\" >下架</a></td>";
		                       		}
	                       		}else{//互动广告和插页广告
	                       			 trStr +=" <td><a class='btn' id='adEdit' adid='"+p.id+"' href='javascript:void(0);'>编辑</a></td>";
		                      		 if(p.status==7){
		                      		 	trStr +=" <td><a class=\"btn\" href=\"javascript:void(0);\" id=\"adUp\" adid=\""+p.id+"\" >上架</a></td>";
		                      		 }else{
		                      		 	trStr +=" <td><a class=\"btn\" href=\"javascript:void(0);\" id=\"adDown\" adid=\""+p.id+"\" >下架</a></td>";
		                      		 }
								}
	                       }else{
	                       		if(typeTemp==2){//互动内容
	                       			tempStr2 ="";
		                       		if(pstat==0){
		                       			tempStr2 = "下架";
		                       		}else if(pstat==1){
		                       			tempStr2 = "通过审核";
		                       		}
		                       		trStr +=" <td id='checkTD' adid='"+p.id+"'>"+tempStr2+"</td>";
		                       		trStr +=" <td>&nbsp;</td>";
	                       		}else{//互动广告和插页广告
		                      		 if(p.status==6){//审核通过
		                       		 trStr +=" <td id='checkTD' adid='"+p.id+"'>已审核</td>";
		                       		 }else if(p.status==7){//下架
		                       		 trStr +=" <td id='checkTD' adid='"+p.id+"'>下架</td>";
		                       		 }else{
		                       		 trStr +=" <td id='checkTD' adid='"+p.id+"'><a class=\"btn\" href=\"javascript:void(0);\" id=\"adCheck\" adid=\""+p.id+"\" >审核</a></td>";
		                       		 }
		                       		 trStr +=" <td>&nbsp;</td>";
		                       	}
	                       }
					trStr +="</tr>";
				});
				$("#tbodyContext").html(trStr);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertising-list!doADListJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//广告 公共
	function pageComm2(currentPage,pid){
		var data = {};
		data.adCurrentPage=currentPage;
		data.status =$("input[name='typeradio']:radio:checked").val();
		data.type=$("a[name='hdad'][Class='current']").attr("adtype");
		data["publication.id"]=pid||"";
		piid = pid;
		var callback =function(result){
			//alert(result.message);
			if(result.code!=200){
			adPageCount=1;
					return;
				}
			if(result.data.adList){
				adPageCount = result.data.adPageNo;
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertising-list!doADListJson.action",
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
		//alert(adPageCount);
		$("#eventListPage").pagination(adPageCount, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 15, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "上页",
			next_text: "下页"
		});
	}
	
	
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	datePickerFun($("#magTimeBegin"),nowDate);
	datePickerFun($("#magTimeEnd"),nowDate);
	
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
	