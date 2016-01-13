
$(function(){
	function freshTable(){
 		fnReadyTable();
 		$("table.JQtableBg").find("tbody tr:odd").addClass('bgColorTable');	
	}
	
	$("select#sortId").unbind().bind("change",function(){
		var obj=$(this);
		if(obj.val()){
			window.location.href=SystemProp.appServerUrl+"/new-publisher/channel!to.action?sortId="+obj.val();
		}
	}); 
	
	if(curSortId){
		//保存频道页展示方式
		$("#saveChannelViewBtn").bind("click",function(){
			var callback = function(rs){
				if(rs.code == 200){
					alert("操作成功!");
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
					
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-view!saveJson.action",
				type: "POST",
				dataType: "json",
				data: {"channelView.sortId":curSortId,"channelView.mode":$("select[name='channelView.mode']").val()},
				success: callback
			});
		});
		
		//重置Banner表单
		$("#resetChannelBannerBtn").bind("click",function(){
			$("#channelBannerForm")[0].reset();
			$("#channelBannerForm input[name='channelBanner.id']").val("");
		});
		
		//保存Banner
		$("#saveChannelBannerBtn").bind("click",function(){
		   var url=SystemProp.appServerUrl+"/new-publisher/channel-banner!saveJson.action";
		   url += "?channelBanner.sortId="+curSortId;
		   url += "&channelBanner.id="+encodeURIComponent($("#channelBannerForm input[name='channelBanner.id']").val()||"");
		   url += "&channelBanner.title="+encodeURIComponent($("#channelBannerForm input[name='channelBanner.title']").val()||"");
		   url += "&channelBanner.description="+encodeURIComponent($("#channelBannerForm textarea[name='channelBanner.description']").val()||"");
		   url += "&channelBanner.weight="+encodeURIComponent($("#channelBannerForm input[name='channelBanner.weight']").val()||"");
		   url += "&channelBanner.url="+encodeURIComponent($("#channelBannerForm input[name='channelBanner.url']").val()||"");
		   var temchk = $("#channelBannerForm input[name='channelBanner.type'][type='checkbox']").attr("checked");
		   if(temchk=='checked'){
			   url += "&channelBanner.type=1";
		   }else{
			   url += "&channelBanner.type=0";
		   }
		   
		   var temchkIndex = $("#channelBannerForm input[name='channelBanner.indexkey'][type='checkbox']").attr("checked");
		   if(temchkIndex=='checked'){
			   url += "&channelBanner.indexkey=1";
		   }else{
			   url += "&channelBanner.indexkey=0";
		   }
		   
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
	            			$("#channelBannerForm input[name='channelBanner.type']").removeAttr("checked");
	            			$("#channelBannerForm input[name='channelBanner.indexkey']").removeAttr("checked");
					 		alert("保存成功");
					 		$("#resetChannelBannerBtn").click();
					 		
					 		var cb=rs.data.channelBanner;
					 		var obj=$("tr[channelBannerId='"+cb.id+"']");
					 		var cbHtml="<td>"+cb.id+"</td>";
					 		cbHtml += "<td>"+cb.title+"</td>";
					 		if(cb.path){
					 			cbHtml += "<td><a href=\""+SystemProp.newPublisherServerUrl+cb.path+"\" target=\"banerPath\">"+SystemProp.newPublisherServerUrl+cb.path+"</a></td>";
					 		}else{
					 			cbHtml += "<td></td>";
					 		}
					 		
					 		if(cb.url){
					 			cbHtml += "<td><a href=\""+cb.url+"\" target=\"banerUrl\">"+cb.url+"</a></td>";
					 		}else{
					 			cbHtml += "<td></td>";
					 		}
					 		if(cb.type==1){
					 			cbHtml += "<td>站外</td>";
					 		}else{
					 			cbHtml += "<td>站内</td>";
					 		}
					 		
					 		cbHtml += "<td>"+(cb.weight?cb.weight:"")+"</td>";
					 		cbHtml += "<td><a class=\"btn\" name=\"editChannelBanner\" href=\"#channelTab\">编辑</a><a class=\"btn\" name=\"deleteChannelBanner\"  href=\"javascript:void(0)\">删除</a></td>";
					 		if(obj.length==0){
					 			cbHtml = "<tr channelBannerId=\""+cb.id+"\">" +cbHtml;
					 			cbHtml = cbHtml+"</tr>"
					 			$("#channelBannerList").append(cbHtml);
					 		}else{
					 			obj.html(cbHtml);
					 		}
							freshTable();
	                    }
	                },
	                //服务器响应失败处理函数
	                error: function (data, status, e) {
	                    return;
	                }
	            }
	        );
		});
		
		//编辑Banner
		$("a[name='editChannelBanner']").live("click",function(){
			var parent=$(this).parents("tr[channelBannerId]");
			var channelBannerId=parent.attr("channelBannerId")||"";
			$("#channelBannerForm input[name='channelBanner.type']").removeAttr("checked");
			$("#channelBannerForm input[name='channelBanner.indexkey']").removeAttr("checked");
			
			
			var callback=function(rs){
				if(rs.code==200){
					var cb=rs.data.channelBanner;
					$("#channelBannerForm input[name='channelBanner.id']").val(cb.id);
					$("#channelBannerForm input[name='channelBanner.title']").val(cb.title);
					$("#channelBannerForm textarea[name='channelBanner.description']").val(cb.description);
					$("#channelBannerForm input[name='channelBanner.weight']").val(cb.weight);
					$("#channelBannerForm input[name='channelBanner.url']").val(cb.url);
					if(cb.type=='1'){
						$("#channelBannerForm input[name='channelBanner.type']").attr("checked","checked");
					}
					if(cb.indexkey=='1'){
						$("#channelBannerForm input[name='channelBanner.indexkey']").attr("checked","checked");
					}
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}					
				}
			};
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-banner!editJson.action",
				type: "POST",
				dataType: "json",
				data: {"id":channelBannerId},
				success: callback
			});			
		});	
		
		//删除Banner
		$("a[name='deleteChannelBanner']").live("click",function(){
			var parent=$(this).parents("tr[channelBannerId]");
			var channelBannerId=parent.attr("channelBannerId")||"";
			
			var callback=function(rs){
				if(rs.code==200){
					alert("删除成功");
					parent.remove();
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}					
				}
			};
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-banner!deleteJson.action",
				type: "POST",
				dataType: "json",
				data: {"id":channelBannerId},
				success: callback
			});			
		});
		
		//重置主题表单
		$("#resetChannelSubjectBtn").bind("click",function(){
			$("#channelSubjectForm input[name='channelSubject.id']").val("");
			$("#channelSubjectForm input[name='channelSubject.name']").val("");
			$("#channelSubjectForm input[name='channelSubject.weight']").val("");
		});
		
		//保存主题
		$("#saveChannelSubjectBtn").bind("click",function(){
			var callback=function(rs){
        		if(rs.code!=200){
        			alert(rs.message);
        		}else{
			 		alert("保存成功");
			 		$("#resetChannelSubjectBtn").click();
			 		
			 		var cb=rs.data.channelSubject;
			 		var obj=$("tr[channelSubjectId='"+cb.id+"']");
			 		var cbHtml="<td>"+cb.id+"</td>";
			 		cbHtml += "<td>"+(cb.name?cb.name:"")+"</td>";
			 		cbHtml += "<td>"+(cb.weight?cb.weight:"")+"</td>";
			 		cbHtml += "<td><a class=\"btn\" name=\"editChannelSubject\" href=\"#channelTab\">编辑</a><a class=\"btn\" name=\"deleteChannelSubject\"  href=\"javascript:void(0)\">删除</a></td>";
			 		if(obj.length==0){
			 			cbHtml = "<tr channelSubjectId=\""+cb.id+"\">" +cbHtml;
			 			cbHtml = cbHtml+"</tr>"
			 			$("#channelSubjectList").append(cbHtml);
			 		}else{
			 			obj.html(cbHtml);
			 		}
					freshTable();
                }
			};
			
			var data={"channelSubject.id":$("#channelSubjectForm input[name='channelSubject.id']").val(),
					"channelSubject.sortId":curSortId,
					"channelSubject.name":$("#channelSubjectForm input[name='channelSubject.name']").val(),
					"channelSubject.weight":$("#channelSubjectForm input[name='channelSubject.weight']").val()
			};
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-subject!saveJson.action",
				type: "POST",
				dataType: "json",
				data: data,
				success: callback
			});
			
		});
				
		//编辑主题
		$("a[name='editChannelSubject']").live("click",function(){
			var parent=$(this).parents("tr[channelSubjectId]");
			var channelSubjectId=parent.attr("channelSubjectId")||"";
			
			var callback=function(rs){
				if(rs.code==200){
					var cb=rs.data.channelSubject;
					$("#channelSubjectForm input[name='channelSubject.id']").val(cb.id);
					$("#channelSubjectForm input[name='channelSubject.name']").val(cb.name);
					$("#channelSubjectForm input[name='channelSubject.weight']").val(cb.weight);
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}					
				}
			};
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-subject!editJson.action",
				type: "POST",
				dataType: "json",
				data: {"id":channelSubjectId},
				success: callback
			});			
		});	
				
		//删除主题
		$("a[name='deleteChannelSubject']").live("click",function(){
			var parent=$(this).parents("tr[channelSubjectId]");
			var channelSubjectId=parent.attr("channelSubjectId")||"";
			
			var callback=function(rs){
				if(rs.code==200){
					alert("删除成功");
					parent.remove();
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}					
				}
			};
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-subject!deleteJson.action",
				type: "POST",
				dataType: "json",
				data: {"id":channelSubjectId},
				success: callback
			});			
		});
		
		//保存广告
		$("#saveChannelAdBtn").bind("click",function(){
			var callback = function(rs){
				if(rs.code == 200){
					alert("操作成功!");
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
					
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-ad!saveJson.action",
				type: "POST",
				dataType: "json",
				data: {"channelAd.sortId":curSortId,"channelAd.content":$("textarea[name='channelAd.content']").val()},
				success: callback
			});
		});	
 		
 		//刷新主题事件中的主题下拉框列表
		$("#subjectEventLable").bind("click",function(){
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					$("#subjectEventList").html("");
					
					$("select[name='subjectList'] option").remove();
					
					var subjectList=$("select[name='subjectList']");
					subjectList.append("<option value=''>请选择主题</option>");
					
					if(rs.data.channelSubjectList){
						$.each(rs.data.channelSubjectList,function(n,cs){
							subjectList.append("<option value='"+cs.id+"'>"+cs.name+"</option>");
						});
					}
				}else{
					alert("error!");
				}
			};
			
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-subject!getListJson.action",
				type: "POST",
				dataType: "json",
				data: {"sortId":curSortId},
				success: callback
			});
		});	
		
		//主题事件中选择一个主题后,刷新该主题下已关联的事件列表
		$("select[name='subjectList']").bind("change",function(){
			var obj=$(this);
			var channelSubjectId=obj.val()||"";
			
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					$("#subjectEventList").html("");
					
					if(rs.data.channelEventList){
						$.each(rs.data.channelEventList,function(n,ce){
							var tr="";
							tr += "<tr channelEventId=\""+ce.id+"\">";
							tr += "    <td>"+ce.event.id+"</td>";
							tr += "    <td>"+ce.event.title+"</td>";
							tr += "    <td><a target=\"viewUserImage\" href=\""+SystemProp.appServerUrl+"/issue-image!show.action?id="+ce.event.id+"\">查看</a></td>";
							tr += "    <td>"+(ce.weight?ce.weight:"")+"</td>";
							tr += "    <td><a class=\"btn\" name=\"editChannelEvent\" href=\"javascript:void(0)\">编辑</a><a class=\"btn\" name=\"deleteChannelEvent\" href=\"javascript:void(0)\">删除</a></td>";
							tr += "</tr>";				
							$("#subjectEventList").append(tr);
						});
					}
					freshTable();
				}else{
					alert("error!");
				}
			};
			
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-event!getListJson.action",
				type: "POST",
				dataType: "json",
				data: {"channelSubjectId":channelSubjectId},
				success: callback
			});			
		});	
		
		//编辑推荐事件的权重
		$("a[name='editChannelEvent']").live("click",function(){
			var obj = $(this);
			var edit = obj.parent().prev();
			var val1 = edit.text();
			var val2 = edit.find("input").val();
			if(!obj.hasClass("btnOn")){
				obj.addClass("btnOn").html("保存");
				edit.addClass("editOn");
				edit.html("<input type='text' class='input g50' value='"+val1+"' />");
				edit.find("input").focus();
			}else{
				var callback=function(rs){
					if(rs.code&&rs.code==200){
						obj.removeClass("btnOn").html("编辑");
						edit.removeClass("editOn").text(val2);					
					}else{
						alert("error!");
					}
				};
				
				$.ajax({
					url: SystemProp.appServerUrl+"/new-publisher/channel-event!updateJson.action",
					type: "POST",
					dataType: "json",
					data: {"channelEvent.id":obj.parents("tr").attr("channeleventid"),"channelEvent.weight":val2},
					success: callback
				});
			}
		});
		
		
		
		//将事件从主题中移除
		$("a[name='deleteChannelEvent']").live("click",function(){
			var obj=$(this);
			var callback = function(rs){
				if(rs.code == 200){
					alert("操作成功!");
					obj.parents("tr").remove();
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
					
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-event!deleteJson.action",
				type: "POST",
				dataType: "json",
				data: {"channelEventId":obj.parents("tr").attr("channeleventid")},
				success: callback
			});
		});
		var pageCount = "0";
		
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
		
		function pageselectCallback(page_id, jq){
			$("#eventListPageadd").html("");
			$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
    		pageOp2(page_id+1);
    		return false;
        }
        function initPage(pageCount){
        $("#eventListPage").html("");
        	$("#eventListPage").pagination(pageCount, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 20, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				prev_text: "前一页",
				next_text: "后一页"
			});
			
        }
        
		//查询事件
		$("a[name='searchEventBtn']").bind("click",function(){
			pageOp(1);//调用同步数据 获取总页数
			initPage(pageCount);		
		});
		function pageOp2(currentPage){//异步数据传输
			var subjectId=$("select[name='subjectList']").val()||"";
			var form=$("#searchEventForm");
			var data={};
			data.subjectId=subjectId;
			data.id=form.find("input[name='id']").val();
			data.title=form.find("input[name='title']").val();
			data.createdTimeStart=form.find("input[name='createdTimeStart']").val();
			data.createdTimeEnd=form.find("input[name='createdTimeEnd']").val();
			data.currentPage=currentPage;
			data.pageNo=pageCount;
			
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					$("#eventList").html("");
					
					pageCount=rs.data.evenPage.pageNo;
					
					if(rs.data.evenPage.eventList){
						$.each(rs.data.evenPage.eventList,function(n,e){
							var imgFile = e.imgFile;
							var tempdou = imgFile.lastIndexOf('/');
							var imag68 = SystemProp.fpageServerUrl+"/event/"+imgFile.substring(0,tempdou)+"/68_"+imgFile.substring(tempdou+1);
							var tr="";
							tr += "<tr eventId=\""+e.id+"\">";
							tr += "    <td><label><input name=\"eventCheck\" type=\"checkbox\" /></label></td>";
							tr += "    <td>"+e.id+"</td>";
							tr += "    <td><img src="+imag68+" /></td>";
							tr += "    <td>"+e.title+"</td>";
							tr += "    <td><a target=\"viewUserImage\" href=\""+SystemProp.appServerUrl+"/issue-image!show.action?id="+e.id+"\">查看</a></td>";
							tr += "    <td><a class=\"btn\" name=\"addToSubject\" href=\"javascript:void(0)\">加入主题</a></td>";
							tr += "</tr>";	
							
							$("#eventList").append(tr);
						});
					}
					freshTable();
				}else{
					alert("error!");
				}
			};
			
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-event!getEventListJson.action",
				type: "POST",
				dataType: "json",
				data: data,
				success: callback
			});
			
		}
		function pageOp(currentPage){//同步调用 第一次初始化
			var subjectId=$("select[name='subjectList']").val()||"";
			var form=$("#searchEventForm");
			var data={};
			data.subjectId=subjectId;
			data.id=form.find("input[name='id']").val();
			data.title=form.find("input[name='title']").val();
			data.createdTimeStart=form.find("input[name='createdTimeStart']").val();
			data.createdTimeEnd=form.find("input[name='createdTimeEnd']").val();
			data.currentPage=currentPage;
			data.pageNo=pageCount;
			
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					$("#eventList").html("");
					
					pageCount=rs.data.evenPage.pageNo;
					
					if(rs.data.evenPage.eventList){
						$.each(rs.data.evenPage.eventList,function(n,e){
							var imgFile = e.imgFile;
							var tempdou = imgFile.lastIndexOf('/');
							var imag68 = SystemProp.fpageServerUrl+"/event/"+imgFile.substring(0,tempdou)+"/68_"+imgFile.substring(tempdou+1);
							var tr="";
							tr += "<tr eventId=\""+e.id+"\">";
							tr += "    <td><label><input name=\"eventCheck\" type=\"checkbox\" /></label></td>";
							tr += "    <td>"+e.id+"</td>";
							tr += "    <td><img src="+imag68+" /></td>";
							tr += "    <td>"+e.title+"</td>";
							tr += "    <td><a target=\"viewUserImage\" href=\""+SystemProp.appServerUrl+"/issue-image!show.action?id="+e.id+"\">查看</a></td>";
							tr += "    <td><a class=\"btn\" name=\"addToSubject\" href=\"javascript:void(0)\">加入主题</a></td>";
							tr += "</tr>";	
							
							$("#eventList").append(tr);
						});
					}
					freshTable();
				}else{
					alert("error!");
				}
			};
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-event!getEventListJson.action",
				type: "POST",
				dataType: "json",
				async:false,
				data: data,
				success: callback
			});
			
		}
		
		//全选和反选
		$("#batchEventCheck").bind("change",function(){
			if($(this).attr("checked")){
				$("input[name='eventCheck']").attr("checked","true");
			}else{
				$("input[name='eventCheck']").removeAttr("checked");
			}
		});	
		
		//将事件加入主题
		$("a[name='addToSubject']").live("click",function(){
			var obj=$(this);
			var subjectId=$("select[name='subjectList']").val()||"";
			if(!subjectId){
				alert("请选择主题");
				return;
			}			
			
			var eventId=$(this).parents("tr").attr("eventId");
			
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					alert("加入主题成功");
					obj.parents("tr").remove();
					$("select[name='subjectList']").change();
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
			
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-event!saveJson.action",
				type: "POST",
				dataType: "json",
				data: {"channelSubjectId":subjectId,"eventIds":eventId},
				success: callback
			});			
		})
		
		//批量将事件加入主题
		$("a[name='batchAddToSubjectBtn']").bind("click",function(){
			var subjectId=$("select[name='subjectList']").val()||"";
			if(!subjectId){
				alert("请选择主题");
				return;
			}
			
			var eventId="";
			$("input[name='eventCheck']:checked").each(function(){
				eventId += $(this).parents("tr").attr("eventId")+",";
			});
			
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					alert("批量加入主题成功");
					$("input[name='eventCheck']:checked").each(function(){
						$(this).parents("tr").remove();
					});				
					$("select[name='subjectList']").change();
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
			
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel-event!saveJson.action",
				type: "POST",
				dataType: "json",
				data: {"channelSubjectId":subjectId,"eventIds":eventId},
				success: callback
			});			
		})
		
		$("a[name='previewChannelBtn']").bind("click",function(){
			window.open(SystemProp.appServerUrl+"/channel!preview.action?sortId="+curSortId,"preview");
		});		
		
		$("a[name='releaseChannelBtn']").bind("click",function(){
			var callback=function(rs){
				if(rs.code&&rs.code==200){
					window.open(SystemProp.appServerUrl+"/purge/channel/"+curSortId+".html","release");					
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/channel!release.action",
				type: "POST",
				dataType: "json",
				data: {"sortId":curSortId},
				success: callback
			});
		});		
						
	}else{
		var callback=function(){
			alert("请先选择一个分类后再执行操作");
		};
		$("a[id$=Btn]").bind("click",callback);
	}
});