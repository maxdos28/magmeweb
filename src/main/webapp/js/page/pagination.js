$(function(){ 
	/**
	 * @author fredy
	 * @date 2013-07-16
	 * 
	 * 通用分页js，适合表格插件
	 * 配合pageBar.ftl使用
	 * 表格的tbody元素需要有id为tbodyContext
	 * 公开函数为pageComm
	 * 
	 * 参数为：
	 * currentPage当前页码
	 * posturl 发送请求的url
	 * data post过去的请求数据
	 * writetablefn 用于拼接一个tr元素的函数
	 * 
	 * 返回数据：
	 * 发送过去的请求必须返回以下数据
	 * result.code 200请求成功 其它请求失败
	 * result.data.pageNo 总页数
	 * result.data.commondatas 返回的数据
	 * 
	 * p1  是 pagebar 的 eventListPageadd ;
	 * p2  是 pagebar  的 eventListPage
	 */
   var pageNo=0; 
   var indata;
   var inposturl;
   var inwritetablefn;
   var tbodyContext;
   var eventListPageadd ;
   var eventListPage;
   var toPageValue;
   
   pageComm=function (currentPage,posturl,data,writetablefn,tc,p1,p2){
	   if(tc)
	   {
		   tbodyContext = tc;
	   }
	   else
		{
		   tbodyContext = 'tbodyContext';
		}
	   
	   if(p1)
	   {
		   eventListPageadd=p1;
		   toPageValue=p1+'a';
	   }else
	   {
		   eventListPageadd='eventListPageadd';
		   toPageValue='toPageValue';
	   } 
	   
	   if(p2)
	   {
		   eventListPage=p2;
	   }else
	   {
		   eventListPage='eventListPage';
	   } 
	    indata=data;
	    inposturl=posturl;
	    inwritetablefn=writetablefn;
	    data.currentPage=currentPage;
		var callback =function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				if(result.data.pageNo){
					pageNo=result.data.pageNo;
					initPage(pageNo);
				}else{
					initPage(1);
				}
				var trContent = "";
				if(result.data.commondatas)
				{
					$.each(result.data.commondatas,function(i,commondata){
						trContent=writetablefn(trContent,commondata);
					});
				}
				
				$("#"+tbodyContext).html(trContent);
				fnReadyTable();
			}
	   }
	   $.ajax({
			url: posturl,
			type: "POST",
			async: false, 
			dataType: "json",
			data: data,
			success: callback
		});
    }
   
   function pageComm2(currentPage){
	    indata.currentPage=currentPage;
		var callback =function(result){
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
					pageNo=result.data.pageNo;
					//initPage(pageNo);
				}
				var trContent = "";
				if(result.data.commondatas)
				{
					$.each(result.data.commondatas,function(i,commondata){
						trContent=inwritetablefn(trContent,commondata);
					});
				}
				
				$("#"+tbodyContext).html(trContent);
				fnReadyTable();
			}
	   }
	   $.ajax({
			url: inposturl,
			type: "POST",
			async: false, 
			dataType: "json",
			data: indata,
			success: callback
		});
   }

	function fnReadyTable(){
		var tableID=1;
		var tdID =1;
		var tableNum = $('table.table').size();
		for(tableID;tableID<=tableNum;tableID++) {
			$('table.table').get(tableID-1).lang='table'+tableID;
			var tdNum = $('table.table[lang*=table'+tableID+'] tr:first td').size();
			for(tdID; tdID <= tdNum; tdID++) {
				$('table.table tr td:nth-child('+tdID+')').addClass('t'+tdID);
			};
		};
	}
	
	function pageselectCallback(page_id, jq){
		
		$("#"+eventListPageadd).html("");
		$("#"+eventListPageadd).append("跳转到<input class=\"input g20\"  type=\"text\" id=\""+toPageValue+"\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm2(page_id+1);
		return false;
	}
	function initPage(pageNo){
		$("#"+eventListPageadd).html("");
		$("#"+eventListPageadd).append("跳转到<input class=\"input g20\"  type=\"text\" id=\""+toPageValue+"\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		$("#"+eventListPage).html("");
		$("#"+eventListPage).pagination(pageNo, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 20, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "前一页",
			next_text: "后一页"
			}
		);
	}
	//页面跳转
	$("#toPageOk").live("click",function(){
			var currentPage = $("#"+toPageValue).val();
			if(currentPage>pageNo) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
			if(currentPage<=0){currentPage=1} 
			$("#"+eventListPage).html("");
			$("#"+eventListPage).pagination(pageNo, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 20, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				current_page:currentPage-1, 
				prev_text: "前一页",
				next_text: "后一页"
			});
	});
	//刷新当前页
	refreshCurrentPage = function()
	{
		var currentPage = indata.currentPage;
		pageComm2(currentPage);
	}
});

    

