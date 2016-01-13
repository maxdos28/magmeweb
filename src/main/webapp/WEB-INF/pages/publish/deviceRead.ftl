<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/style/royalslider.css"/>
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/style/rs-default.css"/>
<title>${webTitle!''}</title>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<style>
h1{ font-size:16px; text-align:center; line-height:1.6em; padding:10px; font-weight:bold;}
.hide{ display:none;}
.body{ padding:0 10px;}
.body a img{ width:100%; display:block;}
.downloadApp img{ width:100%; display:block; max-width:400px; margin:0 auto; background:#ccc;}
.downloadApp{ padding:10px;}



</style>

  </head>
  <body>
  <#if deviceType??&&deviceType=="pc">
  <script>
  	window.location.href="http://www.magme.com/publish/mag-read.action?pageId=${pno!'0'}&Id=${eid!'0'}";
  	</script>
  <#else>
  <div class="main">
    	<div class="downloadApp">
    	 <#if deviceType??&&deviceType=="iPad">
    	 	<a href="javascript:void(0);">
        	<img src="${systemProp.staticServerUrl}/v3/share/ui/ipad.jpg" />
        	</a>
    	 <#elseif deviceType??&&deviceType=="iPhone">
    	 	<a href="javascript:void(0);">
        	<img src="${systemProp.staticServerUrl}/v3/share/ui/iphone.jpg" />
        	</a>
    	 <#else>
    	 	<a href="http://static.magme.com/widget/MagmeEnjoy.apk" target="_blank" >
        	<img src="${systemProp.staticServerUrl}/v3/share/ui/android-pad-h.jpg" />
        	</a>
    	 </#if>
        </div>
    	<h1>${webTitle!''}</h1>
    	<div class="body">
            <a href="javascript:void(0);">
            <#if deviceType??&&deviceType=='iPhone'>
    	 	<img src="http://static.magme.com/pdfprofile/${pid!'0'}/${eid!'0'}/320_${pno!'0'}.jpg" />
    	 <#else>
    	 	<img src="http://static.magme.com/pdfprofile/${pid!'0'}/${eid!'0'}/${size}_${pno!'0'}.jpg" />
    	 </#if>
            </a>
        </div>
        
    </div>
  </#if>
  </body>
</html>