<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script src="${systemProp.staticServerUrl}/v3/dv/sms/smsProjectStep.js"></script>
<div class="body">
    <div class="conLeftMiddleRight">
    	<div class="conTools con4 clearFix">
            <a class="btnWS floatl" href="${systemProp.kaiJieAppUrl}/sms/project-manage.action">返回</a>
            <a class="<#if stepNum??&&stepNum==1>btnOS<#else>btnBS</#if>" href="javascript:void(0);">第1步</a>
            <a class="<#if stepNum??&&stepNum==2>btnOS<#else>btnBS</#if>" href="javascript:void(0);">第2步</a>
            <a class="<#if stepNum??&&stepNum==3>btnOS<#else>btnBS</#if>" href="javascript:void(0);">第3步</a>
            <a class="<#if stepNum??&&stepNum==4>btnOS<#else>btnBS</#if>" href="javascript:void(0);">第4步</a>
            <a class="<#if stepNum??&&stepNum==5>btnOS<#else>btnBS</#if>" href="javascript:void(0);">第5步</a>
            <input type="hidden" id="smsProjectId" value="<#if smsProject??&&smsProject.id??>${smsProject.id!''}</#if>" />
        </div>
        <#if stepNum??&&stepNum==1>
        <div class="conB con5">
        	<fieldset class="new">
            	<div><em>项目名</em></div>
                <div><em><input type="text" name="name" class="input g300" maxlength="40" value="<#if smsProject??&&smsProject.name??>${smsProject.name!''}</#if>" /></em></div>
            </fieldset>
        </div>
        <div class="conFooter tCenter">
        <a class="btnRM" id="firstSave" href="javascript:void(0);">下一步</a>
		</div>
		<#elseif stepNum??&&stepNum==2>
		<div class="conB con6">
        	<fieldset class="new">
            	<div><em>选择模板</em></div>
            	<div class="tplList">
                	<ul class="tpl1">
                    	<li class="a <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl1_a'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl1a.jpg" /></a></li>
                    	<li class="b <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl1_b'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl1b.jpg" /></a></li>
                    	<li class="c <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl1_c'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl1c.jpg" /></a></li>
                    </ul>
                    <ul class="tpl2">
                    	<li class="a <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl2_a'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl2a.jpg" /></a></li>
                    	<li class="b <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl2_b'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl2b.jpg" /></a></li>
                    	<li class="c <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl2_c'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl2c.jpg" /></a></li>
                    </ul>
                    <ul class="tpl3">
                    	<li class="a <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl3_a'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl3a.jpg" /></a></li>
                    	<li class="b <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl3_b'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl3b.jpg" /></a></li>
                    	<li class="c <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl3_c'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl3c.jpg" /></a></li>
                    </ul>
                    <ul class="tpl4">
                    	<li class="a <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl4_a'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl4a.jpg" /></a></li>
                    	<li class="b <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl4_b'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl4b.jpg" /></a></li>
                    	<li class="c <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl4_c'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl4c.jpg" /></a></li>
                    </ul>
                    <ul class="tpl5">
                    	<li class="a <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl5_a'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl5a.jpg" /></a></li>
                    	<li class="b <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl5_b'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl5b.jpg" /></a></li>
                    	<li class="c <#if smsProject??&&smsProject.template??&&smsProject.template=='tpl5_c'>current</#if>"><a href="javascript:void(0);" name='selectModels' ><img src="${systemProp.staticServerUrl}/v3/sms/ui/tpl/tpl5c.jpg" /></a></li>
                    </ul>
                </div>
            </fieldset>
        </div>
        <div class="conFooter tCenter">
        <a class="btnWM" id="firstpage" href="javascript:void(0);" >上一步</a>
        <a class="btnRM" id="secondSave" href="javascript:void(0);">下一步</a>
		</div>
		<#elseif stepNum??&&stepNum==3>
		<script src="${systemProp.appServerUrl}/kindeditor/kindeditor.js"></script>
		<script>
			KE.show({
				id : 'webContent',
				resizeType : 2,
				allowImageUpload : false,
				items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'link'],
				afterCreate : function(id) {
					KE.event.ctrl(document, 13, function() {
						KE.sync(id);
					});
					
				},
				afterBlur:function(){
					$("#mobileIframeId").contents().find("#smsIframmeWebContent").html(KE.html('webContent'));
					$("#webContentHidden").val(KE.html('webContent'));
				}
			});
		</script>
		<div class="conB con7">
        	<fieldset class="new">
        	<input type="hidden" id="smsProjectTemplateValue" value="<#if smsProject??&&smsProject.template??>${smsProject.template!''}</#if>" />
            	<div>
                	<em class="floatr">剩余<span>60</span>字</em><em>短信内容</em>
                </div>
            	<div class="message">
                	<em><textarea class="input" maxlength="60" id="smsContent"><#if smsProject??&&smsProject.smsContent??>${smsProject.smsContent!''}</#if></textarea></em>
                </div>
                <hr />
                <div>
					<em class="g30">标题</em>
					<em><input type="text" class="input g560" id="webTitle" value="<#if smsProject??&&smsProject.webTitle??>${smsProject.webTitle!''}</#if>" /></em>
                </div>
                <input type="hidden" id="webContentHidden" value="<#if smsProject??&&smsProject.webContent??>${smsProject.webContent!''}</#if>" />
                <div class="content">
					<em class="g30">内容</em>
                	<em><textarea id="webContent" class="input g550" ><#if smsProject??&&smsProject.webContent??>${smsProject.webContent!''}</#if></textarea></em>
                </div>
                <!--
                <div>
					<em class="g30">视频</em>
					<em><input type="text" class="input g380" id="video" value="<#if videoUrl??>${videoUrl!''}</#if>" /></em>
                    <em>支持优酷，土豆网视频地址</em>
                </div>
                -->
                <div id="picDivBtn">
	                 <div id='addFileDiv1' name="addFileDivName" >
						<em class="g40">图片1</em>
						<em><input type="file" class="inputFile g370" id="picture1" name="picture1" /></em>
						<em><a class="btnBS" href="javascript:void(0);" fid='1' name='fileDel' path="<#if picture1ContentType??>${picture1ContentType!''}</#if>"  exid="${picture1FileName!''}" <#if picture1FileName??>style="display:block"<#else>style="display:none"</#if> >删除</a></em>
						<em><a class="btnWS" href="javascript:void(0);" fid='1' name="fileSave" <#if picture1FileName??>style="display:none"</#if> >提交</a></em>
	                </div>
	                <div id='addFileDiv2' name="addFileDivName" <#if picture2FileName??><#else>style="display:none"</#if> >
						<em class="g40">图片2</em>
						<em><input type="file" class="inputFile g370" id="picture2" name="picture2" /></em>
						<em><a class="btnBS" href="javascript:void(0);" fid='2' name='fileDel' path="<#if picture2ContentType??>${picture2ContentType!''}</#if>"  exid="${picture2FileName!''}" <#if picture2FileName??>style="display:block"<#else>style="display:none"</#if>  >删除</a></em>
						<em><a class="btnWS" href="javascript:void(0);" fid='2' name="fileSave" <#if picture2FileName??>style="display:none"</#if> >提交</a></em>
	                </div>
	                <div id='addFileDiv3' name="addFileDivName" <#if picture3FileName??><#else>style="display:none"</#if> >
						<em class="g40">图片3</em>
						<em><input type="file" class="inputFile g370" id="picture3" name="picture3" /></em>
						<em><a class="btnBS" href="javascript:void(0);" fid='3' name='fileDel' path="<#if picture3ContentType??>${picture3ContentType!''}</#if>"  exid="${picture3FileName!''}" <#if picture3FileName??>style="display:block"<#else>style="display:none"</#if>  >删除</a></em>
						<em><a class="btnWS" href="javascript:void(0);" fid='3' name="fileSave" <#if picture3FileName??>style="display:none"</#if> >提交</a></em>
	                </div>
	                 <div id='addFileDiv4' name="addFileDivName" <#if picture4FileName??><#else>style="display:none"</#if> >
						<em class="g40">图片4</em>
						<em><input type="file" class="inputFile g370" id="picture4" name="picture4" /></em>
						<em><a class="btnBS" href="javascript:void(0);" fid='4' name='fileDel' path="<#if picture4ContentType??>${picture4ContentType!''}</#if>"  exid="${picture4FileName!''}" <#if picture4FileName??>style="display:block"<#else>style="display:none"</#if>  >删除</a></em>
						<em><a class="btnWS" href="javascript:void(0);" fid='4' name="fileSave" <#if picture4FileName??>style="display:none"</#if> >提交</a></em>
	                </div>
	                <div id='addFileDiv5' name="addFileDivName" <#if picture5FileName??><#else>style="display:none"</#if> >
						<em class="g40">图片5</em>
						<em><input type="file" class="inputFile g370" id="picture5" name="picture5" /></em>
						<em><a class="btnBS" href="javascript:void(0);" fid='5' name='fileDel' path="<#if picture5ContentType??>${picture5ContentType!''}</#if>"  exid="${picture5FileName!''}" <#if picture5FileName??>style="display:block"<#else>style="display:none"</#if> >删除</a></em>
						<em><a class="btnWS" href="javascript:void(0);" fid='5' name="fileSave" <#if picture5FileName??>style="display:none"</#if> >提交</a></em>
	                </div>
                </div>
                <div>
					<em class="g40">&nbsp;</em>
                    <em><a class="btnOS" href="javascript:void(0);" id="picInputBtn">添加图片</a></em>
                    <em>最多添加5张</em>
                </div>
            </fieldset>
        </div>
        <div class="conB con8">
			<iframe class="mobile" id="mobileIframeId" width="320" height="480" border="0" src="../v3/sms/smsIframeTemplate.html"></iframe>
            <a id="mobileShowA" class="btnSend btnGS">发送手机预览</a>
        </div>
        
        <div class="clear conFooter tCenter">
        <a class="btnWM" id="secondpage" href="javascript:void(0);">上一步</a>
        <a class="btnRM" id="thirdSave" href="javascript:void(0);">下一步</a>
		</div>
		<#elseif stepNum??&&stepNum==4>
		<script>
		var pageCount = ${pageNo!'1'};//总页数
		var oldVal = ""; //翻页时临时存放的checked值
		<#if projectGroupRelList??>
			<#list projectGroupRelList as pgrlList>
				<#if pgrlList.phoneGroupId??>
					oldVal +=${pgrlList.phoneGroupId}+",";
				</#if>
			</#list>
		</#if>
		
		$(function(){
		initPage();
		//list
	function pageComm(currentPage){
		var data = {};
		data.pageNo=pageCount;
		data.currentPage=currentPage;
		data.id=$("#smsProjectId").val();
		
		var callback =function(result){
			//alert(result.code);
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
				pageCount = result.data.pageNo;
				}
				var trContent = "";
				if(result.data.smsPhoneGroupList){
					$.each(result.data.smsPhoneGroupList,function(i,p){
						trContent += "<tr>";
						trContent += "<td><label><input name='smsPhoneBox' value='"+p.id+"' type='checkbox'";
						
						var oldArr = oldVal.split(",");
						$.each(oldArr,function(key,val){
							if(val==p.id){
								trContent += " checked='checked'";
							}
						});
						
						trContent += " /></label></td>";
						trContent += "<td>"+p.name+"</td>";
						trContent += "<td>"+p.phoneCount+"</td>";
						trContent += "</tr>";
					});
					$("#tbodyContext").html(trContent);
					fnReadyTable();
				}
				
			}
		};
		
		$.ajax({
			url: SystemProp.kaiJieAppUrl+"/sms/project-manage!groupListJson.action",
			type: "POST",
			dataType: "json",
			data: data,
			success: callback
		});
	}
	
	function pageComm2(){
		var data = {};
		data.pageNo=pageCount;
		var callback =function(result){
			if(result.code!=200){
				alert(result.code);
			}else{
				if(result.data.pageNo){
					pageCount = result.data.pageNo;
				}
			}
		};
		$.ajax({
			url: SystemProp.kaiJieAppUrl+"/sms/project-manage!groupListJson.action",
			type: "POST",
			dataType: "json",
			async:false,
			data: data,
			success: callback
		});
	}
	
	//公共
	function pageselectCallback(page_id, jq){
		$("#eventListPageadd").html("");
		$("#eventListPageadd").append("跳转到<input class=\"input g20\"  type=\"text\" id=\"toPageValue\" />页  <span><a class=\"btnBS\" id=\"toPageOk\" href=\"javascript:void(0);\">确定</a></span>");
		pageComm2();
		pageComm(page_id+1);
		return false;
	}
	
	//初始化
	function initPage(){
		$("#eventListPage").pagination(pageCount, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: 20, //主体页数
			callback:pageselectCallback,
			items_per_page: 1, //每页显示1项
			prev_text: "前一页",
			next_text: "后一页"
		});
	}
	
	//页面跳转
	$("#toPageOk").live("click",function(){
			var currentPage = $("#toPageValue").val();
			if(currentPage>pageCount) {alert('超出最大页数');$("#toPageValue").val(""); return false;}
			if(currentPage<=0){currentPage=1} 
			$("#eventListPage").html("");
        	$("#eventListPage").pagination(pageCount, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 20, //主体页数
				callback:pageselectCallback,
				items_per_page: 1, //每页显示1项
				current_page:currentPage-1, 
				prev_text: "前一页",
				next_text: "后一页"
			});
		});
	});
		</script>
		<div class="conB con9">
        	<h2>选择通讯列表</h2>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50">选择</td>
                    <td>通讯列表名称</td>
                    <td class="g150">数量</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext">
                  
                </tbody>
            </table>
        </div>
        <div class="conFooter">
           <div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
        <div class="conFooter tCenter">
        <a class="btnWM" id="thirdpage" href="javascript:void(0);">上一步</a>
        <a class="btnRM" id="fourthSave" href="javascript:void(0);">下一步</a>
		</div>
		<#elseif stepNum??&&stepNum==5>
		<div class="conB con10">
        	<fieldset class="new">
            	<div>
                	<em class="g80">发送总数</em>
                	<em>${phoneNumCount!'0'}</em>
                </div>
            	<div>
                	<em class="g80">账户余额</em>
                	<em class="g160">${userMoneyStr!'0'}RMB</em>
                </div>
            	<div>
                	<em class="g80">发送费用</em>
                	<em class="g120">${symStr!'0'}RMB</em>
                	<em><a class="btnRS">充值</a></em>
                </div>
            </fieldset>
        </div>
        <div class="conFooter tCenter">
        <a class="btnWM" id="fourthpage" href="javascript:void(0);" >上一步</a>
        <a class="btnRM" id="fifthSave" href="javascript:void(0);" >确认发送</a>
		</div>
		</#if>
    </div>
</div>
