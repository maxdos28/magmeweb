//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-user-gold!searchUserGoldJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.userId)+"</td>";
		trContent += "<td>"+strEmpty(r.nickName)+"</td>";
		trContent += "<td>"+trimDateTime(r.goldDate)+"</td>";
		if(r.goldNum>=0)
		{
			trContent += "<td>入金</td>";
		}
		else
		{
			trContent += "<td>出金</td>";
		}
		if(r.chageType==1)
		{
			trContent += "<td>分享</td>";
		}
		else if(r.chageType==2)
		{
			trContent += "<td>签到</td>";
		}
		else if(r.chageType==3)
		{
			trContent += "<td>阅读</td>";
		}
		else if(r.chageType==4)
		{
			trContent += "<td>兑换</td>";
		}
		else if(r.chageType==5)
		{
			trContent += "<td>初次登陆</td>";
		}
		else if(r.chageType==6)
		{
			trContent += "<td>邀请好友</td>";
		}
		else
		{
			trContent += "<td>其它</td>";
		}
		trContent += "<td>"+numEmpty(r.goldNum)+"</td>";
		
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-from-date").val()!="开始日期")
		data.startDate = $("#s-from-date").val();
		if($("#s-end-date").val()!="结束日期")
			data.endDate = $("#s-end-date").val();
		if($("#s-userId").val()!="ID号")
			data.userId = $("#s-userId").val();
		if($("#s-nickName").val()!="昵称")
			data.nickName = $("#s-nickName").val();
		if($("#s-addType").val()!="-1")
			data.addType = $("#s-addType").val();
		if($("#s-type").val()!="-1")
			data.type = $("#s-type").val();
		pageComm(1, posturl, data, writetablefn);
	});
	function initPage() {
		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#s-from-date"),nowDate);
	      datePickerFun2($("#s-end-date"),nowDate);
	      
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
