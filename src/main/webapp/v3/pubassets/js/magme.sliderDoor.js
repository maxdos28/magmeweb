//显示切换
$.fn.slideDoorLock = function(options){
   var setting = {
        cur:null,			//默认打开
        ani:'fade',			//默认动画
        mask:false,			//默认不显示遮罩
        only:true,			//默认只能打开一个  
        close:true,			//点击内容区域关闭自身，不需要传递
        btn:false,			//内容区域打开后是否关闭点击区域
        time:500			//内容区域动画时间
    };

    var opts = $.extend({}, setting, options);
    opts.only = !opts.only;

    if(!opts.only){     
       opts.cur = null;
    };
    var $$shade = $('<div class="MD_Slidedoor_shade"></div>'),
        $$body  = $('body');
 
    if(opts.mask && $$body.find("div.MD_Slidedoor_shade").length == 0){
        $$body.append($$shade);
    };


    //动画1
    $.fn.baseAnimate = function(o){
        if(o.only){
            return $(this).show().siblings('.MD_body').hide().end();
        }else{
            return $(this).show();
        }
    };
    //动画2
    $.fn.slideAnimate = function(o,_c,_r){
        if(o.only && typeof(_r) !='undefined'){
            $.hideAnimate({
                opts:o,
                tar:_c.eq(_r)
             });
        }
        $(this).slideDown();
    };
    //动画3
    $.fn.cssAnimate = function(o,_c,_r){
        var _this = $(this);
        if(o.only && typeof(_r) !='undefined'){
            $.hideAnimate({
                opts:o,
                tar:_c.eq(_r)
             });
        }
        _this.show().addClass(_this.data('para').showclass);
    };
    //关闭动画
    $.hideAnimate = function(obj){
        if(obj.tar.data('para').anitype == 'slide'){
            obj.tar.slideUp();
        }
        else{
           obj.tar.removeClass(obj.tar.data('para').showclass).addClass(obj.tar.data('para').hideclass);
            setTimeout(function(){
                obj.tar.hide().removeClass(obj.tar.data('para').hideclass);
            },obj.opts.time);
        }
        obj.tar.saveVideo(); 
    };
    //状态切换
    $.fn.currentState = function(o){
        if(o.btn){
            if(o.only){
               return $(this).addClass('MD_btn_current').fadeOut().siblings('.MD_btn').removeClass('MD_btn_current').fadeIn().end();
            }else{
               return $(this).addClass('MD_btn_current').fadeOut();
            }  
        }else{
            if(o.only){
                return $(this).addClass('MD_btn_current').siblings('.MD_btn').removeClass('MD_btn_current').end();
            }else{
                return $(this).addClass('MD_btn_current');
            }   
        }
    };
    //音频禁止
    $.stopAudio = function(){
        var _audio =  $$body.find("div.MD_body").find('audio');
        for(var i=0,len=_audio.length;i<len;i++){
            _audio[i].load();
        }
    };
    //音频播放
    $.fn.playAudio = function(){
        $(this).find('audio').length!=0 ? $(this).find('audio')[0].play(): '';
    };
    //视频删除
    $.fn.saveVideo = function(){
        if(typeof($(this).data('video')) != 'undefined'){
            $(this).find('video').remove();
        };
    };
    //遮罩层切换
    $.shadeShowHide = function(obj){
        if(obj.tp=='show'){
             obj.btn.addClass('MD_zIndex');
             obj.con.addClass('MD_zIndexBG');
             $$body.addClass('MD_bIndex');
             $$body.find('div.MD_Slidedoor_shade').fadeIn();
        };
        if(obj.tp=='hide'){
             obj.btn.removeClass('MD_zIndex');
             setTimeout(function(){
                obj.con.removeClass('MD_zIndexBG');
             },obj.time);
             $$body.removeClass('MD_bIndex');
             $$body.find('div.MD_Slidedoor_shade').fadeOut();
        };
    };
    //保存操作记录
    $.recondAry = function(obj){
        obj.recond.push(obj.tar.data('para').index);
        if(obj.only){
            obj.recond.length > 1 ?  obj.recond.shift() : '';
        };
        $.stopAudio();
    };
    //删除操作记录
     $.recondRemove = function(obj){
         if(obj.only){
            obj.recond.shift();
         }else{
            for(var i=0,len=obj.recond.length;i<len;i++){
                if(obj.recond[i] == obj.tar.data('para').index){
                   obj.recond.splice(i,1);
                   break;
                }
            }   
         }
    };
    //遮罩层关闭判断
    $.maskHide = function (obj){
        var _btnCurrent = obj.block.find('>div.MD_btn_current').length;
         if(obj.opts.mask){
            if(_btnCurrent!=0 &&  $$body.find('div.MD_Slidedoor_shade').is(':hidden')){
                $.shadeShowHide({
                    tp:'show',
                    btn:obj.btn,
                    con:obj.con,
                    time:obj.opts.time
                });
            };
            if(_btnCurrent == 0){
                 $.shadeShowHide({
                    tp:'hide',
                    btn:obj.btn,
                    con:obj.con,
                    time:obj.opts.time
                });
            };
        }else{
            obj.btn.addClass('MD_zIndex');
            obj.con.addClass('MD_zIndexBG');
            if((obj.tp =='close' && obj.opts.only) || (!obj.opts.only && _btnCurrent==0)){
                 obj.btn.removeClass('MD_zIndex');
                 setTimeout(function(){
                    obj.con.removeClass('MD_zIndexBG');
                 },obj.opts.time);  
            }
        }
    };
    //关闭
    $.allClose = function(o){
        $.recondRemove({
            only:o.opts.only,
            tar:o.theBtn,
            con:o.con,
            recond:o.recond
        });
        $.hideAnimate({
            opts:o.opts,
            tar:o.theCon
         });
        $.maskHide({
            opts:o.opts,
            block:o.self,
            btn:o.btn,
            con:o.con,
            tp:o.tp
        });
    }



    return $(this).each(function(){
        var  _this = $(this),
             _btn = _this.find('>div.MD_btn'),
             _con = _this.find('>div.MD_body'),
             _len = _con.length,
             _flag_spec = false,    // 如果cur>0 需要打开指定项
             _videoplay = 0;        //处理web阅读器视频
             _recond = [];

         var _opts = opts;      //配置
        _opts.ani =  _opts.ani.split(';');
        for(var i=0;i<_opts.ani.length;i++){
            if(_opts.ani[i]==''){//兼容输出错误，结尾是;不作处理
                _opts.ani.splice(i,1);
            }
        }

         //获得参数
         for(var i=0;i<_len;i++){
            var con_temp =  _con.eq(i);
            _btn.eq(i).data('para',{index:i,lock:0});
            con_temp.data('para',{
                index:i,
                showclass:typeof(_opts.ani[i])!="undefined" ? "MD_"+_opts.ani[i]+'_show' : "MD_"+_opts.ani[0]+'_show',//向前兼容，只有一个动画时，使用同样动画
                hideclass:typeof(_opts.ani[i])!="undefined" ? "MD_"+_opts.ani[i]+'_hide' : "MD_"+_opts.ani[0]+'_hide',
                anitype:typeof(_opts.ani[i])!="undefined" ? _opts.ani[i] : _opts.ani[0],
                lock:0
            });
            if(con_temp.find('video').length !=0){
                var _parent = con_temp.find('video').parent();
                con_temp.data('video',{
                    html:_parent.html(),
                    pid:_parent.attr('id')
                });
            };
         };
        
         //初始化cur
         if(_opts.cur !=null){
             _opts.cur-=1;//页面输出从1开始
             if(_opts.cur>-1){
                _flag_spec = true;//定义默认打开项后，不可关闭内容区域自身
                _opts.mask = false;//单开并且制定打开不允许有遮罩
                _recond.push(_opts.cur);
                _btn.eq(_opts.cur).currentState(_opts).addClass('MD_zIndex');
                _con.eq(_opts.cur).addClass('MD_zIndexBG').baseAnimate(_opts).playAudio();
             }else{
                _flag_spec = false;
             }
         }

         //点击区域添加CSS动画
         _btn.each(function(i){
            var $this = $(this);
            var _flag = false;
            if($this.find('img').length !=0){//image时处理
                var _dom = $this.find('img');
                _dom.each(function(i){
                    if(!_flag){
                        var $this = $(this);
                        var _png = $this.attr('size').split(',')[2] || $this.attr('src');
                        var _parent = $this.parent();
                        if(/^imgs\/.*\.png$/.test(_png)){
                           if(_parent.width()<50 && _parent.height()<50){
                                 _parent.addClass('MD_blinkImg');
                                 _flag = true;
                           }
                        }
                    }
                });
            }else{//backgroundImage时处理
                var _dom = $this.find('>div');
                _dom.each(function(i){
                    if(!_flag){
                        var $this = $(this);
                        var _png = _dom.css('background-image') || _dom.css('background');
                        if(_png && /imgs\/.*\.png\"\)$/.test(_png)){
                           if($this.width()<50 && $this.height()<50){
                                 $this.addClass('MD_blinkImg');
                                 _flag = true;
                           }
                        }
                    }
                });        
            }
         });

        //点击区域绑定Tap事件 
        _btn.bind('tap',function(event){

            var oEvent = event || window.event;
            oEvent.stopPropagation();
            oEvent.preventDefault();

            var $this  = $(this),							//定义点击区域
                $block = $this.next(),						//定义内容区域
                _aniType = $block.data('para').anitype,		//记录动画类型
                _oRecond = _recond[0];						//记录当前显示组
           
		   if($this.data('para').lock == 0){
                //屏蔽连续点击
                $this.data('para').lock = 1;
                setTimeout(function(){
                    $this.data('para').lock = 0;
                },_opts.time+100);


                //第一次点击后，移除所有点击区域动画
                if($this.find('.MD_blinkImg').length !=0){
                    _this.find('.MD_blinkImg').removeClass('MD_blinkImg');
             
                }

                //点击区域关闭
                if(!$this.hasClass('MD_btn_current')){
                    $this.currentState(_opts);
                    $.recondAry({
                        only:_opts.only,
                        tar:$this,
                        recond:_recond
                    });
                    $block.playAudio();
                    if(_opts.only){
                        _this.find('video').remove();
                    };
                    if(typeof($block.data('video')) != 'undefined' && $block.find('video').length == 0){   
                        $("#"+$block.data('video').pid).html($block.data('video').html);
                    };
                    if(_aniType == 'slide'){
                        $block.slideAnimate(_opts,_con,_oRecond);
                    }
                    else{
                        $block.cssAnimate(_opts,_con,_oRecond);
                    }
                    $.maskHide({
                        opts:_opts,
                        block:_this,
                        btn:_btn,
                        con:_con
                    });    
                //点击区域打开
                }else{
                    if(_flag_spec){
                        return false;
                    };
                    $this.removeClass('MD_btn_current');
                    $.stopAudio();
                    $.allClose({
                        opts:_opts,
                        theBtn:$this,
                        theCon:$block,
                        btn:_btn,
                        con:_con,
                        recond:_recond,
                        tp:'close',
                        self:_this
                    });
                }
             //WEB阅读器隐藏按钮
             if(isweb) parent.me.readerHidebtn();                  
           }
		   	    
        });

        //判断无默认打开项，可以全部内容区域自身
        if(!_flag_spec){
             _con.css({cursor:'pointer'}).bind('tap',function(event){

                var target = $(event.target);
                var tag = event.target.tagName;                 
                //WEB阅读器中含有  MD_slideBanner  video  点击取消关闭
                if(isweb){
                    if(target.parents().hasClass('MD_slideBanner')||(tag=="video")||(tag=="VIDEO")){
                       if(_videoplay==1) return;
                       if((tag=="video")||(tag=="VIDEO")){
                            _videoplay++;
                            if(_videoplay==1){
                                target.get(0).play();
                            } 
                       }
                       return false;
                    }
                };                               
                var oEvent = event || window.event;
                oEvent.stopPropagation();
                oEvent.preventDefault();
                var $this = $(this),
                $btn = $this.prev();

                if($this.data('para').lock == 0){
                //屏蔽连续点击
                    $this.data('para').lock = 1;
                    setTimeout(function(){
                        $this.data('para').lock = 0;
                    },_opts.time+100);


                    $btn.removeClass('MD_btn_current');
                    $.stopAudio();
                    if(_opts.btn){
                        _btn.eq(_con.index(this)).fadeIn();
                    }
                    $.allClose({
                        opts:_opts,
                        theBtn:$this,
                        theCon:$this,
                        btn:_btn,
                        con:_con,
                        recond:_recond,
                        tp:'close',
                        self:_this
                    });
                    if(isweb) newnum = 0;
                    //WEB阅读器显示按钮
                    if(isweb) parent.me.readerShowbtn();
                }  
            });
        };
    });

};;
