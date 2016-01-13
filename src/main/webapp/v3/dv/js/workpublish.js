$(function(){
	initPage();
	var sortId;
	var secondSortId;
	var title;
	var userName;
	var id;
	
	//全选或者取消全选
	$("#theadAllCheckBox").click(function(){
		if($("#theadAllCheckBox").attr("checked")){
			$("input[name='tbodyCheckBox']").attr("checked",true);
		}else{
			$("input[name='tbodyCheckBox']").attr("checked",false);
		}
	});
	
	//未审核页面 begin
	//--通过审核按钮 
	$("#workReleaseCheckId").click(function(){
		var list = checkObjectList();
		if(list){
			updateCreativeIsHomeComm(list,"5");
			//$("#theadAllCheckBox").attr("checked",false);
			//location.href = "/new-publisher/work-publish.action";
			//pageComm2(1);
			//initPage();
		}else{
			alert("请选择作品");
			return;
		}
	});
	//添加标签
	$("#popAddTagValue").unbind("keyup").live("keyup", function(e){
		if(e.keyCode == 13){
		onAddTag();
		}
		}); 
	$("#popAddTag").live("click",function(){
		onAddTag();
	});
	function onAddTag(){
		var name= $("#popAddTagValue").val();
		if(name){
		if(name=='添加新标签'){
		alert("标签名称不能为空");
		return;
		}
		}else{
		alert("标签名称不能为空");
		return;
		}
		var repeat = '';
		var names = name.split(",");
		for(var i = 0; i < names.length; i++){
		$("#popAdminTagList li a").each(function(){
		if($(this).html() === names[i]){
		repeat = names[i];
		return false;
		}
		});
		}
		if(repeat){
		alert("不能重复添加标签:" + repeat);
		return;
		}
		for(var i = 0; i < names.length; i++){
		$("#popAdminTagList li").first().after('<li><a class="change" href="javascript:void(0);">' + names[i] + '</a></li>');
		}
		$("#popAddTagValue").val("");
		} 
	//编辑是选择对应的分类时的事件监听
	$("input[name='picCollection']").live("click",function(){
		var parentId = $(this).attr("parentid");
		if(parentId){
			if(parentId<=4){
				if($(this).attr("checked")){//选择
					var checkedFlag = 0;
					$.each($("input[name='ccname'][parentid='"+parentId+"']"),function(){
						if($(this).attr("checked")){
							checkedFlag =1;
						}
					});
					if(checkedFlag==0){//不能够选择  因为没有对应的分类
						$(this).attr("checked",false);
						alert("请先选择对应的分类");
					}else{
						isPicCollectionFlag(parentId,1);
					}
				}else{
					isPicCollectionFlag(parentId,0);
				}
			}
		}
	});
	
	function isPicCollectionFlag(parentId,val){
		$.each($("input[name='ccname'][parentid='"+parentId+"']"),function(){
			$(this).attr("picCollection",""+val);
		});
	}
	
	//删除标签
	$("li>a",$("#popAdminTagList")).unbind("dblclick").live("dblclick",function(){
		var $obj=$(this);
		if(!$(this).attr("tagId")){
			$(this).remove();
			return;
		}
		if(!confirm("是否确认删除这个标签?")){
			return false;
		}
		
		function callback(result){
			if(result.code!=200){
				alert("删除失败");
			}else{
				$obj.remove();
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/work-publish!deleteTagJson.action",
			type: "POST",
			dataType: "json",
			data: {"id":$(this).attr("tagId")},
			success: callback
		});
		
	});
	
	//删除作品
	$("a[delcreativeid]").unbind("click").live("click",function(){
		if(!confirm("是否确认删除作品?")){
			return false;
		}
		function callback(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				pageComm2(1);
				initPage();
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/work-publish!deleteCreative.action",
			type: "POST",
			dataType: "json",
			data: {"id":$(this).attr("delcreativeid")},
			success: callback
		});
	});
	
	//编辑
	$("a[creativeid]").live("click",function(){
		var obj = $(this);
		var data = {};
		var creId = obj.attr("creativeid");
		data.sortId = creId;
		function callback(result){
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data){
					clearPopContent();
					var obj=result.data.creative;
					var typeList = result.data.creativeCategoryRel;
					var tagList = result.data.tagList;
					var ccList=result.data.ccList;
					var cncrList=result.data.cncrList;
					if(obj){
					//录入时的分类
					if(typeList){
						$.each(typeList,function(k,type){
							var dbccid = type.categoryId+'';
							var picId =type.picCollection;
							$.each($("input[name='ccname']"),function(){
								var ccid = $(this);
								if(ccid){
									var ccidValue = ccid.attr("childcategoryid");
									if(dbccid == ccidValue){
										ccid.attr("checked",true);
										var parentPicId = ccid.attr("parentid");
										if(picId==1){
											var picObj = $("input[name='picCollection'][parentid='"+parentPicId+"']");
											if(picObj){
												picObj.attr("checked",true);
												isPicCollectionFlag(parentPicId,1);
											}
										}
										
									}
								}
							});
						});
					}
					
					//标签集合 popAdminTagList
					if(tagList){
						var tagStr = "";
						$.each(tagList,function(ui,tag){
							if(tag){
								tagStr += "<li><a tagId=\"" +tag.id+"\" href=\"javascript:void(0);\">"+tag.name+"</a></li>";
							}
						});
						$("#popAdminTagList").append(tagStr);
					}
					if(ccList){
						var ccListStr = "";
						$.each(ccList,function(ui,cc){
							if(cc){
								var check=0;
								if(cncrList){
									for(var i=0;i<cncrList.length;i++){
										var cncr=cncrList[i];
										if(cncr.categoryId==cc.id){
											check=1;
										}
									}
								}
								if(check==1){
									ccListStr += "<li><label><input ccid="+cc.id+" type='checkbox' checked='checked'/>"+cc.name+"</label></li>";
								}else{
									ccListStr += "<li><label><input ccid="+cc.id+" type='checkbox' />"+cc.name+"</label></li>";
								}
								
							}
						});
						$("#neteaseCategoryList").append(ccListStr);
					}
					$("#popCreativeIdHidden").val(creId);	
					$("#popContentTitleDig").val(obj.secondTitle);
					$("#popContentDescribedDig").val(obj.secondDesc);
					$("#adminContentEdit").fancybox();
					}else{
						alert("数据读取失败");
					}
					
					
				}
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/work-publish!editCreativePre.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//保存编辑 
	$("#popPublishOk").live("click",function(){
		var data = {};
		var crid = $("#popCreativeIdHidden").val();
		data.sortId = crid;
		data.title =$("#popContentTitleDig").val();
		data.described =$("#popContentDescribedDig").val();
		var relstrs = [];
		var picstrs = [];
		var categoryIds="";
		$("input[ccid]:checked").each(function(){categoryIds+=$(this).attr("ccid")+",";});
		if(categoryIds && categoryIds!=""){
			data.categoryIds=categoryIds;
		}
		$("input[name='ccname']").each(function(){
			if($(this).attr("checked")){
				relstrs[relstrs.length] = $(this).attr("childcategoryid");
				picstrs[picstrs.length] = $(this).attr("piccollection");
			}
		});
		var relArrayStr = relstrs.join(",");
		var picArrayStr = picstrs.join(",");
		if(relArrayStr){
			data.relStr = relArrayStr;
		}
		if(picArrayStr){
			data.picStr = picArrayStr;
		}
		
		var tags = [];
		$("ul li a[class='change']").each(function(){
			tags[tags.length] = $(this).html();
		});
		var tagNames = tags.join(",");
		if(tagNames){
		data["tag.name"] = tagNames;
		data["tag.objectId"] = crid;
		data["tag.type"] = "4";
		} 
		
		function callback(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				alert("编辑成功");
				$.fancybox.close();
				pageComm2(1);
				initPage();
			}
		}
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/work-publish!editCreative.action",
			type:"post",
			dataType:"json",
			data: data,
			success: callback
		});
	});
	
	function clearPopContent(){
		$.each($("input[name='ccname']"),function(){
			$(this).attr("checked",false);
			$(this).attr("piccollection","0");
		});
		$.each($("input[name='picCollection']"),function(){
			$(this).attr("checked",false);
		});
		$("#popAdminTagList").html('<li class="add"><input type="text" id="popAddTagValue" tips="添加新标签" /><a id="popAddTag" href="javascript:void(0);"></a></li>');
		$("#popCreativeIdHidden").val("");
		$("#popContentTitleDig").val("");
		$("#popContentDescribedDig").val("");
		$("#neteaseCategoryList").html("");
	}
	//未审核页面 end
	//已审核页面 begin 
	//置顶 
	$("input[name='tbodyCheckBoxTop']").live("click",function(){
		var obj = $(this);
		if(obj.val()){
			if(obj.attr("checked")){//置顶
				updateCreativeComm(obj.val(),"top","1");
			}else{//撤销置顶
				updateCreativeComm(obj.val(),"top","0");
			}
		}else{
			alert("无操作对象");
		}
		
	});
	//频道置顶 
	$("input[name='tbodyCheckBoxTopChannel']").live("click",function(){
		var obj = $(this);
		if(obj.val()){
			if(obj.attr("checked")){//置顶
				updateCreativeComm(obj.val(),"topChannel","1");
			}else{//撤销置顶
				updateCreativeComm(obj.val(),"topChannel","0");
			}
		}else{
			alert("无操作对象");
		}
		
	});
	//权重修改
	$("input[name='createIdWeight']").live("change",function(){
		var obj = $(this);
		if(obj.attr("cId")){
			updateCreativeComm(obj.attr("cId"),"weight",obj.val());
		}else{
			alert("无操作对象");
		}
	});
	//重新审核
	$("#workAuditedAgainCheck").click(function(){
		var list = checkObjectList();
		if(list){
			updateCreativeIsHomeComm(list,"2");
		}else{
			alert("请选择作品");
			return;
		}
	});
	//立即发布
	$("#workAuditedSave").click(function(){
		var list = checkObjectList();
		if(list){
			$("#workAuditedSaveEm").html("<div class=\"loading32\"></div>");
			var data = {};
			data.sortIdStr = list;
			function callback(result){
				if(result.code!=200){
					alert(result.message);
				}else{
					$("#workAuditedSaveEm").html("<a class=\"btnAS\" id=\"workAuditedSave\" href=\"#\">立即发布(慎用)</a>");
					$("#theadAllCheckBox").attr("checked",false);
					pageComm2(1);
					initPage();
				}
			}
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/work-publish!atOncePublish.action",
				type:"post",
				dataType:"json",
				data: data,
				success: callback
			});
		}else{
			alert("请选择作品");
			return;
		}
	});
	
	//定时发布前的数据准备
	$("#workAuditedTime").click(function(){
		var list = checkObjectList();
		if(list){
			$("#adminTimingIdStr").val(list);
			$("#adminTiming").fancybox();
		}else{
			alert("请选择作品");
			return;
		}
	});
	//定时发布保存时间
	$("#workAuditedOk").live("click",function(){
		if($("#adminTimingIdStr").val()){
			if(!$("#sendTime").val()){alert("日期必选");return;}
			if(!$("#sendHour").val()){alert("时段不能为空");return;}
			var data = {};
			data.sortIdStr = $("#adminTimingIdStr").val();
			data.time= $("#sendTime").val().trim()+" "+$("#sendHour").val().trim();
			function callback(result){
				if(result.code!=200){
					alert(result.message);
				}else{
					$.fancybox.close();
					$("#theadAllCheckBox").attr("checked",false);
					pageComm2(1);
					initPage();
				}
			}
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/work-publish!delayedRelease.action",
				type:"post",
				dataType:"json",
				data: data,
				success: callback
			});
		}else{
			alert("缺少对象");
			return;
		}
	});
	
	function updateCreativeComm(cid,name,val){
		var data = {};
		data.sortId = cid;
		if(name){
			if(name=='weight'){
				data.weight=val;
			}
			if(name=='top'){
				data.isrecommend=val;
			}
			if(name=='topChannel'){
				data.isrecommendChannel=val;
			}
		}else{
			alert("值传递出错");
			return;
		}
		function callback(result){
			if(result.code!=200){
				if(name=='weight'){
					$("input[cid='"+cid+"']").val("0");//重置权重为0
				}
				alert(result.message);
			}else{
				pageComm2(1);
				initPage();
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/work-publish!creativeValChange.action",
			type:"post",
			dataType:"json",
			data: data,
			success: callback
		});
	}	
	//已审核页面 end
	
