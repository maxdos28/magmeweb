//本地document.domain不需要。方便本地测试
if(/magme\.cn/.test(document.location.href))  document.domain = 'magme.com';
//加载JS以及缓存
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
    return year + month + date  + hours  + mins + second;
};
var magmeScriptLoad = {
    parm: [
    'http://static.magme.com/appprofile/read/js/jquery.mousewheel.min.js', //
    'http://static.magme.com/appprofile/read/js/jquery.mobile.custom.min.js', //
    'http://static.magme.com/appprofile/read/js/jquery.mCustomScrollbar.concat.min.js', //桌面端滚动条功能
    'http://static.magme.com/appprofile/read/js/idangerous.swiper.js', //滑动
    // 'http://static.magme.com/appprofile/read/css/reset.css', //
    'http://static.magme.com/appprofile/read/css/jquery.mCustomScrollbar.min.css', //桌面端滚动条样式
    'http://static.magme.com/appprofile/read/css/idangerous.swiper.css', //滑动
    'http://static.magme.com/appprofile/read/css/magme.reader.css', //
    ], 
    _loadjs: function(src) {
        document.write("<script src='" + src + "'><\/script>");
    },
    _loadcss: function(src) {
        document.write('<link rel="stylesheet" href="'+src+'" />');
    }   
}
// var version = new Date().toHyphenDateString();
var version = 201409031431;
for (var i in magmeScriptLoad.parm) {
    if(magmeScriptLoad.parm[i].match(/\.js$/)){
         magmeScriptLoad._loadjs(magmeScriptLoad.parm[i]+'?version='+version);
    }else{
         magmeScriptLoad._loadcss(magmeScriptLoad.parm[i]+'?version='+version);
    }
}
//是否是微信
function is_weixn(){  
    var ua = navigator.userAgent.toLowerCase();  
    if(ua.match(/MicroMessenger/i)=="micromessenger") {  
        return true;  
    } else {  
        return false;  
    }  
}  
//获取指定名称的cookie的值
function getCookie(objName){
    var arrStr = document.cookie.split("; ");
    for(var i = 0;i < arrStr.length;i ++){
        var temp = arrStr[i].split("=");
        if(temp[0] == objName) return unescape(temp[1]);
   }
}
//添加cookie
function addCookie(objName,objValue,objHours){
    var str = objName + "=" + escape(objValue);
    if(objHours > 0){
        var date = new Date();
        var ms = objHours*3600*1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toGMTString();
   }
   document.cookie = str;
}
//设备判断
var browser = {
    versions: function() {
        var u = navigator.userAgent,
        app = navigator.appVersion;
        return { //移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1,
            //IE内核
            presto: u.indexOf('Presto') > -1,
            //opera内核
            webKit: u.indexOf('AppleWebKit') > -1,
            //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
            //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/),
            //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
            //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
            //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1,
            //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1,
            //是否iPad
            ws :  /windows|win32/i.test(u),
            //是否windows
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    } (),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
};
var me = me || {}; 
me.reader = function(eleContainer){ 
    var ele  = eleContainer || $('body');
    var Container = '<div class="readermain-box"><div class="readermain"><a class="fullscreen" href="javascript:;"></a><div class="reader"><a class="reader-prve" href="javascript:;"></a><span class="left-page"></span><div class="reader-concent"><div class="reader-concent-inner"><ul class="clearfix"></ul></div></div><span class="right-page"><em class="page"></em><em class="pageCount">/<strong></strong></em></span><a class="reader-next"href="javascript:;"></a><div class="progressbar"><div class="passbar"></div></div></div><div class="view-pic"><div class="grid-pic-box"style="display:none;"><div class="grid-pic"><ul class="clearfix"></ul></div></div><div class="catalog-pic-box"style="display:none;"><div class="catalog-pic"><ul class="clearfix"></ul></div></div></div><div class="button-icon"><span class="reader-icon cur"></span><span class="grid-icon"></span><span class="catalog-icon"></span></div></div></div>';
        ele.append(Container);
    var appid,                                      // ID
        topicid,                                    // topicId 
        ismz,                                       // 是否杂志
        hashId,                                     // 当前页码
        max,                                        // 最大页码
        page                                        // 页码
        min = 1,                                    // 最小页码
        arrpage = [],                               // 缓存页码大小
        flagnext = true,                            // 点击下一页  是否为页码增大   true 为增加   false 为不增加 
        flagprve = true,                            // 点击上一页  是否为页码增大   true 为增加   false 为不增加 
        loadflag = true,                            // 是否还在loading  首次进入loading
        gridflag = true,                            // 第一次触发缩略图滚动条插件
        catalogflag = true,                         // 第一次触发目录滚动条插件
        gridflagload = false,                       // 小于4页第一次加载        
        isdouble = false,                           // 缩略图是否为单双页
        iframeloadnum = 0,                          // 加载iframe个数。是否加载完毕
        disabledcls = 'disabled',                   // 左右按钮禁用样式
        gripclass = 'gripclass',                    // 当前页码缩略图增加样式
        Antime = 500,                               // 动画执行的时间
        backurl = "http://www.magme.com/",           // 参数错误链接跳出 
        OInfo = [],                                 // 缓存目录数组
        firstload = true,                           // 第一次加载完后显示按钮
        ishascatalog = false,                       // 是否显示目录   ismz = 0 不显示   ismz = 1 XML找不到字段不显示  
        random  =  Math.round(Math.random()*100000),// 随机数
        eachwidth = 768,                            // 默认长度
        backid = 1                                  // phone页面 目录返回
        isweb = false,                              // 是否为网站内容                  
        isphone = 0,                                // 是否手机页面 0为iPad页面   1为phone页面
        isipad = navigator.userAgent.toLowerCase().match(/ipad/i) =='ipad'||false,      // 模拟iapd
        ismobile = ((browser.versions.mobile && (browser.versions.android || browser.versions.ios))&&!isipad)||false,                            // 模拟手机
        ispc =  !ismobile&&!isipad,                 // 模拟PC  ipad横屏判断为PC端处理
        issingle = false,                           // 是否单双  true为单个   
        leftnum = 0,                                // 偏移的值
        maxloadnum = 0,                             // 需要load个数
        _setTime = null,                            // 快速滑动
        mouse = true,
        mouseflag = true,                           //是否快速 
        areL = 1;
        areR = 2;
        areA = 4;        
        concent = $('.reader-concent-inner'),
        $readermain= $('.readermain-box'),
        $reader = $('.reader'),
        $gridpic = $('.grid-pic'),
        $catalogpic = $('.catalog-pic'),
        $leftpage = $('span.left-page'),
        $rightpage = $('span.right-page'),
        $Prev = $('a.reader-prve'),
        $Next = $('a.reader-next'),
        $progressbar = $('.progressbar'),
        $buttonicon = $('div.button-icon'),                         
        $gridicon = $('span.grid-icon'),                            
        $catalogicon = $('span.catalog-icon'),                          
        $readericon = $('span.reader-icon'),   
        $fullscreen = $('.fullscreen'),                                     
        This = this;                  
    this.readerInit = function(){
        var WindowUrl = document.location.href;
        var WindowUrlS = document.location.search;
        var WindowUrlH = document.location.hash;
        if(/tid=/i.test(WindowUrlS)||/\/c\d+/i.test(WindowUrl)){
            if(/\/c\d+/i.test(WindowUrl)){
                isweb = true;   
            }
            if(isweb){
                //网站模块
                ismz = 0;
                isphone = 0;
                appId = 903;                    
                topicId = Number(WindowUrl.match(/\/c\d+/i).join('').replace(/\/c/i,''));
                if(!ispc) window.location.href = 'http://static.magme.com/appprofile/read/html5.html?tid='+topicId;
            }else{
                //阅读器模块
                topicId = Number(WindowUrlS.match(/[\?|\&]tid=\d+/ig).join('').replace(/[\?|\&]tid=/i,''));
                appId = WindowUrlS.match(/[\?|\&]aid=\d+/ig)?Number(WindowUrlS.match(/[\?|\&]aid=\d+/ig).join('').replace(/[\?|\&]aid=/i,'')):0;
                ismz = WindowUrlS.match(/[\?|\&]mz=\d+/ig)?Number(WindowUrlS.match(/[\?|\&]mz=\d+/ig).join('').replace(/[\?|\&]mz=/i,'')):0;
                //没有参数默认参数
                if(!WindowUrlS.match(/[\?|\&]d=\d+/ig)){
                    if(ispc||isipad){
                        isphone = 0;
                    }else{
                        isphone = 1;
                    }
                }else{
                    isphone = WindowUrlS.match(/[\?|\&]d=\d+/ig)?Number(WindowUrlS.match(/[\?|\&]d=\d+/ig).join('').replace(/[\?|\&]d=/i,'')):0;
                };                               
            }
            //网站和阅读器区别
            if(isweb){
                 $readermain.addClass('isweb');
            }else{
                $('body').css({'background' : 'url(http://static.magme.com/appprofile/read/img/bg.png) repeat'});  
            }
            //手机看ipad页面
            if(!isphone&&ismobile){
                $readermain.hide();
                $('body').append('<table id="readertip" style="width:100%;height:100%;background:#000"><tr><td style="vertical-align:middle;"><div style="color:#fff;text-align:center;display:block" class="tips"><p style="text-align:center">此内容不支持手机</p><p style="text-align:center">请用平板或者电脑查看</p></div></td></tr></table>');
                return false;
            }            
            var _get = This.readerGetinfo('xml');
            $.ajax({
                type: "get",
                url: _get,
                async: false, 
                success: function (r) {
                if(isweb) r = r.data.xml;         
                max =  Number($(r).find('pageCount').text()); 
                if(isweb){
                	$rightpage.find('em.pageCount strong').text(max);   
                	$rightpage.find('em.pageCount').show();
                    $fullscreen.show();
                }else{
                    document.title = $(r).find('page').eq(0).find('title').text();
                }                               
                // max = 16;
                //页码处理
                hashId = Number(WindowUrlH.replace(/#/,''));                 
                //缓存每个页面大小
                for (var i = 0; i <= max; i++) {
                    arrpage.push(0);    
                };
                ishascatalog = !!$(r).find('page').eq(0).find('belongToCatalog').length&&!isweb;
                if(ishascatalog){
                    var objinfo = {}; 
                    var i = 1;
                    $(r).find('page').each(function(){
                        var $this = $(this);
                        var isCatalog = $this.find('belongToCatalog').text();
                        //第一个一定为1
                        if($(this).index()==0){
                            isCatalog = 1;
                        }                       
                        if(isCatalog==1){
                            var title = $this.find('title').text();
                            objinfo = {
                                'Otitle' : title,
                                'Oindex' : [i]
                            }
                            OInfo.push(objinfo);    
                        }else{
                            var Oi = OInfo.length-1;
                            OInfo[Oi]['Oindex'].push(i);
                        }
                        i++;
                    });
                    if(ispc){
                        // This.readerCatelog();   
                    }else if(isphone){
                        //PHONE页面返回目录和首页用到OInfo
                    }else{
                        $catalogicon.hide();
                    }                   
                }else{
                    $catalogicon.hide();    
                }                           
                This.readerdevice(This.readerJump);  
               }                                                            
            });
        }else{
            // document.location.href = backurl;
        }
        
    };
    //页面跳转
    this.readerJump = function(targetid,type){
        if(targetid==page&&targetid>4) return;
        var pageid = Number(targetid);           
        //缩略图处理
        if(type&&!issingle){
            isdouble = targetid%2?false:true;
            pageid = isdouble? targetid-1: pageid;  
            if(pageid==page&&targetid>4&&flagnext) return; 
        }  
        page = pageid;
        //小于4页处理
        if(gridflagload){
            This.readersmallAm(pageid);
        }else{
            mouse = false;          
            var html = ''; 
            if(max>4){
                if(page==max&&!issingle) page = max-1;                
                var loadindexIframe = This.readerIframeindex(page);
                for (var i = 0; i < areA; i++) {
                     var loadindexpage = loadindexIframe.loadindex[i];
                    if(page == loadindexIframe.loadindex[i]){
                        html += This.readerloadiframe(loadindexpage,1,pageid);
                    }else{
                        html += '<li load="false" index="'+loadindexpage+'"><div class="loading-parent"><div class="loading"></div></div></li>';
                    }                   
                };
                leftnum = loadindexIframe.leftwidth;
                This.readernewResizeleft(leftnum);  
            }else{
                //小于4页处理
                gridflagload =true;
                var pageid = 1;
                if(max==1) concent.css('left','384px');
                for (var i = 0; i < max; i++) {
                    var urlpageshow = parseInt(pageid+i);
                    html += This.readerloadiframe(urlpageshow,1,pageid);
                };        
            }
            concent.find('ul').html(html);
        }
    };
    //小于四页不重复加载IFRAME
    this.readersmallAm = function(targetid){
        if(targetid==max&&arrpage[max]==1) targetid--;
        var i = 1;
        while (i<targetid){
           leftnum =  leftnum+arrpage[i];
           i++;
        }
        var pagewidth = arrpage[targetid];          
        if(pagewidth==2){
            if(isdouble) {
                leftnum++;
                flagnext = true;
                flagprve = false;                               
            }else{
                flagnext = false;
                flagprve = true;
            }
        }else{
            flagnext = true;
            flagprve = true;
        };
        This.readernewResizeleft(leftnum,Antime);
        page =  targetid;   
        setTimeout(function(){
            isdouble?'':This.readerAnimateon(page);
            This.readerPageshow();      
        },Antime);          
    }
    //上一页 处理 视频、音频、动画、显示切换
    this.readerPrev = function(){
        if($Prev.hasClass(disabledcls)){
            //上一篇杂志
            if(isweb){
                if($('.turnLeft').length>0){
                    window.location.href = $('.turnLeft').attr('href');
                }             
            }
            return false;
        };    
        //处理BUG
        if(page==min&&flagprve) return;    
        loadflag = true;            
        flagnext = true;
        _setTime = null;
        setTimeout(function(){        
        if(!flagprve){
            flagprve = true;
            flagnext = false;           
            leftnum--;
            This.readernewResizeleft(leftnum,Antime);
            setTimeout(function(){
                //音频视频重新load 
                if(!issingle){
                    var tid = page+1;
                    var twidth = concent.find('li[index="'+tid+'"]').attr('px');    
                    if(twidth) This.readerVideostop(twidth);         
                }
                This.readerAnimateon(page);
                This.readerPageshow();      
            },Antime);              
        }else if(max-page<2||page==2){
            page--;
            var tid = page;
            var sid = page+1;
            var oid = page+2;         
            var twidth = concent.find('li[index="'+tid+'"]').attr('px');               
            var swidth = concent.find('li[index="'+sid+'"]').attr('px');                               
            var owidth = concent.find('li[index="'+oid+'"]').attr('px');                               
            if(twidth==2) flagprve = false;
            leftnum--;
            This.readernewResizeleft(leftnum,Antime);
            //自定义动画清除
            if(twidth==1) This.readerAnimateoff(tid);
            setTimeout(function(){
                //自定义动画执行
                if(twidth==1) This.readerAnimateon(tid);
                 //音频视频重新load
                if(issingle){
                    if(sid) This.readerVideostop(swidth);  
                }else{
                    if(oid) This.readerVideostop(owidth);  
                };                
                This.readerPageshow();  
                This.readerreturnbtn(); 
            },Antime);
        }else{
            page--;
            var fid = page-1;   
            var tid = page;
            var sid = page+1;
            var oid = page+2;         
            var twidth = concent.find('li[index="'+tid+'"]').attr('px');               
            var swidth = concent.find('li[index="'+sid+'"]').attr('px');                               
            var owidth = concent.find('li[index="'+oid+'"]').attr('px');                   
            if(twidth==2) flagprve = false;
            var html ='<li load="false" index="'+fid+'"><div class="loading-parent"><div class="loading"></div></div></iframe></li>';
            concent.find('ul').prepend(html);
            concent.find('li:last').remove();
            leftnum++;
            This.readernewResizeleft(leftnum); 
            leftnum--;
            setTimeout(function(){ 
                This.readernewResizeleft(leftnum,Antime); 
            },1);
            //自定义动画清除
            if(twidth==1) This.readerAnimateoff(tid);          
            This.readerAnimateoff(page);            
            setTimeout(function(){ 
                //自定义动画执行
                if(twidth==1) This.readerAnimateon(tid);
                 //音频视频重新load
                if(issingle){
                    if(sid) This.readerVideostop(swidth);  
                }else{
                    if(oid) This.readerVideostop(owidth);  
                };           
                This.readerreturnbtn();  
                This.readerPageshow();                           
            },Antime);   
        };
        },1);
        _setTime = setTimeout(function(){
            This.readerMouse();     
        },1000);            
    };
    //下一页  处理 视频、音频、动画、显示切换
    this.readerNext = function(){
        if($Next.hasClass(disabledcls)){
            //下一篇杂志;
            if(isweb){
                if($('.turnRight').length>0){
                    window.location.href = $('.turnRight').attr('href');
                }
            }
            return false;
        }
        //处理BUG
        if(issingle){
            if(page==max) return;
        }else{
            if(page==max-1) return;
        }
        loadflag = true;
        flagprve = true;
        _setTime = null;
        setTimeout(function(){
            if(!flagnext){
                flagnext = true;
                flagprve = false;
                //自定义动画清除
                if(!issingle){
                    var tid = page+1;
                    var twidth = concent.find('li[index="'+tid+'"]').attr('px');   
                    if(twidth==1) This.readerAnimateoff(tid); 
                };
                leftnum++;
                This.readernewResizeleft(leftnum,Antime);
                setTimeout(function(){
                    //自定义动画执行
                    if(!issingle){   
                        if(twidth==1) This.readerAnimateon(tid); 
                    };
                    This.readerPageshow();  
                },Antime);
            }else if(page==min||max-page<=2||max<=4){   
                page++;
                var fid = page-1;
                var tid = page;
                var sid = page+1;
                var fwidth = concent.find('li[index="'+fid+'"]').attr('px');               
                var twidth = concent.find('li[index="'+tid+'"]').attr('px');               
                var swidth = concent.find('li[index="'+sid+'"]').attr('px');               
                if(twidth==2) flagnext = false;               
                leftnum++;
                This.readernewResizeleft(leftnum,Antime);
                //自定义动画清除
                if(issingle){
                    if(twidth) This.readerAnimateoff(tid);
                }else{
                    if(twidth) This.readerAnimateoff(tid);
                    if(twidth==1&&swidth==1) This.readerAnimateoff(sid);
                }
                setTimeout(function(){
                    //自定义动画执行
                    if(issingle){
                        if(twidth) This.readerAnimateon(tid);
                    }else{
                        if(twidth) This.readerAnimateon(tid);
                        if(twidth==1&&swidth==1) This.readerAnimateon(sid);
                    } 
                    //音频视频重新load 
                    if(fwidth) This.readerVideostop(fid);                
                    This.readerPageshow(); 
                    This.readerreturnbtn(); 
                },Antime); 
            }else{
                page++;
                var fid = page-1;
                var tid = page;
                var sid = page+1;
                var oid = page+2;
                var fwidth = concent.find('li[index="'+fid+'"]').attr('px');               
                var twidth = concent.find('li[index="'+tid+'"]').attr('px');               
                var swidth = concent.find('li[index="'+sid+'"]').attr('px'); 
                if(twidth==2) flagnext = false; 
                var html ='<li load="false" index="'+oid+'"><div class="loading-parent"><div class="loading"></div></div></iframe></li>';
                concent.find('ul').append(html);
                if(concent.find('li:first').attr('px')==2){
                    leftnum = leftnum-2;
                }else{
                    leftnum--;
                }
                concent.find('li:first').remove();
                This.readernewResizeleft(leftnum); 
                leftnum++;
                setTimeout(function(){ 
                    This.readernewResizeleft(leftnum,Antime); 
                },1);
                //自定义动画清除
                if(issingle){
                    if(twidth) This.readerAnimateoff(tid);
                }else{
                    if(twidth) This.readerAnimateoff(tid);
                    if(twidth==1&&swidth==1) This.readerAnimateoff(sid);
                }
                setTimeout(function(){              
                    //自定义动画执行
                    if(issingle){
                        if(twidth) This.readerAnimateon(tid);
                    }else{
                        if(twidth) This.readerAnimateon(tid);
                        if(twidth==1&&swidth==1) This.readerAnimateon(sid);
                    } 
                    //音频视频重新load 
                    if(fwidth) This.readerVideostop(fid);                                         
                    This.readerreturnbtn(); 
                    This.readerPageshow();                               
                },Antime);
            }
        },1);        
        _setTime = setTimeout(function(){
            This.readerMouse();     
        },1000);       
    }; 
    //快速滑动判断
    this.readerMouse = function(){
        mouse = true;
        concent.find('li[load="false"]').find('iframe').remove();
        var relust = This.readerIframeindex(page).loadArray; 
        for (var i = 0;  i < areA; i++) {
            if(!concent.find('li[index="'+ relust[i]+'"]').find('iframe').length){
                concent.find('li[index="'+ relust[i]+'"]').append('<iframe index="'+relust[i]+'" onload="javascript:me.readerWidth('+relust[i]+',this,1,'+page+',2)" src="'+This.readerGetinfo('url',relust[i])+'"></iframe>');
                break;
            }
        }
    };
    //iframe加载顺序    
    this.readerIframeindex = function(cur) {
        maxL = max;
        var loadArray = [];
        var loadindex = [];
        if(cur-areL>0){
            if(cur+areR>maxL){
                var c = 0;
                for (var i = 0; i < areA; i++) {
                    if(cur+i>maxL){
                        c++;
                        loadArray.push(cur-c);
                    }else{
                        loadArray.push(cur+i);
                    }
                };
                leftwidth = cur-(maxL-3);
                loadindex = [maxL-3,maxL-2,maxL-1,maxL];
            }else{
                leftwidth = 1;
                loadindex = [cur-1,cur,cur+1,cur+2];
                loadArray = [cur,cur+1,cur+2,cur-1];

            }
        }else{
            leftwidth = 0;
            loadindex = [cur,cur+1,cur+2,cur+3];
            loadArray = [cur,cur+1,cur+2,cur+3];    
        }
       return {
            'leftwidth' :leftwidth,
            'loadindex' :loadindex,
            'loadArray' :loadArray
       };
    };  
    //iframe单双页判断
    this.readerWidth = function(curpage,obj,typeid,targetid){
        var cur = curpage;
        var type = typeid;
        var indexpage = targetid;
        var $this = $(obj);
        var $w = isphone?320:768;
        if(!arrpage[cur]){      
            var subWeb = parseInt($this.contents().find("body").css('width'));
            if(subWeb>$w){
                arrpage[cur] = 2;                
            }else{
                arrpage[cur] = 1;
            }                   
        };
        $this.parents('li').attr('load','true');
        $this.parents('li').attr({'px': arrpage[cur]});
        if(arrpage[cur]==2){
            $this.contents().find("html").css('overflow-x','hidden');
            $this.parents('li').css({'width':eachwidth*arrpage[cur]});
            $this.css({'width':$w*arrpage[cur]});
            $this.contents().find("body").css('width','100%');  
            if(cur<page) leftnum++,This.readernewResizeleft(leftnum);
        };
        $this.parents('li').find('.loading-parent').remove();        
        if(firstload){
            // This.readerGridpic();
            // this.readerCateArray();    
            if(max == 2){
                $leftpage.show();
                $rightpage.show();
            }else if(max>2){
                $leftpage.show();
                $rightpage.show();
                $progressbar.show();
                $buttonicon.show();
            };
            $Prev.show();
            $Next.show();   
            firstload = false;
            This.readeTiprtos();                                                      
        };
        if(page == cur){
            if(mouse){
                if(arrpage[cur]==2){
                    flagnext = false;
                    flagprve = true;                          
                }else{
                    flagnext = true;
                    flagprve = true;
                }                    
            }else{
                if(arrpage[cur]==2){
                     if(isdouble) {
                        leftnum++;
                        This.readernewResizeleft(leftnum);
                        flagnext = true;
                        flagprve = false;                               
                    }else{
                        flagnext = false;
                        flagprve = true;
                    }                           
                }else{
                    flagnext = true;
                    flagprve = true;
                }
            }
             This.readerPageshow();
        }   
       
        var relust = This.readerIframeindex(page).loadArray; 
        for (var i = 0;  i < areA; i++) {
            if(!concent.find('li[index="'+ relust[i]+'"]').find('iframe').length){
                concent.find('li[index="'+ relust[i]+'"]').append('<iframe index="'+relust[i]+'" onload="javascript:me.readerWidth('+relust[i]+',this,1,'+page+',2)" src="'+This.readerGetinfo('url',relust[i])+'"></iframe>')
                break;
            }
        }            
    };          
    //iframe单双页判断
    this.readerWidthlold = function(curpage,obj,typeid,targetid){
        var cur = curpage;
        var type = typeid;
        var indexpage = targetid;
        var $this = $(obj);
        var $w = isphone?320:768;
        //是否缓存大小
        if(!arrpage[cur]){      
            var subWeb = parseInt($this.contents().find("body").css('width'));
            if(subWeb>$w){
                arrpage[cur] = 2;
            }else{
                arrpage[cur] = 1;
            }                   
        }
        //加载完给参数
        $this.parents('li').attr('load','true');
        
        if(type==1){
            var noloadnum = concent.find('li[load="false"]').length;    
            if(!noloadnum){             
                if(max<4){
                    if(arrpage[1]==1&&max==1){
                        indexpage = 1;                      
                    }else if((arrpage[1]==2&&max==1)||(max==2&&arrpage[1]==1&&arrpage[2]==1)){                      
                        indexpage = 1; 
                    }else{
                        $Prev.show();
                        $Next.show();
                        concent.find('.loading-parent').remove();
                        This.readersmallAm(indexpage);
                    }
                }else{
                    var pagewidth = arrpage[indexpage];
                    leftnum = arrpage[indexpage-1];
                    if(indexpage==max-1){
                        leftnum += arrpage[indexpage-2];
                    }else if(indexpage==max){
                        if(pagewidth==2||issingle){
                            leftnum += arrpage[indexpage-2]+arrpage[indexpage-3];
                        }else{
                            indexpage = indexpage--;
                            leftnum = arrpage[indexpage-1]+arrpage[indexpage-2];
                            pagewidth = arrpage[indexpage];                             
                        };
                    }
                    This.readernewResizeleft(leftnum);
                    if(mouse){
                        //快速滑动处理
                        if(flagprve==false){
                            leftnum++;
                            This.readernewResizeleft(leftnum);                              
                        }
                        if(pagewidth==2){
                            flagnext = false;
                            flagprve = true;
                        }else{
                            flagnext = true;
                            flagprve = true;
                        }                        
                    }else{
                        //首次加载或者目录跳转
                        if(pagewidth==2){
                             if(isdouble) {
                                leftnum++;
                                This.readernewResizeleft(leftnum);
                                flagnext = true;
                                flagprve = false;                               
                            }else{
                                flagnext = false;
                                flagprve = true;
                            }                           
                        }else{
                            flagnext = true;
                            flagprve = true;
                        }
                    }
                };
                page = indexpage;
                 concent.find('li').each(function() {
                    var $th = $(this).find('iframe');
                    var iframewidth =  $(this).attr('index');
                    //加载完给W标识标识 都load完重置参数
                     $(this).attr({'px': arrpage[iframewidth]});
                    //双页处理
                     if(arrpage[iframewidth]==2){
                        $th.contents().find("html").css('overflow-x','hidden');
                        $th.parents('li').css({'width':eachwidth*arrpage[iframewidth]});
                        $th.css({'width':$w*arrpage[iframewidth]});
                        $th.contents().find("body").css('width','100%');              
                    };                  
                 });                  
                $('iframe').show();
                concent.find('.loading-parent').remove();
                This.readerAnimateon(indexpage);
                if(!issingle){
                    if(arrpage[indexpage]==1&&arrpage[indexpage+1]==1)  This.readerAnimateon(indexpage+1);                  
                }           
                if(firstload){
                    if(max == 2){
                        $leftpage.show();
                        $rightpage.show();
                    }else if(max>2){
                        $leftpage.show();
                        $rightpage.show();
                        $progressbar.show();
                        $buttonicon.show();
                    };
                    $Prev.show();
                    $Next.show();   
                    firstload = false;  
                    This.readeTiprtos()                                                       
                }   
                This.readerPageshow();      
            }
        }    
    };
    //设备判断  
    this.readerdevice = function(cb){
        if(isweb){
            var $readerscale = $('.readermain');
            $readerscale.css('transform','scale(0.75)');
            $readerscale.css('-webkit-transform','scale(0.75)');
        }else{
            if(!isphone&&ismobile) return;
            $readermain.removeClass('isphone isipad PCdevice IPADdevice PHONEdevice horizontal');
            if($('#stylecss').length) $('#stylecss').remove();
            if(isphone){
                //phone页面  pc phone ipad处理
                $readermain.addClass('isphone');
                issingle = true; 
                eachwidth  = 320;
                var $height = 568;  
                var bi = 1;               
                if(ispc){
                    $readermain.addClass('PCdevice');
                    var $readerscale = $('.readermain');
                    var $wh = $(window).height()-10;
                    var bi = 1;
                    if($wh<568){
                        bi = $wh/568;
                    }
                    $readerscale.css('transform','scale('+bi+')');
                    $readerscale.css('-webkit-transform','scale('+bi+')'); 
                }else if(isipad){
                    $readermain.addClass('IPADdevice');
                }else{
                    if($('#localStorage').length){
                        $('#localStorage').css('height',$(window).height());
                        $('#localStorage').css('width',$(window).width());                               
                    } 
                    if($(window).width()>$(window).height()){
                        $readermain.hide();
                        if(!$('#readertip').length){
                            $('body').append('<table id="readertip" style="width:100%;height:100%;background:#000"><tr><td style="vertical-align:middle;"><div style="color:#fff;text-align:center;display:block" class="tips"><p style="text-align:center">此内容不支持手机横向</p><p style="text-align:center">请旋转手机查看</p></div></td></tr></table>');
                            $('#readertip').css('height',$(window).height());
                        }else{
                            $('#readertip').css('height',$(window).height());
                            $('#readertip').css('width',$(window).width());
                            $('#readertip').show();
                        }
                    }else{
                        $('#readertip').hide();
                        $readermain.show()
                        $readermain.addClass('PHONEdevice'); 
                        eachwidth  = 320;
                    }  
                    bi = $(window).width()/320;    
                    $height = $(window).height()/bi;  
                }              
                $('.readermain').css({
                    "width" : eachwidth,
                    "height" : $height
                });
                if(!ispc){
                    var styletext = '<div id="stylecss"><style>';
                        styletext += '.readermain-box.isphone .reader-concent-inner li{width:'+eachwidth+'px;height:'+$height+'px;}';
                        styletext += '.readermain-box.isphone .readermain{transform:scale('+bi+');-webkit-transform:scale('+bi+');transform-origin:center 0;-webkit-transform-origin:center 0}';                    
                        styletext += '</style></div>';
                    $('body').append(styletext);
                }
            }else{
                //ipad页面 pc phone ipad处理
                $readermain.addClass('isipad');  
                if(ispc){
                    $readermain.addClass('PCdevice');
                    var $readerscale = $('.readermain');
                    var $wh = $(window).height()-10;
                    var bi = 1;
                    if($wh<1024){
                        bi = $wh/1024;
                    }
                    $readerscale.css('transform','scale('+bi+')');
                    $readerscale.css('-webkit-transform','scale('+bi+')');  
                }else if(isipad){
                    $readermain.addClass('IPADdevice');
                    var ipadflag = $(window).width()>768;
                    var n = 1;
                    if(ipadflag){
                        issingle = false;
                        eachwidth  = 473;
                        n = 2;
                        $readermain.addClass('horizontal');                                  
                    }else{
                        issingle = true;
                        eachwidth  = 690;
                    }
                    var $height = Math.ceil(eachwidth*4/3);
                    $('.readermain').css({
                        "width" : eachwidth*n,
                        "height" : $height
                    });                 
                    var bi = eachwidth/768;
                    var styletext = '<div id="stylecss"><style>';
                        styletext += '.reader-concent-inner li{width:'+eachwidth+'px;}';
                        styletext += '.reader-concent-inner li iframe{transform:scale('+bi+');-webkit-transform:scale('+bi+')}';
                        styletext += '</style></div>';
                    $('body').append(styletext);
                }else{
                    $readermain.addClass('PHONEdevice');
                    issingle = true;
                    eachwidth  = $(window).width();
                    var wheight  = $(window).height();
                    var $phoneheight = Math.ceil(eachwidth*4/3);
                    $('.readermain').css({
                        "width" : eachwidth,
                        "height" : $phoneheight
                    });                  
                    var bi = eachwidth/768;
                    var styletext = '<div id="stylecss"><style>';
                        styletext += '.reader-concent-inner li{width:'+eachwidth+'px;}';
                        styletext += '.reader-concent-inner li iframe{transform:scale('+bi+');-webkit-transform:scale('+bi+')}';
                        styletext += '</style></div>';
                    $('body').append(styletext);
                }
            };
        }
        if(issingle){
            if(!hashId||hashId<min){
                hashId = 1;
            }else if(hashId>=max){
                hashId = max;
            }else{
                hashId = Number(hashId)
            }
        }else{
            if(!hashId||hashId<min){
                hashId = 1;
            }else if(hashId>=max){
                hashId = max-1;
            }else{
                hashId = Number(hashId)
            }
        }
        $('.readermain').show();
        if(cb) cb(hashId);        
    };
    //浏览器大小改变
    this.readerreset = function(){
        if(!isphone&&ismobile) return;
        if(!isweb){
            $('.reader-concent-inner ').find('iframe').each(function(){
                var arrrindex = $(this).attr('index');
                if(arrpage[arrrindex]==2) $(this).parent('li').css({'width':2*eachwidth});
            });    
            if(!issingle){          
                if(page==max){
                    if(arrpage[max-1]==1&&arrpage[max]==1){
                        page--;
                        leftnum--;
                    }else if(arrpage[max-1]==1&&arrpage[max]==2){
                        if(flagnext){
                            leftnum--;
                            flagnext = false;
                        }   
                    }else if(arrpage[max-1]==2&&arrpage[max]==1){
                        page--;
                        leftnum--;
                        flagnext = true;
                        flagprve = false;
                    }else if(arrpage[max-1]==2&&arrpage[max]==2){
                        if(flagnext){
                            leftnum--;
                            flagnext = false;
                        }                       
                    }
                };
                if(page<max){
                    if((arrpage[page]==1&&arrpage[page+1]==1)||(arrpage[page]==2&&flagnext&&arrpage[page+1]==1)) {
                        $('iframe[index="'+parseInt(page+1)+'"]').get(0).contentWindow.fnResetAnimate();    
                        This.readerAnimateon(page+1); 
                    }               
                }else{
                    $('iframe[index="'+page+'"]').get(0).contentWindow.fnResetAnimate();    
                    This.readerAnimateon(page);                       
                }
            }
            This.readernewResizeleft(leftnum);
            this.readerPageshow();
        }
    };
    //页码显示
    this.readerPageshow = function(){
        // console.log(page);
        var cur = page;
        //判断按钮是否能点击
        $Prev.removeClass(disabledcls);
        $Next.removeClass(disabledcls); 
        if(!issingle){
            //双页处理
            if(ispc&&!isphone){
                $leftpage.text(cur);
                if((arrpage[cur]==2&&flagnext)||arrpage[cur]==1){                   
                    $rightpage.find('em.page').text(Number(cur)+1);
                }else{
                     $rightpage.find('em.page').text(cur);
                }
                if(!isweb){
                    var $barw = Number($rightpage.find('em.page').text());
                    var $w = 1536/max;
                    $progressbar.find('.passbar').css({'width':$w*$barw+'px'});  
                }               
            }else{
                $leftpage.hide();
                $rightpage.hide();  
            }           
            //按钮是否能点击
            if(page==1&&flagprve) $Prev.addClass(disabledcls);
            if(page==max-1&&(arrpage[max]==1||!arrpage[max])&&arrpage[max-1]==1) $Next.addClass(disabledcls);
            if(page==max-1&&(arrpage[max]==1||!arrpage[max])&&arrpage[max-1]==2&&flagnext) $Next.addClass(disabledcls);
            if(page==max) $Next.addClass(disabledcls);
        }else{
            if(page==1&&flagprve) $Prev.addClass(disabledcls);
            if(page==max&&flagnext) $Next.addClass(disabledcls);                
        }
        //缩略图增加标识
        $('.grid-pic-list').eq(cur-1).parents('li').addClass(gripclass).siblings('li').removeClass(gripclass); 
        //URL标识
        document.location.hash = '#'+cur;                   
        //动画完成重置变量  
        loadflag = false;
        isdouble = false;
        //返回目录功能
        if(ishascatalog&&isphone){
            if(page == 1){
                $catalogicon.hide();
            }else{
                $catalogicon.show();
            } 
            backid = This.readerbackcate(page);
        } 
    };
    //参数拼接
    this.readerGetinfo = function(type,value){
        var n = type;
        var i = value;
        //URL参数   统计
        var a = ismz?'y':'n';
        var b = ispc?'pc':isipad?'pa':'ph';
        var c = is_weixn()?'wx':'ot';        
        var d = browser.versions.ios?'i':browser.versions.android?'a':browser.versions.ws?'w':'o';
        var e = 'n';
        var jsonArray = {
            'online' :  0,
            'm' :  a,
            'd' :  b,
            'b' :  c,
            'o' :  d,
            's' :  e           
        };
        var tmps = [];  
        for (var key in jsonArray) {  
            tmps.push(key + '=' + jsonArray[key]);  
        }
        var dataArray = tmps.join('&');       
        switch(n){
        case 'xml':
           return ismz?'http://static.magme.com/appprofile/' + appId + '/' + topicId + '/config.xml':isweb?'http://www.magme.com/config-xml.action?issueId='+topicId:'http://static.magme.com/appprofile/app903/' + topicId + '/config.xml'; 
           // return ismz?'http://static.magme.com/appprofile/' + appId + '/' + topicId + '/config.xml':'http://static.magme.com/appprofile/app903/' + topicId + '/config.xml'; 
           break;
        case 'url':
           var returnurl = '';
           var varname = isphone?'phone':'main';
           if(ismz){
                // returnurl = 'http://static.magme.com/appprofile/' + appId + '/' + topicId + '/' + i + '/'+varname+'.html';
                returnurl = 'http://static.magme.com/appprofile/' + appId + '/' + topicId + '/' + i + '/'+varname+'.html?'+dataArray;
           }else{
                // returnurl = 'http://static.magme.com/appprofile/app903/'+ topicId + '/' + i + '/'+varname+'.html';
                returnurl = 'http://static.magme.com/appprofile/app903/'+ topicId + '/' + i + '/'+varname+'.html?'+dataArray;
           }
           return returnurl;
           break;
        case 'pic':
           var returnpic = '';
           var varname = isphone?'phone':'pad';
           if(ismz){
                returnpic = 'http://static.magme.com/appprofile/' + appId + '/' + topicId + '/' + i +'/'+varname+'_q'+ i +'.jpg';
           }else{
                returnpic = 'http://static.magme.com/appprofile/app903/' + topicId + '/' + i +'/'+varname+'_q'+ i +'.jpg';
           }
           return returnpic;            
           break;
        };            
    }; 
    //iframe加载
    this.readerloadiframe = function(bid,tid,pid){
        return '<li load="false" index="'+bid+'"><div class="loading-parent"><div class="loading"></div></div><iframe index="'+bid+'"  onload="javascript:me.readerWidth('+bid+',this,'+tid+','+pid+')" src="'+This.readerGetinfo('url',bid)+'"></iframe></li>';
    };     
    //自定义动画  可以删除
    this.readerAnimate = function(pageid){
        if($('iframe[index="'+pageid+'"]').get(0).contentWindow.fnCustomAnimate){
            $('iframe[index="'+pageid+'"]').get(0).contentWindow.fnCustomAnimate();
        };
    }; 
     this.readerCateArray = function(){                    
        if(ishascatalog){
            var objinfo = {}; 
            var i = 1;
            $(r).find('page').each(function(){
                var $this = $(this);
                var isCatalog = $this.find('belongToCatalog').text();
                //第一个一定为0
                if($(this).index()==0){
                    isCatalog = 1;
                }                       
                if(isCatalog==1){
                    var title = $this.find('title').text();
                    objinfo = {
                        'Otitle' : title,
                        'Oindex' : [i]
                    }
                    OInfo.push(objinfo);    
                }else{
                    var Oi = OInfo.length-1;
                    OInfo[Oi]['Oindex'].push(i);
                }
                i++;
            });
            if(ispc){
                This.readerCatlog();   
            }else if(isphone){
                //PHONE页面返回目录和首页用到OInfo
            }else{
                $catalogicon.hide();
            }                   
        }else{
            $catalogicon.hide();    
        }   
    }                
    //生成HTML
    this.readerGridpic = function(){
        var maxtwo = Math.ceil(max/2);
        var maxtwoflag = max%2?true:false;
        var gridpicul = '';
        for (var i = 0; i < maxtwo; i++) {
            var pageidprev = parseInt(2*i+1);
            var pageidnext = parseInt(2*i+2);
            gridpicul+='<li><div class="grid-pic-list-box clearfix">';
            gridpicul+='<div class="grid-pic-list"><img src="'+This.readerGetinfo("pic",pageidprev)+'"><em>'+pageidprev+'</em></div>';
            if(!(i==maxtwo-1&&maxtwoflag)){
                gridpicul+='<div class="grid-pic-list"><img src="'+This.readerGetinfo("pic",pageidnext)+'"><em class="rightem">'+pageidnext+'</em></div>';
            }
            gridpicul+='</div></li>';
        };
        $('.grid-pic ul').html(gridpicul);
        
    };  
    //生成目录
    this.readerCatelog = function(){
        var Cataloghtml = ''; 
        for (var i = 0,s = OInfo.length; i < s; i++) {
            var firstindex = Number(OInfo[i]['Oindex'][0]);
            var l = OInfo[i]['Oindex'].length+firstindex;
            Cataloghtml += '<li><h4><a title="'+OInfo[i]['Otitle']+'" index="'+ OInfo[i]['Oindex'][0]+'" href="javascript:;">'+OInfo[i]['Otitle']+'</a></h4>';
            var j = firstindex;
            while (j<l){
               Cataloghtml += '<div index="'+j+'" class="catalog-list"><em>'+This.readeraddZero(j)+'</em><img src="'+This.readerGetinfo("pic",j)+'"></div>';
               j++;
            }           
            Cataloghtml += '</li>'
        };
        var $w = parseInt(OInfo.length*100+50);
        $('.catalog-pic ul').css('width', $w +'px');
        $('.catalog-pic ul').html(Cataloghtml);     
    };      
    //URL更改新增参数
    this.readerChangeParam = function(href,name,value){
        var url = href ;
        var newUrl="";   
        var reg = new RegExp("(^|)"+ name +"=([^&]*)(|$)");
        var tmp = name + "=" + value;
        if(url.match(reg) != null){
           newUrl= url.replace(eval(reg),tmp);
        }else{
           if(url.match("[\?]")){
            newUrl= url + "&" + tmp;
           }else{
            newUrl= url + "?" + tmp;
           }
        }     
        return newUrl;
    };
    //重置translate3d
    this.readernewResizeleft = function(n,t){
        var time = t?t:0;
        var $z = 'translate(-'+parseInt(n*eachwidth)+'px, 0px)';
        concent.css({'transition-duration': time+'ms','transform':$z});
        concent.css({'-webkit-transition-duration': time+'ms','-webkit-transform':$z});
    };      
    //补零方法
    this.readeraddZero = function(n){
        return n < 10 ? n = '0' + n : n;
    }; 
    //根据page 返回目录
    this.readerbackcate = function(n){
        var re = 1;
        for (var i = 0,s = OInfo.length; i < s; i++) {
            var ind = $.inArray(n, OInfo[i]['Oindex']);
            if(ind>=0){
                $catalogicon.removeClass('gohome');
                if(ind==0){
                    ind = 1;
                    $catalogicon.addClass('gohome');
                }else{
                    ind = OInfo[i]['Oindex'][0];                    
                }
                re = ind;
                break;
            }
        }
        return re;
    };
     //相册功能滑动开启
    me.readerGalleryon = function(i){
         mySwiper =  new Swiper('.swiper-container',{
            initialSlide : i,
            speed : 500,
            onTouchEnd  : function(){
              var mySwiperindex =  mySwiper.activeIndex;
                _move(mySwiperindex);
            }     
        });
    };     
    //相册功能滑动
    me.readerGallerygo = function(i){
      if(mySwiper){
         mySwiper.swipeTo(i, 500)
      }
    };     
    //清除视频音频
    this.readerVideostop = function(pageid){
        if(concent.find('li[index="'+pageid+'"]').length&&concent.find('li[index="'+pageid+'"]').attr('px')){
            $('iframe[index="'+pageid+'"]').contents().find('video,audio').each(function() {
                $(this).get(0).load();
            });
        }
    };    
    //自定义动画执行
    this.readerAnimateon = function(pageid){
        if(concent.find('li[index="'+pageid+'"]').length&&concent.find('li[index="'+pageid+'"]').attr('px')){
            if($('iframe[index="'+pageid+'"]').get(0).contentWindow.fnCustomAnimate){
                $('iframe[index="'+pageid+'"]').get(0).contentWindow.fnCustomAnimate();
            };
        }
    };    
    //自定义动画清除
    this.readerAnimateoff = function(pageid){
        if(concent.find('li[index="'+pageid+'"]').length&&concent.find('li[index="'+pageid+'"]').attr('px')){
            if($('iframe[index="'+pageid+'"]').get(0).contentWindow.fnResetAnimate){
                $('iframe[index="'+pageid+'"]').get(0).contentWindow.fnResetAnimate();
            };
        }
    };              
    //显示切换隐藏按钮
    this.readerHidebtn = function(){
        if(!isphone) return;
        if(!(concent.find('li[index="'+page+'"]').length&&concent.find('li[index="'+page+'"]').attr('px'))) return;
        var isslideDoorLock = $('iframe[index="'+page+'"]').contents().find('html').html().match(/slideDoorLock\(\{[\s\S]*?\}\)/ig);
        if(isslideDoorLock){
            if(isslideDoorLock.join('').match(/1/ig)){
                return false;
            }else{
                $buttonicon.addClass('none');
                $Prev.addClass('none');
                $Next.addClass('none');             
            }
        };  
    };
     //显示切换显示按钮 
    this.readerShowbtn = function(){
        if(!isphone) return;
        if(!(concent.find('li[index="'+page+'"]').length&&concent.find('li[index="'+page+'"]').attr('px'))) return;        
        $buttonicon.removeClass('none');
        $Prev.removeClass('none');
        $Next.removeClass('none'); 
    }; 
    //显示切换隐藏按钮后滑动处理
    this.readerreturnbtn = function(){
        if(!isphone) return;
        if(!(concent.find('li[index="'+page+'"]').length&&concent.find('li[index="'+page+'"]').attr('px'))) return;        
        var isslideDoorLock = $('iframe[index="'+page+'"]').contents().find('html').html().match(/slideDoorLock\(\{[\s\S]*?\}\)/ig);
        if(isslideDoorLock){
            if(isslideDoorLock.join('').match(/1/ig)){
                This.readerShowbtn();
            }else{
                 if($('iframe[index="'+page+'"]').contents().find('.MD_btn_current').length){
                    This.readerHidebtn();
                 }else{
                    This.readerShowbtn();   
                 }
            }
        }else{
            This.readerShowbtn();           
        }       
    }; 
    //开关灯功能
    this.readerSwitching = function(){
        if(isweb){
            isweb = false; 
            $('html').addClass('switching');
            $fullscreen.addClass('webscreen');
            $readermain.removeClass('isweb');
            $('body').css({'background' : 'url(http://static.magme.com/appprofile/read/img/bg.png) repeat'}); 
            $rightpage.find('em.pageCount').hide();
        }else{
             isweb = true;
            $('html').removeClass('switching');
            $fullscreen.removeClass('webscreen');
            $readermain.addClass('isweb');
            $('body').css({'background' : ''}); 
            $('.reader-icon').trigger('click');
            $rightpage.find('em.pageCount').show();
        }
        This.readerdevice();
    };
    //提示层
    this.readeTiprtos = function(){
       if((!getCookie('reader')||hashId==1)&&!ispc){
           if(!$('#localStorage').length) {                 
                var localStorageHTML;
                if(ismobile){       
                    if(is_weixn()){
                        localStorageHTML = $('<div id="localStorage"><i class="localCT phoneCT"></i><i class="localCC phoneCC"></i></div>');
                    }else{
                        localStorageHTML = $('<div id="localStorage"><i class="localCC phoneCC"></i></div>');
                    }
                    localStorageHTMLBtn = $('<i class="localCB phoneCB"></i>');
                }else{
                    localStorageHTML = $('<div id="localStorage"><i class="localCC ipadCC"></i></div>');
                    localStorageHTMLBtn = $('<i class="localCB ipadCB"></i>');
                }
                localStorageHTML.append(localStorageHTMLBtn);
                localStorageHTML.css('height',$(window).height());
                localStorageHTMLBtn.bind('click',function(){
                   $('#localStorage').remove();
                    addCookie('reader',true,0.2); 
                });                     
                $('body').append(localStorageHTML);                             
           }
       }   
    }             
    //小图标点击
    this.readericonclick = function(o){
        var target = $(o.target);
        if(target.hasClass('cur')) return;
        // if(loadflag) return;
        var cls = target.attr('class');
        target.parent().find('span').removeClass('cur');
        target.addClass('cur');        
        switch(cls){    
        case 'reader-icon':
            if(ishascatalog&&isphone){
                if(page>1)$catalogicon.show()                                                   
            }        
            $gridpic.parent().hide();
            ishascatalog?$catalogpic.parent().hide():'';
            $reader.show(); 
           break;
        case 'grid-icon':
            if(ishascatalog&&isphone){
                $catalogicon.hide()                                                 
            }        
            $reader.hide();
            ishascatalog?$catalogpic.parent().hide():'';    
            $gridpic.parent().show();           
            if(gridflag&&ispc){
                This.readerGridpic();
                $gridpic.mCustomScrollbar({
                    mouseWheelPixels : '600'
                });
                gridflag = false;
            }else if(gridflag&&!ispc){
                This.readerGridpic();
                gridflag = false;
            };
           break;
        case 'catalog-icon':
        case 'catalog-icon gohome':
            if(ishascatalog&&isphone){
                This.readerJump(backid);
                $catalogicon.removeClass('cur');    
                $readericon.addClass('cur');                                                        
            }else{
                $reader.hide();
                $gridpic.parent().hide();
                $catalogpic.parent().show();                    
                if(catalogflag){
                    if(ispc&&ishascatalog) This.readerCatelog(); 
                    var $h = $('.catalog-pic ul').outerHeight(true);
                    if($h<=1024){
                        $('.catalog-pic').css('width','1536px');
                    }                       
                    $catalogpic.mCustomScrollbar({
                        axis : 'yx',
                        mouseWheelPixels : '600'
                    });
                    catalogflag = false;
                }   
            }   
           break;
        };  
    };  
    this.readerInit();
    //上一页触发
    var eventtype = ispc?'click':'click';
    $Prev.bind(eventtype,function(event) {
        This.readerPrev();
    }); 
    //下一页触发 
    $Next.bind(eventtype,function(event) {      
        This.readerNext();
    }); 
    //window大小重置
    $(window).resize(function(event) {
        This.readerdevice();
        This.readerreset(); 
    }); 
    //缩略图点击 
    $('.grid-pic-list').live(eventtype, function(event) {
        var e = event||window.event;
        event.stopPropagation();
        var i = $('.grid-pic-list').index($(this))+1;
        $readericon.trigger('click');
        This.readerJump(i,'grid');
    }); 
    //目录点击
    $catalogpic.find('h4 a,.catalog-list').live(eventtype, function(event) {
        var e = event||window.event;
        event.stopPropagation();
        var i = $(this).attr('index');
        $readericon.trigger('click');
        This.readerJump(i);
    }); 
    //小图标切换         
    $buttonicon.bind(eventtype,function(e) {
        This.readericonclick(e);
    });
    //开关灯
    $fullscreen.live(eventtype, function(event) {
        This.readerSwitching();
    });        
    //移动端支持手滑功能
    if(ismobile||isipad){
        concent.find('li').live('swipeleft',function(){
            This.readerNext();
        });
        concent.find('li').live('swiperight',function(){
            This.readerPrev();
        });    
    }
};
$(function(){
    me.reader($('#magezineBox'));
});