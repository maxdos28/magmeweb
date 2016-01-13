<script src="${systemProp.staticServerUrl}/v3/widget20120529/js/pub.js"></script>
    <!--conTagWall-->
    <div class="outer">
        <!--notification-->
        <div class="notification">
        </div>
        <!--magezineBox-->
        <div id="bodyMagazine" class="desk clearFix">
		<#if magPageInfo?? && magPageInfo.data??>
		    <#list magPageInfo.data as issue>
			<#if issue_index lt 8>
			    <div class="item">
				<a class="png" href="javascript:void(0)" issueId="${issue.id}" name="reader">
					<img src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" />
					<p name="issueRead" class="png">
						${issue.publicationName}<span>${issue.issueNumber}</span>
						<em class="iconHeart" pubId="${issue.publicationId}"></em>
						<em class="iconForward" pubName="${issue.publicationName}"></em>
					</p>
				</a>
			    </div>
			</#if>
		    </#list>
		</#if>
        </div>
        <div class="changePage">
            <a class="pre png" href="javascript:void(0)" title="上一页"></a>
            <a class="next png" href="javascript:void(0)" title="下一页"></a>
        </div>
    </div>
<script type="text/javascript">
	var xx= "${xx!}"; 
	function getX(){
		return xx;
	}
    var pageNo = 1;
    var lastestIssueId = "${issue.id!}";
    var lastestIssueNm = "${issue.publicationName!}" + " " + "${issue.issueNumber!}";
    var totalSize = parseInt("${magPageInfo.total}");
    var totalPage = parseInt("${magPageInfo.totalPage}");
    var srcFlg = "${srcFlg!}";


</script>