<link href="${systemProp.staticServerUrl}/v3/style/channelHtmlpage.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script type="text/javascript">
	//document.title = "麦米网Magme - 专题导航";
	$(function(){
		$(".pageTagList .conA .contentRight .item .photo img").coverImg();
	});
</script>
<style>
	#conDetailWall .item .info strong {
		display:block;    
		border-top: 1px solid #EEEEEE;
	    color: #000000;
	    font-size: 16px;    
	    font-weight: bold;
	    line-height: 1.6em;
	    padding-top: 5px;
	    word-break: break-all;
	    word-wrap: break-word;
	    margin:3px 0 0; 
	}
</style>
<!--body-->
<div class="body bodyHome pageTagList clearFix" id="conDetailWall">
	<#if publications?? && publications?size lt 0>
		<div class="conA">
	    	<h2>杂志</h2>
	        <div class="conBody clearFix">
	        	<div>
	                <ul class="clearFix">
	                	<#list publications as p>
		                	<li><a href="http://${p.domain}.magme.com/${p.englishname}/">${p.name}</a></li>
	                	</#list>
	                </ul>
	            </div>
	        </div>
	    </div>
	</#if>
	<#if pageDMap?? && pageDMap?size gt 0 && homePageItemMap?? && homePageItemMap?size gt 0>
		<#list pageDMap?keys as categoryName> 
			<div class="conA">
		    	<h2>${categoryName}</h2>
		        <div class="conBody clearFix">
		        	<div class="contentLeft">
		                <ul class="clearFix">
		                	<#list pageDMap[categoryName] as pageD>
		                		<li><a href="${systemProp.appServerUrl}/detail${pageD.id}/">${pageD.name}</a></li>
		                	</#list>
		                </ul>
		            </div>
		        	<div class="contentRight">
		                	<#list homePageItemMap[categoryName] as item>
				                <div class="item">
				                        <div class="photo">
								    		<img height="${item.itemHeight!1}"  width="${item.itemWidth!1}" 
								    			src="${systemProp.staticServerUrl}${avatarResize(item.itemImagepath!'','max_800')}"
												alt="${item.itemTitle!''}" />
										</div>
				                        <div class="info png">
											<a eventId="${item.itemId}" clickEventId="" 
												<#if item.type=='event'>
													href="${systemProp.appServerUrl}/index-detail.action?itemId=${item.itemId}&type=${item.type}"
												<#else> href="${systemProp.appServerUrl}/sns/c${item.itemId}/" </#if>
												target="_blank"><strong>${item.itemTitle!''}</strong>
											</a>
								            <p><#if item.itemContent?length gt 90>${item.itemContent?substring(0,90)}……<#else>${item.itemContent}</#if></p>
				                        </div>
				                    <div class="tools png"><em title="喜欢" class="iconHeart png"></em><em class="iconShare png"></em></div>
				                </div>
		                	</#list>
		            </div>
		        </div>
		    </div>
	    </#list>
    </#if>
</div>