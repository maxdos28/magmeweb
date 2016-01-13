 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
 <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/page/jquery.pagination.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/advertisevideo.js"></script>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />

<script>
var currentPage = ${currentPage!'1'};//广告的当前页码
var adPageCount = ${pageNo!'0'};//广告的总页数

</script>
 
<#import "advertisevideoPageTable.ftl" as pt>
<#import "../dialog/videoAdvertise.ftl" as video>
<@video.main />

<div class="conLeftMiddleRight" menu="editor" label="advideo">
    	<div class="con21 conTools">
        	<fieldset>
            		<form id="searchForm" method="post">
            	<div>
                	<em>生效时间范围</em>
                	<em><input id="startTimeAd" name="startTimeAd" type="text" class="input g100" /></em>
                	<em>至</em>
                	<em class="g150"><input id="endTimeAd" name="endTimeAd" type="text" class="input g100" /></em>
                	<em>
                		<select name="categoryId" id="categoryId">
							<option value="" <#if !(status??)> selected="true" </#if> >全部</option>
                            <option value="1" <#if status?? && status==1 > selected="true" </#if> >有效</option>
                            <option value="0" <#if status?? && status==0  > selected="true" </#if> >无效</option>
                        </select>
                	</em>
                    <em><a id="advertiseVideoSearchBtn" class="btnBS" href="#">搜索</a></em>
                    <em><a id="advertiseVideoAddBtn" class="btnWS" href="#">添加</a></em>
                </div>
                </form>
            </fieldset>
        </div>
        <div id="tablePageBarContainer" class="conB con33">
            <@pt.main />
        </div>
         <div class="conFooter">
        	<div id="eventListPageadd" class="gotoPage"></div>
			 <div id="eventListPage" class="changePage" ></div>
		</div>
</div>
<#import "../dialog/editAdvertiseVideo.ftl" as editAd>
<@editAd.main/>
    
