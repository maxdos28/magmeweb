var scrolltotop={
	setting: {startline:1, scrollto: 0, scrollduration:500, fadeduration:[500, 100]},
	controlHTML: '<a class="goTop"></a>', 
	controlattrs: {offsetx:5, offsety:5}, 
	anchorkeyword: '#top',
	state: {isvisible:false, shouldvisible:false},
	scrollup:function(){
		if (!this.cssfixedsupport) 
			this.$control.css({opacity:0})
		var dest=isNaN(this.setting.scrollto)? this.setting.scrollto : parseInt(this.setting.scrollto)
		if (typeof dest=="string" && jQuery('#'+dest).length==1) 
			dest=jQuery('#'+dest).offset().top
		else
			dest=0
		this.$body.animate({scrollTop: dest}, this.setting.scrollduration);
		
	},
	keepfixed:function(){
		var $window=jQuery(window)
		var controlx=$window.scrollLeft() + $window.width() - this.$control.width() - this.controlattrs.offsetx
		var controly=$window.scrollTop() + $window.height() - this.$control.height() - this.controlattrs.offsety
		this.$control.css({left:controlx+'px', top:controly+'px'})
	},
	togglecontrol:function(){
		var scrolltop=jQuery(window).scrollTop()
		if (!this.cssfixedsupport)
			this.keepfixed()
		this.state.shouldvisible=(scrolltop>=this.setting.startline)? true : false
		if (this.state.shouldvisible && !this.state.isvisible){
			this.$control.css({cursor:'pointer'});
			this.$control.show();
			this.$control.stop().animate({opacity:1}, this.setting.fadeduration[0])
			this.state.isvisible=true
		}
		else if (this.state.shouldvisible==false && this.state.isvisible){
			this.$control.css({cursor:'default'});
			this.$control.hide();
			this.$control.stop().animate({opacity:0}, this.setting.fadeduration[1])
			this.state.isvisible=false
		}
	},
	init:function(){
		jQuery(document).ready(function($){
			var mainobj=scrolltotop
			var iebrws=document.all
			mainobj.cssfixedsupport=!iebrws || iebrws && document.compatMode=="CSS1Compat" && window.XMLHttpRequest 
			mainobj.$body=(window.opera)? (document.compatMode=="CSS1Compat"? $('html') : $('body')) : $('html,body')
			mainobj.$control=$('<div id="topcontrol">'+mainobj.controlHTML+'</div>')
				.css({position:mainobj.cssfixedsupport? 'fixed' : 'absolute', bottom:40+'px', opacity:0, cursor:'default'})
				.click(function(){mainobj.scrollup(); return false})
				.appendTo('body')
			if (document.all && !window.XMLHttpRequest && mainobj.$control.text()!='')
				mainobj.$control.css({width:mainobj.$control.width()})
			mainobj.togglecontrol()
			$('a[href="' + mainobj.anchorkeyword +'"]').click(function(){
				mainobj.scrollup()
				return false
			})
			$(window).bind('scroll resize', function(e){
				mainobj.togglecontrol()
			})
			offsetRight();
		})
		function offsetRight() {
			var oldCss=$('#topcontrol').attr('style');
			var right=20+'px';
			$('#topcontrol').attr('style',oldCss+';RIGHT:'+right);	
		}
	}
}

//isIE6
$(function(){
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
if(!isIE6){scrolltotop.init()}
});
