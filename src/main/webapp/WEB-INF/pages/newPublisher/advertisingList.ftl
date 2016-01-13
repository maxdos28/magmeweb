<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />  
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />

<script>
	$.jqueryScrollPhoto("#pubTopbar",5,192,5,0,600);
	var pageCount = ${pageNo!'0'};//杂志的总页数
	var currentPage = ${currentPage!'1'};//杂志的当前页码
	var adPageCount = ${adPageNo!'0'};//广告的总页数
	
	var piid = ${(publication.id)!'0'};
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/advertisingList.js"></script> 
<div class="body" menu="advertise">
    <div class="conLeftMiddleRight">
    	 <div class="conTools clearFix">
            <fieldset>
                <div>
            		<em><input type="text" id="magName" name="magName" class="input g150" tips="杂志名称" /></em>
            		<em><input type="text" id="magTimeBegin" name="magTimeBegin" class="input g80" tips="创建时间" /></em>
            		<em><input type="text" id="magTimeEnd" name="magTimeEnd" class="input g80" tips="创建时间" /></em>
                	<em><a class="btnBS" name="magSearchButton" href="javascript:void(0)">杂志搜索</a></em>
                </div>
            </fieldset>
        </div>
    
        <div class="conB con05" id="pubTopbar">
            <h2>杂志列表</h2>
            <a class="turnLeft" id="mgleft"></a>
            <a class="turnRight" id="mgright"></a>
            <ul class="clearFix" style="width: 1920px; margin-left: 0px;" id="mgMenu">
           		  <#if publicationList??>
                		<#list publicationList as publication>
                			 <li class="item showSlide" name="publicationById"  pid="${(publication.id)!''}">
                			 	<#if publication.imgPath?? && publication.imgPath!=''>
                			 		<img src="${systemProp.magServerUrl}${(publication.imgPath)!''}" />
                			 	<#else>
                			 		<img src="/v3/images/cover182-243.gif" />
                			 	</#if>
			                    <div class="border"></div>
			                    <strong>${(publication.name)!''}</strong>
			                </li>
                		</#list>
                	</#if>
            </ul>
        </div>
        <div class="conB con06">
        	<h2>广告列表</h2>
            <div class="link">
            	<#if session_publisher?exists>
            		<#if (session_publisher.level)??&&(session_publisher.level)==1>
            			<a name="hdad" adtype="1" class="current" href="#">互动广告</a>
		            	<a name="hdad" adtype="2" href="#">互动内容</a>
		            	<a name="hdad" adtype="3" href="#">插页广告</a>
            		<#else>
            			<a name="hdad" adtype="2" class="current" href="#">互动内容</a>
            		</#if>
			    </#if>
            </div>
            <div class="filter">
            	<label><input type="radio" value="" name="typeradio" checked />全部</label>
            	<label><input type="radio" value="1" name="typeradio" />已审核</label>
            	<label><input type="radio" value="2" name="typeradio" />待审核</label>
            </div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft g100">期刊号</td>
                    <td class="g150">标题</td>
                    <td>描述</td>
                    <td class="g80">预览</td>
                    <td class="g80">状态</td>
                    <td class="g80">审核操作</td>
                    <td class="g80">下架操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                		
                </tbody>
            </table>
        </div>
		<div class="conFooter">
        	<div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>
</div>