$(document).ready(function(){
	var isFirMess = getCookie("isFirMess") || '1';
	if(isFirMess === '1'){
		alert("提示：关闭本窗口或刷新本页面将取消上传期刊！");
		var expires_date = new Date(new Date().getTime() + (1*24*60*60*1000));
		setCookie("isFirMess",'0', expires_date);
	}
	//use to upload the file's bar-------------------------------------
	function getUUID(){
		 uuid = "";
		 for (i = 0; i < 32; i++) {
		  uuid += Math.floor(Math.random() * 16).toString(16);
		 }
		 return uuid;
	}
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	
	//期刊的联动选择
	var myDate = new Date();
	$("#year").val(myDate.getFullYear());
	$("#month").val((myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1))
	var removeDate = function(year){
		var febDays = 28;
		if(year=='2012'){
			febDays = 29;
		}
		$("#selectDateBox a").filter(function(i){
			return i>(febDays-1) && i<31;
		}).hide();
	};
	$("#day").unbind("focus").bind('focus',function(){
		var year = $("#year").val();
		var month = $("#month").val();
		$("#selectDateBox a").show();
		switch(month){
			case '04':
			case '06':
			case '09':
			case '11':
				$("#selectDateBox a").eq(30).hide();
				break;
			case '02':
				removeDate(year);
				break;
		}
		$("#selectDateBox").slideDown(400);
	});
	$("#day").blur(function(){
		setTimeout(function(){$("#selectDateBox").slideUp(200)},100);
	});
	$("#selectDateBox a").unbind('click').bind('click',function(){
		$("#day").val($(this).html());
	})
	
	//时间控件
	$("#publishDate").val(nowDate);
	datePickerFun($("#publishDate"),nowDate);
	//input_datepicker------------------------------------
	function datePickerFun($dateInput,date){
		if(!date) date = nowDate;
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
//				$dateInput.DatePickerHide();
			}
		});
	}
	
	//uploadIssue-------------------------------------
	$("#uploadIssueSubmit").click(function(e){
		e.preventDefault();
		$(".inputError").removeClass("inputError");
		$("#uploadIssueForm").submit();
	});
	$("#issueFile").change(function(){
		$("#fileName").val($(this).val()); 
	});
	$("#uploadIssueForm").submit(function(){
		var uuid = getUUID();
		if(!checkParameter()) return false;
		var postUrl=SystemProp.appServerUrl+"/publish/issue-upload!addIssueProcess.action?X-Progress-ID="+uuid+"&random="+Math.random();
		if($("#pubType").val()==3){//互动html杂志
			postUrl=SystemProp.appServerUrl+"/publish/magic-editor-upload.action?X-Progress-ID="+uuid+"&random="+Math.random();
		}
		
		$.ajaxFileUpload(
            {
                url : postUrl,
                secureuri : false,
                data : form2object('uploadIssueForm'),
                fileElementId : ["issueFile","smallPic1","smallPic2"],
                content : $("#uploadIssueForm"),
                dataType : "json",
                async : true,
                type : 'POST',
                success : function(rs){
                	if(!rs) return;
                	if(rs.code != 200){
                		alert(rs.message);
                	}else{
                		//modified by xw
                		alert("杂志上传成功啦！", function(){
                			location.reload();		
                		});               		
                	}
                },
                //服务器响应失败处理函数
                error : function (data, status, e) {
                	//modified by xw
                	alert("亲，上传期刊失败啦！", function(){
                    	$("#bar-inner").css("width",0+"%");
        				$("#persent").html(0+"%");
        				$("#current_size").html(0+"M")
        				$("#max_size").html(0+"M");               		               		
                	});
                }
            }
        );
		progressBarShow(uuid);
		return false;
	});
	//----------------------------------checkParameter-----------------------------------------------
	function checkParameter(){
		issueInfo = form2object("uploadIssueForm");
		var $form = $("#uploadIssueForm");
		if(issueInfo.publicationId == '0'){
			$("#publicationId",$form).addClass("inputError");
			alert("*请选择杂志名称");
			return false;
		}
		var issueFile = issueInfo.issueFile;
		if(!issueFile){
			$("#issueFile",$form).addClass("inputError");
			alert("*上传期刊不能为空");
			return false;
		}
		var names = issueFile.split(".");
		//互动杂志
		if($("#pubType").val()==3)
		{
			if(!($.trim(names[names.length-1]).toLowerCase() == "zip"||$.trim(names[names.length-1]).toLowerCase() == "mpres")){
				$("#issueFile",$form).addClass("inputError");
				alert("*请检查上传期刊类型是否是zip格式");
				return false;
			}
//			var smallPic1 = issueInfo.smallPic1;
//			if(!smallPic1){
//				$("#smallPic1",$form).addClass("inputError");
//				alert("*缩略图(pad)不能为空");
//				return false;
//			}
//			var smallPic2 = issueInfo.smallPic2;
//			if(!smallPic2){
//				$("#smallPic2",$form).addClass("inputError");
//				alert("*缩略图(phone)不能为空");
//				return false;
//			}
		}
		//PDF杂志
		else
		{
			if($.trim(names[names.length-1]).toLowerCase() !== "pdf"){
				$("#issueFile",$form).addClass("inputError");
				alert("*请检查上传期刊类型是否是pdf格式");
				return false;
			}
		}
		
		if(!issueInfo.keyword){
			$("input[name='keyword']",$form).addClass("inputError");
			alert("*关键词不能为空");
			return false;
		}
		var publishDate = issueInfo.publishDate;
		var reg = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/ ;
		if(!publishDate){
			$("#publishDate",$form).addClass("inputError");
			alert("*时间不能为空");
			return false;
		}
		if($.trim(publishDate) && !reg.test(publishDate)){
			$("#publishDate",$form).addClass("inputError");
			alert("*时间格式错误em：1985-03-21");
			return false;
		}
		return true;
	}
	//---------------------------progress-bar-----------------------------------------
	function progressBarShow(uuid){
		var progressBar = $("#progress-bar");
		var isAjax = false;		
		var i = setInterval(function() {
			var callback = function(result){
				isAjax = false;
				var data = result.data;
				if(!result){
					clearInterval(i);
					return;
				}
				data.percent=((data.uploaded*100)/(data.total*1)).toFixed(2);
				if(data.uploaded*1>10000){
					data.uploaded=(data.uploaded*1)/(1024*1024);
				}
				if(data.total*1>10000){
					data.total=(data.total*1)/(1024*1024);
				}
				$("#progress-bar").attr("title","已经上传了"+data.percent+"%");
				$("#bar-inner").css("width",data.percent+"%");
				$("#persent").html(data.percent+"%");
				var current_size = (data.uploaded*1).toFixed(2);
				var max_size = (data.total*1).toFixed(2);
				
				$("#current_size").html(current_size+"M")
				$("#max_size").html(max_size+"M");
				if(data.percent == 100){
					clearInterval(i);
				}
			};
			if(!isAjax){
				isAjax = true;
		        $.ajax({
		        	url:SystemProp.appServerUrl +"/publish/issue-upload-info.action?X-Progress-ID="+uuid+"&random="+Math.random(),
		        	async : true,
					type:"GET",
					success:callback
		        })
			}
	    }, 1000);
		//modified by xw
		progressBar.css({"display":"block"});
	}
	
	//-----------------------------------cancelUpload---------------------------------------------
	$("#cancelUpload").unbind('click').click(function(e){
		e.preventDefault();
		location.reload();
	});
});