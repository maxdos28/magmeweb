$(function(){
	//添加图片
    $("#addPic").unbind("click").live("click",function(){
       var filename=$("#pic").val();
	    if(filename &&  filename!=''){
		  filename=filename.toLowerCase();
		  var fnamearr=filename.split(".");
		  if(fnamearr[fnamearr.length-1]!='jpg'){
			  alert("上传的图片格式不正确，只能支持jpg格式");
			  return;
		}
	  }else{
		  alert("必须上传图片");
		  return;
	  }
	  var picLink=$("#picLink").val();
      if(picLink && !checkeURL(picLink)){
         alert("输入网址不正确");
         return;
      }
	  checkPic();
   });
   function checkPic()
   {
   	var postUrl = SystemProp.appServerUrl+"/phoenix/start-pic!checkPic.action";
		var data = form2object('form');
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "pic"],
			content : $("#form"),
			dataType : "json",
			async : true,
			type : 'POST',
			success : function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var temp = rs.data.tempFile;
					
					if(rs.data.checkPic=="0")
					{
						if(!confirm(rs.message))
							return;
						else
						{
							addPic(temp);
						}
					}
					else
					{
						addPic(temp);
					}
				}
			},
			// 服务器响应失败处理函数
			error : function(rs, status, e) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					
				}
			}
		});
   }
   
   function addPic(temp)
   {
   		var postUrl = SystemProp.appServerUrl+"/phoenix/start-pic!add.action";
		var data = form2object('form');
		data.tempFile = temp;
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "pic"],
			content : $("#form"),
			dataType : "json",
			async : true,
			type : 'POST',
			success : function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					alert("保存成功");
					location.href=SystemProp.appServerUrl+"/phoenix/start-pic!index.action";
				}
			},
			// 服务器响应失败处理函数
			error : function(rs, status, e) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					
				}
			}
		});
   }
   //保存显示方式
    $("#saveShowTypeBtn").unbind("click").live("click",function(){
	  $("#showTypeForm").submit();
   });
    
    $("#showType").unbind("change").live("change",function(){
  	  if($(this).val()=="1")
  	  {
  		  $("#picSize").text("(768*1024)");
  	  }
  	  if($(this).val()=="2")
	  {
		  $("#picSize").text("(1600*1024)");
	  }
     });
   
  //检查url
  function checkeURL(URL){
		var str=URL;
		//在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
		//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
		//下面的代码中应用了转义字符"\"输出一个字符"/"
		var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
		var objExp=new RegExp(Expression);
		if(objExp.test(str)==true){
		   return true;
		}else{
		   return false;
		}
  } 
  //下架
  $(".del").unbind("click").live("click",function(){
      var id=$(this).parent(".item").attr("startPicId"); 
      $.ajax({
	        url : SystemProp.appServerUrl+"/phoenix/start-pic!delJson.action",
			type : "POST",
			dataType:'json',
			data : {"id":id},
			success :$(this).parent(".item").remove()
      });
  });
  //左移
  $(".moveL").unbind("click").live("click",function(){
     var id=$(this).parent(".item").attr("startPicId"); 
     $.ajax({
	        url : SystemProp.appServerUrl+"/phoenix/start-pic!move.action",
			type : "POST",
			dataType:'json',
			data : {"id":id,"moveLeft":1},
			success : function(result){
				if(!result) return;
				if(result.code==200){
					window.location.href=SystemProp.appServerUrl+"/phoenix/start-pic!index.action";
				}else{
				    alert("移动失败!");
				}
		    }
      });
  });
  //右移
  $(".moveR").unbind("click").live("click",function(){
      var id=$(this).parent(".item").attr("startPicId"); 
     $.ajax({
	        url : SystemProp.appServerUrl+"/phoenix/start-pic!move.action",
			type : "POST",
			dataType:'json',
			data : {"id":id,"moveLeft":2},
			success : function(result){
				if(!result) return;
				if(result.code==200){
					window.location.href=SystemProp.appServerUrl+"/phoenix/start-pic!index.action";
				}else{
				    alert("移动失败!");
				}
		    }
      });
  });
});