<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/adTradeNewOfArticle.js"></script>
<div class="body" menu="adTradeNew" submenu="pub">
    <div id="step1Div" class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                   
                    <em class="g80"><strong>设备及尺寸</strong></em>
                    <em class="g80"><label><input id="padSize" type="checkbox" /><strong>Pad</strong></label></em>
                    <#if sizeList?exists>
                    	<#list sizeList as sl>
                    	<#if sl.device == 1>  
                    	<em class="g80"><label><input name="padSize" type="checkbox" value="${sl.sizeValue?if_exists}" /><i>${sl.sizeValue?if_exists}</i></label></em>
                    	</#if>
                    	</#list>
                    </#if>
              </div>
              <hr />
              <div>
                    <em class="g80">&nbsp;</em>
                    <em class="g80"><label><input id="phoneSize" type="checkbox" /><strong>Phone</strong></label></em>
                    <#if sizeList?exists>
                    	<#list sizeList as sl>
                    	<#if sl.device == 2>  
                    	<em class="g80"><label><input name="phoneSize" type="checkbox" value="${sl.sizeValue?if_exists}" /><i>${sl.sizeValue?if_exists}</i></label></em>
                    	</#if>
                    	</#list>
                    </#if>
              </div>
              <hr />
              <div>
                    <em class="g80"><strong>选择标签</strong></em>
                    <div class="floatl g870">
                    <#if keywordList?exists>
                    	<#list keywordList as kl>
                    	<em class="g80"><label><input name="keyword" type="checkbox" value="${kl.keyword?if_exists}" />${kl.keyword?if_exists}</label></em>                    	
                    	</#list>
                    </#if>                    
                    </div>
              </div>
              <hr />
            <div>
                <em class="g80"><strong>广告上线日期</strong></em>
                <em class="g90"><input class="input g80" type="text" id="startDate" /></em>
                <em>~</em>
                <em class="g100"><input class="input g80" type="text" id="endDate" /></em>
            </div>
            <hr />
            <div>
                <em class="g80"><strong>内容上线日期</strong></em>
                <em class="g90"><input class="input g80" type="text" id="contentStartDate" /></em>
                <em>~</em>
                <em class="g100"><input class="input g80" type="text" id="contentEndDate" /></em>
                <em>请注意与广告上线日期匹配</em>
            </div>
              <hr />
              <div>
                    <em class="g80">&nbsp;</em>
                    <em class="g90"><a id="searchAdBtn" class="btnGM" href="javascript:void(0);">筛选</a></em>
              </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>	
                    <td class="g80"><label><input id="allCheck" type="checkbox" />全选</label></td>
                    <td class="g80">ID</td>
                    <td class="tLeft">栏目名称</td>
                    <td class="g70">已投广告数</td>
                    <td class="g70">总广告位数</td>
                    <td class="g70">累计广告数</td>
                  </tr>
                </thead>
                <tbody id="adtbody">                           
                    
                </tbody>
            </table>
        </div>
        <div class="conFooter tCenter">
            <a id="toStep2Btn" class="btnAM" href="#">下一步</a></em>
		</div>
    </div>
    <div id="step2Div" style="display:none" class="conLeftMiddleRight">
    	<div class="conLeftMiddleRight">
    	<div class="con116 conTools">
			<h2>广告发布</h2>
        </div>
        <div class="conB">
        	<fieldset>
              <div>
                    <em class="g180">&nbsp;</em>
                   <em class="g100"><strong>广告名称</strong></em>
                   <em class="g40">&nbsp;</em>
                   <em><input id="adTradeName" class="input g400" type="text" /></em>
              </div>
             <div id="adScriptDiv">
              
              </div>
            </fieldset>
        </div>
        <div class="conFooter tCenter">
            <a id="toStep1Btn" class="btnBM" href="#">上一步</a></em>
            <a id="toStep3Btn" class="btnAM" href="#">下一步</a></em>
		</div>
		</div>
    </div>
    <div id="step3Div" style="display:none" class="conLeftMiddleRight">
    	<div class="con116 conTools">
			<h2>广告发布</h2>
        </div>
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
            <a id="backStep2Btn" class="btnBM" href="#">上一步</a></em>
            <a id="saveAdTradeBtn" class="btnAM" href="#">确认发布</a></em>
		</div>
    </div>
</div>