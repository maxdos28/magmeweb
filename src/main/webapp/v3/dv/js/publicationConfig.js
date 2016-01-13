
$(function(){
	var nowDate = new Date(),
	$sDate = $("#createDate");
	addDatePicker($sDate,nowDate);
	//外链 begin
	var publicationUrlValueSelect = getUrlValue("publicationid");
	var publicationCreate = getUrlValue("create");
	if(publicationUrlValueSelect){
		$("select#publicationOwn > option[value='"+publicationUrlValueSelect+"']").attr("selected",true);
		selectComm(publicationUrlValueSelect);
	}
	
	if(publicationCreate){
		resetFormComm();
	}
	//外链 end
	
	$("#pubNameLogo").hide();
	
	
	$("#help_publisher").live("click",function(){
		$("#adminQuestionComm").fancybox();
	});
	$("#helpCommIframeClose").live("click",function(){
		$.fancybox.close();
	});
	
	//检测英文名称是否重复
	$("#englishname").live('change',function(){
		var data = {};
		data["publication.englishname"]=$("#englishname").val()||"";
		var callback =function(result){
			var checkBackValue = result.data.checkStatus;
			if(checkBackValue=='1')//可以使用
			{$("#tipCheckMessage").html("可以使用");
			}else if(checkBackValue=='2'){//重复
				$("#englishname").val("");
				$("#tipCheckMessage").html("已存在");
			}else{//空
				$("#tipCheckMessage").html("不能为空");
			}
			//alert(result.data.checkStatus);
		}
		
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/publication-config!checkEnglishName.action',
			type : 'post',
			dataType : 'json',
			data :data,
			success : callback
		});
	})

	//选择杂志
	$("select#publicationOwn").unbind().live('change',function(e){
		var obj = $(this);
		if(obj.val()){
			selectComm(obj.val());
		}else{
			resetFormComm();
		}
		
	});
	
	function selectComm(id){
		$("#pubNameLogo").show();
		var data ={"publicationId":id};
		$("#editPublicationForm input[name='categorybox']").removeAttr("checked");
		$("input[name='whratio']").removeAttr("checked");
		var callback=function(result){
			if(result.code!=200){
				alert(result.message);
				return;
			}
			var po = result.data.publication;
			var podata = po.publicationData;
			var pcRel = po.categoryList;
			//logo  begin
			var logoFilePath=po.logo;
			if(logoFilePath){
				var logoArr=logoFilePath.split("/");
				logoArr[3]="172_"+logoArr[3];
				var logoUrl=logoArr.join("/");
				$("#publicationLogo").attr("src",SystemProp.publishProfileServerUrl+logoUrl+"?ts="+new Date().getTime());
			}else{
				$("#publicationLogo").attr("src",SystemProp.staticServerUrl+"/v3/images/head150.gif?ts="+new Date().getTime());
			}
			//logo end
			
			// 已选类目 begin
			//var text = "";
			///$.each(pcRel,function(key,value){
			///	var tempid = value.id
			///	var checkObj = $("#editPublicationForm input[name='categorybox'][value='"+tempid+"']").attr("checked","checked");
			///	text += "," + $(checkObj).parent().text();
			///});
			//text = text.slice(1);
			///$("#showSorts").val(text);
			// 已选类目 end
			
			$("#editPublicationForm input[name='id']").val(po.id);
			$("#editPublicationForm input[name='name']").val(po.name);
			$("#editPublicationForm input[name='englishname']").val(po.englishname);
			$("#editPublicationForm textarea[name='description']").val(po.description);
			$("#editPublicationForm input[name='address']").val(po.address);
			$("input[@type=radio][name='whratio'][value='"+po.whratio+"']").attr("checked","checked");//单选
			$("#editPublicationForm input[name='issn']").val(po.issn);
			$("#editPublicationForm input[name='publisher']").val(po.publisher);
			$("#editPublicationForm select[name='issueType']").val(po.issueType);//下拉菜单
			$("#editPublicationForm select[name='languageId']").val(po.languageId);//下拉菜单
			$("#editPublicationForm select[name='sort']").val(po.categoryId);//下拉菜单
			$("#editPublicationForm input[name='weibo_uid']").val(po.weibo_uid);
			$("#editPublicationForm input[name='weibo_verifier']").val(po.weibo_verifier);
			$("input[@type=radio][name='isfree'][value='"+po.isFree+"']").attr("checked","checked");//单选
			$("input[@type=radio][name='isfree']").attr("disabled",true);
			$("#editPublicationForm select[name='pubType']").val(po.pubType);//下拉菜单
			$("#editPublicationForm textarea[name='assessment']").val(po.assessment);//编辑评论
			var tpdate=po.createDate;
			if(tpdate!=null)
				tpdate=tpdate.split("T")[0];
			$("#editPublicationForm input[name='createDate']").val(tpdate);//杂志上线时间
			
			if(po.signing==0){
				$("input[@type=checkbox][name='signing']").attr("checked","checked");//签约杂志
			}else{
				$("input[@type=checkbox][name='signing']").attr("checked",false);//签约杂志
			}
			
			//重置
			for(var i = 1; i <= 30; i++){
				$("#editPublicationForm input[name='data_c" + i + "']").val("");
			}
			$("#publicationTargetForm")[0].reset();
			//显示信息
			if(podata){
				for(var i = 1; i <= 41; i++){
					if(i >= 32 && i <=37 || i == 39){
						$("#publicationTargetForm select[name='data_c" + i + "'] > option[value='" + podata["c" + i] + "']").attr("selected",true);
					} else {
						$("#editPublicationForm input[name='data_c" + i + "']").val(podata["c" + i]);
					}
				}
				
//				$("#publicationTargetForm input[name='data_c31']").val(podata.c31);//男性
//				$("#publicationTargetForm input[name='data_c40']").val(podata.c40);//女性
//				$("#publicationTargetForm select[name='data_c32'] > option[value='"+podata.c32+"']").attr("selected",true);
//				$("#publicationTargetForm select[name='data_c33'] > option[value='"+podata.c33+"']").attr("selected",true);
//				$("#publicationTargetForm select[name='data_c34'] > option[value='"+podata.c34+"']").attr("selected",true);
//				$("#publicationTargetForm select[name='data_c35'] > option[value='"+podata.c35+"']").attr("selected",true);
//				$("#publicationTargetForm select[name='data_c36'] > option[value='"+podata.c36+"']").attr("selected",true);
//				$("#publicationTargetForm select[name='data_c37'] > option[value='"+podata.c37+"']").attr("selected",true);
//				$("#publicationTargetForm input[name='data_c38']").val(podata.c38);//已婚
//				$("#publicationTargetForm input[name='data_c41']").val(podata.c41);//未婚
//				$("#publicationTargetForm select[name='data_c39'] > option[value='"+podata.c39+"']").attr("selected",true);
			}
		}
		
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/publication-config!doPojoJson.action',
			type : 'post',
			dataType : 'json',
			data :data,
			success : callback
		});
	}
	
	function resetFormComm(){
		$("#editPublicationForm")[0].reset();
		$("#publicationTargetForm")[0].reset();
		$("#editPublicationForm input[name='id']").val("");
		$("input[name='whratio']").removeAttr("checked");
		$("#publicationLogo").attr("src","/v3/images/head150.gif");
		$("#publicationOwn option[selected='selected']").attr("selected",false);
		$("input[@type=radio][name='isfree']").attr("disabled",false);
		$("#pubNameLogo").hide();
		$("#tipError").html("");
	}
	
	//男女比例
	$("#data_c31").keyup(function(){
		suitableValue($(this).val(), true, 31, 40);
	});
	$("#data_c40").keyup(function(){
		suitableValue($(this).val(), false, 31, 40);
	});
	//婚姻比例
	$("#data_c38").keyup(function(){
		suitableValue($(this).val(), true, 38, 41);
	});
	$("#data_c41").keyup(function(){
		suitableValue($(this).val(), false, 38, 41);
	});
	
	//自动组合总和为100的2个数，非法时填入2个空格
	function suitableValue(v1, isFirst, el1Id, el2Id){
		var v2 = "";
		if(checkObjNum(v1) && (v1 >= 0 && v1 <= 100)){
			v2 = (100 - v1);
		}else{
			v1 = "";
		}
		$("#data_c" + el1Id).val(isFirst ? v1 : v2);
		$("#data_c" + el2Id).val(isFirst ? v2 : v1);
	}
	//检验内容是否为数字
	function checkObjNum(vl){
		if(vl){
			var patrn=/^[0-9]{1,20}$/; 
			if (!patrn.exec(vl)) return false;
			return true; 
		}else{
			return fasle;
		}
	}
	
	//新建
	$("#newPublicationFormSubmit").click(function(){
		resetFormComm();
	});

	//杂志定位
	$("#publicationTarget").click(function(){
		$("#adminMgzTarget").fancybox();
	});
	$("#publicationTargetFormSubmit").live('click',function(){
		$.fancybox.close();
	});
	
	//表单绑定
	$("#editPublicationFormSubmit").unbind('click').live('click',function(e){
		e.preventDefault();
		var publication = form2object('editPublicationForm');
		var targetForm = form2object('publicationTargetForm');
		//前端校验 begin
		//var tipsError=$("#tipError",contentV);
		var vilBackStatus = false;
		if(!$.trim($("#name").val()) ){
			$("#tipError").html("杂志名称必须填写").show(); 
			return;
		}
		if(!$.trim($("#englishname").val()) ){
			$("#tipError").html("英文名称必须填写").show(); 
			return;
		}
		var vilBackStatus = false;
		if(!$.trim($("#description").val()) ){
			$("#tipError").html("杂志描述必须填写").show(); 
			return;
		}
		if(!$.trim($("#createDate").val()) ){
			$("#tipError").html("创刊时间必须填写").show(); 
			return;
		}
		
		if(!$.trim($("#address").val()) ){
			$("#tipError").html("杂志社地址必须填写").show(); 
			return;
		}
		var vilBackStatus = false;
		if(!$.trim($("#sort").val()) ){
			$("#tipError").html("杂志类目必须填写").show(); 
			return;
		}
		
		if(!$.trim($("#whratio:checked").val()) ){
			$("#tipError").html("杂志排列必须填写").show(); 
			return;
		}
		var vilBackStatus = false;
		if(!$.trim($("#issn").val()) ){
			$("#tipError").html("杂志刊号必须填写").show(); 
			return;
		}
		if(!$.trim($("#publisher").val()) ){
			$("#tipError").html("出版人必须填写").show(); 
			return;
		}
		var vilBackStatus = false;
		if(!$.trim($("#issueType").val()) ){
			$("#tipError").html("期刊类型必须填写").show(); 
			return;
		}
		if(!$.trim($("#languageId").val()) ){
			$("#tipError").html("语言必须填写").show(); 
			return;
		}
		var vilBackStatus = false;
		if(!$.trim($("#weibo_uid").val()) ){
			$("#tipError").html("新浪微博账号必须填写").show(); 
			return;
		}
		if(!$.trim($("#weibo_verifier").val()) ){
			$("#tipError").html("新浪微博效验码").show(); 
			return;
		}
		//前端校验 end
		
		
		var publicationData = {};
		//数据封装 begin
		//基本信息 publicationList
		publicationData["publication.id"]=publication.id||"";
		publicationData["publication.name"]=publication.name||"";
		publicationData["publication.description"]=publication.description||"";
		publicationData["publication.issueType"]=publication.issueType||"";//下拉菜单
		publicationData["publication.languageId"]=publication.languageId||"";//下拉菜单
		publicationData["publication.categoryIdString"]=publication.sort||"";//类目数组---修改为下拉菜单
		publicationData["publication.whratio"]=publication.whratio||"";//单选
		publicationData["publication.address"]=publication.address||"";
		publicationData["publication.issn"]=publication.issn||"";
		publicationData["publication.englishname"]=publication.englishname||"";
		publicationData["publication.publisher"]=publication.publisher||"";
		publicationData["publication.weibo_uid"]=publication.weibo_uid||"";
		publicationData["publication.weibo_verifier"]=publication.weibo_verifier||"";
		publicationData["publication.isFree"]=publication.isfree||"";//单选
		publicationData["publication.pubType"]=publication.pubType||"";//下拉菜单

		//签约杂志
		if($("#signing").attr("checked")=='checked'){
			publicationData["publication.signing"]=0;
		}else{
			publicationData["publication.signing"]=1;
		}
		
		publicationData["publication.createDate"]=publication.createDate||"";//杂志创建时间
		publicationData["publication.assessment"]=publication.assessment||"";//下拉菜单
		
		//职位信息
		publicationData["publication.publicationData.c1"]=publication.data_c1||"";
		publicationData["publication.publicationData.c2"]=publication.data_c2||"";
		publicationData["publication.publicationData.c3"]=publication.data_c3||"";
		publicationData["publication.publicationData.c4"]=publication.data_c4||"";
		publicationData["publication.publicationData.c5"]=publication.data_c5||"";
		publicationData["publication.publicationData.c6"]=publication.data_c6||"";
		publicationData["publication.publicationData.c7"]=publication.data_c7||"";
		publicationData["publication.publicationData.c8"]=publication.data_c8||"";
		publicationData["publication.publicationData.c9"]=publication.data_c9||"";
		publicationData["publication.publicationData.c10"]=publication.data_c10||"";
		publicationData["publication.publicationData.c11"]=publication.data_c11||"";
		publicationData["publication.publicationData.c12"]=publication.data_c12||"";
		publicationData["publication.publicationData.c13"]=publication.data_c13||"";
		publicationData["publication.publicationData.c14"]=publication.data_c14||"";
		publicationData["publication.publicationData.c15"]=publication.data_c15||"";
		publicationData["publication.publicationData.c16"]=publication.data_c16||"";
		publicationData["publication.publicationData.c17"]=publication.data_c17||"";
		publicationData["publication.publicationData.c18"]=publication.data_c18||"";
		publicationData["publication.publicationData.c19"]=publication.data_c19||"";
		publicationData["publication.publicationData.c20"]=publication.data_c20||"";
		publicationData["publication.publicationData.c21"]=publication.data_c21||"";
		publicationData["publication.publicationData.c22"]=publication.data_c22||"";
		publicationData["publication.publicationData.c23"]=publication.data_c23||"";
		publicationData["publication.publicationData.c24"]=publication.data_c24||"";
		publicationData["publication.publicationData.c25"]=publication.data_c25||"";
		publicationData["publication.publicationData.c26"]=publication.data_c26||"";
		publicationData["publication.publicationData.c27"]=publication.data_c27||"";
		publicationData["publication.publicationData.c28"]=publication.data_c28||"";
		publicationData["publication.publicationData.c29"]=publication.data_c29||"";
		publicationData["publication.publicationData.c30"]=publication.data_c30||"";
		publicationData["publication.publicationData.c31"]=targetForm.data_c31||"";
		publicationData["publication.publicationData.c32"]=targetForm.data_c32||"";
		publicationData["publication.publicationData.c33"]=targetForm.data_c33||"";
		publicationData["publication.publicationData.c34"]=targetForm.data_c34||"";
		publicationData["publication.publicationData.c35"]=targetForm.data_c35||"";
		publicationData["publication.publicationData.c36"]=targetForm.data_c36||"";
		publicationData["publication.publicationData.c37"]=targetForm.data_c37||"";
		publicationData["publication.publicationData.c38"]=targetForm.data_c38||"";
		publicationData["publication.publicationData.c39"]=targetForm.data_c39||"";
		publicationData["publication.publicationData.c40"]=targetForm.data_c40||"";
		publicationData["publication.publicationData.c41"]=targetForm.data_c41||"";
		//数据封装 end
		
		
		var content_Form = $("#editPublicationForm")
		var name = $("#name",content_Form).val();
		
		var callback=function(result){
			if(result.code==200){
				alert("保存成功");
				//重新加载下拉菜单
				$("#publicationOwn").empty();
				$("<option value=\"\">新建杂志</option>").appendTo("#publicationOwn");
				var pojoPublication = result.data.publicationList;
				var pojoPub = null;
				$.each(pojoPublication,function(key,value){
					if(key==0){
						pojoPub = value;
					}
					$("<option value='"+value.id+"'>"+value.name+"</option>").appendTo("#publicationOwn");
				});
				if(publication.id){
					$("#publicationOwn > option[value='"+publication.id+"']").attr("selected","selected");	
				}else{
					if(pojoPub){
					$("#publicationOwn > option[value='"+pojoPub.id+"']").attr("selected","selected");
					$("input[name='id'][type='hidden']").val(pojoPub.id);
					$("#pubNameLogo").show();
					}				
				}
			}
			else{
				alert(result.message);
			}
			
		}
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/publication-config!doJson.action',
			type : 'post',
			dataType : 'json',
			data : publicationData,
			success : callback
		});
	});
	
	//上传logo绑定事件
	if ($.browser.msie){
	    // IE suspends timeouts until after the file dialog closes
		$("#logoFile").live('change',function(event){
	        setTimeout(function(){
		    ajaxFileUpload();	
	        }, 1);
	    });
	}else{
	    // All other browsers behave
		$("#logoFile").live("change",ajaxFileUpload);
	}
	
	function ajaxFileUpload(){
		var fileUrl = $("#logoFile").val();
    	if(!fileUrl){
    		return;
    	}
    	var publication = form2object('editPublicationForm');
    	if(!publication.id){
    		//alert("未选择杂志");
    		return;
    	}
    	 $.ajaxFileUpload(
            {	
                url:SystemProp.appServerUrl+"/new-publisher/publication-config!uploadLogoJson.action?publicationId="+publication.id,//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:"logoFile",//文件上传空间的id属性  <input type="file" id="file" name="file" />
                dataType: "json",//返回值类型 一般设置为json
                success: function (rs, status)  //服务器成功响应处理函数
                { 
            		if(rs.code!=200){
            			alert(rs.message);	
            		}else{
				 		var url = SystemProp.appServerUrl + "/new-publisher/publication-config!updatePublicationLogo.action";
						var data = {"publicationId":publication.id,"x":0,"y":0,"width":0,"height":0,"logoFileName":rs.data.logoFileName};
						var callback = function(result){
							if(!result) return;
							if(result.code != 200){
								alert(rs.message)
							}else{
								//对页面的用户头像修改
								var logoFilePath=result.data.logoFilePath;
								var logoArr=logoFilePath.split("/");
								//tmpLogo="30_"+logoArr[3];
								logoArr[3]="172_"+logoArr[3];
								var logoUrl=logoArr.join("/");
								//logoArr[3]=tmpLogo;
								//var miniLogoUrl=logoArr.join("/");
								$("#publicationLogo").attr("src",SystemProp.publishProfileServerUrl+logoUrl+"?ts="+new Date().getTime());
							}
						};						
						$.ajax({
							url:url,
							type : "POST",
							data : data,
							success: callback
						});
                    }
                },
                //服务器响应失败处理函数
                error: function (data, status, e) {
                    return;
                }
            }
        );
	}
});