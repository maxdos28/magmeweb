<#import "../publish/publisherPubTopToolBar.ftl" as publisherPubTopToolBar>
<#import "issueContent.ftl" as issueContent>
<body>
<!--conMiddleRight-->
<div id="magList" class="conMiddleRight conManageMgzList">
    <!--conPubTopbar-->
    <#--杂志列表-->
    <@publisherPubTopToolBar.main/>
    <#--期刊列表-->
    <@issueContent.main/>
</div>
</body>
