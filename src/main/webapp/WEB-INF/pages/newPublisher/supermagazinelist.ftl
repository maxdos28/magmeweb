<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />

<script>

	//分页 杂志对应的期刊
	var pageCount = ${pageNo!'1'};//杂志的总页数
	var currentPage = ${currentPage!'1'}//杂志的当前页码
	var issuePageCount = ${issuePageNo!'1'};//期刊的总页数
	
	var piid = ${(publication.id)!'0'};
	
	var session_exists = true;
	var objPublisher;
     <#if session_admin?exists>
    	<#else>
    	session_exists = false;
    	objPublisher = <#if session_publisher?exists>${(session_publisher.level)!''}</#if>
    </#if>

</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/magazineList.js"></script>

<div class="body"  menu="publication">
 <div class="conLeftMiddleRight">
        <div class="conB con03" id="pubTopbar">
            <h2><div style="width:350px;">
            	</div>杂志列表</h2>
            <a class="turnLeft" id="mgleft"></a>
            <a class="turnRight" id="mgright"></a>
            <ul class="clearFix" id="mgMenu">
           		  <#if publicationList??>
                		<#list publicationList as publication>
                			 <li class="item showSlide" name="publicationById"  pid="${(publication.id)!''}">
                			 	<#if publication.imgPath?? && publication.imgPath!=''>
                			 		<#if publication.pubType?? && publication.pubType==3>
                			 		<img src="${systemProp.staticServerUrl}/appprofile/${(publication.imgPath)!''}" />
                			 		<#else>
                			 		<img src="${systemProp.magServerUrl}${(publication.imgPath)!''}" />
                			 		</#if>
                			 	<#else>
                			 		<img src="/v3/images/cover182-243.gif" />
                			 	</#if>
			                    <div class="border"></div>
			                    <strong>${(publication.name)!''}</strong>
			                    <div class="slideDown" >
			                    </div>
			                </li>
                		</#list>
                	</#if>
            </ul>
        </div>
        <div class="conB con04">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                   <td>期刊名称</td>
                    <td>出版商级别</td>
                    <td>总页数</td>
                    <td>日期</td>
                    <td>状态</td>
                    <td>事件数量</td>
                    <td>文字比例</td>
                    <td>互动内容数</td>
                    <td>互动广告数</td>
                    <td>插页广告数</td>
                    <td class="g120">操作</td>
                    <#if session_admin?exists>
			                    	<#else>
                    <td>删除</td></#if>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                </tbody>
            </table>
        </div>
       <div>
        	<div id="eventListPageadd" class="conB gotoPage"></div>
			 <div id="eventListPage" class="conB changePage" ></div>
		</div>

    </div>
</div>
