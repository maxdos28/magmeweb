//﻿﻿var jiathis_config = {
//    boldNum:0,
//    siteNum:14,
//    showClose:false,
//    sm:"tsina,tqq,qzone,kaixin001,renren,douban,t163,tsohu",
//    imageWidth:26,
//    url:"http://www.magme.com",
//    title:"看杂志 ·上麦米网",
//    pic:"http://www.magme.com/images/logo.gif",
//    data_track_clickback:true,
//    appkey:{
//        "tsina":"3515375224",
//        "tqq":"a91806d1da344d89a32405cbeac99df6"
//    },
//    ralateuid:{
//        "tsina":"2173428060"
//    }
//};

	function getUUID(){
		 uuid = "";
		 for (i = 0; i < 32; i++) {
		  uuid += Math.floor(Math.random() * 16).toString(16);
		 }
		 return uuid;
	}

	function qqLogin(){
		var url=SystemProp.domain + "/qq/o-auth!request.action";
		url+="?callbackUrl="+encodeURIComponent(window.location.href);
		var ret = window.open(url,"qq登录","location=no,status=no");
	}
	
	function sinaLogin(){
		
		WB2.login(function(){
			WB2.anyWhere(function(W){
			    // 验证当前用户身份是否合法
			W.parseCMD("/account/verify_credentials.json", function(o, bStatus){
			    if(bStatus == true) {
			    	window.open(SystemProp.domain +"/third/third-login!sina.action?id="+o.id+"&name="+encodeURIComponent(o.screen_name)+"&imageUrl="+encodeURIComponent(o.profile_image_url)+"&gender="+o.gender+"&address="+encodeURIComponent(o.location));
			    }
			},{
				source: SystemProp.sinaAppKey
			},{
			    method: 'post'
			});
		});

			
//			//alert(SystemProp.sinaAppKey);
//			 WB.client.parseCMD(
//		                "/account/verify_credentials.json",
//		                 function(o, bStatus) {
//		                    if(bStatus == true){
//		                    	window.open(SystemProp.domain +"/third/third-login!sina.action?id="+o.id+"&name="+o.screen_name+"&imageUrl="+o.profile_image_url+"&gender="+o.gender+"&address="+o.location);
//		                    }
//		                    else{
//		                    	alert("error");
//		                    }
//		                },
//		                {
//		                    source: SystemProp.sinaAppKey
//		                },
//		                {
//		                    method: 'post'
//		                }
//		            );
			});
	}		
	
	function renrenLogin(){
		var url="https://graph.renren.com/oauth/authorize";
		url+="?client_id="+SystemProp.rrAppid+"&response_type="+SystemProp.rrResponseType+"&display=page&redirect_uri="+SystemProp.domain+SystemProp.rrRedirectUri;
		var ret = window.open(url,"人人账号登录","location=no,status=no");
	}
	
	function kaixinLogin(){
		var url="http://api.kaixin001.com/oauth2/authorize";
		url+="?client_id="+SystemProp.kxApiKey+"&response_type="+SystemProp.kxResponseType+"&redirect_uri="+SystemProp.domain+SystemProp.kxRedirectUri;
		var ret = window.open(url,"开心账号登录","location=no,status=no");
	}

	function baiduLogin(){
		baidu.require('connect', function(connect){
		    connect.init(SystemProp.baiduApiKey);
		    	connect.login(function(info){
		    	    connect.api({
		    	        url: '/passport/users/getLoggedInUser',
		    	        onsuccess: function(o){
		    	        	window.open(SystemProp.domain +"/third/third-login!baidu.action?id="+o.uid+"&name="+encodeURIComponent(o.uname)+"&imageUrl="+encodeURIComponent("http://himg.bdimg.com/sys/portrait/item/"+o.portrait+".jpg"));
		    	        },
		    	        onnotlogin: function(){
		    	         	//alert('login first!');
		    	        },
		    	        params:{
		    	        }
		    	    });
		    	});
		    });
	}
	
	
