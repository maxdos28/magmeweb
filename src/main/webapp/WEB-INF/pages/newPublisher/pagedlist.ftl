 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script>
var pageCount = ${pageCount!'1'};//总页数
</script>
 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/paged.js"></script>
<#import "pagedListPageTable.ftl" as pt>
 <div class="conLeftMiddleRight" menu="editor" label="paged">
    	<div class="con32 conTools">
    		<form id="pageDForm">
              <input type="hidden" name="aaid" id="aaid"/>
        	<fieldset>
            	<div class="floatl g220 clearFix">
                	<em class="mgb10"><img width="200" name="picUrl" height="200" src="/images/head172.gif" /></em>
                    <em class="clear"><input id="pic" name="pic" type="file" class="g200" /></em>
                </div>
            	<div class="floatl g360">
                	<em class="g240">名称</em><em>首字母</em>
                    <em class="clear g240"><input type="text" name="name" class="input g180" /></em>
                	<em><select class="g50" name="firstLetter"><option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="D">D</option>
							<option value="E">E</option>
							<option value="F">F</option>
							<option value="G">G</option>
							<option value="H">H</option>
							<option value="I">I</option>
							<option value="J">J</option>
							<option value="K">K</option>
							<option value="L">L</option>
							<option value="M">M</option>
							<option value="N">N</option>
							<option value="O">O</option>
							<option value="P">P</option>
							<option value="Q">Q</option>
							<option value="R">R</option>
							<option value="S">S</option>
							<option value="T">T</option>
							<option value="U">U</option>
							<option value="V">V</option>
							<option value="W">W</option>
							<option value="X">X</option>
							<option value="Y">Y</option>
							<option value="Z">Z</option></select></em>
                	<em class="clear  g100">类别</em>
                	<em class="clear"><select name="categoryId" class="g300">
                	<#if categoryList??>
                		<#list categoryList as clist>
                			<option value="${clist.id!0}" >${clist.name!''}</option>
                		</#list>
                	</#if>
                	</select></em>
                	<em class="clear  g100">标签</em>
                    <em class="clear"><input name="tags" type="text" class="input g290" /></em>
                	<em class="clear  g100">头部文字</em>
                    <em class="clear"><textarea name="headerDesc" class="input g290"></textarea></em>
                    <em class="clear g100">&nbsp;</em>
                    
                    <em class="clear g100"><b>缩略标题</b></em>
                    <em class="clear g200"><input type="text" name="shortTitle" class="input g180" /></em>
                    <em><input type="checkbox" value='1'  name="isHot" class="input" ></em><em><b>热点专题</b></em>
                </div>
            	<div class="floatl g370 mgb10">
                	<em>索引文字标题</em>
                    <em class="clear">
                    <textarea name="title" class="input g360"></textarea></em>
                	<em class="clear">索引文字描述</em>
                    <em class="clear">
                    <textarea name="description" class="input g360"></textarea></em>
                	<em class="clear">索引文字关键字</em>
                    <em class="clear">
                    <textarea name="keyWord" class="input g360"></textarea></em>
                	<em class="clear">索引文字内容</em>
                    <em class="clear"><textarea name="indexDesc" class="input g360"></textarea></em>
                </div>
            	<div class="clear">
                    <em class="floatr"><a class="btnWB" id="chanelPageDBtn" href="javascript:void(0);">取消</a></em>
                    <em class="floatr"><a class="btnBB" id="savePageDBtn" href="javascript:void(0);">确定</a></em>
                </div>
                <hr />
            </fieldset>
            </form>
        </div>
        <div class="con33 conB">
        	<fieldset>
            	<div class="clearFix">
                	<em><select name="filter_type">
                	<option value="" >所有</option>
                	<#if categoryList??>
                		<#list categoryList as clist>
                			<option value="${clist.id!0}" >${clist.name!''}</option>
                		</#list>
                	</#if>
                	</select></em>
                	<em><input name="filter_title" type="text" class="input g100" tips="标题" /></em>
                    <em><a class="btnBS" href="javascript:void(0);" id="filter_search">搜索</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB con34">
            <@pt.main />
        </div>
    </div>
    
