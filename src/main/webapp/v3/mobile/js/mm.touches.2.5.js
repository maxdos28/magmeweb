/*
 * author: xiwan (magme)
 * date: 2011/12/01
 */
(function(g,a,j){var d=d||{};var h=[];var b=0;var f=null;var e=null;var i=null;var c=[];var l=a.userAgent.toLowerCase();var k=g.screen;Function.prototype.on=function(m,n){if(m){this.prototype[m]=n}else{this.prototype=n()}return this};MMConfigs=d.Configs={version:l.match(/OS ([\d_]+)/i)[1].replace(/_/g,"."),agent:l.match(/safari/i),statsT:+(new Date()),gestures:{tap:0,swipe:0,pinch:0},resolution:k.width+"x"+k.height,headerH:$(".header").height(),appServerUrl:null,statsUrl:"http://stat.magme.com/magmeStat/cvStat",dft:"/768_",lrg:"/q",sml:"/"};MMCache={};MMInMem=d.InMem={len:function(q,p){var r,m=0;for(r in q){if(q.hasOwnProperty(r)){if(p){if(q[r]!=null){m++}}else{m++}}}return m},getCash:function(p,m){for(var q in p){if(p.hasOwnProperty(q)&&p[q]!=null&&q==m){return p[q]}}},delCash:function(p,m){for(var q in p){if(p.hasOwnProperty(q)&&p[q]!=null&&q==m){p[q]=null;return p}}},istCash:function(n,m){n[m.key]=m.value}};MMTools=d.Tools={$$:function(m){return document.getElementById(m)||null},testCookie:function(){var m=0,o="wb_check=kcehc_bw";document.cookie=o;if(document.cookie.indexOf(o)>-1){m=1;var n=new Date();n.setTime(n.getTime()-1000);document.cookie=o+"; expires="+n.toGMTString()}return m},setCookie:function(m,o){var n=30;var p=new Date();p.setTime(p.getTime()+n*24*60*60*1000);document.cookie=m+"="+escape(o)+";expires="+p.toGMTString()},getCookie:function(n){var m=document.cookie.match(new RegExp("(^| )"+n+"=([^;]*)(;|$)"));if(m!=null){return unescape(m[2])}return null},delCookie:function(m){var o=new Date();o.setTime(o.getTime()-1);var n=this.getCookie(m);if(n!=null){document.cookie=m+"="+n+";expires="+o.toGMTString()}},bind:function(o,n,m){if(o.addEventListener){o.addEventListener(n,m,false)}else{o.attachEvent(n,m)}},beacon:function(n){var m=["ipAddress="+(n.ipAddress||-1),"city="+(n.city||""),"province="+(n.province||""),"userId="+(n.userId||-1),"userType=-1","muid=-1","pubId="+(n.pubId||-1),"issueId="+(n.issueId||-1),"pageId="+(n.pageId||-1),"interval="+(n.interval||0),"sys="+(n.sys||""),"agent="+(MMConfigs.agent||""),"version="+(MMConfigs.version||""),"resolution="+(MMConfigs.resolution||""),"isPageAd=0","tap="+n.tap,"swipe="+n.swipe,"pinch="+n.pinch,];(new Image()).src=MMConfigs.statsUrl+"?"+m.join("&")},fixDefault:function(m){(m&&m.preventDefault)?m.preventDefault():m.returnValue=false;return m},distanceBetweenPoints:function(n,m){return Math.round(Math.sqrt(Math.pow(n,2)+Math.pow(m,2)))},centerPoints:function(n,m){return Math.round((n+m)/2)},calculateAngle:function(q){var p=q.startX-q.curX;var o=q.curY-q.startY;var m=Math.atan2(o,p);var n=Math.round(m*180/Math.PI);if(n<0){n=360-Math.abs(n)}return n},easeInOut:function(p,r,q,o,m){var s=r-p;var n=p+(Math.pow(((1/q)*o),m)*s);return Math.ceil(n)},fetchContent:function(o,m){if(typeof c[m-1]!="number"){return}e.reset();var n=MMInMem.getCash(MMCache,m);if(typeof n!=="undefined"){$("#content").html(n);return}$.ajax({url:o+"/mobile/mobile-read!getPageContentJson.action?eventId="+MMConfigs.eventId+"&issueId="+MMConfigs.issueId+"&pageNo="+c[m-1],type:"GET",dataType:"json",timeout:1000,error:function(){console.log("Error loading json document")},success:function(q){if(q.code==200&&q.data.textPage){var p=q.data.textPage.content;$("#content").html(p);MMInMem.istCash(MMCache,{key:m,value:p})}}})},fetchAdser:function(m,n){$.ajax({url:MMConfigs.appServerUrl+"/ad/advertise!queryAdsById.action?issueId="+MMConfigs.issueId+"&status=5",type:"GET",dataType:"json",timeout:1000,error:function(){m.ready();console.log("No ads found!")},success:function(r){if(r.code==200){i=r.data.insertAdList;var p=i.length;var o=-1;f.pages+=2*parseInt(p);for(var q=0;q<p;q++){c.splice(i[q].pageNo+q*2-1,0,i[q].userId+"/"+i[q].id+"_768_1",i[q].userId+"/"+i[q].id+"_768_2");if(n>0&&i[q].id==n){o=i[q].pageNo+q*2-1}}m.ready(o)}}})},changeImg:function(n,q,p,m){var o=o||new Image();if(typeof m=="number"){o.src=q+p+m+".jpg"}else{o.src=MMConfigs.adsUrl+"/video/"+m+".jpg"}if(o.complete){n.attr("src",o.src)}else{o.onload=function(){n.attr("src",o.src)}}},connectSrv:function(m){MMConfigs.sys="ipad.Protrait";if(MMConfigs.outW==520&&typeof m=="number"){this.fetchContent(MMConfigs.appServerUrl,m);MMConfigs.sys="ipad.Landscape"}this.beacon({pubId:MMConfigs.pubId,issueId:MMConfigs.issueId,pageId:m,sys:MMConfigs.sys,interval:-1*MMConfigs.statsT,tap:MMConfigs.gestures.tap,swipe:MMConfigs.gestures.swipe,pinch:MMConfigs.gestures.pinch});MMConfigs.statsT=+(new Date());MMConfigs.gestures={tap:0,swipe:0,pinch:0}},detectOrientation:function(m){if(typeof window.onorientationchange!="undefined"){$("#imgCnt").hide();$(window).scrollTop(0);MMConfigs.winH=$(window).height();if(orientation==0||orientation==180){$("#container").removeClass("readHorizontal");$("#imgCnt").removeClass("cntLandscape").addClass("cntProtrait");MMConfigs.outW=1000;MMConfigs.imgW=980;MMConfigs.imgH=1306}else{$("#container").addClass("readHorizontal");$("#imgCnt").removeClass("cntProtrait").addClass("cntLandscape");MMConfigs.outW=520;MMConfigs.imgW=500;MMConfigs.imgH=670;MMTools.fetchContent(MMConfigs.appServerUrl,(typeof m=="number")?m:(f)?f.imgIndex:1)}$("#pageNum").css("left",Math.round((MMConfigs.imgW-100)/2)+"px");if(f){f.reset()}$("#imgCnt").fadeIn(200)}},flipPage:function(o,q,n){var p=(n)?0.15:0;var m=(n)?q:0;o.css({"-webkit-transform":"matrix(1, 0, 0, 1, "+m+", 0)","-webkit-transition":"-webkit-transform "+p+"s ease-out"})[(q>0)?"prev":"next"]().css({"-webkit-transform":"matrix(1, 0, 0, 1, "+m+", 0)","-webkit-transition":"-webkit-transform "+p+"s ease-out"})},movePage:function(n,p,m,o){n.css({"-webkit-transform":"matrix(1, 0, 0, 1, "+p+", "+m+")","-webkit-transition":"-webkit-transform "+o+"s ease-out"})},scalePage:function(o,n,q,m,u,r,p){o.css({left:n+"px",top:q+"px",width:m+"px",height:u+"px","-webkit-transform":"scale("+r+")","-webkit-transition":"-webkit-transform "+p+"s ease-out"})},pinchPage:function(n,m,q,p,o){n.css({"-webkit-transform-origin":m+"px "+q+"px","-webkit-transform":"scale("+p+")","-webkit-transition":"-webkit-transform "+o+"s ease-out"})},foo:function(){}};MMTouches=d.Touches=function(){h.push(this);this.currDis=this.intDis=0;this.swipeLength=0;this.fingerCount=0;this.finger1={startX:0,startY:0,curX:0,curY:0};this.finger2={startX:0,startY:0,curX:0,curY:0}};d.Touches.on("init",function(o,m){var n=h[m];this.$$.guid=m;if(this.$$){n.touches=o;MMTools.bind(this.$$,"touchstart",this.touchStart);MMTools.bind(this.$$,"touchend",this.touchEnd);MMTools.bind(this.$$,"touchmove",this.touchMove);MMTools.bind(this.$$,"touchcancel",this.touchCancel)}else{console.log("touch object not exist!")}}).on("touchStart",function(n){$("#pageNum").fadeOut("fast");MMTools.fixDefault(n);var m=h[this.guid];m.fingerCount=n.touches.length;switch(m.fingerCount){case 1:m.finger1.startX=n.touches[0].pageX;m.finger1.startY=n.touches[0].pageY;break;case 2:m.finger1.startX=n.touches[0].pageX;m.finger1.startY=n.touches[0].pageY;m.finger2.startX=n.touches[1].pageX;m.finger2.startY=n.touches[1].pageY;m.initDis=MMTools.distanceBetweenPoints((m.finger2.startX-m.finger1.startX),(m.finger2.startY-m.finger1.startY));break;default:m.touchCancel(m,n);break}}).on("touchMove",function(s){MMTools.fixDefault(s);var o=h[this.guid];if(o.fingerCount!=s.touches.length){return}var r=+(new Date());switch(o.fingerCount){case 1:if(r&1){o.finger1.curX=s.touches[0].pageX;o.finger1.curY=s.touches[0].pageY;var n=o.finger1.curX-o.finger1.startX;var m=o.finger1.curY-o.finger1.startY;o.swipeLength=MMTools.distanceBetweenPoints(n,m);o.swipeAngle=MMTools.calculateAngle({startX:o.finger1.startX,startY:o.finger1.startY,curX:o.finger1.curX,curY:o.finger1.curY});if((o.swipeAngle<=45)&&(o.swipeAngle>=0)){o.swipeDirection="a"}else{if((o.swipeAngle<=360)&&(o.swipeAngle>=315)){o.swipeDirection="a"}else{if((o.swipeAngle>=135)&&(o.swipeAngle<=225)){o.swipeDirection="d"}else{if((o.swipeAngle>45)&&(o.swipeAngle<135)){o.swipeDirection="s"}else{o.swipeDirection="w"}}}}if(o.touches.swipe){o.touches.swipe.apply(o.touches.target,[n,m,o.swipeDirection,1])}}break;case 2:if(r&1){o.finger1.currX=s.touches[0].pageX;o.finger1.currY=s.touches[0].pageY;o.finger2.currX=s.touches[1].pageX;o.finger2.currY=s.touches[1].pageY;if(o.currDis){o.initDis=o.currDis}o.currDis=MMTools.distanceBetweenPoints((o.finger2.currX-o.finger1.currX),(o.finger2.currY-o.finger1.currY));var q=MMTools.centerPoints(o.finger2.currX,o.finger1.currX);var p=MMTools.centerPoints(o.finger2.currY,o.finger1.currY);if(o.initDis==0){o.initDis=o.currDis}else{if(o.currDis/o.initDis!=1){o.pinchDirection=(o.currDis/o.initDis>1)?1:0;if(o.touches.pinch){o.touches.pinch.apply(o.touches.target,[o.pinchDirection,q,p])}}}}break;default:o.touchCancel(o,s);break}}).on("touchEnd",function(n){MMTools.fixDefault(n);var m=h[this.guid];switch(m.fingerCount){case 1:if(!m.swipeLength){MMConfigs.gestures.tap++;if(m.touches.tapCallback){m.touches.tapCallback.apply(m.touches.target,[m.finger1.startX,m.finger1.startY])}}else{MMConfigs.gestures.swipe++;if(m.touches.swipeCallback){m.touches.swipeCallback.apply(m.touches.target,[m.swipeDirection])}}break;case 2:MMConfigs.gestures.pinch++;if(m.touches.pinchCallback){m.touches.pinchCallback.call(m.touches.target)}break;default:break}m.touchCancel(m,n)}).on("touchCancel",function(n,m){n.fingerCount=0;n.swipeLength=0;n.currDis=n.intDis=0;n.finger1={startX:0,startY:0,curX:0,curY:0};n.finger2={startX:0,startY:0,curX:0,curY:0}});MMTexter=d.Texter=function(m){e=this;this.$$=MMTools.$$(m);this.jQ=$(this.$$)};d.Texter.on("",function(){return new MMTouches()}).on("ready",function(){this.init({target:this,swipe:this.fingerScroll,swipeCallback:this.fingerScrollCallback},b++);return this}).on("reset",function(){this.width=this.jQ.width();this.height=this.jQ.height();MMTools.scalePage(this.jQ,0,0,this.width,this.height,1,0.15)}).on("fingerScroll",function(o,n,m){if(m=="w"||m=="s"){MMTools.movePage(this.jQ,0,n,0.2)}}).on("fingerScrollCallback",function(m){var n=this;setTimeout(function(){var o=n.jQ.position().top;if(o>0){n.reset()}else{MMTools.scalePage(n.jQ,0,o,n.width,n.height,1,0)}},300)});MMReader=d.Reader=function(p,u,s,n,r,t,q,o){MMTools.detectOrientation(o);MMConfigs.appServerUrl=u;MMConfigs.pubId=r;MMConfigs.issueId=t;MMConfigs.eventId=n;f=this;this.$$=MMTools.$$(p);this.jQ=$(this.$$);this.imgPrefix=s+"/"+r+"/"+t;this.pages=Number(q);this.imgIndex=Number(o);this.bound={left:0,top:0,width:MMConfigs.imgW,height:MMConfigs.imgH};this.tmpBound={left:0,top:0,width:MMConfigs.imgW,height:MMConfigs.imgH};this.scale={max:2.5,min:0.5,prv:1,cur:1,tlt:1,flag:0};this.drag={length:20,left:0,flag:0};var m=0;while(m<this.pages){c.push(++m)}if(o>2||o<q-2){(new Image()).src=this.imgPrefix+MMConfigs.dft+(o-2)+".jpg";(new Image()).src=this.imgPrefix+MMConfigs.dft+(o+2)+".jpg"}else{if(o<=2){(new Image()).src=this.imgPrefix+MMConfigs.dft+(o+2)+".jpg"}else{if(o>=q-2){(new Image()).src=this.imgPrefix+MMConfigs.dft+(o-2)+".jpg"}}}$("#turnLeft").click(function(){if(f.imgIndex-->1){MMTools.connectSrv(f.imgIndex);f.loadPages(MMConfigs.dft,"d");f.reset()}});$("#turnRight").click(function(){if(f.imgIndex++<q){MMTools.connectSrv(f.imgIndex);f.loadPages(MMConfigs.dft,"a");f.reset()}})};d.Reader.on("",function(){return new MMTouches()}).on("ready",function(m){if(m&&m>0){this.imgIndex=m+1}MMTools.changeImg(this.jQ.prev(),this.imgPrefix,MMConfigs.dft,c[this.imgIndex-2]);MMTools.changeImg(this.jQ.next(),this.imgPrefix,MMConfigs.dft,c[this.imgIndex]);this.reset();this.init({target:this,tap:this.doubleTapFunc,tapCallback:this.doubleTapCallback,swipe:this.dragFunc,swipeCallback:this.dragCallback,pinch:this.scaleFunc,pinchCallback:this.scaleCallback},b++);return this}).on("reset",function(){if(this._Timer){clearInterval(this._Timer)}MMTools.changeImg(this.jQ,this.imgPrefix,MMConfigs.dft,c[this.imgIndex-1]);MMTools.scalePage(this.jQ,0,0,MMConfigs.imgW,MMConfigs.imgH,1,0.15);MMTools.scalePage(this.jQ.next(),MMConfigs.outW,0,MMConfigs.imgW,MMConfigs.imgH,1,0.15);MMTools.scalePage(this.jQ.prev(),-1*MMConfigs.outW,0,MMConfigs.imgW,MMConfigs.imgH,1,0.15);this.bound={left:0,top:0,width:MMConfigs.imgW,height:MMConfigs.imgH};this.tmpBound={left:0,top:0,width:MMConfigs.imgW,height:MMConfigs.imgH};this.scale={max:2.5,min:0.5,prv:1,cur:1,tlt:1,flag:0};this.drag={length:20,left:0,flag:0}}).on("loadPages",function(n,q){var p=1;MMTools.changeImg(this.jQ,this.imgPrefix,n,c[this.imgIndex-1]);this.imgIndex=(this.imgIndex<1)?1:(this.imgIndex>this.pages)?this.pages:this.imgIndex;var m=(this.imgIndex>1)?this.imgIndex-1:1;var o=(this.imgIndex<this.pages)?this.imgIndex+1:this.pages;if(q=="d"){MMTools.changeImg(this.jQ.prev(),this.imgPrefix,n,c[m-1]);MMTools.changeImg(this.jQ.next(),this.imgPrefix,n,c[o-1]);p=(this.imgIndex>=2)?(this.imgIndex-2):1}else{MMTools.changeImg(this.jQ.next(),this.imgPrefix,n,c[o-1]);MMTools.changeImg(this.jQ.prev(),this.imgPrefix,n,c[m-1]);p=(this.imgIndex<this.pages-1)?(this.imgIndex+2):this.pages}$("#pageNum").text(this.imgIndex).fadeIn("fast");(new Image()).src=this.imgPrefix+n+p+".jpg"}).on("dragFunc",function(s,r,n,o){var p=MMConfigs.imgW;var t=MMConfigs.imgH;var m=MMConfigs.outW;if(this.scale.flag==0){if(n=="a"||n=="d"){this.drag.flag=1;if(Math.abs(this.jQ.position().left)>0.5*p){if(o){if(this._Timer){clearInterval(this._Timer)}var q=this;this._Timer=setInterval(function(){(s>0)?(--q.imgIndex):(++q.imgIndex);q.loadPages(MMConfigs.sml,n)},350)}else{MMTools.flipPage(this.jQ,s,1)}}else{MMTools.flipPage(this.jQ,s,1)}}else{}}else{this.tmpBound.left=s;this.tmpBound.top=r;MMTools.movePage(this.jQ,s,r,0.1)}}).on("dragCallback",function(n){var o=MMConfigs.imgW;var q=MMConfigs.imgH;var p=this;if(p.scale.flag==0){setTimeout(function(){var s=p.jQ.position().left;var r=(s>0)?1:0;switch(s){case 0:if(n=="w"){if($("#imgCnt").css("-webkit-transform").indexOf(MMConfigs.headerH)!=-1){MMTools.movePage($("#imgCnt"),0,(MMConfigs.winH-MMConfigs.headerH-MMConfigs.imgH),0.25)}else{MMTools.movePage($(".header, #imgCnt, #txtCnt"),0,-1*MMConfigs.headerH,0.25)}}else{MMTools.movePage($(".header, #imgCnt, #txtCnt"),0,0,0.25)}break;default:if(p._Timer){clearInterval(p._Timer)}var t=(Math.abs(s)>p.drag.length)?(r)?1:-1:0;p.dragFunc(t*MMConfigs.outW,0,n,0);if(t){(r)?(--p.imgIndex):(++p.imgIndex);setTimeout(function(){MMTools.flipPage(p.jQ,t*MMConfigs.outW,0);MMTools.connectSrv(p.imgIndex);p.loadPages(MMConfigs.dft,n);p.drag.flag=0},300)}var u=+(new Date());MMConfigs.statsT-=u;break}},200)}else{function m(){p.bound.left=Math.round(p.jQ.position().left);p.bound.top=Math.round(p.jQ.position().top);MMTools.scalePage(p.jQ,p.bound.left,p.bound.top,p.bound.width,p.bound.height,1,0)}setTimeout(function(){var v={X:-1,Y:-1};var z=-1*p.bound.left;var D=-1*p.bound.top;var x=z+o-p.bound.width;var C=D+q-p.bound.height;var E=p.jQ.position().left;var t=p.jQ.position().top;var A=o-p.bound.width;var r=q-p.bound.height;var y=(E>0);var w=(t>0);var u=(E<A);var s=(t<r);var F=[(y&&w),(y&&!w&&!s),(y&&s),(s&&!y&&!u),(u&&s),(u&&!w&&!s),(w&&u),(w&&!y&&!u)];var B=F.length;while(B--){if(F[B]){if(y&&(B==0||B==1||B==2)){v.X=z}else{if(!y&&(B==3||B==7)){v.X=p.tmpBound.left}}if(w&&(B==0||B==6||B==7)){v.Y=D}else{if(!w&&(B==1||B==5)){v.Y=p.tmpBound.top}}if(u&&(B==4||B==5||B==6)){v.X=x}else{if(!u&&(B==3||B==7)){v.X=p.tmpBound.left}}if(s&&(B==2||B==3||B==4)){v.Y=C}else{if(!s&&(B==1||B==5)){v.Y=p.tmpBound.top}}break}}v.X=v.X.toFixed(2);v.Y=v.Y.toFixed(2);if(v.X!=-1||v.Y!=-1){MMTools.movePage(p.jQ,v.X,v.Y,0);setTimeout(function(){m()},200)}else{m()}},300)}}).on("doubleTapFunc",function(){}).on("doubleTapCallback",function(o,m){if(!this.clock){this.clock=+(new Date());var p=this;setTimeout(function(){p.clock=null},330)}else{var q=+(new Date());if((this.scale.flag==0||this.scale.flag==-1)&&q-this.clock<300){var n=1.75;if(this.bound.width>=n*MMConfigs.imgW){n=1;this.scale.flag=0}else{this.scale.flag=-1}MMTools.pinchPage(this.jQ,o,m,n,0.2);var p=this;setTimeout(function(){p.bound.width=n*MMConfigs.imgW;p.bound.height=n*MMConfigs.imgH;p.bound.left=Math.round(o*(1-n));p.bound.top=Math.round(m*(1-n));MMTools.scalePage(p.jQ,p.bound.left,p.bound.top,p.bound.width,p.bound.height,1,0)},300)}this.clock=null}}).on("scaleFunc",function(o,s,r){if(!this.drag.flag){var p=this.tmpBound.width;var n=this.tmpBound.height;var q=1;if(o==1){if(p<this.scale.max*MMConfigs.imgW){this.scale.flag=1}else{this.scale.flag=-1}}else{if(p>this.scale.min*MMConfigs.imgW){this.scale.flag=2;q=-1}else{this.scale.flag=-2}}if(this.scale.flag>0){this.scale.cur+=0.05*q;this.tmpBound.width=this.scale.cur*this.bound.width;this.tmpBound.height=this.scale.cur*this.bound.height;var m=Math.abs(this.bound.left)+s;var t=Math.abs(this.bound.top)+r;MMTools.pinchPage(this.jQ,m,t,this.scale.cur,0.3)}}else{this.scale.flag=0}}).on("scaleCallback",function(){if((this.jQ.prev().position().left+MMConfigs.outW)||(this.jQ.next().position().left-MMConfigs.outW)){this.reset();return}var m=this;setTimeout(function(){m.bound.width=m.tmpBound.width;m.bound.height=m.tmpBound.height;m.bound.left=Math.max(MMConfigs.outW-m.bound.width,Math.min(0,m.jQ.position().left));m.bound.top=Math.max(MMConfigs.imgH-m.bound.height,Math.min(0,m.jQ.position().top));m.scale.cur=1;if(m.bound.width<=MMConfigs.imgW){m.reset()}else{MMTools.scalePage(m.jQ,m.bound.left,m.bound.top,m.bound.width,m.bound.height,1,0);if((m.jQ.width()>2*MMConfigs.imgW)){MMTools.changeImg(m.jQ,m.imgPrefix,MMConfigs.lrg,c[m.imgIndex-1])}else{MMTools.changeImg(m.jQ,m.imgPrefix,MMConfigs.dft,c[m.imgIndex-1])}}},250)});if(MMTools.testCookie()&&!MMTools.getCookie("probe")){MMTools.setCookie("probe",1);if(MMConfigs.version[0]<5){alert("For better experience, please update your iso to 5.0!!!")}}MMSetup=function(p,n,m,o){p.ready();if(m){MMConfigs.adsUrl=m;MMTools.fetchAdser(n,o)}else{n.ready();console.log("Ads is closed!!!")}};g.onorientationchange=MMTools.detectOrientation})(window,navigator,document);