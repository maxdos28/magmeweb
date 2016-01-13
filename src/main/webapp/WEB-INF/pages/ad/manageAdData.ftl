<div class="conMiddleRight conManageAdData">
    <div class="conTopTools">
        <fieldset class="clearFix">
	    <form id="adDataForm" method="post">	
            <div class="clearFix">
                <div class="floatr">
                    <em>
                        <a id="seardata" href="javascript:void(0)" class="btnBS">搜索</a>
                    </em>
                </div>
                <div class="floatl g200">
                    <em class="title">
                        广告名称
                    </em>
                    <em>
                        <input type="text" id="adname" name="adname" value="${adname!""}" class="input g130" />
                    </em>
                </div>
                <div class="floatl g200">
                    <em class="title">
                        所属期刊
                    </em>
                    <em>
                        <input type="text" id="issuenm" name="issuenm" value="${issuenm!""}" class="input g130" />
                    </em>
                </div>
                <div class="floatl g290">
                    <em class="title">
                        创建时间
                    </em>
                    <em>
                        <input type="text" id="begintime" name="begintime" value="${(begintime?string("yyyy-MM-dd"))!""}" readonly = true class="input g90" />
                    </em>
                    <em>
                        --
                    </em>
                    <em>
                        <input type="text" id="endtime" name="endtime" value="${(endtime?string("yyyy-MM-dd"))!""}" readonly = true class="input g90" />
                    </em>
                </div>
            </div>
	    </form>
        </fieldset>
    </div>
    <div class="conB">
        <h2>
            广告列表
        </h2>
        <table id="datatable" class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <td width="50">
                        广告编号
                    </td>
                    <td>
                        广告名称
                    </td>
                    <td width="80">
                        上架时间
                    </td>
                    <td width="50">
                        状态
                    </td>
                    <td width="60">
                        阅读次数
                    </td>
                    <td width="50">
                        点击%
                    </td>
                    <td width="60">
                        链接点击%
                    </td>
                    <td width="80">
                        平均停留时长
                    </td>
                    <td width="130">
                        所属期刊
                    </td>
                </tr>
            </thead>
            <tbody>
	    <#if adDataLst??>
		<#list adDataLst as adData>
			<tr>
			    <td>
				${(adData.id)!""}
			    </td>
			    <td>
				${(adData.name)!""}
			    </td>
			    <td>
				${(adData.openTime)!""}
			    </td>
			    <td>
				${(adData.status)!""}
			    </td>
			    <td>
				${(adData.readCount)!""}
			    </td>
			    <td>
				${(adData.clickCount)!""}
			    </td>
			    <td>
				${(adData.linkClickCount)!""}
			    </td>
			    <td>
				${(adData.stayTime)!""}
			    </td>
			    <td>
				${(adData.issueName)!""}
			    </td>
			</tr>
		</#list>
	    </#if>
            </tbody>
        </table>
	${(pageBar)!""}
	
    </div>
</div>