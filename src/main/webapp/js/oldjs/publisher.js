//for topbar
$(document).ready(function(){
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime; 
	
	//conSubMenu高亮展开
	var publicationId = getUrlValue("publicationId");
	if(publicationId){
		var li = $(".conSubMenu .sub:eq(1)");
		li.addClass("current");
		var ul = $(".conSubMenu .sub:eq(1)>ul");
		ul.show();
		ul.find("a[issuemanagepubid='"+publicationId+"']").parent().addClass("current");
	}
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
			$topbarInner.animate({marginLeft:-(itemWidth*currentId)},800,"easeOutQuint");
			fnSetBtn(currentId);
			setTimeout(function(){lock=0},800);
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
	
	function uploadIssue(e){
		e.preventDefault();
		$("#uploadIssueForm").submit();
	}
	
	//-----------------------------uploadIssue------------------------------------- 
	$("#uploadIssue").unbind('click').click(function(){
		var uploadIssueDialog = $("#uploadIssueDialog");
		$("#publishDate",uploadIssueDialog).val(nowDate);
		datePickerFun($("#publishDate"),nowDate);
		var callback=function(result){
			var code = result.code;
			var data = result.data;
			var message = result.message;
			if(code!=200){
				alert("获取杂志信息失败");
			}else{
				var pubs=data.pubs;
				var publication=$("#publicationId",uploadIssueDialog);
				$("option",publication).remove();
				if(pubs.length>1){
					publication.append("<option value='0'>请选择</option>");
				}
				for(var i = 0;i < pubs.length;i++){
					publication.append($("<option/>").attr("value",pubs[i].id).text(pubs[i].name));
				}
				uploadIssueDialog.fancybox();
			}
		};
		//请求杂志
		$.ajax({
        	url:SystemProp.appServerUrl +"/publish/publication!queryNormalPub.action?random="+Math.random(),
			type:"POST",
			success:callback
	     });
		$("#uploadIssueDialog").find("#uploadIssueSubmit").unbind('click').click(uploadIssue);
		$("#uploadIssueDialog").find("#cancel").unbind('click').click(function(e){e.preventDefault();$.fancybox.close();});    
		
	});
	//----------------------------------uploadIssue-----------------------------------------------
	$("#uploadIssueForm").submit(function(){
		var uuid=getUUID();
		if(!checkParameter()) return false;
		$.ajaxFileUpload(
            {
                url : SystemProp.appServerUrl+"/publish/issue-upload!addIssueProcess.action?X-Progress-ID="+uuid+"&random="+Math.random(),
                secureuri : false,
                data : form2object('uploadIssueForm'),
                fileElementId : "issueFile",
                content : $("#uploadIssueForm"),
                dataType : "json",
                async : true,
                type : 'POST',
                success : function(rs){
                	if(!rs) return;
                	if(rs.code != 200){
                		alert("亲，上传期刊失败啦！");
                	}
                },
                //服务器响应失败处理函数
                error : function (data, status, e) {
                	alert("亲，上传期刊失败啦！");
                }
            }
        );
		progressBarShow(uuid);
		return false;
	});
	
	//-----------------------------------cancelUpload---------------------------------------------
	$("#cancelUpload").unbind('click').click(cancelUpload);
	function cancelUpload(e){
		e.preventDefault();
		location.reload();
	}
	
	//----------------------------------checkParameter-----------------------------------------------
	function checkParameter(){
		issueInfo = form2object("uploadIssueForm");
		var tipError = $("#tipError",$("#uploadIssueDialog"));
		var issueFile = issueInfo.issueFile;
		if(!issueFile){
			tipError.html("*上传期刊不能为空").show();
			return false;
		}
		var names = issueFile.split(".");
		if($.trim(names[names.length-1]).toLowerCase() !== 'pdf'){
			tipError.html("*请检查上传期刊类型是否是pdf格式").show();
			return false;
		}
		if(!issueInfo.keyword){
			tipError.html("*关键词不能为空").show();
			return false;
		}
		var publishDate = issueInfo.publishDate;
		var reg = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/ ;
		if(!publishDate){
			tipError.html("*时间不能为空").show();
			return false;
		}
		if($.trim(publishDate) && !reg.test(publishDate)){
			tipError.html("*时间格式错误em：1985-03-21").show();
			return false;
		}
		if(issueInfo.publicationId == '0'){
			tipError.html("*请选择杂志名称").show();
			return false;
		}
		
		tipError.hide();
		$.fancybox.close();
		return true;
	}
	//---------------------------progress-bar-----------------------------------------
	function progressBarShow(uuid){
		var progressBar = $("#progress-bar");
		progressBar.show();
		var isAjax = false;
		var i = setInterval(function() {
			var callback = function(result){
				isAjax = false;
				var data = result.data;
				if(!result){
					progressBar.hide();
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
					$("#progress-bar").attr("title","已经上传了0%");
					$("#bar-inner").css("width","0%");
					$("#persent").html("0%");
					$("#current_size").html(0+"M")
					$("#max_size").html(0+"M");
					progressBar.fadeOut();
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
	}
	
	$("#issueFile").change(function(){
		$("#fileName",$("#uploadIssueDialog")).val($(this).val()); 
	});
	
	//-----------------------createPublication弹出框-----------------------------------
	$("#createPublication").unbind('click').click(function(){
		$("#createPublicationDialog").fancybox();
	});
	
	//提交创建杂志
	$("#createPublicationSubmit").unbind('click').live('click',function(){
		var publicationDialog=$("#createPublicationDialog");
		var tipError = $(".tipsError",publicationDialog).hide();
		var name=$("input[name=name]:visible",publicationDialog).val();
		var categoryId=$("select[name=categoryId]:visible",publicationDialog).val();
		var description=$("input[name=description]:visible",publicationDialog).val();
		var languageId=$("select[name=languageId]:visible",publicationDialog).val();
		var issueType=$("select[name=issueType]:visible",publicationDialog).val();
		var whratio=$("input[name=whratio]:visible:checked",publicationDialog).val();
		
		
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
				var pub=data.pub;
				var em1=$("<em/>").attr("publicationId",pub.id).text("编辑");
				var em2=$("<em/>").attr("pubUpId",pub.id).text("上架");
				var bookbar=$("<div/>").addClass("bookBar").append($("<p/>").append(em1).append(em2));
				var strong=$("<strong/>").attr("name","publication_name").text(pub.name);
				var p=$("<p/>").attr("name","publication_des").text(pub.description);
				var span=$("<span/>").text("期刊数量0");
				var pubdiv=$("<div class='item showBar' id='publication_"+pub.id+"'></div>").append(strong).append(p).append(span).append(bookbar);
				$("#publisherPubTopToolBarInner").append(pubdiv);
				
				//-----------------itemNumber+1-----------------
				itemNumber += 1;
				fnSetBtn(currentId);
				
				tipError.show().html("新建杂志成功！");
				setTimeout(function(){
					$.fancybox.close();
				},500);
			}
		};
		
		$.ajax({
			url:SystemProp.appServerUrl +"/publish/publication!createPub.action",
			type:"POST",
			data:{"name":name,"categoryId":categoryId,"description":description,"languageId":languageId,"issueType":issueType,"whratio":whratio},
			success:callback
		});
		
	});
	
	//addAdvertise 新建广告
	var content = $("#fancybox-content");
	$("#createAdvertise").live('click',function(){
		createAdvertise($(this));
	});
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
	function checkCreateAdParameter(edit){
		var adInfo = form2object("editAdvertiseForm");
		var tipError = $("#tipError",$("#editAdvertiseForm"));
		if(!adInfo.title){
			tipError.html("*标题不能为空").show();
			return false;
		}
		if(!adInfo.keyword){
			tipError.html("*关键词不能为空").show();
			return false;
		}
		if(!adInfo.linkurl && !adInfo.jpgFile && !adInfo.mediaurl  ){
			tipError.html("*广告，图片，视频链接至少填写一个").show();
			return false;
		}
		if(edit==0){
			var startTime = adInfo.startTime;
			if(!startTime){
				tipError.html("*开始时间不能为空").show();
				return false;
			}
			var reg = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/ ;
			if($.trim(startTime) && !reg.test(startTime)){
				tipError.html("*开始时间格式错误em：1985-03-21").show();
				return false;
			}
			var endTime = adInfo.endTime;
			if(!endTime){
				tipError.html("*结束时间不能为空").show();
				return false;
			}
			if($.trim(endTime) && !reg.test(endTime)){
				tipError.html("*结束时间格式错误em：1985-03-21").show();
				return false;
			}
		}
		tipError.hide();
		return true;
	}
	
	
	if ($.browser.msie){
	    // IE suspends timeouts until after the file dialog closes
		$("#jpgFile").unbind("cilck change").live("click change",function(){
			setTimeout(function(){
				var filename=$("#jpgFile").val().split("\\");
				filename2=filename[filename.length-1];
				$("#fileName",$("#editAdvertiseForm")).val(filename2); 
			},1);
			
		});
	}else{
		$("#jpgFile").unbind("change").live("change",function(){
			$("#fileName",$("#editAdvertiseForm")).val($(this).val()); 
		});
	}
	//------------------ajaxUploadAdvJpg---------------------
	function ajaxUploadAdvJpg(url,callback){
		$.ajaxFileUpload(
            {
                url : url,
                secureuri : false,
                data : form2object('editAdvertiseForm'),
                fileElementId : "jpgFile",
                content : $("#editAdvertiseForm"),
                dataType : "json",
                success : callback,
                //服务器响应失败处理函数
                error : function (data, status, e) {
                	$("#tipError",content).html("上传失败").show();
                }
            }
        );
	}
	//------------------createAdvertise----------------------
	function createAdvertise(obj){
		var infoDialog = $("#addAdvertiseDialog");
		var form=$("#editAdvertiseForm");
		//生成dialog
		var adposIds="";
		$("#jpgFile").val("");
		$("input[name='adposCheck']:checked").each(function(){
			adposIds+=$(this).val()+",";
	    });
		if(adposIds!="" && adposIds.length>0){
			$("#adposIds").val(adposIds);
			$("#startTime").show();
			$("#endTime").show();
			$("#adId").val("");
			$("#addAdIdTab").show();
			$("input[name=fileName]").val("");
			$("#startTime",infoDialog).val(nowDate);
			$("#addAdvertiseDialog").fancybox();
			
			datePickerFun($("#addAdvertiseDialog").find("input[name='startTime']"),nowDate);
			datePickerFun($("#addAdvertiseDialog").find("input[name='endTime']"),nowDate);
		}else{
			alert("没有选中要新建广告的广告位");
			return;
		}
		
		//回调函数
		var callback = function(result){
			if(!result) return;
			//禁用
			var adId=$("#addAdId").val();
			if(adId==0){
				$("input",form).attr('disabled',false);
				$("select[name=adType]",form).attr('disabled',false);
			}else{
				$("input",form).filter(function(){
					var name = this.name;
					return name!=='startTime' && name!=='endTime';
				}).attr('disabled',true);
				$("select[name=adType]",form).attr('disabled',true);
			}
			var tipError = $("#tipError",content);
			var code = result.code;
			var data = result.data;
			var message=result.message;
			if(code != 200){
				tipError.html(message).show();
			}else{
				var ad=data.ad;
				var adposMap=data.adposMap;
				var adposmappingMap=data.adposmappingMap;
				$("input[name='adposCheck']:checked").each(function(){
					//禁止选中 不可选
					$(this).attr("checked",false);
					$(this).attr("disabled","disabled");
					var positionid=$(this).val();
					//增加操作元素
                    var str="<a name=\"adposDel"+positionid+"\"  href=\"#\" class=\"btnBS\""+" editpos="+ad.id+" >编辑</a>\n";
                    str+="<a href=\"javascript:void(0)\" preissueId=\""+ adposMap[positionid].issueId+"\" pageId=\""+adposMap[positionid].pageNo+"\" adposId=\""+positionid+"\" class=\"btnWS\" >预览</a>\n";
                    str+="<a name=\"adposDel"+positionid+"\" delad=\""+ad.id+"\" deladposid=\""+positionid+"\" href=\"javascript:void(0)\" class=\"btnOS\" >删除</a>\n";
                    if(adposmappingMap && adposmappingMap[positionid]){
                    	str+="<a mappingId=\""+adposmappingMap[positionid].id+"\" mappingadpos=\"adposDel"+positionid+"\"  href=\"javascript:void(0)\" class=\"btnBS\" >审核通过</a>";
                    }
                    $(this).next().html(str);
                    
                    //增加广告信息
                    $(this).next().next().find("em").attr("name","adposDel"+positionid).html(ad.title);
                    $(this).next().next().find("p").attr("name","adposDel"+positionid).html(ad.description);
			    });
				$.fancybox.close();
			}
		};
		
		
		$("#editAdvertiseForm").unbind('submit').submit(function(){
			if(!checkCreateAdParameter(0)){
				return false;
			}
			//取消禁用
			$("input",form).attr('disabled',false);
			$("select[name=adType]",form).attr('disabled',false);
			var url = SystemProp.appServerUrl +"/publish/advertise!addAdToPosJson.action?random="+Math.random();
			ajaxUploadAdvJpg(url,callback);
			return false;
		});
		
		var formSubmitFun = function(e){
			e.preventDefault();
			$("#editAdvertiseForm").submit();
		};
		
		$("#addsubmit:visible",content).unbind('click').click(formSubmitFun);
		$("#addcancel:visible",content).unbind('click').click(function(e){e.preventDefault();$.fancybox.close();});
	}
	
	//广告选择的改变
	$("#addAdId").unbind('change').live('change',function(){
		var adId=$(this).val();
		var form=$("#editAdvertiseForm");
		if(adId==0){
			$("input",form).attr('disabled',false);
			$("select[name=adType]",form).attr('disabled',false);
		}else{
			var callback=function(result){
				if(!result) return;
				var code = result.code;
				var message=result.message
				if(code != 200){
					alert(message);
				}else{
					var ad=result.data.ad;
					$("input[name=title]",form).val(ad.title);
					$("input[name=keyword]",form).val(ad.keyword);
					$("select[name=adType]",form).val(ad.adType);
					$("input[name=linkurl]",form).val(ad.linkurl);
					//$("input[name=imgurl]",form).val(ad.imgurl);
					$("input[name=mediaurl]",form).val(ad.mediaurl);
					$("input[name=fileName]").val("");
					//$("input[name=startTime]",form).val(ad.startTime);
					//$("input[name=endTime]",form).val(ad.endTime);
					$("input[name=description]",form).val(ad.description);
					//不可修改
					$("input",form).filter(function(){
						var name = this.name;
						return name!=='startTime' && name!=='endTime';
					}).attr('disabled',true);
					$("select[name=adType]",form).attr('disabled',true);
				}
			};
			
			$.ajax({
				url:SystemProp.appServerUrl+"/publish/advertise!queryByIdJson.action",
				type : "POST",
				data : {"adId":adId},
				success: callback
			});
			
			
		}
		
	});
	
	$("a[mappingId]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var url=SystemProp.appServerUrl + "/publish/adpos-mapping!auditMappingJson.action";
		var mappingId=$(this).attr("mappingId");
		var map=$(this);
		var callback=function(result){
			if(!result) return;
			var code = result.code;
			var message=result.message
			if(code != 200){
				alert(message);
			}else{
				map.remove();
			}
		};
		$.ajax(
			{
				url:url,
				type : "POST",
				data : {"mappingId":mappingId},
				success: callback
			}
		);
	});
	
	//删除广告
	$("a[delad]",$("#sideMiddleRight")).unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var posid = $(this).attr("deladposid");
		var delad = $(this).attr("delad");
		var data ={"adPosId":posid,"adId":delad};
		var url = SystemProp.appServerUrl +"/publish/adpos-mapping!delMappingJson.action";
		var deladcallback = function(result){
			if(!result) return;
			var code = result.code;
			var message=result.message
			if(code != 200){
				alert(message);
			}else{
				$("a[name=adposDel"+posid+"]").parent().parent().find("input").attr("disabled",false).attr("checked",false);
				$("a[name=adposDel"+posid+"]").remove();
				$("a[adposId="+posid+"]").remove();
				$("a[mappingadpos=adposDel"+posid+"]").remove();
				$("p[name=adposDel"+posid+"]").html("");
				$("em[name=adposDel"+posid+"]").html("");
			}
		};
		
		$.ajax({
			url: url,
			type : "POST",
			data : data,
			success: deladcallback
		});
		
	});
	//预览广告
	$("a[preissueId]",$("#sideMiddleRight")).unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var url=SystemProp.appServerUrl +"/publish/mag-read!pubReader.action?id="+$(this).attr("preissueid")+"&pageId="+$(this).attr("pageId");
		window.location.href = url;
	});
	
	//编辑广告----------------------------------------
	$("a[editpos]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var adId=$(this).attr("editpos");
		$("#adId").val(adId);
		function close(){
			var infoDialogClone = $("#addAdvertiseDialog").clone().hide();
			infoDialogClone.find("form").removeAttr("id");
			$("div[id='addAdvertiseDialog']:hidden").replaceWith(infoDialogClone);
			$.fancybox.close();
		};
		//广告请求回调
		var queryAdCallback=function(result){
			var tipError = $("#tipError",content);
			var code = result.code;
			var data = result.data;
			var message=result.message;
			var ad=data.ad;
			if(code == 200 && ad){
				if(!confirm("修改广告将修改其它广告位中的相同广告")){
					return false;
				}
				$("#addAdIdTab").hide();
				$("input[name=title]").val(ad.title);
				$("input[name=keyword]").val(ad.keyword);
				$("input[name=linkurl]").val(ad.linkurl);
				$("input[name=fileName]").val("");
				$("#jpgFile").val("");
				//$("input[name=imgurl]").val(ad.imgurl);
				$("input[name=mediaurl]").val(ad.mediaurl);
				$("select[name=adType]","#editAdvertiseForm").attr('disabled',false);
				$("select[name=adType]").val(ad.adType);
				$("#startTime").hide();
				$("#endTime").hide();
				$("#adposIds").val("");
				$("input[name=description]").val(ad.description);
				$("input",$("#editAdvertiseForm")).attr('disabled',false);
				$("#addAdvertiseDialog").fancybox();
			}else{
				alert("编辑广告出错");
			}
		};
		
		//请求广告信息
		$.ajax({
			url:SystemProp.appServerUrl +"/publish/advertise!queryByIdJson.action",
			async: false,
			type : "POST",
			data : {"adId":adId},
			success: queryAdCallback
		});
		//回调函数
		var callback = function(result){
			if(!result) return;
			var tipError = $("#tipError",content);
			var code = result.code;
			var data = result.data;
			var message=result.message;
			
			if(code != 200){
				tipError.html(message).show();
			}else{
				var ad=data.ad;
				$("em[changeval=changeval"+ad.id+"]").html(ad.title);
				$("p[changeval=changeval"+ad.id+"]").html(ad.description);
				$.fancybox.close();
			}
		};
		
		$("#editAdvertiseForm").unbind('submit').submit(function(){
			if(!checkCreateAdParameter(1)){
				return false;
			}
			var url = SystemProp.appServerUrl +"/publish/advertise!updateAdJson.action?random="+Math.random();
			ajaxUploadAdvJpg(url,callback);
			return false;
		});
		
		$("#addsubmit:visible").unbind('click').click(function(e){e.preventDefault();$("#editAdvertiseForm").submit();});
		$("#addcancel:visible").unbind('click').click(function(e){e.preventDefault();close();});
	});
	//checkbox's check
	$(".adList>.item").live('click',function(e){
		var $checkbox = $(this).find("input:checkbox:visible:enabled");
		var targetEle = $(e.target);
		var type = targetEle.attr("type");
		if(type && type.toLowerCase() === 'checkbox'){
			return;
		}
		var checkVal = ($checkbox.attr("checked"))? false : true;
		$checkbox.attr('checked',checkVal);
	});
	//杂志管理
	/*$("#adManager").live('click',function(){
		window.location.href=SystemProp.appServerUrl +"/publish/pcenter-publisher.action";
	});*/
	
	//广告管理
	$("a[publicationId]").live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var publicationId = $(this).attr("publicationId");
		manageAd(publicationId);
	});
	
	//杂志管理
	$("a[issueManagePubId]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var publicationId = $(this).attr("issueManagePubId");
		window.location.href=SystemProp.appServerUrl +"/publish/pcenter-publisher.action"+"?publicationId="+publicationId;
	});
	
	//issue 广告管理
	$("a[issueId]",$("#sideMiddleRight")).unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var issueId = $(this).attr("issueId");
		var publicationId = $(this).attr("pubId");
		var data = {"startRow":0,"totalRow":20,"issueId":issueId,"publicationId":publicationId};
		
		AllAds(data);
	});
	function manageAd(publicationId){
		var data = {"startRow":0,"totalRow":20,"publicationId":publicationId};
		AllAds(data);
	}
	function AllAds(data){
		var callback = function(result){
			$("#sideMiddleRight").replaceWith($(result));
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/adpos-mapping!queryAdPosJson2.action",
			type : "POST",
			dataType : 'html',
			data : data,
			success: callback
		});
	}
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
			if (event.keyCode == '13') {
				event.preventDefault();
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
	$("#createPublicationDialog").find(".btnWS").click(function(e){
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
	
	//issueList编辑
	$("em[issueId]").unbind("click").live("click",function(e){
		e.stopPropagation();
		var issueId = $(this).attr('issueId');
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
	//cancleBtn-------
	$("#editIssueDialog #cancel").live('click',function(e){
		e.preventDefault();
		$.fancybox.close();
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
	//杂志删除
	$(".bookBar .del:visible").unbind('click').live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var delBtn = $(this);
		var issueId = delBtn.attr("issueDelId");
		var callback = function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var $item = delBtn.parents("a.showBar");
				$item.addClass("delete");
				$item.find("[name='pubReader']").unbind('click');
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
	//期刊上架
	$("em[issueupid]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('issueupid');
		var callback = function(result){
			if(result.code == 200){
				el.parents(".showBar").removeClass("disabled");
				el.removeAttr('issueupid');
				el.attr('issuedownid',issueId);
				el.text('下架');
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
	//期刊下架
	$("em[issuedownid]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('issuedownid');
		var callback = function(result){
			if(result.code == 200){
				el.parents(".showBar").addClass("disabled");
				el.removeAttr('issuedownid');
				el.attr('issueupid',issueId);
				el.text('上架');
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
	
	//期刊统计数据
	$("em[staticIssueId]").unbind('click').live('click',function(e){
		e.preventDefault();e.stopPropagation();
		var el = $(this);
		var issueId = $(this).attr('staticIssueId');
		window.location.href=SystemProp.appServerUrl+'/publish/issue-static.action?issueId='+issueId;
	});
	
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
		var tipsError=$("#tipError",$("#editPublisherForm"));
		var callback = function(result){
			if(result.message == 'success'){
				tipsError.text("修改成功").show();
				setTimeout(function(){
					$("#editPublisherForm div.important input").val("");
					$.fancybox.close();
				},500);
			}else{
				tipsError.text(result.message).show();
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
	            			var src = SystemProp.profileServerUrlTmp+rs.data.tempAvatarUrl+"?ts="+new Date().getTime();
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
				$("#publisherLogo").attr("src",SystemProp.profileServerUrl+result.data.logoFilePath+"?ts="+new Date().getTime());
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
	//杂志选择
	$("div[id^='publication']").unbind('click').live('click',function(e){
		e.preventDefault();
		if($(this).hasClass("current")) return;
		var publicationId = $(this).attr("id").split('_')[1];
		
		window.location.href = SystemProp.appServerUrl+"/publish/pcenter-publisher.action?publicationId="+publicationId;
	});
	//publisher-reader------------------------------------------------
	$("[name='pubReader']").bind('click',function(e){
		e.preventDefault();
		var issueId = $(this).parent().attr("publisherIssueId");
		window.location.href = SystemProp.appServerUrl+"/publish/mag-read!pubReader.action?id="+issueId+"&pageId=1&random="+Math.random();
	});
	
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
			url:SystemProp.appServerUrl + "/publish/publisher-upload!reDealSwfJpg.action",
			type : "POST",
			data : {'dealType':dealType,'issueId':issueId,'pageId':pageId},
			success: callback
		});
	});
	
	fnSetFooterHeight();
});
