function AppObj(){}

AppObj.prototype = {
	gid : function(id){
		return document.getElementById(id);
	},
	tab : function(dom){
		var $dom = dom;
		var $btn = $dom.find('div.appTabBtn li');
		var $pcon = $dom.find('div.appTabCon');
		var $con = $pcon.find('div.appTabConItem');
		var on = 'current';
		$con.each(function(i){
			var $this =$(this);
			if(i==0){$this.parent().css({height:$this.innerHeight()});}
			$this.data("height",$this.innerHeight());	
		});

		if($btn.length == $con.length){
			$btn.click(function(){
				var $this = $(this);
				if(!$this.hasClass(on)){
					var _index = $this.index();
					$this.addClass(on).siblings().removeClass(on);
					$con.eq(_index).fadeIn().siblings().hide();
					$pcon.css({height:$con.eq(_index).data('height')});
				}
			});
		}
	}
}



// 调用
$(function(){

	var appTab = new AppObj();
	var $appTabBox = $('div.appTabBox');

	$appTabBox.each(function(){
		appTab.tab($(this));
	});
	
})
