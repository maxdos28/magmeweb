var da = null;

function turnPage(pageNum){
			if ($("#datatable").length > 0)
			{
				$("#adDataForm").attr("action",SystemProp.appServerUrl+"/ad/manage-data!search.action?pageNum=" + pageNum + "&pageSize=10");
				$("#adDataForm").submit();
				return;
			}
			var categoryId = document.getElementById("categoryId").value;
			var pubId = document.getElementById("pubId").value;
			var issueId = document.getElementById("issueId").value;
			var adposDescription = document.getElementById("adposDescription").value;
			var adposKeywords = document.getElementById("adposKeywords").value;
			var adposid = document.getElementById("adposid").value;

			var callback=function(rs){
				da=rs.data;
				if(da!=null && da.adpositionList!=null){
					var lst = da.adpositionList;
					var $select1=$("#detaillst");
					$select1.empty();
					for(var i=0;i<lst.length;i++){
						var str = "<tr>";
						str += "<td><input id='chkpos' name='chkpos' type='checkbox' value='" + lst[i].id +  "' /></td>";
						str += "<td id='id'>"+lst[i].id+"</td>";
						str += "<td>"+lst[i].issueNumber+"</td>";
						str += "<td>"+lst[i].title+"</td>";
						str += "<td>"+lst[i].keywords+"</td>";
						str += "<td>"+lst[i].description+"</td>";
						str += "<td><a id='previewbtn' previewindex='"+ i +"' href='javascript:void(0)'>预览</a></td>";
						str += "<td><a id='adadvertiserbtn' adadvertiserindex='"+ i +"' href='javascript:void(0)'>添加</a></td>";
						str += "</tr>";
						$(str).appendTo($select1);
						//$("<tr/>").appendTo($select1);
						//$("<td/>").html("<input id='chkpos' name='chkpos' type='checkbox' value='" + lst[i].id +  "' />").appendTo($select1);
						///$("<td/>").attr("id","id").html(lst[i].id).appendTo($select1);
						//$("<td/>").html(lst[i].issueNumber).appendTo($select1);
						//$("<td/>").html(lst[i].title).appendTo($select1);
						//$("<td/>").html(lst[i].keywords).appendTo($select1);
						//$("<td/>").html(lst[i].description).appendTo($select1);
						//$("<td/>").html("<a id='previewbtn' previewindex='"+ i +"' href='javascript:void(0)'>预览</a>").appendTo($select1);
						//$("<td/>").html("<a id='adadvertiserbtn' adadvertiserindex='"+ i +"' href='javascript:void(0)'>添加</a>").appendTo($select1);
					}
					$("#createadtable").next().remove();
					$("#createadtable").after(da.pageBar.jsBar);
				}
			};

			$.ajax({
				type:"post",
				data:{"categoryId":categoryId,
					  "pubId":pubId,
					  "issueId":issueId,
					  "adDescription":adposDescription,
					  "adKeywords":adposKeywords,
					  "adid":adposid,
					  "pageNum":pageNum,
					  "pageSize":10},
				url:"/ad/manage-create!adpositionListSearch.action",
				success:callback,
				dataType:"json"
			});
}

