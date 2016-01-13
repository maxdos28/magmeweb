$(function() {
	init();
	//杂志广告位置查询结果
	function writePub(rl) {
		$("#adtbody").html("");
		$("#allCheck").attr("checked",true);
		for(i=0;i<rl.length;i++)
		{
			var trContent = "<tr name='parenttr'>";
			trContent += "<td><label><input name='parentCheck' publicationId='"+rl[i].id+"' checked type='checkbox' />选择</label></td>";
			trContent += "<td>"+strEmpty(rl[i].id)+"</td>";
			trContent += "<td class='tLeft'>"+strEmpty(rl[i].name)+"</td>";
			trContent += "<td>"+numEmpty(rl[i].nowAdTradeCount)+"</td>";
			trContent += "<td>"+numEmpty(rl[i].adCount)+"</td>";
			trContent += "<td>"+numEmpty(rl[i].adTradeCount)+"</td>";
			trContent += "</tr>";
			$("#adtbody").append(trContent);
		}
	}
	
	//筛选
	$("#searchAdBtn").unbind("click").live("click", function() {
		var data = getSearchParam();
		if(data.padSizes.length==0&&data.phoneSizes.length==0)
		{
			alert("请选择广告尺寸");
			return;
		}
		if(data.keywords.length==0)
		{
			alert("请选择广告标签");
			return;
		}
		if(!data.startDate||!data.endDate)
		{
			alert("请选择广告上线日期");
			return;
		}
		if(!data.contentStartDate||!data.contentEndDate)
		{
			alert("请选择内容上线日期");
			return;
		}
		var date = getDateString();
		if(data.startDate<date||data.endDate<date)
		{
			alert("请选择当前日期或当前日期之后广告上线日期");
			return;
		}
		if(data.startDate>=data.endDate||data.contentStartDate>=data.contentEndDate)
		{
			alert("结束日期要在开始日期之后");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/ad-trade-new-of-pub!searchAdByPub.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : data,
			success : function(rs){
				if(rs.code!=200)
				{
					return;
				}
				else
				{
					writePub(rs.data.adLocationListByPub);
				}
			}
		});
	});
	//得到查询参数
	function getSearchParam()
	{
		var devicePad;
		var devicePhone;
		var padSize="";
		var phoneSize="";
		//pad size
		if($("#padSize").attr("checked"))
		{
			devicePad = 1;
			var pads = $.find("input[type=checkbox][name=padSize]:checked");
			if(pads&&pads.length>0)
			{
				for(i=0;i<pads.length;i++)
				{
					if(padSize.length>0)
						padSize+=",";
					padSize+=$(pads[i]).val();
				}
			}
		}
		//phone size
		if($("#phoneSize").attr("checked"))
		{
			devicePhone = 2;
			var phones = $.find("input[type=checkbox][name=phoneSize]:checked");
			if(phones&&phones.length>0)
			{
				for(i=0;i<phones.length;i++)
				{
					if(phoneSize.length>0)
						phoneSize+=",";
					phoneSize+=$(phones[i]).val();
				}
			}
		}
		//keyword
		var keywords = "";
		var ks = $.find("input[type=checkbox][name=keyword]:checked");
		if(ks&&ks.length>0)
		{
			for(i=0;i<ks.length;i++)
			{
				if(keywords.length>0)
					keywords+=",";
				keywords+=$(ks[i]).val();
			}
		}
		var data = {devicePad:devicePad,devicePhone:devicePhone,padSizes:padSize,phoneSizes:phoneSize,keywords:keywords,startDate:$("#startDate").val(),endDate:$("#endDate").val(),contentStartDate:$("#contentStartDate").val(),contentEndDate:$("#contentEndDate").val()};
		return data;
	}
	//下一步
	$("#toStep1Btn").unbind("click").live("click", function() {
		$("#step1Div").show();
		$("#step2Div").hide();
		$("#step3Div").hide();
	});
	$("#toStep2Btn").unbind("click").live("click", function() {
		//检查是否选择了广告位
		var ads = $("input[type=checkbox][name=parentCheck]:checked");
		if(!ads||ads.length==0)
		{
			alert("请选择广告位");
			return;
		}
		//清空step2的尺寸脚本
		$("#adScriptDiv").html("");
		//根据广告尺寸设置脚本输入
		if($("#padSize").attr("checked"))
		{
			var pads = $("input[type=checkbox][name=padSize]:checked");
			if(pads.length>0)
			{
				for(i=0;i<pads.length;i++)
				{
					var script = "<hr />"+
					"<div>"+
                    "<em class='g180'>&nbsp;</em>"+
                    "<em class='g40'><strong>Pad</strong></em>"+
                    "<em class='g100'><i>"+$(pads[i]).val()+"</i></em>"+
                    "<em><textarea name='adScript' device=1 size='"+$(pads[i]).val()+"' class='input g400' rows='4' tips='请输入广告代码'></textarea></em>"+
                    "</div>";
					$("#adScriptDiv").append(script);
				}
			}
		}
		if($("#phoneSize").attr("checked"))
		{
			var phones = $("input[type=checkbox][name=phoneSize]:checked");
			if(phones.length>0)
			{
				for(i=0;i<phones.length;i++)
				{
					var script = "<hr />"+
					"<div>"+
                    "<em class='g180'>&nbsp;</em>"+
                    "<em class='g40'><strong>Phone</strong></em>"+
                    "<em class='g100'><i>"+$(phones[i]).val()+"</i></em>"+
                    "<em><textarea name='adScript' device=2 size='"+$(phones[i]).val()+"' class='input g400' rows='4' tips='请输入广告代码'></textarea></em>"+
                    "</div>";
					$("#adScriptDiv").append(script);
				}
			}
		}
		$("#step1Div").hide();
		$("#step2Div").show();
		$("#step3Div").hide();
	});
	$("#toStep3Btn").unbind("click").live("click", function() {
		//广告名称
		if(!$("#adTradeName").val())
		{
			alert("请输入广告名称");
			return;
		}
		//广告脚本
		$("#adScriptShowDiv").html("");
		var adsa = $("textarea[name=adScript]");
		for(i=0;i<adsa.length;i++)
		{
			if(!$(adsa[i]).val())
			{
				alert("请输入广告脚本");
				return;
			}
			var d = "";
			if($(adsa[i]).attr("device")=="1")
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
            "<em class='g100'><i>"+$(adsa[i]).attr("size")+"</i></em>"+
            "<em><textarea class='input g400 disabled' rows='4'>"+$(adsa[i]).val()+"</textarea></em>"+
            "</div>";
			$("#adScriptShowDiv").append(script);
		}
		//广告名称
		$("#showAdName").text($("#adTradeName").val());
		//广告时间
		$("#showStartDate").text($("#startDate").val());
		$("#showEndDate").text($("#endDate").val());
		$("#showContentStartDate").text($("#contentStartDate").val());
		$("#showContentEndDate").text($("#contentEndDate").val());
		//广告标签
		var keywords = "";
		var ks = $.find("input[type=checkbox][name=keyword]:checked");
		if(ks&&ks.length>0)
		{
			for(i=0;i<ks.length;i++)
			{
				if(keywords.length>0)
					keywords+=",";
				keywords+=$(ks[i]).val();
			}
		}
		$("#showAdKeyword").text(keywords);
		$("#step1Div").hide();
		$("#step2Div").hide();
		$("#step3Div").show();
	});
	$("#backStep2Btn").unbind("click").live("click", function() {
		$("#step1Div").hide();
		$("#step2Div").show();
		$("#step3Div").hide();
	});
	//pad设备选择
	$("#padSize").unbind("change").live("change", function() {
		var check = false;
		if($(this).attr("checked"))
			check = true;
		var cs = $(this).parents().find("input[type=checkbox][name=padSize]");
		for(i=0;i<cs.length;i++)
		{
			$(cs[i]).attr("checked",check);
		}
	});
	//phone设备选择
	$("#phoneSize").unbind("change").live("change", function() {
		var check = false;
		if($(this).attr("checked"))
			check = true;
		var cs = $(this).parents().find("input[type=checkbox][name=phoneSize]");
		for(i=0;i<cs.length;i++)
		{
			$(cs[i]).attr("checked",check);
		}
	});
	//pad尺寸选择
	$("input[type=checkbox][name=padSize]").unbind("change").live("change", function() {
		if($(this).attr("checked"))
			$("#padSize").attr("checked",true);
		var cs = $(this).parents().find("input[type=checkbox][name=padSize]:checked");
		if(!cs||cs.length==0)
			$("#padSize").attr("checked",false);
	});
	//phone尺寸选择
	$("input[type=checkbox][name=phoneSize]").unbind("change").live("change", function() {
		if($(this).attr("checked"))
			$("#phoneSize").attr("checked",true);
		var cs = $(this).parents().find("input[type=checkbox][name=phoneSize]:checked");
		if(!cs||cs.length==0)
			$("#phoneSize").attr("checked",false);
	});
	//广告发布
	$("#saveAdTradeBtn").unbind("click").live("click", function() {
		var data = getSearchParam();
		data.adTradeName = $("#adTradeName").val();
		//杂志广告位
		var adPublications = "";
		var adps = $("input[type=checkbox][name=parentCheck]:checked");
		if(adps&&adps.length>0)
		{
			for(i=0;i<adps.length;i++)
			{
				if(adPublications.length>0)
					adPublications+=",";
				adPublications+=$(adps[i]).attr("publicationId");
			}
		}
		data.adPublications = adPublications;
		//广告脚本
		var adsa = $("textarea[name=adScript]");
		var ka = new Array();
		var va = new Array();
		for(i=0;i<adsa.length;i++)
		{
			var k = $(adsa[i]).attr("device")+":"+$(adsa[i]).attr("size");
			ka[i] = k;
			va[i] = $(adsa[i]).val();
		}
		data.scriptKeyList = ka;
		data.scriptValueList = va;
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/ad-trade-new-of-pub!saveAdTrade.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : data,
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("广告发布成功");
					clear();
				}
			}
		});
	});
	function clear(){
		$("input[type=checkbox]").attr("checked",false);
		$("input[type=text]").val("");
		$("#adtbody").html("");
		$("#step1Div").show();
		$("#step2Div").hide();
		$("#step3Div").hide();
	}
	//全选杂志
	$("#allCheck").unbind("change").live("change", function() {
		var c = false;
		if($(this).attr("checked"))
			c = true;
		$("#adtbody input[type=checkbox]").attr("checked",c);
	});
	//杂志选择
	$("input[type=checkbox][name=parentCheck]").unbind("change").live("change", function() {
		var c = false;
		if($(this).attr("checked"))
			c = true;
		if(!c)
			$("#allCheck").attr("checked",false);
		$(this).parent().parent().parent().next().find("input[type=checkbox]").attr("checked",c);
	});
	function init()
	{
		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#startDate"),nowDate);
	      datePickerFun2($("#endDate"),nowDate);
	      datePickerFun1($("#contentStartDate"),nowDate);
	      datePickerFun2($("#contentEndDate"),nowDate);
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