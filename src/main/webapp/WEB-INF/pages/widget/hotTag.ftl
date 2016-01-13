<script src="${systemProp.staticServerUrl}/js/jquery.masonry.min.js"></script>

<script>
$(function(){
	$('#hotTag').masonry({itemSelector: '.item'});

})
</script>
<!--[if lte IE 6]>
<link href="style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script src="/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->

<!--body-->
<div class="body pageWidget clearFix" style="padding-bottom:0; margin-bottom:0; margin-top:20px;">
    <!--conTagWall-->
	<div class="outer">
        <!--topBar-->
        <!--
        <div class="kanmiTopBar" id="kanmiTopBar">
            <div class="categoryList">
                <a href="hotTag.html" class="current">全部</a>
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

    	<div class="inner hotTag clearFix" id="hotTag" style="visibility:visible;">
                
    			<#list hotTagList as tag >
    			<#if tag_index lt 30>
                <div class="item">
                    <a tag="${tag.id}" class="showBar" href="javascript:void(0)">
                        <div class="img" style="height:${(tag.height)*155/(tag.width)}px;"><img src="${systemProp.tagServerUrl+tag.path}" /></div>
                        <span>${tag.title}</span>
                        
                        </a>
                    <div class="clearFix">
                    	<#if ((tag.user.avatar)??)&&(tag.user.avatar!="")>
                        <img src="${systemProp.profileServerUrl+tag.user.avatar30}" />
                        <#else>
                        <img src="${systemProp.staticServerUrl}/widget/images/head30.gif" />
                        </#if>
                        <p>${(tag.user.nickName)!"用户名"}创建此标签，源于<a href="javascript:void(0)" issue="${tag.issue.id}">${(tag.issue.publicationName)!"杂志名"}</a></p>
                    </div>
                </div>
                </#if>
                </#list>                
                
    	</div>
        <div class="loadMore"><a href="${systemProp.domain}/tagWall.html?begin=0&size=30" target="_blank">点击显示更多内容...</a></div>
    </div>
</div>

<script type="text/javascript">
$(function(){

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
		window.location.href=SystemProp.domain+"/user!visit.action?id="+userId;
	});
	//issue
	$("a[issue]").live('click',function(e){
		var issueId = $(this).attr("issue");
		e.preventDefault();
		window.location.href=SystemProp.domain+"/publish/mag-read.action?id="+issueId;
	});

})

</script>