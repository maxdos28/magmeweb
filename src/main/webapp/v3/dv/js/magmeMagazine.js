
 $(document).ready(function(){
 	$("#eventListPage").html("");
 	initPage();
 	
 	//推荐
 	$("input[name='p_recommend']").live("click",function(){
 		var oj = $(this);
 		var pid = oj.attr("publicationid");
 		recommendValue = '0';
 		if(oj.attr("checked")){
 			recommendValue = '1';
 		}
 		var data = {};
 		data["publication.id"]=pid||"";
 		data["publication.recommend"]=recommendValue||"";
 		
 		var callback = function(result){
 			//alert(result.message);
 		}
 		
 		$.ajax({
 			url: SystemProp.appServerUrl+"/new-publisher/edit-magazine!doRecommendJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
 		});
 	});
 	
 	//查看定价
 	$("a[name='aPriceLook']").live("click",function(){
 		var oj = $(this);
 		$("#priceChange").html("关闭");
 		$("#priceOk").hide();
 		var pid = oj.attr("pid");
 		var data = {};
 		data.publicationId=pid;
	 		function callback(result){
	 				if(result.code!=200){
	 					alert(result.message);
	 				}
	 				clearPriceForm();
	 				if(result.data){
	 					if(result.data.listProduct){
	 						$.each(result.data.listProduct,function(k,product){
	 							var am = product.amount;
	 							if(product.number==1){$("select[name='price1'] option[value='"+am+"']").attr("selected", true);};
	 							if(product.number==3){$("select[name='price3'] option[value='"+am+"']").attr("selected", true);$("input[name='price3box']").attr("checked",true);};
	 							if(product.number==6){$("select[name='price6'] option[value='"+am+"']").attr("selected", true);$("input[name='price6box']").attr("checked",true);};
	 							if(product.number==12){$("select[name='price12'] option[value='"+am+"']").attr("selected", true);$("input[name='price12box']").attr("checked",true);};
	 							if(product.number==18){$("select[name='price18'] option[value='"+am+"']").attr("selected", true);$("input[name='price18box']").attr("checked",true);};
	 							if(product.number==24){$("select[name='price24'] option[value='"+am+"']").attr("selected", true);$("input[name='price24box']").attr("checked",true);};
	 						});
	 						$("#adminIosPricing").fancybox();
	 					}
	 				}
	 			}
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/edit-magazine!queryPublicationProduct.action",
				type: "POST",
				dataType: "json",
				async:false,
				data: data,
				success: callback
			});
 	});
 	
 	//定价弹出前
 	$("a[name='aPrice']").live("click",function(){
 		var oj = $(this);
 		$("#priceChange").html("取消");
 		$("#priceOk").show();
 		var pid = oj.attr("pid");
 		$("#pubIdPrice").val(pid);
 		clearPriceForm();
 		$("#adminIosPricing").fancybox();
 	});
 	
 	function clearPriceForm(){
 		$("select[name='price1']").val('');
		$("select[name='price3']").val('');
		$("select[name='price6']").val('');
		$("select[name='price12']").val('');
		$("select[name='price18']").val('');
		$("select[name='price24']").val('');
		$("input[name='price3box']").attr("checked",false);
		$("input[name='price6box']").attr("checked",false);
		$("input[name='price12box']").attr("checked",false);
		$("input[name='price18box']").attr("checked",false);
		$("input[name='price24box']").attr("checked",false);
 	}
 	
 	$("select[name='price3']").live("change",function(){
 		var obj = $(this);
 		if(obj.val()!=''){
 			$("input[name='price3box']").attr("checked",true);
 		}else{
 			$("input[name='price3box']").attr("checked",false);
 		}
 	});
	$("select[name='price6']").live("change",function(){
 		var obj = $(this);
 		if(obj.val()!=''){
 			$("input[name='price6box']").attr("checked",true);
 		}else{
 			$("input[name='price6box']").attr("checked",false);
 		}
 	});
	$("select[name='price12']").live("change",function(){
 		var obj = $(this);
 		if(obj.val()!=''){
 			$("input[name='price12box']").attr("checked",true);
 		}else{
 			$("input[name='price12box']").attr("checked",false);
 		}
 	});
	$("select[name='price18']").live("change",function(){
 		var obj = $(this);
 		if(obj.val()!=''){
 			$("input[name='price18box']").attr("checked",true);
 		}else{
 			$("input[name='price18box']").attr("checked",false);
 		}
 	});
	$("select[name='price24']").live("change",function(){
 		var obj = $(this);
 		if(obj.val()!=''){
 			$("input[name='price24box']").attr("checked",true);
 		}else{
 			$("input[name='price24box']").attr("checked",false);
 		}
 	});
 	
 	
 	//保存定价
 	$("#priceOk").live("click",function(){
 		var priceVal= $("select[name='price1']").val();
 		var priceVal3= $("select[name='price3']").val();
 		var priceVal6= $("select[name='price6']").val();
 		var priceVal12= $("select[name='price12']").val();
 		var priceVal18= $("select[name='price18']").val();
 		var priceVal24= $("select[name='price24']").val();
 		var pid = $("#pubIdPrice").val();
 		if(!priceVal){
 			alert("单期价格不能为空!");
 			return;
 		}
 		if(ConfirmDel("该定价策略保存成功后,不允许修改.")){
 			var data = {};
 			data.publicationId=pid;
 			data.price1=priceVal;
 			if(priceVal3){data.price3=priceVal3;};
 			if(priceVal6){data.price6=priceVal6;};
 			if(priceVal12){data.price12=priceVal12;};
 			if(priceVal18){data.price18=priceVal18;};
 			if(priceVal24){data.price24=priceVal24;};
 			function callback(result){
 				if(result.code==200){
 					alert("保存成功");
 					$("a[name='aPrice'][pid='"+pid+"']").html("查看定价");
 					$("a[name='aPrice'][pid='"+pid+"']").attr("name","aPriceLook");
 					$.fancybox.close();
 				}else{
 					alert("保存失败");
 				}
 			}
 			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/edit-magazine!doPublicationProduct.action",
				type: "POST",
				dataType: "json",
				async:false,
				data: data,
				success: callback
			});
 		}
 	});
 	
 	//关闭定价窗口
 	$("#priceChange").live("click",function(){
 		$.fancybox.close();
 	});
 	
 	
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
	
	//搜索
	$("#searchButton").bind("click",function(){
		pageComm2(1);
		initPage();
	});
	
	$("#tbodyContext a[name='publicationNameUrl']").unbind().live("click",function(e){
		e.preventDefault();
		var obj = $(this);
		var url = SystemProp.appServerUrl + "/new-publisher/magazine-list!to.action?publication.name=" + obj.attr("publicationName");
		url = encodeURI(url);
		document.location.href = url;
		//window.location.href = SystemProp.appServerUrl+"/new-publisher/publication-config!to.action?publicationid="+obj.attr("pid")+"&&random="+Math.random();
	});
	
	function pageComm(currentPage){
		var data = {};
		data.currentPage=currentPage;
		data["publication.publishname"]=$("input:[name='publishname']").val()||"";
		data["publication.name"]=$("input:[name='name']").val()||"";
		data["publication.level"]=$("input:[name='level']:radio:checked").val()||"";
		data["publication.isFree"]=$("input:[name='isFree']:radio:checked").val()||"";
		data["publication.recommend"]=$("select:[name='recommend']").val()||"";
		
		var callback =function(result){
			//alert(result.message);
			$("#tbodyContext").html("");
			if(result.data.publicationList){
				var trStr = "";
				$.each(result.data.publicationList,function(k,p){
					trStr +="<tr>";
					trStr +="<td class=\"tLeft\">"+p.publishname+"</td>";
					trStr +=' <td class=\"tLeft\"><a href="javascript:void(0)" name="publicationNameUrl"  publicationName="' + p.name + '" >' + p.name + '</a></td>';
					
					//trStr +="<td><a name=\"publicationNameUrl\" pid=\""+p.id+"\">"+p.name+"</a></td>";
					var levelkey = p.level;
					levelValue = '';
					if(levelkey=='1'){
						levelValue = '一线';
					}
					else{
						levelValue = '非一线';
					}
					trStr +="<td>"+levelValue+"</td>";
                    statusValue = p.status;
					if(statusValue=='1'){
						trStr +="<td>上架</td>";
					}else if(statusValue=='2'){
						trStr +="<td>上架</td>";
					}else if(statusValue=='3'){
						trStr +="<td>后台上架</td>";
					}else{
						trStr +="<td>待审核</td>";
					}
					
					
					if(p.isFree==0){//收费
						trStr +="<td>收费</td>";
						if(p.totalIssues==1){
							trStr +="<td><a class='btn' href='javascript:void(0);' name='aPriceLook' pid='"+p.id+"' >查看定价</a></td>";
						}else{
							trStr +="<td><a class='btn' href='javascript:void(0);' name='aPrice' pid='"+p.id+"' >设置定价</a></td>";
						}
					}else{//免费
						trStr +="<td>免费</td>";
						trStr +="<td>&nbsp;</td>";
					}
					
                    if(p.recommend=='1'){
                    	trStr +="<td><label><input type=\"checkbox\" name=\"p_recommend\" publicationid=\""+p.id+"\" checked=\"checked\" /></label></td>";
                    }else{
                    	trStr +="<td><label><input type=\"checkbox\" name=\"p_recommend\" publicationid=\""+p.id+"\" /></label></td>";
                    }
					trStr +="</tr>";
				});
				pageCount = result.data.pageNo;
				$("#tbodyContext").html(trStr);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-magazine!doJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage){
		var data = {};
		data.currentPage=currentPage;
		data["publication.publishname"]=$("input:[name='publishname']").val()||"";
		data["publication.name"]=$("input:[name='name']").val()||"";
		data["publication.level"]=$("input:[name='level']:radio:checked").val()||"";
		data["publication.isFree"]=$("input:[name='isFree']:radio:checked").val()||"";
		data["publication.recommend"]=$("select:[name='recommend']").val()||"";
		
		var callback =function(result){
			//alert(result.message);
			$("#tbodyContext").html("");
			if(result.data.publicationList){
				//var trStr = "";
				$.each(result.data.publicationList,function(k,p){
				});
				pageCount = result.data.pageNo;
				//$("#tbodyContext").html(trStr);
				//fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-magazine!doJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	
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
 });
	