$(function(){	
	
	//将该字符串反序排列
	String.prototype.reverse = function(){
		var aStr="";
		for(var i=this.length-1;i>=0;i--){
			aStr=aStr.concat(this.charAt(i));
		}
		return aStr;
	};
	//head_user_ajax-------------------------------------------------------
	$(document).ready(function(){
		getUserAjax();
	});
	

	
	//loginqq----------------------------------------------------------------
	$("a[name='loginqq']").live('click',function(e){
		e.preventDefault();
		qqLogin();
	});
	$("a[name='loginSina']").live('click',function(e){
		e.preventDefault();
		sinaLogin();
	});		
	$("a[name='loginRenren']").live('click',function(e){
		e.preventDefault();
		renrenLogin();
	});
	$("a[name='loginKaixin']").live('click',function(e){
		e.preventDefault();
		kaixinLogin();
	});
	$("a[name='loginBaidu']").live('click',function(e){
		e.preventDefault();
		baiduLogin();
	});	
	//menu-------------------------------------------------------------------------------//
	$("#homePage").live('click',function(e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/index.html";
	});
	$("#publisherCenter").live('click',function(e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/publish/pcenter-publisher.action?random="+Math.random();
	});
	$("#accountCenter").live('click',function(e){
		e.preventDefault();
		var isLogin = $(this).attr("islogin");
		if(isLogin === 'true'){
			window.location.href = SystemProp.appServerUrl+"/user!center.action?random="+Math.random();
		}else{
			if($("#embedSwf1,#embedSwf2").length>0){
				gotoLogin();
				return;
			}
			alert("请您先登录，才能使用该功能",function(){
				gotoLogin();
			});
		}
	});
	$("#kanmi").live('click',function(e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/kanmi.html";
	});
	$("#tagWallPage").live('click',function(e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/tagWall.html?begin=0&size="+30;
	});
	
//	$("#eventPage").live('click',function(e){
//		e.preventDefault();
//		window.location.href = SystemProp.appServerUrl+"/event.html?eventCode=20110720";
//	});
	
	//"tr" add double color & "tr" add hover style--------------------------------------------//
	var $JQtableBg = $("table.JQtableBg tbody tr:odd");
	var $JQtableHover = $("table.JQtableBg tbody tr");
	$JQtableBg.addClass('bgColorTable');
	$JQtableHover.live("mouseover",function(){
	$(this).addClass("bgTrHover");
	});
	$JQtableHover.live("mouseout",function(){
		$(this).removeClass("bgTrHover");
	});
	function addJQtableBg(element) {
		element.addClass('bgColorTable');
	};
	//"td" add class name---------------------------------------------------------------------//
	var tableID=1;
	var tdID =1;
	var tableNum = $('table.table').size();
	for(tableID;tableID<=tableNum;tableID++) {
		$('table.table').get(tableID-1).lang='table'+tableID;
		var tdNum = $('table.table[lang*=table'+tableID+'] tr:first td').size();
		for(tdID; tdID <= tdNum; tdID++) {
			$('table.table tr td:nth-child('+tdID+')').addClass('t'+tdID);
		};
	};
	//for tipMsg
	$(".tipMsg").append("<a class='close'></a>");
	$(".tipMsg a.close").live("click",function(){
		var tipobj = $(this).parent(".tipMsg");
		tipobj.fadeOut(300);
		setTimeout(function(){tipobj.remove()},300);
	});
	//for popWindow--------------------------------------------------------------------------//	
	var lockPop=0;
	var $popWindow= $(".conTabWall .conBody .popWindow");
	$(".conTabWall .conBody li").hover(
	function(){
		lockPop=0;
		$popWindow.find("sub").removeClass("b");
		var currentLi=$(this).index();
		var currentLine=Math.floor(currentLi/4);
		if(currentLine>2){$popWindow.find("sub").addClass("b")}
		if(lockPop==0){
			$popWindow.find("sub").animate({left:(currentLi-currentLine*4)*72+31},150);
			if(currentLine>2){
				if($.browser.version=="6.0" && !$.browser.mozilla){
					$popWindow.css({top:"auto",bottom:(5-currentLine)*72-4});
				}else{
					$popWindow.css({top:"auto",bottom:(5-currentLine)*72-3});
				}
			}else{
				$popWindow.css({top:(currentLine+1)*72+4});
			}
			
			var $img= $popWindow.find("img");
			$popWindow.find("img").attr("src",$(this).attr("bimg"));
			var img = $img.get(0);
			var imgHeight = 0;
			var imgWidth = 0;
			if(img.complete){
				// get img from the cache;
				imgHeight = $(img).height();
				imgWidth = $(img).width();
			}else{
				// first time loading the img
				img.onload =function(){
					imgHeight = $(img).height();
					imgWidth = $(img).width();
				};
			};
            
			if(imgHeight>120){
				var ratio = imgWidth/imgHeight;
				var newWidth = 120*ratio;
				$(img).height(120);
				$(img).width(newWidth);
			}

			$popWindow.find("a").attr("href",$(this).find("a").attr("href"));
			$popWindow.find("strong").text($(this).attr("name"));
			$popWindow.find("p").text($(this).attr("desc"));
			$popWindow.show();	
		}
	},function(){
		lockPop=1;
		setTimeout(function(){
			if(lockPop==1){
				$popWindow.fadeOut(300);
			}	
		},1000);
	});
	$(".popWindow, .popWindow *").mouseover(function(){
		lockPop=0;
		$(this).show();
	});
	
	//for sns & publisher conSubMenu--------------------------------------------------------------------------//
	var $umItem = $(".conSubMenu>ul>li>a");
	var $umItemItem = $(".conSubMenu>ul>li>ul>li>a");
	var umLock = 0;
	var vmTime = 300;
	$umItem.click(function(){
		if(umLock==0){
			umLock=1;
			if(!$(this).parent().hasClass("current")){
				$(this).parent().addClass("current").find("ul").slideDown(vmTime);
				$(this).parent().siblings().removeClass("current").find("ul").slideUp(vmTime);
			}else if($(this).parent().hasClass("current")){
				$(this).parent().removeClass("current");
				$(this).next("ul").slideUp(vmTime);
			}
			$(this).parent().siblings().find("ul>li").removeClass("current");
			if($(this).next("ul").length==0){
				$(this).parent().siblings().find("ul>li").removeClass("current");
			}else{
				$(this).next("ul").find("li").eq(0).addClass("current").find("a").click();
			}
			setTimeout(function(){umLock=0},vmTime);
		}
	});
	$umItemItem.click(function(){
		$(this).parent().addClass("current");
		$(this).parent().siblings().removeClass("current");
		$(this).parent().parent().parent().siblings().find("ul>li").removeClass("current");
	});
	
	//for searchBar Drowdown
	var $searchSelect = $(".header .search div");
	$searchSelect.hover(
	function(){
		$(this).find("strong").addClass("hover")
		$(this).find("p").show();
	},
	function(){
		$(this).find("strong").removeClass("hover")
		$(this).find("p").hide();
	});
	$searchSelect.find("p span").click(function(){
		$(this).parent("p").hide();
		var tips = $(this).attr("tips");
		$searchSelect.find("strong").removeClass("hover").html($(this).html());
		var searchWord = $(".search input.in").attr("tips",tips);
		var value = searchWord.val();
		if(value=='输入您想看的杂志'||value=='输入您要查找的评论'||value=='输入您要查找的用户'){
			searchWord.val(tips);
		}
		
	});
	//for userBar Drop--------------------------------------------------------------------------//
	var $userBarLi = $("#login ,#register");
	//登入注册展开
	$userBarLi.click(function(e){
		e.preventDefault();
		userBarLiClick(this);
	});
	$("#newUserReg").click(function(){
		$("#register").click();
	})
	//userBarLi 展开事件
	function userBarLiClick (obj){
		var li = $(obj).parent();
		if(!li.hasClass("current")){
			$userBarLi.parent().removeClass("current");
			li.addClass("current");
//			li.find("#authcode").val('验证码');
		}else{
			li.removeClass("current");
		}
//		$(li).find("input:visible").eq(0).focus();
		$(li).find("form").keyup(function(event){
			event.stopPropagation();
			if (event.keyCode == '13') {
				event.preventDefault();
				if($(this).attr("id") == 'logInForm'){
					$("#signIn",$(this)).click();
				}else{
					$("#submit",$(this)).click();	
				}
		    }
		});
	}
	
	//for showBookBar--------------------------------------------------------------------------//
	var $showBar = $(".showBar");
	var $bookBar = $showBar.find(".bookBar");
	var bookBarLock=0;
	var bookBarHeight;
	$showBar.live('mouseover',function(){
		if(!$(this).hasClass("doing") && !$(this).hasClass("delete")){
			$(this).find(".bookBar").show();
			var mgzNameLength = $(this).find("span").html().replace(/[\u4E00-\u9FA5]/g,"00").length;
		}
	});
	$showBar.live('mouseout',function(){
		$(this).find(".bookBar").hide();
	});
	//for BookBarNameLength--------------------------------------------------------------------------//
//	var $nameBar = $(".showBar").not(".bigShow .showBar",".conMagezineShow .showBar",".conLineZone .showBar");
//	var mgzNameLength = [];
//	$nameBar.each(function(i){
//		mgzNameLength[i] = $(this).find("span em").html().replace(/[\u4E00-\u9FA5]/g,"00").length;
//		$(this).find("span em").width(mgzNameLength[i]*6);
//		if(mgzNameLength[i]>18){
//			$(this).find("span em").animate({left:-(mgzNameLength[i]-18)*6},(mgzNameLength[i]-18)*250);
//		}
//	});
//	
	//for getAuthcode ----------------------------------------------------------------------//
	$("input[code='getcode']").live('focusin',function(){
		var div = $(this).parents("div[code='getcode']").eq(0);
		var img = div.find("img").eq(0);
		if($(this).val() == "" || $(this).val() == '验证码'){
			var src = img.attr("src");
			if(src.indexOf("images/code.gif") !== -1){
				getcode(img);
			}
		}
	});
	$("a[name='getAuthcode']").unbind('click').live('click',function(e){
		e.preventDefault();
		var img = $(this).parent().find("img").eq(0);
		getcode(img);
	});
	//register ------------------------------------------------------------------------------//
	$("#userName,#email",$("#registerForm")).bind('blur',function(){
		var elementData = $(this).data("elementData");
		var value = $(this).val();
		if(!elementData || elementData != value){
			$(this).data("elementData",value);
			checkNameOrEmail($(this),$("#registerForm"));
		}
	});
	
	
	
	$("#submit",$("#registerForm")).data("isSubmit",true).unbind('click').live('click',function(){
		registerSubmit( $(this),$("#registerForm") );
	});
	
	//login-------------------------------------------------------------------------------------------//
	$("#loginMagme,a[name='loginMagme']").live('click',function(){
		if(!$("#userBar").find(".l2").hasClass('current')){
			gotoLogin();
		}
	});
	$("#logInForm").submit(function(){
		loginFun();
		return false;
	});
	$("#signIn","#logInForm").unbind('click').live('click',function(){
		$("#logInForm").submit();
	});
	$("#searchForm a.btn").unbind('click').live('click',function(){
		var searchType = $("#searchForm .search div strong").text();
		switch(searchType)
		   {
		   case '杂志':
			 $("#searchForm input[name=searchType]").val('Publication');
		     break;
		   case '评论':
			 $("#searchForm input[name=searchType]").val('Comment');
		     break;
		   case '用户':
			 $("#searchForm input[name=searchType]").val('User');
		     break;
		   default:
		   	 $("#searchForm input[name=searchType]").val('Publication');
		   }
		$("#searchForm").attr("action",SystemProp.appServerUrl+"/search.action");
		$("#searchForm").submit();
	});
	 
	$("#searchForm input[name=queryStr]").unbind('keyup').live('keyup',function(e){
		if(e.keyCode == 13){
			$("#searchForm a.btn").click();
		}
	});
	
	function loginFun (){
		var logForm = $("#logInForm");
		var user = {};
		user.userName = $("#userName",logForm).val();
		user.password = $("#password",logForm).val();
		user.authcode = $("#authcode",logForm).val();
		var remember_login = $("#remember_login").attr("checked");
		
		var callback  = function(result){
			if(!result) return;
			var tipError = $(".tipsError",logForm);
			var data = result.data;
			var message = result.message;
			if(message === 'success'){
				setTimeout(function(){data && loginSuccess(data.user)},1000);
				message = "登入成功！";
				if("checked" == remember_login){
					setCookie("magemecnUserName",user.userName,new Date("December 31,2120"));
					setCookie("magemecnPassword",user.password,new Date("December 31,2120"));
				}
			}else{
				if(data && data.authcode){
					message = data.authcode;
				}
			}
			tipError.html(message).show();
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user!loginJson.action",
			type : "POST",
			data : user,
			success: callback
		});
	}
	
	//forgetPassword------------------------------------------------------------------------//
	function forgetPassword (e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/user-findpwd!toFindpwd.action?random="+Math.random();
	}
	$("#forgetPassword").unbind('click').live('click',forgetPassword);
	
	//登出
	$("#logout").bind("click",function(){
		var userBar = $("#userBar");
		var callback = function(){
			deleteCookie("magemecnUserName");
			deleteCookie("magemecnPassword");
			location.reload();
		}
		$.ajax({
			url : SystemProp.appServerUrl+"/user!logoutJson.action?random="+Math.random(),
			success: callback
		});
	});
	
	//add magezine title
	var $titleRead = "#magezineShow .magezineBox a,";
		$titleRead += ".kanmiCon .bigShow .doorList .item a,";
		$titleRead += ".kanmiCon .smallShow .magezineBox a,";
		$titleRead += ".deskShow .item a,";
		$titleRead += ".conLineZone .conBody .item a";
		
	$($titleRead).each(function(){
		var title = $(this).find(">span").eq(0).html();
		$(this).attr("title",title);
	});
	
	
	//for footer autoHeight
	var resizeTimer = null;
	$(window).bind("resize", function(){
		if(!resizeTimer){
			resizeTimer = setTimeout(function(){
				if($(".footerMini").length==0){
					fnSetFooterHeight();
				}
				resizeTimer = null;
			},300);
		}
	});
	
	//top10
	$("ol.top10").each(function(){
		var i=0;
		$(this).find("li").slice(0,3).addClass("top");
		$(this).find("li").each(function(){
			i++;
			$(this).prepend("<span>"+i+"</span>");
		});
		
	});
	//userInfo-province-city----------------------------------------
	if($("#province").length>0 && $("#city").length>0){
		loadProvinceAndCity();
	}
	
	//issueRead------------------------------------------------------
	$("a[issueReadUrl]").live('click',function(e){
		e.preventDefault();
		var issueReadUrl = $(this).attr("issueReadUrl");
		window.open(issueReadUrl,"_magme");
	});
	
	//对bigShow 中的issue的绑定
	$("a[issueId]").find("[name='issueRead']").live('click',function(e){
		var issueA = $(this).parent("a").eq(0);
		var issueId = issueA.attr("issueId");
		
		e.preventDefault();
		window.open(SystemProp.appServerUrl+"/publish/mag-read.action?id="+issueId,"_magme");
	});
	// 订阅
	$("em[name='subscribe']").live('click',function(){
		var issue = $(this).parents("[issueId]").eq(0);
		var issueId = issue.attr("issueId");
		
		var isOk = addSubscribe(issueId,null);
		if(isOk){
			$(this).html(parseInt($(this).html()||0,10)+1);
		}
		return false;
	});
	//收藏
	$("em[name='collection']").live('click',function(){
		var issue = $(this).parents("[issueId]").eq(0);
		var issueId = issue.attr("issueId");
		
		var isOk = addCollection(issueId);
		if(isOk){
			$(this).html(parseInt($(this).html()||0,10)+1);
		}
		return false;
	});
});

