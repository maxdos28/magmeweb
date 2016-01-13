<#import "../publish/pubCategory.ftl" as pubCategory>
<#import "../publish/issueItem.ftl" as issueItem>

<html>
<head>
<title>看米</title>
</head>
<body>
	  <script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.kanmileft.js"></script>
	  <script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.kanmiright.js"></script>
	  <script type="text/javascript" src="${systemProp.staticServerUrl}/js/kanmi.js"></script>
      <!--topBar-->
      <@pubCategory.main/>
      <!--magezineBox-->
      <@issueItem.main/>
</body>
</html>