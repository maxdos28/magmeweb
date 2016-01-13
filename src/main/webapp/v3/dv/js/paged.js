$(function(){
	initPage();
	//检索
	$("#filter_search").bind("click",function(){
		pageComm2(1,true);
		initPage();
	});
	
	if($("a[name='pageDedit']").offset()){
		$("html,body").animate({scrollTop: $("a[name='pageDedit']").offset().top}, 1000);
	}
	
	
	//编辑
	$("a[name='pageDedit']").live("click",function(){
		$("html,body").animate({scrollTop: $("body").offset().top}, 1000);//焦点定位到顶端修改位置
		var obj = $(this);
		var data = {};
		data.id=obj.attr('aaid');
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
			}else{
				var po = rs.data.paged;
				$("#pageDForm input[name='aaid']").val(po.id);
				$("#pageDForm textarea[name='title']").val(po.title);
				$("#pageDForm input[name='name']").val(po.name);
				$("#pageDForm select[name='firstLetter']").val(po.firstLetter);
				$("#pageDForm select[name='categoryId']").val(po.categoryId);
				$("#pageDForm input[name='tags']").val(po.tags);
				$("#pageDForm textarea[name='description']").val(po.description);
				$("#pageDForm textarea[name='keyWord']").val(po.keyWord);
				$("#pageDForm textarea[name='headerDesc']").val(po.headerDesc);
				$("#pageDForm textarea[name='indexDesc']").val(po.indexDesc);
				$("#pageDForm input[name='shortTitle']").val(po.shortTitle);
				if(po.isHot>0)
					$("#pageDForm input[name='isHot']").attr("checked",true);
				else
					$("#pageDForm input[name='isHot']").attr("checked",false);
				if(po.picUrl){
					$("#pageDForm img[name='picUrl']").attr("src","http://static.magme.com/paged"+po.picUrl);
				}else{
					$("#pageDForm img[name='picUrl']").attr("src","/images/head172.gif");
				}
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/page-d!getPageD.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//删除
	$("a[name='pageDstatus']").live("click",function(){
		if(!ConfirmDel('确认删除吗？')) return;
		var obj = $(this);
		var data={};
		data.id=obj.attr('aaid');
		data.status=2;
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
			url: SystemProp.appServerUrl+"/new-publisher/page-d!editPagedJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//重置表单
		$("#chanelPageDBtn").live("click",function(){
			$("#pageDForm")[0].reset();
			$("#pageDForm input[name='aaid']").val("");
			$("#pageDForm img[name='picUrl']").attr("src","/images/head172.gif");
			$("#pageDForm input[name='isHot']").attr("checked",false);
		});
	//保存
	$("#savePageDBtn").live("click",function(){ 
		var url=SystemProp.appServerUrl+"/new-publisher/page-d!updateImageJson.action";
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
	            			var url = SystemProp.appServerUrl + "/new-publisher/page-d!addPagedJson.action";
	            			var data = {};
	            			if(rs.data.imgpath){ data.url=rs.data.imgpath; }
	            			if(rs.data.width){ data.width=rs.data.width; }
	            			if(rs.data.height){ data.height=rs.data.height; }
							data.id=$("#pageDForm input[name='aaid']").val();
							data.title=$("#pageDForm textarea[name='title']").val();
							data.name=$("#pageDForm input[name='name']").val();
							data.firstLetter=$("#pageDForm select[name='firstLetter']").val();
							data.categoryId=$("#pageDForm select[name='categoryId']").val();
							data.tags=$("#pageDForm input[name='tags']").val();
							data.headerDesc=$("#pageDForm textarea[name='headerDesc']").val();
							data.description=$("#pageDForm textarea[name='description']").val();
							data.keyWord=$("#pageDForm textarea[name='keyWord']").val();
							data.indexDesc=$("#pageDForm textarea[name='indexDesc']").val();
							data.shortTitle=$("#pageDForm input[name='shortTitle']").val();
							data.isHot=0;
							if($("#pageDForm input[name='isHot']").attr("checked")=='checked')
								data.isHot=1;
							
							var callback = function(result){
								if(!result) return;
								
								if(result.code != 200){
									alert(result.message);
								}else{
									alert("保存成功");
							 		$("#chanelPageDBtn").click();
							 		pageComm2(1,true);
									initPage();
								}
							};						
							$.ajax({
								url:url,
								type : "POST",
								dataType: "json",
								data : data,
								success: callback
							});
					 		
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
		data.categoryId=$("select[name='filter_type']").val();
		data.name=$("input[name='filter_title']").val();
		if(data.name=='标题')data.name="";
		data.pageCurrent=currentPage;
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageCount){
					pageCount = result.data.page.totalPage;
				}
				var trContent = "";
				$.each(result.data.page.data,function(k,a){
					trContent+="<tr>";
					trContent+="<td>"+a.name+"</td>";
					trContent+="<td>"+a.firstLetter+"</td>";
					trContent+="<td>"+a.categoryName+"</td>";
					trContent+="<td title='"+a.headerDesc+"'>"+strSub(a.headerDesc)+"</td>";
					trContent+="<td title='"+a.indexDesc+"'>"+strSub(a.indexDesc)+"</td>";
					trContent+="<td><a class='btn' aaid='"+a.id+"' name='pageDedit' href='javascript:void(0)'>编辑</a></td>";
					trContent+="<td><a class='del' aaid='"+a.id+"' name='pageDstatus' href='javascript:void(0)'>删除</a></td>";
					trContent+="</tr>";
				});
				$("#tbodyContext").html(trContent);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/page-d!pageDJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage,isFlag){
		var data = {};
		data.categoryId=$("select[name='filter_type']").val();
		data.name=$("input[name='filter_title']").val();
		if(data.name=='标题')data.name="";
		data.pageCurrent=currentPage;
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.page.totalPage){
				pageCount = result.data.page.totalPage;
				}
				//var trContent = "";
				//$("#tbodyContext").html(trContent);
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/page-d!pageDJson.action",
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
	
	function strSub(v){
		if(v==null || v==$.trim(""))
			return "";
		if(v.length>100){
			return v.substr(0,100)
		}else
			return v;
	}
});