function loadProvinceAndCity(){
	var province = $("#province").attr("province");
	var city = $("#city").attr("city");
	selectProvinceOrCity($("#province"),province);
	loadCity(province,$("#city"));
	selectProvinceOrCity($("#city"),city);
	$("#province").live('change',function(){
		var province = $(this).val();
		changeProvince($(this),$("#city"));
	});
}
//selectProvinceOrCity Function-------------------------------------
function selectProvinceOrCity (select,value){
	$(select).find("option[value='"+value+"']").attr("selected",true);
}
//province_change --------------------------------------------------
function changeProvince (provinceSelect,citySelect){
	provinceSelect = $(provinceSelect);
	citySelect = $(citySelect);
	var provinceData = provinceSelect.data("province");
	var province = provinceSelect.val();
	if(!provinceData || province !== provinceData){
		provinceSelect.data("province",province);
		loadCity(province,citySelect);
	}
}

//loadCityFun-------------------------------------------------------
function loadCity(province,citySelect){
	if(!province || citySelect.length ==0)return;
	var china = {
		"北京":["东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区","大兴区","怀柔区","平谷区","门头沟区","密云县","延庆县","其他地区"],
		"广东":["广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮","其他地区"],
		"上海":["南汇区","松江区","奉贤区","金山区","青浦区","黄浦区","南市区","卢湾区","徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","闵行区","宝山区","郊县","嘉定区","浦东新区","其他"],
		"天津":["和平区","河东区","河西区","南开区","河北区","红桥区","塘沽区","汉沽区","大港区","东丽区","西青区","津南区","北辰区","郊县","经济技术开发区","其他地区"],
		"重庆":["涪陵区","万州区","黔江地区","市区","江津市","其他地区"],
		"江苏":["太仓市","昆山市","常熟市","张家港市","泰州市","宿迁市","常州市","淮安市","连云港市","南京市","南通市","苏州市","无锡市","徐州市","盐城市","扬州市","镇江市","其他地区"],
		"浙江":["龙泉市","永康市","温州市","杭州市","宁波市","嘉兴市","绍兴市","余姚市","东阳市","丽水市","湖州市","金华市","台州市","舟山市","衢州市","其他地区"],
		"辽宁":["鞍山市","本溪市","朝阳市","大连市","丹东市","抚顺市","阜新市","锦西市","锦州市","辽阳市","盘锦市","沈阳市","铁岭市","营口市","其他地区"],
		"湖北":["荆州市","鄂州市","恩施土家族苗族自治州","黄石市","荆门市","武汉市","咸宁市","襄樊市","黄冈市","孝感市","宜昌市","仙桃市","随州市","广水市","十堰市","武穴市","潜江市","其他地区"],
		"四川":["阿坝藏族羌族自治州","巴中市","广安地区","资阳市","眉山市","峨眉山市","凉山彝族自治州","成都市","达川地区","德阳市","广元市","乐山市","甘孜藏族自治州","绵阳市","南充市","内江市","攀枝花市","遂宁市","雅安地区","宜宾地区","自贡市","泸州市","其他地区"],
		"陕西":["商洛市","安康地区","宝鸡市","汉中地区","铜川市","渭南市","西安市","咸阳市","延安地区","榆林地区","其他地区"],
		"河北":["保定市","保定地区","沧州市","承德市","邯郸市","衡水市","廊坊市","秦皇岛市","石家庄市","唐山市","邢台市","张家口市","其他地区"],
		"山西":["长治市","大同市","晋城市","临汾地区","忻州地区","太原市","阳泉市","运城市","朔州市","吕梁地区","晋中地区","其他地区"],
		"河南":["孟州市","安阳市","鹤壁市","焦作市","开封市","洛阳市","南阳市","平顶山市","三门峡市","商丘地区","新乡市","信阳市","许昌市","郑州市","周口地区","驻马店地区","漯河市","濮阳市","济源市","其他地区"],
		"吉林":["白山市","松原市","白城市","长春市","吉林市","辽源市","四平市","通化市","延边朝鲜族自治州","公主岭市","珲春市","其他地区"],
		"黑龙江":["建三江市","哈尔滨市","鹤岗市","大庆市","鸡西市","佳木斯市","牡丹江市","齐齐哈尔市","双鸭山市","绥化地区","伊春市","黑河市","七台河市","松花江地区","大兴安岭地区","其他地区"],
		"内蒙古":["呼伦贝尔盟","兴安盟","哲里木盟","锡林郭勒盟","乌兰察布盟","伊克昭盟","巴彦淖尔盟","阿拉善盟","临河市","包头市","赤峰市","呼和浩特市","乌海市","鄂尔多斯市","其它地区"],
		"山东":["蓬莱市","滨州市","德州市","东营市","菏泽地区","济南市","济宁市","莱芜市","聊城市","临沂市","青岛市","日照市","泰安市","威海市","潍坊市","烟台市","枣庄市","淄博市","其他地区"],
		"安徽":["安庆市","蚌埠市","巢湖地区","池州地区","滁州市","亳州市","合肥市","淮北市","淮南市","黄山市","六安地区","马鞍山市","宿州市","铜陵市","芜湖市","宣城地区","其他地区"],
		"福建":["福州市","龙岩市","南平市","莆田市","泉州市","三明市","厦门市","漳州市","石狮市","晋江市","建阳市","福安市","其他地区"],
		"湖南":["永州市","常德市","长沙市","郴州市","怀化地区","衡阳市","零陵地区","娄底地区","邵阳市","湘潭市","益阳市","岳阳市","株洲市","张家界市","湘西土家族苗族自治州","其他地区"],
		"广西":["防城港市","南宁地区","柳州地区","桂林地区","梧州地区","北海市","桂林市","河池地区","柳州市","南宁市","钦州市","梧州市","玉林地区","贵港市","其他地区"],
		"江西":["庐山市","抚州地区","赣州地区","吉安市","景德镇市","九江市","南昌市","萍乡市","上饶市","新余市","宜春地区","鹰潭市","其他地区"],
		"贵州":["贵阳市","六盘水市","黔东南苗族侗族自治州","黔西南布依族苗族自治州","黔南布依族苗族自治州","遵义地区","铜仁地区","毕节地区","安顺地区","其他地区"],
		"云南":["红河州","文山壮族苗族自治州","思茅市","西双版纳傣族自治州","德宏傣族景颇族自治州","丽江市","怒江傈僳族自治州","迪庆藏族自治州","临沧地区","保山地区","楚雄彝族自治州","大理市","东川市","昆明市","曲靖地区","玉溪地区","昭通地区","其他地区"],
		"西藏":["昌都地区","拉萨市","林芝地区","日喀则地区","山南地区","那曲地区","阿里地区","江达","巴青","仁布","索县","尼木","比如","措勤","堆龙德庆","洛隆","芒康","班戈","曲水","八宿","尼玛","贡嘎","丁青","扎囊","其他地区"],
		"海南":["琼海市","海口市","三亚市","东方市","文昌市","儋州市","其他地区"],
		"甘肃":["庆阳地区","陇南地区","敦煌市","白银市","定西地区","甘南藏族自治州","嘉峪关市","金昌市","酒泉地区","兰州市","临夏回族自治州","平凉地区","天水市","武威地区","张掖地区","其他地区"],
		"宁夏":["石嘴山市","银川市","固原地区","银南地区","其他地区"],
		"青海":["海东地区","黄南藏族自治州","果洛藏族自治州","海西蒙古族藏族自治州","海南藏族自治州","海北藏族自治州","玉树藏族自治州","西宁市","其他地区"],
		"新疆":["石河子市","博尔塔拉蒙古自治州","巴音郭楞蒙古自治州","克孜勒苏柯尔克孜自治州","伊犁哈萨克自治州","伊犁地区","塔城地区","阿克苏地区","阿勒泰地区","昌吉回族自治州","哈密地区","和田地区","喀什地区","克拉玛依市","吐鲁番地区","乌鲁木齐市","其他地区"],
		"香港":["中西区","湾仔区","东区","南区","深水埗区","油尖旺区","九龙城区","黄大仙区","观塘区","北区","大埔区","沙田区","西贡区","元朗区","屯门区","荃湾区","葵青区","离岛区","其他地区"],
		"澳门":["花地玛堂区","圣安多尼堂区","大堂区","望德堂区","风顺堂区","嘉模堂区","圣方济各堂区","路凼","其他地区"],
		"台湾":["台北市","高雄市","台北县","桃园县","新竹县","苗栗县","台中县","彰化县","南投县","云林县","嘉义县","台南县","高雄县","屏东县","宜兰县","花莲县","台东县","澎湖县","基隆市","新竹市","台中市","嘉义市","台南市","其他地区"],
		"海外":["海外"],
		"其他":[]
	};
	citySelect.empty();
	citySelect.append('<option value="不限">请选择城市</option>');
	var cities = china[province];
	if(!cities)return;
	for(var i=0;i<cities.length;i++){
		var city = cities[i];
		var option = '<option value="'+city+'">'+city+'</option>';
		citySelect.append(option);
	}
}

