$(document).ready(function(){
	if (window.location.href.indexOf("/ad/adcenter-home.action") > -1){
		$("#posManage").attr("class","current");
	}

	if (window.location.href.indexOf("/ad/manage-position.action") > -1){
		$("#posManage").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/manage-create.action") > -1){
		$("#createAd").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/manage-my-ad.action") > -1){
		$("#myAd").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/manage-data") > -1){
		$("#adData").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/manage-my-mgz-ad.action") > -1){
		$("#mymgzAd").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/manage-magme-ad.action") > -1){
		$("#magmeAd").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/ad-agency!msgList.action") > -1){
		$("#message").attr("class","current");
	}
	
	if (window.location.href.indexOf("/ad/manage-ad-side!getList.action") > -1){
		$("#sideAd").attr("class","current");
	}
	
	//publisher信息编辑按钮
	$("#editAdAgencyInfo").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var editAdAgencyDialog = $("#editAdAgencyDialog");
		$("#tipError",$("#editAdAgencyForm")).hide();
		$("#editAdAgencyDialog").find("#cancel").unbind('click').click(function(e){e.preventDefault();$.fancybox.close();});
		editAdAgencyDialog.fancybox();
	});
	
	//publisher修改表单提交
	$("#editPublisherFormSubmit").unbind('click').live('click',function(e){
		e.preventDefault();
		var adagency = form2object('editAdAgencyForm');
		var tipsError=$("#tipError",$("#editAdAgencyForm"));
		var callback = function(result){
			if(result.message == 'success'){
				tipsError.text("修改成功").show();
				setTimeout(function(){
					$("#editAdAgencyForm div.important input").val("");
					$.fancybox.close();
				},500);
			}else{
				tipsError.text(result.message).show();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/ad/ad-agency!update.action',
			type : 'post',
			dataType : 'json',
			data : adagency,
			success : callback
		});
	});
	
	
	
	//publisher头像按钮
	$("#changeAdAgencyLogo").unbind('click').live('click',function(e){
		e.preventDefault();
		var uploadLogoDialog = $("#uploadPublisherLogoDialog");
		uploadLogoDialog.fancybox();
		changeAvatar($(this));
	});
	//预加载图片               
	var enhancedImage = function (src,onLoaded){
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
	function changeAvatar(obj){
		var infoDialog = $("#uploadPublisherLogoDialog"),
			content = $("#fancybox-content");
		//生成dialog
		infoDialog.fancybox();
		//上传头像绑定事件
		var avatarFile = $("#logoFile",content);
		if ($.browser.msie){
		    // IE suspends timeouts until after the file dialog closes
			avatarFile.live('click change',function(event){
		        setTimeout(function(){
		        	ajaxFileUpload();
		        }, 1);
		    });
		}else{
		    // All other browsers behave
			$("#logoFile",content).live("change",ajaxFileUpload);
		}
		
	    function ajaxFileUpload(){
	    	var fileUrl = $("#logoFile",content).val();
	    	var fileData = $("#logo",content).data("fileData");
	    	if(fileUrl !="" && (!fileData || fileData != fileUrl) ){
	    		$("#logo",content).data("fileData",fileUrl);
	    	}else{
	    		return;
	    	}
	        $.ajaxFileUpload(
	            {
	                url:SystemProp.appServerUrl+"/ad/ad-agency!uploadLogoJson.action",//用于文件上传的服务器端请求地址
	                secureuri:false,//一般设置为false
	                fileElementId:"logoFile",//文件上传空间的id属性  <input type="file" id="file" name="file" />
	                content:content,
	                dataType: "json",//返回值类型 一般设置为json
	                success: function (rs, status)  //服务器成功响应处理函数
	                { 
	            		if(rs.code!=200){
	            			alert(rs.message);	
	            		}else{
	            			var src = SystemProp.adProfileServerUrlTmp+rs.data.tempAvatarUrl+"?ts="+new Date().getTime();
	                    	var avatar = $("#logo",content);
	                    	avatar.attr("src",src);
	                    	//同时更新页面上logo
	                    	//var logo = $("#publisherLogo");
	                    	//logo.attr("src",src);
	                    	$("#logoBox").removeClass("hide");
	                    	if($("#uploadPublisherBtn").hasClass("btnOB")){
		                    	$("#uploadPublisherBtn").removeClass("btnOB").addClass("btnOS");
		                    	$("#uploadPublisherBtn").find("span").html("重新上传头像");
	                    	}
	                    	$("#logoFileName",content).val(rs.data.logoFileName);
	                    	enhancedImage(src,function(img){
	                    		var imgWidth = img.width;
	                    		var imgHeight = img.height;
	                    		var showWidth = avatar.width();
		                    	var showHeight = avatar.height();
	                    		avatar.imgAreaSelect({
	                    			aspectRatio: "1:1",
	                    			handles: true,
	                    			onSelectEnd: function (img, selection) {
		                    			$("input[name=x]",content).val(selection.x1 * imgWidth / showWidth);
		                    			$("input[name=y]",content).val(selection.y1 * imgHeight /showHeight);
		                    			$("input[name=width]",content).val(selection.width * imgWidth / showWidth);
		                    			$("input[name=height]",content).val(selection.height * imgHeight /showHeight);            
		                    		}
	                    		});
	                    	})
	                    }
	                },
	                //服务器响应失败处理函数
	                error: function (data, status, e) {
	                    return;
	                }
	            }
	        );
	    }
		
		var callback = function(result){
			if(!result) return;
			var message ;
			var tipError = $("#tipError",content);
			if(result.code != 200){
				tipError.show().html(result.message);
			}else{
				$("#logo",content).imgAreaSelect({remove:true});
				$.fancybox.close();
				//对页面的用户头像修改
				$("#publisherLogo").attr("src",SystemProp.publishProfileServerUrl+result.data.logoFilePath+"?ts="+new Date().getTime());
			}
		};
		
		$("#submit",content).unbind('click').click(function(){
			var url = SystemProp.appServerUrl + "/ad/ad-agency!saveLogoJson.action";
			var data = form2object('editPublisherLogoForm');
			$.ajax({
				url:url,
				type : "POST",
				data : data,
				success: callback
			});
		});
		
		$("#cancel",content).unbind('click').bind('click',function(e){
			e.preventDefault();
			$.fancybox.close();
		});
	}
});