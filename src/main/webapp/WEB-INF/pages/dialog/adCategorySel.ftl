<#macro main>
	<div id="viewAdType" class="popContent">
		<fieldset>
			<h6>请选择您想预览的广告类型</h6>
			<form method="post" id="editIssueForm">
			<div>
			    <em class="g80"><label><input id="radioTypePreview" name="radioTypePreview" type="radio" value="2" checked/>图片</label></em>
			    <em class="g80"><label><input id="radioTypePreview" name="radioTypePreview" type="radio" value="3"/>视频</label></em>
			    <em><label><input id="radioTypePreview" name="radioTypePreview" type="radio" value="1"/>链接</label></em>
			</div>
			<div>
				<em><a id="previewDo" href="javascript:void(0)" class="btnBS">预览</a></em>
			    <em><a id="previewCancel" href="javascript:void(0)" class="btnWS">取消</a></em>
			    <input type="hidden" id="hidIssueId" name="hidIssueId"/>
			    <input type="hidden" id="hidPageNo" name="hidPageNo"/>
			    <input type="hidden" id="hidAdis" name="hidAdis"/>
		    </div>
		    </form>
		</fieldset>
	</div>
</#macro>