$(function(){
	
	//全选
	$("#publicationAll").live("click",function(){
		//var checkFlag =publicationAll
		if($(this).attr("checked")){
			$("input[name='tPublication']").attr("checked",true);
		}else{
			$("input[name='tPublication']").attr("checked",false);
		}
	});
	
	$("input[name='userType']").change(function(){
		var selectUserType = $("input[name='userType']:checked").val();
		if(selectUserType==1){
			$(".con36 .selectList div p").html("<a class='current'>暂无</a>");
			$(".con36 .list:eq(1) div a").removeClass("current");
		}
	});
	
	//保存app--确认
	$("#addApp").click(function(){
		$("#app_name").html($("input[name='name']").val());
		$("#app_kewWord").html($("input[name='appKeyword']").val());
		$("#app_description").html($("input[name='description']").val());
		$("#app_info").html($("input[name='information']").val());
		$("#app_secondType").html($("select[name='secondType']").val());
		if($("input[name='userType']:checked").val()==0){
			$("#app_user").html("magme");
		}else{
			$("#app_user").html("出版商");
		}
		var publicationStr = "";
		$.each($("input[name='tPublication']:checked"),function(k,it){
			publicationStr += ""+$(this).attr("pubname")+"、";
		});
		if(publicationStr.length>1){
			publicationStr =publicationStr.substring(0,publicationStr.length-1);
		}
		$("#app_publication").html(publicationStr);
		$("#adminAppConfirm").fancybox();
	});
	
	//关闭查看对话框
	$("#viewAppChange").live("click",function(){
		$.fancybox.close();
	});
	
	//保存app
	$("#viewAppOk").live("click",function(){
		var selectUserType = $("input[name='userType']:checked").val();
		var pubId="";
		var publicationId;
		var publisherId = $("a.selected").attr("publsherid");
		$.each($("input[name='tPublication']:checked"),function(k,it){
			var obj= $(this);
			if(selectUserType==0){
				pubId += ""+obj.val()+",";
			}else{//出版商
				pubId += ""+obj.val()+",";
				publicationId=obj.val();
			}
		});
		if(!pubId) {alert("请选择app要加入的杂志"); $.fancybox.close(); return;}
		
		var data ={};
		data.name=$("input[name='name']").val();
		data.firstType=$("select[name='firstType']").val();
		data.secondType=$("select[name='secondType']").val();
		data.userType=selectUserType;
		data.information=$("input[name='information']").val();
		data.description=$("input[name='description']").val();
		data.appKeyword=$("input[name='appKeyword']").val();
		data.publicationStr=pubId;
		if(selectUserType==1){
			data.publicationId=publicationId;
			data.publisherId=publisherId;
		}
		
		function callback(result){
			if(result.code!=200){
				alert(result.message);
			}else{
				alert("保存成功");
				$.fancybox.close();
				window.location.href=SystemProp.appServerUrl+"/new-publisher/app-manage.action";
			}
			
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-new!addIosApp.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});
	
	//杂志社确定-->加载对应的杂志
	$("#publisherOk").click(function(){
		var publisherId="";
		$("#publicationAll").attr("checked",false);
		$.each($("a.selected"),function(k,it){
			var obj= $(this);
			publisherId += ""+obj.attr("publsherid")+",";
		});
		if(!publisherId){alert("请选择对应的杂志社");return;}
		var data = {};
		data.publisherStr=publisherId;
		function callback(result){
			if(result.code!=200){
				alert(result.message);
				return;
			}else{
				if(result.data){
					var tbody="";
					$.each(result.data.publicationList,function(i,pub){
						tbody +=" <tr>";
                        tbody +=" <td><label><input type='checkbox' name='tPublication' value='"+pub.id+"' pubName='"+pub.name+"' /></label></td>";
                        tbody +=" <td>&nbsp;</td>";
                        tbody +=" <td>"+pub.name+"</td>";
                        tbody +=" <td>&nbsp;</td>";
                        tbody +="</tr>";
                    
					});
					$("#publicationTbody").html(tbody);
				}
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/app-new!publicationListByPublisherId.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	});	
	
})