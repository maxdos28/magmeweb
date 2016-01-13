<!--magezineBox-->
<div id="bodyMagazine" class="bodyMagazine clearFix">

        <#if magPageInfo?? && magPageInfo.data??>
          <#list magPageInfo.data as issue>
              <#if issue_index lt 15>
              <div class="item">
        	       <a issueId="${issue.id}" class="photo" href="javascript:void(0)" name="reader"><img name="issueRead" src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" /><h5 title="${issue.publicationName}的往期期刊" pubName="${issue.publicationName}">[&nbsp;${issue.publicationName}${issue.issueNumber}&nbsp;]</h5></a>
    		  </div>
		      </#if>  
	      </#list>
	    </#if>

</div>


		<#if magPageInfo?? && magPageInfo.totalPage??>
        <div class="control">
        <#if magPageInfo.totalPage gt 9>
        <a id="turnFirst" class="turn first" href="javascript:void(0)">></a>
        <a id="turnLeft" class="turn left" href="javascript:void(0)">></a></#if><#if magPageInfo.totalPage gt 1><#list 1..magPageInfo.totalPage as x><#if x lt 10><a page="${x}" href="javascript:void(0)" <#if x=1>class="current"</#if>>${x}</a></#if></#list>
        </#if>
            
        <#if magPageInfo.totalPage gt 9>
        <a id="turnRight" class="turn right" href="javascript:void(0)"><</a>  
        <a id="turnLast" class="turn last" href="javascript:void(0)"><</a>  
        </#if>
        
        </div>
        </#if>

<script type="text/javascript">
$(function(){	
	var xx = "${xx!''}";
	function getX(){
		return xx;
	}
    var pageNo = 1;
    var totalSize = parseInt("${magPageInfo.total}");
    var totalPage = parseInt("${magPageInfo.totalPage}");
    
	//杂志图片的绑定
	$("a[issueId]").find("[name='issueRead']").live('click',function(e){
		e.preventDefault();
		
		var issueA = $(this).parent("a").eq(0);
		var issueId = issueA.attr("issueId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/widget-read.action?id="+issueId+"&backPageNo="+pageNo+"&backUrl="+encodeURIComponent(backUrl) + "&xx=" + xx;
	});
	
	//杂志名称的绑定
	$("a[issueId]").find("[pubName]").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var pubName = $(this).attr("pubName");
		window.location.href="/widget/widget-search.action?queryStr="+encodeURIComponent(pubName);
	});	
	
	
	var issues = {
				<#if magPageInfo?? && magPageInfo.data?? && magPageInfo.data?size gt 0>
					<#list magPageInfo.data as issue>
					"${issue_index}": {id: ${issue.id}, publicationId: ${issue.publicationId}, publicationName: "${issue.publicationName?js_string}",issueNumber: "${issue.issueNumber?js_string}"}, 
					</#list>
				</#if>
				foo : null
			};
	
	
	//control.click
	$("a[page]").unbind('click').live('click',function(e){
		//$("a.current[page]").removeClass("current");
		//$(this).addClass("current");
		pageNo = parseInt($(this).attr("page"));
		gotoPage(pageNo);
	});
	
	function gotoPage(pageNo){
		$("#bodyMagazine").empty();
		
		for(var i=(pageNo-1)*15+1;i<(pageNo-1)*15+16;i++){
			var issue = issues[i-1];
			var $tmpDiv=$("<div class=\"item\"></div>");
			$tmpDiv.append("<a issueId=\""+issue.id+"\" class=\"photo\" href=\"javascript:void(0)\" name=\"reader\"></a>");
			$tmpDiv.children("a:last").append("<img name=\"issueRead\" src=\""+SystemProp.magServerUrl+"/"+issue.publicationId+"/200_"+issue.id+".jpg\" />");
			$tmpDiv.children("a:last").append("<h5 title=\""+issue.publicationName+"的往期期刊\" pubName=\""+issue.publicationName+"\">[ "+issue.publicationName+issue.issueNumber+" ]</h5>");
            $tmpDiv.appendTo($("#bodyMagazine"));
            
            if(i==totalSize){
            	break;
            }
		}
		
	    bindHover();
		rebuildCtl(pageNo);
	}
	
	$("#turnLeft").unbind('click').live('click',function(e){
		if(pageNo<2){
			return;
		}
		else{
			pageNo = pageNo-1;
			$("a[page="+pageNo+"]").click();
		}
	});
	
	$("#turnRight").unbind('click').live('click',function(e){
		if(pageNo>=totalPage){
			return;
		}
		else{
			pageNo = pageNo+1;
			$("a[page="+pageNo+"]").click();
		}
	});
	
	$("#turnFirst").unbind('click').live('click',function(e){
			rebuildCtl(1);
			$("a[page=1]").click();
	});
	
	$("#turnLast").unbind('click').live('click',function(e){
			rebuildCtl(totalPage);
			$("a[page="+totalPage+"]").click();
	});

	
	function rebuildCtl(curPage){
 		if(totalPage>=10){
			$("div.control a[page]").remove();
			var firstPage = 1;
			var lastPage = totalPage;
			
			if((curPage-4)<=1){
				firstPage = 1;
				lastPage = 9;
			}
			else if((curPage+4)>=totalPage){
				firstPage = totalPage-8;
				lastPage = totalPage;
			}
			else{
				firstPage = curPage-4;
				lastPage = curPage+4;
			}
			var rebuildHtml = "";
			for(var i=firstPage;i<=lastPage;i++){
				rebuildHtml += "<a page=\""+i+"\" href=\"javascript:void(0)\"  >"+i+"</a>";
			}
			$("#turnLeft").after(rebuildHtml);
		}
 		$("a[page]").removeClass("current");
 		$("a[page="+curPage+"]").addClass("current");
	}

	if(!!getUrlValue("curPage")&&parseInt(getUrlValue("curPage"))>1){
		var curPage = parseInt(getUrlValue("curPage"));
		gotoPage(curPage);
	}	
})

</script>