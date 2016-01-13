$(function(){

	$("#passBtn1").bind("click",function(){
		var name = $.trim($("#first-str").val());
		if(name.length==0)
		{
			alert("请输入分类名称");
			return;
		}
		var desc = $.trim($("#first-desc").val());
		if(desc.length==0)
		{
			alert("请输入描述");
			return;
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/phoenix/phoenix-category!addJson.action",
			type: "POST",
			dataType: "json",
			data: {"name":name,"description":desc,"isFree":1},
			success: function(rs){if(rs.code&&rs.code==200){alert("操作成功");refresh();}else{alert("错误:"+rs.message);}}
		});
	});
	$("#passBtn2").bind("click",function(){
		var name = $.trim($("#second-str").val());
		if(name.length==0)
		{
			alert("请输入分类名称");
			return;
		}
		var desc = $.trim($("#second-desc").val());
		if(desc.length==0)
		{
			alert("请输入描述");
			return;
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/phoenix/phoenix-category!addJson.action",
			type: "POST",
			dataType: "json",
			data: {"name":name,"description":desc,"isFree":0},
			success: function(rs){if(rs.code&&rs.code==200){alert("操作成功");refresh();}else{alert("错误:"+rs.message);}}
		});
	});
	$("a[name='p_del']").live("click",function(){
		if(!confirm("确认要删除吗？"))return;
		$.ajax({
			url: SystemProp.appServerUrl+"/phoenix/phoenix-category!delJson.action",
			type: "POST",
			dataType: "json",
			data: {"id":$(this).attr("cid")},
			success: function(rs){if(rs.code&&rs.code==200){alert("操作成功");refresh();}else{alert("错误:"+rs.message);}}
		});
	});
	refresh();
});

function refresh()
{
	var callback = function(rs){
		if(rs.code&&rs.code==200){
			$("#free-dl").html("<dt><label>免费</label></dt>");
			$("#charge-dl").html("<dt><label>收费</label></dt>");
			
			if(rs.data.phoenixCategoryList){
				$.each(rs.data.phoenixCategoryList,function(n,e){
					var tr;
					if(e.recommend=="1")
						tr="<dd><label>"+e.name+"</label></dd>";
					else
						tr="<dd><a href=\"#\" name=\"p_del\" cid=\""+e.id+"\">X</a><label>"+e.name+"</label></dd>";
						
					if(e.isFree=="1")
						$("#free-dl").append(tr);
					else
						$("#charge-dl").append(tr);	
				});
			}
		}else{
			alert("error:"+rs.message);
		}
	};
	var data={};
	$.ajax({
		url: SystemProp.appServerUrl+"/phoenix/phoenix-category!manageChannelJson.action",
		type: "POST",
		dataType: "json",
		data: data,
		success: callback
	});
}