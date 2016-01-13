$(document).ready(function(){
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime; 
	//publication创建按钮
	$("#createPublication").unbind('click').click(function(){
		$("#createPublicationDialog").fancybox();
	});
	
	//uploadIssue-----------------------------------
	$("#uploadIssue").click(function(e){
		e.preventDefault();
		var url = $(this).attr("url");
		window.open(url,"上传期刊","height=400,width=600,top=100,left=150,toolbar=no,menubar=no,scrollbars=no, resizable=no,titlebar=no, location=no, status=no,z-look=yes");
	});

	
	//publication提交创建
	$("#createPublicationSubmit").unbind('click').live('click',function(){
		var publicationDialog=$("#createPublicationDialog");
		var tipError = $(".tipsError",publicationDialog).hide();
		var name=$("input[name=name]:visible",publicationDialog).val();
		var categoryId=$("select[name=categoryId]:visible",publicationDialog).val();
		var description=$("input[name=description]:visible",publicationDialog).val();
		var languageId=$("select[name=languageId]:visible",publicationDialog).val();
		var issueType=$("select[name=issueType]:visible",publicationDialog).val();
		var whratio=$("input[name=whratio]:visible:checked",publicationDialog).val();
		var issn=$("input[name=issn]:visible",publicationDialog).val();
		var publisher=$("input[name=publisher]:visible",publicationDialog).val();
		
		
		if(!$.trim(name)){
			tipError.show().html("杂志名称不能为空!");
			return;
		}else{
			tipError.hide();
		}
		
		var callback=function(result){
			var code = result.code;
			var data = result.data;
			var message = result.message;
			if(code!=200){
				tipError.show().html(message);
			}else{
				//-----------------itemNumber+1-----------------
				tipError.show().html("新建杂志成功！");
				setTimeout(function(){
					$.fancybox.close();
				},500);
			}
		};
		
		$.ajax({
			url:SystemProp.appServerUrl +"/publish/publication!createPub.action",
			type:"POST",
			data:{"name":name,"categoryId":categoryId,"description":description,"languageId":languageId,"issueType":issueType,"whratio":whratio,"issn":issn,"publisher":publisher},
			success:callback
		});
		
	});
	
	
	//publication选择
	$("div[id^='publication']").unbind('click').live('click',function(e){
		e.preventDefault();
		if($(this).hasClass("current")) return;
		var publicationId = $(this).attr("id").split('_')[1];
		
		window.location.href = SystemProp.appServerUrl+"/publish/pcenter-publisher!magList.action?publicationId="+publicationId;
	});
	
	//issue上架
	$("a[issueupid]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('issueupid');
		var callback = function(result){
			if(result.code == 200){
				el.html("下架");
				el.removeAttr("issueupid");
				el.attr("issuedownid",issueId);
				alert("上架成功");
			}else{
				alert("上架失败");
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!upShelfJson.action',
			type : 'post',
			dataType : 'json',
			data : {'id':issueId},
			success : callback
		});
	});
	
	//issue下架
	$("a[issuedownid]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('issuedownid');
		var callback = function(result){
			if(result.code == 200){
				if(result.code == 200){
					el.html("上架");
					el.removeAttr("issuedownid");
					el.attr("issueupid",issueId);
					alert("下架成功");
				}else{
					alert("下架失败");
				}
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!downShelfJson.action',
			type : 'post',
			dataType : 'json',
			data : {'id':issueId},
			success : callback
		});
	});
	
	//issue重新转换
	$("a[issueRetransId]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('issueRetransId');
		var callback = function(result){
			if(result.code == 200){
				el.remove();
				alert("重新转换已经提交");
			}else{
				alert("重新转换提交失败");
			}
			el.remove();
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!reTransferJson.action',
			type : 'post',
			dataType : 'json',
			data : {'id':issueId},
			success : callback
		});
	});
	
	//swf重新转换
	$("a[swfmgzProcessId]").unbind('click').live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var el = $(this);
		var mgzProcessId = $(this).attr('swfmgzProcessId');
		$("#mgzProcessId",$("#swfRetransfer")).val(mgzProcessId);
		$("#swfRetransfer").fancybox();
	});
	//swf输入框取消
	$("#swfRetransfercancel").unbind().click(function(){
		$.fancybox.close();
	});
	
	//swf重新转换提交
	$("#swfRetransferSubmit").unbind().click(function(e){
		e.preventDefault();
		e.stopPropagation();
		var mgzProcessId=$("#mgzProcessId",$("#swfRetransfer")).val();
		var pageNos=$("#pageNos",$("#swfRetransfer")).val();
		var callback = function(result){
			if(result.code == 200){
				$.fancybox.close();
				alert("重新转换swf已经提交");
			}else{
				alert("重新转换swf提交失败");
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/swf-retrans!reTransJson.action',
			type : 'post',
			dataType : 'json',
			data : {'mgzProcessId':mgzProcessId,"pageNos":pageNos},
			success : callback
		});
	});
	
	
	//issue删除
	$("a[issueDelId]").unbind('click').live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var delBtn = $(this);
		var issueId = delBtn.attr("issueDelId");
		var callback = function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				delBtn.parent().parent().remove();
				alert("删除成功");
			}else{
				alert("删除失败");
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!delIssue.action',
			type : 'post',
			dataType : 'json',
			data : {'id':issueId},
			success : callback
		});
	});
	
	//issue编辑
	$("a[issueeditid]").unbind("click").live("click",function(e){
		e.stopPropagation();
		var issueId = $(this).attr('issueeditid');
		var $editIssueForm = $("#editIssueForm");
		var $dateInput = $editIssueForm.find("input[name='publishDate']");
		
		datePickerFun($dateInput,nowDate);
		var callback = function(result){
			var editIssueDialog = $("#editIssueDialog");
			$("input[name=id]",editIssueDialog).attr("value",result.data.issue.id);
			$("input[name=keyword]",editIssueDialog).attr("value",result.data.issue.keyword);
			$("input[name=publishDate]",editIssueDialog).attr("value",result.data.issue.publishDate.substring(0,10));
			$("input[name=issueNumber]",editIssueDialog).attr("value",result.data.issue.issueNumber);
			$("select[name=chop]",editIssueDialog).val(result.data.issue.chop);
			$("textarea[name=description]",editIssueDialog).val(result.data.issue.description);
			$("select[name=languageId]",editIssueDialog).attr("value",result.data.issue.languageId);
			
			$("#tipError",editIssueDialog).hide();
			editIssueDialog.fancybox();
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!getIssueById.action',
			type : 'post',
			dataType : 'json',
			data : {'id':issueId},
			success : callback
		});
	});
	
	//issue编辑提交
	$("#editIssueFormSubmit").unbind('click').live('click',function(e){
		e.preventDefault();
		var issue = form2object('editIssueForm');
		var tipError=$("#tipError",$("#editIssueForm"));
		//表单验证//////////////////////////////////////////////////////////////////
		if(!(issue.keyword) || issue.keyword == '')  {
			tipError.text("关键字不能为空!").show();
			return;
		}
		if(!(issue.publishDate) || issue.publishDate == '') {
			tipError.text("发布日期不能为空").show();
			return;
		}else{
			var pattern = /^(\d{4})\-(\d{2})\-(\d{2})$/; //目前只匹配yyyy-MM-dd格式
			if(!pattern.exec(issue.publishDate)) {
				tipError.text("日期格式不正确，em:1985-01-02").show();
				return;
			};
		}
		if(!(issue.issueNumber) || issue.issueNumber == '') {
			tipError.text("期刊号不能为空").show();
			return;
		}
		//issue.description=$("textarea[name=description]",$("#editIssueDialog")).val();
		//if(issue.fileName == '') {alert('文件名不能为空!');return;}
		//if(issue.publicationId == '') {alert('杂志名不能为空!');return;}
		////////////////////////////////////////////////////////////////////////////
		var callback = function(result){
			$.fancybox.close();
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/issue!updateIssue.action',
			type : 'post',
			dataType : 'json',
			data : issue,
			success : callback
		});
	});
	
	
	//杂志上架
	$("em[pubupid]").unbind('click').live('click',function(e){
		e.stopPropagation();
		var el = $(this);
		var publicationId = $(this).attr('pubupid');
		var callback = function(result){
			//更新面板为下架
			if(result.message == 'success'){
				el.removeAttr('pubupid');
				el.attr('pubdownid',publicationId);
				el.text('下架');
				el.parents("div[id^='publication']").find("strong>span").html("");
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/publication!upshelfPubJson.action',
			type : 'post',
			dataType : 'json',
			data : {'id':publicationId},
			success : callback
		});
	});
	//杂志下架
	$("em[pubdownid]").unbind('click').live('click',function(e){
		e.stopPropagation();
		var el = $(this);
		var publicationId = $(this).attr('pubdownid');
		var callback = function(result){
			if(result.message == 'success'){
				//更新面板为上架
				el.removeAttr('pubdownid');
				el.attr('pubupid',publicationId);
				el.text('上架');
				el.parents("div[id^='publication']").find("strong>span").html("(已下架)");
				$("a[publicationPubId="+publicationId+"]").addClass("disabled");
				var emUps =  $("a[publicationPubId="+publicationId+"]").find("em[issuedownid]");
				emUps.each(function(){
					var issueId = $(this).attr('issuedownid');
					$(this).removeAttr('issuedownid').attr('issueupid',issueId).html("上架");
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/publication!delPubJson.action',
			type : 'post',
			dataType : 'json',
			data : {'id':publicationId},
			success : callback
		});
	});
	
	//publication edit =================
	//杂志编辑窗口
	$("em[publicationId]").unbind('click').live('click',function(e){
		e.stopPropagation();
		var obj = $(this);
		$("#editPublicationDialog #tipError").hide();
		var publicationData = obj.data("publicationData");
		var publicationId = obj.attr("publicationId");
		function fillPublicationData(publication){
			var editPublicationDialog = $("#editPublicationDialog");
			$("input[name='id']",editPublicationDialog).attr('value',publication.id);
			$("input[name='name']",editPublicationDialog).attr('value',publication.name);
			$("input[name='issn']",editPublicationDialog).attr('value',publication.issn);
			$("input[name='publisher']",editPublicationDialog).attr('value',publication.publisher);
			$("#categoryId",editPublicationDialog).val(publication.categoryId);
			$("#description",editPublicationDialog).attr('value',publication.description);
			$("#languageId").val(publication.languageId);
			if(publication.issueType){
				$("#issueType").val(publication.issueType);
			}
			if(publication.whratio &&　(publication.whratio)==1){
//				$("input[name='whratio']").attr("checked",'1');
				//$("input[name='whratio'][value='1']:visible").attr("checked","checked")
				$("#whratio").attr("checked","checked");
			}
			editPublicationDialog.fancybox();
		}
		
		var callback = function(result){
			var publication = result.data.publication;
			fillPublicationData(publication);
			
			obj.data("publicationData",publication);
		};
		if(!publicationData){
			$.ajax({
				url : SystemProp.appServerUrl + "/publish/publication!getPublicationById.action",
				type : "POST",
				dataType : 'json',
				async : false,
				data : {'id':publicationId},
				success : callback
			});
		}else{
			fillPublicationData(publicationData);
		}
		
		
		//-------------------------keyup-----------editPublicationSubmit--------------------------------
		$("#editPublicationDialog").unbind("keyup").keyup(function(e){
			e.preventDefault();
			e.stopPropagation();
			if (e.keyCode == '13') {
				e.preventDefault();
				if($(this).is(":visible")){
					$("#editPublicationSubmit").click();
				}
		    }
		});
	});
	//--------------------------------modify_publication_cancel--------------------------------------------
	$("#editPublicationDialogForm").find("#cancel").unbind('click').click(function(e){
		e.preventDefault();
		$.fancybox.close();
	});
	//publication创建取消按钮
	$("#createPublicationDialog").find(".btnWS").click(function(e){
		e.preventDefault();
		$.fancybox.close();
	});
	
	//issue编辑取消按钮
	$("#editIssueDialog").find(".btnWS").click(function(e){
		e.preventDefault();
		$.fancybox.close();
	});
	
	//杂志编辑提交
	$("#editPublicationSubmit").unbind('click').live('click',function(e){
		e.preventDefault();
		var publication = form2object('editPublicationDialogForm');
		var publicationId = publication.id;
		var tipError = $("#editPublicationDialog #tipError");
		//表单验证////////////////////////////////////////////////////////
		if(!publication.name) {
			tipError.show().html('杂志名称不能为空!');
			return;
		}
		//////////////////////////////////////////////////////////////////
		var callback = function(result){
			if(result.code == 200){
				//更新面板数据
				var toolbar = $("#publisherPubTopToolBarInner");
				var publicationDiv = $("#publication_"+publicationId,toolbar);
				
				$("[name='publication_name']",publicationDiv).html(publication.name);
				$("[name='publication_des']",publicationDiv).html(publication.description);
				
				//对数据进行cash
				$("em[publicationId]",publicationDiv).data("publicationData",publication);
				$.fancybox.close();
			} else {
				tipError.show().html(result.message);
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + "/publish/publication!updatePubJson.action",
			type : 'post',
			dataType : 'json',
			data : publication,
			success : callback
		});
	});
	//------------------------------------------------
	var $topbar = $("#pubTopbar");
	var $topbarInner = $topbar.find(".inner");
	var $topbarItem = $topbar.find(".item");
	var $topbarBtnLR = $topbar.find(".btnLR");
	var itemWidth 	= $topbarItem.eq(0).outerWidth(true);
	var itemNumber	= $topbarItem.length;
	var currentId = 0;
	var lock = 0;
	var delayTime = 2000;
	
	fnSetBtn(0);
	$topbarBtnLR.unbind('click').bind("click",function(){
		if(lock==0){
			lock=1;
			if($(this).hasClass("turnLeft") && currentId!=0){
				currentId--;
			}else if($(this).hasClass("turnRight") && currentId < itemNumber-4){
				currentId++;
			}
			$topbarInner.animate({marginLeft:-(itemWidth*currentId)},800);
			fnSetBtn(currentId);
			setTimeout(function(){lock=0;},800);
		}
	});
	function fnSetBtn(id){
		currentId=id;
		$topbarBtnLR.removeClass("stopL").removeClass("stopR");
		if(itemNumber <= 4){
			$topbarBtnLR.filter(".turnRight").addClass("stopR");
			$topbarBtnLR.filter(".turnLeft").addClass("stopL");
			return;
		}
		if(currentId==0){
			$topbarBtnLR.filter(".turnLeft").addClass("stopL");
		}else if(currentId==itemNumber-4){
			$topbarBtnLR.filter(".turnRight").addClass("stopR");
		}
		
	}
	
	
	
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
				$dateInput.DatePickerHide();
			}
		});
	}
	
	//publisher信息编辑按钮
	$("#editPublisherInfo").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var editPublisherDialog = $("#editPublisherDialog");
		$("#tipError",$("#editPublisherForm")).hide();
		$("#editPublisherDialog").find("#cancel").unbind('click').click(function(e){e.preventDefault();$.fancybox.close();});
		editPublisherDialog.fancybox();
	});
	
	//publisher修改表单提交
	$("#editPublisherFormSubmit").unbind('click').live('click',function(e){
		e.preventDefault();
		var publisher = form2object('editPublisherForm');
		var content22=$("#editPublisherForm");
		var tipsError=$("#tipError",content22);
		var email = $("#email",content22).val();
		var oldPassword = $("#oldPassword",content22).val();
		var password = $("#password",content22).val();
		var password2 = $("#password2",content22).val();
		
		var contact1 = $("#contact1",content22).val();
		var contactPhone1 = $("#contactPhone1",content22).val();
		var normalContact=$("#normalContact",content22).val();
		
		//前端校验
		if(email == '' || email.indexOf('@') == -1){
			//tipError.html("邮箱必须正确填写!").show();
			tipsError.html("邮箱必须正确填写!").show(); 
			return;
		}
		
		var reg = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/ ;
		
		if(!$.trim(oldPassword) && password !== password2){
			tipsError.html("确认密码错误").show(); 
			return;
		}
		
		if(!$.trim(contact1) ){
			tipsError.html("联系人1必须填写").show(); 
			return;
		}
		
		if(!$.trim(contactPhone1) ){
			tipsError.html("联系人电话1必须填写").show(); 
			return;
		}
		
		if(!$.trim(normalContact) ){
			tipsError.html("常用联系方式必须填写").show(); 
			return;
		}
		
		var callback = function(result){
			var data=result.data;
			if(result.message == 'success'){
				tipsError.text("修改成功").show();
				setTimeout(function(){
					$("#editPublisherForm div.important input").val("");
					$.fancybox.close();
				},500);
			}else{
				if(data){
					if(data.email){
						tipsError.html(data.email).show();
					}
					if(data.oldPassword){
						tipsError.html(data.oldPassword).show();
					}
					if(data.password){
						tipsError.html(data.password).show();
					}
				}else{
					tipsError.text(result.message).show();
				}
				
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl + '/publish/publisher!updatePublisher.action',
			type : 'post',
			dataType : 'json',
			data : publisher,
			success : callback
		});
	});
	
	//publisher头像按钮
	$("#changePublisherLogo").unbind('click').live('click',function(e){
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
	                url:SystemProp.appServerUrl+"/publish/publisher!uploadLogoJson.action",//用于文件上传的服务器端请求地址
	                secureuri:false,//一般设置为false
	                fileElementId:"logoFile",//文件上传空间的id属性  <input type="file" id="file" name="file" />
	                content:content,
	                dataType: "json",//返回值类型 一般设置为json
	                success: function (rs, status)  //服务器成功响应处理函数
	                { 
	            		if(rs.code!=200){
	            			alert(rs.message);	
	            		}else{
	            			var src = SystemProp.publishProfileServerUrlTmp+rs.data.tempAvatarUrl+"?ts="+new Date().getTime();
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
				var logoFilePath=result.data.logoFilePath;
				var logoArr=logoFilePath.split("/");
				tmpLogo="30_"+logoArr[2];
				logoArr[2]="60_"+logoArr[2];
				var logoUrl=logoArr.join("/");
				logoArr[2]=tmpLogo;
				var miniLogoUrl=logoArr.join("/");
				$("#publisherLogo").attr("src",SystemProp.publishProfileServerUrl+logoUrl+"?ts="+new Date().getTime());
				$("#publisherMiniLogo").attr("src",SystemProp.publishProfileServerUrl+miniLogoUrl+"?ts="+new Date().getTime());
			}
		};
		
		$("#submit",content).unbind('click').click(function(){
			var url = SystemProp.appServerUrl + "/publish/publisher!updateJson.action";
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
	//重新处理失败的页面
	$("a[dealtype]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var $obj = $(this);
		var dealType = $obj.attr("dealType");
		var issueId = $obj.attr("mgzIssueId");
		var pageId = $obj.attr("pageId");
		
		var callback = function(rs){
			if(!rs) return;
			var code = rs.code;
			if(code == 200){
				$obj.remove();
				alert("失败的页面已经提交处理！");
			}else{
				alert("重新处理失败！");
			}
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/publish/publisher-upload!reDealSwfJpgJson.action",
			type : "POST",
			data : {'dealType':dealType,'issueId':issueId,'pageId':pageId},
			success: callback
		});
	});
	
	$("a[swfRetransReplace]").click(function(e){
		e.preventDefault();e.stopPropagation();
		var $a=$(this);
		var id=$(this).attr("swfRetransReplace");
		var callback = function(rs){
			if(!rs) return;
			var code = rs.code;
			if(code == 200){
				$a.parents("tr").remove();
				alert("替换成功，请过一段时间后查看结果");
			}else{
				alert("替换失败");
			}
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/publish/swf-retrans!replace.action",
			type : "POST",
			data : {'swfRetransId':id},
			success: callback
		});
		
	});
	
	$("a[swfRetransDel]").click(function(e){
		e.preventDefault();e.stopPropagation();
		var $a = $(this);
		var id = $a.attr("swfRetransDel");
		var callback = function(rs){
			if(!rs) return;
			var code = rs.code;
			if(code == 200){
				$a.parents("tr").remove();
				alert("删除成功");
			}else{
				alert("删除失败");
			}
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/publish/swf-retrans!del.action",
			type : "POST",
			data : {'swfRetransId':id},
			success: callback
		});
		
	});
	
	$("a[textPageIssueId]").unbind("click").live("click",function(e){
		var obj=$(this);
		var issueId=obj.attr("textPageIssueId");
		var pageNo=obj.attr("textPagePageNo");
		var flag=true;
		if(!pageNo||pageNo.leng==0){
			pageNo=0;
			flag=false;
		}
		var callback = function(rs){
			if(!flag){
				$("#magList").removeClass("conMiddleRight conManageMgzList").addClass("conMiddleRight conManageMgzInput");
			}
			$("#magList").html(rs);
			$("#textPageForm").find("textarea[name='content']").focus();
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/publish/text-page!recordAjax.action",
			type : "POST",
			data : {"issueId":issueId,"pageNo":pageNo},
			success: callback
		});
	});
	
	$("a[name='textPageFormSaveBtn']").unbind("click").live("click",function(){
		var textPageForm=$("#textPageForm");
		var issueId=textPageForm.find("input[name='issueId']").val();
		var pageNo=textPageForm.find("input[name='pageNo']").val();
		var totalPages=$(this).attr("totalPages");
		var content=textPageForm.find("textarea[name='content']").val();
		var data={"issueId":issueId,"pageNo":pageNo,"content":content};
		var callback = function(rs){
			if(rs.code!=200){
				alert(rs.message);
				return;
			}
			
			if(totalPages&&totalPages.length>0){
				if(pageNo*1>=totalPages*1){
					alert("本期刊已录入完,感谢您的辛勤劳动,先休息一下吧~");
				}else{
					$("a[textPageIssueId][textPagePageNo='"+(pageNo*1+1)+"']").click();
				}
			}else{
				alert("保存成功");
			}
		};
		$.ajax({
			url:SystemProp.appServerUrl + "/publish/text-page!saveJson.action",
			type : "POST",
			data : data,
			success: callback
		});
	});
	

});