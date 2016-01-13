	//将date 转换成'yyyy-mm-dd'
	function strDate(date){
		return (date instanceof Date) ?
				(date.getFullYear()+"-"+ 
						((date.getMonth()+1 < 10) ? ("0"+(date.getMonth()+1)) : (date.getMonth()+1))+"-"+
				 (date.getDate()<10?("0"+date.getDate()) : date.getDate())) : "";
	}
	
	//dateFormat------------------------
	function dateFormat(dateStr){
//		var dateArray = dateStr.split('T')[0].split("-");
//		return dateArray[1]+'-'+dateArray[2];
		return dateStr.split('T')[0];
	}
	
	//add datePicker
	function addDatePicker(domElement,currentDate){
		var $dom = $(domElement);
		$dom.DatePicker({
			format:'Y-m-d',
			date: currentDate,
			current: currentDate,
			starts: 0,
			position: 'bottom',
			onBeforeShow: function(){
				$dom.DatePickerSetDate($dom.val()||currentDate, true);
			},
			onChange: function(formated, dates){
				$dom.val(formated);
			}
		});
	}
	
	//showChart-----------------------------------
	function showChart(condition,ajaxUrl,callBack){
		$("#mychart").empty();
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			data: condition,
			success: callBack
		});
	}
	/**
	 * 从‘年-月-日’ 转换为 Date对象 <br/> 
	 * 只有这种格式的日期才能在IE和firefox中使用 new Date(日期字符串)	
	 */
	function strToDate(strDate){
		var arr = strDate.split('-');
		var date = arr[1] + '/' + arr[2] + '/' + arr[0];
		return new Date(date);
	}
	//judge the date
	function judgeDate(sDate,eDate){
		if(eDate < sDate){
			alert('结束日期必须大于开始日期！');
			return true;
		}else if(eDate > (+new Date())){
			alert('结束日期不能大于当前时间！');
			return true;
		}else if(eDate == sDate){
			alert('不能选择一天的数据！');
			return true;
		}else{
			var et=new Date(eDate);
			var may= strToDate('2012-05-01');
			if(sDate<may || eDate<may){
				alert("查询日期不能在2012-05-01之前");
				return true;
			}
			et.setDate(et.getDate() - 31);
			if(strDate(new Date(eDate))==strDate(new Date())){
				if(new Date(sDate)<et){
					alert('请选择查结束日期前 31 天统计！');
					return true;
				}
			}else{
				if(new Date(sDate)<=et){
					alert('请选择查结束日期前 31 天统计！');
					return true;
				}
			}
		}
		return false;
	}
	
	function getUrlValue(name){ 
		var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
		if (r!=null) return unescape(r[2]); return null;
	}
	
	
	//改变杂志
	$("#publicationId").unbind("change").live("change",function(){
		var pubcallback=function(rs){
			$("#issueId").empty();
			var code = rs.code;
			if(rs.code!=200){
				alert("这本杂志下没有期刊");
				return;
			}
			var data=rs.data.issueList;
			for(var i=0;i<data.length;i++){
				$("<option value='"+data[i].id+"'>"+(data[i].publicationName||"")+(data[i].issueNumber||"") +"</option>").appendTo($("#issueId"));
			}
			
		};
		
		$.ajax({
			url:"http://www.magme.com/publish/issue!getByPubId.action",
			type:"GET",
			data:{publicationId:$(this).val()},
			success:pubcallback
		});
	});
	
	
	//
	
	;$(function($){
		//default 当前时间向前推7天
		var today = new Date();
		
		var yesterday=new Date();
		yesterday.setDate(yesterday.getDate()-1);
		
		var last7Days=new Date();
		last7Days.setDate(last7Days.getDate()-7);
		
		//yesterday
		$("a[expressDate=1]").attr("title",""+formatDateWithDot(yesterday)+"-"+formatDateWithDot(today));
		//lastweek
		$("a[expressDate=2]").attr("title",""+getLastWeekStartDate(today).replace(/\-/g,".")+"-"+getLastWeekEndDate(today).replace(/\-/g,"."));
		//last seven days
		$("a[expressDate=3]").attr("title",""+formatDateWithDot(last7Days)+"-"+formatDateWithDot(yesterday));
		//this month
		$("a[expressDate=4]").attr("title",""+getMonthStartDate(today).replace(/\-/g,".")+"-"+formatDateWithDot(today));
		//lastmonth
		$("a[expressDate=5]").attr("title",""+getLastMonthStartDate(today).replace(/\-/g,".")+"-"+getLastMonthEndDate(today).replace(/\-/g,"."));
	});
	
	$("a[id^=load_]").unbind('click').live("click",function(e){
		e.preventDefault();
		var id = this.id;
		if(id){
			var index = id.split("_")[1] * 1;
			var end = new Date(),start = new Date(), now = new Date();
			switch(index){
				case 0: 
					start.setDate(start.getDate()-1);
					start = strDate(start);
					end = strDate(now);
					break;
				case 1: start = getLastWeekStartDate(); end = getLastWeekEndDate(); break;
				case 2:
					start.setDate(start.getDate() - 1);
					end = strDate(start);
					start.setDate(start.getDate() - 6);
					start = strDate(start);
					break;
				case 3: start = getLastMonthStartDate(); end = getLastMonthEndDate(); break;
				default: return;
			}
			//改变起止日期时间显示
			//$sDate.val(start);
			$("#startDate").val(start);
			$("#endDate").val(end);
			//$eDate.val(end);
			$("#changeChart").click();
		}
	});
	
	$("em[id^=order_]").unbind('click').live("click",function(e){
		e.preventDefault();
		var id = this.id;
		if(id){
			var index =""+id.split("_")[1]+"_"+id.split("_")[2];
			 switch(index){
			 	case "up_date":
					$("#order").val("1");
					break;
				case "down_date":
					$("#order").val("2");
					break;
				case "up_pv":
					$("#order").val("3");
					break;
				case "down_pv":
					$("#order").val("4");
					break;
				case "up_uv":
					$("#order").val("5");
					break;
				case "down_uv":
					$("#order").val("6");
					break;
				case "up_retention":
					$("#order").val("7");
					break;
				case "down_retention":
					$("#order").val("8");
					break;
				default : return;
			}
			//去除高亮
			$(".icon16ascendingSelect").removeClass("icon16ascendingSelect").addClass("icon16ascending");
			$(".icon16descendingSelect").removeClass("icon16descendingSelect").addClass("icon16descending");
			if(index.indexOf("up")>=0){
				$(this).removeAttr("class").addClass("icon16ascendingSelect");
			}else{
				$(this).removeAttr("class").addClass("icon16descendingSelect");	
			}
			
			
			$("#changeChart").click();
		}
		
	});
	
	
	
	
	