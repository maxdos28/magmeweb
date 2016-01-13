;$(function($){
	document.documentElement.style.webkitTouchCallout = "none"; //禁止弹出菜单
	document.documentElement.style.webkitUserSelect = "none";//禁止选中
	
	var os_andriod = "ANDROID";
	var os_ios = "IOS";
	var os = $("#os").val();
	var uid = $("#uid").val();
	var appid = $("#appId").val();
	var muid = $("#muid").val();
	var version = $("#version").val();
	
	//点击分享邀请
	$("#inviteFriends").live("click",function(){
		var val = $(this).attr("val");
		if(os == os_andriod){
			window.magme.shareInvitation(val);
		}else if(os == os_ios){
			window.open("iosmethod:shareInvitation"+val);
		}
	});
	
	//链接点击 如果 跳转消息页
	$('div[name="magme_goto"],div[name="magme_goto"]>a').live("click", function(){
		var val = $(this).attr("val");
		var title = $(this).attr("tt");
		if(os == os_andriod){
			window.magme.transition(val,title);
		}else if(os == os_ios){
			window.open("iosurl:"+val);
		}
	});
	
	//完善个人信息
	$("#user_detial_btn").live("click",function(){
		var age = $("#age").val();
		var sex = $("#sex").val();
		var mobile = $("#mobile").val();
		var invitecode = $("#invitecode").val();
		var userInviteCode = $("#userInviteCode").val();
		if(age == ''){
			$("#age").focus();
			alertMes("请输入年龄！");
			return;
		}else if(age < 1 || age > 110){
			$("#age").focus();
			alertMes("请输入正确年龄！");
			return;
		}
		if(mobile == ''){
			$("#mobile").focus();
			alertMes("请输入手机号码！");
			return;
		}else if(mobile != ''){
			if(!/^1\d{10}$/.test(mobile)){
				$("#mobile").focus();
				alertMes("请输入正确手机号码！");
				return;
			}
		}
		if(invitecode != '' &&  invitecode == userInviteCode){
			$("#invitecode").focus();
			alertMes("邀请码有误,不能使用自己的邀请码！");
			return;
		}
		
		var data = {"userId":uid,"appId":appid,"muid":muid,"v":version,"age":age,"sex":sex,"mobile":mobile,"invitecode":invitecode};
		$.ajax({
			url : SystemProp.appServerUrl + "/app/looker-if!updateUser.action",
			type : "post",
			async : false,
			data : data,
			dataType : 'json',
			success : function (rs){
				if(rs == null) 
					alertMes("更新失败！");
				else
					alertMes(rs.message);
			}
		});
		
	});
	
	//邀请好友页面 输入邀请码
	$("#appBtnRight").live("click",function(){
		var code = $("#inviteCode").val();
		var userInviteCode = $("#userInviteCode").val();
		if($.trim(code) == ''){
			$("#inviteCode").focus();
			alertMes("请输入邀请码!");
			return;
		}else if(code != '' &&  code == userInviteCode){
			$("#inviteCode").focus();
			alertMes("邀请码有误,不能使用自己的邀请码！");
			return;
		}else{
			var data = {"userId":uid,"appId":appid,"muid":muid,"v":version,"invitecode":code};
			$.ajax({
				url : SystemProp.appServerUrl + "/app/looker-if!updateInvitationCode.action",
				type : "post",
				async : false,
				data : data,
				dataType : 'json',
				success : function (rs){
					if(rs == null) 
						alertMes("提交失败！");
					else{
						if(rs.code == 200){
							alertMes("邀请码提交成功！");
							$("#invitecode_div").html("<label>"+code+"</label>");
						}else{
							alertMes(rs.message);
						}
					}
    				
				}
			});
		}
		
	});
	
	//签到
	$("#addGoldByCheckDay").live("click",function(){
		var data = {"userId":uid,"appId":appid,"muid":muid,"v":version};
		var day = $(this).attr("day");
		$.ajax({
			url : SystemProp.appServerUrl + "/app/looker-if!userSignIn.action",
			type : "post",
			async : false,
			data : data,
			dataType : 'json',
			success : function (rs){
				if(rs == null) alertMes("签到失败！");
				else{
					if(rs.code == 200){
						var gold = rs.data.gold;
						alertMes("恭喜领到"+rs.data.addGold+"金币，明天也要努力啊");
						if(os == os_andriod){
							window.magme.signIn(gold);
						}else if(os == os_ios){
							window.open("iosmethod:signIn"+gold);
						}
						$("#cur_gold_html").html(gold +"<span>金币</span>");
						$("#addGoldByCheckDay_parent").html("<lable class='appBtnHas'>已领取</lable>");
						var cur_day = parseInt(day)+1;
						if(cur_day>7)
							cur_day = 1;
						$("#cur_day_html").html(cur_day +"<span>天</span>");
					}else{
						alertMes(rs.message);
					}
				}
				
			}
		});
	});
	
	//金币兑换
	$(".appItem_gray_list>li").live("click",function(){
		//判断兑换按钮是否存在,不存在则不处理
		var change = $(this).find(".appChange");
		if(change.length==0)
			return;
		var pid = $(this).attr("val");
		var gold = $("#gold").val();
		var goldNum = $(this).attr("num");
		var gn = $(this).attr("gn");
		var mes ="";
		if(parseInt(gold) < parseInt(goldNum)){
			mes = "抱歉！金币不足，赶紧赚金币";
			showDialog(mes);
		}else{
			var _notice = $("#covert_notice").val();
			if(_notice == 'true'){
				showDialog("个人信息未完善,请完善个人信息");
			}else{
				mes = "确认申请兑换 ("+ gn +")，成功后将消费"+goldNum+"金币";
				$("#giftId").val(pid);
				if(os == os_andriod){
					window.magme.confirm(mes);
				}else if(os == os_ios){
					window.open("iosconfirm:"+mes);
				}
			}
		}
	});
	
	//显示彩蛋
	$("#show_egg_pic").live("click",function(){
		if(os == os_andriod){
			window.magme.showEgg();
		}else if(os == os_ios){
			window.open("iosmethod:showEgg");
		}
	});
	
	//彩蛋活动宣传页面
	$("#show_activity").live("click",function(){
		alertMes("活动详情 面前还没任何跳转 请尽快完善");
	});
	
	function showDialog(msg){
		if(os == os_andriod){
			window.magme.showDialog(msg);
		}else if(os == os_ios){
			window.open("iosdialog:"+msg);
		}
	};
	
	function alertMes(msg){
		if(os == os_andriod){
			window.magme.alert(msg);
		}else if(os == os_ios){
			window.open("iosmessage:"+msg);
		}
	};
	
	$("#load_message_more").live("click",function(){
		var last = $("#last_message_id").val();
		var data = {"userId":uid,"appId":appid,"muid":muid,"v":version,"messageId":last};
		$.ajax({
			url : SystemProp.appServerUrl + "/app/looker-web!getMessageItem.action",
			type : "post",
			async : false,
			data : data,
			dataType : 'html',
			success : function (rs){
				if(rs == ''){
					$("#appItemBox_last").hide();
				}else{
					var md = $("#message_center").children(".appItem_message-list");
					if(md.length>0)
						$(md[md.length-1]).append(rs);
				}
			}
		});
	});
		//抢购
		$("a[name=buyLimitBtn]").live("click",function(){
			var giftId = $(this).attr("giftId");
			if(!giftId)
			{
				return;
			}
			var data = {"userId":uid,"appId":appid,"muid":muid,"v":version,"giftId":giftId};
			$.ajax({
				url : SystemProp.appServerUrl + "/app/looker-web!buyLimitOrder.action",
				type : "post",
				async : false,
				data : data,
				dataType : 'json',
				success : function (rs){
					if(rs != null && rs.code == 200){
						var gold = rs.data.gold;
						$("#goldp").html("您有"+gold+"个<span class='iconMoney'>金币</span>");
						if(!rs.data.buy)
						{
							alertMes(rs.message);
							return;
						}
						if(os == os_andriod){
							window.magme.showDialog("兑换成功,我们将尽快处理请求。");
							window.magme.signIn(gold);
						}else if(os == os_ios){
							window.open("iosdialog:"+"兑换成功,我们将尽快处理请求。");
							window.open("iosmethod:signIn"+gold);
						}
						
					}else{
						if(os == os_andriod){
							window.magme.showDialog(rs.message);
						}else if(os == os_ios){
							window.open("iosdialog:"+rs.message);
						}
					}
				}
			});
		});
		//消息回复
		$("a[name=replyBtn]").live("click",function(){
			var replyBtn = $(this);
			var msgTextarea = replyBtn.parent().parent().find("textarea");
			var msg = msgTextarea.val();
			if(msg.length==0)
				return;
			var msgId = replyBtn.attr("msgId");
			var data = {"userId":uid,"appId":appid,"muid":muid,"v":version,"advice":msg,"userMessageId":msgId};
			$.ajax({
				url : SystemProp.appServerUrl + "/app/looker-if!userMessages.action",
				type : "post",
				async : false,
				data : data,
				dataType : 'json',
				success : function (rs){
					if(rs != null && rs.code == 200){
						if(os == os_andriod){
							window.magme.showDialog("回复成功");
						}else if(os == os_ios){
							window.open("iosdialog:回复成功");
						}
						var $par = replyBtn.parents('.appItem_message-list');
						$par.find('.reply').removeClass('no');
						$par.find('.reply-Content').hide();	
						msgTextarea.val("");
					}else{
						if(os == os_andriod){
							window.magme.showDialog(rs.message);
						}else if(os == os_ios){
							window.open("iosdialog:"+rs.message);
						}
					}
				}
			});
		});
});

