;(function($){
jQuery.jquerytagbox = function(id,currentId) {

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
		$doorItem.hide();
		$doorItem.eq(currentId).fadeIn(500).addClass("current");
	}
});


};
})(jQuery);
