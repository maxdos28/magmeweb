<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>排行榜</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>
</head>
<body>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<div class="appBody">
    <div class="appTabBox">
    	<!-- appTabBtn -->
    	<div class="appTabBtn">
    		<ul class="i2">
    			<li class="current">阅读榜</li>
    			<li>金币榜</li>
    		</ul>
    	</div>
    	<!-- appTabCon -->
    	<div class="appTabCon">
    		<!-- 阅读榜 -->
    		<div class="appTabConItem">	
    			<#if userId?exists >
    			 <div class="appGroup">
                    <h3 class="appGroup_hd">您当前排名：${readRank}</h3>
                </div> 
                </#if>
		    	<div class="appItemBox_last">
					<#if readTops?? && (readTops?size) gt 0>
		    			<#list readTops as r>
		    				<div class="appItem_list">
						    	<div class="appItem_white_txt i2">
						    		<p><span class="order">${r.rank!}</span>${r.nickName!}</p>
						    		<p>
						    		${r.readNum!}
						    		<#if r_index==0>
						    			<img src="${systemProp.staticServerUrl}/v3/images/looker/gold.png" class="imgsign" />
						    		<#elseif r_index==1>
										<img src="${systemProp.staticServerUrl}/v3/images/looker/ag.png" class="imgsign" />
									<#elseif r_index==2>
										<img src="${systemProp.staticServerUrl}/v3/images/looker/cu.png" class="imgsign" />
									</#if>
						    		</p>
						    	</div>
						     </div>
		    			</#list>
					</#if>
				</div>
    		</div>
    		<!-- 金币榜 -->
    		<div class="appTabConItem">
    			<#if userId?exists>
    			<div class="appGroup">
                    <h3 class="appGroup_hd">您当前排名：${goldRank}</h3>
                </div>
                </#if>
    			<div class="appItemBox_last">
			    	<#if goldTops?? && (goldTops?size) gt 0>
		    			<#list goldTops as g>
		    				<div class="appItem_list">
						    	<div class="appItem_white_txt i2">
						    		<p><span class="order">${g.rank!}</span>${g.nickName!}</p>
						    		<p>
						    		${g.goldNum!}
						    		<#if g_index==0>
						    			<img src="${systemProp.staticServerUrl}/v3/images/looker/gold.png" class="imgsign" />
						    		<#elseif g_index==1>
										<img src="${systemProp.staticServerUrl}/v3/images/looker/ag.png" class="imgsign" />
									<#elseif g_index==2>
										<img src="${systemProp.staticServerUrl}/v3/images/looker/cu.png" class="imgsign" />
									</#if>
						    		</p>
						    	</div>
						     </div>
		    			</#list>
					</#if>
				</div>
    		</div>
    	</div>
    </div>
</div>
</body>
</html>
