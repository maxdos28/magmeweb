<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/iosManage.js"></script>
<#import "../dialog/iosMessageSend.ftl" as pc>
<div class="body" menu="iosManage" >
    <div class="conLeftMiddleRight">
    <#if appList?? && appList?size gt 0 >
        <div class="con34 conTools">
        	<fieldset>
        		<div class="choiseApp">
        			<em class="g100"><strong>请选择APP</strong></em>
		    		<#list appList as app>
		                    <em class="g10"><label><input type="checkbox" name="tApp" value="${app.appKey}" appId="${app.id}" />${app.name}</label></em>
		    		</#list>
		    		<em><a href="javascript:void(0);" id="sendMessageA" class="btnAS">发送消息</a></em>
		        </div>
    			<hr />
            	<div>
                	<em class="g80"><strong>查询列表</strong></em>
                    <em class="g60"><label><input name="status" value="" type="radio" checked />全部</label></em>
                    <em class="g60"><label><input name="status" value="1" type="radio" />已发送</label></em>
                    <em class="g60"><label><input name="status" value="2" type="radio" />待发送</label></em>
                </div>

            </fieldset>
        </div>
        <div class="conB con31">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g60">序号</td>
                    <td class="g100">应用名称</td>
                    <td>内容</td>
                    <td class="g100">时间</td>
                    <td class="g80">状态</td>
                    <td class="g120">操作</td>
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
			<#else>
	    	暂时没有App
	    	</#if>	
    </div>
</div>
<@pc.main/>