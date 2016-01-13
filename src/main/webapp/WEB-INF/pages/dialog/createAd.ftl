<#macro main>
	<div id="addAdvertiseDialog" class="popContent">
	    <fieldset>
	        <h6>
	            创建广告
	        </h6>
	        <form id="editAdvertiseForm" method="post" action="${systemProp.appServerUrl}/ad/manage-create!uploadfileJson.action">
	            <div>
	                <em class="title">标题</em>
	                <em>
	                    <input type="text" id="title" name="title" class="input g280" />
	                    <font color="red">*</font>
	                </em>
	            </div>
	            <div>
	                <em class="title">广告类型</em>
	                <em class="g80">
	                    <label>
	                        <input type="radio" id="adType" name="adType"  value="2" checked/>图片
	                    </label>
	                </em>
	                <em class="g80">
	                    <label>
	                        <input type="radio" id="adType" name="adType" value="3"/>视频
	                    </label>
	                </em>
	                <em>
	                    <label>
	                        <input type="radio" id="adType" name="adType" value="1" />链接
	                    </label>
	                </em>
	            </div>

	            <div id="mediadivid">
	                <em id="imgdivid" class="title">上传图片</em>
	                <em>
	                    <input type="file" id="jpgFile" name="jpgFile" class="g290" />
	                </em>
	            </div>

	            <div id="linkdivid">
	                <em id="imgurl" class="title">图片链接</em>
	                <em>
	                    <input type="text" id="linkurl" name="linkurl" class="g290" />
	                </em>
	            </div>

	            <div>
	                <em class="title">描述
	                </em>
	                <em>
	                    <textarea class="input g280" name="description" /></textarea>
	                </em>
	            </div>
	            <div>
	                <input type="hidden" id="adposIds" name="adposIds" value="" />
	                <input type="hidden" id="adId" name="adId" value="" />
	                <input type="hidden" name="fileName" id="fileName" value="" />
	                <em class="title">
	                </em>
	                <em>
	                    <a id="addsubmit" href="javascript:void(0)" class="btnBS">确定</a>
	                </em>
	                <em>
	                    <a id="addcancel" href="javascript:void(0)" class="btnWS">取消</a>
	                </em>
	                <em id="tipError" class="tipsError">
	                    请填写相关信息
	                </em>
	            </div>
	        </form>
	    </fieldset>
	</div>
</#macro>