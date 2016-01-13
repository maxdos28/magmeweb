<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.smartSearch.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script>
	var pageCount = ${pageCount!'1'};//总页数
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/releaseaudit.js"></script>
<#import "../dialog/editReleaseAudit.ftl" as edit>
<div class="conLeftMiddleRight" menu="editor" label="m1check">  
	<div class="con32 conTools">
        	<fieldset>
            	<div class="clearFix">
                    <div>
                        <em>标题</em>
                        <em><input name="search_title" type="text" class="input g100" /></em>
                        <em>专题</em>
                        <em id="select_pagedid" class="smartSearch">
		                   	<input type="text" class="input g150" name="search_pagedid" />
		                    <div class="list" style="width:150px"><div class="inner g150 jspContainer"></div></div>
		                </em>
		                <!--
                        <em><select name="search_status"><option value="">全部状态</option>
                        <option value="1">通过</option>
                        <option value="2">待审核</option>
                        <option value="3">频道显示</option>
                        </select></em>-->
                        <em><a href="javascript:void(0);" id="search_btn" class="btnBS">搜索</a></em>
                    </div>
                    <!--
                    <hr class="clear" />
                    <div>
                        <em class="g60"><label><input type="checkbox" id="allcc" />全选</label></em>
                        <em><a href="javascript:void(0);" id="passBtn" class="btnWS">通过</a></em>
                        <em><a href="javascript:void(0);" id="noPassBtn" class="btnWS">未通过</a></em>
                        <em><a href="javascript:void(0);" id="isNotHomeBtn" class="btnWS">频道显示</a></em>
                        <em><a href="javascript:void(0);" id="isRecommendBtn" class="btnWS">推荐</a></em>
                        <em><a href="javascript:void(0);" id="isRecommendNotBtn" class="btnWS">未推荐</a></em>
                        <em><a href="javascript:void(0);" id="releaseOk" class="btnWS">发布</a></em>
                    </div>
                    -->
                </div>
            </fieldset>
        </div>
        <div class="conB con33">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g30">选择</td>
                    <td class="g150">标题</td>
                    <td class="g80">图片</td>
                    <td class="g60">专题</td>
                    <td class="g80">作者</td>
                    <td class="g60">编辑</td>
                    <td class="g80">创建日期</td>
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
 <@edit.main />