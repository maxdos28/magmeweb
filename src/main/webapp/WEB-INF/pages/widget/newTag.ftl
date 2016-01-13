<!--body-->
<div class="body pageWidget clearFix" style="padding-bottom:0; margin-bottom:0; margin-top:20px;">
    <!--conTagWall-->
	<div class="outer">
        <!--topBar-->
        <!--
        <div class="kanmiTopBar" id="kanmiTopBar">
            <div class="categoryList">
                <a href="newTag.html" class="current">全部</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=6&orderColumn=createdTime" target="_blank">汽车</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=7&orderColumn=createdTime" target="_blank">旅游</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=10&orderColumn=createdTime" target="_blank">时尚</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=11&orderColumn=createdTime" target="_blank">娱乐</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=13&orderColumn=createdTime" target="_blank">生活</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=12&orderColumn=createdTime" target="_blank">家居</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=8&orderColumn=createdTime" target="_blank">商业</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=9&orderColumn=createdTime" target="_blank">财经</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=14&orderColumn=createdTime" target="_blank">科技数码</a>
                <a href="${systemProp.domain}/user-tag!tagWall.action?categoryId=15&orderColumn=createdTime" target="_blank">人文情感</a>
            </div>
        </div>
        -->

    	<div class="inner newTag clearFix" id="newTag" style="visibility:visible;">
    	
    			<#list tagPageInfo.data as tag >
    			<#if tag_index lt 8>
                <div class="item">
                    <a tag="${tag.id}" class="showBar" href="javascript:void(0)">
                        <div class="img" ><img src="${systemProp.fpageServerUrl}/H0/${tag.path}" /></div>
                        <span>${tag.description}</span>
                        </a>
                    <div class="clearFix">
                    	<#if ((tag.userAvatar)??)&&(tag.userAvatar!="")>
                        <img src="${systemProp.profileServerUrl+tag.userAvatar30}" />
                        <#else>
                        <img src="${systemProp.staticServerUrl}/widget/images/head30.gif" />
                        </#if>
                        <p>${(tag.userNickName)!"用户名"}创建此标签，源于<a href="javascript:void(0)" issue="${tag.issueId}">${(tag.publicationName)!"杂志名"}</a></p>
                    </div>
                </div>
                </#if>
                </#list>
    	</div>
    	
		<#if tagPageInfo?? && tagPageInfo.totalPage??>
        <div class="control" style="text-align: right;">
        <#if tagPageInfo.totalPage gt 9>
        <a id="turnFirst" class="turn first" href="javascript:void(0)">></a>
        <a id="turnLeft" class="turn left" href="javascript:void(0)">></a>
        </#if>
            
            <#if tagPageInfo.totalPage gt 1>
	            <#list 1..tagPageInfo.totalPage as x>
				<#if x lt 10>
	            <a page="${x}" href="javascript:void(0)" <#if x=1>class="current"</#if>>${x}</a>
				</#if>
	            </#list>
            </#if>
            
        <#if tagPageInfo.totalPage gt 9>
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
    var totalSize = parseInt("${tagPageInfo.total}");
    var totalPage = parseInt("${tagPageInfo.totalPage}");
	var tags = {
			<#if tagPageInfo.data?? && tagPageInfo.data?size gt 0>
				<#list tagPageInfo.data as tag>
				"${tag_index}": {id: ${tag.id}, path: "${tag.path}", description: "${tag.description?js_string}",userAvatar: "${tag.userAvatar!}",userAvatar30: "${tag.userAvatar30!}",userId: "${tag.userId}",userNickName: "${tag.userNickName!'用户名'}",issueId: "${tag.issueId}",publicationName: "${tag.publicationName!'杂志名'}"}, 
				</#list>
			</#if>
				foo : null
			};	
	
	//tag.click
	$("a[tag]").live('click',function(e){
		e.preventDefault();
		var targetElement =  e.target;
		var $tag = $(this);
		var tagId = $tag.attr("tag");
		var scrollX = $(window).scrollTop();
		//var url = SystemProp.domain+"/user-tag!show.action?id="+tagId+"&scrollX="+scrollX+
		//			(($(targetElement).attr("name") == 'comment') ? "&focus=comment" : "");
		
		location.href="/widget/widget!showTag.action?id="+tagId;
	});
	
	//tag.user
	$("a[user]").live('click',function(e){
		var userId = $(this).attr("user");
		e.preventDefault();
		location.href=SystemProp.domain+"/user!visit.action?id="+userId;
	});
	//issue
	$("a[issue]").live('click',function(e){
		var issueId = $(this).attr("issue");
		e.preventDefault();
		location.href=SystemProp.domain+"/publish/mag-read.action?widget=1&id="+issueId;
	});

	//control.click
	$("div.control a[page]").live('click',function(e){
		$("div.control a.current").removeClass("current");
		$(this).addClass("current");
		
		pageNo = parseInt($(this).attr("page"));
		
		$("#newTag").empty();
		
		var totalSize = "${tagPageInfo.total}";
		for(var i=(pageNo-1)*8+1;i<(pageNo-1)*8+9;i++){
			var tag = tags[i-1];

			$tmpDiv=$("<div class=\"item\"></div>");
			$tmpA=$("<a tag=\""+tag.id+"\" class=\"showBar\" href=\"javascript:void(0)\"></a>");
			$tmpDiv1=$("<div class=\"img\" ></div>")
			$tmpDiv1.append("<img src=\""+SystemProp.fpageServerUrl+"/H0/"+tag.path+"\" />");
			$tmpA.append($tmpDiv1);
			$tmpA.append("<span>"+tag.description+"</span>");
			$tmpA.appendTo($tmpDiv);
			
			$tmpDiv2=$("<div class=\"clearFix\"></div>");
			
			var $tmpImg;
			if(tag.userAvatar&&tag.userAvatar!=''){
				$tmpImg=$("<img src=\""+SystemProp.profileServerUrl+tag.userAvatar30+"\" />");
			}
			else{
				$tmpImg=$("<img src=\""+SystemProp.staticServerUrl+"/widget/images/head30.gif\" />");
			}
			$tmpDiv2.append($tmpImg);
			$tmpDiv2.append("<p>"+tag.userNickName+"创建此标签，源于<a href=\"javascript:void(0)\" issue=\""+tag.issueId+"\">"+tag.publicationName+"</a></p>");
			$tmpDiv2.appendTo($tmpDiv);
			$tmpDiv.appendTo($("#newTag"));

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