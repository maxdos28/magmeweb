<#import "tablePageBar.ftl" as tablePageBar>

<script>
var curPageNum=${pageNum!"1"};
function turnPage(pageNum){
	curPageNum=pageNum;
	var data=form2object('searchForm');
	data.pageNum=pageNum;
	data.pageSize=${pageSize!"10"};
	var callback=function(rs){
		$("#tablePageBarContainer").html(rs);
		fnReadyTable();
	}
	
	$.ajax({
		url:SystemProp.appServerUrl+"/ad/manage-ad-side!getListAjax.action",
		type : "POST",
		data : data,
		success: callback
	});
}

$(function(){
	$("#submitSearchFormBtn").click(function(){
		turnPage(1);
	});
	
	$("a[name='updateStatusAdSide']").unbind().live("click",function(e){
		var obj=$(this);
		var data={"id":obj.attr("adSideId"),"status":obj.attr("status")};
		
		var callback=function(rs){
			if(rs.code==200){
				var ad=rs.data.advertise;
				alert("操作成功",function(){
					var html="";
					if(data.status==2){
						html='<a name="updateStatusAdSide" adSideId="'+data.id+'" status="3" href="javascript:void(0)">下架</a>';
					}
					if(data.status==3){
						html='<a name="updateStatusAdSide" adSideId="'+data.id+'" status="2" href="javascript:void(0)">发布</a>';
					}
					obj.parents("tr").find("td[name='updateStatus']").html(html);
				});
			}else{
				alert(rs.message);
			}		
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/ad/manage-ad-side!updateStatusJson.action",
			type : "POST",
			dataType : "json",
			data:data,
			success: callback
		});		
	});
	
	$("a[name='editAdSide']").unbind().live("click",function(e){
		e.preventDefault();
		var content = $("#fancybox-content");
		var dialogClose = $.fancybox.close;
		$.fancybox.close = function(){
			dialogClose();
			if($("#addAd")) $("#addAd").remove();			
		};
		
		var obj=$(this);
		var adSideId=obj.attr("adSideId");
		var callback = function(result){
			$("body").append(result);
			datePickerFun($("#sideAdValidBeginTime"));
			datePickerFun($("#sideAdValidEndTime"));
			$addAd = $("#addAd");
			$addAd.fancybox();
			
			$("#editAdSideFormCancelBtn",content).click(function(){$.fancybox.close();});
			$("#editAdSideFormSubmitBtn",content).click(function(){
				var data=form2object('editAdSideForm');
				var callback = function(result){
					if (result.code == 200) {
						alert("保存成功",function(){
							var pa=obj.parents("tr").eq(0);
							if(data.id){
								pa.find("td[name='description']").text(data.description);
								var categoryName=$("#editAdSideForm").find("select[name='categoryId'] option:selected").text();
								pa.find("td[name='categoryName']").text(categoryName);
								pa.find("td[name='validBeginTime']").text(data.validBeginTime);
								pa.find("td[name='validEndTime']").text(data.validEndTime);
								pa.find("td[name='pos']").text(data.pos);								
							}else{
								turnPage(curPageNum);
							}
							$.fancybox.close();
						});
					}else{
						alert(result.message);
					}
				}
				
				$.ajaxFileUpload(
					{
						url : SystemProp.appServerUrl+"/ad/manage-ad-side!saveJson.action",
		                secureuri : false,
		                data : data,
		                fileElementId : "imageFile",
		                content : $("#editAdSideForm"),
		                dataType : "json",
		                success : callback,
		                //服务器响应失败处理函数
		                error : function (data, status, e) {
		                	alert("error");
		                }
		            }
		        );
			});
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/ad/manage-ad-side!editAjax.action",
			type : "POST",
			dataType : "html",
			data: {"id":adSideId},
			success: callback
		});
	});	
	
	function datePickerFun($dateInput){
		var myDate = new Date();
		var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
		var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
		var date= myDate.getFullYear()+"-"+month+"-"+datetime; 
	
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
				//$dateInput.DatePickerHide();
			}
		});
	}		
});
</script>

        <div class="conMiddleRight conManageAdSideAd">
        	<div class="conTopTools">
            	<fieldset class="clearFix">
            		<form id="searchForm" method="post">
                	<div class="clearFix">
                        <div class="floatr">
                            <em><a id="submitSearchFormBtn" href="javascript:void(0)" class="btnBS">搜索</a></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">广告名称</em>
                            <em><input name="description" value="${description!""}" type="text" class="input g130" /></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">所属类别</em>
                            <em>
                            	<select name="categoryId">
											<option value="">全部类别</option>
		                           <#if categoryList??>
		                                <#list categoryList as category>
		                                    <option value="${category.id}" <#if categoryId??&&categoryId==category.id >selected="true"</#if>>${category.name}</option>
		                                </#list>
		                            </#if>
		                        </select>
                            </em>
                        </div>
                        <div class="floatl g290">
                            <em class="title">创建时间</em>
                            <em><input type="text" id="sideAdBeginTime" name="beginTime" value="${(beginTime?string("yyyy-MM-dd"))!""}" readonly="true" class="input g90" /></em>
                            <em>--</em>
                            <em><input type="text" id="sideAdEndTime" name="endTime" value="${(endTime?string("yyyy-MM-dd"))!""}" readonly="true" class="input g90" /></em>
                        </div>
                    </div>
                    </form>
                </fieldset>
            </div>
            <div id="tablePageBarContainer" class="conB">
            	<@tablePageBar.manageAdSide />
            </div>
            
            
		</div>