;(function($){
jQuery.jquerytagbox = function(id,currentId) {
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
var $ctrlItem = $(id+" .ctrl>div");
var $doorList = $(id+" .doorList");
var $doorItem = $(id+" .doorList>.item");
if(currentId=="undefined"){currentId=0}else{currentId=currentId}

$ctrlItem.eq(currentId).addClass("current");
$doorItem.eq(currentId).show().addClass("current");

$($ctrlItem).click(function(){
	if(currentId != $(this).index()){
		currentId = $(this).index();
		//alert($doorItem.eq(currentId).height());
		$ctrlItem.removeClass("current");
		$(this).addClass("current");
		$doorItem.stop(true,true).hide();
		if(isIE6){
			var number = 0;
		}else{
			var number = 300;
		}
		$doorItem.eq(currentId).fadeIn(number).addClass("current");
	}
});


};
})(jQuery);
