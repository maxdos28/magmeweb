<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.colorpicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/emReader.js"></script>

<div class="body"  menu="reader">
    <div class="conLeftMiddleRight">
    	<div class="con07 conTools">
	        <h2>嵌入代码生成工具<em class="icon16question" title="可以复制本段代码到其它的网站，直接可以显示阅读杂志的窗口。"></em></h2>
        	<fieldset>
            	<div id="model" class="floatl g310">
                	<em class="title">模式</em>
                    <em><label><input type="radio" name="model" value="1" checked="checked"/>单本封面</label></em>
                    <em><label><input type="radio" name="model" value="2" />单本阅读器</label></em>
                    <em><label><input type="radio" name="model" value="3" />多本阅读器</label></em>
                </div>	
            	<div id="widthHeight" class="floatl">
                	<em class="title">宽-高</em>
                    <em><input type="text" class="input g50" id="width"/></em>
                    <em><input type="text" class="input g50" id="height"/></em>
                </div>
                <hr class="clear floatl g490" />
            	<div class="clear floatl g310">
                	<em class="title">刊号</em>
                    <em><label><input id="islatest" type="checkbox" name="test3" checked="checked" />自动更新为最新一期</label></em>
                </div>	
            	<div class="floatl">
                	<em class="title">背景色</em>
                    <em id="colorSelector"><div></div><span class="png"></span></em>
                </div>	
                <hr class="clear floatl g490" />
            	<div class="clear floatl g220">
                	<em class="title">杂志选择</em>
                    <em><select id="publication">
                    	<#if publicationList??>
                    		<#list publicationList as publication>
                			  <#if (publication.status == 1)>
                    			<option value="${(publication.id)!''}" >
                    			  <#if (publication.name?length > 25)>
	                    			 ${(publication.name)?substring(0,25)}
	                    			<#else>
	                    			 ${(publication.name)!''}
	                    		  </#if>
                    			</option>
                    		  </#if>
                    		</#list>
                    	</#if>
                    </select></em>
                </div>	
            	<div id="selIssue" class="floatl">
                	<em class="title">期刊选择</em>
                    <em><select id="issue"></select></em>
                </div>	
                <div class="code">
                	<a id="copyCode" class="btnBS" href="#">复制代码</a>
                	<textarea id="emReaderStr" class="input">生成 / 复制代码成功后,alert弹出提示！</textarea>
                </div>
            </fieldset>
        </div>
        
	    <div class="conB con09">
			<h2>效果预览</h2>
            <div id="shower">Flash</div>
        </div>
        <div class="conFooter">
            
		</div>    
    </div>
</div>