<html>
<head>
<title>标签墙</title>
</head>
<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/tagWall.js" ></script>
<div class="body pageTagWall clearFix">
    <!--conTagWall-->
	<div class="outer">
    <!--topBar-->
    <div class="kanmiTopBar" id="kanmiTopBar">
        <div class="conCategoryV2" id="conCategory" userId="${userId!""}" categoryId="${categoryId!""}" orderColumn="${orderColumn!""}" orderType="${orderType!""}">
            <a href="javascript:void(0)" class="btn" id="btn" name="tagCategoryClick" categoryId="">全部切米<span></span></a>
            <div class="conBody clearFix">
            	<#list categoryList as category> 
            	<div class="item item2 clearFix" id="category_${category.id}">
                    <h3><a href="javascript:void(0)" name="tagCategoryClick" categoryId="${category.id}" orderColumn="createdTime">${category.name}</a></h3>
                    <div class="list">
                        <ul>
                            <li><a href="javascript:void(0)" name="tagCategoryClick" categoryId="${category.id}" orderColumn="createdTime">时间</a></li>
                            <li><a href="javascript:void(0)" name="tagCategoryClick" categoryId="${category.id}" orderColumn="browseNum">点击</a></li>
                            <li><a href="javascript:void(0)" name="tagCategoryClick" categoryId="${category.id}" orderColumn="topNum">推荐</a></li>
                        </ul>
                    </div>
                </div>
                </#list>
            </div>
            <div class="conFooter"></div>
        </div>
        <div class="categoryList" id="categoryList">
        	<a href="javascript:void(0)" class="home">排序：</a>
        	<a href="javascript:void(0)" name="tagCategoryClick" orderColumn="createdTime" >时间</a>
        	<a href="javascript:void(0)" name="tagCategoryClick" orderColumn="browseNum">点击</a>
        	<a href="javascript:void(0)" name="tagCategoryClick" orderColumn="topNum">推荐</a>
        </div>
        
    </div>

    	<div class="inner clearFix" id="tagWall" style="visibility:hidden;">
    			<#list tagList as tag>
	                <div class="item">
	                    <a tag="${tag.id}" class="img" href="javascript:void(0)">
	                        <div style="height:${(tag.height)*210/(tag.width)}px;width:210px;"><img src="${systemProp.tagServerUrl+tag.path}" /></div>
	                    </a>
	                    <a tagTitle='title' class="name" href="javascript:void(0)">
							${tag.title}
	                    </a>
	                    <div class="tools">
	                    	<em class="icon1">点击(${tag.clickNum})</em>
	                        <em class="icon2">推荐(${tag.topNum})</em>
	                        <!--
	                        <em class='icon3'>分享</em>
	                        -->
	                    </div>
	                    <div class="clearFix">
	                    	<#if ((tag.user.avatar)??)&&(tag.user.avatar!="")>
	                        <img src="${systemProp.profileServerUrl+tag.user.avatar30}" />
	                        <#else>
	                        <img src="${systemProp.staticServerUrl}/images/head30.gif" />
	                        </#if>
	                        <p><a href="javascript:void(0)" user="${tag.user.id}">${(tag.user.nickName)!"用户名"}</a>创建此标签，源于<a href="javascript:void(0)" issue="${tag.issue.id}">${(tag.issue.publicationName)!"杂志名"}</a></p>
	                    </div>
	                </div>
                </#list>
                <div id="loadingTags" class="loadMore hide">正在加载更多内容</div>
    	</div>
    	<div id="loadingPage" class="loadMore">正在加载内容</div>
    </div>
</div>
</body>
</html>