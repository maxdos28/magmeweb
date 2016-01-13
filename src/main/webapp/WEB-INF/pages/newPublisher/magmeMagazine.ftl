<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<#import "../dialog/editPrice.ftl" as pc>
<script>
 	var pageCount = ${pageNo};
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/magmeMagazine.js"></script>
<div class="conLeftMiddleRight" menu="editor" label="publication">
    	<div class="con14 conTools">
        	<fieldset>
            	<div>
                	<em><label><input type="radio" name="level" value="" checked="checked" />全部出版商</label></em>
                	<em><label><input type="radio" name="level" value="1" />一线出版商</label></em>
                	<em><label><input type="radio" name="level" value="0" />非一线出版商</label></em>
                	<em><label><input type="radio" name="isFree" value="0" />收费</label></em>
                	<em class="g130"><label><input type="radio" name="isFree" value="1" />免费</label></em>
                	<em>杂志社名</em>
                	<em class="g130"><input type="text" name="publishname" class="input g100" /></em>
                	<em>杂志名</em>
                	<em class="g110"><input type="text" name="name" class="input g100" /></em>
                	<em><select name="recommend"><option value="">全部</option><option value="1">推荐</option><option value="0">非推荐</option></select></em>
                    <em><a class="btnWS" id="searchButton" href="javascript:void(0)">搜索</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB con16">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft">杂志社名</td>
                    <td class="tLeft g300">杂志名</td>
                    <td class="g80">级别</td>
                    <td class="g80">状态</td>
                    <td class="g50">属性</td>
                    <td class="g80">定价策略</td>
                    <td class="g50">推荐</td>
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
<@pc.main />