 <script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<script>
	var pageCount = ${pageNo!'1'};//总页数
$(function(){
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	datePickerFun($("#sendTime"),nowDate);
	$("#sendTime").val(nowDate);
	
		//时间控件
	//input_datepicker------------------------------------
	function datePickerFun($dateInput,date){
		if(!date) date = nowDate;
		$dateInput.DatePicker({
			format:'Y-m-d',
			date: date,
			current: date,
			starts: 0,
			position: 'bottom',
			onBeforeShow: function(){
				$dateInput.DatePickerSetDate($dateInput.val()||date, true);
			},
			onChange: function(formated, dates){
				$dateInput.val(formated);
//				$dateInput.DatePickerHide();
			}
		});
	}
});
</script>
<script src="${systemProp.staticServerUrl}/v3/dv/golf/workpublish.js"></script>
<#import "../dialog/adminTimingGolf.ftl" as pc>
<div class="body"  menu="news">
<div class="conLeftMiddleRight">
    	<div class="con37 conTools">
        	<a href="/golf/work-publish.action" ishome="2" class="btnSB">未审核</a>
        	<a href="/golf/work-publish!auditedPage.action" ishome="5" class="btnGB">已审核</a>
        </div>
          <div class="conB con38 clearFix">
        	<h2>分类筛选</h2>
        	 <#if creativeCategoryList?? && (creativeCategoryList?size>0)>
                       <#list creativeCategoryList as creativeCategory>
		                    <dl>
		                        <#-- 一级分类 ,一级分类的颜色表示-->
		                    	<dt><a  href="javascript:void(0);" class="navC1" parentid="${creativeCategory.id}">${creativeCategory.name}(${creativeCategory.creativeCount!''})</a></dt>
		                    	<#-- 二级级分类 -->
		                    	<#if (creativeCategory.childCreativeList)?? && ((creativeCategory.childCreativeList)?size>0)>
			                    	<#list creativeCategory.childCreativeList as cc>
				                    	<dd><a href="javascript:void(0);" parentId="${cc.parentId}" childCategoryId="${cc.id}" >${cc.name}(${cc.creativeCount!''})</a></dd>
			                    	</#list>
		                    	</#if>
		                    </dl>
	                    </#list>
                    </#if>
           
        </div>
        
        <div class="conB con42">
        	<fieldset>
            	<hr />
                <div>
                	<em><a class="btnAS" href="javascript:void(0)" id="workAuditedTime">修改发布时间</a></em>
                	<em><a class="btnWS"  href="javascript:void(0);" id="workAuditedAgainCheck">重新审核</a></em>
                </div>
            </fieldset>
        	
        </div>

        <div class="conB con43">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table JQtableBg">
              <thead>
                <tr>
                    <td class="g50"><label><input id="theadAllCheckBox" type="checkbox" />全选</label></td>
                    <td class="g80">作者</td>
                    <td class="g80">审核人</td>
                    <td class="tLeft">标题</td>
                    <td class="g150">分类</td>
                    <td class="g120">发布时间</td>
                    <td class="g80">操作</td>
                  </tr>
                </thead>
                <tbody id="tbodyContext"></tbody>
            </table>
        </div>
        
        <div class="conFooter">
           <div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
    </div>
 </div>
 <@pc.main/>