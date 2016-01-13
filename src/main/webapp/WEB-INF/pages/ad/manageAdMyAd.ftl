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
		url:SystemProp.appServerUrl+"/ad/manage-my-ad!queryAjax.action",
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

        <div class="conMiddleRight conManageAdMyAd">
        	<div class="conTopTools">
            	<fieldset class="clearFix">
            		<form id="searchForm" method="post">
                	<div class="clearFix">
                        <div class="floatr">
                            <em><a id="submitSearchFormBtn" href="javascript:void(0)" class="btnBS">搜索</a></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">广告名称</em>
                            <em><input type="text" name="title" class="input g130" value="${title!""}"/></em>
                        </div>
                        <div class="floatl g200">
                            <em class="title">广告编号</em>
                            <em><input type="text" name="id" class="input g130" value="${id!""}"/></em>
                        </div>
                        <div class="floatl g290">
                            <em class="title">时间</em>
                            <em><input type="text" id="myadcreatedTimeBegin" name="createdTimeBegin" value="${(createdTimeBegin?string("yyyy-MM-dd"))!""}" readonly = true class="input g100" /></em>
                            <em>--</em>
                            <em><input type="text" id="myadcreatedTimeEnd" name="createdTimeEnd" value="${(createdTimeEnd?string("yyyy-MM-dd"))!""}" readonly = true class="input g100" /></em>
                        </div>
                    </div>
                    </form>
                </fieldset>
            </div>
            <div id="tablePageBarContainer" class="conB">            
			<@tablePageBar.manageAdMyAd />
			</div>
		</div>