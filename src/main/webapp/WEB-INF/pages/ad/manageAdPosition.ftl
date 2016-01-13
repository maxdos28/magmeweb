<#import "tablePageBar.ftl" as tablePageBar>
<#import "../dialog/editAdPositionDialog.ftl" as editAdPositionDialog>

<script type="text/javascript" src="${systemProp.staticServerUrl}/js/manageAd.js"></script>
        <div class="conMiddleRight conManageAdPosition">
        <input id="pageSize" type="hidden" value="${pageSize!"10"}">
        <!--conPubTopbar-->
            <div class="conB conPubTopbar" id="pubTopbar">
                <div class="search">
                    <input id="searchContent" type="text" tips="输入杂志名" class="text" /><input id="searchBtn" type="submit" class="btn" value=""/>
                </div>
                <h2>杂志管理</h2>
                <div class="conBody clearFix">
                    <a class="btnLR turnLeft" href="javascript:void(0)"></a>
                    <div class="outer">
                        <div class="inner">
                        	<#list publicationList as publication>
                            <div name="adPositionPublication" publicationid="${(publication.id)!""}" class="item <#if publication_index==0>current</#if>">
                            	<a href="javascript:void(0)">
                                <strong>${(publication.name)!""}</strong>
                                <p>${(publication.description)!""}</p>
                                <p>期刊数量<span>(${(publication.totalIssues)!"0"})</span></p>
                                </a>
                            </div>
                            </#list>
                        </div>
                    </div>
                    <a class="btnLR turnRight" href="javascript:void(0)"></a>
                </div>
            </div>        
        
        	<div id="tablePageBarContainer">
        	<@tablePageBar.manageAdPosition />
        	</div>
		</div>
		
<@editAdPositionDialog.main />		