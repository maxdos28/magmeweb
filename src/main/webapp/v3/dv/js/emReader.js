	function copyToClipboard(selContent) {     
		if(window.clipboardData) {     
			window.clipboardData.clearData();     
			window.clipboardData.setData("Text", selContent);     
			alert("复制成功！");     
		} else if(navigator.userAgent.indexOf("Opera") != -1) {
			window.location = selContent;     
		} else if (window.netscape) {     
			try {
			   netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			} catch (e) {
			   alert("被浏览器拒绝！/n请在浏览器地址栏输入'about:config'并回车/n然后将'signed.applets.codebase_principal_support'设置为'true'");     
			}
			var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);     
			if (!clip)
			   return;
			var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);     
			if (!trans)
			   return;
			trans.addDataFlavor('text/unicode');     
			var str = new Object();     
			var len = new Object();     
			var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);     
			var copytext = selContent;     
			str.data = copytext;     
			trans.setTransferData("text/unicode",str,copytext.length*2);     
			var clipid = Components.interfaces.nsIClipboard;     
			if (!clip)
			   return false;
			clip.setData(trans,null,clipid.kGlobalClipboard);     
			alert("复制成功！");
	    }
    }
	
	var color = 333333;
	function getStr(config){
		if(config){
			var str = '<object id="midRead" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="' + config.width + '" height="' + config.height + '" >'
						+ '<param name="movie" value="' + SystemProp.staticServerUrl + '/reader/OffsiteTool.swf" />'
						+ '<param name="quality" value="high" />'
						+ '<param name="backColor" value="' + config.backColor + '" />'
						+ '<param name="allowScriptAccess" value="always" />'
						+ '<param name="allowFullScreen" value="true" />'
						+ '<param name="wmode" value="transparent" />'
						+ '<param name="FlashVars" value="publicationId=' + config.pid + '&issueId=' + config.iid + '&viewMode=' + config.model + '&backColor=' + config.backColor + '" />'
						+ '<embed FlashVars="publicationId=' + config.pid + '&issueId=' + config.iid + '&viewMode=' + config.model
						+ '&backColor=' + config.backColor + '" width="' + config.width + '" height="' + config.height + '"' 
						+ 'src="' + SystemProp.staticServerUrl + '/reader/OffsiteTool.swf" type="application/x-shockwave-flash" ' 
						+ 'allowscriptaccess="always" allowfullscreen="true" wmode="opaque"></embed>'
					+ '</object>';
			return str;
		}
		return 'no issue';
	}
	function buildConfig(){
		showSelIssue();
		var $width = $('#width');
		var $height = $('#height');
		if(!$width.val()) $width.val(450)
		if(!$height.val()) $height.val(600)
		var width = $width.val();
		var model=$('#model input:checked').val();
		$height.removeAttr("disabled");
		$height.removeClass("disabled");
		var height = $height.val();
		if(model != 2){
			$height.attr("disabled", "disabled");
			$height.addClass("disabled");
			height = Math.floor(width * (model == 1 ? (4 / 3) : 1));
			$height.val(height)
		}
		var config = null;
		var iid = $('#issue option:selected').val();
		if(iid){
			if($('#selIssue').css('display') == 'none') iid = 0;
			config = {
				pid : $('#publication option:selected').val(),
				iid : iid,
				model : $('#model input:checked').val(),
				backColor : '0x' + color,
				width : width,
				height : height
			};
		}
		return config;
	}
	
	function showPreview(config){
		var str = getStr(config);
		$("#shower").html(str);
		while(str.indexOf("<") > -1 || str.indexOf(">") > -1){
			str = str.replace("<", "&lt;").replace(">", "&gt;");
		}
		$("#emReaderStr").html(str);
	}
	
	function refreshColor(){
		var config = buildConfig();
		var str = getStr(config);
		$("#emReaderStr").html(str);
	}
	
	function refresh(){
		var config = buildConfig();
		showPreview(config);
	}
	
	function getIssue(){
		var pid = $('#publication option:selected').val();
		if(!pid){
			alert("预览失败！您还未选择杂志或您还未上传杂志！");
			return;
		}
		var data = {pubId : pid};
		$.ajax({
			url : SystemProp.appServerUrl + '/new-publisher/em-reader!doIssueJson.action',
			type : 'post',
			dataType: "json",
			data : data,
			success : callback
		});
	}
	
	function callback(result){
        var option = "";
		if(result.code == 200){
			var data = result.data.issueList;
			if(data && data.length){
                for(var j =0; j < data.length; j++){
                	if(data[j].status == 1){//上架状态
                		option += "<option value=\"" + data[j].id + "\">" + data[j].issueNumber + "</option>";
                	}
                }
			}
		} else {
			alert(result.message);
		}
		$('#issue').html(option).trigger('change');
	}
	
	function showSelIssue(){
		var model=$('#model input:checked').val();
		
		var isShow = model < 3;
		if(isShow)
			isShow = !$('#islatest')[0].checked;
		else 
			$('#islatest').attr('checked', 'checked');
		if(isShow)
			$('#selIssue').show();
		else
			$('#selIssue').hide();
	}
	
	$(function(){
		$('#colorSelector').ColorPicker({
			color: '#0000ff',
			onShow: function (colpkr) {
				$(colpkr).fadeIn(500);
				return false;
			},
			onHide: function (colpkr) {
				$(colpkr).fadeOut(500);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				$('#colorSelector div').css('backgroundColor', '#' + hex);
				color = hex;
				refreshColor();
			},
			onHide: function() {
				refresh();
			}
		}); 
	
		$('#model input').change(refresh);
		$('#widthHeight input').change(refresh);
		$('#issue').change(refresh);
		$('#islatest').click(refresh);
		$('#publication').change(getIssue).trigger('change');
		
		$('#copyCode').click(function(){
			copyToClipboard($('#emReaderStr').val());
		});
		showSelIssue();
	});