//意见反馈
function lookerAdvice(){
	var val = $("#txAdvice").val();
	var os_andriod = "ANDROID";
	var os_ios = "IOS";
	var os = $("#os").val();
	var uid = $("#uid").val();
	var appid = $("#appId").val();
	var muid = $("#muid").val();
	var v = $("#version").val();
	
	var msg;
	if($.trim(val) == ''){
		msg = "请输入你反馈的信息";
		if(os == os_andriod){
			window.magme.alert(msg);
		}else if(os == os_ios){
			window.open("iosmessage:"+msg);
		}
	}else{
		var isStar = $("#user-star").val();
		var data;
		//已评星
		if(isStar=="y")
		{
			data = {"userId":uid,"appId":appid,"muid":muid,"v":v,"advice":val};				
		}
		//未评星
		else
		{
			var star1 = $("#star1").find(".on").length;
			var star2 = $("#star2").find(".on").length;
			var star3 = $("#star3").find(".on").length;
			if(star1>0||star2>0||star3>0)
				data = {"userId":uid,"appId":appid,"muid":muid,"v":v,"advice":val,"star1":star1,"star2":star2,"star3":star3};
			else
				data = {"userId":uid,"appId":appid,"muid":muid,"v":v,"advice":val};	
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/app/looker-if!userMessages.action",
			type : "post",
			async : false,
			data : data,
			dataType : 'json',
			success : function (rs){
				if(rs == null){
					if(os == os_andriod){
						window.magme.alert("提交失败！");
					}else if(os == os_ios){
						window.open("iosmessage:提交失败！");
					}	
				}else{
					if(rs.code == 200){
						if(os == os_andriod){
							window.magme.alert("提交成功！");
						}else if(os == os_ios){
							window.open("iosmessage:提交成功！");
						}
						$('.score-list li').unbind('click');
						$("#txAdvice").val('');
					}
				}
			}
		});
	}	
}
//礼品兑换
function giftsconvert(){
	var os_andriod = "ANDROID";
	var os_ios = "IOS";
	var os = $("#os").val();
	var uid = $("#uid").val();
	var appid = $("#appId").val();
	var muid = $("#muid").val();
	var version = $("#version").val();
	var giftId = $("#giftId").val();
	var data = {"userId":uid,"appId":appid,"muid":muid,"v":version,"giftId":giftId,"type":4};
	$.ajax({
		url : SystemProp.appServerUrl + "/app/looker-if!addGold.action",
		type : "post",
		async : false,
		data : data,
		dataType : 'json',
		success : function (rs){
			if(rs != null && rs.code == 200){
				var gold = rs.data.gold;
				$("#goldp").html("您有"+gold+"个<span class='iconMoney'>金币</span>");
				if(os == os_andriod){
					window.magme.showDialog("兑换成功,我们将尽快处理请求。");
					window.magme.signIn(gold);
				}else if(os == os_ios){
					window.open("iosdialog:"+"兑换成功,我们将尽快处理请求。");
					window.open("iosmethod:signIn"+gold);
				}
				
			}else{
				if(os == os_andriod){
					window.magme.showDialog(rs.message);
				}else if(os == os_ios){
					window.open("iosdialog:"+rs.message);
				}
			}
		}
	});
}