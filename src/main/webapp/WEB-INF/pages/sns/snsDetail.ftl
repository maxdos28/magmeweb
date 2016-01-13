<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>麦米购–${(looArticle.title)!''}</title>
<meta name="Keywords" content="麦米购，轻松全球购，${(looArticle.title)!''}">
<meta name="Description" content="${(looArticle.memo)!''}">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelDetailv7.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.qrcode-0.6.0.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/outSideControl.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/header.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/snsDetail.js"></script>


<script>
var browser={
    versions:function(){ 
       var u = navigator.userAgent, app = navigator.appVersion; 
       return {//移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
};
$(function(){
    //console.log(browser.versions.mobile && (browser.versions.android || browser.versions.ios));
	if(browser.versions.mobile && $(window).width()<500){
        return false;
    }else{
      //  return false;
            //调试用代码
        // var iNum = Math.floor(Math.random()*16);
        // var tNum;
        // var tNumLetter = Math.floor(Math.random()*3+1);
        // switch (tNumLetter){
        //     case 1 : tNum = "a"; $("#mainText .text").html( $("#mainText .text").html().slice(0,50)); break;
        //     case 2 : tNum = "b"; $("#mainText .text").html( $("#mainText .text").html().slice(0,200)); break;
        //     case 3 : tNum = "c"; break;
        // }
        
        // fnGenerate(iNum,tNum,null,1);
        // //fnGenerate("1","c","11","4"); undefined
        // $.tplImgView("<img src='template/temp/01.jpg' /><img src='template/temp/02.jpg' /><img src='template/temp/03.jpg' /><img src='template/temp/06.jpg' /><img src='template/temp/07.jpg' /><embed src='http://player.youku.com/player.php/sid/XNDgyNjE0NjQ4/v.swf' allowFullScreen='true' quality='high' width='800' height='500' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash'></embed><img src='template/temp/08.jpg' /><img src='template/temp/09.jpg' /><img src='template/temp/10.jpg' /><img src='template/temp/11.jpg' /><img src='template/temp/12.jpg' />");
        
        
        
        $(".topReader20121108 .tools a.comment").click(function(){
            $(window).scrollTo("#conOtherRead",500)
        });
    }
});
</script>

<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<div id="body">
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>

<input id="creativeId" type="hidden" value="${creativeId!'0'}">
<!--body-->
<div class="topReaderNull" style="display:none;">
    这么多文章，居然都被你看完了，换个分类看看吧~！
</div>
<div class="topReader20150126">
	<h1>${(looArticle.title)!''}</h1>
    <h6>${(looArticle.memo)!''}</h6>
    <div class="contentInner">
    	${(looArticle.title2)!''}
    </div>
    <div id="magezineBox" class="magezineBox">
    </div>
    <div class="body clearFix sharebox">
	    <div class="tools">
	    	<#if nextArticleId?? && (nextArticleId>0) >
	        <a class="turnRight png" href="${systemProp.appServerUrl}/sns/c${(nextArticleId)!'0'}" title="下一篇文章">right</a>
	        </#if>
	    	<#if prevArticleId?? && (prevArticleId>0) >
	        <a class="turnLeft png" href="${systemProp.appServerUrl}/sns/c${(prevArticleId)!'0'}" title="上一篇文章">left</a>
	        </#if>
	    </div>
	    <div class="share">
            <strong>分享到</strong> <a class="shareicon shareiconwenxin" href="javascript:;"></a><a class="shareicon shareiconsina" shareTypeId="eve_${creativeId}_creative" smallPic="${systemProp.staticServerUrl}${looArticle.smallPic}"></a>
            <div class="sharePop">
                <a class="closesharePop" href="javascript:;"></a>
                <p>使用微信扫一扫，即可分享到朋友圈</p>
                <div id="sharePic"></div>
                <em>◆<b>◆</b></em>
            </div>
        </div> 
        <script type="text/javascript">
		$(function(){
    var options =  {
        render    :  "canvas",
        ecLevel   :  "H",
        minVersion:  6,
        color     :  "#333333",
        bgColor   :  "#ffffff",
        text      :  document.location.href,
        size      :  200,
        radius    :  0.5,
        quiet     :  0,
        mode      :  2,
        label     :  "magme.com",
        labelsize :  0.09,
        fontname  :  "Arial",
        fontcolor :  "#25b4fa",
        image     :  {},
        imagesize :  0.13
    }
    $("#sharePic").qrcode(options);

    $('.shareiconwenxin').click(function(event) {
        $('.sharePop').show();
    });
    $('.closesharePop').click(function(event) {
        $('.sharePop').hide();
    });
})
		</script>   
	</div>  
</div>

<div class="body clearFix">
   <#if sameLooArticleList?? && (sameLooArticleList.size()>0) >
     <div class="conOtherRead" id="conOtherRead">
    	<h2>推荐内容</h2>
        <div class="inner clearFix">
           <#list sameLooArticleList as cc>
              <#if cc_index<7>
	            <div class="item">
	                <a href="${systemProp.appServerUrl}/sns/c${(cc.articleId)!'0'}">
	                  <div><#if (cc.smallPic)??><img src="${systemProp.staticServerUrl}${cc.smallPic}" /></#if>
	                  </div>
	                  <p>${(cc.title)!''}</p>
	                </a>
	            </div>
	           </#if>
           </#list>
        </div>
	</div>
   </#if>	
</div>
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</div>

<script type="text/javascript">
if(browser.versions.iPhone || browser.versions.android){
   
    $('head').prepend('<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" id="viewport" />');
    
	if($(window).width()<500){
        $('body').addClass('phoneStyle');
        $('div.mainContent').removeAttr('id');
        var ads = document.createElement('div');
        ads.setAttribute('id','onePic');
        $('#mainText')[0].insertBefore(ads,$('#mainText>h1')[0]);
        $('div.mainContent').find('a.pic:first').prependTo($('#onePic'));
    }else{
        $('#viewport').remove();
        $('body').addClass('padStyle');
    }
}else if(browser.versions.iPad){
    $('body').addClass('padStyle');
}

</script>
<script type="text/javascript">
Date.prototype.toHyphenDateString = function() { 
    var year = this.getFullYear(); 
    var month = this.getMonth() + 1; 
    var date = this.getDate(); 
    if (month < 10) { month = "0" + month; } 
    if (date < 10) { date = "0" + date; } 
    var hours = this.getHours();
    var mins = this.getMin
    var mins = this.getMinutes();
    var second = this.getSeconds();
    return year + month + date  + hours  + mins;
};
var magmereaderScriptLoad =  '${systemProp.staticServerUrl}/appprofile/read/js/magme.reader.js?version='+new Date().toHyphenDateString()
document.write("<script src='" + magmereaderScriptLoad + "'><\/script>");
</script>
</body>
</html>