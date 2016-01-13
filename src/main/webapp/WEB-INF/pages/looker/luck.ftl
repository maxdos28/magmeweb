<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>刮刮乐</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/Lottery.js"></script>
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
	<div class="appHd">
		<h1 class="appHd_hd">刮刮乐</h1>
	</div>
	<div class="appItemBox_last">
    	<div class="appItem_white">
    		<div class="appItem_white_txt inew2">
    			<p id="goldp">您有${gold!}个<span class="iconMoney">金币</span></p>
    			<p><a href="/app/looker-web!getGiftsList.action?os=${os!}&uid=${userId!}&appId=${appId!}&muid=${muid!}&version=${v!}&userId=${userId!}">金币记录</a></p>
    		</div>
    		<span class="icon_arrow"></span>
	    </div>
	</div>
	<div class="appItemBox_last scratchBox">
			<#if luckGiftList?? && (luckGiftList?size) gt 0>
				<#list luckGiftList as g>
					<div class="appItem_white appItem_whiteOther">
				    	<div class="appItem_white_txt i2">
				    		<div class="imgBlock">
				    			<img src="${systemProp.staticServerUrl}${g.picPath!}">
			    				<p class="prize">${g.giftName!}</p>
			    				<p>仅剩${g.showQty!}个</p>
				    		</div>
				  
				    	</div> 
				    </div>
				</#list>
			</#if>
			    	<div class="appItem_white appItem_whiteOther appItem_whiteOther160">
			        	<div class="lottery">
				    		<div id="lotteryContainer"></div>
				    		<div id="lotteryContainer_shade">请先兑换</div>
				    		<div id="lotteryBtn">10个金币兑换</div>
				    	</div>
				    </div>	 
				    <div class="appItem_whiteEvery appItem_change appItem_changeT">
				    	<p class="appItem_gray_txt">规则说明：</p>
				    	<p class="appItem_gray_txt">说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字说明文字</p>
				    </div>
			    </div>	
</div>
</body>
</html>
<script type="text/javascript">

$(function(){
	
	
	var lotteryObj ={
		_send : false,
		_refresh : false,
		_flag : true,
		_awardId : null,
		_appId : '',
		callbackAppId : function(){
			return this._appId || 903;
		},
		getAppId : function(id){
			this._appId = id;
		}
	};
	//showGuaGua();
	//function showGuaGua(){
	//	$('#lotteryContainer_shade').show();
	//	 lotteryObj._send = true;
	//	 lotteryObj._refresh = true;
	//}
	$('#lotteryContainer_shade').show();
	$('#lotteryBtn').on('touchstart click',function(){
		if(!lotteryObj._flag||lotteryObj._refresh) return; 
		   lotteryObj._flag = false;
		var data = {"userId":$("#uid").val(),"appId":$("#appId").val(),"muid":$("#muid").val(),"v":$("#version").val()};
		$.ajax({
				url : SystemProp.appServerUrl + "/app/looker-web!getAward.action",
				type : "post",
				async : false,
				data : data,
				dataType : 'json',
				success : function (rs){
					if(rs.code!=200) 
						alertMes(rs.message);
					else
					{
						var lottery = new Lottery('lotteryContainer', '#CCC', 'color',284, 100, drawPercent);
						lotteryObj._awardId = rs.data.luckCard.cardId;
						lottery.init(rs.data.luckCard.cardName,'text');
						$('#lotteryContainer_shade').fadeOut(200);
					 	$('#lotteryBtn').fadeOut(200);
					 	lotteryObj._send = true;
					 	lotteryObj._refresh = true;	
					}
					lotteryObj._flag = true;
				}
		});
	});
    function drawPercent(percent) {
        if( Math.ceil(percent)>0 && lotteryObj._send){
            lotteryObj._send = false;
			var data = {"userId":$("#uid").val(),"appId":$("#appId").val(),"muid":$("#muid").val(),"v":$("#version").val(),"awardId":lotteryObj._awardId};
            $.ajax({
				url : SystemProp.appServerUrl + "/app/looker-web!commitAward.action",
				type : "post",
				async : false,
				data : data,
				dataType : 'json',
				success : function (rs){
					if(rs.code!=200) 
						alertMes("兑换失败！");
					else
					{
						//刷新金币
						$("#goldp").html("您有"+rs.data.gold+"个<span class='iconMoney'>金币</span>");
					}
				}
			});
        }
        if(Math.ceil(percent)>10  && lotteryObj._refresh){
        	lotteryObj._refresh = false;
        	if($('#lotteryBtn').is(":hidden")){
        		// $('#lotteryBtn').addClass('lotterying');
        		$('#lotteryBtn').fadeIn().text('继续刮奖');
        	}
        }
    }
    function alertMes(msg){
    	var os = $("#os").val();
		if(os == "ANDROID"){
			window.magme.alert(msg);
		}else if(os == "IOS"){
			window.open("iosmessage:"+msg);
		}
	};
	
});

</script>
