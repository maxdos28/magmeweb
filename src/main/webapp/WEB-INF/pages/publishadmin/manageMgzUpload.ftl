<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/uploadIssue.js"></script>
<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->

</head>
<body>
<div id="uploadIssueDialog" class="popContent">
	<fieldset>
		<h6>上传期刊</h6>
        <form method="post" id="uploadIssueForm" enctype="multipart/form-data" onsubmit="return false;">
			<div>
            	<em class="title">杂志名称</em>
            	<em>
            		<select id="publicationId" name="publicationId">
            		  <option value="0">请选择</option>
				      <#if publicationList?? && (publicationList?size>0)>
				         <#list publicationList as publication>
				             <option value="${publication.id}">${publication.name}</option>
				         </#list>
				      </#if>
				    </select>
            	</em>
            </div>
            <div>
            	<em class="title">文件</em>
            	<em><input type="file" id="issueFile" name="issueFile" /></em>
            </div>
			<div>
				<em class="title">期刊号</em>
			    <em class="g100">
			    	<select id="year" name="year" class="g70" >
			        	<option value="2010">2010</option>
			        	<option value="2011">2011</option>
			        	<option value="2012">2012</option>
			        	<option value="2013">2013</option>
			        </select>
			    	&nbsp;年
			    </em>
			    <em class="g80">
			    	<select id="month" name="month" class="g50" >
			            <option selected="selected" value="01">01</option>
					   	<option value="02">02</option>
					   	<option value="03">03</option>
					   	<option value="04">04</option>
					   	<option value="05">05</option>
					   	<option value="06">06</option>
					   	<option value="07">07</option>
					   	<option value="08">08</option>
					   	<option value="09">09</option>
					   	<option value="10">10</option>
					   	<option value="11">11</option>
					   	<option value="12">12</option>
			        </select>
			        &nbsp;月
			    </em>
			    <#assign days=1..31 />
				<em class="date">
					<input type="text" class="g70 input" id="day" name="day"/>
					<div id="selectDateBox">
						<#list days as day>
							<a>${day}</a>
						</#list>
						<a class="text clear">增刊</a>
			            <a class="text">副刊</a>
			            <a class="text">纪念刊</a>
					</div>
			    </em>
			</div>
			<div>
				<em class="title">发布日期</em>
				<em><input type="text" id="publishDate" name="publishDate" class="input" /></em>
			</div>
			<div>
				<em class="title">关键词</em>
				<em><input type="text" class="input" name="keyword" /></em><em>关键词便于用户搜索</em>
			</div>
			
			<div>
				<em class="title">描述</em>
				<em><textarea rows="4" class="input g400" name="description"></textarea></em>
			</div>
			<div>
				<em class="title"><input type="hidden" name="fileName" id="fileName" value=""/></em>
			    <em ><a id ="uploadIssueSubmit" href="javascript:void(0)" class="btnBS">确定</a></em>
			    <em id ="tipError" class="tipsError">请填写相关信息</em>
			</div>
	
		</form>
	</fieldset>
	
	<div id="progress-bar" title="上传进度" class="upload" style="display:none;">
    	<strong>上传进度:</strong>
    	<span id="current_size">0</span>
    	<span>/</span>
    	<span id="max_size">0</span>
    	<div class="inner"><sub id="bar-inner"></sub></div>
        <span id="persent">0%</span>
        <a id="cancelUpload" href="javascript:void(0)">取消</a>
    </div>

	<div class="tips">提示：关闭本窗口或刷新本页面将取消上传期刊！</div>

</div>
</body>
</html>