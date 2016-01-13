<#macro main>
	<div class="conB conWarehouse">
    	<h2>我的米仓</h2>
        <div class="conBody">
        	<div class="item1 clearFix">
            	<p class="iconTags"><strong>我的标签：</strong><span><a href="${systemProp.appServerUrl}/user-tag!manage.action">${session_user.statsMap.tagNum}个</a></span>(站内共${session_user.statsMap.totalTagNum}个)</p>
            	<p class="iconSubscribe"><strong>我的订阅：</strong><span><a href="${systemProp.appServerUrl}/user-favorite!manage.action?pos=1">${session_user.statsMap.subscribeNum}本</a></span></p>
            </div>
        	<div class="item2 clearFix">
            	<p class="iconMessage"><strong>我的消息：</strong><span><a href="${systemProp.appServerUrl}/user-message!manage.action">${session_user.statsMap.newMessageNum}条新</a></span>(站内共${session_user.statsMap.totalMessageNum}条)</p>
            	<p class="iconFavorites"><strong>我的收藏：</strong><span><a href="${systemProp.appServerUrl}/user-favorite!manage.action?pos=0">${session_user.statsMap.favoriteNum}本</a></span></p>
            </div>
        	<div class="item3 clearFix">
            	
            </div>
        </div>
    </div>        
</#macro>