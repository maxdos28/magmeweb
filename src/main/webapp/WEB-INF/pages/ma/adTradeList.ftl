<script src="${systemProp.staticServerUrl}/v3/js/ma/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/adTradeList.js"></script>
<div class="body" menu="adTradeList">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   <div class="floatr">
					<em class="g50"><strong>广告名称</strong></em>
					<em class="g150"><input id="s-title" class="input g140" type="text" /></em>
				   <em>日期范围</em>
                    <em class="g90"><input id="s-startDate" class="input g80" type="text" /></em>
                    <em>~</em>
                    <em class="g100"><input id="s-endDate" class="input g80" type="text" /></em>
                    </div>
                    <em class="g40"><strong>状态</strong></em>
                    <em class="g80"><label><input name="status" value="0" type="checkbox" checked />未审核</label></em>
                    <em class="g200"><label><input name="status" value="1" type="checkbox" checked />已审核(待发布/发布中/已结束)</label></em>
                    <em class="g80"><label><input name="status" value="-1" type="checkbox" checked />已删除</label></em>
              </div>
              <hr />
              <div>
                    <em class="g90"><a id="searchAdBtn" class="btnGM" href="javascript:void(0);">筛选</a></em>
              </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>	
                    <td class="g50">ID</td>
                    <td class="tLeft">广告名称</td>
                    <td class="g150">广告上线日期</td>
                    <td class="g150">内容上线日期</td>
                    <td class="g70">展示数</td>
                    <td class="g70">点击数</td>
                    <td class="g80">状态</td>
                    <td class="g150">操作</td>
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

<@adContent />
<#macro adContent>
 <div id="pop011" class="popContent">
			<h6>广告交易信息</h6>
        <div class="conB conAdPreview">
        	<fieldset>
              <div>
                    <em class="g180">&nbsp;</em>
                   <em class="g100"><strong>广告名称</strong></em>
                   <em class="g40">&nbsp;</em>
                   <em id="showAdName"></em>
              </div>
              <hr />
              <div>
                    <em class="g180">&nbsp;</em>
              		<em class="g100"><strong>广告上线日期</strong></em>
                   <em class="g40">&nbsp;</em>
                    <em id="showStartDate"></em>
                    <em>~</em>
                    <em id="showEndDate"></em>
             </div>
              <hr />
              <div>
                    <em class="g180">&nbsp;</em>
              		<em class="g100"><strong>匹配内容上线日期</strong></em>
                   <em class="g40">&nbsp;</em>
                    <em id="showContentStartDate"></em>
                    <em>~</em>
                    <em id="showContentEndDate"></em>
             </div>
              <hr />
              <div>
                   <em class="g180">&nbsp;</em>
                   <em class="g100"><strong>标签</strong></em>
                   <em class="g40">&nbsp;</em>
                   <em id="showAdKeyword"></em>
              </div>
              <hr />
              <div id="adScriptShowDiv">
              
              </div>
            </fieldset>
        </div>
        <div class="conFooter tCenter">
            <a id="backBtn" class="btnAM" href="#">关闭</a></em>
		</div>
        </div>
</#macro>