//function--------------------------------------------------------------------------------------------------------------------------

function fnSetFooterHeight(){
	var footerOffset=0;
	var $footer = $("body>.footer");
	var browserHeight = $(window).height();
	var mainHeight = $("body>.main").height();
	
	var headerHeight= $(".main>.header").height();
	var bodyHeight = $(".main>.body").height();
	var footerHeight = $(".main>.footer").height();
	browserHeight = $(window).height();
	if(mainHeight<browserHeight){
		if($footer.hasClass("footerDesk")){footerOffset=60}
		else if($footer.hasClass("footerLast")){footerOffset=14}
		else{footerOffset=20}
		$footer.css({height:browserHeight-mainHeight-footerOffset,minHeight:100});
	}
}
//isok is status 防止用户连续提交
function  registerSubmit (obj,register){
	var isOk = obj.data("isSubmit");
	if(isOk){
		obj.data("isSubmit",false);
		var message = "";
		var user = {};
		user.userName = $("#userName",register).val();
		user.email = $("#email",register).val();
		user.password = $("#password",register).val();
		user.password2 = $("#password2",register).val();
		user.authcode = $("#authcode",register).val();
		//确认密码错误将不提交数据库
		var tipError = $(".tipsError",register);
		
		if(user.password !== user.password2){
			message = "确认密码错误！";
			obj.data("isSubmit",true);
			tipError.html(message).show();
			return;
		}else{
			tipError.hide();
		}
		/*
		 * isSubmit是用于监听在注册过程中再次提交
		 * 
		 */
		$("#userBar .loading").show();
		var callback = function(result){
			obj.data("isSubmit",true);
			message = result.message;
			
			var code = result.code;
			var data = result.data;
			$("#userBar .loading").hide();
			if(data.authcode){
				message = data.authcode;
				tipError.html(message).show();
				return;
			}
			if(data.userName){
				message = data.userName;
				tipError.html(message).show();
				return;
			}
			if(data.email){
				message = data.email;
				tipError.html(message).show();
				return;
			}
			if(data.password){
				message = data.password;
				tipError.html(message).show();
				return;
			}
			tipError.html("注册成功").show();
			setTimeout(function(){
				loginSuccess(result.data.user);
				$("#login ,#register").parent().removeClass("current");
			},3000)
		};
		
		$.ajax({
			url : SystemProp.appServerUrl+"/user!registerJson.action",
			type : "POST",
			data : user,
			dataType : 'json',
			success: callback
		});
	}
}
//loginPubish----------------------------------------------
function loginPubish (user){
	var userBar = $("#userBar");
	
	$("#publisherCenter").show().prevAll().remove();
	$("#header_nickName").html(user.publishName);
	$(".l1,.l2",userBar).hide();
	$('.l4,.l0',userBar).show();
}

