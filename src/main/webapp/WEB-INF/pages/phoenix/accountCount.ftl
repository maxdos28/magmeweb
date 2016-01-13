<#import "../components/phoenixAdminHeader.ftl" as header>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/phoenix/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/phoenix/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/phoenix/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/DatePicker-Mon.js"></script>
<script src="${systemProp.staticServerUrl}/phoenix/js/accountCount.js"></script>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<@header.main menuId="account"/>
<body>

<!--body-->
<div class="body">
    <div class="conLeftMiddleRight">
        <div class="conTools clearFix tCenter">
        	<fieldset>
            	<div>
                	<em>开始日期</em>
                	<em><input type="text" class="input g140 datepicker" id="datepicker1" onclick="setmonth(this,'yyyy-MM','2013-01-01','2050-12-30',1)"/></em>
                	<em>结束日期</em>
                	<em><input type="text" class="input g140 datepicker" id="datepicker2" onclick="setmonth(this,'yyyy-MM','2013-01-01','2050-12-30',1)"/></em>
                	<em><a href="#" id="search" class="btnBS">确定</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conB g470 floatr">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft">Android</td>
                    <td class="g100">&nbsp;</td>
                    <td class="g100">&nbsp;</td>
                  </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="tLeft">会员</td>
                        <td id="android-category">￥0元</td>
                        <td><a href="javascript:void(0)" id="android-export">导出Excel</a></td>
                    </tr>
                </tbody>
            </table>            
        </div>
        <div class="conB g470 floatl">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft">IOS</td>
                    <td class="g100">&nbsp;</td>
                    <td class="g100">&nbsp;</td>
                  </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="tLeft">杂志</td>
                        <td id="ios-magazine">￥0元</td>
                        <td><a href="javascript:void(0)" id="ios-magazine-export">导出Excel</a></td>
                    </tr>
                    <tr>
                        <td class="tLeft">会员</td>
                        <td id="ios-category">￥0元</td>
                        <td><a href="javascript:void(0)" id="ios-category-export">导出Excel</a></td>
                    </tr>
                    <tr>
                        <td class="tLeft"><strong>总计</strong></td>
                        <td id="ios-total"><strong>￥0元</strong></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>            
        </div>
        <div class="clear"></div>
        <div class="conB">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="tLeft">收入总计</td>
                    <td class="g100">&nbsp;</td>
                    <td class="g100">&nbsp;</td>
                  </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="tLeft">IOS + Android</td>
                        <td id="all-total">￥0元</td>
                        <td></td>
                    </tr>
                </tbody>
            </table>            
        </div>
        <div class="conFooter tRight">
                
		</div>
    </div>
</div>

</body>
</html>