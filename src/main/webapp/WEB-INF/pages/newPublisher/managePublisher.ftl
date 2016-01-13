<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<#import "../dialog/publicationComm.ftl" as pc>
<script>
 	var pageCount = ${pageNo};
</script>   
<script src="${systemProp.staticServerUrl}/v3/dv/js/managePublisher.js"></script>
    <div class="conLeftMiddleRight" menu="editor" label="publisher">
    	<div class="con14 conTools">
        	<fieldset>
            	<div>
                	<em><label><input type="radio" value="" name="level" checked="checked" />全部出版商</label></em>
                	<em><label><input type="radio" value="1" name="level" />一线出版商</label></em>
                	<em class="g170"><label><input type="radio" value="0" name="level" />非一线出版商</label></em>
                	<em>杂志社名</em>
                	<em class="g130"><input type="text" name="publishName" class="input g100" /></em>
                	<em>用户名</em>
                	<em class="g110"><input type="text" name="userName" class="input g100" /></em>
                    <em><a class="btnWS" id="searchButton" href="javascript:void(0);">搜索</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB con15">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft">杂志社名</td>
                    <td class="g300 tLeft">用户名</td>
                    <td class="g80">状态操作</td>
                    <td class="g80">是否一线</td>
                    <td class="g60">编辑</td>
                    <td class="g60">删除</td>
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
    <@pc.editPublisher />
