<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/neteaseCloudReaderManager.js"></script>
<!--body-->
<div class="body"  menu="neteaseTask">
   <div class="conLeftMiddleRight" >
        <div class="con35 conTools">
        	<fieldset>
            	<div>
                    <em class="g10">&nbsp;</em>
                    <em class="g100"><input id="s-pubId" type="text" class="input g90" tips="杂志ID" /></em>    
                    <em class="g190"><input id="s-pubName" type="text" class="input g180" tips="杂志名称" /></em> 
                    <em class="g100"><input id="s-issueId" type="text" class="input g90" tips="期刊ID" /></em>    
                    <em class="g190"><input id="s-issueName" type="text" class="input g180" tips="期刊名称" /></em>    
                    <em class="g100"><input id="s-adId" type="text" class="input g90" tips="广告ID" /></em>    
                    <em class="g190"><input id="s-adName" type="text" class="input g180" tips="广告名称" /></em>
               </div>
               <div>   
                    <em class="g10">&nbsp;</em>
                    <em class="g90"><input id="s-from-date" class="input g80" type="text" tips="开始日期" /></em>
                    <em>~</em>
                    <em class="g100"><input id="s-end-date" class="input g80" type="text" tips="结束日期" /></em>
                    <em class="g160">
                    <select id="s-status" class="g140">
                    <option value="-1">全部状态</option>
                    <option value="0">未处理</option>
                    <option value="1">处理中</option>
                    <option value="2">处理失败</option>
                    <option value="3">处理成功</option>
                    </select></em>  
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                </div>

            </fieldset>
        </div>
        <div class="conB con36">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g90">杂志ID/期刊ID</td>
                    <td class="g120">杂志名称/期刊名称</td>
                    <td class="g100">广告ID/广告名称</td>
                    <td class="g40">类型</td>
                    <td class="g40">页数</td>
                    <td class="g60">时间</td>
                    <td class="g60">开始时间</td>
                    <td class="g60">结束时间</td>
                    <td>任务描述</td>
                    <td class="g50">状态</td>
                    <td class="g60">操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">      
                </tbody>
            </table>
        </div>
        <#import "../components/pagebar.ftl" as pageBar>
	   <@pageBar.main/>
    </div>
    </div>