//checkName or Email
function checkNameOrEmail(obj,register){
	if(!obj) return;
	
	var tips = obj.attr('tips');
	var val = obj.val();
	if($.trim(val) == '' || val == null){
		tips && obj.css({color:'#ccc'}).val(tips);
		return;
	}
	//如果验证过的就不做验证
	var objData = obj.data("value");
	if(objData != val){
		obj.data("value",val);
	}else{
		return;
	}
	
	var tipError = $(".tipsError",register);
	var id = obj.attr("id");
	var callback = function(result){
		if(result.message === 'success'){
			if(id == 'userName'){
				$("#checkName",register).show().removeClass("tipsWrong").addClass("tipsRight");
			}else{
				$("#checkEmail",register).show().removeClass("tipsWrong").addClass("tipsRight");
			}
			tipError.hide();
		}else{
			if(id == 'userName'){
				$("#checkName",register).show().removeClass("tipsRight").addClass("tipsWrong");
			}else{
				$("#checkEmail",register).show().removeClass("tipsRight").addClass("tipsWrong");
			}
			tipError.html(result.data[id]).show();
		}
	};
	var userData = {};
	userData[id] = val;
	$.ajax({
		url : SystemProp.appServerUrl+"/user!validateUserJson.action",
		type : "POST",
		data : userData,
		success: callback
	});
}
//----addSubscribeFucntion-----------------------------------------------------
function addSubscribe(issueId,publicationId){
	var isOk = false;
	issueId = issueId ||"";
	publicationId  = publicationId || "";
	if(isNaN(issueId) || isNaN(publicationId)) return false;
	
	var callback = function(result){
		var code = result.code;
		var message = result["message"];
		if(code == 400){
			alert(message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code==500){
			alert(message);
		}else if(code == 200){
			alert("订阅成功！");
			if($("#conRecommend").length>0){
				var pos = $(".ctrl>.current").attr("pos");
				if(pos=='0'){
					$("a[name='favoriteMenu']").click();
				}else{
					$("a[name='subscribeMenu']").click();
				}
			}
			isOk = true;
		}
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/user-subscribe!addJson.action",
		type : "POST",
		async : false,
		dataType : "json",
		data : {"issueId":issueId,"publicationId":publicationId},
		success : callback
	});
	return isOk;
}