//	已发布页面 begin
	//撤销发布
	$("#workpublishedRevocation").click(function(){
		var list = checkObjectList();
		if(list){
			updateCreativeIsHomeComm(list,"5");
		}else{
			alert("请选择作品");
			return;
		}
	});
	//撤销置顶
	$("a[name='createIdIsrecommend']").live("click",function(){
		var obj = $(this);
		updateCreativeComm(obj.attr("cid"),"top","0");
	});
	//取消置顶
	
	//撤销频道置顶
	$("a[name='createIdIsrecommendChannel']").live("click",function(){
		var obj = $(this);
		updateCreativeComm(obj.attr("cid"),"topChannel","0");
	});
	//取消频道置顶
	
//	已发布页面 end
	
	//关闭pop
	$("#popPublishClose,#workAuditedClose").click(function(){
		$.fancybox.close();
	});
	
	function updateCreativeIsHomeComm(idStr,isHome){
		var data = {};
		data.sortIdStr = idStr;
		data.ishome=isHome;
		function callback(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				$("#theadAllCheckBox").attr("checked",false);
				pageComm2(1);
				initPage();
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/work-publish!ishomeValChange.action",
			type:"post",
			dataType:"json",
			data: data,
			success: callback
		});
	}
	
	//选择的对象
	function checkObjectList(){
		var chVal = "";
		$("input[name='tbodyCheckBox']").each(function(){
			var obj = $(this);
			if(obj.attr("checked")){
				var v = obj.val();
				chVal += v+",";
			}
		})
		return chVal;
	}

	//搜索标题
	$("#queryByTitleF").click(function(){
		userName='';
		title=$("#queryContentText").val();
		pageComm2(1);
		initPage();
	});
	//搜索作品id
	$("#queryByIdF").click(function(){
		id=$("#queryContentText").val();
		if(isNaN(id)){
			alert("id号必须为数字");
			return;
		}
		pageComm2(1);
		initPage();
	});
	//搜索作者
	$("#queryByAuthorF").click(function(){
		title='';
		userName=$("#queryContentText").val();
		pageComm2(1);
		initPage();
	});
	
	
	
	$("a[childcategoryid][parentid]").click(function(){
		$("a[childcategoryid][parentid]").removeClass("current");
		var obj = $(this);
		secondSortId = obj.attr("childcategoryid");
		obj.addClass("current");
		pageComm2(1);
		initPage();
	});
	
	// 公共
	function strEmpty(str){
		if(str){
			return str;
		}else{
			return "";
		}
	}
	
	//list
	function pageComm(currentPage){
		var data = {};
		data.ishome = $(".conTools .btnGB").attr("ishome");
		data.pageNo=pageCount;
		data.currentPage=currentPage;
		data.secondSortId=secondSortId;
		data.title=title;
		data.userName=userName;
		data.id=id;
		var posturl=SystemProp.appServerUrl+"/new-publisher/work-publish!listM1Json.action";
		if(data.ishome==7){
			posturl=SystemProp.appServerUrl+"/new-publisher/work-publish!listNoEditorJson.action";
		}
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
				pageCount = result.data.pageNo;
				}
				var trContent = "";
				$.each(result.data.creativeList,function(i,creative){
					var creStartTime = "";
					if(creative.startTime){
						creStartTime = creative.startTime.replace("T"," ");
					}
					trContent += "<tr>";
					if(data.ishome==7){
						trContent += "<td >"+creative.id+"</td>";
						if(creative.nickName && creative.nickName!=''){
							trContent += "<td >"+strEmpty(creative.nickName)+"</td>";
						}else{
							trContent += "<td >"+strEmpty(creative.userName)+"</td>";
						}
						trContent += "<td class=\"tLeft\" title=\""+strEmpty(creative.described)+"\"><a href=\"http://www.magme.com/sns/c"+creative.id+"/\" target=\"_blank\">"+strEmpty(creative.title)+"</a></td>";
						
						trContent += "<td><a class=\"btn\" delcreativeId=\""+creative.id+"\" href=\"javascript:void(0);\">删除</a></td>";
					}else{
						trContent += "<td ><label><input name=\"tbodyCheckBox\" type=\"checkbox\" value=\""+creative.id+"\" /></label></td>";
						if(data.ishome!=2){
							trContent += "<td >"+strEmpty(creative.nickName)+"</td>";
							trContent += "<td >"+strEmpty(creative.userName)+"</td>";
						}else{
							trContent += "<td >"+strEmpty(creative.nickName)+"</td>";
						}
						trContent += "<td class=\"tLeft\" title=\""+strEmpty(creative.described)+"\"><a href=\"http://www.magme.com/sns/c"+creative.id+"/\" target=\"_blank\">"+strEmpty(creative.secondTitle)+"</a></td>";
						if(data.ishome==2){
							trContent += '<td ><a class="btn" creativeId="'+creative.id+'" href="javascript:void(0);">编辑</a></td>';
						}
						if(data.ishome==2){
							trContent += "<td >"+creative.updateTime.replace("T"," ")+"</td>";	
						}
						trContent += "<td >"+strEmpty(creative.typeNameSecond)+"</td>";
						if(data.ishome==2){
							trContent += "<td><a class=\"btn\" delcreativeId=\""+creative.id+"\" href=\"javascript:void(0);\">删除</a></td>";
						}
						if(data.ishome==5){
							var checkedStr = "";
							var checkedChannelStr = "";
							var topVal = creative.isrecommend;
							var topChannelVal = creative.isrecommendChannel;
							if(topVal){
								if(topVal==1){checkedStr="checked='checked'";}
							}
							if(topChannelVal){
								if(topChannelVal==1){checkedChannelStr="checked='checked'";}
							}
							trContent += "<td><label><input type=\"checkbox\" name=\"tbodyCheckBoxTop\" value=\""+creative.id+"\" " + checkedStr + " /></label></td>";
							trContent += "<td><label><input type=\"checkbox\" name=\"tbodyCheckBoxTopChannel\" value=\""+creative.id+"\" " + checkedChannelStr + " /></label></td>";
							trContent += "<td><input class=\"input g30 tCenter\" name=\"createIdWeight\" cId=\""+creative.id+"\" value=\""+strEmpty(creative.weight)+"\" type=\"text\"></td>";
							if(creative.ishome==4){
								trContent += "<td>"+creStartTime+"</td>";
							}else{
								trContent += "<td>未发布</td>";
							}
						}
						if(data.ishome==1){
								if(creative.isrecommend && creative.isrecommend==1){
									trContent += "<td><a name=\"createIdIsrecommend\" cid=\""+creative.id+"\" href=\"javascript:void(0);\" class=\"btn\">取消</a></td>";
								}else{
									trContent += "<td>&nbsp;</td>";
								}
								if(creative.isrecommendChannel && creative.isrecommendChannel==1){
									trContent += "<td><a name=\"createIdIsrecommendChannel\" cid=\""+creative.id+"\" href=\"javascript:void(0);\" class=\"btn\">取消</a></td>";
								}else{
									trContent += "<td>&nbsp;</td>";
								}
							trContent += "<td>"+creStartTime+"</td>";
						}
					}
					
					trContent += "</tr>";
				});
				$("#tbodyContext").html(trContent);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: posturl,
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage){
		var data = {};
		data.ishome = $(".conTools .btnGB").attr("ishome");
		data.pageNo=pageCount;
		data.currentPage=currentPage;
		data.secondSortId=secondSortId;
		data.title=title;
		data.userName=userName;
		data.id=id;
		var posturl=SystemProp.appServerUrl+"/new-publisher/work-publish!listM1CountJson.action";
		if(data.ishome==7){
			posturl=SystemProp.appServerUrl+"/new-publisher/work-publish!listNoEditorCountJson.action";
		}
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
				pageCount = result.data.pageNo;
				}
			}
		};
		$.ajax({
			url: posturl,
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
})