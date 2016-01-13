 <script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
 <script>
	var pageCount = ${pageNo!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/sms/smsProject.js"></script>
<div class="body">
    <div class="conLeftMiddleRight">
    	<div class="conTools">
        	<fieldset>
            	<a class="btnOM" href="${systemProp.kaiJieAppUrl}/sms/project-manage!stepFirst.action">+新建项目</a>
            </fieldset>
        </div>
        <div class="conB con2">
        	<h2>项目列表</h2>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td>项目名称</td>
                    <td class="g100">状态</td>
                    <td class="g80">完成率</td>
                    <td class="g150">成功 / 失败</td>
                    <td class="g60">操作</td>
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