//----addCollectionFucntion-----------------------------------------------------
function addCollection(issueId){
	var isOk = false;
	if( isNaN(issueId) ) return isOk;
	var callback = function(result){
		var code = result.code;
		var message = result.message;
		if(code == 400){
			alert(message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code==500){
			alert(message);
		}else if(code == 200){
			alert("收藏成功！");
			if($("#conRecommend").length>0){
				var pos = $(".ctrl>.current").attr("pos");
				if(pos=='0'){
					$("a[name='favoriteMenu']").click();
				}else{
					$("a[name='subscribeMenu']").click();
				}
			}
			isOk = true;
		}
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/user-favorite!addJson.action",
		type : "POST",
		async : false,
		dataType : "json",
		data : {"issueId":issueId},
		success : callback
	});
	return isOk;
}
//function getcode
function getcode(img){
	clearCode();
	var src = SystemProp.appServerUrl + "/authcode.action?random=" + new Date().getTime();
	$(img).attr("src",src);
}
function clearCode(){
	$("img[code='getcode']").attr("src",SystemProp.appServerUrl+"/images/code.gif");
}
/* 
*get url value by given name 
*/
function getUrlValue(name){ 
	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
	if (r!=null) return unescape(r[2]); return null;
}
//To add cookie information to the HTTP header need to use the following Syntax:
// 
// document.cookie = "name=value; expires=date; path=path;domain=domain; secure";
//
// This function sets a client-side cookie as above.  Only first 2 parameters are required
// Rest of the parameters are optional. If no CookieExp value is set, cookie is a session cookie.
//******************************************************************************************
function setCookie(CookieName, CookieVal, CookieExp, CookiePath, CookieDomain, CookieSecure){
	CookiePath = '/';
	if(CookieVal){
		CookieVal = CookieVal.reverse();
	}
    var CookieText = escape(CookieName) + '=' + escape(CookieVal); //escape() : Encodes the String
    CookieText += (CookieExp ? '; EXPIRES=' + CookieExp.toGMTString() : '');
    CookieText += (CookiePath ? '; PATH=' + CookiePath : '');
    CookieText += (CookieDomain ? '; DOMAIN=' + CookieDomain : '');
    CookieText += (CookieSecure ? '; SECURE' : '');
    document.cookie = CookieText;
}

