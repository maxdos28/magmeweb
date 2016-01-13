(function(){
	function g(a,b,d){
		a=k.$(a);
		if(a==null)return false;
		b=b||"click";
		if((typeof d).toLowerCase()=="function"){
			if(a.attachEvent)a.attachEvent("on"+b,d);
			else if(a.addEventListener)a.addEventListener(b,d,false);
			else a["on"+b]=d;
			return true
		}
	}
	var k={
		$:function(a){
			return typeof a==="string"?document.getElementById(a):a
		}
		,trim:function(a){
			if(typeof a!=="string")return a;
			return typeof a.trim==="function"?a.trim():a.replace(/^(\u3000|\s|\t|\u00A0)*|(\u3000|\s|\t|\u00A0)*$/g,"")
		}
		,jsonToQuery:function(a,b){
			var d=function(f,q){
				f=f==null?"":f;
				f=k.trim(f.toString());
				return q?encodeURIComponent(f):f
			}
			,c=[];
			if(typeof a=="object")
				for(var e in a)
					if(a[e]instanceof Array)
						for(var l=0,r=a[e].length;l<r;l++)
							c.push(e+"="+d(a[e][l],b));
					else 
						typeof a[e]!="function"&&c.push(e+"="+d(a[e],b));
			return c.length?c.join("&"):""
		}
		,winSize:function(a){
			var b,d,c;
			c=a?a.document:document;
			if(self.innerHeight){
				c=a?a.self:self;
				b=c.innerWidth;
				d=c.innerHeight
			}else if(c.documentElement&&c.documentElement.clientHeight){
				b=c.documentElement.clientWidth;

				d=c.documentElement.clientHeight
			}else if(c.body){
				b=c.body.clientWidth;
				d=c.body.clientHeight
			}
			return{width:b,height:d}
		}
	},
	o=0,
	m=null,
	j=function(a){
		m=a;
		if(!o){
			window.postMessage?g(window,"message",p):s(window,"name");
			o=1
		}
	},
	s=function(a,b){
		var d="";
		timer=setInterval(function(){
			var c=a[b];
			if(c!=d){
			d=c;
			c=c.split("^").pop().split("&");
			c={
			domain:c[0],data:window.unescape(c[1])}
			;
			p(c)}
		},50)
	},
	p=function(a){
		if(typeof m!="undefined"){
			var b={};
			a=a.data;
			if(a.indexOf("#cancel=1")>0){
				b.cancel=1;
				a=a.replace("#cancel=1","")
			}
			if(a.length==0||a.indexOf("&")<0)b.cancel=1;
			else{
				a=a.split("&");
				for(var d=0;d<a.length;d++)
					try{
						var c=a[d].split("=");b[c[0]]=c[1]
					}catch(e){}
			}
			m(b)
		}
	},
	h={
		show:function(a){
			var b=document.createElement("div");
			b.innerHTML='<iframe id="dialogBox" src="'+a+'" scrolling="no" style="display:none;"></iframe>';
			document.body.appendChild(b.firstChild)
		}
	},
	t=0,
	i=function(){
		var a=k.jsonToQuery({
			width:document.body.clientWidth,
			height:document.body.clientHeight+40
		});
		a=escape(a);
		if(window.postMessage)
			parent.postMessage(a,"*");
		else 
			window.parent.name=(new Date).getTime()+t++ +"^"+document.domain+"&"+window.escape(a)
	},
	n="";
	(function(){
		function a(){
			var d=document.body.innerHTML;
			if(n!=d){
				i();
				n=d
			}
		}
		function b(){
			n=document.body.innerHTML;
			setInterval(a,1E3)
		}
		g(window,"load",i);
		if(navigator.userAgent.indexOf("MSIE")>0)
			document.onLoad=b;
		else{
			g(document.body,"DOMSubtreeModified",i);
			g(document.body,"DOMNodeInserted",i);
			g(document.body,"DOMNodeRemoved",i)
		}
	})();
	KX={
		_app_id:0,
		_host:"http://api.kaixin001.com",
		_logging:true,
		init:function(a){
			if(!KX._app_id){
				a=a||{};
				KX._app_id=a.app_id;
				if(!a.logging)
					KX._logging=false
			}
		},
		log:function(a){
			if(this._logging)
				if(window.Debug&&window.Debug.writeln)
					window.Debug.writeln(a);
				else 
					window.console && window.console.log(a)
		},
		checkParams:function(a){
			if(!a.app_id)
				throw Error("app_id is a required parameter.");
			if(a.display!="popup"&&a.display!="iframe")
				throw Error('"display" must be one of "popup", "iframe"');
		},
		login:function(a){
			this.checkParams(a);
			if(!a.scope)
				a.scope="basic";
			this.init(a);
			a=this._host+"/dialog/authorize?display="+a.display+"&app_id="+a.app_id+"&scope="+encodeURIComponent(a.scope);
			h.show(a)
		}
		,feed:function(a){
			this.checkParams(a);
			if(!a.redirect_uri||!a.display||!a.linktext||!a.link||!a.text)
				throw Error("one of redirect_uri,display,linktext,link,text not found.");
			if(!a.picurl)
				a.picurl="";
			if(!a.need_redirect)
				a.need_redirect=0;this.init(a);
			var b=this._host+"/dialog/feed?display="+a.display+"&redirect_uri="+encodeURIComponent(a.redirect_uri)+"&linktext="+a.linktext+"&link="+encodeURIComponent(a.link)+"&text="+a.text+	"&picurl="+encodeURIComponent(a.picurl)+"&app_id="+a.app_id+"&need_redirect="+a.need_redirect;
			a.cb&&j(a.cb);
			h.show(b)
		}
		,invitation:function(a){
			this.checkParams(a);
			if(!a.text||!a.redirect_uri)
				throw Error("one of redirect_uri,text not found.");
			if(!a.need_redirect)
				a.need_redirect=0;
			var b=this._host+"/dialog/invitation?display="+a.display+"&redirect_uri="+encodeURIComponent(a.redirect_uri)+"&app_id="+a.app_id+"&text="+a.text+"&need_redirect="+a.need_redirect;if(a.fuid)b=b+"&fuid="+a.fuid;
			a.cb&&j(a.cb);
			h.show(b)
		}
		,sysnews:function(a){
			this.checkParams(a);
			if(!a.linktext||!a.link||!a.text||!a.redirect_uri)
				throw Error("one of redirect_uri,linktext,link,text not found.");
			if(!a.picurl)
				a.picurl="";
			if(!a.need_redirect)
				a.need_redirect=0;
			var b=this._host+"/dialog/sysnews?display="+a.display+"&redirect_uri="+encodeURIComponent(a.redirect_uri)+"&app_id="+a.app_id+"&text="+a.text+"&need_redirect="+a.need_redirect+"&link="+a.link+"&picurl="+encodeURIComponent(a.picurl)+"&linktext="+a.linktext;
			a.cb&&j(a.cb);
			h.show(b)
		}
		,pay:function(a){
			this.checkParams(a);
			if(!a.callback||!a.pname||!a.pnumber||!a.pcode||!a.amount||!a.orderid)
				throw Error("one of callback,pname,pnumber,pcode,amount,orderid not found.");
			if(!a.redirect_uri)
				a.redirect_uri="";
			var b=this._host+"/dialog/pay?display="+a.display+"&redirect_uri="+encodeURIComponent(a.redirect_uri)+"&app_id="+a.app_id+"&callback="+encodeURIComponent(a.callback)+"&pname="+a.pname+"&pnumber="+a.pnumber+"&pcode="+a.pcode+"&amount="+a.amount+"&orderid="+a.orderid+"&sig="+a.sig;
			a.cb&&j(a.cb);
			h.show(b)
		}
	};
	window.KX=KX
})();

