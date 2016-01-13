$(function(){
	initPage();
	$("select[name='filter_type']").bind("change",function(){
		pageComm2(1,true);
		initPage();
	})
	
	$("select[name='filter_status']").bind("change",function(){
		pageComm2(1,true);
		initPage();
	})
	
	//编辑
	$("a[name='activityalbumedit']").live("click",function(){
		$("html,body").animate({scrollTop: $("body").offset().top}, 1000);//焦点定位到顶端修改位置
		var obj = $(this);
		var data = {};
		data.aaid=obj.attr('aaid');
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
			}else{
				var po = rs.data.activityAlbum;
				$("#activityAlbumForm input[name='aaid']").val(po.id);
				$("#activityAlbumForm input[name='title']").val(po.title);
				$("#activityAlbumForm input[name='weights']").val(po.weights);
				$("#activityAlbumForm input[name='url']").val(po.url);
				$("#activityAlbumForm textarea[name='alt']").val(po.alt);	
				$("#activityAlbumForm select[name='type']").val(po.type);
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/activity-album!getPojoJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//修改状态
	$("a[name='activityalbumstatus']").live("click",function(){
		var obj = $(this);
		var data={};
		data.aaid=obj.attr('aaid');
		var s = obj.attr('aastatus');
		if(s==1){//删除
			if(!ConfirmDel('确认下架吗？')) return;
		}
		if(s==0 || s==2){
			s=1;
		}else{
			s=0;
		}
		data.status=s;
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
			}else{
				alert("更新成功");
				pageComm2(1,true);
				initPage();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/activity-album!updateStatusJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//重置ActivityAlbum表单
		$("#resetActivityAlbumBtn").live("click",function(){
			$("#activityAlbumForm")[0].reset();
			$("#activityAlbumForm input[name='aaid']").val("");
			$("#activityAlbumForm input[name='title']").val("");
			$("#activityAlbumForm input[name='weights']").val("");
			$("#activityAlbumForm input[name='url']").val("");
			$("#activityAlbumForm textarea[name='alt']").val("");
			$("#activityAlbumForm select[name='type']").val(0);
		});
	//保存
	$("#saveActivityAlbumBtn").live("click",function(){
		var url=SystemProp.appServerUrl+"/new-publisher/activity-album!updateJson.action";
		   url += "?aaid="+encodeURIComponent($("#activityAlbumForm input[name='aaid']").val()||"");
		   url += "&title="+encodeURIComponent($("#activityAlbumForm input[name='title']").val()||"");
		   url += "&alt="+encodeURIComponent($("#activityAlbumForm textarea[name='alt']").val()||"");
		   var ttt=$("#activityAlbumForm select[name='type']").val();
		   if(ttt!=9999){
			   url += "&url="+encodeURIComponent($("#activityAlbumForm input[name='url']").val()||"");   
		   }
		   url += "&weights="+encodeURIComponent($("#activityAlbumForm input[name='weights']").val()||"");
		   url += "&type="+encodeURIComponent($("#activityAlbumForm select[name='type']").val()||"");		   		   
	       $.ajaxFileUpload(
	            {
	                url:url,//用于文件上传的服务器端请求地址
	                secureuri:false,//一般设置为false
	                fileElementId:"pic",//文件上传空间的id属性  <input type="file" id="file" name="file" />
	                dataType: "json",//返回值类型 一般设置为json
	                success: function (rs, status)  //服务器成功响应处理函数
	                { 
	            		if(rs.code!=200){
	            			alert(rs.message);	
	            		}else{
					 		alert("保存成功");
					 		var tv=$("#activityAlbumForm select[name='type']").val();
					 		$("#resetActivityAlbumBtn").click();
					 		$("select[name='filter_type']").val(tv);
					 		$("select[name='filter_status']").val(12);
					 		pageComm2(1,true);
							initPage();
					 		
	                    }
	                },
	                //服务器响应失败处理函数
	                error: function (data, status, e) {
	                    return;
	                }
	            }
	        );
	});
	
	
	//list
	// 公共
	function pageComm(currentPage,isFlag){
		var data = {};
		//data.aaid=$("#activityAlbumForm input[name='aaid']").val();
		//data.title=$("#activityAlbumForm input[name='title']").val();
		//data.alt=$("#activityAlbumForm textarea[name='alt']").val();
		//data.url=$("#activityAlbumForm input[name='url']").val();
		//data.weights=$("#activityAlbumForm input[name='weights']").val();
		data.type=$("select[name='filter_type']").val();
		data.status=$("select[name='filter_status']").val();
		data.pageCount=pageCount;
		data.pageCurrent=currentPage;
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageCount){
				pageCount = result.data.pageCount;
				}
				var trContent = "";
				$.each(result.data.listaa,function(k,a){
					trContent+="<tr>";
					trContent+="<td>"+a.title+"</td>";
					trContent+="<td><img width='100px' height='40px' src='http://static.magme.com/activityalbum"+a.image+"' /></td>";
					if(a.type!=9999){
					trContent+="<td>"+a.url+"</td>";
					}
					var tpyeValue = "";
					var atype =a.type;
					if(atype==0){tpyeValue="首页";}
					else if(atype==43){tpyeValue="IT";}
					else if(atype==44){tpyeValue="育儿";}
					else if(atype==45){tpyeValue="财经";}
					else if(atype==46){tpyeValue="汽车";}
					else if(atype==47){tpyeValue="旅游";}
					else if(atype==48){tpyeValue="娱乐";}
					else if(atype==49){tpyeValue="时尚";}
					else if(atype==9999){tpyeValue="开机图片";}
					trContent+="<td>"+tpyeValue+"</td>";
					trContent+="<td>"+a.weights+"</td>";
					if(a.createDate){
						trContent+="<td>"+a.createDate.substring(0,10)+"</td>";
					}else{
						trContent+="<td>&nbsp;</td>";
					}
					var statusVar = "";
					var opButtonValue = "";
					var tstatus=a.status;
					if(tstatus==2){
						statusVar="待发布";
						opButtonValue="发布";
					}else if(tstatus==1){
						statusVar="已发布";
						opButtonValue="下架";
					}else if(tstatus==0){
						statusVar="下架";
						opButtonValue="发布";
					}
					trContent+="<td>"+statusVar+"</td>";
					trContent+="<td><a class='btn' aaid='"+a.id+"' name='activityalbumedit' href='javascript:void(0)'>编辑</a></td>";
					if(tstatus==1){
						trContent+="<td><a class='del' aaid='"+a.id+"' aastatus='"+tstatus+"' name='activityalbumstatus' href='javascript:void(0)'>"+opButtonValue+"</a></td>";
					}else{
						trContent+="<td><a class='btn' aaid='"+a.id+"' aastatus='"+tstatus+"' name='activityalbumstatus' href='javascript:void(0)'>"+opButtonValue+"</a></td>";
					}
					trContent+="</tr>";
				});
				$("#tbodyContext").html(trContent);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/activity-album!listJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage,isFlag){
		var data = {};
		data.type=$("select[name='filter_type']").val();
		data.status=$("select[name='filter_status']").val();
		data.pageCount=pageCount;
		data.pageCurrent=currentPage;
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageCount){
				pageCount = result.data.pageCount;
				}
				//var trContent = "";
				//$("#tbodyContext").html(trContent);
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/activity-album!listJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	
	//公共
	function pageselectCallback(page_id){
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
})