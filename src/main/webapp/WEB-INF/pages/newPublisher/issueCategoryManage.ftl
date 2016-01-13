<script src="${systemProp.staticServerUrl}/v3/dv/js/issueCategoryManage.js"></script>
<div class="body">
    <div class="conLeftMiddleRight">
    	<div class="con14 conTools">
            <fieldset>
                <div>
                	<input id="issueId" type="hidden" value="${issueId}"/>
                	
                	<em class="g150"><a href="/new-publisher/magazine-list!textWrite.action?id=${issueId}" class="btnWS floatl mgr20">返回</a></em>
                    <em class="g60">栏目添加</em>
                    <em class="g150"><input id="categoryName" type="text" class="input g130" /></em>
                    <em class="g150"><select id="fromCatalog" class="g130"><option>栏目起始文章</option></select></em>
                    <em class="g150"><select id="endCatalog" class="g130"><option>栏目结尾文章</option></select></em>
                    <em class="g150"><a class="btnAS" href="#" id="addCategory">添加</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB con15">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g150">栏目名称</td>
                    <td class="g320">起始文章标题</td>
                    <td>结束文章标题</td>
                    <td class="g100">操作</td>
                  </tr>
                </thead>
                <tbody id="contentTbody">
                </tbody>
            </table>
        </div>
    </div>
</div>