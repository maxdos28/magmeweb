<#import "../components/phoenixAdminHeader.ftl" as header>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/phoenix/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>

<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script type="text/javascript">
  $(".del").unbind("click").live("click",function(e){
     e.preventDefault();
     var id=$(this).parent().parent().attr("userid");
     $.ajax({
        url : SystemProp.appServerUrl+"/phoenix/phoenix-user!delJson.action",
		type : "POST",
		dataType:'json',
		data : {"id":id},
		success: function(result){
			if(!result) return;
			if(result.code==200){
				window.location.reload();
			}else{
			    alert("删除失败!");
			}
		}
     });
  });
  
  $("#add").unbind("click").live("click",function(e){
     e.preventDefault();
     $("#add").html("新增账号");
     $("#editId").val("");
     $("#newAccountDialog").fancybox();
  });
  
  $("#cancel").unbind("click").live("click",function(e){
     e.preventDefault();
     $.fancybox.close();
  });
  
  $("a[editname]").unbind("click").live("click",function(e){
      e.preventDefault();
      $("#editId").val($(this).parent().parent().attr("userid"));
      $("#add").html("修改账号");
      $("#userName").val($(this).attr("editname"));
      $("#newAccountDialog").fancybox();
  });
  
  $("#enter").unbind("click").live("click",function(e){
     e.preventDefault();
     var userName=$("#userName").val();
     var password=$("#password").val();
     var password2=$("#password2").val();
     var id=$("#editId").val();
     if(!userName || userName==''){
        alert("用户名必须输入");
        return;
     }
     if(!id || id==''){
        if(!password || !password2){
           alert("密码必须输入两次");
           return;
        }
     }
     if(password || password2){
        if(password!=password2){
      	   alert("密码不一致");
           return ;
        }
     }
     $.ajax({
        url : SystemProp.appServerUrl+"/phoenix/phoenix-user!addOrEditJson.action",
		type : "POST",
		dataType:'json',
		data : {"type":3,"registerSource":1,"id":id,"userName":userName,"password":password,"password2":password2},
		success: function(result){
			if(!result) return;
			if(result.code==200){
				window.location.reload();
			}else{
			    alert(result.message);
			}
		}
     });
  });
  
</script>
</head>
<body>
<!--header-->
<@header.main menuId="specialAccount"/>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix">
            <a id="add" href="javascript:void(0);" class="btnBS">新增账号</a>
        </div>

        <div class="conB con03">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg innerAccount">
              <thead>
                <tr>
                    <td>名称</td>
                    <td class="g100">操作</td>
                  </tr>
                </thead>
                <tbody>
                  <#if userList?? && (userList?size>0)>
	               <#list userList as u>
	                    <tr userid="${u.id!''}">
	                        <td>${u.userName!''}</td>
	                        <td><a editname="${u.userName!''}"  class="btn" href="javascript:void(0);">编辑</a><a class="del" href="javascript:void(0)">删除</a></td>
	                    </tr>
	               </#list>
                  </#if>
                </tbody>
            </table>            
	        </div>
        
    </div>

</div>
 <div class="popContent" id="newAccountDialog">
            <h6>新增账号</h6>
            <fieldset>
               <input id="editId" type="hidden" value=""/>
               <div>
                    <em class="g50">用户名</em>
                    <em><input id="userName" type="text" class="input g150" /></em>
                </div>
                <div>
                    <em class="g50">密码</em>
                    <em><input id="password" type="password" class="input g150" /></em>
                </div>
                <div>
                    <em class="g50">确认密码</em>
                    <em><input id="password2" type="password" class="input g150" /></em>
                </div>
            </fieldset>
            <div class="actionArea tRight">
                 <em class="g50">&nbsp;</em>
                    <em><a id="enter" href="javascript:void(0)" class="btnAS">确定</a></em>
                    <em><a id="cancel" href="javascript:void(0)" class="btnWS">取消</a></em>
            </div>
        </div>


</body>
</html>