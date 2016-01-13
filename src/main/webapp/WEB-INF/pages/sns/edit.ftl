<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>更新作品(${creative.title!}) - 麦米网Magme</title>
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
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imageUploader.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/channelM1_1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/swfobject.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/kindeditor/kindeditor.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/snsedit.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/music.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/creative.js"></script>
<script>
	KE.show({
		id : 'cr-text-content',
		resizeMode : 0,
		allowPreviewEmoticons : false,
		allowUpload : false,
		items : [
		'bold', 'underline', 'italic', '|', 'insertorderedlist', 'insertunorderedlist' ,'|' ,'source','fullscreen'],
		filterMode:true,
		syncType : 'auto' //auto: 每次修改时都会同步
	});
	
	function uploadServiceUrl(){
		return "http://www.magme.com/sns/creative-img.action";
	}
</script>
<style>
.ke-toolbar-outer{ background:url(${systemProp.staticServerUrl}/v3/kindeditor/skins/magme/toolsBg.gif) 0 0 repeat-x;}
.ke-icon{ width:26px; height:26px; border-color:transparent;}
.ke-icon:hover{border-color:#aaa; background-color:#ddd;}
.ke-icon-selected,
.ke-icon-selected:hover{ background:#ccc}
.ke-icon .ke-common-icon{ width:26px; height:26px; background-image:url(${systemProp.staticServerUrl}/v3/kindeditor/skins/magme/magme.gif); background-repeat:no-repeat;}
.ke-icon .ke-icon-bold{ background-position:0 0;}
.ke-icon .ke-icon-italic{ background-position:-26px 0;}
.ke-icon .ke-icon-underline{ background-position:-52px 0;}
.ke-icon .ke-icon-insertorderedlist{ background-position:-78px 0;}
.ke-icon .ke-icon-insertunorderedlist{ background-position:-104px 0;}
.ke-icon .ke-icon-source{ background-position:-130px 0;}
.ke-icon .ke-icon-fullscreen{ background-position:-156px 0;}
.ke-toolbar-separator{ position:relative; top:7px;}
.ke-bottom{ display:none;}

</style>

<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script>
var imgUploader = $.imageUploader();
// 供flash调用的js方法
function uploadStart(id,name) {
	imgUploader.start(id,name);
	sharePage.transformLayout();
}
function uploadProgress(id,percent) {
	imgUploader.progress(id,percent);
}
function uploadError() {
	imgUploader.error(id,"error");
}
function uploadComplete(id,url,w,h) {
	imgUploader.complete(id,url,w,h);
}

</script>
</head>
<body>
<!--header-->
<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>


<!--body-->
<div class="body pageM1">
	<div class="sideLeftMiddleRight clearFix">
        <!--conLeftMiddle-->
        <div class="conShareOuter  
        				<#if creative.cType==2>
                			shareImages
                		<#elseif creative.cType==4>
                			shareVideo
                		<#elseif creative.cType==3>
                			shareMusic
                		<#elseif creative.cType==1>
                			shareText
                		<#elseif creative.cType==5>
                			shareWorks
                	</#if>">
        			<#if creative.cType==2>
                			 <input id="cr_operate" name="operate" type="hidden" value="image" />
                		<#elseif creative.cType==4>
                		 	<input id="cr_operate" name="operate" type="hidden" value="video" />
                		<#elseif creative.cType==3>
                		 	<input id="cr_operate" name="operate" type="hidden" value="music" />
                		<#elseif creative.cType==1>
                		 	<input id="cr_operate" name="operate" type="hidden" value="texta" />
                		<#elseif creative.cType==5>
                			<input id="cr_operate" name="operate" type="hidden" value="works" />
                	</#if>
            <div class="pageTitle">
                <h1>
                	<#if creative.cType==2>
                		修改图片
                		<#elseif creative.cType==4>
                		修改视频
                		<#elseif creative.cType==3>
                		修改音乐
                		<#elseif creative.cType==1>
                		修改文章
                		<#elseif creative.cType==5>
                		修改作品集
                	</#if>
                </h1>
            </div>
            <input id="s_c_i_d" type="hidden" value="${creative.id!}" />
			<script>
			 	getEditTags();
			</script>
            <div class="pageInner clearFix">
                <div class=" conShareText">
                    <em class="title">标题</em><input id="cr-text-title" name="title" type="text" value='${creative.title!}' class="input" />
                    <em style='color: red;display:none;text-align: right;' class='title' id='cr-text-titleError'>标题与内容必须填写一项</em>
               	</div>
				
				<#if creative.cType==5 >
					<script>
					 	getEditWorksEx();
					</script>
					<div class="conShare conWorkTabs clearFix" style="display:block;">
	                	<a class="imgBtn png current" href="javascript:void(0);">图片</a>
	                    <a class="musicBtn png" href="javascript:void(0);">音乐</a>
	                    <a class="videoBtn png" href="javascript:void(0);">视频</a>
	                </div>
                </#if>
                <#if creative.cType==3 || creative.cType==5 >
	                <div class=" conShareMusic">
		                <#if creative.cType==5 >
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
	                    </#if>
	                    <#if creative.cType==3>
	                    	<script>
					 			getEditMusicEx();
							</script>
		                    <div  class="myMusic clearFix">
		                        <!--<a href="javascript:void(0);" class="close"></a>-->
		                        <div class="cd"><img id='musimg' src="" /><span class="ico png"></span></div>
		                        <div class="player" style="width:250px; height:40px; background:#999;">
		                        	
		                        </div>
		                    </div>
	                    </#if>
	                </div>
	               
                </#if>
                
                <#if creative.cType==4 || creative.cType==5>
	                <div class=" conShareVideo">
	                <#if creative.cType==5 >
                		<div class="addVideo">
	                        <em class="title">视频地址<span>( 优酷 / 土豆 / 酷6 )</span> <span id="cr_video_error_sp" style="color: red;display:none;text-align: right;"></span></em>
	                        <input id=cr_video_url type="text" class="input" />
	                        <em id="cr_video_error" style='color: red;display:none;text-align: right;' class="title"></em>
	                    </div>
                	</#if>
                	<#if creative.cType==4>
		               	<script>
					 		getEditVideoEx();
						</script>
	                    <div id="myVideo" style='height: auto;' class="myVideo clearFix">
	                        <!--<a href="javascript:void(0);" class="close"></a>-->
	                        <div class="player"><img id="myVideoImg" src="" /><span class="ico png"></span></div>
	                        <p id="myVideoTitle"></p>
	                    </div>
	                </#if>
					</div>
                </#if>
                
                <#if creative.cType==2 || creative.cType==5>
	                
		                <div class=" conAddImages clearFix" id="addImages">
		                    <a class="png" href="javascript:void(0);">添加图片<div class="swf"><div id="swfloader"></div></div></a><p>支持jpg、gif或png格式，按Ctrl或Shift多选图片</p>
		                </div>
		                 <div class="clearFix" style="color:red;display:none;text-align: right;" id="ImagesError">
		                    <span>请上传图片</span>
		                </div>
		                <#if creative.cType==5>
	              		 <div class="conShareImages" style="display:block;">
		                  	 <div class="imagesCon" >
		                    	<div class="ico png"></div>
		                        <div class="imagesList clearFix" id="imagesList">
		                        	<div class="inner">
                            		</div>
		                        </div>
		                    </div>
	                    </div>
                    </#if>
                    <#if creative.cType==2>
                    <input id="_img_s_t" type="hidden" value="${creative.showType!}" />
                    <script>
				 		getEditImgEx();
					</script>
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
                <#if (session_user.reserve1??) && (session_user.reserve1=="M") >
	                <em class="title">杂志链接<span>(可选)</span></em><input id="cr-text-magazineUrl" name="magazineUrl" value='${creative.magazineUrl!}' type="text" class="input" />
	                <input id="magazineName" name="magazineName" type="hidden" value="${creative.magazineName!}" />
	                 <input id="issueid" name="issueid" type="hidden" value="${creative.issueid!}" />
	                <input id="publicationid" name="publicationid" type="hidden" value="${creative.publicationid!}" />
	                <em id="magazineUrlError" class="title" style="display:none;color:red;text-align: right;"></em>
	                <div <#if !creative.magazineName?? || creative.magazineName==''>style="display:none"</#if> class="friends">
	                <#if creative.magazineName?? || creative.magazineName!=''>
	                 	<script>
					 		getEditCreativeUser();
						</script>
	                </#if>
	                    <em class="title">参与人<span>(可选)</span></em>
	                    <div class="userList" id="userList">
	                        <div class="input">
	                            <a href="javascript:void(0);" class="open">添加参与人</a>
	                            <div id="creativeuser" class="inner clearFix"></div>
	                        </div>
	                        <div class="lists">
	                            <div id="userlistshow" class="inner clearFix">
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name1</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name2</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name3</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name4</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name5</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name6</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name7</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name8</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name9</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name10name10name10name10</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name11</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name12</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name13</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name14</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name15</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name16</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name17</strong><p>杂志编辑</p><sub></sub></a>
	                                <a href="javascript:void(0);" class="clearFix"><img src="${systemProp.staticServerUrl}/v3/images/head60.gif" /><strong>name18</strong><p>杂志编辑</p><sub></sub></a>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </#if>
                <div class="conText">
                    <em class="title">内容</em>
                    <textarea id="cr-text-content" name="content" style="width:630px;height:200px;visibility:hidden;">${creative.content!}</textarea>
                </div>
                
                <div class="conSoleBtns clearFix">
                    <a class="btnWB cancel" href="${systemProp.appServerUrl}/sns/user-index!home.action">取消</a><a id="btnWB_view"  class="btnWB view">预览</a><a id="btnGB_send" class="btnGB send">发布</a>
                </div>
            </div>
        </div>
    
        <!--conRight-->
        <div class="conRight">
            <div class="con conClass">
                <h6>选择标签</h6>
                <div class="inner">
                    <ul id="tags" class="clearFix">
                     	<li style='display:none'><a href="javascript:void(0);"></a></li>
                     	<li><a href='javascript:void(0);'>soho时尚编辑招聘</a></li>
                        <li><a href='javascript:void(0);'>soho汽车编辑招聘</a></li>
                        <li><a href='javascript:void(0);'>soho家居编辑招聘</a></li>
                        <li><a href='javascript:void(0);'>soho育儿编辑招聘</a></li>
                        <li><a href='javascript:void(0);'>soho旅游编辑招聘</a></li>
                        <li><a href='javascript:void(0);'>soho美食编辑招聘</a></li>
                        <li id="addcurrent" class="add"><input id="addtags" type="text" tips="添加新标签" /><a class="addtags" href="javascript:void(0)"></a></li>
                    </ul>
                </div>
            </div>
            <div class="con conPublic">
                <a class='ploy' href="javascript:void(0);"><input type="checkbox" id="cr-cb-ploy" name="ploy" value="1" <#if creative.ploy==1>checked='checked'</#if> style="vertical-align: text-top;">仅自己可见</a>
                <#if creative.cType==5>
	                <#if (session_user.reserve1??) && (session_user.reserve1=="M") && (event==1) >
	                	<a class='ishome' href="javascript:void(0);"><input type="checkbox" id="cr-cb-ishome" name="ishome" <#if creative.ishome!=0>checked='checked'</#if> value="1" style="vertical-align: text-top;">自主创建事件</a>
	                </#if>
                </#if>
            </div>
            
        </div>
    </div>
</div>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>