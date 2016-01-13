$(document).ready(function() {
	$(".scroll-pane").jScrollPane();
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	
	date = $("#birthdate").val();
	if(!date) date = nowDate;
	$dateInput = $("#birthdate");
	$dateInput.DatePicker({
		format:'Y-m-d',
		date: date,
		current: date,
		starts: 0,
		position: 'bottom',
		onBeforeShow: function(){
			$dateInput.DatePickerSetDate($dateInput.val()||date, true);
		},
		onChange: function(formated, dates){
			$dateInput.val(formated);
//			$dateInput.DatePickerHide();
		}
	});
	

	if($("#province").length>0 && $("#city").length>0){
		loadProvinceAndCity();
	}
	
	function loadProvinceAndCity(){
		var province = $("#province").attr("province");
		var city = $("#city").attr("city");
		selectProvinceOrCity($("#province"),province);
		loadCity($("#province").val(),$("#city"));
		$("#province").live('change',function(){
			var province = $(this).val();
			changeProvince($(this),$("#city"));
		});
		setTimeout(function(){selectProvinceOrCity($("#city"),city);},0);
	}
	//selectProvinceOrCity Function-------------------------------------
	function selectProvinceOrCity (select,value){
		select.find("option[value='"+value+"']").attr("selected",true);
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
	
	$("#submitEditUserInfoForm").click(function(){modify_userInfo_submit();});
	
	function modify_userInfo_submit(){
		var content=$("#editUserInfoForm");
		var url = SystemProp.appServerUrl + "/user-update!editInfoJson.action";
		var user = form2object('editUserInfoForm');
		var tipError = $("#tipError",content).hide();
		var birthdate = $("#birthdate",content).val();
		var oldPassword = $("#oldPassword",content).val();
		var password = $("#password",content).val();
		var password2 = $("#password2",content).val();
		var message = "";

		var email = $("#email",content).val();
		if(email == '' || email.indexOf('@') == -1){
			$("#editUserInfoForm").find("#email").addClass("inputError");
			tipError.html("邮箱必须正确填写!").show();
			return;
		}
				
		var reg = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/ ;
		if($.trim(birthdate) && !reg.test(birthdate)){
			$("#editUserInfoForm").find("#birthdate").addClass("inputError");
			tipError.html("生日的格式错误em：1985-03-21").show();
			return;
		}
		if(!!$.trim(oldPassword) && password !== password2){
			$("#editUserInfoForm").find("#password2").addClass("inputError");
			tipError.html("确认密码错误").show(); 
			return;
		}
		var callback = function(result){
			if(!result) return;
			
			var tipError = $("#tipError",content);
			var code = result.code;
			var data = result.data;
			if(code==200){
				alert("保存用户信息成功");
				$(".inputError").removeClass("inputError");
			}else{
				if(data.email){
					$("#editUserInfoForm").find("#email").addClass("inputError");
					tipError.html(data.email).show();
				}else if(data.oldPassword){
					$("#editUserInfoForm").find("#oldPassword").addClass("inputError");
					tipError.html(data.oldPassword).show();
				}else{
					alert(result.message);
				}
			}
		};
		$.ajax({
			url:url,
			type : "POST",
			data : user,
			success: callback
		});
	}
	
	var content = $("#fancybox-content");
	var dialogClose = $.fancybox.close;		
	//----------------------------change Avatar 修改头像--------------------------------
	$("#changeAvatar").unbind("click").live("click",function(){alert(0);
		$.fancybox.close = function(){
			$("#avatar",content).imgAreaSelect({remove:true});
			dialogClose();
		};
		changeAvatar($(this));
	});
	
	//----------------------------changeAvatarFun---------------------------------------------
	function changeAvatar(obj){
		var infoDialog = $("#uploadUserAvatarDialog"),
			content = $("#fancybox-content");
		
		//生成dialog
		infoDialog.fancybox();
		
		changeAvatar_button_bind();
		
		//米客中心的头像
		$("#avatar",content).attr("src",$("#userAvatar").attr("src"));
		//上传头像绑定事件
		var avatarFile = $("#avatarFile",content);
		if ($.browser.msie){
		    // IE suspends timeouts until after the file dialog closes
			avatarFile.live('click change',function(event){
		        setTimeout(function(){
		        	ajaxFileUpload();
		        }, 1);
		    });
		}else{
		    // All other browsers behave
			$("#avatarFile",content).live("change",ajaxFileUpload);
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
	
	//---------------------------ajaxFileUpload_Fun--------------------------------------
	function ajaxFileUpload(){
    	var fileUrl = $("#avatarFile",content).val();
    	var fileData = $("#avatar",content).data("fileData");
    	if(fileUrl !="" && (!fileData || fileData != fileUrl) ){
    		$("#avatar",content).data("fileData",fileUrl);
    	}else{
    		return;
    	}
    	var successFun = function(rs, status){
    		if(rs.code!=200){
    			alert(rs.message);
    			return;
    		}else{
    			var src = SystemProp.profileServerUrlTmp+rs.data.tempAvatarUrl+"?ts="+new Date().getTime();
            	var avatar = $("#avatar",content);
            	avatar.attr("src",src);
            	$("#avatarBox").removeClass("hide");
            	if($("#uploadBtn").hasClass("btnOB")){
                	$("#uploadBtn").removeClass("btnOB").addClass("btnOS");
                	$("#uploadBtn").find("span").html("重新上传头像");
            	}
            	$("#avatarFileName",content).val(rs.data.avatarFileName);
            	
            	//---enhancedImageFun  in  global.js---------------------
            	enhancedImage(src,function(img){
            		var imgWidth = img.width;
            		var imgHeight = img.height;
            		var showWidth = avatar.width();
                	var showHeight = avatar.height();
            		avatar.imgAreaSelect({
            			aspectRatio: "1:1",
            			x1:5,y1:5,x2:105,y2:105,
            			handles: true,
            			onSelectEnd: function (img, selection) {
                			$("input[name=x]",content).val(selection.x1 * imgWidth / showWidth);
                			$("input[name=y]",content).val(selection.y1 * imgHeight /showHeight);
                			$("input[name=width]",content).val(selection.width * imgWidth / showWidth);
                			$("input[name=height]",content).val(selection.height * imgHeight /showHeight);            
                		}
            		});
            	});
            }
    	};
        $.ajaxFileUpload(
            {
                url:SystemProp.appServerUrl+"/user-update!uploadAvatarJson.action",//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:"avatarFile",//文件上传空间的id属性  <input type="file" id="file" name="file" />
                content:content,
                dataType: "json",//返回值类型 一般设置为json
                success: successFun,
                //服务器响应失败处理函数
                error: function (data, status, e) {
                    return;
                }
            }
        );
    }
	//---------------------------changeAvatar_button-----------------------------
	function changeAvatar_button_bind(){
		var callback = function(result){
			if(!result) return;
			var message ;
			var tipError = $("#tipError",content);
			if(result.code != 200){
				tipError.show().html(result.message);
			}else{
				$("#avatar",content).imgAreaSelect({remove:true});
				$.fancybox.close();
				
				//对页面的用户头像修改
				$("#userAvatar").attr("src",SystemProp.profileServerUrl+result.data.user.avatar+"?ts="+new Date().getTime());
			}
		};
		
		$("#submit",content).click(function(){
			var url = SystemProp.appServerUrl + "/user-update!saveAvatarJson.action";
			var data = form2object('editAvatarForm');
			if( data.height==0 || data.width==0 ){
				data.x = 0;
				data.y = 0;
			}
			$.ajax({
				url:url,
				type : "POST",
				data : data,
				success: callback
			});
		});
		
		$("#cancel",content).click(function(){$.fancybox.close();});
	}	  
});
