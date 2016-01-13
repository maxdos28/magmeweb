$(function(){
	init();
	$("#addCategory").click(function(){
		var issueid= $("#issueId").val();
		if(!issueid)
		{
			alert("无期刊ID");
			return;
		}
		var name = $("#categoryName").val();
		var from = $("#fromCatalog").val();
		var end = $("#endCatalog").val();
		if(!name)
		{
			alert("请填写栏目名称");
			return;
		}
		if(!from)
		{
			alert("请选择开始目录");
			return;
		}
		if(!end)
		{
			alert("请选择结束目录");
			return;
		}
		var data = {};
		data["issueId"]=issueid;
		data["name"]=name;
		data["fromIssueContentsId"]=from;
		data["endIssueContentsId"]=end;
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/issue-category-manage!addIssueCategory.action",
			type : "POST",
			data : data,
			success: function(result){
				if(result.code==200)
				{
					alert("添加成功!");
					search();
				}
				else
				{
					alert(result.message);
				}
					
			}
			});
	});
	function search()
	{
		$("#contentTbody").html("");
		var issueid= $("#issueId").val();
		if(!issueid)
		{
			alert("无期刊ID");
			return;
		}
		var data = {};
		data["issueId"]=issueid;
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/issue-category-manage!searchIssueCategory.action",
			type : "POST",
			data : data,
			success: function(result){
				if(result.code==200)
				{
					var liStr = "";
					$.each(result.data.issueCategoryList,function(k,p){
						liStr+="<tr>"+
                        "<td>"+p.name+"</td>"+
                        "<td>"+p.fromIssueContentsName+"</td>"+
                        "<td>"+p.endIssueContentsName+"</td>"+
                        "<td><a class='del' href='#' id='deleteCategory' cateId='"+p.id+"'>删除</a></td>"+
                        "</tr>";
					});
					$("#contentTbody").append(liStr);
				}
					
			}
			});
	}
	$("#deleteCategory").live("click",function(){
		if(!confirm("是否删除?"))
			return;
		var data = {};
		data["id"]=$(this).attr("cateId");
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/issue-category-manage!deleteIssueCategory.action",
			type : "POST",
			data : data,
			success: function(result){
				if(result.code==200)
				{
					search();
				}
					
			}
			});
	});
	
	var catalogList;
	$("#fromCatalog").change(function(){

		$("#endCatalog").html("");
		var cc = false;
		$.each(catalogList,function(k,p){
			if(p.id==$("#fromCatalog").val())
				cc=true;
			if(cc)
			{
				$("#endCatalog").append("<option value=\""+p.id+"\">"+p.title+"</option>");
			}
		});
	});
	function init()
	{
		var issueid= $("#issueId").val();
		if(!issueid)
		{
			return;
		}
		var data = {};
		data["issueId"]=issueid;
		$.ajax({
			url:SystemProp.appServerUrl + "/new-publisher/issue-category-manage!searchIssueCatalog.action",
			type : "POST",
			data : data,
			success: function(result){
				if(result.code==200)
				{
					catalogList = result.data.issueCatalogList;
					$("#fromCatalog").html("");
					$("#endCatalog").html("");
					$("#fromCatalog").append("<option></option>");
					$.each(catalogList,function(k,p){
						$("#fromCatalog").append("<option value=\""+p.id+"\">"+p.title+"</option>");	
					});
				}
			}
			});
		search();
	}
})