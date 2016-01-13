<script>
	var xx='${xx!}';
</script>
<script src="${systemProp.staticServerUrl}/v3/widget/js/newEvent.js"></script>
        <!--magezineBox-->
        <div id="bodyEvent" class="bodyEvent clearFix">
        
            <#list evePageInfo.data as event >
    			<#if event_index lt 20>
                <div class="item size${event.eventClass}">
					<a eventId="${event.id}" class="photo" href="javascript:void(0)" name="reader">
						<img src="${systemProp.fpageServerUrl}/event/${event.imgFile}" />
						<h5 class="png">${event.title!}</h5>
					</a>
                </div>
                </#if>
            </#list>
        </div>
        
		<#if evePageInfo?? && evePageInfo.totalPage??>
        <div class="control">
        <#if evePageInfo.totalPage gt 9>
        <a id="turnFirst" class="turn first" href="javascript:void(0)">></a>
        <a id="turnLeft" class="turn left" href="javascript:void(0)">></a></#if><#if evePageInfo.totalPage gt 1><#list 1..evePageInfo.totalPage as x><#if x lt 10><a page="${x}" href="javascript:void(0)" <#if x=1>class="current"</#if>>${x}</a></#if></#list>
            </#if>
            
        <#if evePageInfo.totalPage gt 9>
        <a id="turnRight" class="turn right" href="javascript:void(0)"><</a>  
        <a id="turnLast" class="turn last" href="javascript:void(0)"><</a>  
        </#if>
        
        </div>
        </#if>

<script type="text/javascript">
var pageNo = 1;
var totalSize = parseInt("${evePageInfo.total}");
var totalPage = parseInt("${evePageInfo.totalPage}");
</script>