$(function(){
	//添加图片 picDivBtn picInputBtn picture
	//$("a[name='fileDel']").hide();
	$("#picInputBtn").live("click",function(){
		var i=0;
		$("div[name='addFileDivName']").each(function(){
			var csdDis = $(this).css('display');
			if(csdDis=='none'){
				i++;
			}
		});
		
		if(i==0){
			alert("最多添加5张");
			return;
		}else{
			$("div[name='addFileDivName']").each(function(){
				var aas = $(this).css('display');
				if(aas=='none'){
					$(this).css('display','block');
					return false;
				}
			});
		}
	});
	//删除对应图片上传input
	$("a[name='fileDel']").live("click",function(){
		var fid=$(this).attr('fid');
		$("a[name='fileSave'][fid="+fid+"]").show();
		$("a[name='fileDel'][fid="+fid+"]").hide();
		$("#addFileDiv"+fid).css('display','none');
		var exId = $("a[name='fileDel'][fid='"+fid+"']").attr("exid");
		if(exId){
			picDel(exId,fid);
		}else{
			alert("无法删除图片");
		}
	});
	
	//上传图片
	$("a[name='fileSave']").live("click",function(){
		var fid =  $(this).attr("fid");
		var url = $("#picture"+fid).val();
		if(url){
			$(this).hide();
			picSave(fid);
		}else{
			alert("请选择上传的图片");
		}
	});
	
	//跳转
	function jumpProject(id,url){
		url = SystemProp.kaiJieAppUrl+url;
		if(id){
			url= url+"?id="+id;
			window.location.href=url;
		}else{
			window.location.href=url;
		}
	}
	
	//选择模板
	$("a[name='selectModels']").live("click",function(){
		$("a[name='selectModels']").parent().removeClass("current");
		$(this).parent().addClass("current");
	});
	
	//上传图片
	function picSave(picFileId){
		var picFileVal="picture"+picFileId;
		var ajaxUrl = SystemProp.kaiJieAppUrl+"/sms/project-manage!picUpload.action";
		ajaxUrl += "?id="+encodeURIComponent($("#smsProjectId").val()||"");
		 $.ajaxFileUpload(
		            {
		                url:ajaxUrl,//用于文件上传的服务器端请求地址
		                secureuri:false,//一般设置为false
		                fileElementId:picFileVal,//文件上传空间的id属性  <input type="file" id="file" name="file" />
		                dataType: "json",//返回值类型 一般设置为json
		                success: function (rs,status)  //服务器成功响应处理函数
		                { 
		            		if(rs.code!=200){
		            			$("a[name='fileSave'][fid="+picFileId+"]").show();
		            			alert(rs.message);	
		            		}else{
		            			$("a[name='fileDel'][fid='"+picFileId+"']").attr("path",rs.data.path);
		            			$("a[name='fileDel'][fid='"+picFileId+"']").attr("exid",rs.data.id);
		            			$("a[name='fileDel'][fid="+picFileId+"]").show();
		            			
		            			showMobileComm();//预览里数值
						 		alert("上传成功");
		                    }
		                },
		                //服务器响应失败处理函数
		                error: function (data, status, e) {
		                	alert(status);
		                    return;
		                }
		            }
		);
	}
	//删除图片
	function picDel(exId,fid){
		var data = {};
		data.exId=exId;
		var callback = function(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				$("a[name='fileDel'][fid='"+fid+"']").attr("path","");
    			$("a[name='fileDel'][fid='"+fid+"']").attr("exid","");
    			showMobileComm();//预览里数值
			}
		}
		var url="/sms/project-manage!delPicByExid.action";
		ajaxComit(url,data,callback);
		
	}
	
	//第一步保存
	$("#firstSave").click(function(){
		var data={};
		var projectName = $("input[name='name']").val();
		if(projectName){
			data.id=$("#smsProjectId").val();
			data.name=projectName;
		}else{
			alert("项目名不能为空");
			return;
		}
		
		var ajaxUrl = "/sms/project-manage!stepFirstSave.action";
		var callback = function(result){
			if(result.code){
				if(result.code!=200){
					alert(result.message);
				}else{
					if(result.data.id){
						var tempId=result.data.id;
						var tempUrl="/sms/project-manage!stepSecond.action";
						jumpProject(tempId,tempUrl);
					}
				}
			}
		}
		ajaxComit(ajaxUrl,data,callback);
	});
	
	//第二步保存
	$("#secondSave").live("click",function(){
		var data={};
		var class1=$("ul .current").parent().attr("class");
		var class2=$("ul .current").attr('class');
		if(!class1){
			alert("请选择模板");return;
		}
		if(class2){
			class2=class2.substring(0,1);
		}else{
			alert("请选择模板");return;
		}
		var modelsClass = class1+"_"+class2;
		var pId= $("#smsProjectId").val();
		if(pId){
			data.id=pId;
			data.template=modelsClass;
		}else{
			alert("无法获取项目id");
			return;
		}
		
		var ajaxUrl = "/sms/project-manage!stepSecondSave.action";
		var callback = function(result){
			if(result.code){
				if(result.code!=200){
					alert(result.message);
				}else{
					if(result.data.id){
						var tempId=result.data.id;
						var tempUrl="/sms/project-manage!stepThird.action";
						jumpProject(tempId,tempUrl);
					}
				}
			}
		}
		ajaxComit(ajaxUrl,data,callback);
	});

	//第三步保存
	$("#thirdSave").live("click",function(){
		var data={};
		var smsContent = $("#smsContent").val();
		var webTitle = $("#webTitle").val();
		var webContent = $("#webContentHidden").val();
//		var webContent = KE.html('webContent');
		var videoUrl = $("#video").val();
		var pId= $("#smsProjectId").val();
		if(pId){
			data.id=pId;
			data.smsContent=smsContent;
			data.webTitle=webTitle;
			data.webContent=webContent;
			data.videoUrl=videoUrl;
		}else{
			alert("无法获取项目id");
			return;
		}
		var ajaxUrl = "/sms/project-manage!stepThirdSave.action";
		var callback = function(result){
			if(result.code){
				if(result.code!=200){
					alert(result.message);
				}else{
					if(result.data.id){
						var tempId=result.data.id;
						var tempUrl="/sms/project-manage!stepFourth.action";
						jumpProject(tempId,tempUrl);
					}
				}
			}
		}
		ajaxComit(ajaxUrl,data,callback);
	});
	
	//翻页临时保持通讯录组id
	$("input[name='smsPhoneBox']").live("click",function(){
		if($(this).attr("checked")=="checked"){
			oldVal+=$(this).val()+",";
		}else{
			var oldArray = oldVal.split(",");
			oldArray.splice($.inArray($(this).val(),oldArray),1);
			oldVal="";
			$.each(oldArray,function(key,val){
				if(val){
					oldVal+=val+",";
				}
			});
		}
	});
	 
	//第四步保存(电话群组ID)
	$("#fourthSave").live("click",function(){
		var data={};
		 var smsPhoneBox=oldVal;
		if(!smsPhoneBox){alert('请选择通讯录');return}
		var pId= $("#smsProjectId").val();
		if(pId){
			data.id=pId;
			data.phoneGroupId=smsPhoneBox;
		}else{
			alert("无法获取项目id");
			return;
		}
		
		var ajaxUrl = "/sms/project-manage!stepFourthSave.action";
		var callback = function(result){
			if(result.code){
				if(result.code!=200){
					alert(result.message);
				}else{
					if(result.data.id){
						var tempId=result.data.id;
						var tempUrl="/sms/project-manage!stepFifth.action";
						jumpProject(tempId,tempUrl);
					}
				}
			}
		}
		ajaxComit(ajaxUrl,data,callback);
	});
	
	
	//发送消息
	$("#fifthSave").live("click",function(){
		var data={};
		var pId= $("#smsProjectId").val();
		if(pId){
			data.id=pId;
		}else{
			alert("无法获取项目id");
			return;
		}
		var ajaxUrl = "/sms/project-manage!SendingMsgFifth.action";
		var callback = function(result){
			if(result.code){
				if(result.code!=200){
					//alert(result.message);
				}else{
					//发送成功 
					var ajaxUrlMsg = "/sms/project-manage!stepFifthSave.action";
					$.ajax({
						url: SystemProp.kaiJieAppUrl+ajaxUrlMsg,
						type: "POST",
						dataType: "json",
						data: data,
					});
					alert("后台发送进行中,请时刻关注，该项目的发送状态。");
					var tempUrl="/sms/project-manage.action";
					jumpProject(null,tempUrl);
					
				}
			}
		}
		ajaxComit(ajaxUrl,data,callback);
		
		
		
	});
	//回退到第一步
	$("#firstpage").live("click",function(){
		var tempId=$("#smsProjectId").val();
		var tempUrl="/sms/project-manage!stepFirst.action";
		jumpProject(tempId,tempUrl);
	});
	//回退到第二步
	$("#secondpage").live("click",function(){
		var tempId=$("#smsProjectId").val();
		var tempUrl="/sms/project-manage!stepSecond.action";
		jumpProject(tempId,tempUrl);
	});
	//回退到第三步
	$("#thirdpage").live("click",function(){
		var tempId=$("#smsProjectId").val();
		var tempUrl="/sms/project-manage!stepThird.action";
		jumpProject(tempId,tempUrl);
	});
	//回退到第四步
	$("#fourthpage").live("click",function(){
		var tempId=$("#smsProjectId").val();
		var tempUrl="/sms/project-manage!stepFourth.action";
		jumpProject(tempId,tempUrl);
	});
	
	//预览 begin
	$("#webTitle").live("blur",function(){
		$("#mobileIframeId").contents().find("#smsIframmeWebTitle").html($("#webTitle").val());
	});
//	$("#webContent").live("blur",function(){
//		$("#mobileIframeId").contents().find("#smsIframmeWebContent").html(KE.html('webContent'));
////		$("#mobileIframeId").contents().find("#smsIframmeWebContent").html($("#webContent").val());
//	});
	
	function showMobileComm() {
		$("#mobileIframeId").attr("src","../v3/sms/smsIframeTemplate.html");
	}
	
	$("#mobileShowA").live("click",function(){
		showMobileComm();
	});
	
	//预览 end
	
	
	function ajaxComit(url,data,callFun){
		$.ajax({
			url: SystemProp.kaiJieAppUrl+url,
			type: "POST",
			dataType: "json",
			data: data,
			success: callFun
		});
	}
	
	window.onload = function() {
		//showMobileComm();
		$("#mobileIframeId").contents().find("#smsIframmeWebTitle").html($("#webTitle").val());
		$("#mobileIframeId").contents().find("#smsIframmeWebContent").html($("#webContentHidden").val());
		$(".ke-iframe").css("width","565px");
	}
	
})