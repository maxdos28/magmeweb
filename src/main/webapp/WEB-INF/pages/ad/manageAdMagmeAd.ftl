<#import "tablePageBar.ftl" as tablePageBar>

<script>
function turnPage(pageNum){
	var data=form2object('searchForm');
	data.pageNum=pageNum;
	data.pageSize=${pageSize!"10"};
	var callback=function(rs){
		$("#tablePageBarContainer").html(rs);
		fnReadyTable();
	}
	
	$.ajax({
		url:SystemProp.appServerUrl+"/ad/manage-magme-ad!queryAjax.action",
		type : "POST",
		data : data,
		success: callback
	});
}

$(function(){
	$("#submitSearchFormBtn").click(function(){
		turnPage(1);
	});
});
</script>

        <div class="conMiddleRight conManageAdMagmeAd">
        	<div class="conTopTools">
            	<fieldset class="clearFix">
            		<form id="searchForm" method="post">
            		<input type="hidden" name="issueId" value="${issueId!""}" />
                	<div class="clearFix">
                        <div class="floatl g200">
                            <em class="title">广告名称</em>
                            <em><input type="text" name="title" class="input g110" value="${title!""}"/></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">所属期刊</em>
							<em><input type="text" name="description" class="input g110" value="${description!""}"/></em>                            
                        </div>
                        <div class="floatl g210">
                            <em class="title">广告状态</em>
                            <em><select class="g130" name="status">
                            		<option value="">全部</option>
                            		<#list statusList as keyValue>
                            		<option value="${(keyValue.key)!""}" <#if status??&&status==keyValue.value>selected="selected"</#if>>
                            			${(keyValue.value)!""}
                            		</option>
                            		</#list>
                            	</select>
                            </em>
                        </div>
                        <div class="floatl">
                            <em class="title">广告类型</em>
                            <em>
                            	<select class="g90" name="adType">
                            		<option value="">全部</option>
                            		<#list adTypeList as keyValue>
                            		<option value="${(keyValue.key)!""}" <#if adType??&&adType==keyValue.value>selected="selected"</#if>>
                            			${(keyValue.value)!""}
                            		</option>
                            		</#list>                            		
                            	</select>
                            </em>
                        </div>                        
                    </div>
                    <hr />
                    <div class="clearFix">
                        <div class="floatr">
                            <em><a id="submitSearchFormBtn" href="javascript:void(0)" class="btnBS">搜索</a></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">用户名称</em>
                            <em><input type="text" name="userName" value="${userName!""}" class="input g110" /></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">广告编号</em>
                            <em><input type="text" name="id" value="${id!""}" class="input g110" /></em>
                        </div>
                        <div class="floatl">
                            <em class="title">创建时间</em>
                            <em><input type="text" id="magmeadcreatedTimeBegin" name="createdTimeBegin" value="${(createdTimeBegin?string("yyyy-MM-dd"))!""}" readonly = true class="input g90" /></em>
                            <em>--</em>
                            <em><input type="text" id="magmeadcreatedTimeEnd" name="createdTimeEnd" value="${(createdTimeEnd?string("yyyy-MM-dd"))!""}" readonly = true class="input g90" /></em>
                        </div>
                    </div>
                    </form>
                </fieldset>
            </div>
            <div id="tablePageBarContainer" class="conB">
			<@tablePageBar.manageAdMagmeAd /> 				
            </div>
		</div>