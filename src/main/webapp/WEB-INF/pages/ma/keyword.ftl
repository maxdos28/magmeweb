<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/adKeyword.js"></script>
<div class="body" menu="keyword">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
              	<em>标签</em>
                <em class="g200"><input id="s-keyword" input class="input g200" type="text"/></em>
                <em class="g20">&nbsp;</em>
                <em><a id="searchKeywordBtn" class="btnAM" href="javascript:void(0);">筛选</a></em>
                <em><a id="newKeywordBtn" class="btnAM" href="javascript:void(0);">新建广告标签</a></em>
              </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>	
                    <td class="g80">ID</td>
                    <td class="tLeft">标签</td>
                    <td class="g70">操作</td>
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
<@editContent />
<#macro editContent>
<div class="popContent" id="pop002">
            <h6>新建广告标签</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
	            <div>
	            	<em class="g60 tRight">标签名</em>
	            	<em><input id="keyword" type="text" class="input g140"></em>
	            </div>
	        </fieldset>
	        </form>
            <div class="actionArea tCenter">
                <a id="saveKeywordBtn" class="btnGS" href="javascript:void(0)">新建</a>
                <a id="cancelBtn" class="btnWS" href="javascript:void(0)">取消</a>
            </div>
        </div>
</#macro>