// This functions reads & returns the cookie value of the specified cookie (by cookie name) 
function getCookie(CookieName){
	    var CookieVal = null;
    if(document.cookie)	   //only if exists
    {
   	    var arr = document.cookie.split((escape(CookieName) + '=')); 
   	    if(arr.length >= 2)
   	    {
       	    var arr2 = arr[1].split(';');
   		    CookieVal  = unescape(arr2[0]); //unescape() : Decodes the String
   	    }
    }
    if(CookieVal!=null){
    	CookieVal = CookieVal.reverse();
    }
    return CookieVal;
}

// To delete a cookie, pass name of the cookie to be deleted
function deleteCookie(CookieName){
	var tmp = getCookie(CookieName);
    if(tmp) 
    { 
        setCookie(CookieName,tmp,(new Date(1))); //Used for Expire 
    }
}
//user not login----------------------------------------------------------------------
function gotoLogin(){
	if(!$(".l2").hasClass("current")){
		$("#login").click();
	}
}
//---------------------------预加载图片------------------------------------------------ 
/*
 * euthor: edward ,time: 11-06-14
 * description: get the img's width and height
 * src: img's src
 * onLoaded: callbackFunction
 */
function enhancedImage (src,onLoaded){
    var self = this;
    this.src = src;
    this.width = 0;
    this.height = 0;
    this.onLoaded = onLoaded;
    this.loaded = false;
    this.image = null;
    this.load = function(){
        if(this.loaded) return;
        this.image = new Image();
        this.image.src = this.src;
        function loadImage(){
            if(self.width != 0 && self.height != 0){
                clearInterval(interval);
                self.loaded = true;
                self.onLoaded(self);
            }
            self.width = self.image.width;
            self.height = self.image.height;
        }
        var interval = setInterval(loadImage,100);
    }
    this.load();
};
//判断时候有fancybox和lightbox 的遮挡层
function hasOverlay(){
	return ($("#fancybox-overlay").is(":visible")&& $("#fancybox-overlay").height()>30) || $(".eaMask").is(":visible");
}

//tagShare---------------------------------------------------------
//function tagShare(url,tagTitle,picUrl){
//	var jiathis = $("#jiathis");
//	var config = {
//		url: url,
//	    title: "看免费杂志上麦米网，标签标题:"+tagTitle+",杂志链接",
//	    pic: picUrl
//	};
//	
//	jiathis_config = $.extend({},jiathis_config,config);
//}

