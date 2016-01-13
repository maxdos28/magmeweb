<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>

<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/style/datepicker.css"  />

<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>

<#import "../dialog/publicationComm.ftl" as pc>
<#import "../dialog/helpComm.ftl" as pcHelp>

<script src="${systemProp.staticServerUrl}/v3/dv/js/publicationConfig.js"></script>
<div class="body" menu="publisher" menuSecond="publication">
    <div class="conLeftMiddleRight">
        <div class="conB con11">
        	<div class="head">
            	<img id="publicationLogo"  src="${systemProp.staticServerUrl}/v3/images/head150.gif" />
            	 <#if session_admin?exists>
        			<#else>
            	<a class="btnWS" id="pubNameLogo" href="#"><span>修改头像</span><input id="logoFile" name="logoFile"  type="file" class="inputFile" /></a>
            	</#if>
            </div>
            <div class="info">
            <form id="editPublicationForm" method="post" action="" onsubmit="return false;">
            	<input type="hidden" name="id"/>
            	<fieldset>
                	<div class="top">
                    	<em class="floatr"><a class="btnBB" id="publicationTarget" href="javascript:void(0)">杂志定位</a></em>
                    	<em><select id="publicationOwn" >
                    	<option value="">新建杂志</option>
                			<#if publicationList??>
                			<#list publicationList as publication>
                				<option value="${(publication.id)!''}" >${(publication.name)!''}</option>
                			</#list>
                			</#if>
                    	</select></em>
                    </div>
                    <hr />
                    <h6>基本信息（必填）</h6>
                	<div class="clear floatl g300">
                    	<em class="title">杂志名称</em>
                    	<em><input id="name" name="name" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl">
                    	<em class="title">英文名称</em>
                    	<em><input id="englishname" name="englishname" type="text" class="input g170" /></em>
                        <em class="tips" id="tipCheckMessage" >英文名称将用于二级域名，填写拼音也可</em>
                    </div>
                	<div class="clear">
                    	<em class="title">杂志描述</em>
                    	<em><textarea id="description" name="description" rows="4" class="input g470"   maxLength="500"></textarea>
                    	</em>
                    </div>
                    <div class="clear">
                    	<em class="title">编辑评价</em>
                    	<em><textarea id="assessment" name="assessment" rows='4'  class="input g470"   maxLength="500"></textarea>
                    	</em>
                    	<em class="title">（此项为选填项）</em>
                    </div>
                    <div class="clear">
                    	<em class="title">创刊时间</em>
                    	<em>
                    	<input type="text" id="createDate" name="createDate" class="input g90" value="<#if createDate??>${createDate?string('yyyy-MM-dd')}</#if>"/>
                    	</em>
                    </div>
                    <div class="clear clip">
                    	<em class="title">签约杂志</em>
                    	<em class="type1"><em>是否签约杂志：</em><label><input id="signing" name="signing" value="0" checked='checked	' type="checkbox"></label></em>
                    	
                    </div>
                	<div class="clear ">
                    	<em class="title">杂志社地址</em>
                    	<em><input id="address" name="address" type="text" class="input g470" /></em>
                    </div>
                    <script>
                    	$(function(){
							jQuery.fn.isChildAndSelfOf = function(b){return (this.closest(b).length > 0)};
							$("#category .g470").focus(function(){
								$("#category .selectBox").stop(true,true).fadeIn(200);
							});
							$(document).click(function(event){
								if(!$(event.target).isChildAndSelfOf ("#category")){
									$("#category .selectBox").fadeOut(200);
								}
							});
						
							$("input[name='categorybox']").change(function(e){
								if($("input[name='categorybox']:checked").length > 3){
									$(this).removeAttr("checked");
									alert("不能在选择了已经选了3个了!");
								}
								chooseAllSorts();
							});
							
							function chooseAllSorts(){
								var value = "",
									text = "";
								$("input[name='categorybox']:checked").each(function(){
									value += "," + $(this).val();
									text += "," + $(this).parent().text();
								});
								value = value.slice(1);
								text = text.slice(1);
								
								$("#showSorts").val(text);
								$("#submitSorts").val(value);
							}
						})
                    </script>
                	<div class="clear " id="category">
                    	<em class="title">杂志类目</em>
                    	<em><select id="sort" name="sort" >
                    			<#if categoryList??>
								<#list categoryList as category>
									<option value="${(category.id)!''}">${(category.name)!''}</option>
								</#list>
								</#if>
                    	</select>
                    	</em>
                    </div>
                	<div class="clear clip">
                    	<em class="title">杂志排列</em>
                    	<em class="type1"><label><input id="whratio" name="whratio" value="0" type="radio">单页</label><em class="icon16question"  title="期刊的宽度小于或等于高度"></em></em>
                    	<em class="type2"><label><input id="whratio" name="whratio" value="1" type="radio">跨页</label><em class="icon16question"  title="期刊的宽度小于高度的2倍"></em></em>
                        <em class="tips">直接影响杂志上传后的处理方式，请正确选择</em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">杂志刊号</em>
                    	<em><input id="issn" name="issn" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl">
                    	<em class="title">出版人</em>
                    	<em><input id="publisher" name="publisher" type="text" class="input g170" /></em>
                    </div>
                    <div  class="clear ">
			        	<em class="title"> 期刊类型</em>
			            <em>
			                 <select name="issueType" id="issueType">
			                    <option  value="2">周刊</option>
			                    <option  value="3">半月刊</option>
			                    <option  value="4">月刊</option>
			                    <option  value="5">双月刊</option>
			                    <option  value="6">季刊</option>
			                    <option  value="7">半年刊</option>
			                    <option  value="1">日报</option>
			                    <option  value="20">其它</option>
			                 </select>
			            </em>
		        	</div>
		        	
		        	<div  class="clear ">
			        	<em class="title">语言</em>
			        	<em>
			        	 <select class="col-8" id="languageId" name="languageId">
							<option  value="1">中文</option>
							<option  value="2">英语</option>
						 </select>
						</em>
			        </div>
		        	
                	<div class="clear floatl g320">
                    	<em class="title">新浪微博账号</em>
                    	<em><input name="weibo_uid" id="weibo_uid" type="text" class="input g170" maxLength="200" /></em><em class="icon16question" id="help_publisher" title="点击？查看详细"></em>
                    </div>
                	<div class="floatl">
                    	<em class="title">新浪微博效验码</em>
                    	<em><input name="weibo_verifier" id="weibo_verifier" type="text" class="input g170" maxLength="10" /></em>
                    </div>
                    <div class="clear floatl g320">
                    	<em class="title">刊物属性</em>
                    	<em><input name="isfree" id="isfreea" type="radio" class="input" value="1" >免费</input></em><em><input name="isfree" id="isfreeb" checked type="radio" class="input" value="0">收费</input></em>
                    </div>
                	<div class="floatl">
                    	<em class="title">刊物种类</em>
                    	<em><select class="col-8" id="pubType" name="pubType">
							<option  value="0">杂志</option>
							<option  value="1">DM</option>
							<option  value="2">企业内刊</option>
							<option  value="3">互动杂志</option>
						 </select></em>
                    </div>
					<hr class="clear" />
                    <h6>职位信息（选填）</h6>
                	<div class="clear floatl g300">
                    	<em class="title">社长</em>
                    	<em><input id="data_c1" name="data_c1" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">总编</em>
                    	<em><input id="data_c2" name="data_c2" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">主编</em>
                    	<em><input id="data_c3" name="data_c3" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">副主编</em>
                    	<em><input id="data_c4" name="data_c4" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">执行主编</em>
                    	<em><input id="data_c5" name="data_c5" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">首席顾问</em>
                    	<em><input id="data_c6" name="data_c6" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">运营总监</em>
                    	<em><input id="data_c7" name="data_c7" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">编辑部主任</em>
                    	<em><input id="data_c8" name="data_c8" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">编辑总监</em>
                    	<em><input id="data_c9" name="data_c9" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">创意总监</em>
                    	<em><input id="data_c10" name="data_c10" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">设计总监</em>
                    	<em><input id="data_c11" name="data_c11" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">摄影总监</em>
                    	<em><input id="data_c12" name="data_c12" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">发行总监</em>
                    	<em><input id="data_c13" name="data_c13" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">策划总监</em>
                    	<em><input id="data_c14" name="data_c14" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">媒介总监</em>
                    	<em><input id="data_c15" name="data_c15" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">广告总监</em>
                    	<em><input id="data_c16" name="data_c16" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">文字编辑</em>
                    	<em><input id="data_c17" name="data_c17" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">专题编辑</em>
                    	<em><input id="data_c18" name="data_c18" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">英文网站</em>
                    	<em><input id="data_c19" name="data_c19" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">网站编辑</em>
                    	<em><input id="data_c20" name="data_c20" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">流程编辑</em>
                    	<em><input id="data_c21" name="data_c21" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">记者</em>
                    	<em><input id="data_c22" name="data_c22" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">美术设计</em>
                    	<em><input id="data_c23" name="data_c23" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">美术编辑</em>
                    	<em><input id="data_c24" name="data_c24" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">摄影记者</em>
                    	<em><input id="data_c25" name="data_c25" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">发行主管</em>
                    	<em><input id="data_c26" name="data_c26" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">广告主管</em>
                    	<em><input id="data_c27" name="data_c27" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">媒介经理</em>
                    	<em><input id="data_c28" name="data_c28" type="text" class="input g170" /></em>
                    </div>
                	<div class="clear floatl g300">
                    	<em class="title">媒介执行</em>
                    	<em><input id="data_c29" name="data_c29" type="text" class="input g170" /></em>
                    </div>
                	<div class="floatl g300">
                    	<em class="title">排版校队</em>
                    	<em><input id="data_c30" name="data_c30" type="text" class="input g170" /></em>
                    </div>
                    <hr class="clear" />
                    <#if session_admin?exists>
        			<#else>
                	<div class="action">
                    	<em><a id="editPublicationFormSubmit" href="javascript:void(0)" class="btnBB">保存</a></em>
                    	<em><a href="#publicationOwn" id="newPublicationFormSubmit" class="btnWB">新建</a></em>
                    	<em id="tipError" class="tipsError">请填写相关信息</em>
                    </div>
                    </#if>
                </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<@pc.target/>
<@pcHelp.main />