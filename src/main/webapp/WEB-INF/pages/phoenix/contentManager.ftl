<#import "../components/phoenixAdminHeader.ftl" as header>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/phoenix/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/datepicker.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/phoenix/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/articleManager.js"></script>
<link href="${systemProp.staticServerUrl}/phoenix/style/pop.css" rel="stylesheet" type="text/css" />
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<!--header-->
<@header.main menuId="category"/>
<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix tCenter">
<a href="${systemProp.domain}/phoenix/phoenix-category.action" class="btnSM">栏目管理</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${systemProp.domain}/phoenix/phoenix-article!content.action" class="btnAM">内容管理</a>
        </div>

    	<div class="conB">
        	<fieldset>
                <div class="clearFix">
                    <div>
                        <em style="width:50px">是否收费</em>
                        <em class="g80">
                            <select id="free-select" style="width:80px">
                                <option value="" selected="selected">全部</option>
                                <option value="0">收费</option>
                                <option value="1">免费</option>
                            </select>
                        </em>
                        <em style="width:50px">栏目</em>
                        <em class="mgr50" style="width:200px"><select id="cate-select" style="width:200px"><option value="">请选择</option></select></em> 
                        <em style="width:50px">标题</em>
                        <em class="mgr50" style="width:200px"><input id="title" style="width:200px"/></em>                   
                    </div>
                    <div>
                        
                    </div>
                    <div>
                        <em class="floatr"><a id="passBtn" class="btnBS" href="javascript:void(0);">+添加</a></em>
                        <em style="width:50px">状态</em>
                        <em class="g80">
                        	<select id="status-select" style="width:80px">
                        		<option value="">全部</option>
                                <option value="1">已审核</option>
                                <option value="2">未审核</option>
                        	</select>
                        </em>  
                        <em style="width:50px">开始时间</em>
                        <em class="mgr50" style="width:200px"><input id="from-date" style="width:200px"/></em>  
                        <em style="width:50px">结束时间</em>
                        <em class="mgr50" style="width:200px"><input id="end-date" style="width:200px"/></em>
                        <em><a id="search-article" class="btnBS" href="javascript:void(0);">查询</a></em>
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="conB con03">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g80">缩略图</td>
                    <td class="g50">封面故事</td>
                    <td class="g50">排序</td>
                    <td class="g200">标题</td>
                    <td>描述</td>
                    <td class="g60">状态</td>
                    <td class="g60">推送</td>
                    <td class="g80">频道/栏目</td>
                    <td class="g80">创建日期</td>
                    <td class="g90">操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContent">
                    
                </tbody>
            </table>            
	        </div>
        
        <div class="conFooter">
            <div id="eventListPageadd" class="gotoPage"></div>
			<div id="eventListPage" class="changePage" ></div>
		</div>
    </div>




    
</div>
<@editContent />
<#macro editContent>

        <div class="popContent" id="contentModDialog">
    	<fieldset>
            <h6>内容修改</h6>
            <form method="post" id="uploadArticleForm" enctype="multipart/form-data" onsubmit="return false;">
            <input type="hidden" name="id" class="input g300" />
               <div>
                    <em class="g50">标题</em>
                    <em><input type="text" name="title" class="input g300" /></em>
                </div>
                <div>
                    <em class="g50">描述</em>
                    <em><input type="text" name="description" class="input g300" /></em>
                </div>
                <div>
                    <em class="g50">栏目</em>
                    <em><select id="add-cate-select" name="categoryId" style="width:200px"><option value="">请选择</option></select></em>
                </div>
                <div>
                    <em class="g50">图片</em>
                    <em><input type="file" name="articleImg" id="articleImg" class="inputFile g300" /></em>
                </div>
                <div>
                    <em class="g50">zip包</em>
                    <em><input type="file" name="articleZip" id="articleZip" class="inputFile g300" /></em>
                </div>
            <div class="actionArea tRight">
                 <em class="g50">&nbsp;</em>
                    <em><a href="#" id="save_content" class="btnAM">保存</a></em>
                    <em><a href="#" id="cancel_content" class="btnWM">取消</a></em>
            </div>
            </form>
        </fieldset>
</div>
</#macro>
</body>
</html>