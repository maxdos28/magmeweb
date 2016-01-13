<#macro main class>
<div id="adminAppConfirm" class="popContent" style="width:550px;">
            <h6><#if class=="view">查看App信息<#else>请确认以下信息，确认后将不可修改！</#if></h6>
        	<div class="conBody">
                <fieldset>
                    <div>
                        <em class="g80">应用名称</em>
                        <em class="important" id="app_name" ></em>
                    </div>
                    <div>
                        <em class="g80">关键字</em>
                        <em class="important" id="app_kewWord"></em>
                    </div>
                    <div>
                        <em class="g80">描述</em>
                        <em class="important" id="app_description"></em>
                    </div>
                    <div class="g250 floatl">
                        <em class="g80">版权信息</em>
                        <em class="important" id="app_info"></em>
                    </div>
                    <div>
                        <em class="g50">所有者</em>
                        <em class="important" id="app_user"></em>
                    </div>
                    <div class="g250 floatl">
                        <em class="g80">二级分类</em>
                        <em class="important" id="app_secondType"></em>
                    </div>
                    <div>
                        <em class="g80">所选杂志名称</em>
                        <em class="important" id="app_publication"></em>
                    </div>
                </fieldset>
       		</div>
            <div class="actionArea tRight">
            	<#if class=="view">
            		<a class="btnSM" href="javascript:void(0);" id="viewAppChange" >关闭</a>
            	<#else>
            		<a class="btnSM" href="javascript:void(0);" id="viewAppChange" >取消</a>&nbsp;&nbsp;<a class="btnAM" href="javascript:void(0);" id="viewAppOk"  >确定</a>
            	</#if>
                <div>
                        &nbsp;
                    </div>
            </div>
        </div>
</#macro>