$(document).ready(function(){
		//获取完整的年份(4位,1970-????)
		//获取当前月份(0-11,0代表1月)
		//获取当前日(1-31)
		var myDate = new Date();
		var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
		var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
		var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime; 

	    $("#categoryId").val(-1);
		$("#sear").click(function(){
			turnPage(1);
		});
		
		// 创建广告 -> 预览
		$("a[previewindex]").unbind('click').live("click", function(){
			var index = $(this).attr("previewindex");
			var lst = da.adpositionList;
			$("#hidIssueId").val(lst[index].issueId);
			$("#hidPageNo").val(lst[index].pageNo);
			$("#hidAdis").val(lst[index].id);
			$("#viewAdType").fancybox();
		});
		
		// 创建广告 -> 广告添加
		$("a[adadvertiserindex]").unbind('click').live("click", function(){
			var index = $(this).attr("adadvertiserindex");
			var lst = da.adpositionList;
			document.getElementById("adposIds").value = lst[index].id;

			$("#addAdvertiseDialog").fancybox();
		});

		//广告位管理 -> 广告位信息 -> 点击查看 -> 预览
		$("a[detailpreview]").unbind('click').live("click", function(){

			$("#hidIssueId").val($(this).attr("issue"));
			$("#hidPageNo").val($(this).attr("detailpreview"));
			$("#hidAdis").val($(this).attr("adid"));
			$("#viewAdType").fancybox();
		});

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

		$("#adType").live("change",function(){
			if ($(this).val() == 2) {
				$("#imgdivid").html("上传图片");
				$("#imgurl").html("图片链接");
				$("#mediadivid").css("visibility","visible");
				$("#linkdivid").css("visibility","visible");
				$("#jpgFile").val("");
				$("#linkdivid").val("");
			}
			if ($(this).val() == 3) {
				$("#imgdivid").html("上传视频");
				$("#mediadivid").css("visibility","visible");
				$("#jpgFile").val("");
				$("#linkurl").val("");
				$("#linkdivid").css("visibility","hidden");
			}
			if ($(this).val() == 1) {
				$("#imgurl").html("文字链接");
				$("#linkurl").val("");
				$("#mediadivid").css("visibility","hidden");
				$("#linkdivid").css("visibility","visible");
			}
		});
		
		$("#previewCancel").unbind('click').live('click',function(){
			$.fancybox.close();
		});

		$("#addcancel").unbind('click').live('click',function(){
			$.fancybox.close();
		});

		
		$("#multiad").click(function(){
			var idlst="";
			var checked = $("input[name=chkpos]:checkbox:checked");
			var n = checked.length;
			if (n == 0){
				alert('无选中！');
			}else{
				checked.each(function(i){
			    	idlst = idlst+','+$(this).val();
			    });
			    document.getElementById("adposIds").value = idlst;
			    $("#addAdvertiseDialog").fancybox();
			}
		});
		
		var checkCreateAdParameter = function(){
			var adInfo = form2object("editAdvertiseForm");
			var tipError = $("#tipError",$("#editAdvertiseForm"));
			if(!adInfo.title){
				tipError.html("*标题不能为空").show();
				return false;
			}
			
			if (adInfo.adType == 2 && !adInfo.jpgFile)
			{
				tipError.html("*请选择图片").show();
				return false;
			}
			if (adInfo.adType == 3 && !adInfo.jpgFile)
			{
				tipError.html("*请选择视频文件").show();
				return false;
			}
			if (adInfo.adType == 1 && !adInfo.linkurl)
			{
				tipError.html("*请输入链接").show();
				return false;
			}
			tipError.hide();
			return true;
		}
		
		$("#addsubmit").live('click',function(e){
			if(!checkCreateAdParameter()){
				return false;
			}
			
			var callback = function(result){
				var tipError = $("#tipError",$("#editAdvertiseForm"));
				if (result.code == 200) {
					$.fancybox.close();
					$("#sear").click();
				}else{
					tipError.html(result.message).show();
				}
			}
			
			$.ajaxFileUpload(
				{
					url : "/ad/advertise!addAdToPosJson.action",
	                secureuri : false,
	                data : form2object('editAdvertiseForm'),
	                fileElementId : "jpgFile",
	                content : $("#editAdvertiseForm"),
	                dataType : "json",
	                success : callback,
	                //服务器响应失败处理函数
	                error : function (data, status, e) {
	                	$("#tipError",content).html("文件不正").show();
	                }
	            }
	        );
		})

		//预览
		$("#previewDo").unbind('click').live('click',function(){
			var viewAdType=$("#viewAdType");
			var type=$("input[name=radioTypePreview]:checked",viewAdType).val();
			var hidIssueId =$("#hidIssueId").val();
			var hidPageNo =$("#hidPageNo").val();
			var hidAdis =$("#hidAdis").val();
			$.fancybox.close();
			window.open(SystemProp.appServerUrl + '/publish/mag-read!preview.action?id=' 
						+ hidIssueId + '&pageId=' + hidPageNo + '&previewType=' + type,"_blank");
		});

		// 快速创建
		$("#quickcreatead").click(function(){
			var idvalue = document.getElementById("issueId").value;
			if (idvalue == -1) {
				alert("请选择期刊");
				return;
			};
			
			window.open(SystemProp.appServerUrl + '/publish/mag-read!pubReader.action?id=' 
						+ idvalue,"_blank");
		});

		$("#seardata").click(function(){
			turnPage(1);
		});
		//广告数据页：日期控件
		datePickerFun($("#begintime"),nowDate);
		datePickerFun($("#endtime"),nowDate);
		
		//我创建的广告: 日期控件
		datePickerFun($("#myadcreatedTimeBegin"),nowDate);
		datePickerFun($("#myadcreatedTimeEnd"),nowDate);
		
		//我期刊的广告: 日期控件
		datePickerFun($("#magmeadcreatedTimeBegin"),nowDate);
		datePickerFun($("#magmeadcreatedTimeEnd"),nowDate);
		
		//麦米广告: 日期控件
		datePickerFun($("#mymgzcreatedTimeBegin"),nowDate);
		datePickerFun($("#mymgzcreatedTimeEnd"),nowDate);
		
		//侧栏广告: 日期控件
		datePickerFun($("#sideAdBeginTime"),nowDate);
		datePickerFun($("#sideAdEndTime"),nowDate);
		
	}
)


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



	//获取杂志下拉的数据
	function pubdatasouse(){
		var categoryId = document.getElementById("categoryId").value;
		if (categoryId == -1){
			$("#pubId").empty();
			$("<option/>").attr("value","-1").html("全部杂志").appendTo($("#pubId"));
			$("#issueId").empty();
			$("<option/>").attr("value","-1").html("全部期刊").appendTo($("#issueId"));
		}

		var callback=function(rs){
			da=rs.data;
			if(da!=null && da.publicationList!=null){
				var lst = da.publicationList;
				var $select1=$("#pubId");
				$select1.empty();
				$("<option/>").attr("value","-1").html("全部杂志").appendTo($select1);
				for(var i=0;i<lst.length;i++){
					$("<option/>").attr("value",lst[i].id).html(lst[i].name).appendTo($select1);
				}
			}
		};

		$.ajax({
			type:"post",
			data:{"categoryId":categoryId},
			url:"/ad/manage-create!pubSearch.action",
			success:callback,
			dataType:"json"
		});
	}

	//获取期刊下拉的数据
	function issuedatasouse(){
		var pubId = document.getElementById("pubId").value;
		var callback=function(rs){
			da=rs.data;
			if(da!=null && da.issueList!=null){
				var lst = da.issueList;
				var $select1=$("#issueId");
				$select1.empty();
				$("<option/>").attr("value","-1").html("全部期刊").appendTo($select1);
				for(var i=0;i<lst.length;i++){
					$("<option/>").attr("value",lst[i].id).html(lst[i].issueNumber).appendTo($select1);
				}
			}
		};

		$.ajax({
			type:"post",
			data:{"pubId":pubId},
			url:"/ad/manage-create!issueSearch.action",
			success:callback,
			dataType:"json"
		});
	}
