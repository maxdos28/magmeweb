<#import "../user/conUserInfo.ftl" as conUserInfo>
<#import "../user/conSubMenu.ftl" as conSubMenu>
<#import "../user/conWarehouse.ftl" as conWarehouse>
<#import "../user/conInteractionArea.ftl" as conInteractionArea>
<#import "../user/conFriend.ftl" as conFriend>
<#import "../user/conConcern.ftl" as conConcern>
<#import "../user/conSubject.ftl" as conSubject>

<html>
<head>
<title>用户空间</title>
</head>
<body>
<div>
<form action="/magmecn/user-update!uploadAvatarJson.action" method ="POST" enctype ="multipart/form-data" >
	<input type="file" name="avatarFile" />
	<input type="submit" name="提交" />
</form>
</div>
</body>
</html>