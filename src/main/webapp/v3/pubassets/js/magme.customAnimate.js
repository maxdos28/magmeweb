function CustomAnimate(json) {
	this.json = json;
	this.init();
	Animateflag = false;
}
//离开页面动画结束
function fnResetAnimate(){
	Animateflag = true;
	$('div').each(function(){
		var $cls = $(this).attr('class');
		if(!$cls||!$cls.match(/CM_[A-Za-z]+\d+/g)) return;
		var c = $cls.match(/CM_[A-Za-z]+\d+/g).join('');
		if(c.indexOf('flip')>0){
			$(this).unwrap();
			$(this).attr('style','');
		}
		$(this).removeClass(c);
		$(this).css('opacity','');
	});
}
CustomAnimate.prototype = {
	init: function() {
		this.id = [];
		this.type = [];
		this.delay = [];
		this.rotate = [];
		for (var i in this.json) {
			for (var j in this.json[i]) {
				this[j].push(this.json[i][j]);
			}
		};
		if($('#createCss').length>0){
			this.runing();
		}else{
			this.createCss();
		}	
	},
	runing: function() {
		var This = this;
		$.each(this.id,
		function(i, n) {
			var delaytime = This.delay[i];
			var amtype = This.type[i];
			switch(amtype){
				case "floatV":
				case "floatH":
				case "breath":
				case "rotate":	
					delaytime = 0;			
			}
			setTimeout(function() { //动画样式名为原样式名后，添加序号
				if(Animateflag) return;
				$('#' + n).addClass('CM_' + This.type[i] + i).delay(500).css({
					opacity: 1
				}); //进场动画0.5秒后，透明度高度为1
			},
			delaytime);
		});
	},
	createCss: function() {
		var This = this;
		var o0 = 'opacity:0;',
		//开始透明度
		o1 = 'opacity:1;',
		//结束透明度
		_style = '<div id="createCss"><style>';

		$.each(this.type,
		function(i, n) {
			//弧度转换为角度
			var _rotate = This.rotate[i] * Math.PI / 180,
			_start = This.calAngle(n, _rotate),
			//css动画起始处0%
			_end = This.endAngle(_rotate); //css动画结层处100%，循环动画为0%

			switch (n) {
			case "fade":
				_style += This.createName(n + i, '.5s ease'); //动画class名后面加序号
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{" + o0 + "}100%{" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{" + o0 + "}100%{" + o1 + "}}";
				break;
			case "blur":
				//webkit= blur   other = fade
				_style += This.createName(n + i, '1.5s ease');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{" + _start.start + ";" + o0 + "}100%{" + _start.end + ";" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{" + o0 + "}100%{" + o1 + "}}";
				break;
			case "grayscale":
				//webkit= grayscale   other = fade
				_style += This.createName(n + i, '3s ease');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{" + _start.start + ";" + o1 + "}100%{" + _start.end + ";" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{" + o0 + "}100%{" + o1 + "}}";
				break;
			case "scale":
				_style += This.createName(n + i, '.5s ease');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{-webkit-transform:matrix(" + _end + ");" + o0 + "}1%{-webkit-transform:matrix(" + _start.start + ");" + o0 + "}45%{-webkit-transform:matrix(" + _start.middle + ");}100%{-webkit-transform:matrix(" + _end + ");" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{transform:matrix(" + _end + ");" + o0 + "}1%{transform:matrix(" + _start.start + ");" + o0 + "}45%{transform:matrix(" + _start.middle + ");}100%{transform:matrix(" + _end + ");" + o1 + "}}";
				break;
			case "breath":
				_style += This.createName(n + i, '2s linear infinite');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{-webkit-transform:matrix(" + _end + ");" + o1 + "}20%{-webkit-transform:matrix(" + _start + ");" + o0 + "}40%{-webkit-transform:matrix(" + _end + ");" + o1 + "}100%{-webkit-transform:matrix(" + _end + ");" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{transform:matrix(" + _end + ");" + o1 + "}20%{transform:matrix(" + _start + ");" + o0 + "}40%{transform:matrix(" + _end + ");" + o1 + "}100%{transform:matrix(" + _end + ");" + o1 + "}}";
				break;
			case "rotate":
				_style += This.createName(n + i, '2s linear infinite');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{-webkit-transform:" + _start.start + ";" + o1 + "}50%{-webkit-transform:" + _start.middle + ";" + o1 + "}100%{-webkit-transform:" + _start.end + ";" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{transform:" + _start.start + ";" + o1 + "}50%{transform:" + _start.middle + ";" + o1 + "}100%{transform:" + _start.end + ";" + o1 + "}}";
				break;
			case "flip":
				$('#' + This.id[i]).wrapAll("<div style='width:" + $('#' + This.id[i]).width() + "px;height:" + $('#' + This.id[i]).height() + "px;z-index:"+$('#' + This.id[i]).css('z-index')+";left:" + $('#' + This.id[i]).offset().left + "px;top:" + $('#' + This.id[i]).offset().top + "px;-webkit-perspective:1000px;perspective:1000px;position:absolute;'></div>");
				$('#' + This.id[i]).css({
					position: 'static',
					'box-shadow': '0 0 1px rgba(255,255,255,0)'
				});
				_style += This.createName(n + i, '.5s linear');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{-webkit-transform:matrix3d(" + _start + ");" + o1 + "}100%{-webkit-transform:matrix(" + _end + ");" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{transform:matrix3d(" + _start + ");" + o1 + "}100%{transform:matrix(" + _end + ");" + o1 + "}}";
				break;
			case "floatV":
			case "floatH":
				//横向纵向动画结构相同，数值不同
				_style += This.createName(n + i, '1.5s linear infinite');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{-webkit-transform:matrix(" + _end + ");" + o1 + "}25%{-webkit-transform:matrix(" + _start.start + ");" + o1 + "}75%{-webkit-transform:matrix(" + _start.middle + ");" + o1 + "}100%{-webkit-transform:matrix(" + _end + ");" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{transform:matrix(" + _end + ");" + o1 + "}25%{transform:matrix(" + _start.start + ");" + o1 + "}75%{transform:matrix(" + _start.middle + ");" + o1 + "}100%{transform:matrix(" + _end + ");" + o1 + "}}";
				break;
			default:
				_style += This.createName(n + i, '.5s ease');
				_style += "@-webkit-keyframes " + "cm_" + n + i + "{0%{-webkit-transform:matrix(" + _start + ");" + o0 + "}100%{-webkit-transform:matrix(" + _end + ");" + o1 + "}}";
				_style += "@keyframes " + "cm_" + n + i + "{0%{transform:matrix(" + _start + ");" + o0 + "}100%{transform:matrix(" + _end + ");" + o1 + "}}";
				break;

			}

		});
		_style += '</style></div>';
		//页面中生成样式
		$('body').prepend(_style);
		This.runing();

	},
	calAngle: function(t, deg) {
		//matrix(cos(deg),sin(deg),-sin(deg),cos(deg),X,Y);	//2D		
		//XY 位移
		//scale (1*s,0,0,1*s,x,y)
		//skew (1,tan(θy),tan(θx),1,0,0)

		//rotateY(10deg)
		//matrix3d(1  ,0,0   ,0,0,1,0,0,0  ,0,1  ,0,0,0,0,1);
		//matrix3d(cos,0,-sin,0,0,1,0,0,sin,0,cos,0,0,0,0,1);

		var _cos = Math.cos(deg).toFixed(6),
		//角度值需要保留6位，否则0,90,180,270会用科学计数
		_sin = Math.sin(deg).toFixed(6);
		_angle = _cos + ',' + _sin + ',' + ( - 1 * _sin) + ',' + _cos;

		switch (t) {
		case 'up':
			return _angle + ',0,-150';
			break;
		case 'down':
			return _angle + ',0,150';
			break;
		case 'left':
			return _angle + ',-150,0';
			break;
		case 'right':
			return _angle + ',150,0';
			break;
		case 'scale':
			return { //可用json格式,定义多步骤的动画
				start:
				_cos * 0.1 + ',' + _sin + ',' + ( - 1 * _sin) + ',' + _cos * 0.1 + ',0,0',
				middle: _cos * 1.1 + ',' + _sin + ',' + ( - 1 * _sin) + ',' + _cos * 1.1 + ',0,0'
			};
			break;
		case 'blur':
			return {
				start:
				'-webkit-filter: blur(75px)',
				end: '-webkit-filter: blur(0)'
			};
			break;
		case 'grayscale':
			return {
				start:
				'-webkit-filter: grayscale(1)',
				end: '-webkit-filter: grayscale(0)'
			};
			break;
		case 'breath':
			return _cos * 0.8 + ',' + _sin + ',' + ( - 1 * _sin) + ',' + _cos * 0.8 + ',0,0';
			break;
		case 'floatV':
			return {
				start:
				_angle + ',0,-3',
				middle: _angle + ',0,6'
			}
			break;
		case 'floatH':
			return {
				start:
				_angle + ',-3,0',
				middle: _angle + ',6,0'
			}
			break;
		case 'rotate':
			return {
				start:
				_angle + ',0,0',
				middle: 'rotate(180deg)',
				end: 'rotate(360deg)'
			}
			break;
		case 'flip':
			var _flipcos = Math.cos(2 * Math.PI / 360 * 90).toFixed(6),
			_flipsin = Math.sin(2 * Math.PI / 360 * 90).toFixed(6);
			return _flipcos * 0.9 + ',0,' + _flipsin * 0.9 + ',0,0,' + 1 * 0.9 + ',0,0,' + ( - 1 * _flipsin) + ',' + _flipcos + ',0,0,0,0,0,1';
			break;
		default:
			return '1,0,0,1,0,0';
			break;
		}
	},
	endAngle: function(deg) {
		return Math.cos(deg) + ',' + Math.sin(deg) + ',' + ( - 1 * Math.sin(deg)) + ',' + Math.cos(deg) + ',0,0';
	},
	createName: function(d, t) {
		return ".CM_" + d + "{-webkit-animation:cm_" + d + " " + t + ";animation:cm_" + d + " " + t + ";}";
	},
	runingstop : function(){
		Animateflag = true;
		$('div').each(function(){
			var $cls = $(this).attr('class');
			if(!$cls||!$cls.match(/CM_[A-Za-z]+\d+/g)) return;
			var c = $cls.match(/CM_[A-Za-z]+\d+/g).join('');
			if(c.indexOf('flip')>0){
				$(this).unwrap();
				$(this).attr('style','');
			}
			$(this).removeClass(c);
			$(this).css('opacity','');
		});
	}
};