//footerMini----------------------------------------
function footerMini(){
	var $fVavItem = $(".footerMini .nav>ul>li");
	$(".footer").height(26).css("minHeight",26);
	$fVavItem.hover(
	function(){
		$(this).addClass("current").find("ul").show();
	},function(){
		$(this).removeClass("current").find("ul").hide();
	})
}

function getUserAjax(){
	$.ajax({
		url : SystemProp.appServerUrl+"/user!getReaderJson.action",
		type : "POST",
		async: false,
		success: function(result){
			if(!result) return;
			var data = result.data;
			var code = result.code;
			if(code==200){
				if(data && data.publisher){
					loginPubish(data.publisher);
					return;
				}
				if(data && data.user){
					loginSuccess(data.user);
					return;
				}
			}
			autoLogin();
		}
	});
}
//auotoLogin----------------------------------------------------------------------------
function autoLogin(){
	var userName = getCookie("magemecnUserName")||"",
		password = getCookie("magemecnPassword")||"";
	if(userName=="" || password=="") return;
	$.ajax({
		url : SystemProp.appServerUrl+"/user!autoLoginJson.action",
		type : "POST",
		data : {"userName":userName,"password":password},
		success: function(result){
			if(!result) return;
			var data = result.data;
			var code = result.code;
			if(code==200){
				setTimeout(function(){data && loginSuccess(data.user)},1000);
			}
		}
	});
}
//登入成功--------------------------------------------------
function loginSuccess (user){
	if(!user) return;
	var menu = $("#menu");
	$("#accountCenter").attr("islogin","true");
	$("#publisherCenter").remove();
	var userBar = $("#userBar");
	
	$("#header_nickName").html(user.nickName);
	
	$(".l1,.l2",userBar).hide();
	$('.l4,.l0',userBar).show();
	var newMessageNum = user.statsMap.newMessageNum;
	if(newMessageNum*1>0){
		$("#newMessageNum").show().html("("+user.statsMap.newMessageNum+")");
	}
	//index
	if($("#myAccount").length>0){
		$("#myAccount #userName").html(user.userName||"");
		$("#myAccount #friendsNum").html(user.friendsNum || 0);
		var src = user.avatar46 ? SystemProp.profileServerUrl+user.avatar46 : SystemProp.staticServerUrl+"/images/head46.gif";
		$("#myAccount #user_avatar").attr("src",src);
	}
	//index
	if($("#login_module").length > 0){
		$("#login_module").hide();
		$("#myAccount").show();
		$("#nickName").html(user.nickName);
		var messageNum = (user.statsMap && user.statsMap.newMessageNum)?user.statsMap.newMessageNum:0;
		$("#messageNum").html(messageNum+'条新消息');
	}
	//tagshow
	if( $("#conBigMgzShow").length>0){
		var tagCommentForm = $("#tagCommentForm");
		tagCommentForm.find("input[name='userName']").hide();
		tagCommentForm.find("input[tips='密码']").hide();
		tagCommentForm.find("a[name='toUserRegisterBtn']").hide();
	}
	//eventComment
	if($("form[name='eventCommentForm']").length>0){
		var eventCommentForm = $("form[name='eventCommentForm']");
		eventCommentForm.find("input[name='userName']").hide();
		eventCommentForm.find("input[tips='密码']").hide();
		eventCommentForm.find("a[name='toUserRegisterBtn']").hide();
	}
}
//第三方登陆成功后回调方法
function thirdBack(thirdType){
	getUserAjax();
	if(thirdType == 'true'){
		domInfo = createInfo();
		$(domInfo).appendTo($("body"));
		var infoDialog = $("#userInfoDialog2");
		infoDialog.fancybox();
		$("#submit2",infoDialog).click(function(e){e.preventDefault();modify_thirdUser_submit();});
		$("#cancel2",infoDialog).click(function(e){e.preventDefault();$.fancybox.close();});
	}
};

function modify_thirdUser_submit(){
	var url = SystemProp.appServerUrl + "/user-update!editInfoJson.action";
	var user = form2object('editForm');
	var email = $("#email",$("#userInfoDialog2")).val();
	if(email != '' && email.indexOf('@') == -1){
		//tipError.html("邮箱必须正确填写!").show();
		alert("邮箱必须正确填写!");
		return;
	}
	var callback = function(result){
		if(!result) return;
		var code = result.code;
		if(code == 200){
			var nickName = result.data.user.nickName;
			$("#header_nickName").html(nickName);
			$("#nickName").html(nickName);
			$.fancybox.close();
		}else{
			alert(result.message);
		}
	};
	$.ajax({
		url:url,
		type : "POST",
		data : user,
		success: callback
	});
}
function createInfo(){
	var nickName = $("#header_nickName").html()||'';
	var strDom = '<div id="userInfoDialog2" class="popContent popRegister" style="display:none;">'+
			'<div class="content"><fieldset><form id="editForm" method="post" action="" onsubmit="return false;">'+
			'<div><em class="title">昵称</em><em><input id="nickName" name="nickName" value="'+
			nickName+'" class="input g170" type="text" /></em></div><div><em class="title">邮箱</em>'+
			'<em><input id="email" name="email" class="input g170" type="text" />*</em></div><div>'+
			'<em class="title"></em><em ><a id="submit2" href="#" class="btnOS" >确定</a></em>'+
			'<em ><a id="cancel2" href="#" class="btnBS" >取消</a></em></div></form></fieldset></div></div>';
	return strDom;
}


