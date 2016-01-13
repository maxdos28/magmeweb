<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/golf/iosManage.js"></script>
<#import "../dialog/iosMessageSend.ftl" as pc>
<div class="body" menu="message" >
    <div class="conLeftMiddleRight">
        <div class="con34 conTools">
            	<div>
                	<em class="g80"><strong>查询列表</strong></em>
                    <em class="g60"></em>
                </div>
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
<@pc.main/>