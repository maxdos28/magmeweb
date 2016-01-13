<style>
.userList li{ width:194px; height:30px; line-height:30px; margin-bottom:10px; float:left;}
.top10 li{ padding:2px 0;}
</style>

<div class="body">
	<div class="body980">
    <div style="margin-top:20px; position:relative;"><img src="${systemProp.staticServerUrl}/event/20111025/images/img8.jpg" />
    	<a href="${systemProp.appServerUrl}/user-event!index.action" style="position:absolute; right:0; top:15px;">返回活动页</a>
    </div>
    <div style="width:172px; float:left;">
        <h3 style="font-size:16px; margin:0 0 10px 0; border-top:3px solid #eee; padding-top:10px;">切米达人排行榜</h3>
        <ol class="top10">
        	<#if userImageMasterList??>
        	<#list userImageMasterList as master>
            <li><a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${(master.id)!""}">${(master.nickName)!""}</a></li>
            </#list>
            </#if>        
        </ol>
    </div>
    <div style="width:778px; float:left; margin-left:30px;">
        <h3 style="font-size:16px; margin:0 0 10px 0; border-top:3px solid #eee; padding-top:10px;">每周获奖名单</h3>
        <ul class="userList clearFix">
			<li>mb2情侣休闲服饰</li>
			<li>謝谢你的愛1999</li>
			<li>1108070122</li>
			<li>steele88</li>
			<li>dg1wzhang</li>
			<li>darkless</li>
			<li>比浪花在浪一点</li>
			<li>smillesun</li>
			<li>motoxp2</li>
			<li>inezn</li>
			<li>神马如浮云</li>
			<li>之水流觞</li>
			<li>evan102</li>
			<li>wjpcy</li>
			<li>我叫馨馨</li>
			<li>CZJESS</li>
			<li>anruochen</li>
			<li>哈了个皮</li>
			<li>8090</li>
			<li>麦田上的乌鸦</li>
			<li>troube</li>
			<li>Sunng</li>
			<li>pangxieguohe</li>
			<li>小龙哈哈</li>
			<li>VIP刘亚</li>
			<li>团团viky</li>
			<li>抹茶悠扬</li>
			<li>haihanyingjun</li>
			<li>qwez3</li>
			<li>愤怒的驴子</li>
			<li>TT</li>
			<li>饕之妖妖</li>
			<li>四舅奶奶</li>
			<li>麦米乐乐</li>
			<li>春天的三皮</li>
        </ul>
    </div>
    </div>
</div>