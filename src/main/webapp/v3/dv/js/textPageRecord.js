
$(function(){
	//初始化 第0页的标签
	tagLi(issueid,'1');

	//添加标签
	$("#addTagButton").unbind("click").live("click",function(){
		var textPageForm =$("#textPageForm");
		var name= textPageForm.find("#newTag").val();
		if(name){
			if(name=='添加新标签'){
				alert("标签名称不能为空");
				return;
			}
		}else{
			alert("标签名称不能为空");
			return;
		}
		var issueId=textPageForm.find("input[name='issueId']").val();
		var pageNo=textPageForm.find("input[name='pageNo']").val();
		var data ={};
		data["tag.name"] =name;
		data["tag.objectId"] =issueId;
		data["tag.pageNum"] =pageNo;
		data["tag.type"] ="2";
		var callback = function(result){
			if(result.code==200){
				//清空name
				//添加新tag到列表
				tagLi(issueId,pageNo);
			}else{
				alert(result.message);
			}
		}
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!addTagJson.action",
			type : "POST",
			data : data,
			success: callback
		});
	});
	
	//文字录入
	$("a[textPageIssueId]").unbind("click").live("click",function(e){
		var obj=$(this);
		var issueId=obj.attr("textPageIssueId");
		var pageNo=obj.attr("textPagePageNo");
		var flag=true;
		if(!pageNo||pageNo.length==0){
			pageNo=0;
			flag=false;
			return;
		}
		//清空数据
			$("#textPageForm textarea[name=content]").val("");
			$("#textPageForm input[name=title]").val("");
			$("#textPageForm input[name=issueId]").val("");
			$("#textPageForm input[name=pageNo]").val("");
			$("#ad").attr("checked",false);
			$("#cflag").attr("checked",false);
			$("#catalog").attr("checked",false);
			$("#boutique").attr("checked",false);
		
		var callback = function(rs){
			if(rs.data.issue){
			var issue= rs.data.issue;
			tempStr = "768_"+pageNo;
			var url ="";
			if(issue.issueType==2)
			{
				url += SystemProp.staticServerUrl;
				url +="/appprofile/"+issue.jpgPath+"/"+pageNo+"/pad_q"+pageNo+".jpg";
			}
			else
			{
				url += SystemProp.magServerUrl;
				url +="/"+issue.publicationId+"/"+issue.id+"/"+tempStr+".jpg";
			}
				
			$("img#imgUrl").attr("src",url);
			$("input[name=issueId]").val(issue.id);
			$("input[name=pageNo]").val(pageNo);
			$("a[name=textPageFormSaveBtn]").attr("totalPages",issue.totalPages);
			if(issue.totalPages){
				$("div#pageDiv").html("");
				var avl= "";
				for(n=1;n<=issue.totalPages;n++){
					if(n==pageNo){
						avl += "<a href=\"javascript:void(0);\" textPageIssueId='"+issue.id+"' textPagePageNo='"+n+"' class=\"current\">"+n+"</a>";
					}else{
						avl += "<a href=\"javascript:void(0);\" textPageIssueId='"+issue.id+"' textPagePageNo='"+n+"'>"+n+"</a>";
					}
				}
				//alert(avl);
				$("div#pageDiv").html(avl);
			}
			//alert($("img#textImage").attr("src"));
				if(rs.data.textPage){
					var textPage =rs.data.textPage;
					$("#textPageForm textarea[name=content]").val(textPage.content);
					$("#textPageForm input[name=title]").val(textPage.title);
					if(textPage.ad=='1'){
						$("#ad").attr("checked",true);
					}else{
						$("#ad").attr("checked",false);
					}
				}
				if(rs.data.issueContents){
					var issueContents = rs.data.issueContents;
					$("#textPageForm textarea[name=content]").val(issueContents.description);
					$("#textPageForm input[name=title]").val(issueContents.title);
					$("#cflag").attr("checked",true);
					if(rs.data.issueContents.isCatalog=='1')
						$("#catalog").attr("checked",true);
					else
						$("#catalog").attr("checked",false);
					if(rs.data.issueContents.isBoutique=='1')
						$("#boutique").attr("checked",true);
					else
						$("#boutique").attr("checked",false);
				}else{
					$("#cflag").attr("checked",false);
				}
				//加载标签
				tagLi(issue.id,pageNo);
		   }
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!textWriteJson.action",
			type : "POST",
			data : {"issueId":issueId,"pageNo":pageNo},
			success: callback
		});
	});
	
	//加载标签集合
	function tagLi(issueId,pageNo){
		var data ={};
		data["tag.objectId"]=issueId; 
		data["tag.pageNum"]=pageNo;
		if(!issueId){return;}
		if(!pageNo){return;}
		
		var callback=function(result){
			if(result.code==200){
				$("#tagListLi").html("");
				li="<li class=\"title\"><a>标签分类</a></li>";
				if(result.data.tagList){
					$.each(result.data.tagList,function(k,t){
						li += "<li><a href='javascript:void(0);'>"+t.name+"</a></li>";	
					});
				}
				li +="<li class='add'><input type='text' id='newTag' tips='添加新标签' /><a id='addTagButton' href='javascript:void(0)'></a></li>"
				$("#tagListLi").html(li);
			}
		}
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!listTagJson.action",
			type:"POST",
			data:data,
			success:callback
		});
	}
	
	//保存文字录入
	$("a[name='textPageFormSaveBtn']").unbind("click").live("click",function(){
		var textPageForm=$("#textPageForm");
		var title=textPageForm.find("input[name='title']").val();
		var issueId=textPageForm.find("input[name='issueId']").val();
		var pageNo=textPageForm.find("input[name='pageNo']").val();
		var totalPages=$(this).attr("totalPages");
		var content=textPageForm.find("textarea[name='content']").val();
		var issueType = $("#issueType").val();
		var obj = $(this);
		
		var ad = $("#ad").attr("checked");
		
		if(ad){
			ad ='1';
		}else{
			ad ='0';
		}
		
		var cTitle  = $("#cflag").attr("checked");
		if(cTitle){
			cTitle='1';
			if(!title){
				alert('本页作为目录时,标题不能为空');
				return;
			}
		}else{
			cTitle='0';
		}
		var catalog=$("#catalog").attr("checked");
		if(catalog){
			catalog ='1';
			if(!title){
				alert('本页作为目录时,标题不能为空');
				return;
			}
		}else{
			catalog ='0';
		}
		var boutique=$("#boutique").attr("checked");
		if(boutique){
			boutique ='1';
		}else{
			boutique ='0';
		}
		var data={};
		data["textPage.issueId"]=issueId||"";
		data["textPage.content"]=content||"";
		data["textPage.pageNo"]=pageNo||"";
		data["textPage.title"]=title||"";
		data["textPage.ad"]=ad||"";
		data["textPage.contentsFlag"]=cTitle||"";
		data["issueType"]=issueType||"";
		data["catalog"]=catalog||"";
		data["boutique"]=boutique||"";
		
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
				return;
			}
			
			//清空数据
			$("#textPageForm textarea[name=content]").val("");
			$("#textPageForm input[name=title]").val("");
			$("#textPageForm input[name=issueId]").val("");
			$("#textPageForm input[name=pageNo]").val("");
			$("#ad").attr("checked",false);
			$("#cflag").attr("checked",false);
			
			if(totalPages&&totalPages.length>0){
				
					if(obj.attr("moveDirection")=='down'){//下一页
						if(pageNo*1>=totalPages*1){
							$("a[textPageIssueId][textPagePageNo='"+(pageNo*1)+"']").click();
							alert("本期刊已录入完,感谢您的辛勤劳动,先休息一下吧~");
						}
						else
						$("a[textPageIssueId][textPagePageNo='"+(pageNo*1+1)+"']").click();
					}else if(obj.attr("moveDirection")=='up'){//上一页
						if(pageNo*1==1){
							alert("已经是首页");
							$("a[textPageIssueId][textPagePageNo='1']").click();
							return;
						}else{
							$("a[textPageIssueId][textPagePageNo='"+(pageNo*1-1)+"']").click();
						}
					
				
					
				}
			}else{
				alert("保存成功");
			}
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/magazine-list!saveJson.action",
			type : "POST",
			data : data,
			success: callback
		});
	});

	$("a[textPageIssueId][textPagePageNo='1']").click();
});