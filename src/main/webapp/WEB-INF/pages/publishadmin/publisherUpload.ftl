
<body>
<!--conMiddleRight-->
<div class="conMiddleRight">
  	<!--conTopbar-->
   	  <div class="conB conUploadStatus">
        	<h2>上传信息</h2>
        	<div class="conBody clearFix">
        	<#--从新处理-->
        	<#if swfRetransList?? && (swfRetransList?size>0)>
            	<strong>swf单页转换信息</strong>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table JQtableBg">
                  <thead>
                      <tr>
                        <td width="150">杂志名称</td>
                        <td width="100">期刊号</td>
                        <td>页码</td>
                        <td>状态</td>
                        <td>预览</td>
                        <td>替换</td>
                        <td>删除</td>
                    </tr>
                  </thead>
                  <tbody>
                    <#list swfRetransList as swfRetrans>
		                  <tr >
		                    <td>${swfRetrans.publicationName}</td>
		                    <td>${swfRetrans.issueNum}</td>
		                    <td>${swfRetrans.pageNo}</td>
		                    <td><#if swfRetrans.status==2>失败<#else>成功</#if></td>
		                    <td><a swfRetransId="${swfRetrans.id}" href="${systemProp.staticServerUrl}/tmpswf/${swfRetrans.publicationId}/${swfRetrans.issueId}/${swfRetrans.pageNo}.swf" target="_blank">预览</a></td>
		                    <td><a swfRetransReplace="${swfRetrans.id}">替换</a></td>
		                    <td><a swfRetransDel="${swfRetrans.id}">删除</a></td>
		                  </tr>
	                 </#list>
                  </tbody>
                </table>
                <br />
            </#if>
        	
        	<#if processIssueList?? && (processIssueList?size>0)>
	            	<strong>正在处理中</strong>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table JQtableBg">
	                  <thead>
	                      <tr>
	                        <td width="150">杂志名称</td>
	                        <td width="404">期刊号</td>
	                        <td>描述</td>
	                    </tr>
	                  </thead>
	                  <tbody>
	                    <#list processIssueList as processIssue>
			                  <tr>
			                    <td>${processIssue.publicationName}</td>
			                    <td>${processIssue.issueNumber}</td>
			                    <td>处理中…</td>
			                  </tr>
		                 </#list>
	                  </tbody>
	                </table>
	                <br />
                </#if>
                
                <#if mgzProcessList?? && (mgzProcessList?size>0)>
            	<strong>历史记录</strong>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table JQtableBg">
                  <thead>
                      <tr>
                        <td width="50">期刊id</td>
                        <td width="100">杂志名称</td>
                        <td width="65">期刊号</td>
                        <td width="150">失败的页面</td>
                        <td width="40">切割</td>
                        <#--<td width="150">页面大小(px)</td>-->
                        <td>耗时</td>
                        <td>重新转换swf</td>
                      </tr>
                  </thead>
                  <tbody>
	                     <#list mgzProcessList as mgzProcess>
		                    <tr mgzProcessId="${mgzProcess.id}">
		                        <td>${mgzProcess.issueId}</td>
			                    <td>${mgzProcess.publicationName}</td>
			                    <td><#if (mgzProcess.issueNumber)??>${mgzProcess.issueNumber}</#if></td>
			                    <td>
			                    	<#if (mgzProcess.swf)?? && (mgzProcess.missSwf)?? && (mgzProcess.missSwf!="")>
			                    	   <span class="floatl">SWF:</span>
			                    		<#list (mgzProcess.missSwf)?split(" ") as missSwf>
			                    			<a dealType="swf" mgzIssueId="${mgzProcess.issueId}" pageId="${missSwf}"  href="javascript:void(0)">${missSwf}</a>
			                    		</#list>
			                    	</#if>
			                    	
			                    	<#if (mgzProcess.jpg)?? && (mgzProcess.missJpg)?? && (mgzProcess.missJpg!="")>
			                    	   <span class="clear floatl">JPG:</span> 
			                    		<#list (mgzProcess.missJpg)?split(" ") as missJpg>
			                    			<a dealType="jpg" mgzIssueId="${mgzProcess.issueId}" pageId="${missJpg}" href="javascript:void(0)">${missJpg}</a>
			                    		</#list>
			                    	</#if>
			                    </td>
			                    <td><#if (mgzProcess.chopped)?? && (mgzProcess.chopped==1) >是<#else>否</#if></td>
			                    <#--<td><#if (mgzProcess.pageSize1)??>首页:${mgzProcess.pageSize1}</#if><#if (mgzProcess.pageSize2)??><br/>次页:${mgzProcess.pageSize2}</#if></td>-->
			                    <td><#if (mgzProcess.cost)??>${mgzProcess.cost}秒</#if></td>
			                    <td><a swfmgzProcessId="${mgzProcess.id}" href="javascript:void(0)">重新转换</a></td>
		                    </tr>
		                  </#list>
                  </tbody>
                </table>
                </#if>

        </div>
        </div>
</div>
</body>
    	
    