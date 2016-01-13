<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />

<#import "../dialog/editIssue.ftl" as pc>

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
 		<div class="conTools clearFix">
            <fieldset>
                <div class="tLeft ">
                    <#if session_admin?exists>
                    	<em>杂志ID</em><em><input id="publicationIDValue" type="text" class="input g50" /></em>
                        <em>期刊ID</em><em><input id="issueIDValue" type="text" class="input g50" /></em>
                    </#if>
                    <em>杂志名称</em><em><input id="mgSearchValue" type="text" class="input g150" value="<#if publicationName??>${publicationName}</#if>" /></em>
                    <em>收费查询</em><em><select id="isfreeId" name="isFree">
                    	<option value="">全部</option>
                    	<option value="0">收费</option>
                    	<option value="1">免费</option>
                    </select></em>
                    <#if session_admin?exists><#else><em><a class="btnWS" id="publicationCreate" href="javascript:void(0)">杂志创建</a></em></#if>
                    <em><a class="btnBS" id="mgSearch" href="javascript:void(0)">搜索</a></em> 
                </div>
            </fieldset>
        </div>
        <div class="conB con03" id="pubTopbar">
            <h2>杂志列表</h2>
            <a class="turnLeft" id="mgleft"></a>
            <a class="turnRight" id="mgright"></a>
            <ul class="clearFix" id="mgMenu">
           		  <#if publicationList??>
                		<#list publicationList as publication>
                			 <li class="item showSlide" name="publicationById" appid="${(publication.appId)!''}"  pid="${(publication.id)!''}" title='${(publication.id)!''}'>
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
			                    	<#if session_admin?exists>
			                    	<#else>
			                    	<span>操作</span>
			                        <p>
			                            <#if publication.status?? && publication.status==1>
			                             <em id="uploadIssueEM" pid="${(publication.id)!''}" pname='${(publication.name)!''}'>上传期刊</em>
			                             <em id="iosNewPublication" pid="${(publication.id)!''}">ios新刊通知</em>
			                             <em id="newPublication" pid="${(publication.id)!''}">新刊通知</em>
			                             <em id="editPublication" pid="${(publication.id)!''}" >杂志编辑</em>
			                             <em id="downPublication" pid="${(publication.id)!''}">杂志下架</em>
			                             <#else>
			                             <em id="upPublication" pid="${(publication.id)!''}" >杂志上架</em>
			                            </#if>
			                            <em id="delPublication" pid="${(publication.id)!''}" >杂志删除</em>
			                        </p>
			                        </#if>
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
                    <td class="tLeft">期刊名称</td>
                    <#if session_admin?exists>
                    	<td class="g60">出版商级别</td>
					</#if>
                    <td class="g50">总页数</td>
                    <td class="g70">日期</td>
                    <td class="g60">状态</td>
                    <#if session_admin?exists>
                    <td class="g60">事件数量</td>
                    </#if>
                    <td class="g120"><em class="icon16question" title="输入对应页码的标题与内容，输入的文字可以呈现在手机端以供用户阅读。"></em>移动设备端完成率</td>
                    <td class="g80"><em class="icon16question" title="图片和视频可以混合添加，总数要小于15条。"></em>附加内容</td>
                    <td class="g80"><em class="icon16question" title="可以添加一张图片广告(格式为*.jpg、*.gif、*.jpeg、*.pjpeg、*.png)，或添加一段视频广告(格式为*.mp4、*.avi、*.flv、*.mpg、*.mov)，图片与视频的大小均为200M以内。"></em>互动广告</td>
                    <#if session_admin?exists>
                    <td class="g60">插页广告</td>
                    <#else>
                    <td class="g60">定价</td>
                    </#if>
                    
                    <td class="g100">操作</td>
                    <#if session_admin?exists>
			                    	<#else>
                    <td class="g60">删除</td></#if>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                </tbody>
            </table>
        </div>
       <div  class="conFooter">
        	<div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>

    </div>
</div>
 <@pc.main />
 <@pc.netease />