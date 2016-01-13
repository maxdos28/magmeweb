<#import "../components/publishadminheader.ftl" as publisheradminheader>
<#import "../ad/adadmin.ftl" as adadmin>

<#import "../dialog/adCategorySel.ftl" as viewAdType>
<#import "../dialog/createAd.ftl" as addAdvertiseDialog>
<#import "../dialog/editAdAgencyInfo.ftl" as editAdAgencyInfo>
<#import "../dialog/uploadAdAgencyLogo.ftl" as uploadAdagencyLogo>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/imgAreaSelect/imgareaselect-default.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/channelManage.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.imgareaselect.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/adCenter.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/admessage.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/adCreate.js"></script>
<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->

<script>
	$("a[name='updateStatusAdvertise']").unbind().live("click",function(e){
		var obj=$(this);
		var data={"id":obj.attr("advertiseId"),"status":obj.attr("status")};
		
		var callback=function(rs){
			if(rs.code==200){
				var ad=rs.data.advertise;
				alert("操作成功",function(){
					obj.parents("tr").find("td[name='status']").text(ad.statusMsg);
					
					var html="";
					if(ad.status==5){
						html='<a name="updateStatusAdvertise" advertiseId="'+ad.id+'" status="7" href="javascript:void(0)">下架</a>';
					}
					if(ad.status==7){
						html='<a name="updateStatusAdvertise" advertiseId="'+ad.id+'" status="5" href="javascript:void(0)">发布</a>';
					}
					obj.parents("tr").find("td[name='updateStatus']").html(html);
				});
			}else{
				alert(rs.message);
			}		
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/ad/manage-advertise!updateStatusJson.action",
			type : "POST",
			dataType : "json",
			data:data,
			success: callback
		});		
		
		
	});
	$("a[name='viewAdvertise']").unbind().live("click",function(e){
		e.preventDefault();
		var content = $("#fancybox-content");
		var dialogClose = $.fancybox.close;
		$.fancybox.close = function(){
			dialogClose();
			if($("#viewAdDetail")) $("#viewAdDetail").remove();			
		};
		
		var obj=$(this);
		var advertiseId=obj.attr("advertiseId");
		var type=obj.attr("type");
		var callback = function(result){
			$("body").append(result);
			$viewAdDetail = $("#viewAdDetail");
			$viewAdDetail.fancybox();
			
			$("#editAdvertiseFormCancelBtn",content).click(function(){$.fancybox.close();});
			$("#editAdvertiseFormSubmitBtn",content).click(function(){
				var data=form2object('updateAdvertiseForm');
				
				var callback = function(rs){
					if(rs.code==200){
						alert("操作成功",function(){
							$.fancybox.close();
							
							if(obj.parents("tr").find("[name='title']").length>0&&data.title) 
								obj.parents("tr").find("[name='title']").text(data.title);
							
							if(obj.parents("tr").find("[name='remark']").length>0&&data.remark) 	
								obj.parents("tr").find("[name='remark']").text(data.remark);							
						});
					}else{
						alert(rs.message);
					}
				};
				
				$.ajax({
					url: SystemProp.appServerUrl+"/ad/manage-advertise!updateJson.action",
					type: "POST",
					dataType : "json",
					data: data,
					success: callback
				});
			});
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/ad/manage-advertise!viewAjax.action",
			type : "POST",
			dataType : "html",
			data: {"id":advertiseId,"type":type},
			success: callback
		});
	});	
</script>
</head>
<body>
	<!--header-->
	<@publisheradminheader.main 'advertise'/>
	<!--body-->
	<div class="body">
		<div class="body980">
		<!--topBar-->
		<div class="topBar clearFix">
	        <ul class="subNav">
	            <li><a href="javascript:void(0)">广告商中心</a></li>
	        </ul>
	    </div>
		<!--bodyContent-->
		<div class="bodyContent clearFix">
	    	 <@adadmin.main/>
	          ${body}
	    </div>
	</div>
</div>
  <#--广告商有编辑按钮-->
  <#if session_aduser?? && (session_aduser.level==2)>
	<@editAdAgencyInfo.main/>
	<@uploadAdagencyLogo.main/>
  </#if>
  <@viewAdType.main/>
  <@addAdvertiseDialog.main/>
</body>
</html>

