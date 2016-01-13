
<style>
.tableA {
	margin-left: 30px;
	width: 287px;
	float: left;
	margin-top: 20px;
	border-top: 3px solid #eee;
}

.tableA thead td {
	padding: 10px 0;
	font-size: 16px;
	font-weight: bold;
	color: #999;
}

.tableA tbody td {
	padding: 3px 0;
}

#nameList{border:1px solid #ddd; height:20px; background:#f1f1f1; padding:10px; margin:10px 0;}
#nameList strong{ float:left; width:130px; height:20px; line-height:20px;}
#nameList .inner{ width:820px; height:20px; line-height:20px; overflow:hidden; }
#nameList .inner span{padding:0 10px;}
</style>

<script>
function joinUserImageMaster(){
	if( !hasUserLogin() ){
		gotoLogin("请先登录或注册！");
	}else{
		window.location.href = SystemProp.appServerUrl+"/publish/magazine.html";
	}	
}
</script>

<!--body-->
<div class="body">
	<div class="body980">
	
	<#if chargeList?? && chargeList?size gt 0>
    <div id="nameList" class="clearFix">
    	<strong>1元话费实时中奖名单：</strong>
        <marquee class="inner" onMouseOver="this.stop()" onMouseOut="this.start()" scrollamount="4" scrolldelay="0" direction="left"> 
          <#list chargeList as user>
         	 <span>${user.userNickName}</span>
		    </#list>
          </marquee>
    </div>	
    </#if>

		<div
			style="height: 356px; background: url(${systemProp.staticServerUrl}/event/20111025/images/img1.jpg); position: relative;">
			<p
				style="width: 455px; font-size: 16px; color: #fff; padding-left: 505px; padding-top: 85px; line-height: 1.4em;">
        	活动规则：<br />
            	1、五秒钟注册成为麦米用户，即可开始杂志连连看。<br />
		
2、杂志连连看现只推出一关，以完成时间为成绩。<br />
		
3、每天送出十份精美礼品，由成绩最好的选手获得。
<br />
		4、11月11日当天成绩第一名获得Iphone 4s一台。<br />
		
5、11月9日——11月11日期间，凡完成麦米杂志连连看即可获得1元充值话费。<br />
		
6、奖品邮寄以周为单位，统一在下个礼拜的周二寄出。<br />
<a id="beginLlk" href="javascript:void(0)" style="position:absolute; width:260px; height:40px; display:block; right:215px; top:280px; text-indent:-9999em; overflow:hidden;">还在等什么，开始游戏吧！</a>
			</p>
			<a tagShare="tsina" href="javascript:void(0)"
				style="position: absolute; top: 7px; right: 120px; width: 150px; height: 30px; display: block; text-indent: -999em;">新浪</a>
			<a id="hasEventPrize" href="javascript:void(0)"
				style="position: absolute; top: 7px; right: 0px; width: 110px; height: 30px; text-indent: -999em; display: block;">中奖</a>
		</div>
		<div>
			<img src="${systemProp.staticServerUrl}/event/20111025/images/img2.jpg" alt="神圣光棍节终极大奖--iphone4s" />
		</div>
		<div>
			<img src="${systemProp.staticServerUrl}/event/20111025/images/img3.jpg" alt="百分百中奖 来玩即送 好彩不断" />
		</div>
		<div>
			<img src="${systemProp.staticServerUrl}/event/20111025/images/img4.jpg" alt="麦米杂志连连看 每日前十好礼送不停" />
		</div>

		<div style="position: relative;">
			<img src="${systemProp.staticServerUrl}/event/20111025/images/img5.jpg" usemap="#Map1" /> <img
				src="${systemProp.staticServerUrl}/event/20111025/images/cut.gif" width="460" height="380"
				style="position: absolute; left: 16px; top: 142px;" />
			<!--<embed src="http://www.tudou.com/v/B4v6WjgN-1E/v.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" wmode="opaque" width="460" height="383" style="position:absolute; left:16px; top:142px;"></embed>-->
			<map name="Map1" id="Map1">
		        <area shape="rect" coords="596,408,873,454" href="javascript:joinUserImageMaster();" />
		        <area shape="rect" coords="873,5,967,42" href="${systemProp.appServerUrl}/user-image-master/index.html" />			
			</map>

		</div>
		<div>
			<img src="${systemProp.staticServerUrl}/event/20111025/images/img6.jpg" alt="十一月校园行" />
		</div>
		<div>
			<img src="${systemProp.staticServerUrl}/event/20111025/images/img7.jpg" alt="指定合作媒体" />
		</div>


	</div>
</div>




<script type="text/javascript">
$(function(){

	tagInfo = {
			url: '${systemProp.domain}/user-event!index.action',
			title: '麦动十一月，好礼送不停，Iphone 4s、精美笔记本、旅游护照、正品杂志特刊，只要轻松一玩即可获得，更有一元话费，百分百中奖，即时到账。还在等什么，黄金十一月，边玩边拿奖，就在麦米！',
			imgsrc: '${systemProp.staticServerUrl}/event/20111025/images/homeBanner.jpg',
			desc:''
		};
	
	//header_userCenter---------------------------------
	$("#beginLlk").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请先登录或注册！");
		}else{
			window.location.href = SystemProp.appServerUrl+"/user-event!llk.action";
		}
	});	
	
})
</script>