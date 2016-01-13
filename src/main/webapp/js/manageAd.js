var curPageNum;
var curPublicationId;
function turnPage(pageNum,publicationId){
	curPageNum=pageNum;
	curPublicationId=publicationId;
	var data={};
	data.publicationId = publicationId;
	data.pageNum = pageNum;
	data.pageSize = $("#pageSize").val();
	var callback=function(rs){
		$("#tablePageBarContainer").html(rs);
		fnReadyTable();
	}
	
	$.ajax({
		url:SystemProp.appServerUrl+"/ad/manage-position!getAdPositionAjax.action",
		type : "POST",
		data : data,
		success: callback
	});
}
;$(function(){

	//杂志管理滚动
	var $topbar = $("#pubTopbar");
	var $topbarInner = $topbar.find(".inner");
	var $topbarItem = $topbar.find(".item");
	var $topbarBtnLR = $topbar.find(".btnLR");
	var itemWidth 	= $topbarItem.eq(0).outerWidth(true);
	var itemNumber	= $topbarItem.length;
	var currentId = 0;
	var lock = 0;
	var delayTime = 800;
	
	
	fnSetBtn(0);
	$topbarBtnLR.unbind('click').bind("click",function(){
		if(lock==0){
			lock=1;
			if($(this).hasClass("turnLeft") && currentId!=0){
				currentId--;
			}else if($(this).hasClass("turnRight") && (currentId+1)*4 < itemNumber){
				currentId++;
			}
			$topbarInner.animate({marginLeft:-(itemWidth*currentId*4)},delayTime,"easeOutQuint");
			fnSetBtn(currentId);
			setTimeout(function(){lock=0},delayTime);
		}
	});
	function fnSetBtn(id){
		currentId=id;
		$topbarBtnLR.removeClass("stopL").removeClass("stopR");
		if(itemNumber <= 4){
			$topbarBtnLR.filter(".turnRight").addClass("stopR");
			$topbarBtnLR.filter(".turnLeft").addClass("stopL");
			return;
		}
		if(currentId==0){
			$topbarBtnLR.filter(".turnLeft").addClass("stopL");
		}else if((currentId+1)*4>=itemNumber){
			$topbarBtnLR.filter(".turnRight").addClass("stopR");
		}
		
	}
	
	$("div[name='adPositionPublication']").click(function(){
		$(".inner .current").removeClass("current");
		$(this).addClass("current");
		var publicationId=$(this).attr("publicationId");
		
		if(curPublicationId && curPublicationId==publicationId){
			if(!curPageNum){
				curPageNum=1;
			}
		}else{
			curPageNum=1;
		}
		if(publicationId&&publicationId.length>0){
			turnPage(curPageNum,publicationId);
		}
	});
	$("a[name='viewAdPosition']").unbind().live("click",function(){
		var issueId=$(this).attr("issueId");
		var callback=function(rs){
			$("#tablePageBarContainer").html(rs);
		}
		
		$.ajax({
			url:SystemProp.appServerUrl+"/ad/manage-position-detail!queryAjax.action",
			type : "POST",
			data : {"issueId":issueId},
			success: callback
		});		
	});
	
	$("a[name='editAdPosition']").unbind().live("click",function(){
		var content = $("#fancybox-content");
		var adPositionId=$(this).attr("adPositionId");
		var obj=$(this);
		
		var editTagDialog = $("#editTagDialog");
		var childs=$(this).parent().parent().children();
		var adPositionId=childs.eq(0).text();
		var title=childs.eq(2).text();
		var keywords=childs.eq(3).text();
		var description=childs.eq(4).text();
		
		editTagDialog.find("input[name='id']").val(adPositionId);
		editTagDialog.find("input[name='title']").val(title);
		editTagDialog.find("input[name='keywords']").val(keywords);
		editTagDialog.find("textarea[name='description']").val(description);
		
		//生成dialog
		editTagDialog.fancybox();
		
		$("#editAdPositionFormCancelBtn",content).unbind().click(function(){$.fancybox.close();});
		
		$("#editAdPositionFormUpdateBtn",content).unbind().click(function(){
			var url = SystemProp.appServerUrl + "/ad/manage-position-detail!updateJson.action";
			var data = form2object('editAdPositionForm');
			var callback=function(rs){
				if(rs.code != 200){
					alert(rs.message);
				}else{
					alert("修改成功",function(){
						$.fancybox.close();
						
						childs.eq(0).text(data.id);
						childs.eq(2).text(data.title);
						childs.eq(3).text(data.keywords);
						childs.eq(4).text(data.description);
					});
				}
			};
			$.ajax({
				url:url,
				type : "POST",
				dataType : "json",
				data : data,
				success: callback
			});
		});
		
		$("#editAdPositionFormDeleteBtn",content).unbind().click(function(){
			var url = SystemProp.appServerUrl + "/ad/manage-position-detail!deleteJson.action";
			var data = form2object('editAdPositionForm');
			
			var callback=function(rs){
				if(rs.code != 200){
					alert(rs.message);
				}else{
					alert("删除成功",function(){
						$.fancybox.close();
						obj.parent().parent().remove();
					});
				}
			};			
			$.ajax({
				url:url,
				type : "POST",
				dataType : "json",
				data : data,
				success: callback
			});
		});
			
	});
	
	
	//search-----------------------
	$("#searchBtn").click(function(e){
		e.preventDefault();
		var searchContent = $.trim( $("#searchContent").val() );
		if(!!searchContent && searchContent !== '输入杂志名'){
			window.location.href ="/ad/manage-position.action?name="+encodeURIComponent(searchContent);
		}
	});
	$("#searchContent").keyup(function(event){
		event.stopPropagation();
		if (event.keyCode == '13') {
			event.preventDefault();
			$("#searchBtn").click();
	    }
	});
	
});
