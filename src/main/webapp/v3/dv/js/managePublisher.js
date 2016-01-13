
 $(document).ready(function(){
 	$("#eventListPage").html("");
 	initPage();
	
	//是否一线
	$("input[name='p_status']").live("click",function(){
		var currValue =  $(this);
		var publisherid = currValue.attr("publisherid");
		var cv = currValue.attr("value");
		var levelValue= "0"
		if(currValue.attr("checked")){
			if(cv==0){
				levelValue="1";
			}
		}
		var data ={};
		data["publisher.id"]=publisherid||"";
		data["publisher.level"]=levelValue||"";
		var callback =function(result){
			//alert(result.message);
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-publisher!doSaveJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
		
	});
	//编辑--查看
	$("a[name='p_edit']").live("click",function(){
		var currObj =  $(this);
		var pid= $(this).attr("publisherid");
		var editForm = $("#editForm");
		var dataUser = {};
		dataUser["publisher.id"]=pid||"";
		var callbackUser = function(result){
			var obj = result.data.publisher;
			//初始化 begin
			$("#editForm input[name='userName']").val(obj.userName);
			$("#editForm input[name='publishName']").val(obj.publishName);
			$("#editForm input[name='owner']").val(obj.owner);
			$("#editForm input[name='email']").val(obj.email);
			$("#editForm input[name='contact1']").val(obj.contact1);
			$("#editForm input[name='contactPhone1']").val(obj.contactPhone1);
			$("#editForm input[name='contact2']").val(obj.contact2);
			$("#editForm input[name='contactPhone2']").val(obj.contactPhone2);
			$("#editForm input[name='companyPhone']").val(obj.companyPhone);
			$("#editForm input[name='fax']").val(obj.fax);
			$("#editForm input[name='provinceId']").val(obj.provinceId);
			$("#editForm input[name='cityId']").val(obj.cityId);
			$("#editForm input[name='weiboUid']").val(obj.weiboUid);
			$("#editForm input[name='weiboVerifier']").val(obj.weiboVerifier);
			$("#editForm input[name='webSite']").val(obj.webSite);
			$("#editForm select[name='normalContactType'] > option[value='"+obj.normalContactType+"']").attr("selected",true);
			$("#editForm input[name='normalContact']").val(obj.normalContact);
			$("#editForm input[name='address']").val(obj.address);
			//初始化 end
			$("#userInfoDialog").fancybox();
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-publisher!doGetPojoJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: dataUser,
			success: callbackUser
		});
	});
	
	//保存编辑
//	$("#submit").live("click",function(){
//		$.fancybox.close();
//		//alert(3);
//	});
	
	//删除
	$("a[name='p_del']").live("click",function(){
		if(!ConfirmDel('确认注销吗？')) return;
		var currObj =  $(this);
		var pid= currObj.attr("publisherid");
		var currentPage = currObj.attr("currentPage");
		var statusVaue="3";
		var data ={};
		data["publisher.id"]=pid||"";
		data["publisher.status"]=statusVaue||"";
		var callback =function(result){
			//alert(result.message);
			pageComm2(currentPage);
			initPage();
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-publisher!doSaveJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	});
	//审核
	$("a[name='p_check']").live("click",function(){
		var currObj =  $(this);
		var pid = currObj.attr("publisherid");
		var currentPage = currObj.attr("currentPage");
		var statusVaue="1";
		var data ={};
		data["publisher.id"]=pid||"";
		data["publisher.status"]=statusVaue||"";
		var callback =function(result){
			//alert(result.message);
			pageComm2(currentPage);
			initPage();
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-publisher!doSaveJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
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
	
	function pageComm(currentPage){
		var data = {};
		data.currentPage=currentPage;
		data["publisher.publishName"]=$("input:[name='publishName']").val()||"";
		data["publisher.userName"]=$("input:[name='userName']").val()||"";
		data["publisher.level"]=$("input:[name='level']:radio:checked").val()||"";
		
		var callback =function(result){
			//alert(result.message);
			$("#tbodyContext").html("");
			if(result.data.publisherList){
				var trStr = "";
				$.each(result.data.publisherList,function(k,p){
					trStr +="<tr>";
					trStr +="<td  class=\"tLeft\" >"+p.publishName+"</td>";
					trStr +="<td  class=\"tLeft\" >"+p.userName+"</td>";
					var statuskey = p.status;
					statusValue ='';
					if(statuskey=='1'){statusValue="审核";}
					else if(statuskey=='2'){statusValue="锁定";}
					else if(statuskey=='3'){statusValue="注销";}
					else if(statuskey=='0'){statusValue="未审核";}
					trStr +="<td>"+statusValue+"</td>";
					if(p.level=='1'){
						trStr +="<td><label><input currentPage='"+currentPage+"' publisherid=\""+p.id+"\"  name=\"p_status\" value=\""+p.level+"\" type=\"checkbox\" checked=\"checked\" /></label></td>";
					}else{
						trStr +="<td><label><input  currentPage='"+currentPage+"' publisherid=\""+p.id+"\" name=\"p_status\"  value=\""+p.level+"\" type=\"checkbox\" /></label></td>";
					}
					trStr +="<td><a class=\"btn\" name=\"p_edit\" currentPage='"+currentPage+"' publisherid=\""+p.id+"\" href=\"javascript:void(0);\">查看</a></td>";
					if(statuskey=='3'){
						trStr +="<td><a class=\"del\" currentPage='"+currentPage+"' publisherid=\""+p.id+"\" name=\"p_check\" href=\"javascript:void(0);\">审核</a></td>";
					}else{
						trStr +="<td><a class=\"del\" currentPage='"+currentPage+"' publisherid=\""+p.id+"\" name=\"p_del\" href=\"javascript:void(0);\">注销</a></td>";
					}
					trStr +="</tr>";
				});
				$("#tbodyContext").html(trStr);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-publisher!doJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(currentPage){
		var data = {};
		data.currentPage=currentPage;
		data["publisher.publishName"]=$("input:[name='publishName']").val()||"";
		data["publisher.userName"]=$("input:[name='userName']").val()||"";
		data["publisher.level"]=$("input:[name='level']:radio:checked").val()||"";
		
		var callback =function(result){
			//alert(result.message);
			$("#tbodyContext").html("");
			if(result.data.publisherList){
				//var trStr = "";
				pageCount = result.data.pageNo;
				//$("#tbodyContext").html(trStr);
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-publisher!doJson.action",
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
