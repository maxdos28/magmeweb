var shareToObj = {
	defaultInfo:{
		url: 'http://www.magme.com',
		title: "看杂志 ·上麦米网",
		imgsrc:"http://www.magme.com/images/logo.gif",
		desc:''
	},
	shareType: function (type,tagInfo){
		switch(type)
		{
			case 'tqq':
				shareToObj.shareToQwb(tagInfo);
				break;
			case 'tsina':
				shareToObj.shareToSina(tagInfo);
				break;
			case 'renren':
				shareToObj.shareToRenren(tagInfo);
				break;
			case 'qzone':
				shareToObj.shareToQzone(tagInfo);
				break;
			case 'kanxin':
				shareToObj.shareToKanxin(tagInfo);
				break;
		}
	},
	
	shareToSina: function(setting) {
		var tagInfo = $.extend({},shareToObj.defaultInfo,setting);

		javascript: void((function(s, d, e) {
	        try {} catch(e) {}
	        var f = 'http://v.t.sina.com.cn/share/share.php?',


	        p = ['url=', e(tagInfo.url), '&title=', e(tagInfo.title),'&pic=',e(tagInfo.imgsrc), '&appkey=3515375224&ralateUid=2173428060'].join('');
	        
	        function a() {
	            if (!window.open([f, p].join(''), "_blank", "width=615,height=505")) {
	                u.href = [f, p].join('');
	            }
	        };
	        if (/Firefox/.test(navigator.userAgent)) {
	            setTimeout(a, 0);
	        } else {
	            a();
	        }
	    })(screen, document, encodeURIComponent));
	},
    
    shareToQzone: function(setting) {
    	var tagInfo = $.extend({},shareToObj.defaultInfo,setting);
    	javascript: void((function(s, d, e) {
            try {} catch(e) {}
            var f = 'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?';
            var s = {
                url: tagInfo.url,
                desc: tagInfo.desc,
                summary: '',
                title: tagInfo.title,
                pics: tagInfo.imgsrc
            };
            var p = [];
            for (var i in s) {
                p.push(i + '=' + e(s[i] || ''));
            }
            function a() {
                 if (!window.open([f, p.join('&')].join(''), "_blank", "width=615,height=505")) {
                    u.href = [f, p.join('&')].join('');
                 }               
            };
            if (/Firefox/.test(navigator.userAgent)) {
                setTimeout(a, 0);
            } else {
                a();
            }
        })(screen, document, encodeURIComponent));
    },
    
    shareToKanxin: function(setting) {
    	var tagInfo = $.extend({},shareToObj.defaultInfo,setting);
    	javascript: void((function(s, d, e) {
            try {} catch(e) {}
            var f = 'http://www.kaixin001.com/rshare/share.php?';
            var s = {
        		rurl: tagInfo.url,
        		rcontent: tagInfo.desc,
                rtitle: tagInfo.title,
                pics: tagInfo.imgsrc
            };
            var p = [];
            for (var i in s) {
                p.push(i + '=' + e(s[i] || ''));
            }
            function a() {
                 if (!window.open([f, p.join('&')].join(''), "_blank", "width=615,height=505")) {
                    u.href = [f, p.join('&')].join('');
                 }               
            };
            if (/Firefox/.test(navigator.userAgent)) {
                setTimeout(a, 0);
            } else {
                a();
            }
        })(screen, document, encodeURIComponent));
    },
    
    shareToQwb: function(setting) {
    	var tagInfo = $.extend({},shareToObj.defaultInfo,setting);
    	
        javascript: void((function(s, d, e) {
            try {} catch(e) {}
            var f = 'http://v.t.qq.com/share/share.php?',
            p = ['url=', e(tagInfo.url),'', '&title=', e(tagInfo.title),'&pic='+e(tagInfo.imgsrc), '&appkey=a91806d1da344d89a32405cbeac99df6'].join('');
            function a() {
                if (!window.open([f, p].join(''), "_blank", "width=615,height=505")) {
                    u.href = [f, p].join('');
                }
            };
            if (/Firefox/.test(navigator.userAgent)) {
                setTimeout(a, 0);
            } else {
                a();
            }
        })(screen, document, encodeURIComponent));
    },
    
    shareToRenren: function(setting){
    	var tagInfo = $.extend({},shareToObj.defaultInfo,setting);
    	javascript: void((function(s, d, e) {
		    try {
		    	var shareInfo = {
				  "title": e(tagInfo.title),
				  "link": e(tagInfo.url),
				  "image_src": e(tagInfo.imgsrc),
				  "message": e("亲，这个标签不错啊，可以去看看！"),
				  "description": e(tagInfo.desc)
			    };
		    	if(window.XN){
			    	XN.Connect.showShareDialog(shareInfo, function(confirm) {
		    	      if (confirm) {
		    	        alert('分享成功');
		    	      }
		    	      else {
		    	        alert('取消了分享');
		    	      }
		    	    });
		    	}
		    } catch(e) {
		    	alert(e);
		    }
    	})(screen, document, encodeURIComponent));
    }
    
    
/* sina

<script type="text/javascript" charset="utf-8">
(function(){
  var _w = 72 , _h = 16;
  var param = {
    url:location.href,
    type:'3',
    count:'1', //是否显示分享数，1显示(可选)
    appkey:'', //**您申请的应用appkey,显示分享来源(可选)
    title:'', //**分享的文字内容(可选，默认为所在页面的title)
    pic:'', //**分享图片的路径(可选)
    ralateUid:'', //**关联用户的UID，分享微博会@该用户(可选)
    rnd:new Date().valueOf()
  }
  var temp = [];
  for( var p in param ){
    temp.push(p + '=' + encodeURIComponent( param[p] || '' ) )
  }
  document.write('<iframe allowTransparency="true" frameborder="0" scrolling="no" src="http://hits.sinajs.cn/A1/weiboshare.html?' + temp.join('&') + '" width="'+ _w+'" height="'+_h+'"></iframe>')
})()
</script>

 */
    
    
/*  QQ Weibo
 <a href="javascript:void(0)" onclick="postToWb();return false;" class="tmblog"><img src="http://v.t.qq.com/share/images/s/b16.png" border="0" alt="转播到腾讯微博" ></a><script type="text/javascript">
	function postToWb(){
		var _t = encodeURI(document.title);
		var _url = encodeURIComponent(document.location);
		var _appkey = encodeURI("appkey");//你从腾讯获得的appkey
		var _pic = encodeURI('');//（例如：var _pic='图片url1|图片url2|图片url3....）
		var _site = '';//你的网站地址
		var _u = 'http://v.t.qq.com/share/share.php?url='+_url+'&appkey='+_appkey+'&site='+_site+'&pic='+_pic+'&title='+_t;
		window.open( _u,'', 'width=700, height=680, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no' );
	}
</script>
*/
    /*  QQ Weibo    
    <script type="text/javascript">
    (function(){
    var p = {
    url:location.href,
    desc:'',//默认分享理由(可选)
    summary:'',//摘要(可选)
    title:'',//分享标题(可选)
    site:'',//分享来源 如：腾讯网(可选)
    pics:'' //分享图片的路径(可选)
    };
    var s = [];
    for(var i in p){
    s.push(i + '=' + encodeURIComponent(p[i]||''));
    }
    document.write(['<a href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?',s.join('&'),'" target="_blank" title="分享到QQ空间"><img src="http://qzonestyle.gtimg.cn/ac/qzone_v5/app/app_share/qz_logo.png" alt="分享到QQ空间" /></a>'].join(''));
    })();
    </script>
    */    
 
}
