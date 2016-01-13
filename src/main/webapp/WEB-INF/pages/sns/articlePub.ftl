<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>发布作品 - 麦米网Magme</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imageUploader.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/channelM1_1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/swfobject.js"></script>
<script src="${systemProp.appServerUrl}/v3/kindeditor/kindeditor.js"></script>


<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>


<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/articlePub.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>







<script>
	KE.show({
		id : 'cr-text-content',
		resizeMode : 0,
		allowPreviewEmoticons : false,
		allowUpload : false,
		items : ['fullscreen','source','bold','link']
	});
	function uploadServiceUrl(){
		return "http://www.magme.com/sns/creative-img.action";
	}
</script>
<style>
.ke-toolbar-outer{ background:url(${systemProp.appServerUrl}/v3/kindeditor/skins/magme/toolsBg.gif) 0 0 repeat-x;}
.ke-icon{ width:26px; height:26px; border:transparent;}
.ie6 .ke-icon{ border:0;}
.ke-icon:hover{border-color:#aaa; background-color:#ddd;}
.ke-icon-selected,
.ke-icon-selected:hover{ background:#ccc}
.ke-icon .ke-common-icon{ width:26px; height:26px; background-image:url(${systemProp.appServerUrl}/v3/kindeditor/skins/magme/magme.gif); background-repeat:no-repeat; border:0;}
.ke-icon .ke-icon-bold{ background-position:0 0;}
.ke-icon .ke-icon-italic{ background-position:-52px 0;}
.ke-icon .ke-icon-underline{ background-position:-26px 0;}
.ke-icon .ke-icon-insertorderedlist{ background-position:-104px 0;}
.ke-icon .ke-icon-insertunorderedlist{ background-position:-78px 0;}
.ke-icon .ke-icon-source{ background-position:-130px 0;}
.ke-icon .ke-icon-fullscreen{ background-position:-156px 0;}
.ke-icon .ke-icon-link{ background-position:-182px 0;}
.ke-toolbar-separator{ position:relative; top:7px;}
.ke-bottom{ display:none;}

</style>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script>
var imgUploader = $.imageUploader();
// 供flash调用的js方法

// id 第一张为1,张二张为2, 类推, name = 图片名字
function uploadStart(id,name) {
	imgUploader.start(id,name);
	sharePage.transformLayout();
}

// id 相应图片的id, precent = 百分比
function uploadProgress(id,percent) {
	imgUploader.progress(id,percent);
}

// id 相应图片的id
function uploadError(id) {
	imgUploader.error(id,"error");
}

// id 相应图片的id , url 返回图片地址, w 该图片的宽, h 该图片的高
function uploadComplete(id,url,w,h) {
	imgUploader.complete(id,url,w,h);
}
</script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/creative.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/music.js"></script>

</head>
<body class="pageM1Share">
<!--header-->
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>
<input type="hidden" id="articleSortOrder" value="<#if intent==2 || intent==0>${(article.sortOrder)!'0'}<#else>-1</#if>"/>
<!--body-->
<div class="body">
        <!--conLeftMiddle-->
        <div class="conShareOuter shareWorks sideLeftMiddle">
        
            <div class="pageTitle">
                <h1>1分钟制作属于你的杂志</h1>
            </div>
            
            <div class="pageInner clearFix">
            
               <#--只有认证编辑有这段,普通用户和seoer都没有-->
               <#if userLevel?? && userLevel==1 >
                  <#if intent?? && intent==0>
	                <div class="clearFix conShareSeriesSelect">
	                	<label id="conShareSeriesSelect"><input id="creativeFlag" name="creativeFlag" type="checkbox" />创建系列</label>
	               	</div>
	              </#if>
	                <div id="creativeFlagShow" class="clearFix conShare conShareSeries ">
	              <#if intent?? && intent==0>
	                    <div class="hide" id="creativeTitlediv"><em class="title" judgeTitle="">系列标题<span>（20字以内）</span></em><input id="creativeTitle" type="text" class="input" value=""/></div>
	                    <div class="hide" id="creativeDescriptiondiv"><em class="title" judgeDesc="">系列描述<span>（75字以内）</span></em><textarea id="creativeDescription" class="input"></textarea></div>
	              </#if>
	               </div>
               </#if>
               <#if userLevel?? && (userLevel==1 || userLevel==3) && intent?? && intent==0 && creativeCategoryTree?? && (creativeCategoryTree?size>0)>
	                <div class="clearFix conShare conShareCategory">
	                    <em class="title">选择分类</em>
	                       <#list creativeCategoryTree as creativeCategory>
			                    <dl>
			                        <#-- 一级分类 ,一级分类的颜色表示-->
			                    	<dt><label class="navC<#if userLevel==1>${creativeCategory.id}<#elseif userLevel==3>${creativeCategory_index+1}</#if>" parentid="${creativeCategory.id}">${creativeCategory.name}</label></dt>
			                    	<#-- 二级级分类 -->
			                    	<#if (creativeCategory.childCreativeList)?? && ((creativeCategory.childCreativeList)?size>0)>
				                    	<#list creativeCategory.childCreativeList as cc>
					                    	<dd><label><input type="checkbox" value="${cc.id}" parentId="${cc.parentId}" childCategoryId="${cc.id}"/>${cc.name}</label></dd>
				                    	</#list>
				                    	
				                    	<#--前四个有图库标示-->
				                    	<#if userLevel?? && userLevel==1 &&  (creativeCategory.id<5)>
				                    		<dd class="album"><label><input type="checkbox" name="picCollection"  parentId="${creativeCategory.id}"/>[图库]</label></dd>
				                    	</#if>
			                    	</#if>
			                    </dl>
		                    </#list>
	                    
	               	</div>
               	</#if>
               
               <#if (intent==2 && articleInCreative?? && (articleInCreative.size()>1)) || (intent!=2 && articleInCreative?? && (articleInCreative.size()>0))>
	               <div class="clearFix conShare conShareMore">
	                	<em class="title">已发布的系列页面</em>
	                	<div class="pageList">
	                	 <#list articleInCreative as singleArticle>
	                	   <#--发布系列页，第一篇文章不能删除，编辑作品，文章，不能删除自己-->
	                    	<div class="item">
	                        	<div class="pic"><img src="${systemProp.staticServerUrl}<#if (singleArticle.imagePath)??>${singleArticle.imagePath}<#else>/v3/images/temp/home9.jpg</#if>" /></div>
	                            <strong>${(singleArticle.title)!''}</strong>
	                            <#if (!(article??) && singleArticle_index!=0 )|| ((article.id)?? && article.id!=singleArticle.id)>
	                            <div class="tools">
	                               <a href="${systemProp.appServerUrl}/sns/article-pub!edit.action?id=${(singleArticle.id)!'0'}" class="edit">编辑</a>
	                               <#if (singleArticle.sortOrder)?? && singleArticle.sortOrder!=0>
	                                  <a singleArticleId="${(singleArticle.id)!'0'}" href="javascript:void(0)" class="del">删除</a>
	                               </#if>
	                            </div>
	                            </#if>
	                        </div>
	                     </#list>
                        </div>
	                </div>
                </#if>
                
                <div class="clearFix conShare conShareTitle <#if userLevel?? && userLevel==1>userTypeB<#else>userTypeA</#if>">
                    <div class="userA">
                        <em class="firstFont">
                        	<span class="floatl">首字下沉</span>
                            <select class="input" id="bigWordCount">
                            	<option <#if !(article??) || !(article.bigWordCount??) || article.bigWordCount==0 > selected </#if> >0</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==1) >selected</#if> >1</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==2) >selected</#if> >2</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==3) >selected</#if> >3</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==4) >selected</#if> >4</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==5) >selected</#if> >5</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==6) >selected</#if> >6</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==7) >selected</#if> >7</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==8) >selected</#if> >8</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==9) >selected</#if> >9</option>
                            	<option <#if (article?? && article.bigWordCount?? && article.bigWordCount==10) >selected</#if> >10</option>
                            </select>
                        </em>
                    	<em class="title" judgeTitle="">标题<span>（20字以内）</span></em>
                    	<input id="cr-text-title" type="text" class="input" value="<#if (article.title)??>${article.title!}</#if>"/>
                    	<em style='color: red;display:none;text-align: right;' class='title' id='cr-text-titleError'>标题不能为空</em>
                    </div>
                    <#if userLevel?? && userLevel==1>
                    <div class="userB"><em class="title" judgeTitle="">显示标题<span>（20字以内）</span></em><input id="secondTitle" type="text" class="input" value="<#if (tempCreative.secondTitle)??>${tempCreative.secondTitle!''}</#if>"/></div>
                    </#if>
                    <#if userLevel?? && (userLevel==3 || (userLevel==1 && intent==0))>
                    <div class="userA"><em class="title" judgeTitle="">来源标题</em><input id="cr-text-origin" type="text" class="input" value="<#if (tempCreative.origin)??>${tempCreative.origin!}</#if>"/></div>
                    <div class="<#if userLevel==3>userA<#else>userB</#if>"><em class="title" judgeTitle="">来源链接</em><input id="cr-text-originurl" type="text" class="input" value="<#if (tempCreative.originUrl)??>${tempCreative.originUrl!}</#if>"/></div>
                    </#if>
                    
               	</div>
               	
               	<div class="clearFix conShare conShareTitle <#if userLevel?? && userLevel==1>userTypeB<#else>userTypeA</#if>">
               	</div>
                
                <div class="clearFix conShare conShareContent <#if userLevel?? && userLevel==1>userTypeB<#else>userTypeA</#if>">
                	<div class="userA">
                        <em class="title" judgeTitle="">内容</em>
                        <textarea  id="cr-text-content"  name="content"><#if (article.content)??>${article.content!}</#if></textarea>
                    </div>
                    <#if userLevel?? && userLevel==1>
                    <div  class="userB"><em class="title" judgeDes="">显示描述<span>（75字以内）</span></em><textarea class="input"  id="secondDescription"  ><#if (tempCreative.secondDesc)??>${tempCreative.secondDesc!''}</#if></textarea></div>
                    </#if>
                </div>

                <#if userLevel?? && userLevel==1 >
                <div class="conURL">
                	<em class="title">杂志链接<span>(可选)</span></em><input id="cr-text-magazineUrl" type="text" value="${(tempCreative.magazineUrl)!''}" class="input" />
                	<input id="issueid" name="issueid" type="hidden"  value="${(tempCreative.issueid)!''}" />
	                <input id="publicationid" name="publicationid" type="hidden" value="${(tempCreative.publicationid)!''}" />
                	<input id="magazineName" name="magazineName" type="hidden" value="${(tempCreative.magazineName)!''}" />
                	<em id="magazineUrlError" class="title" style="display:none;color:red;text-align: right;"></em>
                </div>
                </#if>
                
                <#-- 这些是隐藏字段 -->
                <input id="cr_operate" name="operate" type="hidden" value="${operate}" />
                <input id="s_c_i_d" type="hidden" value="<#if (article.id)??>${article.id!}</#if>" />
                <input id="creativeId" type="hidden" value="<#if (creativeId)??>${creativeId!}</#if>" />
                <input id="_img_s_t" type="hidden" value="0" />
                <input id="audit_editor" name="audit_editor" type="hidden" value="${userLevel!'0'}" />
                <input id="intent" name="intent" type="hidden" value="${intent!'0'}" />
                
				<#if operate=="works" >
					<div class="conShare conWorkTabs clearFix" style="display:block;">
	                	<a class="imgBtn png current" href="javascript:void(0);">图片</a>
	                    <a class="musicBtn png" href="javascript:void(0);">音乐</a>
	                    <a class="videoBtn png" href="javascript:void(0);">视频</a>
	                </div>
                </#if>
                <#if operate=="music" || operate=="works" >
                
	                <div class=" conShareMusic">
	                	<div class="addMusic">
	                        <em class="title">搜索音乐</em>
	                        <div class="inner">
	                            <div class="search"><span class="ico"></span><input type="text" class="input" /></div>
	                            <div id="musicList" class="list">
	                                <div class="inner">
	                                    <a href="#">听不到 — 五月天</a>
	                                </div>
	                                <div class="fot">
	                                    共有2000首和"五月天"相关的歌曲（音乐服务由 <a href="#">虾米网</a> 提供）
	                                    <div class="page"><a href="#">上一页</a><a href="#">下一页</a></div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                    <em style='color: red;display:none;text-align: right;' class='title' id='cr-music-Error'>请选择音乐</em>
	                    <div style="display:none;" class="myMusic clearFix">
	                        <!--<a href="javascript:void(0);" class="close"></a>-->
	                        <div class="cd"><img id='musimg' src="${systemProp.staticServerUrl}/v3/images/tudoudefault.jpg" /><span class="ico png"></span></div>
	                        <div class="player" style="width:250px; height:40px; background:#999;"></div>
	                    </div>
	                </div>
                </#if>
                
                <#if operate=="video" || operate=="works">
	                <div class=" conShareVideo">
	                    <div class="addVideo">
	                        <em class="title">视频地址<span>( 优酷 / 土豆 / 酷6 )</span> <span id="cr_video_error_sp" style="color: red;display:none;text-align: right;"></span></em>
	                         <div class="search"><span class="ico"></span><input id="cr_video_url" type="text" class="input" /></div>
	                        <em id="cr_video_error" style='color: red;display:none;text-align: right;' class="title"></em>
	                    </div>
	                    
	                    <div id="myVideo" class="myVideo clearFix">
	                        <!--<a href="javascript:void(0);" class="close"></a>-->
	                        <#--这个容器不要了，没有这个示例了
	                        <div class="player"><img id="myVideoImg" src="http://i1.tdimg.com/b/20120406/c73.jpg" /><span class="ico png"></span></div>
	                        <p id="myVideoTitle">日本拦截导弹部署完毕 随时应对朝鲜卫星，日本拦截导弹部署完毕 随时应对朝鲜卫星~！</p>-->
	                    </div>
					</div>
                </#if>
                
                <#if operate=="image" || operate=="works">
	                <div class=" conAddImages clearFix" id="addImages">
	                    <a class="png" href="javascript:void(0);">添加图片<div class="swf"><div id="swfloader"></div></div></a><p>支持jpg、gif或png格式，按Ctrl或Shift多选图片</p>
	                </div>
	                 <div class="clearFix" style="color:red;display:none;text-align: right;" id="ImagesError">
	                    <span>请上传图片</span>
	                </div>
                  	<#if operate=="works">
                  		 <div class="conShareImages" style="display:block;">
                  		 <#if intent?? && intent==2>
                  		  <script>
						 		getArticleEditImgEx();
						  </script>
						  </#if>
		                  	 <div class="imagesCon" >
		                    	<div class="ico png"></div>
		                        <div class="imagesList clearFix" id="imagesList">
		                        	<div class="inner">
                            		</div>
		                        </div>
		                    </div>
	                    </div>
                    </#if>
                    <#if operate=="image">
	                <div class="conShareImages">
	                    <div class="imagesCon">
	                    	<div class="ico png"></div>
	                        <div class="imagesList clearFix" id="imagesList">
	                        	<div class="inner">
                        		</div>
	                        </div>
	                    </div>
	                    <div class="layout" id="imgLayout">
	                        <em class="title">图片布局</em>
	                        <div class="inner">
	                            <div class="album album2 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type1"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span></a>
	                            </div>
	                            <div class="album album3 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type1"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span></a>
	                            </div>
	                            <div class="album album4 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type1"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type1"></span></a>
	                            </div>
	                            <div class="album album5 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type1"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type2"></span></a>
	                            </div>
	                            <div class="album album6 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type3"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type3"></span></a>
	                            </div>
	                            <div class="album album7 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type3"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type3"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type2"></span><span class="type2"></span></a>
	                            </div>
	                            <div class="album album8 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type3"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type3"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type3"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type3"></span><span class="type2"></span></a>
	                            </div>
	                            <div class="album album9 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type2"></span><span class="type3"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type3"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span><span class="type3"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type3"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type2"></span><span class="type2"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type3"></span><span class="type3"></span></a>
	                            </div>
	                            <div class="album album10 clearFix">
	                                <a href="javascript:void(0);" class="item"><span class="type1"></span><span class="type3"></span><span class="type3"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type2"></span><span class="type3"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type3"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type2"></span><span class="type3"></span><span class="type3"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type2"></span><span class="type2"></span><span class="type3"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type2"></span><span class="type3"></span><span class="type2"></span></a>
	                                <a href="javascript:void(0);" class="item"><span class="type3"></span><span class="type3"></span><span class="type2"></span><span class="type2"></span></a>
	                            </div>
	                        </div>
	                	</div>
	                </div></#if>
                </#if>
                
            </div>
            
           
            <div class="conSoleBtns clearFix">
                <a id="btnWB_make" class="btnGB">一键制作</a>
            </div>
           
        </div>
    
        <!--conRight-->
        <div class="conRight">
           <#-- 专题页暂时没有，注释掉
            <div class="con conTopic">
                <h6>选择专题</h6>
                <div class="conBody">
                	<ul>
                    	<li><label><input type="checkbox" />专题标题标题标题专题标题标题标题专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    	<li><label><input type="checkbox" />专题标题标题标题</label></li>
                    </ul>
                </div>
            </div> -->
            <#if intent?? && intent==0>
	            <div class="con conClass">
	                <h6>增加标签</h6>
	                <div class="inner">
	                    <ul id="tags" class="clearFix">
	                        <#-- 默认不输出标签，和yq确认过了 
	                        <li class="current"><a href="javascript:void(0);">电子</a></li>
	                        <li class="current"><a href="javascript:void(0);">科技</a></li>
	                        <li><a href="javascript:void(0);">人文</a></li>
	                        <li><a href="javascript:void(0);">时尚</a></li>
	                        <li><a href="javascript:void(0);">美食</a></li>
	                        <li><a href="javascript:void(0);">电子电子电</a></li>
	                        <li><a href="javascript:void(0);">科技科技</a></li>
	                        <li><a href="javascript:void(0);">人文人</a></li>
	                        <li><a href="javascript:void(0);">时尚</a></li>
	                        <li><a href="javascript:void(0);">美食</a></li>-->
	                        
	                        <li id="addcurrent2" class="add"><input id="addtagsText" type="text" tips="添加新标签" /><a id="addtagsBtn" href="javascript:void(0)"></a></li>
	                    </ul>
	                </div>
	            </div>
            </#if>
            <div class="con conPublic">
            <#-- 增加系列作品第二篇文章以后没有这个选项,seo 没有这个选项-->
            <#if !(userLevel??)>
               <a class="ploy" href="javascript:void(0);"><input type="checkbox" id="cr-cb-ploy" name="ploy" value="0">仅自己可见</a>
            <#elseif userLevel==0 >
               <a class="ploy" href="javascript:void(0);"><input type="checkbox" id="cr-cb-ploy" name="ploy" value="0">仅自己可见</a>
             <#--认证编辑部再显示这个选项 20130111-->
            <#--<#elseif userLevel==1 && !(creativeId??)>
               <a class="ploy" href="javascript:void(0);"><input type="checkbox" id="cr-cb-ploy" name="ploy" value="0" checked="checked" >仅自己可见</a>-->
            </#if>
            </div>
            <#if userLevel?? && userLevel==1>
             <div class="con conRss">
                <h6>网易云阅读频道</h6>
                <div class="conBody">
                	<ul>
                	<#if neteaseCcList?? && (neteaseCcList?size>0)>
                	  <#list neteaseCcList as cc>
                	    <#assign check=0>
                	    <#if neteaseCncrList?? && (neteaseCncrList?size>0)>
                	     <#list neteaseCncrList as neteaseCncr>
                	        <#if neteaseCncr.categoryId==cc.id>
                	          <#assign check=1>
							</#if>
						  </#list>
						</#if>
						<#if check==1>
                    		<li><label><input ccid=${cc.id} type="checkbox" checked="checked" />${cc.name}</label></li>
                    	<#else>
                    		<li><label><input ccid=${cc.id} type="checkbox" />${cc.name}</label></li>
                    	</#if>
                      </#list>
                    </#if>
                    </ul>
                </div>
            </div>
            </#if>
            
        </div>
</div>


<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>