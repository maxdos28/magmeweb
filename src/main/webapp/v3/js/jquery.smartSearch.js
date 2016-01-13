//需要调用 jquery1.4+, jquery.inputfocus, jquery.jscrollpane.min, jquery.mousewheel.min, 
//调用方法：$("#id").smartSearch(jsonFn);
//
//
//
//
//
//
//
//


;(function ($){
jQuery.fn.smartSearch = function(callback) {
	
	var $this = $(this);
	var $input = $this.find(".input");
	var $list = $this.find(".list");
	var $inlist = $list.find(">.inner");
	var _itemH;
	var _showNum;
	var _listVal;
	var _html;
	var _current=-1;
	var _val = $input.val();
	
	$list.hide();
	$input.bind("keyup", onInputChange);
	$inlist.find("li").live("mouseenter",function(){$(this).addClass("current").siblings().removeClass("current");_current=$(this).index()});
	$this.find("a").live("click", onTipsClick);


	function onInputChange(e) {
		if(e.keyCode==38||e.keyCode==40||e.keyCode==13){
			if($inlist.find("li").length>0){
				if(e.keyCode==38){
					if(_current==-1){
						$inlist.data('jsp').scrollToY(9999);
						_current=$inlist.find("li").length-1;
						$inlist.find("li").eq(_current).addClass("current");
						$input.val($inlist.find("li").eq(_current).find("strong").html());
					}
					else if(_current==0){
						$inlist.data('jsp').scrollToY(9999);
						_current=$inlist.find("li").length;
						$inlist.find("li").removeClass("current");
						$input.val(_val);
					}else{
						_current-=1;
						$inlist.find("li").eq(_current).addClass("current").siblings().removeClass("current");
						$input.val($inlist.find("li").eq(_current).find("strong").html());
					}
					if($inlist.find("li").length-_current>_showNum){
						$inlist.data('jsp').scrollByY(-_itemH);
					}
					
				}else if(e.keyCode==40){
					if(_current==$inlist.find("li").length){
						$inlist.data('jsp').scrollToY(0);
						_current=0;
						$inlist.find("li").eq(_current).addClass("current");
						$input.val($inlist.find("li").eq(_current).find("strong").html());
					}else if(_current==$inlist.find("li").length-1){
						$inlist.data('jsp').scrollToY(0);
						_current=-1;
						$inlist.find("li").removeClass("current");
						$input.val(_val);
					}else{
						_current+=1;
						$inlist.find("li").eq(_current).addClass("current").siblings().removeClass("current");
						$input.val($inlist.find("li").eq(_current).find("strong").html());
					}
					if(_current>_showNum-1){
						$inlist.data('jsp').scrollByY(_itemH);
					}
				}else{
					onTipsClick();
				}
			}
		}else{
			// 如果为无效输入(和之前内容一样)，输入框内字节没有发生变化
			if (_val == $input.val()) {
				return false;
			} else {
				_val = $input.val();
			}
			// 如果输入框内的字节长度为0
			if ($input.val().length == 0) {
				$inlist.html("").removeClass("jspScrollable");
				$list.hide();
				_current=-1;
				return false;
			} else {
				$list.fadeIn(200);
				_html = "<ul>";
				_listVal = callback($input.val());
				for (var i = 0; i < _listVal.length; i++) {
					if(!_listVal[i].num){
						_html += "<li><a href='javascript:void(0)'><strong>"+ _listVal[i].keyword + "</strong></a></li>";
					}else{
						_html += "<li><a href='javascript:void(0)'><span>" +_listVal[i].num+ "</span><strong>"+ _listVal[i].keyword + "</strong></a></li>";
					}
				}
				_html += "</ul>";
				$inlist.data("jsp","").html(_html);
				$inlist.jScrollPane();
				_current=-1;
				$inlist.find("li").removeClass("current");
				if(!_itemH){
					_itemH = $inlist.find("li").outerHeight();
				}
				_showNum = parseInt($inlist.height()/_itemH);
				
				
				
			}
		}
	}
	
	function onTipsClick() {
		$input.val($inlist.find("li").eq(_current).find("strong").html()).focus();
		_val = $input.val();
		$list.fadeOut(200,function(){$inlist.html("")});
		_current=-1;
	}
	
	
	
	
	
	
	
	
}})(jQuery);