var SystemProp={};
//加到所有*.action前面
SystemProp.appServerUrl="";
//加到所有静态资源前面,包括js,style以及静态的图片,但是/js/systemProp.js除外
SystemProp.staticServerUrl="http://static.magme.com";
//统计的URL
SystemProp.statServerUrl="http://monitor.magme.com:8080/magmeStat";
//加到用户头像前面
SystemProp.profileServerUrl="http://static.magme.com/profiles";
//加到用户上传的临时头像前面
SystemProp.profileServerUrlTmp="http://static.magme.com/profiles/tmp";
//暂未用到
SystemProp.sampleServerUrl="http://static.magme.com/samplefiles";
//加到标签图片的前面
SystemProp.tagServerUrl="http://static.magme.com/tags";
//加到杂志的图片和SWF前面
SystemProp.magServerUrl="http://static.magme.com/pdfprofile";
//首页图片
SystemProp.fpageServerUrl="http://static.magme.com/fpage";

//供Flash调用
SystemProp.getUrl=function(param){
   var url=eval('SystemProp.'+param);
   return url;
};
