
<!--body-->
<div class="body pageWidget clearFix">
    <!--conTagWall-->
	<div class="outer">
        <!--topBar-->
        <div class="kanmiTopBar" id="kanmiTopBar">
            <div class="categoryList">
                <a href="/widget/widget!magzine.action" <#if !sortId??>class="current"</#if>>全部</a>
                <#list sortList as sort>
                <#if sort_index lt 17>
                <a href="/widget/widget!magzine.action?sortId=${sort.id}" <#if sortId??&&sortId=sort.id>class="current"</#if>>${sort.name}</a>
                </#if>
                </#list>
            </div>
        </div>
        <!--magezineBox-->
        <div class="deskShow">
        <#if magPageInfo?? && magPageInfo.data??>
          <#list magPageInfo.data as issue>
              <#if issue_index lt 15>
              

	           <#if (issue_index+1)%5==1 >
		         	<div class="item">
		       </#if>
			           <a issueId="${issue.id}" class="showBar" href="javascript:void(0)" style="display: inline;">
				           <img name="issueRead" src="${systemProp.magServerUrl}/${issue.publicationId}/110_${issue.id}.jpg" />
				           <span name="issueRead">${issue.publicationName}${issue.issueNumber}</span>
			           </a>
		        <#if (issue_index+1)%5==0>
			         </div>
		        </#if>
		        
		      </#if>  
	      </#list>
	    </#if>
        </div>

		<#if magPageInfo?? && magPageInfo.totalPage??>
        <div class="control" style="text-align: right;">
        <#if magPageInfo.totalPage gt 9>
        <a id="turnFirst" class="turn first" href="javascript:void(0)">></a>
        <a id="turnLeft" class="turn left" href="javascript:void(0)">></a>
        </#if>

            <#if magPageInfo.totalPage gt 1>
	            <#list 1..magPageInfo.totalPage as x>
				<#if x lt 10>
	            <a page="${x}" href="javascript:void(0)" <#if x=1>class="current"</#if>>${x}</a>
				</#if>
	            </#list>
            </#if>
            
        <#if magPageInfo.totalPage gt 9>
        <a id="turnRight" class="turn right" href="javascript:void(0)"><</a>  
        <a id="turnLast" class="turn last" href="javascript:void(0)"><</a>  
        </#if>
        
        </div>
        </#if>
        
    </div>
</div>

<script type="text/javascript">
$(function(){
    var pageNo = 1;
    var totalSize = parseInt("${magPageInfo.total}");
    var totalPage = parseInt("${magPageInfo.totalPage}");
	//对bigShow 中的issue的绑定
	$("a[issueId]").find("[name='issueRead']").live('click',function(e){
		var issueA = $(this).parent("a").eq(0);
		var issueId = issueA.attr("issueId");
		
		e.preventDefault();
		window.location.href=SystemProp.appServerUrl+"/publish/mag-read.action?widget=1&id="+issueId;
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
		$("a.current[page]").removeClass("current");
		$(this).addClass("current");
		
		pageNo = parseInt($(this).attr("page"));
		
		$(".deskShow").empty();
		
		for(var i=(pageNo-1)*15+1;i<(pageNo-1)*15+16;i++){
			var issue = issues[i-1];
			if(i%5==1){
				//$tmpDiv.empty();
				$tmpDiv=$("<div class=\"item\"></div>");
				//alert($tmpDiv.children().length)
			}
				$tmpDiv.append("<a issueId=\""+issue.id+"\" class=\"showBar\" href=\"javascript:void(0)\" style=\"display: inline;\"></a>");
				$tmpDiv.children("a:last").append("<img name=\"issueRead\" src=\""+SystemProp.magServerUrl+"/"+issue.publicationId+"/110_"+issue.id+".jpg\" />");
				$tmpDiv.children("a:last").append("<span name=\"issueRead\">"+issue.publicationName+issue.issueNumber+"</span>");
			if(i%5==0||i==totalSize){
            	$tmpDiv.appendTo($(".deskShow"));
 			}
            if(i==totalSize){
            	break;
            }
		}
		rebuildCtl(pageNo);
		
	});
	
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
 		if(totalPage<10){
			return;
		}
		else{
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
				var appendClass = "";
				if(i==curPage){
					appendClass = "class=\"current\"";
				}
				rebuildHtml += "\r\n<a page=\""+i+"\" href=\"javascript:void(0)\" "+appendClass+" >"+i+"</a>";
			}
			$("#turnLeft").after(rebuildHtml);
		}
	}
	
})

</script>