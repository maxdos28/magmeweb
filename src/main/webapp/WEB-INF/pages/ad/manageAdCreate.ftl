<div class="conMiddleRight conManageAdCreate">
    <div class="conTopTools">
        <fieldset class="clearFix">
            <div class="clearFix">
                <div class="floatr">
		<#assign adUser=session_aduser/>
		    <#if adUser.level == 3>
                    <em>
                        <a id ="quickcreatead" href="javascript:void(0)" class="btnWS">快速创建广告</a>
                    </em>
		    </#if>
                </div>
                <div class="floatl g160">
                    <em class="title">
                        选择类别
                    </em>
                    <em>
                        <select id="categoryId" name="categoryId" onchange="pubdatasouse()">
				<option value="-1">全部类别</option>
                           <#if categoryList??>
                                <#list categoryList as category>
                                    <option value="${category.id}">${category.name}</option>
                                </#list>
                            </#if>
                        </select>
                    </em>
                </div>
                <div class="floatl g200">
                    <em class="title">
                        选择杂志
                    </em>
                    <em>
                        <select id="pubId" name="pubId" class="g120" onchange="issuedatasouse()">  
				<option value="-1">全部杂志</option>
                        </select>
                    </em>
                </div>
                <div class="floatl g230">
                    <em class="title">
                        选择期刊
                    </em>
                    <em>
                        <select id="issueId" name="issueId">
                           <option value="-1">全部期刊</option>
                        </select>
                    </em>
                </div>
            </div>
            <hr />
            <div class="clearFix">
                <div class="floatr">
                    <em>
                        <a id="sear" href="javascript:void(0)" class="btnBS g140">搜索</a>
                    </em>
                </div>
                <div class="floatl g160">
                    <em class="title">
                        广告位内容
                    </em>
                    <em>
                        <input id="adposDescription" name="adposDescription" type="text" class="input g70" />
                    </em>
                </div>
                <div class="floatl g200">
                    <em class="title">
                        广告位品牌
                    </em>
                    <em>
                        <input id="adposKeywords" name="adposKeywords" type="text" class="input g110" />
                    </em>
                </div>
                <div class="floatl">
                    <em class="title">
                        广告位编号
                    </em>
                    <em>
                        <input id="adposid" name="adposid" type="text" class="input g80" />
                    </em>
                </div>
            </div>
        </fieldset>
    </div>
    <div class="conB">
        <h2>
            <a id="multiad" href="javascript:void(0)" class="btnOS floatr">批量添加广告</a>
            现有广告位
        </h2>
        <table id="createadtable" class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <td width="36">
                        选择
                    </td>
                    <td width="75">
                        广告位编号
                    </td>
                    <td width="100">
                        所属期刊
                    </td>
                    <td width="60">
                        内容
                    </td>
                    <td width="60">
                        品牌
                    </td>
                    <td>
                        广告位描述
                    </td>
                    <td width="60">
                        预览
                    </td>
                    <td width="60">
                        广告添加
                    </td>
                </tr>
            </thead>
            <tbody id="detaillst">
            </tbody>
        </table>
	
    </div>
</div>