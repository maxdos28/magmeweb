 <script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
 <script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/sms/devjs/smsPhoneGroup.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>

<div class="body">
    <div class="conLeftMiddleRight">
    	<div class="conTools">
        	<fieldset>
            	<a class="btnOM" id="addSmsPhoneGroup" href="javascript:void(0);">+新建通讯列表</a>
            </fieldset>
        </div>
        <div class="conB con3">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                  <tr>
                    <td class="g50"><!--<a class="btn" href="#">合并</a>--></td>
                    <td>通讯列表名称</td>
                    <td class="g150">数量</td>
                    <td class="g150">操作</td>
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
    <#import "smsAddPhoneGroup.ftl" as smsAddPhoneGroup>
    <@smsAddPhoneGroup.main/>
</div>