//分享内容页添加顶部banner
function MagmeaddBanner($src, $link) {
	var $banner = $('<div class="promotionAd"><a href="' + $link + '"><img /><span href="javascript:void(0);">X</span></a></div>');
	$banner.css({
		width: '100%'
	});
	$banner.find('img').attr({
		'src': $src
	}).css({
		'width': '100%',
		'height': 'auto'
	});
	$banner.find('span').css({
		position: 'absolute',
		width: 30,
		height: 30,
		right: 0,
		top: 0,
		background: '#fff',
		textAlign: 'center',
		lineHeight: '30px',
		'text-decoration': 'none',
		display: 'none',
		cursor: 'pointer'
	});
	$banner.find('span').one('click',
	function() {
		$(this).parent().animate({
			marginTop: -$(this).parent().innerHeight()
		},
		1000,
		function() {
			$banner.remove();
		})
	});
	$('#body').prepend($banner);
}

$(function() {
	//旧内容页面无phone样式
	//if($('body').hasClass('phone')){
	var _href = location.href.substring(location.href.indexOf('appId=') + 6);
	var flagHref = _href.indexOf('&'),
	_val = '';
	if (flagHref != '-1') {
		_val = _href.substring(0, flagHref);
	} else {
		_val = _href;
	}

	if (_val == 903) {
		document.title = "LOO客";
		MagmeaddBanner('http://static.magme.com/appprofile/pubassets/img/phoneAd.jpg', 'http://static.magme.com/appdownload/app_look.html');
	} else if (_val == 901) {
		document.title = "凤凰周刊";
		MagmeaddBanner('http://static.magme.com/appprofile/pubassets/img/phoneAd_fhzk.jpg', 'http://static.magme.com/appdownload/app_phoenix.html');
	}
	//}
});