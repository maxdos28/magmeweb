<#macro main>
	<div id="addAdvertiseDialog" class="popContent" >
    	<fieldset>
        	<h6>新建广告</h6>
             <form id="editAdvertiseForm" method="post" action="${systemProp.appServerUrl}/publish/advertise!updateAdJson.action">
         		<div id="addAdIdTab">
	            	<em class="title">选择广告</em>
	            	<em>
	            		<select class="col-8" id="addAdId" name="addAdId">
	            		    <option  value="0">请选择</option>
	            		    <#if adList??>
	            		       <#list adList as ad>
									<option  value="${ad.id}">${ad.title}</option>
							   </#list>
							</#if>
						</select>
					</em>
	            </div>
	            <div>
	            	<em class="title">标题</em>
	            	<em><input type="text" name="title" class="input g200" /><font color="red">*</font></em>
	            </div>
	            <div>
	            	<em class="title">关键词</em>
	            	<em><input type="text" name="keyword" class="input g200" ><font color="red">*</font></em>
	            </div>
	            <div>
	            	<em class="title">广告类型</em>
	            	<em> 
		            	<select class="col-8" name="adType">
							<option  value="1">链接广告</option>
							<option  value="2">图片广告</option>
							<option  value="3">视频广告</option>
						 </select><font color="red">*</font>
					 </em>
	            </div>
	            <div  id="startTime">
	            	<em class="title">广告开始时间</em>
	            	<em><input id="startTime" type="text" name="startTime" class="input g200" /><font color="red">*</font></em>
	            </div>
	            <div  id="endTime">
	            	<em class="title">广告结束时间</em>
	            	<em><input type="text" name="endTime" class="input g200" /><font color="red">*</font></em>
	            </div>
	            <div>
	            	<em class="title">广告链接</em>
	            	<em><input type="text" name="linkurl" class="input g200"  /></em>
	            </div>
	            <div>
	            	<em class="title">图片广告</em>
	            	<em><input type="file" id="jpgFile" name="jpgFile" class="g210"  /></em>
	            </div>
	            <div>
	            	<em class="title">视频链接</em>
	            	<em><input type="text" name="mediaurl" class="input g200" /></em>  
	            </div>
	            <div>
	            	<em class="title">描述</em>
	            	<em><input type="text" name="description" class="input g200" /></em>
	            </div>
	            <div>
                    <input type="hidden" id="adposIds" name="adposIds" value=""/>
                    <input type="hidden" id="adId" name="adId" value=""/>
                    <input type="hidden" name="fileName" id="fileName" value=""/>
                    <em class="title"></em>
	                <em ><a id="addsubmit" href="javascript:void(0)" class="btnBS" >确定</a></em>
	                <em ><a id="addcancel" href="javascript:void(0)" class="btnWS" >取消</a></em>
	                <em id ="tipError" class="tipsError">请填写相关信息</em>
	            </div>
	            
           </form>
        </fieldset>
    </div>
</#macro>