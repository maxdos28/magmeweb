<#macro main>
<div id="viewAdDetail" class="popContent">
	    	<h6>添加首页广告</h6>
	    	<fieldset>
	        	<form method="post" id="editAdvertisForm"  enctype="multipart/form-data" onsubmit="return false;">
	        	<input type="hidden" id="isadd" name="isadd" value="1" />
	        	<input type="hidden" id="id" name="id" value="" />
	            <div>
                	<em class="title" >标题</em>
                    <em><input type="text"  name="title" id="title" class="input g280" /></em>
                </div>
                <div>
                    <em class="title">描述</em>
                    <em><textarea class="input g280" name="description" id="description" value="" ></textarea></em>
                </div>
	            <div>
                	<em class="title">标签</em>
                    <em><input type="text" id="tags" name="tags" value="" class="input g280" /></em>
                </div>
                
                <div>
                    <em class="title">视频上传</em>
                    <em><input type="file"  id="adFile" name="adFile"class="input g280" /></em>
                </div>
                <#-- 
                <div>
                    <em class="title">广告状态</em>
                    <em>
                       <select id="status" name="status">
                            <option value="1">有效</option>
                            <option value="0">无效</option>
                        </select>
                    </em>
                </div>-->
                <div>
                    <em class="title">广告状态</em>
                    <em>
                       <select id="adType" name="adType">
                            <option value="1">首页</option>
                            <option value="2">女友首页</option>
                        </select>
                    </em>
                </div>
                <div>
                    <em class="title">生效时间</em>
                    <em>
                     <input type="text" id="startTime" name="startTime" value=""  class="input g280" />
                    </em>
                </div>
                <div>
                    <em class="title">失效时间</em>
                    <em>
                       <input type="text" id="endTime" name="endTime" value=""  class="input g280" />
                    </em>
                </div>
               
	            </form>
    </fieldset>
     <div class="actionArea tRight">
                    <a href="javascript:void(0)" id="adSave" class="btnAM">保存</a>
                    <a href="javascript:void(0)" id="adClose" class="btnWM">关闭</a>
                    <em id ="tipError" class="tipsError">请填写相关信息</em>
	            </div>
</div>
</#macro>
