
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/dv/js/editAdminEmail.js"></script>

        <div id="adminEmailNotification" class="popContent">
        	<h6>邮件通知</h6>
        	<fieldset>
                <table class="table" width="450">
                	<thead>
                    	<tr>
                        	<td width="20%">编号</td>
                        	<td>联系人</td>
                        	<td width="20%">删除</td>
                        </tr>
                    </thead>
                </table>
                <div class="list" style="width:450px">
                    <table class="table JQtableBg" width="100%">
                        <tbody id="emailListContainer">
                        	<#if emails??>
                    			<#list emails as mail>
		                            <tr>
		                                <td width="20%">${mail.id}</td>
		                                <td>${mail.emailAddress}</td>
										<td width="20%"><a name="deleteEmail" class="btn"  mailId="${(mail.id)!""}" href="javascript:void(0)">删除</a></td>
		                            </tr>
	                            </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
            </fieldset>
            <div class="actionArea tRight">
                	<em><input id="emailAdress" type="text" class="input g150" /></em>
                	<em><a id="btn_add" class="btnWS">添加</a></em>
        	</div>     
	    </div>