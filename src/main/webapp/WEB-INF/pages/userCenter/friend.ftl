<script>
$(function(){
	//关注attention--------------------------
	$("a[name='iconAdd']").click(function(e){
		e.preventDefault();
		var objectId = $(this).attr("objectId");
		var type = $(this).attr("type");
		ajaxAddFollow(objectId,type,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				$("#iconAdd_"+type+"_"+objectId).hide();
				$("#iconSub_"+type+"_"+objectId).show();
				alert("操作成功！");
			}else if(code == 400){
				gotoLogin("用户未登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	//cancel关注-------------------------------------------------------------------
	$("a[name='iconSub']").click(function(e){
		e.preventDefault();
		var objectId = $(this).attr("objectId");
		var type = $(this).attr("type");
		ajaxCancelFollow(objectId,type,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				$("#iconAdd_"+type+"_"+objectId).show();
				$("#iconSub_"+type+"_"+objectId).hide();
				alert("操作成功！");
			}else if(code == 400){
				gotoLogin("用户未登录！");
			}else{
				alert(rs.message);
			}
		});
	});	
});
</script>

<#list userList as user>  
  	<div class="item">
        <a class="img" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${user.id}">
			<img src="<#if ((user.avatar)??)&&(user.avatar!="")>${systemProp.profileServerUrl+user.avatar60}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" />
        </a>
        <strong>${(user.nickName)!""}</strong>
        <a id="iconAdd_1_${user.id}" name="iconAdd" objectId="${user.id}" type="1" class="del" <#if (user.isFriend)??&&user.isFriend==1>style="display:none;"</#if> href="javascript:void(0)">加为好友</a>
        <a id="iconSub_1_${user.id}" name="iconSub" objectId="${user.id}" type="1" class="del" <#if !((user.isFriend)??&&user.isFriend==1)>style="display:none;"</#if> href="javascript:void(0)">取消好友</a>        
    </div>    
</#list>