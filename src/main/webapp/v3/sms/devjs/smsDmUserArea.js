$(function($){
	var smsProjectId=$("#smsProjectId").val();
	
	var ACTION_URL = SystemProp.kaiJieAppUrl+"/sms/sms-dm-user-area!areaJson.action";
	var ajaxUrl = ACTION_URL + "?smsProjectId="+smsProjectId;
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		myMap.refreshData(ajaxUrl,0);
	}
});
