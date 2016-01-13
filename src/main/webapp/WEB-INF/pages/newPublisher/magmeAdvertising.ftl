<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />   
<script>
	//分页 杂志对应的期刊
	var adPageCount = ${adPageNo!''};//广告的总页数
	var currentPage = ${adCurrentPage!''};//广告的当前页码
	
$(function(){
	//搜索的默认条件
	var commAdLevel=$("input[name='adLevel']:radio:checked").val();
	var commAdStatus=$("input[name='adStatus']:radio:checked").val();
	var commAdTypeSelect=$("#adTypeSelect").val();
	var commAdStartTime =$("#magTimeBegin").val();
	var commAdEndTime =$("#magTimeEnd").val();
	var commAdMagName=$("#adMgName").val();
	var commAdName=$("#adName").val();
	if(!commAdStartTime)commAdStartTime="";
	if(!commAdEndTime)commAdEndTime="";
	if(!commAdMagName)commAdMagName="";
	if(!commAdName)commAdName="";
	//下架adDown
	$("#adDown").unbind("click").live("click",function(){
		var obj = $(this);
		var typeTemp = commAdTypeSelect;
		if(typeTemp==2){
			adComm(obj.attr("adid"),0);
		}else{
			adComm(obj.attr("adid"),7);
		}
	});
	//上架
	$("#adUp").unbind("click").live("click",function(){
		var obj = $(this);
		var typeTemp = commAdTypeSelect;
		if(typeTemp==2){
			adComm(obj.attr("adid"),1);
		}
	});
	//跳转到期刊
	$("#tbodyContext a[name='publicationName']").unbind("click").live("click",function(){
		var obj = $(this);
		var url = SystemProp.appServerUrl + "/new-publisher/magazine-list!to.action?publication.name=" + obj.attr("publicationName");
		url = encodeURI(url);
		document.location.href = url;
	});
	
	//互动广告的编辑
		$("#tbodyContext a[name='adEdit']").unbind().live("click",function(){
		var obj = $(this);
		var data = {};
		data["advertise.id"]=obj.attr("adid");
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-advertising!doEditTo.action",
			type: "POST",
			dataType: "html",
			data: data,
			success: function(result){
				$("body").append(result);
			}
		});
	});
	
	$("#manageAdminEmail").click(function(){
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-admin-email!to.action",
			type : "POST",
			dataType : "html",
			data: {},
			success: function(result){
				$("body").append(result);
			}
		});
	});
	
	
	//广告搜索
	$("#adSearch").live("click",function(){
		commAdLevel=$("input[name='adLevel']:radio:checked").val();
		commAdStatus=$("input[name='adStatus']:radio:checked").val();
		commAdTypeSelect=$("#adTypeSelect").val();
		commAdStartTime =$("#magTimeBegin").val();
		commAdEndTime =$("#magTimeEnd").val();
		commAdMagName=$("#adMgName").val();
		commAdName=$("#adName").val();
		if(!commAdStartTime)commAdStartTime="";
		if(!commAdEndTime)commAdEndTime="";
		if(!commAdMagName)commAdMagName="";
		if(!commAdName)commAdName="";
		pageComm2(1);
		initPage();
	});
	
	//广告操作
	function adComm(adid,statusValue){
		var data ={};
		var typeTemp = commAdTypeSelect;
		data.type= typeTemp;
		data["advertise.id"]=adid;
		data["advertise.status"]=statusValue;
		var callback = function(){
			if(typeTemp==2){
				if(statusValue==0){//下架操作
					$("#statusTD[adid='"+adid+"']").html("下架");
					$("#adDown[adid='"+adid+"']").html("上架");
					$("#adDown[adid='"+adid+"']").attr("id","adUp");
				}else if(statusValue==1){//上架操作
					$("#statusTD[adid='"+adid+"']").html("通过审核");
					$("#adUp[adid='"+adid+"']").html("下架");
					$("#adUp[adid='"+adid+"']").attr("id","adDown");
				}
			}else{
				if(statusValue==7){//下架操作
					$("#statusTD[adid='"+adid+"']").html("下架");
					$("#tdEditEd[adid='"+adid+"']").html("");
					$("#tdDelUp[adid='"+adid+"']").html("");
				}
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/advertising-list!checkAdJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//广告 公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	
	//页面跳转
$("#toPageOk").live("click",function(){
	var currentPage = $("#toPageValue").val();
	if(currentPage>adPageCount) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
	if(currentPage<=0){currentPage=1} 
	$("#eventListPage").html("");
	$("#eventListPage").pagination(adPageCount, {
		num_edge_entries: 1, //边缘页数
		num_display_entries: 15, //主体页数
		callback:pageselectCallback,
		items_per_page: 1, //每页显示1项
		current_page:currentPage-1, 
		prev_text: "上页",
		next_text: "下页"
	});
});
	
	//广告公共
	function pageComm(currentPage){
		var data = {};
		data.adCurrentPage=currentPage;
		data["advertise.level"]=commAdLevel;
		data["advertise.status"]=commAdStatus;
		data["advertise.adType"]=commAdTypeSelect;
		data["advertise.startTime"]=commAdStartTime;
		data["advertise.endTime"]=commAdEndTime;
		data["advertise.publicationName"]=commAdMagName;
		data["advertise.title"]=commAdName;
		var typeTemp =commAdTypeSelect;
		var callback =function(result){
			//alert(result.message);
			$("#tbodyContext").html("");
			if(result.data.adList){
				var trStr = "";
				$.each(result.data.adList,function(k,p){
					trStr +="<tr>";
							trStr +=" <td class=\"tLeft\">"+p.title+"</td>";
							trStr +=' <td class=\"tLeft\"><a href="javascript:void(0)" name="publicationName"  publicationName="' + p.publicationName + '" >'+p.publicationName+p.issueNumber+'</a></td>';
							if(p.startTime){
								 trStr +=" <td>"+p.startTime.substring(0,10)+"</td>";
							}else{
								 trStr +=" <td>&nbsp;</td>";
							}
							if(p.endTime){
								trStr +=" <td>"+p.endTime.substring(0,10)+"</td>";
							}else{
								trStr +=" <td>&nbsp;</td>";
							}
							typeStr = "";
							tdDel = "";
							tdEdit = "";
							if(typeTemp==2){
								typeStr="互动内容";
								trStr +=" <td>"+typeStr+"</td>";
								trStr +=" <td>&nbsp;</td>";
								if(p.status==0){
									 trStr +=" <td id='statusTD' adid='"+p.id+"'>下架</td>";
									 if(p.userTypeId==3){//编辑
									 	tdDel = "<a class='btn' id='adUp' adid='"+p.id+"' href='javascript:void(0);'>上架</a>";
									}
								}else{
									 trStr +=" <td id='statusTD' adid='"+p.id+"'>通过审核</td>";
									  if(p.userTypeId==3){//编辑
									 	tdDel = "<a class='btn' id='adDown' adid='"+p.id+"' href='javascript:void(0);'>下架</a>";
									}
								}
								
							}else{
								if(p.adType){
									if(p.adType==1 || p.adType==2 || p.adType==3){
										typeStr="互动广告";
									}else if(p.adType==5){
										typeStr="插页广告";
									}
								}
								trStr +=" <td>"+typeStr+"</td>";
								urlTemp = SystemProp.appServerUrl+"/publish/mag-read!adPreview.action?advertiseId="+p.id;
								trStr +=" <td><a class='btn' target='_blank' href='"+urlTemp+"'>预览</a></td>";
								if(p.status==2 || p.status==5 || p.status==6 || p.status==8){
									 trStr +=" <td id='statusTD' adid='"+p.id+"'>已审核</td>";
									 if(p.userTypeId==5){//编辑
									 	if(p.adType==1 || p.adType==2 || p.adType==3){//只有互动广告有编辑
									 		tdEdit = "<a class='btn' name='adEdit' adid='"+p.id+"' href='javascript:void(0);'>编辑</a>";
									 	}
									 	tdDel = "<a class='btn' id='adDown' adid='"+p.id+"' href='javascript:void(0);'>下架</a>";
									 }
								}else if(p.status==1){
									trStr +=" <td id='statusTD' adid='"+p.id+"'>待审核</td>";
								}
								else if(p.status==7){
									trStr +=" <td id='statusTD' adid='"+p.id+"'>下架</td>";
								}else{
									trStr +=" <td id='statusTD' adid='"+p.id+"'>&nbsp;</td>";
								}
							}
	                       if(p.level==1){
	                       		trStr +=" <td>一线</td>";
	                       }else{
	                       		trStr +=" <td>非一线</td>";
	                       }
	                       
	                        trStr +=" <td id='tdEditEd'  adid='"+p.id+"'>"+tdEdit+"</td>";
	                       trStr +=" <td id='tdDelUp' adid='"+p.id+"'>"+tdDel+"</td>";
					trStr +="</tr>";
				});
				$("#tbodyContext").html(trStr);
				fnReadyTable();
			}
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-advertising!doJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	//广告 公共
	function pageComm2(currentPage){
		var data = {};
		data.adCurrentPage=currentPage;
		data["advertise.level"]=commAdLevel;
		data["advertise.status"]=commAdStatus;
		data["advertise.adType"]=commAdTypeSelect;
		data["advertise.startTime"]=commAdStartTime;
		data["advertise.endTime"]=commAdEndTime;
		data["advertise.publicationName"]=commAdMagName;
		data["advertise.title"]=commAdName;
		var callback =function(result){
			if(result.data.adList){
				adPageCount = result.data.adPageNo;
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/edit-advertising!doJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	initPage();
	
	//广告 公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm(page_id+1);
		return false;
	}
	
	//广告公共
	function initPage(){
		//alert(adPageCount);
		$("#eventListPage").pagination(adPageCount, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 15, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "上页",
			next_text: "下页"
		});
	}

	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	datePickerFun($("#magTimeBegin"),nowDate);
	datePickerFun($("#magTimeEnd"),nowDate);
	
		//时间控件
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

});	
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/magmeAdvertising.js"></script> 
    <div class="conLeftMiddleRight"  menu="editor" label="advertise">
    	<div class="con17 conTools">
        	<fieldset>
            	<div>
                	<em class="g90"><label><input type="radio" checked name="adLevel" value="" />全部出版商</label></em>
                	<em class="g90"><label><input type="radio" name="adLevel" value="1" />一线出版商</label></em>
                	<em class="g150"><label><input type="radio" name="adLevel" value="2" />非一线出版商</label></em>
                	<em>结束时间范围</em>
                	<em><input id="magTimeBegin" name="magTimeBegin" type="text" class="input g80" value="" /></em>
                	<em class="g160"><input id="magTimeEnd" name="magTimeEnd" type="text" class="input g80" value="" /></em>
                	<em>杂志名</em>
                	<em><input type="text" id="adMgName" class="input g120" value="" /></em>
                    <em><a class="btnWS" id="manageAdminEmail" href="#">邮件设置</a></em>
                </div>
                <hr />
            	<div>
                	<em class="g90"><label><input type="radio" checked name="adStatus" value="" />全部状态</label></em>
                	<em class="g90"><label><input type="radio" name="adStatus" value="1" />审核状态</label></em>
                	<em class="g150"><label><input type="radio" name="adStatus" value="2" />待审核状态</label></em>
                	<em>广告名称</em>
                	<em><input type="text" id="adName" class="input g310" value="" /></em>
                	<em><select class="g120" id="adTypeSelect"><option value="1" checked="checked">互动广告</option><option value="2">互动内容</option><option value="3">插页广告</option></select></em>
                    <em><a class="btnWS" id="adSearch" href="#">搜索</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB con18">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft">广告名称</td>
                    <td class="tLeft g150">所属期刊</td>
                    <td class="g70">开始时间</td>
                    <td class="g70">结束时间</td>
                    <td class="g80">类型</td>
                    <td class="g60">预览</td>
                    <td class="g60">状态</td>
                    <td class="g90">所属杂志级别</td>
                    <td class="g40">编辑</td>
                    <td class="g40">删除</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                	 <#if adList??>
	                	<#list adList as ad>
	                    <tr>
	                        <td>${(ad.title)!''}</td>
	                        <td><#if (ad.startTime) ??>${(ad.startTime)?string('yyyy-MM-dd')}</#if></td>
	                        <td><#if (ad.endTime) ??>${(ad.endTime)?string('yyyy-MM-dd')}</#if></td>
	                        <td>${(ad.issueNumber)!''}</td>
	                        <td><#if (ad.adType)??&&(ad.adType==1||ad.adType==2||ad.adType==3)>互动广告
	                        </#if></td>
	                        <td><a class="btn" target='_blank' href="${systemProp.appServerUrl}/publish/mag-read!adPreview.action?advertiseId=${(ad.id)}">预览</a></td>
	                        <td>&nbsp;</td>
	                        <td>&nbsp;</td>
	                        <td>&nbsp;</td>
	                        <td><a class="btn" href="#">按钮</a></td>
	                    </tr>
	                    </#list>
                    </#if>
                </tbody>
            </table>
        </div>
		<div class="conFooter">
        	<div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>