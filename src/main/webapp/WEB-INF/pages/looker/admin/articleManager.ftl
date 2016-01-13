<link href="${systemProp.staticServerUrl}/look/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/page/pagination.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/jquery.validate.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/validate/messages_cn.js"></script>
<link href="${systemProp.staticServerUrl}/js/validate/jquery.validate.css"  rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/articleManager.js"></script>
<div class="body" menu="item" submenu="article">
    <div class="conLeftMiddleRight">
    	<div class="con116 conTools">
        	<fieldset>
              <div>
                    <em class="g105">
                    <select id="s-status" class="g100">
                    <option value="-1">全部状态</option>
                    <option value="1">上架</option>
                    <option value="0">下架</option>
                    <option value="2">待审核</option>
                    <option value="5">待一审</option>
                    <option value="6">待二审</option>
                    <option value="7">待终审</option>
                    <option value="8">待上架</option>
                    <option value="4">未通过</option>
                    <option value="3">定时</option>
                    </select>
                    </em>
                    <em class="g105"><select id="s-category" class="g100"><option value="-1">全部分类</option></select></em>
                    <em class="g105"><select id="s-item" class="g100"><option value="-1">全部栏目</option></select></em>        
                    <em class="g65"><input id="s-title" type="text" class="input g60" tips="名称" /></em>
                    <em class="g65"><input id="s-cuser" type="text" class="input g60" tips="上传用户" /></em>
                    <em class="g75"><input id="s-createTime" type="text" class="input g70" tips="发布日期" /></em>
                    <em class="g75"><input id="s-publishDate" type="text" class="input g70" tips="上架日期" /></em>
                    <em><a class="btnGS" id="searchBtn" href="#">查询</a></em>
                    <em><a class="btnSS" id="newBtn" href="#">添加</a></em>
                    <em><a class="btnSS" id="batOnBtn" href="#">批量上架</a></em>
                    <!--em><a class="btnSS" id="batStatusAcceptBtn" href="#">批量通过</a></em-->
                    <em><a class="btnSS" href="${systemProp.appServerUrl}/look/look-plan-article.action">定时上架</a></em>
                </div>
            </fieldset>
        </div>
        <div class="con117 conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                	<td class="g20"></td>
                	<td class="g50">缩略图</td>
                    <td class="g30">ID</td>
                    <td class="g70">分类</td>
                    <td class="g80">栏目 / 账号</td>
                    <td class="g90">发布</td>
                    <td class="g90">名称</td>
                    <td>描述</td>
                    <td class="g60">状态</td>
                    <td class="g90">上架</td>
                    <td class="g30">推荐</td>
                    <td class="g140">操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">          
                     
                </tbody>
            </table>
        </div>
       
        <#import "../../components/pagebar.ftl" as pageBar>
	   <@pageBar.main/>
    </div>
</div>

<@editContent />
<#macro editContent>
 <div class="popContent" id="pop100">
            <h6>添加/编辑</h6>
            <form method="post" id="submitForm" name="submitForm" enctype="multipart/form-data" onsubmit="return false;">
            <fieldset>
            	<em class="g80 tRight conAddBtn"><a id="additemBtn" class="btnGS btnCLS" href="javascript:void(0)">增加栏目</a></em>
            	<div id="itemdiv" class="con1">
	            	<em class="g100 tRight">&nbsp;</em>
	            	<p class="g200 floatl" id="aditemp">
	            	</p>
	            </div>
            	<input type="hidden" id="id" name="id">
	             <div>
	            	<em class="g100 tRight">标题</em>
	            	<em><input id="title" name="title" type="text" class="input g140"></em>
	            	<em class="g40 tRight">置顶</em>
	            	<em><input id="isTop" name="isTop" type="checkbox"></em>
	            </div>
	             <div>
	            	<em class="g100 tRight">描述</em>
	            	<em><textarea row=2 name="memo" id="memo" class="input g260"></textarea></em>
	            </div>
	             <div>
	            	<em class="g100 tRight">正文</em>
	            	<em><textarea row=3 name="title2" id="title2" class="input g260"></textarea></em>
	            </div>
	             <div>
	            	<em class="g100 tRight">文件(mpres/zip)</em>
	            	<em><input id="articleZip" name="articleZip" type="file" class="g140"></em>
	            </div>
	             <div>
	            	<em class="g100 tRight">pad缩略图</em>
	            	<em><input id="smallPic1" name="smallPic1" type="file" class="g140">(472-472)</em>
	            </div>
	             <div>
	            	<em class="g100 tRight">phone缩略图</em>
	            	<em><input id="smallPic2" name="smallPic2" type="file" class="g140">(360-360)</em>
	            </div>
	            
	            <div id="aditemdiv" style="display:none">
	            <em class="clear auto tLeft"><select class="g140" name="item"></select><a name="deletePutBtn" href="javascript:void(0)" class="floatNone">X</a></em>
	            </div>
	           
	        </fieldset>
            <div class="actionArea tCenter">
                <a class="btnGS" id="saveBtn" href="javascript:void(0)">保存</a>
                <a class="btnWS" id="cancelBtn" href="javascript:void(0)">取消</a>
            </div>
            </form>
        </div>
</#macro>

<@lookContent />
<#macro lookContent>
 <div id="pop011" class="popContent">
            <h6>关联</h6>
            <fieldset>
            	<div class="tablehidden">
            	<input type="hidden" id="look-articleId"/>
	                <table width="100%" cellspacing="0" cellpadding="0" border="0" class="table JQtableBg">
	                  <thead>
	                    <tr>
	                        <td class="g30">排序</td>
	                        <td class="g140">关联内容标题</td>
	                        <td class="g80">类型</td>
	                        <td>关联内容链接</td>
	                        <td class="g130">操作</td>
	                      </tr>
	                    </thead>
	                    <tbody id="looktbody">
	                        
	                    </tbody>
	                </table>
	            </div>    
	        </fieldset>
            <div class="actionArea tCenter">
                <a href="javascript:void(0)" id="addLookBtn" class="btnGS">添加</a>
                <a href="javascript:void(0)" id="closeLookBtn" class="btnWS">关闭</a>
            </div>
        </div>
</#macro>