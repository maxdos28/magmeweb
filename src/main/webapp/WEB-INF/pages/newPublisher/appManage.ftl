<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/iosAppManage.js"></script>
<!--body-->
<#import "../dialog/iosMessageSend.ftl" as pc>
<#import "../dialog/viewApp.ftl" as ap>
   <div class="conLeftMiddleRight" menu="iosManageAdmin" label="appManage" >
        <div class="con35 conTools">
        	<fieldset>
            	<div>
                    <em class="floatr"><a class="btnAS" id="sendMessageA" href="javascript:void(0);">发送消息</a></em>
                	<em class="g80"><strong>查询列表</strong></em>
                    <em class="g100"><label><input type="radio" name="userType" value="" checked />全部所有者</label></em>
                    <em class="g80"><label><input type="radio"  name="userType" value="0" />magme</label></em>
                    <em class="g100"><label><input type="radio"  name="userType" value="1" />出版商</label></em>
                	<em class="g50">应用名称</em>
                	<em class="g130"><input type="text" name="name" id="iosName" class="input g120" /></em>
                	<em class="g50">杂志名</em>
                	<em class="g130"><input type="text" class="input g120" name="publicationName" id="publicationName" /></em>
                	<em><a href="javascript:void(0);" class="btnBS" id="iosAppSearch" >搜索</a></em>
                </div>

            </fieldset>
        </div>
        <div class="conB con36">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g40">序号</td>
                    <td class="g100">应用名称</td>
                    <td class="g120">关键字</td>
                    <td class="g60">二级分类</td>
                    <td >版权信息</td>
                    <td class="g80">所有者</td>
                    <td class="g80">创建时间</td>
                    <td class="g50">编辑</td>
                    <td class="g50"><label><input id="appAll" type="checkbox" />全选</label></td>
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
<@pc.main/>
<@ap.main class="view" />