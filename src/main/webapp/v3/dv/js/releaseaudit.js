$(function(){
	initPage();
	var t = [];
	function pname(val){
		var url = SystemProp.appServerUrl + "/new-publisher/release-audit!getPageDByName.action";
		$.ajax({
			url:url,
			type : "POST",
			data : {"title":val},
			async: false,
			success: function(result){
				if(result.code!=200) return;
				//没有数量可以去掉num值
				t = [];
				if(result.data.result){
					var ii = result.data.result; 
					$.each(ii,function(k,a){
						t.push(ii[k]);
					});
				}
			}
		});
		return t;
	}
	$("#select_pagedid").smartSearch(jsonFn);
	function jsonFn(val) {
		return pname(val);
	}
	$("#edit_pagedid").smartSearch(jsonFn);
	
	
	$("#allcc").live("click",function(){
		var item = $(this).attr("checked");
		if(item){//全选
			$("input[name='raCheck'][type='checkbox']").attr("checked", true);
             //$("#playList :checkbox").attr("checked", true);  
		}else{//取消全选
			$("input[name='raCheck'][type='checkbox']").attr("checked", false);
		}
	});
	
	function emCheckBox(){
		$("#allcc").attr("checked", false);
	}
	
	//通过
	$("#passBtn").live("click",function(e){
		var data ={};
		if(checkedPub()){
		data.idStr =checkedPub();
		data.status = '1';
		checkPubC(data);
		emCheckBox();
		}
		
	});
	
	//待审核
	$("#noPassBtn").live("click",function(e){
		var data ={};
		if(checkedPub()){
		data.idStr = checkedPub();
		data.status = '2';
		checkPubC(data);
		emCheckBox();
		}
		
	});
	
	//首页的分类
	$("#isNotHomeBtn").live("click",function(e){
		var data ={};
		if(checkedPub()){
		data.idStr = checkedPub();
		data.status = '3';
		checkPubC(data);
		emCheckBox();
		}
		
	});
	
	//推荐
	$("#isRecommendBtn").live("click",function(e){
		var data ={};
		if(checkedPub()){
		data.idStr = checkedPub();
		data.status = '1';
		checkPubRecommend(data);
		emCheckBox();
		}
		
	});
	
	//未推荐
	$("#isRecommendNotBtn").live("click",function(e){
		var data ={};
		if(checkedPub()){
		data.idStr = checkedPub();
		data.status = '0';
		checkPubRecommend(data);
		emCheckBox();
		}
		
	});
	
	$('#releaseOk').click(function(){window.open(SystemProp.appServerUrl + '/index!publish.action?timestamp='+((new Date).getTime()));});
	
	function checkedPub(){
		var str="";
		var strLength = $("input[name='raCheck']:checkbox:checked").length;
		if(strLength==0) {
			alert('请选择审核项');
			return;
		}
		$("input[name='raCheck']:checkbox:checked").each(function () {  
			 str += $(this).val()+","
         });  
		str.split(",");
		return str;
	}
	
	//check public static
	function checkPubC(data){
		var callback = function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				alert('修改成功');
				pageComm2(1);
				initPage();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/release-audit!updateStatusM1Json.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//check public Recommend
	function checkPubRecommend(data){
		var callback = function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				alert('修改成功');
				pageComm2(1);
				initPage();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/release-audit!updateIsRecommendM1Json.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//搜索
	$("#search_btn").live("click",function(){
		pageComm2(1);
		initPage();
	});
	//取消编辑
	$("#eseBtn").live("click",function(e){
		e.preventDefault();
		$.fancybox.close();
	});
	//保存
	$("#okBtn").live("click",function(e){
		e.preventDefault();
		var obj = $(this);
		var data ={};
		data.prid=$("#editReleaseAuditForm input[name='cid']").val();
		//data.described=$("#editReleaseAuditForm textarea[name='content']").val();
		data.title=$("#editReleaseAuditForm input[name='pagedid']").val();
		//data.weight=$("#editReleaseAuditForm input[name='weight']").val();
		var callback = function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				alert('修改成功');
				pageComm2(1);
				initPage();
				$.fancybox.close();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/release-audit!updateM1Json.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	
		
	});
	//编辑
	$("a[name='releaseauditedit']").live("click",function(){
		var obj = $(this);
		var data ={};
		data.prid=obj.attr("aaid");
		var callback = function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				var pojo = result.data.creativepojo;
				var tag
				//赋值
				$("#editReleaseAuditForm input[name='cid']").val(pojo.cid);
				$("#editReleaseAuditForm img[name='imagePath']").attr("src","http://static.magme.com"+pojo.imagepath);
				$("#editReleaseAuditForm input[name='pagedid']").val(pojo.reserve);
				//$("#editReleaseAuditForm textarea[name='described']").val(pojo.described);
				//$("#editReleaseAuditForm textarea[name='content']").val(pojo.content);
				//$("#editReleaseAuditForm input[name='weight']").val(pojo.weight);
				//
				var tagContent = "";
				if(result.data.tagList){
					$.each(result.data.tagList,function(k,pojo){
						tagContent += "<em>"+pojo.name+"</em>";
					});
				}
				$("#editReleaseAuditForm #tagContent").html(tagContent);
				$("#adminCon1").fancybox();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/release-audit!editM1Json.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//list
	// 公共
	function pageComm(currentPage,isFlag){
		var data = {};
		data.idStr=$("input[name='search_pagedid']").val();
		data.status=$("select[name='search_status']").val();
		data.title=$("input[name='search_title']").val();
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
				$.each(result.data.prList,function(k,a){
					trContent+="<tr>";
					trContent+="<td><label><input name='raCheck' type='checkbox' value='"+a.cid+"' /></label></td>";
					trContent+="<td>"+a.title+"</td>";
					if(a.imagepath){
						trContent+="<td><img width='80px' height='40px' src='http://static.magme.com"+a.imagepath+"' /></td>";
					}else{
						trContent+="<td>&nbsp;</td>";
					}
					if(a.name){
					trContent+="<td>"+a.name+"</td>";
					}else{
					trContent+="<td>&nbsp;</td>";
					}
					trContent+="<td>"+a.username+"</td>";
					/*
					trContent+="<td>"+a.weight+"</td>";
					if(a.isrecommend==1){
						trContent+="<td>推荐</td>";
					}else{
						trContent+="<td>未推荐</td>";
					}*/
					
					
					trContent+="<td><a class='btn' aaid='"+a.cid+"' name='releaseauditedit' href='javascript:void(0)'>编辑</a></td>";
					/*
					var ttStatus="";
					if(a.ishome==1){
						ttStatus="通过";
					}else if(a.ishome==2){
						ttStatus="待审核";
					}else if(a.ishome==3){
						ttStatus="频道显示";
					}
					trContent+="<td>"+ttStatus+"</td>";
					*/
					if(a.createtime){
						trContent+="<td>"+a.createtime.substring(0,10)+"</td>";	
					}else{
						trContent+="<td>&nbsp;</td>";
					}
					
					trContent+="</tr>";
				});
				$("#tbodyContext").html(trContent);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/release-audit!listM1Json.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage,isFlag){
		var data = {};
		data.idStr=$("input[name='search_pagedid']").val();
		data.status=$("select[name='search_status']").val();
		data.title=$("input[name='search_title']").val();
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
//				var trContent = "";
//				$("#tbodyContext").html(trContent);
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/release-audit!listM1Json.action",
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
			num_display_entries: 15, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "上页",
			next_text: "下页"
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
				num_display_entries: 15, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				current_page:currentPage-1, 
				prev_text: "上页",
				next_text: "下页"
			});
		});
})