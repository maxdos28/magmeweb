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
    <!--sideMiddle-->
    <div id="sideMiddle" class="sideMiddle">
    	<!--conWarehouse-->
		<@conWarehouse.main />
    	<!--conInteractionArea-->
		<@conInteractionArea.main moreNewsFeed="moreNewsFeed"/>
    </div>
    <div id="sideRight" class="sideRight">
    	<!--conFriend-->
		<@conFriend.main />
    	<!--conConcern-->
		<@conConcern.main />
    	<!--conSubject-->
    	
		<@conSubject.subject1 />
		<#--
		<@conSubject.subject2 />
		-->
    </div>
    <!--
    <div id="jiathis" style="display:none;">
		<div id='ckepop'><a class='jiathis_button_tsina'></a><a class='jiathis_button_tqq'></a><a class='jiathis_button_renren'></a></div>
	</div>
	<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js?uid=1527693" charset="utf-8"></script>
	-->
</body>
</html>