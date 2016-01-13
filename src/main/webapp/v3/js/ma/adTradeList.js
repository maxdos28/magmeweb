$(function() {
	init();
	
	var posturl = SystemProp.appServerUrl
	+ "/ma/ad-trade!searchAdTrade.action";
	function writetablefn(trContent, r) {
		var date = getDateString();
		trContent += "<tr>";
		trContent += "<td>"+r.id+"</td>";
		trContent += "<td>"+strEmpty(r.adName)+"</td>";
		trContent += "<td>"+trimDate(r.startDate)+"~"+trimDate(r.endDate)+"</td>";
		trContent += "<td>"+trimDate(r.contentStartDate)+"~"+trimDate(r.contentEndDate)+"</td>";
		trContent += "<td>"+numEmpty(r.showCount)+"</td>";
		trContent += "<td>"+numEmpty(r.clickCount)+"</td>";
		if(r.status==0)
		{
			trContent += "<td>未审核</td>";
		}else if(r.status==1)
		{
			if(trimDate(r.endDate)<date)
			{
				trContent += "<td>已结束</td>";
			}else if(trimDate(r.startDate)>date)
			{
				trContent += "<td>待发布</td>";
			}else
			{
				trContent += "<td>发布中</td>";
			}			
		}else
		{
			trContent += "<td>已删除</td>";
		}
		
		
		trContent += "<td>";
		if(r.status==0)
		{
			trContent += "<a name='approveBtn' class='del' adHeadId="+r.id+" href='#'>审核</a>";
		}
		if(r.status!=-1)
		{
			trContent += "<a name='delBtn' class='del' adHeadId="+r.id+" href='#'>删除</a>";
		}		
		trContent += "<a name='showInfoBtn' class='del' adHeadId="+r.id+" href='#'>查看</a>";
		trContent += "<a name='showReportBtn' class='del' adHeadId="+r.id+" href='#'>报表</a>";
		trContent += "</td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchAdBtn").unbind("click").live("click", function() {
		var data = {};
		var st = $("[name=status][type=checkbox]:checked");
		var status="";
		if(st)
		{
			for(i=0;i<st.length;i++)
			{
				if(status.length>0)
					status+=",";
				status+=$(st[i]).val();
			}
		}
		if($("#s-title").val())
		data.title = $("#s-title").val();
		if($("#s-startDate").val())
			data.startDate = $("#s-startDate").val();
		if($("#s-endDate").val())
			data.endDate = $("#s-endDate").val();
		if(status.length>0)
			data.statusStr = status;
		pageComm(1, posturl, data, writetablefn);
	});
	//审核
	$("a[name=approveBtn]").unbind("click").live("click", function() {
		if(!confirm("是否确认?"))
			return;
		var id = $(this).attr("adHeadId");
		if(!id)
		{
			alert("无ID");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/ad-trade!releaseAdTrade.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:id},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("保存成功");
					refreshCurrentPage();
				}
			}
		});
	});
	//删除
	$("a[name=delBtn]").unbind("click").live("click", function() {
		if(!confirm("是否确认?"))
			return;
		var id = $(this).attr("adHeadId");
		if(!id)
		{
			alert("无ID");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/ad-trade!deleteAdTrade.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:id},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("保存成功");
					refreshCurrentPage();
				}
			}
		});
	});
	//查看
	$("a[name=showInfoBtn]").unbind("click").live("click", function() {
		var id = $(this).attr("adHeadId");
		if(!id)
		{
			alert("无ID");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/ad-trade!adTradeInfo.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:id},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					var info = rs.data.adHeadInfo;
					var kl = rs.data.adHeadKeyword;
					var sl = rs.data.adHeadSize;
					if(!info)
					{
						alert("未找到信息");
						return;
					}
					$("#showAdName").text(info.adName);
					$("#showStartDate").text(trimDate(info.startDate));
					$("#showEndDate").text(trimDate(info.endDate));
					$("#showContentStartDate").text(trimDate(info.contentStartDate));
					$("#showContentEndDate").text(trimDate(info.contentEndDate));
					if(kl)
					{
						var ks = "";
						for(i=0;i<kl.length;i++)
						{
							if(ks.length>0)ks+=","
							ks+=kl[i].keyword;
						}
						$("#showAdKeyword").text(ks);
					}
					if(sl)
					{
						
						var ss = "";
						for(i=0;i<sl.length;i++)
						{
							var d = "";
							if(sl[i].device=="1")
					        {
					            d = "<em class='g40'><strong>Pad</strong></em>";
					        }
					        else
					        {
					            d = "<em class='g40'><strong>Phone</strong></em>";
					        }
							var script = "<hr />"+
							"<div>"+
				            "<em class='g180'>&nbsp;</em>"+
				            d+
				            "<em class='g100'><i>"+sl[i].sizeValue+"</i></em>"+
				            "<em><textarea class='input g400 disabled' rows='4'>"+sl[i].adScript+"</textarea></em>"+
				            "</div>";
							$("#adScriptShowDiv").append(script);
						}
					}					
					$("#pop011").fancybox();
				}
			}
		});
	});
	//报表
	$("a[name=showReportBtn]").unbind("click").live("click", function() {
		var id = $(this).attr("adHeadId");
		if(!id)
		{
			alert("无ID");
			return;
		}
		window.open(SystemProp.appServerUrl + "/ma/ad-trade-report.action?adHeadId="+id, "_blank");
		
	});
	$("#backBtn").unbind("click").live("click", function() {
		$.fancybox.close();
	});
	function init()
	{
		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#s-startDate"),nowDate);
	      datePickerFun2($("#s-endDate"),nowDate);
	      
	        //时间控件
	      //input_datepicker------------------------------------
	      function datePickerFun1($dateInput,date){
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
	    //        $dateInput.DatePickerHide();
	          }
	        });
	      }
	      function datePickerFun2($dateInput,date){
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
	    //        $dateInput.DatePickerHide();
	          }
	        });
	      }
	}
});