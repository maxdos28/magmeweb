$(function(){ 
	$.jqueryScrollPhoto("#pubTopbar",5,192,5,0,600);
	
	//期刊上传
	$("#uploadIssueEM").live("click",function(e){
		e.preventDefault();
		var oj = $(this);
		publicationId = oj.attr("pid");
		var url = SystemProp.appServerUrl+"/new-publisher/magazine-list!uploadMg.action?id="+publicationId;
		window.open(url,"上传期刊","height=450,width=600,top=100,left=150,toolbar=no,menubar=no,scrollbars=no, resizable=no,titlebar=no, location=no, status=no,z-look=yes");
	});
	
	//期刊重新转换
	$("a[name='issue_retrans']").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('issueid');
		var callback = function(result){
			if(result.code == 200){
				el.remove();
				alert("重新转换已经提交");
				$("td[issueid='"+issueId+"']").html("重新 转换中");
				
			}else{
				alert("重新转换提交失败");
			}
			el.remove();
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!reTransferJson.action',
			type : 'post',
			dataType : 'json',
			data : {'id':issueId},
			success : callback
		});
	});
	
	
	//杂志编辑
	$("#editPublication").live("click",function(e){
			e.preventDefault();
			var obj =$(this);
			window.location.href = SystemProp.appServerUrl+"/new-publisher/publication-config!to.action?publicationid="+obj.attr("pid");+"&&random="+Math.random();
	});
	
	//杂志上架
	$("#upPublication").live("click",function(e){
			e.preventDefault();
			var vl = $.trim($("#mgSearchValue").val());
			var obj =$(this);
			var data = {};
			data["publication.id"]=obj.attr("pid");
			var callback = function(result){
				magComm(1,vl);
			}
			
			$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doUpPublication.action",
			type : "POST",
			data : data,
			success: callback
			});
	});		
	
	//杂志下架
	$("#downPublication").live("click",function(e){
			e.preventDefault();
			var vl = $.trim($("#mgSearchValue").val());
			var obj =$(this);
			var data = {};
			data["publication.id"]=obj.attr("pid");
			var callback = function(result){
				magComm(1,vl);
			}
			
			$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doDownPublication.action",
			type : "POST",
			data : data,
			success: callback
			});
	});	
	//杂志删除
	$("#delPublication").live("click",function(e){
			e.preventDefault();
			if(!ConfirmDel('确认删除吗？'))return;
			var vl = $.trim($("#mgSearchValue").val());
			var obj =$(this);
			var data = {};
			data["publication.id"]=obj.attr("pid");
			var callback = function(result){
				magComm(1,vl);
			}
			
			$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doDelPublication.action",
			type : "POST",
			data : data,
			success: callback
			});
	});	
	
	//IOS新刊通知
	$("#iosNewPublication").live("click",function(e){
			e.preventDefault();
			var obj =$(this);
			var data = {};
			data["publication.id"]=obj.attr("pid");
			var callback = function(result){
				if(result.code!=200){
					alert(result.message);
				}
			}
			
			$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doIosNewPublication.action",
			type : "POST",
			data : data,
			success: callback
			});
	});
	
	//新刊通知
	$("#newPublication").live("click",function(e){
			e.preventDefault();
			var obj =$(this);
			var data = {};
			data["publication.id"]=obj.attr("pid");
			var callback = function(result){
				if(result.code!=200){
					alert(result.message);
				}
			}
			
			$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doNewPublication.action",
			type : "POST",
			data : data,
			success: callback
			});
	});	
	
	//创建杂志
	$("#publicationCreate").live("click",function(e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/new-publisher/publication-config!to.action?createid='create'&&random="+Math.random();
	});
	
	//设计期刊为免费 
	$("a[name='issue_price']").live("click",function(){
		var obj = $(this);
		var issueid = obj.attr("issueid");
		if(ConfirmDel("确认将该期刊设置为免费吗?")){
			var data ={};
			data.issueId=issueid;
			function callback(result){
				if(result.code==200){
					$("#emIssuePrice").html("免费");
				}else{
					alert(result.message);
				}
			}
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/magazine-list!doIssueFree.action",
				type: "POST",
				dataType: "json",
				data: data,
				success: callback
			});
		}
		
	});
	
	$("input[name=isPassWord]").live("change",function(){
		var obj = $("input[name=isPassWord]:checked").val();
		if(obj=="1"){
			$("#editIssueForm input[name=password]").show();
		}else{
			$("#editIssueForm input[name=password]").val("");
			$("#editIssueForm input[name=password]").hide();
		}
	});
	
	//编辑期刊
	$("a[name='issue_edit']").live("click",function(){
		var obj = $(this);
		var editForm = $("#editIssueForm");
		$("#editIssueForm input[name=isPassWord][value='0']").attr("checked",true);
		$("#editIssueForm input[name=password]").hide();
		editForm[0].reset();
		$("#editIssueForm input[name=id]").val(obj.attr("issueid"));
		var data = {};
		data["issue.id"]=obj.attr("issueid")||"";
		var callback = function(result){
			if(result.data.issue){
				var obj = result.data.issue;
				$("#editIssueForm input[name=keyword]").val(obj.keyword);
				$("#editIssueForm input[name=issueNumber]").val(obj.issueNumber);
				$("#editIssueForm textarea[name=description]").val(obj.description);
				if(obj.passWord){
					if(obj.passWord.length>1){
						$("#editIssueForm input[name=isPassWord][value='1']").attr("checked",true);
						$("#editIssueForm input[name=password]").val(obj.passWord);
						$("#editIssueForm input[name=password]").show();
					}
				}else{
					$("#editIssueForm input[name=isPassWord][value='0']").attr("checked",true);
				}
				dayStr = (obj.publishDate).substring(0,10);
				$("#editIssueForm #publishDate").val(dayStr);
				datePickerFun($("#publishDate"),dayStr);
				$("#editIssueDialog").fancybox();
			}
		} 
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doGetIssue.action",
			type : "POST",
			data : data,
			success: callback
		});
	});
	
	//期刊上架
	$("a[name='issue_up']").live("click",function(){
		var obj = $(this);
		saveIssue(obj.attr('issueid'),'1');
	});
	//期刊下架
	$("a[name='issue_down']").live("click",function(){
		var obj = $(this);
		saveIssue(obj.attr('issueid'),'2');
	});
	//期刊删除
	$("a[name='issue_del']").live("click",function(){
		var obj = $(this);
		if(!ConfirmDel('确认删除吗？')) return;
		saveIssue(obj.attr('issueid'),'3');
	});
	
	//网易杂志上传
	$("a[name='toNeteaseBtn']").live("click",function(){
		if(!confirm("是否要上传到网易?"))
			return;
		var issueId = $(this).attr("issueid");
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/magazine-list!toNeteaseAddJson.action",
			type: "POST",
			dataType: "json",
			data: {issueId:issueId},
			success: function(rs){
				if(rs.code==200)
				{
					//更新期刊
					if(rs.data&&rs.data.totalPage)
					{
						showNeteaseUpdateDialog(issueId,rs.data.totalPage)
					}
					//新增期刊
					else
					{
						alert("已加入到上传队列中");
					}
				}
				else
				{
					alert(rs.message);
				}
			}
		});
	});
	//网易更新页码选择
	$("a[name='neteasePage']").live("click",function(){
		if($(this).attr("class"))
		{
			$(this).attr('class','');
			$(this).attr("c",0);
		}
		else
		{
			$(this).addClass('current');
			$(this).attr("c",1);
		}
	});
	
	//显示更新弹出框
	function showNeteaseUpdateDialog(issueId,totalPage)
	{
		$("#pageDiv").html("");
		$("#neteaseIssueId").val(issueId);
		var m = parseInt(totalPage);
		for(i=1;i<=m;i++)
		{
			$("#pageDiv").append("<a href='javascript:void(0);' c='0' name='neteasePage' pageNo='"+i+"'>"+i+"</a>");
		}
		$("#updateNeteaseDialog").fancybox();
	}
	
	//更新网易期刊
	$("#updateNeteaseBtn").live("click",function (issueId)
	{
		var pages = "";
		var pps = $("#pageDiv").find("a[name='neteasePage'][c=1]");
		if(pps.length==0)
		{
			alert("请选择页码");
			return;
		}
		for(i=0;i<pps.length;i++)
		{
			if(pages.length>0)
				pages+=",";
			pages+=$(pps[i]).attr("pageNo");
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/magazine-list!toNeteaseUpdateJson.action",
			type: "POST",
			dataType: "json",
			data: {issueId:$("#neteaseIssueId").val(),pages:pages},
			success: function(rs){
				if(rs.code==200)
				{
					alert("已加入到上传队列中");
				}
				else
				{
					alert(rs.message);
				}
			}
		});
	});
	
	
	//检验内容是否为数字
	function checkObjNum(vl){
		if(vl){
			var patrn=/^[0-9]{1,20}$/; 
			if (!patrn.exec(vl)) return false;
			return true; 
		}else{
			return fasle;
		}
	}
	
	//保存期刊
	$("#editIssueFormSubmit").live("click",function(){
		var data = {};
		data["issue.id"]=$("#editIssueForm input[name=id]").val()||"";
		data["issue.publishDate"]=$("#editIssueForm input[name=publishDate]").val()||"";
		data["issue.keyword"]=$("#editIssueForm input[name=keyword]").val()||"";
		data["issue.issueNumber"]=$("#editIssueForm input[name=issueNumber]").val()||"";
		data["issue.description"]=$("#editIssueForm textarea[name=description]").val()||"";
		var obj = $("input[name=isPassWord]:checked").val();
		if(obj=="1"){
			var passStr =$("#editIssueForm input[name=password]").val();
			if(passStr.length>0&&passStr.length<=4){
				if(checkObjNum(passStr)&&passStr.length==4){
					data["issue.passWord"]=$("#editIssueForm input[name=password]").val()||"";
				}else{
					alert("请输入4位数字");
				}					
			}else{
				data["issue.passWord"]=$("#editIssueForm input[name=password]").val()||"";	
			}
			
		}
		var callback = function(result){
			if(result.code==200)
			{
				alert("保存成功");
				$.fancybox.close();
			}
			else
			{
				alert(result.message);
			}
		}
		$.ajaxFileUpload({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!doSaveIssue.action",
			type : "POST",
            secureuri : false,
            fileElementId : ["issueFile","smallPic1","smallPic2"],
            content : $("#editIssueForm"),
            dataType : "json",
            async : true,
			data : data,
			success: callback
		});
	});
	//取消保存期刊
	$("#cancel").live("click",function(){
		$.fancybox.close();
	});
	

	initPage();
	//杂志对应的期刊
	$("li[name='publicationById']").live("click",function(){
		$("li[name='publicationById']").removeClass("current");
		$(this).addClass("current");
		$("#tbodyContext").html("");
		var obj = $(this);
		pageComm2(1,obj.attr("pid"));
		initPage();
	});
	
	//杂志分页 左翻页
	$("#mgleft").live("click",function(){
		var vl = $.trim($("#mgSearchValue").val());
		currentPage = currentPage-1;
		if(currentPage<=1){//已经是第一页
			currentPage = 1;
			magComm(currentPage,vl);
			$("#mgleft").addClass("disable");
		}else{
			$("#mgleft").removeClass("disable");
			magComm(currentPage,vl);
		}
	});
	//杂志分页 右翻页
	$("#mgright").live("click",function(){
		var vl = $.trim($("#mgSearchValue").val());
		currentPage = currentPage+1;
		if(currentPage>=pageCount){//已经是最后一页
			currentPage=pageCount
			magComm(currentPage,vl);
			$("#mgright").addClass("disable");
		}else{
			$("#mgright").removeClass("disable");
			magComm(currentPage,vl);
		}
	});
	
	//杂志搜索
	$("#mgSearch").live("click",function(){
		var vl = $.trim($("#mgSearchValue").val());
		magComm(1,vl);
	});
	
	//页面跳转
	$("#toPageOk").live("click",function(){
			var currentPage = $("#toPageValue").val();
			if(currentPage>issuePageCount) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
			if(currentPage<=0){currentPage=1} 
			$("#eventListPage").html("");
        	$("#eventListPage").pagination(issuePageCount, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 15, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				current_page:currentPage-1, 
				prev_text: "上页",
				next_text: "下页"
			});
		});
	//杂志公共
	function magComm(currentPage,searchValue){
		var data={};
		data.currentPage=currentPage;
		if(searchValue){
			data["publication.name"]=searchValue;
		}
		var isFreeValue = $("#isfreeId").val();
		if(isFreeValue){
			data["publication.isFree"]=isFreeValue;
		}
		var pubid= $.trim($("#publicationIDValue").val());
		var issueid = $.trim($("#issueIDValue").val());
		if(pubid){
			data["publication.id"]=pubid;
		}
		if(issueid){
			data["publication.issueId"]=issueid;
		}
				
		var callback=function(result){
			if(result.data.publicationList){
				//重置总页数
				pageCount = result.data.pageNo;
				currentPage =1;
				$("#mgMenu").html("");//清空杂志
					var liStr = "";
					$.each(result.data.publicationList,function(k,p){
					 liStr +="<li class=\"item showSlide\" name=\"publicationById\" appid='"+p.appId+"' pid=\""+p.id+"\" title='"+p.id+"'>";
					 if(p.imgPath){
						if(p.pubType==3)
							liStr +="<img src='" + SystemProp.staticServerUrl+"/appprofile/" + p.imgPath + "' />";
						else
							liStr +="<img src='" + SystemProp.magServerUrl + p.imgPath + "' />";
					 }else{
					 	liStr +="<img src=\"/v3/images/cover182-243.gif\" />";
					 }
			         liStr +="           <div class=\"border\"></div>";
			         liStr +="           <strong>"+p.name+"</strong>";
			         liStr +="           <div class=\"slideDown\" >";
			         if(!session_exists){
				         //<if session_admin?exists>
				         //           	<else>
				         liStr +="           	<span>操作</span>";
				         liStr +="               <p>";
				                        	if(p.status=='1'){
				         liStr +="               		<em id=\"uploadIssueEM\" pid='"+p.id+"' pname='"+p.name+"'>上传期刊</em>";
				         if(p.pubType=='0'){
				        	 liStr +="                    <em id=\"newPublication\" pid='"+p.id+"'>新刊通知</em>";
				         }
				         liStr +="                    <em id=\"editPublication\" pid='"+p.id+"' >杂志编辑</em>";
				         liStr +="                    <em id=\"downPublication\" pid='"+p.id+"' >杂志下架</em>";
				                        	}else{
				        liStr +="                		<em id=\"upPublication\" pid='"+p.id+"'>杂志上架</em>";
				                        	}
				        liStr +="                    <em id=\"delPublication\" pid='"+p.id+"'>杂志删除</em>";
				        liStr +="                </p>";
				        //</if>
			        } 
			        liStr +="            </div>";
			        liStr +="        </li>";
					});
					$("#mgMenu").html(liStr);
				if(result.data.issueList){//获取期刊的总页数 然后更杂志id调用对应的期刊
					issuePageCount=result.data.issuePageNo;
					var obj = result.data.publication;
					temppid = obj.id;
					$("#tbodyContext").html("");
					pageComm2(1,temppid);
					initPage();
				}else{
					issuePageCount=1;
					//initPage();
					$("#eventListPage").pagination(1, {
						num_edge_entries: 1, //边缘页数
						num_display_entries: 20, //主体页数
						items_per_page: 1, //每页显示1项
						prev_text: "上页",
						next_text: "下页"
					});
					$("#tbodyContext").html("");
					return;
				}
				if(!liStr){
					$("#tbodyContext").html("");
				}
				fnReadyTable();
			}
		}
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/magazine-list!doJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}

	//期刊 公共
	function pageComm(currentPage){
		var data = {};
		data.issueCurrentPage=currentPage;
		data["publication.id"]=piid||"";
		var appid = $("li[name='publicationById'][pid="+piid+"]").attr("appid");
		var callback =function(result){
			//alert(result.message);
			if(result.code!=200){
						return;
			}
			$("#tbodyContext").html("");
			if(result.data.issueList){
				var pub = result.data.publication;
				var trStr = "";
				$.each(result.data.issueList,function(k,p){
					var activeFlag = "1";
					if(!pub){
						pub = p.publicationPo;
					}
					
					 if(p.issueType=='2'){
						 activeFlag = "0";
					 }
					
					 trStr +="<tr>";
					 if(activeFlag == "0"){
						 trStr +="<td class=\"tLeft\" title='"+p.publicationId+"/"+p.id+"'> <a target='_blank' href='"+SystemProp.staticServerUrl+"/appprofile/read/html5.html?aid="+p.publicationId+"&tid="+p.id+"&mz=1&d=0#1'>"+p.publicationName+p.issueNumber+"</a></td>";
     				}else{
     					trStr +="<td class=\"tLeft\" title='"+p.publicationId+"/"+p.id+"'><a  target='_blank' href='"+SystemProp.appServerUrl+"/publish/mag-read!reader.action?pageId=1&desType=2&id="+p.id+"'>"+p.publicationName+p.issueNumber+"</a></td>";
     				}
					if(session_exists){
						if(p.pubLevel){
							if(p.pubLevel==1){
								trStr +="<td>一线</td>";		
							}else{
								trStr +="<td>非一线</td>";
							}
						}
					}
					if(p.totalPages){
						trStr +="<td>"+p.totalPages+"</td>";
					}else{
						trStr +="<td></td>";
					}
                  	trStr +=" <td>"+(p.createdTime).substring(0,10)+"</td>";
                    statusVal = '';
                     if(p.status==0)statusVal='待审核';
                     else if(p.status==1)statusVal='上架';
                     else if(p.status==2)statusVal='下架';
                     else if(p.status==3)statusVal='后台下架';
                     else if(p.status==4)statusVal='待处理';
                     else if(p.status==5)statusVal='处理中';
                     else if(p.status==6)statusVal='处理失败';
                     else if(p.status==7)statusVal='重新 转换中';
                     else if(p.status==8)statusVal='重新转换被丢弃';
                   trStr +=" <td issueid=\""+p.id+"\">"+statusVal+"</td>";
                   if(session_exists){
                    	 if(p.status==1){
			                   var url = SystemProp.appServerUrl + "/publish/mag-read!adminReader.action?id="+p.id
			                   if(activeFlag == "0"){
		        					trStr +=" <td>--</td>";
		        				}else{
		        					trStr +=" <td>"+p.eventNum+"<a href='"+url+"' target='_blank' class='btn' >添加</a></td>";
		        				}
		                   }else{
		                	   if(activeFlag == "0"){
		        					trStr +=" <td>--</td>";
		        				}else{
		        					trStr +=" <td>"+p.eventNum+"</td>";
		        				}
		                   }
		                   
		                   if(p.status==1){
	        				var textUrl =SystemProp.appServerUrl+"/new-publisher/magazine-list!textWrite.action?id="+p.id;
		        				if(activeFlag == "0"){
		        					trStr +=" <td>--</td>";
		        				}else{
		        					trStr +=" <td>"+p.textProportion+"%<a href=\""+textUrl+"\" textPageIssueId=\""+p.id+"\" class='btn' >添加</a></td>";
		        				}
	        				}else{
	        					if(activeFlag == "0"){
		        					trStr +=" <td>--</td>";
		        				}else{
		        					trStr +=" <td>"+p.textProportion+"%</td>";
		        				}
	        				}
        			} else {
        				//事件数量
        				//trStr +=" <td>"+p.eventNum+"</td>";
        				if(p.status==1){
	        				var textUrl =SystemProp.appServerUrl+"/new-publisher/magazine-list!textWrite.action?id="+p.id;
	        					//is nvsuper ---begin
		        				if(result.data.superId && result.data.superId>0){
		        					if(activeFlag == "0"){
			        					trStr +=" <td>--</td>";
			        				}else{
			        					trStr +=" <td>"+p.textProportion+"%</td>";
			        				}
		        				}//is nvsuper  ---end
		        				else{
		        					if(activeFlag == "0"){
		        						//jasper 2013-7-18 互动杂志有添加功能
			        					trStr +=" <td><a href=\""+textUrl+"\" textPageIssueId=\""+p.id+"\" class='btn' >添加</a></td>";
			        				}else{
			        					trStr +=" <td>"+p.textProportion+"%<a href=\""+textUrl+"\" textPageIssueId=\""+p.id+"\" class='btn' >添加</a></td>";
			        				}
		        				}
        				}else{
        					if(activeFlag == "0"){
	        					trStr +=" <td>--</td>";
	        				}else{
	        					trStr +=" <td>"+p.textProportion+"%</td>";
	        				}
        				}
        		   }
        		   urla = SystemProp.appServerUrl + "/publish/mag-read!reader.action?pageId=1&desType=1&id="+p.id;
        		   if(p.status==1){
        		   		//is nvsuper ---begin
        				if(result.data.superId && result.data.superId>0){
        					if(activeFlag == "0"){
	        					trStr +=" <td>--</td>";
	        				}else{
	        					trStr +=" <td>"+p.interactiveNum+"</td>";
	        				}
        				}//is nvsuper  ---end
        				else{
        					if(activeFlag == "0"){
	        					trStr +=" <td>--</td>";
	        				}else{
	        					trStr +=" <td>"+p.interactiveNum+"<a href='"+urla+"' target='_blank' class='btn' >添加</a></td>";
	        				}
	                   	}
                   }else{
                	   if(activeFlag == "0"){
       					trStr +=" <td>--</td>";
       				}else{
       					trStr +=" <td>"+p.interactiveNum+"</td>";
       				}
                   }
                   
                   if(session_exists){
	                   	//<if session_admin?exists>
	                   	if(p.status==1){
	                   		if(activeFlag == "0"){
	        					trStr +=" <td>--</td>";
	        				}else{
	        					trStr +=" <td>"+p.adNum+"<a href='"+urla+"' target='_blank' class='btn' >添加</a></td>";
	        				}
	                   	 }else{
	                   		if(activeFlag == "0"){
	        					trStr +=" <td>--</td>";
	        				}else{
	        					trStr +=" <td>"+p.adNum+"</td>";
	        				}
	                   	 }
	                   	if(activeFlag == "0"){
        					trStr +=" <td>--</td>";
        				}else{
        					trStr +=" <td>"+p.adPageNum+"<a href='"+urla+"' target='_blank' class='btn'>添加</a></td>";
        				}
	                   	trStr +=" <td>";
	                   	if(p.isFree==0){//收费
	                   	trStr +=" <em id=\"emIssuePrice\"><a class=\"btn\" name=\"issue_price\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">设为免费</a></em>";
	                   	}else{
	                   	trStr +="免费";
	                   	}
	                   	trStr +=" <a class=\"btn\" name=\"issue_edit\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">查看</a>";
	                   	if(appid&&appid=="904")
	                   	trStr +=" <a class=\"btn\" name=\"toNeteaseBtn\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">上传到网易</a>";
	                   	trStr +=" </td>";
	                   	//trStr +=" <td>&nbsp;</td>";
	        			//<else>
        			} else {
        				if(objPublisher==1){//一线出版商
        					if(p.status==1){
        						if(activeFlag == "0"){
    	        					trStr +=" <td>--</td>";
    	        				}else{
    	        					trStr +=" <td>"+p.adNum+"<a href='"+urla+"' target='_blank' class='btn'>添加</a></td>";
    	        				}
        					}else{
        						if(activeFlag == "0"){
    	        					trStr +=" <td>--</td>";
    	        				}else{
    	        					trStr +=" <td>"+p.adNum+"</td>";
    	        				}
        					}
        				}else{//非一线出版商
        					if(activeFlag == "0"){
	        					trStr +=" <td>--</td>";
	        				}else{
	        					trStr +=" <td>"+p.adNum+"</td>";
	        				}
        				}
        				
        				//插页广告
        				//trStr +=" <td>"+p.adPageNum+"</td>";
        				
        				//定价 
        				 trStr +=" <td>";
        				if(p.isFree==0){//收费
	                   		trStr +="<em id=\"emIssuePrice\"><a class=\"btn\" name=\"issue_price\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">设为免费</a></em>";
	                   	}else{
	                   		trStr +="免费";
	                   	}
        				 trStr +=" </td>";
        				
        				//is super user
        				if(result.data.superId && result.data.superId>0){
        					trStr +="<td>";
        				}else{
        					trStr +=" <td><a class=\"btn\" name=\"issue_edit\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">编辑</a>";
        				}
        				if(p.status==0 || p.status==6){
        					if(activeFlag == "0"){
        						trStr +="";
            				}else{
            					trStr +="<a class=\"btn\" name=\"issue_retrans\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">重新转换</a>";
            				}
                   		}
	                   if(p.status==0 || p.status==2){
	                   if(pub.status ==1){		
	                  		 trStr +="<a class=\"btn\" name=\"issue_up\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">上架</a>";
	                  		 }
	                   }else if(p.status==1){
	                	 //is super user
	        				if(result.data.superId && result.data.superId>0){
	        					
	        				}else{
	                   		   trStr +="<a class=\"btn\" name=\"issue_down\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">下架</a>";
	        				}
	                   } 
	                   trStr +=" </td>";
	                   if(p.status!=4 || p.status!=5 || p.status!=7){
	                   trStr +=" <td><a class=\"del\" name=\"issue_del\" issueid=\""+p.id+"\" href=\"javascript:void(0);\">删除</a></td>";
	                   }
        		   //</if>
        		   }
					trStr +="</tr>";
				});
				$("#tbodyContext").html(trStr);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/magazine-list!listIssue.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	//期刊 公共
	function pageComm2(currentPage,pid){
		var data = {};
		data.issueCurrentPage=currentPage;
		data["publication.id"]=pid||"";
		piid = pid;
		var callback =function(result){
			//alert(result.message);
			if(result.code!=200){
			issuePageCount=1;
					return;
				}
			$("#tbodyContext").html("");
			if(result.data.issueList){
			var pub = result.data.publication;
				issuePageCount = result.data.issuePageNo;
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/magazine-list!listIssue.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	
	//期刊 公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	
	//期刊 公共
	function initPage(){
		$("#eventListPage").pagination(issuePageCount, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 15, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "上页",
			next_text: "下页"
		});
	}
	
	//保存期刊 状态值
	function saveIssue(iseId,status,pid){
		var data = {};
		data["issue.id"]=iseId||"";
		data["issue.status"]=status||"";
		var callback = function(result){
			if(result.code==200){
				if(status==1){
					if(result.data){
						if(result.data.superId){
							//女友独有代码
							$("td[issueid="+iseId+"]").html("上架");
						}
					}else{
						$("td[issueid="+iseId+"]").html("上架");
						$("a[issueid="+iseId+"][name=issue_up]").html("下架");
						$("a[issueid="+iseId+"][name=issue_up]").attr("name","issue_down");
					}
					
				}
				if(status==2){
					$("td[issueid="+iseId+"]").html("下架");
					$("a[issueid="+iseId+"][name=issue_down]").html("上架");
					$("a[issueid="+iseId+"][name=issue_down]").attr("name","issue_up");
				}
				if(status==3){
					//重新刷新期刊列表
					$("li[name='publicationById']").removeClass("current");
					$("li[name='publicationById'][pid="+piid+"]").addClass("current");
					$("#tbodyContext").html("");
					pageComm2(1,piid);
					initPage();
					fnReadyTable();
				}
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/magazine-list!doSaveIssue.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	
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