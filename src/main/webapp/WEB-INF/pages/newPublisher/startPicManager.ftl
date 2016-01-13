
<link href="${systemProp.staticServerUrl}/phoenix/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script type="text/javascript">
    //添加图片
     $("#addPic").unbind("click").live("click",function(){
        var filename=$("#pic").val();
	    if(filename &&  filename!=''){
		  filename=filename.toLowerCase();
		  var fnamearr=filename.split(".");
		  if(fnamearr.length!=2){
			  alert("上传的图片格式不正确，只能支持jpg格式");
			  return;
		  }else if(fnamearr[1]!='jpg'){
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
       $.ajaxFileUpload
                     (
                       {
                            url:'/new-publisher/start-pic!add.action', //你处理上传文件的服务端
                            secureuri:false,
                            fileElementId:'pic',
			                data : form2object('form'),
			                content : $("#form"),
                            dataType: 'json',
                            success: function (data)
                                  {
                                    window.location.href=SystemProp.appServerUrl+"/new-publisher/start-pic!index.action";
                                  },
                               error:function()
                               {
                               window.location.href=SystemProp.appServerUrl+"/new-publisher/start-pic!index.action";
                               }
                               }
                         )
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
	        url : SystemProp.appServerUrl+"/new-publisher/start-pic!delJson.action",
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
	        url : SystemProp.appServerUrl+"/new-publisher/start-pic!move.action",
			type : "POST",
			dataType:'json',
			data : {"id":id,"moveLeft":1},
			success : function(result){
				if(!result) return;
				if(result.code==200){
					window.location.href=SystemProp.appServerUrl+"/new-publisher/start-pic!index.action";
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
	        url : SystemProp.appServerUrl+"/new-publisher/start-pic!move.action",
			type : "POST",
			dataType:'json',
			data : {"id":id,"moveLeft":2},
			success : function(result){
				if(!result) return;
				if(result.code==200){
					window.location.href=SystemProp.appServerUrl+"/new-publisher/start-pic!index.action";
				}else{
				    alert("移动失败!");
				}
		    }
       });
   });
</script>
<div class="body"  menu="startPic">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix">
            <fieldset>
                <div>
                    <form id="form" action="${systemProp.appServerUrl}/new-publisher/start-pic!add.action" method="post" enctype="multipart/form-data">
	            		<em>封面图</em>
	            		<em><input id="pic" name="pic" type="file" class="inputFile" /></em>
	            		<em class="g80 tRight">URL</em>
	            		<em><input id="picLink" name="picLink" type="text" class="input g300" /></em>
	                	<em><a id="addPic" class="btnBS" href="javascript:void(0)">添加</a></em>
                	</form>
                	<form id="moveForm" action="${systemProp.appServerUrl}/new-publisher/start-pic!move.action">
                	   <input type="hidden" id="moveId" name="id"/>
                	   <input type="hidden" id="moveLeft" name="moveLeft"/>
                	</from>
                </div>
            </fieldset>
        </div>
        <div class="conB con01">
            <h2>封面列表</h2>
            <ul class="clearFix">
              <#if phoenixStartPicList?? && (phoenixStartPicList?size>0)>
               <#list phoenixStartPicList as startPic>
                <li class="item showBar" startPicId="${startPic.id}">
                    <img src="${systemProp.staticServerUrl}${startPic.picPath}" />
                    <div class="border"></div>
                    <a class="del" href="javascript:void(0);">下架</a>
                    <#if startPic_index!=0>
                    <a class= "moveL" href= "javascript:void(0);"> 前移 </a>
                    </#if>
                    <#if startPic_index!=(phoenixStartPicList?size-1)>
                    <a class= "moveR" href= "javascript:void(0);"> 后移 </a>
                    </#if>
                </li>
               </#list>
              </#if>
            </ul>
        </div>
        
        <div class="conFooter">
		</div>
    </div>
</div>
